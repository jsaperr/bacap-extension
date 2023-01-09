package me.char321.bacapextension;

import com.redlimerl.speedrunigt.timer.category.RunCategory;
import net.fabricmc.api.ClientModInitializer;

public class BACAPExtension implements ClientModInitializer {
    public static final RunCategory _100_ADV_CATEGORY = new RunCategory("100_ADVANCEMENTS", "bacap#100_Advancements", "100 Advancements");
    public static final RunCategory _250_ADV_CATEGORY = new RunCategory("250_ADVANCEMENTS", "bacap#250_Advancements", "250 Advancements");
    public static final RunCategory _JUSTICE_CATEGORY = new RunCategory("JUSTICE", "bacap#Justice", "Justice");
    public static final RunCategory _RMT_CATEGORY = new RunCategory("RMT", "bacap#Riddle_Me_This", "Riddle Me This");

    @Override
    public void onInitializeClient() {

    }
}
