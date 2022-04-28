package com.google.android.systemui.elmyra.gates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.android.p012wm.shell.C1777R;

public final class UsbState extends TransientGate {
    public boolean mUsbConnected;
    public final C22631 mUsbReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            boolean booleanExtra = intent.getBooleanExtra("connected", false);
            UsbState usbState = UsbState.this;
            if (booleanExtra != usbState.mUsbConnected) {
                usbState.mUsbConnected = booleanExtra;
                usbState.block();
            }
        }
    };

    public final void onActivate() {
        IntentFilter intentFilter = new IntentFilter("android.hardware.usb.action.USB_STATE");
        Intent registerReceiver = this.mContext.registerReceiver((BroadcastReceiver) null, intentFilter);
        if (registerReceiver != null) {
            this.mUsbConnected = registerReceiver.getBooleanExtra("connected", false);
        }
        this.mContext.registerReceiver(this.mUsbReceiver, intentFilter);
    }

    public final void onDeactivate() {
        this.mContext.unregisterReceiver(this.mUsbReceiver);
    }

    public UsbState(Context context) {
        super(context, (long) context.getResources().getInteger(C1777R.integer.elmyra_usb_gate_duration));
    }
}
