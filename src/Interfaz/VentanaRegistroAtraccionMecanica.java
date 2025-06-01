package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import Atracciones.GestorAtracciones;
import Persona.Administrador;
import Tiquetes.GestorTiquetes;
import restricciones.RestriccionesMecanica;
import restricciones.Temporada;

public class VentanaRegistroAtraccionMecanica extends JFrame {

    public VentanaRegistroAtraccionMecanica(Administrador administrador) {
        setTitle("Registro de Atracción Mecánica");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(14, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblUbicacion = new JLabel("Ubicación:");
        JTextField txtUbicacion = new JTextField();

        JLabel lblRiesgo = new JLabel("Riesgo:");
        JTextField txtRiesgo = new JTextField();

        JLabel lblDeTemporada = new JLabel("¿Es de temporada?");
        JCheckBox chkDeTemporada = new JCheckBox();

        JLabel lblDisponible = new JLabel("¿Está disponible?");
        JCheckBox chkDisponible = new JCheckBox();

        JLabel lblTemporada = new JLabel("Temporada:");
        JComboBox<String> comboTemporada;

        GestorTiquetes gestorT = GestorTiquetes.getInstance();
        List<Temporada> temporadas = gestorT.obtenerTemporadas();
        String[] nombresTemporadas = new String[temporadas.size()];
        for (int i = 0; i < temporadas.size(); i++) {
            nombresTemporadas[i] = temporadas.get(i).getName();
        }
        comboTemporada = new JComboBox<>(nombresTemporadas);

        JLabel lblCapacidadMax = new JLabel("Capacidad máxima:");
        JTextField txtCapacidadMax = new JTextField();

        JLabel lblMinEmpleados = new JLabel("Mínimo de empleados:");
        JTextField txtMinEmpleados = new JTextField();

        JLabel lblExclusividad = new JLabel("Exclusividad:");
        JTextField txtExclusividad = new JTextField();

        JLabel lblAlturaMin = new JLabel("Altura mínima (m):");
        JTextField txtAlturaMin = new JTextField();

        JLabel lblAlturaMax = new JLabel("Altura máxima (m):");
        JTextField txtAlturaMax = new JTextField();

        JLabel lblPesoMin = new JLabel("Peso mínimo (kg):");
        JTextField txtPesoMin = new JTextField();

        JLabel lblPesoMax = new JLabel("Peso máximo (kg):");
        JTextField txtPesoMax = new JTextField();

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        panel.add(lblNombre);
        panel.add(txtNombre);

        panel.add(lblUbicacion);
        panel.add(txtUbicacion);

        panel.add(lblRiesgo);
        panel.add(txtRiesgo);

        panel.add(lblDeTemporada);
        panel.add(chkDeTemporada);

        panel.add(lblDisponible);
        panel.add(chkDisponible);

        panel.add(lblTemporada);
        panel.add(comboTemporada);

        panel.add(lblCapacidadMax);
        panel.add(txtCapacidadMax);

        panel.add(lblMinEmpleados);
        panel.add(txtMinEmpleados);

        panel.add(lblExclusividad);
        panel.add(txtExclusividad);

        panel.add(lblAlturaMin);
        panel.add(txtAlturaMin);

        panel.add(lblAlturaMax);
        panel.add(txtAlturaMax);

        panel.add(lblPesoMin);
        panel.add(txtPesoMin);

        panel.add(lblPesoMax);
        panel.add(txtPesoMax);

        panel.add(btnRegistrar);
        panel.add(btnCancelar);

        add(panel);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = txtNombre.getText().trim();
                    String ubicacion = txtUbicacion.getText().trim();
                    String riesgo = txtRiesgo.getText().trim();
                    boolean deTemporada = chkDeTemporada.isSelected();
                    boolean disponible = chkDisponible.isSelected();
                    String temporada = (String) comboTemporada.getSelectedItem();
                    int capacidadMax = Integer.parseInt(txtCapacidadMax.getText().trim());
                    int minEmpleados = Integer.parseInt(txtMinEmpleados.getText().trim());
                    String exclusividad = txtExclusividad.getText().trim();
                    double alturaMin = Double.parseDouble(txtAlturaMin.getText().trim());
                    double alturaMax = Double.parseDouble(txtAlturaMax.getText().trim());
                    double pesoMin = Double.parseDouble(txtPesoMin.getText().trim());
                    double pesoMax = Double.parseDouble(txtPesoMax.getText().trim());

                    ArrayList<String> salud = new ArrayList<>();
                    ArrayList<String> clima = new ArrayList<>();

                    RestriccionesMecanica restricciones = new RestriccionesMecanica(clima, exclusividad, alturaMin, alturaMax, pesoMin, pesoMax, salud);

                    GestorAtracciones gestor = GestorAtracciones.getInstancia();
                    gestor.crearAtraccionMecanica(ubicacion, nombre, deTemporada, disponible, capacidadMax, minEmpleados, riesgo, restricciones, temporada);

                    JOptionPane.showMessageDialog(null, "Atracción mecánica registrada con éxito.");
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingresa números válidos para capacidad, empleados, alturas y pesos.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar atracción mecánica: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaAtracciones(administrador);
				dispose();
			}
        	
        });

        setVisible(true);
    }
}
