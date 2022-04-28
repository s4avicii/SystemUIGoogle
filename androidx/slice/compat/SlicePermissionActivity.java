package androidx.slice.compat;

import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.Html;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.BidiFormatter;
import androidx.slice.compat.SliceProviderCompat;
import com.android.p012wm.shell.C1777R;

public class SlicePermissionActivity extends AppCompatActivity implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public String mCallingPkg;
    public AlertDialog mDialog;
    public String mProviderPkg;
    public Uri mUri;

    public final void onClick(DialogInterface dialogInterface, int i) {
        SliceProviderCompat.ProviderHolder acquireClient;
        if (i == -1) {
            String packageName = getPackageName();
            String str = this.mCallingPkg;
            Uri build = this.mUri.buildUpon().path("").build();
            try {
                acquireClient = SliceProviderCompat.acquireClient(getContentResolver(), build);
                Bundle bundle = new Bundle();
                bundle.putParcelable("slice_uri", build);
                bundle.putString("provider_pkg", packageName);
                bundle.putString("pkg", str);
                acquireClient.mProvider.call("grant_perms", "supports_versioned_parcelable", bundle);
                acquireClient.close();
            } catch (RemoteException e) {
                Log.e("SliceProviderCompat", "Unable to get slice descendants", e);
            } catch (Throwable th) {
                th.addSuppressed(th);
            }
        }
        finish();
        return;
        throw th;
    }

    public static CharSequence loadSafeLabel(PackageManager packageManager, ApplicationInfo applicationInfo) {
        String obj = Html.fromHtml(applicationInfo.loadLabel(packageManager).toString()).toString();
        int length = obj.length();
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int codePointAt = obj.codePointAt(i);
            int type = Character.getType(codePointAt);
            if (type == 13 || type == 15 || type == 14) {
                obj = obj.substring(0, i);
            } else {
                if (type == 12) {
                    obj = obj.substring(0, i) + " " + obj.substring(Character.charCount(codePointAt) + i);
                }
                i += Character.charCount(codePointAt);
            }
        }
        String trim = obj.trim();
        if (trim.isEmpty()) {
            return applicationInfo.packageName;
        }
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(42.0f);
        return TextUtils.ellipsize(trim, textPaint, 500.0f, TextUtils.TruncateAt.END);
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUri = (Uri) getIntent().getParcelableExtra("slice_uri");
        this.mCallingPkg = getIntent().getStringExtra("pkg");
        this.mProviderPkg = getIntent().getStringExtra("provider_pkg");
        try {
            PackageManager packageManager = getPackageManager();
            String unicodeWrap = BidiFormatter.getInstance().unicodeWrap(loadSafeLabel(packageManager, packageManager.getApplicationInfo(this.mCallingPkg, 0)).toString());
            String unicodeWrap2 = BidiFormatter.getInstance().unicodeWrap(loadSafeLabel(packageManager, packageManager.getApplicationInfo(this.mProviderPkg, 0)).toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String string = getString(C1777R.string.abc_slice_permission_title, new Object[]{unicodeWrap, unicodeWrap2});
            AlertController.AlertParams alertParams = builder.f2P;
            alertParams.mTitle = string;
            alertParams.mViewLayoutResId = C1777R.layout.abc_slice_permission_request;
            alertParams.mNegativeButtonText = alertParams.mContext.getText(C1777R.string.abc_slice_permission_deny);
            AlertController.AlertParams alertParams2 = builder.f2P;
            alertParams2.mNegativeButtonListener = this;
            alertParams2.mPositiveButtonText = alertParams2.mContext.getText(C1777R.string.abc_slice_permission_allow);
            AlertController.AlertParams alertParams3 = builder.f2P;
            alertParams3.mPositiveButtonListener = this;
            alertParams3.mOnDismissListener = this;
            AlertDialog create = builder.create();
            create.show();
            this.mDialog = create;
            ((TextView) create.getWindow().getDecorView().findViewById(C1777R.C1779id.text1)).setText(getString(C1777R.string.abc_slice_permission_text_1, new Object[]{unicodeWrap2}));
            ((TextView) this.mDialog.getWindow().getDecorView().findViewById(C1777R.C1779id.text2)).setText(getString(C1777R.string.abc_slice_permission_text_2, new Object[]{unicodeWrap2}));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("SlicePermissionActivity", "Couldn't find package", e);
            finish();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDialog.cancel();
        }
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        finish();
    }
}
