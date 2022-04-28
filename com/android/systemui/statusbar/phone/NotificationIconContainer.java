package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import androidx.collection.ArrayMap;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda4;
import com.android.settingslib.Utils;
import com.android.systemui.statusbar.AlphaOptimizedFrameLayout;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.stack.AnimationFilter;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ViewState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class NotificationIconContainer extends AlphaOptimizedFrameLayout {
    public static final C14704 ADD_ICON_PROPERTIES;
    public static final C14671 DOT_ANIMATION_PROPERTIES;
    public static final C14682 ICON_ANIMATION_PROPERTIES;
    public static final C14726 UNISOLATION_PROPERTY;
    public static final C14715 UNISOLATION_PROPERTY_OTHERS;
    public static final C14693 sTempProperties = new AnimationProperties() {
        public AnimationFilter mAnimationFilter = new AnimationFilter();

        public final AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    };
    public int[] mAbsolutePosition = new int[2];
    public int mActualLayoutWidth = Integer.MIN_VALUE;
    public float mActualPaddingEnd = -2.14748365E9f;
    public float mActualPaddingStart = -2.14748365E9f;
    public int mAddAnimationStartIndex = -1;
    public boolean mAnimationsEnabled = true;
    public int mCannedAnimationStartIndex = -1;
    public boolean mChangingViewPositions;
    public boolean mDisallowNextAnimation;
    public int mDotPadding;
    public boolean mDozing;
    public IconState mFirstVisibleIconState;
    public int mIconSize;
    public final HashMap<View, IconState> mIconStates = new HashMap<>();
    public boolean mInNotificationIconShelf;
    public boolean mIsStaticLayout = true;
    public StatusBarIconView mIsolatedIcon;
    public StatusBarIconView mIsolatedIconForAnimation;
    public Rect mIsolatedIconLocation;
    public int mNumDots;
    public boolean mOnLockScreen;
    public int mOverflowWidth;
    public ArrayMap<String, ArrayList<StatusBarIcon>> mReplacingIcons;
    public int mSpeedBumpIndex = -1;
    public int mStaticDotDiameter;
    public int mThemedTextColorPrimary;
    public float mVisualOverflowStart;

    public class IconState extends ViewState {
        public static final /* synthetic */ int $r8$clinit = 0;
        public float clampedAppearAmount = 1.0f;
        public float iconAppearAmount = 1.0f;
        public int iconColor = 0;
        public boolean justAdded = true;
        public boolean justReplaced;
        public final ShellTaskOrganizer$$ExternalSyntheticLambda0 mCannedAnimationEndListener;
        public final View mView;
        public boolean needsCannedAnimation;
        public boolean noAnimations;
        public int visibleState;

        public IconState(View view) {
            this.mView = view;
            this.mCannedAnimationEndListener = new ShellTaskOrganizer$$ExternalSyntheticLambda0(this, 3);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0020, code lost:
            r2 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0013, code lost:
            if (r0.mVisibleState != 1) goto L_0x0015;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x001e, code lost:
            if (r0.mVisibleState == 2) goto L_0x0020;
         */
        /* JADX WARNING: Failed to insert additional move for type inference */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x0081  */
        /* JADX WARNING: Removed duplicated region for block: B:54:0x00a5  */
        /* JADX WARNING: Removed duplicated region for block: B:71:0x010e  */
        /* JADX WARNING: Removed duplicated region for block: B:82:0x0143  */
        /* JADX WARNING: Removed duplicated region for block: B:93:0x0166  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void applyToView(android.view.View r14) {
            /*
                r13 = this;
                boolean r0 = r14 instanceof com.android.systemui.statusbar.StatusBarIconView
                r1 = 0
                if (r0 == 0) goto L_0x019e
                r0 = r14
                com.android.systemui.statusbar.StatusBarIconView r0 = (com.android.systemui.statusbar.StatusBarIconView) r0
                int r2 = r13.visibleState
                r8 = 2
                r9 = 1
                if (r2 != r8) goto L_0x0015
                java.util.Objects.requireNonNull(r0)
                int r2 = r0.mVisibleState
                if (r2 == r9) goto L_0x0020
            L_0x0015:
                int r2 = r13.visibleState
                if (r2 != r9) goto L_0x0022
                java.util.Objects.requireNonNull(r0)
                int r2 = r0.mVisibleState
                if (r2 != r8) goto L_0x0022
            L_0x0020:
                r2 = r9
                goto L_0x0023
            L_0x0022:
                r2 = r1
            L_0x0023:
                com.android.systemui.statusbar.phone.NotificationIconContainer r3 = com.android.systemui.statusbar.phone.NotificationIconContainer.this
                com.android.systemui.statusbar.phone.NotificationIconContainer$1 r4 = com.android.systemui.statusbar.phone.NotificationIconContainer.DOT_ANIMATION_PROPERTIES
                java.util.Objects.requireNonNull(r3)
                boolean r4 = r3.mAnimationsEnabled
                if (r4 != 0) goto L_0x0035
                com.android.systemui.statusbar.StatusBarIconView r3 = r3.mIsolatedIcon
                if (r0 != r3) goto L_0x0033
                goto L_0x0035
            L_0x0033:
                r3 = r1
                goto L_0x0036
            L_0x0035:
                r3 = r9
            L_0x0036:
                if (r3 == 0) goto L_0x0046
                com.android.systemui.statusbar.phone.NotificationIconContainer r3 = com.android.systemui.statusbar.phone.NotificationIconContainer.this
                boolean r3 = r3.mDisallowNextAnimation
                if (r3 != 0) goto L_0x0046
                boolean r3 = r13.noAnimations
                if (r3 != 0) goto L_0x0046
                if (r2 != 0) goto L_0x0046
                r10 = r9
                goto L_0x0047
            L_0x0046:
                r10 = r1
            L_0x0047:
                r11 = 0
                if (r10 == 0) goto L_0x0169
                boolean r2 = r13.justAdded
                if (r2 != 0) goto L_0x005f
                boolean r2 = r13.justReplaced
                if (r2 == 0) goto L_0x0053
                goto L_0x005f
            L_0x0053:
                int r2 = r13.visibleState
                java.util.Objects.requireNonNull(r0)
                int r3 = r0.mVisibleState
                if (r2 == r3) goto L_0x007d
                com.android.systemui.statusbar.phone.NotificationIconContainer$1 r2 = com.android.systemui.statusbar.phone.NotificationIconContainer.DOT_ANIMATION_PROPERTIES
                goto L_0x007b
            L_0x005f:
                super.applyToView(r0)
                boolean r2 = r13.justAdded
                if (r2 == 0) goto L_0x007d
                float r2 = r13.iconAppearAmount
                r3 = 0
                int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                if (r2 == 0) goto L_0x007d
                r0.setAlpha(r3)
                r5 = 0
                r6 = 0
                r3 = 2
                r4 = 0
                r2 = r0
                r2.setVisibleState(r3, r4, r5, r6)
                com.android.systemui.statusbar.phone.NotificationIconContainer$4 r2 = com.android.systemui.statusbar.phone.NotificationIconContainer.ADD_ICON_PROPERTIES
            L_0x007b:
                r3 = r9
                goto L_0x007f
            L_0x007d:
                r3 = r1
                r2 = r11
            L_0x007f:
                if (r3 != 0) goto L_0x009f
                com.android.systemui.statusbar.phone.NotificationIconContainer r4 = com.android.systemui.statusbar.phone.NotificationIconContainer.this
                int r5 = r4.mAddAnimationStartIndex
                if (r5 < 0) goto L_0x009f
                int r4 = r4.indexOfChild(r14)
                com.android.systemui.statusbar.phone.NotificationIconContainer r5 = com.android.systemui.statusbar.phone.NotificationIconContainer.this
                int r5 = r5.mAddAnimationStartIndex
                if (r4 < r5) goto L_0x009f
                java.util.Objects.requireNonNull(r0)
                int r4 = r0.mVisibleState
                if (r4 != r8) goto L_0x009c
                int r4 = r13.visibleState
                if (r4 == r8) goto L_0x009f
            L_0x009c:
                com.android.systemui.statusbar.phone.NotificationIconContainer$1 r2 = com.android.systemui.statusbar.phone.NotificationIconContainer.DOT_ANIMATION_PROPERTIES
                r3 = r9
            L_0x009f:
                boolean r4 = r13.needsCannedAnimation
                r5 = 100
                if (r4 == 0) goto L_0x010c
                com.android.systemui.statusbar.phone.NotificationIconContainer$3 r3 = com.android.systemui.statusbar.phone.NotificationIconContainer.sTempProperties
                java.util.Objects.requireNonNull(r3)
                com.android.systemui.statusbar.notification.stack.AnimationFilter r4 = r3.mAnimationFilter
                r4.reset()
                com.android.systemui.statusbar.phone.NotificationIconContainer$2 r7 = com.android.systemui.statusbar.phone.NotificationIconContainer.ICON_ANIMATION_PROPERTIES
                java.util.Objects.requireNonNull(r7)
                com.android.systemui.statusbar.notification.stack.AnimationFilter r12 = r7.mAnimationFilter
                r4.combineFilter(r12)
                java.util.Objects.requireNonNull(r3)
                r3.mInterpolatorMap = r11
                android.util.ArrayMap<android.util.Property, android.view.animation.Interpolator> r7 = r7.mInterpolatorMap
                if (r7 == 0) goto L_0x00ce
                android.util.ArrayMap r12 = new android.util.ArrayMap
                r12.<init>()
                r3.mInterpolatorMap = r12
                android.util.ArrayMap<android.util.Property, android.view.animation.Interpolator> r12 = r3.mInterpolatorMap
                r12.putAll(r7)
            L_0x00ce:
                java.util.Objects.requireNonNull(r0)
                boolean r7 = r0.mShowsConversation
                if (r7 == 0) goto L_0x00d8
                android.view.animation.PathInterpolator r7 = com.android.systemui.animation.Interpolators.ICON_OVERSHOT_LESS
                goto L_0x00da
            L_0x00d8:
                android.view.animation.PathInterpolator r7 = com.android.systemui.animation.Interpolators.ICON_OVERSHOT
            L_0x00da:
                android.util.Property r12 = android.view.View.TRANSLATION_Y
                r3.setCustomInterpolator(r12, r7)
                com.android.wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda0 r7 = r13.mCannedAnimationEndListener
                r3.mAnimationEndAction = r7
                if (r2 == 0) goto L_0x0100
                com.android.systemui.statusbar.notification.stack.AnimationFilter r7 = r2.getAnimationFilter()
                r4.combineFilter(r7)
                android.util.ArrayMap<android.util.Property, android.view.animation.Interpolator> r2 = r2.mInterpolatorMap
                if (r2 == 0) goto L_0x0100
                android.util.ArrayMap<android.util.Property, android.view.animation.Interpolator> r4 = r3.mInterpolatorMap
                if (r4 != 0) goto L_0x00fb
                android.util.ArrayMap r4 = new android.util.ArrayMap
                r4.<init>()
                r3.mInterpolatorMap = r4
            L_0x00fb:
                android.util.ArrayMap<android.util.Property, android.view.animation.Interpolator> r4 = r3.mInterpolatorMap
                r4.putAll(r2)
            L_0x0100:
                r3.duration = r5
                com.android.systemui.statusbar.phone.NotificationIconContainer r2 = com.android.systemui.statusbar.phone.NotificationIconContainer.this
                int r4 = r2.indexOfChild(r14)
                r2.mCannedAnimationStartIndex = r4
                r2 = r3
                r3 = r9
            L_0x010c:
                if (r3 != 0) goto L_0x013d
                com.android.systemui.statusbar.phone.NotificationIconContainer r4 = com.android.systemui.statusbar.phone.NotificationIconContainer.this
                int r7 = r4.mCannedAnimationStartIndex
                if (r7 < 0) goto L_0x013d
                int r4 = r4.indexOfChild(r14)
                com.android.systemui.statusbar.phone.NotificationIconContainer r7 = com.android.systemui.statusbar.phone.NotificationIconContainer.this
                int r7 = r7.mCannedAnimationStartIndex
                if (r4 <= r7) goto L_0x013d
                java.util.Objects.requireNonNull(r0)
                int r4 = r0.mVisibleState
                if (r4 != r8) goto L_0x0129
                int r4 = r13.visibleState
                if (r4 == r8) goto L_0x013d
            L_0x0129:
                com.android.systemui.statusbar.phone.NotificationIconContainer$3 r2 = com.android.systemui.statusbar.phone.NotificationIconContainer.sTempProperties
                java.util.Objects.requireNonNull(r2)
                com.android.systemui.statusbar.notification.stack.AnimationFilter r3 = r2.mAnimationFilter
                r3.reset()
                r3.animateX = r9
                java.util.Objects.requireNonNull(r2)
                r2.mInterpolatorMap = r11
                r2.duration = r5
                r3 = r9
            L_0x013d:
                com.android.systemui.statusbar.phone.NotificationIconContainer r4 = com.android.systemui.statusbar.phone.NotificationIconContainer.this
                com.android.systemui.statusbar.StatusBarIconView r7 = r4.mIsolatedIconForAnimation
                if (r7 == 0) goto L_0x0166
                r2 = 0
                if (r14 != r7) goto L_0x0155
                com.android.systemui.statusbar.phone.NotificationIconContainer$6 r7 = com.android.systemui.statusbar.phone.NotificationIconContainer.UNISOLATION_PROPERTY
                com.android.systemui.statusbar.StatusBarIconView r4 = r4.mIsolatedIcon
                if (r4 == 0) goto L_0x014e
                goto L_0x014f
            L_0x014e:
                r5 = r2
            L_0x014f:
                java.util.Objects.requireNonNull(r7)
                r7.delay = r5
                goto L_0x0162
            L_0x0155:
                com.android.systemui.statusbar.phone.NotificationIconContainer$5 r7 = com.android.systemui.statusbar.phone.NotificationIconContainer.UNISOLATION_PROPERTY_OTHERS
                com.android.systemui.statusbar.StatusBarIconView r4 = r4.mIsolatedIcon
                if (r4 != 0) goto L_0x015c
                goto L_0x015d
            L_0x015c:
                r5 = r2
            L_0x015d:
                java.util.Objects.requireNonNull(r7)
                r7.delay = r5
            L_0x0162:
                r2 = r7
                r8 = r2
                r12 = r9
                goto L_0x016b
            L_0x0166:
                r8 = r2
                r12 = r3
                goto L_0x016b
            L_0x0169:
                r12 = r1
                r8 = r11
            L_0x016b:
                int r3 = r13.visibleState
                java.util.Objects.requireNonNull(r0)
                r5 = 0
                r6 = 0
                r2 = r0
                r4 = r10
                r2.setVisibleState(r3, r4, r5, r6)
                com.android.systemui.statusbar.phone.NotificationIconContainer r2 = com.android.systemui.statusbar.phone.NotificationIconContainer.this
                boolean r3 = r2.mInNotificationIconShelf
                if (r3 == 0) goto L_0x0181
                int r2 = r2.mThemedTextColorPrimary
                goto L_0x0183
            L_0x0181:
                int r2 = r13.iconColor
            L_0x0183:
                boolean r3 = r13.needsCannedAnimation
                if (r3 == 0) goto L_0x018a
                if (r10 == 0) goto L_0x018a
                goto L_0x018b
            L_0x018a:
                r9 = r1
            L_0x018b:
                r0.setIconColor(r2, r9)
                if (r12 == 0) goto L_0x0194
                r13.animateTo(r0, r8)
                goto L_0x0197
            L_0x0194:
                super.applyToView(r14)
            L_0x0197:
                com.android.systemui.statusbar.phone.NotificationIconContainer$3 r14 = com.android.systemui.statusbar.phone.NotificationIconContainer.sTempProperties
                java.util.Objects.requireNonNull(r14)
                r14.mAnimationEndAction = r11
            L_0x019e:
                r13.justAdded = r1
                r13.justReplaced = r1
                r13.needsCannedAnimation = r1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationIconContainer.IconState.applyToView(android.view.View):void");
        }

        public final void initFrom(View view) {
            super.initFrom(view);
            if (view instanceof StatusBarIconView) {
                this.iconColor = ((StatusBarIconView) view).mDrawableColor;
            }
        }
    }

    public final void applyIconStates() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            ViewState viewState = this.mIconStates.get(childAt);
            if (viewState != null) {
                viewState.applyToView(childAt);
            }
        }
        this.mAddAnimationStartIndex = -1;
        this.mCannedAnimationStartIndex = -1;
        this.mDisallowNextAnimation = false;
        this.mIsolatedIconForAnimation = null;
    }

    public final void resetViewStates() {
        float f;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            ViewState viewState = this.mIconStates.get(childAt);
            viewState.initFrom(childAt);
            StatusBarIconView statusBarIconView = this.mIsolatedIcon;
            if (statusBarIconView == null || childAt == statusBarIconView) {
                f = 1.0f;
            } else {
                f = 0.0f;
            }
            viewState.alpha = f;
            viewState.hidden = false;
        }
    }

    static {
        C14671 r0 = new AnimationProperties() {
            public AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateX = true;
                this.mAnimationFilter = animationFilter;
            }

            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        };
        r0.duration = 200;
        DOT_ANIMATION_PROPERTIES = r0;
        C14682 r02 = new AnimationProperties() {
            public AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateX = true;
                animationFilter.animateY = true;
                animationFilter.animateAlpha = true;
                animationFilter.mAnimatedProperties.add(View.SCALE_X);
                animationFilter.mAnimatedProperties.add(View.SCALE_Y);
                this.mAnimationFilter = animationFilter;
            }

            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        };
        r02.duration = 100;
        ICON_ANIMATION_PROPERTIES = r02;
        C14704 r03 = new AnimationProperties() {
            public AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateAlpha = true;
                this.mAnimationFilter = animationFilter;
            }

            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        };
        r03.duration = 200;
        r03.delay = 50;
        ADD_ICON_PROPERTIES = r03;
        C14715 r04 = new AnimationProperties() {
            public AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateAlpha = true;
                this.mAnimationFilter = animationFilter;
            }

            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        };
        r04.duration = 110;
        UNISOLATION_PROPERTY_OTHERS = r04;
        C14726 r05 = new AnimationProperties() {
            public AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateX = true;
                this.mAnimationFilter = animationFilter;
            }

            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        };
        r05.duration = 110;
        UNISOLATION_PROPERTY = r05;
    }

    public final void calculateIconTranslations() {
        int i;
        IconState iconState;
        boolean z;
        boolean z2;
        float f;
        int i2;
        float f2;
        boolean z3;
        float actualPaddingStart = getActualPaddingStart();
        int childCount = getChildCount();
        if (this.mOnLockScreen) {
            i = 3;
        } else if (this.mIsStaticLayout) {
            i = 4;
        } else {
            i = childCount;
        }
        float layoutEnd = getLayoutEnd();
        float layoutEnd2 = getLayoutEnd() - ((float) this.mOverflowWidth);
        float f3 = 0.0f;
        this.mVisualOverflowStart = 0.0f;
        this.mFirstVisibleIconState = null;
        int i3 = -1;
        int i4 = 0;
        while (i4 < childCount) {
            View childAt = getChildAt(i4);
            IconState iconState2 = this.mIconStates.get(childAt);
            float f4 = iconState2.iconAppearAmount;
            if (f4 == 1.0f) {
                iconState2.xTranslation = actualPaddingStart;
            }
            if (this.mFirstVisibleIconState == null) {
                this.mFirstVisibleIconState = iconState2;
            }
            int i5 = this.mSpeedBumpIndex;
            if ((i5 == -1 || i4 < i5 || f4 <= f3) && i4 < i) {
                z = false;
            } else {
                z = true;
            }
            if (i4 == childCount - 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!this.mOnLockScreen || !(childAt instanceof StatusBarIconView)) {
                f = 1.0f;
            } else {
                StatusBarIconView statusBarIconView = (StatusBarIconView) childAt;
                Objects.requireNonNull(statusBarIconView);
                f = ((float) statusBarIconView.mStatusBarIconDrawingSizeIncreased) / ((float) statusBarIconView.mStatusBarIconDrawingSize);
            }
            if (iconState2.hidden) {
                i2 = 2;
            } else {
                i2 = 0;
            }
            iconState2.visibleState = i2;
            if (z2) {
                f2 = layoutEnd - ((float) this.mIconSize);
            } else {
                f2 = layoutEnd2 - ((float) this.mIconSize);
            }
            if (actualPaddingStart > f2) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (i3 == -1 && (z || z3)) {
                if (!z2 || z) {
                    i3 = i4;
                } else {
                    i3 = i4 - 1;
                }
                float f5 = layoutEnd - ((float) this.mOverflowWidth);
                this.mVisualOverflowStart = f5;
                if (z || this.mIsStaticLayout) {
                    this.mVisualOverflowStart = Math.min(actualPaddingStart, f5);
                }
            }
            actualPaddingStart += iconState2.iconAppearAmount * ((float) childAt.getWidth()) * f;
            i4++;
            f3 = 0.0f;
        }
        this.mNumDots = 0;
        if (i3 != -1) {
            float f6 = this.mVisualOverflowStart;
            while (i3 < childCount) {
                IconState iconState3 = this.mIconStates.get(getChildAt(i3));
                int i6 = this.mStaticDotDiameter + this.mDotPadding;
                iconState3.xTranslation = f6;
                int i7 = this.mNumDots;
                if (i7 < 1) {
                    if (i7 != 0 || iconState3.iconAppearAmount >= 0.8f) {
                        iconState3.visibleState = 1;
                        this.mNumDots = i7 + 1;
                    } else {
                        iconState3.visibleState = 0;
                    }
                    if (this.mNumDots == 1) {
                        i6 *= 1;
                    }
                    f6 = (((float) i6) * iconState3.iconAppearAmount) + f6;
                } else {
                    iconState3.visibleState = 2;
                }
                i3++;
            }
        } else if (childCount > 0) {
            IconState iconState4 = this.mIconStates.get(getChildAt(childCount - 1));
            this.mFirstVisibleIconState = this.mIconStates.get(getChildAt(0));
        }
        if (isLayoutRtl()) {
            for (int i8 = 0; i8 < childCount; i8++) {
                View childAt2 = getChildAt(i8);
                IconState iconState5 = this.mIconStates.get(childAt2);
                iconState5.xTranslation = (((float) getWidth()) - iconState5.xTranslation) - ((float) childAt2.getWidth());
            }
        }
        StatusBarIconView statusBarIconView2 = this.mIsolatedIcon;
        if (statusBarIconView2 != null && (iconState = this.mIconStates.get(statusBarIconView2)) != null) {
            StatusBarIconView statusBarIconView3 = this.mIsolatedIcon;
            Objects.requireNonNull(statusBarIconView3);
            iconState.xTranslation = ((float) (this.mIsolatedIconLocation.left - this.mAbsolutePosition[0])) - (((1.0f - statusBarIconView3.mIconScale) * ((float) this.mIsolatedIcon.getWidth())) / 2.0f);
            iconState.visibleState = 0;
        }
    }

    public final float getActualPaddingStart() {
        float f = this.mActualPaddingStart;
        if (f == -2.14748365E9f) {
            return (float) getPaddingStart();
        }
        return f;
    }

    public final float getLayoutEnd() {
        int i = this.mActualLayoutWidth;
        if (i == Integer.MIN_VALUE) {
            i = getWidth();
        }
        float f = (float) i;
        float f2 = this.mActualPaddingEnd;
        if (f2 == -2.14748365E9f) {
            f2 = (float) getPaddingEnd();
        }
        return f - f2;
    }

    public final boolean isReplacingIcon(View view) {
        if (this.mReplacingIcons == null || !(view instanceof StatusBarIconView)) {
            return false;
        }
        StatusBarIconView statusBarIconView = (StatusBarIconView) view;
        Objects.requireNonNull(statusBarIconView);
        Icon icon = statusBarIconView.mIcon.icon;
        String groupKey = statusBarIconView.mNotification.getGroupKey();
        ArrayMap<String, ArrayList<StatusBarIcon>> arrayMap = this.mReplacingIcons;
        Objects.requireNonNull(arrayMap);
        ArrayList orDefault = arrayMap.getOrDefault(groupKey, null);
        if (orDefault == null || !icon.sameAs(((StatusBarIcon) orDefault.get(0)).icon)) {
            return false;
        }
        return true;
    }

    public final void setAnimationsEnabled(boolean z) {
        if (!z && this.mAnimationsEnabled) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                ViewState viewState = this.mIconStates.get(childAt);
                if (viewState != null) {
                    viewState.cancelAnimations(childAt);
                    viewState.applyToView(childAt);
                }
            }
        }
        this.mAnimationsEnabled = z;
    }

    public NotificationIconContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initDimens();
        setWillNotDraw(true);
    }

    public final void initDimens() {
        this.mDotPadding = getResources().getDimensionPixelSize(C1777R.dimen.overflow_icon_dot_padding);
        this.mStaticDotDiameter = getResources().getDimensionPixelSize(C1777R.dimen.overflow_dot_radius) * 2;
        this.mThemedTextColorPrimary = Utils.getColorAttr(new ContextThemeWrapper(getContext(), 16974563), 16842806).getDefaultColor();
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initDimens();
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(getActualPaddingStart(), 0.0f, getLayoutEnd(), (float) getHeight(), paint);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float height = ((float) getHeight()) / 2.0f;
        this.mIconSize = 0;
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            int i6 = (int) (height - (((float) measuredHeight) / 2.0f));
            childAt.layout(0, i6, measuredWidth, measuredHeight + i6);
            if (i5 == 0) {
                int width = childAt.getWidth();
                this.mIconSize = width;
                this.mOverflowWidth = ((this.mStaticDotDiameter + this.mDotPadding) * 0) + width;
            }
        }
        getLocationOnScreen(this.mAbsolutePosition);
        if (this.mIsStaticLayout) {
            resetViewStates();
            calculateIconTranslations();
            applyIconStates();
        }
    }

    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        boolean isReplacingIcon = isReplacingIcon(view);
        if (!this.mChangingViewPositions) {
            IconState iconState = new IconState(view);
            if (isReplacingIcon) {
                iconState.justAdded = false;
                iconState.justReplaced = true;
            }
            this.mIconStates.put(view, iconState);
        }
        int indexOfChild = indexOfChild(view);
        if (indexOfChild < getChildCount() - 1 && !isReplacingIcon && this.mIconStates.get(getChildAt(indexOfChild + 1)).iconAppearAmount > 0.0f) {
            int i = this.mAddAnimationStartIndex;
            if (i < 0) {
                this.mAddAnimationStartIndex = indexOfChild;
            } else {
                this.mAddAnimationStartIndex = Math.min(i, indexOfChild);
            }
        }
        if (view instanceof StatusBarIconView) {
            ((StatusBarIconView) view).setDozing(this.mDozing, false);
        }
    }

    public final void onViewRemoved(View view) {
        boolean z;
        boolean z2;
        long j;
        super.onViewRemoved(view);
        if (view instanceof StatusBarIconView) {
            boolean isReplacingIcon = isReplacingIcon(view);
            StatusBarIconView statusBarIconView = (StatusBarIconView) view;
            boolean z3 = false;
            if (this.mAnimationsEnabled || statusBarIconView == this.mIsolatedIcon) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                Objects.requireNonNull(statusBarIconView);
                if (statusBarIconView.mVisibleState != 2 && view.getVisibility() == 0 && isReplacingIcon) {
                    float translationX = statusBarIconView.getTranslationX();
                    int i = 0;
                    while (true) {
                        if (i >= getChildCount()) {
                            i = getChildCount();
                            break;
                        } else if (getChildAt(i).getTranslationX() > translationX) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    int i2 = this.mAddAnimationStartIndex;
                    if (i2 < 0) {
                        this.mAddAnimationStartIndex = i;
                    } else {
                        this.mAddAnimationStartIndex = Math.min(i2, i);
                    }
                }
            }
            if (!this.mChangingViewPositions) {
                this.mIconStates.remove(view);
                if (this.mAnimationsEnabled || statusBarIconView == this.mIsolatedIcon) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2 && !isReplacingIcon) {
                    addTransientView(statusBarIconView, 0);
                    if (view == this.mIsolatedIcon) {
                        z3 = true;
                    }
                    PipTaskOrganizer$$ExternalSyntheticLambda4 pipTaskOrganizer$$ExternalSyntheticLambda4 = new PipTaskOrganizer$$ExternalSyntheticLambda4(this, statusBarIconView, 3);
                    if (z3) {
                        j = 110;
                    } else {
                        j = 0;
                    }
                    statusBarIconView.setVisibleState(2, true, pipTaskOrganizer$$ExternalSyntheticLambda4, j);
                }
            }
        }
    }
}
