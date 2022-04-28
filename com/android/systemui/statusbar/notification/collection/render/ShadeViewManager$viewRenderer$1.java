package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ShadeViewManager.kt */
public final class ShadeViewManager$viewRenderer$1 implements NotifViewRenderer {
    public final /* synthetic */ ShadeViewManager this$0;

    public final void onDispatchComplete() {
    }

    public ShadeViewManager$viewRenderer$1(ShadeViewManager shadeViewManager) {
        this.this$0 = shadeViewManager;
    }

    public final NotifViewController getGroupController(GroupEntry groupEntry) {
        NotifViewBarn notifViewBarn = this.this$0.viewBarn;
        NotificationEntry notificationEntry = groupEntry.mSummary;
        if (notificationEntry != null) {
            Objects.requireNonNull(notifViewBarn);
            NotifViewController notifViewController = (NotifViewController) notifViewBarn.rowMap.get(notificationEntry.mKey);
            if (notifViewController != null) {
                return notifViewController;
            }
            throw new IllegalStateException(Intrinsics.stringPlus("No view has been registered for entry: ", notificationEntry.mKey).toString());
        }
        throw new IllegalStateException(Intrinsics.stringPlus("No Summary: ", groupEntry).toString());
    }

    public final NotifViewController getRowController(NotificationEntry notificationEntry) {
        NotifViewBarn notifViewBarn = this.this$0.viewBarn;
        Objects.requireNonNull(notifViewBarn);
        NotifViewController notifViewController = (NotifViewController) notifViewBarn.rowMap.get(notificationEntry.mKey);
        if (notifViewController != null) {
            return notifViewController;
        }
        throw new IllegalStateException(Intrinsics.stringPlus("No view has been registered for entry: ", notificationEntry.mKey).toString());
    }

    public final NotifStackController getStackController() {
        return this.this$0.stackController;
    }

    public final void onRenderList(List<? extends ListEntry> list) {
        ShadeViewManager shadeViewManager = this.this$0;
        Trace.beginSection("ShadeViewManager.onRenderList");
        try {
            shadeViewManager.viewDiffer.applySpec(shadeViewManager.specBuilder.buildNodeSpec(shadeViewManager.rootController, list));
        } finally {
            Trace.endSection();
        }
    }
}
