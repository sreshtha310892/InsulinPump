package bin;
import org.jfree.data.time.*;

class Simulation {
    static Boolean isRunning = false;

    static void startSimulation(Integer insulin, Integer glucagon) {
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
        isRunning = true;

        // Start the data generator.
        new Thread(new Monitor.BloodSugarDataGenerator()).start();

        // Start the hormone system.
        new HormoneSystem(insulin, glucagon);

        // Start the pump system.
        new PumpSystem(true);
    }

    static void stopSimulation() {
        // Set flag.
        isRunning = false;
    }
}
