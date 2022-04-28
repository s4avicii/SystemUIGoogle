package com.android.systemui.statusbar.notification.row;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleSpaceWidgetPinnedReceiver;
import com.android.systemui.people.widget.PeopleSpaceWidgetProvider;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationConversationInfo$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ NotificationConversationInfo f$0;

    public /* synthetic */ NotificationConversationInfo$$ExternalSyntheticLambda0(NotificationConversationInfo notificationConversationInfo) {
        this.f$0 = notificationConversationInfo;
    }

    public final void onClick(View view) {
        NotificationConversationInfo notificationConversationInfo = this.f$0;
        int i = NotificationConversationInfo.$r8$clinit;
        Objects.requireNonNull(notificationConversationInfo);
        notificationConversationInfo.mPressedApply = true;
        if (notificationConversationInfo.mSelectedAction == 2 && notificationConversationInfo.getPriority() != notificationConversationInfo.mSelectedAction) {
            notificationConversationInfo.mShadeController.animateCollapsePanels();
            PeopleSpaceWidgetManager peopleSpaceWidgetManager = notificationConversationInfo.mPeopleSpaceWidgetManager;
            ShortcutInfo shortcutInfo = notificationConversationInfo.mShortcutInfo;
            Bundle bundle = new Bundle();
            Objects.requireNonNull(peopleSpaceWidgetManager);
            RemoteViews preview = peopleSpaceWidgetManager.getPreview(shortcutInfo.getId(), shortcutInfo.getUserHandle(), shortcutInfo.getPackage(), bundle);
            if (preview == null) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Skipping pinning widget: no tile for shortcutId: ");
                m.append(shortcutInfo.getId());
                Log.w("PeopleSpaceWidgetMgr", m.toString());
            } else {
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("appWidgetPreview", preview);
                Context context = peopleSpaceWidgetManager.mContext;
                int i2 = PeopleSpaceWidgetPinnedReceiver.$r8$clinit;
                Intent addFlags = new Intent(context, PeopleSpaceWidgetPinnedReceiver.class).addFlags(268435456);
                addFlags.putExtra("android.intent.extra.shortcut.ID", shortcutInfo.getId());
                addFlags.putExtra("android.intent.extra.USER_ID", shortcutInfo.getUserId());
                addFlags.putExtra("android.intent.extra.PACKAGE_NAME", shortcutInfo.getPackage());
                PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, addFlags, 167772160);
                peopleSpaceWidgetManager.mAppWidgetManager.requestPinAppWidget(new ComponentName(peopleSpaceWidgetManager.mContext, PeopleSpaceWidgetProvider.class), bundle2, broadcast);
            }
        }
        notificationConversationInfo.mGutsContainer.closeControls(view, true);
    }
}
