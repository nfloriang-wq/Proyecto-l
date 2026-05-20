package Dominio;

public abstract class Material {

    protected String codigo;
    protected String titulo;
    protected String autor;

    public Material(String codigo, String titulo, String autor) {

        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public abstract int diasPrestamoMaximo();
}