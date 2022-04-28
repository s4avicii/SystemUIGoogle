package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArrayMap;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import java.util.Objects;

/* compiled from: GroupCountCoordinator.kt */
public final class GroupCountCoordinator implements Coordinator {
    public final ArrayMap<GroupEntry, Integer> untruncatedChildCounts = new ArrayMap<>();

    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeFinalizeFilterListener(new GroupCountCoordinator$attach$1(this));
        GroupCountCoordinator$attach$2 groupCountCoordinator$attach$2 = new GroupCountCoordinator$attach$2(this);
        RenderStageManager renderStageManager = notifPipeline.mRenderStageManager;
        Objects.requireNonNull(renderStageManager);
        renderStageManager.onAfterRenderGroupListeners.add(groupCountCoordinator$attach$2);
    }
}
