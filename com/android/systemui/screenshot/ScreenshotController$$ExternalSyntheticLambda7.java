package com.android.systemui.screenshot;

import android.content.ComponentName;
import android.graphics.Rect;
import com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda4;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenshotController$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ ScreenshotController f$0;
    public final /* synthetic */ ComponentName f$1;
    public final /* synthetic */ Consumer f$2;

    public /* synthetic */ ScreenshotController$$ExternalSyntheticLambda7(ScreenshotController screenshotController, ComponentName componentName, DozeTriggers$$ExternalSyntheticLambda4 dozeTriggers$$ExternalSyntheticLambda4) {
        this.f$0 = screenshotController;
        this.f$1 = componentName;
        this.f$2 = dozeTriggers$$ExternalSyntheticLambda4;
    }

    public final void accept(Object obj) {
        ScreenshotController screenshotController = this.f$0;
        Objects.requireNonNull(screenshotController);
        screenshotController.takeScreenshotInternal(this.f$1, this.f$2, (Rect) obj);
    }
}
