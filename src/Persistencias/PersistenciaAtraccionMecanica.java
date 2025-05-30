package Persistencias;

import Atracciones.AtraccionMecanica;
import Atracciones.GestorAtracciones;
import Persona.GestorPersonas;
import Tiquetes.GestorTiquetes;
import restricciones.Temporada;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PersistenciaAtraccionMecanica {

    private static final String NOMBREARCHIVO = "persistencia/atracciones/atracciones_mecanicas.csv";

    public static void crearArchivo(String nombreArchivo) {
        try {
            Files.createDirectories(Paths.get("persistencia/atracciones"));
            File archivo = new File(nombreArchivo);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + nombreArchivo + " " + e.getMessage());
        }
    }

    public static void persistencia(AtraccionMecanica atraccion) {
        crearArchivo(NOMBREARCHIVO);
        guardarAtraccion(atraccion);
    }

    public static void guardarAtraccion(AtraccionMecanica atraccion) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {

            StringBuilder empleadosStr = new StringBuilder();
            for (String login : atraccion.getEmpleadosAsignados()) {
                empleadosStr.append(login).append(";"); // Ya son logins
            }

            String linea = atraccion.getNombre() + "," +
                    atraccion.getUbicacion() + "," +
                    atraccion.getCupoMax() + "," +
                    atraccion.getMinEmpleados() + "," +
                    atraccion.isDeTemporada() + "," +
                    (atraccion.getTemporada() != null ? atraccion.getTemporada().getName() : "null") + "," +
                    atraccion.getRiesgo() + "," +
                    atraccion.estaDisponible() + "," +
                    empleadosStr.toString();

            escritor.write(linea);
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar atracción mecánica: " + e.getMessage());
        }
    }

    public static void cargarDatos() {
        GestorAtracciones gestor = GestorAtracciones.getInstancia();
        GestorTiquetes gestorT = GestorTiquetes.getInstancia();
        GestorPersonas gestorPersonas = GestorPersonas.getInstance();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBREARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");

                if (partes.length >= 9) {
                    String nombre = partes[0].trim();
                    String ubicacion = partes[1].trim();
                    int cupoMax = Integer.parseInt(partes[2].trim());
                    int minEmpleados = Integer.parseInt(partes[3].trim());
                    boolean deTemporada = Boolean.parseBoolean(partes[4].trim());
                    String temporadaNombre = partes[5].trim();
                    String riesgo = partes[6].trim();
                    boolean disponible = Boolean.parseBoolean(partes[7].trim());

                    String[] loginsEmpleados = partes[8].trim().split(";");

                    Temporada temporada = (deTemporada && !temporadaNombre.equals("null")) ?
                            gestorT.buscarTemporada(temporadaNombre) : null;

                    AtraccionMecanica atraccion = new AtraccionMecanica(
                            nombre, ubicacion, cupoMax, minEmpleados, deTemporada, temporada, riesgo, disponible
                    );

                    for (String login : loginsEmpleados) {
                        if (!login.isEmpty()) {
                            atraccion.getEmpleadosAsignados().add(login.trim());
                        }
                    }

                    gestor.cargarAtraccionMecanica(atraccion);
                } else {
                    System.out.println("Línea con formato inválido en atracciones mecánicas: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar atracciones mecánicas: " + e.getMessage());
        }
    }
}