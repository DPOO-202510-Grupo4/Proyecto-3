import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import Persona.Cliente;
import Persona.GestorPersonas;
import Tiquetes.CategoriaTiquete;
import Tiquetes.FastPass;
import Tiquetes.GestorTiquetes;
import Tiquetes.Tiquete;
import restricciones.Temporada;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Nota: tests comentados, es porque los metodos que prueban no han sido implementados completamente.
class GestorTiquetesTest {

    private GestorTiquetes gestor;
    private Cliente cliente;

    @BeforeEach
    void setUp() {

        gestor = GestorTiquetes.getInstancia();

        GestorPersonas.getInstance().registrarCliente("nombre", "loginTest", "123456", "01/01/2000");

        cliente = GestorPersonas.getInstance().buscarCliente("loginTest");

        ArrayList<String> atracciones = new ArrayList<>();
        atracciones.add("Montaña Rusa");
        gestor.crearCategoriaTiquete("General", atracciones, 50000.0);
    }

    @Test
    void testCrearTiqueteRegular() {
        Date fecha = new Date();
        Tiquete tiquete = gestor.crearTiqueteRegular(cliente, "General", fecha);
        assertNotNull(tiquete);
        assertEquals("Tiquete General", tiquete.getNombre());
    }

    @Test
    void testCrearTiqueteTemporada() {
        Date inicio = new Date(System.currentTimeMillis());
        Date fin = new Date(System.currentTimeMillis() + 86400000L);
        Temporada temporada = new Temporada(inicio, fin, "Vacaciones");

        gestor.cargarTemporada(inicio, fin, "Vacaciones");

        Tiquete tiquete = gestor.crearTiqueteTemporada(cliente, "General", temporada);
        assertNotNull(tiquete);
        assertEquals("Tiquete General", tiquete.getNombre());
    }

    @Test
    void testCrearFastPass() {
        Date fecha = new Date();
        FastPass fp = gestor.crearFastPass("FastPass Día", 70000.0, fecha, false, UUID.randomUUID().toString(), cliente.getLogin());
        assertNotNull(fp);
        assertEquals("FastPass Día", fp.getNombre());
    }

    /*@Test
    void testBuscarTiquetePorId() {
        Date fecha = new Date();
        Tiquete t = gestor.crearTiqueteRegular(cliente, "General", fecha);
        Tiquete encontrado = gestor.buscarTiquetePorId(t.getId());
        assertNotNull(encontrado);
        assertEquals(t.getId(), encontrado.getId());
    }*/

    @Test
    void testUsarTiquete() {
        Date fecha = new Date();
        Tiquete t = gestor.crearTiqueteRegular(cliente, "General", fecha);
        gestor.usarTiquete(t);
        assertTrue(t.isUsado());
    }

    @Test
    void testCrearCategoriaTiqueteExistenteActualiza() {
        ArrayList<String> nuevasAtracciones = new ArrayList<>();
        nuevasAtracciones.add("Carrusel");
        boolean actualizado = gestor.crearCategoriaTiquete("General", nuevasAtracciones, 60000.0);
        assertTrue(actualizado);

        CategoriaTiquete c = gestor.buscarCategoria("General");
        assertEquals(60000.0, c.getPrecioBase());
        assertTrue(c.getAtraccionesDisponibles().contains("Carrusel"));
    }

    @Test
    void testCrearTemporadaValida() {
        Date inicio = new Date();
        Date fin = new Date(System.currentTimeMillis() + 86400000L);
        gestor.crearTemporada(inicio, fin, "Semana Santa");
        assertEquals(1, gestor.obtenerTemporadas().size());
    }

    /*@Test
    void testGetTiquetesDeCliente() {
        Date fecha = new Date();
        gestor.crearTiqueteRegular(cliente, "General", fecha);
        ArrayList<Tiquete> tiquetes = gestor.getTiquetesDeCliente(cliente);
        assertFalse(tiquetes.isEmpty());
    }*/
}
