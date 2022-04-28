package com.android.systemui.statusbar.notification.stack;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.graphics.Point;
import android.os.Handler;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.ExpandHelper;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.media.KeyguardMediaController;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.ExpandAnimationParameters;
import com.android.systemui.statusbar.notification.ForegroundServiceDismissalFeatureController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.collection.legacy.VisualStabilityManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.statusbar.notification.collection.render.NotifStats;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Assert;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda1;
import java.util.Objects;

public final class NotificationStackScrollLayoutController {
    public final boolean mAllowLongPress;
    public int mBarState;
    public final ConfigurationController mConfigurationController;
    @VisibleForTesting
    public final ConfigurationController.ConfigurationListener mConfigurationListener = new ConfigurationController.ConfigurationListener() {
        public final void onConfigChanged(Configuration configuration) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            Objects.requireNonNull(notificationStackScrollLayoutController);
            notificationStackScrollLayoutController.mNotificationDragDownMovement = notificationStackScrollLayoutController.mResources.getDimensionPixelSize(C1777R.dimen.lockscreen_shade_notification_movement);
            notificationStackScrollLayoutController.mTotalDistanceForFullShadeTransition = notificationStackScrollLayoutController.mResources.getDimensionPixelSize(C1777R.dimen.lockscreen_shade_qs_transition_distance);
        }

        public final void onDensityOrFontScaleChanged() {
            NotificationStackScrollLayoutController.this.updateShowEmptyShadeView();
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.inflateFooterView();
            notificationStackScrollLayout.inflateEmptyShadeView();
            notificationStackScrollLayout.updateFooter();
            notificationStackScrollLayout.mSectionsManager.reinflateViews();
        }

