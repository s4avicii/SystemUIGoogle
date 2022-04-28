package com.google.android.systemui.smartspace;

import android.content.ComponentName;
import android.content.Context;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.util.Objects;

/* compiled from: KeyguardSmartspaceController.kt */
public final class KeyguardSmartspaceController {
    public final FeatureFlags featureFlags;

    public KeyguardSmartspaceController(Context context, FeatureFlags featureFlags2, KeyguardZenAlarmViewController keyguardZenAlarmViewController, KeyguardMediaViewController keyguardMediaViewController) {
        this.featureFlags = featureFlags2;
        if (!featureFlags2.isEnabled(Flags.SMARTSPACE)) {
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(ThemeOverlayApplier.SYSUI_PACKAGE, "com.google.android.systemui.keyguard.KeyguardSliceProviderGoogle"), 1, 1);
            return;
        }
        Objects.requireNonNull(keyguardZenAlarmViewController);
        keyguardZenAlarmViewController.plugin.addOnAttachStateChangeListener(new KeyguardZenAlarmViewController$init$1(keyguardZenAlarmViewController));
        keyguardZenAlarmViewController.updateNextAlarm();
        Objects.requireNonNull(keyguardMediaViewController);
        keyguardMediaViewController.plugin.addOnAttachStateChangeListener(new KeyguardMediaViewController$init$1(keyguardMediaViewController));
        keyguardMediaViewController.userTracker = new KeyguardMediaViewController$init$2(keyguardMediaViewController, keyguardMediaViewController.broadcastDispatcher);
    }
}
