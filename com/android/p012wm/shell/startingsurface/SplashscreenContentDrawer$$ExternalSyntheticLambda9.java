package com.android.p012wm.shell.startingsurface;

import android.content.res.TypedArray;
import java.util.function.UnaryOperator;

/* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda9 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplashscreenContentDrawer$$ExternalSyntheticLambda9 implements UnaryOperator {
    public final /* synthetic */ TypedArray f$0;

    public /* synthetic */ SplashscreenContentDrawer$$ExternalSyntheticLambda9(TypedArray typedArray) {
        this.f$0 = typedArray;
    }

    public final Object apply(Object obj) {
        return Integer.valueOf(this.f$0.getColor(60, ((Integer) obj).intValue()));
    }
}
