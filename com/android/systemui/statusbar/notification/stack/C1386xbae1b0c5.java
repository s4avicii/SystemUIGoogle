package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.Objects;
import java.util.function.BiConsumer;

/* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1386xbae1b0c5 implements BiConsumer {
    public final /* synthetic */ NotificationRoundnessManager f$0;

    public /* synthetic */ C1386xbae1b0c5(NotificationRoundnessManager notificationRoundnessManager) {
        this.f$0 = notificationRoundnessManager;
    }

    public final void accept(Object obj, Object obj2) {
        boolean z;
        NotificationRoundnessManager notificationRoundnessManager = this.f$0;
        float floatValue = ((Float) obj).floatValue();
        float floatValue2 = ((Float) obj2).floatValue();
        Objects.requireNonNull(notificationRoundnessManager);
        if (floatValue != 0.0f) {
            z = true;
        } else {
            z = false;
        }
        notificationRoundnessManager.mExpanded = z;
        notificationRoundnessManager.mAppearFraction = floatValue2;
        ExpandableNotificationRow expandableNotificationRow = notificationRoundnessManager.mTrackedHeadsUp;
        if (expandableNotificationRow != null) {
            notificationRoundnessManager.updateView(expandableNotificationRow, false);
        }
    }
}
