package group13_cs4050_assignment3;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<T extends Comparable<T>> implements Tree<T> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private RBTreeNode root;

    // Basic node for Red-Black Tree
    private class RBTreeNode implements TreeNode<T> {
        T value;
        boolean color;
        RBTreeNode left, right, parent;

        RBTreeNode(T value, boolean color, RBTreeNode parent) {
            this.value = value;
            this.color = color;
            this.parent = parent;
            this.left = null;
            this.right = null;
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public TreeNode<T> getLeft() {
            return left;
        }

        @Override
        public TreeNode<T> getRight() {
            return right;
        }

        @Override
        public String getColor() {
            return color ? "RED" : "BLACK";
        }
    }

    public RedBlackTree() {
        this.root = null;
    }

    // Inserts a value into the tree and goes through cases to maintain red black properties
    @Override
    public void insert(T value) {
        // Insert like regular BST then run through the cases
        RBTreeNode node = insertBST(value);

        while (node != root && node.parent.color == RED) {
            RBTreeNode parent = node.parent;
            RBTreeNode grandparent = parent.parent;

            if (grandparent == null) break;

            if (parent == grandparent.left) {
                RBTreeNode uncle = grandparent.right;

                // Case 1
                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandparent.color = RED;
                    node = grandparent; // Move up
                } else {
                    // Case 3 - left rotate
                    if (node == parent.right) {
                        node = parent;
                        leftRotate(node);
                    }
                    // Case 2 - right rotate
                    parent.color = BLACK;
                    grandparent.color = RED;
                    rightRotate(grandparent);
                }
            } else { // Same as above but mirrored for right side
                RBTreeNode uncle = grandparent.left;

                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandparent.color = RED;
                    node = grandparent;
                } else {
                    if (node == parent.left) {
                        node = parent;
                        rightRotate(node);
                    }
                    parent.color = BLACK;
                    grandparent.color = RED;
                    leftRotate(grandparent);
                }
            }
        }
        root.color = BLACK; // Root must always be black
    }

    // Inserts a new node like a regular BST
    private RBTreeNode insertBST(T value) {
        if (root == null) {
            root = new RBTreeNode(value, BLACK, null); // First node is always black
            return root;
        }

        RBTreeNode current = root, parent = null;
        while (current != null) {
            parent = current;
            if (value.compareTo(current.value) < 0) {
                current = current.left;
            } else if (value.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                return current; // Value already exists
            }
        }

        RBTreeNode newNode = new RBTreeNode(value, RED, parent); // New nodes are red
        if (value.compareTo(parent.value) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        return newNode;
    }

    //Deletes value from tree
    @Override
    public boolean delete(T value) {
        if (root == null || !contains(value)) return false;
        root = deleteNode(root, value);
        if (root != null) root.color = BLACK;
        return true;
    }

    // Helper method to delete a node
    private RBTreeNode deleteNode(RBTreeNode node, T value) {
        if (node == null) return null;

        if (value.compareTo(node.value) < 0) {
            node.left = deleteNode(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = deleteNode(node.right, value);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Replace with smallest value in right subtree
            RBTreeNode minNode = findMin(node.right);
            node.value = minNode.value;
            node.right = deleteNode(node.right, minNode.value);
        }
        return node;
    }

    // Finds the smallest node in a subtree
    private RBTreeNode findMin(RBTreeNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    //Checks if value is in tree
    @Override
    public boolean contains(T value) {
        return contains(root, value);
    }

    private boolean contains(RBTreeNode node, T value) {
        if (node == null) return false;
        if (value.compareTo(node.value) == 0) return true;
        return value.compareTo(node.value) < 0 ? contains(node.left, value) : contains(node.right, value);
    }

    //Clears Tree
    @Override
    public void clear() {
        root = null;
    }

    //InOrder Traversal
    @Override
    public List<T> inorderTraversal() {
        List<T> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    private void inorderTraversal(RBTreeNode node, List<T> result) {
        if (node != null) {
            inorderTraversal(node.left, result);
            result.add(node.value);
            inorderTraversal(node.right, result);
        }
    }

    @Override
    public TreeNode<T> getRoot() {
        return root;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(RBTreeNode node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }

    @Override
    public String type() {
        return "RBTree";
    }

    @Override
    public Color color() {
        return root == null ? Color.BLACK : (root.color == RED ? Color.RED : Color.BLACK);
    }

    // Left Rotation
    private void leftRotate(RBTreeNode node) {
        RBTreeNode rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) rightChild.left.parent = node;
        rightChild.parent = node.parent;
        if (node.parent == null) root = rightChild;
        else if (node == node.parent.left) node.parent.left = rightChild;
        else node.parent.right = rightChild;
        rightChild.left = node;
        node.parent = rightChild;
    }

    // Right Rotation
    private void rightRotate(RBTreeNode node) {
        RBTreeNode leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) leftChild.right.parent = node;
        leftChild.parent = node.parent;
        if (node.parent == null) root = leftChild;
        else if (node == node.parent.right) node.parent.right = leftChild;
        else node.parent.left = leftChild;
        leftChild.right = node;
        node.parent = leftChild;
    }
}

