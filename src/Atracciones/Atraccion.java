package Atracciones;

import java.util.ArrayList;
import java.util.Date;

import Persona.Empleado;
import restricciones.Temporada;

public abstract class Atraccion {

    protected String ubicacion;
    protected int cupoMax;
    protected int minEmpleados;
    protected String nombre;
    protected Temporada temporada;
    protected boolean deTemporada;
    protected boolean enFuncionamiento;


    public Atraccion(String nombre, String ubicacion, int cupoMax, int minEmpleados, boolean deTemporada, Temporada temporada) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.cupoMax = cupoMax;
        this.minEmpleados = minEmpleados;
        this.deTemporada = deTemporada;
        this.temporada = temporada;
        this.enFuncionamiento = true; 
    }



    public boolean esDisponible(Date fecha) {
        return true;
    }

    public int getCupoMax() {
		return cupoMax;
	}



	public void setCupoMax(int cupoMax) {
		this.cupoMax = cupoMax;
	}



	public int getMinEmpleados() {
		return minEmpleados;
	}



	public void setMinEmpleados(int minEmpleados) {
		this.minEmpleados = minEmpleados;
	}



	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}



	public void setDeTemporada(boolean deTemporada) {
		this.deTemporada = deTemporada;
	}



	public boolean verificarEmpleados() {

        return true; 
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public boolean isEnFuncionamiento() {
        return enFuncionamiento;
    }

    public void setEnFuncionamiento(boolean enFuncionamiento) {
        this.enFuncionamiento = enFuncionamiento;
    }

    public boolean isDeTemporada() {
        return deTemporada;
    }

    public Temporada getTemporada() {
        return temporada;
    }



	public boolean estaDisponible() {
		// TODO Auto-generated method stub
		return false;
	}



	protected boolean verificarMinimoEmpleados(Date fecha) {
		// TODO Auto-generated method stub
		return false;
	}



	protected ArrayList<String> getEmpleadosAsignados() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
	    return "Atraccion {" +
	           "nombre: " + nombre +
	           ", ubicacion: " + ubicacion +
	           ", cupoMax :" + cupoMax +
	           ", deTemporada :" + deTemporada +
	           ", temporada :" + (temporada != null ? temporada.toString() : "N/A") +
	           ", enFuncionamiento: " + enFuncionamiento +
	           '}';
	}
}
