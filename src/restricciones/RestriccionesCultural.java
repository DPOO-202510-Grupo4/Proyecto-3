package restricciones;

import java.util.ArrayList;

import Atracciones.Atraccion;
import Tiquetes.CategoriaTiquete;

public class RestriccionesCultural extends Restricciones {
	private int edad;

	public RestriccionesCultural(ArrayList<String> clima, String exclusividad, int edad) {
		super(clima, exclusividad);
		this.edad = edad;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public boolean permiteTiquete(CategoriaTiquete categoria, Atraccion atraccion) {
	    return categoria.getAtraccionesDisponibles().contains(atraccion);
	}


	

}
