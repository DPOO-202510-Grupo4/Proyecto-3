package Interfaz;

import Persona.Administrador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class FAdministrador extends JFrame implements ActionListener{

    private Administrador administrador;

    public FAdministrador(Administrador administrador) {
        this.administrador = administrador;

}
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
