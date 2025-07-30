package com.heckvision.bingosplash;

//always import:
import com.heckvision.bingosplash.utils.TickAPI;
import com.heckvision.bingosplash.utils.ShutdownAPI;
import com.heckvision.bingosplash.utils.WorldChangeAPI;


//#if MC==10809

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
//#endif
//#if FORGE && MC==11605
//$$ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//$$ import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
//$$ import net.minecraftforge.fml.common.Mod;
//#endif

//#if FABRIC
//$$ import net.fabricmc.api.ModInitializer;
//#endif





//#if MC==10809
@Mod(modid = BingoSplash.MODID, name = BingoSplash.NAME, version = BingoSplash.VERSION, clientSideOnly = true)
//#endif
//#if FORGE && MC==11605
//$$ @Mod(BingoSplash.MODID)
//#endif
public class BingoSplash
//#if FABRIC
//$$ implements ModInitializer
//#endif
{
    public static final String VERSION = "1.0";
    public static final String NAME = "BingoSplash";
    public static final String MODID = "bingosplash";
    //#if MC==10809
    @Mod.Instance(BingoSplash.MODID)
    public static BingoSplash instance;
    //#endif

    //#if MC==10809
    @Mod.EventHandler
    //#endif

    //#if FABRIC
    //$$ @Override
    //#endif
    public void onInitialize(
            //#if MC==10809
             FMLInitializationEvent event
            //#endif 
            //#if FORGE && MC==11605
            //$$ FMLCommonSetupEvent event
            //#endif
    ) {

        BingoSplashFunctions BSF = new BingoSplashFunctions();
        BSF.OnInit();
        TickAPI.registerClientTickListener(BSF::OnTick);
        ShutdownAPI.registerClientShutdownListener(BSF::OnShutdown);
        WorldChangeAPI.registerWorldChangeListener(BSF::OnWorldChanged);
    }



    //#if FORGE && MC==11605
    //$$  public BingoSplash() {
    //$$      FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onInitialize);
    //$$  }
    //#endif


}
