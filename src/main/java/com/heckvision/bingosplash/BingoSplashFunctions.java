package com.heckvision.bingosplash;

import com.heckvision.bingosplash.commands.CommandRegister;
import com.heckvision.bingosplash.core.ExecuteTasks;
import com.heckvision.bingosplash.core.ModStateHandler;

import static com.heckvision.bingosplash.BingoSplash.NAME;

public class BingoSplashFunctions {


    /**
     * Runs on Initialization of the minecraft client
     */
    public void OnInit(){
        System.out.println("["+ NAME +"] Mod initialized");
        new ExecuteTasks();
        new CommandRegister();
        BingoSplash.stateHandler = new ModStateHandler();
    }

    /**
     * Runs every tick of the minecraft client
     * Register custom void using: TickAPI.registerClientTickListener
     */
    public void OnTick(){
        //Tick code here
    }

    /**
     * Runs on Shutdown of the minecraft client
     * Register custom void using: ShutdownAPI.registerClientShutdownListener
     */
    public void OnShutdown(){
        //Shutdown code here
    }

    /**
     * Runs once the minecraft world is loaded
     */
    public void OnWorldLoaded(){
        //World loaded code here
        System.out.println("test");
        BingoSplash.stateHandler.check();
    }
}
