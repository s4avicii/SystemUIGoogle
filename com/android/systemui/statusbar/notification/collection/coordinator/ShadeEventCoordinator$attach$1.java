package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import java.util.List;
import java.util.Objects;

/* compiled from: ShadeEventCoordinator.kt */
public /* synthetic */ class ShadeEventCoordinator$attach$1 implements OnBeforeRenderListListener {
    public final /* synthetic */ ShadeEventCoordinator $tmp0;

    public ShadeEventCoordinator$attach$1(ShadeEventCoordinator shadeEventCoordinator) {
        this.$tmp0 = shadeEventCoordinator;
    }

    public final void onBeforeRenderList(List<? extends ListEntry> list) {
        ShadeEventCoordinator shadeEventCoordinator = this.$tmp0;
        Objects.requireNonNull(shadeEventCoordinator);
        LogLevel logLevel = LogLevel.DEBUG;
        if (shadeEventCoordinator.mEntryRemoved && list.isEmpty()) {
            ShadeEventCoordinatorLogger shadeEventCoordinatorLogger = shadeEventCoordinator.mLogger;
            Objects.requireNonNull(shadeEventCoordinatorLogger);
            LogBuffer logBuffer = shadeEventCoordinatorLogger.buffer;
            ShadeEventCoordinatorLogger$logShadeEmptied$2 shadeEventCoordinatorLogger$logShadeEmptied$2 = ShadeEventCoordinatorLogger$logShadeEmptied$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                logBuffer.push(logBuffer.obtain("ShadeEventCoordinator", logLevel, shadeEventCoordinatorLogger$logShadeEmptied$2));
            }
            Runnable runnable = shadeEventCoordinator.mShadeEmptiedCallback;
            if (runnable != null) {
                shadeEventCoordinator.mMainExecutor.execute(runnable);
            }
        }
        if (shadeEventCoordinator.mEntryRemoved && shadeEventCoordinator.mEntryRemovedByUser) {
            ShadeEventCoordinatorLogger shadeEventCoordinatorLogger2 = shadeEventCoordinator.mLogger;
            Objects.requireNonNull(shadeEventCoordinatorLogger2);
            LogBuffer logBuffer2 = shadeEventCoordinatorLogger2.buffer;
            ShadeEventCoordinatorLogger$logNotifRemovedByUser$2 shadeEventCoordinatorLogger$logNotifRemovedByUser$2 = ShadeEventCoordinatorLogger$logNotifRemovedByUser$2.INSTANCE;
            Objects.requireNonNull(logBuffer2);
            if (!logBuffer2.frozen) {
                logBuffer2.push(logBuffer2.obtain("ShadeEventCoordinator", logLevel, shadeEventCoordinatorLogger$logNotifRemovedByUser$2));
            }
            Runnable runnable2 = shadeEventCoordinator.mNotifRemovedByUserCallback;
            if (runnable2 != null) {
                shadeEventCoordinator.mMainExecutor.execute(runnable2);
            }
        }
        shadeEventCoordinator.mEntryRemoved = false;
        shadeEventCoordinator.mEntryRemovedByUser = false;
    }
}
