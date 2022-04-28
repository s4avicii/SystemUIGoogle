package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewListener;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import java.util.Objects;
import kotlin.jvm.internal.Reflection;

/* compiled from: GutsCoordinator.kt */
public final class GutsCoordinator$mGutsListener$1 implements NotifGutsViewListener {
    public final /* synthetic */ GutsCoordinator this$0;

    public GutsCoordinator$mGutsListener$1(GutsCoordinator gutsCoordinator) {
        this.this$0 = gutsCoordinator;
    }

    public final void onGutsClose(NotificationEntry notificationEntry) {
        GutsCoordinatorLogger gutsCoordinatorLogger = this.this$0.logger;
        String str = notificationEntry.mKey;
        Objects.requireNonNull(gutsCoordinatorLogger);
        LogBuffer logBuffer = gutsCoordinatorLogger.buffer;
        LogLevel logLevel = LogLevel.DEBUG;
        GutsCoordinatorLogger$logGutsClosed$2 gutsCoordinatorLogger$logGutsClosed$2 = GutsCoordinatorLogger$logGutsClosed$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("GutsCoordinator", logLevel, gutsCoordinatorLogger$logGutsClosed$2);
            obtain.str1 = str;
            logBuffer.push(obtain);
        }
        GutsCoordinator.access$closeGutsAndEndLifetimeExtension(this.this$0, notificationEntry);
    }

    public final void onGutsOpen(NotificationEntry notificationEntry, NotificationGuts notificationGuts) {
        boolean z;
        GutsCoordinatorLogger gutsCoordinatorLogger = this.this$0.logger;
        String str = notificationEntry.mKey;
        Objects.requireNonNull(gutsCoordinatorLogger);
        LogBuffer logBuffer = gutsCoordinatorLogger.buffer;
        LogLevel logLevel = LogLevel.DEBUG;
        GutsCoordinatorLogger$logGutsOpened$2 gutsCoordinatorLogger$logGutsOpened$2 = GutsCoordinatorLogger$logGutsOpened$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        boolean z2 = true;
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("GutsCoordinator", logLevel, gutsCoordinatorLogger$logGutsOpened$2);
            obtain.str1 = str;
            obtain.str2 = Reflection.getOrCreateKotlinClass(notificationGuts.mGutsContent.getClass()).getSimpleName();
            NotificationGuts.GutsContent gutsContent = notificationGuts.mGutsContent;
            if (gutsContent == null || !gutsContent.isLeavebehind()) {
                z = false;
            } else {
                z = true;
            }
            obtain.bool1 = z;
            logBuffer.push(obtain);
        }
        Objects.requireNonNull(notificationGuts);
        NotificationGuts.GutsContent gutsContent2 = notificationGuts.mGutsContent;
        if (gutsContent2 == null || !gutsContent2.isLeavebehind()) {
            z2 = false;
        }
        if (z2) {
            GutsCoordinator.access$closeGutsAndEndLifetimeExtension(this.this$0, notificationEntry);
        } else {
            this.this$0.notifsWithOpenGuts.add(notificationEntry.mKey);
        }
    }
}
