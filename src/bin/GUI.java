
package bin;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.jfree.ui.Align;

class GUI {

    public Container BOTTOM_left;
    public static InsulinReservoir inslunRes= new InsulinReservoir();
    public static GlucagonReservoir glucRes= new GlucagonReservoir();

    GUI(String mode) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // Build the output window frame.
        JFrame frame = new JFrame("Insulin Glucagon Pump");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new PanelAutomatic(mode));
        frame.pack();
        frame.setVisible(true);
        
        
 }
      
    public class PanelAutomatic extends JPanel {
        public PanelAutomatic(String mode) {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
           
            if("Manual".equals(mode)){
                add(new PanelManualConfiguration());
            }else
            {
                add(new PanelConfiguration()); 
            }
            add(new PanelMonitoring());
            if("Manual".equals(mode)) {
                add(new PanelManual());
            } else {
                add(new PanelProgress());
            }
        }
    }

    public static class PanelManualConfiguration extends JPanel{
       
        public static JButton buttonStop;
        public static JButton buttonStart;

        
        public PanelManualConfiguration(){
         
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
            buttonStart = new JButton("Start Simulation");
            //buttonStart.setFont(Font);
            buttonStart.setBackground(Color.GREEN);
            buttonStart.setContentAreaFilled(false);
            buttonStart.setOpaque(true);
            buttonStart.setBorderPainted(false);
           
            Font font = new Font(buttonStart.getFont().getName(),Font.BOLD,buttonStart.getFont().getSize());
            buttonStart.setFont(font);
            
            buttonStop = new JButton("Stop Simulation");
            buttonStop.setBackground(Color.RED);
            buttonStop.setContentAreaFilled(false);
            buttonStop.setOpaque(true);
            buttonStop.setBorderPainted(false);
            buttonStop.setForeground(Color.WHITE);
            buttonStop.setFont(font);

            // Disable buttonStop by default.
            buttonStop.setEnabled(false);

            // Create the action listeners for the buttons.
            buttonStart.addActionListener(e -> {
                // Disable elements.
                buttonStart.setEnabled(false);

                // Enable elements.
                buttonStop.setEnabled(true);

                // Start the simulation.
                ManualSimulation.startSimulationManual(sliderInsulin.getValue(), sliderGlucagon.getValue(),inslunRes, glucRes);
            });

            buttonStop.addActionListener(e -> {
                // Disable elements.
                buttonStop.setEnabled(false);

                // Enable elements.
                buttonStart.setEnabled(true);
                
                // Stop the simulation.
                ManualSimulation.stopSimulationManual();
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
    
    public static class PanelManual extends JPanel implements ActionListener{

        // Create the TextField.
        static JLabel bloodSugarManualLabel = new JLabel();
       static  JTextField insulinField = new JTextField();
        JTextField glucagonField = new JTextField();
        JButton insulinSubmit = new JButton();
        JButton glucagonSubmit = new JButton();
        // Create the progress bars.
        public static JProgressBar insulinProgress = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        public static JProgressBar glucagonProgress = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        
        public PanelManual() {
           

            // Set the layout first.
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            // Set the borders.
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            setBackground(Color.lightGray);

           

            JLabel insulinStatus = new JLabel("Insulin Status:");
            JLabel glucagonStatus = new JLabel("Glucagon Status:");

            JLabel bloodSugarLevel = new JLabel("Blood Sugar Level is: ");
            

            // create the Label for Inject Insulin/Glucagon

            JLabel injectInsulLabel = new JLabel("Inject Insulin :   ");
            JLabel injectGlucgLabel = new JLabel("Inject Glucagon :  ");
            
            Font font = new Font(injectInsulLabel.getFont().getName(),Font.BOLD,injectInsulLabel.getFont().getSize());

            // Set ProgressBar.
            insulinProgress.setValue(inslunRes.getAvailable().intValueExact());
            insulinProgress.setStringPainted(true);
            insulinProgress.setString("Insulin");
            insulinProgress.setBackground(Color.gray);
            insulinProgress.setFont(font);

            glucagonProgress.setValue(glucRes.getAvailable().intValueExact());
            glucagonProgress.setStringPainted(true);
            glucagonProgress.setString("Glucagon");
            glucagonProgress.setBackground(Color.blue);
            glucagonProgress.setFont(font);
            
            // set Text field.

            bloodSugarManualLabel.setText("  " + BloodSugar.getBloodSugar()+ "  ");
            bloodSugarManualLabel.setForeground(Color.red);
            bloodSugarManualLabel.setBorder(BorderFactory.createLineBorder(Color.black));

            // set Label for insulin/glucagon Inject Level.

            insulinField.setText(" 00.0 ");

            glucagonField.setText(" 00.0 ");
            
            // set button for submit
            insulinSubmit.setText("Inject Insulin");
            insulinSubmit.setFont(font);
            insulinSubmit.addActionListener(this);
            
            
            glucagonSubmit.setText("Inject Glucagon");
            glucagonSubmit.setFont(font);
            glucagonSubmit.addActionListener(this);

            
            
            // Add all elements together.
            add(insulinStatus);
            add(insulinProgress);
            add(Box.createRigidArea(new Dimension(25, 0)));
            add(glucagonStatus);
            add(glucagonProgress);
            add(Box.createRigidArea(new Dimension(50, 0)));
            add(bloodSugarLevel);
            add(bloodSugarManualLabel);
            add(Box.createRigidArea(new Dimension(75, 0)));
            add(injectInsulLabel);
            add(insulinField);
            add(insulinSubmit);
            add(Box.createRigidArea(new Dimension(85, 0)));
            add(injectGlucgLabel);
            add(glucagonField);
            add(glucagonSubmit);
        }

        @Override
        public  void actionPerformed(ActionEvent e) {
            if (e.getSource() == insulinSubmit) {
                
            //    BigDecimal insulindate1 = insulinField.get
                BigDecimal insulinData = new BigDecimal(insulinField.getText()); //perform your operation
               // BigDecimal glucagonData = new BigDecimal(glucagonField.getText());
              
            //  System.out.println(glucagonData);
              System.out.println(insulinData);
                
               PumpSystem.changeValueInsulin = insulinData;
                InsulinGlucagonManualCalculation.insulinDose =insulinData;
                InsulinReservoir inRes = new InsulinReservoir();
                inRes.setInsulin(BigDecimal.TEN);
                GlucagonReservoir glures = new GlucagonReservoir();
                glures.setGlucagon(BigDecimal.TEN);
                new PumpSystem("manual", inRes, glures);
            }
            
            
             
       }
       
       

       
    }

    // Contains the configuration for the simulation.
    public static class PanelConfiguration extends JPanel {

        public static JButton buttonStop;
        public static JButton buttonStart;

		public PanelConfiguration() {
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
            buttonStart = new JButton("Start Simulation");
            //buttonStart.setFont(Font);
            buttonStart.setBackground(Color.GREEN);
            buttonStart.setContentAreaFilled(false);
            buttonStart.setOpaque(true);
            buttonStart.setBorderPainted(false);
           
            Font font = new Font(buttonStart.getFont().getName(),Font.BOLD,buttonStart.getFont().getSize());
            buttonStart.setFont(font);
            
            buttonStop = new JButton("Stop Simulation");
            buttonStop.setBackground(Color.RED);
            buttonStop.setContentAreaFilled(false);
            buttonStop.setOpaque(true);
            buttonStop.setBorderPainted(false);
            buttonStop.setForeground(Color.WHITE);
            buttonStop.setFont(font);

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
                Simulation.startSimulation(sliderInsulin.getValue(), sliderGlucagon.getValue(),inslunRes, glucRes);
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
        public static JLabel bloodSugarLabel = new JLabel();
        public static JLabel injectInsulinLabel = new JLabel();
        public static JLabel injectGlucagonLabel = new JLabel();

     // Create the progress bars.
        public static JProgressBar insulinProgress = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        public static JProgressBar glucagonProgress = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        public PanelProgress() {

             
            // Set the layout first.
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            // Set the borders.
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            setBackground(Color.lightGray);

            JLabel insulinStatus = new JLabel("Insulin Status:");
            JLabel glucagonStatus = new JLabel("Glucagon Status:");

            JLabel bloodSugarLevel = new JLabel("Blood Sugar Level is: ");

            // create the Label for Inject Insulin/Glucagon

            JLabel injectInsulLabel = new JLabel("Inject Insulin :   ");
            JLabel injectGlucgLabel = new JLabel("Inject Glucagon :  ");

            Font font = new Font(injectInsulLabel.getFont().getName(),Font.BOLD,injectInsulLabel.getFont().getSize());
            
            // Set ProgressBar.
            insulinProgress.setValue(inslunRes.getAvailable().intValueExact());
            insulinProgress.setStringPainted(true);
            insulinProgress.setString("Insulin");
            insulinProgress.setBackground(Color.gray);
            insulinProgress.setFont(font);

            glucagonProgress.setValue(glucRes.getAvailable().intValueExact());
            glucagonProgress.setStringPainted(true);
            glucagonProgress.setString("Glucagon");
            glucagonProgress.setBackground(Color.blue);
            glucagonProgress.setFont(font);
            
            // set Text field.

            bloodSugarLabel.setText("  " + BloodSugar.getBloodSugar().setScale(0, RoundingMode.HALF_UP)+ "  ");
            bloodSugarLabel.setForeground(Color.red);
            bloodSugarLabel.setBorder(BorderFactory.createLineBorder(Color.black));

            // set Label for insulin/glucagon Inject Level.

            injectInsulinLabel.setText("0.00 mg/dl");
            injectInsulinLabel.setBackground(Color.red);
            injectInsulinLabel.setBorder(BorderFactory.createLineBorder(Color.black));

            injectGlucagonLabel.setText("0.00 mg/dl");
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
            add(bloodSugarLabel);
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

    private class PanelInteractions extends JPanel  {

        

        private PanelInteractions() {
            // Set the layout first.
            setLayout(new GridLayout(3, 1, 20, 20));

            // Set the borders.
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            setBackground(Color.LIGHT_GRAY);

           
            JButton buttonCoke = new JButton("Drink Coke");
            JButton buttonCake = new JButton("Eat Cake");
            JButton buttonSports = new JButton("Do Sports");
           
            buttonCoke.addActionListener(e -> BloodSugar.startBloodSugarChanger(BigDecimal.valueOf(30.0d), 5000));
            buttonCake.addActionListener(e -> BloodSugar.startBloodSugarChanger(BigDecimal.valueOf(50.0d), 5000));
            buttonSports.addActionListener(e -> BloodSugar.startBloodSugChanger(BigDecimal.valueOf(-50.0d), 2500));

            buttonCake.setIcon(new ImageIcon(((new ImageIcon("E:/programm/InsulinGlucagon/image/Cake.png")).getImage()).getScaledInstance(50, 50,java.awt.Image.SCALE_SMOOTH)));
            buttonCoke.setIcon(new ImageIcon(((new ImageIcon("E:/programm/InsulinGlucagon/image/Cola.png")).getImage()).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
            buttonSports.setIcon(new ImageIcon(((new ImageIcon("E:/programm/InsulinGlucagon/image/Sports.png")).getImage()).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
            add(buttonCoke);
            add(buttonCake);
            add(buttonSports);
        }
  
 
}}

