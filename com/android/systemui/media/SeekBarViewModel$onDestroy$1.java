package com.android.systemui.media;

import android.media.session.MediaController;

/* compiled from: SeekBarViewModel.kt */
public final class SeekBarViewModel$onDestroy$1 implements Runnable {
    public final /* synthetic */ SeekBarViewModel this$0;

    public SeekBarViewModel$onDestroy$1(SeekBarViewModel seekBarViewModel) {
        this.this$0 = seekBarViewModel;
    }

    public final void run() {
        this.this$0.setController((MediaController) null);
        SeekBarViewModel seekBarViewModel = this.this$0;
        seekBarViewModel.playbackState = null;
        Runnable runnable = seekBarViewModel.cancel;
        if (runnable != null) {
            runnable.run();
        }
        this.this$0.cancel = null;
    }
}
