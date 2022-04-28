package com.android.systemui.tuner;

import androidx.preference.Preference;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.ActivityStarter;
import java.io.Serializable;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda2 implements ActivityStarter.OnDismissAction, Preference.OnPreferenceChangeListener {
    public static final /* synthetic */ NavBarTuner$$ExternalSyntheticLambda2 INSTANCE = new NavBarTuner$$ExternalSyntheticLambda2();
    public static final /* synthetic */ NavBarTuner$$ExternalSyntheticLambda2 INSTANCE$1 = new NavBarTuner$$ExternalSyntheticLambda2();

    public boolean onDismiss() {
        return false;
    }

    public boolean onPreferenceChange(Preference preference, Serializable serializable) {
        int[][] iArr = NavBarTuner.ICONS;
        String str = (String) serializable;
        if ("default".equals(str)) {
            str = null;
        }
        ((TunerService) Dependency.get(TunerService.class)).setValue("sysui_nav_bar", str);
        return true;
    }
}
