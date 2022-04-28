package com.android.systemui.broadcast.logging;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: BroadcastDispatcherLogger.kt */
public final class BroadcastDispatcherLogger$logReceiverUnregistered$2 extends Lambda implements Function1<LogMessage, String> {
    public static final BroadcastDispatcherLogger$logReceiverUnregistered$2 INSTANCE = new BroadcastDispatcherLogger$logReceiverUnregistered$2();

    public BroadcastDispatcherLogger$logReceiverUnregistered$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Receiver ");
        m.append(logMessage.getStr1());
        m.append(" unregistered for user ");
        m.append(logMessage.getInt1());
        return m.toString();
    }
}
