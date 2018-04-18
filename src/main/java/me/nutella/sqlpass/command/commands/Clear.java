package me.nutella.sqlpass.command.commands;

import me.nutella.sqlpass.command.Command;
import me.nutella.sqlpass.utils.Utils;

public class Clear extends Command {

    public Clear() {
        super("cls", "Clear the Console", "clear");
    }

    @Override
    public boolean executeCommand(String label, String[] args) {
        Utils.clearConsole();
        return true;
    }

}
