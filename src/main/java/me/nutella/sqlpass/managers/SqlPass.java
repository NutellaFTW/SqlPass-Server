package me.nutella.sqlpass.managers;

import me.nutella.sqlpass.clients.Client;
import me.nutella.sqlpass.clients.ClientManager;
import me.nutella.sqlpass.command.InputManager;
import me.nutella.sqlpass.utils.TxtReader;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class SqlPass {

    public static SqlPass instance;

    public int port;
    public File allowedAddresses;

    public SqlPass(int port) {
        instance = this;
        new ClientManager();
        new InputManager();
        this.port = port;
        allowedAddresses = new File("allowedAddresses.txt");
        if(!allowedAddresses.exists())
            try {
                System.out.println("Addresses File Created: " + allowedAddresses.createNewFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        new Thread(this::listenForConnections).start();
    }

    private void listenForConnections() {

        List<String> ips = new TxtReader(allowedAddresses).lines;

        ServerSocket serverSocket;
        Socket socket;

        try {

            serverSocket = new ServerSocket(port);

            System.out.println("BOUND SERVER TO PORT " + port + " SUCCESSFULLY");

            while (true) {
                boolean added = false;
                socket = serverSocket.accept();
                for(String ip : ips) {
                    if(socket.getRemoteSocketAddress().toString().equals(ip)) {
                        ClientManager.instance.clients.add(new Client(socket));
                        added = true;
                    }
                }
                if(!added)
                    socket.close();
            }

        } catch (IOException e) {
            System.out.println("Could not bind server socket.");
        }

    }

}
