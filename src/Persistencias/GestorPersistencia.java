package Persistencias;

import java.text.ParseException;

import Persistencias.*;
import Tiquetes.GestorTiquetes;

public class GestorPersistencia {
	private static GestorPersistencia instancia;
	public void gestorCargaDatos() throws ParseException {
		PersistenciaTemporada.cargarDatos();
		PersistenciaCategoriaTiquete.cargarDatos();
		PersistenciaCliente.cargarDatos();
		PersistenciaAdministrador.cargarDatos();
		PersistenciaEmpleado.cargarDatos();
		PersistenciaTiqueteRegular.cargarDatos();
		PersistenciaTiqueteTemporada.cargarDatos();
		PersistenciaTiqueteFastPass.cargarDatos();
		PersistenciaAtraccionCultural.cargarDatos();
		PersistenciaAtraccionMecanica.cargarDatos();
		PersistenciaEspectaculo.cargarDatos();
	}
    public static GestorPersistencia getInstance() {
        if (instancia == null) {
            instancia = new GestorPersistencia();
        }
        return instancia;
    }
}
