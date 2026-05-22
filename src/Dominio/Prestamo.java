package Dominio;

import java.time.LocalDate;

public class Prestamo {

    private String id;
    private String carnetUsuario;
    private String codigoMaterial;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean devuelto;

    public Prestamo(String carnetUsuario, String codigoMaterial) {
        this.id = generarId();
        this.carnetUsuario = carnetUsuario;
        this.codigoMaterial = codigoMaterial;
        this.fechaPrestamo = LocalDate.now();
        this.devuelto = false;
    }

    private String generarId() {
        return "P-" + System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public String getCarnetUsuario() {
        return carnetUsuario;
    }

    public void setCarnetUsuario(String carnetUsuario) {
        this.carnetUsuario = carnetUsuario;
    }

    public String getCodigoMaterial() {
        return codigoMaterial;
    }

    public void setCodigoMaterial(String codigoMaterial) {
        this.codigoMaterial = codigoMaterial;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public String toString() {
        return id + " - " + carnetUsuario + " -> " + codigoMaterial;
    }
}