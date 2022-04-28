package com.android.systemui.media.muteawait;

import android.graphics.drawable.Drawable;
import android.media.AudioDeviceAttributes;
import android.media.AudioManager;
import java.util.Objects;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManager$muteAwaitConnectionChangeListener$1 */
/* compiled from: MediaMuteAwaitConnectionManager.kt */
public final class C0907x5cc98e9e extends AudioManager.MuteAwaitConnectionCallback {
    public final /* synthetic */ MediaMuteAwaitConnectionManager this$0;

    public final void onMutedUntilConnection(AudioDeviceAttributes audioDeviceAttributes, int[] iArr) {
        if (ArraysKt___ArraysKt.contains(iArr, 1)) {
            MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager = this.this$0;
            Objects.requireNonNull(mediaMuteAwaitConnectionManager);
            mediaMuteAwaitConnectionManager.currentMutedDevice = audioDeviceAttributes;
            this.this$0.localMediaManager.dispatchAboutToConnectDeviceChanged(audioDeviceAttributes.getName(), this.this$0.getIcon(audioDeviceAttributes));
        }
    }

    public C0907x5cc98e9e(MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager) {
        this.this$0 = mediaMuteAwaitConnectionManager;
    }

    public final void onUnmutedEvent(int i, AudioDeviceAttributes audioDeviceAttributes, int[] iArr) {
        MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager = this.this$0;
        Objects.requireNonNull(mediaMuteAwaitConnectionManager);
        if (Intrinsics.areEqual(mediaMuteAwaitConnectionManager.currentMutedDevice, audioDeviceAttributes) && ArraysKt___ArraysKt.contains(iArr, 1)) {
            MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager2 = this.this$0;
            Objects.requireNonNull(mediaMuteAwaitConnectionManager2);
            mediaMuteAwaitConnectionManager2.currentMutedDevice = null;
            this.this$0.localMediaManager.dispatchAboutToConnectDeviceChanged((String) null, (Drawable) null);
        }
    }
}
