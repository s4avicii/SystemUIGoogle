package com.android.systemui.controls.p004ui;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.hdmi.HdmiCecSetMenuLanguageHelper;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.settings.SecureSettings;
import com.google.android.systemui.columbus.gates.PowerState;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* renamed from: com.android.systemui.controls.ui.ControlsActivity_Factory */
public final class ControlsActivity_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider broadcastDispatcherProvider;
    public final Provider uiControllerProvider;

    public /* synthetic */ ControlsActivity_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.uiControllerProvider = provider;
        this.broadcastDispatcherProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new ControlsActivity((ControlsUiController) this.uiControllerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
            case 1:
                return new HdmiCecSetMenuLanguageHelper((Executor) this.uiControllerProvider.get(), (SecureSettings) this.broadcastDispatcherProvider.get());
            case 2:
                return new ExpandableNotificationRowDragController((Context) this.uiControllerProvider.get(), (HeadsUpManager) this.broadcastDispatcherProvider.get());
            default:
                return new PowerState((Context) this.uiControllerProvider.get(), DoubleCheck.lazy(this.broadcastDispatcherProvider));
        }
    }
}
