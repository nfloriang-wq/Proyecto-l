package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import Dominio.Usuario;
import Dominio.Estudiante;
import Dominio.Docente;
import Servicio.Biblioteca;

public class UsuarioPanel extends JPanel {

    private MainFrame frame;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtCarnet, txtNombre;
    private JComboBox<String> cmbTipo;
    private JButton btnAgregar, btnEliminar, btnLimpiar;

    public UsuarioPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"Carnet", "Nombre", "Tipo", "Prestamos"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel labels = new JPanel(new GridLayout(0, 1, 5, 5));
        labels.add(new JLabel("Carnet:"));
        labels.add(new JLabel("Nombre:"));
        labels.add(new JLabel("Tipo:"));

        txtCarnet = new JTextField(15);
        txtNombre = new JTextField(15);
        cmbTipo = new JComboBox<>(new String[]{"Estudiante", "Docente"});
        
        JPanel fields = new JPanel(new GridLayout(0, 1, 5, 5));
        fields.add(txtCarnet);
        fields.add(txtNombre);
        fields.add(cmbTipo);

        btnAgregar = new JButton("Agregar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        
        btnAgregar.addActionListener(e -> agregar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
        
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btns.add(btnAgregar);
        btns.add(btnEliminar);
        btns.add(btnLimpiar);

        JPanel form = new JPanel(new BorderLayout(5, 5));
        form.add(labels, BorderLayout.WEST);
        form.add(fields, BorderLayout.CENTER);
        form.add(btns, BorderLayout.SOUTH);
        
        add(form, BorderLayout.SOUTH);
        cargarTabla();
    }

    private void agregar() {
        String carnet = txtCarnet.getText().trim();
        String nombre = txtNombre.getText().trim();
        
        if (carnet.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carnet y nombre son obligatorios");
            return;
        }
        
        Biblioteca bib = frame.getBiblioteca();
        if (bib.buscarUsuario(carnet) != null) {
            JOptionPane.showMessageDialog(this, "Carnet duplicado");
            return;
        }
        
        Usuario u;
        if (cmbTipo.getSelectedItem().equals("Estudiante")) {
            u = new Estudiante(carnet, nombre, new ArrayList<>());
        } else {
            u = new Docente(carnet, nombre, new ArrayList<>());
        }
        
        bib.agregarUsuario(u);
        cargarTabla();
        limpiar();
        JOptionPane.showMessageDialog(this, "Usuario agregado");
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String carnet = (String) modelo.getValueAt(fila, 0);
            Biblioteca bib = frame.getBiblioteca();
            Usuario u = bib.buscarUsuario(carnet);
            
            if (u != null && u.getPrestamosActivos().size() > 0) {
                JOptionPane.showMessageDialog(this, "No se puede eliminar: tiene prestamos");
                return;
            }
            
            bib.getUsuarios().remove(u);
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Usuario eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
        }
    }

    private void limpiar() {
        txtCarnet.setText("");
        txtNombre.setText("");
        cmbTipo.setSelectedIndex(0);
    }

    public void cargarTabla() {
        modelo.setRowCount(0);
        for (Usuario u : frame.getBiblioteca().listarUsuarios()) {
            modelo.addRow(new Object[]{
                u.getCarnet(), u.getNombre(), u.getClass().getSimpleName(),
                u.getPrestamosActivos().size() + "/" + u.prestamosMaximos()
            });
        }
    }
}