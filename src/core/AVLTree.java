package core;

import core.dto.AVLNode;

public class AVLTree {
    private AVLNode root;

    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    public AVLNode insert(AVLNode node, int seatNumber, boolean isAvailable) {
        if (node == null) {
            return new AVLNode(seatNumber, isAvailable);
        }

        if (seatNumber < node.seatNumber) {
            node.left = insert(node.left, seatNumber, isAvailable);
        } else if (seatNumber > node.seatNumber) {
            node.right = insert(node.right, seatNumber, isAvailable);
        } else {
            node.isAvailable = isAvailable;
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        // Left Left
        if (balance > 1 && seatNumber < node.left.seatNumber) {
            return rightRotate(node);
        }
        // Right Right
        if (balance < -1 && seatNumber > node.right.seatNumber) {
            return leftRotate(node);
        }
        // Left Right
        if (balance > 1 && seatNumber > node.left.seatNumber) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Right Left
        if (balance < -1 && seatNumber < node.right.seatNumber) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void insert(int seatNumber, boolean isAvailable) {
        root = insert(root, seatNumber, isAvailable);
    }

    public boolean isSeatAvailable(int seatNumber) {
        AVLNode current = root;
        while (current != null) {
            if (seatNumber < current.seatNumber) {
                current = current.left;
            } else if (seatNumber > current.seatNumber) {
                current = current.right;
            } else {
                return current.isAvailable;
            }
        }
        return false;
    }

    public void updateSeatAvailability(int seatNumber, boolean isAvailable) {
        root = insert(root, seatNumber, isAvailable);
    }

    public int getNextAvailableSeat() {
        AVLNode current = root;
        while (current != null) {
            if (current.isAvailable) {
                return current.seatNumber;
            }
            current = current.left != null && current.left.isAvailable ? current.left : current.right;
        }
        return -1;
    }
}
