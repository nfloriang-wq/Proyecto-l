package Dominio;

public class Prestamo {

    private Usuario usuario;
    private Material material;
    private String fechaPrestamo;

    public Prestamo(
            Usuario usuario,
            Material material,
            String fechaPrestamo) {

        this.usuario = usuario;
        this.material = material;
        this.fechaPrestamo = fechaPrestamo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Material getMaterial() {
        return material;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void mostrarPrestamo() {

        System.out.println("===== PRESTAMO =====");

        System.out.println("Usuario: "
                + usuario.getNombre());

        System.out.println("Material: "
                + material.getTitulo());

        System.out.println("Autor: "
                + material.getAutor());

        System.out.println("Fecha: "
                + fechaPrestamo);

        System.out.println("Dias maximos: "
                + material.diasPrestamoMaximo());
    }
}