package com.google.android.systemui.columbus.sensors.config;

import android.util.Range;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: GestureConfiguration.kt */
public final class GestureConfiguration$adjustmentCallback$1 extends Lambda implements Function1<Adjustment, Unit> {
    public final /* synthetic */ GestureConfiguration this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public GestureConfiguration$adjustmentCallback$1(GestureConfiguration gestureConfiguration) {
        super(1);
        this.this$0 = gestureConfiguration;
    }

    public final Object invoke(Object obj) {
        Adjustment adjustment = (Adjustment) obj;
        GestureConfiguration gestureConfiguration = this.this$0;
        Range<Float> range = GestureConfiguration.SENSITIVITY_RANGE;
        gestureConfiguration.updateSensitivity();
        return Unit.INSTANCE;
    }
}
