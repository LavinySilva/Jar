package com.mycompany.retria.DAO;

import com.mycompany.retria.MODEL.MaquinaUltrassom;
import com.mycompany.retria.MODEL.MetricaComponente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MetricaComponenteDAO {
    JdbcTemplate con;
    JdbcTemplate conMysql;

    public MetricaComponenteDAO() {
        Conexao conexao = new Conexao();
        con = conexao.getConnection();
        ConexaoMySqlEc2 conMysqlEc2 = new ConexaoMySqlEc2();
        conMysql = conMysqlEc2.getConnection();

    }

    public Integer setMetrica(MetricaComponente metrica) {
        String dataAtual = metrica.getDateFormatedSql();
            con.update("insert into metrica_componente (dt_metrica,uso,fk_especificacao_componente_maquina) values (?,?,?)",dataAtual,metrica.getUso(),metrica.getFkEspecificacaoComponente());

        List<MetricaComponente> metricas = con.query(String.format("""
                select 
                    id_metrica_componente 
                from
                    metrica_componente
                where
                     dt_metrica = '%s'
                and 
                    fk_especificacao_componente_maquina = %d
                """, dataAtual,metrica.getFkEspecificacaoComponente()),
                new BeanPropertyRowMapper(MetricaComponente.class));

        MetricaComponente dados = metricas.get(0);

        conMysql.update("insert into metrica_componente (id_metrica_componente,dt_metrica,uso,fk_especificacao_componente_maquina) values (?,?,?,?)",dados.getIdMetricaComponente(),dataAtual,metrica.getUso(),metrica.getFkEspecificacaoComponente());


        return dados.getIdMetricaComponente();
    }

}
