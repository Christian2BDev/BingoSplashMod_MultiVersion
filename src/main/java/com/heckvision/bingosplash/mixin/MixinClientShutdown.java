package com.heckvision.bingosplash.mixin;

import com.heckvision.bingosplash.utils.ShutdownAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.Minecraft.class)
public class MixinClientShutdown {

    //#if MC==10809
    @Inject(method = "shutdown", at = @At("HEAD"))
    //#endif

    //#if MC>=11605
    //$$ @Inject(method = "stop", at = @At("HEAD"))
    //#endif

    private void onTick(CallbackInfo ci) {
        ShutdownAPI.onClientShutdown();
    }
}
