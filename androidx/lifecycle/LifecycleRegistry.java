package androidx.lifecycle;

import android.annotation.SuppressLint;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Looper;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class LifecycleRegistry extends Lifecycle {
    public int mAddingObserverCounter = 0;
    public final boolean mEnforceMainThread;
    public boolean mHandlingEvent = false;
    public final WeakReference<LifecycleOwner> mLifecycleOwner;
    public boolean mNewEventOccurred = false;
    public FastSafeIterableMap<LifecycleObserver, ObserverWithState> mObserverMap = new FastSafeIterableMap<>();
    public ArrayList<Lifecycle.State> mParentStates = new ArrayList<>();
    public Lifecycle.State mState;

    public static class ObserverWithState {
        public LifecycleEventObserver mLifecycleObserver;
        public Lifecycle.State mState;

        public ObserverWithState(LifecycleObserver lifecycleObserver, Lifecycle.State state) {
            LifecycleEventObserver lifecycleEventObserver;
            HashMap hashMap = Lifecycling.sCallbackCache;
            boolean z = lifecycleObserver instanceof LifecycleEventObserver;
            boolean z2 = lifecycleObserver instanceof FullLifecycleObserver;
            if (z && z2) {
                lifecycleEventObserver = new FullLifecycleObserverAdapter((FullLifecycleObserver) lifecycleObserver, (LifecycleEventObserver) lifecycleObserver);
            } else if (z2) {
                lifecycleEventObserver = new FullLifecycleObserverAdapter((FullLifecycleObserver) lifecycleObserver, (LifecycleEventObserver) null);
            } else if (z) {
                lifecycleEventObserver = (LifecycleEventObserver) lifecycleObserver;
            } else {
                Class<?> cls = lifecycleObserver.getClass();
                if (Lifecycling.getObserverConstructorType(cls) == 2) {
                    List list = (List) Lifecycling.sClassToAdapters.get(cls);
                    if (list.size() == 1) {
                        lifecycleEventObserver = new SingleGeneratedAdapterObserver(Lifecycling.createGeneratedAdapter((Constructor) list.get(0), lifecycleObserver));
                    } else {
                        GeneratedAdapter[] generatedAdapterArr = new GeneratedAdapter[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            generatedAdapterArr[i] = Lifecycling.createGeneratedAdapter((Constructor) list.get(i), lifecycleObserver);
                        }
                        lifecycleEventObserver = new CompositeGeneratedAdaptersObserver(generatedAdapterArr);
                    }
                } else {
                    lifecycleEventObserver = new ReflectiveGenericLifecycleObserver(lifecycleObserver);
                }
            }
            this.mLifecycleObserver = lifecycleEventObserver;
            this.mState = state;
        }

        public final void dispatchEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            Lifecycle.State targetState = event.getTargetState();
            Lifecycle.State state = this.mState;
            if (targetState.compareTo(state) < 0) {
                state = targetState;
            }
            this.mState = state;
            this.mLifecycleObserver.onStateChanged(lifecycleOwner, event);
            this.mState = targetState;
        }
    }

    public static LifecycleRegistry createUnsafe(LifecycleOwner lifecycleOwner) {
        return new LifecycleRegistry(lifecycleOwner, false);
    }

    public void addObserver(LifecycleObserver lifecycleObserver) {
        LifecycleOwner lifecycleOwner;
        boolean z;
        Lifecycle.Event event;
        enforceMainThreadIfNeeded("addObserver");
        Lifecycle.State state = this.mState;
        Lifecycle.State state2 = Lifecycle.State.DESTROYED;
        if (state != state2) {
            state2 = Lifecycle.State.INITIALIZED;
        }
        ObserverWithState observerWithState = new ObserverWithState(lifecycleObserver, state2);
        if (this.mObserverMap.putIfAbsent(lifecycleObserver, observerWithState) == null && (lifecycleOwner = this.mLifecycleOwner.get()) != null) {
            if (this.mAddingObserverCounter != 0 || this.mHandlingEvent) {
                z = true;
            } else {
                z = false;
            }
            Lifecycle.State calculateTargetState = calculateTargetState(lifecycleObserver);
            this.mAddingObserverCounter++;
            while (observerWithState.mState.compareTo(calculateTargetState) < 0) {
                FastSafeIterableMap<LifecycleObserver, ObserverWithState> fastSafeIterableMap = this.mObserverMap;
                Objects.requireNonNull(fastSafeIterableMap);
                if (!fastSafeIterableMap.mHashMap.containsKey(lifecycleObserver)) {
                    break;
                }
                this.mParentStates.add(observerWithState.mState);
                int ordinal = observerWithState.mState.ordinal();
                if (ordinal == 1) {
                    event = Lifecycle.Event.ON_CREATE;
                } else if (ordinal == 2) {
                    event = Lifecycle.Event.ON_START;
                } else if (ordinal != 3) {
                    event = null;
                } else {
                    event = Lifecycle.Event.ON_RESUME;
                }
                if (event != null) {
                    observerWithState.dispatchEvent(lifecycleOwner, event);
                    ArrayList<Lifecycle.State> arrayList = this.mParentStates;
                    arrayList.remove(arrayList.size() - 1);
                    calculateTargetState = calculateTargetState(lifecycleObserver);
                } else {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("no event up from ");
                    m.append(observerWithState.mState);
                    throw new IllegalStateException(m.toString());
                }
            }
            if (!z) {
                sync();
            }
            this.mAddingObserverCounter--;
        }
    }

    public final Lifecycle.State calculateTargetState(LifecycleObserver lifecycleObserver) {
        SafeIterableMap.Entry<K, V> entry;
        Lifecycle.State state;
        FastSafeIterableMap<LifecycleObserver, ObserverWithState> fastSafeIterableMap = this.mObserverMap;
        Objects.requireNonNull(fastSafeIterableMap);
        Lifecycle.State state2 = null;
        if (fastSafeIterableMap.mHashMap.containsKey(lifecycleObserver)) {
            entry = fastSafeIterableMap.mHashMap.get(lifecycleObserver).mPrevious;
        } else {
            entry = null;
        }
        if (entry != null) {
            state = ((ObserverWithState) entry.mValue).mState;
        } else {
            state = null;
        }
        if (!this.mParentStates.isEmpty()) {
            ArrayList<Lifecycle.State> arrayList = this.mParentStates;
            state2 = arrayList.get(arrayList.size() - 1);
        }
        Lifecycle.State state3 = this.mState;
        if (state == null || state.compareTo(state3) >= 0) {
            state = state3;
        }
        if (state2 == null || state2.compareTo(state) >= 0) {
            return state;
        }
        return state2;
    }

    @SuppressLint({"RestrictedApi"})
    public final void enforceMainThreadIfNeeded(String str) {
        boolean z;
        if (this.mEnforceMainThread) {
            ArchTaskExecutor instance = ArchTaskExecutor.getInstance();
            Objects.requireNonNull(instance);
            Objects.requireNonNull(instance.mDelegate);
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                throw new IllegalStateException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("Method ", str, " must be called on the main thread"));
            }
        }
    }

    public final void handleLifecycleEvent(Lifecycle.Event event) {
        enforceMainThreadIfNeeded("handleLifecycleEvent");
        moveToState(event.getTargetState());
    }

    public final void moveToState(Lifecycle.State state) {
        if (this.mState != state) {
            this.mState = state;
            if (this.mHandlingEvent || this.mAddingObserverCounter != 0) {
                this.mNewEventOccurred = true;
                return;
            }
            this.mHandlingEvent = true;
            sync();
            this.mHandlingEvent = false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0196 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void sync() {
        /*
            r11 = this;
            java.lang.ref.WeakReference<androidx.lifecycle.LifecycleOwner> r0 = r11.mLifecycleOwner
            java.lang.Object r0 = r0.get()
            androidx.lifecycle.LifecycleOwner r0 = (androidx.lifecycle.LifecycleOwner) r0
            if (r0 == 0) goto L_0x0199
        L_0x000a:
            androidx.arch.core.internal.FastSafeIterableMap<androidx.lifecycle.LifecycleObserver, androidx.lifecycle.LifecycleRegistry$ObserverWithState> r1 = r11.mObserverMap
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.mSize
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x0016
            goto L_0x003c
        L_0x0016:
            androidx.arch.core.internal.FastSafeIterableMap<androidx.lifecycle.LifecycleObserver, androidx.lifecycle.LifecycleRegistry$ObserverWithState> r1 = r11.mObserverMap
            java.util.Objects.requireNonNull(r1)
            androidx.arch.core.internal.SafeIterableMap$Entry<K, V> r1 = r1.mStart
            java.util.Objects.requireNonNull(r1)
            V r1 = r1.mValue
            androidx.lifecycle.LifecycleRegistry$ObserverWithState r1 = (androidx.lifecycle.LifecycleRegistry.ObserverWithState) r1
            androidx.lifecycle.Lifecycle$State r1 = r1.mState
            androidx.arch.core.internal.FastSafeIterableMap<androidx.lifecycle.LifecycleObserver, androidx.lifecycle.LifecycleRegistry$ObserverWithState> r4 = r11.mObserverMap
            java.util.Objects.requireNonNull(r4)
            androidx.arch.core.internal.SafeIterableMap$Entry<K, V> r4 = r4.mEnd
            java.util.Objects.requireNonNull(r4)
            V r4 = r4.mValue
            androidx.lifecycle.LifecycleRegistry$ObserverWithState r4 = (androidx.lifecycle.LifecycleRegistry.ObserverWithState) r4
            androidx.lifecycle.Lifecycle$State r4 = r4.mState
            if (r1 != r4) goto L_0x003e
            androidx.lifecycle.Lifecycle$State r1 = r11.mState
            if (r1 != r4) goto L_0x003e
        L_0x003c:
            r1 = r3
            goto L_0x003f
        L_0x003e:
            r1 = r2
        L_0x003f:
            if (r1 != 0) goto L_0x0196
            r11.mNewEventOccurred = r2
            androidx.lifecycle.Lifecycle$State r1 = r11.mState
            androidx.arch.core.internal.FastSafeIterableMap<androidx.lifecycle.LifecycleObserver, androidx.lifecycle.LifecycleRegistry$ObserverWithState> r2 = r11.mObserverMap
            java.util.Objects.requireNonNull(r2)
            androidx.arch.core.internal.SafeIterableMap$Entry<K, V> r2 = r2.mStart
            java.util.Objects.requireNonNull(r2)
            V r2 = r2.mValue
            androidx.lifecycle.LifecycleRegistry$ObserverWithState r2 = (androidx.lifecycle.LifecycleRegistry.ObserverWithState) r2
            androidx.lifecycle.Lifecycle$State r2 = r2.mState
            int r1 = r1.compareTo(r2)
            r2 = 0
            r4 = 3
            r5 = 2
            if (r1 >= 0) goto L_0x00f0
            androidx.arch.core.internal.FastSafeIterableMap<androidx.lifecycle.LifecycleObserver, androidx.lifecycle.LifecycleRegistry$ObserverWithState> r1 = r11.mObserverMap
            java.util.Objects.requireNonNull(r1)
            androidx.arch.core.internal.SafeIterableMap$DescendingIterator r6 = new androidx.arch.core.internal.SafeIterableMap$DescendingIterator
            androidx.arch.core.internal.SafeIterableMap$Entry<K, V> r7 = r1.mEnd
            androidx.arch.core.internal.SafeIterableMap$Entry<K, V> r8 = r1.mStart
            r6.<init>(r7, r8)
            java.util.WeakHashMap<androidx.arch.core.internal.SafeIterableMap$SupportRemove<K, V>, java.lang.Boolean> r1 = r1.mIterators
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            r1.put(r6, r7)
        L_0x0073:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L_0x00f0
            boolean r1 = r11.mNewEventOccurred
            if (r1 != 0) goto L_0x00f0
            java.lang.Object r1 = r6.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r7 = r1.getValue()
            androidx.lifecycle.LifecycleRegistry$ObserverWithState r7 = (androidx.lifecycle.LifecycleRegistry.ObserverWithState) r7
        L_0x0089:
            androidx.lifecycle.Lifecycle$State r8 = r7.mState
            androidx.lifecycle.Lifecycle$State r9 = r11.mState
            int r8 = r8.compareTo(r9)
            if (r8 <= 0) goto L_0x0073
            boolean r8 = r11.mNewEventOccurred
            if (r8 != 0) goto L_0x0073
            androidx.arch.core.internal.FastSafeIterableMap<androidx.lifecycle.LifecycleObserver, androidx.lifecycle.LifecycleRegistry$ObserverWithState> r8 = r11.mObserverMap
            java.lang.Object r9 = r1.getKey()
            androidx.lifecycle.LifecycleObserver r9 = (androidx.lifecycle.LifecycleObserver) r9
            java.util.Objects.requireNonNull(r8)
            java.util.HashMap<K, androidx.arch.core.internal.SafeIterableMap$Entry<K, V>> r8 = r8.mHashMap
            boolean r8 = r8.containsKey(r9)
            if (r8 == 0) goto L_0x0073
            androidx.lifecycle.Lifecycle$State r8 = r7.mState
            int r8 = r8.ordinal()
            if (r8 == r5) goto L_0x00bf
            if (r8 == r4) goto L_0x00bc
            r9 = 4
            if (r8 == r9) goto L_0x00b9
            r8 = r2
            goto L_0x00c1
        L_0x00b9:
            androidx.lifecycle.Lifecycle$Event r8 = androidx.lifecycle.Lifecycle.Event.ON_PAUSE
            goto L_0x00c1
        L_0x00bc:
            androidx.lifecycle.Lifecycle$Event r8 = androidx.lifecycle.Lifecycle.Event.ON_STOP
            goto L_0x00c1
        L_0x00bf:
            androidx.lifecycle.Lifecycle$Event r8 = androidx.lifecycle.Lifecycle.Event.ON_DESTROY
        L_0x00c1:
            if (r8 == 0) goto L_0x00db
            androidx.lifecycle.Lifecycle$State r9 = r8.getTargetState()
            java.util.ArrayList<androidx.lifecycle.Lifecycle$State> r10 = r11.mParentStates
            r10.add(r9)
            r7.dispatchEvent(r0, r8)
            java.util.ArrayList<androidx.lifecycle.Lifecycle$State> r8 = r11.mParentStates
            int r9 = r8.size()
            int r9 = r9 + -1
            r8.remove(r9)
            goto L_0x0089
        L_0x00db:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "no event down from "
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            androidx.lifecycle.Lifecycle$State r1 = r7.mState
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r11.<init>(r0)
            throw r11
        L_0x00f0:
            androidx.arch.core.internal.FastSafeIterableMap<androidx.lifecycle.LifecycleObserver, androidx.lifecycle.LifecycleRegistry$ObserverWithState> r1 = r11.mObserverMap
            java.util.Objects.requireNonNull(r1)
            androidx.arch.core.internal.SafeIterableMap$Entry<K, V> r1 = r1.mEnd
            boolean r6 = r11.mNewEventOccurred
            if (r6 != 0) goto L_0x000a
            if (r1 == 0) goto L_0x000a
            androidx.lifecycle.Lifecycle$State r6 = r11.mState
            V r1 = r1.mValue
            androidx.lifecycle.LifecycleRegistry$ObserverWithState r1 = (androidx.lifecycle.LifecycleRegistry.ObserverWithState) r1
            androidx.lifecycle.Lifecycle$State r1 = r1.mState
            int r1 = r6.compareTo(r1)
            if (r1 <= 0) goto L_0x000a
            androidx.arch.core.internal.FastSafeIterableMap<androidx.lifecycle.LifecycleObserver, androidx.lifecycle.LifecycleRegistry$ObserverWithState> r1 = r11.mObserverMap
            java.util.Objects.requireNonNull(r1)
            androidx.arch.core.internal.SafeIterableMap$IteratorWithAdditions r6 = new androidx.arch.core.internal.SafeIterableMap$IteratorWithAdditions
            r6.<init>()
            java.util.WeakHashMap<androidx.arch.core.internal.SafeIterableMap$SupportRemove<K, V>, java.lang.Boolean> r1 = r1.mIterators
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            r1.put(r6, r7)
        L_0x011c:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L_0x000a
            boolean r1 = r11.mNewEventOccurred
            if (r1 != 0) goto L_0x000a
            java.lang.Object r1 = r6.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r7 = r1.getValue()
            androidx.lifecycle.LifecycleRegistry$ObserverWithState r7 = (androidx.lifecycle.LifecycleRegistry.ObserverWithState) r7
        L_0x0132:
            androidx.lifecycle.Lifecycle$State r8 = r7.mState
            androidx.lifecycle.Lifecycle$State r9 = r11.mState
            int r8 = r8.compareTo(r9)
            if (r8 >= 0) goto L_0x011c
            boolean r8 = r11.mNewEventOccurred
            if (r8 != 0) goto L_0x011c
            androidx.arch.core.internal.FastSafeIterableMap<androidx.lifecycle.LifecycleObserver, androidx.lifecycle.LifecycleRegistry$ObserverWithState> r8 = r11.mObserverMap
            java.lang.Object r9 = r1.getKey()
            androidx.lifecycle.LifecycleObserver r9 = (androidx.lifecycle.LifecycleObserver) r9
            java.util.Objects.requireNonNull(r8)
            java.util.HashMap<K, androidx.arch.core.internal.SafeIterableMap$Entry<K, V>> r8 = r8.mHashMap
            boolean r8 = r8.containsKey(r9)
            if (r8 == 0) goto L_0x011c
            androidx.lifecycle.Lifecycle$State r8 = r7.mState
            java.util.ArrayList<androidx.lifecycle.Lifecycle$State> r9 = r11.mParentStates
            r9.add(r8)
            androidx.lifecycle.Lifecycle$State r8 = r7.mState
            int r8 = r8.ordinal()
            if (r8 == r3) goto L_0x016e
            if (r8 == r5) goto L_0x016b
            if (r8 == r4) goto L_0x0168
            r8 = r2
            goto L_0x0170
        L_0x0168:
            androidx.lifecycle.Lifecycle$Event r8 = androidx.lifecycle.Lifecycle.Event.ON_RESUME
            goto L_0x0170
        L_0x016b:
            androidx.lifecycle.Lifecycle$Event r8 = androidx.lifecycle.Lifecycle.Event.ON_START
            goto L_0x0170
        L_0x016e:
            androidx.lifecycle.Lifecycle$Event r8 = androidx.lifecycle.Lifecycle.Event.ON_CREATE
        L_0x0170:
            if (r8 == 0) goto L_0x0181
            r7.dispatchEvent(r0, r8)
            java.util.ArrayList<androidx.lifecycle.Lifecycle$State> r8 = r11.mParentStates
            int r9 = r8.size()
            int r9 = r9 + -1
            r8.remove(r9)
            goto L_0x0132
        L_0x0181:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "no event up from "
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            androidx.lifecycle.Lifecycle$State r1 = r7.mState
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r11.<init>(r0)
            throw r11
        L_0x0196:
            r11.mNewEventOccurred = r2
            return
        L_0x0199:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "LifecycleOwner of this LifecycleRegistry is alreadygarbage collected. It is too late to change lifecycle state."
            r11.<init>(r0)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.LifecycleRegistry.sync():void");
    }

    public LifecycleRegistry(LifecycleOwner lifecycleOwner, boolean z) {
        this.mLifecycleOwner = new WeakReference<>(lifecycleOwner);
        this.mState = Lifecycle.State.INITIALIZED;
        this.mEnforceMainThread = z;
    }

    public void removeObserver(LifecycleObserver lifecycleObserver) {
        enforceMainThreadIfNeeded("removeObserver");
        this.mObserverMap.remove(lifecycleObserver);
    }

    public final void setCurrentState(Lifecycle.State state) {
        enforceMainThreadIfNeeded("setCurrentState");
        moveToState(state);
    }

    public final Lifecycle.State getCurrentState() {
        return this.mState;
    }
}
