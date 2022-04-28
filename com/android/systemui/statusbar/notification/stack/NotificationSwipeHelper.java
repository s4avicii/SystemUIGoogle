package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6;
import com.android.systemui.SwipeHelper;
import com.android.systemui.SwipeHelper$$ExternalSyntheticLambda0;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.StatusBar;
import java.lang.ref.WeakReference;
import java.util.Objects;

public final class NotificationSwipeHelper extends SwipeHelper implements NotificationSwipeActionHelper {
    @VisibleForTesting
    public static final long COVER_MENU_DELAY = 4000;
    public final NotificationCallback mCallback;
    public WeakReference<NotificationMenuRowPlugin> mCurrMenuRowRef;
    public final KeyguardUpdateMonitor$$ExternalSyntheticLambda6 mFalsingCheck = new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(this, 6);
    public boolean mIsExpanded;
    public View mMenuExposedView;
    public final NotificationMenuRowPlugin.OnMenuEventListener mMenuListener;
    public boolean mPulsing;
    public View mTranslatingParentView;

    public interface NotificationCallback extends SwipeHelper.Callback {
    }

    public NotificationSwipeHelper(Resources resources, ViewConfiguration viewConfiguration, FalsingManager falsingManager, FeatureFlags featureFlags, int i, NotificationCallback notificationCallback, NotificationMenuRowPlugin.OnMenuEventListener onMenuEventListener) {
        super(i, notificationCallback, resources, viewConfiguration, falsingManager, featureFlags);
        this.mMenuListener = onMenuEventListener;
        this.mCallback = notificationCallback;
    }

