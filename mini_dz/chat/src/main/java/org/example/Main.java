package org.example;

import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            Server server = new Server();
            Client client = new Client(new Socket("localhost", 9000), "Petr");
            Client client1 = new Client(new Socket("localhost", 9000), "Alexandr");
        } catch (Exception ex) {

        }
    }
}