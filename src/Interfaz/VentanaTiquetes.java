package Interfaz;

import Persona.Administrador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaTiquetes extends JFrame {

    private Administrador administrador;

    public VentanaTiquetes(Administrador administrador) {
        this.administrador = administrador;

        setTitle("Módulo de Tiquetes");
        setSize(600, 400);
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

        JButton btnCrearCategoria = new JButton("Crear Categoría");
        JButton btnConsultarCategorias = new JButton("Consultar Categorías");
        JButton btnRegresar = new JButton("Regresar");

        btnCrearCategoria.setPreferredSize(new Dimension(160, 40));
        btnConsultarCategorias.setPreferredSize(new Dimension(160, 40));
        btnRegresar.setPreferredSize(new Dimension(100, 40));

        Color botonColor = new Color(202, 252, 5);
        btnCrearCategoria.setBackground(botonColor);
        btnConsultarCategorias.setBackground(botonColor);
        btnRegresar.setBackground(botonColor);

        btnCrearCategoria.setFont(new Font("Arial", Font.BOLD, 14));
        btnConsultarCategorias.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 14));

        btnCrearCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaCrearCategoria(administrador);
                dispose();
            }
        });

        btnConsultarCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaConsultaCategorias(administrador);
            }
        });

        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FAdministrador(administrador);
                dispose();
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(btnCrearCategoria);
        panelBotones.add(btnConsultarCategorias);
        panelBotones.add(btnRegresar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
        setVisible(true);
    }
}
