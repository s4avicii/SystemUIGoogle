package com.android.systemui.p006qs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Trace;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda2;
import com.android.systemui.DejankUtils;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.media.MediaHost;
import com.android.systemui.p006qs.customize.QSCustomizer;
import com.android.systemui.p006qs.customize.QSCustomizerController;
import com.android.systemui.p006qs.dagger.QSFragmentComponent;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.plugins.p005qs.QSContainerController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.MultiUserSwitchController;
import com.android.systemui.statusbar.phone.MultiUserSwitchController$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.util.LifecycleFragment;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.qs.QSFragment */
public final class QSFragment extends LifecycleFragment implements C0961QS, CommandQueue.Callbacks, StatusBarStateController.StateListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final C09933 mAnimateHeaderSlidingInListener = new AnimatorListenerAdapter() {
        public final void onAnimationEnd(Animator animator) {
            QSFragment qSFragment = QSFragment.this;
            qSFragment.mHeaderAnimating = false;
            qSFragment.updateQsState();
            QSFragment.this.getView().animate().setListener((Animator.AnimatorListener) null);
        }
    };
    public final KeyguardBypassController mBypassController;
    public QSContainerImpl mContainer;
    public DumpManager mDumpManager;
    public final FalsingManager mFalsingManager;
    public QSFooter mFooter;
    public float mFullShadeProgress;
    public QuickStatusBarHeader mHeader;
    public boolean mHeaderAnimating;
    public boolean mInSplitShade;
    public float mLastHeaderTranslation;
    public boolean mLastKeyguardAndExpanded;
    public float mLastPanelFraction;
    public float mLastQSExpansion = -1.0f;
    public int mLastViewHeight;
    public int mLayoutDirection;
    public boolean mListening;
    public C0961QS.HeightListener mPanelView;
    public QSAnimator mQSAnimator;
    public QSContainerImplController mQSContainerImplController;
    public QSCustomizerController mQSCustomizerController;
    public FooterActionsController mQSFooterActionController;
    public QSPanelController mQSPanelController;
    public NonInterceptingScrollView mQSPanelScrollView;
    public QSSquishinessController mQSSquishinessController;
    public final MediaHost mQqsMediaHost;
    public final Rect mQsBounds = new Rect();
    public final QSFragmentComponent.Factory mQsComponentFactory;
    public boolean mQsDisabled;
    public boolean mQsExpanded;
    public final QSFragmentDisableFlagsLogger mQsFragmentDisableFlagsLogger;
    public final MediaHost mQsMediaHost;
    public QuickQSPanelController mQuickQSPanelController;
    public final RemoteInputQuickSettingsDisabler mRemoteInputQuickSettingsDisabler;
    public C0961QS.ScrollListener mScrollListener;
    public boolean mShowCollapsedOnKeyguard;
    public float mSquishinessFraction = 1.0f;
    public boolean mStackScrollerOverscrolling;
    public int mState;
    public final StatusBarStateController mStatusBarStateController;
    public int[] mTmpLocation = new int[2];
    public boolean mTransitioningToFullShade;

    public final void setHasNotifications(boolean z) {
    }

    public final void setHeaderClickable(boolean z) {
    }

    public final void setTransitionToFullShadeAmount(float f, float f2) {
        boolean z;
        if (f > 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (z != this.mTransitioningToFullShade) {
            this.mTransitioningToFullShade = z;
            updateShowCollapsedOnKeyguard();
        }
        this.mFullShadeProgress = f2;
        float f3 = this.mLastQSExpansion;
        float f4 = this.mLastPanelFraction;
        float f5 = this.mLastHeaderTranslation;
        if (!z) {
            f2 = this.mSquishinessFraction;
        }
        setQsExpansion(f3, f4, f5, f2);
    }

    public final void closeCustomizer() {
        this.mQSCustomizerController.hide();
    }

    public final void closeDetail() {
        QSPanelController qSPanelController = this.mQSPanelController;
        Objects.requireNonNull(qSPanelController);
        QSCustomizerController qSCustomizerController = qSPanelController.mQsCustomizerController;
        Objects.requireNonNull(qSCustomizerController);
        if (((QSCustomizer) qSCustomizerController.mView).isShown()) {
            qSPanelController.mQsCustomizerController.hide();
        }
    }

    public final int getDesiredHeight() {
        QSCustomizerController qSCustomizerController = this.mQSCustomizerController;
        Objects.requireNonNull(qSCustomizerController);
        if (((QSCustomizer) qSCustomizerController.mView).isCustomizing()) {
            return getView().getHeight();
        }
        return getView().getMeasuredHeight();
    }

    public final int getHeightDiff() {
        return this.mHeader.getPaddingBottom() + (this.mQSPanelScrollView.getBottom() - this.mHeader.getBottom());
    }

    public final int getQsMinExpansionHeight() {
        return this.mHeader.getHeight();
    }

    public final boolean isCustomizing() {
        QSCustomizerController qSCustomizerController = this.mQSCustomizerController;
        Objects.requireNonNull(qSCustomizerController);
        return ((QSCustomizer) qSCustomizerController.mView).isCustomizing();
    }

    public final boolean isFullyCollapsed() {
        float f = this.mLastQSExpansion;
        if (f == 0.0f || f == -1.0f) {
            return true;
        }
        return false;
    }

    public final boolean isKeyguardState() {
        if (this.mStatusBarStateController.getState() == 1) {
            return true;
        }
        return false;
    }

    public final boolean isShowingDetail() {
        QSCustomizerController qSCustomizerController = this.mQSCustomizerController;
        Objects.requireNonNull(qSCustomizerController);
        return ((QSCustomizer) qSCustomizerController.mView).isCustomizing();
    }

    public final void notifyCustomizeChanged() {
        int i;
        int i2;
        this.mContainer.updateExpansion();
        boolean isCustomizing = isCustomizing();
        NonInterceptingScrollView nonInterceptingScrollView = this.mQSPanelScrollView;
        int i3 = 0;
        if (!isCustomizing) {
            i = 0;
        } else {
            i = 4;
        }
        nonInterceptingScrollView.setVisibility(i);
        QSFooter qSFooter = this.mFooter;
        if (!isCustomizing) {
            i2 = 0;
        } else {
            i2 = 4;
        }
        qSFooter.setVisibility(i2);
        this.mQSFooterActionController.setVisible(!isCustomizing);
        QuickStatusBarHeader quickStatusBarHeader = this.mHeader;
        if (isCustomizing) {
            i3 = 4;
        }
        quickStatusBarHeader.setVisibility(i3);
        this.mPanelView.onQsHeightChanged();
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        try {
            Trace.beginSection("QSFragment#onCreateView");
            return layoutInflater.cloneInContext(new ContextThemeWrapper(getContext(), 2132018192)).inflate(C1777R.layout.qs_panel, viewGroup, false);
        } finally {
            Trace.endSection();
        }
    }

    public final void onStateChanged(int i) {
        this.mState = i;
        boolean z = true;
        if (i != 1) {
            z = false;
        }
        this.mLastQSExpansion = -1.0f;
        QSAnimator qSAnimator = this.mQSAnimator;
        if (qSAnimator != null) {
            qSAnimator.mOnKeyguard = z;
            qSAnimator.updateQQSVisibility();
            if (qSAnimator.mOnKeyguard) {
                qSAnimator.clearAnimationState();
            }
        }
        this.mFooter.setKeyguardShowing(z);
        FooterActionsController footerActionsController = this.mQSFooterActionController;
        Objects.requireNonNull(footerActionsController);
        footerActionsController.setExpansion(footerActionsController.lastExpansion);
        updateQsState();
        updateShowCollapsedOnKeyguard();
    }

    public final void onViewCreated(View view, Bundle bundle) {
        QSFragmentComponent create = this.mQsComponentFactory.create(this);
        this.mQSPanelController = create.getQSPanelController();
        this.mQuickQSPanelController = create.getQuickQSPanelController();
        this.mQSFooterActionController = create.getQSFooterActionController();
        this.mQSPanelController.init();
        this.mQuickQSPanelController.init();
        this.mQSFooterActionController.init();
        NonInterceptingScrollView nonInterceptingScrollView = (NonInterceptingScrollView) view.findViewById(C1777R.C1779id.expanded_qs_scroll_view);
        this.mQSPanelScrollView = nonInterceptingScrollView;
        nonInterceptingScrollView.addOnLayoutChangeListener(new QSFragment$$ExternalSyntheticLambda0(this));
        this.mQSPanelScrollView.setOnScrollChangeListener(new QSFragment$$ExternalSyntheticLambda2(this));
        this.mHeader = (QuickStatusBarHeader) view.findViewById(C1777R.C1779id.header);
        QSPanelController qSPanelController = this.mQSPanelController;
        Objects.requireNonNull(qSPanelController);
        QSPanel qSPanel = (QSPanel) qSPanelController.mView;
        Objects.requireNonNull(qSPanel);
        qSPanel.mHeaderContainer = (ViewGroup) view.findViewById(C1777R.C1779id.header_text_container);
        this.mFooter = create.getQSFooter();
        QSContainerImplController qSContainerImplController = create.getQSContainerImplController();
        this.mQSContainerImplController = qSContainerImplController;
        qSContainerImplController.init();
        QSContainerImplController qSContainerImplController2 = this.mQSContainerImplController;
        Objects.requireNonNull(qSContainerImplController2);
        QSContainerImpl qSContainerImpl = (QSContainerImpl) qSContainerImplController2.mView;
        this.mContainer = qSContainerImpl;
        this.mDumpManager.registerDumpable(qSContainerImpl.getClass().getName(), this.mContainer);
        this.mQSAnimator = create.getQSAnimator();
        this.mQSSquishinessController = create.getQSSquishinessController();
        QSCustomizerController qSCustomizerController = create.getQSCustomizerController();
        this.mQSCustomizerController = qSCustomizerController;
        qSCustomizerController.init();
        QSCustomizerController qSCustomizerController2 = this.mQSCustomizerController;
        Objects.requireNonNull(qSCustomizerController2);
        QSCustomizer qSCustomizer = (QSCustomizer) qSCustomizerController2.mView;
        Objects.requireNonNull(qSCustomizer);
        qSCustomizer.mQs = this;
        if (bundle != null) {
            setExpanded(bundle.getBoolean("expanded"));
            setListening(bundle.getBoolean("listening"));
            setEditLocation(view);
            QSCustomizerController qSCustomizerController3 = this.mQSCustomizerController;
            Objects.requireNonNull(qSCustomizerController3);
            if (bundle.getBoolean("qs_customizing")) {
                ((QSCustomizer) qSCustomizerController3.mView).setVisibility(0);
                ((QSCustomizer) qSCustomizerController3.mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        ((QSCustomizer) QSCustomizerController.this.mView).removeOnLayoutChangeListener(this);
                        QSCustomizerController.this.show(0, 0, true);
                    }
                });
            }
            if (this.mQsExpanded) {
                this.mQSPanelController.getTileLayout().restoreInstanceState(bundle);
            }
        }
        this.mStatusBarStateController.addCallback(this);
        onStateChanged(this.mStatusBarStateController.getState());
        view.addOnLayoutChangeListener(new QSFragment$$ExternalSyntheticLambda1(this));
        QSPanelController qSPanelController2 = this.mQSPanelController;
        TaskView$$ExternalSyntheticLambda2 taskView$$ExternalSyntheticLambda2 = new TaskView$$ExternalSyntheticLambda2(this, 3);
        Objects.requireNonNull(qSPanelController2);
        qSPanelController2.mUsingHorizontalLayoutChangedListener = taskView$$ExternalSyntheticLambda2;
    }

    public final void setCollapsedMediaVisibilityChangedListener(Consumer<Boolean> consumer) {
        QuickQSPanelController quickQSPanelController = this.mQuickQSPanelController;
        Objects.requireNonNull(quickQSPanelController);
        quickQSPanelController.mMediaVisibilityChangedListener = consumer;
    }

    public final void setContainerController(QSContainerController qSContainerController) {
        QSCustomizerController qSCustomizerController = this.mQSCustomizerController;
        Objects.requireNonNull(qSCustomizerController);
        QSCustomizer qSCustomizer = (QSCustomizer) qSCustomizerController.mView;
        Objects.requireNonNull(qSCustomizer);
        qSCustomizer.mQsContainerController = qSContainerController;
    }

    public final void setExpandClickListener(View.OnClickListener onClickListener) {
        this.mFooter.setExpandClickListener(onClickListener);
    }

    public final void setExpanded(boolean z) {
        this.mQsExpanded = z;
        this.mQSPanelController.setListening(this.mListening, z);
        updateQsState();
    }

    public final void setHeaderListening(boolean z) {
        QSContainerImplController qSContainerImplController = this.mQSContainerImplController;
        Objects.requireNonNull(qSContainerImplController);
        qSContainerImplController.mQuickStatusBarHeaderController.setListening(z);
    }

    public final void setHeightOverride(int i) {
        QSContainerImpl qSContainerImpl = this.mContainer;
        Objects.requireNonNull(qSContainerImpl);
        qSContainerImpl.mHeightOverride = i;
        qSContainerImpl.updateExpansion();
    }

    public final void setInSplitShade(boolean z) {
        this.mInSplitShade = z;
        QSAnimator qSAnimator = this.mQSAnimator;
        Objects.requireNonNull(qSAnimator);
        qSAnimator.mTranslateWhileExpanding = z;
        updateShowCollapsedOnKeyguard();
        updateQsState();
    }

    public final void setListening(boolean z) {
        this.mListening = z;
        QSContainerImplController qSContainerImplController = this.mQSContainerImplController;
        Objects.requireNonNull(qSContainerImplController);
        qSContainerImplController.mQuickStatusBarHeaderController.setListening(z);
        this.mQSFooterActionController.setListening(z);
        this.mQSPanelController.setListening(this.mListening, this.mQsExpanded);
    }

    public final void setOverscrolling(boolean z) {
        this.mStackScrollerOverscrolling = z;
        updateQsState();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:127:0x01c5, code lost:
        if (r6 == false) goto L_0x01c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x01d7, code lost:
        if (r4 != false) goto L_0x01d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x01d9, code lost:
        r4 = r2.qsAnimator;
        java.util.Objects.requireNonNull(r4);
        r4.mNeedsAnimatorUpdate = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setQsExpansion(float r17, float r18, float r19, float r20) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r19
            r3 = r20
            boolean r4 = r0.mTransitioningToFullShade
            r5 = 0
            if (r4 == 0) goto L_0x000f
            r6 = r5
            goto L_0x0010
        L_0x000f:
            r6 = r2
        L_0x0010:
            r7 = 1
            if (r4 != 0) goto L_0x001b
            int r4 = r0.mState
            if (r4 != r7) goto L_0x0018
            goto L_0x001b
        L_0x0018:
            r4 = r18
            goto L_0x001d
        L_0x001b:
            float r4 = r0.mFullShadeProgress
        L_0x001d:
            boolean r8 = r0.mInSplitShade
            r9 = 1065353216(0x3f800000, float:1.0)
            if (r8 == 0) goto L_0x0024
            goto L_0x0025
        L_0x0024:
            r4 = r9
        L_0x0025:
            android.view.View r8 = r16.getView()
            int r10 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            r11 = 0
            if (r10 != 0) goto L_0x0039
            int r12 = r8.getVisibility()
            r13 = 4
            if (r12 == r13) goto L_0x0039
            r8.setVisibility(r13)
            goto L_0x0044
        L_0x0039:
            if (r10 <= 0) goto L_0x0044
            int r10 = r8.getVisibility()
            if (r10 == 0) goto L_0x0044
            r8.setVisibility(r11)
        L_0x0044:
            float r4 = com.android.systemui.animation.ShadeInterpolation.getContentAlpha(r4)
            r8.setAlpha(r4)
            com.android.systemui.qs.QSContainerImpl r4 = r0.mContainer
            java.util.Objects.requireNonNull(r4)
            r4.mQsExpansion = r1
            com.android.systemui.qs.NonInterceptingScrollView r8 = r4.mQSPanelContainer
            int r10 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r10 <= 0) goto L_0x005a
            r12 = r7
            goto L_0x005b
        L_0x005a:
            r12 = r11
        L_0x005b:
            java.util.Objects.requireNonNull(r8)
            r8.mScrollEnabled = r12
            r4.updateExpansion()
            boolean r4 = r0.mInSplitShade
            if (r4 == 0) goto L_0x0069
            r4 = r9
            goto L_0x006c
        L_0x0069:
            r4 = 1036831949(0x3dcccccd, float:0.1)
        L_0x006c:
            float r8 = r1 - r9
            float r8 = r8 * r4
            boolean r4 = r16.isKeyguardState()
            if (r4 == 0) goto L_0x007b
            boolean r4 = r0.mShowCollapsedOnKeyguard
            if (r4 != 0) goto L_0x007b
            r4 = r7
            goto L_0x007c
        L_0x007b:
            r4 = r11
        L_0x007c:
            boolean r12 = r0.mHeaderAnimating
            if (r12 != 0) goto L_0x00a6
            int r12 = r0.mState
            if (r12 != r7) goto L_0x0090
            boolean r12 = r0.mShowCollapsedOnKeyguard
            if (r12 == 0) goto L_0x0090
            boolean r12 = r16.isKeyguardState()
            if (r12 != 0) goto L_0x0090
            r12 = r7
            goto L_0x0091
        L_0x0090:
            r12 = r11
        L_0x0091:
            if (r12 != 0) goto L_0x00a6
            android.view.View r12 = r16.getView()
            if (r4 == 0) goto L_0x00a2
            com.android.systemui.qs.QuickStatusBarHeader r13 = r0.mHeader
            int r13 = r13.getHeight()
            float r13 = (float) r13
            float r13 = r13 * r8
            goto L_0x00a3
        L_0x00a2:
            r13 = r6
        L_0x00a3:
            r12.setTranslationY(r13)
        L_0x00a6:
            android.view.View r12 = r16.getView()
            int r12 = r12.getHeight()
            float r13 = r0.mLastQSExpansion
            int r13 = (r1 > r13 ? 1 : (r1 == r13 ? 0 : -1))
            if (r13 != 0) goto L_0x00c9
            boolean r13 = r0.mLastKeyguardAndExpanded
            if (r13 != r4) goto L_0x00c9
            int r13 = r0.mLastViewHeight
            if (r13 != r12) goto L_0x00c9
            float r13 = r0.mLastHeaderTranslation
            int r13 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r13 != 0) goto L_0x00c9
            float r13 = r0.mSquishinessFraction
            int r13 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1))
            if (r13 != 0) goto L_0x00c9
            return
        L_0x00c9:
            r0.mLastHeaderTranslation = r6
            r6 = r18
            r0.mLastPanelFraction = r6
            r0.mSquishinessFraction = r3
            r0.mLastQSExpansion = r1
            r0.mLastKeyguardAndExpanded = r4
            r0.mLastViewHeight = r12
            int r3 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r3 != 0) goto L_0x00dd
            r6 = r7
            goto L_0x00de
        L_0x00dd:
            r6 = r11
        L_0x00de:
            if (r10 != 0) goto L_0x00e2
            r10 = r7
            goto L_0x00e3
        L_0x00e2:
            r10 = r11
        L_0x00e3:
            int r12 = r16.getHeightDiff()
            float r12 = (float) r12
            float r8 = r8 * r12
            com.android.systemui.qs.QuickStatusBarHeader r12 = r0.mHeader
            java.util.Objects.requireNonNull(r12)
            if (r4 == 0) goto L_0x00f2
            r13 = r9
            goto L_0x00f3
        L_0x00f2:
            r13 = r1
        L_0x00f3:
            com.android.systemui.qs.TouchAnimator r14 = r12.mAlphaAnimator
            if (r14 == 0) goto L_0x00fa
            r14.setPosition(r13)
        L_0x00fa:
            com.android.systemui.qs.TouchAnimator r14 = r12.mTranslationAnimator
            if (r14 == 0) goto L_0x0101
            r14.setPosition(r13)
        L_0x0101:
            com.android.systemui.qs.TouchAnimator r14 = r12.mIconsAlphaAnimator
            if (r14 == 0) goto L_0x0108
            r14.setPosition(r13)
        L_0x0108:
            if (r4 == 0) goto L_0x010e
            r12.setTranslationY(r8)
            goto L_0x0111
        L_0x010e:
            r12.setTranslationY(r5)
        L_0x0111:
            r12.mKeyguardExpansionFraction = r13
            int r12 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r12 >= 0) goto L_0x012e
            double r12 = (double) r1
            r14 = 4607092346807469998(0x3fefae147ae147ae, double:0.99)
            int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r12 <= 0) goto L_0x012e
            com.android.systemui.qs.QuickQSPanelController r12 = r0.mQuickQSPanelController
            boolean r12 = r12.switchTileLayout(r11)
            if (r12 == 0) goto L_0x012e
            com.android.systemui.qs.QuickStatusBarHeader r12 = r0.mHeader
            r12.updateResources()
        L_0x012e:
            com.android.systemui.qs.QSFooter r12 = r0.mFooter
            if (r4 == 0) goto L_0x0134
            r13 = r9
            goto L_0x0135
        L_0x0134:
            r13 = r1
        L_0x0135:
            r12.setExpansion(r13)
            com.android.systemui.qs.FooterActionsController r12 = r0.mQSFooterActionController
            if (r4 == 0) goto L_0x013e
            r4 = r9
            goto L_0x013f
        L_0x013e:
            r4 = r1
        L_0x013f:
            r12.setExpansion(r4)
            com.android.systemui.qs.QSPanelController r4 = r0.mQSPanelController
            java.util.Objects.requireNonNull(r4)
            r4.mRevealExpansion = r1
            com.android.systemui.qs.QSTileRevealController r4 = r4.mQsTileRevealController
            if (r4 == 0) goto L_0x0160
            if (r3 != 0) goto L_0x0159
            android.os.Handler r3 = r4.mHandler
            com.android.systemui.qs.QSTileRevealController$1 r4 = r4.mRevealQsTiles
            r12 = 500(0x1f4, double:2.47E-321)
            r3.postDelayed(r4, r12)
            goto L_0x0160
        L_0x0159:
            android.os.Handler r3 = r4.mHandler
            com.android.systemui.qs.QSTileRevealController$1 r4 = r4.mRevealQsTiles
            r3.removeCallbacks(r4)
        L_0x0160:
            com.android.systemui.qs.QSPanelController r3 = r0.mQSPanelController
            com.android.systemui.qs.QSPanel$QSTileLayout r3 = r3.getTileLayout()
            r3.setExpansion(r1, r2)
            com.android.systemui.qs.QuickQSPanelController r3 = r0.mQuickQSPanelController
            com.android.systemui.qs.QSPanel$QSTileLayout r3 = r3.getTileLayout()
            r3.setExpansion(r1, r2)
            com.android.systemui.qs.NonInterceptingScrollView r2 = r0.mQSPanelScrollView
            r2.setTranslationY(r8)
            if (r10 == 0) goto L_0x017e
            com.android.systemui.qs.NonInterceptingScrollView r2 = r0.mQSPanelScrollView
            r2.setScrollY(r11)
        L_0x017e:
            if (r6 != 0) goto L_0x01a0
            android.graphics.Rect r2 = r0.mQsBounds
            com.android.systemui.qs.NonInterceptingScrollView r3 = r0.mQSPanelScrollView
            float r3 = r3.getTranslationY()
            float r3 = -r3
            int r3 = (int) r3
            r2.top = r3
            android.graphics.Rect r2 = r0.mQsBounds
            com.android.systemui.qs.NonInterceptingScrollView r3 = r0.mQSPanelScrollView
            int r3 = r3.getWidth()
            r2.right = r3
            android.graphics.Rect r2 = r0.mQsBounds
            com.android.systemui.qs.NonInterceptingScrollView r3 = r0.mQSPanelScrollView
            int r3 = r3.getHeight()
            r2.bottom = r3
        L_0x01a0:
            r16.updateQsBounds()
            com.android.systemui.qs.QSSquishinessController r2 = r0.mQSSquishinessController
            if (r2 == 0) goto L_0x01ee
            float r3 = r0.mSquishinessFraction
            float r4 = r2.squishiness
            int r6 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1))
            if (r6 != 0) goto L_0x01b1
            r6 = r7
            goto L_0x01b2
        L_0x01b1:
            r6 = r11
        L_0x01b2:
            if (r6 == 0) goto L_0x01b5
            goto L_0x01ee
        L_0x01b5:
            int r6 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r6 != 0) goto L_0x01bb
            r6 = r7
            goto L_0x01bc
        L_0x01bb:
            r6 = r11
        L_0x01bc:
            if (r6 != 0) goto L_0x01c7
            int r6 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r6 != 0) goto L_0x01c4
            r6 = r7
            goto L_0x01c5
        L_0x01c4:
            r6 = r11
        L_0x01c5:
            if (r6 != 0) goto L_0x01d9
        L_0x01c7:
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 != 0) goto L_0x01cd
            r4 = r7
            goto L_0x01ce
        L_0x01cd:
            r4 = r11
        L_0x01ce:
            if (r4 != 0) goto L_0x01e0
            int r4 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r4 != 0) goto L_0x01d6
            r4 = r7
            goto L_0x01d7
        L_0x01d6:
            r4 = r11
        L_0x01d7:
            if (r4 == 0) goto L_0x01e0
        L_0x01d9:
            com.android.systemui.qs.QSAnimator r4 = r2.qsAnimator
            java.util.Objects.requireNonNull(r4)
            r4.mNeedsAnimatorUpdate = r7
        L_0x01e0:
            r2.squishiness = r3
            com.android.systemui.qs.QSPanelController r4 = r2.qsPanelController
            r4.setSquishinessFraction(r3)
            com.android.systemui.qs.QuickQSPanelController r3 = r2.quickQSPanelController
            float r2 = r2.squishiness
            r3.setSquishinessFraction(r2)
        L_0x01ee:
            com.android.systemui.qs.QSAnimator r2 = r0.mQSAnimator
            if (r2 == 0) goto L_0x01f5
            r2.setPosition(r1)
        L_0x01f5:
            android.content.Context r1 = r16.getContext()
            boolean r1 = com.android.systemui.util.Utils.useQsMediaPlayer(r1)
            if (r1 == 0) goto L_0x022d
            com.android.systemui.qs.QSContainerImpl r1 = r0.mContainer
            int[] r2 = r0.mTmpLocation
            r1.getLocationOnScreen(r2)
            int[] r1 = r0.mTmpLocation
            r1 = r1[r7]
            com.android.systemui.qs.QSContainerImpl r2 = r0.mContainer
            int r2 = r2.getHeight()
            int r2 = r2 + r1
            float r1 = (float) r2
            com.android.systemui.qs.NonInterceptingScrollView r2 = r0.mQSPanelScrollView
            int r2 = r2.getScrollY()
            float r2 = (float) r2
            float r2 = r1 - r2
            com.android.systemui.qs.NonInterceptingScrollView r3 = r0.mQSPanelScrollView
            int r3 = r3.getScrollRange()
            float r3 = (float) r3
            float r2 = r2 + r3
            com.android.systemui.media.MediaHost r3 = r0.mQsMediaHost
            r0.pinToBottom(r2, r3, r7)
            com.android.systemui.media.MediaHost r2 = r0.mQqsMediaHost
            r0.pinToBottom(r1, r2, r11)
        L_0x022d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.QSFragment.setQsExpansion(float, float, float, float):void");
    }

    public final void updateQsBounds() {
        if (this.mLastQSExpansion == 1.0f) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mQSPanelScrollView.getLayoutParams();
            this.mQsBounds.set(-marginLayoutParams.leftMargin, 0, this.mQSPanelScrollView.getWidth() + marginLayoutParams.rightMargin, this.mQSPanelScrollView.getHeight());
        }
        this.mQSPanelScrollView.setClipBounds(this.mQsBounds);
    }

    public final void updateQsState() {
        boolean z;
        boolean z2;
        int i;
        boolean z3;
        boolean z4;
        boolean z5;
        int i2;
        boolean z6 = true;
        int i3 = 0;
        if (this.mQsExpanded || this.mInSplitShade) {
            z = true;
        } else {
            z = false;
        }
        if (z || this.mStackScrollerOverscrolling || this.mHeaderAnimating) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.mQSPanelController.setExpanded(z);
        boolean isKeyguardState = isKeyguardState();
        QuickStatusBarHeader quickStatusBarHeader = this.mHeader;
        if (z || !isKeyguardState || this.mHeaderAnimating || this.mShowCollapsedOnKeyguard) {
            i = 0;
        } else {
            i = 4;
        }
        quickStatusBarHeader.setVisibility(i);
        QuickStatusBarHeader quickStatusBarHeader2 = this.mHeader;
        if ((!isKeyguardState || this.mHeaderAnimating || this.mShowCollapsedOnKeyguard) && (!z || this.mStackScrollerOverscrolling)) {
            z3 = false;
        } else {
            z3 = true;
        }
        QuickQSPanelController quickQSPanelController = this.mQuickQSPanelController;
        Objects.requireNonNull(quickStatusBarHeader2);
        if (quickStatusBarHeader2.mExpanded != z3) {
            quickStatusBarHeader2.mExpanded = z3;
            quickQSPanelController.setExpanded(z3);
            quickStatusBarHeader2.post(new LockIconViewController$$ExternalSyntheticLambda1(quickStatusBarHeader2, 1));
        }
        if (this.mQsDisabled || !z2) {
            z4 = false;
        } else {
            z4 = true;
        }
        if (!z4 || (!z && isKeyguardState && !this.mHeaderAnimating && !this.mShowCollapsedOnKeyguard)) {
            z5 = false;
        } else {
            z5 = true;
        }
        QSFooter qSFooter = this.mFooter;
        if (z5) {
            i2 = 0;
        } else {
            i2 = 4;
        }
        qSFooter.setVisibility(i2);
        this.mQSFooterActionController.setVisible(z5);
        QSFooter qSFooter2 = this.mFooter;
        if ((!isKeyguardState || this.mHeaderAnimating || this.mShowCollapsedOnKeyguard) && (!z || this.mStackScrollerOverscrolling)) {
            z6 = false;
        }
        qSFooter2.setExpanded(z6);
        QSPanelController qSPanelController = this.mQSPanelController;
        if (!z4) {
            i3 = 4;
        }
        Objects.requireNonNull(qSPanelController);
        ((QSPanel) qSPanelController.mView).setVisibility(i3);
    }

    public final void updateShowCollapsedOnKeyguard() {
        boolean z;
        if (this.mBypassController.getBypassEnabled() || (this.mTransitioningToFullShade && !this.mInSplitShade)) {
            z = true;
        } else {
            z = false;
        }
        if (z != this.mShowCollapsedOnKeyguard) {
            this.mShowCollapsedOnKeyguard = z;
            updateQsState();
            QSAnimator qSAnimator = this.mQSAnimator;
            if (qSAnimator != null) {
                qSAnimator.mShowCollapsedOnKeyguard = z;
                qSAnimator.updateQQSVisibility();
                qSAnimator.setPosition(qSAnimator.mLastPosition);
            }
            if (!z && isKeyguardState()) {
                setQsExpansion(this.mLastQSExpansion, this.mLastPanelFraction, 0.0f, this.mSquishinessFraction);
            }
        }
    }

    public QSFragment(RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, StatusBarStateController statusBarStateController, CommandQueue commandQueue, MediaHost mediaHost, MediaHost mediaHost2, KeyguardBypassController keyguardBypassController, QSFragmentComponent.Factory factory, QSFragmentDisableFlagsLogger qSFragmentDisableFlagsLogger, FalsingManager falsingManager, DumpManager dumpManager) {
        this.mRemoteInputQuickSettingsDisabler = remoteInputQuickSettingsDisabler;
        this.mQsMediaHost = mediaHost;
        this.mQqsMediaHost = mediaHost2;
        this.mQsComponentFactory = factory;
        this.mQsFragmentDisableFlagsLogger = qSFragmentDisableFlagsLogger;
        commandQueue.observe((Lifecycle) this.mLifecycle, this);
        this.mFalsingManager = falsingManager;
        this.mBypassController = keyguardBypassController;
        this.mStatusBarStateController = statusBarStateController;
        this.mDumpManager = dumpManager;
    }

    public final void animateHeaderSlidingOut() {
        if (getView().getY() != ((float) (-this.mHeader.getHeight()))) {
            this.mHeaderAnimating = true;
            getView().animate().y((float) (-this.mHeader.getHeight())).setStartDelay(0).setDuration(360).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    if (QSFragment.this.getView() != null) {
                        QSFragment.this.getView().animate().setListener((Animator.AnimatorListener) null);
                    }
                    QSFragment qSFragment = QSFragment.this;
                    qSFragment.mHeaderAnimating = false;
                    qSFragment.updateQsState();
                }
            }).start();
        }
    }

    public final void disable(int i, int i2, int i3, boolean z) {
        int i4;
        boolean z2;
        boolean z3;
        boolean z4;
        int i5;
        if (i == getContext().getDisplayId()) {
            RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler = this.mRemoteInputQuickSettingsDisabler;
            Objects.requireNonNull(remoteInputQuickSettingsDisabler);
            if (!remoteInputQuickSettingsDisabler.remoteInputActive || !remoteInputQuickSettingsDisabler.isLandscape || remoteInputQuickSettingsDisabler.shouldUseSplitNotificationShade) {
                i4 = i3;
            } else {
                i4 = i3 | 1;
            }
            QSFragmentDisableFlagsLogger qSFragmentDisableFlagsLogger = this.mQsFragmentDisableFlagsLogger;
            Objects.requireNonNull(qSFragmentDisableFlagsLogger);
            LogBuffer logBuffer = qSFragmentDisableFlagsLogger.buffer;
            LogLevel logLevel = LogLevel.INFO;
            QSFragmentDisableFlagsLogger$logDisableFlagChange$2 qSFragmentDisableFlagsLogger$logDisableFlagChange$2 = new QSFragmentDisableFlagsLogger$logDisableFlagChange$2(qSFragmentDisableFlagsLogger);
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                LogMessageImpl obtain = logBuffer.obtain("QSFragmentDisableFlagsLog", logLevel, qSFragmentDisableFlagsLogger$logDisableFlagChange$2);
                obtain.int1 = i2;
                obtain.int2 = i3;
                obtain.long1 = (long) i2;
                obtain.long2 = (long) i4;
                logBuffer.push(obtain);
            }
            int i6 = i4 & 1;
            boolean z5 = false;
            if (i6 != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2 != this.mQsDisabled) {
                this.mQsDisabled = z2;
                QSContainerImpl qSContainerImpl = this.mContainer;
                Objects.requireNonNull(qSContainerImpl);
                if (i6 != 0) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (z3 != qSContainerImpl.mQsDisabled) {
                    qSContainerImpl.mQsDisabled = z3;
                }
                QuickStatusBarHeader quickStatusBarHeader = this.mHeader;
                Objects.requireNonNull(quickStatusBarHeader);
                if (i6 != 0) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                if (z4 != quickStatusBarHeader.mQsDisabled) {
                    quickStatusBarHeader.mQsDisabled = z4;
                    QuickQSPanel quickQSPanel = quickStatusBarHeader.mHeaderQsPanel;
                    Objects.requireNonNull(quickQSPanel);
                    int i7 = 8;
                    if (z4 != quickQSPanel.mDisabledByPolicy) {
                        quickQSPanel.mDisabledByPolicy = z4;
                        if (z4) {
                            i5 = 8;
                        } else {
                            i5 = 0;
                        }
                        quickQSPanel.setVisibility(i5);
                    }
                    View view = quickStatusBarHeader.mStatusIconsView;
                    if (!quickStatusBarHeader.mQsDisabled) {
                        i7 = 0;
                    }
                    view.setVisibility(i7);
                    quickStatusBarHeader.updateResources();
                }
                this.mFooter.disable(i4);
                FooterActionsController footerActionsController = this.mQSFooterActionController;
                Objects.requireNonNull(footerActionsController);
                FooterActionsView footerActionsView = (FooterActionsView) footerActionsController.mView;
                MultiUserSwitchController multiUserSwitchController = footerActionsController.multiUserSwitchController;
                Objects.requireNonNull(multiUserSwitchController);
                boolean booleanValue = ((Boolean) DejankUtils.whitelistIpcs(new MultiUserSwitchController$$ExternalSyntheticLambda1(multiUserSwitchController))).booleanValue();
                Objects.requireNonNull(footerActionsView);
                if (i6 != 0) {
                    z5 = true;
                }
                if (z5 != footerActionsView.qsDisabled) {
                    footerActionsView.qsDisabled = z5;
                    footerActionsView.post(new FooterActionsView$updateEverything$1(footerActionsView, booleanValue));
                }
                updateQsState();
            }
        }
    }

    public final void hideImmediately() {
        getView().animate().cancel();
        getView().setY((float) (-this.mHeader.getHeight()));
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setEditLocation(getView());
        if (configuration.getLayoutDirection() != this.mLayoutDirection) {
            this.mLayoutDirection = configuration.getLayoutDirection();
            QSAnimator qSAnimator = this.mQSAnimator;
            if (qSAnimator != null) {
                Objects.requireNonNull(qSAnimator);
                qSAnimator.updateAnimators();
            }
        }
        updateQsState();
    }

    public final void onDestroy() {
        super.onDestroy();
        this.mStatusBarStateController.removeCallback(this);
        if (this.mListening) {
            setListening(false);
        }
        QSCustomizerController qSCustomizerController = this.mQSCustomizerController;
        Objects.requireNonNull(qSCustomizerController);
        QSCustomizer qSCustomizer = (QSCustomizer) qSCustomizerController.mView;
        Objects.requireNonNull(qSCustomizer);
        qSCustomizer.mQs = null;
        this.mScrollListener = null;
        this.mDumpManager.unregisterDumpable(this.mContainer.getClass().getName());
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("expanded", this.mQsExpanded);
        bundle.putBoolean("listening", this.mListening);
        QSCustomizerController qSCustomizerController = this.mQSCustomizerController;
        Objects.requireNonNull(qSCustomizerController);
        if (((QSCustomizer) qSCustomizerController.mView).isShown()) {
            qSCustomizerController.mKeyguardStateController.removeCallback(qSCustomizerController.mKeyguardCallback);
        }
        bundle.putBoolean("qs_customizing", ((QSCustomizer) qSCustomizerController.mView).isCustomizing());
        if (this.mQsExpanded) {
            this.mQSPanelController.getTileLayout().saveInstanceState(bundle);
        }
    }

    public final void pinToBottom(float f, MediaHost mediaHost, boolean z) {
        float f2;
        UniqueObjectHostView hostView = mediaHost.getHostView();
        if (this.mLastQSExpansion <= 0.0f || isKeyguardState() || !this.mQqsMediaHost.getVisible()) {
            hostView.setTranslationY(0.0f);
            return;
        }
        View view = (View) hostView.getParent();
        int i = 0;
        UniqueObjectHostView uniqueObjectHostView = hostView;
        while (!(view instanceof QSContainerImpl) && view != null) {
            i += view.getHeight() - uniqueObjectHostView.getBottom();
            uniqueObjectHostView = view;
            view = (View) view.getParent();
        }
        float height = ((f - ((float) i)) - ((float) hostView.getHeight())) - (((float) mediaHost.getCurrentBounds().top) - hostView.getTranslationY());
        if (z) {
            f2 = Math.min(height, 0.0f);
        } else {
            f2 = Math.max(height, 0.0f);
        }
        hostView.setTranslationY(f2);
    }

    public final void setEditLocation(View view) {
        View findViewById = view.findViewById(16908291);
        int[] locationOnScreen = findViewById.getLocationOnScreen();
        int i = locationOnScreen[0];
        int height = (findViewById.getHeight() / 2) + locationOnScreen[1];
        QSCustomizerController qSCustomizerController = this.mQSCustomizerController;
        Objects.requireNonNull(qSCustomizerController);
        QSCustomizer qSCustomizer = (QSCustomizer) qSCustomizerController.mView;
        Objects.requireNonNull(qSCustomizer);
        int[] locationOnScreen2 = qSCustomizer.findViewById(C1777R.C1779id.customize_container).getLocationOnScreen();
        qSCustomizer.f72mX = ((findViewById.getWidth() / 2) + i) - locationOnScreen2[0];
        qSCustomizer.f73mY = height - locationOnScreen2[1];
    }

    public final void setFancyClipping(int i, int i2, int i3, boolean z) {
        if (getView() instanceof QSContainerImpl) {
            QSContainerImpl qSContainerImpl = (QSContainerImpl) getView();
            Objects.requireNonNull(qSContainerImpl);
            float[] fArr = qSContainerImpl.mFancyClippingRadii;
            boolean z2 = false;
            float f = (float) i3;
            boolean z3 = true;
            if (fArr[0] != f) {
                fArr[0] = f;
                fArr[1] = f;
                fArr[2] = f;
                fArr[3] = f;
                z2 = true;
            }
            if (qSContainerImpl.mFancyClippingTop != i) {
                qSContainerImpl.mFancyClippingTop = i;
                z2 = true;
            }
            if (qSContainerImpl.mFancyClippingBottom != i2) {
                qSContainerImpl.mFancyClippingBottom = i2;
                z2 = true;
            }
            if (qSContainerImpl.mClippingEnabled != z) {
                qSContainerImpl.mClippingEnabled = z;
            } else {
                z3 = z2;
            }
            if (z3) {
                qSContainerImpl.updateClippingPath();
            }
        }
    }

    public final void setPanelView(C0961QS.HeightListener heightListener) {
        this.mPanelView = heightListener;
    }

    public final void setScrollListener(C0961QS.ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    public final View getHeader() {
        return this.mHeader;
    }

    public boolean isExpanded() {
        return this.mQsExpanded;
    }

    public boolean isListening() {
        return this.mListening;
    }
}
