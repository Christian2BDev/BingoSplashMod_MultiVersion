package com.heckvision.bingosplash;

import com.heckvision.bingosplash.commands.CommandRegister;
import com.heckvision.bingosplash.core.ProcessMessages;
import com.heckvision.bingosplash.events.OnWorldChangeEvent;

import static com.heckvision.bingosplash.BingoSplash.NAME;

public class BingoSplashFunctions {


    /**
     * Runs on Initialization of the minecraft client
     */
    public void OnInit(){
        System.out.println("["+ NAME +"] Mod initialized");
        new ProcessMessages();
        new CommandRegister();
    }

    /**
     * Runs every tick of the minecraft client
     * Register custom void using: TickAPI.registerClientTickListener
     */
    public void OnTick(){
        //Tick code here
        OnWorldChangeEvent.WorldChangeCheck();
    }

    /**
     * Runs on Shutdown of the minecraft client
     * Register custom void using: ShutdownAPI.registerClientShutdownListener
     */
    public void OnShutdown(){
        //Shutdown code here
    }

    /**
     * Runs once the minecraft world changed
     * Register custom void using: WorldChangeAPI.registerWorldChangeListener
     */
    public void OnWorldChanged(){
        //World loaded code here
    }
}
