package com.google.android.setupcompat.internal;

import android.annotation.TargetApi;
import android.os.BaseBundle;
import android.os.PersistableBundle;
import android.util.ArrayMap;
import com.android.systemui.R$array;
import com.google.android.setupcompat.util.Logger;
import java.util.Objects;

@TargetApi(22)
public final class PersistableBundles {
    public static final Logger LOG = new Logger("PersistableBundles");

    public static ArrayMap<String, Object> toMap(BaseBundle baseBundle) {
        if (baseBundle == null || baseBundle.isEmpty()) {
            return new ArrayMap<>(0);
        }
        ArrayMap<String, Object> arrayMap = new ArrayMap<>(baseBundle.size());
        for (String next : baseBundle.keySet()) {
            Object obj = baseBundle.get(next);
            if (!isSupportedDataType(obj)) {
                LOG.mo18774w(String.format("Unknown/unsupported data type [%s] for key %s", new Object[]{obj, next}));
            } else {
                arrayMap.put(next, baseBundle.get(next));
            }
        }
        return arrayMap;
    }

    public static PersistableBundle assertIsValid(PersistableBundle persistableBundle) {
        Objects.requireNonNull(persistableBundle, "PersistableBundle cannot be null!");
        for (String next : persistableBundle.keySet()) {
            Object obj = persistableBundle.get(next);
            R$array.checkArgument(isSupportedDataType(obj), String.format("Unknown/unsupported data type [%s] for key %s", new Object[]{obj, next}));
        }
        return persistableBundle;
    }

    public static boolean isSupportedDataType(Object obj) {
        if ((obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Double) || (obj instanceof Float) || (obj instanceof String) || (obj instanceof Boolean)) {
            return true;
        }
        return false;
    }
}
