/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.retria.DAO;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.mycompany.retria.MODEL.MaquinaUltrassomEspecificada;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author lucka
 */
public class MaquinaUltrassomEspecificadaDAO {

    Looca looca = new Looca();

    Memoria memoria = looca.getMemoria();
    Processador processador = looca.getProcessador();
    DiscoGrupo grupoDeDiscos = looca.getGrupoDeDiscos();
    Sistema sistema = looca.getSistema();

    JdbcTemplate con;
    JdbcTemplate conMysql;

    public MaquinaUltrassomEspecificadaDAO() {
        Conexao conexao = new Conexao();
        con = conexao.getConnection();
        ConexaoMySqlEc2 conMysqlEc2 = new ConexaoMySqlEc2();
        conMysql = conMysqlEc2.getConnection();
    }


    public MaquinaUltrassomEspecificada getMaquiUltassomEspecCPU(Double usoMaximo, Integer fkMaquina, Integer fkEspecComp) {
        Integer usoMaximotoInt = usoMaximo.intValue();
        List<MaquinaUltrassomEspecificada> maquinaUltraEspec = con.query(String.format("""
                        select
                            m.*
                        from 
                            maquina_ultrassom_especificada m
                        join 
                            especificacao_componente e
                        on
                            m.fk_especificacao_componente = e.id_especificacao_componente
                        where 
                            uso_maximo = %d
                        and fk_maquina = %d
                        and fk_especificacao_componente = %d                
                        and e.tipo_componente = 'CPU';
                    """, usoMaximotoInt, fkMaquina, fkEspecComp),
                new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));

