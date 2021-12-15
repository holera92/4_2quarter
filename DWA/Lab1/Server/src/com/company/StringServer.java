package com.company;

import com.company.result.ResultImpl;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.stream.Collectors;

public class StringServer {

    private static final String STRING = "2";
    private static final String ARRAY = "1";
    private static final String LIST = "3";

    public static void main(String[] args) {
        try (
                var server = new ServerSocket(12345, 1, InetAddress.getLocalHost()).accept();
                var inputStream = new BufferedReader(new InputStreamReader(server.getInputStream()));
                var outputStream = new PrintWriter(server.getOutputStream(), true);
            ) {

            String str = inputStream.readLine();

            switch (inputStream.readLine()) {
                case ARRAY:
                    var data = Arrays.stream(str.split(",")).map(Integer::valueOf).collect(Collectors.toList()).toArray(new Integer[1]);
                    outputStream.write(new ResultImpl(data).getResult());
                    break;
                case STRING:
                    outputStream.write(new ResultImpl(str).getResult());
                    break;
                case LIST:
                    var dataList = Arrays.stream(str.split(",")).map(Integer::valueOf).collect(Collectors.toList());
                    outputStream.write(new ResultImpl(dataList).getResult());
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
