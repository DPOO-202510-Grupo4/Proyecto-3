package Tiquetes;

public abstract class ItemVenta {
	private String nombre;
	private Double precioBase;
	
	
public ItemVenta(String nombre, Double precioBase) {
		this.nombre = nombre;
		this.precioBase = precioBase;
		
	}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public Double getPrecioBase() {
	return precioBase;
}

public void setPrecioBase(Double precioBase) {
	this.precioBase = precioBase;
}

public double calcularPrecio() {
	// TODO Auto-generated method stub
	return 0;
}



}
