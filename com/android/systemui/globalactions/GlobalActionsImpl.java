package com.android.systemui.globalactions;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.telephony.TelephonyCallback;
import com.android.systemui.telephony.TelephonyListenerManager;
import dagger.Lazy;
import java.util.Objects;

public final class GlobalActionsImpl implements GlobalActions, CommandQueue.Callbacks {
    public final BlurUtils mBlurUtils;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public boolean mDisabled;
    public GlobalActionsDialogLite mGlobalActionsDialog;
    public final Lazy<GlobalActionsDialogLite> mGlobalActionsDialogLazy;
    public final KeyguardStateController mKeyguardStateController;

    public final void destroy() {
        this.mCommandQueue.removeCallback((CommandQueue.Callbacks) this);
        GlobalActionsDialogLite globalActionsDialogLite = this.mGlobalActionsDialog;
        if (globalActionsDialogLite != null) {
            globalActionsDialogLite.mBroadcastDispatcher.unregisterReceiver(globalActionsDialogLite.mBroadcastReceiver);
            TelephonyListenerManager telephonyListenerManager = globalActionsDialogLite.mTelephonyListenerManager;
            GlobalActionsDialogLite.C08146 r2 = globalActionsDialogLite.mPhoneStateListener;
            Objects.requireNonNull(telephonyListenerManager);
            TelephonyCallback telephonyCallback = telephonyListenerManager.mTelephonyCallback;
            Objects.requireNonNull(telephonyCallback);
            telephonyCallback.mServiceStateListeners.remove(r2);
            telephonyListenerManager.updateListening();
            globalActionsDialogLite.mGlobalSettings.unregisterContentObserver(globalActionsDialogLite.mAirplaneModeObserver);
            globalActionsDialogLite.mConfigurationController.removeCallback(globalActionsDialogLite);
            this.mGlobalActionsDialog = null;
        }
    }

    public final void disable(int i, int i2, int i3, boolean z) {
        boolean z2;
        GlobalActionsDialogLite globalActionsDialogLite;
        if ((i3 & 8) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (i == this.mContext.getDisplayId() && z2 != this.mDisabled) {
            this.mDisabled = z2;
            if (z2 && (globalActionsDialogLite = this.mGlobalActionsDialog) != null) {
                globalActionsDialogLite.mHandler.removeMessages(0);
                globalActionsDialogLite.mHandler.sendEmptyMessage(0);
            }
        }
    }

    public final void showGlobalActions(GlobalActions.GlobalActionsManager globalActionsManager) {
        if (!this.mDisabled) {
            GlobalActionsDialogLite globalActionsDialogLite = this.mGlobalActionsDialogLazy.get();
            this.mGlobalActionsDialog = globalActionsDialogLite;
            globalActionsDialogLite.showOrHideDialog(this.mKeyguardStateController.isShowing(), this.mDeviceProvisionedController.isDeviceProvisioned(), (View) null);
        }
    }

    public final void showShutdownUi(boolean z, String str) {
        int i;
        int i2;
        String str2;
        ScrimDrawable scrimDrawable = new ScrimDrawable();
        Dialog dialog = new Dialog(this.mContext, 2132018186);
        dialog.setOnShowListener(new GlobalActionsImpl$$ExternalSyntheticLambda0(this, scrimDrawable, dialog));
        Window window = dialog.getWindow();
        window.requestFeature(1);
        window.getAttributes().systemUiVisibility |= 1792;
        window.getDecorView();
        window.getAttributes().width = -1;
        window.getAttributes().height = -1;
        window.getAttributes().layoutInDisplayCutoutMode = 3;
        window.setType(2020);
        window.getAttributes().setFitInsetsTypes(0);
        window.clearFlags(2);
        window.addFlags(17629472);
        window.setBackgroundDrawable(scrimDrawable);
        window.setWindowAnimations(2132017166);
        dialog.setContentView(17367316);
        dialog.setCancelable(false);
        if (this.mBlurUtils.supportsBlursOnWindows()) {
            i = Utils.getColorAttrDefaultColor(this.mContext, C1777R.attr.wallpaperTextColor);
        } else {
            i = this.mContext.getResources().getColor(C1777R.color.global_actions_shutdown_ui_text);
        }
        ((ProgressBar) dialog.findViewById(16908301)).getIndeterminateDrawable().setTint(i);
        TextView textView = (TextView) dialog.findViewById(16908308);
        TextView textView2 = (TextView) dialog.findViewById(16908309);
        textView.setTextColor(i);
        textView2.setTextColor(i);
        if (str != null && str.startsWith("recovery-update")) {
            i2 = 17041347;
        } else if ((str == null || !str.equals("recovery")) && !z) {
            i2 = 17041492;
        } else {
            i2 = 17041343;
        }
        textView2.setText(i2);
        if (str != null && str.startsWith("recovery-update")) {
            str2 = this.mContext.getString(17041348);
        } else if (str == null || !str.equals("recovery")) {
            str2 = null;
        } else {
            str2 = this.mContext.getString(17041344);
        }
        if (str2 != null) {
            textView.setVisibility(0);
            textView.setText(str2);
        }
        dialog.show();
    }

    public GlobalActionsImpl(Context context, CommandQueue commandQueue, Lazy<GlobalActionsDialogLite> lazy, BlurUtils blurUtils, KeyguardStateController keyguardStateController, DeviceProvisionedController deviceProvisionedController) {
        this.mContext = context;
        this.mGlobalActionsDialogLazy = lazy;
        this.mKeyguardStateController = keyguardStateController;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mCommandQueue = commandQueue;
        this.mBlurUtils = blurUtils;
        commandQueue.addCallback((CommandQueue.Callbacks) this);
    }
}
