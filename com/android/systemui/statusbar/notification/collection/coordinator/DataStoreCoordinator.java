package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import java.util.Objects;

/* compiled from: DataStoreCoordinator.kt */
public final class DataStoreCoordinator implements Coordinator {
    public final NotifLiveDataStoreImpl notifLiveDataStoreImpl;

    public final void attach(NotifPipeline notifPipeline) {
        DataStoreCoordinator$attach$1 dataStoreCoordinator$attach$1 = new DataStoreCoordinator$attach$1(this);
        Objects.requireNonNull(notifPipeline);
        RenderStageManager renderStageManager = notifPipeline.mRenderStageManager;
        Objects.requireNonNull(renderStageManager);
        renderStageManager.onAfterRenderListListeners.add(dataStoreCoordinator$attach$1);
    }

    public DataStoreCoordinator(NotifLiveDataStoreImpl notifLiveDataStoreImpl2) {
        this.notifLiveDataStoreImpl = notifLiveDataStoreImpl2;
    }
}
