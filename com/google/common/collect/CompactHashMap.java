package com.google.common.collect;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import androidx.leanback.R$layout;
import androidx.recyclerview.R$dimen;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import okio.Okio;
import okio.Okio__OkioKt;

class CompactHashMap<K, V> extends AbstractMap<K, V> implements Serializable {
    public static final double HASH_FLOODING_FPP = 0.001d;
    public static final Object NOT_FOUND = new Object();
    public transient int[] entries;
    public transient EntrySetView entrySetView;
    public transient KeySetView keySetView;
    public transient Object[] keys;
    public transient int metadata;
    public transient int size;
    public transient Object table;
    public transient Object[] values;
    public transient ValuesView valuesView;

    public class EntrySetView extends AbstractSet<Map.Entry<K, V>> {
        public EntrySetView() {
        }

        public final void clear() {
            CompactHashMap.this.clear();
        }

        public final boolean contains(Object obj) {
            Map delegateOrNull = CompactHashMap.this.delegateOrNull();
            if (delegateOrNull != null) {
                return delegateOrNull.entrySet().contains(obj);
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int indexOf = CompactHashMap.this.indexOf(entry.getKey());
            if (indexOf == -1 || !R$dimen.equal(CompactHashMap.this.value(indexOf), entry.getValue())) {
                return false;
            }
            return true;
        }

        public final Iterator<Map.Entry<K, V>> iterator() {
            CompactHashMap compactHashMap = CompactHashMap.this;
            Objects.requireNonNull(compactHashMap);
            Map delegateOrNull = compactHashMap.delegateOrNull();
            if (delegateOrNull != null) {
                return delegateOrNull.entrySet().iterator();
            }
            return new CompactHashMap<Object, Object>.Itr<Map.Entry<Object, Object>>() {
                public final Object getOutput(int i) {
                    return new MapEntry(i);
                }
            };
        }

        public final boolean remove(Object obj) {
            Map delegateOrNull = CompactHashMap.this.delegateOrNull();
            if (delegateOrNull != null) {
                return delegateOrNull.entrySet().remove(obj);
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (CompactHashMap.this.needsAllocArrays()) {
                return false;
            }
            CompactHashMap compactHashMap = CompactHashMap.this;
            Objects.requireNonNull(compactHashMap);
            int i = (1 << (compactHashMap.metadata & 31)) - 1;
            Object key = entry.getKey();
            Object value = entry.getValue();
            CompactHashMap compactHashMap2 = CompactHashMap.this;
            Objects.requireNonNull(compactHashMap2);
            Object obj2 = compactHashMap2.table;
            Objects.requireNonNull(obj2);
            int remove = Okio.remove(key, value, i, obj2, CompactHashMap.this.requireEntries(), CompactHashMap.this.requireKeys(), CompactHashMap.this.requireValues());
            if (remove == -1) {
                return false;
            }
            CompactHashMap.this.moveLastEntry(remove, i);
            CompactHashMap compactHashMap3 = CompactHashMap.this;
            compactHashMap3.size--;
            compactHashMap3.incrementModCount();
            return true;
        }

        public final int size() {
            return CompactHashMap.this.size();
        }
    }

    public abstract class Itr<T> implements Iterator<T> {
        public int currentIndex;
        public int expectedMetadata;
        public int indexToRemove = -1;

        public abstract T getOutput(int i);

        public Itr() {
            this.expectedMetadata = CompactHashMap.this.metadata;
            this.currentIndex = CompactHashMap.this.firstEntryIndex();
        }

        public final boolean hasNext() {
            if (this.currentIndex >= 0) {
                return true;
            }
            return false;
        }

        public final T next() {
            if (CompactHashMap.this.metadata != this.expectedMetadata) {
                throw new ConcurrentModificationException();
            } else if (hasNext()) {
                int i = this.currentIndex;
                this.indexToRemove = i;
                T output = getOutput(i);
                this.currentIndex = CompactHashMap.this.getSuccessor(this.currentIndex);
                return output;
            } else {
                throw new NoSuchElementException();
            }
        }

        public final void remove() {
            boolean z;
            if (CompactHashMap.this.metadata == this.expectedMetadata) {
                if (this.indexToRemove >= 0) {
                    z = true;
                } else {
                    z = false;
                }
                Preconditions.checkState(z, "no calls to next() since the last call to remove()");
                this.expectedMetadata += 32;
                CompactHashMap compactHashMap = CompactHashMap.this;
                compactHashMap.remove(compactHashMap.key(this.indexToRemove));
                this.currentIndex = CompactHashMap.this.adjustAfterRemove(this.currentIndex, this.indexToRemove);
                this.indexToRemove = -1;
                return;
            }
            throw new ConcurrentModificationException();
        }
    }

    public class KeySetView extends AbstractSet<K> {
        public KeySetView() {
        }

        public final void clear() {
            CompactHashMap.this.clear();
        }

        public final boolean contains(Object obj) {
            return CompactHashMap.this.containsKey(obj);
        }

        public final Iterator<K> iterator() {
            CompactHashMap compactHashMap = CompactHashMap.this;
            Objects.requireNonNull(compactHashMap);
            Map delegateOrNull = compactHashMap.delegateOrNull();
            if (delegateOrNull != null) {
                return delegateOrNull.keySet().iterator();
            }
            return new CompactHashMap<Object, Object>.Itr<Object>() {
                public final Object getOutput(int i) {
                    CompactHashMap compactHashMap = CompactHashMap.this;
                    Object obj = CompactHashMap.NOT_FOUND;
                    return compactHashMap.key(i);
                }
            };
        }

        public final boolean remove(Object obj) {
            Map delegateOrNull = CompactHashMap.this.delegateOrNull();
            if (delegateOrNull != null) {
                return delegateOrNull.keySet().remove(obj);
            }
            if (CompactHashMap.this.removeHelper(obj) != CompactHashMap.NOT_FOUND) {
                return true;
            }
            return false;
        }

        public final int size() {
            return CompactHashMap.this.size();
        }
    }

    public final class MapEntry extends AbstractMapEntry<K, V> {
        public final K key;
        public int lastKnownIndex;

        public MapEntry(int i) {
            Object obj = CompactHashMap.NOT_FOUND;
            this.key = CompactHashMap.this.key(i);
            this.lastKnownIndex = i;
        }

        public final V getValue() {
            Map delegateOrNull = CompactHashMap.this.delegateOrNull();
            if (delegateOrNull != null) {
                return delegateOrNull.get(this.key);
            }
            updateLastKnownIndex();
            int i = this.lastKnownIndex;
            if (i == -1) {
                return null;
            }
            return CompactHashMap.this.value(i);
        }

        public final V setValue(V v) {
            Map delegateOrNull = CompactHashMap.this.delegateOrNull();
            if (delegateOrNull != null) {
                return delegateOrNull.put(this.key, v);
            }
            updateLastKnownIndex();
            int i = this.lastKnownIndex;
            if (i == -1) {
                CompactHashMap.this.put(this.key, v);
                return null;
            }
            V value = CompactHashMap.this.value(i);
            CompactHashMap compactHashMap = CompactHashMap.this;
            int i2 = this.lastKnownIndex;
            Objects.requireNonNull(compactHashMap);
            compactHashMap.requireValues()[i2] = v;
            return value;
        }

        public final void updateLastKnownIndex() {
            int i = this.lastKnownIndex;
            if (i == -1 || i >= CompactHashMap.this.size() || !R$dimen.equal(this.key, CompactHashMap.this.key(this.lastKnownIndex))) {
                CompactHashMap compactHashMap = CompactHashMap.this;
                K k = this.key;
                Object obj = CompactHashMap.NOT_FOUND;
                this.lastKnownIndex = compactHashMap.indexOf(k);
            }
        }

        public final K getKey() {
            return this.key;
        }
    }

    public class ValuesView extends AbstractCollection<V> {
        public ValuesView() {
        }

        public final void clear() {
            CompactHashMap.this.clear();
        }

        public final Iterator<V> iterator() {
            CompactHashMap compactHashMap = CompactHashMap.this;
            Objects.requireNonNull(compactHashMap);
            Map delegateOrNull = compactHashMap.delegateOrNull();
            if (delegateOrNull != null) {
                return delegateOrNull.values().iterator();
            }
            return new CompactHashMap<Object, Object>.Itr<Object>() {
                public final Object getOutput(int i) {
                    CompactHashMap compactHashMap = CompactHashMap.this;
                    Object obj = CompactHashMap.NOT_FOUND;
                    return compactHashMap.value(i);
                }
            };
        }

        public final int size() {
            return CompactHashMap.this.size();
        }
    }

    public CompactHashMap() {
        init(3);
    }

    public void accessEntry(int i) {
    }

    public int adjustAfterRemove(int i, int i2) {
        return i - 1;
    }

    public void init(int i) {
        boolean z;
        if (i >= 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            this.metadata = R$layout.constrainToRange(i, 1);
            return;
        }
        throw new IllegalArgumentException("Expected size must be >= 0");
    }

    public void insertEntry(int i, K k, V v, int i2, int i3) {
        requireEntries()[i] = (i2 & (~i3)) | (i3 & 0);
        requireKeys()[i] = k;
        requireValues()[i] = v;
    }

    @CanIgnoreReturnValue
    public Map<K, V> convertToHashFloodingResistantImplementation() {
        LinkedHashMap createHashFloodingResistantDelegate = createHashFloodingResistantDelegate(((1 << (this.metadata & 31)) - 1) + 1);
        int firstEntryIndex = firstEntryIndex();
        while (firstEntryIndex >= 0) {
            createHashFloodingResistantDelegate.put(key(firstEntryIndex), value(firstEntryIndex));
            firstEntryIndex = getSuccessor(firstEntryIndex);
        }
        this.table = createHashFloodingResistantDelegate;
        this.entries = null;
        this.keys = null;
        this.values = null;
        incrementModCount();
        return createHashFloodingResistantDelegate;
    }

    public LinkedHashMap createHashFloodingResistantDelegate(int i) {
        return new LinkedHashMap(i, 1.0f);
    }

    public Map<K, V> delegateOrNull() {
        Object obj = this.table;
        if (obj instanceof Map) {
            return (Map) obj;
        }
        return null;
    }

    public final Set<Map.Entry<K, V>> entrySet() {
        EntrySetView entrySetView2 = this.entrySetView;
        if (entrySetView2 != null) {
            return entrySetView2;
        }
        EntrySetView entrySetView3 = new EntrySetView();
        this.entrySetView = entrySetView3;
        return entrySetView3;
    }

    public int getSuccessor(int i) {
        int i2 = i + 1;
        if (i2 < this.size) {
            return i2;
        }
        return -1;
    }

    public final void incrementModCount() {
        this.metadata += 32;
    }

    public final Set<K> keySet() {
        KeySetView keySetView2 = this.keySetView;
        if (keySetView2 != null) {
            return keySetView2;
        }
        KeySetView keySetView3 = new KeySetView();
        this.keySetView = keySetView3;
        return keySetView3;
    }

    public void moveLastEntry(int i, int i2) {
        Object obj = this.table;
        Objects.requireNonNull(obj);
        int[] requireEntries = requireEntries();
        Object[] requireKeys = requireKeys();
        Object[] requireValues = requireValues();
        int size2 = size() - 1;
        if (i < size2) {
            Object obj2 = requireKeys[size2];
            requireKeys[i] = obj2;
            requireValues[i] = requireValues[size2];
            requireKeys[size2] = null;
            requireValues[size2] = null;
            requireEntries[i] = requireEntries[size2];
            requireEntries[size2] = 0;
            int smearedHash = Okio__OkioKt.smearedHash(obj2) & i2;
            int tableGet = Okio.tableGet(obj, smearedHash);
            int i3 = size2 + 1;
            if (tableGet == i3) {
                Okio.tableSet(obj, smearedHash, i + 1);
                return;
            }
            while (true) {
                int i4 = tableGet - 1;
                int i5 = requireEntries[i4];
                int i6 = i5 & i2;
                if (i6 == i3) {
                    requireEntries[i4] = ((~i2) & i5) | ((i + 1) & i2);
                    return;
                }
                tableGet = i6;
            }
        } else {
            requireKeys[i] = null;
            requireValues[i] = null;
            requireEntries[i] = 0;
        }
    }

    public boolean needsAllocArrays() {
        if (this.table == null) {
            return true;
        }
        return false;
    }

    @CanIgnoreReturnValue
    public final V put(K k, V v) {
        int min;
        int i;
        int i2;
        int i3;
        K k2 = k;
        V v2 = v;
        if (needsAllocArrays()) {
            allocArrays();
        }
        Map delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.put(k2, v2);
        }
        int[] requireEntries = requireEntries();
        Object[] requireKeys = requireKeys();
        V[] requireValues = requireValues();
        int i4 = this.size;
        int i5 = i4 + 1;
        int smearedHash = Okio__OkioKt.smearedHash(k);
        int i6 = (1 << (this.metadata & 31)) - 1;
        int i7 = smearedHash & i6;
        Object obj = this.table;
        Objects.requireNonNull(obj);
        int tableGet = Okio.tableGet(obj, i7);
        if (tableGet != 0) {
            int i8 = ~i6;
            int i9 = smearedHash & i8;
            int i10 = 0;
            while (true) {
                int i11 = tableGet - 1;
                int i12 = requireEntries[i11];
                int i13 = i12 & i8;
                if (i13 != i9 || !R$dimen.equal(k2, requireKeys[i11])) {
                    int i14 = i12 & i6;
                    Object[] objArr = requireKeys;
                    int i15 = i10 + 1;
                    if (i14 != 0) {
                        i10 = i15;
                        tableGet = i14;
                        requireKeys = objArr;
                    } else if (i15 >= 9) {
                        return convertToHashFloodingResistantImplementation().put(k2, v2);
                    } else {
                        if (i5 > i6) {
                            if (i6 < 32) {
                                i2 = 4;
                            } else {
                                i2 = 2;
                            }
                            i = resizeTable(i6, (i6 + 1) * i2, smearedHash, i4);
                        } else {
                            requireEntries[i11] = (i5 & i6) | i13;
                        }
                    }
                } else {
                    V v3 = requireValues[i11];
                    requireValues[i11] = v2;
                    accessEntry(i11);
                    return v3;
                }
            }
            int length = requireEntries().length;
            resizeEntries(min);
            insertEntry(i4, k, v, smearedHash, i6);
            this.size = i5;
            incrementModCount();
            return null;
        } else if (i5 > i6) {
            if (i6 < 32) {
                i3 = 4;
            } else {
                i3 = 2;
            }
            i = resizeTable(i6, (i6 + 1) * i3, smearedHash, i4);
        } else {
            Object obj2 = this.table;
            Objects.requireNonNull(obj2);
            Okio.tableSet(obj2, i7, i5);
            int length2 = requireEntries().length;
            if (i5 > length2 && (min = Math.min(1073741823, (Math.max(1, length2 >>> 1) + length2) | 1)) != length2) {
                resizeEntries(min);
            }
            insertEntry(i4, k, v, smearedHash, i6);
            this.size = i5;
            incrementModCount();
            return null;
        }
        i6 = i;
        int length22 = requireEntries().length;
        resizeEntries(min);
        insertEntry(i4, k, v, smearedHash, i6);
        this.size = i5;
        incrementModCount();
        return null;
    }

