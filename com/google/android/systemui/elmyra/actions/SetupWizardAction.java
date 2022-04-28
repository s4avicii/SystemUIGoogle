package com.google.android.systemui.elmyra.actions;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.elmyra.gates.Gate;
import com.google.android.systemui.elmyra.gates.KeyguardDeferredSetup;
import com.google.android.systemui.elmyra.sensors.GestureSensor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public final class SetupWizardAction extends Action {
    public boolean mDeviceInDemoMode;
    public final C22422 mKeyguardDeferredSetupListener;
    public final LaunchOpa mLaunchOpa;
    public final SettingsAction mSettingsAction;
    public final String mSettingsPackageName;
    public final StatusBar mStatusBar;
    public boolean mUserCompletedSuw;
    public final C22411 mUserSwitchCallback;

    public SetupWizardAction(Context context, SettingsAction settingsAction, LaunchOpa launchOpa, StatusBar statusBar) {
        super(context, (ArrayList) null);
        C22411 r0 = new KeyguardUpdateMonitorCallback() {
            public final void onUserSwitching(int i) {
                SetupWizardAction setupWizardAction = SetupWizardAction.this;
                Objects.requireNonNull(setupWizardAction);
                setupWizardAction.mDeviceInDemoMode = UserManager.isDeviceInDemoMode(setupWizardAction.mContext);
                SetupWizardAction.this.notifyListener();
            }
        };
        this.mUserSwitchCallback = r0;
        C22422 r1 = new Gate.Listener() {
            public final void onGateChanged(Gate gate) {
                SetupWizardAction setupWizardAction = SetupWizardAction.this;
                setupWizardAction.mUserCompletedSuw = ((KeyguardDeferredSetup) gate).mDeferredSetupComplete;
                setupWizardAction.notifyListener();
            }
        };
        this.mKeyguardDeferredSetupListener = r1;
        this.mSettingsPackageName = context.getResources().getString(C1777R.string.settings_app_package_name);
        this.mSettingsAction = settingsAction;
        this.mLaunchOpa = launchOpa;
        this.mStatusBar = statusBar;
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).registerCallback(r0);
        KeyguardDeferredSetup keyguardDeferredSetup = new KeyguardDeferredSetup(context, Collections.emptyList());
        keyguardDeferredSetup.activate();
        keyguardDeferredSetup.mListener = r1;
        this.mUserCompletedSuw = keyguardDeferredSetup.mDeferredSetupComplete;
    }

    public static class Builder {
        public final Context mContext;
        public final StatusBar mStatusBar;

        public Builder(Context context, StatusBar statusBar) {
            this.mContext = context;
            this.mStatusBar = statusBar;
        }
    }

    public final boolean isAvailable() {
        if (!this.mDeviceInDemoMode && this.mLaunchOpa.isAvailable() && !this.mUserCompletedSuw && !this.mSettingsAction.isAvailable()) {
            return true;
        }
        return false;
    }

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.mStatusBar.collapseShade();
        triggerFeedbackEffects(detectionProperties);
        if (!this.mUserCompletedSuw && !this.mSettingsAction.isAvailable()) {
            Intent intent = new Intent();
            intent.setAction("com.google.android.settings.ASSIST_GESTURE_TRAINING");
            intent.setPackage(this.mSettingsPackageName);
            intent.setFlags(268468224);
            this.mContext.startActivityAsUser(intent, UserHandle.of(-2));
        }
    }

    public final void triggerFeedbackEffects(GestureSensor.DetectionProperties detectionProperties) {
        super.triggerFeedbackEffects(detectionProperties);
        this.mLaunchOpa.triggerFeedbackEffects(detectionProperties);
    }

    public final void updateFeedbackEffects(float f, int i) {
        super.updateFeedbackEffects(f, i);
        this.mLaunchOpa.updateFeedbackEffects(f, i);
    }

    public final void onProgress(float f, int i) {
        updateFeedbackEffects(f, i);
    }
}
