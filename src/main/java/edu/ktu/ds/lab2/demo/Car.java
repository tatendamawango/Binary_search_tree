package edu.ktu.ds.lab2.demo;

import edu.ktu.ds.lab2.utils.Ks;
import edu.ktu.ds.lab2.utils.Parsable;

import java.time.LocalDate;
import java.util.*;

/**
 * @author EK
 */
public final class Car implements Parsable<Car> {

    // general data for all cars (for the whole class)
    private static final int minYear = 1990;
    private static final int currentYear = LocalDate.now().getYear();
    private static final double minPrice = 100.0;
    private static final double maxPrice = 333000.0;
    private static final String idCode = "TA";   //  ***** new
    private static int serNr = 100;               //  ***** new

    private final String carRegNr;

    private String make = "";
    private String model = "";
    private int year = -1;
    private int mileage = -1;
    private double price = -1.0;

    public Car() {
        carRegNr = idCode + (serNr++);    // provided original carRegNr
    }

    public Car(String make, String model, int year, int mileage, double price) {
        carRegNr = idCode + (serNr++);    // provided original carRegNr
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        validate();
    }

    public Car(String dataString) {
        carRegNr = idCode + (serNr++);    // provided original carRegNr
        this.parse(dataString);
        validate();
    }

    public Car(Builder builder) {
        carRegNr = idCode + (serNr++);    // provided original carRegNr
        this.make = builder.make;
        this.model = builder.model;
        this.year = builder.year;
        this.mileage = builder.mileage;
        this.price = builder.price;
        validate();
    }

    public Car create(String dataString) {
        return new Car(dataString);
    }

    private void validate() {
        String errorType = "";
        if (year < minYear || year > currentYear) {
            errorType = "Invalid manufacture year ["
                    + minYear + ":" + currentYear + "]";
        }
        if (price < minPrice || price > maxPrice) {
            errorType += " Price is outside the allowable range [" + minPrice
                    + ":" + maxPrice + "]";
        }
        
        if (!errorType.isEmpty()) {
            Ks.ern("The car is poorly generated: " + errorType);
        }
    }

    @Override
    public void parse(String dataString) {
        try {   // data separated by spaces
            Scanner scanner = new Scanner(dataString);
            make = scanner.next();
            model = scanner.next();
            year = scanner.nextInt();
            setMileage(scanner.nextInt());
            setPrice(scanner.nextDouble());
        } catch (InputMismatchException e) {
            Ks.ern("Bad data format-> " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Missing data -> " + dataString);
        }
    }

    @Override
    public String toString() {  // supplemented with carRegNr
        return getCarRegNr() + "=" + make + "_" + model + ":" + year + " " + getMileage() + " " + String.format("%4.1f", price);
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCarRegNr() {  //** nauja.
        return carRegNr;
    }

    @Override
    public int compareTo(Car car) {
        return getCarRegNr().compareTo(car.getCarRegNr());
    }

    public static Comparator<Car> byMake = (Car c1, Car c2) -> c1.make.compareTo(c2.make); // initially by brand and then by model

    public static Comparator<Car> byPrice = (Car c1, Car c2) -> {
        // in ascending order, starting from the lowest price
        if (c1.price < c2.price) {
            return -1;
        }
        if (c1.price > c2.price) {
            return +1;
        }
        return 0;
    };

    public static Comparator<Car> byYearPrice = (Car c1, Car c2) -> {
        // years in descending order at the same reference price
        if (c1.year > c2.year) {
            return +1;
        }
        if (c1.year < c2.year) {
            return -1;
        }
        if (c1.price > c2.price) {
            return +1;
        }
        if (c1.price < c2.price) {
            return -1;
        }
        return 0;
    };

    // Car class object builder
    public static class Builder {

        private final static Random RANDOM = new Random(1949);  // Arandom generator
        private final static String[][] MODELS = { // an array of possible car brands and models
            {"Mazda", "121", "323", "626", "MX6"},
            {"Ford", "Fiesta", "Escort", "Focus", "Sierra", "Mondeo"},
            {"Saab", "92", "96"},
            {"Honda", "Accord", "Civic", "Jazz"},
            {"Renault", "Laguna", "Megane", "Twingo", "Scenic"},
            {"Peugeot", "206", "207", "307"}
        };

        private String make = "";
        private String model = "";
        private int year = -1;
        private int mileage = -1;
        private double price = -1.0;

        public Car build() {
            return new Car(this);
        }

        public Car buildRandom() {
            int ma = RANDOM.nextInt(MODELS.length);        // brand index  0..
            int mo = RANDOM.nextInt(MODELS[ma].length - 1) + 1;// model index 1..
            return new Car(MODELS[ma][0],
                    MODELS[ma][mo],
                    1990 + RANDOM.nextInt(20),// years between 1990 and 2009
                    6000 + RANDOM.nextInt(222000),// mileage between 6000 and 228000
                    800 + RANDOM.nextDouble() * 88000);// price between 800 and 88800
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder make(String make) {
            this.make = make;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder mileage(int mileage) {
            this.mileage = mileage;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }
    }
}
