package Interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class FPrincipal extends JFrame implements ActionListener{

        public FPrincipal() {
        setTitle("Parque de Atracciones - Bienvenido");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titulo = new JLabel("Bienvenido al Parque de Atracciones", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo);

        JButton btnLogin = new JButton("Iniciar sesión");
        JButton btnRegistro = new JButton("Registrarse como cliente");

        panel.add(btnLogin);
        panel.add(btnRegistro);

        add(panel);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new FLogin();
                dispose();
            }
        });

        btnRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new FRegistro();
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new FPrincipal();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("NA");
    }
}
