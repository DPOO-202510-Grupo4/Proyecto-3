package Interfaz;

import Persona.Administrador;
import Tiquetes.GestorTiquetes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaCrearTemporada extends JFrame {

    private Administrador administrador;
    private JTextField txtNombre;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public VentanaCrearTemporada() {

        setTitle("Crear temporada");
        setSize(600, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 1, 5, 5));

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(25); 
        JLabel lblFechaInicio = new JLabel("Fecha inicio (yyyy-MM-dd):");
        txtFechaInicio = new JTextField(25);

        JLabel lblFechaFin = new JLabel("Fecha fin (yyyy-MM-dd):");
        txtFechaFin = new JTextField(25);

        JButton btnCrear = new JButton("Crear temporada");
        btnCrear.addActionListener(this::crearTemporada);

        add(lblNombre);
        add(wrapCentered(txtNombre));

        add(lblFechaInicio);
        add(wrapCentered(txtFechaInicio));

        add(lblFechaFin);
        add(wrapCentered(txtFechaFin));

        add(btnCrear);

        setVisible(true);
    }

    private JPanel wrapCentered(JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); 
        panel.add(field);
        return panel;
    }

    private void crearTemporada(ActionEvent e) {
        String nombre = txtNombre.getText().trim();
        String fechaInicioStr = txtFechaInicio.getText().trim();
        String fechaFinStr = txtFechaFin.getText().trim();

        if (nombre.isEmpty() || fechaInicioStr.isEmpty() || fechaFinStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Date fechaInicio = sdf.parse(fechaInicioStr);
            Date fechaFin = sdf.parse(fechaFinStr);

            if (fechaFin.before(fechaInicio)) {
                JOptionPane.showMessageDialog(this, "La fecha de fin debe ser posterior a la de inicio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            GestorTiquetes.getInstancia().crearTemporada(fechaInicio, fechaFin, nombre);
            JOptionPane.showMessageDialog(this, "Temporada creada correctamente.");
            dispose();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usa yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
