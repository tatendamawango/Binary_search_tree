package edu.ktu.ds.lab2.demo;

import edu.ktu.ds.lab2.utils.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

/*
 * Bst or Set testing without Gui
 * It shows a BST tree distribution beautifully on console when working with IntelliJ
 * If it doesn't look beautiful, you should change the settings by :
 *      FIle -> Settings -> Editor -> File Encodings -> Global encoding to change to UTF-8
 *
 */
public class ManualTest {

    static Car[] cars;
    static ParsableSortedSet<Car> cSeries = new ParsableBstSet<>(Car::new, Car.byPrice);

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // We unify number formats
        executeTest();
    }

    public static void executeTest() throws CloneNotSupportedException {
        Car c1 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c2 = new Car.Builder()
                .make("Renault")
                .model("Megane")
                .year(2001)
                .mileage(20000)
                .price(3500)
                .build();
        Car c3 = new Car.Builder().buildRandom();
        Car c4 = new Car("Renault Laguna 2001 115900 700");
        Car c5 = new Car("Renault Megane 1990 365100 9500");
        Car c6 = new Car("Honda   Civic  2001  36400 100.3");
        Car c7 = new Car("Renault Laguna 2001 115900 7500");
        Car c8 = new Car("Renault Megane 1990 365100 950");
        Car c9 = new Car("Honda   Civic  2007  36400 850.3");

        Car[] carsArray = {c9, c7, c8, c5, c1, c6};
        Set<Car> cars = new BstSet<Car>();
        cars.add(c9);

        Ks.oun("Auto Set/Bst:");
        ParsableSortedSet<Car> carsSet = new ParsableBstSet<>(Car::new);

        for (Car c : carsArray) {
            carsSet.add(c);
            Ks.oun("Filling Set/Bst " + c + ". Its size: " + carsSet.size());
        }
        Ks.oun("");
        Ks.oun(carsSet.toVisualizedString(""));

        ParsableSortedSet<Car> carsSetCopy = (ParsableSortedSet<Car>) carsSet.clone();



        Set<Car> carsAdd = new BstSet<Car>();
        carsAdd.add(new Car("Add this 2001 115900 7500"));
        carsAdd.add(c5);
        carsAdd.add(c6);

        ParsableSortedSet<Car> carsSetCopyforRetain = (ParsableSortedSet<Car>) carsSet.clone();
        carsSetCopyforRetain.retainAll((carsAdd));
        Ks.oun("Retain all method implement");
        Ks.oun(carsSetCopyforRetain.toVisualizedString(""));

        Ks.oun("Set to be added");
        Ks.oun(carsAdd.toVisualizedString(""));
        carsSet.addAll((carsAdd));
        Ks.oun("Add all method implement");
        Ks.oun(carsSet.toVisualizedString(""));


        Ks.oun("Original:");
        Ks.ounn(carsSet.toVisualizedString(""));

        Ks.oun("Do the set contains all?");
        if (carsSet.containsAll(cars))
        {
            Ks.oun("yes");
        }
        else
            Ks.oun("no");



        Ks.oun("Do the  exist the set/bst?");
        for (Car c : carsArray) {
            Ks.oun(c + ": " + carsSetCopy.contains(c));
        }
        Ks.oun(c2 + ": " + carsSetCopy.contains(c2));
        Ks.oun(c3 + ": " + carsSetCopy.contains(c3));
        Ks.oun(c4 + ": " + carsSetCopy.contains(c4));
        Ks.oun("");


        Ks.oun("Car set/bst with iterator:");
        Ks.oun("");
        for (Car c : carsSet) {
            Ks.oun(c);
        }
        Set<Car> headSet = carsSet.headSet(c7);
        Ks.oun("HeadSet method implement");
        Ks.ounn(headSet.toVisualizedString(""));
//------------------------------------------------------------------------------------------------------------------------
        //tailset
        long start8=System.nanoTime();

        Ks.oun("tailset method-----------------------------------------------------");
        Set<Car> tailSet = carsSet.tailSet(c7);

        Ks.oun(tailSet.toVisualizedString(""));

        long elapsedTime8=System.nanoTime()-start8;


        Ks.oun("TailSet method implement");
        Ks.ounn(tailSet.toVisualizedString(""));

        Set<Car> subSet = carsSet.subSet(c9, c1);
        Ks.oun("SubSet method implement");
        Ks.ounn(subSet.toVisualizedString(""));



        Ks.oun("Get balance:\n" + carsSet.getBalance(1));

        Ks.oun("");
        Ks.oun("Car set in AVL-tree:");
        ParsableSortedSet<Car> carsSetAvl = new ParsableAvlSet<>(Car::new);
        for (Car c : carsArray) {
            carsSetAvl.add(c);
        }
        Ks.ounn(carsSetAvl.toVisualizedString(""));
        carsSetAvl.remove(c6);
        Ks.oun("Remove:" + c6.toString() );
        Ks.ounn(carsSetAvl.toVisualizedString(""));


        /*
        Ks.oun("Car set/bst with iterator:");
        Ks.oun("");
        for (Car c : carsSetAvl) {
            Ks.oun(c);
        }

        Ks.oun("");
        Ks.oun("Car set with reverse iterator:");
        Ks.oun("");
        Iterator<Car> iter = carsSetAvl.descendingIterator();
        while (iter.hasNext()) {
            Ks.oun(iter.next());
        }

        Ks.oun("");
        Ks.oun("Car set toString () method: ");
        Ks.ounn(carsSetAvl);

        // We clean and form sets by reading from a file
        carsSet.clear();
        carsSetAvl.clear();

        Ks.oun("");
        Ks.oun("Car set in BS-tree:");
        carsSet.load("data\\ban.txt");
        Ks.ounn(carsSet.toVisualizedString(""));
        Ks.oun("Find out why the tree only grew in one direction.");

        Ks.oun("");
        Ks.oun("Car set in AVL-tree:");
        carsSetAvl.load("data\\ban.txt");
        Ks.ounn(carsSetAvl.toVisualizedString(""));

        Set<String> carsSet4 = CarMarket.duplicateCarMakes(carsArray);
        Ks.oun("Duplicate car brands:\n" + carsSet4.toString());

        Set<String> carsSet5 = CarMarket.uniqueCarModels(carsArray);
        Ks.oun("Unique car models:\n" + carsSet5.toString());

*/
        runSpeedTest(carsSet, carsSetAvl, c8);
    }

    static ParsableSortedSet<Car> generateSet(int kiekis, int generN) {
        cars = new Car[generN];
        for (int i = 0; i < generN; i++) {
            cars[i] = new Car.Builder().buildRandom();
        }
        Collections.shuffle(Arrays.asList(cars));

        cSeries.clear();
        Arrays.stream(cars).limit(kiekis).forEach(cSeries::add);
        return cSeries;
    }

    static void runSpeedTest(ParsableSortedSet<Car> bstCarSet, ParsableSortedSet<Car> avlCarSet, Car remove)
    {
        long bstSetRemoveTime = run(bstCarSet, remove);
        long avlSetRemoveTime = run(avlCarSet, remove);
        System.out.println(bstCarSet.size() + " " + bstSetRemoveTime + " " + avlCarSet.size() + " " + avlSetRemoveTime);
    }
    static long run(Set<Car> carSet, Car remove)
    {
        long start = System.nanoTime();
        carSet.remove(remove);
        return System.nanoTime() - start;
    }
}
