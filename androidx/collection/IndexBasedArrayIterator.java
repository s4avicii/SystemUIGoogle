package androidx.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class IndexBasedArrayIterator<T> implements Iterator<T> {
    public boolean mCanRemove;
    public int mIndex;
    public int mSize;

    public abstract T elementAt(int i);

    public abstract void removeAt(int i);

    public final boolean hasNext() {
        if (this.mIndex < this.mSize) {
            return true;
        }
        return false;
    }

    public final void remove() {
        if (this.mCanRemove) {
            int i = this.mIndex - 1;
            this.mIndex = i;
            removeAt(i);
            this.mSize--;
            this.mCanRemove = false;
            return;
        }
        throw new IllegalStateException();
    }

    public IndexBasedArrayIterator(int i) {
        this.mSize = i;
    }

    public final T next() {
        if (hasNext()) {
            T elementAt = elementAt(this.mIndex);
            this.mIndex++;
            this.mCanRemove = true;
            return elementAt;
        }
        throw new NoSuchElementException();
    }
}
