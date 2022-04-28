package kotlinx.coroutines.scheduling;

import java.util.Objects;
import kotlinx.atomicfu.AtomicFU;
import kotlinx.atomicfu.AtomicLong;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* compiled from: Tasks.kt */
public final class GlobalQueue {
    public final AtomicRef<LockFreeTaskQueueCore<Task>> _cur = AtomicFU.atomic(new LockFreeTaskQueueCore(8, false));

    public final boolean addLast(Task task) {
        AtomicRef<LockFreeTaskQueueCore<Task>> atomicRef = this._cur;
        while (true) {
            Objects.requireNonNull(atomicRef);
            LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicRef.value;
            int addLast = lockFreeTaskQueueCore.addLast(task);
            if (addLast == 0) {
                return true;
            }
            if (addLast == 1) {
                this._cur.compareAndSet(lockFreeTaskQueueCore, lockFreeTaskQueueCore.next());
            } else if (addLast == 2) {
                return false;
            }
        }
    }

    public final int getSize() {
        AtomicRef<LockFreeTaskQueueCore<Task>> atomicRef = this._cur;
        Objects.requireNonNull(atomicRef);
        LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) atomicRef.value;
        Objects.requireNonNull(lockFreeTaskQueueCore);
        AtomicLong atomicLong = lockFreeTaskQueueCore._state;
        Objects.requireNonNull(atomicLong);
        long j = atomicLong.value;
        return 1073741823 & (((int) ((j & 1152921503533105152L) >> 30)) - ((int) ((1073741823 & j) >> 0)));
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [kotlinx.coroutines.scheduling.Task, java.lang.Object] */
    public final Task removeFirstOrNull() {
        AtomicRef<LockFreeTaskQueueCore<Task>> atomicRef = this._cur;
        while (true) {
            Objects.requireNonNull(atomicRef);
            LockFreeTaskQueueCore lockFreeTaskQueueCore = atomicRef.value;
            ? removeFirstOrNull = lockFreeTaskQueueCore.removeFirstOrNull();
            if (removeFirstOrNull != LockFreeTaskQueueCore.REMOVE_FROZEN) {
                return removeFirstOrNull;
            }
            this._cur.compareAndSet(lockFreeTaskQueueCore, lockFreeTaskQueueCore.next());
        }
    }
}
