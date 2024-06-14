package com.example.full1.buisnesLogic;

public class Pixel {
    private final int r;
    private final int g;
    private final int b;

    public int getB() {
        return b;
    }

    public int getG() {
        return g;
    }

    public int getR() {
        return r;
    }

    public Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Pixel(int col) {
        r = col / 65536;
        g = (col % 65536) / 256;
        b = col % 256;
    }

    public int[] GetPixelArray() {
        return new int[]{r, g, b};
    }

    public int rgbToInt() {
        return r * 65536 + g * 256 + b;
    }

    @Override
    public String toString() {
        return "r " + r + "g " + g + "b " + b;
    }

}
