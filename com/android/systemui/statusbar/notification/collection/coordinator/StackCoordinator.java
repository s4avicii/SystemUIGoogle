package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.render.NotifStats;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StackCoordinator.kt */
public final class StackCoordinator implements Coordinator {
    public final NotificationIconAreaController notificationIconAreaController;

    public final void attach(NotifPipeline notifPipeline) {
        StackCoordinator$attach$1 stackCoordinator$attach$1 = new StackCoordinator$attach$1(this);
        Objects.requireNonNull(notifPipeline);
        RenderStageManager renderStageManager = notifPipeline.mRenderStageManager;
        Objects.requireNonNull(renderStageManager);
        renderStageManager.onAfterRenderListListeners.add(stackCoordinator$attach$1);
    }

    public StackCoordinator(NotificationIconAreaController notificationIconAreaController2) {
        this.notificationIconAreaController = notificationIconAreaController2;
    }

    public static NotifStats calculateNotifStats(List list) {
        boolean z;
        Iterator it = list.iterator();
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            NotifSection section = listEntry.getSection();
            if (section != null) {
                NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
                if (representativeEntry != null) {
                    if (section.bucket == 6) {
                        z = true;
                    } else {
                        z = false;
                    }
                    boolean isClearable = representativeEntry.isClearable();
                    if (z && isClearable) {
                        z5 = true;
                    } else if (z && !isClearable) {
                        z4 = true;
                    } else if (!z && isClearable) {
                        z3 = true;
                    } else if (!z && !isClearable) {
                        z2 = true;
                    }
                } else {
                    throw new IllegalStateException(Intrinsics.stringPlus("Null notif entry for ", listEntry.getKey()).toString());
                }
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Null section for ", listEntry.getKey()).toString());
            }
        }
        return new NotifStats(list.size(), z2, z3, z4, z5);
    }
}
