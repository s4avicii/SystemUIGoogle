package com.airbnb.lottie.model;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.core.util.Pair;

public final class MutablePair<T> {
    public T first;
    public T second;

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        F f = pair.first;
        T t = this.first;
        if (f == t || (f != null && f.equals(t))) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return false;
        }
        S s = pair.second;
        T t2 = this.second;
        if (s == t2 || (s != null && s.equals(t2))) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i;
        T t = this.first;
        int i2 = 0;
        if (t == null) {
            i = 0;
        } else {
            i = t.hashCode();
        }
        T t2 = this.second;
        if (t2 != null) {
            i2 = t2.hashCode();
        }
        return i ^ i2;
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Pair{");
        m.append(String.valueOf(this.first));
        m.append(" ");
        m.append(String.valueOf(this.second));
        m.append("}");
        return m.toString();
    }
}
