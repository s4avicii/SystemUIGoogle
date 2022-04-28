package com.android.systemui.usb;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.usb.UsbAccessory;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.p012wm.shell.C1777R;

public class UsbAccessoryUriActivity extends AlertActivity implements DialogInterface.OnClickListener {
    public UsbAccessory mAccessory;
    public Uri mUri;

    /* JADX WARNING: type inference failed for: r1v0, types: [com.android.internal.app.AlertActivity, com.android.systemui.usb.UsbAccessoryUriActivity, android.app.Activity] */
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            Intent intent = new Intent("android.intent.action.VIEW", this.mUri);
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.addFlags(268435456);
            try {
                startActivityAsUser(intent, UserHandle.CURRENT);
            } catch (ActivityNotFoundException unused) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("startActivity failed for ");
                m.append(this.mUri);
                Log.e("UsbAccessoryUriActivity", m.toString());
            }
        }
        finish();
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [android.content.Context, android.content.DialogInterface$OnClickListener, com.android.internal.app.AlertActivity, com.android.systemui.usb.UsbAccessoryUriActivity, android.app.Activity] */
    public final void onCreate(Bundle bundle) {
        Uri uri;
        getWindow().addSystemFlags(524288);
        UsbAccessoryUriActivity.super.onCreate(bundle);
        Intent intent = getIntent();
        this.mAccessory = (UsbAccessory) intent.getParcelableExtra("accessory");
        String stringExtra = intent.getStringExtra("uri");
        if (stringExtra == null) {
            uri = null;
        } else {
            uri = Uri.parse(stringExtra);
        }
        this.mUri = uri;
        if (uri == null) {
            Log.e("UsbAccessoryUriActivity", "could not parse Uri " + stringExtra);
            finish();
            return;
        }
        String scheme = uri.getScheme();
        if ("http".equals(scheme) || "https".equals(scheme)) {
            AlertController.AlertParams alertParams = this.mAlertParams;
            String description = this.mAccessory.getDescription();
            alertParams.mTitle = description;
            if (description == null || description.length() == 0) {
                alertParams.mTitle = getString(C1777R.string.title_usb_accessory);
            }
            alertParams.mMessage = getString(C1777R.string.usb_accessory_uri_prompt, new Object[]{this.mUri});
            alertParams.mPositiveButtonText = getString(C1777R.string.label_view);
            alertParams.mNegativeButtonText = getString(17039360);
            alertParams.mPositiveButtonListener = this;
            alertParams.mNegativeButtonListener = this;
            setupAlert();
            return;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Uri not http or https: ");
        m.append(this.mUri);
        Log.e("UsbAccessoryUriActivity", m.toString());
        finish();
    }
}