    public final int[] requireEntries() {
        int[] iArr = this.entries;
        Objects.requireNonNull(iArr);
        return iArr;
    }

    public final Object[] requireKeys() {
        Object[] objArr = this.keys;
        Objects.requireNonNull(objArr);
        return objArr;
    }

    public final Object[] requireValues() {
        Object[] objArr = this.values;
        Objects.requireNonNull(objArr);
        return objArr;
    }

    public final Collection<V> values() {
        ValuesView valuesView2 = this.valuesView;
        if (valuesView2 != null) {
            return valuesView2;
        }
        ValuesView valuesView3 = new ValuesView();
        this.valuesView = valuesView3;
        return valuesView3;
    }

    public CompactHashMap(int i) {
        init(i);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        if (readInt >= 0) {
            init(readInt);
            for (int i = 0; i < readInt; i++) {
                put(objectInputStream.readObject(), objectInputStream.readObject());
            }
            return;
        }
        throw new InvalidObjectException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Invalid size: ", readInt));
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        Iterator it;
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        Map delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            it = delegateOrNull.entrySet().iterator();
        } else {
            it = new CompactHashMap<Object, Object>.Itr<Map.Entry<Object, Object>>() {
                public final Object getOutput(int i) {
                    return new MapEntry(i);
                }
            };
        }
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    @CanIgnoreReturnValue
    public int allocArrays() {
        Preconditions.checkState(needsAllocArrays(), "Arrays already allocated");
        int i = this.metadata;
        int max = Math.max(i + 1, 2);
        int highestOneBit = Integer.highestOneBit(max);
        if (max > ((int) (((double) highestOneBit) * 1.0d))) {
            int i2 = highestOneBit << 1;
            if (i2 <= 0) {
                i2 = 1073741824;
            }
            highestOneBit = i2;
        }
        int max2 = Math.max(4, highestOneBit);
        this.table = Okio.createTable(max2);
        this.metadata = ((32 - Integer.numberOfLeadingZeros(max2 - 1)) & 31) | (this.metadata & -32);
        this.entries = new int[i];
        this.keys = new Object[i];
        this.values = new Object[i];
        return i;
    }

