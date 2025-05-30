package Persistencias;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import Atracciones.Atraccion;
import Persona.Capacitaciones;
import Persona.Empleado;
import Persona.GestorPersonas;

public class PersistenciaEmpleado {

    private static final String NOMBREARCHIVO = "persistencia/personas/empleados.csv";

    public static void crearArchivo(String nombreArchivo) {
        try {
            Files.createDirectories(Paths.get("persistencia/personas"));
            File archivo = new File(nombreArchivo);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + nombreArchivo + " " + e.getMessage());
        }
    }

    public static void persistencia(Empleado persistirPersona) {
        crearArchivo(NOMBREARCHIVO);
        guardarEmpleado(persistirPersona);
    }

    public static void guardarEmpleado(Empleado persona) {
        if (!(persona instanceof Empleado)) {
            System.err.println("El objeto no es un empleado, no se puede guardar.");
            return;
        }

        Empleado empleado = (Empleado) persona;

        try (BufferedWriter empleadoEscrito = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {

            String empleadoFormatoTexto = empleado.getNombre() + ","
                    + empleado.getLogin() + ","
                    + empleado.getPassword() + ","
                    + empleado.getFechaNacimiento(); 
            
            if (empleado.getLugarTrabajo() != null) {
                empleadoFormatoTexto += "," + empleado.getLugarTrabajo().getNombre();
            } else {
                empleadoFormatoTexto += ",N/A";
            }

            if (empleado.getRolActual() != null) {
                empleadoFormatoTexto += "," + empleado.getRolActual().getNombre();
            } else {
                empleadoFormatoTexto += ",N/A";
            }

            empleadoEscrito.write(empleadoFormatoTexto);
            empleadoEscrito.newLine();

        } catch (IOException e) {
            System.err.println("No se pudo guardar el empleado: " + e.getMessage());
        }
    }

    public static void cargarDatos() {
        GestorPersonas gestor = GestorPersonas.getInstance();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBREARCHIVO))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");

                if (partes.length >= 4) {
                    String nombre = partes[0].trim();
                    String login = partes[1].trim();
                    String password = partes[2].trim();
                    String fechaNacimiento = partes[3].trim();

                    String lugarTrabajoStr = "N/A";
                    String rolActualStr = "N/A"; 

                    if (partes.length >= 5) {
                        lugarTrabajoStr = partes[4].trim();
                    }
                    if (partes.length >= 6) {
                        rolActualStr = partes[5].trim();
                    }

                    gestor.cargarEmpleado(nombre, login, password, fechaNacimiento, lugarTrabajoStr, rolActualStr);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar los empleados: " + e.getMessage());
        }
    }
}
