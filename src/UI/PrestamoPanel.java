package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import Dominio.Material;
import Dominio.Prestamo;
import Dominio.Usuario;
import Servicio.Biblioteca;

public class PrestamoPanel extends JPanel {

    private MainFrame frame;
    private JTable tablaTodos, tablaActivos;
    private DefaultTableModel modeloTodos, modeloActivos;
      
    private JComboBox<Object> cbCarnet, cbCodigoPrestamo, cbCodigoDevolucion;
    private JButton btnPrestar, btnDevolver;

    public PrestamoPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel north = new JPanel(new GridLayout(2, 1, 5, 5));
        
        JPanel prestamoPanel = new JPanel();
        cbCarnet = new JComboBox<>();
        cbCodigoPrestamo = new JComboBox<>();
        btnPrestar = new JButton("Prestar");
        
        cbCarnet.setPreferredSize(new Dimension(150, 25));
        cbCodigoPrestamo.setPreferredSize(new Dimension(150, 25));
        
        prestamoPanel.add(new JLabel("Prestamo - Carnet:"));
        prestamoPanel.add(cbCarnet);
        prestamoPanel.add(new JLabel("Codigo:"));
        prestamoPanel.add(cbCodigoPrestamo);
        prestamoPanel.add(btnPrestar);
        
        JPanel devolucionPanel = new JPanel();
        cbCodigoDevolucion = new JComboBox<>();
        btnDevolver = new JButton("Devolver");
        
        cbCodigoDevolucion.setPreferredSize(new Dimension(150, 25));
        
        devolucionPanel.add(new JLabel("Devolucion - Codigo:"));
        devolucionPanel.add(cbCodigoDevolucion);
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
        split.setDividerLocation(150);
        add(split, BorderLayout.CENTER);

        btnPrestar.addActionListener(e -> prestar());
        btnDevolver.addActionListener(e -> devolver());
        
        cargarTablas();
    }

    private void prestar() {
        Object carnetObj = cbCarnet.getSelectedItem();
        Object codigoObj = cbCodigoPrestamo.getSelectedItem();
        
        if (carnetObj == null || codigoObj == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un carnet y un codigo.");
            return;
        }
        
        String carnet = String.valueOf(carnetObj);
        String codigo = String.valueOf(codigoObj);
        
        Biblioteca bib = frame.getBiblioteca();
        boolean ok = bib.prestarMaterial(carnet, codigo);
        
        if (ok) {
            cargarTablas();
            JOptionPane.showMessageDialog(this, "Prestamo realizado con exito");
        } else {
            JOptionPane.showMessageDialog(this, "Error al realizar el prestamo. Verifique disponibilidad y limites.");
        }
    }

    
    private void devolver() {
        Object codigoObj = cbCodigoDevolucion.getSelectedItem();
        
        if (codigoObj == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione el codigo a devolver.");
            return;
        }
        
        String codigo = String.valueOf(codigoObj);
        
        Biblioteca bib = frame.getBiblioteca();
        boolean ok = bib.devolverMaterial(codigo);
        
        if (ok) {
            cargarTablas();
            JOptionPane.showMessageDialog(this, "Devolucion realizada con exito");
        } else {
            JOptionPane.showMessageDialog(this, "Error al procesar la devolucion.");
        }
    }

    public void cargarTablas() {
        modeloTodos.setRowCount(0);
        modeloActivos.setRowCount(0);
        
        Biblioteca bib = frame.getBiblioteca();
        if (bib == null) return;

        if (bib.listarPrestamos() != null) {
            for (Prestamo p : bib.listarPrestamos()) {
                modeloTodos.addRow(new Object[]{
                    p.getId(), 
                    p.getCarnetUsuario(), 
                    p.getCodigoMaterial(), 
                    p.getFechaPrestamo(), 
                    p.isDevuelto() ? "Devuelto" : "Activo"
                });
            }
        }

        if (bib.prestamosActivos() != null) {
            for (Prestamo p : bib.prestamosActivos()) {
                Material m = bib.buscarMaterial(p.getCodigoMaterial());
                modeloActivos.addRow(new Object[]{
                    p.getCarnetUsuario(), 
                    p.getCodigoMaterial(),
                    m != null ? m.getTitulo() : "",
                    p.getFechaPrestamo(),
                    m != null ? m.diasPrestamoMaximo() : ""
                });
            }
        }

        actualizarListasDesplegables(bib);
    }

    private void actualizarListasDesplegables(Biblioteca bib) {
        cbCarnet.removeAllItems();
        cbCodigoPrestamo.removeAllItems();
        cbCodigoDevolucion.removeAllItems();

        if (bib.getUsuarios() != null) {
            for (Usuario u : bib.getUsuarios()) {
                cbCarnet.addItem(u.getCarnet());
            }
        }

        if (bib.getMateriales() != null) {
            for (Material m : bib.getMateriales()) {
                cbCodigoPrestamo.addItem(m.getID()); 
            }
        }

        if (bib.prestamosActivos() != null) {
            for (Prestamo p : bib.prestamosActivos()) {
                cbCodigoDevolucion.addItem(p.getCodigoMaterial());
            }
        }
    }
}