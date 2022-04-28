package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.view.View;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

public final class NotificationMediaTemplateViewWrapper extends NotificationTemplateViewWrapper {
    public View mActions;

    public final boolean shouldClipToRounding(boolean z) {
        return true;
    }

    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        this.mActions = this.mView.findViewById(16909202);
        super.onContentUpdated(expandableNotificationRow);
    }

    public final void updateTransformedTypes() {
        super.updateTransformedTypes();
        View view = this.mActions;
        if (view != null) {
            this.mTransformationHelper.addTransformedView(5, view);
        }
    }

    public NotificationMediaTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
    }
}