    public void clear() {
        if (!needsAllocArrays()) {
            incrementModCount();
            Map delegateOrNull = delegateOrNull();
            if (delegateOrNull != null) {
                this.metadata = R$layout.constrainToRange(size(), 3);
                delegateOrNull.clear();
                this.table = null;
                this.size = 0;
                return;
            }
            Arrays.fill(requireKeys(), 0, this.size, (Object) null);
            Arrays.fill(requireValues(), 0, this.size, (Object) null);
            Object obj = this.table;
            Objects.requireNonNull(obj);
            if (obj instanceof byte[]) {
                Arrays.fill((byte[]) obj, (byte) 0);
            } else if (obj instanceof short[]) {
                Arrays.fill((short[]) obj, 0);
            } else {
                Arrays.fill((int[]) obj, 0);
            }
            Arrays.fill(requireEntries(), 0, this.size, 0);
            this.size = 0;
        }
    }

    public final boolean containsKey(Object obj) {
        Map delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.containsKey(obj);
        }
        if (indexOf(obj) != -1) {
            return true;
        }
        return false;
    }

    public final boolean containsValue(Object obj) {
        Map delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.containsValue(obj);
        }
        for (int i = 0; i < this.size; i++) {
            if (R$dimen.equal(obj, value(i))) {
                return true;
            }
        }
        return false;
    }

    public int firstEntryIndex() {
        if (isEmpty()) {
            return -1;
        }
        return 0;
    }

    public final V get(Object obj) {
        Map delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.get(obj);
        }
        int indexOf = indexOf(obj);
        if (indexOf == -1) {
            return null;
        }
        accessEntry(indexOf);
        return value(indexOf);
    }

    public final int indexOf(Object obj) {
        if (needsAllocArrays()) {
            return -1;
        }
        int smearedHash = Okio__OkioKt.smearedHash(obj);
        int i = (1 << (this.metadata & 31)) - 1;
        Object obj2 = this.table;
        Objects.requireNonNull(obj2);
        int tableGet = Okio.tableGet(obj2, smearedHash & i);
        if (tableGet == 0) {
            return -1;
        }
        int i2 = ~i;
        int i3 = smearedHash & i2;
        do {
            int i4 = tableGet - 1;
            int i5 = requireEntries()[i4];
            if ((i5 & i2) == i3 && R$dimen.equal(obj, key(i4))) {
                return i4;
            }
            tableGet = i5 & i;
        } while (tableGet != 0);
        return -1;
    }

    public final boolean isEmpty() {
        if (size() == 0) {
            return true;
        }
        return false;
    }

    public final K key(int i) {
        return requireKeys()[i];
    }

    @CanIgnoreReturnValue
    public final V remove(Object obj) {
        Map delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.remove(obj);
        }
        V removeHelper = removeHelper(obj);
        if (removeHelper == NOT_FOUND) {
            return null;
        }
        return removeHelper;
    }

    public final Object removeHelper(Object obj) {
        if (needsAllocArrays()) {
            return NOT_FOUND;
        }
        int i = (1 << (this.metadata & 31)) - 1;
        Object obj2 = this.table;
        Objects.requireNonNull(obj2);
        int remove = Okio.remove(obj, (Object) null, i, obj2, requireEntries(), requireKeys(), (Object[]) null);
        if (remove == -1) {
            return NOT_FOUND;
        }
        Object value = value(remove);
        moveLastEntry(remove, i);
        this.size--;
        incrementModCount();
        return value;
    }

    public void resizeEntries(int i) {
        this.entries = Arrays.copyOf(requireEntries(), i);
        this.keys = Arrays.copyOf(requireKeys(), i);
        this.values = Arrays.copyOf(requireValues(), i);
    }

    @CanIgnoreReturnValue
    public final int resizeTable(int i, int i2, int i3, int i4) {
        Object createTable = Okio.createTable(i2);
        int i5 = i2 - 1;
        if (i4 != 0) {
            Okio.tableSet(createTable, i3 & i5, i4 + 1);
        }
        Object obj = this.table;
        Objects.requireNonNull(obj);
        int[] requireEntries = requireEntries();
        for (int i6 = 0; i6 <= i; i6++) {
            int tableGet = Okio.tableGet(obj, i6);
            while (tableGet != 0) {
                int i7 = tableGet - 1;
                int i8 = requireEntries[i7];
                int i9 = ((~i) & i8) | i6;
                int i10 = i9 & i5;
                int tableGet2 = Okio.tableGet(createTable, i10);
                Okio.tableSet(createTable, i10, tableGet);
                requireEntries[i7] = ((~i5) & i9) | (tableGet2 & i5);
                tableGet = i8 & i;
            }
        }
        this.table = createTable;
        this.metadata = ((32 - Integer.numberOfLeadingZeros(i5)) & 31) | (this.metadata & -32);
        return i5;
    }

    public final int size() {
        Map delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.size();
        }
        return this.size;
    }

    public final V value(int i) {
        return requireValues()[i];
    }
}
