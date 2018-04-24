package me.nutella.sqlpass.clients;

import me.nutella.sqlpass.managers.SqlManager;
import me.nutella.sqlpass.managers.SqlPass;
import me.nutella.sqlpass.utils.Utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

    public final Socket client;
    private DataInputStream inputStream;

    public int clientID;

    public Client(Socket client) {
        this.client = client;
        clientID = ClientManager.instance.clients.indexOf(this) + 1;
        System.out.println("CLIENT #" + clientID + " (" + client.getInetAddress().getHostAddress() + "): HAS CONNECTED TO THE SERVER ON PORT " + SqlPass.instance.port);
        try {
            inputStream = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            Utils.sendError("Could not setup input stream... Aborting", e.getStackTrace(), e.getCause().getMessage());
            ClientManager.instance.clients.remove(this);
            this.interrupt();
        }
    }


    @Override
    public void run() {

        while (true) {

            SqlManager.instance.sqlService.submit(() -> {
                try {
                    SqlManager.instance.submitToDatabase(inputStream.readUTF(), this);
                } catch (IOException e) {
                    Utils.sendError("Could not receive incoming data (#" + clientID + ")", e.getStackTrace(), e.getCause().getMessage());
                }
            }, this);

        }

    }

}
