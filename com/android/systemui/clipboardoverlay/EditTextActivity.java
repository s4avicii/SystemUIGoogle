package com.android.systemui.clipboardoverlay;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.pm.PackageManager;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0;
import com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda5;
import java.util.Objects;

public class EditTextActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TextView mAttribution;
    public ClipboardManager mClipboardManager;
    public EditText mEditText;

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1777R.layout.clipboard_edit_text_activity);
        findViewById(C1777R.C1779id.copy_button).setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda5(this, 2));
        findViewById(C1777R.C1779id.share).setOnClickListener(new LayoutPreference$$ExternalSyntheticLambda0(this, 2));
        this.mEditText = (EditText) findViewById(C1777R.C1779id.edit_text);
        this.mAttribution = (TextView) findViewById(C1777R.C1779id.attribution);
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(ClipboardManager.class);
        Objects.requireNonNull(clipboardManager);
        ClipboardManager clipboardManager2 = clipboardManager;
        this.mClipboardManager = clipboardManager;
    }

    public final void onStart() {
        super.onStart();
        ClipData primaryClip = this.mClipboardManager.getPrimaryClip();
        if (primaryClip == null) {
            finish();
            return;
        }
        PackageManager packageManager = getApplicationContext().getPackageManager();
        try {
            CharSequence applicationLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.mClipboardManager.getPrimaryClipSource(), PackageManager.ApplicationInfoFlags.of(0)));
            this.mAttribution.setText(getResources().getString(C1777R.string.clipboard_edit_source, new Object[]{applicationLabel}));
        } catch (PackageManager.NameNotFoundException e) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Package not found: ");
            m.append(this.mClipboardManager.getPrimaryClipSource());
            Log.w("EditTextActivity", m.toString(), e);
        }
        this.mEditText.setText(primaryClip.getItemAt(0).getText());
        this.mEditText.requestFocus();
    }
}
