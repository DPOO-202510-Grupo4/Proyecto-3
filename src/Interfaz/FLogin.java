
package Interfaz;

import Persona.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FLogin extends JFrame implements ActionListener {

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

        setVisible(true);

        botonIngresar.addActionListener(e -> {
            String login = campoUsuario.getText();
            String contrasena = new String(campoContrasena.getPassword());

            GestorPersonas gestor = GestorPersonas.getInstance();
            GestorPersonas.TipoUsuario tipo = gestor.validarLogin(login, contrasena);

            switch (tipo) {
                /*case ADMINISTRADOR:
                    JOptionPane.showMessageDialog(null, "Bienvenido Administrador");
                    dispose();
                    new FAdministrador(); 
                    break; */
                /*case EMPLEADO:
                    JOptionPane.showMessageDialog(null, "Bienvenido Empleado");
                    Empleado empleado = gestor.buscarEmpleado(login); 
                    if (empleado != null) {
                        dispose();
                        new FEmpleado(empleado); 
                    }
                    break; */
                case CLIENTE:
                    JOptionPane.showMessageDialog(null, "Bienvenido Cliente");
                    Cliente cliente = gestor.buscarCliente(login);
                    if (cliente != null) {
                        dispose();
                        new FCliente(cliente);
                    }
                    break;
                case NO_ENCONTRADO:
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                    break;
            }
        });

        botonVolver.addActionListener(e -> {
            new FPrincipal();
            dispose();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("NA");
    }
}