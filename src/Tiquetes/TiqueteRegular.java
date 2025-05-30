package Tiquetes;

import java.util.Date;

import Persona.Cliente;

public class TiqueteRegular extends Tiquete{
	private Date fecha;

	public TiqueteRegular(String nombre, Double precioBase, String idTiquete,
			CategoriaTiquete categoria, boolean usado, Cliente cliente, Date fecha) {
		super(nombre, precioBase, idTiquete, categoria, usado, cliente);
		this.fecha = fecha;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	@Override
	public double calcularPrecio() {
		// TODO Auto-generated method stub
		return 0;
	}





}
