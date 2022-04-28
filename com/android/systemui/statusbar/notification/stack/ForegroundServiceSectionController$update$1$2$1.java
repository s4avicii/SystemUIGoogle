package com.android.systemui.statusbar.notification.stack;

import android.view.View;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.DungeonRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.util.Assert;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ForegroundServiceSectionController.kt */
public final class ForegroundServiceSectionController$update$1$2$1 implements View.OnClickListener {
    public final /* synthetic */ DungeonRow $child;
    public final /* synthetic */ NotificationEntry $entry;
    public final /* synthetic */ ForegroundServiceSectionController this$0;

    public ForegroundServiceSectionController$update$1$2$1(ForegroundServiceSectionController foregroundServiceSectionController, DungeonRow dungeonRow, NotificationEntry notificationEntry) {
        this.this$0 = foregroundServiceSectionController;
        this.$child = dungeonRow;
        this.$entry = notificationEntry;
    }

    public final void onClick(View view) {
        ForegroundServiceSectionController foregroundServiceSectionController = this.this$0;
        DungeonRow dungeonRow = this.$child;
        Objects.requireNonNull(dungeonRow);
        NotificationEntry notificationEntry = dungeonRow.entry;
        Intrinsics.checkNotNull(notificationEntry);
        Objects.requireNonNull(foregroundServiceSectionController);
        Assert.isMainThread();
        foregroundServiceSectionController.entries.remove(notificationEntry);
        this.this$0.update();
        NotificationEntry notificationEntry2 = this.$entry;
        Objects.requireNonNull(notificationEntry2);
        ExpandableNotificationRow expandableNotificationRow = notificationEntry2.row;
        Objects.requireNonNull(expandableNotificationRow);
        expandableNotificationRow.mDismissed = false;
        NotificationEntry notificationEntry3 = this.$entry;
        Objects.requireNonNull(notificationEntry3);
        notificationEntry3.row.resetTranslation();
        ForegroundServiceSectionController foregroundServiceSectionController2 = this.this$0;
        Objects.requireNonNull(foregroundServiceSectionController2);
        foregroundServiceSectionController2.entryManager.updateNotifications("ForegroundServiceSectionController.onClick");
    }
}
