package com.android.keyguard;

import android.app.ActivityManager;
import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.model.SysUiState;
import com.android.systemui.p006qs.external.CustomTileStatePersister;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.NotificationShadeWindowView;
import com.google.android.systemui.assist.OpaEnabledSettings;
import com.google.android.systemui.columbus.sensors.config.SensorConfiguration;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class LiftToActivateListener_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider accessibilityManagerProvider;

    public /* synthetic */ LiftToActivateListener_Factory(Provider provider, int i) {
        this.$r8$classId = i;
        this.accessibilityManagerProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new LiftToActivateListener((AccessibilityManager) this.accessibilityManagerProvider.get());
            case 1:
                ActivityManager activityManager = (ActivityManager) ((Context) this.accessibilityManagerProvider.get()).getSystemService(ActivityManager.class);
                Objects.requireNonNull(activityManager, "Cannot return null from a non-@Nullable @Provides method");
                return activityManager;
            case 2:
                SysUiState sysUiState = new SysUiState();
                ((DumpManager) this.accessibilityManagerProvider.get()).registerDumpable(sysUiState);
                return sysUiState;
            case 3:
                return new CustomTileStatePersister((Context) this.accessibilityManagerProvider.get());
            case 4:
                return new NotificationRoundnessManager((NotificationSectionsFeatureManager) this.accessibilityManagerProvider.get());
            case 5:
                NotificationStackScrollLayout notificationStackScrollLayout = (NotificationStackScrollLayout) ((NotificationShadeWindowView) this.accessibilityManagerProvider.get()).findViewById(C1777R.C1779id.notification_stack_scroller);
                Objects.requireNonNull(notificationStackScrollLayout, "Cannot return null from a non-@Nullable @Provides method");
                return notificationStackScrollLayout;
            case FalsingManager.VERSION /*6*/:
                Optional map = ((Optional) this.accessibilityManagerProvider.get()).map(WMShellBaseModule$$ExternalSyntheticLambda0.INSTANCE);
                Objects.requireNonNull(map, "Cannot return null from a non-@Nullable @Provides method");
                return map;
            case 7:
                return new OpaEnabledSettings((Context) this.accessibilityManagerProvider.get());
            default:
                return new SensorConfiguration((Context) this.accessibilityManagerProvider.get());
        }
    }
}
