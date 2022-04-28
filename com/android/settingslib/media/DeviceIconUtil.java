package com.android.settingslib.media;

import com.android.p012wm.shell.C1777R;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class DeviceIconUtil {
    public final HashMap mAudioDeviceTypeToIconMap = new HashMap();
    public final HashMap mMediaRouteTypeToIconMap = new HashMap();

    public static class Device {
        public final int mAudioDeviceType;
        public final int mIconDrawableRes;
        public final int mMediaRouteType;

        public Device(int i, int i2, int i3) {
            this.mAudioDeviceType = i;
            this.mMediaRouteType = i2;
            this.mIconDrawableRes = i3;
        }
    }

    public DeviceIconUtil() {
        List asList = Arrays.asList(new Device[]{new Device(11, 11, C1777R.C1778drawable.ic_headphone), new Device(22, 22, C1777R.C1778drawable.ic_headphone), new Device(12, 12, C1777R.C1778drawable.ic_headphone), new Device(13, 13, C1777R.C1778drawable.ic_headphone), new Device(9, 9, C1777R.C1778drawable.ic_headphone), new Device(3, 3, C1777R.C1778drawable.ic_headphone), new Device(4, 4, C1777R.C1778drawable.ic_headphone), new Device(2, 2, C1777R.C1778drawable.ic_smartphone)});
        for (int i = 0; i < asList.size(); i++) {
            Device device = (Device) asList.get(i);
            this.mAudioDeviceTypeToIconMap.put(Integer.valueOf(device.mAudioDeviceType), device);
            this.mMediaRouteTypeToIconMap.put(Integer.valueOf(device.mMediaRouteType), device);
        }
    }
}
