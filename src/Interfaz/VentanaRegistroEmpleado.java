package Interfaz;

import Persona.Administrador;
import Persona.GestorPersonas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaRegistroEmpleado extends JFrame {

    public VentanaRegistroEmpleado(Administrador administrador) {
        setTitle("Registro de Empleado");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField();

        JLabel lblContrasena = new JLabel("Contraseña:");
        JTextField txtContrasena = new JPasswordField();

        JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento (dd/MM/yyyy):");
        JTextField txtFechaNacimiento = new JTextField();

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        panel.add(lblNombre);
        panel.add(txtNombre);

        panel.add(lblUsuario);
        panel.add(txtUsuario);

        panel.add(lblContrasena);
        panel.add(txtContrasena);

        panel.add(lblFechaNacimiento);
        panel.add(txtFechaNacimiento);

        panel.add(btnRegistrar);
        panel.add(btnCancelar);

        add(panel);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                String login = txtUsuario.getText().trim();
                String password = txtContrasena.getText().trim();
                String fechaNacimiento = txtFechaNacimiento.getText().trim();

                if (nombre.isEmpty() || login.isEmpty() || password.isEmpty() || fechaNacimiento.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    GestorPersonas gestor = GestorPersonas.getInstance();
                    gestor.crearEmpleadoBasico(nombre, login, password, fechaNacimiento);
                    JOptionPane.showMessageDialog(null, "Empleado registrado exitosamente.");
                    new VentanaEmpleados(administrador);  
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar empleado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaEmpleados(administrador);
                dispose();
            }
        });

        setVisible(true);
    }
}
