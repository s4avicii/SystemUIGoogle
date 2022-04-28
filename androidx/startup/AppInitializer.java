package androidx.startup;

import android.content.Context;
import android.os.Trace;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public final class AppInitializer {
    public static volatile AppInitializer sInstance;
    public static final Object sLock = new Object();
    public final Context mContext;
    public final HashSet mDiscovered = new HashSet();
    public final HashMap mInitialized = new HashMap();

    public final Object doInitialize(Class cls, HashSet hashSet) {
        Object obj;
        synchronized (sLock) {
            if (Trace.isEnabled()) {
                try {
                    Trace.beginSection(cls.getSimpleName());
                } catch (Throwable th) {
                    Trace.endSection();
                    throw th;
                }
            }
            if (!hashSet.contains(cls)) {
                if (!this.mInitialized.containsKey(cls)) {
                    hashSet.add(cls);
                    Initializer initializer = (Initializer) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    List<Class<? extends Initializer<?>>> dependencies = initializer.dependencies();
                    if (!dependencies.isEmpty()) {
                        for (Class next : dependencies) {
                            if (!this.mInitialized.containsKey(next)) {
                                doInitialize(next, hashSet);
                            }
                        }
                    }
                    obj = initializer.create(this.mContext);
                    hashSet.remove(cls);
                    this.mInitialized.put(cls, obj);
                } else {
                    obj = this.mInitialized.get(cls);
                }
                Trace.endSection();
            } else {
                throw new IllegalStateException(String.format("Cannot initialize %s. Cycle detected.", new Object[]{cls.getName()}));
            }
        }
        return obj;
    }

    public AppInitializer(Context context) {
        this.mContext = context.getApplicationContext();
    }
}
