package com.android.systemui.media;

import android.media.session.MediaController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: SeekBarViewModel.kt */
public final class SeekBarViewModel$onSeek$1 implements Runnable {
    public final /* synthetic */ long $position;
    public final /* synthetic */ SeekBarViewModel this$0;

    public SeekBarViewModel$onSeek$1(SeekBarViewModel seekBarViewModel, long j) {
        this.this$0 = seekBarViewModel;
        this.$position = j;
    }

    public final void run() {
        MediaController.TransportControls transportControls;
        SeekBarViewModel seekBarViewModel = this.this$0;
        if (seekBarViewModel.isFalseSeek) {
            if (seekBarViewModel.scrubbing) {
                seekBarViewModel.scrubbing = false;
                seekBarViewModel.checkIfPollingNeeded();
            }
            SeekBarViewModel.access$checkPlaybackPosition(this.this$0);
            return;
        }
        Function0<Unit> function0 = seekBarViewModel.logSmartspaceClick;
        if (function0 == null) {
            function0 = null;
        }
        function0.invoke();
        MediaController mediaController = this.this$0.controller;
        if (!(mediaController == null || (transportControls = mediaController.getTransportControls()) == null)) {
            transportControls.seekTo(this.$position);
        }
        SeekBarViewModel seekBarViewModel2 = this.this$0;
        seekBarViewModel2.playbackState = null;
        if (seekBarViewModel2.scrubbing) {
            seekBarViewModel2.scrubbing = false;
            seekBarViewModel2.checkIfPollingNeeded();
        }
    }
}
