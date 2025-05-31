package Persona;

import Atracciones.Atraccion;
import Persistencias.PersistenciaAdministrador;
import Persistencias.PersistenciaCliente;
import Persistencias.PersistenciaEmpleado;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class GestorPersonas {

    private static GestorPersonas instanciaUnica;

    private ArrayList<Administrador> administradores;
    private HashMap<String, Empleado> empleados;
    private HashMap<String, Cliente> clientes;
	private ArrayList<Turno> turnos;
	private ArrayList<String> tareas;

    private GestorPersonas() {
        this.empleados = new HashMap<>();
        this.clientes = new HashMap<>();
        this.administradores = new ArrayList<>();
	    this.turnos = new ArrayList<>();
	    this.tareas = new ArrayList<>();
    }

    public static GestorPersonas getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new GestorPersonas();
        }
        return instanciaUnica;
    }
    public enum TipoUsuario {
        ADMINISTRADOR,
        EMPLEADO,
        CLIENTE,
        NO_ENCONTRADO
    }


    //------------------------ EMPLEADOS ------------------------

    public Empleado crearEmpleadoBasico(String nombre, String login, String password, String fechaNacimiento) {
        Empleado nuevo = new Empleado(nombre, login, password, fechaNacimiento);
        registrarEmpleado(nuevo);
        return nuevo;
    }

    public void registrarEmpleado(Empleado empleado) {
        this.empleados.put(empleado.getLogin(), empleado);
        PersistenciaEmpleado.persistencia(empleado);
    }

    public void cargarEmpleado(String nombre, String login, String password, String fechaNacimiento,String lugarTrabajo, String rol) {
    	Empleado empleado = new Empleado(nombre, login, password, fechaNacimiento);
        this.empleados.put(login, empleado);
    }

    public void eliminarEmpleado(String login) {
        if (this.empleados.containsKey(login)) {
            this.empleados.remove(login);
            System.out.println("Empleado con login '" + login + "' eliminado exitosamente.");
        } else {
            System.out.println("Empleado con login '" + login + "' no encontrado.");
        }
    }

    public Empleado obtenerEmpleadoPorLogin(String login) {
        Empleado empleado = this.empleados.get(login);
        if (empleado != null) {
            return empleado;
        } else {
            return null;
        }
    }

    public ArrayList<Empleado> empleadosPorTipo(String tipo) {

        HashMap<String, ArrayList> empleadosTipo = new HashMap<>();

		ArrayList<Empleado> cocineros = new ArrayList<>();
		ArrayList<Empleado> cajeros = new ArrayList<>();

        
		for(Empleado empleado : this.empleados.values()){
			Capacitaciones capacitaciones = empleado.getCapacitaciones();
			if(capacitaciones.getEsCocinero() == true){
				cocineros.add(empleado);
			}
			if(capacitaciones.getEsCajero() == true){
				cajeros.add(empleado);
			}
		}

		empleadosTipo.put("Cocinero", cocineros);
		empleadosTipo.put("Cajero", cajeros);
		
        return empleadosTipo.get(tipo);
    }

    /*public ArrayList<Empleado> empleadosDisponibles(LocalDate fecha, String lugar) {
        ArrayList<Empleado> empleadosDisponibles = new ArrayList<>();
        for (Empleado empleado : this.empleados.values()) {
            for (Turno turno : empleado.getTurnos()) {
                if (!turno.getFecha().equals(fecha) && !turno.getLugarTrabajo().equals(lugar)) {
                    empleadosDisponibles.add(empleado);
                }
            }
        }
        return empleadosDisponibles;
    }

    public void asignarTurno(String login, Turno turno) {
        Empleado empleado = this.empleados.get(login);
        if (empleado != null) {
            if (empleado.getTurnos().contains(turno)) {
                System.out.println("El empleado ya tiene un turno asignado en esa fecha.");
            } else {
                empleado.getTurnos().add(turno);
            }
        } else {
            System.out.println("Empleado no encontrado para asignar turno.");
        }
    }

    public ArrayList<Turno> turnosDeEmpleado(Empleado empleado) {
        return new ArrayList<>(empleado.getTurnos());
    }

    public void asignarTarea(String login, String tarea) {
        Empleado empleado = this.empleados.get(login);
        if (empleado != null) {
            if (empleado.getTareas().contains(tarea)) {
                System.out.println("El empleado ya tiene esta tarea asignada.");
            } else {
                empleado.getTareas().add(tarea);
            }
        } else {
            System.out.println("Empleado no encontrado para asignar tarea.");
        }
    }*/

    public boolean verificarMinimosAtraccion(Atraccion atraccion, LocalDate fecha) {
        // TODO: implementar verificación de mínimos
        return false;
    }

    public HashMap<String, Empleado> getEmpleados() {
        return empleados;
    }

    //------------------------ CLIENTES ------------------------

    public void registrarCliente(String nombre, String login, String contrasena, String fechaNacimiento) {
        Cliente nuevoCliente = new Cliente(nombre, login, contrasena, fechaNacimiento);
        clientes.put(nuevoCliente.getLogin(), nuevoCliente);
        PersistenciaCliente.persistencia(nuevoCliente);
    }
    public void cargarCliente(String nombre, String login, String password, String fechaNacimiento) {
    	Cliente cliente = new Cliente(nombre, login, password, fechaNacimiento);
        this.clientes.put(cliente.getLogin(), cliente);
    }

    public Cliente buscarCliente(String login) {
        return clientes.get(login);
    }

    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }


    //------------------------ ADMINISTRADORES ------------------------

    public Administrador crearAdministrador(String nombre, String login, String password, String fechaNacimiento) {
        Administrador nuevoAdministrador = new Administrador(nombre, login, password, fechaNacimiento);
        registrarAdministrador(nuevoAdministrador);
        return nuevoAdministrador;
    }
    public void cargarAdministrador(String nombre, String login, String password, String fechaNacimiento) {
    	Administrador administrador = new Administrador(nombre, login, password, fechaNacimiento);
        this.administradores.add(administrador);  
    }

    public void registrarAdministrador(Administrador administrador) {
        this.administradores.add(administrador);
        PersistenciaAdministrador.persistencia(administrador);  
    }

    public void eliminarAdministrador(String login) {
        Administrador administradorAEliminar = null;
        for (Administrador administrador : this.administradores) {
            if (administrador.getLogin().equals(login)) {
                administradorAEliminar = administrador;
                break;
            }
        }
        if (administradorAEliminar != null) {
            this.administradores.remove(administradorAEliminar);
            System.out.println("Administrador con login '" + login + "' eliminado exitosamente.");
        } else {
            System.out.println("Administrador con login '" + login + "' no encontrado.");
        }
    }

    public Administrador obtenerAdministrador(String login) {
        for (Administrador administrador : this.administradores) {
            if (administrador.getLogin().equals(login)) {
                return administrador;
            }
        }
        return null;
    }

    public ArrayList<Administrador> getAdministradores() {
        return administradores;
    }
    public TipoUsuario validarLogin(String login, String contraseña) {
        Administrador admin = obtenerAdministrador(login);
        if (admin != null && admin.getPassword().equals(contraseña)) {
            return TipoUsuario.ADMINISTRADOR;
        }

        Empleado empleado = obtenerEmpleadoPorLogin(login);
        if (empleado != null && empleado.getPassword().equals(contraseña)) {
            return TipoUsuario.EMPLEADO;
        }

        Cliente cliente = buscarCliente(login);
        if (cliente != null && cliente.getPassword().equals(contraseña)) {
            return TipoUsuario.CLIENTE;
        }

        return TipoUsuario.NO_ENCONTRADO;
    }
}
