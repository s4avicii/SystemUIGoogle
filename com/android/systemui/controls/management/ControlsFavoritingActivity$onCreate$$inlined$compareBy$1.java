package com.android.systemui.controls.management;

import java.text.Collator;
import java.util.Comparator;
import java.util.Objects;

/* compiled from: Comparisons.kt */
public final class ControlsFavoritingActivity$onCreate$$inlined$compareBy$1<T> implements Comparator {
    public final /* synthetic */ Comparator $comparator;

    public ControlsFavoritingActivity$onCreate$$inlined$compareBy$1(Collator collator) {
        this.$comparator = collator;
    }

    public final int compare(T t, T t2) {
        Comparator comparator = this.$comparator;
        StructureContainer structureContainer = (StructureContainer) t;
        Objects.requireNonNull(structureContainer);
        CharSequence charSequence = structureContainer.structureName;
        StructureContainer structureContainer2 = (StructureContainer) t2;
        Objects.requireNonNull(structureContainer2);
        return comparator.compare(charSequence, structureContainer2.structureName);
    }
}
