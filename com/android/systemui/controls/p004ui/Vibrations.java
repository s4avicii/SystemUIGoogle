package com.android.systemui.controls.p004ui;

import android.os.VibrationEffect;

/* renamed from: com.android.systemui.controls.ui.Vibrations */
/* compiled from: Vibrations.kt */
public final class Vibrations {
    public static final VibrationEffect rangeEdgeEffect;
    public static final VibrationEffect rangeMiddleEffect;

    static {
        VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
        startComposition.addPrimitive(7, 0.5f);
        rangeEdgeEffect = startComposition.compose();
        VibrationEffect.Composition startComposition2 = VibrationEffect.startComposition();
        startComposition2.addPrimitive(7, 0.1f);
        rangeMiddleEffect = startComposition2.compose();
    }
}
