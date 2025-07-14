package com.heckvision.bingosplash.mixin;

import com.heckvision.bingosplash.utils.TickAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.Minecraft.class)
public class MixinClientTick {
    //#if MC==10809
    @Inject(method = "runTick", at = @At("HEAD"))
    //#endif

    //#if MC>=11605
    //$$ @Inject(method = "tick", at = @At("HEAD"))
    //#endif


    private void onTick(
            //#if FORGE
            CallbackInfo ci
            //#endif
            //#if FABRIC
            //$$ CallbackInfoReturnable<Boolean> cir
            //#endif

    ) {
        TickAPI.onClientTick();
    }
}