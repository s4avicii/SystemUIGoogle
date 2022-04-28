package com.android.systemui.animation;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.service.dreams.IDreamManager;
import android.view.GhostView;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.LaunchAnimator;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DialogLaunchAnimator.kt */
public final class AnimatedDialog {
    public final AnimatedBoundsLayoutListener backgroundLayoutListener;
    public final Lazy decorView$delegate = LazyKt__LazyJVMKt.lazy(new AnimatedDialog$decorView$2(this));
    public AnimatedDialog$start$dialogContentWithBackground$2 decorViewLayoutListener;
    public final Dialog dialog;
    public ViewGroup dialogContentWithBackground;
    public boolean dismissRequested;
    public final IDreamManager dreamManager;
    public boolean exitAnimationDisabled;
    public final boolean forceDisableSynchronization;
    public boolean isDismissing;
    public boolean isLaunching = true;
    public boolean isOriginalDialogViewLaidOut;
    public boolean isTouchSurfaceGhostDrawn;
    public final LaunchAnimator launchAnimator;
    public final Function1<AnimatedDialog, Unit> onDialogDismissed;
    public int originalDialogBackgroundColor = -16777216;
    public final AnimatedDialog parentAnimatedDialog;
    public View touchSurface;

    /* compiled from: DialogLaunchAnimator.kt */
    public static final class AnimatedBoundsLayoutListener implements View.OnLayoutChangeListener {
        public ValueAnimator currentAnimator;
        public Rect lastBounds;

        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            View view2 = view;
            int i9 = i5;
            int i10 = i6;
            int i11 = i7;
            int i12 = i8;
            int i13 = i2;
            int i14 = i3;
            if (i == i9 && i13 == i10) {
                int i15 = i4;
                if (i14 == i11 && i15 == i12) {
                    Rect rect = this.lastBounds;
                    if (rect != null) {
                        view2.setLeft(rect.left);
                        view2.setTop(rect.top);
                        view2.setRight(rect.right);
                        view2.setBottom(rect.bottom);
                        return;
                    }
                    return;
                }
            } else {
                int i16 = i4;
            }
            if (this.lastBounds == null) {
                this.lastBounds = new Rect(i9, i10, i11, i12);
            }
            Rect rect2 = this.lastBounds;
            Intrinsics.checkNotNull(rect2);
            int i17 = rect2.left;
            int i18 = rect2.top;
            int i19 = rect2.right;
            int i20 = rect2.bottom;
            ValueAnimator valueAnimator = this.currentAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.currentAnimator = null;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            ofFloat.setDuration(500);
            ofFloat.setInterpolator(Interpolators.STANDARD);
            ofFloat.addListener(new C0663xa7324177(this));
            ofFloat.addUpdateListener(new C0664xa7324178(rect2, i17, i, i18, i2, i19, i3, i20, i4, view));
            this.currentAnimator = ofFloat;
            ofFloat.start();
        }
    }

    public static ViewGroup findFirstViewGroupWithBackground(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (viewGroup.getBackground() != null) {
            return viewGroup;
        }
        int i = 0;
        int childCount = viewGroup.getChildCount();
        while (i < childCount) {
            int i2 = i + 1;
            ViewGroup findFirstViewGroupWithBackground = findFirstViewGroupWithBackground(viewGroup.getChildAt(i));
            if (findFirstViewGroupWithBackground != null) {
                return findFirstViewGroupWithBackground;
            }
            i = i2;
        }
        return null;
    }

    public final ViewGroup getDecorView() {
        return (ViewGroup) this.decorView$delegate.getValue();
    }

    public final View prepareForStackDismiss() {
        AnimatedDialog animatedDialog = this.parentAnimatedDialog;
        if (animatedDialog == null) {
            return this.touchSurface;
        }
        animatedDialog.exitAnimationDisabled = true;
        animatedDialog.dialog.hide();
        View prepareForStackDismiss = this.parentAnimatedDialog.prepareForStackDismiss();
        this.parentAnimatedDialog.dialog.dismiss();
        prepareForStackDismiss.setVisibility(4);
        return prepareForStackDismiss;
    }

    public final void startAnimation(boolean z, Function0<Unit> function0, Function0<Unit> function02) {
        View view;
        View view2;
        if (z) {
            view = this.touchSurface;
        } else {
            view = this.dialogContentWithBackground;
            Intrinsics.checkNotNull(view);
        }
        if (z) {
            view2 = this.dialogContentWithBackground;
            Intrinsics.checkNotNull(view2);
        } else {
            view2 = this.touchSurface;
        }
        GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController = new GhostedViewLaunchAnimatorController(view, (Integer) null, 6);
        GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController2 = new GhostedViewLaunchAnimatorController(view2, (Integer) null, 6);
        ghostedViewLaunchAnimatorController.launchContainer = getDecorView();
        ghostedViewLaunchAnimatorController2.launchContainer = getDecorView();
        LaunchAnimator.State createAnimatorState = ghostedViewLaunchAnimatorController2.createAnimatorState();
        AnimatedDialog$startAnimation$controller$1 animatedDialog$startAnimation$controller$1 = new AnimatedDialog$startAnimation$controller$1(ghostedViewLaunchAnimatorController, ghostedViewLaunchAnimatorController2, function0, function02, createAnimatorState);
        LaunchAnimator launchAnimator2 = this.launchAnimator;
        int i = this.originalDialogBackgroundColor;
        PorterDuffXfermode porterDuffXfermode = LaunchAnimator.SRC_MODE;
        launchAnimator2.startAnimation(animatedDialog$startAnimation$controller$1, createAnimatorState, i, false);
    }

    public AnimatedDialog(LaunchAnimator launchAnimator2, IDreamManager iDreamManager, View view, Function1<? super AnimatedDialog, Unit> function1, Dialog dialog2, boolean z, AnimatedDialog animatedDialog, boolean z2) {
        AnimatedBoundsLayoutListener animatedBoundsLayoutListener;
        this.launchAnimator = launchAnimator2;
        this.dreamManager = iDreamManager;
        this.touchSurface = view;
        this.onDialogDismissed = function1;
        this.dialog = dialog2;
        this.parentAnimatedDialog = animatedDialog;
        this.forceDisableSynchronization = z2;
        if (z) {
            animatedBoundsLayoutListener = new AnimatedBoundsLayoutListener();
        } else {
            animatedBoundsLayoutListener = null;
        }
        this.backgroundLayoutListener = animatedBoundsLayoutListener;
    }

    public static final void access$maybeStartLaunchAnimation(AnimatedDialog animatedDialog) {
        Objects.requireNonNull(animatedDialog);
        if (animatedDialog.isTouchSurfaceGhostDrawn && animatedDialog.isOriginalDialogViewLaidOut) {
            animatedDialog.dialog.getWindow().addFlags(2);
            animatedDialog.startAnimation(true, new AnimatedDialog$maybeStartLaunchAnimation$1(animatedDialog), new AnimatedDialog$maybeStartLaunchAnimation$2(animatedDialog));
        }
    }

    public final void addTouchSurfaceGhost() {
        LaunchableView launchableView;
        if (getDecorView().getViewRootImpl() == null) {
            getDecorView().post(new AnimatedDialog$addTouchSurfaceGhost$1(this));
            return;
        }
        AnimatedDialog$addTouchSurfaceGhost$2 animatedDialog$addTouchSurfaceGhost$2 = new AnimatedDialog$addTouchSurfaceGhost$2(this);
        if (this.forceDisableSynchronization) {
            animatedDialog$addTouchSurfaceGhost$2.invoke();
        } else {
            boolean z = ViewRootSync.forceDisableSynchronization;
            ViewRootSync.synchronizeNextDraw(this.touchSurface, getDecorView(), animatedDialog$addTouchSurfaceGhost$2);
        }
        GhostView.addGhost(this.touchSurface, getDecorView());
        View view = this.touchSurface;
        if (view instanceof LaunchableView) {
            launchableView = (LaunchableView) view;
        } else {
            launchableView = null;
        }
        if (launchableView != null) {
            launchableView.setShouldBlockVisibilityChanges(true);
        }
    }

    /* JADX WARNING: type inference failed for: r1v5, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onDialogDismissed() {
        /*
            r7 = this;
            android.os.Looper r0 = android.os.Looper.myLooper()
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r0 != 0) goto L_0x0021
            android.app.Dialog r0 = r7.dialog
            android.content.Context r0 = r0.getContext()
            java.util.concurrent.Executor r0 = r0.getMainExecutor()
            com.android.systemui.animation.AnimatedDialog$onDialogDismissed$1 r1 = new com.android.systemui.animation.AnimatedDialog$onDialogDismissed$1
            r1.<init>(r7)
            r0.execute(r1)
            return
        L_0x0021:
            boolean r0 = r7.isLaunching
            r1 = 1
            if (r0 == 0) goto L_0x0029
            r7.dismissRequested = r1
            return
        L_0x0029:
            boolean r0 = r7.isDismissing
            if (r0 == 0) goto L_0x002e
            return
        L_0x002e:
            r7.isDismissing = r1
            com.android.systemui.animation.AnimatedDialog$onDialogDismissed$2 r0 = new com.android.systemui.animation.AnimatedDialog$onDialogDismissed$2
            r0.<init>(r7)
            com.android.systemui.animation.AnimatedDialog$start$dialogContentWithBackground$2 r2 = r7.decorViewLayoutListener
            if (r2 == 0) goto L_0x0042
            android.view.ViewGroup r2 = r7.getDecorView()
            com.android.systemui.animation.AnimatedDialog$start$dialogContentWithBackground$2 r3 = r7.decorViewLayoutListener
            r2.removeOnLayoutChangeListener(r3)
        L_0x0042:
            boolean r2 = r7.exitAnimationDisabled
            r3 = 0
            r4 = 4
            r5 = 0
            if (r2 != 0) goto L_0x0083
            android.app.Dialog r2 = r7.dialog
            boolean r2 = r2.isShowing()
            if (r2 != 0) goto L_0x0052
            goto L_0x0083
        L_0x0052:
            android.service.dreams.IDreamManager r2 = r7.dreamManager
            boolean r2 = r2.isDreaming()
            if (r2 == 0) goto L_0x005b
            goto L_0x0083
        L_0x005b:
            android.view.View r2 = r7.touchSurface
            int r2 = r2.getVisibility()
            if (r2 == r4) goto L_0x0064
            goto L_0x0083
        L_0x0064:
            android.view.View r2 = r7.touchSurface
            boolean r2 = r2.isAttachedToWindow()
            if (r2 != 0) goto L_0x006d
            goto L_0x0083
        L_0x006d:
            android.view.View r2 = r7.touchSurface
            android.view.ViewParent r2 = r2.getParent()
            boolean r6 = r2 instanceof android.view.View
            if (r6 == 0) goto L_0x007a
            android.view.View r2 = (android.view.View) r2
            goto L_0x007b
        L_0x007a:
            r2 = r3
        L_0x007b:
            if (r2 != 0) goto L_0x007e
            goto L_0x0084
        L_0x007e:
            boolean r1 = r2.isShown()
            goto L_0x0084
        L_0x0083:
            r1 = r5
        L_0x0084:
            if (r1 != 0) goto L_0x00b4
            java.lang.String r1 = "DialogLaunchAnimator"
            java.lang.String r2 = "Skipping animation of dialog into the touch surface"
            android.util.Log.i(r1, r2)
            android.view.View r1 = r7.touchSurface
            boolean r2 = r1 instanceof com.android.systemui.animation.LaunchableView
            if (r2 == 0) goto L_0x0096
            r3 = r1
            com.android.systemui.animation.LaunchableView r3 = (com.android.systemui.animation.LaunchableView) r3
        L_0x0096:
            if (r3 != 0) goto L_0x0099
            goto L_0x009c
        L_0x0099:
            r3.setShouldBlockVisibilityChanges(r5)
        L_0x009c:
            android.view.View r1 = r7.touchSurface
            int r1 = r1.getVisibility()
            if (r1 != r4) goto L_0x00a9
            android.view.View r1 = r7.touchSurface
            r1.setVisibility(r5)
        L_0x00a9:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            r0.invoke(r1)
            kotlin.jvm.functions.Function1<com.android.systemui.animation.AnimatedDialog, kotlin.Unit> r0 = r7.onDialogDismissed
            r0.invoke(r7)
            goto L_0x00c1
        L_0x00b4:
            com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$1 r1 = new com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$1
            r1.<init>(r7)
            com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$2 r2 = new com.android.systemui.animation.AnimatedDialog$hideDialogIntoView$2
            r2.<init>(r7, r0)
            r7.startAnimation(r5, r1, r2)
        L_0x00c1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.AnimatedDialog.onDialogDismissed():void");
    }
}
