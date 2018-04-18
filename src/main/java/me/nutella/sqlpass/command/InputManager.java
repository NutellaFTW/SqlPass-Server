package me.nutella.sqlpass.command;

import me.nutella.sqlpass.command.commands.Clear;
import me.nutella.sqlpass.command.commands.Clients;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputManager {

    public static InputManager instance;

    public List<Command> commands;

    public InputManager() {
        instance = this;
        commands = new ArrayList<>();
        initCommands();
        new Thread(this::startInputLoop).start();
    }

    public void startInputLoop() {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            String[] structure = scanner.nextLine().split(" ");

            for (Command command : commands) {
                if (structure[0].equalsIgnoreCase(command.name)) {
                    if (!command.executeCommand(structure[0], Arrays.copyOfRange(structure, 1, structure.length)))
                        System.out.println(helpString());
                } else if(command.aliases != null) {
                    for (String alias : command.aliases) {
                        if (structure[0].equalsIgnoreCase(alias)) {
                            if (!command.executeCommand(structure[0], Arrays.copyOfRange(structure, 1, structure.length)))
                                System.out.println(helpString());
                        }
                    }
                }
            }

        }

    }

    private void initCommands() {
        commands.addAll(Arrays.asList(
                new Clear(),
                new Clients()
        ));
    }

    private String helpString() {
        return "\n" + "cls - Clears the Console\n" +
                "clients:\n     list - Lists all Clients Connected\n     kick <id> - Kicks the Specified Client";
    }

}
