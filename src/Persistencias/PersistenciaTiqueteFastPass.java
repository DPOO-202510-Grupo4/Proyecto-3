package Persistencias;

import Tiquetes.FastPass;
import Tiquetes.GestorTiquetes;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersistenciaTiqueteFastPass {

    private static final String NOMBREARCHIVO = "persistencia/tiquetes/tiquetes_fastpass.csv";
    private static final String FORMATO_FECHA = "yyyy-MM-dd"; 
    
    public static void crearArchivo(String nombreArchivo) {
        try {
            Files.createDirectories(Paths.get("persistencia/tiquetes"));
            File archivo = new File(nombreArchivo);
            if (!archivo.exists()) {
                archivo.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {

                    writer.write("idTiquete, nombre, precioBase, fecha, usado, dueño");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + nombreArchivo + " " + e.getMessage());
        }
    }

    public static void persistencia(FastPass fastPass) {
        crearArchivo(NOMBREARCHIVO);
        guardarTiqueteFastPass(fastPass);
    }

    public static void guardarTiqueteFastPass(FastPass fastPass) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {

            String linea = fastPass.getIdTiquete() + "," +
                           fastPass.getNombre() + "," +
                           fastPass.getPrecioBase() + "," +
                           sdf.format(fastPass.getFecha()) + "," +  
                           fastPass.getDueño();
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("No se pudo guardar el tiquete FastPass: " + e.getMessage());
        }
    }

    public static void cargarDatos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(NOMBREARCHIVO))) {
            GestorTiquetes gestorT = GestorTiquetes.getInstance();
            String linea;
            reader.readLine(); 
            
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");

                if (partes.length == 6) {
                    String idTiquete = partes[0].trim();
                    String nombre = partes[1].trim();
                    Double precioBase = Double.parseDouble(partes[2].trim());

                    SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
                    Date fecha = sdf.parse(partes[3].trim()); 

                    boolean usado = Boolean.parseBoolean(partes[4].trim());
                    String dueño = partes[5].trim();

                    gestorT.cargarFastPass(nombre, precioBase, fecha, usado, idTiquete, dueño);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar los tiquetes FastPass: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al convertir la fecha: " + e.getMessage());
        }
    }
}
