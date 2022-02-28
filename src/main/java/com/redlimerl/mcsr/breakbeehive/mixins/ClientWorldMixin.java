package com.redlimerl.mcsr.breakbeehive.mixins;

import com.redlimerl.mcsr.breakbeehive.BreakBeehive;
import com.redlimerl.speedrunigt.timer.InGameTimer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Inject(method = "updateListeners", at = @At("TAIL"))
    public void onBlockUpdate(BlockPos pos, BlockState oldState, BlockState newState, int flags, CallbackInfo ci) {
        InGameTimer timer = InGameTimer.getInstance();

        if (timer.getCategory() == BreakBeehive.BREAK_BEEHIVE_CATEGORY && timer.isPlaying()) {
            if (flags == 11 && newState.getBlock() == Blocks.AIR && oldState.getBlock() == Blocks.BEE_NEST) {
                InGameTimer.complete();
            }
        }
    }
}
