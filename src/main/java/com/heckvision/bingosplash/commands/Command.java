package com.heckvision.bingosplash.commands;

public class Command {
    private final String command;
    private final String[] aliases;
    public Command(String command, String[] aliases) {
        this.command = command;
        this.aliases = aliases;
    }
    public String getName() {
        return command;
    }

    public String[] getAliases() {
        return aliases;
    }

    void Run(){
        System.out.println("ran command: " + command);
    }

}
