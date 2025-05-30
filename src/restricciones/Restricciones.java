package restricciones;

import java.util.ArrayList;

public abstract class Restricciones {
	protected ArrayList<String> clima;
	protected String exclusividad;
	
	public Restricciones(ArrayList<String> clima, String exclusividad) {
		this.clima = clima;
		this.exclusividad = exclusividad;
	}

	public ArrayList<String> getClima() {
		return clima;
	}

	public void setClima(ArrayList<String> clima) {
		this.clima = clima;
	}

	public String getExclusividad() {
		return exclusividad;
	}

	public void setExclusividad(String exclusividad) {
		this.exclusividad = exclusividad;
	}
	
	
}
