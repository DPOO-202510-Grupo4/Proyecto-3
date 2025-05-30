package Tiquetes;
import java.util.ArrayList;

import Atracciones.*;
public class CategoriaTiquete {
	private String nombre;
	private ArrayList<String> atraccionesDisponibles;
	private Double precioBase;
	public CategoriaTiquete(String nombre, ArrayList<String> atraccionesDisponibles, Double precioBase) {
	    this.nombre = nombre;
	    this.atraccionesDisponibles = atraccionesDisponibles;
	    this.precioBase = precioBase;
	}

	public Double getPrecioBase() {
	    return precioBase;
	}

	public String getNombre() {
		return nombre;
	}

	public ArrayList<String> getAtraccionesDisponibles() {
		return atraccionesDisponibles;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setAtraccionesDisponibles(ArrayList<String> atraccionesDisponibles) {
		this.atraccionesDisponibles = atraccionesDisponibles;
	}

	public void setPrecioBase(Double precioBase) {
		this.precioBase = precioBase;
	}


	
	

}
