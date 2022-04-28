package com.android.p012wm.shell;

import android.os.RemoteException;
import android.view.WindowManagerGlobal;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.pip.PinnedStackListenerForwarder;
import java.util.Objects;

/* renamed from: com.android.wm.shell.WindowManagerShellWrapper */
public class WindowManagerShellWrapper {
    public final PinnedStackListenerForwarder mPinnedStackListenerForwarder;

    public final void addPinnedStackListener(PinnedStackListenerForwarder.PinnedTaskListener pinnedTaskListener) throws RemoteException {
        PinnedStackListenerForwarder pinnedStackListenerForwarder = this.mPinnedStackListenerForwarder;
        Objects.requireNonNull(pinnedStackListenerForwarder);
        pinnedStackListenerForwarder.mListeners.add(pinnedTaskListener);
        PinnedStackListenerForwarder pinnedStackListenerForwarder2 = this.mPinnedStackListenerForwarder;
        Objects.requireNonNull(pinnedStackListenerForwarder2);
        WindowManagerGlobal.getWindowManagerService().registerPinnedTaskListener(0, pinnedStackListenerForwarder2.mListenerImpl);
    }

    public WindowManagerShellWrapper(ShellExecutor shellExecutor) {
        this.mPinnedStackListenerForwarder = new PinnedStackListenerForwarder(shellExecutor);
    }
}
