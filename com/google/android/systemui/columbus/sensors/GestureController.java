package com.google.android.systemui.columbus.sensors;

import android.util.SparseLongArray;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.google.android.systemui.columbus.gates.Gate;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GestureController.kt */
public final class GestureController implements Dumpable {
    public GestureListener gestureListener;
    public final GestureSensor gestureSensor;
    public final GestureController$gestureSensorListener$1 gestureSensorListener;
    public final SparseLongArray lastTimestampMap = new SparseLongArray();
    public long softGateBlockCount;
    public final GestureController$softGateListener$1 softGateListener;
    public final Set<Gate> softGates;
    public final UiEventLogger uiEventLogger;

    /* compiled from: GestureController.kt */
    public final class ColumbusCommand implements Command {
        public ColumbusCommand() {
        }

        public final void execute(PrintWriter printWriter, List<String> list) {
            if (list.isEmpty()) {
                printWriter.println("usage: quick-tap <command>");
                printWriter.println("Available commands:");
                printWriter.println("  trigger");
            } else if (Intrinsics.areEqual(list.get(0), "trigger")) {
                GestureController gestureController = GestureController.this;
                gestureController.gestureSensorListener.onGestureDetected(gestureController.gestureSensor, 1, (GestureSensor.DetectionProperties) null);
            } else {
                printWriter.println("usage: quick-tap <command>");
                printWriter.println("Available commands:");
                printWriter.println("  trigger");
            }
        }
    }

    /* compiled from: GestureController.kt */
    public interface GestureListener {
        void onGestureDetected(int i, GestureSensor.DetectionProperties detectionProperties);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(Intrinsics.stringPlus("  Soft Blocks: ", Long.valueOf(this.softGateBlockCount)));
        printWriter.println(Intrinsics.stringPlus("  Gesture Sensor: ", this.gestureSensor));
        GestureSensor gestureSensor2 = this.gestureSensor;
        if (gestureSensor2 instanceof Dumpable) {
            ((Dumpable) gestureSensor2).dump(fileDescriptor, printWriter, strArr);
        }
    }

    public GestureController(GestureSensor gestureSensor2, Set<Gate> set, CommandRegistry commandRegistry, UiEventLogger uiEventLogger2) {
        this.gestureSensor = gestureSensor2;
        this.softGates = set;
        this.uiEventLogger = uiEventLogger2;
        GestureController$gestureSensorListener$1 gestureController$gestureSensorListener$1 = new GestureController$gestureSensorListener$1(this);
        this.gestureSensorListener = gestureController$gestureSensorListener$1;
        this.softGateListener = new GestureController$softGateListener$1();
        Objects.requireNonNull(gestureSensor2);
        gestureSensor2.listener = gestureController$gestureSensorListener$1;
        commandRegistry.registerCommand("quick-tap", new Function0<Command>(this) {
            public final /* synthetic */ GestureController this$0;

            {
                this.this$0 = r1;
            }

            public final Object invoke() {
                return new ColumbusCommand();
            }
        });
    }
}
