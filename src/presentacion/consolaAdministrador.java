package presentacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

import Atracciones.Atraccion;
import Atracciones.AtraccionCultural;
import Atracciones.AtraccionMecanica;
import Atracciones.Espectaculo;
import Atracciones.GestorAtracciones;
import Persona.GestorPersonas;
import Persona.Rol;
import Persona.Turno;
import Tiquetes.CategoriaTiquete;
import Tiquetes.GestorTiquetes;
import restricciones.RestriccionesCultural;
import restricciones.RestriccionesMecanica;

public class consolaAdministrador {
    private static final SimpleDateFormat formatoHoraCompleta = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    public static void menuAdministrador(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ADMINISTRADOR ---");
            System.out.println("1. Gestión de empleados");
            System.out.println("2. Gestión de atracciones");
            System.out.println("3. Gestión de tiquetes y temporadas");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion == 1) {
                menuEmpleados(scanner);
            } else if (opcion == 2) {
                menuAtracciones(scanner);
            } else if (opcion == 3) {
                menuTiquetesTemporadas(scanner);
            } else if (opcion == 4) {
                salir = true;
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    // ------------------------ SUBMENÚ EMPLEADOS ------------------------

    private static void menuEmpleados(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE EMPLEADOS ---");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Eliminar empleado");
            System.out.println("3. Consultar empleados");
            System.out.println("4. Asignar turno");
            System.out.println("5. Asignar tarea");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion == 1) {
                registrarEmpleado();
            } else if (opcion == 2) {
                eliminarEmpleado();
            } else if (opcion == 3) {
                consultarEmpleados();
            } else if (opcion == 4) {
                //asignarTurno();
            } else if (opcion == 5) {
                //asignarTarea();
            } else if (opcion == 6) {
                volver = true;
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    // ------------------------ SUBMENÚ ATRACCIONES ------------------------

    private static void menuAtracciones(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE ATRACCIONES ---");
            System.out.println("1. Registrar atracción");
            System.out.println("2. Modificar atracción");
            System.out.println("3. Modificar restricciones");
            System.out.println("4. Ver atracciones");
            System.out.println("5. Volver");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion == 1) {
                registrarAtraccion();
            } else if (opcion == 2) {
                modificarAtraccion();
            } else if (opcion == 3) {
                modificarRestricciones();
            } else if (opcion == 4) {
                verAtracciones();
            } else if (opcion == 5) {
                volver = true;
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    // ------------------------ SUBMENÚ TIQUETES Y TEMPORADAS ------------------------

    private static void menuTiquetesTemporadas(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE TIQUETES Y TEMPORADAS ---");
            System.out.println("1. Crear categoría de tiquete");
            System.out.println("2. Consultar categorías de tiquete");
            System.out.println("3. Crear temporada");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion == 1) {
                crearCategoriaTiquete();
            } else if (opcion == 2) {
                consultarCategoriasTiquete();
            } else if (opcion == 3) {
                crearTemporada();
            } else if (opcion == 4) {
                volver = true;
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }
    
    // ------------------------ METODOS ------------------------
    
    private static void registrarEmpleado() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- REGISTRO DE EMPLEADO ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        String fechaNacimiento = scanner.nextLine();

        GestorPersonas gestor = GestorPersonas.getInstance();

        gestor.crearEmpleadoBasico(nombre, login, password, fechaNacimiento);

        System.out.println("Empleado registrado exitosamente.");
    }

    private static void eliminarEmpleado() {
    	Scanner scanner = new Scanner(System.in);

        System.out.println("--- ELIMINACION DE EMPLEADO ---");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        GestorPersonas gestor = GestorPersonas.getInstance();
        gestor.eliminarEmpleado(login);
    }

    private static void consultarEmpleados() {
    	 System.out.println("--- CONSULTAR EMPLEADOS ---");
    	 GestorPersonas gestor = GestorPersonas.getInstance();
    	 //TODO Corregir funcion getEmpleados para imprimir correctamente la lista
    	 System.out.println(gestor.getEmpleados());
    }

   /* private static void asignarTurno() {
    	Scanner scanner = new Scanner(System.in);
    	Boolean turnoApertura = false;
    	Boolean turnoCierre = false;
    	System.out.println("--- Asignar Turno ---");
    	
    	System.out.print("Login: ");
        String login = scanner.nextLine();
        
        System.out.print("Fecha (YYYY-MM-DD): ");
        String inputFecha = scanner.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
		try {
			fecha = sdf.parse(inputFecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.print("Turno Apertura: y/n ");
        String turnoAp = scanner.nextLine();
        System.out.print("Turno Cierre: y/n ");
        String turnoCie = scanner.nextLine();
        if (turnoAp.equalsIgnoreCase("y")) {
        	turnoApertura = true;
        }
        if (turnoCie.equalsIgnoreCase("y")) {
        	 turnoCierre = true;
        }
        GestorPersonas gestor = GestorPersonas.getInstance();
        
        Turno turno = new Turno(fecha, turnoApertura, turnoCierre, login);
        gestor.asignarTurno(login, turno);
    }

    private static void asignarTarea() {
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("--- Asignar TAREA ---");
    	
    	System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Tarea: ");
        String tarea = scanner.nextLine();
        GestorPersonas gestor = GestorPersonas.getInstance();
        
        gestor.asignarTarea(login, tarea);
        
    }*/

    private static void registrarAtraccion() {
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("--- Asignar TAREA ---");
    	System.out.println("1. Registar Atracción Mecánica: ");
    	System.out.println("2. Registar Atracción Cultural: ");
    	System.out.println("3. Registar Espectáculo: ");
    	
    	 int opcion = Integer.parseInt(scanner.nextLine());

         switch (opcion) {
             case 1:
            	 registrarAtraccionMecanica();
                 break;
             case 2:
            	 registrarAtraccionCultural();
                 break;
             case 3:
            	 registrarEspectaculo();
                 break;
         }
    }

    

    public static void registrarEspectaculo() {
    	GestorAtracciones gestorAtracciones = GestorAtracciones.getInstancia();
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- CREAR ESPECTÁCULO ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Empresa encargada: ");
        String empresa = scanner.nextLine();

        System.out.print("Fecha (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine();

        System.out.print("Hora de inicio (HH:mm): ");
        String horaInicioStr = scanner.nextLine();

        System.out.print("Hora de finalización (HH:mm): ");
        String horaFinStr = scanner.nextLine();

        try {
            Date fecha = formatoFecha.parse(fechaStr);
            Date horaInicio = formatoHoraCompleta.parse(fechaStr + " " + horaInicioStr);
            Date horaFin = formatoHoraCompleta.parse(fechaStr + " " + horaFinStr);

            
            gestorAtracciones.crearEspectaculo(fecha, horaInicio, horaFin, empresa, nombre);

            System.out.println("Espectáculo registrado correctamente.");
        } catch (ParseException e) {
            System.out.println("Error al interpretar la fecha u hora. Asegúrate de usar el formato correcto.");
        }
    }
    private static void registrarAtraccionCultural() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("--- CREAR ATRACCIÓN CULTURAL ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ubicación: ");
        String ubicacion = scanner.nextLine();

        System.out.print("¿Es de temporada? (y/n): ");
        boolean deTemporada = scanner.nextLine().equalsIgnoreCase("y");

        System.out.print("¿Está disponible? (y/n): ");
        boolean disponible = scanner.nextLine().equalsIgnoreCase("y");

        System.out.print("Capacidad máxima: ");
        int capacidad = Integer.parseInt(scanner.nextLine());

        System.out.print("Mínimo de empleados: ");
        int minEmpleados = Integer.parseInt(scanner.nextLine());

        System.out.print("Temporada: ");
        String temporadaIn = scanner.nextLine();

        // --- RESTRICCIONES ---
        ArrayList<String> clima = new ArrayList<>();

        System.out.print("Exclusividad: ");
        String exclusividad = scanner.nextLine();

        System.out.print("Edad mínima: ");
        int edadMin = Integer.parseInt(scanner.nextLine());

        System.out.print("¿Desea agregar condiciones climáticas? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            String condicion;
            do {
                System.out.print("Condición climática (escriba 'fin' para terminar): ");
                condicion = scanner.nextLine();
                if (!condicion.equalsIgnoreCase("fin")) {
                    clima.add(condicion);
                }
            } while (!condicion.equalsIgnoreCase("fin"));
        }

 
        RestriccionesCultural restricciones = new RestriccionesCultural(clima, exclusividad, edadMin);

        GestorAtracciones gestor = GestorAtracciones.getInstancia();
        gestor.crearAtraccionCultural(ubicacion, nombre, deTemporada, capacidad, minEmpleados, restricciones, temporadaIn);

        System.out.println("Atracción cultural registrada con éxito.");
    }

    private static void registrarAtraccionMecanica() {
        Scanner scanner = new Scanner(System.in);
        Boolean deTemporada = false;
        Boolean disponible = false;
        
        System.out.println("--- CREAR ATRACCIÓN MECÁNICA ---");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ubicación: ");
        String ubicacion = scanner.nextLine();

        System.out.print("Riesgo: ");
        String riesgo = scanner.nextLine();

        System.out.print("¿Es de temporada? (y/n): ");
        String deTemp = scanner.nextLine();
        if (deTemp.equalsIgnoreCase("y")) {
            deTemporada = true;
        }

        System.out.print("¿Está disponible? (y/n): ");
        String disp = scanner.nextLine();
        if (disp.equalsIgnoreCase("y")) {
            disponible = true;
        }

        System.out.print("Temporada: ");
        String temporada = scanner.nextLine();

        System.out.print("Capacidad máxima: ");
        int cupoMax = Integer.parseInt(scanner.nextLine());

        System.out.print("Mínimo de empleados: ");
        int minEmpleados = Integer.parseInt(scanner.nextLine());

        // --- RESTRICCIONES ---
        ArrayList<String> salud = new ArrayList<>();
        ArrayList<String> clima = new ArrayList<>();
        
        System.out.print("Exclusividad: ");
        String exclusividad = scanner.nextLine();
        
        System.out.print("Altura máxima: ");
        Double alturaMax = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Altura mínima: ");
        Double alturaMin = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Peso máximo: ");
        Double pesoMax = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Peso mínimo: ");
        Double pesoMin = Double.parseDouble(scanner.nextLine());
        
        RestriccionesMecanica restricciones = new RestriccionesMecanica(clima, exclusividad, alturaMin, alturaMax, pesoMin, pesoMax, salud);

        GestorAtracciones gestor = GestorAtracciones.getInstancia();
        gestor.crearAtraccionMecanica(ubicacion, nombre, deTemporada, disponible, cupoMax, minEmpleados, riesgo, restricciones, temporada);

        System.out.println("Atracción mecánica registrada con éxito.");
    }

	private static void modificarAtraccion() {
        System.out.println("Función para modificar atracción (a implementar)");
    }

    private static void modificarRestricciones() {
        System.out.println("Función para modificar restricciones (a implementar)");
    }

    private static void verAtracciones() {
        System.out.println("Función para ver atracciones (a implementar)");
    }

    private static void crearCategoriaTiquete() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre de la categoría de tiquete:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el precio base de la categoría de tiquete:");
        double precioBase = Double.parseDouble(scanner.nextLine());

        GestorAtracciones gestorAtracciones = GestorAtracciones.getInstancia();
        ArrayList<String> atraccionesSeleccionadas = new ArrayList<>();

        ArrayList<AtraccionMecanica> mecanicas = gestorAtracciones.getAtraccionesMecanicas();
        if (!mecanicas.isEmpty()) {
            System.out.println("Seleccione las atracciones mecánicas (separe los números por coma):");
            for (int i = 0; i < mecanicas.size(); i++) {
                System.out.println((i + 1) + ". " + mecanicas.get(i).getNombre());
            }
            String[] indicesMecanicas = scanner.nextLine().split(",");
            for (String indice : indicesMecanicas) {
                try {
                    int idx = Integer.parseInt(indice.trim()) - 1;
                    if (idx >= 0 && idx < mecanicas.size()) {
                        atraccionesSeleccionadas.add(mecanicas.get(idx).getNombre());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida: " + indice);
                }
            }
        }

        ArrayList<AtraccionCultural> culturales = gestorAtracciones.getAtraccionesCulturales();
        if (!culturales.isEmpty()) {
            System.out.println("Seleccione las atracciones culturales (separe los números por coma):");
            for (int i = 0; i < culturales.size(); i++) {
                System.out.println((i + 1) + ". " + culturales.get(i).getNombre());
            }
            String[] indicesCulturales = scanner.nextLine().split(",");
            for (String indice : indicesCulturales) {
                try {
                    int idx = Integer.parseInt(indice.trim()) - 1;
                    if (idx >= 0 && idx < culturales.size()) {
                        atraccionesSeleccionadas.add(culturales.get(idx).getNombre());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida: " + indice);
                }
            }
        }

        GestorTiquetes.getInstancia().crearCategoriaTiquete(nombre, atraccionesSeleccionadas, precioBase);

        System.out.println("Categoría de tiquete creada exitosamente.");
    }

    private static void consultarCategoriasTiquete() {
        try {
  
            GestorTiquetes gestor = GestorTiquetes.getInstancia();

            ArrayList<CategoriaTiquete> categorias = gestor.getCategoriasDisponibles();

            if (categorias == null || categorias.isEmpty()) {
                System.out.println("No hay categorías disponibles en este momento.");
            } else {
            	
                for (CategoriaTiquete categoria : categorias) {
                    System.out.println(categoria.getNombre());
            }
            }
            }catch (Exception e) {

            System.err.println("Ocurrió un error al consultar las categorías de tiquetes: " + e.getMessage());
        }
    }

    private static void crearTemporada() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            System.out.print("Nombre de la temporada: ");
            String nombre = scanner.nextLine();

            System.out.print("Fecha de inicio (dd/MM/yyyy): ");
            String fechaInicioStr = scanner.nextLine();
            Date fechaInicio = sdf.parse(fechaInicioStr);

            System.out.print("Fecha de finalización (dd/MM/yyyy): ");
            String fechaFinalStr = scanner.nextLine();
            Date fechaFinal = sdf.parse(fechaFinalStr);

            GestorTiquetes gestor = GestorTiquetes.getInstancia();
            gestor.crearTemporada(fechaInicio, fechaFinal, nombre);

        } catch (ParseException e) {
            System.out.println("Formato de fecha inválido. Usa dd/MM/yyyy.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
