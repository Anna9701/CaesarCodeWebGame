package com.annawyrwal.caesarcodegame.serverside;

import java.util.LinkedList;

public class RequestHandler extends Thread {
    private Listener.Client client;
    private int option;
    private LinkedList<String> messages;
    private Ranking ranking;

    RequestHandler(Listener.Client client, LinkedList messages, Ranking ranking) {
        this.messages = messages;
        this.client = client;
        this.ranking = ranking;
    }

    public void run () {
        while (true) {
            if (messages.size() == 0)
                try {
                    Thread.sleep(0);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            try {
                option = Integer.parseInt(messages.remove().toString());
                menuOptionHandler(option);
            } catch (NumberFormatException ex) {
                System.err.println("Wrong input");
                client.sendMessage("Wrong input, please try again.");
            }

        }

    }

    private void menuOptionHandler (int option) {
        switch (option) {
            case 1:
                recieveResponse();
                break;
            case 2:
                showRanking();
                break;
            case 0:
                disconnectClient();
                break;
        }

        client.sendMessage('\n' + Listener.getStartMenuMessage());
    }

    private void recieveResponse() {

    }

    private void showRanking() {
        client.sendMessage(ranking.getRanking());
    }

    private String askForResponse (String question) {
        client.sendMessage(question);
        while (messages.size() == 0) {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return messages.remove();
    }

    private void disconnectClient() {
        client.disconnect();
    }
}
