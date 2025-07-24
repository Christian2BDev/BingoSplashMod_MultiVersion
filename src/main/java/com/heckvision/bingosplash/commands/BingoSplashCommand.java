package com.heckvision.bingosplash.commands;

import com.heckvision.bingosplash.gui.BingoOpenConfig;

import java.lang.reflect.Array;
import java.util.Arrays;

public class BingoSplashCommand extends Command {
    public BingoSplashCommand() {
        super("bs", new String[]{"bsm, bingosplash"});
    }

    @Override
    void Run(){
        BingoOpenConfig.openConfig();
    }
}
