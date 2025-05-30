package Persistencias;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Persona.Cliente;
import Persona.GestorPersonas;
import Tiquetes.CategoriaTiquete;
import Tiquetes.GestorTiquetes;
import Tiquetes.TiqueteRegular;

public class PersistenciaTiqueteRegular {

    private static final String NOMBRE_ARCHIVO = "persistencia/tiquetes/tiquetes_regulares.csv";

    public static void crearArchivo(String nombreArchivo) {
        try {
            Files.createDirectories(Paths.get("persistencia/tiquetes"));
            File archivo = new File(nombreArchivo);
            if (!archivo.exists()) {
                archivo.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                    writer.write("idTiquete,categoria,usado,loginCliente,fecha");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + nombreArchivo + " " + e.getMessage());
        }
    }

    public static void persistencia(TiqueteRegular t) {
        crearArchivo(NOMBRE_ARCHIVO);
        guardarTiquete(t);
    }

    public static void guardarTiquete(TiqueteRegular t) {
        if (t == null) {
            System.err.println("El tiquete es nulo, no se puede guardar.");
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            String linea = t.getIdTiquete() + "," +
                           t.getCategoria().getNombre() + "," +
                           t.isUsado() + "," +
                           t.getDueño() + "," +
                           formatter.format(t.getFecha());
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar el tiquete: " + e.getMessage());
        }
    }

    public static void cargarDatos() throws ParseException {
        GestorTiquetes gestorT = GestorTiquetes.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            lector.readLine(); 

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");

                if (partes.length == 5) {
                    String idTiquete = partes[0].trim();
                    String nombreCategoria = partes[1].trim();
                    boolean usado = Boolean.parseBoolean(partes[2].trim());
                    String loginCliente = partes[3].trim();
                    Date fecha = formatter.parse(partes[4].trim());

                    gestorT.cargarTiqueteRegular(loginCliente, nombreCategoria, fecha, idTiquete, usado);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar los tiquetes: " + e.getMessage());
        }
    }
}
