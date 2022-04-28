package com.android.systemui.statusbar.lockscreen;

import android.app.smartspace.SmartspaceSession;
import android.util.Log;
import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.Objects;
import kotlin.collections.EmptyList;

/* compiled from: LockscreenSmartspaceController.kt */
public final class LockscreenSmartspaceController$stateChangeListener$1 implements View.OnAttachStateChangeListener {
    public final /* synthetic */ LockscreenSmartspaceController this$0;

    public LockscreenSmartspaceController$stateChangeListener$1(LockscreenSmartspaceController lockscreenSmartspaceController) {
        this.this$0 = lockscreenSmartspaceController;
    }

    public final void onViewAttachedToWindow(View view) {
        this.this$0.smartspaceViews.add((BcSmartspaceDataPlugin.SmartspaceView) view);
        this.this$0.connectSession();
        LockscreenSmartspaceController.access$updateTextColorFromWallpaper(this.this$0);
        LockscreenSmartspaceController lockscreenSmartspaceController = this.this$0;
        lockscreenSmartspaceController.statusBarStateListener.onDozeAmountChanged(0.0f, lockscreenSmartspaceController.statusBarStateController.getDozeAmount());
    }

    public final void onViewDetachedFromWindow(View view) {
        this.this$0.smartspaceViews.remove((BcSmartspaceDataPlugin.SmartspaceView) view);
        if (this.this$0.smartspaceViews.isEmpty()) {
            LockscreenSmartspaceController lockscreenSmartspaceController = this.this$0;
            Objects.requireNonNull(lockscreenSmartspaceController);
            if (lockscreenSmartspaceController.smartspaceViews.isEmpty()) {
                lockscreenSmartspaceController.execution.assertIsMainThread();
                SmartspaceSession smartspaceSession = lockscreenSmartspaceController.session;
                if (smartspaceSession != null) {
                    smartspaceSession.removeOnTargetsAvailableListener(lockscreenSmartspaceController.sessionListener);
                    smartspaceSession.close();
                    lockscreenSmartspaceController.userTracker.removeCallback(lockscreenSmartspaceController.userTrackerCallback);
                    lockscreenSmartspaceController.contentResolver.unregisterContentObserver(lockscreenSmartspaceController.settingsObserver);
                    lockscreenSmartspaceController.configurationController.removeCallback(lockscreenSmartspaceController.configChangeListener);
                    lockscreenSmartspaceController.statusBarStateController.removeCallback(lockscreenSmartspaceController.statusBarStateListener);
                    lockscreenSmartspaceController.session = null;
                    BcSmartspaceDataPlugin bcSmartspaceDataPlugin = lockscreenSmartspaceController.plugin;
                    if (bcSmartspaceDataPlugin != null) {
                        bcSmartspaceDataPlugin.registerSmartspaceEventNotifier((BcSmartspaceDataPlugin.SmartspaceEventNotifier) null);
                    }
                    BcSmartspaceDataPlugin bcSmartspaceDataPlugin2 = lockscreenSmartspaceController.plugin;
                    if (bcSmartspaceDataPlugin2 != null) {
                        bcSmartspaceDataPlugin2.onTargetsAvailable(EmptyList.INSTANCE);
                    }
                    Log.d("LockscreenSmartspaceController", "Ending smartspace session for lockscreen");
                }
            }
        }
    }
}
