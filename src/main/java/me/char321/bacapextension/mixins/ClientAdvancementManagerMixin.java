package me.char321.bacapextension.mixins;

import com.redlimerl.speedrunigt.timer.InGameTimer;
import com.redlimerl.speedrunigt.timer.TimerAdvancementTracker;
import com.redlimerl.speedrunigt.timer.TimerStatus;
import com.redlimerl.speedrunigt.timer.category.RunCategories;
import me.char321.bacapextension.BACAPExtension;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.network.packet.s2c.play.AdvancementUpdateS2CPacket;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Mixin(ClientAdvancementManager.class)
public abstract class ClientAdvancementManagerMixin {

    @Shadow @Final private MinecraftClient client;
    @Shadow @Final private AdvancementManager manager;

    @Shadow public abstract AdvancementManager getManager();

    @Shadow @Final private Map<Advancement, AdvancementProgress> advancementProgresses;

    //Specific Advancement Categories
    @ModifyVariable(method = "onAdvancements", at = @At(value = "INVOKE", target = "Ljava/util/Map$Entry;getValue()Ljava/lang/Object;"))
    public Map.Entry<Identifier, AdvancementProgress> advancement(Map.Entry<Identifier, AdvancementProgress> entry) {
        InGameTimer timer = InGameTimer.getInstance();

        Advancement advancement = this.manager.get(entry.getKey());
        AdvancementProgress advancementProgress = entry.getValue();
        assert advancement != null;
        advancementProgress.init(advancement.getCriteria(), advancement.getRequirements());

        if (advancementProgress.isDone() && timer.getStatus() != TimerStatus.NONE) {

            //Justice
            if (timer.getCategory() == BACAPExtension._JUSTICE_CATEGORY && Objects.equals(advancement.getId().toString(), new Identifier("blazeandcave:adventure/justice").toString())) {
                InGameTimer.complete();
            }
            //Riddle Me This
            if (timer.getCategory() == BACAPExtension._RMT_CATEGORY && Objects.equals(advancement.getId().toString(), new Identifier("blazeandcave:challenges/riddle_me_this").toString())) {
                InGameTimer.complete();
            }
        }

        return entry;
    }

    //# of Advancement Categories
    @Inject(at={@At(value="RETURN")}, method={"onAdvancements"})
    public void onAdvancementDone(AdvancementUpdateS2CPacket packet, CallbackInfo ci) {
        InGameTimer timer = InGameTimer.getInstance();
        //100 Advancements
        if (timer.getStatus() != TimerStatus.NONE && timer.getCategory() == BACAPExtension._100_ADV_CATEGORY && this.getCompleteAdvancementsCount() >= 100) {
            InGameTimer.complete();
        }
        //250 Advancements
        if (timer.getStatus() != TimerStatus.NONE && timer.getCategory() == BACAPExtension._250_ADV_CATEGORY && this.getCompleteAdvancementsCount() >= 250) {
            InGameTimer.complete();
        }
    }

    private int getCompleteAdvancementsCount() {
        Set<String> completedAdvancements = new HashSet<>();
        for (Map.Entry<String, TimerAdvancementTracker.AdvancementTrack> track : InGameTimer.getInstance().getAdvancementsTracker().getAdvancements().entrySet()) {
            if (track.getValue().isAdvancement() && track.getValue().isComplete()) completedAdvancements.add(track.getKey());
        }
        for (Advancement advancement : this.getManager().getAdvancements()) {
            if (this.advancementProgresses.containsKey(advancement) && advancement.getDisplay() != null) {
                AdvancementProgress advancementProgress = this.advancementProgresses.get(advancement);

                advancementProgress.init(advancement.getCriteria(), advancement.getRequirements());
                String advancementID = advancement.getId().toString();
                if (advancementProgress.isDone() && completedAdvancements.contains(advancementID)) {
                    completedAdvancements.add(advancementID);
                    InGameTimer.getInstance().tryInsertNewAdvancement(advancementID, null, true);
                }
            }
        }
        return completedAdvancements.size();
    }
}
