package ap2.imageserviceapp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import org.json.JSONObject;
import java.nio.file.Files;
import java.util.Base64;

public class TCPCommunicator implements ITCPCommunicator{
    private Socket socket;

    /**
     * The function opens a new socket and connects to the server
     */
    public void connect() {
        try {
            InetAddress serverAddr = InetAddress.getByName("10.0.2.2"); // set IP
            this.socket = new Socket(serverAddr, 9900); // create socket
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The function creates a JSONObject for the given file, and sends it to the server
     * @param img - the file to send
     */
    public void send(File img) {
        try {
            // create JSONObject for a photo file
            JSONObject photo = new JSONObject();
            photo.put("name", img.getName()); // set file's name
            // create a bytes array of file info
            byte[] bytes = Files.readAllBytes(img.toPath());
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            photo.put("bytes", base64Image); // set file's bytes
            // create a PrintWriter to socket's OutputStream
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            // send the JSONObject
            out.println(photo.toString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The function closes the socket, and disconnects from the server
     */
    public void disconnect() {
        try {
            this.socket.close(); // close socket
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}