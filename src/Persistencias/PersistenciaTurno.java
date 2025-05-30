package Persistencias;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import Persona.Turno;

public class PersistenciaTurno {

    private static final String NOMBREARCHIVO = "persistencia/personas/turnos.csv"; 
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

    public static void persistencia(Turno persistirTurno, String loginEmpleado) {
        crearArchivo(); 
        guardarTurno(persistirTurno, loginEmpleado);
    }

    public static void guardarTurno(Turno turno, String loginEmpleado) {
        if (!(turno instanceof Turno)) {
            System.err.println("El objeto no es un turno, no se puede guardar.");
            return;
        }

        try (BufferedWriter turnoEscrito = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = sdf.format(turno.getFecha());

            String turnoFormatoTexto = String.format(
                "%s,%b,%b,%s,%s",
                fechaFormateada,
                turno.getTurnoApertura(),
                turno.getTurnoCierre(),
                turno.getLugarTrabajo(),
                loginEmpleado
            );

            turnoEscrito.write(turnoFormatoTexto);
            turnoEscrito.newLine();
        } catch (IOException e) {
            System.err.println("No se pudo guardar el turno: " + e.getMessage());
        }
    }

    public static void cargarTurnos() {
        try {
            Files.lines(Paths.get(NOMBREARCHIVO))
                .forEach(linea -> {
                    String[] campos = linea.split(",");
                    if (campos.length >= 5) {

                        String fechaStr = campos[0];
                        Boolean turnoApertura = Boolean.parseBoolean(campos[1]);
                        Boolean turnoCierre = Boolean.parseBoolean(campos[2]);
                        String lugarTrabajo = campos[3];
                        String loginEmpleado = campos[4];

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date fecha = null;
                        try {
                            fecha = sdf.parse(fechaStr);
                        } catch (Exception e) {
                            System.err.println("Error al parsear la fecha: " + e.getMessage());
                        }

                        Turno turno = new Turno(fecha, turnoApertura, turnoCierre, lugarTrabajo);
                        System.out.println(turno); 
                }});
        } catch (IOException e) {
            System.err.println("No se pudo cargar los turnos: " + e.getMessage());
        }
    }
}
