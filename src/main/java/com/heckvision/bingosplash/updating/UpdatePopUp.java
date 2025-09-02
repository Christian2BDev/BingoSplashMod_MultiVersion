package com.heckvision.bingosplash.updating;

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
import gg.essential.universal.UScreen;
import kotlin.Unit;

import java.awt.*;


public class UpdatePopUp extends WindowScreen {

    UpdatePopUp(String Oldversion, String Newversion) {
        super(ElementaVersion.V6);

        UIBlock overlay = new UIBlock(new Color(0,0,0,150));
        overlay.getConstraints().setWidth(new RelativeConstraint(1f));
        overlay.getConstraints().setHeight(new RelativeConstraint(1f));
        overlay.setChildOf(this.getWindow());

        UIBlock block = new UIBlock(new Color(48, 48, 48, 255));
        block.getConstraints().setX(new CenterConstraint());
        block.getConstraints().setY(new CenterConstraint());
        block.getConstraints().setWidth(new RelativeConstraint(0.5f));
        block.getConstraints().setHeight(new RelativeConstraint(0.5f));
        block.enableEffect(new ScissorEffect());
        block.setChildOf(overlay);

        UIBlock confirm = new UIBlock(new Color(46, 59, 92, 255));
        confirm.getConstraints().setX(new RelativeConstraint(0.7f));
        confirm.getConstraints().setY(new RelativeConstraint(0.7f));
        confirm.getConstraints().setWidth(new RelativeConstraint(0.2f));
        confirm.getConstraints().setHeight(new RelativeConstraint(0.2f));
        confirm.enableEffect(new ScissorEffect());
        confirm.setChildOf(block);

        confirm.onMouseClick((c,e) -> {
            System.out.println("confirmed");
            return Unit.INSTANCE;

        });
        UIText confirmText = new UIText("Confirm",true);
        confirmText.getConstraints().setX(new CenterConstraint());
        confirmText.getConstraints().setY(new CenterConstraint());
        confirmText.setChildOf(confirm);

        UIBlock cancel = new UIBlock(new Color(189, 100, 100, 255));
        cancel.getConstraints().setX(new RelativeConstraint(0.1f));
        cancel.getConstraints().setY(new RelativeConstraint(0.7f));
        cancel.getConstraints().setWidth(new RelativeConstraint(0.2f));
        cancel.getConstraints().setHeight(new RelativeConstraint(0.2f));
        cancel.enableEffect(new ScissorEffect());
        cancel.setChildOf(block);

        cancel.onMouseClick((c,e) -> {
            System.out.println("canceled");
            UScreen.displayScreen(null);

            return Unit.INSTANCE;

        });
        UIText cancelText = new UIText("Cancel",true);
        cancelText.getConstraints().setX(new CenterConstraint());
        cancelText.getConstraints().setY(new CenterConstraint());
        cancelText.setChildOf(cancel);

        UIText Title = new UIText("New Version Available",true);
        Title.getConstraints().setX(new CenterConstraint());
        Title.getConstraints().setY(new RelativeConstraint(0.1f));
        Title.setChildOf(block);

        UIText subTitle = new UIText(Oldversion + " -> " + Newversion,true);
        subTitle.getConstraints().setX(new CenterConstraint());
        subTitle.getConstraints().setY(new RelativeConstraint(0.3f));
        subTitle.getConstraints().setTextScale( new PixelConstraint(1.5f));
        subTitle.setChildOf(block);

    }



}