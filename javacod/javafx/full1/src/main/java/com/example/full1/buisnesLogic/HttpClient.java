package com.example.full1.buisnesLogic;

import com.example.full1.buisnesLogic.error.TypeHttpError;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;


public class HttpClient {
    enum setting {
        weight,
        height,
        left,
        up,
        vertical,
        bright,
        ssid,
        password,
        pinM,
        pinS
    }

    public boolean sendImg(Img img, String name,boolean up,boolean left,boolean vertical){
        if (validName(name)){
            TransportImage transportImage = new TransportImage(img.getPixelColor(),vertical,up,left);
            var arr = transportImage.convertTo1D();
            for (int i = 0; i <arr.length ; i+=16) {
                sendMessage(formMessage(name, Arrays.copyOfRange(arr,i,16+i),(byte) 0));
            }
            return true;
        }
        else {
            return false;
        }
    }

    public void sendMessage(byte[] arr) {
        try {
            Socket socket = new Socket("192.168.4.1", 80);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.write(arr);
            outputStream.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();
            outputStream.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    byte[] formMessage(String name, int[] arr, byte type) {
        byte[] newMessage = new byte[64];
        newMessage[0] = type;
        for (int i = 0; i < name.length(); i++) {
            newMessage[i + 1] = (byte) name.toCharArray()[i];
        }
        if (type == 0) {
            var byteArr = extractFirstThreeBytes(arr);
            System.arraycopy(byteArr, 0, newMessage, 9, byteArr.length);
        }
        if (type > 2) {
            throw new TypeHttpError(type);
        }
        calculateChecksum(newMessage);
        return newMessage;
    }

    boolean validName(String line) {
        if (line.length() > 8) {
            return false;
        }
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) > 127) {
                return false;
            }
        }
        return true;
    }

    byte[] extractFirstThreeBytes(int[] data) {
        byte[] result = new byte[data.length * 3];
        for (int i = 0; i < data.length; i++) {
            int value = data[i];
            result[i * 3] = (byte) ((value >> 16) & 0xFF);
            result[i * 3 + 1] = (byte) ((value >> 8) & 0xFF);
            result[i * 3 + 2] = (byte) (value & 0xFF);
        }
        return result;
    }

    void calculateChecksum(byte[] data) {
        long checksum = 0;

        for (int i = 0; i < 57; i++) {
            checksum += (data[i] & 0xFF);
        }

        for (int i = 0; i < 7; i++) {
            data[57 + i] = (byte) (checksum >> (8 * (6 - i)));
        }
    }

    void generateSetting(){
        byte[] newMessage = new byte[64];
        newMessage[0]=(byte) 255;

    }
}
