package com.mycompany.retria.DAO;

import com.mycompany.retria.MODEL.Alerta;
import com.mycompany.retria.MODEL.MaquinaUltrassom;
import com.mycompany.retria.MODEL.MetricaComponente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AlertaDAO {

    JdbcTemplate con;
    JdbcTemplate conMysql;


    public AlertaDAO() {
        Conexao conexao = new Conexao();
        con = conexao.getConnection();
        ConexaoMySqlEc2 conMysqlEc2 = new ConexaoMySqlEc2();
        conMysql = conMysqlEc2.getConnection();
    }

    public void setAlerta(Alerta alerta) {
        con.execute(String.format("""
                insert into alerta (dt_alerta,fk_tipo_alerta,fk_metrica_componente) values ('%s',%d,%d)
                """, alerta.getDateFormatedSql(), alerta.getTipoAlerta(), alerta.getFkMetricaComponente()));
        conMysql.execute(String.format("""
                insert into alerta (dt_alerta,fk_tipo_alerta,fk_metrica_componente) values ('%s',%d,%d)
                """, alerta.getDateFormatedSql(), alerta.getTipoAlerta(), alerta.getFkMetricaComponente()));
    }


}