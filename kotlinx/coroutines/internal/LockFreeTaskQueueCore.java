package kotlinx.coroutines.internal;

import java.util.Objects;
import kotlinx.atomicfu.AtomicArray;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.atomicfu.InterceptorKt;
import kotlinx.atomicfu.TraceBase;
import kotlinx.coroutines.DebugKt;

/* compiled from: LockFreeTaskQueue.kt */
public final class LockFreeTaskQueueCore<E> {
    public static final Symbol REMOVE_FROZEN = new Symbol("REMOVE_FROZEN");
    public final AtomicRef<LockFreeTaskQueueCore<E>> _next = new AtomicRef<>((Object) null);
    public final AtomicLong _state = new AtomicLong(0);
    public final AtomicArray<Object> array;
    public final int capacity;
    public final int mask;
    public final boolean singleConsumer;

    /* compiled from: LockFreeTaskQueue.kt */
    public static final class Placeholder {
        public final int index;

        public Placeholder(int i) {
            this.index = i;
        }
    }

    public final int addLast(E e) {
        LockFreeTaskQueueCore lockFreeTaskQueueCore = this;
        E e2 = e;
        AtomicLong atomicLong = lockFreeTaskQueueCore._state;
        while (true) {
            Objects.requireNonNull(atomicLong);
            long j = atomicLong.value;
            if ((3458764513820540928L & j) == 0) {
                int i = (int) ((1073741823 & j) >> 0);
                int i2 = (int) ((1152921503533105152L & j) >> 30);
                int i3 = lockFreeTaskQueueCore.mask;
                if (((i2 + 2) & i3) == (i & i3)) {
                    return 1;
                }
                if (!lockFreeTaskQueueCore.singleConsumer) {
                    AtomicArray<Object> atomicArray = lockFreeTaskQueueCore.array;
                    Objects.requireNonNull(atomicArray);
                    AtomicRef<T> atomicRef = atomicArray.array[i2 & i3];
                    Objects.requireNonNull(atomicRef);
                    if (atomicRef.value != null) {
                        int i4 = lockFreeTaskQueueCore.capacity;
                        if (i4 < 1024 || ((i2 - i) & 1073741823) > (i4 >> 1)) {
                            return 1;
                        }
                    }
                }
                if (lockFreeTaskQueueCore._state.compareAndSet(j, (((long) ((i2 + 1) & 1073741823)) << 30) | (-1152921503533105153L & j))) {
                    AtomicArray<Object> atomicArray2 = lockFreeTaskQueueCore.array;
                    Objects.requireNonNull(atomicArray2);
                    atomicArray2.array[i2 & i3].setValue(e2);
                    do {
                        AtomicLong atomicLong2 = lockFreeTaskQueueCore._state;
                        Objects.requireNonNull(atomicLong2);
                        if ((atomicLong2.value & 1152921504606846976L) == 0) {
                            return 0;
                        }
                        lockFreeTaskQueueCore = lockFreeTaskQueueCore.next();
                        AtomicArray<Object> atomicArray3 = lockFreeTaskQueueCore.array;
                        Objects.requireNonNull(atomicArray3);
                        AtomicRef<T> atomicRef2 = atomicArray3.array[lockFreeTaskQueueCore.mask & i2];
                        Objects.requireNonNull(atomicRef2);
                        T t = atomicRef2.value;
                        if (!(t instanceof Placeholder) || ((Placeholder) t).index != i2) {
                            lockFreeTaskQueueCore = null;
                            continue;
                        } else {
                            AtomicArray<Object> atomicArray4 = lockFreeTaskQueueCore.array;
                            Objects.requireNonNull(atomicArray4);
                            atomicArray4.array[lockFreeTaskQueueCore.mask & i2].setValue(e2);
                            continue;
                        }
                    } while (lockFreeTaskQueueCore != null);
                    return 0;
                }
            } else if ((2305843009213693952L & j) != 0) {
                return 2;
            } else {
                return 1;
            }
        }
        return 1;
    }

    public final boolean close() {
        long j;
        AtomicLong atomicLong = this._state;
        do {
            Objects.requireNonNull(atomicLong);
            j = atomicLong.value;
            if ((j & 2305843009213693952L) != 0) {
                return true;
            }
            if ((1152921504606846976L & j) != 0) {
                return false;
            }
        } while (!atomicLong.compareAndSet(j, 2305843009213693952L | j));
        return true;
    }

