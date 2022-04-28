package com.android.systemui.util.leak;

import android.os.Process;
import android.util.Log;
import com.android.systemui.util.concurrency.MessageRouter;
import com.android.systemui.util.leak.GarbageMonitor;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GarbageMonitor$$ExternalSyntheticLambda1 implements MessageRouter.SimpleMessageListener {
    public final /* synthetic */ GarbageMonitor f$0;

    public /* synthetic */ GarbageMonitor$$ExternalSyntheticLambda1(GarbageMonitor garbageMonitor) {
        this.f$0 = garbageMonitor;
    }

    public final void onMessage() {
        GarbageMonitor garbageMonitor = this.f$0;
        Objects.requireNonNull(garbageMonitor);
        synchronized (garbageMonitor.mPids) {
            int i = 0;
            while (true) {
                if (i >= garbageMonitor.mPids.size()) {
                    break;
                }
                int intValue = garbageMonitor.mPids.get(i).intValue();
                long[] rss = Process.getRss(intValue);
                if (rss != null || rss.length != 0) {
                    long j = rss[0];
                    long j2 = (long) intValue;
                    GarbageMonitor.ProcessMemInfo processMemInfo = garbageMonitor.mData.get(j2);
                    long[] jArr = processMemInfo.rss;
                    int i2 = processMemInfo.head;
                    processMemInfo.currentRss = j;
                    jArr[i2] = j;
                    processMemInfo.head = (i2 + 1) % jArr.length;
                    if (j > processMemInfo.max) {
                        processMemInfo.max = j;
                    }
                    if (j == 0) {
                        if (GarbageMonitor.DEBUG) {
                            Log.v("GarbageMonitor", "update: pid " + intValue + " has rss=0, it probably died");
                        }
                        garbageMonitor.mData.remove(j2);
                    }
                    i++;
                } else if (GarbageMonitor.DEBUG) {
                    Log.e("GarbageMonitor", "update: Process.getRss() didn't provide any values.");
                }
            }
            int size = garbageMonitor.mPids.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                if (garbageMonitor.mData.get((long) garbageMonitor.mPids.get(size).intValue()) == null) {
                    garbageMonitor.mPids.remove(size);
                    garbageMonitor.logPids();
                }
            }
        }
        GarbageMonitor.MemoryTile memoryTile = garbageMonitor.mQSTile;
        if (memoryTile != null) {
            memoryTile.refreshState((Object) null);
        }
        garbageMonitor.mMessageRouter.cancelMessages(3000);
        garbageMonitor.mMessageRouter.sendMessageDelayed(3000, 60000);
    }
}
