package com.google.android.systemui.gamedashboard;

import android.view.ViewTreeObserver;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShortcutBarView$$ExternalSyntheticLambda3 implements ViewTreeObserver.OnDrawListener {
    public final /* synthetic */ ShortcutBarView f$0;

    public /* synthetic */ ShortcutBarView$$ExternalSyntheticLambda3(ShortcutBarView shortcutBarView) {
        this.f$0 = shortcutBarView;
    }

    public final void onDraw() {
        ShortcutBarView shortcutBarView = this.f$0;
        int i = ShortcutBarView.SHORTCUT_BAR_BACKGROUND_COLOR;
        Objects.requireNonNull(shortcutBarView);
        if (shortcutBarView.mExcludeBackRegionCallback != null) {
            if (shortcutBarView.mIsAttached) {
                shortcutBarView.getTouchableRegion();
            } else {
                shortcutBarView.mTmpRect.setEmpty();
            }
            shortcutBarView.mExcludeBackRegionCallback.accept(shortcutBarView.mTmpRect);
        }
    }
}
