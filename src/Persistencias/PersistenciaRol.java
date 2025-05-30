package Persistencias;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import Persona.Rol;

public class PersistenciaRol {

    private static final String NOMBREARCHIVO = "persistencia/personas/roles.csv"; 

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

    public static void persistencia(Rol persistirRol) {
        crearArchivo(); 
        guardarRol(persistirRol);
    }
    public static void guardarRol(Rol rol) {
        if (!(rol instanceof Rol)) {
            System.err.println("El objeto no es un rol, no se puede guardar.");
            return;
        }

        try (BufferedWriter rolEscrito = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {
            String permisosTexto = String.join(",", rol.getPermisosPermitidos());

            String rolFormatoTexto = String.format(
                "%s,%s",
                rol.getNombre(),
                permisosTexto
            );

            rolEscrito.write(rolFormatoTexto);
            rolEscrito.newLine();
        } catch (IOException e) {
            System.err.println("No se pudo guardar el rol: " + e.getMessage());
        }
    }

    public static void cargarRoles() {
        try {
            Files.lines(Paths.get(NOMBREARCHIVO))
                .forEach(linea -> {
                    String[] campos = linea.split(",");
                    if (campos.length >= 2) {
                        String nombreRol = campos[0];
                        Set<String> permisos = Set.of(campos[1].split(",")); 
                        Rol rol = new Rol(nombreRol, permisos);
                        System.out.println(rol); 
                    }
                });
        } catch (IOException e) {
            System.err.println("No se pudo cargar los roles: " + e.getMessage());
        }
    }
}
