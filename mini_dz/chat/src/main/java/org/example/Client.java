package org.example;

import java.io.*;
import java.net.Socket;

public class Client {
    public Socket socket;
    public String name;


    public Client(Socket socket, String name) {
        try {
            this.socket = socket;
            this.name = name;
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.writeBytes(name + " joined chat");
            System.out.println("Joined");
        } catch (Exception ex) {

        }
    }

    public void sendMessage(String text) {
        DataOutputStream output = null;
        try {
            output = new DataOutputStream(socket.getOutputStream());
            output.writeBytes(name + " joined chat");
            System.out.println("Message sent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
