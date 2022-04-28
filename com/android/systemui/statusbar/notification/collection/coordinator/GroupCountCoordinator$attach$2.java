package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener;
import com.android.systemui.statusbar.notification.collection.render.NotifGroupController;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GroupCountCoordinator.kt */
public /* synthetic */ class GroupCountCoordinator$attach$2 implements OnAfterRenderGroupListener {
    public final /* synthetic */ GroupCountCoordinator $tmp0;

    public GroupCountCoordinator$attach$2(GroupCountCoordinator groupCountCoordinator) {
        this.$tmp0 = groupCountCoordinator;
    }

    public final void onAfterRenderGroup(GroupEntry groupEntry, NotifGroupController notifGroupController) {
        GroupCountCoordinator groupCountCoordinator = this.$tmp0;
        Objects.requireNonNull(groupCountCoordinator);
        Integer num = groupCountCoordinator.untruncatedChildCounts.get(groupEntry);
        if (num != null) {
            notifGroupController.setUntruncatedChildCount(num.intValue());
            return;
        }
        throw new IllegalStateException(Intrinsics.stringPlus("No untruncated child count for group: ", groupEntry.mKey).toString());
    }
}
