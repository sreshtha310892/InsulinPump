package bin;
public class InsulinGlucagonPump {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Insulin Glucagon Pump started ...");

        // Create objects .
        new BloodSugar();
        new Monitor();
        new GUI();
    }
}
