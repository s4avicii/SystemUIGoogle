package com.android.systemui.statusbar.notification.collection.provider;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import com.android.keyguard.KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.Assert;
import com.android.systemui.util.ListenerSet;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import kotlin.collections.EmptyList;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DebugModeFilterProvider.kt */
public final class DebugModeFilterProvider implements Dumpable {
    public List<String> allowedPackages = EmptyList.INSTANCE;
    public final Context context;
    public final ListenerSet<Runnable> listeners = new ListenerSet<>();
    public final DebugModeFilterProvider$mReceiver$1 mReceiver;

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        ListenerSet<Runnable> listenerSet = this.listeners;
        Objects.requireNonNull(listenerSet);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(!listenerSet.listeners.isEmpty(), "initialized: ", printWriter);
        printWriter.println(Intrinsics.stringPlus("allowedPackages: ", Integer.valueOf(this.allowedPackages.size())));
        int i = 0;
        for (T next : this.allowedPackages) {
            int i2 = i + 1;
            if (i >= 0) {
                printWriter.println("  [" + i + "]: " + ((String) next));
                i = i2;
            } else {
                SetsKt__SetsKt.throwIndexOverflow();
                throw null;
            }
        }
    }

    public DebugModeFilterProvider(Context context2, DumpManager dumpManager) {
        this.context = context2;
        dumpManager.registerDumpable(this);
        this.mReceiver = new DebugModeFilterProvider$mReceiver$1(this);
    }

    public final void registerInvalidationListener(Runnable runnable) {
        Assert.isMainThread();
        if (Build.isDebuggable()) {
            ListenerSet<Runnable> listenerSet = this.listeners;
            Objects.requireNonNull(listenerSet);
            boolean isEmpty = listenerSet.listeners.isEmpty();
            this.listeners.addIfAbsent(runnable);
            if (isEmpty) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.android.systemui.action.SET_NOTIF_DEBUG_MODE");
                this.context.registerReceiver(this.mReceiver, intentFilter, "com.android.systemui.permission.NOTIF_DEBUG_MODE", (Handler) null, 2);
                Log.d("DebugModeFilterProvider", Intrinsics.stringPlus("Registered: ", this.mReceiver));
            }
        }
    }
}
