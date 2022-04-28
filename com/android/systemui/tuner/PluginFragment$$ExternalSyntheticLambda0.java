package com.android.systemui.tuner;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.ArrayMap;
import android.util.ArraySet;
import androidx.preference.PreferenceScreen;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.tuner.PluginFragment;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PluginFragment$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ PluginFragment f$0;
    public final /* synthetic */ ArrayMap f$1;
    public final /* synthetic */ PluginManager f$2;
    public final /* synthetic */ Context f$3;
    public final /* synthetic */ PreferenceScreen f$4;

    public /* synthetic */ PluginFragment$$ExternalSyntheticLambda0(PluginFragment pluginFragment, ArrayMap arrayMap, PluginManager pluginManager, Context context, PreferenceScreen preferenceScreen) {
        this.f$0 = pluginFragment;
        this.f$1 = arrayMap;
        this.f$2 = pluginManager;
        this.f$3 = context;
        this.f$4 = preferenceScreen;
    }

    public final void accept(Object obj) {
        PluginFragment pluginFragment = this.f$0;
        ArrayMap arrayMap = this.f$1;
        PluginManager pluginManager = this.f$2;
        Context context = this.f$3;
        PreferenceScreen preferenceScreen = this.f$4;
        PackageInfo packageInfo = (PackageInfo) obj;
        int i = PluginFragment.$r8$clinit;
        Objects.requireNonNull(pluginFragment);
        if (arrayMap.containsKey(packageInfo.packageName) && !ArrayUtils.contains(pluginManager.getPrivilegedPlugins(), packageInfo.packageName)) {
            PluginFragment.PluginPreference pluginPreference = new PluginFragment.PluginPreference(context, packageInfo, pluginFragment.mPluginEnabler);
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Plugins: ");
            StringBuilder sb = new StringBuilder();
            Iterator it = ((ArraySet) arrayMap.get(packageInfo.packageName)).iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(str);
            }
            m.append(sb.toString());
            pluginPreference.setSummary(m.toString());
            preferenceScreen.addPreference(pluginPreference);
        }
    }
}
