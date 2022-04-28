package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.os.RemoteException;
import android.util.Slog;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda0;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CoreStartable f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda0(CoreStartable coreStartable, Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = coreStartable;
        this.f$1 = obj;
        this.f$2 = obj2;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ScreenDecorations screenDecorations = (ScreenDecorations) this.f$0;
                String str = (String) this.f$1;
                String str2 = (String) this.f$2;
                boolean z = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                Objects.requireNonNull(screenDecorations);
                if (screenDecorations.mOverlays != null && "sysui_rounded_size".equals(str)) {
                    Point point = screenDecorations.mRoundedDefault;
                    Point point2 = screenDecorations.mRoundedDefaultTop;
                    Point point3 = screenDecorations.mRoundedDefaultBottom;
                    if (str2 != null) {
                        try {
                            int parseInt = (int) (((float) Integer.parseInt(str2)) * screenDecorations.mDensity);
                            point = new Point(parseInt, parseInt);
                        } catch (Exception unused) {
                        }
                    }
                    screenDecorations.updateRoundedCornerSize(point, point2, point3);
                    return;
                }
                return;
            default:
                KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this.f$0;
                IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = (IRemoteAnimationFinishedCallback) this.f$1;
                RemoteAnimationTarget[] remoteAnimationTargetArr = (RemoteAnimationTarget[]) this.f$2;
                boolean z2 = KeyguardViewMediator.DEBUG;
                if (iRemoteAnimationFinishedCallback == null) {
                    keyguardViewMediator.mInteractionJankMonitor.end(29);
                    return;
                }
                Objects.requireNonNull(keyguardViewMediator);
                SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(keyguardViewMediator.mKeyguardViewControllerLazy.get().getViewRootImpl().getView());
                RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr[0];
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                ofFloat.setDuration(400);
                ofFloat.setInterpolator(Interpolators.LINEAR);
                ofFloat.addUpdateListener(new KeyguardViewMediator$$ExternalSyntheticLambda0(remoteAnimationTarget, syncRtSurfaceTransactionApplier));
                ofFloat.addListener(new AnimatorListenerAdapter(iRemoteAnimationFinishedCallback) {
                    public final /* synthetic */ IRemoteAnimationFinishedCallback val$finishedCallback;

                    {
                        this.val$finishedCallback = r2;
                    }

                    public final void onAnimationCancel(Animator animator) {
                        KeyguardViewMediator keyguardViewMediator;
                        try {
                            this.val$finishedCallback.onAnimationFinished();
                            keyguardViewMediator = KeyguardViewMediator.this;
                        } catch (RemoteException unused) {
                            Slog.e("KeyguardViewMediator", "RemoteException");
                            keyguardViewMediator = KeyguardViewMediator.this;
                        } catch (Throwable th) {
                            KeyguardViewMediator.this.mInteractionJankMonitor.cancel(29);
                            throw th;
                        }
                        keyguardViewMediator.mInteractionJankMonitor.cancel(29);
                    }

                    public final void onAnimationEnd(Animator animator) {
                        KeyguardViewMediator keyguardViewMediator;
                        try {
                            this.val$finishedCallback.onAnimationFinished();
                            keyguardViewMediator = KeyguardViewMediator.this;
                        } catch (RemoteException unused) {
                            Slog.e("KeyguardViewMediator", "RemoteException");
                            keyguardViewMediator = KeyguardViewMediator.this;
                        } catch (Throwable th) {
                            KeyguardViewMediator.this.mInteractionJankMonitor.end(29);
                            throw th;
                        }
                        keyguardViewMediator.mInteractionJankMonitor.end(29);
                    }
                });
                ofFloat.start();
                return;
        }
    }
}
