package com.android.systemui.statusbar.phone;

import android.content.res.Configuration;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* compiled from: PhoneStatusBarViewController.kt */
public final class PhoneStatusBarViewController$configurationListener$1 implements ConfigurationController.ConfigurationListener {
    public final /* synthetic */ PhoneStatusBarViewController this$0;

    public PhoneStatusBarViewController$configurationListener$1(PhoneStatusBarViewController phoneStatusBarViewController) {
        this.this$0 = phoneStatusBarViewController;
    }

    public final void onConfigChanged(Configuration configuration) {
        ((PhoneStatusBarView) this.this$0.mView).updateResources();
    }
}
