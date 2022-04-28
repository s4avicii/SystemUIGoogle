package com.google.common.util.concurrent;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.google.common.base.Platform;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ForOverride;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

public abstract class AbstractFuture<V> extends InternalFutureFailureAccess implements ListenableFuture<V> {
    public static final AtomicHelper ATOMIC_HELPER;
    public static final boolean GENERATE_CANCELLATION_CAUSES;
    public static final Object NULL = new Object();
    public static final Logger log = Logger.getLogger(AbstractFuture.class.getName());
    public volatile Listener listeners;
    public volatile Object value;
    public volatile Waiter waiters;

    public static abstract class AtomicHelper {
        public abstract boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2);

        public abstract boolean casValue(AbstractFuture<?> abstractFuture, Object obj, Object obj2);

        public abstract boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2);

        public abstract void putNext(Waiter waiter, Waiter waiter2);

        public abstract void putThread(Waiter waiter, Thread thread);
    }

    public static final class Cancellation {
        public static final Cancellation CAUSELESS_CANCELLED;
        public static final Cancellation CAUSELESS_INTERRUPTED;
        public final Throwable cause;
        public final boolean wasInterrupted;

        static {
            if (AbstractFuture.GENERATE_CANCELLATION_CAUSES) {
                CAUSELESS_CANCELLED = null;
                CAUSELESS_INTERRUPTED = null;
                return;
            }
            CAUSELESS_CANCELLED = new Cancellation(false, (Throwable) null);
            CAUSELESS_INTERRUPTED = new Cancellation(true, (Throwable) null);
        }

        public Cancellation(boolean z, Throwable th) {
            this.wasInterrupted = z;
            this.cause = th;
        }
    }

    public static final class SafeAtomicHelper extends AtomicHelper {
        public final AtomicReferenceFieldUpdater<AbstractFuture, Listener> listenersUpdater;
        public final AtomicReferenceFieldUpdater<AbstractFuture, Object> valueUpdater;
        public final AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater;
        public final AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater;
        public final AtomicReferenceFieldUpdater<AbstractFuture, Waiter> waitersUpdater;

        public final boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            return this.listenersUpdater.compareAndSet(abstractFuture, listener, listener2);
        }

        public final boolean casValue(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            return this.valueUpdater.compareAndSet(abstractFuture, obj, obj2);
        }

        public final boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            return this.waitersUpdater.compareAndSet(abstractFuture, waiter, waiter2);
        }

        public final void putNext(Waiter waiter, Waiter waiter2) {
            this.waiterNextUpdater.lazySet(waiter, waiter2);
        }

        public final void putThread(Waiter waiter, Thread thread) {
            this.waiterThreadUpdater.lazySet(waiter, thread);
        }

        public SafeAtomicHelper(AtomicReferenceFieldUpdater<Waiter, Thread> atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<Waiter, Waiter> atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<AbstractFuture, Waiter> atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<AbstractFuture, Listener> atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater<AbstractFuture, Object> atomicReferenceFieldUpdater5) {
            this.waiterThreadUpdater = atomicReferenceFieldUpdater;
            this.waiterNextUpdater = atomicReferenceFieldUpdater2;
            this.waitersUpdater = atomicReferenceFieldUpdater3;
            this.listenersUpdater = atomicReferenceFieldUpdater4;
            this.valueUpdater = atomicReferenceFieldUpdater5;
        }
    }

    public static final class SetFuture<V> implements Runnable {
        public final ListenableFuture<? extends V> future;
        public final AbstractFuture<V> owner;

        public final void run() {
            if (this.owner.value == this) {
                if (AbstractFuture.ATOMIC_HELPER.casValue(this.owner, this, AbstractFuture.getFutureValue(this.future))) {
                    AbstractFuture.complete(this.owner);
                }
            }
        }

        public SetFuture(AbstractFuture<V> abstractFuture, ListenableFuture<? extends V> listenableFuture) {
            this.owner = abstractFuture;
            this.future = listenableFuture;
        }
    }

    public interface Trusted<V> extends ListenableFuture<V> {
    }

    public static abstract class TrustedFuture<V> extends AbstractFuture<V> implements Trusted<V> {
        @CanIgnoreReturnValue
        public final V get() throws InterruptedException, ExecutionException {
            return AbstractFuture.super.get();
        }

        @CanIgnoreReturnValue
        public final V get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            return AbstractFuture.super.get(j, timeUnit);
        }

        public final boolean isCancelled() {
            return this.value instanceof Cancellation;
        }

        @CanIgnoreReturnValue
        public final boolean cancel(boolean z) {
            return AbstractFuture.super.cancel(z);
        }

        public final boolean isDone() {
            return AbstractFuture.super.isDone();
        }

        public final void addListener(Runnable runnable, Executor executor) {
            AbstractFuture.super.addListener(runnable, executor);
        }
    }

    public static final class UnsafeAtomicHelper extends AtomicHelper {
        public static final long LISTENERS_OFFSET;
        public static final Unsafe UNSAFE;
        public static final long VALUE_OFFSET;
        public static final long WAITERS_OFFSET;
        public static final long WAITER_NEXT_OFFSET;
        public static final long WAITER_THREAD_OFFSET;

        /* JADX WARNING: Code restructure failed: missing block: B:20:0x006d, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0079, code lost:
            throw new java.lang.RuntimeException("Could not initialize intrinsics", r0.getCause());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:4:?, code lost:
            r1 = (sun.misc.Unsafe) java.security.AccessController.doPrivileged(new com.google.common.util.concurrent.AbstractFuture.UnsafeAtomicHelper.C24821());
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0007 */
        static {
            /*
                java.lang.Class<com.google.common.util.concurrent.AbstractFuture$Waiter> r0 = com.google.common.util.concurrent.AbstractFuture.Waiter.class
                sun.misc.Unsafe r1 = sun.misc.Unsafe.getUnsafe()     // Catch:{ SecurityException -> 0x0007 }
                goto L_0x0012
            L_0x0007:
                com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper$1 r1 = new com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper$1     // Catch:{ PrivilegedActionException -> 0x006d }
                r1.<init>()     // Catch:{ PrivilegedActionException -> 0x006d }
                java.lang.Object r1 = java.security.AccessController.doPrivileged(r1)     // Catch:{ PrivilegedActionException -> 0x006d }
                sun.misc.Unsafe r1 = (sun.misc.Unsafe) r1     // Catch:{ PrivilegedActionException -> 0x006d }
            L_0x0012:
                java.lang.Class<com.google.common.util.concurrent.AbstractFuture> r2 = com.google.common.util.concurrent.AbstractFuture.class
                java.lang.String r3 = "waiters"
                java.lang.reflect.Field r3 = r2.getDeclaredField(r3)     // Catch:{ Exception -> 0x0056 }
                long r3 = r1.objectFieldOffset(r3)     // Catch:{ Exception -> 0x0056 }
                WAITERS_OFFSET = r3     // Catch:{ Exception -> 0x0056 }
                java.lang.String r3 = "listeners"
                java.lang.reflect.Field r3 = r2.getDeclaredField(r3)     // Catch:{ Exception -> 0x0056 }
                long r3 = r1.objectFieldOffset(r3)     // Catch:{ Exception -> 0x0056 }
                LISTENERS_OFFSET = r3     // Catch:{ Exception -> 0x0056 }
                java.lang.String r3 = "value"
                java.lang.reflect.Field r2 = r2.getDeclaredField(r3)     // Catch:{ Exception -> 0x0056 }
                long r2 = r1.objectFieldOffset(r2)     // Catch:{ Exception -> 0x0056 }
                VALUE_OFFSET = r2     // Catch:{ Exception -> 0x0056 }
                java.lang.String r2 = "thread"
                java.lang.reflect.Field r2 = r0.getDeclaredField(r2)     // Catch:{ Exception -> 0x0056 }
                long r2 = r1.objectFieldOffset(r2)     // Catch:{ Exception -> 0x0056 }
                WAITER_THREAD_OFFSET = r2     // Catch:{ Exception -> 0x0056 }
                java.lang.String r2 = "next"
                java.lang.reflect.Field r0 = r0.getDeclaredField(r2)     // Catch:{ Exception -> 0x0056 }
                long r2 = r1.objectFieldOffset(r0)     // Catch:{ Exception -> 0x0056 }
                WAITER_NEXT_OFFSET = r2     // Catch:{ Exception -> 0x0056 }
                UNSAFE = r1     // Catch:{ Exception -> 0x0056 }
                return
            L_0x0056:
                r0 = move-exception
                java.lang.String r1 = com.google.common.base.Throwables.SHARED_SECRETS_CLASSNAME
                boolean r1 = r0 instanceof java.lang.RuntimeException
                if (r1 != 0) goto L_0x006a
                boolean r1 = r0 instanceof java.lang.Error
                if (r1 != 0) goto L_0x0067
                java.lang.RuntimeException r1 = new java.lang.RuntimeException
                r1.<init>(r0)
                throw r1
            L_0x0067:
                java.lang.Error r0 = (java.lang.Error) r0
                throw r0
            L_0x006a:
                java.lang.RuntimeException r0 = (java.lang.RuntimeException) r0
                throw r0
            L_0x006d:
                r0 = move-exception
                java.lang.RuntimeException r1 = new java.lang.RuntimeException
                java.lang.Throwable r0 = r0.getCause()
                java.lang.String r2 = "Could not initialize intrinsics"
                r1.<init>(r2, r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractFuture.UnsafeAtomicHelper.<clinit>():void");
        }

        public final boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, LISTENERS_OFFSET, listener, listener2);
        }

        public final boolean casValue(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, VALUE_OFFSET, obj, obj2);
        }

        public final boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, WAITERS_OFFSET, waiter, waiter2);
        }

        public final void putNext(Waiter waiter, Waiter waiter2) {
            UNSAFE.putObject(waiter, WAITER_NEXT_OFFSET, waiter2);
        }

        public final void putThread(Waiter waiter, Thread thread) {
            UNSAFE.putObject(waiter, WAITER_THREAD_OFFSET, thread);
        }
    }

    public static final class Waiter {
        public static final Waiter TOMBSTONE = new Waiter(0);
        public volatile Waiter next;
        public volatile Thread thread;

        public Waiter(int i) {
        }

        public Waiter() {
            AbstractFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
        }
    }

    public static void complete(AbstractFuture<?> abstractFuture) {
        Waiter waiter;
        Listener listener;
        Listener listener2 = null;
        while (true) {
            Objects.requireNonNull(abstractFuture);
            do {
                waiter = abstractFuture.waiters;
            } while (!ATOMIC_HELPER.casWaiters(abstractFuture, waiter, Waiter.TOMBSTONE));
            while (waiter != null) {
                Thread thread = waiter.thread;
                if (thread != null) {
                    waiter.thread = null;
                    LockSupport.unpark(thread);
                }
                waiter = waiter.next;
            }
            abstractFuture.afterDone();
            do {
                listener = abstractFuture.listeners;
            } while (!ATOMIC_HELPER.casListeners(abstractFuture, listener, Listener.TOMBSTONE));
            while (listener != null) {
                Listener listener3 = listener.next;
                listener.next = listener2;
                listener2 = listener;
                listener = listener3;
            }
            while (listener2 != null) {
                Listener listener4 = listener2.next;
                Runnable runnable = listener2.task;
                Objects.requireNonNull(runnable);
                Runnable runnable2 = runnable;
                if (runnable2 instanceof SetFuture) {
                    SetFuture setFuture = (SetFuture) runnable2;
                    AbstractFuture<V> abstractFuture2 = setFuture.owner;
                    if (abstractFuture2.value == setFuture) {
                        if (ATOMIC_HELPER.casValue(abstractFuture2, setFuture, getFutureValue(setFuture.future))) {
                            AbstractFuture<V> abstractFuture3 = abstractFuture2;
                            listener2 = listener4;
                            abstractFuture = abstractFuture3;
                        }
                    } else {
                        continue;
                    }
                } else {
                    Executor executor = listener2.executor;
                    Objects.requireNonNull(executor);
                    executeListener(runnable2, executor);
                }
                listener2 = listener4;
            }
            return;
        }
    }

    @ForOverride
    public void afterDone() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ac  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00d3  */
    @com.google.errorprone.annotations.CanIgnoreReturnValue
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public V get(long r18, java.util.concurrent.TimeUnit r20) throws java.lang.InterruptedException, java.util.concurrent.TimeoutException, java.util.concurrent.ExecutionException {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r3 = r20
            long r4 = r3.toNanos(r1)
            boolean r6 = java.lang.Thread.interrupted()
            if (r6 != 0) goto L_0x0185
            java.lang.Object r6 = r0.value
            r7 = 1
            if (r6 == 0) goto L_0x0017
            r8 = r7
            goto L_0x0018
        L_0x0017:
            r8 = 0
        L_0x0018:
            boolean r9 = r6 instanceof com.google.common.util.concurrent.AbstractFuture.SetFuture
            r9 = r9 ^ r7
            r8 = r8 & r9
            if (r8 == 0) goto L_0x0023
            java.lang.Object r0 = getDoneValue(r6)
            return r0
        L_0x0023:
            r8 = 0
            int r6 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r6 <= 0) goto L_0x002f
            long r10 = java.lang.System.nanoTime()
            long r10 = r10 + r4
            goto L_0x0030
        L_0x002f:
            r10 = r8
        L_0x0030:
            r12 = 1000(0x3e8, double:4.94E-321)
            int r6 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r6 < 0) goto L_0x009f
            com.google.common.util.concurrent.AbstractFuture$Waiter r6 = r0.waiters
            com.google.common.util.concurrent.AbstractFuture$Waiter r8 = com.google.common.util.concurrent.AbstractFuture.Waiter.TOMBSTONE
            if (r6 == r8) goto L_0x0095
            com.google.common.util.concurrent.AbstractFuture$Waiter r8 = new com.google.common.util.concurrent.AbstractFuture$Waiter
            r8.<init>()
        L_0x0041:
            com.google.common.util.concurrent.AbstractFuture$AtomicHelper r9 = ATOMIC_HELPER
            r9.putNext(r8, r6)
            boolean r6 = r9.casWaiters(r0, r6, r8)
            if (r6 == 0) goto L_0x008f
        L_0x004c:
            r14 = 2147483647999999999(0x1dcd64ffffffffff, double:3.98785104510193E-165)
            long r4 = java.lang.Math.min(r4, r14)
            java.util.concurrent.locks.LockSupport.parkNanos(r0, r4)
            boolean r4 = java.lang.Thread.interrupted()
            if (r4 != 0) goto L_0x0086
            java.lang.Object r4 = r0.value
            if (r4 == 0) goto L_0x0064
            r5 = r7
            goto L_0x0065
        L_0x0064:
            r5 = 0
        L_0x0065:
            boolean r6 = r4 instanceof com.google.common.util.concurrent.AbstractFuture.SetFuture
            r6 = r6 ^ r7
            r5 = r5 & r6
            if (r5 == 0) goto L_0x0070
            java.lang.Object r0 = getDoneValue(r4)
            return r0
        L_0x0070:
            long r4 = java.lang.System.nanoTime()
            long r4 = r10 - r4
            int r6 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r6 >= 0) goto L_0x004c
            r0.removeWaiter(r8)
            r8 = r7
            r13 = r12
            r6 = r4
            r11 = r10
            r4 = r3
            r5 = r4
            r2 = r1
            r1 = r0
            goto L_0x00ca
        L_0x0086:
            r0.removeWaiter(r8)
            java.lang.InterruptedException r0 = new java.lang.InterruptedException
            r0.<init>()
            throw r0
        L_0x008f:
            com.google.common.util.concurrent.AbstractFuture$Waiter r6 = r0.waiters
            com.google.common.util.concurrent.AbstractFuture$Waiter r9 = com.google.common.util.concurrent.AbstractFuture.Waiter.TOMBSTONE
            if (r6 != r9) goto L_0x0041
        L_0x0095:
            java.lang.Object r0 = r0.value
            java.util.Objects.requireNonNull(r0)
            java.lang.Object r0 = getDoneValue(r0)
            return r0
        L_0x009f:
            r13 = r12
            r11 = r10
            r9 = r8
            r8 = r7
            r6 = r4
            r4 = r3
            r5 = r4
            r2 = r1
            r1 = r0
        L_0x00a8:
            int r9 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r9 <= 0) goto L_0x00d3
            java.lang.Object r6 = r1.value
            if (r6 == 0) goto L_0x00b2
            r7 = r8
            goto L_0x00b3
        L_0x00b2:
            r7 = 0
        L_0x00b3:
            boolean r9 = r6 instanceof com.google.common.util.concurrent.AbstractFuture.SetFuture
            r9 = r9 ^ r8
            r7 = r7 & r9
            if (r7 == 0) goto L_0x00be
            java.lang.Object r0 = getDoneValue(r6)
            return r0
        L_0x00be:
            boolean r6 = java.lang.Thread.interrupted()
            if (r6 != 0) goto L_0x00cd
            long r6 = java.lang.System.nanoTime()
            long r6 = r11 - r6
        L_0x00ca:
            r9 = 0
            goto L_0x00a8
        L_0x00cd:
            java.lang.InterruptedException r0 = new java.lang.InterruptedException
            r0.<init>()
            throw r0
        L_0x00d3:
            java.lang.String r1 = r0.toString()
            java.lang.String r9 = r4.toString()
            java.util.Locale r10 = java.util.Locale.ROOT
            java.lang.String r9 = r9.toLowerCase(r10)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "Waited "
            r11.append(r12)
            r11.append(r2)
            java.lang.String r2 = " "
            r11.append(r2)
            java.lang.String r3 = r4.toString()
            java.lang.String r3 = r3.toLowerCase(r10)
            r11.append(r3)
            java.lang.String r3 = r11.toString()
            long r10 = r6 + r13
            r15 = 0
            int r4 = (r10 > r15 ? 1 : (r10 == r15 ? 0 : -1))
            if (r4 >= 0) goto L_0x0167
            java.lang.String r4 = " (plus "
            java.lang.String r3 = androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0.m4m(r3, r4)
            long r6 = -r6
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r10 = r5.convert(r6, r4)
            long r4 = r5.toNanos(r10)
            long r6 = r6 - r4
            r4 = 0
            int r4 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x0128
            int r5 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
            if (r5 <= 0) goto L_0x0127
            goto L_0x0128
        L_0x0127:
            r8 = 0
        L_0x0128:
            if (r4 <= 0) goto L_0x014b
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r3)
            r4.append(r10)
            r4.append(r2)
            r4.append(r9)
            java.lang.String r3 = r4.toString()
            if (r8 == 0) goto L_0x0147
            java.lang.String r4 = ","
            java.lang.String r3 = androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0.m4m(r3, r4)
        L_0x0147:
            java.lang.String r3 = androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0.m4m(r3, r2)
        L_0x014b:
            if (r8 == 0) goto L_0x0161
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r3)
            r2.append(r6)
            java.lang.String r3 = " nanoseconds "
            r2.append(r3)
            java.lang.String r3 = r2.toString()
        L_0x0161:
            java.lang.String r2 = "delay)"
            java.lang.String r3 = androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0.m4m(r3, r2)
        L_0x0167:
            boolean r0 = r0.isDone()
            if (r0 == 0) goto L_0x0179
            java.util.concurrent.TimeoutException r0 = new java.util.concurrent.TimeoutException
            java.lang.String r1 = " but future completed as timeout expired"
            java.lang.String r1 = androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0.m4m(r3, r1)
            r0.<init>(r1)
            throw r0
        L_0x0179:
            java.util.concurrent.TimeoutException r0 = new java.util.concurrent.TimeoutException
            java.lang.String r2 = " for "
            java.lang.String r1 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m(r3, r2, r1)
            r0.<init>(r1)
            throw r0
        L_0x0185:
            java.lang.InterruptedException r0 = new java.lang.InterruptedException
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractFuture.get(long, java.util.concurrent.TimeUnit):java.lang.Object");
    }

    public final void removeWaiter(Waiter waiter) {
        waiter.thread = null;
        while (true) {
            Waiter waiter2 = this.waiters;
            if (waiter2 != Waiter.TOMBSTONE) {
                Waiter waiter3 = null;
                while (waiter2 != null) {
                    Waiter waiter4 = waiter2.next;
                    if (waiter2.thread != null) {
                        waiter3 = waiter2;
                    } else if (waiter3 != null) {
                        waiter3.next = waiter4;
                        if (waiter3.thread == null) {
                        }
                    } else if (!ATOMIC_HELPER.casWaiters(this, waiter2, waiter4)) {
                    }
                    waiter2 = waiter4;
                }
                return;
            }
            return;
        }
    }

    public static final class Failure {
        public static final Failure FALLBACK_INSTANCE = new Failure(new Throwable("Failure occurred while trying to finish a future.") {
            public final synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        public final Throwable exception;

        public Failure(Throwable th) {
            Objects.requireNonNull(th);
            this.exception = th;
        }
    }

    static {
        boolean z;
        AtomicHelper atomicHelper;
        Class<Waiter> cls = Waiter.class;
        try {
            z = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
        } catch (SecurityException unused) {
            z = false;
        }
        GENERATE_CANCELLATION_CAUSES = z;
        Throwable th = null;
        try {
            atomicHelper = new UnsafeAtomicHelper();
            th = null;
        } catch (Throwable th2) {
            th = th2;
            atomicHelper = new SynchronizedHelper();
        }
        ATOMIC_HELPER = atomicHelper;
        Class<LockSupport> cls2 = LockSupport.class;
        if (th != null) {
            Logger logger = log;
            Level level = Level.SEVERE;
            logger.log(level, "UnsafeAtomicHelper is broken!", th);
            logger.log(level, "SafeAtomicHelper is broken!", th);
        }
    }

    private void addDoneString(StringBuilder sb) {
        Object obj;
        boolean z = false;
        while (true) {
            try {
                obj = get();
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (ExecutionException e) {
                sb.append("FAILURE, cause=[");
                sb.append(e.getCause());
                sb.append("]");
                return;
            } catch (CancellationException unused2) {
                sb.append("CANCELLED");
                return;
            } catch (RuntimeException e2) {
                sb.append("UNKNOWN, cause=[");
                sb.append(e2.getClass());
                sb.append(" thrown from get()]");
                return;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        sb.append("SUCCESS, result=[");
        appendResultObject(sb, obj);
        sb.append("]");
    }

    private static Object getDoneValue(Object obj) throws ExecutionException {
        if (obj instanceof Cancellation) {
            Throwable th = ((Cancellation) obj).cause;
            CancellationException cancellationException = new CancellationException("Task was cancelled.");
            cancellationException.initCause(th);
            throw cancellationException;
        } else if (obj instanceof Failure) {
            throw new ExecutionException(((Failure) obj).exception);
        } else if (obj == NULL) {
            return null;
        } else {
            return obj;
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object getFutureValue(com.google.common.util.concurrent.ListenableFuture<?> r6) {
        /*
            java.lang.String r0 = "get() did not throw CancellationException, despite reporting isCancelled() == true: "
            boolean r1 = r6 instanceof com.google.common.util.concurrent.AbstractFuture.Trusted
            r2 = 0
            if (r1 == 0) goto L_0x0028
            com.google.common.util.concurrent.AbstractFuture r6 = (com.google.common.util.concurrent.AbstractFuture) r6
            java.lang.Object r6 = r6.value
            boolean r0 = r6 instanceof com.google.common.util.concurrent.AbstractFuture.Cancellation
            if (r0 == 0) goto L_0x0024
            r0 = r6
            com.google.common.util.concurrent.AbstractFuture$Cancellation r0 = (com.google.common.util.concurrent.AbstractFuture.Cancellation) r0
            boolean r1 = r0.wasInterrupted
            if (r1 == 0) goto L_0x0024
            java.lang.Throwable r6 = r0.cause
            if (r6 == 0) goto L_0x0022
            com.google.common.util.concurrent.AbstractFuture$Cancellation r6 = new com.google.common.util.concurrent.AbstractFuture$Cancellation
            java.lang.Throwable r0 = r0.cause
            r6.<init>(r2, r0)
            goto L_0x0024
        L_0x0022:
            com.google.common.util.concurrent.AbstractFuture$Cancellation r6 = com.google.common.util.concurrent.AbstractFuture.Cancellation.CAUSELESS_CANCELLED
        L_0x0024:
            java.util.Objects.requireNonNull(r6)
            return r6
        L_0x0028:
            boolean r1 = r6 instanceof com.google.common.util.concurrent.internal.InternalFutureFailureAccess
            if (r1 == 0) goto L_0x003b
            r1 = r6
            com.google.common.util.concurrent.internal.InternalFutureFailureAccess r1 = (com.google.common.util.concurrent.internal.InternalFutureFailureAccess) r1
            java.lang.Throwable r1 = r1.tryInternalFastPathGetFailure()
            if (r1 == 0) goto L_0x003b
            com.google.common.util.concurrent.AbstractFuture$Failure r6 = new com.google.common.util.concurrent.AbstractFuture$Failure
            r6.<init>(r1)
            return r6
        L_0x003b:
            boolean r1 = r6.isCancelled()
            boolean r3 = GENERATE_CANCELLATION_CAUSES
            r4 = 1
            r3 = r3 ^ r4
            r3 = r3 & r1
            if (r3 == 0) goto L_0x004c
            com.google.common.util.concurrent.AbstractFuture$Cancellation r6 = com.google.common.util.concurrent.AbstractFuture.Cancellation.CAUSELESS_CANCELLED
            java.util.Objects.requireNonNull(r6)
            return r6
        L_0x004c:
            r3 = r2
        L_0x004d:
            java.lang.Object r4 = r6.get()     // Catch:{ InterruptedException -> 0x00dc, all -> 0x0081 }
            if (r3 == 0) goto L_0x005a
            java.lang.Thread r3 = java.lang.Thread.currentThread()     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
            r3.interrupt()     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
        L_0x005a:
            if (r1 == 0) goto L_0x007c
            com.google.common.util.concurrent.AbstractFuture$Cancellation r3 = new com.google.common.util.concurrent.AbstractFuture$Cancellation     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
            r5.<init>()     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
            r5.append(r0)     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
            r5.append(r6)     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
            java.lang.String r5 = r5.toString()     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
            r4.<init>(r5)     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
            r3.<init>(r2, r4)     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
            return r3
        L_0x0076:
            r6 = move-exception
            goto L_0x008c
        L_0x0078:
            r0 = move-exception
            goto L_0x0092
        L_0x007a:
            r3 = move-exception
            goto L_0x00b6
        L_0x007c:
            if (r4 != 0) goto L_0x0080
            java.lang.Object r4 = NULL     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
        L_0x0080:
            return r4
        L_0x0081:
            r4 = move-exception
            if (r3 == 0) goto L_0x008b
            java.lang.Thread r3 = java.lang.Thread.currentThread()     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
            r3.interrupt()     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
        L_0x008b:
            throw r4     // Catch:{ ExecutionException -> 0x007a, CancellationException -> 0x0078, all -> 0x0076 }
        L_0x008c:
            com.google.common.util.concurrent.AbstractFuture$Failure r0 = new com.google.common.util.concurrent.AbstractFuture$Failure
            r0.<init>(r6)
            return r0
        L_0x0092:
            if (r1 != 0) goto L_0x00b0
            com.google.common.util.concurrent.AbstractFuture$Failure r1 = new com.google.common.util.concurrent.AbstractFuture$Failure
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "get() threw CancellationException, despite reporting isCancelled() == false: "
            r3.append(r4)
            r3.append(r6)
            java.lang.String r6 = r3.toString()
            r2.<init>(r6, r0)
            r1.<init>(r2)
            return r1
        L_0x00b0:
            com.google.common.util.concurrent.AbstractFuture$Cancellation r6 = new com.google.common.util.concurrent.AbstractFuture$Cancellation
            r6.<init>(r2, r0)
            return r6
        L_0x00b6:
            if (r1 == 0) goto L_0x00d2
            com.google.common.util.concurrent.AbstractFuture$Cancellation r1 = new com.google.common.util.concurrent.AbstractFuture$Cancellation
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r0)
            r5.append(r6)
            java.lang.String r6 = r5.toString()
            r4.<init>(r6, r3)
            r1.<init>(r2, r4)
            return r1
        L_0x00d2:
            com.google.common.util.concurrent.AbstractFuture$Failure r6 = new com.google.common.util.concurrent.AbstractFuture$Failure
            java.lang.Throwable r0 = r3.getCause()
            r6.<init>(r0)
            return r6
        L_0x00dc:
            r3 = r4
            goto L_0x004d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractFuture.getFutureValue(com.google.common.util.concurrent.ListenableFuture):java.lang.Object");
    }

    public void addListener(Runnable runnable, Executor executor) {
        Listener listener;
        Objects.requireNonNull(executor, "Executor was null.");
        if (isDone() || (listener = this.listeners) == Listener.TOMBSTONE) {
            executeListener(runnable, executor);
        }
        Listener listener2 = new Listener(runnable, executor);
        do {
            listener2.next = listener;
            if (!ATOMIC_HELPER.casListeners(this, listener, listener2)) {
                listener = this.listeners;
            } else {
                return;
            }
        } while (listener != Listener.TOMBSTONE);
        executeListener(runnable, executor);
    }

    public final void appendResultObject(StringBuilder sb, Object obj) {
        if (obj == null) {
            sb.append("null");
        } else if (obj == this) {
            sb.append("this future");
        } else {
            sb.append(obj.getClass().getName());
            sb.append("@");
            sb.append(Integer.toHexString(System.identityHashCode(obj)));
        }
    }

    @CanIgnoreReturnValue
    public boolean cancel(boolean z) {
        boolean z2;
        Cancellation cancellation;
        boolean z3;
        Object obj = this.value;
        if (obj == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2 && !(obj instanceof SetFuture)) {
            return false;
        }
        if (GENERATE_CANCELLATION_CAUSES) {
            cancellation = new Cancellation(z, new CancellationException("Future.cancel() was called."));
        } else {
            if (z) {
                cancellation = Cancellation.CAUSELESS_INTERRUPTED;
            } else {
                cancellation = Cancellation.CAUSELESS_CANCELLED;
            }
            Objects.requireNonNull(cancellation);
        }
        boolean z4 = false;
        while (true) {
            if (ATOMIC_HELPER.casValue(this, obj, cancellation)) {
                complete(this);
                if (!(obj instanceof SetFuture)) {
                    return true;
                }
                ListenableFuture<? extends V> listenableFuture = ((SetFuture) obj).future;
                if (listenableFuture instanceof Trusted) {
                    this = (AbstractFuture) listenableFuture;
                    obj = this.value;
                    if (obj == null) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (!z3 && !(obj instanceof SetFuture)) {
                        return true;
                    }
                    z4 = true;
                } else {
                    listenableFuture.cancel(z);
                    return true;
                }
            } else {
                obj = this.value;
                if (!(obj instanceof SetFuture)) {
                    return z4;
                }
            }
        }
    }

    public boolean isCancelled() {
        return this.value instanceof Cancellation;
    }

    public boolean isDone() {
        boolean z;
        Object obj = this.value;
        if (obj != null) {
            z = true;
        } else {
            z = false;
        }
        return (!(obj instanceof SetFuture)) & z;
    }

    public String pendingToString() {
        if (!(this instanceof ScheduledFuture)) {
            return null;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("remaining delay=[");
        m.append(((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS));
        m.append(" ms]");
        return m.toString();
    }

    @CanIgnoreReturnValue
    public boolean setException(Throwable th) {
        Objects.requireNonNull(th);
        if (!ATOMIC_HELPER.casValue(this, (Object) null, new Failure(th))) {
            return false;
        }
        complete(this);
        return true;
    }

    public final String toString() {
        String str;
        boolean z;
        StringBuilder sb = new StringBuilder();
        if (getClass().getName().startsWith("com.google.common.util.concurrent.")) {
            sb.append(getClass().getSimpleName());
        } else {
            sb.append(getClass().getName());
        }
        sb.append('@');
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("[status=");
        if (isCancelled()) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            addDoneString(sb);
        } else {
            int length = sb.length();
            sb.append("PENDING");
            Object obj = this.value;
            if (obj instanceof SetFuture) {
                sb.append(", setFuture=[");
                ListenableFuture<? extends V> listenableFuture = ((SetFuture) obj).future;
                if (listenableFuture == this) {
                    try {
                        sb.append("this future");
                    } catch (RuntimeException | StackOverflowError e) {
                        sb.append("Exception thrown from implementation: ");
                        sb.append(e.getClass());
                    }
                } else {
                    sb.append(listenableFuture);
                }
                sb.append("]");
            } else {
                try {
                    str = pendingToString();
                    int i = Platform.$r8$clinit;
                    if (str == null || str.isEmpty()) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        str = null;
                    }
                } catch (RuntimeException | StackOverflowError e2) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Exception thrown from implementation: ");
                    m.append(e2.getClass());
                    str = m.toString();
                }
                if (str != null) {
                    sb.append(", info=[");
                    sb.append(str);
                    sb.append("]");
                }
            }
            if (isDone()) {
                sb.delete(length, sb.length());
                addDoneString(sb);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public final Throwable tryInternalFastPathGetFailure() {
        if (!(this instanceof Trusted)) {
            return null;
        }
        Object obj = this.value;
        if (obj instanceof Failure) {
            return ((Failure) obj).exception;
        }
        return null;
    }

    public static final class Listener {
        public static final Listener TOMBSTONE = new Listener();
        public final Executor executor;
        public Listener next;
        public final Runnable task;

        public Listener(Runnable runnable, Executor executor2) {
            this.task = runnable;
            this.executor = executor2;
        }

        public Listener() {
            this.task = null;
            this.executor = null;
        }
    }

    public static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            Logger logger = log;
            Level level = Level.SEVERE;
            logger.log(level, "RuntimeException while executing runnable " + runnable + " with executor " + executor, e);
        }
    }

    @CanIgnoreReturnValue
    public V get() throws InterruptedException, ExecutionException {
        Object obj;
        if (!Thread.interrupted()) {
            Object obj2 = this.value;
            if ((obj2 != null) && (!(obj2 instanceof SetFuture))) {
                return getDoneValue(obj2);
            }
            Waiter waiter = this.waiters;
            if (waiter != Waiter.TOMBSTONE) {
                Waiter waiter2 = new Waiter();
                do {
                    AtomicHelper atomicHelper = ATOMIC_HELPER;
                    atomicHelper.putNext(waiter2, waiter);
                    if (atomicHelper.casWaiters(this, waiter, waiter2)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                obj = this.value;
                            } else {
                                removeWaiter(waiter2);
                                throw new InterruptedException();
                            }
                        } while (!((obj != null) & (!(obj instanceof SetFuture))));
                        return getDoneValue(obj);
                    }
                    waiter = this.waiters;
                } while (waiter != Waiter.TOMBSTONE);
            }
            Object obj3 = this.value;
            Objects.requireNonNull(obj3);
            return getDoneValue(obj3);
        }
        throw new InterruptedException();
    }

    public static final class SynchronizedHelper extends AtomicHelper {
        public final boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            synchronized (abstractFuture) {
                if (abstractFuture.listeners != listener) {
                    return false;
                }
                abstractFuture.listeners = listener2;
                return true;
            }
        }

        public final boolean casValue(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            synchronized (abstractFuture) {
                if (abstractFuture.value != obj) {
                    return false;
                }
                abstractFuture.value = obj2;
                return true;
            }
        }

        public final boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            synchronized (abstractFuture) {
                if (abstractFuture.waiters != waiter) {
                    return false;
                }
                abstractFuture.waiters = waiter2;
                return true;
            }
        }

        public final void putNext(Waiter waiter, Waiter waiter2) {
            waiter.next = waiter2;
        }

        public final void putThread(Waiter waiter, Thread thread) {
            waiter.thread = thread;
        }
    }
}
