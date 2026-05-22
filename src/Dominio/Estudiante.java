package Dominio;

import java.util.ArrayList;

public class Estudiante extends Usuario {

	public Estudiante(String carnet, String nombre, ArrayList<Prestamo> prestamosActivos) {
		super(carnet, nombre, prestamosActivos);
	}

	@Override
	public int prestamosMaximos() {
		return 3;
	}
}
