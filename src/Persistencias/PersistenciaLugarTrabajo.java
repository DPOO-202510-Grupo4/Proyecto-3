package Persistencias;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import Persona.LugarTrabajo;
import Persona.Rol;

public class PersistenciaLugarTrabajo {

    private static final String NOMBREARCHIVO = "persistencia/personas/lugares_trabajo.csv";
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


    public static void persistencia(LugarTrabajo persistirLugar) {
        crearArchivo(); 
        guardarLugarTrabajo(persistirLugar);
    }

    public static void guardarLugarTrabajo(LugarTrabajo lugarTrabajo) {
        if (!(lugarTrabajo instanceof LugarTrabajo)) {
            System.err.println("El objeto no es un lugar de trabajo, no se puede guardar.");
            return;
        }

        try (BufferedWriter lugarTrabajoEscrito = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {
            String rolesNombres = String.join(",", obtenerNombresRoles(lugarTrabajo.getRolesPermitidos()));

            String lugarTrabajoFormatoTexto = String.format(
                "%s,%s,%s",
                lugarTrabajo.getNombre(),
                lugarTrabajo.getUbicacion(),
                rolesNombres
            );

            lugarTrabajoEscrito.write(lugarTrabajoFormatoTexto);
            lugarTrabajoEscrito.newLine();
        } catch (IOException e) {
            System.err.println("No se pudo guardar el lugar de trabajo: " + e.getMessage());
        }
    }

    private static Set<String> obtenerNombresRoles(Set<Rol> roles) {
        return roles.stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toSet()); 
    }

    /*public static void cargarLugaresTrabajo() {
        try {
            // Leemos el archivo línea por línea
            Files.lines(Paths.get(NOMBREARCHIVO))
                .forEach(linea -> {
                    
                    String[] campos = linea.split(",");
                    if (campos.length >= 3) {
                        
                        String nombreLugarTrabajo = campos[0];
                        String ubicacion = campos[1];
                        Set<String> rolesNombres = Set.of(campos[2].split(",")); 
                        LugarTrabajo lugarTrabajo = new LugarTrabajo(nombreLugarTrabajo, ubicacion, rolesNombres);
                        System.out.println(lugarTrabajo); 
                    }
                });
        } catch (IOException e) {
            System.err.println("No se pudo cargar los lugares de trabajo: " + e.getMessage());
        }
    }*/
}
