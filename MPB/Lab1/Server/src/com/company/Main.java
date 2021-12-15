package com.company;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        var server = new Server();
        server.start(12345);
    }


}
