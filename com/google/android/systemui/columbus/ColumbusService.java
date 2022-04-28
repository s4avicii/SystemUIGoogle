package com.google.android.systemui.columbus;

import android.os.PowerManager;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.google.android.systemui.columbus.PowerManagerWrapper;
import com.google.android.systemui.columbus.actions.Action;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.gates.Gate;
import com.google.android.systemui.columbus.sensors.GestureController;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ColumbusService.kt */
public final class ColumbusService implements Dumpable {
    public final ColumbusService$actionListener$1 actionListener;
    public final List<Action> actions;
    public final Set<FeedbackEffect> effects;
    public final ColumbusService$gateListener$1 gateListener;
    public final Set<Gate> gates;
    public final GestureController gestureController;
    public final ColumbusService$gestureListener$1 gestureListener;
    public Action lastActiveAction;
    public final PowerManagerWrapper.WakeLockWrapper wakeLock;

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        String str2;
        printWriter.println(Intrinsics.stringPlus("ColumbusService", " state:"));
        printWriter.println("  Gates:");
        Iterator<T> it = this.gates.iterator();
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
        for (Action action : this.actions) {
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
        printWriter.println(Intrinsics.stringPlus("  Active: ", this.lastActiveAction));
        printWriter.println("  Feedback Effects:");
        for (FeedbackEffect obj : this.effects) {
            printWriter.print("    ");
            printWriter.println(obj.toString());
        }
        GestureController gestureController2 = this.gestureController;
        Objects.requireNonNull(gestureController2);
        printWriter.println(Intrinsics.stringPlus("  Soft Blocks: ", Long.valueOf(gestureController2.softGateBlockCount)));
        printWriter.println(Intrinsics.stringPlus("  Gesture Sensor: ", gestureController2.gestureSensor));
        GestureSensor gestureSensor = gestureController2.gestureSensor;
        if (gestureSensor instanceof Dumpable) {
            ((Dumpable) gestureSensor).dump(fileDescriptor, printWriter, strArr);
        }
    }

    public final void stopListening() {
        boolean z;
        GestureController gestureController2 = this.gestureController;
        Objects.requireNonNull(gestureController2);
        if (gestureController2.gestureSensor.isListening()) {
            gestureController2.gestureSensor.stopListening();
            for (Gate unregisterListener : gestureController2.softGates) {
                unregisterListener.unregisterListener(gestureController2.softGateListener);
            }
            z = true;
        } else {
            z = false;
        }
        if (z) {
            for (FeedbackEffect onGestureDetected : this.effects) {
                onGestureDetected.onGestureDetected(0, (GestureSensor.DetectionProperties) null);
            }
            Action updateActiveAction = updateActiveAction();
            if (updateActiveAction != null) {
                updateActiveAction.onGestureDetected(0, (GestureSensor.DetectionProperties) null);
            }
        }
    }

    public final Action updateActiveAction() {
        T t;
        Iterator<T> it = this.actions.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            Action action = (Action) t;
            Objects.requireNonNull(action);
            if (action.isAvailable) {
                break;
            }
        }
        Action action2 = (Action) t;
        Action action3 = this.lastActiveAction;
        if (!(action3 == null || action2 == action3)) {
            Log.i("Columbus/Service", "Switching action from " + action3 + " to " + action2);
            action3.onGestureDetected(0, (GestureSensor.DetectionProperties) null);
        }
        this.lastActiveAction = action2;
        return action2;
    }

    public ColumbusService(List<Action> list, Set<FeedbackEffect> set, Set<Gate> set2, GestureController gestureController2, PowerManagerWrapper powerManagerWrapper) {
        PowerManager.WakeLock wakeLock2;
        this.actions = list;
        this.effects = set;
        this.gates = set2;
        this.gestureController = gestureController2;
        PowerManager powerManager = powerManagerWrapper.powerManager;
        if (powerManager == null) {
            wakeLock2 = null;
        } else {
            wakeLock2 = powerManager.newWakeLock(1, "Columbus/Service");
        }
        this.wakeLock = new PowerManagerWrapper.WakeLockWrapper(wakeLock2);
        this.actionListener = new ColumbusService$actionListener$1(this);
        this.gateListener = new ColumbusService$gateListener$1(this);
        this.gestureListener = new ColumbusService$gestureListener$1(this);
        for (Action action : list) {
            ColumbusService$actionListener$1 columbusService$actionListener$1 = this.actionListener;
            Objects.requireNonNull(action);
            action.listeners.add(columbusService$actionListener$1);
        }
        GestureController gestureController3 = this.gestureController;
        ColumbusService$gestureListener$1 columbusService$gestureListener$1 = this.gestureListener;
        Objects.requireNonNull(gestureController3);
        gestureController3.gestureListener = columbusService$gestureListener$1;
        updateSensorListener();
    }

    public final void updateSensorListener() {
        T t;
        Action updateActiveAction = updateActiveAction();
        if (updateActiveAction == null) {
            Log.i("Columbus/Service", "No available actions");
            for (Gate unregisterListener : this.gates) {
                unregisterListener.unregisterListener(this.gateListener);
            }
            stopListening();
            return;
        }
        for (Gate registerListener : this.gates) {
            registerListener.registerListener(this.gateListener);
        }
        Iterator<T> it = this.gates.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            if (((Gate) t).isBlocking()) {
                break;
            }
        }
        Gate gate = (Gate) t;
        if (gate != null) {
            Log.i("Columbus/Service", Intrinsics.stringPlus("Gated by ", gate));
            stopListening();
            return;
        }
        Log.i("Columbus/Service", Intrinsics.stringPlus("Unblocked; current action: ", updateActiveAction));
        GestureController gestureController2 = this.gestureController;
        Objects.requireNonNull(gestureController2);
        if (!gestureController2.gestureSensor.isListening()) {
            for (Gate registerListener2 : gestureController2.softGates) {
                registerListener2.registerListener(gestureController2.softGateListener);
            }
            gestureController2.gestureSensor.startListening();
        }
    }
}
