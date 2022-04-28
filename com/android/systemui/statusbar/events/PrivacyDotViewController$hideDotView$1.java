package com.android.systemui.statusbar.events;

import android.view.View;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.ScreenDecorations$2$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.events.PrivacyDotViewController;

/* compiled from: PrivacyDotViewController.kt */
public final class PrivacyDotViewController$hideDotView$1 implements Runnable {
    public final /* synthetic */ View $dot;
    public final /* synthetic */ PrivacyDotViewController this$0;

    public PrivacyDotViewController$hideDotView$1(View view, PrivacyDotViewController privacyDotViewController) {
        this.$dot = view;
        this.this$0 = privacyDotViewController;
    }

    public final void run() {
        this.$dot.setVisibility(4);
        PrivacyDotViewController.ShowingListener showingListener = this.this$0.showingListener;
        if (showingListener != null) {
            View view = this.$dot;
            ScreenDecorations.C06392 r0 = (ScreenDecorations.C06392) showingListener;
            ScreenDecorations screenDecorations = ScreenDecorations.this;
            if (screenDecorations.mHwcScreenDecorationSupport != null && view != null) {
                screenDecorations.mExecutor.execute(new ScreenDecorations$2$$ExternalSyntheticLambda0(r0, view, 0));
            }
        }
    }
}
