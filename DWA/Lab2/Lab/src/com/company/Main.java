package com.company;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        var httpServer = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        httpServer.setExecutor(Executors.newFixedThreadPool(4));
        httpServer.createContext("/", new HttpHandler());
        httpServer.start();
    }
}
