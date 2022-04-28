package com.android.systemui.statusbar.notification.interruption;

import android.util.ArrayMap;
import androidx.core.p002os.CancellationSignal;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import java.util.Objects;

public final class HeadsUpViewBinder {
    public final HeadsUpViewBinderLogger mLogger;
    public final NotificationMessagingUtil mNotificationMessagingUtil;
    public NotificationPresenter mNotificationPresenter;
    public final ArrayMap mOngoingBindCallbacks = new ArrayMap();
    public final RowContentBindStage mStage;

    public final void abortBindCallback(NotificationEntry notificationEntry) {
        CancellationSignal cancellationSignal = (CancellationSignal) this.mOngoingBindCallbacks.remove(notificationEntry);
        if (cancellationSignal != null) {
            HeadsUpViewBinderLogger headsUpViewBinderLogger = this.mLogger;
            Objects.requireNonNull(notificationEntry);
            String str = notificationEntry.mKey;
            Objects.requireNonNull(headsUpViewBinderLogger);
            LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
            LogLevel logLevel = LogLevel.INFO;
            HeadsUpViewBinderLogger$currentOngoingBindingAborted$2 headsUpViewBinderLogger$currentOngoingBindingAborted$2 = HeadsUpViewBinderLogger$currentOngoingBindingAborted$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                LogMessageImpl obtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$currentOngoingBindingAborted$2);
                obtain.str1 = str;
                logBuffer.push(obtain);
            }
            cancellationSignal.cancel();
        }
    }

    public final void bindHeadsUpView(NotificationEntry notificationEntry, NotifBindPipeline.BindCallback bindCallback) {
        boolean z;
        RowContentBindParams rowContentBindParams = (RowContentBindParams) this.mStage.getStageParams(notificationEntry);
        NotificationMessagingUtil notificationMessagingUtil = this.mNotificationMessagingUtil;
        Objects.requireNonNull(notificationEntry);
        if (!notificationMessagingUtil.isImportantMessaging(notificationEntry.mSbn, notificationEntry.getImportance()) || ((StatusBarNotificationPresenter) this.mNotificationPresenter).isPresenterFullyCollapsed()) {
            z = false;
        } else {
            z = true;
        }
        if (rowContentBindParams.mUseIncreasedHeadsUpHeight != z) {
            rowContentBindParams.mDirtyContentViews |= 4;
        }
        rowContentBindParams.mUseIncreasedHeadsUpHeight = z;
        rowContentBindParams.requireContentViews(4);
        CancellationSignal requestRebind = this.mStage.requestRebind(notificationEntry, new HeadsUpViewBinder$$ExternalSyntheticLambda1(this, notificationEntry, rowContentBindParams, bindCallback));
        abortBindCallback(notificationEntry);
        HeadsUpViewBinderLogger headsUpViewBinderLogger = this.mLogger;
        String str = notificationEntry.mKey;
        Objects.requireNonNull(headsUpViewBinderLogger);
        LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpViewBinderLogger$startBindingHun$2 headsUpViewBinderLogger$startBindingHun$2 = HeadsUpViewBinderLogger$startBindingHun$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$startBindingHun$2);
            obtain.str1 = str;
            logBuffer.push(obtain);
        }
        this.mOngoingBindCallbacks.put(notificationEntry, requestRebind);
    }

    public HeadsUpViewBinder(NotificationMessagingUtil notificationMessagingUtil, RowContentBindStage rowContentBindStage, HeadsUpViewBinderLogger headsUpViewBinderLogger) {
        this.mNotificationMessagingUtil = notificationMessagingUtil;
        this.mStage = rowContentBindStage;
        this.mLogger = headsUpViewBinderLogger;
    }

    public final void unbindHeadsUpView(NotificationEntry notificationEntry) {
        abortBindCallback(notificationEntry);
        ((RowContentBindParams) this.mStage.getStageParams(notificationEntry)).markContentViewsFreeable(4);
        HeadsUpViewBinderLogger headsUpViewBinderLogger = this.mLogger;
        String str = notificationEntry.mKey;
        Objects.requireNonNull(headsUpViewBinderLogger);
        LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpViewBinderLogger$entryContentViewMarkedFreeable$2 headsUpViewBinderLogger$entryContentViewMarkedFreeable$2 = HeadsUpViewBinderLogger$entryContentViewMarkedFreeable$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$entryContentViewMarkedFreeable$2);
            obtain.str1 = str;
            logBuffer.push(obtain);
        }
        this.mStage.requestRebind(notificationEntry, new HeadsUpViewBinder$$ExternalSyntheticLambda0(this));
    }
}
