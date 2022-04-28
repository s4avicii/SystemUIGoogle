package com.android.systemui.statusbar.core;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.google.android.systemui.communal.dock.conditions.TimeoutToUserZeroFeatureCondition;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class StatusBarInitializer_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider windowControllerProvider;

    public /* synthetic */ StatusBarInitializer_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.windowControllerProvider = provider;
    }

    public final Object get() {
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager;
        switch (this.$r8$classId) {
            case 0:
                return new StatusBarInitializer((StatusBarWindowController) this.windowControllerProvider.get());
            case 1:
                InputMethodManager inputMethodManager = (InputMethodManager) ((Context) this.windowControllerProvider.get()).getSystemService(InputMethodManager.class);
                Objects.requireNonNull(inputMethodManager, "Cannot return null from a non-@Nullable @Provides method");
                return inputMethodManager;
            case 2:
                return new ShadeEventCoordinatorLogger((LogBuffer) this.windowControllerProvider.get());
            case 3:
                NodeController nodeController = ((SectionHeaderControllerSubcomponent) this.windowControllerProvider.get()).getNodeController();
                Objects.requireNonNull(nodeController, "Cannot return null from a non-@Nullable @Provides method");
                return nodeController;
            case 4:
                View findViewById = ((PhoneStatusBarView) this.windowControllerProvider.get()).findViewById(C1777R.C1779id.operator_name);
                Objects.requireNonNull(findViewById, "Cannot return null from a non-@Nullable @Provides method");
                return findViewById;
            case 5:
                Context context = (Context) this.windowControllerProvider.get();
                synchronized (DeviceStateRotationLockSettingsManager.class) {
                    if (DeviceStateRotationLockSettingsManager.sSingleton == null) {
                        DeviceStateRotationLockSettingsManager.sSingleton = new DeviceStateRotationLockSettingsManager(context.getApplicationContext());
                    }
                    deviceStateRotationLockSettingsManager = DeviceStateRotationLockSettingsManager.sSingleton;
                }
                Objects.requireNonNull(deviceStateRotationLockSettingsManager, "Cannot return null from a non-@Nullable @Provides method");
                return deviceStateRotationLockSettingsManager;
            default:
                return new TimeoutToUserZeroFeatureCondition(((Boolean) this.windowControllerProvider.get()).booleanValue());
        }
    }
}
