package bin;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.sun.java.swing.plaf.windows.resources.windows;

import main.GUI.PanelManual;
import sun.net.ProgressEvent;

import java.awt.*;
import java.awt.event.*;

public class InsulinGlucagonPump extends JFrame {

    protected static final Component PanelManual = null;

    public InsulinGlucagonPump() {

        JButton Manually;
        JButton Automatic;
        JButton Setting;
        
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
        Setting = new JButton("Setting");
        
        
      
      
        add(Manually);
        add(Automatic);
        add(Box.createRigidArea(new Dimension(80, 0)));
        add(Setting);

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
        
        Setting.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
               
                // TODO Auto-generated method stub
             
                JTextField firstName = new JTextField(15);
                JTextField lastName = new JTextField(15);
                JTextField contactNumber = new JTextField(25);
                JTextField address = new JTextField(25);
                JTextField weight = new JTextField(5);
                JTextField hight = new JTextField(5);
                JTextField lastBloodSugar = new JTextField(5);
                JTextField currrentBloodSugar = new JTextField(5);
                JTextField comment = new JTextField(25);
                
                
                JPanel myPanel = new JPanel();
                myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
                myPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
              
                myPanel.add(new JLabel("First Name:"));
                myPanel.add(firstName);
                myPanel.add(new JLabel("Last Name:"));
                myPanel.add(lastName);
                myPanel.add(new JLabel("Contact Number:"));
                myPanel.add(contactNumber);
                myPanel.add(new JLabel("Address:"));
                myPanel.add(address);
                myPanel.add(new JLabel("Weight:"));
                myPanel.add(weight);
                myPanel.add(new JLabel("Hight:"));
                myPanel.add(hight);
                myPanel.add(new JLabel("Last Blood Suger Level:"));
                myPanel.add(lastBloodSugar);
                myPanel.add(new JLabel("Current BloodSugar Level:"));
                myPanel.add(currrentBloodSugar);
                myPanel.add(new JLabel("Comment:"));
                myPanel.add(comment);
             
                int result = JOptionPane.showConfirmDialog(null, myPanel, 
                         "Please Enter Patient Details", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                   System.out.println("First Name: " + firstName.getText());
                   System.out.println("Last Name: " + lastName.getText());
                   System.out.println("Contact Number: " + contactNumber.getText());
                   System.out.println("Address: " + address.getText());
                   System.out.println("Weight: " + weight.getText());
                   System.out.println("Hight: " + hight.getText());
                   System.out.println("Last Blood Sugar Level: " + lastBloodSugar.getText());
                   System.out.println("Current Blood Sugar Level: " + currrentBloodSugar.getText());
                   System.out.println("Comment: " + comment.getText());
                 
                   
                }
                
                

            }
        });
        
   }
    public void close(){
        WindowEvent windClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(windClosingEvent);
        
    }
 
    public static void main(String args[]) {

        new InsulinGlucagonPump();

    }

}