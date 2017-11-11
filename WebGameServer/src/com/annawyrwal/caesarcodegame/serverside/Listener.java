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
    private int numberOfRounds;
    private final int DEFAULT_NUMBER_OF_ROUNDS = 8;

    public Listener(int portNumber) {
        numberOfRounds = DEFAULT_NUMBER_OF_ROUNDS;
        initialize(portNumber);
    }

    public Listener(int portNumber, int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
        initialize(portNumber);
    }

    private void initialize(int portNumber) {
        clients = new ArrayList<>();
        gameEngine = new GameEngine(numberOfRounds);
        this.portNumber = portNumber;
        try {
            serverSocket = new ServerSocket(this.portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStartMenuMessage() {
        Enigma enigma = gameEngine.getEnigma();

        return "Now is " + gameEngine.getCurrentRound() + " round from " + gameEngine.getNumberOfRounds() + " rounds.\n" +
                "Please, remember, that text has no spaces \n" +
                "Text to decrypt: \n" +
                enigma.getCryptedText() + "\n" +
                enigma.getDecryptedText() + "\n" + //enigma.getKey() + "\n\n" + /*************************************** Do wywalenia **********************************************************/
                "1. Send answer \n" +
                "2. View ranking \n" +
                "0. Exit \n";
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

    private void sendToAllClientsExcept (String msg, Client except) {
        for (Client client : clients) {
            if (client != except)
                client.sendMessage(msg);
        }
    }

     class Client extends Thread {
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
                sendToAllClientsExcept(getClientName() + " found valid answer!", this);
                sendToAllClientsExcept("Decrypted text was: " + gameEngine.getEnigma().getDecryptedText() + "\n", this);
                gameEngine.addPointsForCorrectAnswer(this);
                if(!gameEngine.nextRound()) {
                    endOfGame();
                }
                sendToAllClientsExcept(getStartMenuMessage(), this);
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

