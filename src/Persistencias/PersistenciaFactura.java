package Persistencias;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import Tiquetes.Factura;
import Tiquetes.ItemVenta;


public class PersistenciaFactura {

    private static final String NOMBREARCHIVO = "persistencia/factura.txt";

    public void crearArchivo(String nombreArchivo) {
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

    public void persistencia(String nombre, Factura persistirFactura) {
        crearArchivo(nombre);
        guardarFactura(persistirFactura);
    }

    public void guardarFactura(Factura factura) {
        try (BufferedWriter facturaEscrita = new BufferedWriter(new FileWriter(NOMBREARCHIVO, true))) {

            String itemsTexto = "";
            for (int i = 0; i < factura.getItems().size(); i++) {
                ItemVenta item = factura.getItems().get(i);
                itemsTexto += item.getNombre() + ";" + item.getPrecioBase();
                if (i < factura.getItems().size() - 1) {
                    itemsTexto += "|";
                }
            }

            String facturaFormatoTexto = factura.getCliente().getLogin() + "," 
                + factura.getFecha().getTime() + ","                            
                + itemsTexto + ","                                              
                + factura.getCosto();

            facturaEscrita.write(facturaFormatoTexto);
            facturaEscrita.newLine();

        } catch (IOException e) {
            System.err.println("No se pudo guardar la factura: " + e.getMessage());
        }
    }



}
