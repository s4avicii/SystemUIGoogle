package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

public interface NotificationPresenter extends ExpandableNotificationRow.OnExpandClickListener, ActivatableNotificationView.OnActivatedListener {
}
