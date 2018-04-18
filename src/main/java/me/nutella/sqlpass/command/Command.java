package me.nutella.sqlpass.command;

public abstract class Command {

    public String name;
    public String description;
    public String aliases[];

    public Command(String name, String description, String... aliases) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
    }

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract boolean executeCommand(String label, String[] args);

}
