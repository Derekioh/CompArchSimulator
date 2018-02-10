package com.csci6461.team13.simulator.util;

import java.math.BigInteger;
import java.util.Arrays;

public class CoreUtil {

    private CoreUtil(){

    }

    // convert a binary value to a decimal value
    public static int binaryToDecimal(int binary) {
        return new BigInteger(String.valueOf(binary), 2).intValue();
    }

    // int to bits parsers
    // sample results
    // [0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0] <=> 100
    // [false, false, false, false, false, false, false, false, false, true, true, false, false, true, false, false] <=> 100
    public static boolean[] intToBooleans(int value) {
        byte[] bytes = intToBytes(value);
        boolean[] booleans = new boolean[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            booleans[i] = bytes[i] == 1;
        }
        return booleans;
    }

    public static int booleansToInt(boolean[] booleans) {
        byte[] bytes = new byte[booleans.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = booleans[i]?(byte)1:0;
        }
        return bytesToInt(bytes);
    }

    public static byte[] intToBytes(int value) {
        String binaryString = Integer.toBinaryString(value);
        byte[] bytes = new byte[16];
        for (int i = 16-binaryString.length(), j=0; i < bytes.length; i++,j++) {
            bytes[i] = Byte.valueOf(String.valueOf(binaryString.charAt(j)));
        }
        return bytes;
    }

    public static int bytesToInt(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte bit : bytes) {
            builder.append(bit);
        }
        String binaryString = builder.toString();
        return Integer.parseInt(binaryString, 2);
    }

    public static void main(String[] args) {
        byte[] bytes = intToBytes(100);
        System.out.println(Arrays.toString(bytes));
        System.out.println(bytesToInt(bytes));
        boolean[] booleans = intToBooleans(100);
        System.out.println(Arrays.toString(booleans));
        System.out.println(booleansToInt(booleans));
    }
}