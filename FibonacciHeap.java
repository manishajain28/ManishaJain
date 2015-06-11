package com.manisha.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class FibonacciHeap<T> {

    public static final class Entry<T> {
        private int     mDegree = 0;       // Number of children
        private boolean mIsMarked = false; // Whether this node is marked

        private Entry<T> mNext;   // Next and previous elements in the list
        private Entry<T> mPrev;

        private Entry<T> mParent; // Parent in the tree, if any.

        private Entry<T> mChild;  // Child node, if any.

        private T      mElem;     // Element being stored here
        private double mPriority; // Its priority

        /**
         * @return The element represented by this heap entry.
         */
        public T getValue() {
            return mElem;
        }

        /**
         * @return The priority of this element.
         */
        public double getPriority() {
            return mPriority;
        }

        /**
         * @param elem The element stored in this node.
         * @param priority The priority of this element.
         */
        Entry(T elem, double priority) {
            mNext = mPrev = this;
            mElem = elem;
            mPriority = priority;
        }
    }

    /* Pointer to the minimum element in the heap. */
    private Entry<T> mMin = null;

    /* Cached size of the heap, so we don't have to recompute this explicitly. */
    private int mSize = 0;

    /**
     * @param value The value to insert.
     * @param priority Its priority, which must be valid.
     * @return An Entry representing that element in the tree.
     */
    public Entry<T> enqueue(T value, double priority) {
        checkPriority(priority);

        /* Create the entry object, which is a circularly-linked list of length
         * one.
         */
        Entry<T> result = new Entry<T>(value, priority);

        /* Merge this singleton list with the tree list. */
        mMin = mergeLists(mMin, result);

        /* Increase the size of the heap; we just added something. */
        ++mSize;

        /* Return the reference to the new element. */
        return result;
    }

    /**
     *
     * @return The smallest element of the heap.
     * @throws NoSuchElementException If the heap is empty.
     */
    public Entry<T> min() {
        if (isEmpty())
            throw new NoSuchElementException("Heap is empty.");
        return mMin;
    }

    /**
     * @return Whether the heap is empty.
     */
    public boolean isEmpty() {
        return mMin == null;
    }

    /**
     * @return The number of elements in the heap.
     */
    public int size() {
        return mSize;
    }


    /**
     *
     * @return The smallest element of the Fibonacci heap.
     * @throws NoSuchElementException If the heap is empty.
     */
    public Entry<T> dequeueMin() {
        /* Check for whether we're empty. */
        if (isEmpty())
            throw new NoSuchElementException("Heap is empty.");

        /* Otherwise, we're about to lose an element, so decrement the number of
         * entries in this heap.
         */
        --mSize;

        /* Grab the minimum element so we know what to return. */
        Entry<T> minElem = mMin;

        if (mMin.mNext == mMin) { // Case one
            mMin = null;
        }
        else { // Case two
            mMin.mPrev.mNext = mMin.mNext;
            mMin.mNext.mPrev = mMin.mPrev;
            mMin = mMin.mNext; // Arbitrary element of the root list.
        }


        if (minElem.mChild != null) {
            /* Keep track of the first visited node. */
            Entry<?> curr = minElem.mChild;
            do {
                curr.mParent = null;

                /* Walk to the next node, then stop if this is the node we
                 * started at.
                 */
                curr = curr.mNext;
            } while (curr != minElem.mChild);
        }

        mMin = mergeLists(mMin, minElem.mChild);

        /* If there are no entries left, we're done. */
        if (mMin == null) return minElem;
        List<Entry<T>> treeTable = new ArrayList<Entry<T>>();
        List<Entry<T>> toVisit = new ArrayList<Entry<T>>();
        for (Entry<T> curr = mMin; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.mNext)
            toVisit.add(curr);

        /* Traverse this list and perform the appropriate unioning steps. */
        for (Entry<T> curr: toVisit) {
            /* Keep merging until a match arises. */
            while (true) {
                /* Ensure that the list is long enough to hold an element of this
                 * degree.
                 */
                while (curr.mDegree >= treeTable.size())
                    treeTable.add(null);

                /* If nothing's here, we're can record that this tree has this size
                 * and are done processing.
                 */
                if (treeTable.get(curr.mDegree) == null) {
                    treeTable.set(curr.mDegree, curr);
                    break;
                }

                /* Otherwise, merge with what's there. */
                Entry<T> other = treeTable.get(curr.mDegree);
                treeTable.set(curr.mDegree, null); // Clear the slot

                /* Determine which of the two trees has the smaller root, storing
                 * the two tree accordingly.
                 */
                Entry<T> min = (other.mPriority < curr.mPriority)? other : curr;
                Entry<T> max = (other.mPriority < curr.mPriority)? curr  : other;

                /* Break max out of the root list, then merge it into min's child
                 * list.
                 */
                max.mNext.mPrev = max.mPrev;
                max.mPrev.mNext = max.mNext;

                /* Make it a singleton so that we can merge it. */
                max.mNext = max.mPrev = max;
                min.mChild = mergeLists(min.mChild, max);
                
                /* Reparent max appropriately. */
                max.mParent = min;

                /* Clear max's mark, since it can now lose another child. */
                max.mIsMarked = false;

                /* Increase min's degree; it now has another child. */
                ++min.mDegree;

                /* Continue merging this tree. */
                curr = min;
            }

            if (curr.mPriority <= mMin.mPriority) mMin = curr;
        }
        return minElem;
    }


    /**
     *
     * @param priority The user's specified priority.
     * @throws IllegalArgumentException If it is not valid.
     */
    private void checkPriority(double priority) {
        if (Double.isNaN(priority))
            throw new IllegalArgumentException(priority + " is invalid.");
    }


    private static <T> Entry<T> mergeLists(Entry<T> one, Entry<T> two) {
        /* There are four cases depending on whether the lists are null or not.
         * We consider each separately.
         */
        if (one == null && two == null) { // Both null, resulting list is null.
            return null;
        }
        else if (one != null && two == null) { // Two is null, result is one.
            return one;
        }
        else if (one == null && two != null) { // One is null, result is two.
            return two;
        }
        else { 
            Entry<T> oneNext = one.mNext; // Cache this since we're about to overwrite it.
            one.mNext = two.mNext;
            one.mNext.mPrev = one;
            two.mNext = oneNext;
            two.mNext.mPrev = two;

            /* Return a pointer to whichever's smaller. */
            return one.mPriority < two.mPriority? one : two;
        }
    }
}
