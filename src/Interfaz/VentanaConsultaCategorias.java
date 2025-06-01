package Interfaz;

import Persona.Administrador;
import Tiquetes.CategoriaTiquete;
import Tiquetes.GestorTiquetes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaConsultaCategorias extends JFrame {

    public VentanaConsultaCategorias(Administrador administrador) {
        setTitle("Consulta de Categorías de Tiquetes");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ArrayList<String> nombresCategorias = new ArrayList<>();
        try {
            ArrayList<CategoriaTiquete> categorias = GestorTiquetes.getInstancia().getCategoriasDisponibles();
            if (categorias == null || categorias.isEmpty()) {
                nombresCategorias.add("No hay categorías disponibles.");
            } else {
                for (CategoriaTiquete categoria : categorias) {
                    nombresCategorias.add(categoria.getNombre());
                }
            }
        } catch (Exception e) {
            nombresCategorias.add("Error al cargar categorías.");
        }

        JList<String> listaCategorias = new JList<>(nombresCategorias.toArray(new String[0]));
        listaCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaCategorias);

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setPreferredSize(new Dimension(100, 40));
        btnRegresar.setBackground(new Color(202, 252, 5));
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnRegresar);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        add(panelPrincipal);
        setVisible(true);
    }
}
