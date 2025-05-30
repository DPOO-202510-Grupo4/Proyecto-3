package restricciones;

import java.util.ArrayList;
import java.util.List;

import Atracciones.Atraccion;
import Tiquetes.CategoriaTiquete;

public class RestriccionesMecanica extends Restricciones {

    private Double alturaMin;
    private Double alturaMax;
    private Double pesoMin;
    private Double pesoMax;
    private ArrayList<String> salud;

    public RestriccionesMecanica(ArrayList<String> clima, String exclusividad, Double alturaMin, Double alturaMax,
    		Double pesoMin, Double pesoMax, ArrayList<String> salud) {
        super(clima, exclusividad);
        this.alturaMin = alturaMin;
        this.alturaMax = alturaMax;
        this.pesoMin = pesoMin;
        this.pesoMax = pesoMax;
        this.salud = salud;
    }

    public Double getAlturaMin() {
        return alturaMin;
    }

    public void setAlturaMin(Double alturaMin) {
        this.alturaMin = alturaMin;
    }

    public Double getAlturaMax() {
        return alturaMax;
    }

    public void setAlturaMax(Double alturaMax) {
        this.alturaMax = alturaMax;
    }

    public Double getPesoMin() {
        return pesoMin;
    }

    public void setPesoMin(Double pesoMin) {
        this.pesoMin = pesoMin;
    }

    public Double getPesoMax() {
        return pesoMax;
    }

    public void setPesoMax(Double pesoMax) {
        this.pesoMax = pesoMax;
    }

    public ArrayList<String> getSalud() {
        return salud;
    }

    public void setSalud(ArrayList<String> salud) {
        this.salud = salud;
    }

    public boolean puedeOperarSegunClima(String climaActual) {
        return !getClima().contains(climaActual.toLowerCase());
    }

    public boolean validarAlturaYPeso(int altura, int peso) {
        return altura >= alturaMin && altura <= alturaMax &&
               peso >= pesoMin && peso <= pesoMax;
    }

    public boolean permiteTiquete(CategoriaTiquete categoria, Atraccion atraccion) {
	    return categoria.getAtraccionesDisponibles().contains(atraccion);
	}

    public boolean tieneRestriccionMedica(List<String> condicionesDelUsuario) {
        for (String condicion : condicionesDelUsuario) {
            if (salud.contains(condicion.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}