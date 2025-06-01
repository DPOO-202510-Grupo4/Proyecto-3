package Interfaz;

import Atracciones.*;
import Persona.*;
import Tiquetes.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class FEmpleado extends JFrame implements ActionListener {
    private Empleado empleado;

    public FEmpleado(Empleado empleado) {
        this.empleado = empleado;
        setTitle("Panel de Empleado"); 
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6,1,10,10));

        JButton btnTurnos = new JButton("Consultar Turnos");
        JButton btnTareas = new JButton("Consultar Tareas");
        JButton btnCapacitaciones = new JButton("Consultar Capacitaciones");
        JButton btnRegistrarVenta = new JButton("Registrar Venta");
        JButton btnRegistrarTiquete = new JButton("Registrar Tiquete");
        JButton btnCerrar = new JButton("Cerrar Sesión");

        panel.add(btnTurnos);
        panel.add(btnTareas);
        panel.add(btnCapacitaciones);
        panel.add(btnRegistrarVenta);
        panel.add(btnRegistrarTiquete);
        panel.add(btnCerrar);

        add(panel);

        //btnTurnos.addActionListener(e -> mostrarTurnos());
        //btnTareas.addActionListener(e -> mostrarTareas());
        btnCapacitaciones.addActionListener(e -> mostrarCapacitaciones());
        btnRegistrarVenta.addActionListener(e -> registrarVenta());
        btnRegistrarTiquete.addActionListener(e -> registrarTiquete());
        btnCerrar.addActionListener(e -> {
            dispose();
            new FLogin().setVisible(true);
        });
    }   

    /* private void mostrarTurnos() {
        ArrayList<Turno> turnos = GestorPersonas.getInstance().turnosDeEmpleado(empleado);
        if (turnos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes turnos asignados.");
            return;
        }
        StringBuilder sb = new StringBuilder("Tus turnos:\n");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (Turno t : turnos) {
            sb.append("- ").append(sdf.format(t.getInicio())).append(" a ").append(sdf.format(t.getFin())).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void mostrarTareas() {
        ArrayList<String> tareas = empleado.getTareas();
        if (tareas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes tareas asignadas.");
            return;
        }
        StringBuilder sb = new StringBuilder("Tus tareas:\n");
        for (String t : tareas) {
            sb.append("- ").append(t).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    } */

    private void mostrarCapacitaciones() {
        Capacitaciones c = empleado.getCapacitaciones();
        if (c == null) {
            JOptionPane.showMessageDialog(this, "No tienes capacitaciones registradas.");
            return;
        }
        StringBuilder sb = new StringBuilder("Capacitaciones:\n");
        sb.append("Cocinero: ").append(c.getEsCocinero()).append("\n");
        sb.append("Cajero: ").append(c.getEsCajero()).append("\n");
        sb.append("Atracciones:\n");
        for (Atraccion a : c.getCapacitacionAtracciones()) {
            sb.append("- ").append(a.getNombre()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    } 

    private void registrarVenta() {
        GestorPersonas gestorPersonas = GestorPersonas.getInstance();
        HashMap<String, Cliente> clientesMap = gestorPersonas.getClientes();

        if (clientesMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay clientes registrados.");
            return;
        }

        ArrayList<Cliente> clientes = new ArrayList<>(clientesMap.values());

        String[] nombresClientes = new String[clientes.size()];
        for (int i = 0; i < clientes.size(); i++) {
            nombresClientes[i] = clientes.get(i).getNombre();
        }

        String nombreSeleccionado = (String) JOptionPane.showInputDialog(
            this,
            "Seleccione un cliente:",
            "Seleccionar Cliente",
            JOptionPane.QUESTION_MESSAGE,
            null,
            nombresClientes,
            nombresClientes[0]
        );

        if (nombreSeleccionado == null) {
            return;
        }

        Cliente clienteSeleccionado = null;
        for (Cliente c : clientes) {
            if (c.getNombre().equals(nombreSeleccionado)) {
                clienteSeleccionado = c;
                break;
            }
        }

        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
            return;
        }

        FCliente ventanaCliente = new FCliente(clienteSeleccionado);
        ventanaCliente.mostrarDialogoCompra();
    }



    private void registrarTiquete() {
        String id = JOptionPane.showInputDialog(this, "Ingrese el ID del tiquete:");
        if (id == null || id.isBlank()) return;

        GestorTiquetes gestor = GestorTiquetes.getInstancia();
        Tiquete t = gestor.buscarTiquetePorId(id);
        if (t == null) {
            JOptionPane.showMessageDialog(this, "Tiquete no encontrado.");
        } else {
            gestor.usarTiquete(t);
            JOptionPane.showMessageDialog(this, "Tiquete registrado como usado.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}