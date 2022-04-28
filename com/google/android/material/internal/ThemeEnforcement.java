package com.google.android.material.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.TintTypedArray;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.R$styleable;

public final class ThemeEnforcement {
    public static final int[] APPCOMPAT_CHECK_ATTRS = {C1777R.attr.colorPrimary};
    public static final int[] MATERIAL_CHECK_ATTRS = {C1777R.attr.colorPrimaryVariant};

    public static void checkCompatibleTheme(Context context, AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ThemeEnforcement, i, i2);
        boolean z = obtainStyledAttributes.getBoolean(1, false);
        obtainStyledAttributes.recycle();
        if (z) {
            TypedValue typedValue = new TypedValue();
            if (!context.getTheme().resolveAttribute(C1777R.attr.isMaterialTheme, typedValue, true) || (typedValue.type == 18 && typedValue.data == 0)) {
                checkTheme(context, MATERIAL_CHECK_ATTRS, "Theme.MaterialComponents");
            }
        }
        checkTheme(context, APPCOMPAT_CHECK_ATTRS, "Theme.AppCompat");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001a, code lost:
        if (r0.getResourceId(0, -1) != -1) goto L_0x0037;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void checkTextAppearance(android.content.Context r4, android.util.AttributeSet r5, int[] r6, int r7, int r8, int... r9) {
        /*
            int[] r0 = com.google.android.material.R$styleable.ThemeEnforcement
            android.content.res.TypedArray r0 = r4.obtainStyledAttributes(r5, r0, r7, r8)
            r1 = 2
            r2 = 0
            boolean r1 = r0.getBoolean(r1, r2)
            if (r1 != 0) goto L_0x0012
            r0.recycle()
            return
        L_0x0012:
            r1 = -1
            int r3 = r9.length
            if (r3 != 0) goto L_0x001d
            int r4 = r0.getResourceId(r2, r1)
            if (r4 == r1) goto L_0x0038
            goto L_0x0037
        L_0x001d:
            android.content.res.TypedArray r4 = r4.obtainStyledAttributes(r5, r6, r7, r8)
            int r5 = r9.length
            r6 = r2
        L_0x0023:
            if (r6 >= r5) goto L_0x0034
            r7 = r9[r6]
            int r7 = r4.getResourceId(r7, r1)
            if (r7 != r1) goto L_0x0031
            r4.recycle()
            goto L_0x0038
        L_0x0031:
            int r6 = r6 + 1
            goto L_0x0023
        L_0x0034:
            r4.recycle()
        L_0x0037:
            r2 = 1
        L_0x0038:
            r0.recycle()
            if (r2 == 0) goto L_0x003e
            return
        L_0x003e:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "This component requires that you specify a valid TextAppearance attribute. Update your app theme to inherit from Theme.MaterialComponents (or a descendant)."
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.internal.ThemeEnforcement.checkTextAppearance(android.content.Context, android.util.AttributeSet, int[], int, int, int[]):void");
    }

    public static void checkTheme(Context context, int[] iArr, String str) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(iArr);
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= iArr.length) {
                obtainStyledAttributes.recycle();
                z = true;
                break;
            } else if (!obtainStyledAttributes.hasValue(i)) {
                obtainStyledAttributes.recycle();
                break;
            } else {
                i++;
            }
        }
        if (!z) {
            throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("The style on this component requires your app theme to be ", str, " (or a descendant)."));
        }
    }

    public static TypedArray obtainStyledAttributes(Context context, AttributeSet attributeSet, int[] iArr, int i, int i2, int... iArr2) {
        checkCompatibleTheme(context, attributeSet, i, i2);
        checkTextAppearance(context, attributeSet, iArr, i, i2, iArr2);
        return context.obtainStyledAttributes(attributeSet, iArr, i, i2);
    }

    public static TintTypedArray obtainTintedStyledAttributes(Context context, AttributeSet attributeSet, int[] iArr, int i, int i2, int... iArr2) {
        checkCompatibleTheme(context, attributeSet, i, i2);
        checkTextAppearance(context, attributeSet, iArr, i, i2, iArr2);
        return new TintTypedArray(context, context.obtainStyledAttributes(attributeSet, iArr, i, i2));
    }
}