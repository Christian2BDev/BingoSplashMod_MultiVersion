package com.heckvision.bingosplash.utils;


import gg.essential.universal.UMinecraft;


//#if MC==10809
import net.minecraft.util.ChatComponentText;
//#endif

//#if MC==12105
//$$ import net.minecraft.text.Text;
//#endif

public class TitleAPI {
    public static void ShowTitle(String title, String SubTitle, int fadeIn, int stay, int fadeOut)  {
        //#if MC==10809
        UMinecraft.getMinecraft().ingameGUI.displayTitle(null, SubTitle, fadeIn * 20, stay * 20, fadeOut * 20);
        UMinecraft.getMinecraft().ingameGUI.displayTitle(title, null, -1, -1, -1);
        //#endif

        //#if MC==12105
        //$$ UMinecraft.getMinecraft().inGameHud.setTitleTicks(fadeIn*20,stay*20,fadeOut*20);
        //$$ UMinecraft.getMinecraft().inGameHud.setTitle(Text.literal(title));
        //$$ UMinecraft.getMinecraft().inGameHud.setSubtitle(Text.literal(SubTitle));
        //#endif
    }
}
