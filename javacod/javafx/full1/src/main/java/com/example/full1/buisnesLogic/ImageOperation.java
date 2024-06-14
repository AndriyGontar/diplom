package com.example.full1.buisnesLogic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageOperation {

    public static Img readImage(String imagePath) throws IOException {

        BufferedImage myPicture = ImageIO.
                read(new File(imagePath));
        int width = myPicture.getWidth();
        int height = myPicture.getHeight();
        Pixel[][] result = new Pixel[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = new Pixel(myPicture.getRGB(col, row));
            }
        }
        return new Img(result);
    }

    public static void saveImage(int width, int height, Img arr1) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        var arr = arr1.get1dArray();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                bufferedImage.setRGB(j, i, arr1.getPixels()[i][j].rgbToInt());
            }
            System.out.println();
        }
        ImageIO.write(bufferedImage, "png", new File("src/main/resources/output.png"));
    }

    public static Img compressionArray(Pixel[][] arr, int y, int x) {
        Pixel[][] compressedArr = new Pixel[x][y];
        int origRows = arr.length;
        int origCols = arr[0].length;
        int rowStart = 0;
        int rowEnd = 0;
        var ostX = origRows % x;
        var ostY = origCols % y;
        for (int i = 0; i < x; i++) {
            int colStart = 0;
            int colEnd = 0;
            rowEnd += origRows / x;
            rowEnd += i < ostX ? 1 : 0;
            compressionLine(arr, y, colEnd, origCols, ostY, rowStart, rowEnd, colStart, compressedArr[i]);
            rowStart = rowEnd;
        }
        return new Img(compressedArr);
    }

    private static void compressionLine(Pixel[][] arr, int y, int colEnd, int origCols, int ostY, int rowStart, int rowEnd, int colStart, Pixel[] compressedArr) {
        for (int j = 0; j < y; j++) {
            int sumR = 0;
            int sumG = 0;
            int sumB = 0;
            int cellCount = 0;
            colEnd += origCols / y;
            colEnd += j < ostY ? 1 : 0;
            for (int m = rowStart; m < rowEnd; m++) {
                for (int n = colStart; n < colEnd; n++) {
                    sumR += arr[m][n].getR();
                    sumG += arr[m][n].getG();
                    sumB += arr[m][n].getB();
                    cellCount++;
                }
            }
            colStart = colEnd;
            compressedArr[j] = new Pixel(sumR / cellCount, sumG / cellCount, sumB / cellCount);
        }
    }

    public static Img bicubicInterpolate(Img img, int newWidth, int newHeight) {
        Pixel[][] pixels = img.getPixels();
        int oldWidth = img.getWight();
        int oldHeight = img.getHeight();

        Pixel[][] resultPixels = new Pixel[newHeight][newWidth];

        double xRatio = (double) oldWidth / newWidth;
        double yRatio = (double) oldHeight / newHeight;

        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                double x = j * xRatio;
                double y = i * yRatio;
                int xInt = (int) x;
                int yInt = (int) y;
                double xDiff = x - xInt;
                double yDiff = y - yInt;

                int r = (int) calculateBicubic(pixels, xInt, yInt, xDiff, yDiff, oldWidth, oldHeight, 'r');
                int g = (int) calculateBicubic(pixels, xInt, yInt, xDiff, yDiff, oldWidth, oldHeight, 'g');
                int b = (int) calculateBicubic(pixels, xInt, yInt, xDiff, yDiff, oldWidth, oldHeight, 'b');

                resultPixels[i][j] = new Pixel(clamp(r), clamp(g), clamp(b));
            }
        }

        return new Img(resultPixels);
    }

    private static double calculateBicubic(Pixel[][] pixels, int x, int y, double xDiff, double yDiff, int width, int height, char colorComponent) {
        double result = 0.0;
        for (int m = -1; m <= 2; m++) {
            for (int n = -1; n <= 2; n++) {
                result += getPixelValue(pixels, x + m, y + n, width, height, colorComponent) *
                        cubicPolynomial(m - xDiff) * cubicPolynomial(yDiff - n);
            }
        }
        return result;
    }

    private static int getPixelValue(Pixel[][] pixels, int x, int y, int width, int height, char colorComponent) {
        if (x < 0) x = 0;
        if (x >= width) x = width - 1;
        if (y < 0) y = 0;
        if (y >= height) y = height - 1;

        switch (colorComponent) {
            case 'r':
                return pixels[y][x].getR();
            case 'g':
                return pixels[y][x].getG();
            case 'b':
                return pixels[y][x].getB();
            default:
                throw new IllegalArgumentException("Invalid color component");
        }
    }

    private static double cubicPolynomial(double t) {
        t = Math.abs(t);
        if (t <= 1) {
            return (1.5 * t - 2.5) * t * t + 1;
        } else if (t <= 2) {
            return ((-0.5 * t + 2.5) * t - 4) * t + 2;
        } else {
            return 0;
        }
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
