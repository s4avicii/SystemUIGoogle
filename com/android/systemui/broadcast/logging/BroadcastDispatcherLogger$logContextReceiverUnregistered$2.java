package com.android.systemui.broadcast.logging;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: BroadcastDispatcherLogger.kt */
public final class BroadcastDispatcherLogger$logContextReceiverUnregistered$2 extends Lambda implements Function1<LogMessage, String> {
    public static final BroadcastDispatcherLogger$logContextReceiverUnregistered$2 INSTANCE = new BroadcastDispatcherLogger$logContextReceiverUnregistered$2();

    public BroadcastDispatcherLogger$logContextReceiverUnregistered$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Receiver unregistered with Context for user ");
        m.append(logMessage.getInt1());
        m.append(", action ");
        m.append(logMessage.getStr1());
        return m.toString();
    }
}
