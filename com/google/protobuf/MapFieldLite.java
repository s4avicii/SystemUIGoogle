package com.google.protobuf;

import com.google.protobuf.Internal;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class MapFieldLite<K, V> extends LinkedHashMap<K, V> {
    public static final MapFieldLite EMPTY_MAP_FIELD;
    private boolean isMutable = true;

    private MapFieldLite() {
    }

    public final void makeImmutable() {
        this.isMutable = false;
    }

    static {
        MapFieldLite mapFieldLite = new MapFieldLite();
        EMPTY_MAP_FIELD = mapFieldLite;
        Objects.requireNonNull(mapFieldLite);
        mapFieldLite.isMutable = false;
    }

    public static int calculateHashCodeForObject(Object obj) {
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            Charset charset = Internal.UTF_8;
            int length = bArr.length;
            int i = length;
            for (int i2 = 0; i2 < 0 + length; i2++) {
                i = (i * 31) + bArr[i2];
            }
            if (i == 0) {
                return 1;
            }
            return i;
        } else if (!(obj instanceof Internal.EnumLite)) {
            return obj.hashCode();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public final void ensureMutable() {
        if (!this.isMutable) {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof java.util.Map
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x005e
            java.util.Map r6 = (java.util.Map) r6
            if (r5 != r6) goto L_0x000c
        L_0x000a:
            r5 = r1
            goto L_0x005b
        L_0x000c:
            int r0 = r5.size()
            int r3 = r6.size()
            if (r0 == r3) goto L_0x0018
        L_0x0016:
            r5 = r2
            goto L_0x005b
        L_0x0018:
            java.util.Set r5 = r5.entrySet()
            java.util.Iterator r5 = r5.iterator()
        L_0x0020:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x000a
            java.lang.Object r0 = r5.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r3 = r0.getKey()
            boolean r3 = r6.containsKey(r3)
            if (r3 != 0) goto L_0x0037
            goto L_0x0016
        L_0x0037:
            java.lang.Object r3 = r0.getValue()
            java.lang.Object r0 = r0.getKey()
            java.lang.Object r0 = r6.get(r0)
            boolean r4 = r3 instanceof byte[]
            if (r4 == 0) goto L_0x0054
            boolean r4 = r0 instanceof byte[]
            if (r4 == 0) goto L_0x0054
            byte[] r3 = (byte[]) r3
            byte[] r0 = (byte[]) r0
            boolean r0 = java.util.Arrays.equals(r3, r0)
            goto L_0x0058
        L_0x0054:
            boolean r0 = r3.equals(r0)
        L_0x0058:
            if (r0 != 0) goto L_0x0020
            goto L_0x0016
        L_0x005b:
            if (r5 == 0) goto L_0x005e
            goto L_0x005f
        L_0x005e:
            r1 = r2
        L_0x005f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.MapFieldLite.equals(java.lang.Object):boolean");
    }

    public MapFieldLite(Map<K, V> map) {
        super(map);
    }

    public final void clear() {
        ensureMutable();
        super.clear();
    }

    public final Set<Map.Entry<K, V>> entrySet() {
        if (isEmpty()) {
            return Collections.emptySet();
        }
        return super.entrySet();
    }

    public final int hashCode() {
        int i = 0;
        for (Map.Entry entry : entrySet()) {
            i += calculateHashCodeForObject(entry.getValue()) ^ calculateHashCodeForObject(entry.getKey());
        }
        return i;
    }

    public final MapFieldLite<K, V> mutableCopy() {
        if (isEmpty()) {
            return new MapFieldLite<>();
        }
        return new MapFieldLite<>(this);
    }

    public final V put(K k, V v) {
        ensureMutable();
        Charset charset = Internal.UTF_8;
        Objects.requireNonNull(k);
        Objects.requireNonNull(v);
        return super.put(k, v);
    }

    public final void putAll(Map<? extends K, ? extends V> map) {
        ensureMutable();
        for (Object next : map.keySet()) {
            Charset charset = Internal.UTF_8;
            Objects.requireNonNull(next);
            Objects.requireNonNull(map.get(next));
        }
        super.putAll(map);
    }

    public final V remove(Object obj) {
        ensureMutable();
        return super.remove(obj);
    }

    public final boolean isMutable() {
        return this.isMutable;
    }
}
