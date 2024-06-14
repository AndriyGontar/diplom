package com.example.full1.buisnesLogic;

public class TransportImage {
    private final int[][] arr;
    private final boolean vertical;
    private  boolean up;
    private  boolean left;

    public TransportImage(int[][] arr, boolean vertical, boolean up, boolean left) {
        this.arr = arr;
        this.vertical = vertical;
        this.up = up;
        this.left = left;
    }

    public int[] convertTo1D() {
        int iStop = vertical ? arr.length : arr[0].length;
        int jStop = vertical ? arr[0].length : arr.length;
        int[] newArr = new int[arr.length * arr[0].length];
        up= vertical == up;
        left = vertical==left;
        int index = 0;
        boolean reverse = false;
        for (int i = up ? 0 : (iStop - 1) * -1; i <= (up ? iStop-1 : 0); i++) {
            boolean line = (reverse&&left)||(!reverse&& !left);
            for (int j = line? (jStop - 1)*-1 : 0; j <= (line?0:jStop-1); j++) {
                newArr[index++] = arr[vertical?Math.abs(i):Math.abs(j)][vertical?Math.abs(j):Math.abs(i)];
            }
            reverse = !reverse;
        }
        return newArr;
    }
}
