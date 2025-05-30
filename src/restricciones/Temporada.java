package restricciones;

import java.util.Date;

public class Temporada {
	private Date fechaInicio;
	private Date fechaFinal;
	private String name;
	public Temporada(Date fechaInicio, Date fechaFinal, String name) {
		
		this.fechaInicio = fechaInicio;
		this.fechaFinal = fechaFinal;
		this.name = name;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean estaDentroDeTemporada(Date fecha) {
	    return !fecha.before(fechaInicio) && !fecha.after(fechaFinal);
	}
 
}
