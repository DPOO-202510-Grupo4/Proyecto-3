package Persistencias;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import Persona.GestorPersonas;
import Persona.Persona;

public class PersistenciaCliente {

    private static final String NOMBREARCHIVO = "persistencia/personas/clientes.csv";

    public static void crearArchivo() {
        try {
            Files.createDirectories(Paths.get("persistencia/personas"));
            File archivo = new File(NOMBREARCHIVO);
            if (!archivo.exists()) {
                archivo.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                    writer.write("nombre,login,password");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + NOMBREARCHIVO + " " + e.getMessage());
        }
    }

    public static void persistencia(Persona cliente) {
        crearArchivo();
        guardarCliente(cliente);
    }

    public static void guardarCliente(Persona cliente) {
        if (cliente == null) {
            System.err.println("Cliente nulo, no se puede guardar.");
            return;
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {
            String lineaCSV = String.format(
                "%s,%s,%s,%s",
                cliente.getNombre(),
                cliente.getLogin(),
                cliente.getPassword(),
                cliente.getFechaNacimiento()
            );
            escritor.write(lineaCSV);
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("No se pudo guardar el cliente: " + e.getMessage());
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
                    gestor.cargarCliente(nombre, login, password, fechaNacimiento);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar los clientes: " + e.getMessage());
        }

    }
}
