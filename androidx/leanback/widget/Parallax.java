package androidx.leanback.widget;

import android.util.Property;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Parallax<PropertyT extends Property> {
    public final ArrayList mEffects = new ArrayList(4);
    public float[] mFloatValues = new float[4];
    public final ArrayList mProperties;
    public final List<PropertyT> mPropertiesReadOnly;
    public int[] mValues = new int[4];

    public Parallax() {
        ArrayList arrayList = new ArrayList();
        this.mProperties = arrayList;
        this.mPropertiesReadOnly = Collections.unmodifiableList(arrayList);
    }
}
