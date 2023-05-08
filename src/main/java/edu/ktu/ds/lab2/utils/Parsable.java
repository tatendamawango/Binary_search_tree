package edu.ktu.ds.lab2.utils;

/** @author Eimutis Karčiauskas, KTU IF Department of Software Engineering
 *
 * This is the interface that the data classes created by KTU students must meet.
 *       The methods ensure convenient data generation from String strings
 ******************************************************************************/
public interface Parsable<T> extends Comparable<T> {

    /**
     * Forms an object from a string
     *
     * @param e
     */
    void parse(String e);
}
