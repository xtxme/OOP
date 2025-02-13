
public class Lab02 {
    public static class AirPurifier {
        private String model;
        private String brand;
        private double weight; //double เอาไว้เก็บตัวแปรทศนิยม
        private double suitableArea;
        private int noiseLevel;
        private int windLevel;
        private double particleCADR;
        private int filterLife;

        private static final int maxWindLevel = 5;
        private static final int maxNoiseLevel = 3;
        private static final int transferFilterLife = 1000;

        public AirPurifier(String model, String brand, double weight, double suitableArea, double particleCADR, int filterLife) {
            this.model = model;
            this.brand = brand;
            this.weight = weight;
            this.suitableArea = suitableArea;
            this.particleCADR = particleCADR;
            this.filterLife = filterLife;
            this.noiseLevel = 1;
            this.windLevel = 1;
        }

        public void nowOn() {
            System.out.println("Air Purifier is ON.");
        }

        public void nowOff() {
            System.out.println("Air Purifier is OFF.");
        }

        public String getDetails() {
            return String.format("Model: %s, Brand: %s, Weight: %.2f kg", model, brand, weight);
        }

        public void setWindLevel(int level) {
            if (level >= 1 && level <= maxWindLevel) {
                this.windLevel = level;
                System.out.println("Wind level set to: " + level);
            } else {
                System.out.println("Invalid wind level. Please choose between 1 and " + maxWindLevel);
            }
        }

        public void setNoiseLevel(int level) {
            if (level >= 1 && level <= maxNoiseLevel) {
                this.noiseLevel = level;
                System.out.println("Noise level set to: " + level);
            } else {
                System.out.println("Invalid noise level. Please choose between 1 and " + maxNoiseLevel);
            }
        }

        public int measureAQI() {
            int aqi = (int) (Math.random() * 301); // Random AQI between 0 and 300
            System.out.println("Current AQI: " + aqi);
            return aqi;
        }

        public static int getTransferFilterHours() {
            return transferFilterLife;
        }

        public static int[] getWindLevels() {
            return new int[] { 1, 2, 3, 4, 5 };
        }

        public static int[] getNoiseLevels() {
            return new int[] { 1, 2, 3 };
        }

        public boolean isFilterReplacementNeeded() {
            return this.filterLife < 0.1 * transferFilterLife;
        }
    }

    public static void main(String[] args) {
        AirPurifier purifier = new AirPurifier("Xiaomi Mi Pro EU Air Purifier", "Xiaomi", 7.5, 35.0, 250.0, 800);

        purifier.nowOn();
        purifier.nowOff();

        System.out.println(purifier.getDetails());

        purifier.setWindLevel(3);
        purifier.setNoiseLevel(2);

        purifier.measureAQI();

        System.out.println("Transfer filter life: " + AirPurifier.getTransferFilterHours() + " hours");
        System.out.println("Available wind levels: " + java.util.Arrays.toString(AirPurifier.getWindLevels()));
        System.out.println("Available noise levels: " + java.util.Arrays.toString(AirPurifier.getNoiseLevels()));
        System.out.println("Does the filter need replacement? " + purifier1.isFilterReplacementNeeded());
    }
}