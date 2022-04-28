package com.android.p012wm.shell.onehanded;

import android.content.res.Configuration;
import android.os.SystemProperties;
import com.android.systemui.wmshell.WMShell;

/* renamed from: com.android.wm.shell.onehanded.OneHanded */
public interface OneHanded {
    public static final boolean sIsSupportOneHandedMode = SystemProperties.getBoolean("ro.support_one_handed_mode", false);

    IOneHanded createExternalInterface() {
        return null;
    }

    void onConfigChanged(Configuration configuration);

    void onKeyguardVisibilityChanged(boolean z);

    void onUserSwitch(int i);

    void registerEventCallback(WMShell.C17718 r1);

    void registerTransitionCallback(OneHandedTransitionCallback oneHandedTransitionCallback);

    void setLockedDisabled(boolean z);

    void stopOneHanded();

    void stopOneHanded(int i);
}
