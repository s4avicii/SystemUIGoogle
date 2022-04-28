package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

@DebugMetadata(mo21073c = "com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2", mo21074f = "SensitiveContentCoordinator.kt", mo21075l = {120, 122}, mo21076m = "invokeSuspend")
/* compiled from: SensitiveContentCoordinator.kt */
final class SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super NotificationEntry>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ ListEntry $listEntry;
    private /* synthetic */ Object L$0;
    public int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2(ListEntry listEntry, Continuation<? super SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2> continuation) {
        super(continuation);
        this.$listEntry = listEntry;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2 sensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2 = new SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2(this.$listEntry, continuation);
        sensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2.L$0 = obj;
        return sensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L_0x0020
            if (r1 == r3) goto L_0x0018
            if (r1 != r2) goto L_0x0010
            kotlin.ResultKt.throwOnFailure(r5)
            goto L_0x0061
        L_0x0010:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L_0x0018:
            java.lang.Object r1 = r4.L$0
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r5)
            goto L_0x0030
        L_0x0020:
            kotlin.ResultKt.throwOnFailure(r5)
            java.lang.Object r5 = r4.L$0
            r1 = r5
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            com.android.systemui.statusbar.notification.collection.ListEntry r5 = r4.$listEntry
            com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = r5.getRepresentativeEntry()
            if (r5 != 0) goto L_0x0064
        L_0x0030:
            com.android.systemui.statusbar.notification.collection.ListEntry r5 = r4.$listEntry
            boolean r3 = r5 instanceof com.android.systemui.statusbar.notification.collection.GroupEntry
            if (r3 == 0) goto L_0x0061
            com.android.systemui.statusbar.notification.collection.GroupEntry r5 = (com.android.systemui.statusbar.notification.collection.GroupEntry) r5
            java.util.Objects.requireNonNull(r5)
            java.util.List<com.android.systemui.statusbar.notification.collection.NotificationEntry> r5 = r5.mUnmodifiableChildren
            kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 r3 = new kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            r3.<init>(r5)
            com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1 r5 = com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1.INSTANCE
            kotlin.sequences.FlatteningSequence r5 = kotlin.sequences.SequencesKt___SequencesKt.flatMap(r3, r5)
            r3 = 0
            r4.L$0 = r3
            r4.label = r2
            java.util.Objects.requireNonNull(r1)
            kotlin.sequences.FlatteningSequence$iterator$1 r2 = new kotlin.sequences.FlatteningSequence$iterator$1
            r2.<init>(r5)
            java.lang.Object r4 = r1.yieldAll(r2, r4)
            if (r4 != r0) goto L_0x005c
            goto L_0x005e
        L_0x005c:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
        L_0x005e:
            if (r4 != r0) goto L_0x0061
            return r0
        L_0x0061:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L_0x0064:
            r4.L$0 = r1
            r4.label = r3
            r1.yield(r5, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
