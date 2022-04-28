package kotlin.coroutines.jvm.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* compiled from: DebugMetadata.kt */
public @interface DebugMetadata {
    /* renamed from: c */
    String mo21073c() default "";

    /* renamed from: f */
    String mo21074f() default "";

    /* renamed from: l */
    int[] mo21075l() default {};

    /* renamed from: m */
    String mo21076m() default "";

    /* renamed from: v */
    int mo21077v() default 1;
}
