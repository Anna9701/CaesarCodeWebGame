package com.annawyrwal.caesarcodegame.serverside;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Ranking {
    Map<Listener.Client, WrapInt> clients;

    public Ranking() {
        clients = new HashMap<>();
    }

    public String getWinner() {
        int index = 0;

        ArrayList<WrapInt> wrapInts = new ArrayList<>();
        for (WrapInt i : clients.values())
            wrapInts.add(i);

        WrapInt max = wrapInts.get(0);

        for (int i = 1; i < wrapInts.size(); i++) {
            if(wrapInts.get(i).value > max.value) {
                index = i;
                max = wrapInts.get(i);
            }
        }

        ArrayList<Listener.Client> values = new ArrayList<>();
        for (Listener.Client client : clients.keySet())
            values.add(client);

        return "Winner is " + values.get(index).getClientName() + " with " + max.value + "pkt. Congratulation!";
    }

    public void addClientToRanking(Listener.Client client) {
        clients.put(client, new WrapInt());
    }

    public void addPointToCliet (Listener.Client client, int points) {
        WrapInt p = clients.get(client);
        p.value += points;
    }

    public String getRanking() {
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();

        for (WrapInt val : clients.values())
            values.add(val.value);
        for (Listener.Client client : clients.keySet())
            keys.add(client.getClientName());

        String resultString = "";
        for (int i = 0; i < keys.size(); i++)
            resultString += keys.get(i) + " : " + values.get(i) + "pkt.\n";

        return resultString;
    }

    public void resetRanking() {
        for (WrapInt val : clients.values())
            val.value = 0;
    }
}

class WrapInt{
    int value;
}
