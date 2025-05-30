package Persistencias;

import Atracciones.Espectaculo;
import Atracciones.GestorAtracciones;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import Persona.GestorPersonas;
import Tiquetes.GestorTiquetes;

public class PersistenciaEspectaculo {

    private static final String NOMBREARCHIVO = "persistencia/atracciones/espectaculos.csv";
    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

    public static void crearArchivo(String nombreArchivo) {
        try {
            Files.createDirectories(Paths.get("persistencia"));
            File archivo = new File(nombreArchivo);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + nombreArchivo + " " + e.getMessage());
        }
    }

    public static void persistencia(Espectaculo persistirEspectaculo) {
        crearArchivo(NOMBREARCHIVO);
        guardarEspectaculo(persistirEspectaculo);
    }

    public static void guardarEspectaculo(Espectaculo espectaculo) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {
            String espectaculoFormatoTexto =
                espectaculo.getNombre() + "," +
                espectaculo.getEmpresaEncargada() + "," +
                formatoFecha.format(espectaculo.getFecha()) + "," +
                formatoHora.format(espectaculo.getHoraInicio()) + "," +
                formatoHora.format(espectaculo.getHoraFinalizacion());

            escritor.write(espectaculoFormatoTexto);
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("No se pudo guardar el espectáculo: " + e.getMessage());
        }
    }

    public static void cargarDatos() {
        GestorAtracciones gestor = GestorAtracciones.getInstancia();
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBREARCHIVO))) {
            String linea;

            lector.readLine();

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");

                if (partes.length == 5) {
                    String nombre = partes[0].trim();
                    String empresaEncargada = partes[1].trim();
                    String fechaStr = partes[2].trim();
                    String horaInicioStr = partes[3].trim();
                    String horaFinalizacionStr = partes[4].trim();

                    Date fecha = convertirFecha(fechaStr);
                    Date horaInicio = convertirHora(horaInicioStr);
                    Date horaFinalizacion = convertirHora(horaFinalizacionStr);

                    if (fecha != null && horaInicio != null && horaFinalizacion != null) {

                        gestor.cargarEspectaculo(fecha, horaInicio, horaFinalizacion,empresaEncargada,nombre);
                    } else {
                        System.out.println("Error: Fecha o hora inválida en la línea: " + linea);
                    }
                } else {
                    System.out.println("Error: Línea con formato incorrecto en el archivo de espectáculos: " + linea);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar los espectáculos: " + e.getMessage());
        }
    }

    private static Date convertirFecha(String fechaStr) {
        try {
            return formatoFecha.parse(fechaStr);
        } catch (Exception e) {
            System.out.println("Error al parsear la fecha: " + fechaStr);
            return null;
        }
    }

    private static Date convertirHora(String horaStr) {
        try {
            return formatoHora.parse(horaStr);
        } catch (Exception e) {
            System.out.println("Error al parsear la hora: " + horaStr);
            return null;
        }
    }
}
