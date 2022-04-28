package com.android.settingslib.media;

import android.content.Context;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class MediaManager {
    public final CopyOnWriteArrayList mCallbacks = new CopyOnWriteArrayList();
    public Context mContext;
    public final ArrayList mMediaDevices = new ArrayList();

    public interface MediaDeviceCallback {
        void onConnectedDeviceChanged(String str);

        void onDeviceAttributesChanged();

        void onDeviceListAdded(ArrayList arrayList);

        void onRequestFailed(int i);
    }

    public MediaManager(Context context) {
        this.mContext = context;
    }
}
