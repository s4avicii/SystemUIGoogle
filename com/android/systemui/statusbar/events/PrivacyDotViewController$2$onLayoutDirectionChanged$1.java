package com.android.systemui.statusbar.events;

import android.graphics.Rect;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import java.util.Objects;

/* compiled from: PrivacyDotViewController.kt */
public final class PrivacyDotViewController$2$onLayoutDirectionChanged$1 implements Runnable {
    public final /* synthetic */ boolean $isRtl;
    public final /* synthetic */ PrivacyDotViewController this$0;
    public final /* synthetic */ PrivacyDotViewController.C12122 this$1;

    public PrivacyDotViewController$2$onLayoutDirectionChanged$1(PrivacyDotViewController privacyDotViewController, PrivacyDotViewController.C12122 r2, boolean z) {
        this.this$0 = privacyDotViewController;
        this.this$1 = r2;
        this.$isRtl = z;
    }

    public final void run() {
        this.this$0.setCornerVisibilities();
        PrivacyDotViewController.C12122 r1 = this.this$1;
        PrivacyDotViewController privacyDotViewController = this.this$0;
        boolean z = this.$isRtl;
        synchronized (r1) {
            ViewState viewState = privacyDotViewController.nextViewState;
            Objects.requireNonNull(viewState);
            privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, (Rect) null, (Rect) null, (Rect) null, (Rect) null, z, 0, 0, 0, privacyDotViewController.selectDesignatedCorner(viewState.rotation, z), (String) null, 12031));
        }
    }
}
