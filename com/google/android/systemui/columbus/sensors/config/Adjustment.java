package com.google.android.systemui.columbus.sensors.config;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: Adjustment.kt */
public abstract class Adjustment {
    public Function1<? super Adjustment, Unit> callback;

    public abstract float adjustSensitivity(float f);
}
