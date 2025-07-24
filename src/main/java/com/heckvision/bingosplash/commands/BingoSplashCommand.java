package com.heckvision.bingosplash.commands;

import com.heckvision.bingosplash.gui.BingoOpenConfig;


public class BingoSplashCommand extends Command {
    public BingoSplashCommand() {
        super("bs", new String[]{"bsm", "bingosplash"});
    }

    @Override
    void Run(){
        BingoOpenConfig.openConfig();
    }
}
