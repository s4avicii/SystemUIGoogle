package com.google.android.systemui.columbus.gates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

/* compiled from: UsbState.kt */
public final class UsbState extends TransientGate {
    public final long gateDuration;
    public boolean usbConnected;
    public final UsbState$usbReceiver$1 usbReceiver = new UsbState$usbReceiver$1(this);

    public final void onActivate() {
        IntentFilter intentFilter = new IntentFilter("android.hardware.usb.action.USB_STATE");
        Intent registerReceiver = this.context.registerReceiver((BroadcastReceiver) null, intentFilter);
        if (registerReceiver != null) {
            this.usbConnected = registerReceiver.getBooleanExtra("connected", false);
        }
        this.context.registerReceiver(this.usbReceiver, intentFilter);
    }

    public final void onDeactivate() {
        this.context.unregisterReceiver(this.usbReceiver);
    }

    public UsbState(Context context, Handler handler, long j) {
        super(context, handler);
        this.gateDuration = j;
    }
}
