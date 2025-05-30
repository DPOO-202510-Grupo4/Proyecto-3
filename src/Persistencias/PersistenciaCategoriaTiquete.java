package Persistencias;

import Tiquetes.CategoriaTiquete;
import Tiquetes.GestorTiquetes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PersistenciaCategoriaTiquete {

    private static final String NOMBREARCHIVO = "persistencia/tiquetes/categorias.csv";

    public static void crearArchivo() {
        try {
            Files.createDirectories(Paths.get("persistencia/tiquetes"));
            File archivo = new File(NOMBREARCHIVO);
            if (!archivo.exists()) {
                archivo.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                    writer.write("nombre,precioBase,atracciones");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + NOMBREARCHIVO + " " + e.getMessage());
        }
    }

    public static void persistencia(CategoriaTiquete categoria) {
        crearArchivo();
        guardarCategoria(categoria);
    }

    public static void guardarCategoria(CategoriaTiquete categoria) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {
            StringBuilder atraccionesBuilder = new StringBuilder();
            ArrayList<String> atracciones = categoria.getAtraccionesDisponibles();

            for (int i = 0; i < atracciones.size(); i++) {
                atraccionesBuilder.append(atracciones.get(i));
                if (i < atracciones.size() - 1) {
                    atraccionesBuilder.append(";");
                }
            }

            String lineaCSV = categoria.getNombre() + "," +
                              categoria.getPrecioBase() + "," +
                              atraccionesBuilder.toString();

            writer.write(lineaCSV);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("No se pudo guardar la categoría de tiquete: " + e.getMessage());
        }
    }

    public static void cargarDatos() {
        GestorTiquetes gestor = GestorTiquetes.getInstance();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBREARCHIVO))) {
            String linea;
            lector.readLine();

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",", 3);
                if (partes.length == 3) {
                    String nombre = partes[0].trim();
                    Double precioBase = Double.parseDouble(partes[1].trim());
                    String[] atraccionesArray = partes[2].trim().split(";");
                    ArrayList<String> atracciones = new ArrayList<>();

                    for (int i = 0; i < atraccionesArray.length; i++) {
                        atracciones.add(atraccionesArray[i].trim());
                    }

                    gestor.cargarCategoriaTiquete(nombre, atracciones, precioBase);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar las categorías de tiquete: " + e.getMessage());
        }
    }
}
