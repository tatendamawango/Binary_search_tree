package edu.ktu.ds.lab2.utils;

/**
 * Interfeisu aprašomas Aibės ADT.
 *
 * @param <E> The data type of the set element
 */
public interface Set<E> extends Iterable<E> {

    //Checking that the set is empty.
    boolean isEmpty();

    // Returns the number of items in the set.
    int size();

    // The set is cleared.
    void clear();

    // A new element is added to the set.
    void add(E element);

    // The elements of both sets are added to the existing set, if both sets have the same element, it is not placed.
    void addAll(Set<E> set);

    // Removes an item from a set.
    void remove(E element);

    // The set contains the elements that are in the existing set and in the set.
    void retainAll(Set<E> set);

    // Checks if the element exists in the set.
    boolean contains(E element);

    // It is checked whether all the elements of both sets exist in the set.
    boolean containsAll(Set<E> set);

    boolean getBalance(int k);

    // Returns an array of set elements.
    Object[] toArray();

    // Returns the visually arranged content of the set elements
    String toVisualizedString(String dataCodeDelimiter);
}
