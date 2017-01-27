package bin;
import org.jfree.data.time.*;

import bin.GUI.PanelConfiguration;
import bin.GUI.PanelProgress;

class Simulation {
    static Boolean isRunning = false;

    static void startSimulation(Integer insulin, Integer glucagon,InsulinReservoir insRes, GlucagonReservoir glures) {
        // Reset all data first.
        //chart.setNotify(false);
        
        PanelProgress.injectInsulinLabel.setText("0.00 mg/dl");
        PanelProgress.injectGlucagonLabel.setText("0.00 mg/dl");
        
        for (Integer i = 0; i < Monitor.dataset.getSeries().size(); i++) {
            TimeSeries timeSeries = (TimeSeries) Monitor.dataset.getSeries().get(i);
            timeSeries.clear();
        }
        //chart.setNotify(true);

        // Reset the value of the bloodsugar.
        BloodSugar.resetBloodSugar();

        // Set flag.
        isRunning = true;

        // Start the data generator.
        new Thread(new Monitor.BloodSugarDataGenerator()).start();

        // Start the hormone system.
        new HormoneSystem(insulin, glucagon);

        // Start the pump system.
        new PumpSystem("auto",insRes, glures);
    }

    static void stopSimulation() {
        // Set flag.
        
        PanelConfiguration.buttonStop.setEnabled(false);
        PanelConfiguration.buttonStart.setEnabled(true);
        isRunning = false;
    }
}
