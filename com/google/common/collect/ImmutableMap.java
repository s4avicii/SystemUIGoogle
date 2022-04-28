package com.google.common.collect;

import com.google.common.collect.ImmutableCollection;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.DoNotCall;
import com.google.errorprone.annotations.DoNotMock;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

@DoNotMock("Use ImmutableMap.of or another implementation")
public abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable {
    @LazyInit
    public transient ImmutableSet<Map.Entry<K, V>> entrySet;
    @LazyInit
    public transient ImmutableSet<K> keySet;
    @LazyInit
    public transient ImmutableCollection<V> values;

    @DoNotMock
    public static class Builder<K, V> {
        public Object[] alternatingKeysAndValues;
        public int size = 0;

        /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
            jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [short[], byte[]], vars: [r3v3 ?, r3v5 ?, r3v4 ?, r3v6 ?]
            	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
            	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
            	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
            	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:51)
            	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:32)
            */
        public com.google.common.collect.ImmutableMap<K, V> buildOrThrow() {
            /*
                r12 = this;
                int r0 = r12.size
                java.lang.Object[] r12 = r12.alternatingKeysAndValues
                com.google.common.collect.ImmutableMap<java.lang.Object, java.lang.Object> r1 = com.google.common.collect.RegularImmutableMap.EMPTY
                if (r0 != 0) goto L_0x000e
                com.google.common.collect.ImmutableMap<java.lang.Object, java.lang.Object> r12 = com.google.common.collect.RegularImmutableMap.EMPTY
                com.google.common.collect.RegularImmutableMap r12 = (com.google.common.collect.RegularImmutableMap) r12
                goto L_0x0105
            L_0x000e:
                r1 = 0
                r2 = 0
                r3 = 1
                if (r0 != r3) goto L_0x0025
                r0 = r12[r2]
                java.util.Objects.requireNonNull(r0)
                r0 = r12[r3]
                java.util.Objects.requireNonNull(r0)
                com.google.common.collect.RegularImmutableMap r0 = new com.google.common.collect.RegularImmutableMap
                r0.<init>(r1, r12, r3)
                r12 = r0
                goto L_0x0105
            L_0x0025:
                int r4 = r12.length
                int r4 = r4 >> r3
                com.google.common.base.Preconditions.checkPositionIndex(r0, r4)
                int r4 = com.google.common.collect.ImmutableSet.chooseTableSize(r0)
                if (r0 != r3) goto L_0x003c
                r2 = r12[r2]
                java.util.Objects.requireNonNull(r2)
                r2 = r12[r3]
                java.util.Objects.requireNonNull(r2)
                goto L_0x00ff
            L_0x003c:
                int r1 = r4 + -1
                r3 = 128(0x80, float:1.794E-43)
                r5 = -1
                if (r4 > r3) goto L_0x0083
                byte[] r3 = new byte[r4]
                java.util.Arrays.fill(r3, r5)
                r4 = r2
            L_0x0049:
                if (r4 >= r0) goto L_0x0080
                int r5 = r4 * 2
                int r5 = r5 + r2
                r6 = r12[r5]
                java.util.Objects.requireNonNull(r6)
                r7 = r5 ^ 1
                r7 = r12[r7]
                java.util.Objects.requireNonNull(r7)
                int r8 = r6.hashCode()
                int r8 = okio.Okio__OkioKt.smear(r8)
            L_0x0062:
                r8 = r8 & r1
                byte r9 = r3[r8]
                r10 = 255(0xff, float:3.57E-43)
                r9 = r9 & r10
                if (r9 != r10) goto L_0x0070
                byte r5 = (byte) r5
                r3[r8] = r5
                int r4 = r4 + 1
                goto L_0x0049
            L_0x0070:
                r10 = r12[r9]
                boolean r10 = r6.equals(r10)
                if (r10 != 0) goto L_0x007b
                int r8 = r8 + 1
                goto L_0x0062
            L_0x007b:
                java.lang.IllegalArgumentException r12 = com.google.common.collect.RegularImmutableMap.duplicateKeyException(r6, r7, r12, r9)
                throw r12
            L_0x0080:
                r1 = r3
                goto L_0x00ff
            L_0x0083:
                r3 = 32768(0x8000, float:4.5918E-41)
                if (r4 > r3) goto L_0x00c6
                short[] r3 = new short[r4]
                java.util.Arrays.fill(r3, r5)
                r4 = r2
            L_0x008e:
                if (r4 >= r0) goto L_0x0080
                int r5 = r4 * 2
                int r5 = r5 + r2
                r6 = r12[r5]
                java.util.Objects.requireNonNull(r6)
                r7 = r5 ^ 1
                r7 = r12[r7]
                java.util.Objects.requireNonNull(r7)
                int r8 = r6.hashCode()
                int r8 = okio.Okio__OkioKt.smear(r8)
            L_0x00a7:
                r8 = r8 & r1
                short r9 = r3[r8]
                r10 = 65535(0xffff, float:9.1834E-41)
                r9 = r9 & r10
                if (r9 != r10) goto L_0x00b6
                short r5 = (short) r5
                r3[r8] = r5
                int r4 = r4 + 1
                goto L_0x008e
            L_0x00b6:
                r10 = r12[r9]
                boolean r10 = r6.equals(r10)
                if (r10 != 0) goto L_0x00c1
                int r8 = r8 + 1
                goto L_0x00a7
            L_0x00c1:
                java.lang.IllegalArgumentException r12 = com.google.common.collect.RegularImmutableMap.duplicateKeyException(r6, r7, r12, r9)
                throw r12
            L_0x00c6:
                int[] r3 = new int[r4]
                java.util.Arrays.fill(r3, r5)
                r4 = r2
            L_0x00cc:
                if (r4 >= r0) goto L_0x0080
                int r6 = r4 * 2
                int r6 = r6 + r2
                r7 = r12[r6]
                java.util.Objects.requireNonNull(r7)
                r8 = r6 ^ 1
                r8 = r12[r8]
                java.util.Objects.requireNonNull(r8)
                int r9 = r7.hashCode()
                int r9 = okio.Okio__OkioKt.smear(r9)
            L_0x00e5:
                r9 = r9 & r1
                r10 = r3[r9]
                if (r10 != r5) goto L_0x00ef
                r3[r9] = r6
                int r4 = r4 + 1
                goto L_0x00cc
            L_0x00ef:
                r11 = r12[r10]
                boolean r11 = r7.equals(r11)
                if (r11 != 0) goto L_0x00fa
                int r9 = r9 + 1
                goto L_0x00e5
            L_0x00fa:
                java.lang.IllegalArgumentException r12 = com.google.common.collect.RegularImmutableMap.duplicateKeyException(r7, r8, r12, r10)
                throw r12
            L_0x00ff:
                com.google.common.collect.RegularImmutableMap r2 = new com.google.common.collect.RegularImmutableMap
                r2.<init>(r1, r12, r0)
                r12 = r2
            L_0x0105:
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ImmutableMap.Builder.buildOrThrow():com.google.common.collect.ImmutableMap");
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            int i = (this.size + 1) * 2;
            Object[] objArr = this.alternatingKeysAndValues;
            if (i > objArr.length) {
                this.alternatingKeysAndValues = Arrays.copyOf(objArr, ImmutableCollection.Builder.expandedCapacity(objArr.length, i));
            }
            CollectPreconditions.checkEntryNotNull(k, v);
            Object[] objArr2 = this.alternatingKeysAndValues;
            int i2 = this.size;
            int i3 = i2 * 2;
            objArr2[i3] = k;
            objArr2[i3 + 1] = v;
            this.size = i2 + 1;
            return this;
        }

        public Builder(int i) {
            this.alternatingKeysAndValues = new Object[(i * 2)];
        }

        public ImmutableMap<K, V> build() {
            return buildOrThrow();
        }
    }

    public static class SerializedForm<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Object keys;
        private final Object values;

        public Builder<K, V> makeBuilder(int i) {
            return new Builder<>(i);
        }

        public final Object readResolve() {
            Object obj = this.keys;
            if (!(obj instanceof ImmutableSet)) {
                Object[] objArr = (Object[]) obj;
                Object[] objArr2 = (Object[]) this.values;
                Builder makeBuilder = makeBuilder(objArr.length);
                for (int i = 0; i < objArr.length; i++) {
                    makeBuilder.put(objArr[i], objArr2[i]);
                }
                return makeBuilder.build();
            }
            ImmutableSet immutableSet = (ImmutableSet) obj;
            Builder makeBuilder2 = makeBuilder(immutableSet.size());
            UnmodifiableIterator it = immutableSet.iterator();
            UnmodifiableIterator it2 = ((ImmutableCollection) this.values).iterator();
            while (it.hasNext()) {
                makeBuilder2.put(it.next(), it2.next());
            }
            return makeBuilder2.build();
        }

        public SerializedForm(ImmutableMap<K, V> immutableMap) {
            Object[] objArr = new Object[immutableMap.size()];
            Object[] objArr2 = new Object[immutableMap.size()];
            UnmodifiableIterator<Map.Entry<K, V>> it = immutableMap.entrySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                Map.Entry next = it.next();
                objArr[i] = next.getKey();
                objArr2[i] = next.getValue();
                i++;
            }
            this.keys = objArr;
            this.values = objArr2;
        }
    }

    public abstract ImmutableSet<Map.Entry<K, V>> createEntrySet();

    public abstract ImmutableSet<K> createKeySet();

    public abstract ImmutableCollection<V> createValues();

    public abstract V get(Object obj);

    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        ImmutableSet<Map.Entry<K, V>> immutableSet = this.entrySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<Map.Entry<K, V>> createEntrySet = createEntrySet();
        this.entrySet = createEntrySet;
        return createEntrySet;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    public ImmutableSet<K> keySet() {
        ImmutableSet<K> immutableSet = this.keySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<K> createKeySet = createKeySet();
        this.keySet = createKeySet;
        return createKeySet;
    }

    @CanIgnoreReturnValue
    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @DoNotCall("Always throws UnsupportedOperationException")
    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    public ImmutableCollection<V> values() {
        ImmutableCollection<V> immutableCollection = this.values;
        if (immutableCollection != null) {
            return immutableCollection;
        }
        ImmutableCollection<V> createValues = createValues();
        this.values = createValues;
        return createValues;
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(this);
    }

    public final boolean containsKey(Object obj) {
        if (get(obj) != null) {
            return true;
        }
        return false;
    }

    public final boolean containsValue(Object obj) {
        return values().contains(obj);
    }

    public final V getOrDefault(Object obj, V v) {
        V v2 = get(obj);
        if (v2 != null) {
            return v2;
        }
        return v;
    }

    public final int hashCode() {
        return Sets.hashCodeImpl(entrySet());
    }

    public final boolean isEmpty() {
        if (size() == 0) {
            return true;
        }
        return false;
    }

    public final String toString() {
        return Maps.toStringImpl(this);
    }
}
