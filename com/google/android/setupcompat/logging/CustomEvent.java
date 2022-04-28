package com.google.android.setupcompat.logging;

import android.annotation.TargetApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import androidx.preference.R$drawable;
import com.android.systemui.R$array;
import com.google.android.setupcompat.internal.ClockProvider;
import com.google.android.setupcompat.internal.PersistableBundles;
import com.google.android.setupcompat.internal.Ticker;
import com.google.android.setupcompat.util.Logger;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@TargetApi(29)
public final class CustomEvent implements Parcelable {
    public static final Parcelable.Creator<CustomEvent> CREATOR = new Parcelable.Creator<CustomEvent>() {
        public final Object createFromParcel(Parcel parcel) {
            return new CustomEvent(parcel.readLong(), (MetricKey) parcel.readParcelable(MetricKey.class.getClassLoader()), parcel.readPersistableBundle(MetricKey.class.getClassLoader()), parcel.readPersistableBundle(MetricKey.class.getClassLoader()));
        }

        public final Object[] newArray(int i) {
            return new CustomEvent[i];
        }
    };
    public static final int MAX_STR_LENGTH = 50;
    public static final int MIN_BUNDLE_KEY_LENGTH = 3;
    public final MetricKey metricKey;
    public final PersistableBundle persistableBundle;
    public final PersistableBundle piiValues;
    public final long timestampMillis;

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomEvent)) {
            return false;
        }
        CustomEvent customEvent = (CustomEvent) obj;
        if (this.timestampMillis == customEvent.timestampMillis) {
            MetricKey metricKey2 = this.metricKey;
            MetricKey metricKey3 = customEvent.metricKey;
            if (metricKey2 == metricKey3 || (metricKey2 != null && metricKey2.equals(metricKey3))) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                PersistableBundle persistableBundle2 = this.persistableBundle;
                PersistableBundle persistableBundle3 = customEvent.persistableBundle;
                Logger logger = PersistableBundles.LOG;
                if (persistableBundle2 == persistableBundle3 || PersistableBundles.toMap(persistableBundle2).equals(PersistableBundles.toMap(persistableBundle3))) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    PersistableBundle persistableBundle4 = this.piiValues;
                    PersistableBundle persistableBundle5 = customEvent.piiValues;
                    if (persistableBundle4 == persistableBundle5 || PersistableBundles.toMap(persistableBundle4).equals(PersistableBundles.toMap(persistableBundle5))) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (z3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.timestampMillis), this.metricKey, this.persistableBundle, this.piiValues});
    }

    public static CustomEvent create(MetricKey metricKey2, PersistableBundle persistableBundle2) {
        PersistableBundle persistableBundle3 = PersistableBundle.EMPTY;
        Ticker ticker = ClockProvider.ticker;
        long millis = TimeUnit.NANOSECONDS.toMillis(ClockProvider.ticker.read());
        PersistableBundles.assertIsValid(persistableBundle2);
        PersistableBundles.assertIsValid(persistableBundle3);
        return new CustomEvent(millis, metricKey2, persistableBundle2, persistableBundle3);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.timestampMillis);
        parcel.writeParcelable(this.metricKey, i);
        parcel.writePersistableBundle(this.persistableBundle);
        parcel.writePersistableBundle(this.piiValues);
    }

    public CustomEvent(long j, MetricKey metricKey2, PersistableBundle persistableBundle2, PersistableBundle persistableBundle3) {
        boolean z;
        boolean z2;
        if (j >= 0) {
            z = true;
        } else {
            z = false;
        }
        R$array.checkArgument(z, "Timestamp cannot be negative.");
        Objects.requireNonNull(metricKey2, "MetricKey cannot be null.");
        Objects.requireNonNull(persistableBundle2, "Bundle cannot be null.");
        R$array.checkArgument(!persistableBundle2.isEmpty(), "Bundle cannot be empty.");
        Objects.requireNonNull(persistableBundle3, "piiValues cannot be null.");
        for (String next : persistableBundle2.keySet()) {
            R$drawable.assertLengthInRange(next, "bundle key", 3, 50);
            Object obj = persistableBundle2.get(next);
            if (obj instanceof String) {
                if (((String) obj).length() <= 50) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                R$array.checkArgument(z2, String.format("Maximum length of string value for key='%s' cannot exceed %s.", new Object[]{next, 50}));
            }
        }
        this.timestampMillis = j;
        this.metricKey = metricKey2;
        this.persistableBundle = new PersistableBundle(persistableBundle2);
        this.piiValues = new PersistableBundle(persistableBundle3);
    }
}
