package com.android.systemui.dreams;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import com.android.internal.policy.PhoneWindow;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.p012wm.shell.bubbles.Bubble$$ExternalSyntheticLambda1;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.dagger.DreamOverlayComponent;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda21;
import java.util.Objects;
import java.util.concurrent.Executor;

public class DreamOverlayService extends android.service.dreams.DreamOverlayService {
    public static final boolean DEBUG = Log.isLoggable("DreamOverlayService", 3);
    public final Context mContext;
    public final DreamOverlayContainerViewController mDreamOverlayContainerViewController;
    public final Executor mExecutor;
    public final C07851 mHost;
    public final C07862 mKeyguardCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LifecycleRegistry mLifecycleRegistry;
    public DreamOverlayStateController mStateController;
    public ViewModelStore mViewModelStore = new ViewModelStore();
    public PhoneWindow mWindow;

    /* JADX WARNING: type inference failed for: r4v0, types: [com.android.systemui.dreams.DreamOverlayService, android.app.Service, java.lang.Object] */
    public final void onDestroy() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardCallback);
        this.mExecutor.execute(new Bubble$$ExternalSyntheticLambda1(this, Lifecycle.State.DESTROYED, 1));
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService(WindowManager.class);
        PhoneWindow phoneWindow = this.mWindow;
        if (phoneWindow != null) {
            windowManager.removeView(phoneWindow.getDecorView());
        }
        this.mStateController.setOverlayActive(false);
        DreamOverlayService.super.onDestroy();
    }

    public final void onStartDream(WindowManager.LayoutParams layoutParams) {
        this.mExecutor.execute(new Bubble$$ExternalSyntheticLambda1(this, Lifecycle.State.STARTED, 1));
        this.mExecutor.execute(new StatusBar$$ExternalSyntheticLambda21(this, layoutParams, 1));
    }

    public DreamOverlayService(Context context, Executor executor, DreamOverlayComponent.Factory factory, DreamOverlayStateController dreamOverlayStateController, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        C07851 r0 = new Complication.Host() {
        };
        this.mHost = r0;
        C07862 r1 = new KeyguardUpdateMonitorCallback() {
            public final void onShadeExpandedChanged(boolean z) {
                Lifecycle.State state = Lifecycle.State.STARTED;
                LifecycleRegistry lifecycleRegistry = DreamOverlayService.this.mLifecycleRegistry;
                Objects.requireNonNull(lifecycleRegistry);
                Lifecycle.State state2 = lifecycleRegistry.mState;
                Lifecycle.State state3 = Lifecycle.State.RESUMED;
                if (state2 != state3) {
                    LifecycleRegistry lifecycleRegistry2 = DreamOverlayService.this.mLifecycleRegistry;
                    Objects.requireNonNull(lifecycleRegistry2);
                    if (lifecycleRegistry2.mState != state) {
                        return;
                    }
                }
                LifecycleRegistry lifecycleRegistry3 = DreamOverlayService.this.mLifecycleRegistry;
                if (!z) {
                    state = state3;
                }
                lifecycleRegistry3.setCurrentState(state);
            }
        };
        this.mKeyguardCallback = r1;
        this.mContext = context;
        this.mExecutor = executor;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        keyguardUpdateMonitor.registerCallback(r1);
        this.mStateController = dreamOverlayStateController;
        DreamOverlayComponent create = factory.create(this.mViewModelStore, r0);
        this.mDreamOverlayContainerViewController = create.getDreamOverlayContainerViewController();
        executor.execute(new Bubble$$ExternalSyntheticLambda1(this, Lifecycle.State.CREATED, 1));
        this.mLifecycleRegistry = create.getLifecycleRegistry();
        DreamOverlayTouchMonitor dreamOverlayTouchMonitor = create.getDreamOverlayTouchMonitor();
        Objects.requireNonNull(dreamOverlayTouchMonitor);
        dreamOverlayTouchMonitor.mLifecycle.addObserver(dreamOverlayTouchMonitor.mLifecycleObserver);
    }
}
