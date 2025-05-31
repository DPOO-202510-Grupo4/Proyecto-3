package Persona;
import Tiquetes.Factura;
import Tiquetes.FastPass;
import Tiquetes.Tiquete;
import java.util.ArrayList;
import java.util.HashSet;

public class Cliente extends Persona {
	
	public ArrayList<Factura> historialCompras;
	public ArrayList<Tiquete> tiquetes;
	public ArrayList<FastPass> fastPass;// Cliente.java
	private HashSet<String> tiquetesImpresos = new HashSet<>();

	
	public Cliente(String nombre, String login, String password, String fechaNacimiento) {
	    super(nombre, login, password, fechaNacimiento);
	    this.historialCompras = new ArrayList<>();
	    this.tiquetes = new ArrayList<>();
	    this.fastPass = new ArrayList<>();
	}
	public void agregarTiquete(Tiquete tiquete) {
		this.tiquetes.add(tiquete);
	}

	public void agregarFastPass(FastPass fastPass) {
		this.fastPass.add(fastPass);
	}

	public void agregarFactura(Factura factura) {
		this.historialCompras.add(factura);
	}
	public ArrayList<Factura> getHistorialCompras() {
		return historialCompras;
	}
	public void setHistorialCompras(ArrayList<Factura> historialCompras) {
		this.historialCompras = historialCompras;
	}
	public ArrayList<Tiquete> getTiquetes() {
		return tiquetes;
	}
	public void setTiquetes(ArrayList<Tiquete> tiquetes) {
		this.tiquetes = tiquetes;
	}
	public ArrayList<FastPass> getFastPass() {
		return fastPass;
	}
	public void setFastPass(ArrayList<FastPass> fastPass) {
		this.fastPass = fastPass;
	}

	public boolean yaImpreso(String idTiquete) {
    	return tiquetesImpresos.contains(idTiquete);
	}

	public void marcarComoImpreso(String idTiquete) {
    	tiquetesImpresos.add(idTiquete);
	}
	
}


