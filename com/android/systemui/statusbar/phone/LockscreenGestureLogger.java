package com.android.systemui.statusbar.phone;

import android.metrics.LogMaker;
import android.util.ArrayMap;
import android.util.EventLog;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import kotlin.p018io.CloseableKt;

public final class LockscreenGestureLogger {
    public ArrayMap<Integer, Integer> mLegacyMap = new ArrayMap<>(11);
    public final MetricsLogger mMetricsLogger;

    public enum LockscreenUiEvent implements UiEventLogger.UiEventEnum {
        LOCKSCREEN_PULL_SHADE_OPEN(539),
        LOCKSCREEN_LOCK_SHOW_HINT(543),
        LOCKSCREEN_DIALER(545),
        LOCKSCREEN_CAMERA(546),
        LOCKSCREEN_UNLOCK(547),
        LOCKSCREEN_NOTIFICATION_FALSE_TOUCH(548),
        LOCKSCREEN_UNLOCKED_NOTIFICATION_PANEL_EXPAND(549),
        LOCKSCREEN_SWITCH_USER_TAP(934);
        
        private final int mId;

        /* access modifiers changed from: public */
        LockscreenUiEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public static void log(LockscreenUiEvent lockscreenUiEvent) {
        new UiEventLoggerImpl().log(lockscreenUiEvent);
    }

    public final void write(int i, int i2, int i3) {
        int i4;
        this.mMetricsLogger.write(new LogMaker(i).setType(4).addTaggedData(826, Integer.valueOf(i2)).addTaggedData(827, Integer.valueOf(i3)));
        Integer num = this.mLegacyMap.get(Integer.valueOf(i));
        if (num == null) {
            i4 = 0;
        } else {
            i4 = num.intValue();
        }
        EventLog.writeEvent(36021, new Object[]{Integer.valueOf(i4), Integer.valueOf(i2), Integer.valueOf(i3)});
    }

    public LockscreenGestureLogger(MetricsLogger metricsLogger) {
        this.mMetricsLogger = metricsLogger;
        int i = 0;
        while (true) {
            int[] iArr = CloseableKt.METRICS_GESTURE_TYPE_MAP;
            if (i < 11) {
                this.mLegacyMap.put(Integer.valueOf(iArr[i]), Integer.valueOf(i));
                i++;
            } else {
                return;
            }
        }
    }
}
