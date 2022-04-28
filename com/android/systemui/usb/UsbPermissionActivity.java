package com.android.systemui.usb;

import android.widget.CheckBox;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class UsbPermissionActivity extends UsbDialogActivity {
    public boolean mPermissionGranted = false;

    /* JADX WARNING: type inference failed for: r2v0, types: [com.android.systemui.usb.UsbPermissionActivity, com.android.systemui.usb.UsbDialogActivity, android.app.Activity] */
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
        }
        this.mPermissionGranted = true;
        finish();
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.android.systemui.usb.UsbPermissionActivity, com.android.systemui.usb.UsbDialogActivity, android.app.Activity] */
    public final void onPause() {
        if (isFinishing()) {
            this.mDialogHelper.sendPermissionDialogResponse(this.mPermissionGranted);
        }
        super.onPause();
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
                i = C1777R.string.usb_device_permission_prompt_warn;
            } else {
                i = C1777R.string.usb_device_permission_prompt;
            }
        } else {
            i = C1777R.string.usb_accessory_permission_prompt;
        }
        setAlertParams(i);
        if (!z) {
            UsbDialogHelper usbDialogHelper2 = this.mDialogHelper;
            Objects.requireNonNull(usbDialogHelper2);
            if (usbDialogHelper2.mCanBeDefault) {
                addAlwaysUseCheckbox();
            }
        }
        setupAlert();
    }
}
