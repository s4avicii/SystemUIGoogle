package com.android.systemui;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Slog;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.os.BinderInternal;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.dump.LogBufferFreezer;
import com.android.systemui.dump.LogBufferFreezer$attach$1;
import com.android.systemui.dump.SystemUIAuxiliaryDumpService;
import com.android.systemui.statusbar.policy.BatteryStateNotifier;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

public class SystemUIService extends Service {
    public final BatteryStateNotifier mBatteryStateNotifier;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final DumpHandler mDumpHandler;
    public final LogBufferFreezer mLogBufferFreezer;
    public final Handler mMainHandler;

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (strArr.length == 0) {
            strArr = new String[]{"--dump-priority", "CRITICAL"};
        }
        this.mDumpHandler.dump(fileDescriptor, printWriter, strArr);
    }

    public final IBinder onBind(Intent intent) {
        return null;
    }

    public SystemUIService(Handler handler, DumpHandler dumpHandler, BroadcastDispatcher broadcastDispatcher, LogBufferFreezer logBufferFreezer, BatteryStateNotifier batteryStateNotifier) {
        this.mMainHandler = handler;
        this.mDumpHandler = dumpHandler;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mLogBufferFreezer = logBufferFreezer;
        this.mBatteryStateNotifier = batteryStateNotifier;
    }

    public final void onCreate() {
        super.onCreate();
        ((SystemUIApplication) getApplication()).startServicesIfNeeded();
        LogBufferFreezer logBufferFreezer = this.mLogBufferFreezer;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        Objects.requireNonNull(logBufferFreezer);
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, new LogBufferFreezer$attach$1(logBufferFreezer), new IntentFilter("com.android.internal.intent.action.BUGREPORT_STARTED"), logBufferFreezer.executor, UserHandle.ALL, 16);
        if (getResources().getBoolean(C1777R.bool.config_showNotificationForUnknownBatteryState)) {
            BatteryStateNotifier batteryStateNotifier = this.mBatteryStateNotifier;
            Objects.requireNonNull(batteryStateNotifier);
            batteryStateNotifier.controller.addCallback(batteryStateNotifier);
        }
        if (!Build.IS_DEBUGGABLE || !SystemProperties.getBoolean("debug.crash_sysui", false)) {
            if (Build.IS_DEBUGGABLE) {
                BinderInternal.nSetBinderProxyCountEnabled(true);
                BinderInternal.nSetBinderProxyCountWatermarks(1000, 900);
                BinderInternal.setBinderProxyCountCallback(new BinderInternal.BinderProxyLimitListener() {
                    public final void onLimitReached(int i) {
                        StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("uid ", i, " sent too many Binder proxies to uid ");
                        m.append(Process.myUid());
                        Slog.w("SystemUIService", m.toString());
                    }
                }, this.mMainHandler);
            }
            startServiceAsUser(new Intent(getApplicationContext(), SystemUIAuxiliaryDumpService.class), UserHandle.SYSTEM);
            return;
        }
        throw new RuntimeException();
    }
}
