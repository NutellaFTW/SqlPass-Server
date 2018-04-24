package me.nutella.sqlpass.managers;

import me.nutella.sqlpass.clients.Client;
import me.nutella.sqlpass.clients.ClientManager;
import me.nutella.sqlpass.command.InputManager;
import me.nutella.sqlpass.utils.TxtReader;
import me.nutella.sqlpass.utils.Utils;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class SqlPass {

    public static SqlPass instance;

    public int port;
    public File allowedAddresses;
    public File mysqlProperties;

    public SqlPass(int port) {
        instance = this;
        new ClientManager();
        new InputManager();
        this.port = port;
        allowedAddresses = new File("allowedAddresses.txt");
        mysqlProperties = new File("mysql.properties");
            try {
                if(!allowedAddresses.exists())
                    Utils.sendInfo("Addresses File Created: " + allowedAddresses.createNewFile());
                if(!mysqlProperties.exists())
                    copyFile();
            } catch (IOException e) {
                Utils.sendError("Could not Create Files", e.getStackTrace(), e.getCause().getMessage());
            }
        new SqlManager();
        new Thread(this::listenForConnections).start();
    }

    private void listenForConnections() {

        List<String> ips = new TxtReader(allowedAddresses).lines;

        ServerSocket serverSocket;
        Socket socket;

        try {

            serverSocket = new ServerSocket(port);

            Utils.sendInfo("BOUND SERVER TO PORT " + port + " SUCCESSFULLY");

            while (true) {
                boolean added = false;
                socket = serverSocket.accept();
                for(String ip : ips) {
                    if(socket.getRemoteSocketAddress().toString().equals(ip)) {
                        Client client = new Client(socket);
                        ClientManager.instance.clients.add(client);
                        client.start();
                        added = true;
                    }
                }
                if(!added)
                    socket.close();
            }

        } catch (IOException e) {
            Utils.sendError("Could not bind server socket.", e.getStackTrace(), e.getCause().getMessage());
        }

    }

    private void copyFile() {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        boolean created;

        try {

            inputStream = Toolkit.getDefaultToolkit().getClass().getResource("/resources/mysql.properties").openStream();
            outputStream = new FileOutputStream(mysqlProperties);

            byte[] bufferArray = new byte[1024];

            int bufferLength;

            while ((bufferLength = inputStream.read(bufferArray)) > 0) {
                outputStream.write(bufferArray, 0, bufferLength);
            }

            outputStream.flush();

            created = true;

        } catch (IOException e) {
            created = false;
            Utils.sendError("Could not copy mysql.properties.", e.getStackTrace(), e.getCause().getMessage());
        } finally {
            try {
                if(inputStream != null)
                    inputStream.close();
                if(outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                Utils.sendError("Could not close inputStreams.", e.getStackTrace(), e.getCause().getMessage());
            }
        }

        Utils.sendInfo("Mysql Properties File Copied: " + created);

    }

}
