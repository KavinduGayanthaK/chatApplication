package lk.ijse.service;

import com.google.gson.Gson;
import javafx.concurrent.Task;
import lk.ijse.controller.ClientController;
import lk.ijse.dto.ReceivedData;
import lk.ijse.dto.TransferData;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ClientSide extends Task<ReceivedData>  {
    Socket socket;
    //DataOutputStream dataOutputStream;
    String name;
    final private DataInputStream dataInputStream;

    final private DataOutputStream dataOutputStream;
    final private ClientController controller;

    public ClientSide(ClientController controller) {
        try {
            socket = new Socket("localhost",3013);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.controller = controller;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected ReceivedData call() throws Exception {

        while (true) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = "";
            while ((message = bufferedReader.readLine()) !=null) {
                updateValue(new ReceivedData(message));


            }
        }
    }



    public void sendToClient(TransferData message) throws IOException {
        PrintWriter printWriter = new PrintWriter( socket.getOutputStream(),true);
        String sendAbleData = new Gson().toJson(message);
        name = message.getFrom();
        System.out.println(sendAbleData);
        printWriter.println(sendAbleData);
    }

    public void sendImg(String fName, byte[] imageBytes) {
        try {
            dataOutputStream.writeUTF(URLEncoder.encode("#imag3*", StandardCharsets.UTF_8));
            dataOutputStream.writeUTF(URLEncoder.encode(fName, StandardCharsets.UTF_8));
            dataOutputStream.writeInt(imageBytes.length);
            dataOutputStream.write(imageBytes);
            dataOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }


}
