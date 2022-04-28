package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ExceptionsConstuctor.kt */
public final class ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$3 extends Lambda implements Function1<Throwable, Throwable> {
    public final /* synthetic */ Constructor $constructor$inlined;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$3(Constructor constructor) {
        super(1);
        this.$constructor$inlined = constructor;
    }

    /* JADX WARNING: type inference failed for: r4v3, types: [kotlin.Result$Failure] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invoke(java.lang.Object r4) {
        /*
            r3 = this;
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            java.lang.reflect.Constructor r3 = r3.$constructor$inlined     // Catch:{ all -> 0x0022 }
            r0 = 1
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0022 }
            r1 = 0
            java.lang.String r2 = r4.getMessage()     // Catch:{ all -> 0x0022 }
            r0[r1] = r2     // Catch:{ all -> 0x0022 }
            java.lang.Object r3 = r3.newInstance(r0)     // Catch:{ all -> 0x0022 }
            if (r3 == 0) goto L_0x001a
            java.lang.Throwable r3 = (java.lang.Throwable) r3     // Catch:{ all -> 0x0022 }
            r3.initCause(r4)     // Catch:{ all -> 0x0022 }
            goto L_0x0029
        L_0x001a:
            java.lang.NullPointerException r3 = new java.lang.NullPointerException     // Catch:{ all -> 0x0022 }
            java.lang.String r4 = "null cannot be cast to non-null type kotlin.Throwable"
            r3.<init>(r4)     // Catch:{ all -> 0x0022 }
            throw r3     // Catch:{ all -> 0x0022 }
        L_0x0022:
            r3 = move-exception
            kotlin.Result$Failure r4 = new kotlin.Result$Failure
            r4.<init>(r3)
            r3 = r4
        L_0x0029:
            boolean r4 = r3 instanceof kotlin.Result.Failure
            if (r4 == 0) goto L_0x002e
            r3 = 0
        L_0x002e:
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$3.invoke(java.lang.Object):java.lang.Object");
    }
}
