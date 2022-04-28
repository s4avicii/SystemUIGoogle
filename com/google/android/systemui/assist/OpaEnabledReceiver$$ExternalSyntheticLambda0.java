package com.google.android.systemui.assist;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.PathInterpolator;
import com.android.internal.app.AssistUtils;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda15;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0;
import com.android.systemui.util.Assert;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OpaEnabledReceiver$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ OpaEnabledReceiver f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ BroadcastReceiver.PendingResult f$2;

    public /* synthetic */ OpaEnabledReceiver$$ExternalSyntheticLambda0(OpaEnabledReceiver opaEnabledReceiver, boolean z, BroadcastReceiver.PendingResult pendingResult) {
        this.f$0 = opaEnabledReceiver;
        this.f$1 = z;
        this.f$2 = pendingResult;
    }

    public final void run() {
        boolean z;
        boolean z2;
        boolean z3;
        OpaEnabledReceiver opaEnabledReceiver = this.f$0;
        boolean z4 = this.f$1;
        BroadcastReceiver.PendingResult pendingResult = this.f$2;
        Objects.requireNonNull(opaEnabledReceiver);
        OpaEnabledSettings opaEnabledSettings = opaEnabledReceiver.mOpaEnabledSettings;
        Objects.requireNonNull(opaEnabledSettings);
        Assert.isNotMainThread();
        boolean z5 = false;
        if (Settings.Secure.getIntForUser(opaEnabledSettings.mContext.getContentResolver(), "systemui.google.opa_enabled", 0, ActivityManager.getCurrentUser()) != 0) {
            z = true;
        } else {
            z = false;
        }
        opaEnabledReceiver.mIsOpaEligible = z;
        OpaEnabledSettings opaEnabledSettings2 = opaEnabledReceiver.mOpaEnabledSettings;
        Objects.requireNonNull(opaEnabledSettings2);
        Assert.isNotMainThread();
        Context context = opaEnabledSettings2.mContext;
        PathInterpolator pathInterpolator = OpaUtils.INTERPOLATOR_40_40;
        ComponentName assistComponentForUser = new AssistUtils(context).getAssistComponentForUser(-2);
        if (assistComponentForUser == null || !"com.google.android.googlequicksearchbox/com.google.android.voiceinteraction.GsaVoiceInteractionService".equals(assistComponentForUser.flattenToString())) {
            z2 = false;
        } else {
            z2 = true;
        }
        opaEnabledReceiver.mIsAGSAAssistant = z2;
        OpaEnabledSettings opaEnabledSettings3 = opaEnabledReceiver.mOpaEnabledSettings;
        Objects.requireNonNull(opaEnabledSettings3);
        Assert.isNotMainThread();
        try {
            z3 = opaEnabledSettings3.mLockSettings.getBoolean("systemui.google.opa_user_enabled", false, ActivityManager.getCurrentUser());
        } catch (RemoteException e) {
            Log.e("OpaEnabledSettings", "isOpaEnabled RemoteException", e);
            z3 = false;
        }
        opaEnabledReceiver.mIsOpaEnabled = z3;
        OpaEnabledSettings opaEnabledSettings4 = opaEnabledReceiver.mOpaEnabledSettings;
        Objects.requireNonNull(opaEnabledSettings4);
        Assert.isNotMainThread();
        if (Settings.Secure.getInt(opaEnabledSettings4.mContext.getContentResolver(), "assist_long_press_home_enabled", opaEnabledSettings4.mContext.getResources().getBoolean(17891368) ? 1 : 0) != 0) {
            z5 = true;
        }
        opaEnabledReceiver.mIsLongPressHomeEnabled = z5;
        if (z4) {
            opaEnabledReceiver.mFgExecutor.execute(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(opaEnabledReceiver, 9));
        }
        if (pendingResult != null) {
            opaEnabledReceiver.mFgExecutor.execute(new BubbleStackView$$ExternalSyntheticLambda15(pendingResult, 10));
        }
    }
}
