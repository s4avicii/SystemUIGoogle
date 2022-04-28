package androidx.lifecycle;

import java.util.HashMap;

public abstract class ViewModel {
    public final HashMap mBagOfTags = new HashMap();

    public void onCleared() {
    }
}
