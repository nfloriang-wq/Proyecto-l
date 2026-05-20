package Dominio;

public class Usuario {

    private String nombre;
    private String carnet;
    private String correo;

    public Usuario(
            String nombre,
            String carnet,
            String correo) {

        this.nombre = nombre;
        this.carnet = carnet;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCarnet() {
        return carnet;
    }

    public String getCorreo() {
        return correo;
    }
}