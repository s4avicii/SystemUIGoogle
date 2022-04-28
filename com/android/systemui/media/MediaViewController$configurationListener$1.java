package com.android.systemui.media;

import android.content.res.Configuration;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.animation.TransitionLayout;

/* compiled from: MediaViewController.kt */
public final class MediaViewController$configurationListener$1 implements ConfigurationController.ConfigurationListener {
    public final /* synthetic */ MediaViewController this$0;

    public MediaViewController$configurationListener$1(MediaViewController mediaViewController) {
        this.this$0 = mediaViewController;
    }

    public final void onConfigChanged(Configuration configuration) {
        if (configuration != null) {
            MediaViewController mediaViewController = this.this$0;
            TransitionLayout transitionLayout = mediaViewController.transitionLayout;
            boolean z = false;
            if (transitionLayout != null && transitionLayout.getRawLayoutDirection() == configuration.getLayoutDirection()) {
                z = true;
            }
            if (!z) {
                TransitionLayout transitionLayout2 = mediaViewController.transitionLayout;
                if (transitionLayout2 != null) {
                    transitionLayout2.setLayoutDirection(configuration.getLayoutDirection());
                }
                mediaViewController.refreshState();
            }
        }
    }
}
