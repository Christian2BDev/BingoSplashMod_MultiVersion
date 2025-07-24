package com.heckvision.bingosplash.commands;

//#if MC==10809
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

public class CommandParserForge extends CommandBase {

    public String name;
    public Runnable runnable;
    public List<String> aliases;

    public CommandParserForge(String name, String[] aliases , Runnable runnable) {
        this.name = name;
        this.runnable = runnable;
        this.aliases = Arrays.asList(aliases);
    }

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        runnable.run();
    }
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
//#endif
