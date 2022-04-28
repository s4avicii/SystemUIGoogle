package com.google.common.collect;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

public final class ObjectArrays {
    @CanIgnoreReturnValue
    public static Object[] checkElementsNotNull(Object[] objArr, int i) {
        int i2 = 0;
        while (i2 < i) {
            if (objArr[i2] != null) {
                i2++;
            } else {
                throw new NullPointerException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("at index ", i2));
            }
        }
        return objArr;
    }
}
