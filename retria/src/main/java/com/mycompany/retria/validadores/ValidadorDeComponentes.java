package com.mycompany.retria.validadores;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.mycompany.retria.DAO.AlertaDAO;
import com.mycompany.retria.DAO.MetricaComponenteDAO;
import com.mycompany.retria.MODEL.Alerta;
import com.mycompany.retria.MODEL.MetricaComponente;
import com.mycompany.retria.exception.ValidacaoException;
import com.mycompany.retria.services.Service;
import com.mycompany.retria.services.SlackRetria;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ValidadorDeComponentes {

    private AlertaDAO alertaDAO = new AlertaDAO();

    private MetricaComponenteDAO metricaComponenteDAO = new MetricaComponenteDAO();

    private Service service = new Service();

    private MetricaComponente metricaComponente;

    private SlackRetria slack = new SlackRetria();

    private Alerta alerta;

    private Looca looca = new Looca();

    public void validarCpu(Processador dados, Integer fkMaquinaUltrassom) throws ValidacaoException {
        Double usoProcessador = dados.getUso();
        metricaComponente = new MetricaComponente(null, usoProcessador, fkMaquinaUltrassom);
        Integer fkMetricaComponente = metricaComponenteDAO.setMetrica(metricaComponente);
        String frase = "";

        if (dados == null) {
            throw new ValidacaoException("Não é possível validar o uso de cpu nulo!!!");
        }

        if (usoProcessador < 35.0) {
            System.out.println("Uso dentro dos conformes!");
        } else if (usoProcessador < 40.0) {
            alertaDAO.setAlerta(new Alerta(null, 1, fkMetricaComponente));
            frase = "CPU está com nível de uso em - ALERTA!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        } else if (usoProcessador < 45.0) {
            alertaDAO.setAlerta(new Alerta(null, 2, fkMetricaComponente));
            frase = "CPU está com nível de uso em - PERIGOSO! Contate o suporte!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        } else {
            alertaDAO.setAlerta(new Alerta(null, 3, fkMetricaComponente));
            frase = "CPU está com nível de uso em - CRÍTICO! Contate o suporte IMEDIATAMENTE!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        }
    }

    public void validarRam(Memoria dados, Integer fkMaquinaUltrassom) throws ValidacaoException {
        Double memRamTotal = service.convertBytesToGB(dados.getTotal());
        Double usoMemoria = service.convertBytesToGB(dados.getEmUso());
        Double porcentagemDeRam = (usoMemoria * 100) / memRamTotal;

        System.out.println("USO DE RAM " + usoMemoria);
        metricaComponente = new MetricaComponente(null, porcentagemDeRam, fkMaquinaUltrassom);
        Integer fkMetricaComponente = metricaComponenteDAO.setMetrica(metricaComponente);
        String frase = "";
        System.out.println("A FK É " + fkMetricaComponente);

        if (dados == null) {
            throw new ValidacaoException("Não é possível validar memória nula!!!");
        }
        if (porcentagemDeRam < 49.0) {
            System.out.println("Uso de ram dentro dos conformes!");
        } else if (porcentagemDeRam < 56.0) {
            alertaDAO.setAlerta(new Alerta(null, 1, fkMetricaComponente));
            frase = "RAM está com nível de uso em - ALERTA!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        } else if (porcentagemDeRam < 63.0) {
            alertaDAO.setAlerta(new Alerta(null, 2, fkMetricaComponente));
            frase = "RAM está com nível de uso em - PERIGOSO! Contate o suporte!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        } else {
            alertaDAO.setAlerta(new Alerta(null, 3, fkMetricaComponente));
            frase = "RAM está com nível de uso em - CRÍTICO! Contate o suporte IMEDIATAMENTE!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        }
    }

    public void validarDisco(Volume dados, Integer fkMaquinaUltrassom) throws ValidacaoException {
        Double emUso = service.convertBytesToGB(dados.getTotal() - dados.getDisponivel());
        Double porcentagemDeUsoDisc = (emUso * 100) / service.convertBytesToGB(dados.getTotal());
        System.out.println("---------------------------- " + porcentagemDeUsoDisc);
        metricaComponente = new MetricaComponente(null, porcentagemDeUsoDisc, fkMaquinaUltrassom);
        Integer fkMetricaComponente = metricaComponenteDAO.setMetrica(metricaComponente);
        String frase = "";

        if (dados == null) {
            throw new ValidacaoException("Não é possível validar discos de uma lista vazia!!!");
        }

        if (porcentagemDeUsoDisc < 56.0) {
            System.out.println("Uso de DISCO dentro dos conformes!");
        } else if (porcentagemDeUsoDisc < 64.0) {
            alertaDAO.setAlerta(new Alerta(null, 1, fkMetricaComponente));
            frase = "DISCO está com nível de uso em - ALERTA!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        } else if (porcentagemDeUsoDisc < 72.0) {
            alertaDAO.setAlerta(new Alerta(null, 2, fkMetricaComponente));
            frase = "DISCO está com nível de uso em - PERIGOSO! Contate o suporte!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        } else {
            alertaDAO.setAlerta(new Alerta(null, 3, fkMetricaComponente));
            frase = "DISCO está com nível de uso em - CRÍTICO! Contate o suporte IMEDIATAMENTE!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        }
    }

    public void validarRede(Integer fkRede) {
        try {

            List<RedeInterface> interfaces = looca.getRede().getGrupoDeInterfaces().getInterfaces();
            RedeInterface redeAtual = interfaces.get(0);

            for (int i = 0; i < interfaces.size(); i++) {
                RedeInterface redeUsada = interfaces.get(i);

                if (redeUsada.getBytesRecebidos() > redeAtual.getBytesRecebidos()) {
                    redeAtual = redeUsada;
                }
            }

            long bytesRec1 = redeAtual.getBytesRecebidos();
            TimeUnit.SECONDS.sleep(3);
            long bytesRec2 = redeAtual.getBytesRecebidos();
            Double mbpsAtual = service.convertBytesToMB(bytesRec2 - bytesRec1);
            System.out.println(mbpsAtual);
            metricaComponente = new MetricaComponente(null, mbpsAtual, fkRede);
            Integer fkMetricaComponente = metricaComponenteDAO.setMetrica(metricaComponente);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
