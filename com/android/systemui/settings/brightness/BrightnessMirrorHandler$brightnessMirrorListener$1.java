package com.android.systemui.settings.brightness;

import com.android.systemui.statusbar.policy.BrightnessMirrorController;

/* compiled from: BrightnessMirrorHandler.kt */
public final class BrightnessMirrorHandler$brightnessMirrorListener$1 implements BrightnessMirrorController.BrightnessMirrorListener {
    public final /* synthetic */ BrightnessMirrorHandler this$0;

    public BrightnessMirrorHandler$brightnessMirrorListener$1(BrightnessMirrorHandler brightnessMirrorHandler) {
        this.this$0 = brightnessMirrorHandler;
    }

    public final void onBrightnessMirrorReinflated() {
        this.this$0.updateBrightnessMirror();
    }
}
