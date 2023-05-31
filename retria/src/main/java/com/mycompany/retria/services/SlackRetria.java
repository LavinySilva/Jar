/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.retria.services;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.mycompany.retria.DAO.*;
import com.mycompany.retria.MODEL.MetricaComponente;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Iury
 */
public class SlackRetria {

    private static final String webHooksUrl = new WebhookDAO().getLink();
    private static final String slackChannel = "hospital-estadual-de-vila-alpina-";
    private LocalDateTime teste = LocalDateTime.now();

    public void sendMensagemToSlack(String mensagem) {
        System.out.println(webHooksUrl);
        MetricaComponenteDAO m = new MetricaComponenteDAO();
        LocalDateTime teste2 = LocalDateTime.now();

        if (ChronoUnit.MINUTES.between(teste, teste2) >= 1) {
            try {
                System.out.println("ESTOU NA CLASSE DO SLACK");
                StringBuilder msgbuilde = new StringBuilder();
                msgbuilde.append(mensagem);
                Payload payload = Payload.builder().channel(slackChannel).text(msgbuilde.toString()).build();
                com.github.seratch.jslack.api.webhook.WebhookResponse wbResp = Slack.getInstance().send(webHooksUrl, payload);
                System.out.println("#####ALERTA ENVIADO PARA O SLACK#####");
                teste = LocalDateTime.now();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("#####AGUARDE 1 MINUTO PARA O PROXIMO ALERTA#####");
        }
    }

}
