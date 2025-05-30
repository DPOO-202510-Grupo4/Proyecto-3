package Persistencias;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import Atracciones.AtraccionCultural;
import Atracciones.GestorAtracciones;
import Persona.Empleado;
import Persona.GestorPersonas;
import Tiquetes.GestorTiquetes;
import restricciones.Temporada;

public class PersistenciaAtraccionCultural {

    private static final String NOMBREARCHIVO = "persistencia/atracciones/atracciones_culturales.csv";

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

    public static void persistencia(AtraccionCultural atraccion) {
        crearArchivo(NOMBREARCHIVO);
        guardarAtraccion(atraccion);
    }

    public static void guardarAtraccion(AtraccionCultural atraccion) {
        try (BufferedWriter atraccionEscrita = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {

            String empleadosTexto = "";
            List<String> empleados = atraccion.getEmpleadosAsignados();
            for (int i = 0; i < empleados.size(); i++) {
                empleadosTexto += empleados.get(i); 
                if (i < empleados.size() - 1) {
                    empleadosTexto += ",";
                }
            }

            String temporadasTexto = "";
            List<Temporada> temporadas = atraccion.getDisponibilidad();
            for (int i = 0; i < temporadas.size(); i++) {
                temporadasTexto += temporadas.get(i).getName();
                if (i < temporadas.size() - 1) {
                    temporadasTexto += ",";
                }
            }

            String atraccionCulturalFormatoTexto =
                atraccion.getNombre() + "," +
                atraccion.getUbicacion() + "," +
                atraccion.getCupoMax() + "," +
                atraccion.getMinEmpleados() + "," +
                atraccion.isDeTemporada() + "," +
                empleadosTexto + "," +
                temporadasTexto;

            atraccionEscrita.write(atraccionCulturalFormatoTexto);
            atraccionEscrita.newLine();

        } catch (IOException e) {
            System.err.println("No se pudo guardar la atracción: " + e.getMessage());
        }
    }

    public static void cargarDatos() {
        GestorAtracciones gestor = GestorAtracciones.getInstancia();
        GestorPersonas gestorP = GestorPersonas.getInstance();
        GestorTiquetes gestorT = GestorTiquetes.getInstancia(); 

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBREARCHIVO))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 7) {  
                    String nombre = partes[0].trim();
                    String ubicacion = partes[1].trim();
                    int cupoMax = Integer.parseInt(partes[2].trim());
                    int minEmpleados = Integer.parseInt(partes[3].trim());
                    boolean deTemporada = Boolean.parseBoolean(partes[4].trim());

                    String[] loginsEmpleados = partes[5].trim().split(",");
                
                    String[] temporadas = partes[6].trim().split(",");

                    AtraccionCultural atraccion = new AtraccionCultural(
                            nombre, ubicacion, cupoMax, minEmpleados, deTemporada, null);

                    for (String login : loginsEmpleados) {
                        Empleado emp = gestorP.obtenerEmpleadoPorLogin(login.trim());
                        if (emp != null) {
                            atraccion.getEmpleadosAsignados().add(emp.getLogin());
                        }
                    }

                    for (String nombreTemporada : temporadas) {
                        Temporada temp = gestorT.buscarTemporada(nombreTemporada.trim());
                        if (temp != null) {
                            atraccion.getDisponibilidad().add(temp);
                        }
                    }

                    gestor.cargarAtraccionCultural(atraccion);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar las atracciones culturales: " + e.getMessage());
        }
    }
}
