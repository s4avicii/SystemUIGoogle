package com.android.p012wm.shell.bubbles.storage;

import java.util.Objects;
import java.util.function.Predicate;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.wm.shell.bubbles.storage.BubbleVolatileRepository$removeBubbles$1$1 */
/* compiled from: BubbleVolatileRepository.kt */
public final class BubbleVolatileRepository$removeBubbles$1$1<T> implements Predicate {

    /* renamed from: $b */
    public final /* synthetic */ BubbleEntity f123$b;

    public BubbleVolatileRepository$removeBubbles$1$1(BubbleEntity bubbleEntity) {
        this.f123$b = bubbleEntity;
    }

    public final boolean test(Object obj) {
        BubbleEntity bubbleEntity = this.f123$b;
        Objects.requireNonNull(bubbleEntity);
        return Intrinsics.areEqual(bubbleEntity.key, ((BubbleEntity) obj).key);
    }
}
