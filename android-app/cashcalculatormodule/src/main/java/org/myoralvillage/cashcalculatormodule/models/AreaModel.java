package org.myoralvillage.cashcalculatormodule.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A model class used to represent an area for the denominations in the CurrencyScrollbarView and
 * CountingTableView.
 *
 * @author Hamza Mahfooz
 * @author Peter Panagiotis Roubatsis
 * @see java.lang.Object
 */
public class AreaModel {
    /**
     * A List of boxes created when a new denomination is added to this specific area. Each element
     * in the list contains a <code>Box</code> class.
     *
     * @see Box
     */
    private List<Box> boxes;

    /**
     * Constructs a new area with an empty list of boxes instantiated.
     */
    public AreaModel() {
        this.boxes = new ArrayList<>();
    }

    /**
     * Removes all the boxes present in this area.
     */
    public void clearArea() {
        boxes.clear();
    }

    /**
     * Adds a box to the list of boxes for this object.
     *
     * @param box A box to be added.
     * @see Box
     */
    public void addBox(Box box) {
        boxes.add(box);
    }

    /**
     * Removes the last box added to the list of boxes for this object.
     *
     * @see Box
     */
    public void removeLastBox() {
        if (boxes.size() > 0) {
            boxes.remove(boxes.get(boxes.size() - 1));
        }
    }

    /**
     * Returns the index of the box from the list of boxes based on the location of the box, a point,
     * in the <code>AreaModel</code>.
     *
     * @param x The x coordinate of this point.
     * @param y The y coordinate of this point.
     * @return The index of the box from the list, <code>boxes</code>. If the box is not found,
     * return -1.
     *
     * @see Box
     */
    public int getBoxIndexFromPoint(float x, float y) {
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).isPointInBox(x, y)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returns the <code>Box</code> from the list of boxes based on the location of the box, a point,
     * in the <code>AreaModel</code>.
     *
     * @param x The x coordinate of this point.
     * @param y The y coordinate of this point.
     * @return The <code>Box</code> form the list, <code>boxes</code>. If the box is not found,
     * return null.
     *
     * @see Box
     */
    public Box getBoxFromPoint(float x, float y) {
        int index = getBoxIndexFromPoint(x, y);
        if (index >= 0) return boxes.get(index);
        return null;
    }

    /**
     * A model class used to represent a rectangular area for each denomination in the
     * <code>CurrencyScrollBarView</code> and <code>CountingTableView</code>. This area allows for
     * gestures to be performed on the denominations. A box is viewed from the top left (of the
     * screen).
     *
     * @author Peter Panagiotis Roubatsis
     * @author Hamza Mahfooz
     * @see java.lang.Object
     */
    public static class Box {
        /**
         * An integer used to record the x coordinate of the left side of the <code>Box</code>.
         * It is the smallest x value of the <code>box</code>
         */
        private int minX;

        /**
         * An integer used to record the y coordinate of the bottom of the <code>Box</code>.
         * It is the largest y value of the <code>box</code>
         */
        private int maxY;

        /**
         * An integer used to record the x coordinate of the right side of the <code>Box</code>.
         * It is the largest x value of the <code>box</code>
         */
        private int maxX;

        /**
         * An integer used to record the y coordinate of the top of the <code>Box</code>.
         * It is the smallest y value of the <code>box</code>
         */
        private int minY;

        /**
         * Constructs a new <code>Box</code> with the specified dimensions of the box and the
         * point of the top left vertex of the rectangular box.
         *
         * @param minX The x coordinate of the top left vertex of where the <code>Box</code> should
         *             be placed.
         * @param minY The y coordinate of the top left vertex of where the <code>Box</code> should
         *             be placed.
         * @param width The width of the rectangular area.
         * @param height The height of the rectangular area.
         */
        public Box(int minX, int minY, int width, int height) {
            this.minX = minX;
            this.maxX = minX + width;
            this.minY = minY;
            this.maxY = minY + height;
        }

        /**
         * Indicates whether the specified point is in the area of the <code>Box</code>.
         *
         * @param x The x coordinate of the point.
         * @param y The y coordinate of the point.
         * @return true if this point is the area of the <code>Box</code>; false otherwise.
         */
        boolean isPointInBox(float x, float y) {
            return x >= minX && x <= maxX && y >= minY && y <= maxY;
        }

        public int getX() {
            return minX;
        }

        public int getY() {
            return minY;
        }

        /**
         * Indicates whether some other <code>Box</code> is equal to this one.
         *
         * @param obj a <code>Box</code> with which to compare.
         * @return true if this <code>Box</code> is the same as the obj argument; false
         * otherwise.
         */
        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof Box) {
                Box box = (Box) obj;
                return box.minX == minX && box.maxX == maxX
                        && box.minY == minY && box.maxY == maxY;
            }

            return false;
        }
    }

}
