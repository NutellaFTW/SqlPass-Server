package me.nutella.sqlpass.command.commands;

import me.nutella.sqlpass.clients.Client;
import me.nutella.sqlpass.clients.ClientManager;
import me.nutella.sqlpass.command.Command;

public class Clients extends Command {

    public Clients() {
        super("clients", "Manages Clients Connected to The Server");
    }

    @Override
    public boolean executeCommand(String label, String[] args) {

            if(args.length > 0) {

                switch (args[0].toLowerCase()) {

                    case "list": {

                        StringBuilder builder = new StringBuilder("\nClients (" + ClientManager.instance.clients.size() + ")");

                        for(Client client : ClientManager.instance.clients) {
                            builder.append("- #").append(client.clientID).append(" (").append(client.client.getInetAddress().getHostAddress()).append(")\n");
                        }

                        System.out.println(builder.toString());

                        return true;

                    }

                    case "kick": {

                        return true;
                    }

                    default:
                        return false;

                }

            }

        return false;
    }

}
