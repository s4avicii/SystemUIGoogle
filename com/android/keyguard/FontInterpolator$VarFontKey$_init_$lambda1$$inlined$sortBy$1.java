package com.android.keyguard;

import android.graphics.fonts.FontVariationAxis;
import androidx.fragment.R$styleable;
import java.util.Comparator;

/* renamed from: com.android.keyguard.FontInterpolator$VarFontKey$_init_$lambda-1$$inlined$sortBy$1  reason: invalid class name */
/* compiled from: Comparisons.kt */
public final class FontInterpolator$VarFontKey$_init_$lambda1$$inlined$sortBy$1<T> implements Comparator {
    public final int compare(T t, T t2) {
        return R$styleable.compareValues(((FontVariationAxis) t).getTag(), ((FontVariationAxis) t2).getTag());
    }
}
