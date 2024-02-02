package lk.ijse;

import com.google.gson.Gson;
import lk.ijse.dto.TransferData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static ArrayList<Socket> socketArrayList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3013);

        while (true) {
            System.out.println("Listening......");
            Socket socket = serverSocket.accept();
            socketArrayList.add(socket);
            System.out.println("Accepted ");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread thread = new Thread() {
                @Override
                public void run() {
                    String message = "";
                    try {
                        while ((message = bufferedReader.readLine()) != null) {
                            System.out.println(message);
                            TransferData transferData = new Gson().fromJson(message, TransferData.class);
                            manageTransferData(transferData, socket);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
    }

    public synchronized static void manageTransferData(TransferData transferData, Socket socket) {
        if (transferData.getCommand().equals("TEXT_MESSAGE")) {
            if (transferData.getTo().equals("ALL")) {
                forwardMessageToAll(socket, transferData.getMessage());
            }
        }
    }

    public synchronized static void forwardMessageToAll(Socket received, String message) {
        for (Socket socket : socketArrayList) {
            if (socket.getPort() == received.getPort()) {
                continue;
            }
            try {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
