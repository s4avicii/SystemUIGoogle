package com.android.p012wm.shell;

import com.android.p012wm.shell.ShellCommandHandlerImpl;
import com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda0;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda3;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda4;
import java.io.PrintWriter;
import java.util.Objects;

/* renamed from: com.android.wm.shell.ShellCommandHandlerImpl$HandlerImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellCommandHandlerImpl$HandlerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ShellCommandHandlerImpl.HandlerImpl f$0;
    public final /* synthetic */ PrintWriter f$1;

    public /* synthetic */ ShellCommandHandlerImpl$HandlerImpl$$ExternalSyntheticLambda0(ShellCommandHandlerImpl.HandlerImpl handlerImpl, PrintWriter printWriter) {
        this.f$0 = handlerImpl;
        this.f$1 = printWriter;
    }

    public final void run() {
        ShellCommandHandlerImpl.HandlerImpl handlerImpl = this.f$0;
        PrintWriter printWriter = this.f$1;
        Objects.requireNonNull(handlerImpl);
        ShellCommandHandlerImpl shellCommandHandlerImpl = ShellCommandHandlerImpl.this;
        Objects.requireNonNull(shellCommandHandlerImpl);
        ShellTaskOrganizer shellTaskOrganizer = shellCommandHandlerImpl.mShellTaskOrganizer;
        Objects.requireNonNull(shellTaskOrganizer);
        synchronized (shellTaskOrganizer.mLock) {
            printWriter.println("ShellTaskOrganizer");
            printWriter.println("  " + shellTaskOrganizer.mTaskListeners.size() + " Listeners");
            int size = shellTaskOrganizer.mTaskListeners.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                int keyAt = shellTaskOrganizer.mTaskListeners.keyAt(size);
                printWriter.println("  " + "#" + size + " " + ShellTaskOrganizer.taskListenerTypeToString(keyAt));
                shellTaskOrganizer.mTaskListeners.valueAt(size).dump(printWriter, "    ");
            }
            printWriter.println();
            printWriter.println("  " + shellTaskOrganizer.mTasks.size() + " Tasks");
            int size2 = shellTaskOrganizer.mTasks.size();
            while (true) {
                size2--;
                if (size2 < 0) {
                    break;
                }
                printWriter.println("  " + "#" + size2 + " task=" + shellTaskOrganizer.mTasks.keyAt(size2) + " listener=" + shellTaskOrganizer.getTaskListener(shellTaskOrganizer.mTasks.valueAt(size2).getTaskInfo(), false));
            }
            printWriter.println();
            printWriter.println("  " + shellTaskOrganizer.mLaunchCookieToListener.size() + " Launch Cookies");
            int size3 = shellTaskOrganizer.mLaunchCookieToListener.size();
            while (true) {
                size3--;
                if (size3 >= 0) {
                    printWriter.println("  " + "#" + size3 + " cookie=" + shellTaskOrganizer.mLaunchCookieToListener.keyAt(size3) + " listener=" + shellTaskOrganizer.mLaunchCookieToListener.valueAt(size3));
                }
            }
        }
        printWriter.println();
        printWriter.println();
        shellCommandHandlerImpl.mPipOptional.ifPresent(new CreateUserActivity$$ExternalSyntheticLambda4(printWriter, 3));
        shellCommandHandlerImpl.mLegacySplitScreenOptional.ifPresent(new ActivityStarterDelegate$$ExternalSyntheticLambda0(printWriter, 2));
        shellCommandHandlerImpl.mOneHandedOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda1(printWriter, 0));
        shellCommandHandlerImpl.mHideDisplayCutout.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda4(printWriter, 0));
        printWriter.println();
        printWriter.println();
        shellCommandHandlerImpl.mAppPairsOptional.ifPresent(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda3(printWriter, 1));
        printWriter.println();
        printWriter.println();
        shellCommandHandlerImpl.mSplitScreenOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda3(printWriter, 0));
        printWriter.println();
        printWriter.println();
        shellCommandHandlerImpl.mRecentTasks.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda9(printWriter));
    }
}