    public static boolean isTouchInView(MotionEvent motionEvent, View view) {
        int i;
        if (view == null) {
            return false;
        }
        if (view instanceof ExpandableView) {
            i = ((ExpandableView) view).mActualHeight;
        } else {
            i = view.getHeight();
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        return new Rect(i2, i3, view.getWidth() + i2, i + i3).contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
    }

    @VisibleForTesting
    public void snapClosed(View view, float f) {
        snapChild(view, 0.0f, f);
    }

    public final void snapOpen(View view, int i, float f) {
        snapChild(view, (float) i, f);
    }

    @VisibleForTesting
    public void superDismissChild(View view, float f, boolean z) {
        dismissChild(view, f, (NotificationStackScrollLayout$$ExternalSyntheticLambda2) null, 0, z, 0, false);
    }

    public final boolean swipedFarEnough(float f, float f2) {
        return swipedFarEnough();
    }

    public final boolean swipedFastEnough(float f, float f2) {
        return swipedFastEnough();
    }

    public static class Builder {
        public final FalsingManager mFalsingManager;
        public final FeatureFlags mFeatureFlags;
        public NotificationMenuRowPlugin.OnMenuEventListener mOnMenuEventListener;
        public final Resources mResources;
        public final ViewConfiguration mViewConfiguration;

        public Builder(Resources resources, ViewConfiguration viewConfiguration, FalsingManager falsingManager, FeatureFlags featureFlags) {
            this.mResources = resources;
            this.mViewConfiguration = viewConfiguration;
            this.mFalsingManager = falsingManager;
            this.mFeatureFlags = featureFlags;
        }
    }

    public final NotificationMenuRowPlugin getCurrentMenuRow() {
        WeakReference<NotificationMenuRowPlugin> weakReference = this.mCurrMenuRowRef;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    @VisibleForTesting
    public float getEscapeVelocity() {
        return this.mDensityScale * 500.0f;
    }

    public final float getTranslation(View view) {
        if (view instanceof SwipeableView) {
            return ((SwipeableView) view).getTranslation();
        }
        return 0.0f;
    }

    @VisibleForTesting
    public void handleMenuCoveredOrDismissed() {
        View view = this.mMenuExposedView;
        if (view != null && view == this.mTranslatingParentView) {
            this.mMenuExposedView = null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0028, code lost:
        if (r1 != 3) goto L_0x016c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00d5, code lost:
        if (r10 != false) goto L_0x017a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r10) {
        /*
            r9 = this;
            boolean r0 = r9.mIsSwiping
            com.android.systemui.statusbar.notification.row.ExpandableView r1 = r9.mTouchedView
            boolean r2 = r1 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r2 == 0) goto L_0x0019
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r1 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r1
            java.util.Objects.requireNonNull(r1)
            com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin r1 = r1.mMenuRow
            if (r1 == 0) goto L_0x0019
            com.android.systemui.statusbar.notification.row.ExpandableView r2 = r9.mTouchedView
            boolean r1 = r1.onInterceptTouchEvent(r2, r10)
            r9.mMenuRowIntercepting = r1
        L_0x0019:
            int r1 = r10.getAction()
            r2 = 1
            r3 = 0
            r4 = 0
            if (r1 == 0) goto L_0x00d9
            if (r1 == r2) goto L_0x00af
            r5 = 2
            if (r1 == r5) goto L_0x002c
            r10 = 3
            if (r1 == r10) goto L_0x00af
            goto L_0x016c
        L_0x002c:
            com.android.systemui.statusbar.notification.row.ExpandableView r1 = r9.mTouchedView
            if (r1 == 0) goto L_0x016c
            boolean r1 = r9.mLongPressSent
            if (r1 != 0) goto L_0x016c
            android.view.VelocityTracker r1 = r9.mVelocityTracker
            r1.addMovement(r10)
            float r1 = r9.getPos(r10)
            int r6 = r9.mSwipeDirection
            if (r6 != 0) goto L_0x0046
            float r6 = r10.getY()
            goto L_0x004a
        L_0x0046:
            float r6 = r10.getX()
        L_0x004a:
            float r7 = r9.mInitialTouchPos
            float r1 = r1 - r7
            float r7 = r9.mPerpendicularInitialTouchPos
            float r6 = r6 - r7
            int r7 = r10.getClassification()
            if (r7 != r2) goto L_0x005c
            float r7 = r9.mPagingTouchSlop
            float r8 = r9.mSlopMultiplier
            float r7 = r7 * r8
            goto L_0x005e
        L_0x005c:
            float r7 = r9.mPagingTouchSlop
        L_0x005e:
            float r8 = java.lang.Math.abs(r1)
            int r7 = (r8 > r7 ? 1 : (r8 == r7 ? 0 : -1))
            if (r7 <= 0) goto L_0x0095
            float r1 = java.lang.Math.abs(r1)
            float r6 = java.lang.Math.abs(r6)
            int r1 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r1 <= 0) goto L_0x0095
            com.android.systemui.SwipeHelper$Callback r1 = r9.mCallback
            java.util.Objects.requireNonNull(r1)
            r9.mIsSwiping = r2
            com.android.systemui.SwipeHelper$Callback r1 = r9.mCallback
            com.android.systemui.statusbar.notification.row.ExpandableView r5 = r9.mTouchedView
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r1 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r1
            r1.onBeginDrag(r5)
            float r10 = r9.getPos(r10)
            r9.mInitialTouchPos = r10
            com.android.systemui.statusbar.notification.row.ExpandableView r10 = r9.mTouchedView
            float r10 = r9.getTranslation(r10)
            r9.mTranslation = r10
            r9.cancelLongPress()
            goto L_0x016c
        L_0x0095:
            int r10 = r10.getClassification()
            if (r10 != r5) goto L_0x016c
            android.os.Handler r10 = r9.mHandler
            com.android.systemui.SwipeHelper$1 r1 = r9.mPerformLongPress
            boolean r10 = r10.hasCallbacks(r1)
            if (r10 == 0) goto L_0x016c
            r9.cancelLongPress()
            com.android.systemui.SwipeHelper$1 r10 = r9.mPerformLongPress
            r10.run()
            goto L_0x016c
        L_0x00af:
            boolean r10 = r9.mIsSwiping
            if (r10 != 0) goto L_0x00be
            boolean r10 = r9.mLongPressSent
            if (r10 != 0) goto L_0x00be
            boolean r10 = r9.mMenuRowIntercepting
            if (r10 == 0) goto L_0x00bc
            goto L_0x00be
        L_0x00bc:
            r10 = r3
            goto L_0x00bf
        L_0x00be:
            r10 = r2
        L_0x00bf:
            r9.mIsSwiping = r3
            r9.mTouchedView = r4
            r9.mLongPressSent = r3
            com.android.systemui.SwipeHelper$Callback r1 = r9.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r1 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r1
            java.util.Objects.requireNonNull(r1)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r1 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
            r1.mLongPressedView = r4
            r9.mMenuRowIntercepting = r3
            r9.cancelLongPress()
            if (r10 == 0) goto L_0x016c
            goto L_0x017a
        L_0x00d9:
            r9.mTouchAboveFalsingThreshold = r3
            r9.mIsSwiping = r3
            r9.mSnappingChild = r3
            r9.mLongPressSent = r3
            com.android.systemui.SwipeHelper$Callback r1 = r9.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r1 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r1
            java.util.Objects.requireNonNull(r1)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r1 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
            r1.mLongPressedView = r4
            android.view.VelocityTracker r1 = r9.mVelocityTracker
            r1.clear()
            r9.cancelLongPress()
            com.android.systemui.SwipeHelper$Callback r1 = r9.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r1 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r1
            com.android.systemui.statusbar.notification.row.ExpandableView r1 = r1.getChildAtPosition(r10)
            r9.mTouchedView = r1
            if (r1 == 0) goto L_0x016c
            r9.mTranslatingParentView = r1
            com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin r5 = r9.getCurrentMenuRow()
            if (r5 == 0) goto L_0x010b
            r5.onTouchStart()
        L_0x010b:
            r9.setCurrentMenuRow(r4)
            android.os.Handler r5 = r9.getHandler()
            java.lang.Runnable r6 = r9.getFalsingCheck()
            r5.removeCallbacks(r6)
            r9.resetExposedMenuView(r2, r3)
            boolean r5 = r1 instanceof com.android.systemui.statusbar.notification.stack.SwipeableView
            if (r5 == 0) goto L_0x0125
            com.android.systemui.statusbar.notification.stack.SwipeableView r1 = (com.android.systemui.statusbar.notification.stack.SwipeableView) r1
            r9.initializeRow(r1)
        L_0x0125:
            com.android.systemui.SwipeHelper$Callback r1 = r9.mCallback
            com.android.systemui.statusbar.notification.row.ExpandableView r5 = r9.mTouchedView
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r1 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r1
            boolean r1 = r1.canChildBeDismissed(r5)
            r9.mCanCurrViewBeDimissed = r1
            android.view.VelocityTracker r1 = r9.mVelocityTracker
            r1.addMovement(r10)
            float r1 = r9.getPos(r10)
            r9.mInitialTouchPos = r1
            int r1 = r9.mSwipeDirection
            if (r1 != 0) goto L_0x0145
            float r1 = r10.getY()
            goto L_0x0149
        L_0x0145:
            float r1 = r10.getX()
        L_0x0149:
            r9.mPerpendicularInitialTouchPos = r1
            com.android.systemui.statusbar.notification.row.ExpandableView r1 = r9.mTouchedView
            float r1 = r9.getTranslation(r1)
            r9.mTranslation = r1
            float[] r1 = r9.mDownLocation
            float r5 = r10.getRawX()
            r1[r3] = r5
            float[] r1 = r9.mDownLocation
            float r10 = r10.getRawY()
            r1[r2] = r10
            android.os.Handler r10 = r9.mHandler
            com.android.systemui.SwipeHelper$1 r1 = r9.mPerformLongPress
            long r5 = r9.mLongPressTimeout
            r10.postDelayed(r1, r5)
        L_0x016c:
            boolean r10 = r9.mIsSwiping
            if (r10 != 0) goto L_0x017a
            boolean r10 = r9.mLongPressSent
            if (r10 != 0) goto L_0x017a
            boolean r10 = r9.mMenuRowIntercepting
            if (r10 == 0) goto L_0x0179
            goto L_0x017a
        L_0x0179:
            r2 = r3
        L_0x017a:
            boolean r10 = r9.mIsSwiping
            if (r10 == 0) goto L_0x0180
            com.android.systemui.statusbar.notification.row.ExpandableView r4 = r9.mTouchedView
        L_0x0180:
            if (r0 != 0) goto L_0x018c
            if (r4 == 0) goto L_0x018c
            com.android.internal.jank.InteractionJankMonitor r9 = com.android.internal.jank.InteractionJankMonitor.getInstance()
            r10 = 4
            r9.begin(r4, r10)
        L_0x018c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @VisibleForTesting
    public void setCurrentMenuRow(NotificationMenuRowPlugin notificationMenuRowPlugin) {
        WeakReference<NotificationMenuRowPlugin> weakReference;
        if (notificationMenuRowPlugin != null) {
            weakReference = new WeakReference<>(notificationMenuRowPlugin);
        } else {
            weakReference = null;
        }
        this.mCurrMenuRowRef = weakReference;
    }

    @VisibleForTesting
    public boolean shouldResetMenu(boolean z) {
        View view = this.mMenuExposedView;
        if (view == null) {
            return false;
        }
        if (z || view != this.mTranslatingParentView) {
            return true;
        }
        return false;
    }

    public final void snooze(StatusBarNotification statusBarNotification, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
        NotificationStackScrollLayoutController.C13777 r0 = (NotificationStackScrollLayoutController.C13777) this.mCallback;
        Objects.requireNonNull(r0);
        StatusBar statusBar = NotificationStackScrollLayoutController.this.mStatusBar;
        Objects.requireNonNull(statusBar);
        statusBar.mNotificationsController.setNotificationSnoozed(statusBarNotification, snoozeOption);
    }

    @VisibleForTesting
    public Animator superGetViewTranslationAnimator(View view, float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        Property property;
        if (this.mSwipeDirection == 0) {
            property = View.TRANSLATION_X;
        } else {
            property = View.TRANSLATION_Y;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, property, new float[]{f});
        if (animatorUpdateListener != null) {
            ofFloat.addUpdateListener(animatorUpdateListener);
        }
        return ofFloat;
    }

    @VisibleForTesting
    public void superSnapChild(View view, float f, float f2) {
        Animator animator;
        boolean canChildBeDismissed = ((NotificationStackScrollLayoutController.C13777) this.mCallback).canChildBeDismissed(view);
        SwipeHelper$$ExternalSyntheticLambda0 swipeHelper$$ExternalSyntheticLambda0 = new SwipeHelper$$ExternalSyntheticLambda0(this, view, canChildBeDismissed);
        boolean z = view instanceof ExpandableNotificationRow;
        if (z) {
            animator = ((ExpandableNotificationRow) view).getTranslateViewAnimator(f, swipeHelper$$ExternalSyntheticLambda0);
        } else {
            animator = superGetViewTranslationAnimator(view, f, swipeHelper$$ExternalSyntheticLambda0);
        }
        if (animator == null) {
            InteractionJankMonitor.getInstance().end(4);
            return;
        }
        animator.addListener(new AnimatorListenerAdapter(view, canChildBeDismissed) {
            public final /* synthetic */ View val$animView;
            public final /* synthetic */ boolean val$canBeDismissed;
            public boolean wasCancelled;

            public final void onAnimationCancel(
/*
Method generation error in method: com.android.systemui.SwipeHelper.4.onAnimationCancel(android.animation.Animator):void, dex: classes.dex
            jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.SwipeHelper.4.onAnimationCancel(android.animation.Animator):void, class status: UNLOADED
            	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
            
*/

            public final void onAnimationEnd(
/*
Method generation error in method: com.android.systemui.SwipeHelper.4.onAnimationEnd(android.animation.Animator):void, dex: classes.dex
            jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.SwipeHelper.4.onAnimationEnd(android.animation.Animator):void, class status: UNLOADED
            	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
            
*/
        });
        this.mSnappingChild = true;
        Animator animator2 = animator;
        this.mFlingAnimationUtils.apply(animator2, getTranslation(view), f, f2, Math.abs(f - getTranslation(view)));
        animator.start();
        NotificationStackScrollLayoutController.C13777 r9 = (NotificationStackScrollLayoutController.C13777) this.mCallback;
        Objects.requireNonNull(r9);
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.updateFirstAndLastBackgroundViews();
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationStackScrollLayout.mController;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        notificationStackScrollLayoutController.mNotificationRoundnessManager.setViewsAffectedBySwipe((ExpandableView) null, (ExpandableView) null, (ExpandableView) null);
        notificationStackScrollLayout.mShelf.updateAppearance();
        if (z) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            Objects.requireNonNull(expandableNotificationRow);
            if (expandableNotificationRow.mIsPinned && !r9.canChildBeDismissed(expandableNotificationRow)) {
                NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                Objects.requireNonNull(notificationEntry);
                if (notificationEntry.mSbn.getNotification().fullScreenIntent == null) {
                    HeadsUpManagerPhone headsUpManagerPhone = NotificationStackScrollLayoutController.this.mHeadsUpManager;
                    NotificationEntry notificationEntry2 = expandableNotificationRow.mEntry;
                    Objects.requireNonNull(notificationEntry2);
                    headsUpManagerPhone.removeNotification(notificationEntry2.mSbn.getKey(), true);
                }
            }
        }
    }

    @VisibleForTesting
    public boolean swipedFarEnough() {
        return Math.abs(getTranslation(this.mTouchedView)) > getSize(this.mTouchedView) * 0.6f;
    }

    @VisibleForTesting
    public boolean swipedFastEnough() {
        float f;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (this.mSwipeDirection == 0) {
            f = velocityTracker.getXVelocity();
        } else {
            f = velocityTracker.getYVelocity();
        }
        float translation = getTranslation(this.mTouchedView);
        if (Math.abs(f) > getEscapeVelocity()) {
            if ((f > 0.0f) == (translation > 0.0f)) {
                return true;
            }
        }
        return false;
    }

    public final void dismiss(View view, float f) {
        dismissChild(view, f, !swipedFastEnough());
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void dismissChild(android.view.View r3, float r4, boolean r5) {
        /*
            r2 = this;
            r2.superDismissChild(r3, r4, r5)
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper$NotificationCallback r4 = r2.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r4 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r4
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r5 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r5.mView
            java.util.Objects.requireNonNull(r5)
            boolean r5 = r5.mIsExpanded
            r0 = 1
            r1 = 0
            if (r5 == 0) goto L_0x0031
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r4 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r4 = r4.mView
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.notification.stack.AmbientState r4 = r4.mAmbientState
            java.util.Objects.requireNonNull(r4)
            float r4 = r4.mDozeAmount
            r5 = 0
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 != 0) goto L_0x002c
            r4 = r0
            goto L_0x002d
        L_0x002c:
            r4 = r1
        L_0x002d:
            if (r4 == 0) goto L_0x0031
            r4 = r0
            goto L_0x0032
        L_0x0031:
            r4 = r1
        L_0x0032:
            if (r4 == 0) goto L_0x003b
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper$NotificationCallback r4 = r2.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r4 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r4
            r4.handleChildViewDismissed(r3)
        L_0x003b:
            com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper$NotificationCallback r3 = r2.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$7 r3 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777) r3
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r3 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
            com.android.systemui.statusbar.notification.row.NotificationGutsManager r3 = r3.mNotificationGutsManager
            r3.closeAndSaveGuts(r0, r1, r1, r1)
            r2.handleMenuCoveredOrDismissed()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper.dismissChild(android.view.View, float, boolean):void");
    }

    public final float getMinDismissVelocity() {
        return getEscapeVelocity();
    }

    @VisibleForTesting
    public void handleMenuRowSwipe(MotionEvent motionEvent, View view, float f, NotificationMenuRowPlugin notificationMenuRowPlugin) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        if (!notificationMenuRowPlugin.shouldShowMenu()) {
            if (isDismissGesture(motionEvent)) {
                dismiss(view, f);
                return;
            }
            snapClosed(view, f);
            notificationMenuRowPlugin.onSnapClosed();
        } else if (notificationMenuRowPlugin.isSnappedAndOnSameSide()) {
            boolean isDismissGesture = isDismissGesture(motionEvent);
            if (notificationMenuRowPlugin.isWithinSnapMenuThreshold() && !isDismissGesture) {
                notificationMenuRowPlugin.onSnapOpen();
                snapChild(view, (float) notificationMenuRowPlugin.getMenuSnapTarget(), f);
            } else if (!isDismissGesture || notificationMenuRowPlugin.shouldSnapBack()) {
                snapClosed(view, f);
                notificationMenuRowPlugin.onSnapClosed();
            } else {
                dismiss(view, f);
                notificationMenuRowPlugin.onDismiss();
            }
        } else {
            boolean isDismissGesture2 = isDismissGesture(motionEvent);
            boolean isTowardsMenu = notificationMenuRowPlugin.isTowardsMenu(f);
            boolean z10 = true;
            if (getEscapeVelocity() <= Math.abs(f)) {
                z = true;
            } else {
                z = false;
            }
            double eventTime = (double) (motionEvent.getEventTime() - motionEvent.getDownTime());
            if (notificationMenuRowPlugin.canBeDismissed() || eventTime < 200.0d) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (!isTowardsMenu || isDismissGesture2) {
                z3 = false;
            } else {
                z3 = true;
            }
            if (!z || z2) {
                z4 = true;
            } else {
                z4 = false;
            }
            if (swipedFarEnough() || !notificationMenuRowPlugin.isSwipedEnoughToShowMenu()) {
                z5 = false;
            } else {
                z5 = true;
            }
            if (!z5 || !z4) {
                z6 = false;
            } else {
                z6 = true;
            }
            if (!z || isTowardsMenu || isDismissGesture2) {
                z7 = false;
            } else {
                z7 = true;
            }
            if (notificationMenuRowPlugin.shouldShowGutsOnSnapOpen() || (this.mIsExpanded && !this.mPulsing)) {
                z8 = true;
            } else {
                z8 = false;
            }
            if (z6 || (z7 && z8)) {
                z9 = true;
            } else {
                z9 = false;
            }
            int menuSnapTarget = notificationMenuRowPlugin.getMenuSnapTarget();
            if (isFalseGesture() || !z9) {
                z10 = false;
            }
            if ((z3 || z10) && menuSnapTarget != 0) {
                snapChild(view, (float) menuSnapTarget, f);
                notificationMenuRowPlugin.onSnapOpen();
            } else if (!isDismissGesture(motionEvent) || isTowardsMenu) {
                snapClosed(view, f);
                notificationMenuRowPlugin.onSnapClosed();
            } else {
                dismiss(view, f);
                notificationMenuRowPlugin.onDismiss();
            }
        }
    }

    @VisibleForTesting
    public void initializeRow(SwipeableView swipeableView) {
        if (swipeableView.hasFinishedInitialization()) {
            NotificationMenuRowPlugin createMenu = swipeableView.createMenu();
            setCurrentMenuRow(createMenu);
            if (createMenu != null) {
                createMenu.setMenuClickListener(this.mMenuListener);
                createMenu.onTouchStart();
            }
        }
    }

    public final void resetExposedMenuView(boolean z, boolean z2) {
        Animator animator;
        if (shouldResetMenu(z2)) {
            View view = this.mMenuExposedView;
            if (z) {
                if (view instanceof ExpandableNotificationRow) {
                    animator = ((ExpandableNotificationRow) view).getTranslateViewAnimator(0.0f, (ValueAnimator.AnimatorUpdateListener) null);
                } else {
                    animator = superGetViewTranslationAnimator(view, 0.0f, (ValueAnimator.AnimatorUpdateListener) null);
                }
                if (animator != null) {
                    animator.start();
                }
            } else if (view instanceof SwipeableView) {
                SwipeableView swipeableView = (SwipeableView) view;
                if (!swipeableView.isRemoved()) {
                    swipeableView.resetTranslation();
                }
            }
            this.mMenuExposedView = null;
        }
    }

    public final void snapChild(View view, float f, float f2) {
        superSnapChild(view, f, f2);
        NotificationStackScrollLayoutController.C13777 r1 = (NotificationStackScrollLayoutController.C13777) this.mCallback;
        Objects.requireNonNull(r1);
        NotificationStackScrollLayoutController.this.mFalsingCollector.onNotificationStopDismissing();
        if (f == 0.0f) {
            handleMenuCoveredOrDismissed();
        }
    }

    @VisibleForTesting
    public void setTranslatingParentView(View view) {
        this.mTranslatingParentView = view;
    }

    @VisibleForTesting
    public Runnable getFalsingCheck() {
        return this.mFalsingCheck;
    }

    @VisibleForTesting
    public Handler getHandler() {
        return this.mHandler;
    }
}
