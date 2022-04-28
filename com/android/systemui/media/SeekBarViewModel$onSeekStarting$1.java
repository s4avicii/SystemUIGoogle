package com.android.systemui.media;

import java.util.Objects;

/* compiled from: SeekBarViewModel.kt */
public final class SeekBarViewModel$onSeekStarting$1 implements Runnable {
    public final /* synthetic */ SeekBarViewModel this$0;

    public SeekBarViewModel$onSeekStarting$1(SeekBarViewModel seekBarViewModel) {
        this.this$0 = seekBarViewModel;
    }

    public final void run() {
        SeekBarViewModel seekBarViewModel = this.this$0;
        Objects.requireNonNull(seekBarViewModel);
        if (!seekBarViewModel.scrubbing) {
            seekBarViewModel.scrubbing = true;
            seekBarViewModel.checkIfPollingNeeded();
        }
        this.this$0.isFalseSeek = false;
    }
}
