package com.android.systemui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Objects;

public final class ForegroundServicesDialog extends AlertActivity implements AdapterView.OnItemSelectedListener, DialogInterface.OnClickListener, AlertController.AlertParams.OnPrepareListViewListener {
    public PackageItemAdapter mAdapter;
    public C06361 mAppClickListener = new DialogInterface.OnClickListener() {
        /* JADX WARNING: type inference failed for: r3v7, types: [android.content.Context, com.android.systemui.ForegroundServicesDialog] */
        /* JADX WARNING: type inference failed for: r2v1, types: [com.android.systemui.ForegroundServicesDialog, android.app.Activity] */
        public final void onClick(DialogInterface dialogInterface, int i) {
            String str = ((ApplicationInfo) ForegroundServicesDialog.this.mAdapter.getItem(i)).packageName;
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", str, (String) null));
            ForegroundServicesDialog.this.startActivity(intent);
            ForegroundServicesDialog.this.finish();
        }
    };
    public LayoutInflater mInflater;
    public final MetricsLogger mMetricsLogger;
    public String[] mPackages;

    public static class PackageItemAdapter extends ArrayAdapter<ApplicationInfo> {
        public final IconDrawableFactory mIconDrawableFactory;
        public final LayoutInflater mInflater;
        public final PackageManager mPm;

        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(C1777R.layout.foreground_service_item, viewGroup, false);
            }
            ((ImageView) view.findViewById(C1777R.C1779id.app_icon)).setImageDrawable(this.mIconDrawableFactory.getBadgedIcon((ApplicationInfo) getItem(i)));
            ((TextView) view.findViewById(C1777R.C1779id.app_name)).setText(((ApplicationInfo) getItem(i)).loadLabel(this.mPm));
            return view;
        }

        public PackageItemAdapter(Context context) {
            super(context, C1777R.layout.foreground_service_item);
            this.mPm = context.getPackageManager();
            this.mInflater = LayoutInflater.from(context);
            this.mIconDrawableFactory = IconDrawableFactory.newInstance(context, true);
        }
    }

    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
    }

    public final void onNothingSelected(AdapterView adapterView) {
    }

    public final void onPrepareListView(ListView listView) {
    }

    public final void updateApps(Intent intent) {
        String[] stringArrayExtra = intent.getStringArrayExtra("packages");
        this.mPackages = stringArrayExtra;
        if (stringArrayExtra != null) {
            PackageItemAdapter packageItemAdapter = this.mAdapter;
            Objects.requireNonNull(packageItemAdapter);
            packageItemAdapter.clear();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < stringArrayExtra.length; i++) {
                try {
                    arrayList.add(packageItemAdapter.mPm.getApplicationInfo(stringArrayExtra[i], 4202496));
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            arrayList.sort(new ApplicationInfo.DisplayNameComparator(packageItemAdapter.mPm));
            packageItemAdapter.addAll(arrayList);
        }
    }

    public ForegroundServicesDialog(MetricsLogger metricsLogger) {
        this.mMetricsLogger = metricsLogger;
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [android.content.Context, android.content.DialogInterface$OnClickListener, com.android.internal.app.AlertActivity, com.android.systemui.ForegroundServicesDialog, com.android.internal.app.AlertController$AlertParams$OnPrepareListViewListener, android.app.Activity, android.widget.AdapterView$OnItemSelectedListener] */
    public final void onCreate(Bundle bundle) {
        ForegroundServicesDialog.super.onCreate(bundle);
        this.mInflater = LayoutInflater.from(this);
        PackageItemAdapter packageItemAdapter = new PackageItemAdapter(this);
        this.mAdapter = packageItemAdapter;
        AlertController.AlertParams alertParams = this.mAlertParams;
        alertParams.mAdapter = packageItemAdapter;
        alertParams.mOnClickListener = this.mAppClickListener;
        alertParams.mCustomTitleView = this.mInflater.inflate(C1777R.layout.foreground_service_title, (ViewGroup) null);
        alertParams.mIsSingleChoice = true;
        alertParams.mOnItemSelectedListener = this;
        alertParams.mPositiveButtonText = getString(17040151);
        alertParams.mPositiveButtonListener = this;
        alertParams.mOnPrepareListViewListener = this;
        updateApps(getIntent());
        if (this.mPackages == null) {
            Log.w("ForegroundServicesDialog", "No packages supplied");
            finish();
            return;
        }
        setupAlert();
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [com.android.systemui.ForegroundServicesDialog, android.app.Activity] */
    public final void onNewIntent(Intent intent) {
        ForegroundServicesDialog.super.onNewIntent(intent);
        updateApps(intent);
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [com.android.systemui.ForegroundServicesDialog, android.app.Activity] */
    public final void onPause() {
        ForegroundServicesDialog.super.onPause();
        this.mMetricsLogger.hidden(944);
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [com.android.systemui.ForegroundServicesDialog, android.app.Activity] */
    public final void onResume() {
        ForegroundServicesDialog.super.onResume();
        this.mMetricsLogger.visible(944);
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [com.android.systemui.ForegroundServicesDialog, android.app.Activity] */
    public final void onStop() {
        ForegroundServicesDialog.super.onStop();
        if (!isChangingConfigurations()) {
            finish();
        }
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [com.android.systemui.ForegroundServicesDialog, android.app.Activity] */
    public final void onClick(DialogInterface dialogInterface, int i) {
        finish();
    }
}
