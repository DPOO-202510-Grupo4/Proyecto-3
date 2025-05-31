package Tiquetes;

import Persona.Cliente;

public abstract class Tiquete extends ItemVenta{
	protected String idTiquete;
	protected CategoriaTiquete categoria;
	protected boolean usado;
	private boolean impreso = false;


	private String dueño;
	
	public Tiquete(String nombre, Double precioBase, String idTiquete,
			CategoriaTiquete categoria, boolean usado, Cliente cliente) {
		super(nombre, precioBase);
		this.idTiquete = idTiquete;
		this.categoria = categoria;
		this.usado = usado;
		this.dueño = cliente.getLogin();
	}

	public String getIdTiquete() {
		return idTiquete;
	}

	public void setIdTiquete(String idTiquete) {
		this.idTiquete = idTiquete;
	}

	public CategoriaTiquete getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaTiquete categoria) {
		this.categoria = categoria;
	}

	public boolean isUsado1() {
		return usado;
	}

	public void setUsado(boolean usado) {
		this.usado = usado;
	}
	

	
	public String getId() {
		return idTiquete;
	}

	public boolean isUsado() {
		return usado;
	}

	public void marcarComoUsado() {
		this.usado = true;
	}

	public String getDueño() {
		return dueño;
	}

	public boolean isImpreso() {
    	return impreso;
	}

	public void setImpreso(boolean impreso) {
    	this.impreso = impreso;
	}

	
}
