package com.google.android.systemui.columbus.actions;

import android.app.KeyguardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.tuner.TunerService;
import com.google.android.systemui.assist.AssistManagerGoogle;
import com.google.android.systemui.columbus.ColumbusContentObserver;
import com.google.android.systemui.columbus.ColumbusEvent;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import dagger.Lazy;
import java.util.Objects;
import java.util.Set;

/* compiled from: LaunchOpa.kt */
public final class LaunchOpa extends UserAction {
    public final AssistManagerGoogle assistManager;
    public boolean enableForAnyAssistant;
    public boolean isGestureEnabled;
    public boolean isOpaEnabled;
    public final Lazy<KeyguardManager> keyguardManager;
    public final LaunchOpa$opaEnabledListener$1 opaEnabledListener;
    public final StatusBar statusBar;
    public final String tag = "Columbus/LaunchOpa";
    public final LaunchOpa$tunable$1 tunable;
    public final UiEventLogger uiEventLogger;

    public final boolean availableOnLockscreen() {
        return true;
    }

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        long j;
        int i;
        this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_ASSISTANT);
        this.statusBar.collapseShade();
        if (detectionProperties == null) {
            j = 0;
        } else {
            j = detectionProperties.actionId;
        }
        Bundle bundle = new Bundle();
        if (this.keyguardManager.get().isKeyguardLocked()) {
            i = 120;
        } else {
            i = 119;
        }
        bundle.putInt("triggered_by", i);
        bundle.putLong("latency_id", j);
        bundle.putInt("invocation_type", 2);
        AssistManagerGoogle assistManagerGoogle = this.assistManager;
        if (assistManagerGoogle != null) {
            assistManagerGoogle.startAssist(bundle);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" [isGestureEnabled -> ");
        sb.append(this.isGestureEnabled);
        sb.append("; isOpaEnabled -> ");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(sb, this.isOpaEnabled, ']');
    }

    public LaunchOpa(Context context, StatusBar statusBar2, Set<FeedbackEffect> set, AssistManager assistManager2, Lazy<KeyguardManager> lazy, TunerService tunerService, ColumbusContentObserver.Factory factory, UiEventLogger uiEventLogger2) {
        super(context, set);
        AssistManagerGoogle assistManagerGoogle;
        boolean z;
        boolean z2;
        this.statusBar = statusBar2;
        this.keyguardManager = lazy;
        this.uiEventLogger = uiEventLogger2;
        if (assistManager2 instanceof AssistManagerGoogle) {
            assistManagerGoogle = (AssistManagerGoogle) assistManager2;
        } else {
            assistManagerGoogle = null;
        }
        this.assistManager = assistManagerGoogle;
        LaunchOpa$opaEnabledListener$1 launchOpa$opaEnabledListener$1 = new LaunchOpa$opaEnabledListener$1(this);
        this.opaEnabledListener = launchOpa$opaEnabledListener$1;
        Uri uriFor = Settings.Secure.getUriFor("assist_gesture_enabled");
        LaunchOpa$settingsObserver$1 launchOpa$settingsObserver$1 = new LaunchOpa$settingsObserver$1(this);
        Objects.requireNonNull(factory);
        ColumbusContentObserver columbusContentObserver = new ColumbusContentObserver(factory.contentResolver, uriFor, launchOpa$settingsObserver$1, factory.userTracker, factory.executor, factory.handler);
        LaunchOpa$tunable$1 launchOpa$tunable$1 = new LaunchOpa$tunable$1(this);
        this.tunable = launchOpa$tunable$1;
        boolean z3 = true;
        if (Settings.Secure.getIntForUser(context.getContentResolver(), "assist_gesture_enabled", 1, -2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.isGestureEnabled = z;
        if (Settings.Secure.getInt(context.getContentResolver(), "assist_gesture_any_assistant", 0) == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.enableForAnyAssistant = z2;
        columbusContentObserver.userTracker.addCallback(columbusContentObserver.userTrackerCallback, columbusContentObserver.executor);
        columbusContentObserver.updateContentObserver();
        tunerService.addTunable(launchOpa$tunable$1, "assist_gesture_any_assistant");
        if (assistManagerGoogle != null) {
            assistManagerGoogle.addOpaEnabledListener(launchOpa$opaEnabledListener$1);
        }
        setAvailable((!this.isGestureEnabled || !this.isOpaEnabled) ? false : z3);
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.tag;
    }
}
