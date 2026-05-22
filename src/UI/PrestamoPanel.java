package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import Dominio.Material;
import Dominio.Prestamo;
import Servicio.Biblioteca;

public class PrestamoPanel extends JPanel {

    private MainFrame frame;
    private JTable tablaTodos, tablaActivos;
    private DefaultTableModel modeloTodos, modeloActivos;
    private JTextField txtCarnet, txtCodigo, txtDevolucion;
    private JButton btnPrestar, btnDevolver;

    public PrestamoPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel north = new JPanel(new GridLayout(2, 1, 5, 5));
        
        JPanel prestamoPanel = new JPanel();
        txtCarnet = new JTextField(10);
        txtCodigo = new JTextField(10);
        btnPrestar = new JButton("Prestar");
        
        prestamoPanel.add(new JLabel("Prestamo - Carnet:"));
        prestamoPanel.add(txtCarnet);
        prestamoPanel.add(new JLabel("Codigo:"));
        prestamoPanel.add(txtCodigo);
        prestamoPanel.add(btnPrestar);
        
        JPanel devolucionPanel = new JPanel();
        txtDevolucion = new JTextField(10);
        btnDevolver = new JButton("Devolver");
        
        devolucionPanel.add(new JLabel("Devolucion - Codigo:"));
        devolucionPanel.add(txtDevolucion);
        devolucionPanel.add(btnDevolver);
        
        north.add(prestamoPanel);
        north.add(devolucionPanel);
        add(north, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        modeloTodos = new DefaultTableModel(
            new String[]{"ID", "Usuario", "Material", "Fecha", "Estado"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaTodos = new JTable(modeloTodos);
        
        modeloActivos = new DefaultTableModel(
            new String[]{"Usuario", "Material", "Titulo", "Fecha", "Dias max."}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaActivos = new JTable(modeloActivos);
        
        split.setTopComponent(new JScrollPane(tablaTodos));
        split.setBottomComponent(new JScrollPane(tablaActivos));
        add(split, BorderLayout.CENTER);

        btnPrestar.addActionListener(e -> prestar());
        btnDevolver.addActionListener(e -> devolver());
        
        cargarTablas();
    }

    private void prestar() {
        String carnet = txtCarnet.getText().trim();
        String codigo = txtCodigo.getText().trim();
        
        if (carnet.isEmpty() || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese carnet y codigo");
            return;
        }
        
        Biblioteca bib = frame.getBiblioteca();
        boolean ok = bib.prestarMaterial(carnet, codigo);
        
        if (ok) {
            txtCarnet.setText("");
            txtCodigo.setText("");
            cargarTablas();
            JOptionPane.showMessageDialog(this, "Prestamo realizado");
        } else {
            JOptionPane.showMessageDialog(this, "Error: Verifique usuario/material o limite");
        }
    }

    private void devolver() {
        String codigo = txtDevolucion.getText().trim();
        
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese codigo");
            return;
        }
        
        Biblioteca bib = frame.getBiblioteca();
        boolean ok = bib.devolverMaterial(codigo);
        
        if (ok) {
            txtDevolucion.setText("");
            cargarTablas();
            JOptionPane.showMessageDialog(this, "Devolucion realizada");
        } else {
            JOptionPane.showMessageDialog(this, "Error: Prestamo no encontrado");
        }
    }

    public void cargarTablas() {
        modeloTodos.setRowCount(0);
        modeloActivos.setRowCount(0);
        
        Biblioteca bib = frame.getBiblioteca();
        
        for (Prestamo p : bib.listarPrestamos()) {
            modeloTodos.addRow(new Object[]{
                p.getId(), p.getCarnetUsuario(), p.getCodigoMaterial(),
                p.getFechaPrestamo(), p.isDevuelto() ? "Devuelto" : "Activo"
            });
        }
        
        for (Prestamo p : bib.prestamosActivos()) {
            Material m = bib.buscarMaterial(p.getCodigoMaterial());
            modeloActivos.addRow(new Object[]{
                p.getCarnetUsuario(), p.getCodigoMaterial(),
                m != null ? m.getTitulo() : "",
                p.getFechaPrestamo(),
                m != null ? m.diasPrestamoMaximo() : ""
            });
        }
    }
}