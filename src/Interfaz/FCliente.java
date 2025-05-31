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
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        add(btnComprarTiquete);
        add(btnConsultarTiquetes);
        add(btnConsultarEspectaculos);
        add(btnConsultarAtracciones);
        add(btnCerrarSesion);

        btnComprarTiquete.addActionListener(e -> mostrarDialogoCompra());
        btnConsultarTiquetes.addActionListener(e -> mostrarTiquetes());
        btnConsultarEspectaculos.addActionListener(e -> mostrarEspectaculos());
        btnConsultarAtracciones.addActionListener(e -> mostrarAtracciones());
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

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("NA");
    }
}