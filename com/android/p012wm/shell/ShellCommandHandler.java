package com.android.p012wm.shell;

import java.io.PrintWriter;

/* renamed from: com.android.wm.shell.ShellCommandHandler */
public interface ShellCommandHandler {
    void dump(PrintWriter printWriter);

    boolean handleCommand(String[] strArr, PrintWriter printWriter);
}
