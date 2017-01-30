package bin;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.data.time.*;

//import com.sun.javafx.scene.control.skin.ButtonSkin;

import bin.GUI.PanelProgress;
import bin.GUI.PanelManual;

import java.awt.*;

import javax.swing.JOptionPane;

class Monitor {
    static ChartPanel chartPanel;
    static TimeSeriesCollection dataset;

    Monitor() {
        // Define a TimeSeriesChart.
        dataset = new TimeSeriesCollection();
        dataset.addSeries(BloodSugar.timeSeriesBloodSugar);
        dataset.addSeries(BloodSugar.timeSeriesSafeRangeMin);
        dataset.addSeries(BloodSugar.timeSeriesSafeRangeMax);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Bloodsugar",
                "Time",
                "Glucose [mg/dl]",
                dataset,
                false,
                false,
                false
        );

        // Define a plot and set colors/thicknesses.
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        plot.getRenderer().setSeriesPaint(0, Color.RED);
        plot.getRenderer().setSeriesPaint(1, Color.BLACK);
        plot.getRenderer().setSeriesPaint(2, Color.BLACK);

        plot.getRenderer().setSeriesStroke(0, new BasicStroke(1.5f));
        plot.getRenderer().setSeriesStroke(1, new BasicStroke(0.25f));
        plot.getRenderer().setSeriesStroke(2, new BasicStroke(0.25f));

        // Set the range of the Y axis.
        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setRange(BloodSugar.rangeMin.doubleValue(), BloodSugar.rangeMax.doubleValue());

        // Display values of the last two minutes.
        ValueAxis domainAxis = plot.getDomainAxis();
        domainAxis.setAutoRange(true);
        domainAxis.setFixedAutoRange(120000.0);

        chartPanel = new ChartPanel(chart);

        // Disable zooming functionality.
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);

        // Disable right-click menu.
        chartPanel.setPopupMenu(null);
    }

    static class BloodSugarDataGenerator implements Runnable {
    	
        public void run() {
            // Only generate data when the simulation is active.
            while (Simulation.isRunning) {
                Millisecond millisecond = new Millisecond();

               // Adds the new value to the TimeSeries object.
                BloodSugar.timeSeriesBloodSugar.addOrUpdate(millisecond, BloodSugar.getBloodSugar());
            
                // set blood sugar value in Textfield
                PanelProgress.bloodSugarLabel.setText(""+BloodSugar.getBloodSugar());
                
                if(BloodSugar.getBloodSugar().intValue() > 250 || BloodSugar.getBloodSugar().intValue() < 50){
                  	
                  	Component frame = null;
                  	Simulation.stopSimulation();
                  	
                  	
                  	
                  	
          			JOptionPane.showMessageDialog(frame,
                      	    "Imidiately Consult Doctor!!",
                      	    "Emergency",
                      	    JOptionPane.ERROR_MESSAGE);
          		 }
               
                // Adds the other fixed values.
                BloodSugar.timeSeriesSafeRangeMin.addOrUpdate(millisecond, BloodSugar.safeRangeMin);
                BloodSugar.timeSeriesSafeRangeMax.addOrUpdate(millisecond, BloodSugar.safeRangeMax);

                try {
                    // Wait for 100 milliseconds.
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.out.println(ex.toString());
                }
            }
            
            // generated ManualSimulation is active
            
            while (ManualSimulation.isRunningManual) {
                Millisecond millisecond = new Millisecond();

               // Adds the new value to the TimeSeries object.
                BloodSugar.timeSeriesBloodSugar.addOrUpdate(millisecond, BloodSugar.getBloodSugar());
            
                // set blood sugar value in Textfield
                PanelManual.bloodSugarManualLabel.setText(""+BloodSugar.getBloodSugar());
                
                if(BloodSugar.getBloodSugar().intValue() > 250 || BloodSugar.getBloodSugar().intValue() < 50){
                    
                    Component frame = null;
                    ManualSimulation.stopSimulationManual();
                
                    JOptionPane.showMessageDialog(frame,
                            "Imidiately Consult Doctor!!",
                            "Emergency",
                            JOptionPane.ERROR_MESSAGE);
                 }
               
                // Adds the other fixed values.
                BloodSugar.timeSeriesSafeRangeMin.addOrUpdate(millisecond, BloodSugar.safeRangeMin);
                BloodSugar.timeSeriesSafeRangeMax.addOrUpdate(millisecond, BloodSugar.safeRangeMax);

                try {
                    // Wait for 100 milliseconds.
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.out.println(ex.toString());
                }
            }
            
            
        }
    }
}
