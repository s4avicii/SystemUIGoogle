package kotlinx.coroutines.flow.internal;

import java.util.Arrays;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;

/* compiled from: AbstractSharedFlow.kt */
public abstract class AbstractSharedFlow<S extends AbstractSharedFlowSlot<?>> {
    public int nCollectors;
    public int nextIndex;
    public S[] slots;

    public final S allocateSlot() {
        S s;
        synchronized (this) {
            S[] sArr = this.slots;
            if (sArr == null) {
                sArr = createSlotArray();
                this.slots = sArr;
            } else if (this.nCollectors >= sArr.length) {
                S[] copyOf = Arrays.copyOf(sArr, sArr.length * 2);
                this.slots = (AbstractSharedFlowSlot[]) copyOf;
                sArr = (AbstractSharedFlowSlot[]) copyOf;
            }
            int i = this.nextIndex;
            do {
                s = sArr[i];
                if (s == null) {
                    s = createSlot();
                    sArr[i] = s;
                }
                i++;
                if (i >= sArr.length) {
                    i = 0;
                }
            } while (!s.allocateLocked(this));
            this.nextIndex = i;
            this.nCollectors++;
        }
        return s;
    }

    public abstract S createSlot();

    public abstract AbstractSharedFlowSlot[] createSlotArray();

    public final void freeSlot(S s) {
        int i;
        Continuation[] freeLocked;
        synchronized (this) {
            int i2 = this.nCollectors - 1;
            this.nCollectors = i2;
            i = 0;
            if (i2 == 0) {
                this.nextIndex = 0;
            }
            freeLocked = s.freeLocked(this);
        }
        int length = freeLocked.length;
        while (i < length) {
            Continuation continuation = freeLocked[i];
            i++;
            if (continuation != null) {
                continuation.resumeWith(Unit.INSTANCE);
            }
        }
    }
}
