package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.SystemClock;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.EventLog;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.statusbar.AlertingNotificationManager;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.util.ListenerSet;
import java.util.Iterator;
import java.util.Objects;

public abstract class HeadsUpManager extends AlertingNotificationManager {
    public final AccessibilityManagerWrapper mAccessibilityMgr;
    public final Context mContext;
    public boolean mHasPinnedNotification;
    public final ListenerSet<OnHeadsUpChangedListener> mListeners = new ListenerSet<>();
    public int mSnoozeLengthMs;
    public final ArrayMap<String, Long> mSnoozedPackages;
    public int mTouchAcceptanceDelay;
    public final UiEventLogger mUiEventLogger;
    public int mUser;

    public class HeadsUpEntry extends AlertingNotificationManager.AlertEntry {
        public boolean expanded;
        public boolean remoteInputActive;

        public HeadsUpEntry() {
            super();
        }

        public long calculateFinishTime() {
            long j = this.mPostTime;
            HeadsUpManager headsUpManager = HeadsUpManager.this;
            int i = headsUpManager.mAutoDismissNotificationDecay;
            AccessibilityManagerWrapper accessibilityManagerWrapper = headsUpManager.mAccessibilityMgr;
            Objects.requireNonNull(accessibilityManagerWrapper);
            return j + ((long) accessibilityManagerWrapper.mAccessibilityManager.getRecommendedTimeoutMillis(i, 7));
        }

        public final int compareTo(AlertingNotificationManager.AlertEntry alertEntry) {
            HeadsUpEntry headsUpEntry = (HeadsUpEntry) alertEntry;
            boolean isRowPinned = this.mEntry.isRowPinned();
            boolean isRowPinned2 = headsUpEntry.mEntry.isRowPinned();
            if (isRowPinned && !isRowPinned2) {
                return -1;
            }
            if (!isRowPinned && isRowPinned2) {
                return 1;
            }
            HeadsUpManager headsUpManager = HeadsUpManager.this;
            NotificationEntry notificationEntry = this.mEntry;
            Objects.requireNonNull(headsUpManager);
            boolean hasFullScreenIntent = HeadsUpManager.hasFullScreenIntent(notificationEntry);
            HeadsUpManager headsUpManager2 = HeadsUpManager.this;
            NotificationEntry notificationEntry2 = headsUpEntry.mEntry;
            Objects.requireNonNull(headsUpManager2);
            boolean hasFullScreenIntent2 = HeadsUpManager.hasFullScreenIntent(notificationEntry2);
            if (hasFullScreenIntent && !hasFullScreenIntent2) {
                return -1;
            }
            if (!hasFullScreenIntent && hasFullScreenIntent2) {
                return 1;
            }
            boolean r0 = HeadsUpManager.m252$$Nest$smisCriticalCallNotif(this.mEntry);
            boolean r1 = HeadsUpManager.m252$$Nest$smisCriticalCallNotif(headsUpEntry.mEntry);
            if (r0 && !r1) {
                return -1;
            }
            if (!r0 && r1) {
                return 1;
            }
            boolean z = this.remoteInputActive;
            if (z && !headsUpEntry.remoteInputActive) {
                return -1;
            }
            if (z || !headsUpEntry.remoteInputActive) {
                return super.compareTo((AlertingNotificationManager.AlertEntry) headsUpEntry);
            }
            return 1;
        }

        public boolean isSticky() {
            if ((!this.mEntry.isRowPinned() || !this.expanded) && !this.remoteInputActive) {
                HeadsUpManager headsUpManager = HeadsUpManager.this;
                NotificationEntry notificationEntry = this.mEntry;
                Objects.requireNonNull(headsUpManager);
                if (HeadsUpManager.hasFullScreenIntent(notificationEntry)) {
                    return true;
                }
                return false;
            }
            return true;
        }

        public final long calculatePostTime() {
            return super.calculatePostTime() + ((long) HeadsUpManager.this.mTouchAcceptanceDelay);
        }

