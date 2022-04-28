package com.android.systemui.shared.recents.utilities;

import android.view.View;

public final class ViewRippler {
    public final C11191 mRipple = new Runnable() {
        public final void run() {
            if (ViewRippler.this.mRoot.isAttachedToWindow()) {
                ViewRippler.this.mRoot.setPressed(true);
                ViewRippler.this.mRoot.setPressed(false);
            }
        }
    };
    public View mRoot;
}
