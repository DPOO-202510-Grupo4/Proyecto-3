package Persistencias;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import Persona.Administrador;
import Persona.GestorPersonas;

public class PersistenciaAdministrador {

    private static final String NOMBREARCHIVO = "persistencia/personas/administradores.csv";

    public static void crearArchivo() {
        try {
            Files.createDirectories(Paths.get("persistencia/personas"));
            File archivo = new File(NOMBREARCHIVO);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    public static void persistencia(Administrador persistirAdministrador) {
        crearArchivo(); 
        guardarAdministrador(persistirAdministrador);
    }

    public static void guardarAdministrador(Administrador administrador) {
        if (administrador == null) {
            System.err.println("Administrador nulo, no se puede guardar.");
            return;
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {
            String lineaCSV = String.format(
                "%s,%s,%s,%s",
                administrador.getNombre(),
                administrador.getLogin(),
                administrador.getPassword(),
                administrador.getFechaNacimiento()
            );
            escritor.write(lineaCSV);
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("No se pudo guardar el administrador: " + e.getMessage());
        }
    }
    public static void cargarDatos() {
    	GestorPersonas gestor = GestorPersonas.getInstance();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBREARCHIVO))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    String nombre = partes[0].trim();
                    String login = partes[1].trim();
                    String password = partes[2].trim();
                    String fechaNacimiento = partes[3].trim();
                    gestor.cargarAdministrador(nombre, login, password, fechaNacimiento);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar los administradores: " + e.getMessage());
        }

    }
}
