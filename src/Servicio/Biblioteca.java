package Servicio;

import java.time.LocalDate;
import java.util.ArrayList;
import Dominio.Material;
import Dominio.Prestamo;
import Dominio.Usuario;

public class Biblioteca {

    private ArrayList<Material> materiales;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Prestamo> prestamos;

    public Biblioteca() {
        materiales = new ArrayList<>();
        usuarios = new ArrayList<>();
        prestamos = new ArrayList<>();
    }

    // ==================== GETTERS ====================

    public ArrayList<Material> getMateriales() {
        return materiales;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }

    // ==================== AGREGAR ====================

    public boolean agregarMaterial(Material material) {
        for (Material m : materiales) {
            if (m.getID().equalsIgnoreCase(material.getID())) {
                return false;
            }
        }
        materiales.add(material);
        return true;
    }

    public boolean agregarUsuario(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (u.getCarnet().equalsIgnoreCase(usuario.getCarnet())) {
                return false;
            }
        }
        usuarios.add(usuario);
        return true;
    }

    // ==================== BUSCAR ====================

    public Material buscarMaterial(String id) {
        for (Material material : materiales) {
            if (material.getID().equalsIgnoreCase(id)) {
                return material;
            }
        }
        return null;
    }

    public Usuario buscarUsuario(String carnet) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCarnet().equalsIgnoreCase(carnet)) {
                return usuario;
            }
        }
        return null;
    }

    public ArrayList<Material> buscarPorTitulo(String titulo) {
        ArrayList<Material> resultados = new ArrayList<>();
        for (Material m : materiales) {
            if (m.buscarPorTitulo(titulo)) {
                resultados.add(m);
            }
        }
        return resultados;
    }

    // ==================== PRÉSTAMO ====================

    public boolean prestarMaterial(String carnet, String id) {
        Usuario usuario = buscarUsuario(carnet);
        Material material = buscarMaterial(id);

        if (usuario == null || material == null) {
            return false;
        }

        if (!material.isDisponible()) {
            return false;
        }

        if (usuario.getPrestamosActivos().size() >= usuario.prestamosMaximos()) {
            return false;
        }

        Prestamo prestamo = new Prestamo(carnet, id);

        prestamos.add(prestamo);
        usuario.getPrestamosActivos().add(prestamo);
        material.setDisponible(false);

        return true;
    }

    public boolean devolverMaterial(String id) {
        Prestamo prestamoEncontrado = null;

        for (Prestamo prestamo : prestamos) {
            if (prestamo.getCodigoMaterial().equalsIgnoreCase(id) && !prestamo.isDevuelto()) {
                prestamoEncontrado = prestamo;
                break;
            }
        }

        if (prestamoEncontrado == null) {
            return false;
        }

        Material material = buscarMaterial(id);
        if (material != null) {
            material.setDisponible(true);
        }

        String carnet = prestamoEncontrado.getCarnetUsuario();
        Usuario usuario = buscarUsuario(carnet);
        if (usuario != null) {
            usuario.getPrestamosActivos().remove(prestamoEncontrado);
        }

        prestamoEncontrado.setDevuelto(true);
        prestamoEncontrado.setFechaDevolucion(LocalDate.now());
        prestamos.remove(prestamoEncontrado);

        return true;
    }

    // ==================== CONSULTAS ====================

    public ArrayList<Material> listarMateriales() {
        return new ArrayList<>(materiales);
    }

    public ArrayList<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public ArrayList<Prestamo> listarPrestamos() {
        return new ArrayList<>(prestamos);
    }

    public ArrayList<Prestamo> prestamosActivos() {
        ArrayList<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (!p.isDevuelto()) {
                activos.add(p);
            }
        }
        return activos;
    }

    public ArrayList<Prestamo> prestamosActivosPorUsuario(String carnet) {
        Usuario usuario = buscarUsuario(carnet);
        if (usuario == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(usuario.getPrestamosActivos());
    }

    public int cantidadMateriales() {
        return materiales.size();
    }

    public int cantidadUsuarios() {
        return usuarios.size();
    }

    public int cantidadPrestamos() {
        return prestamos.size();
    }

    public int cantidadPrestamosActivos() {
        return prestamosActivos().size();
    }
}