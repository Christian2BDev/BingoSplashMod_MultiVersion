package com.heckvision.bingosplash.commands;
import com.heckvision.bingosplash.core.ModStateHandler;
import com.heckvision.bingosplash.utils.TitleAPI;

public class TestDataCommand extends Command {
    public TestDataCommand() {
        super("testdata", new String[]{});
    }

    @Override
    void Run(){
        ModStateHandler.debug();
        TitleAPI.ShowTitle("§6type","§fMessage",5,5,5);
    }
}
