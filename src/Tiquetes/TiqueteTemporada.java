package Tiquetes;
import Persona.Cliente;
import restricciones.Temporada;

public class TiqueteTemporada extends Tiquete {
	private Temporada temporada;
	public TiqueteTemporada(String nombre, Double precioBase,  String idTiquete,
			CategoriaTiquete categoria, boolean usado, Cliente cliente, Temporada temporada) {
		super(nombre, precioBase, idTiquete, categoria, usado, cliente);
		this.temporada = temporada;
	}

	public Temporada getTemporada() {
		return temporada;
	}

	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}
	@Override
	public double calcularPrecio() {
		// TODO Auto-generated method stub
		return 0;
	}


	

	

	
	
}
