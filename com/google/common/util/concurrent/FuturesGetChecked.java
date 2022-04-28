package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public final class FuturesGetChecked {
    public static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function<Constructor<?>, Boolean>() {
        public final Object apply(Object obj) {
            return Boolean.valueOf(Arrays.asList(((Constructor) obj).getParameterTypes()).contains(String.class));
        }
    }).reverse();

    public interface GetCheckedTypeValidator {
        void validateClass(Class<? extends Exception> cls);
    }

    public static class GetCheckedTypeValidatorHolder {
        public static final GetCheckedTypeValidator BEST_VALIDATOR = FuturesGetChecked.weakSetValidator();

        public enum WeakSetValidator implements GetCheckedTypeValidator {
            ;
            
            public static final CopyOnWriteArraySet validClasses = null;

            /* access modifiers changed from: public */
            static {
                validClasses = new CopyOnWriteArraySet();
            }

            /* access modifiers changed from: public */
            WeakSetValidator() {
            }

            public final void validateClass(Class<? extends Exception> cls) {
                Iterator it = validClasses.iterator();
                while (it.hasNext()) {
                    if (cls.equals(((WeakReference) it.next()).get())) {
                        return;
                    }
                }
                FuturesGetChecked.checkExceptionClassValidity(cls);
                CopyOnWriteArraySet copyOnWriteArraySet = validClasses;
                if (copyOnWriteArraySet.size() > 1000) {
                    copyOnWriteArraySet.clear();
                }
                copyOnWriteArraySet.add(new WeakReference(cls));
            }
        }
    }

    public static boolean isCheckedException(Class<? extends Exception> cls) {
        return !RuntimeException.class.isAssignableFrom(cls);
    }

    public static void checkExceptionClassValidity(Class<? extends Exception> cls) {
        boolean z;
        if (isCheckedException(cls)) {
            try {
                newWithCause(cls, new Exception());
                z = true;
            } catch (Exception unused) {
                z = false;
            }
            if (!z) {
                throw new IllegalArgumentException(Strings.lenientFormat("Futures.getChecked exception type (%s) must be an accessible class with an accessible constructor whose parameters (if any) must be of type String and/or Throwable", cls));
            }
            return;
        }
        throw new IllegalArgumentException(Strings.lenientFormat("Futures.getChecked exception type (%s) must not be a RuntimeException", cls));
    }

    @CanIgnoreReturnValue
    public static <V, X extends Exception> V getChecked(GetCheckedTypeValidator getCheckedTypeValidator, Future<V> future, Class<X> cls) throws Exception {
        getCheckedTypeValidator.validateClass(cls);
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw newWithCause(cls, e);
        } catch (ExecutionException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof Error) {
                throw new ExecutionError((Error) cause);
            } else if (cause instanceof RuntimeException) {
                throw new UncheckedExecutionException(cause);
            } else {
                throw newWithCause(cls, cause);
            }
        }
    }

    public static <X extends Exception> X newWithCause(Class<X> cls, Throwable th) {
        X x;
        List asList = Arrays.asList(cls.getConstructors());
        Ordering<Constructor<?>> ordering = WITH_STRING_PARAM_FIRST;
        Objects.requireNonNull(ordering);
        if (!(asList instanceof Collection)) {
            Iterator it = asList.iterator();
            ArrayList arrayList = new ArrayList();
            Objects.requireNonNull(it);
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
            asList = arrayList;
        }
        Object[] array = asList.toArray();
        Arrays.sort(array, ordering);
        List asList2 = Arrays.asList(array);
        Objects.requireNonNull(asList2);
        Iterator it2 = new ArrayList(asList2).iterator();
        while (it2.hasNext()) {
            Constructor constructor = (Constructor) it2.next();
            Class[] parameterTypes = constructor.getParameterTypes();
            Object[] objArr = new Object[parameterTypes.length];
            int i = 0;
            while (true) {
                x = null;
                if (i < parameterTypes.length) {
                    Class cls2 = parameterTypes[i];
                    if (!cls2.equals(String.class)) {
                        if (!cls2.equals(Throwable.class)) {
                            break;
                        }
                        objArr[i] = th;
                    } else {
                        objArr[i] = th.toString();
                    }
                    i++;
                } else {
                    try {
                        x = constructor.newInstance(objArr);
                        break;
                    } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException unused) {
                    }
                }
            }
            X x2 = (Exception) x;
            if (x2 != null) {
                if (x2.getCause() == null) {
                    x2.initCause(th);
                }
                return x2;
            }
        }
        throw new IllegalArgumentException("No appropriate constructor for exception of type " + cls + " in response to chained exception", th);
    }

    public static GetCheckedTypeValidator weakSetValidator() {
        return GetCheckedTypeValidatorHolder.WeakSetValidator.INSTANCE;
    }
}
