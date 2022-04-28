package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener;
import com.android.systemui.statusbar.notification.collection.render.NotifRowController;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RowAppearanceCoordinator.kt */
public /* synthetic */ class RowAppearanceCoordinator$attach$2 implements OnAfterRenderEntryListener {
    public final /* synthetic */ RowAppearanceCoordinator $tmp0;

    public RowAppearanceCoordinator$attach$2(RowAppearanceCoordinator rowAppearanceCoordinator) {
        this.$tmp0 = rowAppearanceCoordinator;
    }

    public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
        boolean z;
        RowAppearanceCoordinator rowAppearanceCoordinator = this.$tmp0;
        Objects.requireNonNull(rowAppearanceCoordinator);
        if (rowAppearanceCoordinator.mAlwaysExpandNonGroupedNotification || Intrinsics.areEqual(notificationEntry, rowAppearanceCoordinator.entryToExpand)) {
            z = true;
        } else {
            z = false;
        }
        notifRowController.setSystemExpanded(z);
        AssistantFeedbackController assistantFeedbackController = rowAppearanceCoordinator.mAssistantFeedbackController;
        Objects.requireNonNull(assistantFeedbackController);
        notifRowController.setFeedbackIcon(assistantFeedbackController.mIcons.get(assistantFeedbackController.getFeedbackStatus(notificationEntry)));
        Objects.requireNonNull(notificationEntry);
        notifRowController.setLastAudiblyAlertedMs(notificationEntry.mRanking.getLastAudiblyAlertedMillis());
    }
}
