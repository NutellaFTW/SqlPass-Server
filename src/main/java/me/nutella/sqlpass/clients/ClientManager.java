package me.nutella.sqlpass.clients;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {

    public static ClientManager instance;

    public List<Client> clients;

    public ClientManager() {
        instance = this;
        clients = new ArrayList<>();
    }

}
