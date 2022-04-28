package com.android.systemui.controls.p004ui;

import java.text.Collator;
import java.util.Comparator;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl$special$$inlined$compareBy$1 */
/* compiled from: Comparisons.kt */
public final class ControlsUiControllerImpl$special$$inlined$compareBy$1<T> implements Comparator {
    public final /* synthetic */ Comparator $comparator;

    public ControlsUiControllerImpl$special$$inlined$compareBy$1(Collator collator) {
        this.$comparator = collator;
    }

    public final int compare(T t, T t2) {
        return this.$comparator.compare(((SelectionItem) t).getTitle(), ((SelectionItem) t2).getTitle());
    }
}
