package com.android.systemui.media;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.BidiFormatter;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.util.Utils;

public class MediaProjectionPermissionActivity extends Activity implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
    public AlertDialog mDialog;
    public String mPackageName;
    public IMediaProjectionManager mService;
    public int mUid;

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002f, code lost:
        if (r4 == null) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003f, code lost:
        if (r4 != null) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0041, code lost:
        r4.dismiss();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0044, code lost:
        finish();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0047, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onClick(android.content.DialogInterface r4, int r5) {
        /*
            r3 = this;
            r4 = -1
            if (r5 != r4) goto L_0x003d
            r5 = 0
            int r0 = r3.mUid     // Catch:{ RemoteException -> 0x0022 }
            java.lang.String r1 = r3.mPackageName     // Catch:{ RemoteException -> 0x0022 }
            android.media.projection.IMediaProjectionManager r2 = r3.mService     // Catch:{ RemoteException -> 0x0022 }
            android.media.projection.IMediaProjection r0 = r2.createProjection(r0, r1, r5, r5)     // Catch:{ RemoteException -> 0x0022 }
            android.content.Intent r1 = new android.content.Intent     // Catch:{ RemoteException -> 0x0022 }
            r1.<init>()     // Catch:{ RemoteException -> 0x0022 }
            android.os.IBinder r0 = r0.asBinder()     // Catch:{ RemoteException -> 0x0022 }
            java.lang.String r2 = "android.media.projection.extra.EXTRA_MEDIA_PROJECTION"
            r1.putExtra(r2, r0)     // Catch:{ RemoteException -> 0x0022 }
            r3.setResult(r4, r1)     // Catch:{ RemoteException -> 0x0022 }
            goto L_0x003d
        L_0x0020:
            r4 = move-exception
            goto L_0x0032
        L_0x0022:
            r4 = move-exception
            java.lang.String r0 = "MediaProjectionPermissionActivity"
            java.lang.String r1 = "Error granting projection permission"
            android.util.Log.e(r0, r1, r4)     // Catch:{ all -> 0x0020 }
            r3.setResult(r5)     // Catch:{ all -> 0x0020 }
            android.app.AlertDialog r4 = r3.mDialog
            if (r4 == 0) goto L_0x0044
            goto L_0x0041
        L_0x0032:
            android.app.AlertDialog r5 = r3.mDialog
            if (r5 == 0) goto L_0x0039
            r5.dismiss()
        L_0x0039:
            r3.finish()
            throw r4
        L_0x003d:
            android.app.AlertDialog r4 = r3.mDialog
            if (r4 == 0) goto L_0x0044
        L_0x0041:
            r4.dismiss()
        L_0x0044:
            r3.finish()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaProjectionPermissionActivity.onClick(android.content.DialogInterface, int):void");
    }

    public final void onCreate(Bundle bundle) {
        CharSequence charSequence;
        String str;
        super.onCreate(bundle);
        this.mPackageName = getCallingPackage();
        this.mService = IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection"));
        if (this.mPackageName == null) {
            finish();
            return;
        }
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.mPackageName, 0);
            int i = applicationInfo.uid;
            this.mUid = i;
            try {
                if (this.mService.hasProjectionPermission(i, this.mPackageName)) {
                    IMediaProjection createProjection = this.mService.createProjection(this.mUid, this.mPackageName, 0, false);
                    Intent intent = new Intent();
                    intent.putExtra("android.media.projection.extra.EXTRA_MEDIA_PROJECTION", createProjection.asBinder());
                    setResult(-1, intent);
                    finish();
                    return;
                }
                TextPaint textPaint = new TextPaint();
                textPaint.setTextSize(42.0f);
                if (Utils.isHeadlessRemoteDisplayProvider(packageManager, this.mPackageName)) {
                    charSequence = getString(C1777R.string.media_projection_dialog_service_text);
                    str = getString(C1777R.string.media_projection_dialog_service_title);
                } else {
                    String charSequence2 = applicationInfo.loadLabel(packageManager).toString();
                    int length = charSequence2.length();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        int codePointAt = charSequence2.codePointAt(i2);
                        int type = Character.getType(codePointAt);
                        if (type == 13 || type == 15 || type == 14) {
                            charSequence2 = charSequence2.substring(0, i2) + "…";
                        } else {
                            i2 += Character.charCount(codePointAt);
                        }
                    }
                    charSequence2 = charSequence2.substring(0, i2) + "…";
                    if (charSequence2.isEmpty()) {
                        charSequence2 = this.mPackageName;
                    }
                    String unicodeWrap = BidiFormatter.getInstance().unicodeWrap(TextUtils.ellipsize(charSequence2, textPaint, 500.0f, TextUtils.TruncateAt.END).toString());
                    String string = getString(C1777R.string.media_projection_dialog_text, new Object[]{unicodeWrap});
                    SpannableString spannableString = new SpannableString(string);
                    int indexOf = string.indexOf(unicodeWrap);
                    if (indexOf >= 0) {
                        spannableString.setSpan(new StyleSpan(1), indexOf, unicodeWrap.length() + indexOf, 0);
                    }
                    str = getString(C1777R.string.media_projection_dialog_title, new Object[]{unicodeWrap});
                    charSequence = spannableString;
                }
                View inflate = View.inflate(this, C1777R.layout.media_projection_dialog_title, (ViewGroup) null);
                ((TextView) inflate.findViewById(C1777R.C1779id.dialog_title)).setText(str);
                AlertDialog create = new AlertDialog.Builder(this).setCustomTitle(inflate).setMessage(charSequence).setPositiveButton(C1777R.string.media_projection_action_text, this).setNegativeButton(17039360, this).setOnCancelListener(this).create();
                this.mDialog = create;
                create.create();
                this.mDialog.getButton(-1).setFilterTouchesWhenObscured(true);
                Window window = this.mDialog.getWindow();
                window.setType(2009);
                window.addSystemFlags(524288);
                this.mDialog.show();
            } catch (RemoteException e) {
                Log.e("MediaProjectionPermissionActivity", "Error checking projection permissions", e);
                finish();
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("MediaProjectionPermissionActivity", "unable to look up package name", e2);
            finish();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public final void onCancel(DialogInterface dialogInterface) {
        finish();
    }
}
