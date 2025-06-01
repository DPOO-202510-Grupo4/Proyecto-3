package Interfaz;

import Persona.Administrador;
import Persona.Empleado;
import Persona.GestorPersonas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class VentanaConsultaEmpleados extends JFrame {

    private JComboBox<String> comboEmpleados;
    private JTextArea areaDetalles;
    private HashMap<String, Empleado> empleados;

    public VentanaConsultaEmpleados(Administrador administrador) {
        setTitle("Consultar Empleados");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GestorPersonas gestor = GestorPersonas.getInstance();
        empleados = gestor.getEmpleados();

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        comboEmpleados = new JComboBox<>();
        for (String login : empleados.keySet()) {
            comboEmpleados.addItem(login);
        }

        areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);
        areaDetalles.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(areaDetalles);

        comboEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLogin = (String) comboEmpleados.getSelectedItem();
                if (selectedLogin != null && empleados.containsKey(selectedLogin)) {
                    Empleado seleccionado = empleados.get(selectedLogin);
                    String info = "Nombre: " + seleccionado.getNombre() +
                            "\nLogin: " + seleccionado.getLogin() +
                            "\nLugar de trabajo: " + seleccionado.getLugarTrabajo() +
                            "\nCapacitaciones: " + seleccionado.getCapacitaciones();
                    areaDetalles.setText(info);
                }
            }
        });

        if (!empleados.isEmpty()) {
            comboEmpleados.setSelectedIndex(0);
        }

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VentanaEmpleados(administrador);
                dispose();
            }
        });

        panel.add(comboEmpleados, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnRegresar, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
