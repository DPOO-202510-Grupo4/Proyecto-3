package presentacion;

import Atracciones.Atraccion;
import Persona.Capacitaciones;
import Persona.Empleado;
import Persona.GestorPersonas;
import Persona.Turno;
import Tiquetes.GestorTiquetes;
import Tiquetes.Tiquete;
import java.util.ArrayList;
import java.util.Scanner;

public class consolaEmpleado {

    public static void menuEmpleado(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENÚ EMPLEADO ---");
            System.out.println("1. Consultar Turnos");
            System.out.println("2. Consultar Tareas");
            System.out.println("3. Consultar Capacitaciones");
            System.out.println("4. Registrar Venta");
            System.out.println("5. Registrar Tiquete");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            String input = scanner.nextLine();
            int opcion;

            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                continue;
            }

            switch (opcion) {
                case 1:
                    //consultarTurnos(scanner);
                    break;
                case 2:
                    //consultarTareas(scanner);
                    break;
                case 3:
                    consultarCapacitaciones(scanner);
                    break;
                case 4:
                    registrarVenta(scanner);
                    break;
                case 5:
                    registrarTiquete(scanner);
                    break;
                case 6:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static Empleado obtenerEmpleadoDesdeLogin(Scanner scanner) {
        System.out.println("Ingrese su Login: ");
        String login = scanner.nextLine();

        GestorPersonas gestor = GestorPersonas.getInstance();
        Empleado empleado = gestor.obtenerEmpleadoPorLogin(login);

        if (empleado == null) {
            System.out.println("Empleado con login '" + login + "' no encontrado.");
        }

        return empleado;
    }

    /*private static void consultarTurnos(Scanner scanner) {
        Empleado empleado = obtenerEmpleadoDesdeLogin(scanner);
        if (empleado == null) return;

        GestorPersonas gestor = GestorPersonas.getInstance();
        ArrayList<Turno> turnos = gestor.turnosDeEmpleado(empleado);

        if (turnos.isEmpty()) {
            System.out.println("El empleado no tiene turnos asignados.");
        } else {
            System.out.println("Turnos asignados al empleado:");
            for (Turno turno : turnos) {
                System.out.println(turno);
            }
        }
    }*/

    /*private static void consultarTareas(Scanner scanner) {
        Empleado empleado = obtenerEmpleadoDesdeLogin(scanner);
        if (empleado == null) return;

        ArrayList<String> tareas = empleado.getTareas();

        if (tareas.isEmpty()) {
            System.out.println("El empleado no tiene tareas asignadas.");
        } else {
            System.out.println("Tareas asignadas al empleado:");
            for (String tarea : tareas) {
                System.out.println("- " + tarea);
            }
        }
    } */

    private static void consultarCapacitaciones(Scanner scanner) {
        Empleado empleado = obtenerEmpleadoDesdeLogin(scanner);
        if (empleado == null) return;

        Capacitaciones capacitaciones = empleado.getCapacitaciones();

        if (capacitaciones == null) {
            System.out.println("El empleado no tiene capacitaciones asignadas.");
            return;
        }

        System.out.println("Capacitaciones del empleado:");
        System.out.println("Cocinero: " + capacitaciones.getEsCocinero());
        System.out.println("Cajero: " + capacitaciones.getEsCajero());
        System.out.println("Atracciones capacitadas:");
        for (Atraccion atraccion : capacitaciones.getCapacitacionAtracciones()) {
            System.out.println("- " + atraccion.getNombre());
        }
    }

    private static void registrarVenta(Scanner scanner) {
        consolaCliente.comprarTiquete(scanner);
    }

    private static void registrarTiquete(Scanner scanner) {
        System.out.println("Ingrese el ID del tiquete: ");
        String id = scanner.nextLine();

        GestorTiquetes gestorTiquetes = GestorTiquetes.getInstancia();
        Tiquete tiquete = gestorTiquetes.buscarTiquetePorId(id);

        if (tiquete != null) {
            gestorTiquetes.usarTiquete(tiquete);
            System.out.println("Tiquete registrado como usado.");
        } else {
            System.out.println("No se encontró un tiquete con ese ID.");
        }
    }
}
