import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {
        var client = Client.create(new Socket(InetAddress.getLoopbackAddress(), 12345));
        client.send(
                new ParseInfo(
                        Parser.REQUEST_PREFIX,
                        Collections.emptyList(),
                        3
                )
        );

        var message = client.receive();
        client.close();

        client = Client.create(new Socket(InetAddress.getLoopbackAddress(), 12345));

        client.send(
                new ParseInfo(
                        Parser.RESPONSE_PREFIX,
                        message.values().stream().map(Main::fact).toList(),
                        0
                )
        );
        client.close();

    }

    private static int fact(int n) {
        if (n == 1) return 1;

        return n * fact(n - 1);
    }

    private static String readAllBytes(InputStream inputStream) throws IOException {
        var baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];

        var n = inputStream.read(buffer, 0, 512);
        baos.write(buffer);

        return baos.toString(Charset.defaultCharset());
    }
}
