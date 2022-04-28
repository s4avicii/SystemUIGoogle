package com.android.systemui;

import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.view.Choreographer;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.function.Supplier;

public final class DejankUtils {
    public static final boolean STRICT_MODE_ENABLED;
    public static final DejankUtils$$ExternalSyntheticLambda0 sAnimationCallbackRunnable = DejankUtils$$ExternalSyntheticLambda0.INSTANCE;
    public static Stack<String> sBlockingIpcs = new Stack<>();
    public static final Choreographer sChoreographer = Choreographer.getInstance();
    public static final Handler sHandler = new Handler();
    public static boolean sImmediate;
    public static final Object sLock = new Object();
    public static final ArrayList<Runnable> sPendingRunnables = new ArrayList<>();
    public static final C06221 sProxy;
    public static boolean sTemporarilyIgnoreStrictMode;
    public static final HashSet<String> sWhitelistedFrameworkClasses;

    public static void whitelistIpcs(Runnable runnable) {
        if (!STRICT_MODE_ENABLED || sTemporarilyIgnoreStrictMode) {
            runnable.run();
            return;
        }
        Object obj = sLock;
        synchronized (obj) {
            sTemporarilyIgnoreStrictMode = true;
        }
        try {
            runnable.run();
            synchronized (obj) {
                sTemporarilyIgnoreStrictMode = false;
            }
        } catch (Throwable th) {
            synchronized (sLock) {
                sTemporarilyIgnoreStrictMode = false;
                throw th;
            }
        }
    }

