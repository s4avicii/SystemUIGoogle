package com.android.systemui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.slice.SliceManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.BidiFormatter;
import android.util.EventLog;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

public class SlicePermissionActivity extends Activity implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public CheckBox mAllCheckbox;
    public String mCallingPkg;
    public String mProviderPkg;
    public Uri mUri;

    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            ((SliceManager) getSystemService(SliceManager.class)).grantPermissionFromUser(this.mUri, this.mCallingPkg, this.mAllCheckbox.isChecked());
        }
        finish();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUri = (Uri) getIntent().getParcelableExtra("slice_uri");
        this.mCallingPkg = getIntent().getStringExtra("pkg");
        if (this.mUri == null || !"vnd.android.slice".equals(getContentResolver().getType(this.mUri)) || !"com.android.intent.action.REQUEST_SLICE_PERMISSION".equals(getIntent().getAction())) {
            Log.e("SlicePermissionActivity", "Intent is not valid");
            finish();
            return;
        }
        try {
            PackageManager packageManager = getPackageManager();
            this.mProviderPkg = packageManager.resolveContentProvider(this.mUri.getAuthority(), 128).applicationInfo.packageName;
            verifyCallingPkg();
            String unicodeWrap = BidiFormatter.getInstance().unicodeWrap(packageManager.getApplicationInfo(this.mCallingPkg, 0).loadSafeLabel(packageManager, 1000.0f, 5).toString());
            String unicodeWrap2 = BidiFormatter.getInstance().unicodeWrap(packageManager.getApplicationInfo(this.mProviderPkg, 0).loadSafeLabel(packageManager, 1000.0f, 5).toString());
            AlertDialog create = new AlertDialog.Builder(this).setTitle(getString(C1777R.string.slice_permission_title, new Object[]{unicodeWrap, unicodeWrap2})).setView(C1777R.layout.slice_permission_request).setNegativeButton(C1777R.string.slice_permission_deny, this).setPositiveButton(C1777R.string.slice_permission_allow, this).setOnDismissListener(this).create();
            create.getWindow().addSystemFlags(524288);
            create.show();
            ((TextView) create.getWindow().getDecorView().findViewById(C1777R.C1779id.text1)).setText(getString(C1777R.string.slice_permission_text_1, new Object[]{unicodeWrap2}));
            ((TextView) create.getWindow().getDecorView().findViewById(C1777R.C1779id.text2)).setText(getString(C1777R.string.slice_permission_text_2, new Object[]{unicodeWrap2}));
            CheckBox checkBox = (CheckBox) create.getWindow().getDecorView().findViewById(C1777R.C1779id.slice_permission_checkbox);
            this.mAllCheckbox = checkBox;
            checkBox.setText(getString(C1777R.string.slice_permission_checkbox, new Object[]{unicodeWrap}));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("SlicePermissionActivity", "Couldn't find package", e);
            finish();
        }
    }

    public final void verifyCallingPkg() {
        String str;
        String stringExtra = getIntent().getStringExtra("provider_pkg");
        if (stringExtra != null && !this.mProviderPkg.equals(stringExtra)) {
            Uri referrer = getReferrer();
            if (referrer == null) {
                str = null;
            } else {
                str = referrer.getHost();
            }
            Object[] objArr = new Object[2];
            objArr[0] = "159145361";
            int i = -1;
            if (str != null) {
                try {
                    i = getPackageManager().getApplicationInfo(str, 0).uid;
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            objArr[1] = Integer.valueOf(i);
            EventLog.writeEvent(1397638484, objArr);
        }
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        finish();
    }
}
