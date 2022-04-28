package com.android.systemui.util;

import kotlin.jvm.internal.MutablePropertyReference0Impl;

/* compiled from: DualHeightHorizontalLinearLayout.kt */
public /* synthetic */ class DualHeightHorizontalLinearLayout$updateResources$2 extends MutablePropertyReference0Impl {
    public DualHeightHorizontalLinearLayout$updateResources$2(Object obj) {
        super(obj, DualHeightHorizontalLinearLayout.class, "singleLineHeightPx", "getSingleLineHeightPx()I");
    }

    public final Object get() {
        return Integer.valueOf(((DualHeightHorizontalLinearLayout) this.receiver).singleLineHeightPx);
    }

    public final void set(Object obj) {
        ((DualHeightHorizontalLinearLayout) this.receiver).singleLineHeightPx = ((Number) obj).intValue();
    }
}
