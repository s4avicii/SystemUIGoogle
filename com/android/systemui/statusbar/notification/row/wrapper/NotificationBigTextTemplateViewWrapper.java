package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.view.View;
import com.android.internal.widget.ImageFloatingTextView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.Objects;

public final class NotificationBigTextTemplateViewWrapper extends NotificationTemplateViewWrapper {
    public ImageFloatingTextView mBigtext;

    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        Objects.requireNonNull(expandableNotificationRow);
        Objects.requireNonNull(expandableNotificationRow.mEntry);
        this.mBigtext = this.mView.findViewById(16908820);
        super.onContentUpdated(expandableNotificationRow);
    }

    public final void updateTransformedTypes() {
        super.updateTransformedTypes();
        ImageFloatingTextView imageFloatingTextView = this.mBigtext;
        if (imageFloatingTextView != null) {
            this.mTransformationHelper.addTransformedView(2, imageFloatingTextView);
        }
    }

    public NotificationBigTextTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
    }
}
