package Dominio;

import java.time.LocalDate;

public class Material {
	
   private String ID;
   public String titulo;
   private boolean disponible;
   private int cantidad;        // NUEVO: cantidad de copias
   
   public Material(String iD, String titulo, boolean disponible) {
	super();
	ID = iD;
	this.titulo = titulo;
	this.disponible = true;
	this.cantidad = 1;          // Por defecto 1
   }
   
   public Material(String iD, String titulo, boolean disponible, int cantidad) {
	super();
	ID = iD;
	this.titulo = titulo;
	this.cantidad = cantidad;
	this.disponible = cantidad > 0;
   }
   
   public String getID() { return ID; }
   
   public void setID(String iD) { ID = iD; }
   
   public String getTitulo() { return titulo; }
   
   public void setTitulo(String titulo) { this.titulo = titulo; }
   
   public boolean isDisponible() { return disponible; }
   
   public void setDisponible(boolean disponible) { this.disponible = disponible; }
   
   // NUEVOS métodos para cantidad
   public int getCantidad() { return cantidad; }
   
   public void setCantidad(int cantidad) { 
       this.cantidad = cantidad; 
       this.disponible = cantidad > 0;
   }
   
   public void decreaseCantidad() {
       if (cantidad > 0) {
           cantidad--;
           disponible = cantidad > 0;
       }
   }
   
   public void increaseCantidad() {
       cantidad++;
       disponible = true;
   }
  
   public int diasPrestamoMaximo() { return 7; }

   public boolean buscarPorTitulo(String titulo) {
       return this.titulo.toLowerCase().contains(titulo.toLowerCase());
   }
   
   @Override
   public String toString() {
       return ID + " - " + titulo;
   }
}