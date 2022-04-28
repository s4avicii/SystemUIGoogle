package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Method;

/* compiled from: DebugMetadata.kt */
public final class ModuleNameRetriever {
    public static Cache cache;
    public static final Cache notOnJava9 = new Cache((Method) null, (Method) null, (Method) null);

    /* compiled from: DebugMetadata.kt */
    public static final class Cache {
        public final Method getDescriptorMethod;
        public final Method getModuleMethod;
        public final Method nameMethod;

        public Cache(Method method, Method method2, Method method3) {
            this.getModuleMethod = method;
            this.getDescriptorMethod = method2;
            this.nameMethod = method3;
        }
    }
}
