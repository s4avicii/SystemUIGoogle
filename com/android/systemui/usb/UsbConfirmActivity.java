package com.android.systemui.usb;

import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.widget.CheckBox;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class UsbConfirmActivity extends UsbDialogActivity {
    /* JADX WARNING: type inference failed for: r4v0, types: [com.android.systemui.usb.UsbDialogActivity, android.app.Activity, com.android.systemui.usb.UsbConfirmActivity] */
    public final void onConfirm() {
        boolean z;
        this.mDialogHelper.grantUidAccessPermission();
        CheckBox checkBox = this.mAlwaysUse;
        if (checkBox == null || !checkBox.isChecked()) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            this.mDialogHelper.setDefaultPackage();
        } else {
            UsbDialogHelper usbDialogHelper = this.mDialogHelper;
            Objects.requireNonNull(usbDialogHelper);
            int myUserId = UserHandle.myUserId();
            try {
                if (usbDialogHelper.mIsUsbDevice) {
                    usbDialogHelper.mUsbService.setDevicePackage(usbDialogHelper.mDevice, (String) null, myUserId);
                } else {
                    usbDialogHelper.mUsbService.setAccessoryPackage(usbDialogHelper.mAccessory, (String) null, myUserId);
                }
            } catch (RemoteException e) {
                Log.e("UsbDialogHelper", "IUsbService connection failed", e);
            }
        }
        this.mDialogHelper.confirmDialogStartActivity();
        finish();
    }

    public final void onResume() {
        int i;
        super.onResume();
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        Objects.requireNonNull(usbDialogHelper);
        boolean z = false;
        if (usbDialogHelper.mIsUsbDevice) {
            if (this.mDialogHelper.deviceHasAudioCapture() && !this.mDialogHelper.packageHasAudioRecordingPermission()) {
                z = true;
            }
            if (z) {
                i = C1777R.string.usb_device_confirm_prompt_warn;
            } else {
                i = C1777R.string.usb_device_confirm_prompt;
            }
        } else {
            i = C1777R.string.usb_accessory_confirm_prompt;
        }
        setAlertParams(i);
        if (!z) {
            addAlwaysUseCheckbox();
        }
        setupAlert();
    }
}
