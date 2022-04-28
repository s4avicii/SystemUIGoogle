package androidx.collection;

import java.lang.reflect.Array;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public final class ArrayMap<K, V> extends SimpleArrayMap<K, V> implements Map<K, V> {
    public ArrayMap<K, V>.EntrySet mEntrySet;
    public ArrayMap<K, V>.KeySet mKeySet;
    public ArrayMap<K, V>.ValueCollection mValues;

    public final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        public EntrySet() {
        }

        public final Iterator<Map.Entry<K, V>> iterator() {
            return new MapIterator();
        }

        public final int size() {
            return ArrayMap.this.mSize;
        }
    }

    public final class KeyIterator extends IndexBasedArrayIterator<K> {
        public KeyIterator() {
            super(ArrayMap.this.mSize);
        }

        public final K elementAt(int i) {
            return ArrayMap.this.keyAt(i);
        }

        public final void removeAt(int i) {
            ArrayMap.this.removeAt(i);
        }
    }

    public final class KeySet implements Set<K> {
        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Set) {
                Set set = (Set) obj;
                try {
                    if (size() == set.size() && containsAll(set)) {
                        return true;
                    }
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return false;
        }

        public final Object[] toArray() {
            int i = ArrayMap.this.mSize;
            Object[] objArr = new Object[i];
            for (int i2 = 0; i2 < i; i2++) {
                objArr[i2] = ArrayMap.this.keyAt(i2);
            }
            return objArr;
        }

        public KeySet() {
        }

        public final boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        public final boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        public final void clear() {
            ArrayMap.this.clear();
        }

        public final boolean contains(Object obj) {
            return ArrayMap.this.containsKey(obj);
        }

        public final boolean containsAll(Collection<?> collection) {
            ArrayMap arrayMap = ArrayMap.this;
            Objects.requireNonNull(arrayMap);
            for (Object containsKey : collection) {
                if (!arrayMap.containsKey(containsKey)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i;
            int i2 = 0;
            for (int i3 = ArrayMap.this.mSize - 1; i3 >= 0; i3--) {
                Object keyAt = ArrayMap.this.keyAt(i3);
                if (keyAt == null) {
                    i = 0;
                } else {
                    i = keyAt.hashCode();
                }
                i2 += i;
            }
            return i2;
        }

        public final boolean isEmpty() {
            return ArrayMap.this.isEmpty();
        }

        public final Iterator<K> iterator() {
            return new KeyIterator();
        }

        public final boolean remove(Object obj) {
            int indexOfKey = ArrayMap.this.indexOfKey(obj);
            if (indexOfKey < 0) {
                return false;
            }
            ArrayMap.this.removeAt(indexOfKey);
            return true;
        }

        public final boolean removeAll(Collection<?> collection) {
            ArrayMap arrayMap = ArrayMap.this;
            Objects.requireNonNull(arrayMap);
            int i = arrayMap.mSize;
            for (Object remove : collection) {
                arrayMap.remove(remove);
            }
            if (i != arrayMap.mSize) {
                return true;
            }
            return false;
        }

        public final boolean retainAll(Collection<?> collection) {
            return ArrayMap.this.retainAll(collection);
        }

        public final int size() {
            return ArrayMap.this.mSize;
        }

        public final <T> T[] toArray(T[] tArr) {
            return ArrayMap.this.toArrayHelper(tArr, 0);
        }
    }

    public final class MapIterator implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
        public int mEnd;
        public boolean mEntryValid;
        public int mIndex = -1;

        public MapIterator() {
            this.mEnd = ArrayMap.this.mSize - 1;
        }

        public final boolean equals(Object obj) {
            boolean z;
            boolean z2;
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            } else if (!(obj instanceof Map.Entry)) {
                return false;
            } else {
                Map.Entry entry = (Map.Entry) obj;
                Object key = entry.getKey();
                Object keyAt = ArrayMap.this.keyAt(this.mIndex);
                if (key == keyAt || (key != null && key.equals(keyAt))) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    return false;
                }
                Object value = entry.getValue();
                Object valueAt = ArrayMap.this.valueAt(this.mIndex);
                if (value == valueAt || (value != null && value.equals(valueAt))) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    return true;
                }
                return false;
            }
        }

        public final K getKey() {
            if (this.mEntryValid) {
                return ArrayMap.this.keyAt(this.mIndex);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final V getValue() {
            if (this.mEntryValid) {
                return ArrayMap.this.valueAt(this.mIndex);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final boolean hasNext() {
            if (this.mIndex < this.mEnd) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            int i;
            if (this.mEntryValid) {
                Object keyAt = ArrayMap.this.keyAt(this.mIndex);
                Object valueAt = ArrayMap.this.valueAt(this.mIndex);
                int i2 = 0;
                if (keyAt == null) {
                    i = 0;
                } else {
                    i = keyAt.hashCode();
                }
                if (valueAt != null) {
                    i2 = valueAt.hashCode();
                }
                return i ^ i2;
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final void remove() {
            if (this.mEntryValid) {
                ArrayMap.this.removeAt(this.mIndex);
                this.mIndex--;
                this.mEnd--;
                this.mEntryValid = false;
                return;
            }
            throw new IllegalStateException();
        }

        public final V setValue(V v) {
            if (this.mEntryValid) {
                ArrayMap arrayMap = ArrayMap.this;
                int i = this.mIndex;
                Objects.requireNonNull(arrayMap);
                int i2 = (i << 1) + 1;
                V[] vArr = arrayMap.mArray;
                V v2 = vArr[i2];
                vArr[i2] = v;
                return v2;
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final String toString() {
            return getKey() + "=" + getValue();
        }

        public final Object next() {
            if (hasNext()) {
                this.mIndex++;
                this.mEntryValid = true;
                return this;
            }
            throw new NoSuchElementException();
        }
    }

    public final class ValueCollection implements Collection<V> {
        public final Object[] toArray() {
            int i = ArrayMap.this.mSize;
            Object[] objArr = new Object[i];
            for (int i2 = 0; i2 < i; i2++) {
                objArr[i2] = ArrayMap.this.valueAt(i2);
            }
            return objArr;
        }

        public ValueCollection() {
        }

        public final boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        public final boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        public final void clear() {
            ArrayMap.this.clear();
        }

        public final boolean contains(Object obj) {
            if (ArrayMap.this.indexOfValue(obj) >= 0) {
                return true;
            }
            return false;
        }

        public final boolean isEmpty() {
            return ArrayMap.this.isEmpty();
        }

        public final Iterator<V> iterator() {
            return new ValueIterator();
        }

        public final boolean remove(Object obj) {
            int indexOfValue = ArrayMap.this.indexOfValue(obj);
            if (indexOfValue < 0) {
                return false;
            }
            ArrayMap.this.removeAt(indexOfValue);
            return true;
        }

        public final boolean removeAll(Collection<?> collection) {
            int i = ArrayMap.this.mSize;
            int i2 = 0;
            boolean z = false;
            while (i2 < i) {
                if (collection.contains(ArrayMap.this.valueAt(i2))) {
                    ArrayMap.this.removeAt(i2);
                    i2--;
                    i--;
                    z = true;
                }
                i2++;
            }
            return z;
        }

        public final boolean retainAll(Collection<?> collection) {
            int i = ArrayMap.this.mSize;
            int i2 = 0;
            boolean z = false;
            while (i2 < i) {
                if (!collection.contains(ArrayMap.this.valueAt(i2))) {
                    ArrayMap.this.removeAt(i2);
                    i2--;
                    i--;
                    z = true;
                }
                i2++;
            }
            return z;
        }

        public final int size() {
            return ArrayMap.this.mSize;
        }

        public final boolean containsAll(Collection<?> collection) {
            for (Object contains : collection) {
                if (!contains(contains)) {
                    return false;
                }
            }
            return true;
        }

        public final <T> T[] toArray(T[] tArr) {
            return ArrayMap.this.toArrayHelper(tArr, 1);
        }
    }

    public final class ValueIterator extends IndexBasedArrayIterator<V> {
        public ValueIterator() {
            super(ArrayMap.this.mSize);
        }

        public final V elementAt(int i) {
            return ArrayMap.this.valueAt(i);
        }

        public final void removeAt(int i) {
            ArrayMap.this.removeAt(i);
        }
    }

    public ArrayMap() {
    }

    public ArrayMap(ArrayMap arrayMap) {
        if (arrayMap != null) {
            int i = arrayMap.mSize;
            ensureCapacity(this.mSize + i);
            if (this.mSize != 0) {
                for (int i2 = 0; i2 < i; i2++) {
                    put(arrayMap.keyAt(i2), arrayMap.valueAt(i2));
                }
            } else if (i > 0) {
                System.arraycopy(arrayMap.mHashes, 0, this.mHashes, 0, i);
                System.arraycopy(arrayMap.mArray, 0, this.mArray, 0, i << 1);
                this.mSize = i;
            }
        }
    }

    public final Set<Map.Entry<K, V>> entrySet() {
        ArrayMap<K, V>.EntrySet entrySet = this.mEntrySet;
        if (entrySet != null) {
            return entrySet;
        }
        ArrayMap<K, V>.EntrySet entrySet2 = new EntrySet();
        this.mEntrySet = entrySet2;
        return entrySet2;
    }

    public final Set<K> keySet() {
        ArrayMap<K, V>.KeySet keySet = this.mKeySet;
        if (keySet != null) {
            return keySet;
        }
        ArrayMap<K, V>.KeySet keySet2 = new KeySet();
        this.mKeySet = keySet2;
        return keySet2;
    }

    public final void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(map.size() + this.mSize);
        for (Map.Entry next : map.entrySet()) {
            put(next.getKey(), next.getValue());
        }
    }

    public final boolean retainAll(Collection<?> collection) {
        int i = this.mSize;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (!collection.contains(keyAt(i2))) {
                removeAt(i2);
            }
        }
        if (i != this.mSize) {
            return true;
        }
        return false;
    }

    public final <T> T[] toArrayHelper(T[] tArr, int i) {
        int i2 = this.mSize;
        if (tArr.length < i2) {
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), i2);
        }
        for (int i3 = 0; i3 < i2; i3++) {
            tArr[i3] = this.mArray[(i3 << 1) + i];
        }
        if (tArr.length > i2) {
            tArr[i2] = null;
        }
        return tArr;
    }

    public final Collection<V> values() {
        ArrayMap<K, V>.ValueCollection valueCollection = this.mValues;
        if (valueCollection != null) {
            return valueCollection;
        }
        ArrayMap<K, V>.ValueCollection valueCollection2 = new ValueCollection();
        this.mValues = valueCollection2;
        return valueCollection2;
    }
}
