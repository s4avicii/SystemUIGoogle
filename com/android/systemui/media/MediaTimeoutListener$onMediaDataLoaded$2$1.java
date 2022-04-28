package com.android.systemui.media;

import android.util.Log;
import com.android.systemui.media.MediaTimeoutListener;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaTimeoutListener.kt */
public final class MediaTimeoutListener$onMediaDataLoaded$2$1 implements Runnable {
    public final /* synthetic */ String $key;
    public final /* synthetic */ MediaTimeoutListener this$0;

    public MediaTimeoutListener$onMediaDataLoaded$2$1(MediaTimeoutListener mediaTimeoutListener, String str) {
        this.this$0 = mediaTimeoutListener;
        this.$key = str;
    }

    public final void run() {
        boolean z;
        MediaTimeoutListener.PlaybackStateListener playbackStateListener = (MediaTimeoutListener.PlaybackStateListener) this.this$0.mediaListeners.get(this.$key);
        if (playbackStateListener == null) {
            z = false;
        } else {
            z = Intrinsics.areEqual(playbackStateListener.playing, Boolean.TRUE);
        }
        if (z) {
            Log.d("MediaTimeout", Intrinsics.stringPlus("deliver delayed playback state for ", this.$key));
            MediaTimeoutListener mediaTimeoutListener = this.this$0;
            Objects.requireNonNull(mediaTimeoutListener);
            Function2<? super String, ? super Boolean, Unit> function2 = mediaTimeoutListener.timeoutCallback;
            if (function2 == null) {
                function2 = null;
            }
            function2.invoke(this.$key, Boolean.FALSE);
        }
    }
}
