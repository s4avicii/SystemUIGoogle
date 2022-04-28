package com.google.android.systemui.elmyra.sensors.config;

import android.content.Context;
import java.util.function.Consumer;

public abstract class Adjustment {
    public Consumer<Adjustment> mCallback;

    public abstract float adjustSensitivity(float f);

    public Adjustment(Context context) {
    }
}
