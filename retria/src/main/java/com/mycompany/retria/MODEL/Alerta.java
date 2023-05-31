package com.mycompany.retria.MODEL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Alerta {

    private Integer idAlerta;

    private final LocalDateTime dtMetrica = LocalDateTime.now();
    private Integer tipoAlerta;

    private Integer fkMetricaComponente;

    public Alerta(Integer idAlerta, Integer tipoAlerta, Integer fkMetricaComponente) {
        this.idAlerta = idAlerta;
        this.tipoAlerta = tipoAlerta;
        this.fkMetricaComponente = fkMetricaComponente;
    }

    public Alerta() {
    }

    public String getDateFormatedSql () {
        return dtMetrica.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public String getDateFormatedForLog () {
        return dtMetrica.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public Integer getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(Integer tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public Integer getFkMetricaComponente() {
        return fkMetricaComponente;
    }

    public void setFkMetricaComponente(Integer fkMetricaComponente) {
        this.fkMetricaComponente = fkMetricaComponente;
    }

    @Override
    public String toString() {
        return String.format("""
                
                Alertas
                
                ID : %d
                
                DATA : %s
                
                TIPO : %s
                
                """, this.idAlerta, this.tipoAlerta);
    }
}
