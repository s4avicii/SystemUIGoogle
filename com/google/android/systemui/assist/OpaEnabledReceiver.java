package com.google.android.systemui.assist;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class OpaEnabledReceiver {
    public final Executor mBgExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final OpaEnabledBroadcastReceiver mBroadcastReceiver = new OpaEnabledBroadcastReceiver();
    public final AssistantContentObserver mContentObserver;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final Executor mFgExecutor;
    public boolean mIsAGSAAssistant;
    public boolean mIsLongPressHomeEnabled;
    public boolean mIsOpaEligible;
    public boolean mIsOpaEnabled;
    public final ArrayList mListeners = new ArrayList();
    public final OpaEnabledSettings mOpaEnabledSettings;

    public class AssistantContentObserver extends ContentObserver {
        public AssistantContentObserver(Context context) {
            super(new Handler(context.getMainLooper()));
        }

        public final void onChange(boolean z, Uri uri) {
            OpaEnabledReceiver opaEnabledReceiver = OpaEnabledReceiver.this;
            Objects.requireNonNull(opaEnabledReceiver);
            opaEnabledReceiver.updateOpaEnabledState(true, (BroadcastReceiver.PendingResult) null);
        }
    }

    public class OpaEnabledBroadcastReceiver extends BroadcastReceiver {
        public OpaEnabledBroadcastReceiver() {
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.google.android.systemui.OPA_ENABLED")) {
                boolean booleanExtra = intent.getBooleanExtra("OPA_ENABLED", false);
                OpaEnabledSettings opaEnabledSettings = OpaEnabledReceiver.this.mOpaEnabledSettings;
                Objects.requireNonNull(opaEnabledSettings);
                Assert.isNotMainThread();
                Settings.Secure.putIntForUser(opaEnabledSettings.mContext.getContentResolver(), "systemui.google.opa_enabled", booleanExtra ? 1 : 0, ActivityManager.getCurrentUser());
            } else if (intent.getAction().equals("com.google.android.systemui.OPA_USER_ENABLED")) {
                boolean booleanExtra2 = intent.getBooleanExtra("OPA_USER_ENABLED", false);
                OpaEnabledSettings opaEnabledSettings2 = OpaEnabledReceiver.this.mOpaEnabledSettings;
                Objects.requireNonNull(opaEnabledSettings2);
                Assert.isNotMainThread();
                try {
                    opaEnabledSettings2.mLockSettings.setBoolean("systemui.google.opa_user_enabled", booleanExtra2, ActivityManager.getCurrentUser());
                } catch (RemoteException e) {
                    Log.e("OpaEnabledSettings", "RemoteException on OPA_USER_ENABLED", e);
                }
            }
            OpaEnabledReceiver.this.updateOpaEnabledState(true, goAsync());
        }
    }

    public final void dispatchOpaEnabledState(Context context) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Dispatching OPA eligble = ");
        m.append(this.mIsOpaEligible);
        m.append("; AGSA = ");
        m.append(this.mIsAGSAAssistant);
        m.append("; OPA enabled = ");
        m.append(this.mIsOpaEnabled);
        Log.i("OpaEnabledReceiver", m.toString());
        for (int i = 0; i < this.mListeners.size(); i++) {
            ((OpaEnabledListener) this.mListeners.get(i)).onOpaEnabledReceived(context, this.mIsOpaEligible, this.mIsAGSAAssistant, this.mIsOpaEnabled, this.mIsLongPressHomeEnabled);
        }
    }

    public final void registerContentObserver() {
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assistant"), false, this.mContentObserver, -2);
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assist_long_press_home_enabled"), false, this.mContentObserver, -2);
    }

    public final void registerEnabledReceiver(int i) {
        this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.google.android.systemui.OPA_ENABLED"), this.mBgExecutor, new UserHandle(i));
        this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.google.android.systemui.OPA_USER_ENABLED"), this.mBgExecutor, new UserHandle(i));
    }

    public final void updateOpaEnabledState(boolean z, BroadcastReceiver.PendingResult pendingResult) {
        this.mBgExecutor.execute(new OpaEnabledReceiver$$ExternalSyntheticLambda0(this, z, pendingResult));
    }

    public OpaEnabledReceiver(Context context, BroadcastDispatcher broadcastDispatcher, Executor executor, Executor executor2, OpaEnabledSettings opaEnabledSettings) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mContentObserver = new AssistantContentObserver(context);
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mFgExecutor = executor;
        this.mBgExecutor = executor2;
        this.mOpaEnabledSettings = opaEnabledSettings;
        updateOpaEnabledState(false, (BroadcastReceiver.PendingResult) null);
        registerContentObserver();
        registerEnabledReceiver(-2);
    }

    @VisibleForTesting
    public BroadcastReceiver getBroadcastReceiver() {
        return this.mBroadcastReceiver;
    }
}
