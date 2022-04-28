package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class HeadsUpViewBinder$$ExternalSyntheticLambda1 implements NotifBindPipeline.BindCallback {
    public final /* synthetic */ HeadsUpViewBinder f$0;
    public final /* synthetic */ NotificationEntry f$1;
    public final /* synthetic */ RowContentBindParams f$2;
    public final /* synthetic */ NotifBindPipeline.BindCallback f$3;

    public /* synthetic */ HeadsUpViewBinder$$ExternalSyntheticLambda1(HeadsUpViewBinder headsUpViewBinder, NotificationEntry notificationEntry, RowContentBindParams rowContentBindParams, NotifBindPipeline.BindCallback bindCallback) {
        this.f$0 = headsUpViewBinder;
        this.f$1 = notificationEntry;
        this.f$2 = rowContentBindParams;
        this.f$3 = bindCallback;
    }

    public final void onBindFinished(NotificationEntry notificationEntry) {
        HeadsUpViewBinder headsUpViewBinder = this.f$0;
        NotificationEntry notificationEntry2 = this.f$1;
        RowContentBindParams rowContentBindParams = this.f$2;
        NotifBindPipeline.BindCallback bindCallback = this.f$3;
        Objects.requireNonNull(headsUpViewBinder);
        HeadsUpViewBinderLogger headsUpViewBinderLogger = headsUpViewBinder.mLogger;
        Objects.requireNonNull(notificationEntry2);
        String str = notificationEntry2.mKey;
        Objects.requireNonNull(headsUpViewBinderLogger);
        LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpViewBinderLogger$entryBoundSuccessfully$2 headsUpViewBinderLogger$entryBoundSuccessfully$2 = HeadsUpViewBinderLogger$entryBoundSuccessfully$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$entryBoundSuccessfully$2);
            obtain.str1 = str;
            logBuffer.push(obtain);
        }
        Objects.requireNonNull(notificationEntry);
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        Objects.requireNonNull(rowContentBindParams);
        boolean z = rowContentBindParams.mUseIncreasedHeadsUpHeight;
        Objects.requireNonNull(expandableNotificationRow);
        expandableNotificationRow.mUseIncreasedHeadsUpHeight = z;
        headsUpViewBinder.mOngoingBindCallbacks.remove(notificationEntry2);
        if (bindCallback != null) {
            bindCallback.onBindFinished(notificationEntry);
        }
    }
}
