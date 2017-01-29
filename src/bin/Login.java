package bin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Login {
	public Login() {
		
		JFrame frame = new JFrame("Login");
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		
		frame.setVisible(true);

		panel.setLayout(null);

		JLabel userLabel = new JLabel("User Name :");
		userLabel.setBounds(10, 50, 80, 25);
		panel.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(200, 50, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password :");
		passwordLabel.setBounds(10, 100, 80, 25);
		panel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(200, 100, 160, 25);
		panel.add(passwordText);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(70, 180, 80, 25);
		panel.add(loginButton);
		
		JButton registerButton = new JButton("Cancle");
		registerButton.setBounds(220, 180, 80, 25);
		panel.add(registerButton);
		
		loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              
                // TODO Auto-generated method stub
             
                if (userText.getText().equals("admin") && passwordText.getText().equals("admin")) {
                	
                	new BloodSugar();
                    new Monitor();
                    new GUI("Manual");
                    
                } else {
                	
                	Component frame = null;
                  	
                  	JOptionPane.showMessageDialog(frame,
                      	    "User Name or Password incorrect !",
                      	    "Warning",
                      	    JOptionPane.ERROR_MESSAGE);
                	
                }
                
            }

        });
		
		registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              
                // TODO Auto-generated method stub
             frame.dispose();
                
            }

        });
	}
		
}


