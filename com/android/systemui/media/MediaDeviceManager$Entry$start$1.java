package com.android.systemui.media;

import android.media.AudioDeviceAttributes;
import android.media.session.MediaController;
import com.android.settingslib.media.LocalMediaManager;
import com.android.systemui.media.MediaDeviceManager;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManager;
import java.util.Objects;

/* compiled from: MediaDeviceManager.kt */
public final class MediaDeviceManager$Entry$start$1 implements Runnable {
    public final /* synthetic */ MediaDeviceManager.Entry this$0;

    public MediaDeviceManager$Entry$start$1(MediaDeviceManager.Entry entry) {
        this.this$0 = entry;
    }

    public final void run() {
        MediaController.PlaybackInfo playbackInfo;
        MediaDeviceManager.Entry entry = this.this$0;
        Objects.requireNonNull(entry);
        LocalMediaManager localMediaManager = entry.localMediaManager;
        MediaDeviceManager.Entry entry2 = this.this$0;
        Objects.requireNonNull(localMediaManager);
        localMediaManager.mCallbacks.add(entry2);
        MediaDeviceManager.Entry entry3 = this.this$0;
        Objects.requireNonNull(entry3);
        entry3.localMediaManager.startScan();
        MediaDeviceManager.Entry entry4 = this.this$0;
        Objects.requireNonNull(entry4);
        MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager = entry4.muteAwaitConnectionManager;
        if (mediaMuteAwaitConnectionManager != null) {
            mediaMuteAwaitConnectionManager.audioManager.registerMuteAwaitConnectionCallback(mediaMuteAwaitConnectionManager.mainExecutor, mediaMuteAwaitConnectionManager.muteAwaitConnectionChangeListener);
            AudioDeviceAttributes mutingExpectedDevice = mediaMuteAwaitConnectionManager.audioManager.getMutingExpectedDevice();
            if (mutingExpectedDevice != null) {
                mediaMuteAwaitConnectionManager.currentMutedDevice = mutingExpectedDevice;
                mediaMuteAwaitConnectionManager.localMediaManager.dispatchAboutToConnectDeviceChanged(mutingExpectedDevice.getName(), mediaMuteAwaitConnectionManager.getIcon(mutingExpectedDevice));
            }
        }
        MediaDeviceManager.Entry entry5 = this.this$0;
        Objects.requireNonNull(entry5);
        MediaController mediaController = entry5.controller;
        int i = 0;
        if (!(mediaController == null || (playbackInfo = mediaController.getPlaybackInfo()) == null)) {
            i = playbackInfo.getPlaybackType();
        }
        entry5.playbackType = i;
        MediaDeviceManager.Entry entry6 = this.this$0;
        Objects.requireNonNull(entry6);
        MediaController mediaController2 = entry6.controller;
        if (mediaController2 != null) {
            mediaController2.registerCallback(this.this$0);
        }
        this.this$0.updateCurrent();
        this.this$0.started = true;
    }
}
