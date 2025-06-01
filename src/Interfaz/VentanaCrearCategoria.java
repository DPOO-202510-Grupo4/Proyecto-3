package Interfaz;

import Persona.Administrador;
import Atracciones.GestorAtracciones;
import Tiquetes.GestorTiquetes;
import Atracciones.AtraccionMecanica;
import Atracciones.AtraccionCultural;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaCrearCategoria extends JFrame {

    private Administrador administrador;

    private JTextField txtNombre;
    private JTextField txtPrecioBase;
    private JList<String> listaMecanicas;
    private JList<String> listaCulturales;

    public VentanaCrearCategoria(Administrador administrador) {
        this.administrador = administrador;

        setTitle("Crear Categoría de Tiquete");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelInputs = new JPanel(new GridLayout(2, 2, 10, 10));
        panelInputs.add(new JLabel("Nombre de la categoría:"));
        txtNombre = new JTextField();
        panelInputs.add(txtNombre);

        panelInputs.add(new JLabel("Precio base:"));
        txtPrecioBase = new JTextField();
        panelInputs.add(txtPrecioBase);

        panelPrincipal.add(panelInputs, BorderLayout.NORTH);

        JPanel panelListas = new JPanel(new GridLayout(1, 2, 10, 10));

        GestorAtracciones gestorAtracciones = GestorAtracciones.getInstancia();

        ArrayList<AtraccionMecanica> mecanicas = gestorAtracciones.getAtraccionesMecanicas();
        DefaultListModel<String> modeloMecanicas = new DefaultListModel<>();
        for (AtraccionMecanica am : mecanicas) {
            modeloMecanicas.addElement(am.getNombre());
        }
        listaMecanicas = new JList<>(modeloMecanicas);
        listaMecanicas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollMecanicas = new JScrollPane(listaMecanicas);
        JPanel panelMecanicas = new JPanel(new BorderLayout());
        panelMecanicas.add(new JLabel("Atracciones Mecánicas:"), BorderLayout.NORTH);
        panelMecanicas.add(scrollMecanicas, BorderLayout.CENTER);

        ArrayList<AtraccionCultural> culturales = gestorAtracciones.getAtraccionesCulturales();
        DefaultListModel<String> modeloCulturales = new DefaultListModel<>();
        for (AtraccionCultural ac : culturales) {
            modeloCulturales.addElement(ac.getNombre());
        }
        listaCulturales = new JList<>(modeloCulturales);
        listaCulturales.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollCulturales = new JScrollPane(listaCulturales);
        JPanel panelCulturales = new JPanel(new BorderLayout());
        panelCulturales.add(new JLabel("Atracciones Culturales:"), BorderLayout.NORTH);
        panelCulturales.add(scrollCulturales, BorderLayout.CENTER);

        panelListas.add(panelMecanicas);
        panelListas.add(panelCulturales);

        panelPrincipal.add(panelListas, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnCrear = new JButton("Crear");
        JButton btnCancelar = new JButton("Cancelar");

        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearCategoria();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaTiquetes(administrador);
                dispose();
            }
        });

        panelBotones.add(btnCrear);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
        setVisible(true);
    }

    private void crearCategoria() {
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecioBase.getText().trim();

        if (nombre.isEmpty() || precioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double precioBase;
        try {
            precioBase = Double.parseDouble(precioTexto);
            if (precioBase < 0) {
                JOptionPane.showMessageDialog(this, "El precio base debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para el precio base.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<String> atraccionesSeleccionadas = new ArrayList<>();

        for (String nombreAtrac : listaMecanicas.getSelectedValuesList()) {
            atraccionesSeleccionadas.add(nombreAtrac);
        }
        for (String nombreAtrac : listaCulturales.getSelectedValuesList()) {
            atraccionesSeleccionadas.add(nombreAtrac);
        }

        if (atraccionesSeleccionadas.isEmpty()) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "No ha seleccionado ninguna atracción. ¿Desea crear la categoría sin atracciones?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }
        }

        GestorTiquetes.getInstancia().crearCategoriaTiquete(nombre, atraccionesSeleccionadas, precioBase);

        JOptionPane.showMessageDialog(this, "Categoría de tiquete creada exitosamente.");

        new VentanaTiquetes(administrador);
        dispose();
    }
}
