package Interfaz;

import Atracciones.GestorAtracciones;
import restricciones.RestriccionesCultural;
import restricciones.Temporada;
import Tiquetes.GestorTiquetes;
import Persona.Administrador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaRegistroAtraccionCultural extends JFrame {

    private final DefaultListModel<String> modeloClima = new DefaultListModel<>();

    public VentanaRegistroAtraccionCultural(Administrador administrador) {
        setTitle("Registro de Atracción Cultural");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(12, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JTextField txtNombre = new JTextField();
        JTextField txtUbicacion = new JTextField();
        JTextField txtCapacidad = new JTextField();
        JTextField txtMinEmpleados = new JTextField();
        JTextField txtExclusividad = new JTextField();
        JTextField txtEdadMin = new JTextField();
        GestorTiquetes gestorT = GestorTiquetes.getInstance();
        List<Temporada> temporadas = gestorT.obtenerTemporadas();
        String[] nombresTemporadas = new String[temporadas.size()];

        for (int i = 0; i < temporadas.size(); i++) {
            nombresTemporadas[i] = temporadas.get(i).getName();
        }

        JComboBox<String> comboTemporada = new JComboBox<>(nombresTemporadas);

        JRadioButton rbTemporadaSi = new JRadioButton("Sí");
        JRadioButton rbTemporadaNo = new JRadioButton("No");
        ButtonGroup grupoTemporada = new ButtonGroup();
        grupoTemporada.add(rbTemporadaSi);
        grupoTemporada.add(rbTemporadaNo);

        JRadioButton rbDisponibleSi = new JRadioButton("Sí");
        JRadioButton rbDisponibleNo = new JRadioButton("No");
        ButtonGroup grupoDisponible = new ButtonGroup();
        grupoDisponible.add(rbDisponibleSi);
        grupoDisponible.add(rbDisponibleNo);

        JTextField txtCondicion = new JTextField();
        JButton btnAgregarCondicion = new JButton("Agregar condición");
        JList<String> listaCondiciones = new JList<>(modeloClima);

        btnAgregarCondicion.addActionListener(e -> {
            String condicion = txtCondicion.getText().trim();
            if (!condicion.isEmpty()) {
                modeloClima.addElement(condicion);
                txtCondicion.setText("");
            }
        });

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel("Ubicación:"));
        panel.add(txtUbicacion);

        panel.add(new JLabel("¿Es de temporada?"));
        JPanel temporadaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        temporadaPanel.add(rbTemporadaSi);
        temporadaPanel.add(rbTemporadaNo);
        panel.add(temporadaPanel);

        panel.add(new JLabel("¿Está disponible?"));
        JPanel disponiblePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        disponiblePanel.add(rbDisponibleSi);
        disponiblePanel.add(rbDisponibleNo);
        panel.add(disponiblePanel);

        panel.add(new JLabel("Capacidad máxima:"));
        panel.add(txtCapacidad);

        panel.add(new JLabel("Mínimo de empleados:"));
        panel.add(txtMinEmpleados);

        panel.add(new JLabel("Temporada:"));
        panel.add(comboTemporada);

        panel.add(new JLabel("Exclusividad:"));
        panel.add(txtExclusividad);

        panel.add(new JLabel("Edad mínima:"));
        panel.add(txtEdadMin);

        panel.add(new JLabel("Condición climática:"));
        JPanel climaPanel = new JPanel(new BorderLayout());
        climaPanel.add(txtCondicion, BorderLayout.CENTER);
        climaPanel.add(btnAgregarCondicion, BorderLayout.EAST);
        panel.add(climaPanel);

        panel.add(new JLabel("Condiciones agregadas:"));
        panel.add(new JScrollPane(listaCondiciones));

        panel.add(btnRegistrar);
        panel.add(btnCancelar);

        add(panel);

 
        btnRegistrar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String ubicacion = txtUbicacion.getText().trim();
                boolean deTemporada = rbTemporadaSi.isSelected();
                boolean disponible = rbDisponibleSi.isSelected(); 
                int capacidad = Integer.parseInt(txtCapacidad.getText().trim());
                int minEmpleados = Integer.parseInt(txtMinEmpleados.getText().trim());
                String temporadaIn = (String) comboTemporada.getSelectedItem();
                String exclusividad = txtExclusividad.getText().trim();
                int edadMin = Integer.parseInt(txtEdadMin.getText().trim());

                ArrayList<String> clima = new ArrayList<>();
                for (int i = 0; i < modeloClima.size(); i++) {
                    clima.add(modeloClima.getElementAt(i));
                }

                RestriccionesCultural restricciones = new RestriccionesCultural(clima, exclusividad, edadMin);

                GestorAtracciones gestor = GestorAtracciones.getInstancia();
                gestor.crearAtraccionCultural(ubicacion, nombre, deTemporada, capacidad, minEmpleados, restricciones, temporadaIn);

                JOptionPane.showMessageDialog(null, "Atracción cultural registrada con éxito.");
                new VentanaAtracciones(administrador);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al registrar la atracción. Verifica los datos.\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> {
            new VentanaAtracciones(administrador);
            dispose();
        });

        setVisible(true);
    }
}
