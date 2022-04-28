package com.android.systemui.media;

import android.app.Notification;
import android.app.PendingIntent;
import android.util.Log;
import com.android.systemui.plugins.ActivityStarter;
import java.util.Objects;

/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$createActionsFromNotification$runnable$1 implements Runnable {
    public final /* synthetic */ Notification.Action $action;
    public final /* synthetic */ MediaDataManager this$0;

    public MediaDataManager$createActionsFromNotification$runnable$1(Notification.Action action, MediaDataManager mediaDataManager) {
        this.$action = action;
        this.this$0 = mediaDataManager;
    }

    public final void run() {
        if (this.$action.actionIntent.isActivity()) {
            this.this$0.activityStarter.startPendingIntentDismissingKeyguard(this.$action.actionIntent);
        } else if (this.$action.isAuthenticationRequired()) {
            final MediaDataManager mediaDataManager = this.this$0;
            ActivityStarter activityStarter = mediaDataManager.activityStarter;
            final Notification.Action action = this.$action;
            activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() {
                public final boolean onDismiss() {
                    MediaDataManager mediaDataManager = mediaDataManager;
                    PendingIntent pendingIntent = action.actionIntent;
                    Objects.requireNonNull(mediaDataManager);
                    try {
                        pendingIntent.send();
                        return true;
                    } catch (PendingIntent.CanceledException e) {
                        Log.d("MediaDataManager", "Intent canceled", e);
                        return false;
                    }
                }
            }, C08832.INSTANCE, true);
        } else {
            MediaDataManager mediaDataManager2 = this.this$0;
            PendingIntent pendingIntent = this.$action.actionIntent;
            Objects.requireNonNull(mediaDataManager2);
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                Log.d("MediaDataManager", "Intent canceled", e);
            }
        }
    }
}
