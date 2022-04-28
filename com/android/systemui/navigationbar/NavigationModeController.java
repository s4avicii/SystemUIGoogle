package com.android.systemui.navigationbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.IOverlayManager;
import android.content.pm.PackageManager;
import android.content.res.ApkAssets;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class NavigationModeController implements Dumpable {
    public final Context mContext;
    public Context mCurrentUserContext;
    public final C09261 mDeviceProvisionedCallback;
    public ArrayList<ModeChangedListener> mListeners = new ArrayList<>();
    public final IOverlayManager mOverlayManager;
    public C09272 mReceiver;
    public final Executor mUiBgExecutor;

    public interface ModeChangedListener {
        void onNavigationModeChanged(int i);
    }

    public final int addListener(ModeChangedListener modeChangedListener) {
        this.mListeners.add(modeChangedListener);
        return getCurrentInteractionMode(this.mCurrentUserContext);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "NavigationModeController:", "  mode=");
        m.append(getCurrentInteractionMode(this.mCurrentUserContext));
        printWriter.println(m.toString());
        try {
            str = String.join(", ", this.mOverlayManager.getDefaultOverlayPackages());
        } catch (RemoteException unused) {
            str = "failed_to_fetch";
        }
        printWriter.println("  defaultOverlays=" + str);
        dumpAssetPaths(this.mCurrentUserContext);
    }

    public final void dumpAssetPaths(Context context) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  contextUser=");
        m.append(this.mCurrentUserContext.getUserId());
        Log.d("NavigationModeController", m.toString());
        Log.d("NavigationModeController", "  assetPaths=");
        for (ApkAssets debugName : context.getResources().getAssets().getApkAssets()) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("    ");
            m2.append(debugName.getDebugName());
            Log.d("NavigationModeController", m2.toString());
        }
    }

    public final Context getCurrentUserContext() {
        Objects.requireNonNull(ActivityManagerWrapper.sInstance);
        int currentUserId = ActivityManagerWrapper.getCurrentUserId();
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("getCurrentUserContext: contextUser=");
        m.append(this.mContext.getUserId());
        m.append(" currentUser=");
        m.append(currentUserId);
        Log.d("NavigationModeController", m.toString());
        if (this.mContext.getUserId() == currentUserId) {
            return this.mContext;
        }
        try {
            Context context = this.mContext;
            return context.createPackageContextAsUser(context.getPackageName(), 0, UserHandle.of(currentUserId));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("NavigationModeController", "Failed to create package context", e);
            return null;
        }
    }

    public NavigationModeController(Context context, DeviceProvisionedController deviceProvisionedController, ConfigurationController configurationController, Executor executor, DumpManager dumpManager) {
        C09261 r0 = new DeviceProvisionedController.DeviceProvisionedListener() {
            public final void onUserSwitched() {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onUserSwitched: ");
                Objects.requireNonNull(ActivityManagerWrapper.sInstance);
                m.append(ActivityManagerWrapper.getCurrentUserId());
                Log.d("NavigationModeController", m.toString());
                NavigationModeController.this.updateCurrentInteractionMode(true);
            }
        };
        this.mDeviceProvisionedCallback = r0;
        this.mReceiver = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                Log.d("NavigationModeController", "ACTION_OVERLAY_CHANGED");
                NavigationModeController.this.updateCurrentInteractionMode(true);
            }
        };
        this.mContext = context;
        this.mCurrentUserContext = context;
        this.mOverlayManager = IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
        this.mUiBgExecutor = executor;
        dumpManager.registerDumpable("NavigationModeController", this);
        deviceProvisionedController.addCallback(r0);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.OVERLAY_CHANGED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(ThemeOverlayApplier.ANDROID_PACKAGE, 0);
        context.registerReceiverAsUser(this.mReceiver, UserHandle.ALL, intentFilter, (String) null, (Handler) null);
        configurationController.addCallback(new ConfigurationController.ConfigurationListener() {
            public final void onThemeChanged() {
                Log.d("NavigationModeController", "onOverlayChanged");
                NavigationModeController.this.updateCurrentInteractionMode(true);
            }
        });
        updateCurrentInteractionMode(false);
    }

    public static int getCurrentInteractionMode(Context context) {
        int integer = context.getResources().getInteger(17694878);
        StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("getCurrentInteractionMode: mode=", integer, " contextUser=");
        m.append(context.getUserId());
        Log.d("NavigationModeController", m.toString());
        return integer;
    }

    public final void updateCurrentInteractionMode(boolean z) {
        Context currentUserContext = getCurrentUserContext();
        this.mCurrentUserContext = currentUserContext;
        int currentInteractionMode = getCurrentInteractionMode(currentUserContext);
        this.mUiBgExecutor.execute(new NavigationModeController$$ExternalSyntheticLambda0(this, currentInteractionMode));
        Log.d("NavigationModeController", "updateCurrentInteractionMode: mode=" + currentInteractionMode);
        dumpAssetPaths(this.mCurrentUserContext);
        if (z) {
            for (int i = 0; i < this.mListeners.size(); i++) {
                this.mListeners.get(i).onNavigationModeChanged(currentInteractionMode);
            }
        }
    }
}
