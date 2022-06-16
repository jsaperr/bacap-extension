package com.redlimerl.mcsr.breakbeehive;

import com.redlimerl.speedrunigt.api.SpeedRunIGTApi;
import com.redlimerl.speedrunigt.timer.category.RunCategory;

public class BreakBeehiveImpl implements SpeedRunIGTApi {
    @Override
    public RunCategory registerCategory() {
        return BreakBeehive.BREAK_BEEHIVE_CATEGORY;
    }
}
