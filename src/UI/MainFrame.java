package UI;

import javax.swing.*;
import java.awt.event.*;
import Servicio.Biblioteca;
import Persistencia.Archivo;

public class MainFrame extends JFrame {

    private Biblioteca biblioteca;
    private JTabbedPane pestanas;
    private MaterialPanel panelMateriales;
    private UsuarioPanel panelUsuarios;
    private PrestamoPanel panelPrestamos;

    public MainFrame() {
        biblioteca = new Biblioteca();
        
        setTitle("Biblioteca 2.0");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            Archivo.cargarTodo(biblioteca);
        } catch (Exception e) {
            System.out.println("Sin datos guardados");
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        
        JMenuItem guardar = new JMenuItem("Guardar ahora");
        
        guardar.addActionListener(e -> {
            try {
                Archivo.guardarTodo(biblioteca);
                JOptionPane.showMessageDialog(this, "Datos guardados");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        menu.add(guardar);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        pestanas = new JTabbedPane();
        panelMateriales = new MaterialPanel(this);
        panelUsuarios = new UsuarioPanel(this);
        panelPrestamos = new PrestamoPanel(this);
        
        pestanas.addTab("Materiales", panelMateriales);
        pestanas.addTab("Usuarios", panelUsuarios);
        pestanas.addTab("Prestamos", panelPrestamos);
        
        add(pestanas);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Archivo.guardarTodo(biblioteca);
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
                System.exit(0);
            }
        });
    }

    public Biblioteca getBiblioteca() { return biblioteca; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}