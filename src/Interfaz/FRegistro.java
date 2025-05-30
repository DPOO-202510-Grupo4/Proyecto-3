
package Interfaz;

import Persona.GestorPersonas;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FRegistro extends JFrame implements ActionListener{
    
    private JTextField campoNombre;
    private JTextField campoLogin;
    private JPasswordField campoContrasena;
    private JTextField campoFechaNacimiento;

    public FRegistro() {
        setTitle("Registro de Cliente");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Nombre completo:"));
        campoNombre = new JTextField();
        add(campoNombre);

        add(new JLabel("Login (usuario):"));
        campoLogin = new JTextField();
        add(campoLogin);

        add(new JLabel("Contraseña:"));
        campoContrasena = new JPasswordField();
        add(campoContrasena);

        add(new JLabel("Fecha nacimiento (dd/MM/yyyy):"));
        campoFechaNacimiento = new JTextField();
        add(campoFechaNacimiento);

        JButton botonRegistrar = new JButton("Registrar");
        JButton botonCancelar = new JButton("Cancelar");
        add(botonRegistrar);
        add(botonCancelar);
        
        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarCliente();
            }
        });

        botonCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void registrarCliente() {
        String nombre = campoNombre.getText().trim();
        String login = campoLogin.getText().trim();
        String contrasena = new String(campoContrasena.getPassword());
        String fechaNacimiento = campoFechaNacimiento.getText().trim();

        if (nombre.isEmpty() || login.isEmpty() || contrasena.isEmpty() || fechaNacimiento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        GestorPersonas gestor = GestorPersonas.getInstance();

        if (gestor.buscarCliente(login) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un cliente con ese login.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        gestor.registrarCliente(nombre, login, contrasena, fechaNacimiento);
        JOptionPane.showMessageDialog(this, "¡Registro exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose(); 

        new FLogin();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("NA");
    }
}