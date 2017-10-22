package com.annawyrwal.caesarcodegame.serverside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Listener extends Thread {
    private int portNumber;
    private ServerSocket serverSocket;
    private ArrayList<Client> clients;
    private Ranking ranking;

    public Listener(int number) {
        clients = new ArrayList<>();
        ranking = new Ranking();
        portNumber = number;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStartMenuMessage() {
        String text = "Hello on our Caesar Code Game!\n" +
                "1. Send answer \n" +
                "2. View ranking \n" +
                "0. Exit";
        return text;
    }

    public void run() {
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress().getHostName());
                Client client = new Client(socket);
                client.sendMessage(getStartMenuMessage());
                clients.add(client);
                ranking.addClientToRanking(client);
                client.start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void sendToAllClients (String msg) {
        for (Client client : clients) {
            client.sendMessage(msg);
        }
    }

     class Client extends  Thread {
        private Socket client;
        private PrintWriter out;
        private BufferedReader in;
        private LinkedList<String> messages;

        public String getClientName() {
            return client.getInetAddress().getHostName();
        }

        public Client(Socket socket) throws IOException {
            client = socket;
            messages = new LinkedList<>();
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        }

        public void disconnect () {
            try {
                client.close();
                clients.remove(client);
            } catch (IOException ex) {
                System.err.println("Exception during disconnecting : " + ex.getMessage());
                return;
            }
        }

        public void run() {
            try {
                Client currentClient = this;
                Thread handle = new Thread () {
                    public void run() {
                        new RequestHandler(currentClient, messages, ranking).start();
                    }
                };
                handle.start();
                while (true) {
                    String msg = in.readLine();
                    messages.add(msg);
                }
            } catch (IOException e) {
                System.err.println("Client " + client.getInetAddress().getHostName() + " disconnected");
                try {
                    client.close();
                    clients.remove(client);
                } catch (IOException e1) {
                    System.err.println("Closing connection error " + e1.getMessage());
                    return;
                }
                return;
            }
        }

        public void sendMessage(String msg) {
            out.println(msg);
        }
    }
}

