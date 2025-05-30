package Atracciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import Persona.Empleado;
import Persona.Persona;
import Tiquetes.Tiquete;
import restricciones.RestriccionesCultural;
import restricciones.Temporada;

public class AtraccionCultural extends Atraccion {

    private ArrayList<Temporada> disponibilidad;
    private RestriccionesCultural restricciones;
    private ArrayList<String> empleadosAsignados;

    public AtraccionCultural(String nombre, String ubicacion, int cupoMax, int minEmpleados, boolean deTemporada, Temporada temporada) {
        super(nombre, ubicacion, cupoMax, minEmpleados, deTemporada, temporada);
        this.disponibilidad = new ArrayList<>();
        this.restricciones = null;
        this.empleadosAsignados = new ArrayList<>();
    }

    @Override
    public boolean esDisponible(Date fecha) {
        if (!enFuncionamiento) return false;

        if (deTemporada && temporada != null) {
            return temporada.estaDentroDeTemporada(fecha);
        }

        for (Temporada t : disponibilidad) {
            if (t.estaDentroDeTemporada(fecha)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean verificarEmpleados() {
        return empleadosAsignados != null && empleadosAsignados.size() >= minEmpleados;
    }

    public boolean validarRestricciones(Persona persona, Tiquete tiquete) {
        return validarEdad(persona) && validarTiquete(tiquete);
    }

    public boolean validarEdad(Persona persona) {
        if (restricciones == null) return true;

        String fechaNacimiento = persona.getFechaNacimiento();
        LocalDate formatoFecha = LocalDate.parse(fechaNacimiento);
        int edad = LocalDate.now().getYear() - formatoFecha.getYear();

        return edad >= restricciones.getEdad();
    }

    public boolean validarTiquete(Tiquete tiquete) {
        if (restricciones == null) return true;
        return restricciones.permiteTiquete(tiquete.getCategoria(), this);
    }

    public ArrayList<Temporada> getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(ArrayList<Temporada> disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public RestriccionesCultural getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(RestriccionesCultural restricciones) {
        this.restricciones = restricciones;
    }

    @Override
    public ArrayList<String> getEmpleadosAsignados() {
        return empleadosAsignados;
    }

    public void setEmpleadosAsignados(ArrayList<String> empleadosAsignados) {
        this.empleadosAsignados = empleadosAsignados;
    }
    

    @Override
    public String toString() {
        return "Atracción Cultural: " +
               "\n  - Nombre: " + getNombre() +
               "\n  - Ubicación: " + getUbicacion();
    }
}