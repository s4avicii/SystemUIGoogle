package com.google.common.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class Throwables {
    public static final String SHARED_SECRETS_CLASSNAME = "sun.misc.SharedSecrets";
    public static final Object jla;

    static {
        Object obj;
        Method method = null;
        try {
            obj = Class.forName(SHARED_SECRETS_CLASSNAME, false, (ClassLoader) null).getMethod("getJavaLangAccess", new Class[0]).invoke((Object) null, new Object[0]);
        } catch (ThreadDeath e) {
            throw e;
        } catch (Throwable unused) {
            obj = null;
        }
        jla = obj;
        if (obj != null) {
            try {
                Class.forName("sun.misc.JavaLangAccess", false, (ClassLoader) null).getMethod("getStackTraceElement", new Class[]{Throwable.class, Integer.TYPE});
            } catch (ThreadDeath e2) {
                throw e2;
            } catch (Throwable unused2) {
            }
        }
        if (obj != null) {
            try {
                method = Class.forName("sun.misc.JavaLangAccess", false, (ClassLoader) null).getMethod("getStackTraceDepth", new Class[]{Throwable.class});
            } catch (ThreadDeath e3) {
                throw e3;
            } catch (IllegalAccessException | UnsupportedOperationException | InvocationTargetException unused3) {
                return;
            } catch (Throwable unused4) {
            }
            if (method != null) {
                method.invoke(obj, new Object[]{new Throwable()});
            }
        }
    }
}
