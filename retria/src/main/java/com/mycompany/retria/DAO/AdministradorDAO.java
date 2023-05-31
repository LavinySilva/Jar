/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.retria.DAO;

import com.mycompany.retria.MODEL.Administrador;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author lucka
 */
public class AdministradorDAO {

    JdbcTemplate con;
    JdbcTemplate conMysql;
    EmpresaDAO empresaDAO = new EmpresaDAO();
    public AdministradorDAO() {
        Conexao conexao = new Conexao();
        con = conexao.getConnection();
        ConexaoMySqlEc2 conexaoMysql = new ConexaoMySqlEc2();
        conMysql = conexaoMysql.getConnection();
    }

    public Boolean consultar(String email, String senha) {

        List<Administrador> administradores = con.query(String.format("""
                        SELECT 
                            *
                        FROM 
                            administrador
                        WHERE
                            email_administrador = '%s'
                        AND senha_administrador = '%s';
                        """, email, senha),
                new BeanPropertyRowMapper(Administrador.class));

        if (!administradores.isEmpty()) {
            return true;
        }

        System.out.println("Email e/ou senha invalidos");
        return false;
    }

    public Administrador setAdministrador(String email, String senha) {

        List<Administrador> administradores = con.query(String.format("""
                        SELECT 
                            *
                        FROM 
                            administrador
                        WHERE
                            email_administrador = '%s'
                        AND senha_administrador = '%s';
                        """, email, senha),
                new BeanPropertyRowMapper(Administrador.class));

        List<Administrador> administradoresLocal = conMysql.query(String.format("""
                        SELECT 
                            *
                        FROM 
                            administrador
                        WHERE
                            email_administrador = '%s'
                        AND senha_administrador = '%s';
                        """, email, senha),
                new BeanPropertyRowMapper(Administrador.class));

        if (administradoresLocal.isEmpty()) {
            
            Administrador dados = administradores.get(0);
            empresaDAO.setEmpresaBDLocal(dados.getFk_empresa());
            conMysql.execute(String.format("insert into administrador values"
                    + " (%d, '%s', '%s', '%s', '%s', '%s', %d, %d)",
                    dados.getId_administrador(), dados.getNome_administrador(),
                    dados.getEmail_administrador(), dados.getSenha_administrador(),
                    dados.getTelefone_administrador(), dados.getChave_seguranca_administrador(),
                    dados.getFk_ocupacao(), dados.getFk_empresa()));

        }
        Administrador dados = administradores.get(0);
        return new Administrador(dados.getId_administrador(), dados.getNome_administrador(),
                dados.getEmail_administrador(),dados.getSenha_administrador()
                ,dados.getTelefone_administrador(), dados.getChave_seguranca_administrador()
                , dados.getFk_ocupacao(), dados.getFk_empresa());
    }
}