        List<MaquinaUltrassomEspecificada> maquinaUltraEspecLocal = conMysql.query(String.format("""
                        select
                            m.*
                        from 
                            maquina_ultrassom_especificada m
                        join 
                            especificacao_componente e
                        on
                            m.fk_especificacao_componente = e.id_especificacao_componente
                        where 
                            uso_maximo = %d
                        and fk_maquina = %d
                        and fk_especificacao_componente = %d                
                        and e.tipo_componente = 'CPU';
                    """, usoMaximotoInt, fkMaquina, fkEspecComp),
                new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));

        if (maquinaUltraEspec.isEmpty()) {
            con.execute(String.format("""
                        insert into maquina_ultrassom_especificada 
                            (uso_maximo, fk_maquina, fk_especificacao_componente)
                        values (%d, %d, %d);
                      """, usoMaximotoInt, fkMaquina, fkEspecComp));

            maquinaUltraEspec
                    = con.query(String.format("""
                        select
                            m.*
                        from 
                            maquina_ultrassom_especificada m
                        join 
                            especificacao_componente e
                        on
                            m.fk_especificacao_componente = e.id_especificacao_componente
                        where 
                            uso_maximo = %d
                        and fk_maquina = %d
                        and fk_especificacao_componente = %d                
                        and e.tipo_componente = 'CPU';
                    """, usoMaximotoInt,fkMaquina, fkEspecComp),
                    new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));
        }

        if (maquinaUltraEspecLocal.isEmpty()) {
            MaquinaUltrassomEspecificada dados = maquinaUltraEspec.get(0);

            conMysql.execute(String.format("""
                        insert into maquina_ultrassom_especificada 
                            (id_especificacao_componente_maquina,uso_maximo, fk_maquina, fk_especificacao_componente)
                        values (%d,%d, %d, %d);
                      """,dados.getId_especificacao_componente_maquina(),usoMaximotoInt, fkMaquina, fkEspecComp));
        }

        MaquinaUltrassomEspecificada dados = maquinaUltraEspec.get(0);

        System.out.println("ESPEFIFICAÇÃO DE COMPONENTES CADASTRADOS COM SUCESSO!");
        return new MaquinaUltrassomEspecificada(dados.getId_especificacao_componente_maquina(),
                dados.getUso_maximo(),
                dados.getFk_maquina(),
                dados.getFk_especificacao_componente());
    }

    public MaquinaUltrassomEspecificada getMaquiUltassomEspecRAM(Double usoMaximo, Integer fkMaquina, Integer fkEspecComp) {
        List<MaquinaUltrassomEspecificada> maquinaUltraEspec = con.query(String.format("""
                        select
                            m.*
                        from 
                            maquina_ultrassom_especificada m
                        join 
                            especificacao_componente e
                        on
                            m.fk_especificacao_componente = e.id_especificacao_componente
                        where 
                            uso_maximo = %.0f
                        and fk_maquina = %d
                        and fk_especificacao_componente = %d                
                        and e.tipo_componente = 'RAM';
                    """, usoMaximo, fkMaquina, fkEspecComp),
                new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));

        List<MaquinaUltrassomEspecificada> maquinaUltraEspecLocal = conMysql.query(String.format("""
                        select
                            m.*
                        from 
                            maquina_ultrassom_especificada m
                        join 
                            especificacao_componente e
                        on
                            m.fk_especificacao_componente = e.id_especificacao_componente
                        where 
                            uso_maximo = %.0f
                        and fk_maquina = %d
                        and fk_especificacao_componente = %d                
                        and e.tipo_componente = 'RAM';
                    """, usoMaximo, fkMaquina, fkEspecComp),
                new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));

        if (maquinaUltraEspec.isEmpty()) {
            con.execute(String.format("""
                        insert into maquina_ultrassom_especificada 
                            (uso_maximo, fk_maquina, fk_especificacao_componente)
                        values (%.0f, %d, %d);
                      """, usoMaximo, fkMaquina, fkEspecComp));

            maquinaUltraEspec
                    = con.query(String.format("""
                        select
                            m.*
                        from 
                            maquina_ultrassom_especificada m
                        join 
                            especificacao_componente e
                        on
                            m.fk_especificacao_componente = e.id_especificacao_componente
                        where 
                            uso_maximo = %.0f
                        and fk_maquina = %d
                        and fk_especificacao_componente = %d                
                        and e.tipo_componente = 'RAM';
                    """, usoMaximo, fkMaquina, fkEspecComp),
                            new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));

        }

        if (maquinaUltraEspecLocal.isEmpty()) {
            MaquinaUltrassomEspecificada dados = maquinaUltraEspec.get(0);

            conMysql.execute(String.format("""
                        insert into maquina_ultrassom_especificada 
                            (id_especificacao_componente_maquina, uso_maximo, fk_maquina, fk_especificacao_componente)
                        values (%d,%.0f, %d, %d);
                      """,dados.getId_especificacao_componente_maquina() ,usoMaximo, fkMaquina, fkEspecComp));
        }

        MaquinaUltrassomEspecificada dados = maquinaUltraEspec.get(0);

        System.out.println("ESPEFIFICAÇÃO DE COMPONENTES CADASTRADOS COM SUCESSO!");
        return new MaquinaUltrassomEspecificada(dados.getId_especificacao_componente_maquina(),
                dados.getUso_maximo(),
                dados.getFk_maquina(),
                dados.getFk_especificacao_componente());
    }

    public MaquinaUltrassomEspecificada getMaquiUltassomEspecDISCO(Double usoMaximo, Integer fkMaquina, Integer fkEspecComp) {
        Integer usoMaximotoInt = usoMaximo.intValue();
        List<MaquinaUltrassomEspecificada> maquinaUltraEspec = con.query(String.format("""
                        select
                            m.*
                        from 
                            maquina_ultrassom_especificada m
                        join 
                            especificacao_componente e
                        on
                            m.fk_especificacao_componente = e.id_especificacao_componente
                        where 
                            uso_maximo = %d
                        and fk_maquina = %d
                        and fk_especificacao_componente = %d                
                        and e.tipo_componente = 'DISCO';
                    """, usoMaximotoInt,fkMaquina, fkEspecComp),
                new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));

        List<MaquinaUltrassomEspecificada> maquinaUltraEspecLocal = conMysql.query(String.format("""
                        select
                            m.*
                        from 
                            maquina_ultrassom_especificada m
                        join 
                            especificacao_componente e
                        on
                            m.fk_especificacao_componente = e.id_especificacao_componente
                        where 
                            uso_maximo = %d
                        and fk_maquina = %d
                        and fk_especificacao_componente = %d                
                        and e.tipo_componente = 'DISCO';
                    """, usoMaximotoInt,fkMaquina, fkEspecComp),
                new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));


        if (maquinaUltraEspec.isEmpty()) {
            con.execute(String.format("""
                        insert into maquina_ultrassom_especificada 
                            (uso_maximo, fk_maquina, fk_especificacao_componente)
                        values (%d, %d, %d);
                      """, usoMaximotoInt,fkMaquina, fkEspecComp));

            maquinaUltraEspec
                    = con.query(String.format("""
                        select
                            m.*
                        from 
                            maquina_ultrassom_especificada m
                        join 
                            especificacao_componente e
                        on
                            m.fk_especificacao_componente = e.id_especificacao_componente
                        where 
                            uso_maximo = %d
                        and fk_maquina = %d
                        and fk_especificacao_componente = %d               
                        and e.tipo_componente = 'DISCO';
                    """, usoMaximotoInt,fkMaquina, fkEspecComp),
                            new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));
        }

        if (maquinaUltraEspecLocal.isEmpty()) {
            MaquinaUltrassomEspecificada dados = maquinaUltraEspec.get(0);

            conMysql.execute(String.format("""
                        insert into maquina_ultrassom_especificada 
                            (id_especificacao_componente_maquina,uso_maximo, fk_maquina, fk_especificacao_componente)
                        values (%d,%d, %d, %d);
                      """,dados.getId_especificacao_componente_maquina(), usoMaximotoInt,fkMaquina, fkEspecComp));

        }

        MaquinaUltrassomEspecificada dados = maquinaUltraEspec.get(0);

        System.out.println("ESPEFIFICAÇÃO DE COMPONENTES CADASTRADOS COM SUCESSO!");
        return new MaquinaUltrassomEspecificada(dados.getId_especificacao_componente_maquina(),
                dados.getUso_maximo(),
                dados.getFk_maquina(),
                dados.getFk_especificacao_componente());
    }

    public MaquinaUltrassomEspecificada getMaquiUltassomEspecRede(Integer fkMaquina, Integer fkEspecComp) {

        List<MaquinaUltrassomEspecificada> maquinaUltraEspec = con.query(String.format("""
                        select
                            m.*
                        from 
                            maquina_ultrassom_especificada m
                        join 
                            especificacao_componente e
                        on
                            m.fk_especificacao_componente = e.id_especificacao_componente
                        where 
                         fk_maquina = %d
                        and fk_especificacao_componente = %d                
                        and e.tipo_componente = 'REDE';
                    """, fkMaquina, fkEspecComp),
                new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));

        List<MaquinaUltrassomEspecificada> maquinaUltraEspecLocal = conMysql.query(String.format("""
                        select
                            m.*
                        from 
                            maquina_ultrassom_especificada m
                        join 
                            especificacao_componente e
                        on
                            m.fk_especificacao_componente = e.id_especificacao_componente
                        where 
                         fk_maquina = %d
                        and fk_especificacao_componente = %d                
                        and e.tipo_componente = 'REDE';
                    """, fkMaquina, fkEspecComp),
                new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));

        if (maquinaUltraEspec.isEmpty()) {
            con.execute(String.format("""
                        insert into maquina_ultrassom_especificada 
                            (fk_maquina, fk_especificacao_componente)
                        values (%d, %d);
                      """, fkMaquina, fkEspecComp));

            maquinaUltraEspec
                    = con.query(String.format("""
                        select
                            m.*
                        from 
                            maquina_ultrassom_especificada m
                        join 
                            especificacao_componente e
                        on
                            m.fk_especificacao_componente = e.id_especificacao_componente
                        where
                        fk_maquina = %d
                        and fk_especificacao_componente = %d                
                        and e.tipo_componente = 'REDE';
                    """,fkMaquina, fkEspecComp),
                    new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));
        }

        if (maquinaUltraEspecLocal.isEmpty()) {
            MaquinaUltrassomEspecificada dados = maquinaUltraEspec.get(0);

            conMysql.execute(String.format("""
                        insert into maquina_ultrassom_especificada 
                            (id_especificacao_componente_maquina, fk_maquina, fk_especificacao_componente)
                        values (%d, %d, %d);
                      """,dados.getId_especificacao_componente_maquina(), fkMaquina, fkEspecComp));
        }

        MaquinaUltrassomEspecificada dados = maquinaUltraEspec.get(0);

        System.out.println("ESPEFIFICAÇÃO DE COMPONENTES CADASTRADOS COM SUCESSO!");
        return new MaquinaUltrassomEspecificada(dados.getId_especificacao_componente_maquina(),
                dados.getUso_maximo(),
                dados.getFk_maquina(),
                dados.getFk_especificacao_componente());
    }
}
