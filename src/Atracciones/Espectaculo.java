package Atracciones;

import java.util.Date;

public class Espectaculo {
    private Date fecha;
    private Date horaInicio;
    private Date horaFinalizacion;
    private String empresaEncargada;
    private String nombre;

    public Espectaculo(Date fecha, Date horaInicio, Date horaFinalizacion, String empresaEncargada, String nombre) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFinalizacion = horaFinalizacion;
        this.empresaEncargada = empresaEncargada;
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFinalizacion() {
        return horaFinalizacion;
    }

    public void setHoraFinalizacion(Date horaFinalizacion) {
        this.horaFinalizacion = horaFinalizacion;
    }

    public String getEmpresaEncargada() {
        return empresaEncargada;
    }

    public void setEmpresaEncargada(String empresaEncargada) {
        this.empresaEncargada = empresaEncargada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
