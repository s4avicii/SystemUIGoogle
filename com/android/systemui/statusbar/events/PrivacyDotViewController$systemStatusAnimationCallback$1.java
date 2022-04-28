package com.android.systemui.statusbar.events;

import android.graphics.Rect;
import android.view.View;

/* compiled from: PrivacyDotViewController.kt */
public final class PrivacyDotViewController$systemStatusAnimationCallback$1 implements SystemStatusAnimationCallback {
    public final /* synthetic */ PrivacyDotViewController this$0;

    public PrivacyDotViewController$systemStatusAnimationCallback$1(PrivacyDotViewController privacyDotViewController) {
        this.this$0 = privacyDotViewController;
    }

    public final void onHidePersistentDot() {
        PrivacyDotViewController privacyDotViewController = this.this$0;
        synchronized (privacyDotViewController.lock) {
            privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, (Rect) null, (Rect) null, (Rect) null, (Rect) null, false, 0, 0, 0, (View) null, (String) null, 16381));
        }
    }

    public final void onSystemStatusAnimationTransitionToPersistentDot(String str) {
        PrivacyDotViewController privacyDotViewController = this.this$0;
        synchronized (privacyDotViewController.lock) {
            privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, true, false, false, (Rect) null, (Rect) null, (Rect) null, (Rect) null, false, 0, 0, 0, (View) null, str, 8189));
        }
    }
}
