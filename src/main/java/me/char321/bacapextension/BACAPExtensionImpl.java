package me.char321.bacapextension;

import com.redlimerl.speedrunigt.api.SpeedRunIGTApi;
import com.redlimerl.speedrunigt.timer.category.RunCategory;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.Collection;

public class BACAPExtensionImpl implements SpeedRunIGTApi {

    @Override
    public Collection<RunCategory> registerCategories() {
        return Arrays.asList(BACAPExtension._100_ADV_CATEGORY, BACAPExtension._250_ADV_CATEGORY, BACAPExtension._JUSTICE_CATEGORY, BACAPExtension._RMT_CATEGORY);
        }
}
