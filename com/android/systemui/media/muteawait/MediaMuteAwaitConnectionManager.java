package com.android.systemui.media.muteawait;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioDeviceAttributes;
import android.media.AudioManager;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.media.DeviceIconUtil;
import com.android.settingslib.media.LocalMediaManager;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: MediaMuteAwaitConnectionManager.kt */
public final class MediaMuteAwaitConnectionManager {
    public final AudioManager audioManager;
    public final Context context;
    public AudioDeviceAttributes currentMutedDevice;
    public final DeviceIconUtil deviceIconUtil;
    public final LocalMediaManager localMediaManager;
    public final Executor mainExecutor;
    public final C0907x5cc98e9e muteAwaitConnectionChangeListener = new C0907x5cc98e9e(this);

    public final Drawable getIcon(AudioDeviceAttributes audioDeviceAttributes) {
        int i;
        DeviceIconUtil deviceIconUtil2 = this.deviceIconUtil;
        int type = audioDeviceAttributes.getType();
        Context context2 = this.context;
        Objects.requireNonNull(deviceIconUtil2);
        if (deviceIconUtil2.mAudioDeviceTypeToIconMap.containsKey(Integer.valueOf(type))) {
            i = ((DeviceIconUtil.Device) deviceIconUtil2.mAudioDeviceTypeToIconMap.get(Integer.valueOf(type))).mIconDrawableRes;
        } else {
            i = C1777R.C1778drawable.ic_smartphone;
        }
        return context2.getDrawable(i);
    }

    public MediaMuteAwaitConnectionManager(Executor executor, LocalMediaManager localMediaManager2, Context context2, DeviceIconUtil deviceIconUtil2) {
        this.mainExecutor = executor;
        this.localMediaManager = localMediaManager2;
        this.context = context2;
        this.deviceIconUtil = deviceIconUtil2;
        Object systemService = context2.getSystemService("audio");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.media.AudioManager");
        this.audioManager = (AudioManager) systemService;
    }
}
