package me.nutella.sqlpass;

import me.nutella.sqlpass.managers.SqlPass;
import me.nutella.sqlpass.utils.Utils;

public class Core {

    public static void main(String[] args) {
        Utils.clearConsole();
        if(args.length < 1) {
            Utils.sendError("You must specify a port.");
        } else {
            try {
                int port = Integer.parseInt(args[0]);
                new SqlPass(port);
            } catch (Exception e) {
                Utils.sendError("That is an invalid port.", e.getStackTrace(), e.getCause().getMessage());
            }
        }

    }

}
