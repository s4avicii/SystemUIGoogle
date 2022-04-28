package com.google.android.systemui.columbus;

import com.android.systemui.Dumpable;
import com.google.android.systemui.columbus.actions.Action;
import com.google.android.systemui.columbus.actions.SettingsAction;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.gates.Gate;
import com.google.android.systemui.columbus.sensors.GestureController;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ColumbusServiceWrapper.kt */
public final class ColumbusServiceWrapper implements Dumpable {
    public final Lazy<ColumbusService> columbusService;
    public final ColumbusSettings columbusSettings;
    public final Lazy<ColumbusStructuredDataManager> columbusStructuredDataManager;
    public final Lazy<SettingsAction> settingsAction;
    public final ColumbusServiceWrapper$settingsChangeListener$1 settingsChangeListener;
    public boolean started;

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        String str2;
        if (this.started) {
            ColumbusService columbusService2 = this.columbusService.get();
            Objects.requireNonNull(columbusService2);
            printWriter.println(Intrinsics.stringPlus("ColumbusService", " state:"));
            printWriter.println("  Gates:");
            Iterator<T> it = columbusService2.gates.iterator();
            while (true) {
                str = "X ";
                if (!it.hasNext()) {
                    break;
                }
                Gate gate = (Gate) it.next();
                printWriter.print("    ");
                Objects.requireNonNull(gate);
                if (gate.active) {
                    if (!gate.isBlocking()) {
                        str = "O ";
                    }
                    printWriter.print(str);
                } else {
                    printWriter.print("- ");
                }
                printWriter.println(gate.toString());
            }
            printWriter.println("  Actions:");
            for (Action action : columbusService2.actions) {
                printWriter.print("    ");
                Objects.requireNonNull(action);
                if (action.isAvailable) {
                    str2 = "O ";
                } else {
                    str2 = str;
                }
                printWriter.print(str2);
                printWriter.println(action.toString());
            }
            printWriter.println(Intrinsics.stringPlus("  Active: ", columbusService2.lastActiveAction));
            printWriter.println("  Feedback Effects:");
            for (FeedbackEffect obj : columbusService2.effects) {
                printWriter.print("    ");
                printWriter.println(obj.toString());
            }
            GestureController gestureController = columbusService2.gestureController;
            Objects.requireNonNull(gestureController);
            printWriter.println(Intrinsics.stringPlus("  Soft Blocks: ", Long.valueOf(gestureController.softGateBlockCount)));
            printWriter.println(Intrinsics.stringPlus("  Gesture Sensor: ", gestureController.gestureSensor));
            GestureSensor gestureSensor = gestureController.gestureSensor;
            if (gestureSensor instanceof Dumpable) {
                ((Dumpable) gestureSensor).dump(fileDescriptor, printWriter, strArr);
            }
        }
    }

    public ColumbusServiceWrapper(ColumbusSettings columbusSettings2, Lazy<ColumbusService> lazy, Lazy<SettingsAction> lazy2, Lazy<ColumbusStructuredDataManager> lazy3) {
        this.columbusSettings = columbusSettings2;
        this.columbusService = lazy;
        this.settingsAction = lazy2;
        this.columbusStructuredDataManager = lazy3;
        ColumbusServiceWrapper$settingsChangeListener$1 columbusServiceWrapper$settingsChangeListener$1 = new ColumbusServiceWrapper$settingsChangeListener$1(this);
        this.settingsChangeListener = columbusServiceWrapper$settingsChangeListener$1;
        if (columbusSettings2.isColumbusEnabled()) {
            Objects.requireNonNull(columbusSettings2);
            columbusSettings2.listeners.remove(columbusServiceWrapper$settingsChangeListener$1);
            this.started = true;
            lazy.get();
        } else {
            columbusSettings2.registerColumbusSettingsChangeListener(columbusServiceWrapper$settingsChangeListener$1);
            lazy2.get();
        }
        lazy3.get();
    }
}
