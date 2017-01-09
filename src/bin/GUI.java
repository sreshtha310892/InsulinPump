package bin;
import javax.swing.*;
import java.awt.*;
import java.math.*;

class GUI {
	
	
	    public Container BOTTOM_left;

		GUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
      
      
        // Build the output window frame.
        JFrame frame = new JFrame("Insulin Glucagon Pump");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new PanelMain());
        frame.pack();
        frame.setVisible(true);
        }

	private class PanelMain extends JPanel {
        private PanelMain() {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            add(new PanelConfiguration());
            add(new PanelMonitoring());
            add(new PanelProgress());
        }
    }
    
    // Contains the configuration for the simulation.
    private class PanelConfiguration extends JPanel {
    	
       private PanelConfiguration() {
            // Set the layout first.
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

            // Set the borders.
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Create the labels.
            JLabel labelInsulin = new JLabel("Insulin:");
            JLabel labelGlucagon = new JLabel("Glucagon:");

            // Create the sliders.
            JSlider sliderInsulin = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
            JSlider sliderGlucagon = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
            
        
            // Set labels and ticks to sliders.
            sliderInsulin.setMinorTickSpacing(10);
            sliderInsulin.setMajorTickSpacing(50);
            sliderInsulin.setPaintTicks(true);
            sliderInsulin.setPaintLabels(true);

            sliderGlucagon.setMinorTickSpacing(10);
            sliderGlucagon.setMajorTickSpacing(50);
            sliderGlucagon.setPaintTicks(true);
            sliderGlucagon.setPaintLabels(true);
            
       
           
                     
            // Create the buttons.
            JButton buttonStart = new JButton("Start Simulation");
            JButton buttonStop = new JButton("Stop Simulation");
                       
            // Disable buttonStop by default.
            buttonStop.setEnabled(false);

            // Create the action listeners for the buttons.
            buttonStart.addActionListener(e -> {
                // Disable elements.
                buttonStart.setEnabled(false);
                sliderInsulin.setEnabled(false);
                sliderGlucagon.setEnabled(false);
                
                // Enable elements.
                buttonStop.setEnabled(true);

                // Start the simulation.
                Simulation.startSimulation(sliderInsulin.getValue(), sliderGlucagon.getValue());
            });

            buttonStop.addActionListener(e -> {
                // Disable elements.
                buttonStop.setEnabled(false);

                // Enable elements.
                buttonStart.setEnabled(true);
                sliderInsulin.setEnabled(true);
                sliderGlucagon.setEnabled(true);

                // Stop the simulation.
                Simulation.stopSimulation();
            });

            // Add all elements together.
           
            add(labelInsulin);
            add(sliderInsulin);
            add(Box.createRigidArea(new Dimension(25, 0)));
            add(labelGlucagon);
            add(sliderGlucagon);
            add(Box.createRigidArea(new Dimension(50, 0)));
            add(buttonStart);
            add(buttonStop);
            
           
            
        }
    }
    public static class PanelProgress extends JPanel {
    	
    	// Create the TextField.
    	public static JLabel bloodSugarText = new JLabel();
        public static JLabel injectInsulinLabel= new JLabel();
        public static JLabel injectGlucagonLabel = new JLabel();
        
    	private PanelProgress() {
         
            // Set the layout first.
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            // Set the borders.
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            setBackground(Color.lightGray);
            
          //Create the progress bars.
            JProgressBar insulinProgress = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
            JProgressBar glucagonProgress = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
           
            JLabel insulinStatus = new JLabel("Insulin Status:");
            JLabel glucagonStatus = new JLabel("Glucagon Status:");
            
           
            JLabel bloodSugarLevel = new JLabel("Blood Sugar Level is: ");
            
            // create the Label for Inject Insulin/Glucagon
           
            JLabel injectInsulLabel= new JLabel("Inject Insulin :   ");
            JLabel injectGlucgLabel = new JLabel("Inject Glucagon :  ");
           
         // Set ProgressBar.
            insulinProgress.setValue(70);
            insulinProgress.setStringPainted(true);
            insulinProgress.setString("Insulin");
            insulinProgress.setBackground(Color.gray);
            
            
            glucagonProgress.setValue(25);
            glucagonProgress.setStringPainted(true);
            glucagonProgress.setString("Glucagon");
            glucagonProgress.setBackground(Color.blue);
            
         // set Text field.
           
            bloodSugarText.setText(""+BloodSugar.getBloodSugar());
            bloodSugarText.setForeground(Color.red);
            bloodSugarText.setBorder(BorderFactory.createLineBorder(Color.black));
            
            //set Label for insulin/glucagon Ineject Level.
            injectInsulinLabel.setText("00.0");
            injectInsulinLabel.setBackground(Color.red);
            injectInsulinLabel.setBorder(BorderFactory.createLineBorder(Color.black));
            
            injectGlucagonLabel.setText("00.0");
            injectGlucagonLabel.setBackground(Color.yellow);
            injectGlucagonLabel.setBorder(BorderFactory.createLineBorder(Color.black));
            
           
          // Add all elements together.
            add(insulinStatus);
            add(insulinProgress);
            add(Box.createRigidArea(new Dimension(25, 0)));
            add(glucagonStatus);
            add(glucagonProgress);
            add(Box.createRigidArea(new Dimension(50, 0)));
            add(bloodSugarLevel);
            add(bloodSugarText);
            add(Box.createRigidArea(new Dimension(75, 0)));
            add(injectInsulLabel);
            add(injectInsulinLabel);
            add(Box.createRigidArea(new Dimension(85, 0)));
            add(injectGlucgLabel);
            add(injectGlucagonLabel); 
           
            
           
        }
    }
    

    private class PanelMonitoring extends JPanel {
        private PanelMonitoring() {
            // Set the layout first.
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            setBackground(Color.LIGHT_GRAY);

            add(new PanelChart());
            add(new PanelInteractions());
            
        }
    }

    private class PanelChart extends JPanel {
        private PanelChart() {
            setBackground(Color.LIGHT_GRAY);
            add(Monitor.chartPanel);
            
           
          
        }
    }
    
   
 
	
    private class PanelInteractions extends JPanel {
        private PanelInteractions() {
            // Set the layout first.
            setLayout(new GridLayout(3, 1, 20, 20));

            // Set the borders.
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            setBackground(Color.LIGHT_GRAY);

            JButton buttonCoke = new JButton("Drink Coke");
            JButton buttonCake = new JButton("Eat Cake");
            JButton buttonSports = new JButton("Do Sports");
            buttonCoke.addActionListener(e -> BloodSugar.startBloodSugarChanger(BigDecimal.valueOf(50.0d), 10000));
            buttonCake.addActionListener(e -> BloodSugar.startBloodSugarChanger(BigDecimal.valueOf(75.0d), 10000));
            buttonSports.addActionListener(e -> BloodSugar.startBloodSugarChanger(BigDecimal.valueOf(-50.0d), 5000));

            add(buttonCoke);
            add(buttonCake);
            add(buttonSports);
        }
    }
}
