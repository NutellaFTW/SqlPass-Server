package me.nutella.sqlpass;

import me.nutella.sqlpass.managers.SqlPass;
import me.nutella.sqlpass.utils.Utils;

public class Core {

    public static void main(String[] args) {
        Utils.clearConsole();
        if(args.length < 1) {
            System.out.println("You must specify a port.");
        } else {
            try {
                int port = Integer.parseInt(args[0]);
                new SqlPass(port);
            } catch (Exception ignored) {
                System.out.println("That is an invalid port.");
            }
        }

    }

}
