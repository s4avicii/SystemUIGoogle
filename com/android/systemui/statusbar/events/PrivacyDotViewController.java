package com.android.systemui.statusbar.events;

import android.graphics.Rect;
import android.view.View;
import com.android.internal.annotations.GuardedBy;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.ScreenDecorations$2$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsChangedListener;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;

/* compiled from: PrivacyDotViewController.kt */
public final class PrivacyDotViewController {
    public final SystemStatusAnimationScheduler animationScheduler;

    /* renamed from: bl */
    public View f76bl;

    /* renamed from: br */
    public View f77br;
    public Runnable cancelRunnable;
    public final ConfigurationController configurationController;
    public final StatusBarContentInsetsProvider contentInsetsProvider;
    public ViewState currentViewState;
    public final Object lock = new Object();
    public final Executor mainExecutor;
    @GuardedBy({"lock"})
    public ViewState nextViewState;
    public ShowingListener showingListener;
    public final StatusBarStateController stateController;
    public final PrivacyDotViewController$systemStatusAnimationCallback$1 systemStatusAnimationCallback;

    /* renamed from: tl */
    public View f78tl;

    /* renamed from: tr */
    public View f79tr;
    public DelayableExecutor uiExecutor;

    /* compiled from: PrivacyDotViewController.kt */
    public interface ShowingListener {
    }

