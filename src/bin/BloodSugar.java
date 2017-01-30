package bin;
import org.jfree.data.time.*;
import java.math.*;

class BloodSugar {
    // Range of possible bloodsugar values: 0.0 - 200.0.
    static BigDecimal rangeMin = BigDecimal.valueOf(0.0d);
    static BigDecimal rangeMax = BigDecimal.valueOf(300.0d);

    // The starting value in the middle of the range with a perfect bloodsugar value.
    static BigDecimal initialValue = BigDecimal.valueOf(100.0d);

    // Safe area for the bloodsugar: 60.0 - 140.0.
    static BigDecimal safeRangeMin = BigDecimal.valueOf(70.0d);
    static BigDecimal safeRangeMax = BigDecimal.valueOf(130.0d);

    // Contains the actual bloodsugar value.
    private static BigDecimal bloodSugar;

    // Contains the TimeSeries data.
    static TimeSeries timeSeriesBloodSugar;
    static TimeSeries timeSeriesSafeRangeMin;
    static TimeSeries timeSeriesSafeRangeMax;
    
    private static BigDecimal sugar;

    // The constructor.
    BloodSugar() {
        // Initialize values.
        bloodSugar = initialValue;
        timeSeriesBloodSugar = new TimeSeries("Bloodsugar");
        timeSeriesSafeRangeMin = new TimeSeries("SafeRange Minimum");
        timeSeriesSafeRangeMax = new TimeSeries("SafeRange Maximum");
    }

    static void resetBloodSugar() {
        bloodSugar = initialValue;
    }

    static void changeBloodSugar(BigDecimal changeValue) {
        BigDecimal tempValue = bloodSugar;

        if (changeValue.compareTo(BigDecimal.ZERO) > 0) {
            tempValue = tempValue.add(changeValue);

            if (tempValue.compareTo(rangeMax) > 0) {
                tempValue = rangeMax;
            }
        } else if (changeValue.compareTo(BigDecimal.ZERO) < 0) {
            tempValue = tempValue.subtract(changeValue.abs());

            if (tempValue.compareTo(rangeMin) < 0) {
                tempValue = rangeMin;
            }
        }

        bloodSugar = tempValue;
    }

    static BigDecimal getBloodSugar() {
        return bloodSugar;
    }
    
   

    // A thread which is running when some action happens like drinking/eating/sports.
    private static class BloodSugarChanger implements Runnable {
        // We have to wait for 10 seconds until something happens.
        // Bloodsugar changes not directly because e.g. food has to stay in the stomach first for some time.
        private static int initialWaitingTime = 10000;
        private BigDecimal amountOfSugar;
        private BigDecimal Sugar;

        BloodSugarChanger(BigDecimal amountOfSugar, int waitingTime) {
        	setSugar(amountOfSugar);
            this.amountOfSugar = amountOfSugar;
            initialWaitingTime = waitingTime;
           
        }

        
        public void run() {
            // Wait first until the sugar can react in the body.
            try {
                Thread.sleep(initialWaitingTime);
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }

            while (Simulation.isRunning && this.amountOfSugar.compareTo(BigDecimal.ZERO) != 0) {
                // We can change 0.1 bloodsugar every 100ms.
                if (this.amountOfSugar.compareTo(BigDecimal.ZERO) > 0) {
                    BloodSugar.changeBloodSugar(BigDecimal.valueOf(0.1d));
                    this.amountOfSugar = this.amountOfSugar.subtract(BigDecimal.valueOf(0.1d));
                } else if (this.amountOfSugar.compareTo(BigDecimal.ZERO) < 0) {
                    BloodSugar.changeBloodSugar(BigDecimal.valueOf(-0.1d));
                    this.amountOfSugar = this.amountOfSugar.add(BigDecimal.valueOf(0.1d));
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.out.println(ex.toString());
                }
            }
            while (ManualSimulation.isRunningManual && this.amountOfSugar.compareTo(BigDecimal.ZERO) != 0) {
                // We can change 0.1 bloodsugar every 100ms.
                if (this.amountOfSugar.compareTo(BigDecimal.ZERO) > 0) {
                    BloodSugar.changeBloodSugar(BigDecimal.valueOf(0.1d));
                    this.amountOfSugar = this.amountOfSugar.subtract(BigDecimal.valueOf(0.1d));
                } else if (this.amountOfSugar.compareTo(BigDecimal.ZERO) < 0) {
                    BloodSugar.changeBloodSugar(BigDecimal.valueOf(-0.1d));
                    this.amountOfSugar = this.amountOfSugar.add(BigDecimal.valueOf(0.1d));
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.out.println(ex.toString());
                }
            }
        }
    }

    static void startBloodSugarChanger(BigDecimal amountOfSugar, int waitingTime) {
        new Thread(new BloodSugarChanger(amountOfSugar, waitingTime)).start();
    }
    
    static void startBloodSugChanger(BigDecimal Sugar, int waitingTime) {
        new Thread(new BloodSugarChanger(Sugar, waitingTime)).start();
    }

	public static BigDecimal getSugar() {
		return sugar;
	}

	public static void setSugar(BigDecimal sugar) {
		BloodSugar.sugar = sugar;
	}
}
