package com.android.settingslib.deviceinfo;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.format.DateUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import java.lang.ref.WeakReference;

public abstract class AbstractUptimePreferenceController extends AbstractPreferenceController implements LifecycleObserver, OnStart, OnStop {
    public static final String KEY_UPTIME = "up_time";
    public MyHandler mHandler;

    public static class MyHandler extends Handler {
        public WeakReference<AbstractUptimePreferenceController> mStatus;

        public final void handleMessage(Message message) {
            if (this.mStatus.get() != null) {
                if (message.what != 500) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unknown message ");
                    m.append(message.what);
                    throw new IllegalStateException(m.toString());
                }
                DateUtils.formatElapsedTime(SystemClock.elapsedRealtime() / 1000);
                throw null;
            }
        }

        public MyHandler(AbstractUptimePreferenceController abstractUptimePreferenceController) {
            this.mStatus = new WeakReference<>(abstractUptimePreferenceController);
        }
    }

    public final void onStart() {
        if (this.mHandler == null) {
            this.mHandler = new MyHandler(this);
        }
        this.mHandler.sendEmptyMessage(500);
    }

    public final void onStop() {
        if (this.mHandler == null) {
            this.mHandler = new MyHandler(this);
        }
        this.mHandler.removeMessages(500);
    }
}
