package com.android.settingslib.wifi;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AccessPoint$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ long f$0;
    public final /* synthetic */ Iterator f$1;

    public /* synthetic */ AccessPoint$$ExternalSyntheticLambda2(long j, Iterator it) {
        this.f$0 = j;
        this.f$1 = it;
    }

    public final void accept(Object obj) {
        long j = this.f$0;
        Iterator it = this.f$1;
        TimestampedScoredNetwork timestampedScoredNetwork = (TimestampedScoredNetwork) obj;
        Objects.requireNonNull(timestampedScoredNetwork);
        if (timestampedScoredNetwork.mUpdatedTimestampMillis < j) {
            it.remove();
        }
    }
}
