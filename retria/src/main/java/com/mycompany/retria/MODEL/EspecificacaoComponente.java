/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.retria.MODEL;

/**
 *
 * @author lucka
 * 
 */
public class EspecificacaoComponente {
    
    private Integer id_especificacao_componente;
    private TipoComponente tipoComponente;
    private String nome_fabricante;
    private String descricao_componente;

    private String numero_serial;

    public EspecificacaoComponente(Integer id_especificacao_componente, TipoComponente tipo, String nome_fabricante, String descricao_componente, String numero_serial) {
        this.id_especificacao_componente = id_especificacao_componente;
        this.tipoComponente = tipo;
        this.nome_fabricante = nome_fabricante;
        this.descricao_componente = descricao_componente;
        this.numero_serial = numero_serial;
    }

    public EspecificacaoComponente() {
    }

    public Integer getId_especificacao_componente() {
        return id_especificacao_componente;
    }

    public void setId_especificacao_componente(Integer id_especificacao_componente) {
        this.id_especificacao_componente = id_especificacao_componente;
    }

    public TipoComponente getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(TipoComponente tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public String getNome_fabricante() {
        return nome_fabricante;
    }

    public void setNome_fabricante(String nome_fabricante) {
        this.nome_fabricante = nome_fabricante;
    }

    public String getDescricao_componente() {
        return descricao_componente;
    }

    public String getNumero_serial() {
        return numero_serial;
    }

    public void setNumero_serial(String numero_serial) {
        this.numero_serial = numero_serial;
    }

    public void setDescricao_componente(String descricao_componente) {
        this.descricao_componente = descricao_componente;
    }

    @Override
    public String toString() {
       return String.format("""
               %s
               %s
               %s
               %s

               """,tipoComponente,descricao_componente,numero_serial,nome_fabricante);
    }


}