        public void reset() {
            super.reset();
            this.expanded = false;
            this.remoteInputActive = false;
        }

        public void setExpanded(boolean z) {
            this.expanded = z;
        }
    }

    public enum NotificationPeekEvent implements UiEventLogger.UiEventEnum {
        ;
        
        private final int mId;

        /* access modifiers changed from: public */
        NotificationPeekEvent() {
            this.mId = 801;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public final int compare(NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (notificationEntry == null || notificationEntry2 == null) {
            if (notificationEntry == null) {
                z = true;
            } else {
                z = false;
            }
            if (notificationEntry2 != null) {
                z3 = false;
            }
            return Boolean.compare(z, z3);
        }
        HeadsUpEntry headsUpEntry = getHeadsUpEntry(notificationEntry.mKey);
        HeadsUpEntry headsUpEntry2 = getHeadsUpEntry(notificationEntry2.mKey);
        if (headsUpEntry != null && headsUpEntry2 != null) {
            return headsUpEntry.compareTo((AlertingNotificationManager.AlertEntry) headsUpEntry2);
        }
        if (headsUpEntry == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (headsUpEntry2 != null) {
            z3 = false;
        }
        return Boolean.compare(z2, z3);
    }

    public boolean isTrackingHeadsUp() {
        return false;
    }

    public final void addListener(OnHeadsUpChangedListener onHeadsUpChangedListener) {
        this.mListeners.addIfAbsent(onHeadsUpChangedListener);
    }

    public HeadsUpEntry createAlertEntry() {
        return new HeadsUpEntry();
    }

    public final HeadsUpEntry getHeadsUpEntry(String str) {
        return (HeadsUpEntry) this.mAlertEntries.get(str);
    }

    public final HeadsUpEntry getTopHeadsUpEntry() {
        HeadsUpEntry headsUpEntry = null;
        if (this.mAlertEntries.isEmpty()) {
            return null;
        }
        for (AlertingNotificationManager.AlertEntry next : this.mAlertEntries.values()) {
            if (headsUpEntry == null || next.compareTo((AlertingNotificationManager.AlertEntry) headsUpEntry) < 0) {
                headsUpEntry = (HeadsUpEntry) next;
            }
        }
        return headsUpEntry;
    }

    public final boolean isSnoozed(String str) {
        LogLevel logLevel = LogLevel.INFO;
        String str2 = this.mUser + "," + str;
        Long l = this.mSnoozedPackages.get(str2);
        if (l == null) {
            return false;
        }
        long longValue = l.longValue();
        Objects.requireNonNull(this.mClock);
        if (longValue > SystemClock.elapsedRealtime()) {
            HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
            Objects.requireNonNull(headsUpManagerLogger);
            LogBuffer logBuffer = headsUpManagerLogger.buffer;
            HeadsUpManagerLogger$logIsSnoozedReturned$2 headsUpManagerLogger$logIsSnoozedReturned$2 = HeadsUpManagerLogger$logIsSnoozedReturned$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (logBuffer.frozen) {
                return true;
            }
            LogMessageImpl obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logIsSnoozedReturned$2);
            obtain.str1 = str2;
            logBuffer.push(obtain);
            return true;
        }
        HeadsUpManagerLogger headsUpManagerLogger2 = this.mLogger;
        Objects.requireNonNull(headsUpManagerLogger2);
        LogBuffer logBuffer2 = headsUpManagerLogger2.buffer;
        HeadsUpManagerLogger$logPackageUnsnoozed$2 headsUpManagerLogger$logPackageUnsnoozed$2 = HeadsUpManagerLogger$logPackageUnsnoozed$2.INSTANCE;
        Objects.requireNonNull(logBuffer2);
        if (!logBuffer2.frozen) {
            LogMessageImpl obtain2 = logBuffer2.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logPackageUnsnoozed$2);
            obtain2.str1 = str2;
            logBuffer2.push(obtain2);
        }
        this.mSnoozedPackages.remove(str2);
        return false;
    }

