package com.android.systemui.chooser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;

public final class ChooserActivity extends Activity {
    /* JADX INFO: finally extract failed */
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Intent intent2 = (Intent) intent.getParcelableExtra("android.intent.extra.INTENT");
        Bundle bundle2 = (Bundle) intent.getParcelableExtra("android.app.extra.OPTIONS");
        IBinder binder = extras.getBinder("android.app.extra.PERMISSION_TOKEN");
        boolean booleanExtra = intent.getBooleanExtra("android.app.extra.EXTRA_IGNORE_TARGET_SECURITY", false);
        int intExtra = intent.getIntExtra("android.intent.extra.USER_ID", -1);
        StrictMode.disableDeathOnFileUriExposure();
        try {
            startActivityAsCaller(intent2, bundle2, binder, booleanExtra, intExtra);
            StrictMode.enableDeathOnFileUriExposure();
            finish();
        } catch (Throwable th) {
            StrictMode.enableDeathOnFileUriExposure();
            throw th;
        }
    }
}
