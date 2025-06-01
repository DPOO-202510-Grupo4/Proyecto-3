package Interfaz;

import Persona.Administrador;
import Atracciones.GestorAtracciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaRegistroEspectaculo extends JFrame {

    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat formatoHoraCompleta = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public VentanaRegistroEspectaculo(Administrador administrador) {
        setTitle("Registro de Espectáculo");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblNombre = new JLabel("Nombre del espectáculo:");
        JTextField txtNombre = new JTextField();

        JLabel lblEmpresa = new JLabel("Empresa encargada:");
        JTextField txtEmpresa = new JTextField();

        JLabel lblFecha = new JLabel("Fecha (dd/MM/yyyy):");
        JTextField txtFecha = new JTextField();

        JLabel lblHoraInicio = new JLabel("Hora de inicio (HH:mm):");
        JTextField txtHoraInicio = new JTextField();

        JLabel lblHoraFin = new JLabel("Hora de finalización (HH:mm):");
        JTextField txtHoraFin = new JTextField();

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        panel.add(lblNombre);
        panel.add(txtNombre);

        panel.add(lblEmpresa);
        panel.add(txtEmpresa);

        panel.add(lblFecha);
        panel.add(txtFecha);

        panel.add(lblHoraInicio);
        panel.add(txtHoraInicio);

        panel.add(lblHoraFin);
        panel.add(txtHoraFin);

        panel.add(btnRegistrar);
        panel.add(btnCancelar);

        add(panel);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                String empresa = txtEmpresa.getText().trim();
                String fechaStr = txtFecha.getText().trim();
                String horaInicioStr = txtHoraInicio.getText().trim();
                String horaFinStr = txtHoraFin.getText().trim();

                if (nombre.isEmpty() || empresa.isEmpty() || fechaStr.isEmpty() || horaInicioStr.isEmpty() || horaFinStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Date fecha = formatoFecha.parse(fechaStr);
                    Date horaInicio = formatoHoraCompleta.parse(fechaStr + " " + horaInicioStr);
                    Date horaFin = formatoHoraCompleta.parse(fechaStr + " " + horaFinStr);

                    GestorAtracciones gestor = GestorAtracciones.getInstancia();
                    gestor.crearEspectaculo(fecha, horaInicio, horaFin, empresa, nombre);

                    JOptionPane.showMessageDialog(null, "Espectáculo registrado exitosamente.");
                    new VentanaAtracciones(administrador);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al interpretar la fecha u hora. Usa el formato correcto.\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
