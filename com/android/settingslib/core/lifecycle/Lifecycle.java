package com.android.settingslib.core.lifecycle;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.R$dimen;
import com.android.settingslib.core.lifecycle.events.OnAttach;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.systemui.plugins.FalsingManager;
import java.util.ArrayList;
import java.util.Objects;

public final class Lifecycle extends LifecycleRegistry {
    public final ArrayList mObservers = new ArrayList();

    public class LifecycleProxy implements LifecycleObserver {
        public LifecycleProxy() {
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        public void onLifecycleEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            int i = C05951.$SwitchMap$androidx$lifecycle$Lifecycle$Event[event.ordinal()];
            int i2 = 0;
            switch (i) {
                case 2:
                    Lifecycle lifecycle = Lifecycle.this;
                    Objects.requireNonNull(lifecycle);
                    int size = lifecycle.mObservers.size();
                    while (i2 < size) {
                        LifecycleObserver lifecycleObserver = (LifecycleObserver) lifecycle.mObservers.get(i2);
                        if (lifecycleObserver instanceof OnStart) {
                            ((OnStart) lifecycleObserver).onStart();
                        }
                        i2++;
                    }
                    return;
                case 3:
                    Lifecycle lifecycle2 = Lifecycle.this;
                    Objects.requireNonNull(lifecycle2);
                    int size2 = lifecycle2.mObservers.size();
                    while (i2 < size2) {
                        LifecycleObserver lifecycleObserver2 = (LifecycleObserver) lifecycle2.mObservers.get(i2);
                        if (lifecycleObserver2 instanceof OnResume) {
                            ((OnResume) lifecycleObserver2).onResume();
                        }
                        i2++;
                    }
                    return;
                case 4:
                    Lifecycle lifecycle3 = Lifecycle.this;
                    Objects.requireNonNull(lifecycle3);
                    int size3 = lifecycle3.mObservers.size();
                    while (i2 < size3) {
                        LifecycleObserver lifecycleObserver3 = (LifecycleObserver) lifecycle3.mObservers.get(i2);
                        if (lifecycleObserver3 instanceof OnPause) {
                            ((OnPause) lifecycleObserver3).onPause();
                        }
                        i2++;
                    }
                    return;
                case 5:
                    Lifecycle lifecycle4 = Lifecycle.this;
                    Objects.requireNonNull(lifecycle4);
                    int size4 = lifecycle4.mObservers.size();
                    while (i2 < size4) {
                        LifecycleObserver lifecycleObserver4 = (LifecycleObserver) lifecycle4.mObservers.get(i2);
                        if (lifecycleObserver4 instanceof OnStop) {
                            ((OnStop) lifecycleObserver4).onStop();
                        }
                        i2++;
                    }
                    return;
                case FalsingManager.VERSION /*6*/:
                    Lifecycle lifecycle5 = Lifecycle.this;
                    Objects.requireNonNull(lifecycle5);
                    int size5 = lifecycle5.mObservers.size();
                    while (i2 < size5) {
                        LifecycleObserver lifecycleObserver5 = (LifecycleObserver) lifecycle5.mObservers.get(i2);
                        if (lifecycleObserver5 instanceof OnDestroy) {
                            ((OnDestroy) lifecycleObserver5).onDestroy();
                        }
                        i2++;
                    }
                    return;
                case 7:
                    Log.wtf("LifecycleObserver", "Should not receive an 'ANY' event!");
                    return;
                default:
                    return;
            }
        }
    }

    public Lifecycle(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner, true);
        addObserver(new LifecycleProxy());
    }

    /* renamed from: com.android.settingslib.core.lifecycle.Lifecycle$1 */
    public static /* synthetic */ class C05951 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$Event;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|(3:13|14|16)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                androidx.lifecycle.Lifecycle$Event[] r0 = androidx.lifecycle.Lifecycle.Event.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$androidx$lifecycle$Lifecycle$Event = r0
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_CREATE     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$androidx$lifecycle$Lifecycle$Event     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_START     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$androidx$lifecycle$Lifecycle$Event     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_RESUME     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$androidx$lifecycle$Lifecycle$Event     // Catch:{ NoSuchFieldError -> 0x0033 }
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_PAUSE     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$androidx$lifecycle$Lifecycle$Event     // Catch:{ NoSuchFieldError -> 0x003e }
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_STOP     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$androidx$lifecycle$Lifecycle$Event     // Catch:{ NoSuchFieldError -> 0x0049 }
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_DESTROY     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = $SwitchMap$androidx$lifecycle$Lifecycle$Event     // Catch:{ NoSuchFieldError -> 0x0054 }
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_ANY     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.core.lifecycle.Lifecycle.C05951.<clinit>():void");
        }
    }

    public final void addObserver(LifecycleObserver lifecycleObserver) {
        boolean z;
        if (R$dimen.sMainThread == null) {
            R$dimen.sMainThread = Looper.getMainLooper().getThread();
        }
        if (Thread.currentThread() == R$dimen.sMainThread) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            super.addObserver(lifecycleObserver);
            if (lifecycleObserver instanceof LifecycleObserver) {
                this.mObservers.add((LifecycleObserver) lifecycleObserver);
                return;
            }
            return;
        }
        throw new RuntimeException("Must be called on the UI thread");
    }

    public final void onAttach() {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver lifecycleObserver = (LifecycleObserver) this.mObservers.get(i);
            if (lifecycleObserver instanceof OnAttach) {
                ((OnAttach) lifecycleObserver).onAttach$1();
            }
        }
    }

    public final void onCreate(Bundle bundle) {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver lifecycleObserver = (LifecycleObserver) this.mObservers.get(i);
            if (lifecycleObserver instanceof OnCreate) {
                ((OnCreate) lifecycleObserver).onCreate(bundle);
            }
        }
    }

    public final void removeObserver(LifecycleObserver lifecycleObserver) {
        boolean z;
        if (R$dimen.sMainThread == null) {
            R$dimen.sMainThread = Looper.getMainLooper().getThread();
        }
        if (Thread.currentThread() == R$dimen.sMainThread) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            super.removeObserver(lifecycleObserver);
            if (lifecycleObserver instanceof LifecycleObserver) {
                this.mObservers.remove(lifecycleObserver);
                return;
            }
            return;
        }
        throw new RuntimeException("Must be called on the UI thread");
    }
}
