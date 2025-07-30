package com.heckvision.bingosplash.commands;
import com.heckvision.bingosplash.core.ModStateHandler;

public class TestDataCommand extends Command {
    public TestDataCommand() {
        super("testdata", new String[]{});
    }

    @Override
    void Run(){
        ModStateHandler.debug();
    }
}
