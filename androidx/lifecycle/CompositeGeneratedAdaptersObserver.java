package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import java.util.HashMap;

class CompositeGeneratedAdaptersObserver implements LifecycleEventObserver {
    public final GeneratedAdapter[] mGeneratedAdapters;

    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        new HashMap();
        for (GeneratedAdapter callMethods : this.mGeneratedAdapters) {
            callMethods.callMethods();
        }
        for (GeneratedAdapter callMethods2 : this.mGeneratedAdapters) {
            callMethods2.callMethods();
        }
    }

    public CompositeGeneratedAdaptersObserver(GeneratedAdapter[] generatedAdapterArr) {
        this.mGeneratedAdapters = generatedAdapterArr;
    }
}
