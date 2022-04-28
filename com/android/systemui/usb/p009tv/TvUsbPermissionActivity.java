package com.android.systemui.usb.p009tv;

import com.android.p012wm.shell.C1777R;
import com.android.systemui.usb.UsbDialogHelper;
import java.util.Objects;

/* renamed from: com.android.systemui.usb.tv.TvUsbPermissionActivity */
public class TvUsbPermissionActivity extends TvUsbDialogActivity {
    public boolean mPermissionGranted = false;

    public final void onConfirm() {
        this.mDialogHelper.grantUidAccessPermission();
        this.mPermissionGranted = true;
        finish();
    }

    public final void onPause() {
        if (isFinishing()) {
            this.mDialogHelper.sendPermissionDialogResponse(this.mPermissionGranted);
        }
        super.onPause();
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
                i = C1777R.string.usb_device_permission_prompt_warn;
            } else {
                i = C1777R.string.usb_device_permission_prompt;
            }
        } else {
            i = C1777R.string.usb_accessory_permission_prompt;
        }
        UsbDialogHelper usbDialogHelper2 = this.mDialogHelper;
        Objects.requireNonNull(usbDialogHelper2);
        String string = getString(i, new Object[]{usbDialogHelper2.mAppName, this.mDialogHelper.getDeviceDescription()});
        UsbDialogHelper usbDialogHelper3 = this.mDialogHelper;
        Objects.requireNonNull(usbDialogHelper3);
        initUI(usbDialogHelper3.mAppName, string);
    }

    static {
        Class<TvUsbPermissionActivity> cls = TvUsbPermissionActivity.class;
    }
}
