package Persona;

import java.time.LocalDate;
import java.util.ArrayList;

public class Empleado extends Persona {


	private Capacitaciones capacitaciones;
	private Rol rolActual; 
	private LugarTrabajo lugarTrabajo;

	public Empleado(String nombre, String login, String password, String fechaNacimiento) {
	    super(nombre, login, password, fechaNacimiento);

	    this.capacitaciones = null;
	    this.rolActual = null;
	    this.lugarTrabajo = null;
	}

	@Override
	public String getFechaNacimiento() {
		return super.getFechaNacimiento();
	}

	@Override
	public String getLogin() {
		return super.getLogin();
	}

	@Override
	public String getNombre() {
		return super.getNombre();
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public void setFechaNacimiento(String fechaNacimiento) {
		super.setFechaNacimiento(fechaNacimiento);
	}

	@Override
	public void setLogin(String login) {
		super.setLogin(login);
	}

	@Override
	public void setNombre(String nombre) {
		super.setNombre(nombre);
	}

	@Override
	public void setPassword(String password) {
		super.setPassword(password);
	}



	public LugarTrabajo getLugarTrabajo() {
		return this.lugarTrabajo;

	}

	public Capacitaciones getCapacitaciones() {
		return this.capacitaciones;
	}


	public Rol getRolActual() {
		return rolActual;
	}
	
}



	
	

