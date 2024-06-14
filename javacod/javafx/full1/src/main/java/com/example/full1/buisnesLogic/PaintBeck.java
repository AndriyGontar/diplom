package com.example.full1.buisnesLogic;

public class PaintBeck {
    private int[][] paint;
    public PaintBeck(int wight,int height){
        paint = new int[wight][height];
    }


    public int[][] getPaint() {
        return paint;
    }
    public  void setPixel(int x,int y, int pixel){
        if(x<paint.length && y<paint[0].length) {
            paint[x][y] = pixel;
        }
    }

    public void drawLine( int x1, int y1, int x2, int y2) {
        int value =1;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;
        while (x1 != x2 || y1 != y2) {
            paint[y1][x1] = value;
            int err2 = 2 * err;
            if (err2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (err2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
        paint[y2][x2] = value;
    }

    public  void drawSequence(int x1,int y1,int x2,int y2){
        int value=1; //pixel
        drawLine(x1,y1,x2,y1);
        drawLine(x1,y1,x1,y2);
        drawLine(x2,y2,x1,y2);
        drawLine(x2,y2,x2,y1);
    }
    public  void drawRhombus(int x1, int y1,int x2, int y2){
        int value=1; //pixel
        int xc =(Math.max(x1,x2)-Math.min(x1,x2))/2;
        int yc =(Math.max(y1,y2)-Math.min(y1,y2))/2;
        drawLine(x1,yc,xc,y1);
        drawLine(x1,yc,xc,y2);
        drawLine(x2,yc,xc,y2);
        drawLine(x2,yc,xc,y1);
    }
    
}
