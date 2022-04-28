package com.android.systemui.doze;

import android.os.SystemClock;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DozeTriggers$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ DozeTriggers f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Consumer f$3;

    public /* synthetic */ DozeTriggers$$ExternalSyntheticLambda6(DozeTriggers dozeTriggers, long j, int i, Consumer consumer) {
        this.f$0 = dozeTriggers;
        this.f$1 = j;
        this.f$2 = i;
        this.f$3 = consumer;
    }

    public final void accept(Object obj) {
        boolean z;
        DozeTriggers dozeTriggers = this.f$0;
        long j = this.f$1;
        int i = this.f$2;
        Consumer consumer = this.f$3;
        Boolean bool = (Boolean) obj;
        Objects.requireNonNull(dozeTriggers);
        long uptimeMillis = SystemClock.uptimeMillis();
        DozeLog dozeLog = dozeTriggers.mDozeLog;
        if (bool == null) {
            z = false;
        } else {
            z = bool.booleanValue();
        }
        long j2 = uptimeMillis - j;
        Objects.requireNonNull(dozeLog);
        DozeLogger dozeLogger = dozeLog.mLogger;
        Objects.requireNonNull(dozeLogger);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogLevel logLevel = LogLevel.DEBUG;
        DozeLogger$logProximityResult$2 dozeLogger$logProximityResult$2 = DozeLogger$logProximityResult$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logProximityResult$2);
            obtain.bool1 = z;
            obtain.long1 = j2;
            obtain.int1 = i;
            logBuffer.push(obtain);
        }
        DozeLog.SummaryStats summaryStats = dozeLog.mProxStats[i][!z];
        Objects.requireNonNull(summaryStats);
        summaryStats.mCount++;
        consumer.accept(bool);
        dozeTriggers.mWakeLock.release("DozeTriggers");
    }
}
