package com.company;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] argv) {
        try (var client = new Socket(InetAddress.getLocalHost(), 12345)) {
            var outputStream = new PrintWriter(client.getOutputStream(), true);
            var inputStream = new BufferedReader(new InputStreamReader(client.getInputStream())
            );

            String numbers = "11,32,1,22,14";

            outputStream.println(numbers);

            System.out.println("Выберите как обрабатывать данные на сервере");
            System.out.println("1 - массив");
            System.out.println("2 - строка");
            System.out.println("3 - список");

            outputStream.println(new Scanner(System.in).nextLine());

            System.out.println(inputStream.readLine());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
