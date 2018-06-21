package ap2.imageserviceapp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import org.json.JSONObject;
import java.nio.file.Files;
import java.util.Base64;

public class TCPCommunicator implements ITCPCommunicator{
    private Socket socket;


    public void connect() {
        try {
            InetAddress serverAddr = InetAddress.getByName("10.0.2.2");
            this.socket = new Socket(serverAddr, 9900);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(File img) {
        try {
            JSONObject photo = new JSONObject();
            photo.put("name", img.getName());
            byte[] bytes = Files.readAllBytes(img.toPath());
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            photo.put("bytes", base64Image);
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            out.println(photo.toString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}