package com.android.systemui.classifier;

import android.content.Context;
import android.net.Uri;
import android.provider.DeviceConfig;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.FalsingPlugin;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.util.DeviceConfigProxy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class FalsingManagerProxy implements FalsingManager, Dumpable {
    public final Provider<BrightLineFalsingManager> mBrightLineFalsingManagerProvider;
    public final DeviceConfigProxy mDeviceConfig;
    public final FalsingManagerProxy$$ExternalSyntheticLambda0 mDeviceConfigListener;
    public final DumpManager mDumpManager;
    public FalsingManager mInternalFalsingManager;
    public final C07231 mPluginListener;
    public final PluginManager mPluginManager;

    public final void addFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        this.mInternalFalsingManager.addFalsingBeliefListener(falsingBeliefListener);
    }

    public final void addTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        this.mInternalFalsingManager.addTapListener(falsingTapListener);
    }

    public final void cleanupInternal() {
        DeviceConfigProxy deviceConfigProxy = this.mDeviceConfig;
        FalsingManagerProxy$$ExternalSyntheticLambda0 falsingManagerProxy$$ExternalSyntheticLambda0 = this.mDeviceConfigListener;
        Objects.requireNonNull(deviceConfigProxy);
        DeviceConfig.removeOnPropertiesChangedListener(falsingManagerProxy$$ExternalSyntheticLambda0);
        this.mPluginManager.removePluginListener(this.mPluginListener);
        this.mDumpManager.unregisterDumpable("FalsingManager");
        this.mInternalFalsingManager.cleanupInternal();
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mInternalFalsingManager.dump(fileDescriptor, printWriter, strArr);
    }

    public final boolean isClassifierEnabled() {
        return this.mInternalFalsingManager.isClassifierEnabled();
    }

    public final boolean isFalseDoubleTap() {
        return this.mInternalFalsingManager.isFalseDoubleTap();
    }

    public final boolean isFalseTap(int i) {
        return this.mInternalFalsingManager.isFalseTap(i);
    }

    public final boolean isFalseTouch(int i) {
        return this.mInternalFalsingManager.isFalseTouch(i);
    }

    public final boolean isReportingEnabled() {
        return this.mInternalFalsingManager.isReportingEnabled();
    }

    public final boolean isSimpleTap() {
        return this.mInternalFalsingManager.isSimpleTap();
    }

    public final boolean isUnlockingDisabled() {
        return this.mInternalFalsingManager.isUnlockingDisabled();
    }

    public final void onProximityEvent(FalsingManager.ProximityEvent proximityEvent) {
        this.mInternalFalsingManager.onProximityEvent(proximityEvent);
    }

    public final void onSuccessfulUnlock() {
        this.mInternalFalsingManager.onSuccessfulUnlock();
    }

    public final void removeFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        this.mInternalFalsingManager.removeFalsingBeliefListener(falsingBeliefListener);
    }

    public final void removeTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        this.mInternalFalsingManager.removeTapListener(falsingTapListener);
    }

    public final Uri reportRejectedTouch() {
        return this.mInternalFalsingManager.reportRejectedTouch();
    }

    public final void setupFalsingManager() {
        FalsingManager falsingManager = this.mInternalFalsingManager;
        if (falsingManager != null) {
            falsingManager.cleanupInternal();
        }
        this.mInternalFalsingManager = this.mBrightLineFalsingManagerProvider.get();
    }

    public final boolean shouldEnforceBouncer() {
        return this.mInternalFalsingManager.shouldEnforceBouncer();
    }

    public FalsingManagerProxy(PluginManager pluginManager, Executor executor, DeviceConfigProxy deviceConfigProxy, DumpManager dumpManager, Provider<BrightLineFalsingManager> provider) {
        FalsingManagerProxy$$ExternalSyntheticLambda0 falsingManagerProxy$$ExternalSyntheticLambda0 = new FalsingManagerProxy$$ExternalSyntheticLambda0(this);
        this.mDeviceConfigListener = falsingManagerProxy$$ExternalSyntheticLambda0;
        this.mPluginManager = pluginManager;
        this.mDumpManager = dumpManager;
        this.mDeviceConfig = deviceConfigProxy;
        this.mBrightLineFalsingManagerProvider = provider;
        setupFalsingManager();
        Objects.requireNonNull(deviceConfigProxy);
        DeviceConfig.addOnPropertiesChangedListener("systemui", executor, falsingManagerProxy$$ExternalSyntheticLambda0);
        C07231 r3 = new PluginListener<FalsingPlugin>() {
            public final void onPluginConnected(Plugin plugin, Context context) {
                FalsingManager falsingManager = ((FalsingPlugin) plugin).getFalsingManager(context);
                if (falsingManager != null) {
                    FalsingManagerProxy.this.mInternalFalsingManager.cleanupInternal();
                    FalsingManagerProxy.this.mInternalFalsingManager = falsingManager;
                }
            }

            public final void onPluginDisconnected(Plugin plugin) {
                FalsingPlugin falsingPlugin = (FalsingPlugin) plugin;
                FalsingManagerProxy.this.setupFalsingManager();
            }
        };
        this.mPluginListener = r3;
        pluginManager.addPluginListener(r3, FalsingPlugin.class);
        dumpManager.registerDumpable("FalsingManager", this);
    }
}
