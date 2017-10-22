package com.annawyrwal.caesarcodegame.serverside;

import com.annawyrwal.caesarcodegame.caesarcode.Enigma;

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
    private static GameEngine gameEngine;
    private int numberOfRounds = 3; // ewentualnie przekazywanie przez parametr wejściowy??

    public Listener(int number) {
        clients = new ArrayList<>();
        gameEngine = new GameEngine(numberOfRounds);
        portNumber = number;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStartMenuMessage() {
        Enigma enigma = gameEngine.getEnigma();

        String text = "Now is " + gameEngine.getCurrentRound() + " round from " + gameEngine.getNumberOfRounds() + " rounds.\n" +
                "Please, remember, that text has no spaces \n" +
                "Text to decrypt (Text is in polish language): \n" +
                enigma.getCryptedText() + "\n" +
                enigma.getDecryptedText() + enigma.getKey() + "\n\n" + /*************************************** Do wywalenia **********************************************************/
                "1. Send answer \n" +
                "2. View ranking \n" +
                "0. Exit \n";
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
                gameEngine.addUserToRanking(client);
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

        public boolean checkEnigma(String text) {
            if (gameEngine.getEnigma().isValid(text)) {
                sendToAllClients(getClientName() + " found valid answer!");
                sendToAllClients("Decrypted text was: " + gameEngine.getEnigma().getDecryptedText());
                gameEngine.addPointsForCorrectAnswer(this);
                if(!gameEngine.nextRound()) {
                    endOfGame();
                }
                sendToAllClients(getStartMenuMessage());
                return true;
            }
            return false;
        }

        public void run() {
            try {
                Client currentClient = this;
                Thread handle = new Thread () {
                    public void run() {
                        new RequestHandler(currentClient, messages, gameEngine.getRanking()).start();
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

        private void endOfGame() {
            sendToAllClients(gameEngine.getRanking().getWinner());
            gameEngine.resetGame();
            gameEngine.getRanking().resetRanking();
        }

        public void sendMessage(String msg) {
            out.println(msg);
        }
    }
}

