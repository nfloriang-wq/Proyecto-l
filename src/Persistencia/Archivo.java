package Persistencia;

import java.io.*;
import java.util.*;
import Dominio.*;
import Servicio.Biblioteca;

public class Archivo {

    private static final String ARCHIVO_MATERIALES = "materiales.csv";
    private static final String ARCHIVO_USUARIOS = "usuarios.csv";
    private static final String ARCHIVO_PRESTAMOS = "prestamos.csv";

    // ==================== GUARDAR ====================
    
    public static void guardarTodo(Biblioteca bib) throws IOException {
        guardarMateriales(bib.getMateriales());
        guardarUsuarios(bib.getUsuarios());
        guardarPrestamos(bib.getPrestamos());
    }

    private static void guardarMateriales(ArrayList<Material> materiales) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_MATERIALES))) {
            pw.println("codigo,titulo,tipo,autor,año,cantidad,disponible");
            for (Material m : materiales) {
                String tipo = m.getClass().getSimpleName();
                String autor = "";
                String año = "";
                if (m instanceof Libro) {
                    autor = ((Libro) m).getAutor();
                    año = String.valueOf(((Libro) m).getAño());
                }
                pw.println(m.getID() + "," + m.getTitulo() + "," + tipo + "," + 
                          autor + "," + año + "," + m.getCantidad() + "," + 
                          m.isDisponible());
            }
        }
    }

    private static void guardarUsuarios(ArrayList<Usuario> usuarios) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_USUARIOS))) {
            pw.println("carnet,nombre,tipo");
            for (Usuario u : usuarios) {
                pw.println(u.getCarnet() + "," + u.getNombre() + "," + 
                          u.getClass().getSimpleName());
            }
        }
    }

    private static void guardarPrestamos(ArrayList<Prestamo> prestamos) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_PRESTAMOS))) {
            pw.println("id,carnetUsuario,codigoMaterial,fechaPrestamo,devuelto");
            for (Prestamo p : prestamos) {
                pw.println(p.getId() + "," + p.getCarnetUsuario() + "," + 
                          p.getCodigoMaterial() + "," + p.getFechaPrestamo() + "," + 
                          p.isDevuelto());
            }
        }
    }

    // ==================== CARGAR ====================
    
    public static void cargarTodo(Biblioteca bib) throws IOException {
        bib.getMateriales().clear();
        bib.getUsuarios().clear();
        bib.getPrestamos().clear();
        
        bib.setCargando(true);
        
        cargarMateriales(bib);
        cargarUsuarios(bib);
        cargarPrestamos(bib);
        
        bib.setCargando(false);
        
        System.out.println("Cargado: M=" + bib.cantidadMateriales() + 
                          " U=" + bib.cantidadUsuarios() + 
                          " P=" + bib.cantidadPrestamos());
    }

    private static void cargarMateriales(Biblioteca bib) throws IOException {
        File archivo = new File(ARCHIVO_MATERIALES);
        if (!archivo.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(",");
                if (p.length >= 6) {
                    String codigo = p[0];
                    String titulo = p[1];
                    String tipo = p[2];
                    int cantidad = Integer.parseInt(p[5]);
                    
                    Material m;
                    if (tipo.equals("Libro")) {
                        String autor = p[3];
                        int año = p[4].isEmpty() ? 2024 : Integer.parseInt(p[4]);
                        m = new Libro(codigo, titulo, true, autor, año, cantidad);
                    } else {
                        m = new Revista(codigo, titulo, true, 1, cantidad);
                    }
                    
                    if (p.length >= 7) {
                        m.setDisponible(Boolean.parseBoolean(p[6]));
                    }
                    
                    bib.agregarMaterialSinGuardar(m);
                }
            }
        }
    }

    private static void cargarUsuarios(Biblioteca bib) throws IOException {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(",");
                if (p.length >= 3) {
                    String carnet = p[0];
                    String nombre = p[1];
                    String tipo = p[2];
                    
                    Usuario u;
                    if (tipo.equals("Estudiante")) {
                        u = new Estudiante(carnet, nombre, new ArrayList<>());
                    } else {
                        u = new Docente(carnet, nombre, new ArrayList<>());
                    }
                    
                    bib.agregarUsuarioSinGuardar(u);
                }
            }
        }
    }

    private static void cargarPrestamos(Biblioteca bib) throws IOException {
        File archivo = new File(ARCHIVO_PRESTAMOS);
        if (!archivo.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(",");
                if (p.length >= 5) {
                    String carnet = p[1];
                    String codigo = p[2];
                    boolean devuelto = Boolean.parseBoolean(p[4]);
                    
                    Prestamo prestamo = new Prestamo(carnet, codigo);
                    bib.agregarPrestamoSinGuardar(prestamo);
                    
                    if (!devuelto) {
                        Usuario u = bib.buscarUsuario(carnet);
                        if (u != null) u.getPrestamosActivos().add(prestamo);
                        
                        Material m = bib.buscarMaterial(codigo);
                        if (m != null) m.decreaseCantidad();
                    }
                    
                    if (devuelto) prestamo.setDevuelto(true);
                }
            }
        }
    }
}