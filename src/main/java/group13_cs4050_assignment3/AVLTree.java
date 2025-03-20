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

    /**
     *
     * @return color BLUEVIOLET to distinguish AVL tree from other algorithms
     */
    public Color color() {
        return Color.BLUEVIOLET;
    }

    /**
     *
     * @param value, value to be inserted into the tree
     */
    public void insert(T value) {
        this.root = this.insert(this.root, value);
        ++this.size;
    }

    /**
     *
     * @param node, from the passed node in the tree, find the bottom most point to calculate
     *              the height of the current node
     * @return the larger height between the heights of the left and right subtrees
     *
     * search for and return the height of both subtrees to find the maximum height of the current node
     */
    private int getHeight(AVLTree<T>.Node node) {
        if (node == null) {
            return 0;
        } else {
            int left = this.getHeight(node.left);
            int right = this.getHeight(node.right);
            return Math.max(left, right) + 1;
        }
    }

    /**
     *
     * @param node, at the current node, find the height of both the left and right subtrees
     *              then return the difference
     * @return the difference in height between the left and right subtrees of the current node
     *
     * used in determining whether the current state of the tree is balanced or not
     */
    private int heightDiff(AVLTree<T>.Node node) {
        int retVal;
        if (node == null) {
            retVal = 0;
        } else {
            retVal = this.getHeight(node.left) - this.getHeight(node.right);
        }

        return retVal;
    }

    /**
     *
     * @param node, current node at which to perform a left rotation
     * @return the node originally to the right of the passed node,
     * the node which now fills the position of the passed node
     *
     * set the passed node to the position at the left child of the node to the right of the passed node,
     * set the node originally to the left of the node to the right of the passed node to the position to the
     * right of the node to the right of the passed node.
     */
    private AVLTree<T>.Node rotateLeft(AVLTree<T>.Node node) {
        AVLTree<T>.Node rightTemp = node.right;
        AVLTree<T>.Node rightLeftTemp = rightTemp.left;
        rightTemp.left = node;
        node.right = rightLeftTemp;
        return rightTemp;
    }

    /**
     *
     * @param node, the node at which to perform a right rotation
     * @return the node originally to the left of the passed node, which now fills the position
     * of the passed node
     *
     * perform the opposite operation to the left rotation above
     */
    private AVLTree<T>.Node rotateRight(AVLTree<T>.Node node) {
        AVLTree<T>.Node leftTemp = node.left;
        AVLTree<T>.Node leftRightTemp = leftTemp.right;
        leftTemp.right = node;
        node.left = leftRightTemp;
        return leftTemp;
    }

    /**
     *
     * @param node, the node at which to identify empty positions to facilitate insertion
     *              of the passed value to the tree. if the current position is not null,
     *              compare the passed value to existing values to find the direction to iteratively move to
     *              to facilitate proper insertion of the value
     * @param value to be inserted into the tree
     * @return node
     */
    private AVLTree<T>.Node insert(AVLTree<T>.Node node, T value) {
        // values to be used for comparison in balancing
        T rightValue = null;
        T leftValue = null;
        // if current node position is null, return a new node at this position with the passed value
        if (node == null) {
            return new Node(value);
        // otherwise, given the left and right positions are not null, set the values of right and left value variables
        } else {
            if (node.right != null) {
                AVLTree<T>.Node nodeRight = node.right;
                rightValue = nodeRight.value;
            }

            if (node.left != null) {
                AVLTree<T>.Node nodeLeft = node.left;
                leftValue = nodeLeft.value;
            }
            // compare the passed value to the current node value to determine if the recursive call should go left or right
            if (value.compareTo(node.getValue()) < 0) {
                node.left = this.insert(node.left, value);
            } else if (value.compareTo(node.getValue()) > 0) {
                node.right = this.insert(node.right, value);
            }
            // get the difference in height to determine if the tree is balanced or not
            int diff = this.heightDiff(node);
            // if the height of the left subtree is greater than the right, and the current value is less than the value to the left, perform a right rotation
            if (diff > 1 && value.compareTo(leftValue) < 0) {
                return this.rotateRight(node);
            // if the height of the left subtree is greater than the right, and the current value is greater than the left, then perform a left right rotation
            } else if (diff > 1 && value.compareTo(leftValue) > 0) {
                node.left = this.rotateLeft(node.left);
                return this.rotateRight(node);
            // if the height of the right subtree is greater than the left, and the current value is greater than the right, then perform a left rotation
            } else if (diff < -1 && value.compareTo(rightValue) > 0) {
                return this.rotateLeft(node);
            // if the height of the right subtree is greater than the left, and the current value is less than the right, then perform a right left rotation
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

    /**
     *
     * @param value, value to be deleted from the tree
     * @return return true or false depending on if the new size is less than the original size as it should be
     * to indicate if the delete was performed successfully
     *
     * same as delete from BinarySearchTree
     */
    public boolean delete(T value) {
        int origSize = this.size;
        this.root = this.delete(this.root, value);
        return this.size < origSize;
    }

    /**
     *
     * @param node node at which to find the minimum value of the tree
     * @return minimum value
     *
     * same as one from BinarySearchTree
     */
    private AVLTree<T>.Node findMin(AVLTree<T>.Node node) {
        while(node.left != null) {
            node = node.left;
        }

        return node;
    }

    /**
     *
     * @param node position at which to start the search for the node to be deleted
     * @param value to be removed from the tree
     * @return node
     *
     * same as delete from BinarySearchTree, besides the addition of balancing portion
     */

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
            // same as balancing from the insertion
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

    /**
     *
     * @param value to be searched for in the tree
     * @return return true or false to indicate if the value is found
     *
     * same contains function as from BinarySearchTree
     */
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

    /**
     * clear tree
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * get stored size of tree
     * @return size
     */
    public int size() {
        return this.size;
    }

    /**
     * traverse the tree in order and store the visited values to a list
     * @return the list of stored values
     * same as function from BinarySearchTree
     */
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

    /**
     * get the current root value
     * @return root
     */
    public TreeNode<T> getRoot() {
        return this.root;
    }

    /**
     * AVL implementation of Node, same as implementation from BinarySearchTree
     */
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
