package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import Dominio.Material;
import Dominio.Libro;
import Dominio.Revista;
import Servicio.Biblioteca;

public class MaterialPanel extends JPanel {

    private MainFrame frame;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtCodigo, txtTitulo, txtAutor, txtAño;
    private JComboBox<String> cmbTipo;
    private JButton btnAgregar, btnEliminar, btnLimpiar;

    public MaterialPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnas = {"Codigo", "Titulo", "Tipo", "Autor", "Anio", "Disp."};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel labels = new JPanel(new GridLayout(0, 1, 5, 5));
        labels.add(new JLabel("Codigo:"));
        labels.add(new JLabel("Titulo:"));
        labels.add(new JLabel("Tipo:"));
        labels.add(new JLabel("Autor:"));
        labels.add(new JLabel("Anio:"));

        txtCodigo = new JTextField(15);
        txtTitulo = new JTextField(15);
        cmbTipo = new JComboBox<>(new String[]{"Libro", "Revista"});
        txtAutor = new JTextField(15);
        txtAño = new JTextField(15);
        
        cmbTipo.addActionListener(e -> {
            boolean esLibro = cmbTipo.getSelectedItem().equals("Libro");
            txtAutor.setEnabled(esLibro);
            txtAño.setEnabled(esLibro);
            if (!esLibro) { txtAutor.setText(""); txtAño.setText(""); }
        });
        
        JPanel fields = new JPanel(new GridLayout(0, 1, 5, 5));
        fields.add(txtCodigo);
        fields.add(txtTitulo);
        fields.add(cmbTipo);
        fields.add(txtAutor);
        fields.add(txtAño);

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
        try {
            String codigo = txtCodigo.getText().trim();
            String titulo = txtTitulo.getText().trim();
            
            if (codigo.isEmpty() || titulo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Codigo y titulo son obligatorios");
                return;
            }
            
            Biblioteca bib = frame.getBiblioteca();
            if (bib.buscarMaterial(codigo) != null) {
                JOptionPane.showMessageDialog(this, "Codigo duplicado");
                return;
            }
            
            Material m;
            if (cmbTipo.getSelectedItem().equals("Libro")) {
                String autor = txtAutor.getText().trim();
                int año = txtAño.getText().trim().isEmpty() ? 2024 : Integer.parseInt(txtAño.getText().trim());
                m = new Libro(codigo, titulo, true, autor, año);
            } else {
                m = new Revista(codigo, titulo, true, 1);
            }
            
            bib.agregarMaterial(m);
            cargarTabla();
            limpiar();
            JOptionPane.showMessageDialog(this, "Material agregado");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ano debe ser numerico");
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String codigo = (String) modelo.getValueAt(fila, 0);
            Biblioteca bib = frame.getBiblioteca();
            Material m = bib.buscarMaterial(codigo);
            
            if (m != null && !m.isDisponible()) {
                JOptionPane.showMessageDialog(this, "No se puede eliminar: esta prestado");
                return;
            }
            
            bib.getMateriales().remove(m);
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Material eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un material");
        }
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAño.setText("");
        cmbTipo.setSelectedIndex(0);
    }

    public void cargarTabla() {
        modelo.setRowCount(0);
        for (Material m : frame.getBiblioteca().listarMateriales()) {
            modelo.addRow(new Object[]{
                m.getID(), m.getTitulo(), m.getClass().getSimpleName(),
                (m instanceof Libro) ? ((Libro)m).getAutor() : "",
                (m instanceof Libro) ? ((Libro)m).getAño() : "",
                m.isDisponible() ? "Si" : "No"
            });
        }
    }
}