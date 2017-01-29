package bin;
import org.jfree.data.time.TimeSeries;

import bin.GUI.PanelManualConfiguration;
import bin.GUI.PanelProgress;

public class ManualSimulation {

      static Boolean isRunningManual = false;

        static void startSimulationManual(Integer insulin, Integer glucagon, InsulinReservoir insRes, GlucagonReservoir glures) {
            // Reset all data first.
            //chart.setNotify(false);
            for (Integer i = 0; i < Monitor.dataset.getSeries().size(); i++) {
                TimeSeries timeSeries = (TimeSeries) Monitor.dataset.getSeries().get(i);
                timeSeries.clear();
            }
            //chart.setNotify(true);

            // Reset the value of the bloodsugar.
            BloodSugar.resetBloodSugar();

            // Set flag.
            isRunningManual = true;

            // Start the data generator.
            new Thread(new Monitor.BloodSugarDataGenerator()).start();

            // Start the hormone system.
        //    new HormoneSystem(insulin, glucagon);

          
        }

        static void stopSimulationManual() {
            // Set flag.
            PanelManualConfiguration.buttonStop.setEnabled(false);
            PanelManualConfiguration.buttonStart.setEnabled(true);
            isRunningManual = false;
        }
    }

