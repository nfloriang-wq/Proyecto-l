package Dominio;

import java.util.ArrayList;

public class Usuario {

	private String carnet;
	private String nombre;
	private ArrayList<Prestamo> prestamosActivos;
	
	public Usuario(String carnet, String nombre, ArrayList<Prestamo> prestamosActivos) {
		super();
		this.carnet = carnet;
		this.nombre = nombre;
		this.prestamosActivos = new ArrayList<>();
	}

	public String getCarnet() {
		return carnet;
	}

	public void setCarnet(String carnet) {
		this.carnet = carnet;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Prestamo> getPrestamosActivos() {
		return prestamosActivos;
	}

	public void setPrestamosActivos(ArrayList<Prestamo> prestamosActivos) {
		this.prestamosActivos = prestamosActivos;
	}
	
	public int prestamosMaximos() {
		return 3;
	}
	
	@Override
    public String toString() {
        return carnet + " - " + nombre;
	}
}
