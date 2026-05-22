package Servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import Dominio.Material;
import Dominio.Prestamo;
import Dominio.Usuario;
import Persistencia.Archivo;

public class Biblioteca {

    private ArrayList<Material> materiales;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Prestamo> prestamos;
    private boolean cargando = false;

    public Biblioteca() {
        materiales = new ArrayList<>();
        usuarios = new ArrayList<>();
        prestamos = new ArrayList<>();
    }

    public ArrayList<Material> getMateriales() { return materiales; }
    public ArrayList<Usuario> getUsuarios() { return usuarios; }
    public ArrayList<Prestamo> getPrestamos() { return prestamos; }

    public boolean isCargando() { return cargando; }
    public void setCargando(boolean valor) { this.cargando = valor; }

    // Agregar SIN guardar (para carga)
    public boolean agregarMaterialSinGuardar(Material material) {
        for (Material m : materiales) {
            if (m.getID().equalsIgnoreCase(material.getID())) return false;
        }
        return materiales.add(material);
    }

    public boolean agregarUsuarioSinGuardar(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (u.getCarnet().equalsIgnoreCase(usuario.getCarnet())) return false;
        }
        return usuarios.add(usuario);
    }

    public boolean agregarPrestamoSinGuardar(Prestamo prestamo) {
        return prestamos.add(prestamo);
    }

    // Agregar CON guardar (para UI)
    public boolean agregarMaterial(Material material) {
        if (cargando) return agregarMaterialSinGuardar(material);
        
        for (Material m : materiales) {
            if (m.getID().equalsIgnoreCase(material.getID())) return false;
        }
        boolean resultado = materiales.add(material);
        if (resultado) guardarAutomatico();
        return resultado;
    }

    public boolean agregarUsuario(Usuario usuario) {
        if (cargando) return agregarUsuarioSinGuardar(usuario);
        
        for (Usuario u : usuarios) {
            if (u.getCarnet().equalsIgnoreCase(usuario.getCarnet())) return false;
        }
        boolean resultado = usuarios.add(usuario);
        if (resultado) guardarAutomatico();
        return resultado;
    }
    
    private void guardarAutomatico() {
        try {
            Archivo.guardarTodo(this);
            System.out.println("Guardado");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Material buscarMaterial(String id) {
        for (Material m : materiales) {
            if (m.getID().equalsIgnoreCase(id)) return m;
        }
        return null;
    }

    public Usuario buscarUsuario(String carnet) {
        for (Usuario u : usuarios) {
            if (u.getCarnet().equalsIgnoreCase(carnet)) return u;
        }
        return null;
    }

    public boolean prestarMaterial(String carnet, String id) {
        Usuario usuario = buscarUsuario(carnet);
        Material material = buscarMaterial(id);

        if (usuario == null || material == null) return false;
        if (!material.isDisponible()) return false;
        if (usuario.getPrestamosActivos().size() >= usuario.prestamosMaximos()) return false;

        material.decreaseCantidad();
        
        Prestamo prestamo = new Prestamo(carnet, id);
        boolean resultado = prestamos.add(prestamo);
        usuario.getPrestamosActivos().add(prestamo);
        
        if (resultado) guardarAutomatico();
        return resultado;
    }

    public boolean devolverMaterial(String id) {
        Prestamo prestamoEncontrado = null;

        for (Prestamo prestamo : prestamos) {
            if (prestamo.getCodigoMaterial().equalsIgnoreCase(id) && !prestamo.isDevuelto()) {
                prestamoEncontrado = prestamo;
                break;
            }
        }

        if (prestamoEncontrado == null) return false;

        Material material = buscarMaterial(id);
        if (material != null) material.increaseCantidad();

        String carnet = prestamoEncontrado.getCarnetUsuario();
        Usuario usuario = buscarUsuario(carnet);
        if (usuario != null) usuario.getPrestamosActivos().remove(prestamoEncontrado);

        prestamoEncontrado.setDevuelto(true);
        prestamoEncontrado.setFechaDevolucion(LocalDate.now());
        prestamos.remove(prestamoEncontrado);
        
        guardarAutomatico();
        return true;
    }

    public ArrayList<Material> listarMateriales() { return new ArrayList<>(materiales); }
    public ArrayList<Usuario> listarUsuarios() { return new ArrayList<>(usuarios); }
    public ArrayList<Prestamo> listarPrestamos() { return new ArrayList<>(prestamos); }

    public ArrayList<Prestamo> prestamosActivos() {
        ArrayList<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (!p.isDevuelto()) activos.add(p);
        }
        return activos;
    }

    public int cantidadMateriales() { return materiales.size(); }
    public int cantidadUsuarios() { return usuarios.size(); }
    public int cantidadPrestamos() { return prestamos.size(); }
}