import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Persona.*;
import Persistencias.*;
import Atracciones.*;
import restricciones.*;
import Tiquetes.*;

public class GestorPersonasTest {

    private GestorPersonas gestor;

    @BeforeEach
    public void setUp() {
        gestor = GestorPersonas.getInstance();
        gestor.getEmpleados().clear();
        gestor.getClientes().clear();
        gestor.getAdministradores().clear();
    }

    @Test
    public void testCrearYObtenerEmpleado() {
        Empleado empleado = gestor.crearEmpleadoBasico("David", "david123", "1234", "2000-01-01");
        assertEquals(empleado, gestor.obtenerEmpleadoPorLogin("david123"));
    }

    @Test
    public void testEliminarEmpleado() {
        gestor.crearEmpleadoBasico("Federico", "fede123", "1234", "1995-05-05");
        gestor.eliminarEmpleado("fede123");
        assertNull(gestor.obtenerEmpleadoPorLogin("fede123"));
    }

    @Test
    public void testRegistrarYBuscarCliente() {
        gestor.registrarCliente("Juliana", "juli123", "1234", "1990-12-12");
        Cliente cliente = gestor.buscarCliente("juli123");
        assertNotNull(cliente);
        assertEquals("Juliana", cliente.getNombre());
    }

    @Test
    public void testCrearYEliminarAdministrador() {
        gestor.crearAdministrador("Santiago", "santi123", "1234", "1988-07-07");
        assertNotNull(gestor.obtenerAdministrador("santi123"));
        gestor.eliminarAdministrador("santi123");
        assertNull(gestor.obtenerAdministrador("santi123"));
    }

    @Test
    public void testValidarLogin() {
        gestor.crearAdministrador("Max", "max123", "1234", "1980-02-02");
        gestor.crearEmpleadoBasico("Tony", "tony123", "1234", "1990-03-03");
        gestor.registrarCliente("Fiona", "fiona123", "1234", "1999-04-04");

        assertEquals(GestorPersonas.TipoUsuario.ADMINISTRADOR, gestor.validarLogin("max123", "1234"));
        assertEquals(GestorPersonas.TipoUsuario.EMPLEADO, gestor.validarLogin("tony123", "1234"));
        assertEquals(GestorPersonas.TipoUsuario.CLIENTE, gestor.validarLogin("fiona123", "1234"));
        assertEquals(GestorPersonas.TipoUsuario.NO_ENCONTRADO, gestor.validarLogin("fake", "1234"));
    }

    /*@Test
    public void testAsignarYConsultarTurnos() {
        Empleado empleado = gestor.crearEmpleadoBasico("Sara", "sara123", "clave", "2001-08-08");
        Date fecha = new Date(05, 05, 2025);
        Turno turno = new Turno(fecha, true, false, "Taquilla");

        gestor.asignarTurno("sara123", turno);

        ArrayList<Turno> turnos = gestor.turnosDeEmpleado(empleado);
        assertTrue(turnos.contains(turno));
    }*/

}