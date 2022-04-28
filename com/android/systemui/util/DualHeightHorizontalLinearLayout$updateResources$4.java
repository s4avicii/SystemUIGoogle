package com.android.systemui.util;

import kotlin.jvm.internal.MutablePropertyReference0Impl;

/* compiled from: DualHeightHorizontalLinearLayout.kt */
public /* synthetic */ class DualHeightHorizontalLinearLayout$updateResources$4 extends MutablePropertyReference0Impl {
    public DualHeightHorizontalLinearLayout$updateResources$4(Object obj) {
        super(obj, DualHeightHorizontalLinearLayout.class, "singleLineVerticalPaddingPx", "getSingleLineVerticalPaddingPx()I");
    }

    public final Object get() {
        return Integer.valueOf(((DualHeightHorizontalLinearLayout) this.receiver).singleLineVerticalPaddingPx);
    }

    public final void set(Object obj) {
        ((DualHeightHorizontalLinearLayout) this.receiver).singleLineVerticalPaddingPx = ((Number) obj).intValue();
    }
}
