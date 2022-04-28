package com.android.systemui.media;

import com.android.systemui.media.SeekBarViewModel;

/* compiled from: SeekBarViewModel.kt */
public final class SeekBarViewModel$onSeekProgress$1 implements Runnable {
    public final /* synthetic */ long $position;
    public final /* synthetic */ SeekBarViewModel this$0;

    public SeekBarViewModel$onSeekProgress$1(SeekBarViewModel seekBarViewModel, long j) {
        this.this$0 = seekBarViewModel;
        this.$position = j;
    }

    public final void run() {
        SeekBarViewModel seekBarViewModel = this.this$0;
        if (seekBarViewModel.scrubbing) {
            SeekBarViewModel.Progress copy$default = SeekBarViewModel.Progress.copy$default(seekBarViewModel._data, Integer.valueOf((int) this.$position), 11);
            seekBarViewModel._data = copy$default;
            seekBarViewModel._progress.postValue(copy$default);
        }
    }
}
