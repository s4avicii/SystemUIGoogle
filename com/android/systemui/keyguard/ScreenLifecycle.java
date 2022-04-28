package com.android.systemui.keyguard;

import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public final class ScreenLifecycle extends Lifecycle<Observer> implements Dumpable {
    public int mScreenState = 0;

    public interface Observer {
        void onScreenTurnedOff() {
        }

        void onScreenTurnedOn() {
        }

        void onScreenTurningOff() {
        }

        void onScreenTurningOn(Runnable runnable) {
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "ScreenLifecycle:", "  mScreenState=");
        m.append(this.mScreenState);
        printWriter.println(m.toString());
    }

    public ScreenLifecycle(DumpManager dumpManager) {
        dumpManager.registerDumpable("ScreenLifecycle", this);
    }
}
