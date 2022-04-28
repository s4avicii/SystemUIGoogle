package com.android.systemui.keyguard;

import android.app.StatsManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import com.android.internal.util.Preconditions;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.dreams.DreamOverlayContainerView;
import com.android.systemui.dreams.DreamOverlayStatusBarView;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.p006qs.QuickStatusBarHeader;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleSpaceWidgetPinnedReceiver;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.stack.StackStateLogger;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.google.android.systemui.autorotate.DataLogger;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class ScreenLifecycle_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Object dumpManagerProvider;

    public /* synthetic */ ScreenLifecycle_Factory(Object obj, int i) {
        this.$r8$classId = i;
        this.dumpManagerProvider = obj;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new ScreenLifecycle((DumpManager) ((Provider) this.dumpManagerProvider).get());
            case 1:
                DreamOverlayStatusBarView dreamOverlayStatusBarView = (DreamOverlayStatusBarView) Preconditions.checkNotNull((DreamOverlayStatusBarView) ((DreamOverlayContainerView) ((Provider) this.dumpManagerProvider).get()).findViewById(C1777R.C1779id.dream_overlay_status_bar), "R.id.status_bar must not be null");
                Objects.requireNonNull(dreamOverlayStatusBarView, "Cannot return null from a non-@Nullable @Provides method");
                return dreamOverlayStatusBarView;
            case 2:
                return new PeopleSpaceWidgetPinnedReceiver((PeopleSpaceWidgetManager) ((Provider) this.dumpManagerProvider).get());
            case 3:
                StatusIconContainer statusIconContainer = (StatusIconContainer) ((QuickStatusBarHeader) ((Provider) this.dumpManagerProvider).get()).findViewById(C1777R.C1779id.statusIcons);
                Objects.requireNonNull(statusIconContainer, "Cannot return null from a non-@Nullable @Provides method");
                return statusIconContainer;
            case 4:
                return new StackStateLogger((LogBuffer) ((Provider) this.dumpManagerProvider).get());
            case 5:
                BatteryMeterView batteryMeterView = (BatteryMeterView) ((View) ((Provider) this.dumpManagerProvider).get()).findViewById(C1777R.C1779id.batteryRemainingIcon);
                Objects.requireNonNull(batteryMeterView, "Cannot return null from a non-@Nullable @Provides method");
                return batteryMeterView;
            case FalsingManager.VERSION /*6*/:
                return new DataLogger((StatsManager) ((Provider) this.dumpManagerProvider).get());
            default:
                Objects.requireNonNull((DependencyProvider) this.dumpManagerProvider);
                HandlerThread handlerThread = new HandlerThread("TimeTick");
                handlerThread.start();
                return new Handler(handlerThread.getLooper());
        }
    }
}
