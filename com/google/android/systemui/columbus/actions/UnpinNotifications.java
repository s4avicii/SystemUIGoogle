package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.util.Log;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.gates.SilenceAlertsDisabled;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Optional;
import java.util.Set;

/* compiled from: UnpinNotifications.kt */
public final class UnpinNotifications extends Action {
    public boolean hasPinnedHeadsUp;
    public final UnpinNotifications$headsUpChangedListener$1 headsUpChangedListener;
    public final HeadsUpManager headsUpManager;
    public final SilenceAlertsDisabled silenceAlertsDisabled;
    public final String tag = "Columbus/UnpinNotif";

    public UnpinNotifications(Context context, SilenceAlertsDisabled silenceAlertsDisabled2, Optional<HeadsUpManager> optional) {
        super(context, (Set<? extends FeedbackEffect>) null);
        boolean z;
        this.silenceAlertsDisabled = silenceAlertsDisabled2;
        HeadsUpManager orElse = optional.orElse((Object) null);
        this.headsUpManager = orElse;
        this.headsUpChangedListener = new UnpinNotifications$headsUpChangedListener$1(this);
        UnpinNotifications$gateListener$1 unpinNotifications$gateListener$1 = new UnpinNotifications$gateListener$1(this);
        if (orElse == null) {
            Log.w("Columbus/UnpinNotif", "No HeadsUpManager");
        } else {
            silenceAlertsDisabled2.registerListener(unpinNotifications$gateListener$1);
        }
        if (silenceAlertsDisabled2.isBlocking() || !this.hasPinnedHeadsUp) {
            z = false;
        } else {
            z = true;
        }
        setAvailable(z);
    }

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        HeadsUpManager headsUpManager2 = this.headsUpManager;
        if (headsUpManager2 != null) {
            headsUpManager2.unpinAll();
        }
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.tag;
    }
}
