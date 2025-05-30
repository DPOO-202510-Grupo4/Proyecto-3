package Tiquetes;

import java.util.Date;

public class FastPass extends ItemVenta{
	private Date fecha;
	private boolean usado;
	private String idTiquete;
	private String dueño;

	public FastPass(String nombre, Double precioBase, Date fecha, boolean usado, String idTiquete, String dueño) {
		super(nombre, precioBase);
		this.fecha = fecha;
		this.usado = usado;
		this.idTiquete = idTiquete;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isUsado() {
		return usado;
	}

	public void setUsado(boolean usado) {
		this.usado = usado;
	}

	public String getIdTiquete() {
		return idTiquete;
	}

	public void setIdTiquete(String idTiquete) {
		this.idTiquete = idTiquete;
	}

	@Override
	public double calcularPrecio() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getDueño() {
		return dueño;
	}








	
}
