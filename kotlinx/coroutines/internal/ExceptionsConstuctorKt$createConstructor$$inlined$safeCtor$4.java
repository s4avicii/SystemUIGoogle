package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ExceptionsConstuctor.kt */
public final class ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$4 extends Lambda implements Function1<Throwable, Throwable> {
    public final /* synthetic */ Constructor $constructor$inlined;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$4(Constructor constructor) {
        super(1);
        this.$constructor$inlined = constructor;
    }

    /* JADX WARNING: type inference failed for: r2v3, types: [kotlin.Result$Failure] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invoke(java.lang.Object r2) {
        /*
            r1 = this;
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            java.lang.reflect.Constructor r1 = r1.$constructor$inlined     // Catch:{ all -> 0x001b }
            r0 = 0
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x001b }
            java.lang.Object r1 = r1.newInstance(r0)     // Catch:{ all -> 0x001b }
            if (r1 == 0) goto L_0x0013
            java.lang.Throwable r1 = (java.lang.Throwable) r1     // Catch:{ all -> 0x001b }
            r1.initCause(r2)     // Catch:{ all -> 0x001b }
            goto L_0x0022
        L_0x0013:
            java.lang.NullPointerException r1 = new java.lang.NullPointerException     // Catch:{ all -> 0x001b }
            java.lang.String r2 = "null cannot be cast to non-null type kotlin.Throwable"
            r1.<init>(r2)     // Catch:{ all -> 0x001b }
            throw r1     // Catch:{ all -> 0x001b }
        L_0x001b:
            r1 = move-exception
            kotlin.Result$Failure r2 = new kotlin.Result$Failure
            r2.<init>(r1)
            r1 = r2
        L_0x0022:
            boolean r2 = r1 instanceof kotlin.Result.Failure
            if (r2 == 0) goto L_0x0027
            r1 = 0
        L_0x0027:
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$4.invoke(java.lang.Object):java.lang.Object");
    }
}
