package com.android.systemui.animation;

import android.app.Dialog;
import android.service.dreams.IDreamManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.LaunchAnimator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DialogLaunchAnimator.kt */
public final class DialogLaunchAnimator {
    @Deprecated
    public static final LaunchAnimator.Interpolators INTERPOLATORS;
    @Deprecated
    public static final int TAG_LAUNCH_ANIMATION_RUNNING = C1777R.C1779id.launch_animation_running;
    @Deprecated
    public static final LaunchAnimator.Timings TIMINGS = ActivityLaunchAnimator.TIMINGS;
    public final IDreamManager dreamManager;
    public final boolean isForTesting = false;
    public final LaunchAnimator launchAnimator;
    public final HashSet<AnimatedDialog> openedDialogs = new HashSet<>();

    static {
        LaunchAnimator.Timings timings = ActivityLaunchAnimator.TIMINGS;
        LaunchAnimator.Interpolators interpolators = ActivityLaunchAnimator.INTERPOLATORS;
        Objects.requireNonNull(interpolators);
        Interpolator interpolator = interpolators.positionInterpolator;
        Interpolator interpolator2 = interpolators.contentBeforeFadeOutInterpolator;
        Interpolator interpolator3 = interpolators.contentAfterFadeInInterpolator;
        Objects.requireNonNull(interpolators);
        INTERPOLATORS = new LaunchAnimator.Interpolators(interpolator, interpolator, interpolator2, interpolator3);
    }

    public DialogLaunchAnimator(IDreamManager iDreamManager) {
        LaunchAnimator launchAnimator2 = new LaunchAnimator(TIMINGS, INTERPOLATORS);
        this.dreamManager = iDreamManager;
        this.launchAnimator = launchAnimator2;
    }

    public final void disableAllCurrentDialogsExitAnimations() {
        for (AnimatedDialog animatedDialog : this.openedDialogs) {
            Objects.requireNonNull(animatedDialog);
            animatedDialog.exitAnimationDisabled = true;
        }
    }

