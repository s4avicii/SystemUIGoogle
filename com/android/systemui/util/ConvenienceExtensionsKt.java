package com.android.systemui.util;

import android.view.ViewGroup;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;

/* compiled from: ConvenienceExtensions.kt */
public final class ConvenienceExtensionsKt {
    public static final SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 getChildren(ViewGroup viewGroup) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new ConvenienceExtensionsKt$children$1(viewGroup, (Continuation<? super ConvenienceExtensionsKt$children$1>) null));
    }

    public static final SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 takeUntil(FilteringSequence filteringSequence, Function1 function1) {
        return new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new ConvenienceExtensionsKt$takeUntil$1(filteringSequence, function1, (Continuation<? super ConvenienceExtensionsKt$takeUntil$1>) null));
    }
}
