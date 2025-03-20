//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package group13_cs4050_assignment3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

public class AVLTree<T extends Comparable<T>> implements Tree<T>, Serializable {
    private AVLTree<T>.Node root;
    private int size;

    public AVLTree() {
    }

    public Color color() {
        return Color.BLUEVIOLET;
    }

    public void insert(T value) {
        this.root = this.insert(this.root, value);
        ++this.size;
    }

    private int getHeight(AVLTree<T>.Node node) {
        if (node == null) {
            return 0;
        } else {
            int left = this.getHeight(node.left);
            int right = this.getHeight(node.right);
            return Math.max(left, right) + 1;
        }
    }

    private int heightDiff(AVLTree<T>.Node node) {
        int retVal;
        if (node == null) {
            retVal = 0;
        } else {
            retVal = this.getHeight(node.left) - this.getHeight(node.right);
        }

        return retVal;
    }

    private AVLTree<T>.Node rotateLeft(AVLTree<T>.Node node) {
        AVLTree<T>.Node rightTemp = node.right;
        AVLTree<T>.Node rightLeftTemp = rightTemp.left;
        rightTemp.left = node;
        node.right = rightLeftTemp;
        return rightTemp;
    }

    private AVLTree<T>.Node rotateRight(AVLTree<T>.Node node) {
        AVLTree<T>.Node leftTemp = node.left;
        AVLTree<T>.Node leftRightTemp = leftTemp.right;
        leftTemp.right = node;
        node.left = leftRightTemp;
        return leftTemp;
    }

    private AVLTree<T>.Node insert(AVLTree<T>.Node node, T value) {
        T rightValue = null;
        T leftValue = null;
        if (node == null) {
            return new Node(value);
        } else {
            if (node.right != null) {
                AVLTree<T>.Node nodeRight = node.right;
                rightValue = nodeRight.value;
            }

            if (node.left != null) {
                AVLTree<T>.Node nodeLeft = node.left;
                leftValue = nodeLeft.value;
            }

            if (value.compareTo(node.getValue()) < 0) {
                node.left = this.insert(node.left, value);
            } else if (value.compareTo(node.getValue()) > 0) {
                node.right = this.insert(node.right, value);
            }

            int diff = this.heightDiff(node);
            if (diff > 1 && value.compareTo(leftValue) < 0) {
                return this.rotateRight(node);
            } else if (diff > 1 && value.compareTo(leftValue) > 0) {
                node.left = this.rotateLeft(node.left);
                return this.rotateRight(node);
            } else if (diff < -1 && value.compareTo(rightValue) > 0) {
                return this.rotateLeft(node);
            } else if (diff < -1 && value.compareTo(rightValue) < 0) {
                node.right = this.rotateRight(node.right);
                return this.rotateLeft(node);
            } else {
                return node;
            }
        }
    }

    public String type() {
        return "AVL";
    }

    public boolean delete(T value) {
        int origSize = this.size;
        this.root = this.delete(this.root, value);
        return this.size < origSize;
    }

    private AVLTree<T>.Node findMin(AVLTree<T>.Node node) {
        while(node.left != null) {
            node = node.left;
        }

        return node;
    }

    private AVLTree<T>.Node delete(AVLTree<T>.Node node, T value) {
        T rightValue = null;
        T leftValue = null;
        if (node == null) {
            return null;
        } else {
            if (node.right != null) {
                AVLTree<T>.Node nodeRight = node.right;
                rightValue = nodeRight.value;
            }

            if (node.left != null) {
                AVLTree<T>.Node nodeLeft = node.left;
                leftValue = nodeLeft.value;
            }

            if (value.compareTo(node.value) < 0) {
                --this.size;
                node.left = this.delete(node.left, value);
            } else if (value.compareTo(node.value) > 0) {
                --this.size;
                node.right = this.delete(node.right, value);
            } else {
                if (node.left == null) {
                    --this.size;
                    return node.right;
                }

                if (node.right == null) {
                    --this.size;
                    return node.left;
                }

                AVLTree<T>.Node minRight = this.findMin(node.right);
                node.value = minRight.value;
                node.right = this.delete(node.right, minRight.value);
            }

            int diff = this.heightDiff(node);
            if (diff > 1 && value.compareTo(leftValue) < 0) {
                return this.rotateRight(node);
            } else if (diff > 1 && value.compareTo(leftValue) > 0) {
                node.left = this.rotateLeft(node.left);
                return this.rotateRight(node);
            } else if (diff < -1 && value.compareTo(rightValue) > 0) {
                return this.rotateLeft(node);
            } else if (diff < -1 && value.compareTo(rightValue) < 0) {
                node.right = this.rotateRight(node.right);
                return this.rotateLeft(node);
            } else {
                return node;
            }
        }
    }

    @Override
    public boolean contains(T value) {
        return contains(root, value);
    }

    private boolean contains(Node node, T value) {
        if (node == null) {
            return false;
        }
        if (value.compareTo(node.value) == 0) {
            return true;
        } else if (value.compareTo(node.value) < 0) {
            return contains(node.left, value);
        } else {
            return contains(node.right, value);
        }
    }
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public List<T> inorderTraversal() {
        List<T> result = new ArrayList();
        this.inorderTraversal(this.root, result);
        return result;
    }

    private void inorderTraversal(AVLTree<T>.Node node, List<T> result) {
        if (node != null) {
            this.inorderTraversal(node.left, result);
            result.add(node.value);
            this.inorderTraversal(node.right, result);
        }

    }

    public TreeNode<T> getRoot() {
        return this.root;
    }

    private class Node implements TreeNode<T>, Serializable {
        T value;
        Node left;
        Node right;

        Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return this.value;
        }

        public TreeNode<T> getLeft() {
            return this.left;
        }

        public TreeNode<T> getRight() {
            return this.right;
        }

        public String getColor() {
            return null;
        }
    }
}
