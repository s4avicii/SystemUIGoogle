package com.android.systemui.dreams.complication;

import com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda3;
import com.android.systemui.dreams.complication.DreamWeatherComplication;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.systemui.dreams.complication.DreamWeatherComplication$DreamWeatherViewController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0791xbf1b0f76 implements BcSmartspaceDataPlugin.SmartspaceTargetListener {
    public final /* synthetic */ DreamWeatherComplication.DreamWeatherViewController f$0;

    public /* synthetic */ C0791xbf1b0f76(DreamWeatherComplication.DreamWeatherViewController dreamWeatherViewController) {
        this.f$0 = dreamWeatherViewController;
    }

    public final void onSmartspaceTargetsUpdated(List list) {
        DreamWeatherComplication.DreamWeatherViewController dreamWeatherViewController = this.f$0;
        Objects.requireNonNull(dreamWeatherViewController);
        list.forEach(new DozeTriggers$$ExternalSyntheticLambda3(dreamWeatherViewController, 2));
    }
}
