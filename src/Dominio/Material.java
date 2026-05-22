package Dominio;

import java.time.LocalDate;

public class Material {
	
   private String ID;
   public String titulo;
   private boolean disponible;
   public Material(String iD, String titulo, boolean disponible) {
	super();
	ID = iD;
	this.titulo = titulo;
	this.disponible = true;
   }
   public String getID() {
	return ID;
   }
   public void setID(String iD) {
	ID = iD;
   }
   public String getTitulo() {
	return titulo;
   }
   public void setTitulo(String titulo) {
	this.titulo = titulo;
   }
   public boolean isDisponible() {
	return disponible;
   }
   public void setDisponible(boolean disponible) {
	this.disponible = disponible;
   }
  
   public int diasPrestamoMaximo() {
	   return 7;
   }

   public boolean buscarPorTitulo(String titulo) {
       return this.titulo.toLowerCase().contains(titulo.toLowerCase());
   }
   
   @Override
   public String toString() {
       return ID + " - " + titulo;
   }
}
