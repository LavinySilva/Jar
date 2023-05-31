/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.retria.MODEL;

/**
 *
 * @author lucka
 */
public class Empresa {
   
    private Integer id_empresa; 
    private String nome_empresa;
    private String prcnpj;
    private String telefone_01;
    private String telefone_02;
    private String email;
    private String responsavel_empresa;
    private Integer fkMatriz;

    public Integer getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(Integer id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNome_empresa() {
        return nome_empresa;
    }

    public void setNome_empresa(String nome_empresa) {
        this.nome_empresa = nome_empresa;
    }

    public String getPrcnpj() {
        return prcnpj;
    }

    public void setPrcnpj(String prcnpj) {
        this.prcnpj = prcnpj;
    }

    public String getTelefone_01() {
        return telefone_01;
    }

    public void setTelefone_01(String telefone_01) {
        this.telefone_01 = telefone_01;
    }

    public String getTelefone_02() {
        return telefone_02;
    }

    public void setTelefone_02(String telefone_02) {
        this.telefone_02 = telefone_02;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFkMatriz() {
        return fkMatriz;
    }

    public void setFkMatriz(Integer fkMatriz) {
        this.fkMatriz = fkMatriz;
    }
    
    
    
    public String getResponsavel_empresa() {
        return responsavel_empresa;
    }

    public void setResponsavel_empresa(String responsavel_empresa) {
        this.responsavel_empresa = responsavel_empresa;
    }

    @Override
    public String toString() {
        return "Empresa{" + "id_empresa=" + id_empresa + ", nome_empresa=" + nome_empresa + ", prcnpj=" + prcnpj + ", telefone_01=" + telefone_01 + ", telefone_02=" + telefone_02 + ", email=" + email + ", responsavel_empresa=" + responsavel_empresa + ", fkMatriz=" + fkMatriz + '}';
    }

    
}
