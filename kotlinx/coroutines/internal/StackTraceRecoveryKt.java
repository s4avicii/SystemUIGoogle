package kotlinx.coroutines.internal;

import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;

/* compiled from: StackTraceRecovery.kt */
public final class StackTraceRecoveryKt {
    public static final String baseContinuationImplClassName;
    public static final String stackTraceRecoveryClassName;

    public static final int frameIndex(StackTraceElement[] stackTraceElementArr, String str) {
        int length = stackTraceElementArr.length;
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            if (Intrinsics.areEqual(str, stackTraceElementArr[i].getClassName())) {
                return i;
            }
            i = i2;
        }
        return -1;
    }

    /* JADX WARNING: type inference failed for: r2v1, types: [kotlin.Result$Failure] */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            java.lang.String r0 = "kotlin.coroutines.jvm.internal.BaseContinuationImpl"
            java.lang.Class r1 = java.lang.Class.forName(r0)     // Catch:{ all -> 0x000b }
            java.lang.String r1 = r1.getCanonicalName()     // Catch:{ all -> 0x000b }
            goto L_0x0012
        L_0x000b:
            r1 = move-exception
            kotlin.Result$Failure r2 = new kotlin.Result$Failure
            r2.<init>(r1)
            r1 = r2
        L_0x0012:
            java.lang.Throwable r2 = kotlin.Result.m320exceptionOrNullimpl(r1)
            if (r2 != 0) goto L_0x0019
            r0 = r1
        L_0x0019:
            java.lang.String r0 = (java.lang.String) r0
            baseContinuationImplClassName = r0
            java.lang.Class<kotlinx.coroutines.internal.StackTraceRecoveryKt> r0 = kotlinx.coroutines.internal.StackTraceRecoveryKt.class
            java.lang.String r0 = r0.getCanonicalName()     // Catch:{ all -> 0x0024 }
            goto L_0x002b
        L_0x0024:
            r0 = move-exception
            kotlin.Result$Failure r1 = new kotlin.Result$Failure
            r1.<init>(r0)
            r0 = r1
        L_0x002b:
            java.lang.Throwable r1 = kotlin.Result.m320exceptionOrNullimpl(r0)
            if (r1 != 0) goto L_0x0032
            goto L_0x0034
        L_0x0032:
            java.lang.String r0 = "kotlinx.coroutines.internal.StackTraceRecoveryKt"
        L_0x0034:
            java.lang.String r0 = (java.lang.String) r0
            stackTraceRecoveryClassName = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.StackTraceRecoveryKt.<clinit>():void");
    }

    public static final <E extends Throwable> E recoverStackTrace(E e) {
        E tryCopyAndVerify;
        int i;
        StackTraceElement stackTraceElement;
        if (!DebugKt.RECOVER_STACK_TRACES || (tryCopyAndVerify = tryCopyAndVerify(e)) == null) {
            return e;
        }
        StackTraceElement[] stackTrace = tryCopyAndVerify.getStackTrace();
        int length = stackTrace.length;
        int frameIndex = frameIndex(stackTrace, stackTraceRecoveryClassName);
        int i2 = frameIndex + 1;
        int frameIndex2 = frameIndex(stackTrace, baseContinuationImplClassName);
        if (frameIndex2 == -1) {
            i = 0;
        } else {
            i = length - frameIndex2;
        }
        int i3 = (length - frameIndex) - i;
        StackTraceElement[] stackTraceElementArr = new StackTraceElement[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            if (i4 == 0) {
                stackTraceElement = new StackTraceElement(Intrinsics.stringPlus("\b\b\b(", "Coroutine boundary"), "\b", "\b", -1);
            } else {
                stackTraceElement = stackTrace[(i2 + i4) - 1];
            }
            stackTraceElementArr[i4] = stackTraceElement;
        }
        tryCopyAndVerify.setStackTrace(stackTraceElementArr);
        return tryCopyAndVerify;
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public static final <E extends java.lang.Throwable> E tryCopyAndVerify(E r10) {
        /*
            int r0 = kotlinx.coroutines.internal.ExceptionsConstuctorKt.throwableFields
            boolean r0 = r10 instanceof kotlinx.coroutines.CopyableThrowable
            r1 = 0
            if (r0 == 0) goto L_0x001f
            r2 = r10
            kotlinx.coroutines.CopyableThrowable r2 = (kotlinx.coroutines.CopyableThrowable) r2     // Catch:{ all -> 0x000f }
            java.lang.Throwable r2 = r2.createCopy()     // Catch:{ all -> 0x000f }
            goto L_0x0016
        L_0x000f:
            r2 = move-exception
            kotlin.Result$Failure r3 = new kotlin.Result$Failure
            r3.<init>(r2)
            r2 = r3
        L_0x0016:
            boolean r3 = r2 instanceof kotlin.Result.Failure
            if (r3 == 0) goto L_0x001b
            r2 = r1
        L_0x001b:
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            goto L_0x0169
        L_0x001f:
            java.util.concurrent.locks.ReentrantReadWriteLock r2 = kotlinx.coroutines.internal.ExceptionsConstuctorKt.cacheLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r3 = r2.readLock()
            r3.lock()
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.Function1<java.lang.Throwable, java.lang.Throwable>> r4 = kotlinx.coroutines.internal.ExceptionsConstuctorKt.exceptionCtors     // Catch:{ all -> 0x017e }
            java.lang.Class r5 = r10.getClass()     // Catch:{ all -> 0x017e }
            java.lang.Object r4 = r4.get(r5)     // Catch:{ all -> 0x017e }
            kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4     // Catch:{ all -> 0x017e }
            r3.unlock()
            if (r4 != 0) goto L_0x0163
            int r3 = kotlinx.coroutines.internal.ExceptionsConstuctorKt.throwableFields
            java.lang.Class r4 = r10.getClass()
            r5 = 0
            int r4 = kotlinx.coroutines.internal.ExceptionsConstuctorKt.fieldsCountOrDefault(r4, r5)
            if (r3 == r4) goto L_0x008b
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r3 = r2.readLock()
            int r4 = r2.getWriteHoldCount()
            if (r4 != 0) goto L_0x0055
            int r4 = r2.getReadHoldCount()
            goto L_0x0056
        L_0x0055:
            r4 = r5
        L_0x0056:
            r6 = r5
        L_0x0057:
            if (r6 >= r4) goto L_0x005f
            int r6 = r6 + 1
            r3.unlock()
            goto L_0x0057
        L_0x005f:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r2 = r2.writeLock()
            r2.lock()
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.Function1<java.lang.Throwable, java.lang.Throwable>> r6 = kotlinx.coroutines.internal.ExceptionsConstuctorKt.exceptionCtors     // Catch:{ all -> 0x007e }
            java.lang.Class r7 = r10.getClass()     // Catch:{ all -> 0x007e }
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$4$1 r8 = kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$4$1.INSTANCE     // Catch:{ all -> 0x007e }
            r6.put(r7, r8)     // Catch:{ all -> 0x007e }
        L_0x0071:
            if (r5 >= r4) goto L_0x0079
            int r5 = r5 + 1
            r3.lock()
            goto L_0x0071
        L_0x0079:
            r2.unlock()
            goto L_0x014d
        L_0x007e:
            r10 = move-exception
        L_0x007f:
            if (r5 >= r4) goto L_0x0087
            int r5 = r5 + 1
            r3.lock()
            goto L_0x007f
        L_0x0087:
            r2.unlock()
            throw r10
        L_0x008b:
            java.lang.Class r2 = r10.getClass()
            java.lang.reflect.Constructor[] r2 = r2.getConstructors()
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$$inlined$sortedByDescending$1 r3 = new kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$$inlined$sortedByDescending$1
            r3.<init>()
            int r4 = r2.length
            r6 = 1
            if (r4 != 0) goto L_0x009e
            r4 = r6
            goto L_0x009f
        L_0x009e:
            r4 = r5
        L_0x009f:
            if (r4 == 0) goto L_0x00a2
            goto L_0x00ad
        L_0x00a2:
            int r4 = r2.length
            java.lang.Object[] r2 = java.util.Arrays.copyOf(r2, r4)
            int r4 = r2.length
            if (r4 <= r6) goto L_0x00ad
            java.util.Arrays.sort(r2, r3)
        L_0x00ad:
            java.util.List r2 = java.util.Arrays.asList(r2)
            java.util.Iterator r2 = r2.iterator()
            r3 = r1
        L_0x00b6:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x010f
            java.lang.Object r3 = r2.next()
            java.lang.reflect.Constructor r3 = (java.lang.reflect.Constructor) r3
            java.lang.Class<java.lang.String> r4 = java.lang.String.class
            java.lang.Class[] r7 = r3.getParameterTypes()
            int r8 = r7.length
            if (r8 == 0) goto L_0x0107
            if (r8 == r6) goto L_0x00e9
            r9 = 2
            if (r8 == r9) goto L_0x00d1
            goto L_0x0105
        L_0x00d1:
            r8 = r7[r5]
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r4)
            if (r4 == 0) goto L_0x0105
            r4 = r7[r6]
            java.lang.Class<java.lang.Throwable> r7 = java.lang.Throwable.class
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r7)
            if (r4 == 0) goto L_0x0105
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$1 r4 = new kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$1
            r4.<init>(r3)
            goto L_0x010c
        L_0x00e9:
            r7 = r7[r5]
            java.lang.Class<java.lang.Throwable> r8 = java.lang.Throwable.class
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r8)
            if (r8 == 0) goto L_0x00f9
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$2 r4 = new kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$2
            r4.<init>(r3)
            goto L_0x010c
        L_0x00f9:
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r4)
            if (r4 == 0) goto L_0x0105
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$3 r4 = new kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$3
            r4.<init>(r3)
            goto L_0x010c
        L_0x0105:
            r3 = r1
            goto L_0x010d
        L_0x0107:
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$4 r4 = new kotlinx.coroutines.internal.ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$4
            r4.<init>(r3)
        L_0x010c:
            r3 = r4
        L_0x010d:
            if (r3 == 0) goto L_0x00b6
        L_0x010f:
            java.util.concurrent.locks.ReentrantReadWriteLock r2 = kotlinx.coroutines.internal.ExceptionsConstuctorKt.cacheLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r4 = r2.readLock()
            int r6 = r2.getWriteHoldCount()
            if (r6 != 0) goto L_0x0120
            int r6 = r2.getReadHoldCount()
            goto L_0x0121
        L_0x0120:
            r6 = r5
        L_0x0121:
            r7 = r5
        L_0x0122:
            if (r7 >= r6) goto L_0x012a
            int r7 = r7 + 1
            r4.unlock()
            goto L_0x0122
        L_0x012a:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r2 = r2.writeLock()
            r2.lock()
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.Function1<java.lang.Throwable, java.lang.Throwable>> r7 = kotlinx.coroutines.internal.ExceptionsConstuctorKt.exceptionCtors     // Catch:{ all -> 0x0156 }
            java.lang.Class r8 = r10.getClass()     // Catch:{ all -> 0x0156 }
            if (r3 != 0) goto L_0x013c
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$5$1 r9 = kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$5$1.INSTANCE     // Catch:{ all -> 0x0156 }
            goto L_0x013d
        L_0x013c:
            r9 = r3
        L_0x013d:
            r7.put(r8, r9)     // Catch:{ all -> 0x0156 }
        L_0x0140:
            if (r5 >= r6) goto L_0x0148
            int r5 = r5 + 1
            r4.lock()
            goto L_0x0140
        L_0x0148:
            r2.unlock()
            if (r3 != 0) goto L_0x014f
        L_0x014d:
            r2 = r1
            goto L_0x0169
        L_0x014f:
            java.lang.Object r2 = r3.invoke(r10)
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            goto L_0x0169
        L_0x0156:
            r10 = move-exception
        L_0x0157:
            if (r5 >= r6) goto L_0x015f
            int r5 = r5 + 1
            r4.lock()
            goto L_0x0157
        L_0x015f:
            r2.unlock()
            throw r10
        L_0x0163:
            java.lang.Object r2 = r4.invoke(r10)
            java.lang.Throwable r2 = (java.lang.Throwable) r2
        L_0x0169:
            if (r2 != 0) goto L_0x016c
            return r1
        L_0x016c:
            if (r0 != 0) goto L_0x017d
            java.lang.String r0 = r2.getMessage()
            java.lang.String r10 = r10.getMessage()
            boolean r10 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r10)
            if (r10 != 0) goto L_0x017d
            return r1
        L_0x017d:
            return r2
        L_0x017e:
            r10 = move-exception
            r3.unlock()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.StackTraceRecoveryKt.tryCopyAndVerify(java.lang.Throwable):java.lang.Throwable");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:77:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Throwable access$recoverFromStackFrame(java.lang.Throwable r12, kotlin.coroutines.jvm.internal.CoroutineStackFrame r13) {
        /*
            java.lang.Throwable r0 = r12.getCause()
            java.lang.String r1 = "\b\b\b"
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x0041
            java.lang.Class r4 = r0.getClass()
            java.lang.Class r5 = r12.getClass()
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
            if (r4 == 0) goto L_0x0041
            java.lang.StackTraceElement[] r4 = r12.getStackTrace()
            int r5 = r4.length
            r6 = r3
        L_0x001e:
            if (r6 >= r5) goto L_0x0030
            r7 = r4[r6]
            int r6 = r6 + 1
            java.lang.String r7 = r7.getClassName()
            boolean r7 = r7.startsWith(r1)
            if (r7 == 0) goto L_0x001e
            r5 = r2
            goto L_0x0031
        L_0x0030:
            r5 = r3
        L_0x0031:
            if (r5 == 0) goto L_0x0039
            kotlin.Pair r5 = new kotlin.Pair
            r5.<init>(r0, r4)
            goto L_0x0049
        L_0x0039:
            java.lang.StackTraceElement[] r0 = new java.lang.StackTraceElement[r3]
            kotlin.Pair r4 = new kotlin.Pair
            r4.<init>(r12, r0)
            goto L_0x0048
        L_0x0041:
            java.lang.StackTraceElement[] r0 = new java.lang.StackTraceElement[r3]
            kotlin.Pair r4 = new kotlin.Pair
            r4.<init>(r12, r0)
        L_0x0048:
            r5 = r4
        L_0x0049:
            java.lang.Object r0 = r5.component1()
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            java.lang.Object r4 = r5.component2()
            java.lang.StackTraceElement[] r4 = (java.lang.StackTraceElement[]) r4
            java.lang.Throwable r5 = tryCopyAndVerify(r0)
            if (r5 != 0) goto L_0x005d
            goto L_0x0144
        L_0x005d:
            java.util.ArrayDeque r6 = new java.util.ArrayDeque
            r6.<init>()
            java.lang.StackTraceElement r7 = r13.getStackTraceElement()
            if (r7 != 0) goto L_0x0069
            goto L_0x006c
        L_0x0069:
            r6.add(r7)
        L_0x006c:
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r13 = r13.getCallerFrame()
            if (r13 != 0) goto L_0x0145
            boolean r13 = r6.isEmpty()
            if (r13 == 0) goto L_0x007a
            goto L_0x0144
        L_0x007a:
            r13 = -1
            if (r0 == r12) goto L_0x00e8
            int r12 = r4.length
            r7 = r3
        L_0x007f:
            if (r7 >= r12) goto L_0x0092
            int r8 = r7 + 1
            r9 = r4[r7]
            java.lang.String r9 = r9.getClassName()
            boolean r9 = r9.startsWith(r1)
            if (r9 == 0) goto L_0x0090
            goto L_0x0093
        L_0x0090:
            r7 = r8
            goto L_0x007f
        L_0x0092:
            r7 = r13
        L_0x0093:
            int r7 = r7 + r2
            int r12 = r4.length
            int r12 = r12 - r2
            if (r7 > r12) goto L_0x00e8
        L_0x0098:
            int r1 = r12 + -1
            r8 = r4[r12]
            java.lang.Object r9 = r6.getLast()
            java.lang.StackTraceElement r9 = (java.lang.StackTraceElement) r9
            int r10 = r8.getLineNumber()
            int r11 = r9.getLineNumber()
            if (r10 != r11) goto L_0x00d8
            java.lang.String r10 = r8.getMethodName()
            java.lang.String r11 = r9.getMethodName()
            boolean r10 = kotlin.jvm.internal.Intrinsics.areEqual(r10, r11)
            if (r10 == 0) goto L_0x00d8
            java.lang.String r10 = r8.getFileName()
            java.lang.String r11 = r9.getFileName()
            boolean r10 = kotlin.jvm.internal.Intrinsics.areEqual(r10, r11)
            if (r10 == 0) goto L_0x00d8
            java.lang.String r8 = r8.getClassName()
            java.lang.String r9 = r9.getClassName()
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r9)
            if (r8 == 0) goto L_0x00d8
            r8 = r2
            goto L_0x00d9
        L_0x00d8:
            r8 = r3
        L_0x00d9:
            if (r8 == 0) goto L_0x00de
            r6.removeLast()
        L_0x00de:
            r8 = r4[r12]
            r6.addFirst(r8)
            if (r12 != r7) goto L_0x00e6
            goto L_0x00e8
        L_0x00e6:
            r12 = r1
            goto L_0x0098
        L_0x00e8:
            java.lang.StackTraceElement r12 = new java.lang.StackTraceElement
            java.lang.String r1 = "\b\b\b("
            java.lang.String r2 = "Coroutine boundary"
            java.lang.String r1 = kotlin.jvm.internal.Intrinsics.stringPlus(r1, r2)
            java.lang.String r2 = "\b"
            r12.<init>(r1, r2, r2, r13)
            r6.addFirst(r12)
            java.lang.StackTraceElement[] r12 = r0.getStackTrace()
            java.lang.String r0 = baseContinuationImplClassName
            int r0 = frameIndex(r12, r0)
            if (r0 != r13) goto L_0x0117
            java.lang.StackTraceElement[] r12 = new java.lang.StackTraceElement[r3]
            java.lang.Object[] r12 = r6.toArray(r12)
            java.lang.String r13 = "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>"
            java.util.Objects.requireNonNull(r12, r13)
            java.lang.StackTraceElement[] r12 = (java.lang.StackTraceElement[]) r12
            r5.setStackTrace(r12)
            goto L_0x0143
        L_0x0117:
            int r13 = r6.size()
            int r13 = r13 + r0
            java.lang.StackTraceElement[] r13 = new java.lang.StackTraceElement[r13]
            r1 = r3
        L_0x011f:
            if (r1 >= r0) goto L_0x0129
            int r2 = r1 + 1
            r4 = r12[r1]
            r13[r1] = r4
            r1 = r2
            goto L_0x011f
        L_0x0129:
            java.util.Iterator r12 = r6.iterator()
        L_0x012d:
            boolean r1 = r12.hasNext()
            if (r1 == 0) goto L_0x0140
            int r1 = r3 + 1
            java.lang.Object r2 = r12.next()
            java.lang.StackTraceElement r2 = (java.lang.StackTraceElement) r2
            int r3 = r3 + r0
            r13[r3] = r2
            r3 = r1
            goto L_0x012d
        L_0x0140:
            r5.setStackTrace(r13)
        L_0x0143:
            r12 = r5
        L_0x0144:
            return r12
        L_0x0145:
            java.lang.StackTraceElement r7 = r13.getStackTraceElement()
            if (r7 != 0) goto L_0x014d
            goto L_0x006c
        L_0x014d:
            r6.add(r7)
            goto L_0x006c
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.StackTraceRecoveryKt.access$recoverFromStackFrame(java.lang.Throwable, kotlin.coroutines.jvm.internal.CoroutineStackFrame):java.lang.Throwable");
    }

    public static final <E extends Throwable> E unwrapImpl(E e) {
        E cause = e.getCause();
        if (cause != null && Intrinsics.areEqual(cause.getClass(), e.getClass())) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            int length = stackTrace.length;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                StackTraceElement stackTraceElement = stackTrace[i];
                i++;
                if (stackTraceElement.getClassName().startsWith("\b\b\b")) {
                    z = true;
                    break;
                }
            }
            if (z) {
                return cause;
            }
        }
        return e;
    }
}
