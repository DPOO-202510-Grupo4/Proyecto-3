package Atracciones;

import java.util.ArrayList;
import java.util.Date;

import Persona.Empleado;
import Tiquetes.Tiquete;
import restricciones.RestriccionesMecanica;
import restricciones.Temporada;

public class AtraccionMecanica extends Atraccion {

    protected String riesgo;
    protected boolean disponible;
    protected RestriccionesMecanica restricciones;
    protected ArrayList<String> empleadosAsignados;

    public AtraccionMecanica(String nombre, String ubicacion, int cupoMax, int minEmpleados, boolean deTemporada,
			Temporada temporada, String riesgo, boolean disponible) {
		super(nombre, ubicacion, cupoMax, minEmpleados, deTemporada, temporada);
		this.riesgo = riesgo;
		this.restricciones = null;
		this.empleadosAsignados = new ArrayList<>();
        this.disponible = disponible;
	}

	public String getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(String riesgo) {
        this.riesgo = riesgo;
    }

    public RestriccionesMecanica getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(RestriccionesMecanica restricciones) {
        this.restricciones = restricciones;
    }

    @Override
    public ArrayList<String> getEmpleadosAsignados() {
		return empleadosAsignados;
	}


	public boolean validarTiquete(Tiquete tiquete) {
        if (restricciones == null) return true;
        return restricciones.permiteTiquete(tiquete.getCategoria(), this);
    }

    public boolean validarAlturaYPeso(int altura, int peso) {
        return restricciones == null || restricciones.validarAlturaYPeso(altura, peso);
    }

    public boolean puedeOperarSegunClima(String clima) {
        return restricciones == null || restricciones.puedeOperarSegunClima(clima);
    }

    public boolean puedeOperarSegunNumEmpleados(int cantidadEmpleadosActuales) {
        return cantidadEmpleadosActuales >= getMinEmpleados();
    }


	public boolean usarAtraccion(Tiquete tiquete, String clima, int altura, int peso, int numEmpleados) {
        return validarTiquete(tiquete) &&
               validarAlturaYPeso(altura, peso) &&
               puedeOperarSegunClima(clima) &&
               puedeOperarSegunNumEmpleados(numEmpleados);
    }

    @Override
    public boolean estaDisponible() {
        if (isDeTemporada()) {
            return getTemporada().estaDentroDeTemporada(new Date());
        }
        return true;
    }

    @Override
    protected boolean verificarMinimoEmpleados(Date fecha) {
        return getEmpleadosAsignados() != null && getEmpleadosAsignados().size() >= getMinEmpleados();
    }



    @Override
    public String toString() {


        return "Atracción Mecánica: " +
               "\n  - Nombre: " + getNombre() +
               "\n  - Ubicación: " + getUbicacion() +
               "\n  - Riesgo: " + getRiesgo();
    }}
