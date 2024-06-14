package com.example.full1.buisnesLogic;

import java.io.IOException;
import java.util.Random;

public class Main {
    /*public static void print(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void print(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }*/


    public static void main(String[] args) throws IOException, InterruptedException {
        /*ImageOperation imageOperation = new ImageOperation();
        var q=imageOperation.compressArray(imageOperation.readData().getPixels(), 8,1);
        HttpClient httpClient = new HttpClient();
        httpClient.sendMessage(q.get1dArray());*/

        for (int i = 0; i < 4; i++) {
            int[] arr = new int[16];
            Random random = new Random();
            for (int j = 0; j <arr.length ; j++) {
                arr[j]=random.nextInt(15,256*255*255);
            }
            HttpClient httpClient = new HttpClient();
            var q =httpClient.formMessage("aza",arr,(byte)0);

            httpClient.sendMessage(q);
            System.out.println("/output.png");

        }

    }


}