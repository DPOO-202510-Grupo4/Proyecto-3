package Interfaz;

import Persona.Administrador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FAdministrador extends JFrame implements ActionListener {

    private Administrador administrador;

    public FAdministrador(Administrador administrador) {
        this.administrador = administrador;

        setTitle("Panel del Administrador");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Bienvenido, " + administrador.getNombre());
        label.setHorizontalAlignment(SwingConstants.CENTER);

        add(label, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Puedes implementar acciones aquí si tienes botones en el futuro
    }
}
