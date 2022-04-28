package com.android.p012wm.shell.pip;

import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.view.IPinnedTaskListener;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda22;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.phone.NotificationIconAreaController$$ExternalSyntheticLambda0;
import java.util.ArrayList;

/* renamed from: com.android.wm.shell.pip.PinnedStackListenerForwarder */
public final class PinnedStackListenerForwarder {
    public final PinnedTaskListenerImpl mListenerImpl = new PinnedTaskListenerImpl();
    public final ArrayList<PinnedTaskListener> mListeners = new ArrayList<>();
    public final ShellExecutor mMainExecutor;

    /* renamed from: com.android.wm.shell.pip.PinnedStackListenerForwarder$PinnedTaskListener */
    public static class PinnedTaskListener {
        public void onActionsChanged(ParceledListSlice<RemoteAction> parceledListSlice) {
        }

        public void onActivityHidden(ComponentName componentName) {
        }

        public void onAspectRatioChanged(float f) {
        }

        public void onExpandedAspectRatioChanged(float f) {
        }

        public void onImeVisibilityChanged(boolean z, int i) {
        }

        public void onMovementBoundsChanged(boolean z) {
        }
    }

    /* renamed from: com.android.wm.shell.pip.PinnedStackListenerForwarder$PinnedTaskListenerImpl */
    public class PinnedTaskListenerImpl extends IPinnedTaskListener.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;

        public PinnedTaskListenerImpl() {
        }

        public final void onActionsChanged(ParceledListSlice<RemoteAction> parceledListSlice) {
            PinnedStackListenerForwarder.this.mMainExecutor.execute(new NotificationIconAreaController$$ExternalSyntheticLambda0(this, parceledListSlice, 3));
        }

        public final void onActivityHidden(ComponentName componentName) {
            PinnedStackListenerForwarder.this.mMainExecutor.execute(new BubbleStackView$$ExternalSyntheticLambda22(this, componentName, 3));
        }

        /* JADX WARNING: type inference failed for: r3v0, types: [android.os.Binder, com.android.wm.shell.pip.PinnedStackListenerForwarder$PinnedTaskListenerImpl] */
        public final void onAspectRatioChanged(float f) {
            PinnedStackListenerForwarder.this.mMainExecutor.execute(new OverviewProxyService$1$$ExternalSyntheticLambda1(this, f, 1));
        }

        public final void onExpandedAspectRatioChanged(float f) {
            PinnedStackListenerForwarder.this.mMainExecutor.execute(new C1882x2eacda56(this, f));
        }

        public final void onImeVisibilityChanged(boolean z, int i) {
            PinnedStackListenerForwarder.this.mMainExecutor.execute(new C1884x2eacda58(this, z, i));
        }

        public final void onMovementBoundsChanged(boolean z) {
            PinnedStackListenerForwarder.this.mMainExecutor.execute(new C1883x2eacda57(this, z));
        }
    }

    public PinnedStackListenerForwarder(ShellExecutor shellExecutor) {
        this.mMainExecutor = shellExecutor;
    }
}
