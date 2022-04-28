package com.android.systemui.usb.p009tv;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p008tv.TvBottomSheetActivity;
import com.android.systemui.usb.UsbDialogHelper;
import com.android.systemui.usb.UsbDisconnectedReceiver;
import java.util.Objects;

/* renamed from: com.android.systemui.usb.tv.TvUsbDialogActivity */
public abstract class TvUsbDialogActivity extends TvBottomSheetActivity implements View.OnClickListener {
    public UsbDialogHelper mDialogHelper;

    public abstract void onConfirm();

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
        super.onPause();
    }

    public final void initUI(CharSequence charSequence, CharSequence charSequence2) {
        Button button = (Button) findViewById(C1777R.C1779id.bottom_sheet_positive_button);
        Button button2 = (Button) findViewById(C1777R.C1779id.bottom_sheet_negative_button);
        ((TextView) findViewById(C1777R.C1779id.bottom_sheet_title)).setText(charSequence);
        ((TextView) findViewById(C1777R.C1779id.bottom_sheet_body)).setText(charSequence2);
        ((ImageView) findViewById(C1777R.C1779id.bottom_sheet_icon)).setImageResource(17302876);
        ((ImageView) findViewById(C1777R.C1779id.bottom_sheet_second_icon)).setVisibility(8);
        button.setText(17039370);
        button.setOnClickListener(this);
        button2.setText(17039360);
        button2.setOnClickListener(this);
        button2.requestFocus();
    }

    public final void onClick(View view) {
        if (view.getId() == C1777R.C1779id.bottom_sheet_positive_button) {
            onConfirm();
        } else {
            finish();
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addPrivateFlags(524288);
        try {
            this.mDialogHelper = new UsbDialogHelper(getApplicationContext(), getIntent());
        } catch (IllegalStateException e) {
            Log.e("TvUsbDialogActivity", "unable to initialize", e);
            finish();
        }
    }

    public void onResume() {
        super.onResume();
        UsbDialogHelper usbDialogHelper = this.mDialogHelper;
        Objects.requireNonNull(usbDialogHelper);
        if (usbDialogHelper.mIsUsbDevice) {
            usbDialogHelper.mDisconnectedReceiver = new UsbDisconnectedReceiver((Activity) this, usbDialogHelper.mDevice);
        } else {
            usbDialogHelper.mDisconnectedReceiver = new UsbDisconnectedReceiver((Activity) this, usbDialogHelper.mAccessory);
        }
    }

    static {
        Class<TvUsbDialogActivity> cls = TvUsbDialogActivity.class;
    }
}
