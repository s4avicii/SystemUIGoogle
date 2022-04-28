package com.android.p012wm.shell.transition;

import android.app.ActivityTaskManager;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda5;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda19;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import com.android.p012wm.shell.transition.Transitions;
import java.util.ArrayList;

/* renamed from: com.android.wm.shell.transition.RemoteTransitionHandler */
public final class RemoteTransitionHandler implements Transitions.TransitionHandler {
    public final ArrayMap<IBinder, RemoteDeathHandler> mDeathHandlers = new ArrayMap<>();
    public final ArrayList<Pair<TransitionFilter, RemoteTransition>> mFilters = new ArrayList<>();
    public final ShellExecutor mMainExecutor;
    public final ArrayMap<IBinder, RemoteTransition> mRequestedRemotes = new ArrayMap<>();

    /* renamed from: com.android.wm.shell.transition.RemoteTransitionHandler$RemoteDeathHandler */
    public class RemoteDeathHandler implements IBinder.DeathRecipient {
        public final ArrayList<Transitions.TransitionFinishCallback> mPendingFinishCallbacks = new ArrayList<>();
        public final IBinder mRemote;
        public int mUsers = 0;

        public RemoteDeathHandler(IBinder iBinder) {
            this.mRemote = iBinder;
        }

        public final void binderDied() {
            RemoteTransitionHandler.this.mMainExecutor.execute(new TaskView$$ExternalSyntheticLambda5(this, 7));
        }
    }

    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        IBinder iBinder2 = iBinder;
        TransitionInfo transitionInfo2 = transitionInfo;
        Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
        RemoteTransition remoteTransition = this.mRequestedRemotes.get(iBinder);
        if (remoteTransition == null) {
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1269886472, 0, "Transition %s doesn't have explicit remote, search filters for match for %s", String.valueOf(iBinder), String.valueOf(transitionInfo));
            }
            int size = this.mFilters.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 990371881, 0, " Checking filter %s", String.valueOf(this.mFilters.get(size)));
                }
                if (((TransitionFilter) this.mFilters.get(size).first).matches(transitionInfo2)) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Found filter");
                    m.append(this.mFilters.get(size));
                    Slog.d("RemoteTransitionHandler", m.toString());
                    remoteTransition = (RemoteTransition) this.mFilters.get(size).second;
                    this.mRequestedRemotes.put(iBinder, remoteTransition);
                    break;
                }
                size--;
            }
        }
        RemoteTransition remoteTransition2 = remoteTransition;
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1671119352, 0, " Delegate animation for %s to %s", String.valueOf(iBinder), String.valueOf(remoteTransition2));
        }
        if (remoteTransition2 == null) {
            return false;
        }
        final Transitions$$ExternalSyntheticLambda1 transitions$$ExternalSyntheticLambda1 = (Transitions$$ExternalSyntheticLambda1) transitionFinishCallback2;
        final RemoteTransition remoteTransition3 = remoteTransition2;
        final SurfaceControl.Transaction transaction3 = transaction2;
        final IBinder iBinder3 = iBinder;
        C19411 r1 = new IRemoteTransitionFinishedCallback.Stub() {
            public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
                RemoteTransitionHandler.this.unhandleDeath(remoteTransition3.asBinder(), transitions$$ExternalSyntheticLambda1);
                RemoteTransitionHandler.this.mMainExecutor.execute(new RemoteTransitionHandler$1$$ExternalSyntheticLambda0(this, transaction, transaction3, iBinder3, transitions$$ExternalSyntheticLambda1, windowContainerTransaction));
            }
        };
        try {
            handleDeath(remoteTransition2.asBinder(), (Transitions$$ExternalSyntheticLambda1) transitionFinishCallback2);
            try {
                ActivityTaskManager.getService().setRunningRemoteTransitionDelegate(remoteTransition2.getAppThread());
            } catch (SecurityException unused) {
                Log.e("ShellTransitions", "Unable to boost animation thread. This should only happen during unit tests");
            }
            remoteTransition2.getRemoteTransition().startAnimation(iBinder, transitionInfo2, transaction, r1);
        } catch (RemoteException e) {
            Log.e("ShellTransitions", "Error running remote transition.", e);
            unhandleDeath(remoteTransition2.asBinder(), transitionFinishCallback2);
            this.mRequestedRemotes.remove(iBinder);
            this.mMainExecutor.execute(new BubbleStackView$$ExternalSyntheticLambda19(transitionFinishCallback2, 7));
        }
        return true;
    }

    public final void handleDeath(IBinder iBinder, Transitions$$ExternalSyntheticLambda1 transitions$$ExternalSyntheticLambda1) {
        synchronized (this.mDeathHandlers) {
            RemoteDeathHandler remoteDeathHandler = this.mDeathHandlers.get(iBinder);
            if (remoteDeathHandler == null) {
                remoteDeathHandler = new RemoteDeathHandler(iBinder);
                try {
                    iBinder.linkToDeath(remoteDeathHandler, 0);
                    this.mDeathHandlers.put(iBinder, remoteDeathHandler);
                } catch (RemoteException unused) {
                    Slog.e("RemoteTransitionHandler", "Failed to link to death");
                    return;
                }
            }
            if (transitions$$ExternalSyntheticLambda1 != null) {
                remoteDeathHandler.mPendingFinishCallbacks.add(transitions$$ExternalSyntheticLambda1);
            }
            remoteDeathHandler.mUsers++;
        }
    }

    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final IBinder iBinder2, final Transitions$$ExternalSyntheticLambda0 transitions$$ExternalSyntheticLambda0) {
        IRemoteTransition remoteTransition = this.mRequestedRemotes.get(iBinder2).getRemoteTransition();
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            String valueOf = String.valueOf(iBinder);
            String valueOf2 = String.valueOf(remoteTransition);
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -114556030, 0, " Attempt merge %s into %s", valueOf, valueOf2);
        }
        if (remoteTransition != null) {
            try {
                remoteTransition.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, new IRemoteTransitionFinishedCallback.Stub() {
                    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
                        RemoteTransitionHandler.this.mMainExecutor.execute(new RemoteTransitionHandler$2$$ExternalSyntheticLambda0(this, iBinder2, transitions$$ExternalSyntheticLambda0, windowContainerTransaction));
                    }
                });
            } catch (RemoteException e) {
                Log.e("ShellTransitions", "Error attempting to merge remote transition.", e);
            }
        }
    }

    public final void onTransitionMerged(IBinder iBinder) {
        this.mRequestedRemotes.remove(iBinder);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void unhandleDeath(android.os.IBinder r4, com.android.p012wm.shell.transition.Transitions.TransitionFinishCallback r5) {
        /*
            r3 = this;
            android.util.ArrayMap<android.os.IBinder, com.android.wm.shell.transition.RemoteTransitionHandler$RemoteDeathHandler> r0 = r3.mDeathHandlers
            monitor-enter(r0)
            android.util.ArrayMap<android.os.IBinder, com.android.wm.shell.transition.RemoteTransitionHandler$RemoteDeathHandler> r1 = r3.mDeathHandlers     // Catch:{ all -> 0x003a }
            java.lang.Object r1 = r1.get(r4)     // Catch:{ all -> 0x003a }
            com.android.wm.shell.transition.RemoteTransitionHandler$RemoteDeathHandler r1 = (com.android.p012wm.shell.transition.RemoteTransitionHandler.RemoteDeathHandler) r1     // Catch:{ all -> 0x003a }
            if (r1 != 0) goto L_0x000f
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            return
        L_0x000f:
            if (r5 == 0) goto L_0x0016
            java.util.ArrayList<com.android.wm.shell.transition.Transitions$TransitionFinishCallback> r2 = r1.mPendingFinishCallbacks     // Catch:{ all -> 0x003a }
            r2.remove(r5)     // Catch:{ all -> 0x003a }
        L_0x0016:
            int r5 = r1.mUsers     // Catch:{ all -> 0x003a }
            int r5 = r5 + -1
            r1.mUsers = r5     // Catch:{ all -> 0x003a }
            if (r5 != 0) goto L_0x0038
            java.util.ArrayList<com.android.wm.shell.transition.Transitions$TransitionFinishCallback> r5 = r1.mPendingFinishCallbacks     // Catch:{ all -> 0x003a }
            boolean r5 = r5.isEmpty()     // Catch:{ all -> 0x003a }
            if (r5 == 0) goto L_0x0030
            r5 = 0
            r4.unlinkToDeath(r1, r5)     // Catch:{ all -> 0x003a }
            android.util.ArrayMap<android.os.IBinder, com.android.wm.shell.transition.RemoteTransitionHandler$RemoteDeathHandler> r3 = r3.mDeathHandlers     // Catch:{ all -> 0x003a }
            r3.remove(r4)     // Catch:{ all -> 0x003a }
            goto L_0x0038
        L_0x0030:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch:{ all -> 0x003a }
            java.lang.String r4 = "Unhandling death for binder that still has pending finishCallback(s)."
            r3.<init>(r4)     // Catch:{ all -> 0x003a }
            throw r3     // Catch:{ all -> 0x003a }
        L_0x0038:
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            return
        L_0x003a:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.transition.RemoteTransitionHandler.unhandleDeath(android.os.IBinder, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):void");
    }

    public RemoteTransitionHandler(ShellExecutor shellExecutor) {
        this.mMainExecutor = shellExecutor;
    }

    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        RemoteTransition remoteTransition = transitionRequestInfo.getRemoteTransition();
        if (remoteTransition == null) {
            return null;
        }
        this.mRequestedRemotes.put(iBinder, remoteTransition);
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            String valueOf = String.valueOf(iBinder);
            String valueOf2 = String.valueOf(remoteTransition);
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 214412327, 0, "RemoteTransition directly requested for %s: %s", valueOf, valueOf2);
        }
        return new WindowContainerTransaction();
    }
}
