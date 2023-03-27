package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    ServerSocket serverSocket;
    ArrayList<Socket> clients;
    DataOutputStream dataOutputStream;

    public Server() throws IOException {
        serverSocket = new ServerSocket(9000);

    }

    public void joinChat() {

        try {
            Socket client = serverSocket.accept();
            InputStream inputStream = client.getInputStream();
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(inputStream));
            String data = br.readLine();
            System.out.println(data);
            for (var cl :
                    clients) {
                InputStream inputStream1 = null;
                inputStream1 = cl.getInputStream();
                BufferedReader br1 = new BufferedReader(new InputStreamReader(inputStream1));
            }
            clients.add(client);
        } catch (Exception ex) {
        }

        System.out.println("Client added");
    }

}
