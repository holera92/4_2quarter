import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Client {

   private final InputStream in;
   private final OutputStream out;
   private final Socket socket;

   private Client(Socket sock) throws IOException {
       socket = sock;
       in = sock.getInputStream();
       out = sock.getOutputStream();
   }

   public static Client create(Socket sock) throws IOException {
      return new Client(sock);
   }

   public void send(ParseInfo info) throws IOException {
      out.write(Parser.serialize(info).getBytes(StandardCharsets.UTF_8));
   }

   public ParseInfo receive() throws IOException {
      return Parser.deserialize(readAllBytes(in));
   }

   public void close() throws IOException {
      socket.close();
   }

   private String readAllBytes(InputStream inputStream) throws IOException {
      var baos = new ByteArrayOutputStream();
      byte[] buffer = new byte[512];

      var n = inputStream.read(buffer, 0, 512);
      baos.write(buffer);

      return baos.toString(Charset.defaultCharset());
   }
}
