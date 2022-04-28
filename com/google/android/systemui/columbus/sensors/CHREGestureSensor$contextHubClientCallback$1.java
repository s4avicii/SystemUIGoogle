package com.google.android.systemui.columbus.sensors;

import android.hardware.location.ContextHubClient;
import android.hardware.location.ContextHubClientCallback;
import android.hardware.location.NanoAppMessage;
import android.util.Log;
import com.google.android.systemui.columbus.proto.nano.ColumbusProto$GestureDetected;
import com.google.android.systemui.columbus.proto.nano.ColumbusProto$NanoappEvents;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CHREGestureSensor.kt */
public final class CHREGestureSensor$contextHubClientCallback$1 extends ContextHubClientCallback {
    public final /* synthetic */ CHREGestureSensor this$0;

    public CHREGestureSensor$contextHubClientCallback$1(CHREGestureSensor cHREGestureSensor) {
        this.this$0 = cHREGestureSensor;
    }

    public final void onMessageFromNanoApp(ContextHubClient contextHubClient, NanoAppMessage nanoAppMessage) {
        if (nanoAppMessage.getNanoAppId() == 5147455389092024345L) {
            try {
                int messageType = nanoAppMessage.getMessageType();
                if (messageType == 300) {
                    CHREGestureSensor cHREGestureSensor = this.this$0;
                    byte[] messageBody = nanoAppMessage.getMessageBody();
                    ColumbusProto$GestureDetected columbusProto$GestureDetected = new ColumbusProto$GestureDetected();
                    MessageNano.mergeFrom(columbusProto$GestureDetected, messageBody);
                    CHREGestureSensor.access$handleGestureDetection(cHREGestureSensor, columbusProto$GestureDetected);
                } else if (messageType != 500) {
                    Log.e("Columbus/GestureSensor", Intrinsics.stringPlus("Unknown message type: ", Integer.valueOf(nanoAppMessage.getMessageType())));
                } else {
                    CHREGestureSensor cHREGestureSensor2 = this.this$0;
                    byte[] messageBody2 = nanoAppMessage.getMessageBody();
                    ColumbusProto$NanoappEvents columbusProto$NanoappEvents = new ColumbusProto$NanoappEvents();
                    MessageNano.mergeFrom(columbusProto$NanoappEvents, messageBody2);
                    CHREGestureSensor.access$handleNanoappEvents(cHREGestureSensor2, columbusProto$NanoappEvents);
                }
            } catch (InvalidProtocolBufferNanoException e) {
                Log.e("Columbus/GestureSensor", "Invalid protocol buffer", e);
            }
        }
    }

    public final void onHubReset(ContextHubClient contextHubClient) {
        Log.d("Columbus/GestureSensor", Intrinsics.stringPlus("HubReset: ", Integer.valueOf(contextHubClient.getAttachedHub().getId())));
    }

    public final void onNanoAppAborted(ContextHubClient contextHubClient, long j, int i) {
        if (j == 5147455389092024345L) {
            Log.e("Columbus/GestureSensor", Intrinsics.stringPlus("Nanoapp aborted, code: ", Integer.valueOf(i)));
        }
    }

    public final void onNanoAppLoaded(ContextHubClient contextHubClient, long j) {
        if (j == 5147455389092024345L) {
            CHREGestureSensor cHREGestureSensor = this.this$0;
            Objects.requireNonNull(cHREGestureSensor);
            if (cHREGestureSensor.isListening) {
                Log.d("Columbus/GestureSensor", "Nanoapp loaded");
                this.this$0.updateScreenState();
                this.this$0.startRecognizer();
            }
        }
    }
}
