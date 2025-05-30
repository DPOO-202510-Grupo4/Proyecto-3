package presentacion;

import java.text.ParseException;
import java.util.Scanner;

import Persistencias.GestorPersistencia;
import Persona.GestorPersonas;
import Persona.GestorPersonas.TipoUsuario;

public class ConsolaLogin {

    public void registrarCuentaCliente() {
        Scanner scanner = new Scanner(System.in);
        GestorPersonas gestorPersonas = GestorPersonas.getInstance();

        while (true) {
            System.out.println("\n--- CREAR CUENTA ---");
            System.out.println("Ingrese 0 en cualquier momento para volver al menú principal.");

            System.out.print("Ingrese su nombre completo: ");
            String nombre = scanner.nextLine();
            if (nombre.equals("0")) return;

            System.out.print("Ingrese su login (usuario): ");
            String login = scanner.nextLine();
            if (login.equals("0")) return;

            System.out.print("Ingrese su contraseña: ");
            String contrasena = scanner.nextLine();
            if (contrasena.equals("0")) return;

            System.out.print("Ingrese su fecha de nacimiento (dd/MM/yyyy): ");
            String fechaNacimiento = scanner.nextLine();
            if (fechaNacimiento.equals("0")) return;

            if (gestorPersonas.buscarCliente(login) != null) {
                System.out.println("Ya existe una cuenta con ese login.");
                return;
            } else {
                gestorPersonas.registrarCliente(nombre, login, contrasena, fechaNacimiento);
                System.out.println("¡Cuenta creada exitosamente!");
                return;
            }
        }
    }

    public void iniciarSesion() {
        Scanner scanner = new Scanner(System.in);
        GestorPersonas gestor = GestorPersonas.getInstance();

        System.out.println("\n--- INICIAR SESIÓN ---");
        System.out.println("Ingrese 0 como usuario para volver al menú principal.");

        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        if (usuario.equals("0")) return;

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        TipoUsuario tipo = gestor.validarLogin(usuario, contrasena);

        switch (tipo) {
            case ADMINISTRADOR:
                System.out.println("Bienvenido Administrador.");
                consolaAdministrador.menuAdministrador(scanner);
                break;
            case EMPLEADO:
                System.out.println("Bienvenido Empleado.");
                consolaEmpleado.menuEmpleado(scanner);
                break;
            case CLIENTE:
                System.out.println("Bienvenido Cliente.");
                consolaCliente.menuCliente(scanner);
                break;
            case NO_ENCONTRADO:
                System.out.println("Usuario o contraseña incorrectos.");
                break;
        }
    }

}
