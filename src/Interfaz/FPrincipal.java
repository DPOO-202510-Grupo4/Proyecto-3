package Interfaz;

import Persistencias.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import javax.swing.*;


public class FPrincipal extends JFrame implements ActionListener{

        public FPrincipal() {
        setTitle("Nebula Park");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("img/TitleBG.png"));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelImagen.setBackground(new Color(255, 255, 255));
        ImageIcon iconoOriginal = new ImageIcon("img/Title.png");
        Image imagen = iconoOriginal.getImage();

        int anchoOriginal = iconoOriginal.getIconWidth();
        int altoOriginal = iconoOriginal.getIconHeight();

        int maxAncho = 400;
        double escala = (double) maxAncho / anchoOriginal;

        int nuevoAncho = (int) (anchoOriginal * escala);
        int nuevoAlto = (int) (altoOriginal * escala);

        Image imagenEscalada = imagen.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

        JLabel etiquetaImagen = new JLabel(iconoEscalado);
        panelImagen.add(etiquetaImagen);

        add(panelImagen, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(255, 255, 255));

        JButton btnLogin = new JButton("Iniciar sesión");
        JButton btnRegistro = new JButton("Registrarse");

        Dimension tamañoBoton = new Dimension(150, 40);
        btnLogin.setPreferredSize(tamañoBoton);
        btnRegistro.setPreferredSize(tamañoBoton);

        btnLogin.setBackground(new Color(202, 252, 5));
        btnLogin.setOpaque(true);
        btnLogin.setFocusPainted(false);

        btnRegistro.setBackground(new Color(202, 252, 5));
        btnRegistro.setOpaque(true);

        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistro);

        add(panelBotones, BorderLayout.CENTER);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("NA");
    }
}
