package com.android.systemui.monet;

import androidx.fragment.R$styleable;
import java.util.Comparator;
import java.util.Map;

/* compiled from: Comparisons.kt */
public final class ColorScheme$Companion$getSeedColors$$inlined$sortByDescending$1<T> implements Comparator {
    public final int compare(T t, T t2) {
        return R$styleable.compareValues((Double) ((Map.Entry) t2).getValue(), (Double) ((Map.Entry) t).getValue());
    }
}
