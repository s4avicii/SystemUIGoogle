package com.android.systemui.media;

import android.media.session.MediaController;
import android.media.session.PlaybackState;
import java.util.Objects;

/* compiled from: SeekBarViewModel.kt */
public final class SeekBarViewModel$callback$1 extends MediaController.Callback {
    public final /* synthetic */ SeekBarViewModel this$0;

    public SeekBarViewModel$callback$1(SeekBarViewModel seekBarViewModel) {
        this.this$0 = seekBarViewModel;
    }

    public final void onPlaybackStateChanged(PlaybackState playbackState) {
        this.this$0.playbackState = playbackState;
        if (playbackState != null) {
            Integer num = 0;
            if (!num.equals(this.this$0.playbackState)) {
                this.this$0.checkIfPollingNeeded();
                return;
            }
        }
        SeekBarViewModel seekBarViewModel = this.this$0;
        Objects.requireNonNull(seekBarViewModel);
        seekBarViewModel.bgExecutor.execute(new SeekBarViewModel$clearController$1(seekBarViewModel));
    }

    public final void onSessionDestroyed() {
        SeekBarViewModel seekBarViewModel = this.this$0;
        Objects.requireNonNull(seekBarViewModel);
        seekBarViewModel.bgExecutor.execute(new SeekBarViewModel$clearController$1(seekBarViewModel));
    }
}
