package Interfaz;


import Atracciones.*;
import Persona.*;
import Tiquetes.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import restricciones.*;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class FCliente extends JFrame implements ActionListener {
    
    private Cliente cliente;

    public FCliente(Cliente cliente) {
        this.cliente = cliente;

        setTitle("Panel del Cliente");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 10, 10));

        JLabel lblBienvenida = new JLabel("Bienvenido, " + cliente.getNombre(), SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblBienvenida);

        JButton btnComprarTiquete = new JButton("Comprar Tiquete");
        JButton btnConsultarTiquetes = new JButton("Consultar Tiquetes");
        JButton btnConsultarEspectaculos = new JButton("Consultar Espectáculos");
        JButton btnConsultarAtracciones = new JButton("Consultar Atracciones");
        JButton btnImprimirTiquetes = new JButton("Imprimir Tiquetes");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        add(btnComprarTiquete);
        add(btnConsultarTiquetes);
        add(btnConsultarEspectaculos);
        add(btnConsultarAtracciones);
        add(btnImprimirTiquetes);
        add(btnCerrarSesion);

        btnComprarTiquete.addActionListener(e -> mostrarDialogoCompra());
        btnConsultarTiquetes.addActionListener(e -> mostrarTiquetes());
        btnConsultarEspectaculos.addActionListener(e -> mostrarEspectaculos());
        btnConsultarAtracciones.addActionListener(e -> mostrarAtracciones());
        btnImprimirTiquetes.addActionListener(e -> imprimirTiquetes());
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new FLogin();
        });

        setVisible(true);
    }

    private void mostrarDialogoCompra() {
        GestorTiquetes gestor = GestorTiquetes.getInstancia();
        ArrayList<CategoriaTiquete> categorias = gestor.getCategoriasDisponibles();

        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay categorías disponibles.");
            return;
        }

        String[] opciones = {"Regular", "Temporada"};
        int tipo = JOptionPane.showOptionDialog(this, "Tipo de tiquete:",
                "Compra", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);

        if (tipo == -1) return;

        String[] nombresCategorias = categorias.stream().map(CategoriaTiquete::getNombre).toArray(String[]::new);
        String categoria = (String) JOptionPane.showInputDialog(this, "Seleccione una categoría:",
                "Categoría", JOptionPane.QUESTION_MESSAGE, null, nombresCategorias, nombresCategorias[0]);

        if (categoria == null) return;

        if (tipo == 0) { 
            String fechaStr = JOptionPane.showInputDialog(this, "Ingrese fecha (dd/MM/yyyy):");
            if (fechaStr == null) return;
            try {
                Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
                gestor.crearTiqueteRegular(cliente, categoria, fecha);
                JOptionPane.showMessageDialog(this, "¡Tiquete regular creado!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Fecha inválida.");
            }
        } else { 
            ArrayList<Temporada> temporadas = gestor.getTemporadas();
            if (temporadas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay temporadas.");
                return;
            }

            String[] nombresTemp = temporadas.stream().map(Temporada::getName).toArray(String[]::new);
            String seleccion = (String) JOptionPane.showInputDialog(this, "Seleccione temporada:",
                    "Temporada", JOptionPane.QUESTION_MESSAGE, null, nombresTemp, nombresTemp[0]);

            if (seleccion == null) return;

            Temporada temporada = temporadas.stream().filter(t -> t.getName().equals(seleccion)).findFirst().get();
            gestor.crearTiqueteTemporada(cliente, categoria, temporada);
            JOptionPane.showMessageDialog(this, "¡Tiquete de temporada creado!");
        }
    }

    private void mostrarTiquetes() {
        ArrayList<Tiquete> tiquetes = cliente.getTiquetes();
        ArrayList<FastPass> fastPasses = cliente.getFastPass();

        StringBuilder mensaje = new StringBuilder("--- TIQUETES ---\n");

        if (tiquetes.isEmpty()) {
            mensaje.append("No tiene tiquetes normales.\n");
        } else {
            for (Tiquete t : tiquetes) {
                mensaje.append("ID: ").append(t.getId()).append(", Categoría: ").append(t.getCategoria().getNombre()).append("\n");
            }
        }

        mensaje.append("\n--- FASTPASS ---\n");

        if (fastPasses.isEmpty()) {
            mensaje.append("No tiene FastPass.\n");
        } else {
            for (FastPass f : fastPasses) {
                mensaje.append("ID Tiquete: ").append(f.getIdTiquete())
                        .append(", Fecha: ").append(f.getFecha()).append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, mensaje.toString());
    }

    private void mostrarEspectaculos() {
        ArrayList<Espectaculo> espectaculos = GestorAtracciones.getInstancia().obtenerEspectaculos();

        if (espectaculos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay espectáculos.");
            return;
        }

        StringBuilder sb = new StringBuilder("--- ESPECTÁCULOS ---\n");
        for (Espectaculo e : espectaculos) {
            sb.append("- ").append(e.getNombre()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void mostrarAtracciones() {
        GestorAtracciones gestor = GestorAtracciones.getInstancia();
        ArrayList<AtraccionMecanica> mecanicas = gestor.getAtraccionesMecanicas();
        ArrayList<AtraccionCultural> culturales = gestor.getAtraccionesCulturales();

        if (mecanicas.isEmpty() && culturales.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay atracciones.");
            return;
        }

        StringBuilder sb = new StringBuilder("--- ATRACCIONES ---\n");
        for (AtraccionMecanica a : mecanicas) {
            sb.append("[Mecánica] ").append(a.toString()).append("\n");
        }
        for (AtraccionCultural a : culturales) {
            sb.append("[Cultural] ").append(a.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void imprimirTiquetes() {
    ArrayList<Tiquete> tiquetes = cliente.getTiquetes();

    if (tiquetes.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No tiene tiquetes para imprimir.");
        return;
    }

    for (Tiquete t : tiquetes) {
        if (t.isImpreso()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "El tiquete con ID " + t.getId() + " ya fue impreso. ¿Desea reimprimirlo?",
                "Reimpresión", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) continue;
        }

        String tipo;
        if (t instanceof TiqueteTemporada) {
            tipo = "Temporada";
        } else if (t instanceof TiqueteRegular) {
            tipo = "Regular";
        } else {
            tipo = "Desconocido";
        }

        String id = t.getId();
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        String qrContenido = "Tipo: " + tipo + ", ID: " + id + ", Fecha: " + fecha;

        try {
            int width = 200;
            int height = 200;
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrContenido, BarcodeFormat.QR_CODE, width, height);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            JFrame frame = new JFrame("Tiquete " + id);
            frame.setSize(300, 400);
            frame.setLayout(new BorderLayout());

            JTextArea texto = new JTextArea("Tipo: " + tipo + "\nID: " + id + "\nFecha: " + fecha);
            texto.setEditable(false);
            texto.setFont(new Font("Arial", Font.PLAIN, 14));

            JLabel qrLabel = new JLabel(new ImageIcon(qrImage));
            qrLabel.setHorizontalAlignment(SwingConstants.CENTER);

            frame.add(texto, BorderLayout.NORTH);
            frame.add(qrLabel, BorderLayout.CENTER);
            frame.setVisible(true);

            t.setImpreso(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar QR para el tiquete " + id);
        }
    }
}

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("NA");
    }
}