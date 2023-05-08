package edu.ktu.ds.lab2.utils;

import java.util.Iterator;

public interface SortedSet<E> extends Set<E> {

    /**
     * Returns a subset of the set up to the date of the item.
     *
     * @param element - Set element.
     * @return Returns a subset of the set up to the date of the item.
     */
    Set<E> headSet(E element);
    boolean getBalance(int k);
    /**
     * Returns a subset of the set from element date1 to date2.
     *
     * @param element1 - the original element of a subset of the set.
     * @param element2 - the end element of a subset of the set.
     * @return Returns a subset of the set from element date1 to date2.
     */
    Set<E> subSet(E element1, E element2);



    /**
     * Returns a subset of the set up to the date of the item.
     *
     * @param element - Set element.
     * @return Returns a subset of the set from the date of the item.
     */
    Set<E> tailSet(E element);

    /**
     * Returns the reverse iterator.
     *
     * @return Returns the reverse iterator.
     */
    Iterator<E> descendingIterator();
}
