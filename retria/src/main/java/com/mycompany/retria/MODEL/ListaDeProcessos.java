/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.retria.MODEL;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processos.Processo;
import com.github.britooo.looca.api.group.processos.ProcessoGrupo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author lucka
 */
public class ListaDeProcessos {

    public void listarProcessos() {
        Looca looca = new Looca();

        ProcessoGrupo grupoDeProcessos = looca.getGrupoDeProcessos();
        List<Processo> processos = grupoDeProcessos.getProcessos();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy ");
        LocalDateTime dataHora = LocalDateTime.now();

        System.out.println("LISTAGEM DE PROCESSOS: ");
        System.out.println("Data e Hora: " + dataHora.format(formatter));

        for (int i = 0; i <= 5; i++) {
            System.out.println(String.format(
                    """
                        Processo: %s; PID: %d
                           Percentual de uso da CPU: %.3f%%
                           Percentual de usa da RAM: %.3f%%
                           MBs alocados na RAM: %dMB 
                            new RetriaLogin().dispose();
                    \n
                        """,
                    processos.get(i).getNome(), processos.get(i).getPid(),
                    processos.get(i).getUsoCpu(), // Retorna o valor percentual de uso da CPU pelo processo.
                    processos.get(i).getUsoMemoria(), //Retorna o Valor percentual de uso da Memória RAM pelo processo.
                    (processos.get(i).getBytesUtilizados() / 1000000) // Retorna quanta memória está alocada para esse processo e está na RAM em MB
            ));
        }
    }
}
