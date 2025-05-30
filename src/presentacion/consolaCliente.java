package presentacion;

import Atracciones.AtraccionCultural;
import Atracciones.AtraccionMecanica;
import Atracciones.Espectaculo;
import Atracciones.GestorAtracciones;
import Persona.Cliente;
import Persona.GestorPersonas;
import Tiquetes.CategoriaTiquete;
import Tiquetes.Factura;
import Tiquetes.FastPass;
import Tiquetes.GestorTiquetes;
import Tiquetes.Tiquete;
import restricciones.Temporada;
import Atracciones.AtraccionCultural;
import Atracciones.AtraccionMecanica;
import Atracciones.Espectaculo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import restricciones.Temporada;

public class consolaCliente {

    public static void menuCliente(Scanner scanner) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("1. Comprar tiquete");
            System.out.println("2. Consultar tiquetes");
            System.out.println("3. Consultar espectáculos");
            System.out.println("4. Consultar atracciones");
            System.out.println("5. Volver al menú principal");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                	comprarTiquete(scanner);
                    break;
                case 2:
                	consultarTiquetes(scanner);
                    break;
                case 3:
                	consultarEspectaculos();
                    break;
                case 4:
                	consultarAtracciones();
                    break;
                case 5:
                	volver = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    static void comprarTiquete(Scanner scanner) {
        System.out.println("¿Desea un tiquete regular (1) o de temporada (2)?");
        int opcion = Integer.parseInt(scanner.nextLine());

        GestorPersonas gestorPersonas = GestorPersonas.getInstance();
        GestorTiquetes gestorTiquetes = GestorTiquetes.getInstancia();

        System.out.println("Ingrese el login del cliente:");
        String login = scanner.nextLine();

        Cliente cliente = gestorPersonas.buscarCliente(login);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        ArrayList<CategoriaTiquete> categorias = gestorTiquetes.getCategoriasDisponibles();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías disponibles.");
            return;
        }

        System.out.println("Escoja una categoría:");
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println((i + 1) + ". " + categorias.get(i).getNombre());
        }

        int indiceCategoria = Integer.parseInt(scanner.nextLine()) - 1;
        if (indiceCategoria < 0 || indiceCategoria >= categorias.size()) {
            System.out.println("Categoría inválida.");
            return;
        }

        String nombreCategoria = categorias.get(indiceCategoria).getNombre();

        if (opcion == 1) {
            System.out.println("Ingrese la fecha del tiquete (formato: dd/MM/yyyy):");
            String fechaTexto = scanner.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                Date fecha = sdf.parse(fechaTexto);
                gestorTiquetes.crearTiqueteRegular(cliente, nombreCategoria, fecha);
                System.out.println("¡Tiquete regular creado con éxito!");
            } catch (Exception e) {
                System.out.println("Formato de fecha inválido.");
            }
        } else if (opcion == 2) {
            ArrayList<Temporada> temporadas = gestorTiquetes.getTemporadas();
            if (temporadas.isEmpty()) {
                System.out.println("No hay temporadas disponibles.");
                return;
            }

            System.out.println("Escoja una temporada:");
            for (int i = 0; i < temporadas.size(); i++) {
                System.out.println((i + 1) + ". " + temporadas.get(i).getName());
            }

            int indiceTemporada = Integer.parseInt(scanner.nextLine()) - 1;
            if (indiceTemporada < 0 || indiceTemporada >= temporadas.size()) {
                System.out.println("Temporada inválida.");
                return;
            }

            Temporada temporada = temporadas.get(indiceTemporada);
            gestorTiquetes.crearTiqueteTemporada(cliente, nombreCategoria, temporada);
            System.out.println("¡Tiquete de temporada creado con éxito!");
        } else {
            System.out.println("Opción inválida.");
        }
    }

   private static void consultarTiquetes(Scanner scanner) {
        System.out.print("Ingrese el login del cliente: ");
        String login = scanner.nextLine();

        GestorPersonas gestorPersonas = GestorPersonas.getInstance();
        Cliente cliente = gestorPersonas.buscarCliente(login);

        if (cliente == null) {
            System.out.println("Cliente no encontrado. Verifique el login e intente nuevamente.");
            return;
        }

        System.out.println("\n--- TIQUETES DE " + cliente.getNombre() + " ---");
        
        ArrayList<Tiquete> tiquetes = cliente.getTiquetes();
        if (tiquetes.isEmpty()) {
            System.out.println("No tiene tiquetes normales registrados.");
        } else {
            System.out.println("Tiquetes normales:");
            for (int i = 0; i < tiquetes.size(); i++) {
                Tiquete t = tiquetes.get(i);
                System.out.println((i + 1) + ". ID: " + t.getId() +
                                   ", Categoría: " + t.getCategoria().getNombre());
            }
        }

        ArrayList<FastPass> fastPasses = cliente.getFastPass();
        if (fastPasses.isEmpty()) {
            System.out.println("No tiene FastPass registrados.");
        } else {
            System.out.println("\nFastPass:");
            for (int i = 0; i < fastPasses.size(); i++) {
                FastPass f = fastPasses.get(i);
                System.out.println((i + 1) + ". ID: " + f.getIdTiquete() +
                                   ", Fecha: " + f.getFecha());
            }
        }
    }

    private static void consultarAtracciones() {
        System.out.println("\n--- TODAS LAS ATRACCIONES DEL PARQUE ---");

        GestorAtracciones gestor = GestorAtracciones.getInstancia();
        ArrayList<AtraccionMecanica> atraccionesMecanicas = gestor.getAtraccionesMecanicas();
        ArrayList<AtraccionCultural> atraccionesCulturales = gestor.getAtraccionesCulturales();

        int contador = 1;

        if (atraccionesMecanicas.isEmpty() && atraccionesCulturales.isEmpty()) {
            System.out.println("No hay atracciones registradas en el sistema.");
        } else {
            for (int i = 0; i < atraccionesMecanicas.size(); i++) {
                System.out.println(contador + ". " + atraccionesMecanicas.get(i).toString());
                contador++;
            }

            for (int i = 0; i < atraccionesCulturales.size(); i++) {
                System.out.println(contador + ". " + atraccionesCulturales.get(i).toString());
                contador++;
            }
        }
    }
    private static void consultarEspectaculos() {
        GestorAtracciones gestorAtracciones = GestorAtracciones.getInstancia();
        ArrayList<Espectaculo> espectaculos = gestorAtracciones.obtenerEspectaculos();
        if (espectaculos.isEmpty()) {
            System.out.println("No hay espectáculos registrados.");
        } else {
            System.out.println("--- LISTA DE ESPECTÁCULOS ---");
            for (Espectaculo espectaculo : espectaculos) {
                System.out.println("Nombre del Espectáculo: " + espectaculo.getNombre());
            }
        }
    }
}
