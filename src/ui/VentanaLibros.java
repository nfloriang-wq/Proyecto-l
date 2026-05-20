package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class VentanaLibros extends JFrame {

    public VentanaLibros() {

        setTitle("Gestion de Libros");

        setSize(400, 300);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Ventana de Libros");

        add(label);
    }
}