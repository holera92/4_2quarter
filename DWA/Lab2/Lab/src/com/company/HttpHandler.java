package com.company;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpHandler implements com.sun.net.httpserver.HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try (var outputStream = exchange.getResponseBody()) {

            if (exchange.getRequestMethod().equals("GET")) {


                String response = "<html>" +
                        "<body>" +
                        "<h1>" +
                        "Работу выполнял: Змитрович Н.С. ПИ-182(2)" +
                        "</h1>" +
                        "</body>";


                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
                exchange.sendResponseHeaders(200, response.length());
                outputStream.write(response.getBytes(StandardCharsets.UTF_8));
            }
        }

    }
}
