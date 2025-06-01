package Interfaz;
 
import Tiquetes.Tiquete;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class VentanaQR extends JFrame {

    public VentanaQR(Tiquete tiquete) {
        setTitle("Tiquete Parque");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("TIQUETE PARQUE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(15, 0));

        JLabel qrLabel = new JLabel();
        try {
            Date ahora = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaActual = sdf.format(ahora);

            String contenido = "ID: " + tiquete.getId() +
                    "\nCategoría: " + tiquete.getCategoria().getNombre() +
                    "\nFecha impresión: " + fechaActual;

            //BitMatrix matrix = new MultiFormatWriter().encode(contenido, BarcodeFormat.QR_CODE, 150, 150, new HashMap<>());
            //ImageIcon icon = new ImageIcon(MatrixToImageWriter.toBufferedImage(matrix));
            //qrLabel.setIcon(icon);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generando QR: " + ex.getMessage());
        }

        panelCentro.add(qrLabel, BorderLayout.WEST);


        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información"));

        JLabel lblId = new JLabel("ID: " + tiquete.getId());
        JLabel lblCategoria = new JLabel("Categoría: " + tiquete.getCategoria().getNombre());

        Date ahora = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaActual = sdf.format(ahora);
        JLabel lblFecha = new JLabel("Fecha impresión: " + fechaActual);

        lblId.setFont(new Font("Arial", Font.PLAIN, 16));
        lblCategoria.setFont(new Font("Arial", Font.PLAIN, 16));
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 16));

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
