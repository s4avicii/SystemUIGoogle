package com.android.systemui.statusbar.notification.row;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.Objects;

public final class ExpandableNotificationRowDragController {
    public final Context mContext;
    public final HeadsUpManager mHeadsUpManager;
    public int mIconSize;

    public void startDragAndDrop(View view) {
        ExpandableNotificationRow expandableNotificationRow;
        Drawable drawable;
        if (view instanceof ExpandableNotificationRow) {
            expandableNotificationRow = (ExpandableNotificationRow) view;
        } else {
            expandableNotificationRow = null;
        }
        Objects.requireNonNull(expandableNotificationRow);
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry);
        Notification notification = notificationEntry.mSbn.getNotification();
        PendingIntent pendingIntent = notification.contentIntent;
        if (pendingIntent == null) {
            pendingIntent = notification.fullScreenIntent;
        }
        NotificationEntry notificationEntry2 = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry2);
        String packageName = notificationEntry2.mSbn.getPackageName();
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 795136);
            if (applicationInfo != null) {
                drawable = packageManager.getApplicationIcon(applicationInfo);
            } else {
                Log.d("ExpandableNotificationRowDragController", " application info is null ");
                drawable = packageManager.getDefaultActivityIcon();
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("ExpandableNotificationRowDragController", "can not find package with : " + packageName);
            drawable = packageManager.getDefaultActivityIcon();
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        ImageView imageView = new ImageView(this.mContext);
        imageView.setImageBitmap(createBitmap);
        int i = this.mIconSize;
        imageView.layout(0, 0, i, i);
        ClipDescription clipDescription = new ClipDescription("Drag And Drop", new String[]{"application/vnd.android.activity"});
        Intent intent = new Intent();
        intent.putExtra("android.intent.extra.PENDING_INTENT", pendingIntent);
        intent.putExtra("android.intent.extra.USER", Process.myUserHandle());
        ClipData clipData = new ClipData(clipDescription, new ClipData.Item(intent));
        View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(imageView);
        view.setOnDragListener(new C1314x70846d1a(this));
        view.startDragAndDrop(clipData, dragShadowBuilder, (Object) null, 256);
    }

    public ExpandableNotificationRowDragController(Context context, HeadsUpManager headsUpManager) {
        this.mContext = context;
        this.mHeadsUpManager = headsUpManager;
        this.mIconSize = context.getResources().getDimensionPixelSize(C1777R.dimen.drag_and_drop_icon_size);
    }
}