    static {
        boolean z = false;
        if (Build.IS_ENG || SystemProperties.getBoolean("persist.sysui.strictmode", false)) {
            z = true;
        }
        STRICT_MODE_ENABLED = z;
        HashSet<String> hashSet = new HashSet<>();
        sWhitelistedFrameworkClasses = hashSet;
        C06221 r2 = new Binder.ProxyTransactListener() {
            public final void onTransactEnded(Object obj) {
            }

            public final Object onTransactStarted(IBinder iBinder, int i) {
                return null;
            }

            /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
                r3 = r3.getInterfaceDescriptor();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:21:0x0034, code lost:
                monitor-enter(r2);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:24:0x003b, code lost:
                if (com.android.systemui.DejankUtils.sWhitelistedFrameworkClasses.contains(r3) == false) goto L_0x003f;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:25:0x003d, code lost:
                monitor-exit(r2);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:26:0x003e, code lost:
                return null;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:27:0x003f, code lost:
                monitor-exit(r2);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:33:0x0044, code lost:
                r2 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:34:0x0045, code lost:
                r2.printStackTrace();
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object onTransactStarted(android.os.IBinder r3, int r4, int r5) {
                /*
                    r2 = this;
                    java.lang.Object r2 = com.android.systemui.DejankUtils.sLock
                    monitor-enter(r2)
                    r4 = 1
                    r5 = r5 & r4
                    r0 = 0
                    if (r5 == r4) goto L_0x0061
                    java.util.Stack<java.lang.String> r5 = com.android.systemui.DejankUtils.sBlockingIpcs     // Catch:{ all -> 0x0063 }
                    boolean r5 = r5.empty()     // Catch:{ all -> 0x0063 }
                    if (r5 != 0) goto L_0x0061
                    java.lang.Thread r5 = androidx.recyclerview.R$dimen.sMainThread     // Catch:{ all -> 0x0063 }
                    if (r5 != 0) goto L_0x001e
                    android.os.Looper r5 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x0063 }
                    java.lang.Thread r5 = r5.getThread()     // Catch:{ all -> 0x0063 }
                    androidx.recyclerview.R$dimen.sMainThread = r5     // Catch:{ all -> 0x0063 }
                L_0x001e:
                    java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0063 }
                    java.lang.Thread r1 = androidx.recyclerview.R$dimen.sMainThread     // Catch:{ all -> 0x0063 }
                    if (r5 != r1) goto L_0x0027
                    goto L_0x0028
                L_0x0027:
                    r4 = 0
                L_0x0028:
                    if (r4 == 0) goto L_0x0061
                    boolean r4 = com.android.systemui.DejankUtils.sTemporarilyIgnoreStrictMode     // Catch:{ all -> 0x0063 }
                    if (r4 == 0) goto L_0x002f
                    goto L_0x0061
                L_0x002f:
                    monitor-exit(r2)     // Catch:{ all -> 0x0063 }
                    java.lang.String r3 = r3.getInterfaceDescriptor()     // Catch:{ RemoteException -> 0x0044 }
                    monitor-enter(r2)     // Catch:{ RemoteException -> 0x0044 }
                    java.util.HashSet<java.lang.String> r4 = com.android.systemui.DejankUtils.sWhitelistedFrameworkClasses     // Catch:{ all -> 0x0041 }
                    boolean r3 = r4.contains(r3)     // Catch:{ all -> 0x0041 }
                    if (r3 == 0) goto L_0x003f
                    monitor-exit(r2)     // Catch:{ all -> 0x0041 }
                    return r0
                L_0x003f:
                    monitor-exit(r2)     // Catch:{ all -> 0x0041 }
                    goto L_0x0048
                L_0x0041:
                    r3 = move-exception
                    monitor-exit(r2)     // Catch:{ all -> 0x0041 }
                    throw r3     // Catch:{ RemoteException -> 0x0044 }
                L_0x0044:
                    r2 = move-exception
                    r2.printStackTrace()
                L_0x0048:
                    java.lang.String r2 = "IPC detected on critical path: "
                    java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
                    java.util.Stack<java.lang.String> r3 = com.android.systemui.DejankUtils.sBlockingIpcs
                    java.lang.Object r3 = r3.peek()
                    java.lang.String r3 = (java.lang.String) r3
                    r2.append(r3)
                    java.lang.String r2 = r2.toString()
                    android.os.StrictMode.noteSlowCall(r2)
                    return r0
                L_0x0061:
                    monitor-exit(r2)     // Catch:{ all -> 0x0063 }
                    return r0
                L_0x0063:
                    r3 = move-exception
                    monitor-exit(r2)     // Catch:{ all -> 0x0063 }
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.DejankUtils.C06221.onTransactStarted(android.os.IBinder, int, int):java.lang.Object");
            }
        };
        sProxy = r2;
        if (z) {
            hashSet.add("android.view.IWindowSession");
            hashSet.add("com.android.internal.policy.IKeyguardStateCallback");
            hashSet.add("android.os.IPowerManager");
            hashSet.add("com.android.internal.statusbar.IStatusBarService");
            Binder.setProxyTransactListener(r2);
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectCustomSlowCalls().penaltyFlashScreen().penaltyLog().build());
        }
    }

    public static void postAfterTraversal(Runnable runnable) {
        if (sImmediate) {
            runnable.run();
            return;
        }
        Assert.isMainThread();
        sPendingRunnables.add(runnable);
        sChoreographer.postCallback(1, sAnimationCallbackRunnable, (Object) null);
    }

    public static void startDetectingBlockingIpcs(String str) {
        if (STRICT_MODE_ENABLED) {
            synchronized (sLock) {
                sBlockingIpcs.push(str);
            }
        }
    }

    public static void stopDetectingBlockingIpcs(String str) {
        if (STRICT_MODE_ENABLED) {
            synchronized (sLock) {
                sBlockingIpcs.remove(str);
            }
        }
    }

    public static <T> T whitelistIpcs(Supplier<T> supplier) {
        if (!STRICT_MODE_ENABLED || sTemporarilyIgnoreStrictMode) {
            return supplier.get();
        }
        Object obj = sLock;
        synchronized (obj) {
            sTemporarilyIgnoreStrictMode = true;
        }
        try {
            T t = supplier.get();
            synchronized (obj) {
                sTemporarilyIgnoreStrictMode = false;
            }
            return t;
        } catch (Throwable th) {
            synchronized (sLock) {
                sTemporarilyIgnoreStrictMode = false;
                throw th;
            }
        }
    }

    @VisibleForTesting
    public static void setImmediate(boolean z) {
        sImmediate = z;
    }
}
