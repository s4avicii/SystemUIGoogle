package com.android.systemui.media;

import com.android.systemui.media.MediaDeviceManager;
import java.util.Objects;

/* compiled from: MediaDeviceManager.kt */
public final class MediaDeviceManager$Entry$current$1 implements Runnable {
    public final /* synthetic */ MediaDeviceData $value;
    public final /* synthetic */ MediaDeviceManager this$0;
    public final /* synthetic */ MediaDeviceManager.Entry this$1;

    public MediaDeviceManager$Entry$current$1(MediaDeviceManager mediaDeviceManager, MediaDeviceManager.Entry entry, MediaDeviceData mediaDeviceData) {
        this.this$0 = mediaDeviceManager;
        this.this$1 = entry;
        this.$value = mediaDeviceData;
    }

    public final void run() {
        MediaDeviceManager mediaDeviceManager = this.this$0;
        MediaDeviceManager.Entry entry = this.this$1;
        Objects.requireNonNull(entry);
        String str = entry.key;
        MediaDeviceManager.Entry entry2 = this.this$1;
        Objects.requireNonNull(entry2);
        String str2 = entry2.oldKey;
        MediaDeviceData mediaDeviceData = this.$value;
        Objects.requireNonNull(mediaDeviceManager);
        for (MediaDeviceManager.Listener onMediaDeviceChanged : mediaDeviceManager.listeners) {
            onMediaDeviceChanged.onMediaDeviceChanged(str, str2, mediaDeviceData);
        }
    }
}
