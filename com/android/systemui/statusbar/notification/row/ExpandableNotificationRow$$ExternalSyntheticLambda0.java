package com.android.systemui.statusbar.notification.row;

import android.view.View;
import android.view.ViewStub;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ExpandableNotificationRow$$ExternalSyntheticLambda0 implements ViewStub.OnInflateListener {
    public final /* synthetic */ ExpandableNotificationRow f$0;

    public /* synthetic */ ExpandableNotificationRow$$ExternalSyntheticLambda0(ExpandableNotificationRow expandableNotificationRow) {
        this.f$0 = expandableNotificationRow;
    }

    public final void onInflate(ViewStub viewStub, View view) {
        ExpandableNotificationRow expandableNotificationRow = this.f$0;
        ExpandableNotificationRow.C13092 r1 = ExpandableNotificationRow.TRANSLATE_CONTENT;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationGuts notificationGuts = (NotificationGuts) view;
        expandableNotificationRow.mGuts = notificationGuts;
        int i = expandableNotificationRow.mClipTopAmount;
        Objects.requireNonNull(notificationGuts);
        notificationGuts.mClipTopAmount = i;
        notificationGuts.invalidate();
        NotificationGuts notificationGuts2 = expandableNotificationRow.mGuts;
        int i2 = expandableNotificationRow.mActualHeight;
        Objects.requireNonNull(notificationGuts2);
        notificationGuts2.mActualHeight = i2;
        notificationGuts2.invalidate();
        expandableNotificationRow.mGutsStub = null;
    }
}
