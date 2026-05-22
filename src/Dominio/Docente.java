package Dominio;

import java.util.ArrayList;

public class Docente extends Usuario {

	public Docente(String carnet, String nombre, ArrayList<Prestamo> prestamosActivos) {
		super(carnet, nombre, prestamosActivos);
	}

	@Override
	public int prestamosMaximos() {
		return 3;
	}	
}
