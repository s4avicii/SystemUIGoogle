package kotlinx.coroutines.flow.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(mo21073c = "kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2", mo21074f = "Combine.kt", mo21075l = {57, 79, 82}, mo21076m = "invokeSuspend")
/* compiled from: Combine.kt */
public final class CombineKt$combineInternal$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Function0<T[]> $arrayFactory;
    public final /* synthetic */ Flow<T>[] $flows;
    public final /* synthetic */ FlowCollector<R> $this_combineInternal;
    public final /* synthetic */ Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> $transform;
    public int I$0;
    public int I$1;
    private /* synthetic */ Object L$0;
    public Object L$1;
    public Object L$2;
    public int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CombineKt$combineInternal$2(Flow<? extends T>[] flowArr, Function0<T[]> function0, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3, FlowCollector<? super R> flowCollector, Continuation<? super CombineKt$combineInternal$2> continuation) {
        super(2, continuation);
        this.$flows = flowArr;
        this.$arrayFactory = function0;
        this.$transform = function3;
        this.$this_combineInternal = flowCollector;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CombineKt$combineInternal$2 combineKt$combineInternal$2 = new CombineKt$combineInternal$2(this.$flows, this.$arrayFactory, this.$transform, this.$this_combineInternal, continuation);
        combineKt$combineInternal$2.L$0 = obj;
        return combineKt$combineInternal$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((CombineKt$combineInternal$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v12, resolved type: kotlin.collections.IndexedValue} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: kotlinx.coroutines.internal.Symbol[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v13, resolved type: kotlinx.coroutines.internal.Symbol[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: kotlinx.coroutines.internal.Symbol[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v19, resolved type: kotlinx.coroutines.internal.Symbol[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v20, resolved type: kotlinx.coroutines.internal.Symbol[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v21, resolved type: kotlinx.coroutines.internal.Symbol[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00bb A[LOOP:1: B:29:0x00bb->B:38:0x00de, LOOP_START, PHI: r4 r11 
      PHI: (r4v2 int) = (r4v1 int), (r4v3 int) binds: [B:26:0x00b6, B:38:0x00de] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r11v3 kotlin.collections.IndexedValue) = (r11v2 kotlin.collections.IndexedValue), (r11v13 kotlin.collections.IndexedValue) binds: [B:26:0x00b6, B:38:0x00de] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r21) {
        /*
            r20 = this;
            r0 = r20
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 0
            r5 = 1
            r6 = 3
            r7 = 0
            if (r2 == 0) goto L_0x004e
            if (r2 == r5) goto L_0x0030
            if (r2 == r3) goto L_0x001c
            if (r2 != r6) goto L_0x0014
            goto L_0x001c
        L_0x0014:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x001c:
            int r4 = r0.I$1
            int r2 = r0.I$0
            java.lang.Object r8 = r0.L$2
            byte[] r8 = (byte[]) r8
            java.lang.Object r9 = r0.L$1
            kotlinx.coroutines.channels.Channel r9 = (kotlinx.coroutines.channels.Channel) r9
            java.lang.Object r10 = r0.L$0
            java.lang.Object[] r10 = (java.lang.Object[]) r10
            kotlin.ResultKt.throwOnFailure(r21)
            goto L_0x0093
        L_0x0030:
            int r2 = r0.I$1
            int r4 = r0.I$0
            java.lang.Object r8 = r0.L$2
            byte[] r8 = (byte[]) r8
            java.lang.Object r9 = r0.L$1
            kotlinx.coroutines.channels.Channel r9 = (kotlinx.coroutines.channels.Channel) r9
            java.lang.Object r10 = r0.L$0
            java.lang.Object[] r10 = (java.lang.Object[]) r10
            kotlin.ResultKt.throwOnFailure(r21)
            r11 = r21
            kotlinx.coroutines.channels.ChannelResult r11 = (kotlinx.coroutines.channels.ChannelResult) r11
            java.util.Objects.requireNonNull(r11)
            java.lang.Object r11 = r11.holder
            r15 = r10
            goto L_0x00ae
        L_0x004e:
            kotlin.ResultKt.throwOnFailure(r21)
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.CoroutineScope r2 = (kotlinx.coroutines.CoroutineScope) r2
            kotlinx.coroutines.flow.Flow<T>[] r8 = r0.$flows
            int r8 = r8.length
            if (r8 != 0) goto L_0x005d
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x005d:
            java.lang.Object[] r10 = new java.lang.Object[r8]
            kotlinx.coroutines.internal.Symbol r9 = androidx.slice.compat.SliceProviderCompat.C03502.UNINITIALIZED
            java.util.Arrays.fill(r10, r4, r8, r9)
            r9 = 6
            kotlinx.coroutines.channels.AbstractChannel r9 = com.android.systemui.R$anim.Channel$default(r8, r9)
            java.util.concurrent.atomic.AtomicInteger r15 = new java.util.concurrent.atomic.AtomicInteger
            r15.<init>(r8)
            r13 = r4
        L_0x006f:
            if (r13 >= r8) goto L_0x008b
            int r17 = r13 + 1
            kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1 r14 = new kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1
            kotlinx.coroutines.flow.Flow<T>[] r12 = r0.$flows
            r16 = 0
            r11 = r14
            r4 = r14
            r14 = r15
            r18 = r15
            r15 = r9
            r11.<init>(r12, r13, r14, r15, r16)
            kotlinx.coroutines.BuildersKt.launch$default(r2, r7, r7, r4, r6)
            r13 = r17
            r15 = r18
            r4 = 0
            goto L_0x006f
        L_0x008b:
            byte[] r2 = new byte[r8]
            r4 = 0
            r19 = r8
            r8 = r2
        L_0x0091:
            r2 = r19
        L_0x0093:
            int r4 = r4 + r5
            byte r4 = (byte) r4
            r0.L$0 = r10
            r0.L$1 = r9
            r0.L$2 = r8
            r0.I$0 = r2
            r0.I$1 = r4
            r0.label = r5
            java.lang.Object r11 = r9.m326receiveCatchingJP2dKIU(r0)
            if (r11 != r1) goto L_0x00a8
            return r1
        L_0x00a8:
            r15 = r10
            r19 = r4
            r4 = r2
            r2 = r19
        L_0x00ae:
            boolean r10 = r11 instanceof kotlinx.coroutines.channels.ChannelResult.Failed
            if (r10 != 0) goto L_0x00b3
            goto L_0x00b4
        L_0x00b3:
            r11 = r7
        L_0x00b4:
            kotlin.collections.IndexedValue r11 = (kotlin.collections.IndexedValue) r11
            if (r11 != 0) goto L_0x00bb
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x00bb:
            int r10 = r11.index
            r12 = r15[r10]
            T r11 = r11.value
            r15[r10] = r11
            kotlinx.coroutines.internal.Symbol r11 = androidx.slice.compat.SliceProviderCompat.C03502.UNINITIALIZED
            if (r12 != r11) goto L_0x00c9
            int r4 = r4 + -1
        L_0x00c9:
            byte r11 = r8[r10]
            if (r11 != r2) goto L_0x00ce
            goto L_0x00e0
        L_0x00ce:
            byte r11 = (byte) r2
            r8[r10] = r11
            java.lang.Object r10 = r9.m327tryReceivePtdJZtk()
            boolean r11 = r10 instanceof kotlinx.coroutines.channels.ChannelResult.Failed
            if (r11 != 0) goto L_0x00da
            goto L_0x00db
        L_0x00da:
            r10 = r7
        L_0x00db:
            r11 = r10
            kotlin.collections.IndexedValue r11 = (kotlin.collections.IndexedValue) r11
            if (r11 != 0) goto L_0x00bb
        L_0x00e0:
            if (r4 != 0) goto L_0x012c
            kotlin.jvm.functions.Function0<T[]> r10 = r0.$arrayFactory
            java.lang.Object r10 = r10.invoke()
            r14 = r10
            java.lang.Object[] r14 = (java.lang.Object[]) r14
            if (r14 != 0) goto L_0x0104
            kotlin.jvm.functions.Function3<kotlinx.coroutines.flow.FlowCollector<? super R>, T[], kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r10 = r0.$transform
            kotlinx.coroutines.flow.FlowCollector<R> r11 = r0.$this_combineInternal
            r0.L$0 = r15
            r0.L$1 = r9
            r0.L$2 = r8
            r0.I$0 = r4
            r0.I$1 = r2
            r0.label = r3
            java.lang.Object r10 = r10.invoke(r11, r15, r0)
            if (r10 != r1) goto L_0x012c
            return r1
        L_0x0104:
            r12 = 0
            r13 = 0
            r16 = 0
            r17 = 14
            r10 = r15
            r11 = r14
            r3 = r14
            r14 = r16
            r5 = r15
            r15 = r17
            kotlin.collections.ArraysKt___ArraysKt.copyInto$default(r10, r11, r12, r13, r14, r15)
            kotlin.jvm.functions.Function3<kotlinx.coroutines.flow.FlowCollector<? super R>, T[], kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r10 = r0.$transform
            kotlinx.coroutines.flow.FlowCollector<R> r11 = r0.$this_combineInternal
            r0.L$0 = r5
            r0.L$1 = r9
            r0.L$2 = r8
            r0.I$0 = r4
            r0.I$1 = r2
            r0.label = r6
            java.lang.Object r3 = r10.invoke(r11, r3, r0)
            if (r3 != r1) goto L_0x012d
            return r1
        L_0x012c:
            r5 = r15
        L_0x012d:
            r10 = r5
            r3 = 2
            r5 = 1
            r19 = r4
            r4 = r2
            goto L_0x0091
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
