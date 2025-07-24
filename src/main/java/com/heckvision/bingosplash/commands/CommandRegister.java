package com.heckvision.bingosplash.commands;

//#if MC==10809
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
//#endif

//#if MC==12105
//$$ import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
//$$ import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
//$$ import net.minecraft.client.MinecraftClient;
//$$ import net.minecraft.text.Text;
//#endif


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandRegister {
    private final Map<String, Command> commands = new HashMap<>();
    public CommandRegister() {
       commands.put("Bingo",new BingoSplashCommand());

       for (Command c : commands.values()) {
           RegisterCommand(c);
       }
   }

    public void RegisterCommand(Command c){
        System.out.println("Registering command: " + c.getName() + " Aliases: " + Arrays.toString(c.getAliases()));

        //#if MC==10809
        ClientCommandHandler.instance.registerCommand(new CommandParserForge(c.getName(),c.getAliases(), c::Run));
        //#endif
        //#if MC==12105
        //$$ ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            //$$ var root = ClientCommandManager.literal(c.getName()).executes(ctx -> { c.Run(); return 0; });
            //$$ dispatcher.register(root);
            //$$ for (String alias : c.getAliases()) {
                //$$ dispatcher.register(ClientCommandManager.literal(alias).executes(ctx -> { c.Run();return 0; }));
                //$$ }
            //$$ });
        //#endif
    }
}
