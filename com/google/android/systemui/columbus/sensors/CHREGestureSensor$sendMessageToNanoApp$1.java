package com.google.android.systemui.columbus.sensors;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.location.ContextHubClient;
import android.hardware.location.NanoAppMessage;
import android.util.Log;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: CHREGestureSensor.kt */
public final class CHREGestureSensor$sendMessageToNanoApp$1 implements Runnable {
    public final /* synthetic */ byte[] $bytes;
    public final /* synthetic */ int $messageType;
    public final /* synthetic */ Function0<Unit> $onFail;
    public final /* synthetic */ Function0<Unit> $onSuccess;
    public final /* synthetic */ CHREGestureSensor this$0;

    public CHREGestureSensor$sendMessageToNanoApp$1(int i, byte[] bArr, CHREGestureSensor cHREGestureSensor, Function0<Unit> function0, Function0<Unit> function02) {
        this.$messageType = i;
        this.$bytes = bArr;
        this.this$0 = cHREGestureSensor;
        this.$onFail = function0;
        this.$onSuccess = function02;
    }

    public final void run() {
        Integer num;
        NanoAppMessage createMessageToNanoApp = NanoAppMessage.createMessageToNanoApp(5147455389092024345L, this.$messageType, this.$bytes);
        ContextHubClient contextHubClient = this.this$0.contextHubClient;
        if (contextHubClient == null) {
            num = null;
        } else {
            num = Integer.valueOf(contextHubClient.sendMessageToNanoApp(createMessageToNanoApp));
        }
        if (num != null && num.intValue() == 0) {
            Function0<Unit> function0 = this.$onSuccess;
            if (function0 != null) {
                function0.invoke();
                return;
            }
            return;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unable to send message ");
        m.append(this.$messageType);
        m.append(" to nanoapp, error code ");
        m.append(num);
        Log.e("Columbus/GestureSensor", m.toString());
        Function0<Unit> function02 = this.$onFail;
        if (function02 != null) {
            function02.invoke();
        }
    }
}
