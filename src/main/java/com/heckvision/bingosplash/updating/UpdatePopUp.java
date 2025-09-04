package com.heckvision.bingosplash.updating;

import com.heckvision.bingosplash.utils.MainTreadAPI;
import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.UIConstraints;
import gg.essential.elementa.WindowScreen;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.constraints.*;

import gg.essential.elementa.effects.ScissorEffect;
import gg.essential.elementa.events.UIClickEvent;
import gg.essential.elementa.font.FontProvider;
import gg.essential.elementa.state.State;
import gg.essential.universal.UScreen;
import kotlin.Unit;

import java.awt.*;
import java.util.Objects;


public class UpdatePopUp extends WindowScreen {

    UpdatePopUp(String Oldversion, String Newversion, boolean upToDate) {
        super(ElementaVersion.V6);

        UIBlock overlay = new UIBlock(new Color(0,0,0,150));
        overlay.getConstraints().setWidth(new RelativeConstraint(1f));
        overlay.getConstraints().setHeight(new RelativeConstraint(1f));
        overlay.setChildOf(this.getWindow());

        UIBlock block = new UIBlock(new Color(48, 48, 48, 255));
        block.getConstraints().setX(new CenterConstraint());
        block.getConstraints().setY(new CenterConstraint());
        block.getConstraints().setWidth(new PixelConstraint(378f));
        block.getConstraints().setHeight(new PixelConstraint(236f));
        block.enableEffect(new ScissorEffect());
        block.setChildOf(overlay);


        if (!upToDate) {
            //cancel Btn
            newButton(new Color(189, 100, 100, 255),"Cancel",new RelativeConstraint(0.1f), new RelativeConstraint(0.7f),new RelativeConstraint(0.2f),new RelativeConstraint(0.2f), this::closeGUI).setChildOf(block);
            //install Btn
            newButton(new Color(132, 175, 117, 255),"Install",new RelativeConstraint(0.7f), new RelativeConstraint(0.7f),new RelativeConstraint(0.2f),new RelativeConstraint(0.2f),()-> UpdateManager.getInstance().downloadUpdate()).setChildOf(block);
        }else{
            newButton(new Color(132, 175, 117, 255),"Confirm",new RelativeConstraint(0.4f), new RelativeConstraint(0.7f),new RelativeConstraint(0.2f),new RelativeConstraint(0.2f), this::closeGUI).setChildOf(block);
        }


        String TitleText = upToDate ? "You are up to date!" : "New Version Available!";
        String SubTitleText = upToDate ?  ("Current version: " + Oldversion) : (Oldversion + " -> " + Newversion) ;

        UIText Title = new UIText(TitleText,true);
        Title.getConstraints().setX(new CenterConstraint());
        Title.getConstraints().setY(new RelativeConstraint(0.1f));
        Title.getConstraints().setTextScale(new PixelConstraint(2f));
        Title.setChildOf(block);

        UIText subTitle = new UIText(SubTitleText,true);
        subTitle.getConstraints().setX(new CenterConstraint());
        subTitle.getConstraints().setY(new RelativeConstraint(0.3f));
        subTitle.getConstraints().setTextScale(new PixelConstraint(1.3f));
        subTitle.setChildOf(block);

    }


    public UIBlock newButton(Color color , String Text, XConstraint xConstraint, YConstraint yConstraint, WidthConstraint widthConstraint, HeightConstraint heightConstraint, Runnable r) {
        //background
        UIBlock button = new UIBlock(color);
        button.getConstraints().setX(xConstraint);
        button.getConstraints().setY(yConstraint);
        button.getConstraints().setWidth(widthConstraint);
        button.getConstraints().setHeight(heightConstraint);

        //text
        UIText cancelText = new UIText(Text,true);
        cancelText.getConstraints().setX(new CenterConstraint());
        cancelText.getConstraints().setY(new CenterConstraint());
        cancelText.setChildOf(button);

        //action
        button.onMouseClick((c,e) -> {
            r.run();
            return Unit.INSTANCE;

        });

        return button;
    }

    public void closeGUI(){
        MainTreadAPI.runOnMainThread(() -> UScreen.displayScreen(null));

    }



}