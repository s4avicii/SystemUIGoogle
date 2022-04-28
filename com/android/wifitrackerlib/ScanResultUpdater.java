package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import android.util.Pair;
import com.android.p012wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda6;
import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class ScanResultUpdater {
    public final Clock mClock;
    public final Object mLock = new Object();
    public final long mMaxScanAgeMillis;
    public HashMap mScanResultsBySsidAndBssid = new HashMap();

    public final ArrayList getScanResults(long j) throws IllegalArgumentException {
        ArrayList arrayList;
        if (j <= this.mMaxScanAgeMillis) {
            synchronized (this.mLock) {
                arrayList = new ArrayList();
                for (ScanResult scanResult : this.mScanResultsBySsidAndBssid.values()) {
                    if (this.mClock.millis() - (scanResult.timestamp / 1000) <= j) {
                        arrayList.add(scanResult);
                    }
                }
            }
            return arrayList;
        }
        throw new IllegalArgumentException("maxScanAgeMillis argument cannot be greater than mMaxScanAgeMillis!");
    }

    public final void update(List<ScanResult> list) {
        synchronized (this.mLock) {
            synchronized (this.mLock) {
                this.mScanResultsBySsidAndBssid.entrySet().removeIf(new BubbleData$$ExternalSyntheticLambda6(this, 1));
            }
            for (ScanResult next : list) {
                Pair pair = new Pair(next.SSID, next.BSSID);
                ScanResult scanResult = (ScanResult) this.mScanResultsBySsidAndBssid.get(pair);
                if (scanResult == null || scanResult.timestamp < next.timestamp) {
                    this.mScanResultsBySsidAndBssid.put(pair, next);
                }
            }
        }
    }

    public ScanResultUpdater(Clock clock, long j) {
        this.mMaxScanAgeMillis = j;
        this.mClock = clock;
    }
}
