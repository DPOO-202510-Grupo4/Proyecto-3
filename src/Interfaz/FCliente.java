package Interfaz;

import Persona.Cliente;
import Tiquetes.Factura;
import Tiquetes.FastPass;
import Tiquetes.Tiquete;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FCliente extends JFrame implements ActionListener {
    
    private Cliente cliente;

    public FCliente(Cliente cliente) {
        this.cliente = cliente;

        setTitle("Panel del Cliente");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));
        panelSuperior.add(new JLabel("Bienvenido: " + cliente.getNombre()));
        panelSuperior.add(new JLabel("Usuario: " + cliente.getLogin()));
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton botonHistorial = new JButton("Ver historial de compras");
        JButton botonTiquetes = new JButton("Ver tiquetes");
        JButton botonFastPass = new JButton("Ver FastPass");

        panelCentral.add(botonHistorial);
        panelCentral.add(botonTiquetes);
        panelCentral.add(botonFastPass);

        add(panelCentral, BorderLayout.CENTER);

        JButton botonCerrarSesion = new JButton("Cerrar sesión");
        add(botonCerrarSesion, BorderLayout.SOUTH);

        botonHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarHistorialCompras();
            }
        });

        botonTiquetes.addActionListener(e -> mostrarTiquetes());

        botonFastPass.addActionListener(e -> mostrarFastPass());

        botonCerrarSesion.addActionListener(e -> {
            dispose();
            new FLogin();
        });

        setVisible(true);
    }

    private void mostrarHistorialCompras() {
        if (cliente.getHistorialCompras().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes compras registradas.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Factura factura : cliente.getHistorialCompras()) {
            sb.append(factura.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Historial de Compras", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarTiquetes() {
        if (cliente.getTiquetes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes tiquetes.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Tiquete t : cliente.getTiquetes()) {
            sb.append(t.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Tiquetes", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarFastPass() {
        if (cliente.getFastPass().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes FastPass.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (FastPass fp : cliente.getFastPass()) {
            sb.append(fp.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "FastPass", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
