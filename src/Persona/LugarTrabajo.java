package Persona;

import java.util.Set;

public class LugarTrabajo {
    private String nombre;
    private String ubicacion;
    private Set<Rol> rolesPermitidos;

    public LugarTrabajo(String nombre, String ubicacion, Set<Rol> rolesPermitidos) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.rolesPermitidos = rolesPermitidos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Set<Rol> getRolesPermitidos() {
        return rolesPermitidos;
    }
    @Override
    public String toString() {
        String str = nombre + " - " + ubicacion + " - Roles: ";
        for (Rol rol : this.rolesPermitidos) {
            str += rol.getNombre() + ", ";
        }
        return str;
    }
}
