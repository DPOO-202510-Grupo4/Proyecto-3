package ParqueDiversiones;

import Atracciones.*;
import Interfaz.FPrincipal;
import Persistencias.*;
import Persona.*;
import restricciones.*;
import Tiquetes.*;
import presentacion.ConsolaLogin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class ParqueDiversiones {

    public static void main(String[] args) throws ParseException {
        GestorPersistencia gestorPersistencia = GestorPersistencia.getInstance();
        gestorPersistencia.gestorCargaDatos();
        new FPrincipal();
        boolean volver = false;
        // Scanner scanner = new Scanner(System.in);
        ConsolaLogin login = new ConsolaLogin();

        while (!volver) {
            /*
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Crear Cuenta");
            System.out.println("3. Salir");

            String entrada = scanner.nextLine();

            switch (entrada) {
                case "1":
                    login.iniciarSesion();
                    break;
                case "2":
                    login.registrarCuentaCliente();
                    break;
                case "3":
                    volver = true;
                    System.out.println("¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
            */
            // Si quieres salir del ciclo directamente:
            volver = true; 
        }
        // scanner.close(); 
    }
}

