package com.android.systemui.usb;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public abstract class UsbDialogActivity extends AlertActivity implements DialogInterface.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public CheckBox mAlwaysUse;
    public TextView mClearDefaultHint;
    public UsbDialogHelper mDialogHelper;

    /* JADX WARNING: type inference failed for: r0v0, types: [com.android.systemui.usb.UsbDialogActivity, android.app.Activity] */
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            onConfirm();
        } else {
            finish();
        }
    }

    public abstract void onConfirm();

    /* JADX WARNING: type inference failed for: r7v0, types: [android.content.Context, com.android.internal.app.AlertActivity, com.android.systemui.usb.UsbDialogActivity, android.widget.CompoundButton$OnCheckedChangeListener] */
    public final void addAlwaysUseCheckbox() {
        AlertController.AlertParams alertParams = this.mAlertParams;
        View inflate = ((LayoutInflater) getSystemService(LayoutInflater.class)).inflate(17367093, (ViewGroup) null);
        alertParams.mView = inflate;
        this.mAlwaysUse = (CheckBox) inflate.findViewById(16908772);
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        Objects.requireNonNull(usbDialogHelper);
        if (!usbDialogHelper.mIsUsbDevice) {
            CheckBox checkBox = this.mAlwaysUse;
            UsbDialogHelper usbDialogHelper2 = this.mDialogHelper;
            Objects.requireNonNull(usbDialogHelper2);
            checkBox.setText(getString(C1777R.string.always_use_accessory, new Object[]{usbDialogHelper2.mAppName, this.mDialogHelper.getDeviceDescription()}));
        } else {
            CheckBox checkBox2 = this.mAlwaysUse;
            UsbDialogHelper usbDialogHelper3 = this.mDialogHelper;
            Objects.requireNonNull(usbDialogHelper3);
            checkBox2.setText(getString(C1777R.string.always_use_device, new Object[]{usbDialogHelper3.mAppName, this.mDialogHelper.getDeviceDescription()}));
        }
        this.mAlwaysUse.setOnCheckedChangeListener(this);
        TextView textView = (TextView) alertParams.mView.findViewById(16908876);
        this.mClearDefaultHint = textView;
        textView.setVisibility(8);
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        TextView textView = this.mClearDefaultHint;
        if (textView != null) {
            if (z) {
                textView.setVisibility(0);
            } else {
                textView.setVisibility(8);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [android.content.Context, com.android.systemui.usb.UsbDialogActivity, android.app.Activity] */
    public void onPause() {
        UsbDisconnectedReceiver usbDisconnectedReceiver;
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        if (!(usbDialogHelper == null || (usbDisconnectedReceiver = usbDialogHelper.mDisconnectedReceiver) == null)) {
            try {
                unregisterReceiver(usbDisconnectedReceiver);
            } catch (Exception unused) {
            }
            usbDialogHelper.mDisconnectedReceiver = null;
        }
        UsbDialogActivity.super.onPause();
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [android.content.Context, android.content.DialogInterface$OnClickListener, com.android.internal.app.AlertActivity, com.android.systemui.usb.UsbDialogActivity] */
    public final void setAlertParams(int i) {
        AlertController.AlertParams alertParams = this.mAlertParams;
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        Objects.requireNonNull(usbDialogHelper);
        alertParams.mTitle = usbDialogHelper.mAppName;
        UsbDialogHelper usbDialogHelper2 = this.mDialogHelper;
        Objects.requireNonNull(usbDialogHelper2);
        alertParams.mMessage = getString(i, new Object[]{usbDialogHelper2.mAppName, this.mDialogHelper.getDeviceDescription()});
        alertParams.mPositiveButtonText = getString(17039370);
        alertParams.mNegativeButtonText = getString(17039360);
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonListener = this;
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [android.content.Context, com.android.internal.app.AlertActivity, com.android.systemui.usb.UsbDialogActivity, android.app.Activity] */
    public final void onCreate(Bundle bundle) {
        UsbDialogActivity.super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        try {
            this.mDialogHelper = new UsbDialogHelper(getApplicationContext(), getIntent());
        } catch (IllegalStateException e) {
            Log.e("UsbDialogActivity", "unable to initialize", e);
            finish();
        }
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [com.android.systemui.usb.UsbDialogActivity, android.app.Activity] */
    public void onResume() {
        UsbDialogActivity.super.onResume();
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        Objects.requireNonNull(usbDialogHelper);
        if (usbDialogHelper.mIsUsbDevice) {
            usbDialogHelper.mDisconnectedReceiver = new UsbDisconnectedReceiver((Activity) this, usbDialogHelper.mDevice);
        } else {
            usbDialogHelper.mDisconnectedReceiver = new UsbDisconnectedReceiver((Activity) this, usbDialogHelper.mAccessory);
        }
    }

    static {
        Class<UsbDialogActivity> cls = UsbDialogActivity.class;
    }
}
