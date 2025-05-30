package Persistencias;

import Tiquetes.CategoriaTiquete;
import Tiquetes.GestorTiquetes;
import Tiquetes.TiqueteTemporada;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PersistenciaTiqueteTemporada {

    private static final String NOMBRE_ARCHIVO = "persistencia/tiquetes/tiquetes_temporada.csv";

    public static void crearArchivo(String nombreArchivo) {
        try {
            Files.createDirectories(Paths.get("persistencia/tiquetes"));
            File archivo = new File(nombreArchivo);
            if (!archivo.exists()) {
                archivo.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                    writer.write("idTiquete,categoria,usado,loginCliente,temporada");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + nombreArchivo + " " + e.getMessage());
        }
    }

    public static void persistencia(TiqueteTemporada t) {
        crearArchivo(NOMBRE_ARCHIVO);
        guardarTiquete(t);
    }

    public static void guardarTiquete(TiqueteTemporada t) {
        if (t == null) {
            System.err.println("El tiquete es nulo, no se puede guardar.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
 
            String linea = t.getIdTiquete() + "," +
                           t.getCategoria().getNombre() + "," +
                           t.isUsado() + "," +
                           t.getDueño() + "," +
                           t.getTemporada().getName();  
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar el tiquete: " + e.getMessage());
        }
    }

    public static void cargarDatos() {
        GestorTiquetes gestorT = GestorTiquetes.getInstance();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            lector.readLine();

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");

                if (partes.length == 5) {
                    String idTiquete = partes[0].trim();
                    String nombreCategoria = partes[1].trim();
                    //boolean usado = Boolean.parseBoolean(partes[2].trim());
                    String loginCliente = partes[3].trim();
                    String temporada = partes[4].trim();  
                    
                    gestorT.cargarTiqueteTemporada(loginCliente, nombreCategoria, temporada, idTiquete);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar los tiquetes de temporada: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Temporada inválida en el archivo CSV: " + e.getMessage());
        }
    }
}
