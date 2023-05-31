package com.mycompany.retria.MODEL;

public class Webhook {

    private Integer id_web;
    private String link;

    public Webhook(Integer id_web, String link) {
        this.id_web = id_web;
        this.link = link;
    }

    public Webhook() {
    }

    public Integer getId_web() {
        return id_web;
    }

    public void setId_web(Integer id_web) {
        this.id_web = id_web;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Webhook{" +
                "id_web=" + id_web +
                ", link='" + link + '\'' +
                '}';
    }
}
