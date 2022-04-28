package com.android.systemui.screenshot;

import android.graphics.Rect;
import com.android.systemui.screenshot.LongScreenshotActivity;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LongScreenshotActivity$1$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ LongScreenshotActivity.C10711 f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ LongScreenshotActivity$1$$ExternalSyntheticLambda1(LongScreenshotActivity.C10711 r1, float f, float f2) {
        this.f$0 = r1;
        this.f$1 = f;
        this.f$2 = f2;
    }

    public final void run() {
        LongScreenshotActivity.C10711 r0 = this.f$0;
        float f = this.f$1;
        float f2 = this.f$2;
        Objects.requireNonNull(r0);
        Rect rect = new Rect();
        LongScreenshotActivity.this.mEnterTransitionView.getBoundsOnScreen(rect);
        LongScreenshotData longScreenshotData = LongScreenshotActivity.this.mLongScreenshotHolder;
        Objects.requireNonNull(longScreenshotData);
        longScreenshotData.mTransitionDestinationCallback.getAndSet((Object) null).setTransitionDestination(rect, new LongScreenshotActivity$1$$ExternalSyntheticLambda0(r0, f, f2));
    }
}
