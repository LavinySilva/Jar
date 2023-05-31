/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.retria.DAO;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author lucka
 */
public class ConexaoMySqlEc2 {

    private final JdbcTemplate connection;

    public ConexaoMySqlEc2() {

        /**
         * jdbc:sqlserver://umms-retria.database.windows.net:1433;database=BD-UMMS;user=adm@umms-retria;password=#Gfgrupo3;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;
         *
         */
        BasicDataSource dataSource = new BasicDataSource();
        // setDriverClassName recebe o drive para conexão do banco // Mysql = com.mysql.cj.jdbc.Driver
//        dataSource​.setDriverClassName("com.mysql.cj.jdbc.Driver");

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

//         setUrl cria um arquivo de com o nome do banco que vc especificar no argumento // Mysql = jdbc:mysql://localhost:3306/nomeBanco
        dataSource.setUrl("jdbc:mysql://localhost:3306/retria");
        //dataSource.setUrl("jdbc:mysql://0.0.0.0:3306/retria?autoReconnect=true&useSSL=false");

        // setUsername recebe o nome do usuario como argumento // root
        dataSource.setUsername("root");

//        dataSource​.setUsername("root");
        // setPassword recebe a senha do usuario como argumento
        dataSource.setPassword("urubu100");
//
//        dataSource​.setPassword("40028922");

        this.connection = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConnection() {
        return connection;
    }

}
