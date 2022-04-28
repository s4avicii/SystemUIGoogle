package com.android.systemui.keyguard;

import android.media.MediaMetadata;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardSliceProvider$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ KeyguardSliceProvider f$0;
    public final /* synthetic */ MediaMetadata f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ KeyguardSliceProvider$$ExternalSyntheticLambda1(KeyguardSliceProvider keyguardSliceProvider, MediaMetadata mediaMetadata, int i) {
        this.f$0 = keyguardSliceProvider;
        this.f$1 = mediaMetadata;
        this.f$2 = i;
    }

    public final void run() {
        KeyguardSliceProvider keyguardSliceProvider = this.f$0;
        MediaMetadata mediaMetadata = this.f$1;
        int i = this.f$2;
        Objects.requireNonNull(keyguardSliceProvider);
        synchronized (keyguardSliceProvider) {
            keyguardSliceProvider.updateMediaStateLocked(mediaMetadata, i);
            keyguardSliceProvider.mMediaWakeLock.setAcquired(false);
        }
    }
}
