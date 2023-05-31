package com.mycompany.retria.MODEL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MetricaComponente {

    private Integer idMetricaComponente;

    private final LocalDateTime dtMetrica = LocalDateTime.now();

    private Double uso;

    private Integer fkEspecificacaoComponente;

    public MetricaComponente(Integer idMetricaComponente, Double uso, Integer fkEspecificacaoComponente) {
        this.idMetricaComponente = idMetricaComponente;
        this.uso = uso;
        this.fkEspecificacaoComponente = fkEspecificacaoComponente;
    }

    public MetricaComponente() {
    }

    public String getDateFormatedSql () {
        return dtMetrica.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getDateFormatedForLog () {
        return dtMetrica.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Integer getIdMetricaComponente() {
        return idMetricaComponente;
    }

    public void setIdMetricaComponente(Integer idMetricaComponente) {
        this.idMetricaComponente = idMetricaComponente;
    }

    public Double getUso() {
        return uso;
    }

    public void setUso(Double uso) {
        this.uso = uso;
    }


    public Integer getFkEspecificacaoComponente() {
        return fkEspecificacaoComponente;
    }

    public void setFkEspecificacaoComponente(Integer fkEspecificacaoComponente) {
        this.fkEspecificacaoComponente = fkEspecificacaoComponente;
    }

    @Override
    public String toString() {
        return String.format("""
                
                METRICA DO COMPONENTE
                
                ID : %d
                
                Data : %s
                
                Uso : %.2f
                
                Frequência : %.2f
                
                """, this.idMetricaComponente,
                this.dtMetrica, this.uso);
    }
}
