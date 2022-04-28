package com.android.systemui.statusbar.notification;

import android.os.Handler;
import android.provider.DeviceConfig;
import android.service.notification.NotificationListenerService;
import android.util.SparseArray;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.Objects;

public final class AssistantFeedbackController {
    public volatile boolean mFeedbackEnabled = DeviceConfig.getBoolean("systemui", "enable_nas_feedback", false);
    public final Handler mHandler;
    public final SparseArray<FeedbackIcon> mIcons;
    public final C12231 mPropertiesChangedListener;

    public final int getFeedbackStatus(NotificationEntry notificationEntry) {
        if (!this.mFeedbackEnabled) {
            return 0;
        }
        Objects.requireNonNull(notificationEntry);
        NotificationListenerService.Ranking ranking = notificationEntry.mRanking;
        int importance = ranking.getChannel().getImportance();
        int importance2 = ranking.getImportance();
        if (importance < 3 && importance2 >= 3) {
            return 1;
        }
        if (importance >= 3 && importance2 < 3) {
            return 2;
        }
        if (importance < importance2 || ranking.getRankingAdjustment() == 1) {
            return 3;
        }
        if (importance > importance2 || ranking.getRankingAdjustment() == -1) {
            return 4;
        }
        return 0;
    }

    public AssistantFeedbackController(Handler handler, DeviceConfigProxy deviceConfigProxy) {
        C12231 r0 = new DeviceConfig.OnPropertiesChangedListener() {
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("enable_nas_feedback")) {
                    AssistantFeedbackController.this.mFeedbackEnabled = properties.getBoolean("enable_nas_feedback", false);
                }
            }
        };
        this.mPropertiesChangedListener = r0;
        this.mHandler = handler;
        Objects.requireNonNull(deviceConfigProxy);
        DeviceConfig.addOnPropertiesChangedListener("systemui", new AssistantFeedbackController$$ExternalSyntheticLambda0(this), r0);
        SparseArray<FeedbackIcon> sparseArray = new SparseArray<>(4);
        this.mIcons = sparseArray;
        sparseArray.set(1, new FeedbackIcon(17302441, 17040892));
        sparseArray.set(2, new FeedbackIcon(17302444, 17040895));
        sparseArray.set(3, new FeedbackIcon(17302445, 17040894));
        sparseArray.set(4, new FeedbackIcon(17302442, 17040893));
    }
}
