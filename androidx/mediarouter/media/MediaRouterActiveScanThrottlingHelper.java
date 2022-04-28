package androidx.mediarouter.media;

import android.os.Handler;
import android.os.Looper;
import androidx.mediarouter.media.MediaRouter;

public final class MediaRouterActiveScanThrottlingHelper {
    public boolean mActiveScan;
    public long mCurrentTime;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public long mSuppressActiveScanTimeout;
    public final Runnable mUpdateDiscoveryRequestRunnable;

    public MediaRouterActiveScanThrottlingHelper(MediaRouter.GlobalMediaRouter.C02812 r3) {
        this.mUpdateDiscoveryRequestRunnable = r3;
    }
}
