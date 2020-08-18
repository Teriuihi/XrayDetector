package com.alttd.xraydetector.xraydetector.Objects;

public class Coordinates {

    private int x;
    private int y;
    private int z;

    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean isStraightLine(Coordinates coordinates){

        if (coordinates.getX() - 3 < x && x < coordinates.getX() + 3){
            return true;
        }

        if (coordinates.getZ() - 3 < z && z < coordinates.getZ() + 3){
            return true;
        }

        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "Coordinates: (x: " + x + " y: " + y + "z: " + z + ")";
    }
}
