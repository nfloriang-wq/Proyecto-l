package Dominio;

public class Libro extends Material {

	private String autor;
	private int año;
	
	public Libro(String iD, String titulo, boolean disponible, String autor, int año) {
		super(iD, titulo, disponible);
		this.autor = autor;
		this.año = año;
	}
	
	public Libro(String iD, String titulo, boolean disponible, String autor, int año, int cantidad) {
		super(iD, titulo, disponible, cantidad);
		this.autor = autor;
		this.año = año;
	}
	
	public String getAutor() { return autor; }
	public void setAutor(String autor) { this.autor = autor; }
	public int getAño() { return año; }
	public void setAño(int año) { this.año = año; }
	
	@Override
	public int diasPrestamoMaximo() { return 10; }
}