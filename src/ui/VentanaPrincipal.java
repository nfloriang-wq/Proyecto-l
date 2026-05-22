package ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private JPanel panel;

    private JButton btnLibros;
    private JButton btnUsuarios;
    private JButton btnPrestamos;

    public VentanaPrincipal() {

        setTitle("Biblioteca 2.0");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel titulo = new JLabel("SISTEMA BIBLIOTECA 2.0");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBounds(90, 40, 350, 40);

        btnLibros = new JButton("Gestionar Libros");
        btnLibros.setBounds(140, 120, 200, 40);

        btnUsuarios = new JButton("Gestionar Usuarios");
        btnUsuarios.setBounds(140, 180, 200, 40);

        btnPrestamos = new JButton("Prestamos");
        btnPrestamos.setBounds(140, 240, 200, 40);

        panel.add(titulo);
        panel.add(btnLibros);
        panel.add(btnUsuarios);
        panel.add(btnPrestamos);

        add(panel);

        btnLibros.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                VentanaLibros ventana = new VentanaLibros();
                ventana.setVisible(true);
            }
        });

        btnUsuarios.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                VentanaUsuarios ventana = new VentanaUsuarios();
                ventana.setVisible(true);
            }
        });

        btnPrestamos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                VentanaPrestamos ventana = new VentanaPrestamos();
                ventana.setVisible(true);
            }
        });
    }
}