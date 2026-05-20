package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class VentanaUsuarios extends JFrame {

    public VentanaUsuarios() {

        setTitle("Gestion de Usuarios");

        setSize(400, 300);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Ventana de Usuarios");

        add(label);
    }
}
