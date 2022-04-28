package com.android.systemui.statusbar.notification.collection.coordinator;

import android.service.notification.StatusBarNotification;
import com.android.systemui.media.MediaDataManagerKt;
import com.android.systemui.media.MediaFeatureFlag;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.util.Utils;
import java.util.Objects;

public final class MediaCoordinator implements Coordinator {
    public final Boolean mIsMediaFeatureEnabled;
    public final C12701 mMediaFilter = new NotifFilter() {
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            if (MediaCoordinator.this.mIsMediaFeatureEnabled.booleanValue()) {
                Objects.requireNonNull(notificationEntry);
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                String[] strArr = MediaDataManagerKt.ART_URIS;
                if (statusBarNotification.getNotification().isMediaNotification()) {
                    return true;
                }
            }
            return false;
        }
    };

    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.mMediaFilter);
    }

    public MediaCoordinator(MediaFeatureFlag mediaFeatureFlag) {
        Objects.requireNonNull(mediaFeatureFlag);
        this.mIsMediaFeatureEnabled = Boolean.valueOf(Utils.useQsMediaPlayer(mediaFeatureFlag.context));
    }
}
