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
        setSize(700, 700);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("img/TitleBG.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lblBienvenida = new JLabel("Bienvenido, " + cliente.getNombre(), SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblBienvenida, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(6, 1, 10, 10)); 
        add(panelBotones, BorderLayout.CENTER);

        JButton btnComprarTiquete = new JButton("Comprar Tiquete");
        JButton btnConsultarTiquetes = new JButton("Mis Tiquetes");
        JButton btnConsultarEspectaculos = new JButton("Consultar Espectáculos");
        JButton btnConsultarAtracciones = new JButton("Consultar Atracciones");
        JButton btnImprimirTiquetes = new JButton("Imprimir Tiquetes");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        panelBotones.add(btnConsultarAtracciones);
        btnConsultarAtracciones.setFocusPainted(false);
        panelBotones.add(btnConsultarEspectaculos);
        panelBotones.add(btnComprarTiquete);
        panelBotones.add(btnConsultarTiquetes);
        panelBotones.add(btnImprimirTiquetes);
        panelBotones.add(btnCerrarSesion);

        btnComprarTiquete.addActionListener(e -> mostrarDialogoCompra());
        btnConsultarTiquetes.addActionListener(e -> mostrarTiquetes());
        btnConsultarEspectaculos.addActionListener(e -> mostrarEspectaculos());
        btnConsultarAtracciones.addActionListener(e -> mostrarAtracciones());
        btnImprimirTiquetes.addActionListener(e -> imprimirTiqueteQR());
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new FLogin();
        });

        setVisible(true);
    }

    private void imprimirTiqueteQR() {
        ArrayList<Tiquete> tiquetes = cliente.getTiquetes();

        if (tiquetes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes tiquetes para imprimir.");
            return;
        }

        String[] opciones = new String[tiquetes.size()];
        for (int i = 0; i < tiquetes.size(); i++) {
            Tiquete t = tiquetes.get(i);
            opciones[i] = "ID: " + t.getId() + " - " + t.getCategoria().getNombre();
        }

        String seleccion = (String) JOptionPane.showInputDialog(this, "Seleccione un tiquete:",
                "Imprimir QR", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == null) return;

        for (Tiquete t : tiquetes) {
            if (seleccion.contains(String.valueOf(t.getId()))) {
                new VentanaQR(t);
                break;
            }
        }
    }

	public void mostrarDialogoCompra() {
        GestorTiquetes gestor = GestorTiquetes.getInstancia();
        ArrayList<CategoriaTiquete> categorias = gestor.getCategoriasDisponibles();

        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay categorías disponibles.");
            return;
        }

        String[] tipos = {"Regular", "Temporada"};
        int tipo = JOptionPane.showOptionDialog(this, "Tipo de tiquete:", "Compra",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, tipos, tipos[0]);
        if (tipo == -1) return;

        ArrayList<String> nombresCategorias = new ArrayList<>();
        for (CategoriaTiquete cat : categorias) {
            if (tipo == 1 && cat.getNombre().equalsIgnoreCase("Básico")) {
                continue;
            }
            nombresCategorias.add(cat.getNombre());
        }

        if (nombresCategorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay categorías disponibles para este tipo.");
            return;
        }

        String[] opcionesCategorias = nombresCategorias.toArray(new String[0]);
        String categoria = (String) JOptionPane.showInputDialog(this, "Seleccione categoría:",
                "Categoría", JOptionPane.QUESTION_MESSAGE, null, opcionesCategorias, opcionesCategorias[0]);
        if (categoria == null) return;

        if (tipo == 0) { // Regular
            String fechaStr = JOptionPane.showInputDialog(this, "Ingrese fecha (dd/MM/yyyy):");
            if (fechaStr == null) return;

            try {
                Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
                gestor.crearTiqueteRegular(cliente, categoria, fecha);
                JOptionPane.showMessageDialog(this, "¡Tiquete regular creado!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Fecha inválida.");
            }

        } else { // Temporada
            ArrayList<Temporada> temporadas = gestor.getTemporadas();

            if (temporadas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay temporadas disponibles.");
                return;
            }

            ArrayList<String> nombresTemporadas = new ArrayList<>();
            for (Temporada t : temporadas) {
                nombresTemporadas.add(t.getName());
            }

            String[] opcionesTemporadas = nombresTemporadas.toArray(new String[0]);
            String seleccion = (String) JOptionPane.showInputDialog(this, "Seleccione temporada:",
                    "Temporada", JOptionPane.QUESTION_MESSAGE, null, opcionesTemporadas, opcionesTemporadas[0]);
            if (seleccion == null) return;

            Temporada temporadaSeleccionada = null;
            for (Temporada t : temporadas) {
                if (t.getName().equals(seleccion)) {
                    temporadaSeleccionada = t;
                    break;
                }
            }

            if (temporadaSeleccionada == null) return;

            gestor.crearTiqueteTemporada(cliente, categoria, temporadaSeleccionada);
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