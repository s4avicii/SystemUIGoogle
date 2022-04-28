package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class HeadsUpViewBinder$$ExternalSyntheticLambda0 implements NotifBindPipeline.BindCallback {
    public final /* synthetic */ HeadsUpViewBinder f$0;

    public /* synthetic */ HeadsUpViewBinder$$ExternalSyntheticLambda0(HeadsUpViewBinder headsUpViewBinder) {
        this.f$0 = headsUpViewBinder;
    }

    public final void onBindFinished(NotificationEntry notificationEntry) {
        HeadsUpViewBinder headsUpViewBinder = this.f$0;
        Objects.requireNonNull(headsUpViewBinder);
        HeadsUpViewBinderLogger headsUpViewBinderLogger = headsUpViewBinder.mLogger;
        Objects.requireNonNull(notificationEntry);
        String str = notificationEntry.mKey;
        Objects.requireNonNull(headsUpViewBinderLogger);
        LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpViewBinderLogger$entryUnbound$2 headsUpViewBinderLogger$entryUnbound$2 = HeadsUpViewBinderLogger$entryUnbound$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$entryUnbound$2);
            obtain.str1 = str;
            logBuffer.push(obtain);
        }
    }
}