    public final void dismissStack(Dialog dialog) {
        T t;
        Iterator<T> it = this.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            AnimatedDialog animatedDialog = (AnimatedDialog) t;
            Objects.requireNonNull(animatedDialog);
            if (Intrinsics.areEqual(animatedDialog.dialog, dialog)) {
                break;
            }
        }
        AnimatedDialog animatedDialog2 = (AnimatedDialog) t;
        if (animatedDialog2 != null) {
            animatedDialog2.touchSurface = animatedDialog2.prepareForStackDismiss();
        }
        dialog.dismiss();
    }

    public static DialogLaunchAnimator$createActivityLaunchController$1 createActivityLaunchController$default(DialogLaunchAnimator dialogLaunchAnimator, View view) {
        AnimatedDialog animatedDialog;
        ViewGroup viewGroup;
        GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController;
        Objects.requireNonNull(dialogLaunchAnimator);
        Iterator<AnimatedDialog> it = dialogLaunchAnimator.openedDialogs.iterator();
        while (true) {
            if (!it.hasNext()) {
                animatedDialog = null;
                break;
            }
            animatedDialog = it.next();
            AnimatedDialog animatedDialog2 = animatedDialog;
            Objects.requireNonNull(animatedDialog2);
            if (Intrinsics.areEqual(animatedDialog2.dialog.getWindow().getDecorView().getViewRootImpl(), view.getViewRootImpl())) {
                break;
            }
        }
        AnimatedDialog animatedDialog3 = animatedDialog;
        if (animatedDialog3 == null) {
            return null;
        }
        animatedDialog3.exitAnimationDisabled = true;
        Dialog dialog = animatedDialog3.dialog;
        if (!dialog.isShowing() || (viewGroup = animatedDialog3.dialogContentWithBackground) == null) {
            return null;
        }
        if (!(viewGroup.getParent() instanceof ViewGroup)) {
            Log.wtf("ActivityLaunchAnimator", "Skipping animation as view " + viewGroup + " is not attached to a ViewGroup", new Exception());
            ghostedViewLaunchAnimatorController = null;
        } else {
            ghostedViewLaunchAnimatorController = new GhostedViewLaunchAnimatorController((View) viewGroup, (Integer) null, 4);
        }
        if (ghostedViewLaunchAnimatorController == null) {
            return null;
        }
        return new DialogLaunchAnimator$createActivityLaunchController$1(ghostedViewLaunchAnimatorController, dialog, animatedDialog3);
    }

    /* JADX WARNING: Failed to insert additional move for type inference */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void showFromView(android.app.Dialog r13, android.view.View r14, boolean r15) {
        /*
            r12 = this;
            android.os.Looper r0 = android.os.Looper.myLooper()
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r0 == 0) goto L_0x01be
            java.util.HashSet<com.android.systemui.animation.AnimatedDialog> r0 = r12.openedDialogs
            java.util.Iterator r0 = r0.iterator()
        L_0x0014:
            boolean r1 = r0.hasNext()
            r2 = 0
            if (r1 == 0) goto L_0x003e
            java.lang.Object r1 = r0.next()
            r3 = r1
            com.android.systemui.animation.AnimatedDialog r3 = (com.android.systemui.animation.AnimatedDialog) r3
            java.util.Objects.requireNonNull(r3)
            android.app.Dialog r3 = r3.dialog
            android.view.Window r3 = r3.getWindow()
            android.view.View r3 = r3.getDecorView()
            android.view.ViewRootImpl r3 = r3.getViewRootImpl()
            android.view.ViewRootImpl r4 = r14.getViewRootImpl()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r4)
            if (r3 == 0) goto L_0x0014
            goto L_0x003f
        L_0x003e:
            r1 = r2
        L_0x003f:
            r10 = r1
            com.android.systemui.animation.AnimatedDialog r10 = (com.android.systemui.animation.AnimatedDialog) r10
            if (r10 != 0) goto L_0x0045
            goto L_0x0049
        L_0x0045:
            android.view.ViewGroup r0 = r10.dialogContentWithBackground
            if (r0 != 0) goto L_0x004b
        L_0x0049:
            r6 = r14
            goto L_0x004c
        L_0x004b:
            r6 = r0
        L_0x004c:
            int r14 = TAG_LAUNCH_ANIMATION_RUNNING
            java.lang.Object r0 = r6.getTag(r14)
            if (r0 == 0) goto L_0x005f
            java.lang.String r12 = "DialogLaunchAnimator"
            java.lang.String r14 = "Not running dialog launch animation as there is already one running"
            android.util.Log.e(r12, r14)
            r13.show()
            return
        L_0x005f:
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            r6.setTag(r14, r0)
            com.android.systemui.animation.AnimatedDialog r14 = new com.android.systemui.animation.AnimatedDialog
            com.android.systemui.animation.LaunchAnimator r4 = r12.launchAnimator
            android.service.dreams.IDreamManager r5 = r12.dreamManager
            com.android.systemui.animation.DialogLaunchAnimator$showFromView$animatedDialog$1 r7 = new com.android.systemui.animation.DialogLaunchAnimator$showFromView$animatedDialog$1
            r7.<init>(r12)
            boolean r11 = r12.isForTesting
            r3 = r14
            r8 = r13
            r9 = r15
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11)
            java.util.HashSet<com.android.systemui.animation.AnimatedDialog> r12 = r12.openedDialogs
            r12.add(r14)
            r13.create()
            android.view.Window r12 = r13.getWindow()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r12)
            android.view.WindowManager$LayoutParams r15 = r12.getAttributes()
            int r15 = r15.width
            r0 = 0
            r1 = -1
            r3 = 1
            if (r15 != r1) goto L_0x009b
            android.view.WindowManager$LayoutParams r15 = r12.getAttributes()
            int r15 = r15.height
            if (r15 != r1) goto L_0x009b
            r15 = r3
            goto L_0x009c
        L_0x009b:
            r15 = r0
        L_0x009c:
            r4 = 2
            if (r15 == 0) goto L_0x00c9
            android.view.ViewGroup r13 = r14.getDecorView()
            int r13 = r13.getChildCount()
            r15 = r0
        L_0x00a8:
            if (r15 >= r13) goto L_0x00bd
            int r1 = r15 + 1
            android.view.ViewGroup r2 = r14.getDecorView()
            android.view.View r15 = r2.getChildAt(r15)
            android.view.ViewGroup r2 = com.android.systemui.animation.AnimatedDialog.findFirstViewGroupWithBackground(r15)
            if (r2 == 0) goto L_0x00bb
            goto L_0x00bd
        L_0x00bb:
            r15 = r1
            goto L_0x00a8
        L_0x00bd:
            if (r2 == 0) goto L_0x00c1
            goto L_0x0156
        L_0x00c1:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "Unable to find ViewGroup with background"
            r12.<init>(r13)
            throw r12
        L_0x00c9:
            android.widget.FrameLayout r15 = new android.widget.FrameLayout
            android.content.Context r2 = r13.getContext()
            r15.<init>(r2)
            android.view.ViewGroup r2 = r14.getDecorView()
            android.widget.FrameLayout$LayoutParams r5 = new android.widget.FrameLayout$LayoutParams
            r5.<init>(r1, r1)
            r2.addView(r15, r0, r5)
            android.widget.FrameLayout r2 = new android.widget.FrameLayout
            android.content.Context r13 = r13.getContext()
            r2.<init>(r13)
            android.view.ViewGroup r13 = r14.getDecorView()
            android.graphics.drawable.Drawable r13 = r13.getBackground()
            r2.setBackground(r13)
            r13 = 17170445(0x106000d, float:2.461195E-38)
            r12.setBackgroundDrawableResource(r13)
            com.android.systemui.animation.AnimatedDialog$start$dialogContentWithBackground$1 r13 = new com.android.systemui.animation.AnimatedDialog$start$dialogContentWithBackground$1
            r13.<init>(r14)
            r15.setOnClickListener(r13)
            r2.setClickable(r3)
            r15.setImportantForAccessibility(r4)
            r2.setImportantForAccessibility(r4)
            android.widget.FrameLayout$LayoutParams r13 = new android.widget.FrameLayout$LayoutParams
            android.view.WindowManager$LayoutParams r5 = r12.getAttributes()
            int r5 = r5.width
            android.view.WindowManager$LayoutParams r6 = r12.getAttributes()
            int r6 = r6.height
            android.view.WindowManager$LayoutParams r7 = r12.getAttributes()
            int r7 = r7.gravity
            r13.<init>(r5, r6, r7)
            r15.addView(r2, r13)
            android.view.ViewGroup r13 = r14.getDecorView()
            int r13 = r13.getChildCount()
            r15 = r3
        L_0x012c:
            if (r15 >= r13) goto L_0x0143
            int r15 = r15 + 1
            android.view.ViewGroup r5 = r14.getDecorView()
            android.view.View r5 = r5.getChildAt(r3)
            android.view.ViewGroup r6 = r14.getDecorView()
            r6.removeViewAt(r3)
            r2.addView(r5)
            goto L_0x012c
        L_0x0143:
            r12.setLayout(r1, r1)
            com.android.systemui.animation.AnimatedDialog$start$dialogContentWithBackground$2 r13 = new com.android.systemui.animation.AnimatedDialog$start$dialogContentWithBackground$2
            r13.<init>(r12, r2)
            r14.decorViewLayoutListener = r13
            android.view.ViewGroup r13 = r14.getDecorView()
            com.android.systemui.animation.AnimatedDialog$start$dialogContentWithBackground$2 r15 = r14.decorViewLayoutListener
            r13.addOnLayoutChangeListener(r15)
        L_0x0156:
            r14.dialogContentWithBackground = r2
            android.graphics.drawable.Drawable r13 = r2.getBackground()
            android.graphics.drawable.GradientDrawable r13 = com.android.systemui.animation.GhostedViewLaunchAnimatorController.Companion.findGradientDrawable(r13)
            r15 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            if (r13 != 0) goto L_0x0165
            goto L_0x0170
        L_0x0165:
            android.content.res.ColorStateList r13 = r13.getColor()
            if (r13 != 0) goto L_0x016c
            goto L_0x0170
        L_0x016c:
            int r15 = r13.getDefaultColor()
        L_0x0170:
            r14.originalDialogBackgroundColor = r15
            r13 = 4
            r2.setTransitionVisibility(r13)
            android.view.WindowManager$LayoutParams r13 = r12.getAttributes()
            r15 = 2132017163(0x7f14000b, float:1.9672597E38)
            r13.windowAnimations = r15
            android.view.WindowManager$LayoutParams r13 = r12.getAttributes()
            r15 = 3
            r13.layoutInDisplayCutoutMode = r15
            android.view.WindowManager$LayoutParams r13 = r12.getAttributes()
            r12.setAttributes(r13)
            r12.setDecorFitsSystemWindows(r0)
            android.view.ViewParent r13 = r2.getParent()
            java.lang.String r15 = "null cannot be cast to non-null type android.view.ViewGroup"
            java.util.Objects.requireNonNull(r13, r15)
            android.view.ViewGroup r13 = (android.view.ViewGroup) r13
            com.android.systemui.animation.AnimatedDialog$start$1 r15 = com.android.systemui.animation.AnimatedDialog$start$1.INSTANCE
            r13.setOnApplyWindowInsetsListener(r15)
            com.android.systemui.animation.AnimatedDialog$start$2 r13 = new com.android.systemui.animation.AnimatedDialog$start$2
            r13.<init>(r2, r14)
            r2.addOnLayoutChangeListener(r13)
            r12.clearFlags(r4)
            android.app.Dialog r12 = r14.dialog
            com.android.systemui.animation.AnimatedDialog$start$3 r13 = new com.android.systemui.animation.AnimatedDialog$start$3
            r13.<init>(r14)
            r12.setDismissOverride(r13)
            android.app.Dialog r12 = r14.dialog
            r12.show()
            r14.addTouchSurfaceGhost()
            return
        L_0x01be:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "showFromView must be called from the main thread and dialog must be created in the main thread"
            r12.<init>(r13)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.DialogLaunchAnimator.showFromView(android.app.Dialog, android.view.View, boolean):void");
    }
}
