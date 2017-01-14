package bin;

import javax.swing.*;

import main.GUI.PanelManual;

import java.awt.*;
import java.awt.event.*;

public class InsulinGlucagonPump extends JFrame {

    protected static final Component PanelManual = null;

    public InsulinGlucagonPump() {

        JButton Manually;
        JButton Automatic;

        setTitle("Insulin/Glucagon Pump");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        setLayout(new BorderLayout());
        setContentPane(new JLabel(new ImageIcon("C:\\Users\\Hardik\\Desktop\\insulin.jpg")));
        setLayout(new FlowLayout());

        Automatic = new JButton("Automatic");
        Manually = new JButton("Manually");

        add(Manually);
        add(Automatic);

        // Just for refresh :) Not optional!
        setSize(399, 399);
        setSize(400, 400);

        Automatic.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new BloodSugar();
                new Monitor();
                new GUI("Auto");
            }
        });

        Manually.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                new BloodSugar();
                new Monitor();
                new GUI("Manual");
            }

        });

    }

    public static void main(String args[]) {

        new InsulinGlucagonPump();

    }

}