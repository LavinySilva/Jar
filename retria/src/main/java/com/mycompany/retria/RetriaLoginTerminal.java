/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.retria;

import com.github.britooo.looca.api.core.Looca;
import com.mycompany.retria.DAO.AdministradorDAO;
import com.mycompany.retria.DAO.Conexao;
import com.mycompany.retria.services.Service;
import java.util.Scanner;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author aluno
 */
public class RetriaLoginTerminal {

    public static void main(String[] args) {
        Conexao conexao = new Conexao();
        JdbcTemplate con = conexao.getConnection();

        AdministradorDAO admDAO = new AdministradorDAO();
        Looca looca = new Looca();
        Service service = new Service();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o email: ");
        String email = scanner.nextLine();

        System.out.println("Digite a senha: ");
        String senha = scanner.nextLine();

        if (admDAO.consultar(email, senha)) {
            System.out.println("Login válido");
            service.scriptDeValidacaoDeBanco(email, senha);
            try {
                service.validarMetrica();
            } catch (Exception ex) {
                ex.getMessage();
            }

        } else {
            System.out.println("Login inválido");
        }
    }
}
