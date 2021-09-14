package com.fs.api.parking.lot.util;

import com.fs.api.parking.lot.model.enums.SlotType;
import org.springframework.stereotype.Component;

import static com.fs.api.parking.lot.model.enums.SlotType.LARGE;
import static com.fs.api.parking.lot.model.enums.SlotType.MEDIUM;

@Component
public class SlotSizeHelper {

    public SlotType defineTypeByHeightAndWeight(int height, int weight) {
        if (height > 2 || weight > 2) {
            return LARGE;
        }

        return MEDIUM;
    }
}
