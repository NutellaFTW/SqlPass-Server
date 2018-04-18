package me.nutella.sqlpass.clients;

import me.nutella.sqlpass.managers.SqlPass;

import java.net.Socket;

public class Client extends Thread {

    public Socket client;

    public int clientID;

    public Client(Socket client) {
        this.client = client;
        clientID = ClientManager.instance.clients.indexOf(this) + 1;
        System.out.println("CLIENT #" + clientID + " (" + client.getInetAddress().getHostAddress() + "): HAS CONNECTED TO THE SERVER ON PORT " + SqlPass.instance.port);
        this.start();
    }

    @Override
    public void run() {

    }

}
