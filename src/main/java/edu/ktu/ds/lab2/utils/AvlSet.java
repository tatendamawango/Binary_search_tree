package edu.ktu.ds.lab2.utils;

import java.util.Comparator;

/**
 * Sorted object collections - realization of a set with AVL-tree.
 *
 * @param <E> Set element type. The Comparable <E> interface must be satisfied, or
 *            an object satisfying the Comparator <E> interface must be passed
 *            through the class constructor.
 * 
 * @author darius.matulis@ktu.lt
 * @užduotis Review and clarify the methods provided.
 */
public class AvlSet<E extends Comparable<E>> extends BstSet<E>
        implements SortedSet<E> {

    public AvlSet() {
    }

    public AvlSet(Comparator<? super E> c) {
        super(c);
    }

    /**
     * A new element is added to the set.
     *
     * @param element
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }
        root = addRecursive(element, (AVLNode<E>) root);

    }

    /**
     * The private recursive method is used in the add method;
     *
     * @param element
     * @param node
     * @return
     */
    private AVLNode<E> addRecursive(E element, AVLNode<E> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(element);
        }
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.setLeft(addRecursive(element, node.getLeft()));
            if ((height(node.getLeft()) - height(node.getRight())) == 2) {
                int cmp2 = c.compare(element, node.getLeft().element);
                node = (cmp2 < 0) ? rightRotation(node) : doubleRightRotation(node);
            }
        } else if (cmp > 0) {
            node.setRight(addRecursive(element, node.getRight()));
            if ((height(node.getRight()) - height(node.getLeft())) == 2) {
                int cmp2 = c.compare(node.getRight().element, element);
                node = (cmp2 < 0) ? leftRotation(node) : doubleLeftRotation(node);
            }
        }
        node.height = Math.max(height(node.getLeft()), height(node.getRight())) + 1;
        return node;
    }

    /**
     * Removes an item from a set.
     *
     * @param element
     */
    @Override
    public void remove(E element) {
        //throw new UnsupportedOperationException("Students need to implement remove(E element)");
        if (element == null) {
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }
        root = removeRecursive(element, (AVLNode<E>) root);
    }

    private AVLNode<E> removeRecursive(E element, AVLNode<E> n) {
        //throw new UnsupportedOperationException("Students need to implement removeRecursive(E element, AVLNode<E> n)");
        if (n == null){
            return n;
        }
        int cmp = c.compare(element, n.element);

        if (cmp < 0) {
            n.setLeft(removeRecursive(element, n.getLeft()));
        }
        else if (cmp > 0) {
            n.setRight(removeRecursive(element, n.getRight()));
        }
        else {
            if ((n.getLeft() == null) || (n.getRight() == null))
            {
                AVLNode<E> temp = null;
                if (temp == n.getLeft())
                    temp = n.getRight();
                else
                    temp = n.getLeft();

                if (temp == null)
                {
                    temp = n;
                    n = null;
                }
                else
                    n = temp;
            }
            else
            {
                AVLNode<E> temp = n.getMinVal(n.getRight());
                n.element = temp.element;
                n.setRight(removeRecursive(temp.element, n.getRight()));
            }
        }

        if (n == null)
            return n;

        n.height = Math.max(height(n.getLeft()), height(n.getRight())) + 1;

        int balance = n.getBalance(n);

        if (balance > 1 && n.getBalance(n.getLeft()) >= 0)
            return rightRotation(n);

        if (balance > 1 && n.getBalance(n.getLeft()) < 0)
        {
            root.left = leftRotation(n.getLeft());
            return rightRotation(n);
        }

        if (balance < -1 && n.getBalance(n.getRight()) <= 0)
            return leftRotation(n);

        if (balance < -1 && n.getBalance(n.getRight()) > 0)
        {
            root.right = rightRotation(n.getRight());
            return leftRotation(n);
        }

        return n;
    }

    // Additional private methods used to implement operations with the set
    // AVL-tree;

    //           n2
    //          /                n1
    //         n1      ==>      /  \
    //        /                n3  n2
    //       n3

    private AVLNode<E> rightRotation(AVLNode<E> n2) {
        AVLNode<E> n1 = n2.getLeft();
        n2.setLeft(n1.getRight());
        n1.setRight(n2);
        n2.height = Math.max(height(n2.getLeft()), height(n2.getRight())) + 1;
        n1.height = Math.max(height(n1.getLeft()), height(n2)) + 1;
        return n1;
    }

    private AVLNode<E> leftRotation(AVLNode<E> n1) {
        AVLNode<E> n2 = n1.getRight();
        n1.setRight(n2.getLeft());
        n2.setLeft(n1);
        n1.height = Math.max(height(n1.getLeft()), height(n1.getRight())) + 1;
        n2.height = Math.max(height(n2.getRight()), height(n1)) + 1;
        return n2;
    }

    //            n3               n3
    //           /                /                n2
    //          n1      ==>      n2      ==>      /  \
    //           \              /                n1  n3
    //            n2           n1
    //
    private AVLNode<E> doubleRightRotation(AVLNode<E> n3) {
        n3.left = leftRotation(n3.getLeft());
        return rightRotation(n3);
    }

    private AVLNode<E> doubleLeftRotation(AVLNode<E> n1) {
        n1.right = rightRotation(n1.getRight());
        return leftRotation(n1);
    }

    private int height(AVLNode<E> n) {
        return (n == null) ? -1 : n.height;
    }

    /**
     * Internal class of the collection node
     *
     * @param <N> node element data type
     */
    protected class AVLNode<N> extends BstNode<N> {

        protected int height;

        protected AVLNode(N element) {
            super(element);
            this.height = 0;
        }

        protected void setLeft(AVLNode<N> left) {
            this.left = left;
        }

        protected AVLNode<N> getLeft() {
            return (AVLNode<N>) left;
        }

        protected void setRight(AVLNode<N> right) {
            this.right = right;
        }

        protected AVLNode<N> getRight() {
            return (AVLNode<N>) right;
        }

        private AVLNode<E> getMinVal(AVLNode<E> node)
        {
            AVLNode<E> current = node;

            while (current.getLeft() != null)
                current = current.getLeft();

            return current;
        }
        private int getBalance(AVLNode<E> n)
        {
            if (n == null)
                return 0;
            return height(n.getLeft()) - height(n.getRight());
        }
    }
}
