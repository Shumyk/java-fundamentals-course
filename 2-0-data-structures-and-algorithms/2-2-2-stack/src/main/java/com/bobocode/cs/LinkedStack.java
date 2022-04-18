package com.bobocode.cs;

import com.bobocode.cs.exception.EmptyStackException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * {@link LinkedStack} is a stack implementation that is based on singly linked generic nodes.
 * A node is implemented as inner static class {@link Node<T>}.
 *
 * @param <T> generic type parameter
 * @author Taras Boychuk
 * @author Serhii Hryhus
 */
public class LinkedStack<T> implements Stack<T> {

    private Node<T> head;
    private int size = 0;

    /**
     * This method creates a stack of provided elements
     *
     * @param elements elements to add
     * @param <T>      generic type
     * @return a new stack of elements that were passed as method parameters
     */
    @SafeVarargs
    public static <T> LinkedStack<T> of(T... elements) {
        final var stack = new LinkedStack<T>();
        Stream.of(elements).forEach(stack::push);
        return stack;
    }

    /**
     * The method pushes an element onto the top of this stack. This has exactly the same effect as:
     * addElement(item)
     *
     * @param element elements to add
     */
    @Override
    public void push(T element) {
        requireNonNull(element);
        final var newNode = new Node<>(element);
        if (head != null) {
            newNode.next = head;
        }
        head = newNode;
        size++;
    }

    /**
     * This method removes the object at the top of this stack
     * and returns that object as the value of this function.
     *
     * @return The object at the top of this stack
     * @throws EmptyStackException - if this stack is empty
     */
    @Override
    public T pop() {
        if (head == null) {
            throw  new EmptyStackException();
        }
        final T currentValue = head.value;
        head = head.next;
        size--;
        return currentValue;
    }

    /**
     * Returns the number of elements in the stack
     *
     * @return number of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if a stack is empty
     *
     * @return {@code true} if a stack is empty, {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
