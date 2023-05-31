/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.retria.DAO;

import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.mycompany.retria.MODEL.EspecificacaoComponente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.retria.services.Service;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author lucka
 */
public class EspecificacaoComponenteDAO {

    JdbcTemplate con;
    JdbcTemplate conMysql;
    Service service = new Service();

    private List<EspecificacaoComponente> componentesCadastrados;


    public EspecificacaoComponenteDAO() {
        Conexao conexao = new Conexao();
        con = conexao.getConnection();
        ConexaoMySqlEc2 conMysqlEc2 = new ConexaoMySqlEc2();
        conMysql = conMysqlEc2.getConnection();
        componentesCadastrados = new ArrayList<>();
    }

    public EspecificacaoComponente getComponenteCpu(Processador processador) {

        List<EspecificacaoComponente> especificacaoComponentes =
                con.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        and
                            numero_serial = '%s'
                        """, processador.getNome(),processador.getId()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        List<EspecificacaoComponente> especificacaoComponentesLocal =
                conMysql.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        and
                            numero_serial = '%s'
                        """, processador.getNome(),processador.getId()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        if (especificacaoComponentes.isEmpty()) {
            con.execute(String.format("insert into especificacao_componente" +
                            "(tipo_componente, descricao_componente, nome_fabricante, numero_serial) values ('%s', '%s', '%s', '%s')",
                  "CPU",processador.getNome(), processador.getFabricante(), processador.getId()));

            especificacaoComponentes =
                    con.query(String.format("""
                            select
                                *
                            from
                                especificacao_componente
                            where
                                descricao_componente = '%s'
                            and
                                numero_serial = '%s'
                            """, processador.getNome(),processador.getId()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));
        }

        if (especificacaoComponentesLocal.isEmpty()) {
            EspecificacaoComponente dados = especificacaoComponentes.get(0);

            conMysql.execute(String.format("insert into especificacao_componente" +
                            "(id_especificacao_componente,tipo_componente, descricao_componente, nome_fabricante, numero_serial) " +
                            "values (%d,'%s', '%s', '%s', '%s')",
                    dados.getId_especificacao_componente(),"CPU", processador.getNome(), processador.getFabricante(), processador.getId()));
        }

        EspecificacaoComponente dados = especificacaoComponentes.get(0);

        return new EspecificacaoComponente(dados.getId_especificacao_componente(),
                dados.getTipoComponente(), dados.getNome_fabricante(), dados.getDescricao_componente(),dados.getNumero_serial());
    }

    public EspecificacaoComponente getComponenteMemoria(Memoria memoria) {
        String nomeMemoria = String.format("Pente de memória ram - %.0f GB", service.convertBytesToGB(memoria.getTotal()));

        List<EspecificacaoComponente> especificacaoComponentes =
                con.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        """, nomeMemoria), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        List<EspecificacaoComponente> especificacaoComponentesLocal =
                conMysql.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        """, nomeMemoria), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        if (especificacaoComponentes.isEmpty()) {
            con.execute(String.format("insert into especificacao_componente" +
                            "(tipo_componente, descricao_componente) values ('%s','%s')",
                    "RAM",nomeMemoria));

            especificacaoComponentes =
                    con.query(String.format("""
                            select
                                *
                            from
                                especificacao_componente
                            where
                                descricao_componente = '%s'
                            """, nomeMemoria), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        }

        if (especificacaoComponentesLocal.size() == 0) {
            EspecificacaoComponente dados = especificacaoComponentes.get(0);

            conMysql.execute(String.format("insert into especificacao_componente" +
                            "(id_especificacao_componente,tipo_componente, descricao_componente) values (%d,'%s','%s')",
                    dados.getId_especificacao_componente(),"RAM",nomeMemoria));
        }

        EspecificacaoComponente dados = especificacaoComponentes.get(0);
        return new EspecificacaoComponente(dados.getId_especificacao_componente(),
                dados.getTipoComponente(), dados.getNome_fabricante(), dados.getDescricao_componente(),dados.getNumero_serial());
    }

