package Dominio;

public class Revista extends Material {

	private int noEdicion;

	public Revista(String iD, String titulo, boolean disponible, int noEdicion) {
		super(iD, titulo, disponible);
		this.noEdicion = noEdicion;
	}
	
	public Revista(String iD, String titulo, boolean disponible, int noEdicion, int cantidad) {
		super(iD, titulo, disponible, cantidad);
		this.noEdicion = noEdicion;
	}

	public int getNoEdicion() { return noEdicion; }
	public void setNoEdicion(int noEdicion) { this.noEdicion = noEdicion; }

	@Override
	public int diasPrestamoMaximo() { return 5; }
}
