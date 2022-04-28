package com.android.systemui.doze;

import android.content.Context;
import android.os.PowerManager;
import android.os.SystemClock;
import android.service.dreams.DreamService;
import android.util.Log;
import android.view.Display;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.dagger.DozeComponent;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.plugins.DozeServicePlugin;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.shared.plugins.PluginManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

public class DozeService extends DreamService implements DozeMachine.Service, DozeServicePlugin.RequestDoze, PluginListener<DozeServicePlugin> {
    public static final boolean DEBUG = Log.isLoggable("DozeService", 3);
    public final DozeComponent.Builder mDozeComponentBuilder;
    public DozeMachine mDozeMachine;
    public DozeServicePlugin mDozePlugin;
    public PluginManager mPluginManager;

    public final void onDestroy() {
        PluginManager pluginManager = this.mPluginManager;
        if (pluginManager != null) {
            pluginManager.removePluginListener(this);
        }
        super.onDestroy();
        DozeMachine dozeMachine = this.mDozeMachine;
        Objects.requireNonNull(dozeMachine);
        for (DozeMachine.Part destroy : dozeMachine.mParts) {
            destroy.destroy();
        }
        this.mDozeMachine = null;
    }

    public final void onPluginConnected(Plugin plugin, Context context) {
        DozeServicePlugin dozeServicePlugin = (DozeServicePlugin) plugin;
        this.mDozePlugin = dozeServicePlugin;
        dozeServicePlugin.setDozeRequester(this);
    }

    public final void onPluginDisconnected(Plugin plugin) {
        DozeServicePlugin dozeServicePlugin = (DozeServicePlugin) plugin;
        DozeServicePlugin dozeServicePlugin2 = this.mDozePlugin;
        if (dozeServicePlugin2 != null) {
            dozeServicePlugin2.onDreamingStopped();
            this.mDozePlugin = null;
        }
    }

    public final void onRequestHideDoze() {
        DozeMachine dozeMachine = this.mDozeMachine;
        if (dozeMachine != null) {
            dozeMachine.requestState(DozeMachine.State.DOZE);
        }
    }

    public final void onRequestShowDoze() {
        DozeMachine dozeMachine = this.mDozeMachine;
        if (dozeMachine != null) {
            dozeMachine.requestState(DozeMachine.State.DOZE_AOD);
        }
    }

    public final void requestWakeUp() {
        ((PowerManager) getSystemService(PowerManager.class)).wakeUp(SystemClock.uptimeMillis(), 4, "com.android.systemui:NODOZE");
    }

    public DozeService(DozeComponent.Builder builder, PluginManager pluginManager) {
        this.mDozeComponentBuilder = builder;
        setDebug(DEBUG);
        this.mPluginManager = pluginManager;
    }

    public final void dumpOnHandler(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dumpOnHandler(fileDescriptor, printWriter, strArr);
        DozeMachine dozeMachine = this.mDozeMachine;
        if (dozeMachine != null) {
            Objects.requireNonNull(dozeMachine);
            printWriter.print(" state=");
            printWriter.println(dozeMachine.mState);
            printWriter.print(" wakeLockHeldForCurrentState=");
            printWriter.println(dozeMachine.mWakeLockHeldForCurrentState);
            printWriter.print(" wakeLock=");
            printWriter.println(dozeMachine.mWakeLock);
            printWriter.print(" isDozeSuppressed=");
            printWriter.println(dozeMachine.mDozeHost.isDozeSuppressed());
            printWriter.println("Parts:");
            for (DozeMachine.Part dump : dozeMachine.mParts) {
                dump.dump(printWriter);
            }
        }
    }

    public final void onCreate() {
        super.onCreate();
        setWindowless(true);
        this.mPluginManager.addPluginListener(this, DozeServicePlugin.class, false);
        this.mDozeMachine = this.mDozeComponentBuilder.build(this).getDozeMachine();
    }

    public final void onDreamingStarted() {
        super.onDreamingStarted();
        this.mDozeMachine.requestState(DozeMachine.State.INITIALIZED);
        startDozing();
        DozeServicePlugin dozeServicePlugin = this.mDozePlugin;
        if (dozeServicePlugin != null) {
            dozeServicePlugin.onDreamingStarted();
        }
    }

    public final void onDreamingStopped() {
        super.onDreamingStopped();
        this.mDozeMachine.requestState(DozeMachine.State.FINISH);
        DozeServicePlugin dozeServicePlugin = this.mDozePlugin;
        if (dozeServicePlugin != null) {
            dozeServicePlugin.onDreamingStopped();
        }
    }

    public final void setDozeScreenState(int i) {
        super.setDozeScreenState(i);
        DozeMachine dozeMachine = this.mDozeMachine;
        Objects.requireNonNull(dozeMachine);
        DozeLog dozeLog = dozeMachine.mDozeLog;
        Objects.requireNonNull(dozeLog);
        DozeLogger dozeLogger = dozeLog.mLogger;
        Objects.requireNonNull(dozeLogger);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$logDisplayStateChanged$2 dozeLogger$logDisplayStateChanged$2 = DozeLogger$logDisplayStateChanged$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logDisplayStateChanged$2);
            obtain.str1 = Display.stateToString(i);
            logBuffer.push(obtain);
        }
        for (DozeMachine.Part onScreenState : dozeMachine.mParts) {
            onScreenState.onScreenState(i);
        }
    }
}
