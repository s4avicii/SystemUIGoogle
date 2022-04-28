package com.android.systemui.media;

import android.util.Log;
import com.android.systemui.media.MediaTimeoutListener;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaTimeoutListener.kt */
public final class MediaTimeoutListener$PlaybackStateListener$processState$1 implements Runnable {
    public final /* synthetic */ MediaTimeoutListener.PlaybackStateListener this$0;
    public final /* synthetic */ MediaTimeoutListener this$1;

    public MediaTimeoutListener$PlaybackStateListener$processState$1(MediaTimeoutListener.PlaybackStateListener playbackStateListener, MediaTimeoutListener mediaTimeoutListener) {
        this.this$0 = playbackStateListener;
        this.this$1 = mediaTimeoutListener;
    }

    public final void run() {
        MediaTimeoutListener.PlaybackStateListener playbackStateListener = this.this$0;
        Function2<? super String, ? super Boolean, Unit> function2 = null;
        playbackStateListener.cancellation = null;
        Log.v("MediaTimeout", Intrinsics.stringPlus("Execute timeout for ", playbackStateListener.key));
        MediaTimeoutListener.PlaybackStateListener playbackStateListener2 = this.this$0;
        Objects.requireNonNull(playbackStateListener2);
        playbackStateListener2.timedOut = true;
        MediaTimeoutListener mediaTimeoutListener = this.this$1;
        Objects.requireNonNull(mediaTimeoutListener);
        Function2<? super String, ? super Boolean, Unit> function22 = mediaTimeoutListener.timeoutCallback;
        if (function22 != null) {
            function2 = function22;
        }
        MediaTimeoutListener.PlaybackStateListener playbackStateListener3 = this.this$0;
        Objects.requireNonNull(playbackStateListener3);
        String str = playbackStateListener3.key;
        MediaTimeoutListener.PlaybackStateListener playbackStateListener4 = this.this$0;
        Objects.requireNonNull(playbackStateListener4);
        function2.invoke(str, Boolean.valueOf(playbackStateListener4.timedOut));
    }
}
