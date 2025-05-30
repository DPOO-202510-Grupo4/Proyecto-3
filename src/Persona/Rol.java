package Persona;

import java.util.Set;

public class Rol {
    private String nombre;
    private Set<String> permisosPermitidos;

    public Rol(String nombre, Set<String> permisosPermitidos) {
        this.nombre = nombre;
        this.permisosPermitidos = permisosPermitidos;

    }

    public boolean tienePermiso(String permiso) {
        return permisosPermitidos.contains(permiso);
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<String> getPermisosPermitidos() {
		return permisosPermitidos;
	}

	public void setPermisosPermitidos(Set<String> permisosPermitidos) {
		this.permisosPermitidos = permisosPermitidos;
	}


	@Override
    public String toString() {
        String str = nombre + " - Permisos: ";
        for (String permiso: this.permisosPermitidos) {
            str += permiso.toString() + ", ";
        }
        return str;
    }
}
