package com.google.protobuf;

import java.util.Objects;

public final class MapFieldSchemaLite implements MapFieldSchema {
    public final Object toImmutable(Object obj) {
        ((MapFieldLite) obj).makeImmutable();
        return obj;
    }

    public final void forMapMetadata(Object obj) {
        Objects.requireNonNull((MapEntryLite) obj);
    }

    public final MapFieldLite mergeFrom(Object obj, Object obj2) {
        MapFieldLite mapFieldLite = (MapFieldLite) obj;
        MapFieldLite mapFieldLite2 = (MapFieldLite) obj2;
        if (!mapFieldLite2.isEmpty()) {
            if (!mapFieldLite.isMutable()) {
                mapFieldLite = mapFieldLite.mutableCopy();
            }
            mapFieldLite.ensureMutable();
            if (!mapFieldLite2.isEmpty()) {
                mapFieldLite.putAll(mapFieldLite2);
            }
        }
        return mapFieldLite;
    }

    public final MapFieldLite forMapData(Object obj) {
        return (MapFieldLite) obj;
    }
}