    public PrivacyDotViewController(Executor executor, StatusBarStateController statusBarStateController, ConfigurationController configurationController2, StatusBarContentInsetsProvider statusBarContentInsetsProvider, SystemStatusAnimationScheduler systemStatusAnimationScheduler) {
        StatusBarStateController statusBarStateController2 = statusBarStateController;
        ConfigurationController configurationController3 = configurationController2;
        StatusBarContentInsetsProvider statusBarContentInsetsProvider2 = statusBarContentInsetsProvider;
        this.mainExecutor = executor;
        this.stateController = statusBarStateController2;
        this.configurationController = configurationController3;
        this.contentInsetsProvider = statusBarContentInsetsProvider2;
        this.animationScheduler = systemStatusAnimationScheduler;
        ViewState viewState = new ViewState(0);
        this.currentViewState = viewState;
        this.nextViewState = ViewState.copy$default(viewState, false, false, false, false, (Rect) null, (Rect) null, (Rect) null, (Rect) null, false, 0, 0, 0, (View) null, (String) null, 16383);
        C12111 r4 = new StatusBarContentInsetsChangedListener(this) {
            public final /* synthetic */ PrivacyDotViewController this$0;

            {
                this.this$0 = r1;
            }

            public final void onStatusBarContentInsetsChanged() {
                PrivacyDotViewController privacyDotViewController = this.this$0;
                Objects.requireNonNull(privacyDotViewController);
                List listOf = SetsKt__SetsKt.listOf(privacyDotViewController.contentInsetsProvider.getStatusBarContentAreaForRotation(3), privacyDotViewController.contentInsetsProvider.getStatusBarContentAreaForRotation(0), privacyDotViewController.contentInsetsProvider.getStatusBarContentAreaForRotation(1), privacyDotViewController.contentInsetsProvider.getStatusBarContentAreaForRotation(2));
                synchronized (privacyDotViewController.lock) {
                    privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, (Rect) listOf.get(1), (Rect) listOf.get(2), (Rect) listOf.get(3), (Rect) listOf.get(0), false, 0, 0, 0, (View) null, (String) null, 16143));
                }
            }
        };
        Objects.requireNonNull(statusBarContentInsetsProvider);
        statusBarContentInsetsProvider2.listeners.add(r4);
        configurationController3.addCallback(new ConfigurationController.ConfigurationListener(this) {
            public final /* synthetic */ PrivacyDotViewController this$0;

            {
                this.this$0 = r1;
            }

            public final void onLayoutDirectionChanged(boolean z) {
                PrivacyDotViewController privacyDotViewController = this.this$0;
                DelayableExecutor delayableExecutor = privacyDotViewController.uiExecutor;
                if (delayableExecutor != null) {
                    delayableExecutor.execute(new PrivacyDotViewController$2$onLayoutDirectionChanged$1(privacyDotViewController, this, z));
                }
            }
        });
        statusBarStateController2.addCallback(new StatusBarStateController.StateListener(this) {
            public final /* synthetic */ PrivacyDotViewController this$0;

            {
                this.this$0 = r1;
            }

            public final void onExpandedChanged(boolean z) {
                PrivacyDotViewController.access$updateStatusBarState(this.this$0);
            }

            public final void onStateChanged(int i) {
                PrivacyDotViewController.access$updateStatusBarState(this.this$0);
            }
        });
        this.systemStatusAnimationCallback = new PrivacyDotViewController$systemStatusAnimationCallback$1(this);
    }

    public static final void access$updateStatusBarState(PrivacyDotViewController privacyDotViewController) {
        boolean z;
        PrivacyDotViewController privacyDotViewController2 = privacyDotViewController;
        Objects.requireNonNull(privacyDotViewController);
        synchronized (privacyDotViewController2.lock) {
            ViewState viewState = privacyDotViewController2.nextViewState;
            if ((!privacyDotViewController2.stateController.isExpanded() || privacyDotViewController2.stateController.getState() != 0) && privacyDotViewController2.stateController.getState() != 2) {
                z = false;
            } else {
                z = true;
            }
            privacyDotViewController2.setNextViewState(ViewState.copy$default(viewState, false, false, z, false, (Rect) null, (Rect) null, (Rect) null, (Rect) null, false, 0, 0, 0, (View) null, (String) null, 16379));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        if (r8 != false) goto L_0x0014;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0010, code lost:
        if (r8 != false) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int activeRotationForCorner(android.view.View r7, boolean r8) {
        /*
            r6 = this;
            android.view.View r0 = r6.f79tr
            r1 = 0
            if (r0 != 0) goto L_0x0006
            r0 = r1
        L_0x0006:
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r0)
            r2 = 2
            r3 = 3
            r4 = 1
            r5 = 0
            if (r0 == 0) goto L_0x0016
            if (r8 == 0) goto L_0x0014
        L_0x0012:
            r2 = r4
            goto L_0x0038
        L_0x0014:
            r2 = r5
            goto L_0x0038
        L_0x0016:
            android.view.View r0 = r6.f78tl
            if (r0 != 0) goto L_0x001b
            r0 = r1
        L_0x001b:
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r0)
            if (r0 == 0) goto L_0x0026
            if (r8 == 0) goto L_0x0024
            goto L_0x0014
        L_0x0024:
            r2 = r3
            goto L_0x0038
        L_0x0026:
            android.view.View r6 = r6.f77br
            if (r6 != 0) goto L_0x002b
            goto L_0x002c
        L_0x002b:
            r1 = r6
        L_0x002c:
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r1)
            if (r6 == 0) goto L_0x0035
            if (r8 == 0) goto L_0x0012
            goto L_0x0038
        L_0x0035:
            if (r8 == 0) goto L_0x0038
            goto L_0x0024
        L_0x0038:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.events.PrivacyDotViewController.activeRotationForCorner(android.view.View, boolean):int");
    }

    public final int cornerForView(View view) {
        View view2 = this.f78tl;
        View view3 = null;
        if (view2 == null) {
            view2 = null;
        }
        if (Intrinsics.areEqual(view, view2)) {
            return 0;
        }
        View view4 = this.f79tr;
        if (view4 == null) {
            view4 = null;
        }
        if (Intrinsics.areEqual(view, view4)) {
            return 1;
        }
        View view5 = this.f76bl;
        if (view5 == null) {
            view5 = null;
        }
        if (Intrinsics.areEqual(view, view5)) {
            return 3;
        }
        View view6 = this.f77br;
        if (view6 != null) {
            view3 = view6;
        }
        if (Intrinsics.areEqual(view, view3)) {
            return 2;
        }
        throw new IllegalArgumentException("not a corner view");
    }

    public final Sequence<View> getViews() {
        View view = this.f78tl;
        if (view == null) {
            return SequencesKt__SequencesKt.sequenceOf(new View[0]);
        }
        View[] viewArr = new View[4];
        View view2 = null;
        if (view == null) {
            view = null;
        }
        viewArr[0] = view;
        View view3 = this.f79tr;
        if (view3 == null) {
            view3 = null;
        }
        viewArr[1] = view3;
        View view4 = this.f77br;
        if (view4 == null) {
            view4 = null;
        }
        viewArr[2] = view4;
        View view5 = this.f76bl;
        if (view5 != null) {
            view2 = view5;
        }
        viewArr[3] = view2;
        return SequencesKt__SequencesKt.sequenceOf(viewArr);
    }

    public final View selectDesignatedCorner(int i, boolean z) {
        View view = this.f78tl;
        if (view == null) {
            return null;
        }
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalStateException("unknown rotation");
                    } else if (z) {
                        View view2 = this.f76bl;
                        if (view2 != null) {
                            return view2;
                        }
                    } else if (view != null) {
                        return view;
                    }
                } else if (z) {
                    View view3 = this.f77br;
                    if (view3 != null) {
                        return view3;
                    }
                } else {
                    View view4 = this.f76bl;
                    if (view4 != null) {
                        return view4;
                    }
                }
            } else if (z) {
                View view5 = this.f79tr;
                if (view5 != null) {
                    return view5;
                }
            } else {
                View view6 = this.f77br;
                if (view6 != null) {
                    return view6;
                }
            }
        } else if (!z) {
            View view7 = this.f79tr;
            if (view7 != null) {
                return view7;
            }
        } else if (view != null) {
            return view;
        }
        return null;
    }

    public final void setNextViewState(ViewState viewState) {
        Runnable runnable;
        this.nextViewState = viewState;
        Runnable runnable2 = this.cancelRunnable;
        if (runnable2 != null) {
            runnable2.run();
        }
        DelayableExecutor delayableExecutor = this.uiExecutor;
        if (delayableExecutor == null) {
            runnable = null;
        } else {
            runnable = delayableExecutor.executeDelayed(new PrivacyDotViewController$scheduleUpdate$1(this), 100);
        }
        this.cancelRunnable = runnable;
    }

    public final void setCornerVisibilities() {
        for (View next : getViews()) {
            next.setVisibility(4);
            ShowingListener showingListener2 = this.showingListener;
            if (showingListener2 != null) {
                ScreenDecorations.C06392 r2 = (ScreenDecorations.C06392) showingListener2;
                ScreenDecorations screenDecorations = ScreenDecorations.this;
                if (screenDecorations.mHwcScreenDecorationSupport != null) {
                    screenDecorations.mExecutor.execute(new ScreenDecorations$2$$ExternalSyntheticLambda0(r2, next, 0));
                }
            }
        }
    }
}
