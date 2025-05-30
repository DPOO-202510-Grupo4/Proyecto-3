package Persistencias;

import restricciones.Temporada;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Persona.GestorPersonas;
import Tiquetes.GestorTiquetes;

public class PersistenciaTemporada {

    private static final String NOMBREARCHIVO = "persistencia/tiquetes/temporadas.csv";
    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

    public static void crearArchivo() {
        try {
            Files.createDirectories(Paths.get("persistencia/tiquetes"));
            File archivo = new File(NOMBREARCHIVO);
            if (!archivo.exists()) {
                archivo.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                    writer.write("nombre,fechaInicio,fechaFinal");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + NOMBREARCHIVO + " " + e.getMessage());
        }
    }

    public static void persistencia(Temporada temporada) {
        crearArchivo();
        guardarTemporada(temporada);
    }

    public static void guardarTemporada(Temporada temporada) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {
            String lineaCSV = temporada.getName() + "," +
                              formatoFecha.format(temporada.getFechaInicio()) + "," +
                              formatoFecha.format(temporada.getFechaFinal());
            writer.write(lineaCSV);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("No se pudo guardar la temporada: " + e.getMessage());
        }
    }

    public static void cargarDatos() {
        GestorTiquetes gestor = GestorTiquetes.getInstancia();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBREARCHIVO))) {
            String linea;

            lector.readLine();

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");

                if (partes.length == 3) {
                    String nombre = partes[0].trim();
                    String fechaInicioString = partes[1].trim();
                    String fechaFinalString = partes[2].trim();

                    Date fechaInicio = convertirFecha(fechaInicioString);
                    Date fechaFinal = convertirFecha(fechaFinalString);

                    if (fechaInicio != null && fechaFinal != null) {
                    	
                        gestor.cargarTemporada(fechaInicio, fechaFinal, nombre);
                    } else {
                        System.out.println("Error: Fecha inválida en la línea: " + linea);
                    }
                } else {
                    System.out.println("Error: Línea con formato incorrecto en el archivo de temporadas: " + linea);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar las temporadas: " + e.getMessage());
        }
    }

    private static Date convertirFecha(String fechaStr) {
        try {
            return formatoFecha.parse(fechaStr);
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + fechaStr);
            return null;
        }
    }
}
