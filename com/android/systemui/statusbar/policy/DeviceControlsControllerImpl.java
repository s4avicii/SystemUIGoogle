package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.Log;
import com.android.keyguard.KeyguardStatusView$$ExternalSyntheticLambda0;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.statusbar.policy.DeviceControlsController;
import com.android.systemui.util.settings.SecureSettings;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeviceControlsControllerImpl.kt */
public final class DeviceControlsControllerImpl implements DeviceControlsController {
    public DeviceControlsController.Callback callback;
    public final Context context;
    public final ControlsComponent controlsComponent;
    public final DeviceControlsControllerImpl$listingCallback$1 listingCallback = new DeviceControlsControllerImpl$listingCallback$1(this);
    public Integer position;
    public final SecureSettings secureSettings;
    public final UserContextProvider userContextProvider;

    public final void removeCallback() {
        this.position = null;
        this.callback = null;
        this.controlsComponent.getControlsListingController().ifPresent(new DeviceControlsControllerImpl$removeCallback$1(this));
    }

    public final void fireControlsUpdate() {
        Log.i("DeviceControlsControllerImpl", Intrinsics.stringPlus("Setting DeviceControlsTile position: ", this.position));
        DeviceControlsController.Callback callback2 = this.callback;
        if (callback2 != null) {
            Integer num = this.position;
            AutoTileManager.C14024 r0 = (AutoTileManager.C14024) callback2;
            if (!AutoTileManager.this.mAutoTracker.isAdded("controls")) {
                if (num != null) {
                    AutoTileManager.this.mHost.addTile("controls", num.intValue());
                }
                AutoTileManager.this.mAutoTracker.setTileAdded("controls");
                AutoTileManager.this.mHandler.post(new KeyguardStatusView$$ExternalSyntheticLambda0(r0, 3));
            }
        }
    }

    public DeviceControlsControllerImpl(Context context2, ControlsComponent controlsComponent2, UserContextProvider userContextProvider2, SecureSettings secureSettings2) {
        this.context = context2;
        this.controlsComponent = controlsComponent2;
        this.userContextProvider = userContextProvider2;
        this.secureSettings = secureSettings2;
    }

    public final void setCallback(AutoTileManager.C14024 r3) {
        removeCallback();
        this.callback = r3;
        if (this.secureSettings.getInt("controls_enabled", 1) == 0) {
            fireControlsUpdate();
            return;
        }
        this.controlsComponent.getControlsController().ifPresent(new DeviceControlsControllerImpl$checkMigrationToQs$1(this));
        this.controlsComponent.getControlsListingController().ifPresent(new DeviceControlsControllerImpl$setCallback$1(this));
    }
}
