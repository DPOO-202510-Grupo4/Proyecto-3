package Persona;

import Atracciones.*;
import java.util.ArrayList;

public class Capacitaciones {
	private Boolean esCocinero;
	private Boolean esCajero;
	private ArrayList<Atraccion> capacitacionAtracciones;
	
	public Capacitaciones(Boolean esCocinero, Boolean esCajero, ArrayList<Atraccion> capacitacionAtracciones) {
		super();
		this.esCocinero = esCocinero;
		this.esCajero = esCajero;
		this.capacitacionAtracciones = capacitacionAtracciones;
	}

	public Boolean getEsCocinero() {
		return esCocinero;
	}

	public void setEsCocinero(Boolean esCocinero) {
		this.esCocinero = esCocinero;
	}

	public Boolean getEsCajero() {
		return esCajero;
	}

	public void setEsCajero(Boolean esCajero) {
		this.esCajero = esCajero;
	}

	public ArrayList<Atraccion> getCapacitacionAtracciones() {
		return capacitacionAtracciones;
	}

	public void setCapacitacionAtracciones(ArrayList<Atraccion> capacitacionAtracciones) {
		this.capacitacionAtracciones = capacitacionAtracciones;
	} 
	

}
