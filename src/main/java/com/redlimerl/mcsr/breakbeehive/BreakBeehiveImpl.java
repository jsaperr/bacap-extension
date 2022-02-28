package com.redlimerl.mcsr.breakbeehive;

import com.redlimerl.speedrunigt.api.SpeedRunIGTApi;
import com.redlimerl.speedrunigt.timer.running.RunCategory;

public class BreakBeehiveImpl implements SpeedRunIGTApi {
    @Override
    public RunCategory registerCategory() {
        return BreakBeehive.BREAK_BEEHIVE_CATEGORY;
    }
}
