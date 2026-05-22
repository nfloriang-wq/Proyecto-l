package UI;

import javax.swing.*;
import Servicio.Biblioteca;

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

        pestanas = new JTabbedPane();
        panelMateriales = new MaterialPanel(this);
        panelUsuarios = new UsuarioPanel(this);
        panelPrestamos = new PrestamoPanel(this);
        
        pestanas.addTab("Materiales", panelMateriales);
        pestanas.addTab("Usuarios", panelUsuarios);
        pestanas.addTab("Prestamos", panelPrestamos);
        
        add(pestanas);
    }

    public Biblioteca getBiblioteca() { return biblioteca; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}