    public final void onAlertEntryAdded(AlertingNotificationManager.AlertEntry alertEntry) {
        NotificationEntry notificationEntry = alertEntry.mEntry;
        notificationEntry.setHeadsUp(true);
        setEntryPinned((HeadsUpEntry) alertEntry, shouldHeadsUpBecomePinned(notificationEntry));
        EventLog.writeEvent(36001, new Object[]{notificationEntry.mKey, 1});
        Iterator<OnHeadsUpChangedListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onHeadsUpStateChanged(notificationEntry, true);
        }
    }

    public void onAlertEntryRemoved(AlertingNotificationManager.AlertEntry alertEntry) {
        NotificationEntry notificationEntry = alertEntry.mEntry;
        notificationEntry.setHeadsUp(false);
        setEntryPinned((HeadsUpEntry) alertEntry, false);
        EventLog.writeEvent(36001, new Object[]{notificationEntry.mKey, 0});
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        String str = notificationEntry.mKey;
        Objects.requireNonNull(headsUpManagerLogger);
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpManagerLogger$logNotificationActuallyRemoved$2 headsUpManagerLogger$logNotificationActuallyRemoved$2 = HeadsUpManagerLogger$logNotificationActuallyRemoved$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logNotificationActuallyRemoved$2);
            obtain.str1 = str;
            logBuffer.push(obtain);
        }
        Iterator<OnHeadsUpChangedListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onHeadsUpStateChanged(notificationEntry, false);
        }
    }

    public final void setEntryPinned(HeadsUpEntry headsUpEntry, boolean z) {
        StatusBarNotification statusBarNotification;
        HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        Objects.requireNonNull(notificationEntry);
        String str = notificationEntry.mKey;
        Objects.requireNonNull(headsUpManagerLogger);
        LogBuffer logBuffer = headsUpManagerLogger.buffer;
        LogLevel logLevel = LogLevel.VERBOSE;
        HeadsUpManagerLogger$logSetEntryPinned$2 headsUpManagerLogger$logSetEntryPinned$2 = HeadsUpManagerLogger$logSetEntryPinned$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logSetEntryPinned$2);
            obtain.str1 = str;
            obtain.bool1 = z;
            logBuffer.push(obtain);
        }
        NotificationEntry notificationEntry2 = headsUpEntry.mEntry;
        if (notificationEntry2.isRowPinned() != z) {
            ExpandableNotificationRow expandableNotificationRow = notificationEntry2.row;
            boolean z2 = false;
            if (expandableNotificationRow != null) {
                int intrinsicHeight = expandableNotificationRow.getIntrinsicHeight();
                boolean isAboveShelf = expandableNotificationRow.isAboveShelf();
                expandableNotificationRow.mIsPinned = z;
                if (intrinsicHeight != expandableNotificationRow.getIntrinsicHeight()) {
                    expandableNotificationRow.notifyHeightChanged(false);
                }
                if (z) {
                    expandableNotificationRow.setIconAnimationRunning(true);
                    expandableNotificationRow.mExpandedWhenPinned = false;
                } else if (expandableNotificationRow.mExpandedWhenPinned) {
                    expandableNotificationRow.setUserExpanded(true, false);
                }
                expandableNotificationRow.setChronometerRunning(expandableNotificationRow.mLastChronometerRunning);
                if (expandableNotificationRow.isAboveShelf() != isAboveShelf) {
                    ((AboveShelfObserver) expandableNotificationRow.mAboveShelfChangedListener).onAboveShelfStateChanged(!isAboveShelf);
                }
            }
            Iterator<String> it = this.mAlertEntries.keySet().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (getHeadsUpEntry(it.next()).mEntry.isRowPinned()) {
                        z2 = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (z2 != this.mHasPinnedNotification) {
                HeadsUpManagerLogger headsUpManagerLogger2 = this.mLogger;
                Objects.requireNonNull(headsUpManagerLogger2);
                LogBuffer logBuffer2 = headsUpManagerLogger2.buffer;
                LogLevel logLevel2 = LogLevel.INFO;
                HeadsUpManagerLogger$logUpdatePinnedMode$2 headsUpManagerLogger$logUpdatePinnedMode$2 = HeadsUpManagerLogger$logUpdatePinnedMode$2.INSTANCE;
                Objects.requireNonNull(logBuffer2);
                if (!logBuffer2.frozen) {
                    LogMessageImpl obtain2 = logBuffer2.obtain("HeadsUpManager", logLevel2, headsUpManagerLogger$logUpdatePinnedMode$2);
                    obtain2.bool1 = z2;
                    logBuffer2.push(obtain2);
                }
                this.mHasPinnedNotification = z2;
                if (z2) {
                    MetricsLogger.count(this.mContext, "note_peek", 1);
                }
                Iterator<OnHeadsUpChangedListener> it2 = this.mListeners.iterator();
                while (it2.hasNext()) {
                    it2.next().onHeadsUpPinnedModeChanged(z2);
                }
            }
            if (z && (statusBarNotification = notificationEntry2.mSbn) != null) {
                this.mUiEventLogger.logWithInstanceId(NotificationPeekEvent.NOTIFICATION_PEEK, statusBarNotification.getUid(), notificationEntry2.mSbn.getPackageName(), notificationEntry2.mSbn.getInstanceId());
            }
            Iterator<OnHeadsUpChangedListener> it3 = this.mListeners.iterator();
            while (it3.hasNext()) {
                OnHeadsUpChangedListener next = it3.next();
                if (z) {
                    next.onHeadsUpPinned(notificationEntry2);
                } else {
                    next.onHeadsUpUnPinned(notificationEntry2);
                }
            }
        }
    }

    public void snooze() {
        for (String headsUpEntry : this.mAlertEntries.keySet()) {
            NotificationEntry notificationEntry = getHeadsUpEntry(headsUpEntry).mEntry;
            Objects.requireNonNull(notificationEntry);
            String packageName = notificationEntry.mSbn.getPackageName();
            String str = this.mUser + "," + packageName;
            HeadsUpManagerLogger headsUpManagerLogger = this.mLogger;
            Objects.requireNonNull(headsUpManagerLogger);
            LogBuffer logBuffer = headsUpManagerLogger.buffer;
            LogLevel logLevel = LogLevel.INFO;
            HeadsUpManagerLogger$logPackageSnoozed$2 headsUpManagerLogger$logPackageSnoozed$2 = HeadsUpManagerLogger$logPackageSnoozed$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                LogMessageImpl obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logPackageSnoozed$2);
                obtain.str1 = str;
                logBuffer.push(obtain);
            }
            ArrayMap<String, Long> arrayMap = this.mSnoozedPackages;
            Objects.requireNonNull(this.mClock);
            arrayMap.put(str, Long.valueOf(SystemClock.elapsedRealtime() + ((long) this.mSnoozeLengthMs)));
        }
    }

    public final void unpinAll() {
        boolean z;
        for (String headsUpEntry : this.mAlertEntries.keySet()) {
            HeadsUpEntry headsUpEntry2 = getHeadsUpEntry(headsUpEntry);
            setEntryPinned(headsUpEntry2, false);
            headsUpEntry2.updateEntry(false);
            NotificationEntry notificationEntry = headsUpEntry2.mEntry;
            if (notificationEntry != null) {
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow == null || !expandableNotificationRow.mustStayOnScreen()) {
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    NotificationEntry notificationEntry2 = headsUpEntry2.mEntry;
                    Objects.requireNonNull(notificationEntry2);
                    ExpandableNotificationRow expandableNotificationRow2 = notificationEntry2.row;
                    if (expandableNotificationRow2 != null) {
                        expandableNotificationRow2.mMustStayOnScreen = false;
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$smisCriticalCallNotif  reason: not valid java name */
    public static boolean m252$$Nest$smisCriticalCallNotif(NotificationEntry notificationEntry) {
        boolean z;
        Objects.requireNonNull(notificationEntry);
        Notification notification = notificationEntry.mSbn.getNotification();
        if (!notification.isStyle(Notification.CallStyle.class) || notification.extras.getInt("android.callType") != 1) {
            z = false;
        } else {
            z = true;
        }
        if (z || (notificationEntry.mSbn.isOngoing() && "call".equals(notification.category))) {
            return true;
        }
        return false;
    }

    public HeadsUpManager(final Context context, HeadsUpManagerLogger headsUpManagerLogger) {
        super(headsUpManagerLogger);
        this.mContext = context;
        this.mAccessibilityMgr = (AccessibilityManagerWrapper) Dependency.get(AccessibilityManagerWrapper.class);
        this.mUiEventLogger = (UiEventLogger) Dependency.get(UiEventLogger.class);
        Resources resources = context.getResources();
        this.mMinimumDisplayTime = resources.getInteger(C1777R.integer.heads_up_notification_minimum_time);
        this.mAutoDismissNotificationDecay = resources.getInteger(C1777R.integer.heads_up_notification_decay);
        this.mTouchAcceptanceDelay = resources.getInteger(C1777R.integer.touch_acceptance_delay);
        this.mSnoozedPackages = new ArrayMap<>();
        this.mSnoozeLengthMs = Settings.Global.getInt(context.getContentResolver(), "heads_up_snooze_length_ms", resources.getInteger(C1777R.integer.heads_up_default_snooze_length_ms));
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("heads_up_snooze_length_ms"), false, new ContentObserver(this.mHandler) {
            public final void onChange(boolean z) {
                int i = Settings.Global.getInt(context.getContentResolver(), "heads_up_snooze_length_ms", -1);
                if (i > -1) {
                    HeadsUpManager headsUpManager = HeadsUpManager.this;
                    if (i != headsUpManager.mSnoozeLengthMs) {
                        headsUpManager.mSnoozeLengthMs = i;
                        HeadsUpManagerLogger headsUpManagerLogger = headsUpManager.mLogger;
                        Objects.requireNonNull(headsUpManagerLogger);
                        LogBuffer logBuffer = headsUpManagerLogger.buffer;
                        LogLevel logLevel = LogLevel.INFO;
                        HeadsUpManagerLogger$logSnoozeLengthChange$2 headsUpManagerLogger$logSnoozeLengthChange$2 = HeadsUpManagerLogger$logSnoozeLengthChange$2.INSTANCE;
                        Objects.requireNonNull(logBuffer);
                        if (!logBuffer.frozen) {
                            LogMessageImpl obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logSnoozeLengthChange$2);
                            obtain.int1 = i;
                            logBuffer.push(obtain);
                        }
                    }
                }
            }
        });
    }

    public static boolean hasFullScreenIntent(NotificationEntry notificationEntry) {
        Objects.requireNonNull(notificationEntry);
        if (notificationEntry.mSbn.getNotification().fullScreenIntent != null) {
            return true;
        }
        return false;
    }

    public final NotificationEntry getTopEntry() {
        HeadsUpEntry topHeadsUpEntry = getTopHeadsUpEntry();
        if (topHeadsUpEntry != null) {
            return topHeadsUpEntry.mEntry;
        }
        return null;
    }

    public boolean shouldHeadsUpBecomePinned(NotificationEntry notificationEntry) {
        return hasFullScreenIntent(notificationEntry);
    }

    public final void updateNotification(String str, boolean z) {
        super.updateNotification(str, z);
        HeadsUpEntry headsUpEntry = getHeadsUpEntry(str);
        if (z && headsUpEntry != null) {
            setEntryPinned(headsUpEntry, shouldHeadsUpBecomePinned(headsUpEntry.mEntry));
        }
    }
}
