package Dominio;

public class Revista extends Material {

    private int edicion;

    public Revista(
            String codigo,
            String titulo,
            String autor,
            int edicion) {

        super(codigo, titulo, autor);

        this.edicion = edicion;
    }

    @Override
    public int diasPrestamoMaximo() {
        return 5;
    }

    public int getEdicion() {
        return edicion;
    }
}