package Dominio;

import java.util.ArrayList;

public class Biblioteca {

    private ArrayList<Material> materiales;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Prestamo> prestamos;

    public Biblioteca() {

        materiales = new ArrayList<>();
        usuarios = new ArrayList<>();
        prestamos = new ArrayList<>();
    }

    public void agregarMaterial(Material material) {
        materiales.add(material);
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void agregarPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
    }

    public ArrayList<Material> getMateriales() {
        return materiales;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }
}