package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.PathInterpolator;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.policy.SystemBarUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import java.util.Objects;

public class NotificationShelf extends ActivatableNotificationView implements View.OnLayoutChangeListener, StatusBarStateController.StateListener {
    public static final PathInterpolator ICON_ALPHA_INTERPOLATOR = new PathInterpolator(0.6f, 0.0f, 0.6f, 0.0f);
    public float mActualWidth = -1.0f;
    public AmbientState mAmbientState;
    public boolean mAnimationsEnabled = true;
    public Rect mClipRect = new Rect();
    public NotificationIconContainer mCollapsedIcons;
    public NotificationShelfController mController;
    public float mCornerAnimationDistance;
    public float mFirstElementRoundness;
    public float mFractionToShade;
    public boolean mHasItemsInStableShelf;
    public boolean mHideBackground;
    public NotificationStackScrollLayoutController mHostLayoutController;
    public int mIndexOfFirstViewInShelf = -1;
    public boolean mInteractive;
    public int mNotGoneIndex;
    public int mPaddingBetweenElements;
    public int mScrollFastThreshold;
    public NotificationIconContainer mShelfIcons;
    public boolean mShowNotificationShelf;
    public int mStatusBarState;
    public int[] mTmp = new int[2];

    public class ShelfState extends ExpandableViewState {
        public ExpandableView firstViewInShelf;
        public boolean hasItemsInStableShelf;

        public ShelfState() {
        }

        public final void animateTo(View view, AnimationProperties animationProperties) {
            if (NotificationShelf.this.mShowNotificationShelf) {
                super.animateTo(view, animationProperties);
                NotificationShelf notificationShelf = NotificationShelf.this;
                ExpandableView expandableView = this.firstViewInShelf;
                Objects.requireNonNull(notificationShelf);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationShelf.mHostLayoutController;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                notificationShelf.mIndexOfFirstViewInShelf = notificationStackScrollLayoutController.mView.indexOfChild(expandableView);
                NotificationShelf.this.updateAppearance();
                NotificationShelf notificationShelf2 = NotificationShelf.this;
                boolean z = this.hasItemsInStableShelf;
                Objects.requireNonNull(notificationShelf2);
                if (notificationShelf2.mHasItemsInStableShelf != z) {
                    notificationShelf2.mHasItemsInStableShelf = z;
                    notificationShelf2.updateInteractiveness();
                }
                NotificationShelf notificationShelf3 = NotificationShelf.this;
                notificationShelf3.mShelfIcons.setAnimationsEnabled(notificationShelf3.mAnimationsEnabled);
            }
        }

        public final void applyToView(View view) {
            if (NotificationShelf.this.mShowNotificationShelf) {
                super.applyToView(view);
                NotificationShelf notificationShelf = NotificationShelf.this;
                ExpandableView expandableView = this.firstViewInShelf;
                Objects.requireNonNull(notificationShelf);
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationShelf.mHostLayoutController;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                notificationShelf.mIndexOfFirstViewInShelf = notificationStackScrollLayoutController.mView.indexOfChild(expandableView);
                NotificationShelf.this.updateAppearance();
                NotificationShelf notificationShelf2 = NotificationShelf.this;
                boolean z = this.hasItemsInStableShelf;
                Objects.requireNonNull(notificationShelf2);
                if (notificationShelf2.mHasItemsInStableShelf != z) {
                    notificationShelf2.mHasItemsInStableShelf = z;
                    notificationShelf2.updateInteractiveness();
                }
                NotificationShelf notificationShelf3 = NotificationShelf.this;
                notificationShelf3.mShelfIcons.setAnimationsEnabled(notificationShelf3.mAnimationsEnabled);
            }
        }
    }

    public final boolean hasOverlappingRendering() {
        return false;
    }

    @VisibleForTesting
    public boolean isXInView(float f, float f2, float f3, float f4) {
        return f3 - f2 <= f && f < f4 + f2;
    }

    @VisibleForTesting
    public boolean isYInView(float f, float f2, float f3, float f4) {
        return f3 - f2 <= f && f < f4 + f2;
    }

    public final ExpandableViewState createExpandableViewState() {
        return new ShelfState();
    }

