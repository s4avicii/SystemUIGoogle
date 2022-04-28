package kotlinx.coroutines;

import java.util.Objects;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlinx.coroutines.internal.ArrayQueue;

/* compiled from: EventLoop.common.kt */
public abstract class EventLoop extends CoroutineDispatcher {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean shared;
    public ArrayQueue<DispatchedTask<?>> unconfinedQueue;
    public long useCount;

    public void shutdown() {
    }

    public final void decrementUseCount(boolean z) {
        long j;
        long j2 = this.useCount;
        if (z) {
            j = 4294967296L;
        } else {
            j = 1;
        }
        long j3 = j2 - j;
        this.useCount = j3;
        if (j3 <= 0) {
            boolean z2 = DebugKt.DEBUG;
            if (this.shared) {
                shutdown();
            }
        }
    }

    public final void dispatchUnconfined(DispatchedTask<?> dispatchedTask) {
        ArrayQueue<DispatchedTask<?>> arrayQueue = this.unconfinedQueue;
        if (arrayQueue == null) {
            arrayQueue = new ArrayQueue<>();
            this.unconfinedQueue = arrayQueue;
        }
        Object[] objArr = arrayQueue.elements;
        int i = arrayQueue.tail;
        objArr[i] = dispatchedTask;
        int length = (i + 1) & (objArr.length - 1);
        arrayQueue.tail = length;
        int i2 = arrayQueue.head;
        if (length == i2) {
            int length2 = objArr.length;
            Object[] objArr2 = new Object[(length2 << 1)];
            ArraysKt___ArraysKt.copyInto$default(objArr, objArr2, 0, i2, 0, 10);
            Object[] objArr3 = arrayQueue.elements;
            int length3 = objArr3.length;
            int i3 = arrayQueue.head;
            ArraysKt___ArraysKt.copyInto$default(objArr3, objArr2, length3 - i3, 0, i3, 4);
            arrayQueue.elements = objArr2;
            arrayQueue.head = 0;
            arrayQueue.tail = length2;
        }
    }

    public final void incrementUseCount(boolean z) {
        long j;
        long j2 = this.useCount;
        if (z) {
            j = 4294967296L;
        } else {
            j = 1;
        }
        this.useCount = j + j2;
        if (!z) {
            this.shared = true;
        }
    }

    public final boolean isUnconfinedLoopActive() {
        if (this.useCount >= 4294967296L) {
            return true;
        }
        return false;
    }

    public final boolean processUnconfinedEvent() {
        ArrayQueue<DispatchedTask<?>> arrayQueue = this.unconfinedQueue;
        if (arrayQueue == null) {
            return false;
        }
        int i = arrayQueue.head;
        Object obj = null;
        if (i != arrayQueue.tail) {
            Object[] objArr = arrayQueue.elements;
            Object obj2 = objArr[i];
            objArr[i] = null;
            arrayQueue.head = (i + 1) & (objArr.length - 1);
            Objects.requireNonNull(obj2, "null cannot be cast to non-null type T of kotlinx.coroutines.internal.ArrayQueue");
            obj = obj2;
        }
        DispatchedTask dispatchedTask = (DispatchedTask) obj;
        if (dispatchedTask == null) {
            return false;
        }
        dispatchedTask.run();
        return true;
    }

    public long processNextEvent() {
        if (!processUnconfinedEvent()) {
            return Long.MAX_VALUE;
        }
        return 0;
    }
}
