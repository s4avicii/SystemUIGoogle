package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import android.view.View;
import androidx.leanback.R$raw;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.ViewClippingUtil;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda2;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.HeadsUpStatusBarView;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.Clock;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public final class HeadsUpAppearanceController extends ViewController<HeadsUpStatusBarView> implements OnHeadsUpChangedListener, DarkIconDispatcher.DarkReceiver, NotificationWakeUpCoordinator.WakeUpListener {
    public boolean mAnimationsEnabled = true;
    @VisibleForTesting
    public float mAppearFraction;
    public final KeyguardBypassController mBypassController;
    public final Clock mClockView;
    public final CommandQueue mCommandQueue;
    public final DarkIconDispatcher mDarkIconDispatcher;
    @VisibleForTesting
    public float mExpandedHeight;
    public final HeadsUpManagerPhone mHeadsUpManager;
    public final KeyguardStateController mKeyguardStateController;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public final NotificationPanelViewController mNotificationPanelViewController;
    public final Optional<View> mOperatorNameViewOptional;
    public final C14161 mParentClippingParams = new ViewClippingUtil.ClippingParameters() {
        public final boolean shouldFinish(View view) {
            if (view.getId() == C1777R.C1779id.status_bar) {
                return true;
            }
            return false;
        }
    };
    public final HeadsUpAppearanceController$$ExternalSyntheticLambda1 mSetExpandedHeight = new HeadsUpAppearanceController$$ExternalSyntheticLambda1(this);
    public final StatusBar$$ExternalSyntheticLambda31 mSetTrackingHeadsUp = new StatusBar$$ExternalSyntheticLambda31(this, 3);
    public boolean mShown;
    public final NotificationStackScrollLayoutController mStackScrollerController;
    public final StatusBarStateController mStatusBarStateController;
    public ExpandableNotificationRow mTrackedChild;
    public final NotificationWakeUpCoordinator mWakeUpCoordinator;

    public final void hide(View view, int i, KeyguardUpdateMonitor$$ExternalSyntheticLambda6 keyguardUpdateMonitor$$ExternalSyntheticLambda6) {
        if (this.mAnimationsEnabled) {
            R$raw.fadeOut(view, 110, (Runnable) new HeadsUpAppearanceController$$ExternalSyntheticLambda0(view, i, keyguardUpdateMonitor$$ExternalSyntheticLambda6));
            return;
        }
        view.setVisibility(i);
        if (keyguardUpdateMonitor$$ExternalSyntheticLambda6 != null) {
            keyguardUpdateMonitor$$ExternalSyntheticLambda6.run();
        }
    }

    public final boolean isExpanded() {
        if (this.mExpandedHeight > 0.0f) {
            return true;
        }
        return false;
    }

    public final void onDarkChanged(ArrayList<Rect> arrayList, float f, int i) {
        HeadsUpStatusBarView headsUpStatusBarView = (HeadsUpStatusBarView) this.mView;
        Objects.requireNonNull(headsUpStatusBarView);
        headsUpStatusBarView.mTextView.setTextColor(DarkIconDispatcher.getTint(arrayList, headsUpStatusBarView, i));
    }

    public final void onViewAttached() {
        this.mHeadsUpManager.addListener(this);
        HeadsUpStatusBarView headsUpStatusBarView = (HeadsUpStatusBarView) this.mView;
        CarrierTextManager$$ExternalSyntheticLambda0 carrierTextManager$$ExternalSyntheticLambda0 = new CarrierTextManager$$ExternalSyntheticLambda0(this, 5);
        Objects.requireNonNull(headsUpStatusBarView);
        headsUpStatusBarView.mOnDrawingRectChangedListener = carrierTextManager$$ExternalSyntheticLambda0;
        NotificationWakeUpCoordinator notificationWakeUpCoordinator = this.mWakeUpCoordinator;
        Objects.requireNonNull(notificationWakeUpCoordinator);
        notificationWakeUpCoordinator.wakeUpListeners.add(this);
        NotificationPanelViewController notificationPanelViewController = this.mNotificationPanelViewController;
        StatusBar$$ExternalSyntheticLambda31 statusBar$$ExternalSyntheticLambda31 = this.mSetTrackingHeadsUp;
        Objects.requireNonNull(notificationPanelViewController);
        notificationPanelViewController.mTrackingHeadsUpListeners.add(statusBar$$ExternalSyntheticLambda31);
        NotificationPanelViewController notificationPanelViewController2 = this.mNotificationPanelViewController;
        Objects.requireNonNull(notificationPanelViewController2);
        notificationPanelViewController2.mHeadsUpAppearanceController = this;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mStackScrollerController;
        HeadsUpAppearanceController$$ExternalSyntheticLambda1 headsUpAppearanceController$$ExternalSyntheticLambda1 = this.mSetExpandedHeight;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mExpandedHeightListeners.add(headsUpAppearanceController$$ExternalSyntheticLambda1);
        this.mDarkIconDispatcher.addDarkReceiver((DarkIconDispatcher.DarkReceiver) this);
    }

    public final void onViewDetached() {
        HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
        Objects.requireNonNull(headsUpManagerPhone);
        headsUpManagerPhone.mListeners.remove(this);
        HeadsUpStatusBarView headsUpStatusBarView = (HeadsUpStatusBarView) this.mView;
        Objects.requireNonNull(headsUpStatusBarView);
        headsUpStatusBarView.mOnDrawingRectChangedListener = null;
        NotificationWakeUpCoordinator notificationWakeUpCoordinator = this.mWakeUpCoordinator;
        Objects.requireNonNull(notificationWakeUpCoordinator);
        notificationWakeUpCoordinator.wakeUpListeners.remove(this);
        NotificationPanelViewController notificationPanelViewController = this.mNotificationPanelViewController;
        StatusBar$$ExternalSyntheticLambda31 statusBar$$ExternalSyntheticLambda31 = this.mSetTrackingHeadsUp;
        Objects.requireNonNull(notificationPanelViewController);
        notificationPanelViewController.mTrackingHeadsUpListeners.remove(statusBar$$ExternalSyntheticLambda31);
        NotificationPanelViewController notificationPanelViewController2 = this.mNotificationPanelViewController;
        Objects.requireNonNull(notificationPanelViewController2);
        notificationPanelViewController2.mHeadsUpAppearanceController = null;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.mStackScrollerController;
        HeadsUpAppearanceController$$ExternalSyntheticLambda1 headsUpAppearanceController$$ExternalSyntheticLambda1 = this.mSetExpandedHeight;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mExpandedHeightListeners.remove(headsUpAppearanceController$$ExternalSyntheticLambda1);
        this.mDarkIconDispatcher.removeDarkReceiver((DarkIconDispatcher.DarkReceiver) this);
    }

    public final void setShown(boolean z) {
        if (this.mShown != z) {
            this.mShown = z;
            if (z) {
                ViewClippingUtil.setClippingDeactivated(this.mView, true, this.mParentClippingParams);
                ((HeadsUpStatusBarView) this.mView).setVisibility(0);
                show(this.mView);
                hide(this.mClockView, 4, (KeyguardUpdateMonitor$$ExternalSyntheticLambda6) null);
                this.mOperatorNameViewOptional.ifPresent(new StatusBar$$ExternalSyntheticLambda32(this, 2));
            } else {
                show(this.mClockView);
                this.mOperatorNameViewOptional.ifPresent(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda2(this, 1));
                hide(this.mView, 8, new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(this, 7));
            }
            if (this.mStatusBarStateController.getState() != 0) {
                this.mCommandQueue.recomputeDisableFlags(((HeadsUpStatusBarView) this.mView).getContext().getDisplayId(), false);
            }
        }
    }

    public final boolean shouldBeVisible() {
        boolean z;
        NotificationWakeUpCoordinator notificationWakeUpCoordinator = this.mWakeUpCoordinator;
        Objects.requireNonNull(notificationWakeUpCoordinator);
        boolean z2 = !notificationWakeUpCoordinator.notificationsFullyHidden;
        if (isExpanded() || !z2) {
            z = false;
        } else {
            z = true;
        }
        if (this.mBypassController.getBypassEnabled() && ((this.mStatusBarStateController.getState() == 1 || this.mKeyguardStateController.isKeyguardGoingAway()) && z2)) {
            z = true;
        }
        if (z) {
            HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
            Objects.requireNonNull(headsUpManagerPhone);
            if (headsUpManagerPhone.mHasPinnedNotification) {
                return true;
            }
        }
        return false;
    }

    public final void show(View view) {
        if (this.mAnimationsEnabled) {
            R$raw.fadeIn(view, 110, 100);
        } else {
            view.setVisibility(0);
        }
    }

    @VisibleForTesting
    public HeadsUpAppearanceController(NotificationIconAreaController notificationIconAreaController, HeadsUpManagerPhone headsUpManagerPhone, StatusBarStateController statusBarStateController, KeyguardBypassController keyguardBypassController, NotificationWakeUpCoordinator notificationWakeUpCoordinator, DarkIconDispatcher darkIconDispatcher, KeyguardStateController keyguardStateController, CommandQueue commandQueue, NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationPanelViewController notificationPanelViewController, HeadsUpStatusBarView headsUpStatusBarView, Clock clock, Optional<View> optional) {
        super(headsUpStatusBarView);
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mHeadsUpManager = headsUpManagerPhone;
        Objects.requireNonNull(notificationPanelViewController);
        this.mTrackedChild = notificationPanelViewController.mTrackedHeadsUpNotification;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        this.mAppearFraction = notificationStackScrollLayout.mLastSentAppear;
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout2);
        this.mExpandedHeight = notificationStackScrollLayout2.mLastSentExpandedHeight;
        this.mStackScrollerController = notificationStackScrollLayoutController;
        this.mNotificationPanelViewController = notificationPanelViewController;
        notificationStackScrollLayoutController.mHeadsUpAppearanceController = this;
        NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController.mView;
        Objects.requireNonNull(notificationStackScrollLayout3);
        notificationStackScrollLayout3.mHeadsUpAppearanceController = this;
        this.mClockView = clock;
        this.mOperatorNameViewOptional = optional;
        this.mDarkIconDispatcher = darkIconDispatcher;
        headsUpStatusBarView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (HeadsUpAppearanceController.this.shouldBeVisible()) {
                    HeadsUpAppearanceController.this.updateTopEntry();
                    NotificationStackScrollLayoutController notificationStackScrollLayoutController = HeadsUpAppearanceController.this.mStackScrollerController;
                    Objects.requireNonNull(notificationStackScrollLayoutController);
                    notificationStackScrollLayoutController.mView.requestLayout();
                }
                ((HeadsUpStatusBarView) HeadsUpAppearanceController.this.mView).removeOnLayoutChangeListener(this);
            }
        });
        this.mBypassController = keyguardBypassController;
        this.mStatusBarStateController = statusBarStateController;
        this.mWakeUpCoordinator = notificationWakeUpCoordinator;
        this.mCommandQueue = commandQueue;
        this.mKeyguardStateController = keyguardStateController;
    }

    public final void onHeadsUpPinned(NotificationEntry notificationEntry) {
        updateTopEntry();
        updateHeader(notificationEntry);
    }

    public final void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
        updateTopEntry();
        updateHeader(notificationEntry);
    }

    public final void updateHeader(NotificationEntry notificationEntry) {
        float f;
        Objects.requireNonNull(notificationEntry);
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        Objects.requireNonNull(expandableNotificationRow);
        if (expandableNotificationRow.mIsPinned || expandableNotificationRow.mHeadsupDisappearRunning || expandableNotificationRow == this.mTrackedChild || expandableNotificationRow.showingPulsing()) {
            f = this.mAppearFraction;
        } else {
            f = 1.0f;
        }
        if (expandableNotificationRow.mHeaderVisibleAmount != f) {
            expandableNotificationRow.mHeaderVisibleAmount = f;
            for (NotificationContentView notificationContentView : expandableNotificationRow.mLayouts) {
                Objects.requireNonNull(notificationContentView);
                NotificationViewWrapper notificationViewWrapper = notificationContentView.mContractedWrapper;
                if (notificationViewWrapper != null) {
                    notificationViewWrapper.setHeaderVisibleAmount(f);
                }
                NotificationViewWrapper notificationViewWrapper2 = notificationContentView.mHeadsUpWrapper;
                if (notificationViewWrapper2 != null) {
                    notificationViewWrapper2.setHeaderVisibleAmount(f);
                }
                NotificationViewWrapper notificationViewWrapper3 = notificationContentView.mExpandedWrapper;
                if (notificationViewWrapper3 != null) {
                    notificationViewWrapper3.setHeaderVisibleAmount(f);
                }
            }
            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
            if (notificationChildrenContainer != null) {
                notificationChildrenContainer.mHeaderVisibleAmount = f;
                notificationChildrenContainer.mCurrentHeaderTranslation = (int) ((1.0f - f) * ((float) notificationChildrenContainer.mTranslationForHeader));
            }
            expandableNotificationRow.notifyHeightChanged(false);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0063  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateTopEntry() {
        /*
            r5 = this;
            boolean r0 = r5.shouldBeVisible()
            r1 = 0
            if (r0 == 0) goto L_0x000e
            com.android.systemui.statusbar.phone.HeadsUpManagerPhone r0 = r5.mHeadsUpManager
            com.android.systemui.statusbar.notification.collection.NotificationEntry r0 = r0.getTopEntry()
            goto L_0x000f
        L_0x000e:
            r0 = r1
        L_0x000f:
            T r2 = r5.mView
            com.android.systemui.statusbar.HeadsUpStatusBarView r2 = (com.android.systemui.statusbar.HeadsUpStatusBarView) r2
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r2 = r2.mShowingEntry
            T r3 = r5.mView
            com.android.systemui.statusbar.HeadsUpStatusBarView r3 = (com.android.systemui.statusbar.HeadsUpStatusBarView) r3
            r3.setEntry(r0)
            if (r0 == r2) goto L_0x0076
            r3 = 1
            r4 = 0
            if (r0 != 0) goto L_0x002d
            r5.setShown(r4)
            boolean r2 = r5.isExpanded()
            goto L_0x0036
        L_0x002d:
            if (r2 != 0) goto L_0x0038
            r5.setShown(r3)
            boolean r2 = r5.isExpanded()
        L_0x0036:
            r4 = r2 ^ 1
        L_0x0038:
            com.android.systemui.statusbar.phone.NotificationIconAreaController r2 = r5.mNotificationIconAreaController
            T r3 = r5.mView
            com.android.systemui.statusbar.HeadsUpStatusBarView r3 = (com.android.systemui.statusbar.HeadsUpStatusBarView) r3
            java.util.Objects.requireNonNull(r3)
            android.graphics.Rect r3 = r3.mIconDrawingRect
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.phone.NotificationIconContainer r2 = r2.mNotificationIcons
            java.util.Objects.requireNonNull(r2)
            r2.mIsolatedIconLocation = r3
            com.android.systemui.statusbar.phone.NotificationIconAreaController r5 = r5.mNotificationIconAreaController
            if (r0 != 0) goto L_0x0052
            goto L_0x0059
        L_0x0052:
            com.android.systemui.statusbar.notification.icon.IconPack r0 = r0.mIcons
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.StatusBarIconView r1 = r0.mStatusBarIcon
        L_0x0059:
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.phone.NotificationIconContainer r5 = r5.mNotificationIcons
            java.util.Objects.requireNonNull(r5)
            if (r4 == 0) goto L_0x006b
            if (r1 == 0) goto L_0x0067
            r0 = r1
            goto L_0x0069
        L_0x0067:
            com.android.systemui.statusbar.StatusBarIconView r0 = r5.mIsolatedIcon
        L_0x0069:
            r5.mIsolatedIconForAnimation = r0
        L_0x006b:
            r5.mIsolatedIcon = r1
            r5.resetViewStates()
            r5.calculateIconTranslations()
            r5.applyIconStates()
        L_0x0076:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.HeadsUpAppearanceController.updateTopEntry():void");
    }

    public final void onFullyHiddenChanged(boolean z) {
        updateTopEntry();
    }

    @VisibleForTesting
    public void setAnimationsEnabled(boolean z) {
        this.mAnimationsEnabled = z;
    }

    @VisibleForTesting
    public boolean isShown() {
        return this.mShown;
    }
}