    public EspecificacaoComponente getComponenteDisco(Volume disco) {
        String nomeDisco = String.format("HD/SSD - %.0f GB", service.convertBytesToGB(disco.getTotal()));
        List<EspecificacaoComponente> especificacaoComponentes =
                con.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        and
                            numero_serial = '%s'
                        """, nomeDisco,disco.getUUID()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        List<EspecificacaoComponente> especificacaoComponentesLocal =
                conMysql.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        and
                            numero_serial = '%s'
                        """, nomeDisco,disco.getUUID()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        if (especificacaoComponentes.isEmpty()) {
            con.execute(String.format("insert into especificacao_componente" +
                            "(tipo_componente,descricao_componente, numero_serial) values ('%s', '%s','%s')",
                    "DISCO",nomeDisco, disco.getUUID()));

            especificacaoComponentes =
                    con.query(String.format("""
                            select
                                *
                            from
                                especificacao_componente
                            where
                                descricao_componente = '%s'
                            and
                                numero_serial = '%s'
                            """, nomeDisco,disco.getUUID()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        }

        if (especificacaoComponentesLocal.isEmpty()) {
            EspecificacaoComponente dados = especificacaoComponentes.get(0);

            conMysql.execute(String.format("insert into especificacao_componente" +
                            "(id_especificacao_componente,tipo_componente,descricao_componente, numero_serial) values (%d,'%s', '%s','%s')",
                    dados.getId_especificacao_componente(),"DISCO",nomeDisco, disco.getUUID()));
        }

        EspecificacaoComponente dados = especificacaoComponentes.get(0);

        EspecificacaoComponente componenteACadastrar = new EspecificacaoComponente(dados.getId_especificacao_componente(),
                dados.getTipoComponente(), dados.getNome_fabricante(), dados.getDescricao_componente(), dados.getNumero_serial());

        if (componentesCadastrados.size() == 0) {
            componentesCadastrados.add(componenteACadastrar);
            return componenteACadastrar;
        } else {
            for (EspecificacaoComponente e : componentesCadastrados){
                if (e.getNumero_serial().equalsIgnoreCase(componenteACadastrar.getNumero_serial())) {
                    return null;
                }
            }
        }
        return componenteACadastrar;
    }

    public EspecificacaoComponente getRede(List<RedeInterface> interfaces) {
            RedeInterface redeAtual = interfaces.get(0);
        for (int i = 0; i < interfaces.size(); i++) {
            RedeInterface redeUsada = interfaces.get(i);

            if (redeUsada.getBytesRecebidos() > redeAtual.getBytesRecebidos()) {
                redeAtual = redeUsada;
            }
        }

        List<EspecificacaoComponente> especificacaoComponentes =
                con.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        and
                            numero_serial = '%s'
                        """, redeAtual.getNomeExibicao(),redeAtual.getEnderecoMac()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        List<EspecificacaoComponente> especificacaoComponentesLocal =
                conMysql.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        and
                            numero_serial = '%s'
                        """, redeAtual.getNomeExibicao(),redeAtual.getEnderecoMac()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        if (especificacaoComponentes.isEmpty()) {
            con.execute(String.format("insert into especificacao_componente" +
                            "(tipo_componente,descricao_componente, numero_serial) values ('%s', '%s','%s')",
                    "REDE",redeAtual.getNomeExibicao(), redeAtual.getEnderecoMac()));

            especificacaoComponentes =
                    con.query(String.format("""
                            select
                                *
                            from
                                especificacao_componente
                            where
                                descricao_componente = '%s'
                            and
                                numero_serial = '%s'
                            """, redeAtual.getNomeExibicao(),redeAtual.getEnderecoMac()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        }

        if (especificacaoComponentesLocal.isEmpty()) {
            EspecificacaoComponente dados = especificacaoComponentes.get(0);

            conMysql.execute(String.format("insert into especificacao_componente" +
                            "(id_especificacao_componente,tipo_componente,descricao_componente, numero_serial) values (%d,'%s', '%s','%s')",
                    dados.getId_especificacao_componente(),"REDE",redeAtual.getNomeExibicao(),redeAtual.getEnderecoMac()));
        }

        EspecificacaoComponente dados = especificacaoComponentes.get(0);
        return new EspecificacaoComponente(dados.getId_especificacao_componente(),
                dados.getTipoComponente(), dados.getNome_fabricante(), dados.getDescricao_componente(),dados.getNumero_serial());
    }
}