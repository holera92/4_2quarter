package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true)
            new ClientHandler(serverSocket.accept()).start();
    }

    private static class ClientHandler extends Thread {

        private final Socket socket;
        private final InputStream in;
        private final OutputStream out;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
        }

        @Override
        public void interrupt() {
            super.interrupt();
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                var message = Parser.deserialize(readAllBytes(in));
                switch (message.type()) {
                    case Parser.REQUEST_PREFIX -> {
                        var file = new BufferedReader(new FileReader("fact.txt"));
                        List<Integer> values = new ArrayList<>();
                        for (int i = 0; i < message.numberOfValues(); i++) {
                            values.add(Integer.valueOf(file.readLine()));
                        }

                        out.write(
                                Parser.serialize(new ParseInfo(Parser.RESPONSE_PREFIX, values, 0)).getBytes(StandardCharsets.UTF_8)
                        );
                        file.close();
                    }

                    case Parser.RESPONSE_PREFIX -> {
                        var file = new PrintWriter(new FileOutputStream("answer.txt"));
                        message.values().forEach(file::println);
                        file.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static String readAllBytes(InputStream inputStream) throws IOException {
            var baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];

            var n = inputStream.read(buffer, 0, 512);
            baos.write(buffer);

            return baos.toString(Charset.defaultCharset()).trim();
        }
    }
}
