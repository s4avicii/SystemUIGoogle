package com.google.android.systemui.elmyra.actions;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda10;
import com.android.systemui.Dependency;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.tuner.TunerService;
import com.google.android.systemui.assist.AssistManagerGoogle;
import com.google.android.systemui.assist.OpaEnabledListener;
import com.google.android.systemui.assist.OpaEnabledReceiver;
import com.google.android.systemui.elmyra.UserContentObserver;
import com.google.android.systemui.elmyra.sensors.GestureSensor;
import java.util.ArrayList;
import java.util.Objects;

public final class LaunchOpa extends Action implements TunerService.Tunable {
    public final AssistManager mAssistManager;
    public boolean mEnableForAnyAssistant;
    public boolean mIsGestureEnabled;
    public boolean mIsOpaEnabled;
    public final KeyguardManager mKeyguardManager = ((KeyguardManager) this.mContext.getSystemService("keyguard"));
    public final C22401 mOpaEnabledListener;
    public final StatusBar mStatusBar;

    public static class Builder {
        public final Context mContext;
        public ArrayList mFeedbackEffects = new ArrayList();
        public final StatusBar mStatusBar;

        public Builder(Context context, StatusBar statusBar) {
            this.mContext = context;
            this.mStatusBar = statusBar;
        }
    }

    public final boolean isAvailable() {
        if (!this.mIsGestureEnabled || !this.mIsOpaEnabled) {
            return false;
        }
        return true;
    }

    public final void launchOpa(long j) {
        int i;
        Bundle bundle = new Bundle();
        if (this.mKeyguardManager.isKeyguardLocked()) {
            i = 14;
        } else {
            i = 13;
        }
        bundle.putInt("triggered_by", i);
        bundle.putLong("latency_id", j);
        bundle.putInt("invocation_type", 2);
        this.mAssistManager.startAssist(bundle);
    }

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        long j;
        this.mStatusBar.collapseShade();
        triggerFeedbackEffects(detectionProperties);
        if (detectionProperties != null) {
            j = detectionProperties.mActionId;
        } else {
            j = 0;
        }
        launchOpa(j);
    }

    public final void onTuningChanged(String str, String str2) {
        if ("assist_gesture_any_assistant".equals(str)) {
            this.mEnableForAnyAssistant = "1".equals(str2);
            AssistManagerGoogle assistManagerGoogle = (AssistManagerGoogle) this.mAssistManager;
            Objects.requireNonNull(assistManagerGoogle);
            OpaEnabledReceiver opaEnabledReceiver = assistManagerGoogle.mOpaEnabledReceiver;
            Objects.requireNonNull(opaEnabledReceiver);
            opaEnabledReceiver.dispatchOpaEnabledState(opaEnabledReceiver.mContext);
        }
    }

    public final String toString() {
        return super.toString() + " [mIsGestureEnabled -> " + this.mIsGestureEnabled + "; mIsOpaEnabled -> " + this.mIsOpaEnabled + "]";
    }

    public LaunchOpa(Context context, StatusBar statusBar, ArrayList arrayList) {
        super(context, arrayList);
        boolean z;
        C22401 r7 = new OpaEnabledListener() {
            public final void onOpaEnabledReceived(Context context, boolean z, boolean z2, boolean z3, boolean z4) {
                boolean z5;
                boolean z6 = false;
                if (z2 || LaunchOpa.this.mEnableForAnyAssistant) {
                    z5 = true;
                } else {
                    z5 = false;
                }
                if (z && z5 && z3) {
                    z6 = true;
                }
                LaunchOpa launchOpa = LaunchOpa.this;
                if (launchOpa.mIsOpaEnabled != z6) {
                    launchOpa.mIsOpaEnabled = z6;
                    launchOpa.notifyListener();
                }
            }
        };
        this.mOpaEnabledListener = r7;
        this.mStatusBar = statusBar;
        AssistManager assistManager = (AssistManager) Dependency.get(AssistManager.class);
        this.mAssistManager = assistManager;
        boolean z2 = true;
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "assist_gesture_enabled", 1, -2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mIsGestureEnabled = z;
        new UserContentObserver(this.mContext, Settings.Secure.getUriFor("assist_gesture_enabled"), new BubbleController$$ExternalSyntheticLambda10(this, 3), true);
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "assist_gesture_any_assistant");
        this.mEnableForAnyAssistant = Settings.Secure.getInt(this.mContext.getContentResolver(), "assist_gesture_any_assistant", 0) != 1 ? false : z2;
        ((AssistManagerGoogle) assistManager).addOpaEnabledListener(r7);
    }

    public final void onProgress(float f, int i) {
        updateFeedbackEffects(f, i);
    }
}
