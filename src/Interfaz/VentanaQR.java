package Interfaz;

import Tiquetes.Tiquete;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class VentanaQR extends JFrame {

    public VentanaQR(Tiquete tiquete) {
        setTitle("Tiquete Parque");
        setSize(800, 300); // Más ancho para un tiquete horizontal
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel principal con fondo rojo (color de tiquete)
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(200, 0, 0)); // Rojo fuerte
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título centrado en blanco
        JLabel titulo = new JLabel("TIQUETE PARQUE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Panel central que contiene QR, imagen y datos
        JPanel panelCentro = new JPanel(new BorderLayout(20, 0));
        panelCentro.setOpaque(false); // Transparente para mostrar fondo rojo

        // QR con marco
        JLabel qrLabel = new JLabel();
        try {
            Date ahora = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaActual = sdf.format(ahora);

            String contenido = "ID: " + tiquete.getId() +
                    "\nCategoría: " + tiquete.getCategoria().getNombre() +
                    "\nFecha impresión: " + fechaActual;

            BitMatrix matrix = new MultiFormatWriter().encode(contenido, BarcodeFormat.QR_CODE, 150, 150, new HashMap<>());
            ImageIcon icon = new ImageIcon(MatrixToImageWriter.toBufferedImage(matrix));

            // Marco para el QR
            JPanel qrPanel = new JPanel();
            qrPanel.setBackground(Color.WHITE);
            qrPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            qrPanel.add(new JLabel(icon));

            panelCentro.add(qrPanel, BorderLayout.WEST);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generando QR: " + ex.getMessage());
        }

        // Panel para imagen PNG (centro del tiquete)
        JLabel imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagenLabel.setPreferredSize(new Dimension(200, 150)); // Espacio reservado

        // Cargar imagen (puedes cambiar la ruta)
        try {
            ImageIcon img = new ImageIcon("img/TitleBG.png");
            Image escalar = img.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
            imagenLabel.setIcon(new ImageIcon(escalar));
        } catch (Exception e) {
            imagenLabel.setText("Aquí va tu imagen PNG");
            imagenLabel.setForeground(Color.WHITE);
        }

        panelCentro.add(imagenLabel, BorderLayout.CENTER);

        // Información a la derecha
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setOpaque(false); // Fondo transparente
        panelInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE, 2), "Información", 0, 0, new Font("Arial", Font.BOLD, 14), Color.WHITE));

        JLabel lblId = new JLabel("ID: " + tiquete.getId());
        JLabel lblCategoria = new JLabel("Categoría: " + tiquete.getCategoria().getNombre());
        JLabel lblFecha = new JLabel("Fecha impresión: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        Font infoFont = new Font("Arial", Font.PLAIN, 16);
        Color textColor = Color.WHITE;
        lblId.setFont(infoFont);
        lblCategoria.setFont(infoFont);
        lblFecha.setFont(infoFont);

        lblId.setForeground(textColor);
        lblCategoria.setForeground(textColor);
        lblFecha.setForeground(textColor);

        panelInfo.add(lblId);
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(lblCategoria);
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(lblFecha);

        panelCentro.add(panelInfo, BorderLayout.EAST);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);
        setVisible(true);
    }
}
