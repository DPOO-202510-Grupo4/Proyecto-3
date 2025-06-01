package Tiquetes;

import Persistencias.PersistenciaCategoriaTiquete;
import Persistencias.PersistenciaTemporada;
import Persistencias.PersistenciaTiqueteFastPass;
import Persistencias.PersistenciaTiqueteRegular;
import Persistencias.PersistenciaTiqueteTemporada;
import Persona.Cliente;
import Persona.GestorPersonas;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import restricciones.Temporada;

public class GestorTiquetes {
    // ======================
    // Atributos
    // ======================
    private HashMap<String, ArrayList<Tiquete>> tiquetesVendidos;
    private ArrayList<CategoriaTiquete> categoriasDisponibles;
    private ArrayList<Temporada> temporadas;
    private static GestorTiquetes instancia;

    // ======================
    // Singleton e inicialización
    // ======================
    public GestorTiquetes() {
        this.tiquetesVendidos = new HashMap<>();
        this.categoriasDisponibles = new ArrayList<>();
        this.temporadas = new ArrayList<>();
        inicializarCategoriasPorDefecto();
    }

    public static GestorTiquetes getInstancia() {
        if (instancia == null) {
            instancia = new GestorTiquetes();
        }
        return instancia;
    }

    // ======================
    // Gestión de TIQUETES
    // ======================
    public static String generarID(String nombreCategoria) {
        if (nombreCategoria == null || nombreCategoria.isEmpty()) {
            return generarNumerosAleatorios(7);  // Solo números si no hay categoría
        }

        char primeraLetra = nombreCategoria.charAt(0);  // la primera letra
        String numeros = generarNumerosAleatorios(7);
        return Character.toUpperCase(primeraLetra) + numeros;
    }

