package com.example.full1.buisnesLogic;

import java.util.ArrayList;

public class Img {
    private Pixel[][] pixels;

    public Img(ArrayList<Pixel> pixels, int height, int wight) {
        this.pixels = new Pixel[height][wight];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < wight; j++) {
                this.pixels[i][j] = pixels.get(i * wight + j);
            }
        }
    }

    public Img(Pixel[][] pixels) {
        this.pixels = pixels;
    }

    public Pixel[][] getPixels() {
        return pixels;
    }

    public int[][] getPixelColor(){
        int[][] arr = new int[pixels.length][pixels[0].length];
        for (int i = 0; i < pixels.length ; i++) {
            for (int j = 0; j <pixels[0].length ; j++) {
                arr[i][j]=pixels[i][j].rgbToInt();
            }
        }
        return arr;
    }

    public int getHeight() {
        return pixels.length;
    }

    public int getWight() {
        return pixels[0].length;
    }

    public int[] get1dArray() {
        int[] newArray = new int[pixels.length * pixels[0].length];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                newArray[i * pixels[0].length + j] = pixels[i][j].rgbToInt();
            }
        }
        return newArray;
    }

}
