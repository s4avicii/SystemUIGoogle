package com.android.systemui.statusbar.notification.row.wrapper;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Pools;
import android.view.View;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.ImageTransformState;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.Objects;

public final class NotificationBigPictureTemplateViewWrapper extends NotificationTemplateViewWrapper {
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        Bitmap bitmap;
        super.onContentUpdated(expandableNotificationRow);
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry);
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        Bundle bundle = statusBarNotification.getNotification().extras;
        if (bundle.containsKey("android.largeIcon.big")) {
            Icon icon = (Icon) bundle.getParcelable("android.largeIcon.big");
            ImageView imageView = this.mRightIcon;
            Pools.SimplePool<ImageTransformState> simplePool = ImageTransformState.sInstancePool;
            imageView.setTag(C1777R.C1779id.image_icon_tag, icon);
            this.mLeftIcon.setTag(C1777R.C1779id.image_icon_tag, icon);
            return;
        }
        ImageView imageView2 = this.mRightIcon;
        Pools.SimplePool<ImageTransformState> simplePool2 = ImageTransformState.sInstancePool;
        Notification notification = statusBarNotification.getNotification();
        Icon largeIcon = notification.getLargeIcon();
        if (largeIcon == null && (bitmap = notification.largeIcon) != null) {
            largeIcon = Icon.createWithBitmap(bitmap);
        }
        imageView2.setTag(C1777R.C1779id.image_icon_tag, largeIcon);
    }

    public NotificationBigPictureTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
    }
}
