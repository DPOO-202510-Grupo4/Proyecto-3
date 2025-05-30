package Tiquetes;

import java.util.ArrayList;
import java.util.Date;

import Persona.Cliente;

public class Factura {
private ArrayList<ItemVenta> items;
private Date fecha;
private Cliente cliente;
private Double costo;

public Factura(ArrayList<ItemVenta> items, Date fecha, Cliente cliente, Double costo) {
	
	this.items = items;
	this.fecha = fecha;
	this.cliente = cliente;
	this.costo = calcularCosto(items);
}

private Double calcularCosto(ArrayList<ItemVenta> items) {
	Double costo = 0.0;
	for (ItemVenta i : items) {
		costo += i.getPrecioBase();
	}
	return costo;
}

public ArrayList<ItemVenta> getItems() {
	return items;
}

public void setItems(ArrayList<ItemVenta> items) {
	this.items = items;
}

public Date getFecha() {
	return fecha;
}

public void setFecha(Date fecha) {
	this.fecha = fecha;
}

public Cliente getCliente() {
	return cliente;
}

public void setCliente(Cliente cliente) {
	this.cliente = cliente;
}

public Double getCosto() {
	return costo;
}

public void setCosto(Double costo) {
	this.costo = costo;
}



}
