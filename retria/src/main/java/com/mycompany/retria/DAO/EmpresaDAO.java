/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.retria.DAO;

import com.mycompany.retria.MODEL.Administrador;
import com.mycompany.retria.MODEL.Empresa;
import java.util.List;
import java.util.Scanner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author lucka
 */
public class EmpresaDAO {

    JdbcTemplate con;
    JdbcTemplate conMysql;

    public EmpresaDAO() {
        Conexao conexao = new Conexao();
        con = conexao.getConnection();
        ConexaoMySqlEc2 conexaoMysql = new ConexaoMySqlEc2();
        conMysql = conexaoMysql.getConnection();
    }

    public void setEmpresaBDLocal(Integer fkEmpresa) {
        List<Empresa> empresas = con.query(
                String.format("""
                              SELECT
                                *
                              FROM
                                empresa
                              WHERE 
                                id_empresa = %d;
                              """, fkEmpresa),
                new BeanPropertyRowMapper(Empresa.class));

        List<Empresa> empresasLocal = conMysql.query(
                String.format("""
                              SELECT
                                *
                              FROM
                                empresa
                              WHERE 
                                id_empresa = %d;
                              """, fkEmpresa),
                new BeanPropertyRowMapper(Empresa.class));

        if (empresasLocal.isEmpty()) {
            Empresa dados = empresas.get(0);
            conMysql.execute(String.format("""
                            INSERT INTO empresa values
                            (%d, '%s', '%s', '%s', '%s', '%s', '%s', %d);
                            """,
                    dados.getId_empresa(),
                    dados.getNome_empresa(),
                    dados.getPrcnpj(),
                    dados.getTelefone_01(),
                    dados.getTelefone_02(),
                    dados.getEmail(),
                    dados.getResponsavel_empresa(),
                        dados.getFkMatriz())
            );
        }
    }
}
