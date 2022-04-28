package com.android.systemui.usb.p009tv;

import com.android.p012wm.shell.C1777R;
import com.android.systemui.usb.UsbDialogHelper;
import java.util.Objects;

/* renamed from: com.android.systemui.usb.tv.TvUsbConfirmActivity */
public class TvUsbConfirmActivity extends TvUsbDialogActivity {
    public final void onConfirm() {
        this.mDialogHelper.grantUidAccessPermission();
        this.mDialogHelper.confirmDialogStartActivity();
        finish();
    }

    public final void onResume() {
        int i;
        boolean z;
        super.onResume();
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        Objects.requireNonNull(usbDialogHelper);
        if (usbDialogHelper.mIsUsbDevice) {
            if (!this.mDialogHelper.deviceHasAudioCapture() || this.mDialogHelper.packageHasAudioRecordingPermission()) {
                z = false;
            } else {
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
        UsbDialogHelper usbDialogHelper2 = this.mDialogHelper;
        Objects.requireNonNull(usbDialogHelper2);
        String string = getString(i, new Object[]{usbDialogHelper2.mAppName, this.mDialogHelper.getDeviceDescription()});
        UsbDialogHelper usbDialogHelper3 = this.mDialogHelper;
        Objects.requireNonNull(usbDialogHelper3);
        initUI(usbDialogHelper3.mAppName, string);
    }

    static {
        Class<TvUsbConfirmActivity> cls = TvUsbConfirmActivity.class;
    }
}