    public final boolean needsOutline() {
        if (this.mHideBackground || !super.needsOutline()) {
            return false;
        }
        return true;
    }

    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        NotificationIconContainer notificationIconContainer = this.mCollapsedIcons;
        if (notificationIconContainer != null) {
            notificationIconContainer.getLocationOnScreen(this.mTmp);
        }
        getLocationOnScreen(this.mTmp);
    }

    public final void onStateChanged(int i) {
        this.mStatusBarState = i;
        updateInteractiveness();
    }

    public final void setFakeShadowIntensity(float f, float f2, int i, int i2) {
        if (!this.mHasItemsInStableShelf) {
            f = 0.0f;
        }
        super.setFakeShadowIntensity(f, f2, i, i2);
    }

    @VisibleForTesting
    public void updateActualWidth(float f, float f2) {
        float f3;
        if (this.mAmbientState.isOnKeyguard()) {
            f3 = MathUtils.lerp(f2, (float) getWidth(), f);
        } else {
            f3 = (float) getWidth();
        }
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        if (notificationBackgroundView != null) {
            Objects.requireNonNull(notificationBackgroundView);
            notificationBackgroundView.mActualWidth = (int) f3;
        }
        NotificationIconContainer notificationIconContainer = this.mShelfIcons;
        if (notificationIconContainer != null) {
            Objects.requireNonNull(notificationIconContainer);
            notificationIconContainer.mActualLayoutWidth = (int) f3;
        }
        this.mActualWidth = f3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0220, code lost:
        if (r14 == false) goto L_0x0222;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0294, code lost:
        if (r10 == false) goto L_0x0298;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0051, code lost:
        if (java.lang.Math.abs(r7.mExpandingVelocity) > ((float) r0.mScrollFastThreshold)) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0159, code lost:
        if (r10 >= r2) goto L_0x01a3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0204  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x0211  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0234  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0241  */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x0254  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x02c4  */
    /* JADX WARNING: Removed duplicated region for block: B:168:0x02d7  */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x02d9  */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x02dc  */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x02e6  */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x02f2  */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x0306  */
    /* JADX WARNING: Removed duplicated region for block: B:181:0x0308  */
    /* JADX WARNING: Removed duplicated region for block: B:195:0x033e  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x037e  */
    /* JADX WARNING: Removed duplicated region for block: B:226:0x03bd  */
    /* JADX WARNING: Removed duplicated region for block: B:233:0x03fb  */
    /* JADX WARNING: Removed duplicated region for block: B:236:0x0413  */
    /* JADX WARNING: Removed duplicated region for block: B:250:0x0445  */
    /* JADX WARNING: Removed duplicated region for block: B:251:0x0447  */
    /* JADX WARNING: Removed duplicated region for block: B:254:0x044c  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:274:0x0488  */
    /* JADX WARNING: Removed duplicated region for block: B:306:0x0544  */
    /* JADX WARNING: Removed duplicated region for block: B:318:0x057a  */
    /* JADX WARNING: Removed duplicated region for block: B:319:0x057c  */
    /* JADX WARNING: Removed duplicated region for block: B:322:0x0590  */
    /* JADX WARNING: Removed duplicated region for block: B:323:0x0592  */
    /* JADX WARNING: Removed duplicated region for block: B:333:0x05c1  */
    /* JADX WARNING: Removed duplicated region for block: B:334:0x05c3  */
    /* JADX WARNING: Removed duplicated region for block: B:337:0x05d9  */
    /* JADX WARNING: Removed duplicated region for block: B:341:0x0602  */
    /* JADX WARNING: Removed duplicated region for block: B:356:0x064a  */
    /* JADX WARNING: Removed duplicated region for block: B:357:0x064c  */
    /* JADX WARNING: Removed duplicated region for block: B:364:0x066f  */
    /* JADX WARNING: Removed duplicated region for block: B:373:0x0688  */
    /* JADX WARNING: Removed duplicated region for block: B:380:0x0560 A[EDGE_INSN: B:380:0x0560->B:310:0x0560 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:390:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01bb  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01bf  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x01f5  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01fc  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x01ff  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateAppearance() {
        /*
            r32 = this;
            r0 = r32
            boolean r1 = r0.mShowNotificationShelf
            if (r1 != 0) goto L_0x0007
            return
        L_0x0007:
            com.android.systemui.statusbar.phone.NotificationIconContainer r1 = r0.mShelfIcons
            r1.resetViewStates()
            float r1 = r32.getTranslationY()
            com.android.systemui.statusbar.notification.stack.AmbientState r2 = r0.mAmbientState
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.notification.row.ExpandableView r2 = r2.mLastVisibleBackgroundChild
            r3 = -1
            r0.mNotGoneIndex = r3
            boolean r4 = r0.mHideBackground
            r5 = 0
            if (r4 == 0) goto L_0x0029
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r4 = r0.mViewState
            com.android.systemui.statusbar.NotificationShelf$ShelfState r4 = (com.android.systemui.statusbar.NotificationShelf.ShelfState) r4
            boolean r4 = r4.hasItemsInStableShelf
            if (r4 != 0) goto L_0x0029
            r4 = 1
            goto L_0x002a
        L_0x0029:
            r4 = r5
        L_0x002a:
            com.android.systemui.statusbar.notification.stack.AmbientState r7 = r0.mAmbientState
            java.util.Objects.requireNonNull(r7)
            float r7 = r7.mCurrentScrollVelocity
            int r8 = r0.mScrollFastThreshold
            float r8 = (float) r8
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 > 0) goto L_0x0056
            com.android.systemui.statusbar.notification.stack.AmbientState r7 = r0.mAmbientState
            java.util.Objects.requireNonNull(r7)
            boolean r7 = r7.mExpansionChanging
            if (r7 == 0) goto L_0x0054
            com.android.systemui.statusbar.notification.stack.AmbientState r7 = r0.mAmbientState
            java.util.Objects.requireNonNull(r7)
            float r7 = r7.mExpandingVelocity
            float r7 = java.lang.Math.abs(r7)
            int r8 = r0.mScrollFastThreshold
            float r8 = (float) r8
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 <= 0) goto L_0x0054
            goto L_0x0056
        L_0x0054:
            r7 = r5
            goto L_0x0057
        L_0x0056:
            r7 = 1
        L_0x0057:
            com.android.systemui.statusbar.notification.stack.AmbientState r8 = r0.mAmbientState
            java.util.Objects.requireNonNull(r8)
            boolean r8 = r8.mExpansionChanging
            if (r8 == 0) goto L_0x006b
            com.android.systemui.statusbar.notification.stack.AmbientState r8 = r0.mAmbientState
            java.util.Objects.requireNonNull(r8)
            boolean r8 = r8.mPanelTracking
            if (r8 != 0) goto L_0x006b
            r8 = 1
            goto L_0x006c
        L_0x006b:
            r8 = r5
        L_0x006c:
            com.android.systemui.statusbar.notification.stack.AmbientState r9 = r0.mAmbientState
            java.util.Objects.requireNonNull(r9)
            r10 = r5
            r11 = r10
            r14 = r11
            r15 = r14
            r16 = r15
            r18 = r16
            r19 = r18
            r12 = 0
            r13 = 0
            r17 = 0
        L_0x007f:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = r0.mHostLayoutController
            int r6 = r6.getChildCount()
            r9 = 8
            if (r10 >= r6) goto L_0x052d
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = r0.mHostLayoutController
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r6.mView
            android.view.View r6 = r6.getChildAt(r10)
            com.android.systemui.statusbar.notification.row.ExpandableView r6 = (com.android.systemui.statusbar.notification.row.ExpandableView) r6
            boolean r22 = r6.needsClippingToShelf()
            if (r22 == 0) goto L_0x04ff
            int r3 = r6.getVisibility()
            if (r3 != r9) goto L_0x00a4
            goto L_0x04ff
        L_0x00a4:
            int r3 = com.android.systemui.statusbar.notification.stack.ViewState.TAG_ANIMATOR_TRANSLATION_Z
            java.lang.Object r3 = r6.getTag(r3)
            android.animation.ValueAnimator r3 = (android.animation.ValueAnimator) r3
            if (r3 != 0) goto L_0x00b3
            float r3 = r6.getTranslationZ()
            goto L_0x00bf
        L_0x00b3:
            int r3 = com.android.systemui.statusbar.notification.stack.ViewState.TAG_END_TRANSLATION_Z
            java.lang.Object r3 = r6.getTag(r3)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
        L_0x00bf:
            float r9 = (float) r5
            int r3 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r3 > 0) goto L_0x00cd
            boolean r3 = r6.isPinned()
            if (r3 == 0) goto L_0x00cb
            goto L_0x00cd
        L_0x00cb:
            r3 = r5
            goto L_0x00ce
        L_0x00cd:
            r3 = 1
        L_0x00ce:
            if (r6 != r2) goto L_0x00d3
            r23 = 1
            goto L_0x00d5
        L_0x00d3:
            r23 = r5
        L_0x00d5:
            float r24 = r6.getTranslationY()
            float r25 = r6.getTranslationY()
            int r5 = r6.mActualHeight
            r27 = r2
            int r2 = r0.mPaddingBetweenElements
            int r5 = r5 + r2
            android.view.View r2 = r6.getShelfTransformationTarget()
            if (r2 != 0) goto L_0x00ef
            float r2 = r6.getTranslationY()
            goto L_0x0105
        L_0x00ef:
            float r28 = r6.getTranslationY()
            int r2 = r6.getRelativeTopPadding(r2)
            float r2 = (float) r2
            float r28 = r28 + r2
            com.android.systemui.statusbar.StatusBarIconView r2 = r6.getShelfIcon()
            int r2 = r2.getTop()
            float r2 = (float) r2
            float r2 = r28 - r2
        L_0x0105:
            r28 = r13
            float r13 = (float) r5
            float r13 = r13 + r25
            float r13 = r13 - r2
            r29 = r14
            int r14 = r32.getHeight()
            float r14 = (float) r14
            float r13 = java.lang.Math.min(r13, r14)
            if (r23 == 0) goto L_0x0138
            r14 = 0
            int r26 = r6.getMinHeight(r14)
            int r30 = r32.getHeight()
            r31 = r15
            int r15 = r26 - r30
            int r5 = java.lang.Math.min(r5, r15)
            int r15 = r6.getMinHeight(r14)
            int r14 = r32.getHeight()
            int r15 = r15 - r14
            float r14 = (float) r15
            float r13 = java.lang.Math.min(r13, r14)
            goto L_0x013a
        L_0x0138:
            r31 = r15
        L_0x013a:
            float r5 = (float) r5
            float r14 = r25 + r5
            float r15 = r32.getTranslationY()
            r30 = r12
            com.android.systemui.statusbar.notification.stack.AmbientState r12 = r0.mAmbientState
            java.util.Objects.requireNonNull(r12)
            boolean r12 = r12.mExpansionChanging
            if (r12 == 0) goto L_0x015c
            com.android.systemui.statusbar.notification.stack.AmbientState r12 = r0.mAmbientState
            boolean r12 = r12.isOnKeyguard()
            if (r12 != 0) goto L_0x015c
            int r2 = r0.mIndexOfFirstViewInShelf
            r5 = -1
            if (r2 == r5) goto L_0x01a6
            if (r10 < r2) goto L_0x01a6
            goto L_0x01a3
        L_0x015c:
            int r12 = (r14 > r15 ? 1 : (r14 == r15 ? 0 : -1))
            if (r12 < 0) goto L_0x01a6
            com.android.systemui.statusbar.notification.stack.AmbientState r12 = r0.mAmbientState
            java.util.Objects.requireNonNull(r12)
            boolean r12 = r12.mUnlockHintRunning
            if (r12 == 0) goto L_0x016d
            boolean r12 = r6.mInShelf
            if (r12 == 0) goto L_0x01a6
        L_0x016d:
            com.android.systemui.statusbar.notification.stack.AmbientState r12 = r0.mAmbientState
            java.util.Objects.requireNonNull(r12)
            boolean r12 = r12.mShadeExpanded
            if (r12 != 0) goto L_0x0182
            boolean r12 = r6.isPinned()
            if (r12 != 0) goto L_0x01a6
            boolean r12 = r6.isHeadsUpAnimatingAway()
            if (r12 != 0) goto L_0x01a6
        L_0x0182:
            int r12 = (r25 > r15 ? 1 : (r25 == r15 ? 0 : -1))
            if (r12 >= 0) goto L_0x01a3
            float r12 = r15 - r25
            float r5 = r12 / r5
            r14 = 1065353216(0x3f800000, float:1.0)
            float r5 = java.lang.Math.min(r14, r5)
            float r5 = r14 - r5
            if (r23 == 0) goto L_0x0198
            float r2 = r2 - r25
            float r12 = r12 / r2
            goto L_0x019b
        L_0x0198:
            float r15 = r15 - r2
            float r12 = r15 / r13
        L_0x019b:
            r2 = 0
            float r12 = android.util.MathUtils.constrain(r12, r2, r14)
            float r2 = r14 - r12
            goto L_0x01a8
        L_0x01a3:
            r2 = 1065353216(0x3f800000, float:1.0)
            goto L_0x01a7
        L_0x01a6:
            r2 = 0
        L_0x01a7:
            r5 = r2
        L_0x01a8:
            com.android.systemui.statusbar.StatusBarIconView r12 = r6.getShelfIcon()
            com.android.systemui.statusbar.phone.NotificationIconContainer r13 = r0.mShelfIcons
            java.util.Objects.requireNonNull(r13)
            java.util.HashMap<android.view.View, com.android.systemui.statusbar.phone.NotificationIconContainer$IconState> r13 = r13.mIconStates
            java.lang.Object r13 = r13.get(r12)
            com.android.systemui.statusbar.phone.NotificationIconContainer$IconState r13 = (com.android.systemui.statusbar.phone.NotificationIconContainer.IconState) r13
            if (r13 != 0) goto L_0x01bf
            r25 = r10
            goto L_0x0319
        L_0x01bf:
            r14 = 1056964608(0x3f000000, float:0.5)
            int r14 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1))
            if (r14 > 0) goto L_0x01f7
            android.view.View r14 = r6.getShelfTransformationTarget()
            if (r14 != 0) goto L_0x01ce
            r25 = r10
            goto L_0x01f1
        L_0x01ce:
            float r15 = r6.getTranslationY()
            r25 = r10
            float r10 = r6.mContentTranslation
            float r15 = r15 + r10
            int r10 = r6.getRelativeTopPadding(r14)
            float r10 = (float) r10
            float r15 = r15 + r10
            int r10 = r14.getHeight()
            float r10 = (float) r10
            float r15 = r15 + r10
            float r10 = r32.getTranslationY()
            int r14 = r0.mPaddingBetweenElements
            float r14 = (float) r14
            float r10 = r10 - r14
            int r10 = (r15 > r10 ? 1 : (r15 == r10 ? 0 : -1))
            if (r10 < 0) goto L_0x01f1
            r10 = 1
            goto L_0x01f2
        L_0x01f1:
            r10 = 0
        L_0x01f2:
            if (r10 == 0) goto L_0x01f5
            goto L_0x01f9
        L_0x01f5:
            r10 = 0
            goto L_0x01fa
        L_0x01f7:
            r25 = r10
        L_0x01f9:
            r10 = 1
        L_0x01fa:
            if (r10 == 0) goto L_0x01ff
            r10 = 1065353216(0x3f800000, float:1.0)
            goto L_0x0200
        L_0x01ff:
            r10 = 0
        L_0x0200:
            int r14 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r14 != 0) goto L_0x020f
            if (r7 != 0) goto L_0x0208
            if (r8 == 0) goto L_0x020c
        L_0x0208:
            if (r23 != 0) goto L_0x020c
            r14 = 1
            goto L_0x020d
        L_0x020c:
            r14 = 0
        L_0x020d:
            r13.noAnimations = r14
        L_0x020f:
            if (r23 != 0) goto L_0x0228
            if (r7 != 0) goto L_0x0222
            if (r8 == 0) goto L_0x0228
            int r14 = com.android.systemui.statusbar.notification.stack.ViewState.TAG_ANIMATOR_TRANSLATION_Y
            java.lang.Object r14 = r12.getTag(r14)
            if (r14 == 0) goto L_0x021f
            r14 = 1
            goto L_0x0220
        L_0x021f:
            r14 = 0
        L_0x0220:
            if (r14 != 0) goto L_0x0228
        L_0x0222:
            r13.cancelAnimations(r12)
            r12 = 1
            r13.noAnimations = r12
        L_0x0228:
            com.android.systemui.statusbar.notification.stack.AmbientState r12 = r0.mAmbientState
            boolean r12 = r12.isHiddenAtAll()
            if (r12 == 0) goto L_0x0241
            boolean r12 = r6.mInShelf
            if (r12 != 0) goto L_0x0241
            com.android.systemui.statusbar.notification.stack.AmbientState r2 = r0.mAmbientState
            boolean r2 = r2.isFullyHidden()
            if (r2 == 0) goto L_0x023f
            r2 = 1065353216(0x3f800000, float:1.0)
            goto L_0x024c
        L_0x023f:
            r2 = 0
            goto L_0x024c
        L_0x0241:
            float r12 = r13.clampedAppearAmount
            int r12 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r12 == 0) goto L_0x0249
            r12 = 1
            goto L_0x024a
        L_0x0249:
            r12 = 0
        L_0x024a:
            r13.needsCannedAnimation = r12
        L_0x024c:
            r13.clampedAppearAmount = r10
            boolean r10 = r6 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r10 != 0) goto L_0x0254
            goto L_0x0319
        L_0x0254:
            r12 = r6
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r12 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r12
            com.android.systemui.statusbar.StatusBarIconView r13 = r12.getShelfIcon()
            com.android.systemui.statusbar.phone.NotificationIconContainer r14 = r0.mShelfIcons
            java.util.Objects.requireNonNull(r14)
            java.util.HashMap<android.view.View, com.android.systemui.statusbar.phone.NotificationIconContainer$IconState> r14 = r14.mIconStates
            java.lang.Object r14 = r14.get(r13)
            com.android.systemui.statusbar.phone.NotificationIconContainer$IconState r14 = (com.android.systemui.statusbar.phone.NotificationIconContainer.IconState) r14
            if (r14 != 0) goto L_0x026c
            goto L_0x0319
        L_0x026c:
            android.view.animation.PathInterpolator r15 = ICON_ALPHA_INTERPOLATOR
            float r15 = r15.getInterpolation(r2)
            r14.alpha = r15
            boolean r15 = r12.mDrawingAppearAnimation
            if (r15 == 0) goto L_0x027e
            boolean r15 = r12.mInShelf
            if (r15 != 0) goto L_0x027e
            r15 = 1
            goto L_0x027f
        L_0x027e:
            r15 = 0
        L_0x027f:
            if (r15 != 0) goto L_0x02bf
            if (r10 == 0) goto L_0x0297
            boolean r10 = r12.mIsLowPriority
            if (r10 == 0) goto L_0x0297
            com.android.systemui.statusbar.phone.NotificationIconContainer r10 = r0.mShelfIcons
            java.util.Objects.requireNonNull(r10)
            int r10 = r10.mNumDots
            r15 = 1
            if (r10 < r15) goto L_0x0293
            r10 = r15
            goto L_0x0294
        L_0x0293:
            r10 = 0
        L_0x0294:
            if (r10 != 0) goto L_0x02bf
            goto L_0x0298
        L_0x0297:
            r15 = 1
        L_0x0298:
            r10 = 0
            int r20 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r20 != 0) goto L_0x02a3
            boolean r10 = r14.isAnimating(r13)
            if (r10 == 0) goto L_0x02bf
        L_0x02a3:
            boolean r10 = r12.isAboveShelf()
            if (r10 != 0) goto L_0x02bf
            boolean r10 = r12.showingPulsing()
            if (r10 != 0) goto L_0x02bf
            float r10 = r12.getTranslationZ()
            com.android.systemui.statusbar.notification.stack.AmbientState r15 = r0.mAmbientState
            java.util.Objects.requireNonNull(r15)
            int r9 = (r10 > r9 ? 1 : (r10 == r9 ? 0 : -1))
            if (r9 <= 0) goto L_0x02bd
            goto L_0x02bf
        L_0x02bd:
            r9 = 0
            goto L_0x02c0
        L_0x02bf:
            r9 = 1
        L_0x02c0:
            r14.hidden = r9
            if (r9 == 0) goto L_0x02c5
            r2 = 0
        L_0x02c5:
            r14.iconAppearAmount = r2
            com.android.systemui.statusbar.phone.NotificationIconContainer r2 = r0.mShelfIcons
            float r2 = r2.getActualPaddingStart()
            r14.xTranslation = r2
            boolean r2 = r12.mInShelf
            if (r2 == 0) goto L_0x02d9
            boolean r2 = r12.mTransformingInShelf
            if (r2 != 0) goto L_0x02d9
            r2 = 1
            goto L_0x02da
        L_0x02d9:
            r2 = 0
        L_0x02da:
            if (r2 == 0) goto L_0x02e6
            r2 = 1065353216(0x3f800000, float:1.0)
            r14.iconAppearAmount = r2
            r14.alpha = r2
            r2 = 0
            r14.hidden = r2
            goto L_0x02e7
        L_0x02e6:
            r2 = 0
        L_0x02e7:
            int r9 = r0.calculateBgColor(r2, r2)
            java.util.Objects.requireNonNull(r13)
            int r2 = r13.mCachedContrastBackgroundColor
            if (r2 == r9) goto L_0x02f7
            r13.mCachedContrastBackgroundColor = r9
            r13.updateContrastedStaticColor()
        L_0x02f7:
            int r2 = r13.mContrastedDrawableColor
            boolean r9 = r12.areGutsExposed()
            if (r9 == 0) goto L_0x0300
            goto L_0x0308
        L_0x0300:
            android.view.View r9 = r12.getShelfTransformationTarget()
            if (r9 == 0) goto L_0x0308
            r9 = 1
            goto L_0x0309
        L_0x0308:
            r9 = 0
        L_0x0309:
            if (r9 == 0) goto L_0x0317
            if (r2 == 0) goto L_0x0317
            int r9 = r12.getOriginalIconColor()
            float r10 = r14.iconAppearAmount
            int r2 = com.android.systemui.R$array.interpolateColors(r9, r2, r10)
        L_0x0317:
            r14.iconColor = r2
        L_0x0319:
            if (r23 == 0) goto L_0x031f
            boolean r2 = r6.mInShelf
            if (r2 == 0) goto L_0x032a
        L_0x031f:
            if (r3 != 0) goto L_0x032a
            if (r4 == 0) goto L_0x0324
            goto L_0x032a
        L_0x0324:
            int r2 = r0.mPaddingBetweenElements
            float r2 = (float) r2
            float r2 = r1 - r2
            goto L_0x0330
        L_0x032a:
            int r2 = r32.getHeight()
            float r2 = (float) r2
            float r2 = r2 + r1
        L_0x0330:
            r10 = r18
            int r2 = r0.updateNotificationClipHeight(r6, r2, r10)
            int r11 = java.lang.Math.max(r2, r11)
            boolean r2 = r6 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r2 == 0) goto L_0x03fb
            r2 = r6
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r2 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r2
            float r12 = r30 + r5
            r9 = 0
            int r13 = r2.calculateBgColor(r9, r9)
            int r14 = (r24 > r1 ? 1 : (r24 == r1 ? 0 : -1))
            if (r14 < 0) goto L_0x036a
            int r14 = r0.mNotGoneIndex
            r15 = -1
            if (r14 != r15) goto L_0x036a
            r0.mNotGoneIndex = r10
            int r14 = r0.mBgTint
            r15 = r31
            if (r15 == r14) goto L_0x035e
            r0.mBgTint = r15
            r0.updateBackgroundTint(r9)
        L_0x035e:
            r9 = r16
            r14 = r17
            r0.setOverrideTintColor(r9, r14)
            r16 = r4
            r17 = r7
            goto L_0x037b
        L_0x036a:
            r9 = r16
            r14 = r17
            r15 = r31
            r16 = r4
            int r4 = r0.mNotGoneIndex
            r17 = r7
            r7 = -1
            if (r4 != r7) goto L_0x037b
            r14 = r5
            goto L_0x037c
        L_0x037b:
            r15 = r9
        L_0x037c:
            if (r23 == 0) goto L_0x03ae
            com.android.systemui.statusbar.NotificationShelfController r4 = r0.mController
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.notification.stack.AmbientState r7 = r4.mAmbientState
            java.util.Objects.requireNonNull(r7)
            boolean r7 = r7.mShadeExpanded
            if (r7 == 0) goto L_0x039e
            com.android.systemui.statusbar.notification.stack.AmbientState r7 = r4.mAmbientState
            boolean r7 = r7.isOnKeyguard()
            if (r7 == 0) goto L_0x039c
            com.android.systemui.statusbar.phone.KeyguardBypassController r4 = r4.mKeyguardBypassController
            boolean r4 = r4.getBypassEnabled()
            if (r4 != 0) goto L_0x039e
        L_0x039c:
            r4 = 1
            goto L_0x039f
        L_0x039e:
            r4 = 0
        L_0x039f:
            if (r4 == 0) goto L_0x03ae
            if (r19 != 0) goto L_0x03a5
            r4 = r13
            goto L_0x03a7
        L_0x03a5:
            r4 = r19
        L_0x03a7:
            r2.setOverrideTintColor(r4, r5)
            r7 = r4
            r4 = 0
            r5 = 0
            goto L_0x03b4
        L_0x03ae:
            r4 = 0
            r5 = 0
            r2.setOverrideTintColor(r5, r4)
            r7 = r13
        L_0x03b4:
            if (r10 != 0) goto L_0x03b8
            if (r3 != 0) goto L_0x03bb
        L_0x03b8:
            r2.setAboveShelf(r5)
        L_0x03bb:
            if (r10 != 0) goto L_0x03ed
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r2.mEntry
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.icon.IconPack r3 = r3.mIcons
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.StatusBarIconView r3 = r3.mShelfIcon
            com.android.systemui.statusbar.phone.NotificationIconContainer r5 = r0.mShelfIcons
            java.util.Objects.requireNonNull(r5)
            java.util.HashMap<android.view.View, com.android.systemui.statusbar.phone.NotificationIconContainer$IconState> r5 = r5.mIconStates
            java.lang.Object r3 = r5.get(r3)
            com.android.systemui.statusbar.phone.NotificationIconContainer$IconState r3 = (com.android.systemui.statusbar.phone.NotificationIconContainer.IconState) r3
            if (r3 == 0) goto L_0x03ed
            float r3 = r3.clampedAppearAmount
            r5 = 1065353216(0x3f800000, float:1.0)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x03ed
            float r3 = r6.getTranslationY()
            float r5 = r32.getTranslationY()
            float r3 = r3 - r5
            int r3 = (int) r3
            float r2 = r2.mCurrentTopRoundness
            goto L_0x03f1
        L_0x03ed:
            r2 = r28
            r3 = r29
        L_0x03f1:
            int r18 = r10 + 1
            r19 = r7
            r9 = r15
            r15 = r13
            r13 = r2
            r2 = r14
            r14 = r3
            goto L_0x040f
        L_0x03fb:
            r9 = r16
            r14 = r17
            r15 = r31
            r16 = r4
            r17 = r7
            r4 = 0
            r18 = r10
            r2 = r14
            r13 = r28
            r14 = r29
            r12 = r30
        L_0x040f:
            boolean r3 = r6 instanceof com.android.systemui.statusbar.notification.row.ActivatableNotificationView
            if (r3 == 0) goto L_0x04fd
            com.android.systemui.statusbar.notification.row.ActivatableNotificationView r6 = (com.android.systemui.statusbar.notification.row.ActivatableNotificationView) r6
            com.android.systemui.statusbar.notification.stack.AmbientState r3 = r0.mAmbientState
            boolean r3 = r3.isOnKeyguard()
            if (r3 != 0) goto L_0x0433
            com.android.systemui.statusbar.notification.stack.AmbientState r3 = r0.mAmbientState
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r3.mShadeExpanded
            if (r3 != 0) goto L_0x0433
            boolean r3 = r6 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r3 == 0) goto L_0x0433
            r3 = r6
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r3
            boolean r3 = r3.mIsHeadsUp
            if (r3 == 0) goto L_0x0433
            r3 = 1
            goto L_0x0434
        L_0x0433:
            r3 = 0
        L_0x0434:
            com.android.systemui.statusbar.notification.stack.AmbientState r5 = r0.mAmbientState
            java.util.Objects.requireNonNull(r5)
            boolean r5 = r5.mShadeExpanded
            if (r5 == 0) goto L_0x0447
            com.android.systemui.statusbar.notification.stack.AmbientState r5 = r0.mAmbientState
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r5 = r5.getTrackedHeadsUpRow()
            if (r6 != r5) goto L_0x0447
            r5 = 1
            goto L_0x0448
        L_0x0447:
            r5 = 0
        L_0x0448:
            int r7 = (r24 > r1 ? 1 : (r24 == r1 ? 0 : -1))
            if (r7 >= 0) goto L_0x0483
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r7 = r0.mHostLayoutController
            java.util.Objects.requireNonNull(r7)
            com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager r7 = r7.mNotificationRoundnessManager
            com.android.systemui.statusbar.notification.row.ExpandableView r10 = r7.mSwipedView
            if (r6 == r10) goto L_0x0462
            com.android.systemui.statusbar.notification.row.ExpandableView r10 = r7.mViewBeforeSwipedView
            if (r6 == r10) goto L_0x0462
            com.android.systemui.statusbar.notification.row.ExpandableView r7 = r7.mViewAfterSwipedView
            if (r6 != r7) goto L_0x0460
            goto L_0x0462
        L_0x0460:
            r7 = 0
            goto L_0x0463
        L_0x0462:
            r7 = 1
        L_0x0463:
            if (r7 != 0) goto L_0x0483
            if (r3 != 0) goto L_0x0483
            if (r5 != 0) goto L_0x0483
            boolean r3 = r6.isAboveShelf()
            if (r3 != 0) goto L_0x0483
            com.android.systemui.statusbar.notification.stack.AmbientState r3 = r0.mAmbientState
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r3.mPulsing
            if (r3 != 0) goto L_0x0483
            com.android.systemui.statusbar.notification.stack.AmbientState r3 = r0.mAmbientState
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r3.mDozing
            if (r3 != 0) goto L_0x0483
            r3 = 1
            goto L_0x0484
        L_0x0483:
            r3 = 0
        L_0x0484:
            if (r3 != 0) goto L_0x0488
            goto L_0x04fd
        L_0x0488:
            android.content.res.Resources r3 = r32.getResources()
            r5 = 2131166626(0x7f0705a2, float:1.7947503E38)
            float r3 = r3.getDimension(r5)
            android.content.res.Resources r5 = r32.getResources()
            r7 = 2131166625(0x7f0705a1, float:1.79475E38)
            float r5 = r5.getDimension(r7)
            float r3 = r3 / r5
            int r5 = r6.mActualHeight
            float r5 = (float) r5
            float r5 = r5 + r24
            float r7 = r0.mCornerAnimationDistance
            com.android.systemui.statusbar.notification.stack.AmbientState r10 = r0.mAmbientState
            java.util.Objects.requireNonNull(r10)
            float r10 = r10.mExpansionFraction
            float r7 = r7 * r10
            float r10 = r1 - r7
            int r21 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r21 < 0) goto L_0x04c7
            float r5 = r5 - r10
            float r5 = r5 / r7
            float r5 = android.util.MathUtils.saturate(r5)
            boolean r4 = r6.mLastInSection
            if (r4 == 0) goto L_0x04c2
            r4 = 0
            r5 = 1065353216(0x3f800000, float:1.0)
            goto L_0x04c3
        L_0x04c2:
            r4 = 0
        L_0x04c3:
            r6.setBottomRoundness(r5, r4)
            goto L_0x04d7
        L_0x04c7:
            r4 = 0
            int r5 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r5 >= 0) goto L_0x04d7
            boolean r5 = r6.mLastInSection
            if (r5 == 0) goto L_0x04d3
            r5 = 1065353216(0x3f800000, float:1.0)
            goto L_0x04d4
        L_0x04d3:
            r5 = r3
        L_0x04d4:
            r6.setBottomRoundness(r5, r4)
        L_0x04d7:
            int r4 = (r24 > r10 ? 1 : (r24 == r10 ? 0 : -1))
            if (r4 < 0) goto L_0x04ee
            float r24 = r24 - r10
            float r24 = r24 / r7
            float r3 = android.util.MathUtils.saturate(r24)
            boolean r4 = r6.mFirstInSection
            if (r4 == 0) goto L_0x04e9
            r3 = 1065353216(0x3f800000, float:1.0)
        L_0x04e9:
            r4 = 0
            r6.setTopRoundness(r3, r4)
            goto L_0x051d
        L_0x04ee:
            r4 = 0
            int r5 = (r24 > r10 ? 1 : (r24 == r10 ? 0 : -1))
            if (r5 >= 0) goto L_0x051d
            boolean r5 = r6.mFirstInSection
            if (r5 == 0) goto L_0x04f9
            r3 = 1065353216(0x3f800000, float:1.0)
        L_0x04f9:
            r6.setTopRoundness(r3, r4)
            goto L_0x051d
        L_0x04fd:
            r4 = 0
            goto L_0x051d
        L_0x04ff:
            r27 = r2
            r25 = r10
            r30 = r12
            r28 = r13
            r29 = r14
            r9 = r16
            r14 = r17
            r10 = r18
            r16 = r4
            r4 = r5
            r17 = r7
            r18 = r10
            r2 = r14
            r13 = r28
            r14 = r29
            r12 = r30
        L_0x051d:
            int r10 = r25 + 1
            r5 = r4
            r4 = r16
            r7 = r17
            r3 = -1
            r17 = r2
            r16 = r9
            r2 = r27
            goto L_0x007f
        L_0x052d:
            r4 = r5
            r30 = r12
            r28 = r13
            r29 = r14
            r10 = r18
            r14 = r4
        L_0x0537:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r1 = r0.mHostLayoutController
            java.util.Objects.requireNonNull(r1)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r1 = r1.mView
            int r1 = r1.getTransientViewCount()
            if (r14 >= r1) goto L_0x0560
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r1 = r0.mHostLayoutController
            java.util.Objects.requireNonNull(r1)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r1 = r1.mView
            android.view.View r1 = r1.getTransientView(r14)
            boolean r2 = r1 instanceof com.android.systemui.statusbar.notification.row.ExpandableView
            if (r2 == 0) goto L_0x055d
            com.android.systemui.statusbar.notification.row.ExpandableView r1 = (com.android.systemui.statusbar.notification.row.ExpandableView) r1
            float r2 = r32.getTranslationY()
            r3 = -1
            r0.updateNotificationClipHeight(r1, r2, r3)
        L_0x055d:
            int r14 = r14 + 1
            goto L_0x0537
        L_0x0560:
            r0.setClipTopAmount(r11)
            com.android.systemui.statusbar.notification.stack.ExpandableViewState r1 = r0.mViewState
            boolean r1 = r1.hidden
            if (r1 != 0) goto L_0x057c
            int r1 = r32.getHeight()
            if (r11 >= r1) goto L_0x057c
            boolean r1 = r0.mShowNotificationShelf
            if (r1 == 0) goto L_0x057c
            r1 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r30 > r1 ? 1 : (r30 == r1 ? 0 : -1))
            if (r1 >= 0) goto L_0x057a
            goto L_0x057c
        L_0x057a:
            r14 = r4
            goto L_0x057d
        L_0x057c:
            r14 = 1
        L_0x057d:
            android.view.animation.PathInterpolator r1 = com.android.systemui.animation.Interpolators.STANDARD
            float r2 = r0.mFractionToShade
            float r1 = r1.getInterpolation(r2)
            com.android.systemui.statusbar.phone.NotificationIconContainer r2 = r0.mShelfIcons
            java.util.Objects.requireNonNull(r2)
            int r3 = r2.getChildCount()
            if (r3 != 0) goto L_0x0592
            r2 = 0
            goto L_0x05bc
        L_0x0592:
            r3 = 1082130432(0x40800000, float:4.0)
            int r3 = (r30 > r3 ? 1 : (r30 == r3 ? 0 : -1))
            if (r3 > 0) goto L_0x059e
            int r3 = r2.mIconSize
            float r3 = (float) r3
            float r12 = r30 * r3
            goto L_0x05a8
        L_0x059e:
            int r3 = r2.mIconSize
            int r3 = r3 * 3
            float r3 = (float) r3
            int r5 = r2.mOverflowWidth
            float r5 = (float) r5
            float r12 = r3 + r5
        L_0x05a8:
            float r3 = r2.getActualPaddingStart()
            float r3 = r3 + r12
            float r5 = r2.mActualPaddingEnd
            r6 = -822083584(0xffffffffcf000000, float:-2.14748365E9)
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 != 0) goto L_0x05ba
            int r2 = r2.getPaddingEnd()
            float r5 = (float) r2
        L_0x05ba:
            float r2 = r5 + r3
        L_0x05bc:
            r0.updateActualWidth(r1, r2)
            if (r14 == 0) goto L_0x05c3
            r1 = 4
            goto L_0x05c4
        L_0x05c3:
            r1 = r4
        L_0x05c4:
            r0.setVisibility(r1)
            com.android.systemui.statusbar.notification.row.NotificationBackgroundView r1 = r0.mBackgroundNormal
            java.util.Objects.requireNonNull(r1)
            r5 = r29
            r1.mBackgroundTop = r5
            r1.invalidate()
            float r1 = r0.mFirstElementRoundness
            int r1 = (r1 > r28 ? 1 : (r1 == r28 ? 0 : -1))
            if (r1 == 0) goto L_0x05dd
            r13 = r28
            r0.mFirstElementRoundness = r13
        L_0x05dd:
            com.android.systemui.statusbar.phone.NotificationIconContainer r1 = r0.mShelfIcons
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r2 = r0.mHostLayoutController
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r2 = r2.mView
            int r2 = r2.getSpeedBumpIndex()
            java.util.Objects.requireNonNull(r1)
            r1.mSpeedBumpIndex = r2
            com.android.systemui.statusbar.phone.NotificationIconContainer r1 = r0.mShelfIcons
            r1.calculateIconTranslations()
            com.android.systemui.statusbar.phone.NotificationIconContainer r1 = r0.mShelfIcons
            r1.applyIconStates()
            r1 = r4
        L_0x05fa:
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r2 = r0.mHostLayoutController
            int r2 = r2.getChildCount()
            if (r1 >= r2) goto L_0x066b
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r2 = r0.mHostLayoutController
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r2 = r2.mView
            android.view.View r2 = r2.getChildAt(r1)
            com.android.systemui.statusbar.notification.row.ExpandableView r2 = (com.android.systemui.statusbar.notification.row.ExpandableView) r2
            boolean r3 = r2 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r3 == 0) goto L_0x0668
            int r3 = r2.getVisibility()
            if (r3 != r9) goto L_0x061a
            goto L_0x0668
        L_0x061a:
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r2 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r2
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r2.mEntry
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.icon.IconPack r3 = r3.mIcons
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.StatusBarIconView r3 = r3.mShelfIcon
            int r5 = com.android.systemui.statusbar.notification.stack.ViewState.TAG_ANIMATOR_TRANSLATION_Y
            java.lang.Object r5 = r3.getTag(r5)
            if (r5 == 0) goto L_0x0632
            r5 = 1
            goto L_0x0633
        L_0x0632:
            r5 = r4
        L_0x0633:
            if (r5 == 0) goto L_0x0640
            com.android.systemui.statusbar.notification.stack.AmbientState r5 = r0.mAmbientState
            java.util.Objects.requireNonNull(r5)
            boolean r5 = r5.mDozing
            if (r5 != 0) goto L_0x0640
            r5 = 1
            goto L_0x0641
        L_0x0640:
            r5 = r4
        L_0x0641:
            r6 = 2131427748(0x7f0b01a4, float:1.847712E38)
            java.lang.Object r7 = r3.getTag(r6)
            if (r7 == 0) goto L_0x064c
            r7 = 1
            goto L_0x064d
        L_0x064c:
            r7 = r4
        L_0x064d:
            if (r5 == 0) goto L_0x0668
            if (r7 != 0) goto L_0x0668
            android.view.ViewTreeObserver r5 = r3.getViewTreeObserver()
            com.android.systemui.statusbar.NotificationShelf$1 r7 = new com.android.systemui.statusbar.NotificationShelf$1
            r7.<init>(r3, r5, r2)
            r5.addOnPreDrawListener(r7)
            com.android.systemui.statusbar.NotificationShelf$2 r2 = new com.android.systemui.statusbar.NotificationShelf$2
            r2.<init>(r5, r7)
            r3.addOnAttachStateChangeListener(r2)
            r3.setTag(r6, r7)
        L_0x0668:
            int r1 = r1 + 1
            goto L_0x05fa
        L_0x066b:
            boolean r1 = r0.mHideBackground
            if (r1 == r14) goto L_0x0683
            r0.mHideBackground = r14
            boolean r1 = r0.mCustomOutline
            if (r1 == 0) goto L_0x0676
            goto L_0x0683
        L_0x0676:
            boolean r1 = r32.needsOutline()
            if (r1 == 0) goto L_0x067f
            com.android.systemui.statusbar.notification.row.ExpandableOutlineView$1 r1 = r0.mProvider
            goto L_0x0680
        L_0x067f:
            r1 = 0
        L_0x0680:
            r0.setOutlineProvider(r1)
        L_0x0683:
            int r1 = r0.mNotGoneIndex
            r2 = -1
            if (r1 != r2) goto L_0x068a
            r0.mNotGoneIndex = r10
        L_0x068a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationShelf.updateAppearance():void");
    }

    public final void updateInteractiveness() {
        boolean z;
        int i = 1;
        if (this.mStatusBarState != 1 || !this.mHasItemsInStableShelf) {
            z = false;
        } else {
            z = true;
        }
        this.mInteractive = z;
        setClickable(z);
        setFocusable(this.mInteractive);
        if (!this.mInteractive) {
            i = 4;
        }
        setImportantForAccessibility(i);
    }

    public NotificationShelf(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void initDimens() {
        Resources resources = getResources();
        SystemBarUtils.getStatusBarHeight(this.mContext);
        this.mPaddingBetweenElements = resources.getDimensionPixelSize(C1777R.dimen.notification_divider_height);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = resources.getDimensionPixelOffset(C1777R.dimen.notification_shelf_height);
        setLayoutParams(layoutParams);
        int dimensionPixelOffset = resources.getDimensionPixelOffset(C1777R.dimen.shelf_icon_container_padding);
        this.mShelfIcons.setPadding(dimensionPixelOffset, 0, dimensionPixelOffset, 0);
        this.mScrollFastThreshold = resources.getDimensionPixelOffset(C1777R.dimen.scroll_fast_threshold);
        this.mShowNotificationShelf = resources.getBoolean(C1777R.bool.config_showNotificationShelf);
        this.mCornerAnimationDistance = (float) resources.getDimensionPixelSize(C1777R.dimen.notification_corner_animation_distance);
        NotificationIconContainer notificationIconContainer = this.mShelfIcons;
        Objects.requireNonNull(notificationIconContainer);
        notificationIconContainer.mInNotificationIconShelf = true;
        if (!this.mShowNotificationShelf) {
            setVisibility(8);
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initDimens();
    }

    @VisibleForTesting
    public void onFinishInflate() {
        super.onFinishInflate();
        NotificationIconContainer notificationIconContainer = (NotificationIconContainer) findViewById(C1777R.C1779id.content);
        this.mShelfIcons = notificationIconContainer;
        notificationIconContainer.setClipChildren(false);
        this.mShelfIcons.setClipToPadding(false);
        this.mClipToActualHeight = false;
        updateClipping();
        setClipChildren(false);
        setClipToPadding(false);
        NotificationIconContainer notificationIconContainer2 = this.mShelfIcons;
        Objects.requireNonNull(notificationIconContainer2);
        notificationIconContainer2.mIsStaticLayout = false;
        setBottomRoundness(1.0f, false);
        setTopRoundness(1.0f, false);
        this.mFirstInSection = true;
        initDimens();
    }

    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.mInteractive) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, getContext().getString(C1777R.string.accessibility_overflow_action)));
        }
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        NotificationIconContainer notificationIconContainer = this.mCollapsedIcons;
        if (notificationIconContainer != null) {
            notificationIconContainer.getLocationOnScreen(this.mTmp);
        }
        getLocationOnScreen(this.mTmp);
        int i5 = getResources().getDisplayMetrics().heightPixels;
        this.mClipRect.set(0, -i5, getWidth(), i5);
        NotificationIconContainer notificationIconContainer2 = this.mShelfIcons;
        if (notificationIconContainer2 != null) {
            notificationIconContainer2.setClipBounds(this.mClipRect);
        }
    }

    public final boolean pointInView(float f, float f2, float f3) {
        int i;
        float f4;
        float width = (float) getWidth();
        float f5 = this.mActualWidth;
        if (f5 > -1.0f) {
            i = (int) f5;
        } else {
            i = getWidth();
        }
        float f6 = (float) i;
        if (isLayoutRtl()) {
            f4 = width - f6;
        } else {
            f4 = 0.0f;
        }
        if (!isLayoutRtl()) {
            width = f6;
        }
        float f7 = (float) this.mClipTopAmount;
        float f8 = (float) this.mActualHeight;
        if (!isXInView(f, f3, f4, width) || !isYInView(f2, f3, f7, f8)) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0065 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int updateNotificationClipHeight(com.android.systemui.statusbar.notification.row.ExpandableView r6, float r7, int r8) {
        /*
            r5 = this;
            float r0 = r6.getTranslationY()
            int r1 = r6.mActualHeight
            float r1 = (float) r1
            float r0 = r0 + r1
            boolean r1 = r6.isPinned()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0016
            boolean r1 = r6.isHeadsUpAnimatingAway()
            if (r1 == 0) goto L_0x0020
        L_0x0016:
            com.android.systemui.statusbar.notification.stack.AmbientState r1 = r5.mAmbientState
            boolean r1 = r1.isDozingAndNotPulsing(r6)
            if (r1 != 0) goto L_0x0020
            r1 = r2
            goto L_0x0021
        L_0x0020:
            r1 = r3
        L_0x0021:
            com.android.systemui.statusbar.notification.stack.AmbientState r4 = r5.mAmbientState
            boolean r4 = r4.isPulseExpanding()
            if (r4 == 0) goto L_0x002e
            if (r8 != 0) goto L_0x002c
            goto L_0x0032
        L_0x002c:
            r2 = r3
            goto L_0x0032
        L_0x002e:
            boolean r2 = r6.showingPulsing()
        L_0x0032:
            int r8 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r8 <= 0) goto L_0x0059
            if (r2 != 0) goto L_0x0059
            com.android.systemui.statusbar.notification.stack.AmbientState r8 = r5.mAmbientState
            java.util.Objects.requireNonNull(r8)
            boolean r8 = r8.mShadeExpanded
            if (r8 != 0) goto L_0x0043
            if (r1 != 0) goto L_0x0059
        L_0x0043:
            float r7 = r0 - r7
            int r7 = (int) r7
            if (r1 == 0) goto L_0x0055
            int r8 = r6.getIntrinsicHeight()
            int r1 = r6.getCollapsedHeight()
            int r8 = r8 - r1
            int r7 = java.lang.Math.min(r8, r7)
        L_0x0055:
            r6.setClipBottomAmount(r7)
            goto L_0x005c
        L_0x0059:
            r6.setClipBottomAmount(r3)
        L_0x005c:
            if (r2 == 0) goto L_0x0065
            float r5 = r5.getTranslationY()
            float r0 = r0 - r5
            int r5 = (int) r0
            return r5
        L_0x0065:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationShelf.updateNotificationClipHeight(com.android.systemui.statusbar.notification.row.ExpandableView, float, int):int");
    }

    public final View getContentView() {
        return this.mShelfIcons;
    }
}
