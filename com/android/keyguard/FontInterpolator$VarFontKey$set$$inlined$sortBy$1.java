package com.android.keyguard;

import android.graphics.fonts.FontVariationAxis;
import androidx.fragment.R$styleable;
import java.util.Comparator;

/* compiled from: Comparisons.kt */
public final class FontInterpolator$VarFontKey$set$$inlined$sortBy$1<T> implements Comparator {
    public final int compare(T t, T t2) {
        return R$styleable.compareValues(((FontVariationAxis) t).getTag(), ((FontVariationAxis) t2).getTag());
    }
}
