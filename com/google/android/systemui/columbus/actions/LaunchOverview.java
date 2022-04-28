package com.google.android.systemui.columbus.actions;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.recents.Recents;
import com.google.android.systemui.columbus.ColumbusEvent;
import com.google.android.systemui.columbus.sensors.GestureSensor;

/* compiled from: LaunchOverview.kt */
public final class LaunchOverview extends UserAction {
    public final Recents recents;
    public final String tag = "Columbus/LaunchOverview";
    public final UiEventLogger uiEventLogger;

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.recents.toggleRecentApps();
        this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_OVERVIEW);
    }

    public LaunchOverview(Context context, Recents recents2, UiEventLogger uiEventLogger2) {
        super(context);
        this.recents = recents2;
        this.uiEventLogger = uiEventLogger2;
        setAvailable(true);
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.tag;
    }
}
