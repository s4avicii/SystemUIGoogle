package com.google.android.systemui.assist;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.IWindowManager;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda8;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.AssistantSessionEvent;
import com.android.systemui.assist.PhoneStateMonitor;
import com.android.systemui.assist.p003ui.DefaultUiController;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.google.android.systemui.assist.uihints.AssistantPresenceHandler;
import com.google.android.systemui.assist.uihints.GoogleDefaultUiController;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.NgaUiController;
import dagger.Lazy;
import java.util.Objects;

public final class AssistManagerGoogle extends AssistManager {
    public final AssistantPresenceHandler mAssistantPresenceHandler;
    public boolean mCheckAssistantStatus = true;
    public final GoogleDefaultUiController mDefaultUiController;
    public boolean mGoogleIsAssistant;
    public int mNavigationMode;
    public boolean mNgaIsAssistant;
    public final NgaMessageHandler mNgaMessageHandler;
    public final NgaUiController mNgaUiController;
    public final KeyguardUpdateMonitor$$ExternalSyntheticLambda8 mOnProcessBundle;
    public final OpaEnabledReceiver mOpaEnabledReceiver;
    public boolean mSqueezeSetUp;
    public AssistManager.UiController mUiController;
    public final Handler mUiHandler;
    public final IWindowManager mWindowManagerService;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AssistManagerGoogle(DeviceProvisionedController deviceProvisionedController, Context context, AssistUtils assistUtils, NgaUiController ngaUiController, CommandQueue commandQueue, OpaEnabledReceiver opaEnabledReceiver, PhoneStateMonitor phoneStateMonitor, OverviewProxyService overviewProxyService, OpaEnabledDispatcher opaEnabledDispatcher, KeyguardUpdateMonitor keyguardUpdateMonitor, NavigationModeController navigationModeController, AssistantPresenceHandler assistantPresenceHandler, NgaMessageHandler ngaMessageHandler, Lazy<SysUiState> lazy, Handler handler, DefaultUiController defaultUiController, GoogleDefaultUiController googleDefaultUiController, IWindowManager iWindowManager, AssistLogger assistLogger) {
        super(deviceProvisionedController, context, assistUtils, commandQueue, phoneStateMonitor, overviewProxyService, lazy, defaultUiController, assistLogger, handler);
        AssistantPresenceHandler assistantPresenceHandler2 = assistantPresenceHandler;
        GoogleDefaultUiController googleDefaultUiController2 = googleDefaultUiController;
        this.mUiHandler = handler;
        this.mOpaEnabledReceiver = opaEnabledReceiver;
        addOpaEnabledListener(opaEnabledDispatcher);
        keyguardUpdateMonitor.registerCallback(new KeyguardUpdateMonitorCallback() {
            public final void onUserSwitching(int i) {
                OpaEnabledReceiver opaEnabledReceiver = AssistManagerGoogle.this.mOpaEnabledReceiver;
                Objects.requireNonNull(opaEnabledReceiver);
                opaEnabledReceiver.updateOpaEnabledState(true, (BroadcastReceiver.PendingResult) null);
                opaEnabledReceiver.mContentResolver.unregisterContentObserver(opaEnabledReceiver.mContentObserver);
                opaEnabledReceiver.registerContentObserver();
                opaEnabledReceiver.mBroadcastDispatcher.unregisterReceiver(opaEnabledReceiver.mBroadcastReceiver);
                opaEnabledReceiver.registerEnabledReceiver(i);
            }
        });
        this.mNgaUiController = ngaUiController;
        this.mDefaultUiController = googleDefaultUiController2;
        this.mUiController = googleDefaultUiController2;
        this.mNavigationMode = navigationModeController.addListener(new AssistManagerGoogle$$ExternalSyntheticLambda0(this));
        this.mAssistantPresenceHandler = assistantPresenceHandler2;
        AssistManagerGoogle$$ExternalSyntheticLambda1 assistManagerGoogle$$ExternalSyntheticLambda1 = new AssistManagerGoogle$$ExternalSyntheticLambda1(this);
        Objects.requireNonNull(assistantPresenceHandler);
        assistantPresenceHandler2.mAssistantPresenceChangeListeners.add(assistManagerGoogle$$ExternalSyntheticLambda1);
        this.mNgaMessageHandler = ngaMessageHandler;
        this.mOnProcessBundle = new KeyguardUpdateMonitor$$ExternalSyntheticLambda8(this, 5);
        this.mWindowManagerService = iWindowManager;
    }

