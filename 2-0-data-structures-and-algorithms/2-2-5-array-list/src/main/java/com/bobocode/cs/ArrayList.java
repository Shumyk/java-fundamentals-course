package com.bobocode.cs;

import com.bobocode.util.ExerciseNotCompletedException;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * {@link ArrayList} is an implementation of {@link List} interface. This resizable data structure
 * based on an array and is simplified version of {@link java.util.ArrayList}.
 *
 * @author Serhii Hryhus
 */
public class ArrayList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 5;

    private Object[] data;
    private int size = 0;

    /**
     * This constructor creates an instance of {@link ArrayList} with a specific capacity of an array inside.
     *
     * @param initCapacity - the initial capacity of the list
     * @throws IllegalArgumentException – if the specified initial capacity is negative or 0.
     */
    public ArrayList(int initCapacity) {
        if (initCapacity <= 0) throw new IllegalArgumentException();

        this.data = new Object[initCapacity];
    }

    /**
     * This constructor creates an instance of {@link ArrayList} with a default capacity of an array inside.
     * A default size of inner array is 5;
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates and returns an instance of {@link ArrayList} with provided elements
     *
     * @param elements to add
     * @return new instance
     */
    @SafeVarargs
    public static <T> List<T> of(T... elements) {
        final var list = new ArrayList<T>(elements.length);
        Stream.of(elements).forEach(list::add);
        return list;
    }

    /**
     * Adds an element to the array.
     *
     * @param element element to add
     */
    @Override
    public void add(T element) {
        increaseInnerArraySize();
        data[size] = element;
        size++;
    }

    private void increaseInnerArraySize() {
        if (data.length == size) {
            var newArray = new Object[data.length * 2];
            System.arraycopy(data, 0, newArray, 0, data.length);
            data = newArray;
        }
    }

    /**
     * Adds an element to the specific position in the array where
     *
     * @param index   index of position
     * @param element element to add
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException(index);

        increaseInnerArraySize();
        moveSubSequenceInArrayToRight(index);

        data[index] = element;
        size++;
    }

    /**
     * Retrieves an element by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index index of element
     * @return en element
     */
    @Override
    public T get(int index) {
        if (index < 0 || index + 1 > size) throw new IndexOutOfBoundsException(index);
        return (T) data[index];
    }

    /**
     * Returns the first element of the list. Operation is performed in constant time O(1)
     *
     * @return the first element of the list
     * @throws java.util.NoSuchElementException if list is empty
     */
    @Override
    public T getFirst() {
        if (size == 0) throw new NoSuchElementException();
        return (T) data[0];
    }

    /**
     * Returns the last element of the list. Operation is performed in constant time O(1)
     *
     * @return the last element of the list
     * @throws java.util.NoSuchElementException if list is empty
     */
    @Override
    public T getLast() {
        if (size == 0) throw new NoSuchElementException();
        return (T) data[size - 1];
    }

    /**
     * Changes the value of array at specific position. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   position of value
     * @param element a new value
     */
    @Override
    public void set(int index, T element) {
        if (index < 0 || index + 1 > size) throw new IndexOutOfBoundsException(index);
        data[index] = element;
    }

    /**
     * Removes an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return deleted element
     */
    @Override
    public T remove(int index) {
        if (index < 0 || index + 1 > size) throw new IndexOutOfBoundsException(index);

        final T deletedElement = (T) data[index];
        data[index] = null;
        moveSubSequenceInArrayToLeft(index);
        size--;
        return deletedElement;
    }

    private void moveSubSequenceInArrayToLeft(int index) {
        for (int i = index; i < size; i++) {
            if (i == data.length - 1) data[i] = null;
            else data[i] = data[i + 1];
        }
    }

    private void moveSubSequenceInArrayToRight(int index) {
        for (int i = size - 1; i > index; i--) {
            if (i != 0) data[i] = data[i - 1];
        }
    }

    /**
     * Checks for existing of a specific element in the list.
     *
     * @param element is element
     * @return If element exists method returns true, otherwise it returns false
     */
    @Override
    public boolean contains(T element) {
        return Stream.of(data)
                .anyMatch(e -> Objects.equals(e, element));
    }

    /**
     * Checks if a list is empty
     *
     * @return {@code true} if list is empty, {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return amount of saved elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes all list elements
     */
    @Override
    public void clear() {
        Arrays.fill(data, null);
        size = 0;
    }
}
