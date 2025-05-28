package core.dto;

public class AVLNode {
    public int seatNumber;
    public boolean isAvailable;
    public int height;
    public AVLNode left, right;

    public AVLNode(int seatNumber, boolean isAvailable) {
        this.seatNumber = seatNumber;
        this.isAvailable = isAvailable;
        this.height = 1;
    }
}
