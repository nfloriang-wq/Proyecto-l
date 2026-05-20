package Dominio;

public class Libro extends Material {

    private int paginas;

    public Libro(
            String codigo,
            String titulo,
            String autor,
            int paginas) {

        super(codigo, titulo, autor);

        this.paginas = paginas;
    }

    @Override
    public int diasPrestamoMaximo() {
        return 10;
    }

    public int getPaginas() {
        return paginas;
    }
}