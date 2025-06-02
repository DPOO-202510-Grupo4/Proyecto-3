package Interfaz;

import Atracciones.*;
import Persona.*;
import Tiquetes.*;
import restricciones.Temporada;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FEmpleado extends JFrame {
    private Empleado empleado;

    public FEmpleado(Empleado empleado) {
        this.empleado = empleado;
        setTitle("Panel del Empleado");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setIconImage(Toolkit.getDefaultToolkit().getImage("img/TitleBG.png"));

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Bienvenido, " + empleado.getNombre(), SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panelPrincipal.add(label, BorderLayout.NORTH);

        try {
            ImageIcon img = new ImageIcon("img/TitleBG.png");
            JLabel imagenCentral = new JLabel(new ImageIcon(img.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH)));
            imagenCentral.setHorizontalAlignment(SwingConstants.CENTER);
            panelPrincipal.add(imagenCentral, BorderLayout.CENTER);
        } catch (Exception e) {
            JLabel fallback = new JLabel("Imagen no disponible", SwingConstants.CENTER);
            fallback.setFont(new Font("Arial", Font.ITALIC, 14));
            panelPrincipal.add(fallback, BorderLayout.CENTER);
        }

        JButton btnCapacitaciones = crearBotonEstilizado("Ver Capacitaciones");
        JButton btnRegistrarVenta = crearBotonEstilizado("Vender Tiquete");
        JButton btnRegistrarTiquete = crearBotonEstilizado("Validar Tiquete");
        JButton btnCerrar = crearBotonEstilizado("Cerrar Sesión");

        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotones.add(btnCapacitaciones);
        panelBotones.add(btnRegistrarVenta);
        panelBotones.add(btnRegistrarTiquete);
        panelBotones.add(btnCerrar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);

        btnCapacitaciones.addActionListener(e -> mostrarCapacitaciones());
        btnRegistrarVenta.addActionListener(e -> registrarVenta());
        btnRegistrarTiquete.addActionListener(e -> registrarTiquete());
        btnCerrar.addActionListener(e -> {
            dispose();
            new FPrincipal().setVisible(true);
        });

        setVisible(true);
    }

    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(150, 40));
        boton.setBackground(new Color(202, 252, 5));
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        return boton;
    }

    private void mostrarCapacitaciones() {
        Capacitaciones c = empleado.getCapacitaciones();
        if (c == null) {
            JOptionPane.showMessageDialog(this, "No tienes capacitaciones registradas.");
            return;
        }
        StringBuilder sb = new StringBuilder("Capacitaciones:\n");
        sb.append("Cocinero: ").append(c.getEsCocinero()).append("\n");
        sb.append("Cajero: ").append(c.getEsCajero()).append("\n");
        sb.append("Atracciones:\n");
        for (Atraccion a : c.getCapacitacionAtracciones()) {
            sb.append("- ").append(a.getNombre()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void registrarVenta() {
        GestorPersonas gestorPersonas = GestorPersonas.getInstance();
        String login = JOptionPane.showInputDialog(this, "Ingrese el login del cliente:");

        if (login == null || login.isBlank()) return;

        Cliente cliente = gestorPersonas.buscarCliente(login);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
            return;
        }

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


    private void registrarTiquete() {
        String id = JOptionPane.showInputDialog(this, "Ingrese el ID del tiquete:");
        if (id == null || id.isBlank()) return;

        GestorTiquetes gestor = GestorTiquetes.getInstancia();
        Tiquete t = gestor.buscarTiquetePorId(id);
        System.out.println(t);
        if (t == null) {
            JOptionPane.showMessageDialog(this, "Tiquete no encontrado.");
        } else {
            gestor.usarTiquete(t);
            JOptionPane.showMessageDialog(this, "Tiquete registrado como usado.");
        }
    }
}
