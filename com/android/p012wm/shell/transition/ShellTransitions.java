package com.android.p012wm.shell.transition;

import android.window.RemoteTransition;
import android.window.TransitionFilter;

/* renamed from: com.android.wm.shell.transition.ShellTransitions */
public interface ShellTransitions {
    IShellTransitions createExternalInterface() {
        return null;
    }

    void registerRemote(TransitionFilter transitionFilter, RemoteTransition remoteTransition) {
    }
}
