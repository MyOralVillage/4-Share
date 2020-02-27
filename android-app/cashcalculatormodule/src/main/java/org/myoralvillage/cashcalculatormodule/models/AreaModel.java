package org.myoralvillage.cashcalculatormodule.models;

import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

public class AreaModel {

    private Set<Box> boxes;

    public AreaModel() {
        this.boxes = new HashSet<>();
    }

    public void clearArea() {
        boxes.clear();
    }

    public void addBox(Box box) {
        boxes.add(box);
    }

    public void removeBox(Box box) {
        boxes.remove(box);
    }

    public Box getBoxFromPoint(float x, float y) {
        for (Box box : boxes) {
            if (box.isPointInBox(x, y)) {
                return box;
            }
        }

        return null;
    }

    /**
     * A box as viewed from the top left (of the screen)
     */
    public static class Box {
        private int minX;
        private int maxY;
        private int maxX;
        private int minY;

        public Box(int minX, int minY, int width, int height) {
            this.minX = minX;
            this.maxX = minX + width;
            this.minY = minY;
            this.maxY = minY + height;
        }

        boolean isPointInBox(float x, float y) {
            return x >= minX && x <= maxX && y >= minY && y <= maxY;
        }

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
