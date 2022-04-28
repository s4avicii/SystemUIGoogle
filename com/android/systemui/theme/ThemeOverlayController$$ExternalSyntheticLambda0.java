package com.android.systemui.theme;

import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import java.util.Map;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ThemeOverlayController$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ Map f$0;

    public /* synthetic */ ThemeOverlayController$$ExternalSyntheticLambda0(ArrayMap arrayMap) {
        this.f$0 = arrayMap;
    }

    public final Object apply(Object obj) {
        Map map = this.f$0;
        String str = (String) obj;
        StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m(str, " -> ");
        m.append(map.get(str));
        return m.toString();
    }
}
