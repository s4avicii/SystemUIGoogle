package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.ScreenshotHelper;
import com.google.android.systemui.columbus.ColumbusEvent;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.function.Consumer;

/* compiled from: TakeScreenshot.kt */
public final class TakeScreenshot extends UserAction {
    public final Handler handler;
    public final ScreenshotHelper screenshotHelper;
    public final String tag = "Columbus/TakeScreenshot";
    public final UiEventLogger uiEventLogger;

    public final boolean availableOnLockscreen() {
        return true;
    }

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.screenshotHelper.takeScreenshot(1, true, true, 6, this.handler, (Consumer) null);
        this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_SCREENSHOT);
    }

    public TakeScreenshot(Context context, Handler handler2, UiEventLogger uiEventLogger2) {
        super(context);
        this.handler = handler2;
        this.uiEventLogger = uiEventLogger2;
        this.screenshotHelper = new ScreenshotHelper(context);
        setAvailable(true);
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.tag;
    }
}