    public final void logStartAssistLegacy(int i, int i2) {
        AssistantPresenceHandler assistantPresenceHandler = this.mAssistantPresenceHandler;
        Objects.requireNonNull(assistantPresenceHandler);
        MetricsLogger.action(new LogMaker(1716).setType(1).setSubtype(((assistantPresenceHandler.mNgaIsAssistant ? 1 : 0) << true) | (i << 1) | 0 | (i2 << 4)));
    }

    public final void onGestureCompletion(float f) {
        this.mCheckAssistantStatus = true;
        this.mUiController.onGestureCompletion(f / this.mContext.getResources().getDisplayMetrics().density);
    }

    public final void onInvocationProgress(int i, float f) {
        boolean z;
        boolean z2 = true;
        if (f == 0.0f || f == 1.0f) {
            this.mCheckAssistantStatus = true;
            if (i == 2) {
                if (Settings.Secure.getInt(this.mContext.getContentResolver(), "assist_gesture_setup_complete", 0) == 1) {
                    z = true;
                } else {
                    z = false;
                }
                this.mSqueezeSetUp = z;
            }
        }
        if (this.mCheckAssistantStatus) {
            AssistantPresenceHandler assistantPresenceHandler = this.mAssistantPresenceHandler;
            Objects.requireNonNull(assistantPresenceHandler);
            ComponentName assistComponentForUser = assistantPresenceHandler.mAssistUtils.getAssistComponentForUser(-2);
            if (assistComponentForUser == null || !"com.google.android.googlequicksearchbox/com.google.android.voiceinteraction.GsaVoiceInteractionService".equals(assistComponentForUser.flattenToString())) {
                z2 = false;
            }
            assistantPresenceHandler.updateAssistantPresence(z2, assistantPresenceHandler.mNgaIsAssistant, assistantPresenceHandler.mSysUiIsNgaUi);
            this.mCheckAssistantStatus = false;
        }
        if (i != 2 || this.mSqueezeSetUp) {
            this.mUiController.onInvocationProgress(i, f);
        }
    }

    public final void addOpaEnabledListener(OpaEnabledListener opaEnabledListener) {
        OpaEnabledReceiver opaEnabledReceiver = this.mOpaEnabledReceiver;
        Objects.requireNonNull(opaEnabledReceiver);
        opaEnabledReceiver.mListeners.add(opaEnabledListener);
        opaEnabledListener.onOpaEnabledReceived(opaEnabledReceiver.mContext, opaEnabledReceiver.mIsOpaEligible, opaEnabledReceiver.mIsAGSAAssistant, opaEnabledReceiver.mIsOpaEnabled, opaEnabledReceiver.mIsLongPressHomeEnabled);
    }

    public final void registerVoiceInteractionSessionListener() {
        this.mAssistUtils.registerVoiceInteractionSessionListener(new IVoiceInteractionSessionListener.Stub() {
            public final void onSetUiHints(Bundle bundle) {
                String string = bundle.getString("action");
                if ("set_assist_gesture_constrained".equals(string)) {
                    SysUiState sysUiState = AssistManagerGoogle.this.mSysUiState.get();
                    sysUiState.setFlag(8192, bundle.getBoolean("should_constrain", false));
                    sysUiState.commitUpdate(0);
                } else if ("show_global_actions".equals(string)) {
                    try {
                        AssistManagerGoogle.this.mWindowManagerService.showGlobalActions();
                    } catch (RemoteException e) {
                        Log.e("AssistManagerGoogle", "showGlobalActions failed", e);
                    }
                } else {
                    AssistManagerGoogle assistManagerGoogle = AssistManagerGoogle.this;
                    assistManagerGoogle.mNgaMessageHandler.processBundle(bundle, assistManagerGoogle.mOnProcessBundle);
                }
            }

            public final void onVoiceSessionHidden() throws RemoteException {
                AssistManagerGoogle.this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_CLOSE);
            }

            public final void onVoiceSessionShown() throws RemoteException {
                AssistManagerGoogle.this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_UPDATE);
            }
        });
    }
}
