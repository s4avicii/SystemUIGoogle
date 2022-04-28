package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;
import java.util.ArrayList;

public interface CastController extends CallbackController<Callback>, Dumpable {

    public interface Callback {
        void onCastDevicesChanged();
    }

    public static final class CastDevice {
        public String name;
        public int state = 0;
        public Object tag;
    }

    ArrayList getCastDevices();

    void setCurrentUserId(int i);

    void setDiscovering();

    void stopCasting(CastDevice castDevice);
}
