package com.google.common.collect;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.NoSuchElementException;

public abstract class AbstractIterator<T> extends UnmodifiableIterator<T> {
    public T next;
    public State state = State.NOT_READY;

    public enum State {
        READY,
        NOT_READY,
        DONE,
        FAILED
    }

    public abstract T computeNext();

    @CanIgnoreReturnValue
    public final boolean hasNext() {
        boolean z;
        State state2 = this.state;
        State state3 = State.FAILED;
        if (state2 != state3) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            int ordinal = state2.ordinal();
            if (ordinal == 0) {
                return true;
            }
            if (ordinal == 2) {
                return false;
            }
            this.state = state3;
            this.next = computeNext();
            if (this.state == State.DONE) {
                return false;
            }
            this.state = State.READY;
            return true;
        }
        throw new IllegalStateException();
    }

    @CanIgnoreReturnValue
    public final T next() {
        if (hasNext()) {
            this.state = State.NOT_READY;
            T t = this.next;
            this.next = null;
            return t;
        }
        throw new NoSuchElementException();
    }
}
