package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class VentanaPrestamos extends JFrame {

    public VentanaPrestamos() {

        setTitle("Prestamos");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Modulo de Prestamos");
        label.setBounds(120, 100, 200, 30);

        setLayout(null);

        add(label);
    }
}