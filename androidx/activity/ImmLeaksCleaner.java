package androidx.activity;

import android.app.Activity;
import androidx.lifecycle.LifecycleEventObserver;
import java.lang.reflect.Field;

final class ImmLeaksCleaner implements LifecycleEventObserver {
    public static Field sHField;
    public static Field sNextServedViewField;
    public static int sReflectedFieldsInitialized;
    public static Field sServedViewField;
    public Activity mActivity;

    /* JADX WARNING: Can't wrap try/catch for region: R(3:32|33|34) */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x006e, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x006d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onStateChanged(androidx.lifecycle.LifecycleOwner r2, androidx.lifecycle.Lifecycle.Event r3) {
        /*
            r1 = this;
            androidx.lifecycle.Lifecycle$Event r2 = androidx.lifecycle.Lifecycle.Event.ON_DESTROY
            if (r3 == r2) goto L_0x0005
            return
        L_0x0005:
            int r2 = sReflectedFieldsInitialized
            r3 = 1
            if (r2 != 0) goto L_0x0036
            r2 = 2
            sReflectedFieldsInitialized = r2     // Catch:{ NoSuchFieldException -> 0x0036 }
            java.lang.Class<android.view.inputmethod.InputMethodManager> r2 = android.view.inputmethod.InputMethodManager.class
            java.lang.String r0 = "mServedView"
            java.lang.reflect.Field r2 = r2.getDeclaredField(r0)     // Catch:{ NoSuchFieldException -> 0x0036 }
            sServedViewField = r2     // Catch:{ NoSuchFieldException -> 0x0036 }
            r2.setAccessible(r3)     // Catch:{ NoSuchFieldException -> 0x0036 }
            java.lang.Class<android.view.inputmethod.InputMethodManager> r2 = android.view.inputmethod.InputMethodManager.class
            java.lang.String r0 = "mNextServedView"
            java.lang.reflect.Field r2 = r2.getDeclaredField(r0)     // Catch:{ NoSuchFieldException -> 0x0036 }
            sNextServedViewField = r2     // Catch:{ NoSuchFieldException -> 0x0036 }
            r2.setAccessible(r3)     // Catch:{ NoSuchFieldException -> 0x0036 }
            java.lang.Class<android.view.inputmethod.InputMethodManager> r2 = android.view.inputmethod.InputMethodManager.class
            java.lang.String r0 = "mH"
            java.lang.reflect.Field r2 = r2.getDeclaredField(r0)     // Catch:{ NoSuchFieldException -> 0x0036 }
            sHField = r2     // Catch:{ NoSuchFieldException -> 0x0036 }
            r2.setAccessible(r3)     // Catch:{ NoSuchFieldException -> 0x0036 }
            sReflectedFieldsInitialized = r3     // Catch:{ NoSuchFieldException -> 0x0036 }
        L_0x0036:
            int r2 = sReflectedFieldsInitialized
            if (r2 != r3) goto L_0x0077
            android.app.Activity r1 = r1.mActivity
            java.lang.String r2 = "input_method"
            java.lang.Object r1 = r1.getSystemService(r2)
            android.view.inputmethod.InputMethodManager r1 = (android.view.inputmethod.InputMethodManager) r1
            java.lang.reflect.Field r2 = sHField     // Catch:{ IllegalAccessException -> 0x0077 }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ IllegalAccessException -> 0x0077 }
            if (r2 != 0) goto L_0x004d
            return
        L_0x004d:
            monitor-enter(r2)
            java.lang.reflect.Field r3 = sServedViewField     // Catch:{ IllegalAccessException -> 0x0073, ClassCastException -> 0x0071 }
            java.lang.Object r3 = r3.get(r1)     // Catch:{ IllegalAccessException -> 0x0073, ClassCastException -> 0x0071 }
            android.view.View r3 = (android.view.View) r3     // Catch:{ IllegalAccessException -> 0x0073, ClassCastException -> 0x0071 }
            if (r3 != 0) goto L_0x005a
            monitor-exit(r2)     // Catch:{ all -> 0x006f }
            return
        L_0x005a:
            boolean r3 = r3.isAttachedToWindow()     // Catch:{ all -> 0x006f }
            if (r3 == 0) goto L_0x0062
            monitor-exit(r2)     // Catch:{ all -> 0x006f }
            return
        L_0x0062:
            java.lang.reflect.Field r3 = sNextServedViewField     // Catch:{ IllegalAccessException -> 0x006d }
            r0 = 0
            r3.set(r1, r0)     // Catch:{ IllegalAccessException -> 0x006d }
            monitor-exit(r2)     // Catch:{ all -> 0x006f }
            r1.isActive()
            goto L_0x0077
        L_0x006d:
            monitor-exit(r2)     // Catch:{ all -> 0x006f }
            return
        L_0x006f:
            r1 = move-exception
            goto L_0x0075
        L_0x0071:
            monitor-exit(r2)     // Catch:{ all -> 0x006f }
            return
        L_0x0073:
            monitor-exit(r2)     // Catch:{ all -> 0x006f }
            return
        L_0x0075:
            monitor-exit(r2)     // Catch:{ all -> 0x006f }
            throw r1
        L_0x0077:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.activity.ImmLeaksCleaner.onStateChanged(androidx.lifecycle.LifecycleOwner, androidx.lifecycle.Lifecycle$Event):void");
    }
}
