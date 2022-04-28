package com.android.systemui.tuner;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.PluginEnablerImpl;
import com.android.systemui.shared.plugins.PluginEnabler;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.shared.plugins.PluginPrefs;
import java.util.Iterator;
import java.util.Objects;

public class PluginFragment extends PreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public PluginEnablerImpl mPluginEnabler;
    public PluginPrefs mPluginPrefs;
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            PluginFragment pluginFragment = PluginFragment.this;
            int i = PluginFragment.$r8$clinit;
            pluginFragment.loadPrefs();
        }
    };

    public static class PluginPreference extends SwitchPreference {
        public final boolean mHasSettings;
        public final PackageInfo mInfo;
        public final PluginEnabler mPluginEnabler;

        public final boolean persistBoolean(boolean z) {
            PackageInfo packageInfo;
            int i = 0;
            boolean z2 = false;
            while (true) {
                packageInfo = this.mInfo;
                if (i >= packageInfo.services.length) {
                    break;
                }
                PackageInfo packageInfo2 = this.mInfo;
                ComponentName componentName = new ComponentName(packageInfo2.packageName, packageInfo2.services[i].name);
                if (this.mPluginEnabler.isEnabled(componentName) != z) {
                    if (z) {
                        this.mPluginEnabler.setEnabled(componentName);
                    } else {
                        this.mPluginEnabler.setDisabled(componentName, 1);
                    }
                    z2 = true;
                }
                i++;
            }
            if (z2) {
                String str = packageInfo.packageName;
                Uri uri = null;
                if (str != null) {
                    uri = Uri.fromParts("package", str, (String) null);
                }
                this.mContext.sendBroadcast(new Intent("com.android.systemui.action.PLUGIN_CHANGED", uri));
            }
            return true;
        }

        public PluginPreference(Context context, PackageInfo packageInfo, PluginEnablerImpl pluginEnablerImpl) {
            super(context);
            boolean z;
            PackageManager packageManager = context.getPackageManager();
            boolean z2 = false;
            if (packageManager.resolveActivity(new Intent("com.android.systemui.action.PLUGIN_SETTINGS").setPackage(packageInfo.packageName), 0) != null) {
                z = true;
            } else {
                z = false;
            }
            this.mHasSettings = z;
            this.mInfo = packageInfo;
            this.mPluginEnabler = pluginEnablerImpl;
            setTitle(packageInfo.applicationInfo.loadLabel(packageManager));
            int i = 0;
            while (true) {
                if (i >= this.mInfo.services.length) {
                    z2 = true;
                    break;
                }
                PackageInfo packageInfo2 = this.mInfo;
                if (!this.mPluginEnabler.isEnabled(new ComponentName(packageInfo2.packageName, packageInfo2.services[i].name))) {
                    break;
                }
                i++;
            }
            setChecked(z2);
            this.mWidgetLayoutResId = C1777R.layout.tuner_widget_settings_switch;
        }

        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            int i;
            super.onBindViewHolder(preferenceViewHolder);
            View findViewById = preferenceViewHolder.findViewById(C1777R.C1779id.settings);
            int i2 = 0;
            if (this.mHasSettings) {
                i = 0;
            } else {
                i = 8;
            }
            findViewById.setVisibility(i);
            View findViewById2 = preferenceViewHolder.findViewById(C1777R.C1779id.divider);
            if (!this.mHasSettings) {
                i2 = 8;
            }
            findViewById2.setVisibility(i2);
            preferenceViewHolder.findViewById(C1777R.C1779id.settings).setOnClickListener(new PluginFragment$PluginPreference$$ExternalSyntheticLambda0(this));
            preferenceViewHolder.itemView.setOnLongClickListener(new PluginFragment$PluginPreference$$ExternalSyntheticLambda1(this));
        }
    }

    public final void loadPrefs() {
        PluginManager pluginManager = (PluginManager) Dependency.get(PluginManager.class);
        PreferenceManager preferenceManager = this.mPreferenceManager;
        Context context = getContext();
        Objects.requireNonNull(preferenceManager);
        PreferenceScreen preferenceScreen = new PreferenceScreen(context, (AttributeSet) null);
        preferenceScreen.onAttachedToHierarchy(preferenceManager);
        preferenceScreen.mOrderingAsAdded = false;
        PreferenceManager preferenceManager2 = this.mPreferenceManager;
        Objects.requireNonNull(preferenceManager2);
        Context context2 = preferenceManager2.mContext;
        this.mPluginPrefs = new PluginPrefs(getContext());
        PackageManager packageManager = getContext().getPackageManager();
        PluginPrefs pluginPrefs = this.mPluginPrefs;
        Objects.requireNonNull(pluginPrefs);
        ArraySet arraySet = new ArraySet(pluginPrefs.mPluginActions);
        ArrayMap arrayMap = new ArrayMap();
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            String replace = str.replace("com.android.systemui.action.PLUGIN_", "");
            StringBuilder sb = new StringBuilder();
            for (String str2 : replace.split("_")) {
                if (sb.length() != 0) {
                    sb.append(' ');
                }
                sb.append(str2.substring(0, 1));
                sb.append(str2.substring(1).toLowerCase());
            }
            String sb2 = sb.toString();
            for (ResolveInfo resolveInfo : packageManager.queryIntentServices(new Intent(str), 512)) {
                String str3 = resolveInfo.serviceInfo.packageName;
                if (!arrayMap.containsKey(str3)) {
                    arrayMap.put(str3, new ArraySet());
                }
                ((ArraySet) arrayMap.get(str3)).add(sb2);
            }
        }
        packageManager.getPackagesHoldingPermissions(new String[]{"com.android.systemui.permission.PLUGIN"}, 516).forEach(new PluginFragment$$ExternalSyntheticLambda0(this, arrayMap, pluginManager, context2, preferenceScreen));
        setPreferenceScreen(preferenceScreen);
    }

    public final void onCreatePreferences(String str) {
        this.mPluginEnabler = new PluginEnablerImpl(getContext());
        loadPrefs();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        getContext().registerReceiver(this.mReceiver, intentFilter);
        getContext().registerReceiver(this.mReceiver, new IntentFilter("android.intent.action.USER_UNLOCKED"));
    }

    public final void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(this.mReceiver);
    }
}
