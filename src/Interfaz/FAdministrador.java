package Interfaz;

import Persona.Administrador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FAdministrador extends JFrame implements ActionListener {

    private Administrador administrador;
    private JButton btnEmpleados, btnAtracciones, btnTiquetes, btnTemporadas;
    private JButton btnCerrarSesion;

    public FAdministrador(Administrador administrador) {
        this.administrador = administrador;

        setTitle("Panel del Administrador");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setIconImage(Toolkit.getDefaultToolkit().getImage("img/TitleBG.png"));

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Bienvenido, " + administrador.getNombre(), SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panelPrincipal.add(label, BorderLayout.NORTH);

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

        btnEmpleados = crearBotonEstilizado("Empleados");
        btnAtracciones = crearBotonEstilizado("Atracciones");
        btnTiquetes = crearBotonEstilizado("Tiquetes");
        btnTemporadas = crearBotonEstilizado("Temporadas");
        btnCerrarSesion = crearBotonEstilizado("Cerrar sesión");

        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotones.add(btnEmpleados);
        panelBotones.add(btnAtracciones);
        panelBotones.add(btnTiquetes);
        panelBotones.add(btnTemporadas);

        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.add(panelBotones, BorderLayout.NORTH);
        panelInferior.add(btnCerrarSesion, BorderLayout.SOUTH);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

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
        boton.addActionListener(this);
        return boton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnEmpleados) {
            new VentanaEmpleados(administrador);
            dispose();
        } else if (source == btnAtracciones) {
            new VentanaAtracciones(administrador);
            dispose();
        } else if (source == btnTiquetes) {
        	new VentanaTiquetes(administrador);
        	 dispose();
        } else if (source == btnTemporadas) {
            new VentanaCrearTemporada();
        } else if (source == btnCerrarSesion) {
            new FPrincipal(); 
            dispose();
        }
    }
}
