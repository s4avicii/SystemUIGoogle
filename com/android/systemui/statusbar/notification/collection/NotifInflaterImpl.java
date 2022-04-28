package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.google.android.systemui.lowlightclock.LowLightDockManager$$ExternalSyntheticLambda0;
import java.util.Objects;

public final class NotifInflaterImpl implements NotifInflater {
    public final NotifInflationErrorManager mNotifErrorManager;
    public NotificationRowBinderImpl mNotificationRowBinder;

    public final void inflateViews(NotificationEntry notificationEntry, NotifInflater.Params params, final NotifInflater.InflationCallback inflationCallback) {
        try {
            NotificationRowBinderImpl notificationRowBinderImpl = this.mNotificationRowBinder;
            if (notificationRowBinderImpl != null) {
                notificationRowBinderImpl.inflateViews(notificationEntry, params, new NotificationRowContentBinder.InflationCallback() {
                    public final void handleInflationException(NotificationEntry notificationEntry, Exception exc) {
                        NotifInflaterImpl.this.mNotifErrorManager.setInflationError(notificationEntry, exc);
                    }

                    public final void onAsyncInflationFinished(NotificationEntry notificationEntry) {
                        NotifInflaterImpl.this.mNotifErrorManager.clearInflationError(notificationEntry);
                        NotifInflater.InflationCallback inflationCallback = inflationCallback;
                        if (inflationCallback != null) {
                            Objects.requireNonNull(notificationEntry);
                            inflationCallback.onInflationFinished(notificationEntry, notificationEntry.mRowController);
                        }
                    }
                });
                return;
            }
            throw new RuntimeException("NotificationRowBinder must be attached before using NotifInflaterImpl.");
        } catch (InflationException e) {
            this.mNotifErrorManager.setInflationError(notificationEntry, e);
        }
    }

    public NotifInflaterImpl(NotifInflationErrorManager notifInflationErrorManager) {
        this.mNotifErrorManager = notifInflationErrorManager;
    }

    public final void abortInflation(NotificationEntry notificationEntry) {
        notificationEntry.abortTask();
    }

    public final void rebindViews(NotificationEntry notificationEntry, NotifInflater.Params params, LowLightDockManager$$ExternalSyntheticLambda0 lowLightDockManager$$ExternalSyntheticLambda0) {
        inflateViews(notificationEntry, params, lowLightDockManager$$ExternalSyntheticLambda0);
    }
}
