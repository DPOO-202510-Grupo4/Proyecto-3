package Persona;

import java.time.LocalDate;

public class Administrador extends Persona {

	

	public Administrador(String nombre, String login, String password, String fechaNacimiento) {
		super(nombre, login, password, fechaNacimiento);
	}

	@Override
	public String getFechaNacimiento() {
		// TODO Auto-generated method stub
		return super.getFechaNacimiento();
	}

	@Override
	public String getLogin() {
		// TODO Auto-generated method stub
		return super.getLogin();
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return super.getNombre();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return super.getPassword();
	}

}