        public final void onThemeChanged() {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            int dimensionPixelSize = notificationStackScrollLayout.getResources().getDimensionPixelSize(C1777R.dimen.notification_corner_radius);
            if (notificationStackScrollLayout.mCornerRadius != dimensionPixelSize) {
                notificationStackScrollLayout.mCornerRadius = dimensionPixelSize;
                notificationStackScrollLayout.invalidate();
            }
            NotificationStackScrollLayoutController.this.mView.updateBgColor();
            NotificationStackScrollLayoutController.this.mView.updateDecorViews();
            NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout2);
            notificationStackScrollLayout2.inflateFooterView();
            notificationStackScrollLayout2.inflateEmptyShadeView();
            notificationStackScrollLayout2.updateFooter();
            notificationStackScrollLayout2.mSectionsManager.reinflateViews();
            NotificationStackScrollLayoutController.this.updateShowEmptyShadeView();
            NotificationStackScrollLayoutController.this.updateFooter();
        }

        public final void onUiModeChanged() {
            NotificationStackScrollLayoutController.this.mView.updateBgColor();
            NotificationStackScrollLayoutController.this.mView.updateDecorViews();
        }
    };
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final C13722 mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() {
        public final void updateCurrentUserIsSetup() {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            boolean isCurrentUserSetup = notificationStackScrollLayoutController.mDeviceProvisionedController.isCurrentUserSetup();
            Objects.requireNonNull(notificationStackScrollLayout);
            if (notificationStackScrollLayout.mIsCurrentUserSetup != isCurrentUserSetup) {
                notificationStackScrollLayout.mIsCurrentUserSetup = isCurrentUserSetup;
                notificationStackScrollLayout.updateFooter();
            }
        }

        public final void onDeviceProvisionedChanged() {
            updateCurrentUserIsSetup();
        }

        public final void onUserSetupChanged() {
            updateCurrentUserIsSetup();
        }

        public final void onUserSwitched() {
            updateCurrentUserIsSetup();
        }
    };
    public final DynamicPrivacyController mDynamicPrivacyController;
    public final C1380xbae1b0bf mDynamicPrivacyControllerListener = new C1380xbae1b0bf(this);
    public boolean mFadeNotificationsOnDismiss;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final ForegroundServiceDismissalFeatureController mFgFeatureController;
    public final ForegroundServiceSectionController mFgServicesSectionController;
    public HeadsUpAppearanceController mHeadsUpAppearanceController;
    public final HeadsUpManagerPhone mHeadsUpManager;
    public Boolean mHistoryEnabled;
    public final IStatusBarService mIStatusBarService;
    public final InteractionJankMonitor mJankMonitor;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardMediaController mKeyguardMediaController;
    public final LayoutInflater mLayoutInflater;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public final C13755 mLockscreenUserChangeListener = new NotificationLockscreenUserManager.UserChangedListener() {
        public final void onUserChanged(int i) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mView.updateSensitiveness(false, notificationStackScrollLayoutController.mLockscreenUserManager.isAnyProfilePublicMode());
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController2.mHistoryEnabled = null;
            notificationStackScrollLayoutController2.updateFooter();
        }
    };
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final NotificationStackScrollLogger mLogger;
    public View mLongPressedView;
    public final C13766 mMenuEventListener = new NotificationMenuRowPlugin.OnMenuEventListener() {
        public final void onMenuClicked(View view, int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            if (notificationStackScrollLayoutController.mAllowLongPress) {
                if (view instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                    MetricsLogger metricsLogger = notificationStackScrollLayoutController.mMetricsLogger;
                    Objects.requireNonNull(expandableNotificationRow);
                    NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                    Objects.requireNonNull(notificationEntry);
                    metricsLogger.write(notificationEntry.mSbn.getLogMaker().setCategory(333).setType(4));
                }
                NotificationStackScrollLayoutController.this.mNotificationGutsManager.openGuts(view, i, i2, menuItem);
            }
        }

        public final void onMenuReset(View view) {
            NotificationSwipeHelper notificationSwipeHelper = NotificationStackScrollLayoutController.this.mSwipeHelper;
            Objects.requireNonNull(notificationSwipeHelper);
            View view2 = notificationSwipeHelper.mTranslatingParentView;
            if (view2 != null && view == view2) {
                NotificationSwipeHelper notificationSwipeHelper2 = NotificationStackScrollLayoutController.this.mSwipeHelper;
                Objects.requireNonNull(notificationSwipeHelper2);
                notificationSwipeHelper2.mMenuExposedView = null;
                NotificationSwipeHelper notificationSwipeHelper3 = NotificationStackScrollLayoutController.this.mSwipeHelper;
                Objects.requireNonNull(notificationSwipeHelper3);
                notificationSwipeHelper3.setTranslatingParentView((View) null);
                if (view instanceof ExpandableNotificationRow) {
                    HeadsUpManagerPhone headsUpManagerPhone = NotificationStackScrollLayoutController.this.mHeadsUpManager;
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                    Objects.requireNonNull(expandableNotificationRow);
                    headsUpManagerPhone.setMenuShown(expandableNotificationRow.mEntry, false);
                }
            }
        }

        public final void onMenuShown(View view) {
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                MetricsLogger metricsLogger = NotificationStackScrollLayoutController.this.mMetricsLogger;
                Objects.requireNonNull(expandableNotificationRow);
                NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                Objects.requireNonNull(notificationEntry);
                metricsLogger.write(notificationEntry.mSbn.getLogMaker().setCategory(332).setType(4));
                NotificationStackScrollLayoutController.this.mHeadsUpManager.setMenuShown(expandableNotificationRow.mEntry, true);
                NotificationSwipeHelper notificationSwipeHelper = NotificationStackScrollLayoutController.this.mSwipeHelper;
                Objects.requireNonNull(notificationSwipeHelper);
                notificationSwipeHelper.mMenuExposedView = notificationSwipeHelper.mTranslatingParentView;
                C13777 r2 = (C13777) notificationSwipeHelper.mCallback;
                Objects.requireNonNull(r2);
                NotificationStackScrollLayoutController.this.mFalsingCollector.onNotificationStopDismissing();
                Handler handler = notificationSwipeHelper.getHandler();
                C13777 r4 = (C13777) notificationSwipeHelper.mCallback;
                Objects.requireNonNull(r4);
                if (NotificationStackScrollLayoutController.this.mView.onKeyguard()) {
                    handler.removeCallbacks(notificationSwipeHelper.getFalsingCheck());
                    handler.postDelayed(notificationSwipeHelper.getFalsingCheck(), 4000);
                }
                NotificationStackScrollLayoutController.this.mNotificationGutsManager.closeAndSaveGuts(true, false, false, false);
                NotificationMenuRowPlugin notificationMenuRowPlugin = expandableNotificationRow.mMenuRow;
                if (notificationMenuRowPlugin.shouldShowGutsOnSnapOpen()) {
                    NotificationMenuRowPlugin.MenuItem menuItemToExposeOnSnap = notificationMenuRowPlugin.menuItemToExposeOnSnap();
                    if (menuItemToExposeOnSnap != null) {
                        Point revealAnimationOrigin = notificationMenuRowPlugin.getRevealAnimationOrigin();
                        NotificationStackScrollLayoutController.this.mNotificationGutsManager.openGuts(view, revealAnimationOrigin.x, revealAnimationOrigin.y, menuItemToExposeOnSnap);
                    } else {
                        Log.e("StackScrollerController", "Provider has shouldShowGutsOnSnapOpen, but provided no menu item in menuItemtoExposeOnSnap. Skipping.");
                    }
                    NotificationStackScrollLayoutController.this.mSwipeHelper.resetExposedMenuView(false, true);
                }
            }
        }
    };
    public final MetricsLogger mMetricsLogger;
    public final NotifCollection mNotifCollection;
    public final NotifPipeline mNotifPipeline;
    public final NotifPipelineFlags mNotifPipelineFlags;
    public final NotifStackControllerImpl mNotifStackController = new NotifStackControllerImpl();
    public NotifStats mNotifStats = NotifStats.empty;
    public NotificationActivityStarter mNotificationActivityStarter;
    public final C13777 mNotificationCallback = new NotificationSwipeHelper.NotificationCallback() {
        public final boolean canChildBeDismissed(View view) {
            boolean z = NotificationStackScrollLayout.SPEW;
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                if (expandableNotificationRow.areGutsExposed() || !expandableNotificationRow.mEntry.hasFinishedInitialization()) {
                    return false;
                }
                return expandableNotificationRow.canViewBeDismissed();
            } else if (!(view instanceof PeopleHubView)) {
                return false;
            } else {
                Objects.requireNonNull((PeopleHubView) view);
                return false;
            }
        }

        public final ExpandableView getChildAtPosition(MotionEvent motionEvent) {
            ExpandableView childAtPosition = NotificationStackScrollLayoutController.this.mView.getChildAtPosition(motionEvent.getX(), motionEvent.getY(), true, false);
            if (!(childAtPosition instanceof ExpandableNotificationRow)) {
                return childAtPosition;
            }
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAtPosition;
            Objects.requireNonNull(expandableNotificationRow);
            ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow.mNotificationParent;
            if (expandableNotificationRow2 == null || !expandableNotificationRow2.mChildrenExpanded) {
                return childAtPosition;
            }
            if (!expandableNotificationRow2.areGutsExposed()) {
                NotificationSwipeHelper notificationSwipeHelper = NotificationStackScrollLayoutController.this.mSwipeHelper;
                Objects.requireNonNull(notificationSwipeHelper);
                if (notificationSwipeHelper.mMenuExposedView != expandableNotificationRow2 && (expandableNotificationRow2.getAttachedChildren().size() != 1 || !expandableNotificationRow2.mEntry.isDismissable())) {
                    return childAtPosition;
                }
            }
            return expandableNotificationRow2;
        }

        public final void handleChildViewDismissed(View view) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            if (!notificationStackScrollLayout.mClearAllInProgress) {
                NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayoutController.this.mView;
                Objects.requireNonNull(notificationStackScrollLayout2);
                notificationStackScrollLayout2.updateFirstAndLastBackgroundViews();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationStackScrollLayout2.mController;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                notificationStackScrollLayoutController.mNotificationRoundnessManager.setViewsAffectedBySwipe((ExpandableView) null, (ExpandableView) null, (ExpandableView) null);
                notificationStackScrollLayout2.mShelf.updateAppearance();
                if (view instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
                    Objects.requireNonNull(expandableNotificationRow);
                    if (expandableNotificationRow.mIsHeadsUp) {
                        HeadsUpManagerPhone headsUpManagerPhone = NotificationStackScrollLayoutController.this.mHeadsUpManager;
                        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                        Objects.requireNonNull(notificationEntry);
                        String key = notificationEntry.mSbn.getKey();
                        Objects.requireNonNull(headsUpManagerPhone);
                        headsUpManagerPhone.mSwipedOutKeys.add(key);
                    }
                    expandableNotificationRow.performDismiss(false);
                }
                NotificationStackScrollLayout notificationStackScrollLayout3 = NotificationStackScrollLayoutController.this.mView;
                Objects.requireNonNull(notificationStackScrollLayout3);
                notificationStackScrollLayout3.mSwipedOutViews.add(view);
                NotificationStackScrollLayoutController.this.mFalsingCollector.onNotificationDismissed();
                NotificationStackScrollLayoutController.this.mFalsingCollector.shouldEnforceBouncer();
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:7:0x0034, code lost:
            if (r5.mSectionsManager.beginsSection(r6, r2) != false) goto L_0x0036;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onBeginDrag(android.view.View r6) {
            /*
                r5 = this;
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.classifier.FalsingCollector r0 = r0.mFalsingCollector
                r0.onNotificationStartDismissing()
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r5 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r5.mView
                java.util.Objects.requireNonNull(r5)
                boolean r0 = r6 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                if (r0 != 0) goto L_0x0013
                goto L_0x006c
            L_0x0013:
                int r0 = r5.indexOfChild(r6)
                if (r0 >= 0) goto L_0x001a
                goto L_0x006c
            L_0x001a:
                com.android.systemui.statusbar.notification.stack.NotificationSectionsManager r1 = r5.mSectionsManager
                com.android.systemui.statusbar.notification.stack.NotificationSection[] r2 = r5.mSections
                java.util.ArrayList r3 = r5.getChildrenWithBackground()
                r1.updateFirstAndLastViewsForAllSections(r2, r3)
                r1 = 0
                if (r0 <= 0) goto L_0x0036
                int r2 = r0 + -1
                android.view.View r2 = r5.getChildAt(r2)
                com.android.systemui.statusbar.notification.stack.NotificationSectionsManager r3 = r5.mSectionsManager
                boolean r3 = r3.beginsSection(r6, r2)
                if (r3 == 0) goto L_0x0037
            L_0x0036:
                r2 = r1
            L_0x0037:
                int r3 = r5.getChildCount()
                r4 = 1
                if (r0 >= r3) goto L_0x004d
                int r0 = r0 + r4
                android.view.View r0 = r5.getChildAt(r0)
                com.android.systemui.statusbar.notification.stack.NotificationSectionsManager r3 = r5.mSectionsManager
                boolean r3 = r3.beginsSection(r0, r6)
                if (r3 == 0) goto L_0x004c
                goto L_0x004d
            L_0x004c:
                r1 = r0
            L_0x004d:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = r5.mController
                java.util.Objects.requireNonNull(r0)
                com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager r0 = r0.mNotificationRoundnessManager
                com.android.systemui.statusbar.notification.row.ExpandableView r2 = (com.android.systemui.statusbar.notification.row.ExpandableView) r2
                com.android.systemui.statusbar.notification.row.ExpandableView r6 = (com.android.systemui.statusbar.notification.row.ExpandableView) r6
                com.android.systemui.statusbar.notification.row.ExpandableView r1 = (com.android.systemui.statusbar.notification.row.ExpandableView) r1
                r0.setViewsAffectedBySwipe(r2, r6, r1)
                r5.updateFirstAndLastBackgroundViews()
                r5.requestDisallowInterceptTouchEvent(r4)
                r5.updateContinuousShadowDrawing()
                r5.updateContinuousBackgroundDrawing()
                r5.requestChildrenUpdate()
            L_0x006c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13777.onBeginDrag(android.view.View):void");
        }
    };
    public int mNotificationDragDownMovement;
    public final NotificationEntryManager mNotificationEntryManager;
    public final NotificationGutsManager mNotificationGutsManager;
    public final NotificationListContainerImpl mNotificationListContainer = new NotificationListContainerImpl();
    public final NotificationRoundnessManager mNotificationRoundnessManager;
    public final NotificationSwipeHelper.Builder mNotificationSwipeHelperBuilder;
    @VisibleForTesting
    public final View.OnAttachStateChangeListener mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {
        public final void onViewAttachedToWindow(View view) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mConfigurationController.addCallback(notificationStackScrollLayoutController.mConfigurationListener);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController2.mZenModeController.addCallback(notificationStackScrollLayoutController2.mZenModeControllerCallback);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController3.mBarState = notificationStackScrollLayoutController3.mStatusBarStateController.getState();
            NotificationStackScrollLayoutController notificationStackScrollLayoutController4 = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController4.mStatusBarStateController.addCallback(notificationStackScrollLayoutController4.mStateListener, 2);
        }

        public final void onViewDetachedFromWindow(View view) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mConfigurationController.removeCallback(notificationStackScrollLayoutController.mConfigurationListener);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController2.mZenModeController.removeCallback(notificationStackScrollLayoutController2.mZenModeControllerCallback);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController3 = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController3.mStatusBarStateController.removeCallback(notificationStackScrollLayoutController3.mStateListener);
        }
    };
    public final C13788 mOnHeadsUpChangedListener = new OnHeadsUpChangedListener() {
        public final void onHeadsUpPinned(NotificationEntry notificationEntry) {
            NotificationStackScrollLayoutController.this.mNotificationRoundnessManager.updateView(notificationEntry.row, false);
        }

        public final void onHeadsUpPinnedModeChanged(boolean z) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.mInHeadsUpPinnedMode = z;
            notificationStackScrollLayout.updateClipping();
        }

        public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
            long count = NotificationStackScrollLayoutController.this.mHeadsUpManager.getAllEntries().count();
            NotificationEntry topEntry = NotificationStackScrollLayoutController.this.mHeadsUpManager.getTopEntry();
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.mNumHeadsUp = count;
            Objects.requireNonNull(notificationStackScrollLayout.mAmbientState);
            NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout2);
            notificationStackScrollLayout2.mTopHeadsUpEntry = topEntry;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            Objects.requireNonNull(notificationStackScrollLayoutController);
            NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayoutController.mView;
            Objects.requireNonNull(notificationStackScrollLayout3);
            notificationStackScrollLayout3.generateHeadsUpAnimation(notificationEntry.row, z);
            NotificationStackScrollLayoutController.this.mNotificationRoundnessManager.updateView(notificationEntry.row, true);
        }

        public final void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            expandableNotificationRow.post(new CarrierTextManager$$ExternalSyntheticLambda1(this, expandableNotificationRow, 4));
        }
    };
    public final NotificationRemoteInputManager mRemoteInputManager;
    public final Resources mResources;
    public final ScrimController mScrimController;
    public final ShadeController mShadeController;
    public boolean mShowEmptyShadeView;
    public final SectionHeaderController mSilentHeaderController;
    public final StackStateLogger mStackStateLogger;
    public final C13744 mStateListener = new StatusBarStateController.StateListener() {
        public final void onStatePreChange(int i, int i2) {
            if (i == 2 && i2 == 1) {
                NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
                Objects.requireNonNull(notificationStackScrollLayout);
                if (notificationStackScrollLayout.mIsExpanded && notificationStackScrollLayout.mAnimationsEnabled) {
                    notificationStackScrollLayout.mEverythingNeedsAnimation = true;
                    notificationStackScrollLayout.mNeedsAnimation = true;
                    notificationStackScrollLayout.requestChildrenUpdate();
                }
            }
        }

        public final void onStateChanged(int i) {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mBarState = i;
            notificationStackScrollLayoutController.mView.setStatusBarState(i);
        }

        public final void onStatePostChange() {
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mView.updateSensitiveness(notificationStackScrollLayoutController.mStatusBarStateController.goingToFullShade(), NotificationStackScrollLayoutController.this.mLockscreenUserManager.isAnyProfilePublicMode());
            NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = NotificationStackScrollLayoutController.this;
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController2.mView;
            boolean fromShadeLocked = notificationStackScrollLayoutController2.mStatusBarStateController.fromShadeLocked();
            Objects.requireNonNull(notificationStackScrollLayout);
            boolean onKeyguard = notificationStackScrollLayout.onKeyguard();
            AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
            Objects.requireNonNull(ambientState);
            ambientState.mActivatedChild = null;
            AmbientState ambientState2 = notificationStackScrollLayout.mAmbientState;
            Objects.requireNonNull(ambientState2);
            ambientState2.mDimmed = onKeyguard;
            HeadsUpAppearanceController headsUpAppearanceController = notificationStackScrollLayout.mHeadsUpAppearanceController;
            if (headsUpAppearanceController != null) {
                headsUpAppearanceController.updateTopEntry();
            }
            notificationStackScrollLayout.setDimmed(onKeyguard, fromShadeLocked);
            boolean z = true;
            ExpandHelper expandHelper = notificationStackScrollLayout.mExpandHelper;
            Objects.requireNonNull(expandHelper);
            expandHelper.mEnabled = !onKeyguard;
            AmbientState ambientState3 = notificationStackScrollLayout.mAmbientState;
            Objects.requireNonNull(ambientState3);
            ActivatableNotificationView activatableNotificationView = ambientState3.mActivatedChild;
            AmbientState ambientState4 = notificationStackScrollLayout.mAmbientState;
            Objects.requireNonNull(ambientState4);
            ambientState4.mActivatedChild = null;
            if (notificationStackScrollLayout.mAnimationsEnabled) {
                notificationStackScrollLayout.mActivateNeedsAnimation = true;
                notificationStackScrollLayout.mNeedsAnimation = true;
            }
            notificationStackScrollLayout.requestChildrenUpdate();
            int i = 0;
            if (activatableNotificationView != null) {
                if (activatableNotificationView.mActivated) {
                    activatableNotificationView.mActivated = false;
                }
                ActivatableNotificationView.OnActivatedListener onActivatedListener = activatableNotificationView.mOnActivatedListener;
                if (onActivatedListener != null) {
                    onActivatedListener.onActivationReset(activatableNotificationView);
                }
            }
            notificationStackScrollLayout.updateFooter();
            notificationStackScrollLayout.requestChildrenUpdate();
            notificationStackScrollLayout.onUpdateRowStates();
            if (notificationStackScrollLayout.mAmbientState.isFullyHidden() && notificationStackScrollLayout.onKeyguard()) {
                z = false;
            }
            if (!z) {
                i = 4;
            }
            notificationStackScrollLayout.setVisibility(i);
            NotificationStackScrollLayoutController.this.mNotificationEntryManager.updateNotifications("StatusBar state changed");
        }
    };
    public final StatusBar mStatusBar;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public NotificationSwipeHelper mSwipeHelper;
    public int mTotalDistanceForFullShadeTransition;
    public final TunerService mTunerService;
    public final UiEventLogger mUiEventLogger;
    public NotificationStackScrollLayout mView;
    public final NotificationVisibilityProvider mVisibilityProvider;
    public final VisualStabilityManager mVisualStabilityManager;
    public final ZenModeController mZenModeController;
    public final C13799 mZenModeControllerCallback = new ZenModeController.Callback() {
        public final void onZenChanged(int i) {
            NotificationStackScrollLayoutController.this.updateShowEmptyShadeView();
        }
    };

    public class NotifStackControllerImpl implements NotifStackController {
        public NotifStackControllerImpl() {
        }
    }

    public class NotificationListContainerImpl implements NotificationListContainer {
        public NotificationListContainerImpl() {
        }

        public final void addContainerView(View view) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            Assert.isMainThread();
            notificationStackScrollLayout.addView(view);
            if (view instanceof ExpandableNotificationRow) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationStackScrollLayout.mController;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                if (notificationStackScrollLayoutController.mShowEmptyShadeView) {
                    notificationStackScrollLayout.mController.updateShowEmptyShadeView();
                    notificationStackScrollLayout.updateFooter();
                }
            }
            notificationStackScrollLayout.mSpeedBumpIndexDirty = true;
        }

        public final void addContainerViewAt(View view, int i) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            Assert.isMainThread();
            if (view.getParent() != null && (view instanceof ExpandableView)) {
                ((ExpandableView) view).removeFromTransientContainerForAdditionTo(notificationStackScrollLayout);
            }
            notificationStackScrollLayout.addView(view, i);
            if (view instanceof ExpandableNotificationRow) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationStackScrollLayout.mController;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                if (notificationStackScrollLayoutController.mShowEmptyShadeView) {
                    notificationStackScrollLayout.mController.updateShowEmptyShadeView();
                    notificationStackScrollLayout.updateFooter();
                }
            }
            notificationStackScrollLayout.mSpeedBumpIndexDirty = true;
        }

        public final void applyExpandAnimationParams(ExpandAnimationParameters expandAnimationParameters) {
            boolean z;
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.mLaunchAnimationParams = expandAnimationParameters;
            boolean z2 = true;
            if (expandAnimationParameters != null) {
                z = true;
            } else {
                z = false;
            }
            if (z != notificationStackScrollLayout.mLaunchingNotification) {
                notificationStackScrollLayout.mLaunchingNotification = z;
                if (expandAnimationParameters == null || (expandAnimationParameters.startRoundedTopClipping <= 0 && expandAnimationParameters.parentStartRoundedTopClipping <= 0)) {
                    z2 = false;
                }
                notificationStackScrollLayout.mLaunchingNotificationNeedsToBeClipped = z2;
                if (!z2 || !z) {
                    notificationStackScrollLayout.mLaunchedNotificationClipPath.reset();
                }
                notificationStackScrollLayout.invalidate();
            }
            notificationStackScrollLayout.updateLaunchedNotificationClipPath();
            notificationStackScrollLayout.requestChildrenUpdate();
        }

        public final void bindRow(ExpandableNotificationRow expandableNotificationRow) {
            WifiPickerTracker$$ExternalSyntheticLambda1 wifiPickerTracker$$ExternalSyntheticLambda1 = new WifiPickerTracker$$ExternalSyntheticLambda1(this, expandableNotificationRow, 1);
            Objects.requireNonNull(expandableNotificationRow);
            expandableNotificationRow.mHeadsUpAnimatingAwayListener = wifiPickerTracker$$ExternalSyntheticLambda1;
        }

        public final void changeViewPosition(ExpandableView expandableView, int i) {
            NotificationStackScrollLayoutController.this.mView.changeViewPosition(expandableView, i);
        }

        public final void cleanUpViewStateForEntry(NotificationEntry notificationEntry) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            NotificationSwipeHelper notificationSwipeHelper = notificationStackScrollLayout.mSwipeHelper;
            Objects.requireNonNull(notificationSwipeHelper);
            if (expandableNotificationRow == notificationSwipeHelper.mTranslatingParentView) {
                NotificationSwipeHelper notificationSwipeHelper2 = notificationStackScrollLayout.mSwipeHelper;
                Objects.requireNonNull(notificationSwipeHelper2);
                notificationSwipeHelper2.setTranslatingParentView((View) null);
            }
        }

        public final boolean containsView(View view) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            if (view.getParent() == notificationStackScrollLayout) {
                return true;
            }
            return false;
        }

        public final void generateAddAnimation(ExpandableNotificationRow expandableNotificationRow, boolean z) {
            NotificationStackScrollLayoutController.this.mView.generateAddAnimation(expandableNotificationRow, z);
        }

        public final void generateChildOrderChangedEvent() {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            if (notificationStackScrollLayout.mIsExpanded && notificationStackScrollLayout.mAnimationsEnabled) {
                notificationStackScrollLayout.mGenerateChildOrderChangedEvent = true;
                notificationStackScrollLayout.mNeedsAnimation = true;
                notificationStackScrollLayout.requestChildrenUpdate();
            }
        }

        public final View getContainerChildAt(int i) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            return notificationStackScrollLayout.getChildAt(i);
        }

        public final int getContainerChildCount() {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            return notificationStackScrollLayout.getChildCount();
        }

        public final NotificationSwipeHelper getSwipeActionHelper() {
            return NotificationStackScrollLayoutController.this.mSwipeHelper;
        }

        public final int getTopClippingStartLocation() {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            if (notificationStackScrollLayout.mIsExpanded) {
                return notificationStackScrollLayout.mQsScrollBoundaryPosition;
            }
            return 0;
        }

        public final NotificationStackScrollLayout getViewParentForNotification() {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            return notificationStackScrollLayout;
        }

        public final boolean isInVisibleLocation(NotificationEntry notificationEntry) {
            Objects.requireNonNull(NotificationStackScrollLayoutController.this);
            return NotificationStackScrollLayoutController.isInVisibleLocation(notificationEntry);
        }

        public final void notifyGroupChildAdded(ExpandableNotificationRow expandableNotificationRow) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.onViewAddedInternal(expandableNotificationRow);
        }

        public final void notifyGroupChildRemoved(ExpandableNotificationRow expandableNotificationRow, ViewGroup viewGroup) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.onViewRemovedInternal(expandableNotificationRow, viewGroup);
        }

        public final void onHeightChanged(ExpandableView expandableView, boolean z) {
            NotificationStackScrollLayoutController.this.mView.onChildHeightChanged(expandableView, z);
        }

        public final void onReset(ExpandableNotificationRow expandableNotificationRow) {
            boolean z;
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            if ((notificationStackScrollLayout.mAnimationsEnabled || notificationStackScrollLayout.mPulsing) && (notificationStackScrollLayout.mIsExpanded || NotificationStackScrollLayout.isPinnedHeadsUp(expandableNotificationRow))) {
                z = true;
            } else {
                z = false;
            }
            boolean z2 = expandableNotificationRow instanceof ExpandableNotificationRow;
            if (z2) {
                expandableNotificationRow.setIconAnimationRunning(z);
            }
            if (z2) {
                expandableNotificationRow.setChronometerRunning(notificationStackScrollLayout.mIsExpanded);
            }
        }

        public final void removeContainerView(View view) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            Assert.isMainThread();
            notificationStackScrollLayout.removeView(view);
            if (view instanceof ExpandableNotificationRow) {
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationStackScrollLayout.mController;
                Objects.requireNonNull(notificationStackScrollLayoutController);
                if (!notificationStackScrollLayoutController.mShowEmptyShadeView) {
                    notificationStackScrollLayout.mController.updateShowEmptyShadeView();
                    notificationStackScrollLayout.updateFooter();
                }
            }
            notificationStackScrollLayout.mSpeedBumpIndexDirty = true;
        }

        public final void resetExposedMenuView() {
            NotificationStackScrollLayoutController.this.mSwipeHelper.resetExposedMenuView(false, true);
        }

        public final void setChildLocationsChangedListener(NotificationLogger.C12971 r1) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.mListener = r1;
        }

        public final void setChildTransferInProgress(boolean z) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            Assert.isMainThread();
            notificationStackScrollLayout.mChildTransferInProgress = z;
        }

        public final void setExpandingNotification(ExpandableNotificationRow expandableNotificationRow) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            Objects.requireNonNull(notificationStackScrollLayout);
            ExpandableNotificationRow expandableNotificationRow2 = notificationStackScrollLayout.mExpandingNotificationRow;
            if (expandableNotificationRow2 != null && expandableNotificationRow == null) {
                expandableNotificationRow2.mExpandingClipPath = null;
                expandableNotificationRow2.invalidate();
                ExpandableNotificationRow expandableNotificationRow3 = notificationStackScrollLayout.mExpandingNotificationRow;
                Objects.requireNonNull(expandableNotificationRow3);
                ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow3.mNotificationParent;
                if (expandableNotificationRow4 != null) {
                    expandableNotificationRow4.mExpandingClipPath = null;
                    expandableNotificationRow4.invalidate();
                }
            }
            notificationStackScrollLayout.mExpandingNotificationRow = expandableNotificationRow;
            notificationStackScrollLayout.updateLaunchedNotificationClipPath();
            notificationStackScrollLayout.requestChildrenUpdate();
        }
    }

    public class TouchHandler implements Gefingerpoken {
        public TouchHandler() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:18:0x007b  */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x009f  */
        /* JADX WARNING: Removed duplicated region for block: B:40:0x00e4  */
        /* JADX WARNING: Removed duplicated region for block: B:41:0x00e6  */
        /* JADX WARNING: Removed duplicated region for block: B:51:0x010b  */
        /* JADX WARNING: Removed duplicated region for block: B:59:0x012e A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:64:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean onInterceptTouchEvent(android.view.MotionEvent r9) {
            /*
                r8 = this;
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r0.mView
                java.util.Objects.requireNonNull(r0)
                int r1 = r9.getAction()
                r2 = 1
                r3 = 0
                if (r1 != 0) goto L_0x002c
                r0.mExpandedInThisMotion = r3
                android.widget.OverScroller r1 = r0.mScroller
                boolean r1 = r1.isFinished()
                r1 = r1 ^ r2
                r0.mOnlyScrollingInThisMotion = r1
                r0.mDisallowScrollingInThisMotion = r3
                r0.mDisallowDismissInThisMotion = r3
                r0.mTouchIsClick = r2
                float r1 = r9.getX()
                r0.mInitialTouchX = r1
                float r1 = r9.getY()
                r0.mInitialTouchY = r1
            L_0x002c:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r0.mView
                r0.handleEmptySpaceClick(r9)
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.row.NotificationGutsManager r0 = r0.mNotificationGutsManager
                java.util.Objects.requireNonNull(r0)
                com.android.systemui.statusbar.notification.row.NotificationGuts r0 = r0.mNotificationGutsExposed
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r1 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                android.view.View r4 = r1.mLongPressedView
                if (r4 == 0) goto L_0x0049
                com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r1 = r1.mSwipeHelper
                boolean r1 = r1.onInterceptTouchEvent(r9)
                goto L_0x004a
            L_0x0049:
                r1 = r3
            L_0x004a:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r4 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                android.view.View r5 = r4.mLongPressedView
                if (r5 != 0) goto L_0x0074
                com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r4 = r4.mSwipeHelper
                java.util.Objects.requireNonNull(r4)
                boolean r4 = r4.mIsSwiping
                if (r4 != 0) goto L_0x0074
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r4 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r4 = r4.mView
                java.util.Objects.requireNonNull(r4)
                boolean r4 = r4.mOnlyScrollingInThisMotion
                if (r4 != 0) goto L_0x0074
                if (r0 != 0) goto L_0x0074
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r4 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r4 = r4.mView
                java.util.Objects.requireNonNull(r4)
                com.android.systemui.ExpandHelper r4 = r4.mExpandHelper
                boolean r4 = r4.onInterceptTouchEvent(r9)
                goto L_0x0075
            L_0x0074:
                r4 = r3
            L_0x0075:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r5 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                android.view.View r6 = r5.mLongPressedView
                if (r6 != 0) goto L_0x0098
                com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r5 = r5.mSwipeHelper
                java.util.Objects.requireNonNull(r5)
                boolean r5 = r5.mIsSwiping
                if (r5 != 0) goto L_0x0098
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r5 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r5.mView
                java.util.Objects.requireNonNull(r5)
                boolean r5 = r5.mExpandingNotification
                if (r5 != 0) goto L_0x0098
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r5 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r5.mView
                boolean r5 = r5.onInterceptTouchEventScroll(r9)
                goto L_0x0099
            L_0x0098:
                r5 = r3
            L_0x0099:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                android.view.View r7 = r6.mLongPressedView
                if (r7 != 0) goto L_0x00dd
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r6.mView
                java.util.Objects.requireNonNull(r6)
                boolean r6 = r6.mIsBeingDragged
                if (r6 != 0) goto L_0x00dd
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r6.mView
                java.util.Objects.requireNonNull(r6)
                boolean r6 = r6.mExpandingNotification
                if (r6 != 0) goto L_0x00dd
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r6.mView
                java.util.Objects.requireNonNull(r6)
                boolean r6 = r6.mExpandedInThisMotion
                if (r6 != 0) goto L_0x00dd
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r6.mView
                java.util.Objects.requireNonNull(r6)
                boolean r6 = r6.mOnlyScrollingInThisMotion
                if (r6 != 0) goto L_0x00dd
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r6.mView
                java.util.Objects.requireNonNull(r6)
                boolean r6 = r6.mDisallowDismissInThisMotion
                if (r6 != 0) goto L_0x00dd
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r6 = r6.mSwipeHelper
                boolean r6 = r6.onInterceptTouchEvent(r9)
                goto L_0x00de
            L_0x00dd:
                r6 = r3
            L_0x00de:
                int r7 = r9.getActionMasked()
                if (r7 != r2) goto L_0x00e6
                r7 = r2
                goto L_0x00e7
            L_0x00e6:
                r7 = r3
            L_0x00e7:
                boolean r0 = com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper.isTouchInView(r9, r0)
                if (r0 != 0) goto L_0x0105
                if (r7 == 0) goto L_0x0105
                if (r6 != 0) goto L_0x0105
                if (r4 != 0) goto L_0x0105
                if (r5 != 0) goto L_0x0105
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r0.mView
                java.util.Objects.requireNonNull(r0)
                r0.mCheckForLeavebehind = r3
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.row.NotificationGutsManager r0 = r0.mNotificationGutsManager
                r0.closeAndSaveGuts(r2, r3, r3, r3)
            L_0x0105:
                int r0 = r9.getActionMasked()
                if (r0 != r2) goto L_0x0114
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r0.mView
                java.util.Objects.requireNonNull(r0)
                r0.mCheckForLeavebehind = r2
            L_0x0114:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.internal.jank.InteractionJankMonitor r0 = r0.mJankMonitor
                if (r0 == 0) goto L_0x012c
                if (r5 == 0) goto L_0x012c
                int r9 = r9.getActionMasked()
                if (r9 == 0) goto L_0x012c
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r8 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.internal.jank.InteractionJankMonitor r9 = r8.mJankMonitor
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r8 = r8.mView
                r0 = 2
                r9.begin(r8, r0)
            L_0x012c:
                if (r6 != 0) goto L_0x0136
                if (r5 != 0) goto L_0x0136
                if (r4 != 0) goto L_0x0136
                if (r1 == 0) goto L_0x0135
                goto L_0x0136
            L_0x0135:
                r2 = r3
            L_0x0136:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.TouchHandler.onInterceptTouchEvent(android.view.MotionEvent):boolean");
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x004b  */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x00b7  */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x00e8  */
        /* JADX WARNING: Removed duplicated region for block: B:60:0x0123  */
        /* JADX WARNING: Removed duplicated region for block: B:68:0x013d  */
        /* JADX WARNING: Removed duplicated region for block: B:71:0x0159  */
        /* JADX WARNING: Removed duplicated region for block: B:72:0x0162  */
        /* JADX WARNING: Removed duplicated region for block: B:85:0x018c A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:90:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean onTouchEvent(android.view.MotionEvent r13) {
            /*
                r12 = this;
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.row.NotificationGutsManager r0 = r0.mNotificationGutsManager
                java.util.Objects.requireNonNull(r0)
                com.android.systemui.statusbar.notification.row.NotificationGuts r0 = r0.mNotificationGutsExposed
                int r1 = r13.getActionMasked()
                r2 = 3
                r3 = 1
                r4 = 0
                if (r1 == r2) goto L_0x001b
                int r1 = r13.getActionMasked()
                if (r1 != r3) goto L_0x0019
                goto L_0x001b
            L_0x0019:
                r1 = r4
                goto L_0x001c
            L_0x001b:
                r1 = r3
            L_0x001c:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r5 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r5.mView
                r5.handleEmptySpaceClick(r13)
                if (r0 == 0) goto L_0x0032
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r5 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                android.view.View r6 = r5.mLongPressedView
                if (r6 == 0) goto L_0x0032
                com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r5 = r5.mSwipeHelper
                boolean r5 = r5.onTouchEvent(r13)
                goto L_0x0033
            L_0x0032:
                r5 = r4
            L_0x0033:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r6.mView
                java.util.Objects.requireNonNull(r6)
                boolean r6 = r6.mOnlyScrollingInThisMotion
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r7 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r7 = r7.mView
                java.util.Objects.requireNonNull(r7)
                boolean r7 = r7.mExpandingNotification
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r8 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                android.view.View r9 = r8.mLongPressedView
                if (r9 != 0) goto L_0x00b0
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r8 = r8.mView
                java.util.Objects.requireNonNull(r8)
                boolean r8 = r8.mIsExpanded
                if (r8 == 0) goto L_0x00b0
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r8 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r8 = r8.mSwipeHelper
                java.util.Objects.requireNonNull(r8)
                boolean r8 = r8.mIsSwiping
                if (r8 != 0) goto L_0x00b0
                if (r6 != 0) goto L_0x00b0
                if (r0 != 0) goto L_0x00b0
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r8 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r8 = r8.mView
                java.util.Objects.requireNonNull(r8)
                com.android.systemui.ExpandHelper r8 = r8.mExpandHelper
                if (r1 == 0) goto L_0x0073
                java.util.Objects.requireNonNull(r8)
                r8.mOnlyMovements = r4
            L_0x0073:
                boolean r8 = r8.onTouchEvent(r13)
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r9 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r9 = r9.mView
                java.util.Objects.requireNonNull(r9)
                boolean r9 = r9.mExpandingNotification
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r10 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r10 = r10.mView
                java.util.Objects.requireNonNull(r10)
                boolean r10 = r10.mExpandedInThisMotion
                if (r10 == 0) goto L_0x00ae
                if (r9 != 0) goto L_0x00ae
                if (r7 == 0) goto L_0x00ae
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r7 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r7 = r7.mView
                java.util.Objects.requireNonNull(r7)
                boolean r7 = r7.mDisallowScrollingInThisMotion
                if (r7 != 0) goto L_0x00ae
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r7 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r7 = r7.mView
                java.util.Objects.requireNonNull(r7)
                android.view.MotionEvent r10 = android.view.MotionEvent.obtain(r13)
                r10.setAction(r4)
                r7.onScrollTouch(r10)
                r10.recycle()
            L_0x00ae:
                r7 = r9
                goto L_0x00b1
            L_0x00b0:
                r8 = r4
            L_0x00b1:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r9 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                android.view.View r10 = r9.mLongPressedView
                if (r10 != 0) goto L_0x00e1
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r9 = r9.mView
                java.util.Objects.requireNonNull(r9)
                boolean r9 = r9.mIsExpanded
                if (r9 == 0) goto L_0x00e1
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r9 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r9 = r9.mSwipeHelper
                java.util.Objects.requireNonNull(r9)
                boolean r9 = r9.mIsSwiping
                if (r9 != 0) goto L_0x00e1
                if (r7 != 0) goto L_0x00e1
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r9 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r9 = r9.mView
                java.util.Objects.requireNonNull(r9)
                boolean r9 = r9.mDisallowScrollingInThisMotion
                if (r9 != 0) goto L_0x00e1
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r9 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r9 = r9.mView
                boolean r9 = r9.onScrollTouch(r13)
                goto L_0x00e2
            L_0x00e1:
                r9 = r4
            L_0x00e2:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r10 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                android.view.View r11 = r10.mLongPressedView
                if (r11 != 0) goto L_0x0114
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r10 = r10.mView
                java.util.Objects.requireNonNull(r10)
                boolean r10 = r10.mIsBeingDragged
                if (r10 != 0) goto L_0x0114
                if (r7 != 0) goto L_0x0114
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r7 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r7 = r7.mView
                java.util.Objects.requireNonNull(r7)
                boolean r7 = r7.mExpandedInThisMotion
                if (r7 != 0) goto L_0x0114
                if (r6 != 0) goto L_0x0114
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r6 = r6.mView
                java.util.Objects.requireNonNull(r6)
                boolean r6 = r6.mDisallowDismissInThisMotion
                if (r6 != 0) goto L_0x0114
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r6 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper r6 = r6.mSwipeHelper
                boolean r6 = r6.onTouchEvent(r13)
                goto L_0x0115
            L_0x0114:
                r6 = r4
            L_0x0115:
                if (r0 == 0) goto L_0x0137
                boolean r7 = com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper.isTouchInView(r13, r0)
                if (r7 != 0) goto L_0x0137
                com.android.systemui.statusbar.notification.row.NotificationGuts$GutsContent r0 = r0.mGutsContent
                boolean r7 = r0 instanceof com.android.systemui.statusbar.notification.row.NotificationSnooze
                if (r7 == 0) goto L_0x0137
                com.android.systemui.statusbar.notification.row.NotificationSnooze r0 = (com.android.systemui.statusbar.notification.row.NotificationSnooze) r0
                java.util.Objects.requireNonNull(r0)
                boolean r0 = r0.mExpanded
                if (r0 == 0) goto L_0x012e
                if (r1 != 0) goto L_0x0132
            L_0x012e:
                if (r6 != 0) goto L_0x0137
                if (r9 == 0) goto L_0x0137
            L_0x0132:
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                r0.checkSnoozeLeavebehind()
            L_0x0137:
                int r0 = r13.getActionMasked()
                if (r0 != r3) goto L_0x014f
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.plugins.FalsingManager r0 = r0.mFalsingManager
                r1 = 11
                r0.isFalseTouch(r1)
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r0.mView
                java.util.Objects.requireNonNull(r0)
                r0.mCheckForLeavebehind = r3
            L_0x014f:
                int r13 = r13.getActionMasked()
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.internal.jank.InteractionJankMonitor r1 = r0.mJankMonitor
                if (r1 != 0) goto L_0x0162
                java.lang.String r12 = "StackScrollerController"
                java.lang.String r13 = "traceJankOnTouchEvent, mJankMonitor is null"
                android.util.Log.w(r12, r13)
                goto L_0x018a
            L_0x0162:
                r7 = 2
                if (r13 == 0) goto L_0x0183
                if (r13 == r3) goto L_0x0170
                if (r13 == r2) goto L_0x016a
                goto L_0x018a
            L_0x016a:
                if (r9 == 0) goto L_0x018a
                r1.cancel(r7)
                goto L_0x018a
            L_0x0170:
                if (r9 == 0) goto L_0x018a
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r13 = r0.mView
                java.util.Objects.requireNonNull(r13)
                boolean r13 = r13.mFlingAfterUpEvent
                if (r13 != 0) goto L_0x018a
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r12 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
                com.android.internal.jank.InteractionJankMonitor r12 = r12.mJankMonitor
                r12.end(r7)
                goto L_0x018a
            L_0x0183:
                if (r9 == 0) goto L_0x018a
                com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r12 = r0.mView
                r1.begin(r12, r7)
            L_0x018a:
                if (r6 != 0) goto L_0x0194
                if (r9 != 0) goto L_0x0194
                if (r8 != 0) goto L_0x0194
                if (r5 == 0) goto L_0x0193
                goto L_0x0194
            L_0x0193:
                r3 = r4
            L_0x0194:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.TouchHandler.onTouchEvent(android.view.MotionEvent):boolean");
        }
    }

    public NotificationStackScrollLayoutController(boolean z, NotificationGutsManager notificationGutsManager, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManagerPhone headsUpManagerPhone, NotificationRoundnessManager notificationRoundnessManager, TunerService tunerService, DeviceProvisionedController deviceProvisionedController, DynamicPrivacyController dynamicPrivacyController, ConfigurationController configurationController, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardMediaController keyguardMediaController, KeyguardBypassController keyguardBypassController, ZenModeController zenModeController, NotificationLockscreenUserManager notificationLockscreenUserManager, MetricsLogger metricsLogger, FalsingCollector falsingCollector, FalsingManager falsingManager, Resources resources, NotificationSwipeHelper.Builder builder, StatusBar statusBar, ScrimController scrimController, NotificationGroupManagerLegacy notificationGroupManagerLegacy, GroupExpansionManager groupExpansionManager, SectionHeaderController sectionHeaderController, NotifPipelineFlags notifPipelineFlags, NotifPipeline notifPipeline, NotifCollection notifCollection, NotificationEntryManager notificationEntryManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, IStatusBarService iStatusBarService, UiEventLogger uiEventLogger, ForegroundServiceDismissalFeatureController foregroundServiceDismissalFeatureController, ForegroundServiceSectionController foregroundServiceSectionController, LayoutInflater layoutInflater, NotificationRemoteInputManager notificationRemoteInputManager, VisualStabilityManager visualStabilityManager, ShadeController shadeController, InteractionJankMonitor interactionJankMonitor, StackStateLogger stackStateLogger, NotificationStackScrollLogger notificationStackScrollLogger) {
        Resources resources2 = resources;
        this.mStackStateLogger = stackStateLogger;
        this.mLogger = notificationStackScrollLogger;
        this.mAllowLongPress = z;
        this.mNotificationGutsManager = notificationGutsManager;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mHeadsUpManager = headsUpManagerPhone;
        this.mNotificationRoundnessManager = notificationRoundnessManager;
        this.mTunerService = tunerService;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mDynamicPrivacyController = dynamicPrivacyController;
        this.mConfigurationController = configurationController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mKeyguardMediaController = keyguardMediaController;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mZenModeController = zenModeController;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mMetricsLogger = metricsLogger;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        this.mFalsingCollector = falsingCollector;
        this.mFalsingManager = falsingManager;
        this.mResources = resources2;
        this.mNotificationSwipeHelperBuilder = builder;
        this.mStatusBar = statusBar;
        this.mScrimController = scrimController;
        this.mJankMonitor = interactionJankMonitor;
        groupExpansionManager.registerGroupExpansionChangeListener(new C1381xbae1b0c0(this));
        C136710 r2 = new NotificationGroupManagerLegacy.OnGroupChangeListener() {
            public final void onGroupsChanged() {
                StatusBar statusBar = NotificationStackScrollLayoutController.this.mStatusBar;
                Objects.requireNonNull(statusBar);
                statusBar.mNotificationsController.requestNotificationUpdate("onGroupsChanged");
            }
        };
        Objects.requireNonNull(notificationGroupManagerLegacy);
        NotificationGroupManagerLegacy.GroupEventDispatcher groupEventDispatcher = notificationGroupManagerLegacy.mEventDispatcher;
        Objects.requireNonNull(groupEventDispatcher);
        groupEventDispatcher.mGroupChangeListeners.add(r2);
        this.mNotifPipelineFlags = notifPipelineFlags;
        this.mSilentHeaderController = sectionHeaderController;
        this.mNotifPipeline = notifPipeline;
        this.mNotifCollection = notifCollection;
        this.mNotificationEntryManager = notificationEntryManager;
        this.mIStatusBarService = iStatusBarService;
        this.mUiEventLogger = uiEventLogger;
        this.mFgFeatureController = foregroundServiceDismissalFeatureController;
        this.mFgServicesSectionController = foregroundServiceSectionController;
        this.mLayoutInflater = layoutInflater;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mVisualStabilityManager = visualStabilityManager;
        this.mShadeController = shadeController;
        this.mNotificationDragDownMovement = resources2.getDimensionPixelSize(C1777R.dimen.lockscreen_shade_notification_movement);
        this.mTotalDistanceForFullShadeTransition = resources2.getDimensionPixelSize(C1777R.dimen.lockscreen_shade_qs_transition_distance);
    }

    public enum NotificationPanelEvent implements UiEventLogger.UiEventEnum {
        INVALID(0),
        DISMISS_ALL_NOTIFICATIONS_PANEL(312),
        DISMISS_SILENT_NOTIFICATIONS_PANEL(314);
        
        private final int mId;

        /* access modifiers changed from: public */
        NotificationPanelEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public final void checkSnoozeLeavebehind() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        if (notificationStackScrollLayout.mCheckForLeavebehind) {
            this.mNotificationGutsManager.closeAndSaveGuts(true, false, false, false);
            NotificationStackScrollLayout notificationStackScrollLayout2 = this.mView;
            Objects.requireNonNull(notificationStackScrollLayout2);
            notificationStackScrollLayout2.mCheckForLeavebehind = false;
        }
    }

    public final int getChildCount() {
        return this.mView.getChildCount();
    }

    public final int getHeight() {
        return this.mView.getHeight();
    }

    public final int getNotGoneChildCount() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        int childCount = notificationStackScrollLayout.getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            ExpandableView expandableView = (ExpandableView) notificationStackScrollLayout.getChildAt(i2);
            if (!(expandableView.getVisibility() == 8 || expandableView.mWillBeGone || expandableView == notificationStackScrollLayout.mShelf)) {
                i++;
            }
        }
        return i;
    }

    public final int getVisibleNotificationCount() {
        NotifStats notifStats = this.mNotifStats;
        Objects.requireNonNull(notifStats);
        return notifStats.numActiveNotifs;
    }

    public final boolean hasNotifications(int i, boolean z) {
        boolean z2;
        boolean z3;
        if (z) {
            NotifStats notifStats = this.mNotifStats;
            Objects.requireNonNull(notifStats);
            z2 = notifStats.hasClearableAlertingNotifs;
        } else {
            NotifStats notifStats2 = this.mNotifStats;
            Objects.requireNonNull(notifStats2);
            z2 = notifStats2.hasNonClearableAlertingNotifs;
        }
        if (z) {
            NotifStats notifStats3 = this.mNotifStats;
            Objects.requireNonNull(notifStats3);
            z3 = notifStats3.hasClearableSilentNotifs;
        } else {
            NotifStats notifStats4 = this.mNotifStats;
            Objects.requireNonNull(notifStats4);
            z3 = notifStats4.hasNonClearableSilentNotifs;
        }
        if (i != 0) {
            if (i == 1) {
                return z2;
            }
            if (i == 2) {
                return z3;
            }
            throw new IllegalStateException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Bad selection: ", i));
        } else if (z3 || z2) {
            return true;
        } else {
            return false;
        }
    }

    public final boolean isAddOrRemoveAnimationPending() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        if (!notificationStackScrollLayout.mNeedsAnimation || (notificationStackScrollLayout.mChildrenToAddAnimated.isEmpty() && notificationStackScrollLayout.mChildrenToRemoveAnimated.isEmpty())) {
            return false;
        }
        return true;
    }

    public final boolean isHistoryEnabled() {
        Boolean bool = this.mHistoryEnabled;
        if (bool == null) {
            NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
            boolean z = false;
            if (notificationStackScrollLayout == null || notificationStackScrollLayout.getContext() == null) {
                Log.wtf("StackScrollerController", "isHistoryEnabled failed to initialize its value");
                return false;
            }
            if (Settings.Secure.getIntForUser(this.mView.getContext().getContentResolver(), "notification_history_enabled", 0, -2) == 1) {
                z = true;
            }
            bool = Boolean.valueOf(z);
            this.mHistoryEnabled = bool;
        }
        return bool.booleanValue();
    }

    public final void setShouldShowShelfOnly(boolean z) {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mView;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mShouldShowShelfOnly = z;
        notificationStackScrollLayout.updateAlgorithmLayoutMinHeight();
    }

    public final void updateFooter() {
        Trace.beginSection("NSSLC.updateFooter");
        this.mView.updateFooter();
        Trace.endSection();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001b, code lost:
        if (r0.mShouldUseSplitNotificationShade != false) goto L_0x001d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0053  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateShowEmptyShadeView() {
        /*
            r6 = this;
            java.lang.String r0 = "NSSLC.updateShowEmptyShadeView"
            android.os.Trace.beginSection(r0)
            int r0 = r6.mBarState
            r1 = 0
            r2 = 1
            if (r0 == r2) goto L_0x0025
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r6.mView
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mQsExpanded
            if (r0 == 0) goto L_0x001d
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r6.mView
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mShouldUseSplitNotificationShade
            if (r0 == 0) goto L_0x0025
        L_0x001d:
            int r0 = r6.getVisibleNotificationCount()
            if (r0 != 0) goto L_0x0025
            r0 = r2
            goto L_0x0026
        L_0x0025:
            r0 = r1
        L_0x0026:
            r6.mShowEmptyShadeView = r0
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r3 = r6.mView
            com.android.systemui.statusbar.policy.ZenModeController r6 = r6.mZenModeController
            boolean r6 = r6.areNotificationsHiddenInShade()
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.EmptyShadeView r4 = r3.mEmptyShadeView
            boolean r5 = r3.mIsExpanded
            if (r5 == 0) goto L_0x003e
            boolean r5 = r3.mAnimationsEnabled
            if (r5 == 0) goto L_0x003e
            r1 = r2
        L_0x003e:
            r4.setVisible(r0, r1)
            com.android.systemui.statusbar.EmptyShadeView r0 = r3.mEmptyShadeView
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mText
            if (r6 == 0) goto L_0x004e
            r6 = 2131952289(0x7f1302a1, float:1.9541017E38)
            goto L_0x0051
        L_0x004e:
            r6 = 2131952312(0x7f1302b8, float:1.9541063E38)
        L_0x0051:
            if (r0 == r6) goto L_0x005f
            com.android.systemui.statusbar.EmptyShadeView r0 = r3.mEmptyShadeView
            java.util.Objects.requireNonNull(r0)
            r0.mText = r6
            android.widget.TextView r0 = r0.mEmptyText
            r0.setText(r6)
        L_0x005f:
            android.os.Trace.endSection()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.updateShowEmptyShadeView():void");
    }

    public static void $r8$lambda$a_N8g0656__oN00XIQFjVt0r6vI(NotificationStackScrollLayoutController notificationStackScrollLayoutController, int i) {
        NotificationPanelEvent notificationPanelEvent;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        UiEventLogger uiEventLogger = notificationStackScrollLayoutController.mUiEventLogger;
        if (i == 0) {
            notificationPanelEvent = NotificationPanelEvent.DISMISS_ALL_NOTIFICATIONS_PANEL;
        } else if (i == 2) {
            notificationPanelEvent = NotificationPanelEvent.DISMISS_SILENT_NOTIFICATIONS_PANEL;
        } else {
            notificationPanelEvent = NotificationPanelEvent.INVALID;
        }
        uiEventLogger.log(notificationPanelEvent);
    }

    public static boolean isInVisibleLocation(NotificationEntry notificationEntry) {
        Objects.requireNonNull(notificationEntry);
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        Objects.requireNonNull(expandableNotificationRow);
        ExpandableViewState expandableViewState = expandableNotificationRow.mViewState;
        if (expandableViewState == null || (expandableViewState.location & 5) == 0 || expandableNotificationRow.getVisibility() != 0) {
            return false;
        }
        return true;
    }
}
