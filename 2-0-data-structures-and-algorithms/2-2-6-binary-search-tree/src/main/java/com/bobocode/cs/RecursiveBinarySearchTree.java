package com.bobocode.cs;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * {@link RecursiveBinarySearchTree} is an implementation of a {@link BinarySearchTree} that is based on a linked nodes
 * and recursion. A tree node is represented as a nested class {@link Node}. It holds an element (a value) and
 * two references to the left and right child nodes.
 *
 * @param <T> a type of elements that are stored in the tree
 * @author Taras Boychuk
 * @author Maksym Stasiuk
 */
public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private Node<T> root;
    private int size = 0;

    @SafeVarargs
    public static <T extends Comparable<T>> RecursiveBinarySearchTree<T> of(T... elements) {
        final var tree = new RecursiveBinarySearchTree<T>();
        Stream.of(elements).forEach(tree::insert);
        return tree;
    }

    @Override
    public boolean insert(T element) {
        if (null == root) {
            root = new Node<>(element);
            size++;
            return true;
        }
        return insert(root, element);
    }

    private boolean insert(Node<T> node, T element) {
        if (node.value.compareTo(element) > 0) {
            return processInsertOnSubNode(null == node.left, node.left, n -> node.left = n, element);
        } else if (node.value.compareTo(element) < 0) {
            return processInsertOnSubNode(null == node.right, node.right, n -> node.right = n, element);
        }
        return false;
    }

    private boolean processInsertOnSubNode(boolean subNodeNull, Node<T> subNode,
                                           Consumer<Node<T>> setSubNode, T element) {
        if (subNodeNull) {
            setSubNode.accept(new Node<>(element));
            size++;
            return true;
        }
        return insert(subNode, element);
    }

    @Override
    public boolean contains(T element) {
        Objects.requireNonNull(element);
        return contains(root, element);
    }

    private boolean contains(Node<T> node, T element) {
        if (null == node) {
            return false;
        } else if (node.value.compareTo(element) > 0) {
            return contains(node.left, element);
        } else if (node.value.compareTo(element) < 0) {
            return contains(node.right, element);
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int depth() {
        return null == root ? 0 : depth(root) - 1;
    }

    private int depth(Node<T> node) {
        return null == node ? 0 : 1 + Math.max(depth(node.left), depth(node.right));
    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        inOrderTraversal(root, consumer);
    }

    private void inOrderTraversal(Node<T> node, Consumer<T> consumer) {
        if (null == node) return;
        inOrderTraversal(node.left, consumer);
        consumer.accept(node.value);
        inOrderTraversal(node.right, consumer);
    }

    static class Node<T> {
        private final T value;
        private Node<T> left;
        private Node<T> right;

        public Node(T value) {
            this.value = value;
        }
    }
}