    public final LockFreeTaskQueueCore<E> next() {
        long j;
        AtomicLong atomicLong = this._state;
        while (true) {
            Objects.requireNonNull(atomicLong);
            j = atomicLong.value;
            if ((j & 1152921504606846976L) == 0) {
                long j2 = 1152921504606846976L | j;
                if (atomicLong.compareAndSet(j, j2)) {
                    j = j2;
                    break;
                }
            } else {
                break;
            }
        }
        AtomicRef<LockFreeTaskQueueCore<E>> atomicRef = this._next;
        while (true) {
            Objects.requireNonNull(atomicRef);
            LockFreeTaskQueueCore<E> lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicRef.value;
            if (lockFreeTaskQueueCore != null) {
                return lockFreeTaskQueueCore;
            }
            AtomicRef<LockFreeTaskQueueCore<E>> atomicRef2 = this._next;
            LockFreeTaskQueueCore lockFreeTaskQueueCore2 = new LockFreeTaskQueueCore(this.capacity * 2, this.singleConsumer);
            int i = (int) ((1073741823 & j) >> 0);
            int i2 = (int) ((1152921503533105152L & j) >> 30);
            while (true) {
                int i3 = this.mask;
                int i4 = i & i3;
                if (i4 == (i3 & i2)) {
                    break;
                }
                AtomicArray<Object> atomicArray = this.array;
                Objects.requireNonNull(atomicArray);
                AtomicRef<T> atomicRef3 = atomicArray.array[i4];
                Objects.requireNonNull(atomicRef3);
                T t = atomicRef3.value;
                if (t == null) {
                    t = new Placeholder(i);
                }
                AtomicArray<Object> atomicArray2 = lockFreeTaskQueueCore2.array;
                Objects.requireNonNull(atomicArray2);
                atomicArray2.array[lockFreeTaskQueueCore2.mask & i].setValue(t);
                i++;
            }
            AtomicLong atomicLong2 = lockFreeTaskQueueCore2._state;
            Objects.requireNonNull(atomicLong2);
            int i5 = InterceptorKt.$r8$clinit;
            atomicLong2.value = -1152921504606846977L & j;
            TraceBase traceBase = atomicLong2.trace;
            if (traceBase != TraceBase.None.INSTANCE) {
                Objects.requireNonNull(traceBase);
            }
            atomicRef2.compareAndSet(null, lockFreeTaskQueueCore2);
        }
    }

    public final Object removeFirstOrNull() {
        LockFreeTaskQueueCore lockFreeTaskQueueCore = this;
        AtomicLong atomicLong = lockFreeTaskQueueCore._state;
        while (true) {
            Objects.requireNonNull(atomicLong);
            long j = atomicLong.value;
            if ((j & 1152921504606846976L) != 0) {
                return REMOVE_FROZEN;
            }
            long j2 = 1073741823;
            int i = (int) ((j & 1073741823) >> 0);
            int i2 = lockFreeTaskQueueCore.mask;
            int i3 = ((int) ((1152921503533105152L & j) >> 30)) & i2;
            int i4 = i2 & i;
            if (i3 == i4) {
                return null;
            }
            AtomicArray<Object> atomicArray = lockFreeTaskQueueCore.array;
            Objects.requireNonNull(atomicArray);
            AtomicRef<T> atomicRef = atomicArray.array[i4];
            Objects.requireNonNull(atomicRef);
            T t = atomicRef.value;
            if (t == null) {
                if (lockFreeTaskQueueCore.singleConsumer) {
                    return null;
                }
            } else if (t instanceof Placeholder) {
                return null;
            } else {
                long j3 = ((long) ((i + 1) & 1073741823)) << 0;
                if (lockFreeTaskQueueCore._state.compareAndSet(j, (j & -1073741824) | j3)) {
                    AtomicArray<Object> atomicArray2 = lockFreeTaskQueueCore.array;
                    Objects.requireNonNull(atomicArray2);
                    atomicArray2.array[lockFreeTaskQueueCore.mask & i].setValue(null);
                    return t;
                } else if (lockFreeTaskQueueCore.singleConsumer) {
                    while (true) {
                        AtomicLong atomicLong2 = lockFreeTaskQueueCore._state;
                        while (true) {
                            Objects.requireNonNull(atomicLong2);
                            long j4 = atomicLong2.value;
                            int i5 = (int) ((j4 & j2) >> 0);
                            boolean z = DebugKt.DEBUG;
                            if ((j4 & 1152921504606846976L) != 0) {
                                lockFreeTaskQueueCore = lockFreeTaskQueueCore.next();
                                break;
                            } else if (lockFreeTaskQueueCore._state.compareAndSet(j4, (j4 & -1073741824) | j3)) {
                                AtomicArray<Object> atomicArray3 = lockFreeTaskQueueCore.array;
                                Objects.requireNonNull(atomicArray3);
                                atomicArray3.array[lockFreeTaskQueueCore.mask & i5].setValue(null);
                                lockFreeTaskQueueCore = null;
                                break;
                            } else {
                                j2 = 1073741823;
                            }
                        }
                        if (lockFreeTaskQueueCore == null) {
                            return t;
                        }
                        j2 = 1073741823;
                    }
                }
            }
        }
    }

    public LockFreeTaskQueueCore(int i, boolean z) {
        boolean z2;
        this.capacity = i;
        this.singleConsumer = z;
        int i2 = i - 1;
        this.mask = i2;
        this.array = new AtomicArray<>(i);
        boolean z3 = false;
        if (i2 <= 1073741823) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            if (!((i & i2) == 0 ? true : z3)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            return;
        }
        throw new IllegalStateException("Check failed.".toString());
    }
}
