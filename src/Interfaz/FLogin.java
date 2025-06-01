package Interfaz;

import Persona.*;
import javax.swing.*;
import java.awt.*;

public class FLogin extends JFrame {

    public FLogin() {
        setTitle("Iniciar Sesión");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("img/TitleBG.png"));

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JTextField campoUsuario = new JTextField();
        JPasswordField campoContrasena = new JPasswordField();

        JButton botonIngresar = new JButton("Ingresar");
        JButton botonVolver = new JButton("Volver");

        panel.add(new JLabel("Usuario:"));
        panel.add(campoUsuario);
        panel.add(new JLabel("Contraseña:"));
        panel.add(campoContrasena);

        JPanel botones = new JPanel(new FlowLayout());
        botones.add(botonIngresar);
        botones.add(botonVolver);

        add(panel, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        // Acción para botón Ingresar
        botonIngresar.addActionListener(e -> {
            String login = campoUsuario.getText();
            String contrasena = new String(campoContrasena.getPassword());

            GestorPersonas gestor = GestorPersonas.getInstance();
            GestorPersonas.TipoUsuario tipo = gestor.validarLogin(login, contrasena);

            switch (tipo) {
                case ADMINISTRADOR:
                    JOptionPane.showMessageDialog(this, "Bienvenido Administrador");
                    Administrador administrador = gestor.obtenerAdministrador(login);
                    if (administrador != null) {
                        dispose();
                        new FAdministrador(administrador); 
                    } else {
                        JOptionPane.showMessageDialog(this, "Empleado no encontrado.");
                    }
                    dispose();
                    
                    break;
                case EMPLEADO:
                    JOptionPane.showMessageDialog(this, "Bienvenido Empleado");
                    Empleado empleado = gestor.obtenerEmpleadoPorLogin(login);
                    if (empleado != null) {
                        dispose();
                        new FEmpleado(empleado); 
                    } else {
                        JOptionPane.showMessageDialog(this, "Empleado no encontrado.");
                    }
                    break;
                case CLIENTE:
                    JOptionPane.showMessageDialog(this, "Bienvenido Cliente");
                    Cliente cliente = gestor.buscarCliente(login);
                    if (cliente != null) {
                        dispose();
                        new FCliente(cliente);
                    } else {
                        JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
                    }
                    break;
                case NO_ENCONTRADO:
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
                    break;
            }
        });

        // Acción para botón Volver
        botonVolver.addActionListener(e -> {
            new FPrincipal();  // Tu ventana principal
            dispose();
        });

        setVisible(true);
    }
}
