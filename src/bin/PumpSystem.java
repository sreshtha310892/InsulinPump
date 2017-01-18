package bin;
import java.math.BigDecimal;
import java.util.LinkedList;

import bin.GUI.PanelManual;
import bin.GUI.PanelProgress;

public class PumpSystem implements Runnable {
    private Boolean automaticMode;
    private LinkedList<BigDecimal> bloodsugarValues = new LinkedList<BigDecimal>();
    private Integer checkInterval = 5000;
    private Integer historyLength = 3;
    private Integer lastInjection = 0;
    InsulinReservoir insulinReservoir;
   

    PumpSystem (Boolean mode,InsulinReservoir insRes) {
        automaticMode = mode;
        this.insulinReservoir= insRes;
        // Start data-collector thread.
        new Thread(this).start();
    }

    public void run() {
        // Only check blood sugar when the simulation is active.
        while (Simulation.isRunning) {
            // Make sure that we have only n-elements of history data.
            if (bloodsugarValues.size() == historyLength) {
                bloodsugarValues.removeFirst();
            }

            // Add the latest blood sugar-value to the history.
            bloodsugarValues.add(BloodSugar.getBloodSugar());

            // Check and inject when in automatic-mode.
            if (automaticMode) {
                checkAndInject();
            }

            // Increment the lastInjection counter.
            lastInjection++;

            // We check the bloodsugar values only in a certain interval.
            try {
                Thread.sleep(checkInterval);
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public void checkAndInject() {
        // Only inject when the last injection is at least the history length.
        if (lastInjection >= historyLength) {
            // Low bloodsugar-level is the most critical. So we check the last measurement.
            // When it is in the dangerous range, inject glucagon immediately.
            if (bloodsugarValues.getLast().compareTo(BloodSugar.safeRangeMin) < 0) {
                // Change the bloodsugar.
                BigDecimal changeValue = BloodSugar.initialValue.subtract(bloodsugarValues.getLast());
                System.out.println("Bloodsugar is critical low!");
                System.out.println("Inject " + changeValue.toString() + " mg/dl Glucagon ...");
                BloodSugar.startBloodSugarChanger(changeValue, 0);
                
                // set value for insulin mg/dl in Label
                 PanelProgress.injectGlucagonLabel.setText(""+changeValue.toString()+" mg/dl");
                // Reset last injection.
                lastInjection = 0;
            } else {
                // We need at least a full record of history-values.
                if (bloodsugarValues.size() == historyLength) {
                    // Calculate the average value based on history values.
                    // To ensure that we dont depend too much on strong
                    // temporary deviations to the bloodsugar, we want a full
                    // set of history data.
                    BigDecimal bloodsugarSum = BigDecimal.ZERO;
                    for (int i = 0; i < bloodsugarValues.size(); i++) {
                        bloodsugarSum = bloodsugarSum.add(bloodsugarValues.get(i));
                    }
                    BigDecimal bloodsugarAverage = bloodsugarSum.divide(BigDecimal.valueOf(bloodsugarValues.size()), 2, BigDecimal.ROUND_UP);

                    // Only inject when the average is above the safe-range and the last-value is above the safe-range too.
                    if (bloodsugarAverage.compareTo(BloodSugar.safeRangeMax) > 0 && bloodsugarValues.getLast().compareTo(BloodSugar.safeRangeMax) > 0) {
                        // Change the bloodsugar.
        
                    	BigDecimal changeValue = InsulinGlucagonCalculation.dose(BloodSugar.getSugar(),bloodsugarAverage);
                    	insulinReservoir.getInsulinAmount(changeValue);
                    	PanelManual.insulinProgress.setValue(insulinReservoir.getAvailable().intValue());
                    	PanelProgress.insulinProgress.setValue(insulinReservoir.getAvailable().intValue());
                        //BigDecimal changeValue = (bloodsugarAverage.subtract(BloodSugar.initialValue)).negate();
                        System.out.println("Bloodsugar is too high!");
                        System.out.println("Inject " + changeValue.toString() + " mg/dl Insulin ...");
                        BloodSugar.startBloodSugarChanger(changeValue, 0);
                        
                        // set value for insulin mg/dl in Label
                        PanelProgress.injectInsulinLabel.setText(""+changeValue.toString()+" mg/dl");

                        // Reset last injection.
                        lastInjection = 0;
                    }
                }
            }
        }
    }
}