    private static String generarNumerosAleatorios(int longitud) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            int num = (int)(Math.random() * 10);
            sb.append(num);
        }
        return sb.toString();
    }

    public Tiquete crearTiqueteTemporada(Cliente cliente, String nombreCategoria, Temporada temporada ) {
    	ArrayList<CategoriaTiquete> categorias = getCategoriasDisponibles();
        CategoriaTiquete categoria = null;
        for (CategoriaTiquete c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombreCategoria)) {
                categoria = c;
                break;
            }
        }

        if (categoria == null) {
            System.out.println("Categoría no encontrada: " + nombreCategoria);
            return null;
        }
        String id = generarID(categoria.getNombre());
        TiqueteTemporada t = new TiqueteTemporada("Tiquete " + categoria.getNombre(), categoria.getPrecioBase() , id, categoria, false,cliente, temporada);
        
        PersistenciaTiqueteTemporada.persistencia(t);
        agregarTiqueteACliente(cliente, t);
        return t;
    }

    public Tiquete cargarTiqueteTemporada(String login, String nombreCategoria, String temporada, String id ) {
    	GestorPersonas gestor = GestorPersonas.getInstance();
    	ArrayList<CategoriaTiquete> categorias = getCategoriasDisponibles();
        CategoriaTiquete categoria = null;
        for (CategoriaTiquete c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombreCategoria)) {
                categoria = c;
                break;
            }
        }
        Temporada temp = buscarTemporada(temporada);
        if (categoria == null) {
            System.out.println("Categoría no encontrada: " + nombreCategoria);
            return null;
        }
        Cliente cliente = gestor.buscarCliente(login);
        TiqueteTemporada t = new TiqueteTemporada("Tiquete " + categoria.getNombre(), categoria.getPrecioBase() , id, categoria, false,cliente, temp);
        
        PersistenciaTiqueteTemporada.persistencia(t);
        return t;
    }    


    public Tiquete crearTiqueteRegular(Cliente cliente, String nombreCategoria, Date fecha) {
 
        ArrayList<CategoriaTiquete> categorias = getCategoriasDisponibles();
        CategoriaTiquete categoria = null;
        for (CategoriaTiquete c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombreCategoria)) {
                categoria = c;
                break;
            }
        }

        if (categoria == null) {
            System.out.println("Categoría no encontrada: " + nombreCategoria);
            return null;
        }
        String id = generarID(categoria.getNombre());
        TiqueteRegular t = new TiqueteRegular(
            "Tiquete " + categoria.getNombre(),
            categoria.getPrecioBase(),
            id,
            categoria,
            false,
            cliente,
            fecha
        );
        PersistenciaTiqueteRegular.persistencia(t);
        agregarTiqueteACliente(cliente, t);
        return t;
    }
    public void cargarTiqueteRegular(String login, String nombreCategoria, Date fecha, String id, boolean usado) {
    	GestorPersonas gestor = GestorPersonas.getInstance();
        ArrayList<CategoriaTiquete> categorias = getCategoriasDisponibles();
        CategoriaTiquete categoria = null;
        for (CategoriaTiquete c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombreCategoria)) {
                categoria = c;
                break;
            }
        }

        if (categoria == null) {
            System.out.println("Categoría no encontrada: " + nombreCategoria);
        }
        Cliente cliente = gestor.buscarCliente(login);
        TiqueteRegular t = new TiqueteRegular(
            "Tiquete " + categoria.getNombre(),
            categoria.getPrecioBase(),
            id,
            categoria,
            false,
            cliente,
            fecha
        );
        agregarTiqueteACliente(cliente, t);
        if (usado == true){
        	usarTiquete(t);
        }
    }
    public FastPass crearFastPass(String nombre, Double precioBase, Date fecha, boolean usado, String idTiquete,String dueño) {
    	GestorPersonas gestor = GestorPersonas.getInstance();
        FastPass fastPass = new FastPass(nombre, precioBase, fecha, usado, idTiquete,dueño);
        Cliente cliente = gestor.buscarCliente(dueño);
        agregarFastPassACliente(cliente, fastPass);
        PersistenciaTiqueteFastPass.persistencia(fastPass);
        return fastPass;
    }
    public  void cargarFastPass(String nombre, Double precioBase, Date fecha, boolean usado, String idTiquete,String dueño) {
    	GestorPersonas gestor = GestorPersonas.getInstance();
        FastPass fastPass = new FastPass(nombre, precioBase, fecha, usado, idTiquete,dueño);
        Cliente cliente = gestor.buscarCliente(dueño);
        agregarFastPassACliente(cliente, fastPass);

    }
    

    public void usarTiquete(Tiquete tiquete) {
        tiquete.marcarComoUsado();
    }

    public ArrayList<Tiquete> getTiquetesDeCliente(Cliente cliente) {
        return new ArrayList<>(tiquetesVendidos.getOrDefault(cliente, new ArrayList<>()));
    }
    public Tiquete buscarTiquetePorId(String id) {
        for (ArrayList<Tiquete> listaTiquetes : tiquetesVendidos.values()) {
            for (Tiquete t : listaTiquetes) {
                if (t.getId().equals(id)) {
                    return t;
                }
            }
        }
        return null;
    }

    // ======================
    // Gestión de CATEGORÍAS de TIQUETE
    // ======================

    public boolean crearCategoriaTiquete(String nombre, ArrayList<String> atraccionesDisponibles, Double precioBase) {
    	
        CategoriaTiquete existente = buscarCategoria(nombre);
        if (existente != null) {
            existente.setAtraccionesDisponibles(atraccionesDisponibles);
            existente.setPrecioBase(precioBase);
            return true;
        }

        CategoriaTiquete nuevaCategoria = new CategoriaTiquete(nombre, atraccionesDisponibles, precioBase);
        categoriasDisponibles.add(nuevaCategoria);
        PersistenciaCategoriaTiquete.persistencia(nuevaCategoria);
        return true;
    }
    public boolean cargarCategoriaTiquete(String nombre, ArrayList<String> atraccionesDisponibles, Double precioBase) {
    	
        CategoriaTiquete existente = buscarCategoria(nombre);
        if (existente != null) {
            existente.setAtraccionesDisponibles(atraccionesDisponibles);
            existente.setPrecioBase(precioBase);
            return true;
        }

        CategoriaTiquete nuevaCategoria = new CategoriaTiquete(nombre, atraccionesDisponibles, precioBase);
        categoriasDisponibles.add(nuevaCategoria);
        return true;
    }

    public ArrayList<CategoriaTiquete> getCategoriasDisponibles() {
        return categoriasDisponibles;
    }


    // ======================
    // Gestión de TEMPORADAS
    // ======================
    public void crearTemporada(Date fechaInicio, Date fechaFinal, String nombre) {
        if (fechaInicio == null || fechaFinal == null || nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("Los parámetros de la temporada son inválidos.");
        }

        if (fechaInicio.after(fechaFinal)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha final.");
        }

        Temporada temporada = new Temporada(fechaInicio, fechaFinal, nombre);
        temporadas.add(temporada);
        PersistenciaTemporada.guardarTemporada(temporada);

        System.out.println("Temporada creada exitosamente: " + temporada.getName());
    }

    public List<Temporada> obtenerTemporadas() {
        return temporadas;
    }

    public void consultarTemporadas() {
        if (temporadas.isEmpty()) {
            System.out.println("No hay temporadas registradas.");
        } else {
            for (Temporada temporada : temporadas) {
                System.out.println(temporada.getName() + " - Inicio: " + temporada.getFechaInicio() + " - Fin: " + temporada.getFechaFinal());
            }
        }
    }

    public void cargarTemporada(Date fechaInicio, Date fechaFinal, String nombre) {
    	if (fechaInicio == null || fechaFinal == null || nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("Los parámetros de la temporada son inválidos.");
        }

        /*if (fechaInicio.after(fechaFinal)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha final.");
        }*/ 
    	//Se dañó antes de la entrega, pero para la próxima estará arreglado!

        Temporada temporada = new Temporada(fechaInicio, fechaFinal, nombre);
        temporadas.add(temporada);
    }

    // ======================
    // Métodos PRIVADOS auxiliares
    // ======================

    public CategoriaTiquete buscarCategoria(String tipoCategoria) {
        for (CategoriaTiquete cat : categoriasDisponibles) {
            if (cat.getNombre().equalsIgnoreCase(tipoCategoria)) {
                return cat;
            }
        }
        return null;
    }

    private void agregarTiqueteACliente(Cliente cliente, Tiquete tiquete) {

        cliente.agregarTiquete(tiquete);
    }
    private void agregarFastPassACliente(Cliente cliente, FastPass fastpass) {
        cliente.agregarFastPass(fastpass);
    }

	public ArrayList<Temporada> getTemporadas() {
		return temporadas;
	}


	public HashMap<String, ArrayList<Tiquete>> getTiquetesVendidos() {
		return tiquetesVendidos;
	}
	
	public Temporada buscarTemporada(String temporadaIn) {
	    
	    ArrayList<Temporada> temporadas = getTemporadas();

	    for (Temporada t : temporadas) {
	        if (t.getName().equalsIgnoreCase(temporadaIn)) {
	            return t;
	        }
	    }

	    return null; 
	}
    
    public static GestorTiquetes getInstance() {
        if (instancia == null) {
            instancia = new GestorTiquetes();
        }
        return instancia;
    }

    private void inicializarCategoriasPorDefecto() {
    if (categoriasDisponibles.isEmpty()) {
        ArrayList<String> sinAcceso = new ArrayList<>();
        ArrayList<String> familiar = new ArrayList<>();
        ArrayList<String> oro = new ArrayList<>();
        ArrayList<String> diamante = new ArrayList<>();

        // Llenar listas con las atracciones a las que se tiene acceso

        categoriasDisponibles.add(new CategoriaTiquete("Básico", sinAcceso, 10000.0));
        categoriasDisponibles.add(new CategoriaTiquete("Familiar", familiar, 30000.0));
        categoriasDisponibles.add(new CategoriaTiquete("Oro", oro, 45000.0));
        categoriasDisponibles.add(new CategoriaTiquete("Diamante", diamante, 60000.0));
    }
}

}


