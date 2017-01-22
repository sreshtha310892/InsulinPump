package bin;
import java.math.*;

// The hormone system.
class HormoneSystem implements Runnable {
    // The ratio/percentage insulin and glucagon is generated.
    // Actually it can be between 0.0 and 2.0 (0% and 200%).
    private BigDecimal ratioInsulin;
    private BigDecimal ratioGlucagon;

    // Defines the amount of how time in ms before the
    // hormone system can make changes to the bloodsugar again.
    private Integer tickLength;

    // The amount of bloodsugar which can be changed by the
    // hormone system during each tick under normal conditions.
    private BigDecimal tickValue;

    HormoneSystem(Integer insulin, Integer glucagon) {
        // Initialize values.
        tickLength = 100;
        tickValue = BigDecimal.valueOf(0.05);

        // Set the configured ratios.
        ratioInsulin = BigDecimal.valueOf(insulin).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_UP);
        ratioGlucagon = BigDecimal.valueOf(glucagon).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_UP);

        // Start the hormone system.
        new Thread(this).start();
    }

    public void run() {
        // Only generate data when the simulation is active.
        while (Simulation.isRunning) {
            // Basically we can change 1 bloodsugar in 2 seconds.
            // So we can change 0.05 bloodsugar each 100ms.
            try {
                Thread.sleep(tickLength);
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }

            if (BloodSugar.getBloodSugar().compareTo(BloodSugar.initialValue) > 0) {
                // Calculate the change made by the hormone system
                // based on tick-value, insulin-ratio and negation.
                BigDecimal changeValue = (tickValue.multiply(ratioInsulin)).negate();

                // Change the bloodsugar.
                BloodSugar.changeBloodSugar(changeValue);
            } else if (BloodSugar.getBloodSugar().compareTo(BloodSugar.initialValue) < 0) {
                // Calculate the change made by the hormone system
                // based on tick-value and glucagon-ratio.
                BigDecimal changeValue = tickValue.multiply(ratioGlucagon);

                // Change the bloodsugar.
                BloodSugar.changeBloodSugar(changeValue);
            }
        }
   
    }
}