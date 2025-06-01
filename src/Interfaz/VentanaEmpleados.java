package Interfaz;

import javax.swing.*;

import Persona.Administrador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaEmpleados extends JFrame {

    public VentanaEmpleados(Administrador administrador) {
        setTitle("Gestión de Empleados");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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

        JButton btnConsultar = crearBotonEstilizado("Consultar empleado");
        JButton btnRegistrar = crearBotonEstilizado("Registrar empleado");
        JButton btnRegresar = crearBotonEstilizado("Regresar");

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaRegistroEmpleado(administrador); 
                dispose(); 
            }
        });
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaConsultaEmpleados(administrador);
                dispose();
            }
        });

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelBotones.add(btnConsultar);
        panelBotones.add(btnRegistrar);

        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.add(panelBotones, BorderLayout.NORTH);
        panelInferior.add(btnRegresar, BorderLayout.SOUTH);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new FAdministrador(administrador);
                dispose(); 
            }
        });

        add(panelPrincipal);
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
}
