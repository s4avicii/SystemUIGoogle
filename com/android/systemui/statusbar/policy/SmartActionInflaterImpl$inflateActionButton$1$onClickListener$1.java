package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.PendingIntent;
import android.os.RemoteException;
import android.view.View;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationClickNotifier$onNotificationActionClick$1;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.Objects;

/* compiled from: SmartReplyStateInflater.kt */
public final class SmartActionInflaterImpl$inflateActionButton$1$onClickListener$1 implements View.OnClickListener {
    public final /* synthetic */ Notification.Action $action;
    public final /* synthetic */ int $actionIndex;
    public final /* synthetic */ NotificationEntry $entry;
    public final /* synthetic */ SmartReplyView.SmartActions $smartActions;
    public final /* synthetic */ SmartActionInflaterImpl this$0;

    public SmartActionInflaterImpl$inflateActionButton$1$onClickListener$1(SmartActionInflaterImpl smartActionInflaterImpl, NotificationEntry notificationEntry, SmartReplyView.SmartActions smartActions, int i, Notification.Action action) {
        this.this$0 = smartActionInflaterImpl;
        this.$entry = notificationEntry;
        this.$smartActions = smartActions;
        this.$actionIndex = i;
        this.$action = action;
    }

    public final void onClick(View view) {
        SmartActionInflaterImpl smartActionInflaterImpl = this.this$0;
        NotificationEntry notificationEntry = this.$entry;
        SmartReplyView.SmartActions smartActions = this.$smartActions;
        int i = this.$actionIndex;
        Notification.Action action = this.$action;
        Objects.requireNonNull(smartActionInflaterImpl);
        if (!smartActions.fromAssistant || 11 != action.getSemanticAction()) {
            ActivityStarter activityStarter = smartActionInflaterImpl.activityStarter;
            PendingIntent pendingIntent = action.actionIntent;
            Objects.requireNonNull(notificationEntry);
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            SmartActionInflaterImpl$onSmartActionClick$1 smartActionInflaterImpl$onSmartActionClick$1 = new SmartActionInflaterImpl$onSmartActionClick$1(smartActionInflaterImpl, notificationEntry, i, action, smartActions);
            boolean z = SmartReplyStateInflaterKt.DEBUG;
            activityStarter.startPendingIntentDismissingKeyguard(pendingIntent, (Runnable) new SmartReplyStateInflaterKt$startPendingIntentDismissingKeyguard$1(smartActionInflaterImpl$onSmartActionClick$1), (View) expandableNotificationRow);
            return;
        }
        Objects.requireNonNull(notificationEntry);
        ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
        expandableNotificationRow2.doSmartActionClick(((int) expandableNotificationRow2.getX()) / 2, ((int) notificationEntry.row.getY()) / 2);
        SmartReplyController smartReplyController = smartActionInflaterImpl.smartReplyController;
        boolean z2 = smartActions.fromAssistant;
        Objects.requireNonNull(smartReplyController);
        NotificationVisibility obtain = smartReplyController.mVisibilityProvider.obtain(notificationEntry, true);
        NotificationClickNotifier notificationClickNotifier = smartReplyController.mClickNotifier;
        String str = notificationEntry.mKey;
        Objects.requireNonNull(notificationClickNotifier);
        try {
            notificationClickNotifier.barService.onNotificationActionClick(str, i, action, obtain, z2);
        } catch (RemoteException unused) {
        }
        notificationClickNotifier.mainExecutor.execute(new NotificationClickNotifier$onNotificationActionClick$1(notificationClickNotifier, str));
    }
}
