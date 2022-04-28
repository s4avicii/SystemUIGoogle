package com.google.android.systemui.reversecharging;

import android.app.AlarmManager;
import android.hardware.usb.UsbDevice;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ReverseChargingController$$ExternalSyntheticLambda4 implements AlarmManager.OnAlarmListener {
    public final /* synthetic */ ReverseChargingController f$0;

    public /* synthetic */ ReverseChargingController$$ExternalSyntheticLambda4(ReverseChargingController reverseChargingController) {
        this.f$0 = reverseChargingController;
    }

    public final void onAlarm() {
        ReverseChargingController reverseChargingController = this.f$0;
        Objects.requireNonNull(reverseChargingController);
        if (reverseChargingController.mUsbManagerOptional.isPresent()) {
            for (UsbDevice checkAndChangeNfcPollingAgainstUsbAudioDevice : reverseChargingController.mUsbManagerOptional.get().getDeviceList().values()) {
                reverseChargingController.checkAndChangeNfcPollingAgainstUsbAudioDevice(false, checkAndChangeNfcPollingAgainstUsbAudioDevice);
            }
        }
    }
}
