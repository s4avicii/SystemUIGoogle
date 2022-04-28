package com.google.android.systemui.columbus;

import android.content.Context;
import com.android.systemui.media.MediaFlags;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManagerFactory;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.legacy.LowPriorityInflationHelper;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ColumbusStructuredDataManager_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider bgExecutorProvider;
    public final Provider contextProvider;
    public final Provider userTrackerProvider;

    public /* synthetic */ ColumbusStructuredDataManager_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
        this.bgExecutorProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new ColumbusStructuredDataManager((Context) this.contextProvider.get(), (UserTracker) this.userTrackerProvider.get(), (Executor) this.bgExecutorProvider.get());
            case 1:
                return new MediaMuteAwaitConnectionManagerFactory((MediaFlags) this.contextProvider.get(), (Context) this.userTrackerProvider.get(), (Executor) this.bgExecutorProvider.get());
            default:
                return new LowPriorityInflationHelper((NotificationGroupManagerLegacy) this.contextProvider.get(), (RowContentBindStage) this.userTrackerProvider.get(), (NotifPipelineFlags) this.bgExecutorProvider.get());
        }
    }
}
