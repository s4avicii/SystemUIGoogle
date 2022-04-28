package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.util.ContrastColorUtil;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.settingslib.Utils;
import com.android.systemui.R$array;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda6;
import com.android.systemui.p006qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda10;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class NotificationIconAreaController implements DarkIconDispatcher.DarkReceiver, StatusBarStateController.StateListener, NotificationWakeUpCoordinator.WakeUpListener, DemoMode {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAnimationsEnabled;
    public int mAodIconAppearTranslation;
    public int mAodIconTint;
    public NotificationIconContainer mAodIcons;
    public boolean mAodIconsVisible;
    public final Optional<Bubbles> mBubblesOptional;
    public final KeyguardBypassController mBypassController;
    public Context mContext;
    public final ContrastColorUtil mContrastColorUtil;
    public final DozeParameters mDozeParameters;
    public int mIconHPadding;
    public int mIconSize;
    public int mIconTint = -1;
    public final NotificationMediaManager mMediaManager;
    public List<ListEntry> mNotificationEntries = List.of();
    public View mNotificationIconArea;
    public NotificationIconContainer mNotificationIcons;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final NotificationListener.NotificationSettingsListener mSettingsListener;
    public NotificationIconContainer mShelfIcons;
    public boolean mShowLowPriority = true;
    public final StatusBarStateController mStatusBarStateController;
    public final StatusBarWindowController mStatusBarWindowController;
    public final ArrayList<Rect> mTintAreas = new ArrayList<>();
    public final WMShell$7$$ExternalSyntheticLambda0 mUpdateStatusBarIcons = new WMShell$7$$ExternalSyntheticLambda0(this, 5);
    public final NotificationWakeUpCoordinator mWakeUpCoordinator;

    public final void applyNotificationIconsTint() {
        for (int i = 0; i < this.mNotificationIcons.getChildCount(); i++) {
            StatusBarIconView statusBarIconView = (StatusBarIconView) this.mNotificationIcons.getChildAt(i);
            if (statusBarIconView.getWidth() != 0) {
                updateTintForIcon(statusBarIconView, this.mIconTint);
            } else {
                statusBarIconView.mLayoutRunnable = new NotificationIconAreaController$$ExternalSyntheticLambda0(this, statusBarIconView, 0);
            }
        }
        updateAodIconColors();
    }

    public final void onStateChanged(int i) {
        updateAodIconsVisibility(false, false);
        updateAnimations();
    }

    public final void animateInAodIconTranslation() {
        this.mAodIcons.animate().setInterpolator(Interpolators.DECELERATE_QUINT).translationY(0.0f).setDuration(200).start();
    }

    public final List<String> demoCommands() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("notifications");
        return arrayList;
    }

    public final void dispatchDemoCommand(String str, Bundle bundle) {
        int i;
        if (this.mNotificationIconArea != null) {
            if ("false".equals(bundle.getString("visible"))) {
                i = 4;
            } else {
                i = 0;
            }
            this.mNotificationIconArea.setVisibility(i);
        }
    }

    public final void onDarkChanged(ArrayList<Rect> arrayList, float f, int i) {
        this.mTintAreas.clear();
        this.mTintAreas.addAll(arrayList);
        if (DarkIconDispatcher.isInAreas(arrayList, this.mNotificationIconArea)) {
            this.mIconTint = i;
        }
        applyNotificationIconsTint();
    }

    public final void onDemoModeFinished() {
        View view = this.mNotificationIconArea;
        if (view != null) {
            view.setVisibility(0);
        }
    }

    public final void onDozingChanged(boolean z) {
        boolean z2;
        if (this.mAodIcons != null) {
            if (!this.mDozeParameters.getAlwaysOn() || this.mDozeParameters.getDisplayNeedsBlanking()) {
                z2 = false;
            } else {
                z2 = true;
            }
            NotificationIconContainer notificationIconContainer = this.mAodIcons;
            Objects.requireNonNull(notificationIconContainer);
            notificationIconContainer.mDozing = z;
            notificationIconContainer.mDisallowNextAnimation |= !z2;
            for (int i = 0; i < notificationIconContainer.getChildCount(); i++) {
                View childAt = notificationIconContainer.getChildAt(i);
                if (childAt instanceof StatusBarIconView) {
                    ((StatusBarIconView) childAt).setDozing(z, z2);
                }
            }
        }
    }

    public final void onFullyHiddenChanged(boolean z) {
        boolean z2 = true;
        if (!this.mBypassController.getBypassEnabled()) {
            if (!this.mDozeParameters.getAlwaysOn() || this.mDozeParameters.getDisplayNeedsBlanking()) {
                z2 = false;
            }
            z2 &= z;
        }
        updateAodIconsVisibility(z2, false);
        updateAodNotificationIcons();
    }

    public final void onPulseExpansionChanged(boolean z) {
        if (z) {
            updateAodIconsVisibility(true, false);
        }
    }

    public final void updateAnimations() {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (this.mStatusBarStateController.getState() == 0) {
            z = true;
        } else {
            z = false;
        }
        NotificationIconContainer notificationIconContainer = this.mAodIcons;
        if (notificationIconContainer != null) {
            if (!this.mAnimationsEnabled || z) {
                z2 = false;
            } else {
                z2 = true;
            }
            notificationIconContainer.setAnimationsEnabled(z2);
        }
        NotificationIconContainer notificationIconContainer2 = this.mNotificationIcons;
        if (!this.mAnimationsEnabled || !z) {
            z3 = false;
        }
        notificationIconContainer2.setAnimationsEnabled(z3);
    }

    public final void updateAodIconColors() {
        if (this.mAodIcons != null) {
            for (int i = 0; i < this.mAodIcons.getChildCount(); i++) {
                StatusBarIconView statusBarIconView = (StatusBarIconView) this.mAodIcons.getChildAt(i);
                if (statusBarIconView.getWidth() != 0) {
                    updateTintForIcon(statusBarIconView, this.mAodIconTint);
                } else {
                    statusBarIconView.mLayoutRunnable = new InternetDialog$$ExternalSyntheticLambda10(this, statusBarIconView, 2);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0024  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0124  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateAodIconsVisibility(boolean r8, boolean r9) {
        /*
            r7 = this;
            com.android.systemui.statusbar.phone.NotificationIconContainer r0 = r7.mAodIcons
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            com.android.systemui.statusbar.phone.KeyguardBypassController r0 = r7.mBypassController
            boolean r0 = r0.getBypassEnabled()
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x001b
            com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator r0 = r7.mWakeUpCoordinator
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.notificationsFullyHidden
            if (r0 == 0) goto L_0x0019
            goto L_0x001b
        L_0x0019:
            r0 = r2
            goto L_0x001c
        L_0x001b:
            r0 = r1
        L_0x001c:
            com.android.systemui.plugins.statusbar.StatusBarStateController r3 = r7.mStatusBarStateController
            int r3 = r3.getState()
            if (r3 == r1) goto L_0x0052
            com.android.systemui.statusbar.phone.ScreenOffAnimationController r3 = r7.mScreenOffAnimationController
            java.util.Objects.requireNonNull(r3)
            java.util.ArrayList r3 = r3.animations
            boolean r4 = r3 instanceof java.util.Collection
            if (r4 == 0) goto L_0x0036
            boolean r4 = r3.isEmpty()
            if (r4 == 0) goto L_0x0036
            goto L_0x004e
        L_0x0036:
            java.util.Iterator r3 = r3.iterator()
        L_0x003a:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x004e
            java.lang.Object r4 = r3.next()
            com.android.systemui.statusbar.phone.ScreenOffAnimation r4 = (com.android.systemui.statusbar.phone.ScreenOffAnimation) r4
            boolean r4 = r4.shouldShowAodIconsWhenShade()
            if (r4 == 0) goto L_0x003a
            r3 = r1
            goto L_0x004f
        L_0x004e:
            r3 = r2
        L_0x004f:
            if (r3 != 0) goto L_0x0052
            r0 = r2
        L_0x0052:
            r3 = 0
            if (r0 == 0) goto L_0x0078
            com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator r4 = r7.mWakeUpCoordinator
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r4 = r4.mStackScrollerController
            if (r4 != 0) goto L_0x005f
            r4 = r3
        L_0x005f:
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r4 = r4.mView
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.statusbar.notification.stack.AmbientState r4 = r4.mAmbientState
            boolean r4 = r4.isPulseExpanding()
            if (r4 == 0) goto L_0x0078
            com.android.systemui.statusbar.phone.KeyguardBypassController r4 = r7.mBypassController
            boolean r4 = r4.getBypassEnabled()
            if (r4 != 0) goto L_0x0078
            r0 = r2
        L_0x0078:
            boolean r4 = r7.mAodIconsVisible
            if (r4 != r0) goto L_0x007e
            if (r9 == 0) goto L_0x0137
        L_0x007e:
            r7.mAodIconsVisible = r0
            com.android.systemui.statusbar.phone.NotificationIconContainer r9 = r7.mAodIcons
            android.view.ViewPropertyAnimator r9 = r9.animate()
            r9.cancel()
            r9 = 0
            r4 = 1065353216(0x3f800000, float:1.0)
            if (r8 == 0) goto L_0x0124
            com.android.systemui.statusbar.phone.NotificationIconContainer r8 = r7.mAodIcons
            int r8 = r8.getVisibility()
            if (r8 == 0) goto L_0x0098
            r8 = r1
            goto L_0x0099
        L_0x0098:
            r8 = r2
        L_0x0099:
            boolean r0 = r7.mAodIconsVisible
            r5 = 210(0xd2, double:1.04E-321)
            if (r0 == 0) goto L_0x011b
            if (r8 == 0) goto L_0x0112
            com.android.systemui.statusbar.phone.NotificationIconContainer r8 = r7.mAodIcons
            r8.setVisibility(r2)
            com.android.systemui.statusbar.phone.NotificationIconContainer r8 = r7.mAodIcons
            r8.setAlpha(r4)
            com.android.systemui.statusbar.phone.NotificationIconContainer r8 = r7.mAodIcons
            if (r8 != 0) goto L_0x00b1
            goto L_0x0137
        L_0x00b1:
            com.android.systemui.statusbar.phone.ScreenOffAnimationController r8 = r7.mScreenOffAnimationController
            java.util.Objects.requireNonNull(r8)
            java.util.ArrayList r8 = r8.animations
            boolean r0 = r8 instanceof java.util.Collection
            if (r0 == 0) goto L_0x00c3
            boolean r0 = r8.isEmpty()
            if (r0 == 0) goto L_0x00c3
            goto L_0x00da
        L_0x00c3:
            java.util.Iterator r8 = r8.iterator()
        L_0x00c7:
            boolean r0 = r8.hasNext()
            if (r0 == 0) goto L_0x00da
            java.lang.Object r0 = r8.next()
            com.android.systemui.statusbar.phone.ScreenOffAnimation r0 = (com.android.systemui.statusbar.phone.ScreenOffAnimation) r0
            boolean r0 = r0.shouldAnimateAodIcons()
            if (r0 != 0) goto L_0x00c7
            r1 = r2
        L_0x00da:
            if (r1 == 0) goto L_0x0107
            com.android.systemui.statusbar.phone.NotificationIconContainer r8 = r7.mAodIcons
            int r0 = r7.mAodIconAppearTranslation
            int r0 = -r0
            float r0 = (float) r0
            r8.setTranslationY(r0)
            com.android.systemui.statusbar.phone.NotificationIconContainer r8 = r7.mAodIcons
            r8.setAlpha(r9)
            r7.animateInAodIconTranslation()
            com.android.systemui.statusbar.phone.NotificationIconContainer r7 = r7.mAodIcons
            android.view.ViewPropertyAnimator r7 = r7.animate()
            android.view.ViewPropertyAnimator r7 = r7.alpha(r4)
            android.view.animation.LinearInterpolator r8 = com.android.systemui.animation.Interpolators.LINEAR
            android.view.ViewPropertyAnimator r7 = r7.setInterpolator(r8)
            r8 = 200(0xc8, double:9.9E-322)
            android.view.ViewPropertyAnimator r7 = r7.setDuration(r8)
            r7.start()
            goto L_0x0137
        L_0x0107:
            com.android.systemui.statusbar.phone.NotificationIconContainer r8 = r7.mAodIcons
            r8.setAlpha(r4)
            com.android.systemui.statusbar.phone.NotificationIconContainer r7 = r7.mAodIcons
            r7.setTranslationY(r9)
            goto L_0x0137
        L_0x0112:
            r7.animateInAodIconTranslation()
            com.android.systemui.statusbar.phone.NotificationIconContainer r7 = r7.mAodIcons
            androidx.leanback.R$raw.fadeIn((android.view.View) r7, (long) r5, (int) r2)
            goto L_0x0137
        L_0x011b:
            r7.animateInAodIconTranslation()
            com.android.systemui.statusbar.phone.NotificationIconContainer r7 = r7.mAodIcons
            androidx.leanback.R$raw.fadeOut((android.view.View) r7, (long) r5, (java.lang.Runnable) r3)
            goto L_0x0137
        L_0x0124:
            com.android.systemui.statusbar.phone.NotificationIconContainer r8 = r7.mAodIcons
            r8.setAlpha(r4)
            com.android.systemui.statusbar.phone.NotificationIconContainer r8 = r7.mAodIcons
            r8.setTranslationY(r9)
            com.android.systemui.statusbar.phone.NotificationIconContainer r7 = r7.mAodIcons
            if (r0 == 0) goto L_0x0133
            goto L_0x0134
        L_0x0133:
            r2 = 4
        L_0x0134:
            r7.setVisibility(r2)
        L_0x0137:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationIconAreaController.updateAodIconsVisibility(boolean, boolean):void");
    }

    public final void updateAodNotificationIcons() {
        NotificationIconContainer notificationIconContainer = this.mAodIcons;
        if (notificationIconContainer != null) {
            updateIconsForLayout(DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda6.INSTANCE$1, notificationIconContainer, false, true, true, true, true, this.mBypassController.getBypassEnabled());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0047, code lost:
        if (r7.equals(r8.mMediaNotificationKey) != false) goto L_0x0115;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d0, code lost:
        if (r7 != false) goto L_0x0115;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00fa, code lost:
        if (r5.mPulseSupressed != false) goto L_0x00fd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0113, code lost:
        if (r0.mBubblesOptional.get().isBubbleExpanded(r5.mKey) != false) goto L_0x0115;
     */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0118  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateIconsForLayout(java.util.function.Function<com.android.systemui.statusbar.notification.collection.NotificationEntry, com.android.systemui.statusbar.StatusBarIconView> r17, com.android.systemui.statusbar.phone.NotificationIconContainer r18, boolean r19, boolean r20, boolean r21, boolean r22, boolean r23, boolean r24) {
        /*
            r16 = this;
            r0 = r16
            r1 = r18
            java.util.ArrayList r2 = new java.util.ArrayList
            java.util.List<com.android.systemui.statusbar.notification.collection.ListEntry> r3 = r0.mNotificationEntries
            int r3 = r3.size()
            r2.<init>(r3)
            r3 = 0
            r4 = r3
        L_0x0011:
            java.util.List<com.android.systemui.statusbar.notification.collection.ListEntry> r5 = r0.mNotificationEntries
            int r5 = r5.size()
            r6 = 1
            if (r4 >= r5) goto L_0x012c
            java.util.List<com.android.systemui.statusbar.notification.collection.ListEntry> r5 = r0.mNotificationEntries
            java.lang.Object r5 = r5.get(r4)
            com.android.systemui.statusbar.notification.collection.ListEntry r5 = (com.android.systemui.statusbar.notification.collection.ListEntry) r5
            com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = r5.getRepresentativeEntry()
            if (r5 == 0) goto L_0x0126
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = r5.row
            if (r7 == 0) goto L_0x0126
            android.service.notification.NotificationListenerService$Ranking r7 = r5.mRanking
            boolean r7 = r7.isAmbient()
            if (r7 == 0) goto L_0x0038
            if (r19 != 0) goto L_0x0038
            goto L_0x0115
        L_0x0038:
            if (r23 == 0) goto L_0x004b
            java.lang.String r7 = r5.mKey
            com.android.systemui.statusbar.NotificationMediaManager r8 = r0.mMediaManager
            java.util.Objects.requireNonNull(r8)
            java.lang.String r8 = r8.mMediaNotificationKey
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x004b
            goto L_0x0115
        L_0x004b:
            if (r20 != 0) goto L_0x0056
            int r7 = r5.getImportance()
            r8 = 3
            if (r7 >= r8) goto L_0x0056
            goto L_0x0115
        L_0x0056:
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = r5.row
            if (r7 == 0) goto L_0x0064
            android.view.ViewParent r7 = r7.getParent()
            boolean r7 = r7 instanceof com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout
            if (r7 == 0) goto L_0x0064
            r7 = r6
            goto L_0x0065
        L_0x0064:
            r7 = r3
        L_0x0065:
            if (r7 != 0) goto L_0x0069
            goto L_0x0115
        L_0x0069:
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = r5.row
            int r7 = r7.getVisibility()
            r8 = 8
            if (r7 != r8) goto L_0x0075
            goto L_0x0115
        L_0x0075:
            boolean r7 = r5.isRowDismissed()
            if (r7 == 0) goto L_0x007f
            if (r21 == 0) goto L_0x007f
            goto L_0x0115
        L_0x007f:
            if (r22 == 0) goto L_0x00d3
            boolean r7 = r5.hasSentReply
            if (r7 != 0) goto L_0x0086
            goto L_0x00cf
        L_0x0086:
            android.service.notification.StatusBarNotification r7 = r5.mSbn
            android.app.Notification r7 = r7.getNotification()
            android.os.Bundle r7 = r7.extras
            java.lang.String r8 = "android.remoteInputHistoryItems"
            android.os.Parcelable[] r8 = r7.getParcelableArray(r8)
            boolean r8 = com.android.internal.util.ArrayUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x009b
            goto L_0x00c0
        L_0x009b:
            java.lang.String r8 = "android.messages"
            android.os.Parcelable[] r8 = r7.getParcelableArray(r8)
            java.util.List r8 = android.app.Notification.MessagingStyle.Message.getMessagesFromBundleArray(r8)
            if (r8 == 0) goto L_0x00cf
            boolean r9 = r8.isEmpty()
            if (r9 != 0) goto L_0x00cf
            int r9 = r8.size()
            int r9 = r9 - r6
            java.lang.Object r8 = r8.get(r9)
            android.app.Notification$MessagingStyle$Message r8 = (android.app.Notification.MessagingStyle.Message) r8
            if (r8 == 0) goto L_0x00cf
            android.app.Person r8 = r8.getSenderPerson()
            if (r8 != 0) goto L_0x00c2
        L_0x00c0:
            r7 = r6
            goto L_0x00d0
        L_0x00c2:
            java.lang.String r9 = "android.messagingUser"
            android.os.Parcelable r7 = r7.getParcelable(r9)
            android.app.Person r7 = (android.app.Person) r7
            boolean r7 = java.util.Objects.equals(r7, r8)
            goto L_0x00d0
        L_0x00cf:
            r7 = r3
        L_0x00d0:
            if (r7 == 0) goto L_0x00d3
            goto L_0x0115
        L_0x00d3:
            if (r19 != 0) goto L_0x00de
            r7 = 32
            boolean r7 = r5.shouldSuppressVisualEffect(r7)
            if (r7 == 0) goto L_0x00de
            goto L_0x0115
        L_0x00de:
            if (r24 == 0) goto L_0x00fd
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = r5.row
            if (r7 == 0) goto L_0x00ec
            boolean r7 = r7.showingPulsing()
            if (r7 == 0) goto L_0x00ec
            r7 = r6
            goto L_0x00ed
        L_0x00ec:
            r7 = r3
        L_0x00ed:
            if (r7 == 0) goto L_0x00fd
            com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator r7 = r0.mWakeUpCoordinator
            java.util.Objects.requireNonNull(r7)
            boolean r7 = r7.notificationsFullyHidden
            if (r7 == 0) goto L_0x0115
            boolean r7 = r5.mPulseSupressed
            if (r7 != 0) goto L_0x00fd
            goto L_0x0115
        L_0x00fd:
            java.util.Optional<com.android.wm.shell.bubbles.Bubbles> r7 = r0.mBubblesOptional
            boolean r7 = r7.isPresent()
            if (r7 == 0) goto L_0x0116
            java.util.Optional<com.android.wm.shell.bubbles.Bubbles> r7 = r0.mBubblesOptional
            java.lang.Object r7 = r7.get()
            com.android.wm.shell.bubbles.Bubbles r7 = (com.android.p012wm.shell.bubbles.Bubbles) r7
            java.lang.String r8 = r5.mKey
            boolean r7 = r7.isBubbleExpanded(r8)
            if (r7 == 0) goto L_0x0116
        L_0x0115:
            r6 = r3
        L_0x0116:
            if (r6 == 0) goto L_0x0126
            r7 = r17
            java.lang.Object r5 = r7.apply(r5)
            com.android.systemui.statusbar.StatusBarIconView r5 = (com.android.systemui.statusbar.StatusBarIconView) r5
            if (r5 == 0) goto L_0x0128
            r2.add(r5)
            goto L_0x0128
        L_0x0126:
            r7 = r17
        L_0x0128:
            int r4 = r4 + 1
            goto L_0x0011
        L_0x012c:
            androidx.collection.ArrayMap r4 = new androidx.collection.ArrayMap
            r4.<init>()
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r7 = r3
        L_0x0137:
            int r8 = r18.getChildCount()
            r9 = 0
            if (r7 >= r8) goto L_0x01a9
            android.view.View r8 = r1.getChildAt(r7)
            boolean r10 = r8 instanceof com.android.systemui.statusbar.StatusBarIconView
            if (r10 != 0) goto L_0x0147
            goto L_0x01a6
        L_0x0147:
            boolean r10 = r2.contains(r8)
            if (r10 != 0) goto L_0x01a6
            com.android.systemui.statusbar.StatusBarIconView r8 = (com.android.systemui.statusbar.StatusBarIconView) r8
            java.util.Objects.requireNonNull(r8)
            android.service.notification.StatusBarNotification r10 = r8.mNotification
            java.lang.String r10 = r10.getGroupKey()
            r11 = r3
            r12 = r11
        L_0x015a:
            int r13 = r2.size()
            if (r11 >= r13) goto L_0x018c
            java.lang.Object r13 = r2.get(r11)
            com.android.systemui.statusbar.StatusBarIconView r13 = (com.android.systemui.statusbar.StatusBarIconView) r13
            java.util.Objects.requireNonNull(r13)
            com.android.internal.statusbar.StatusBarIcon r14 = r13.mIcon
            android.graphics.drawable.Icon r14 = r14.icon
            com.android.internal.statusbar.StatusBarIcon r15 = r8.mIcon
            android.graphics.drawable.Icon r15 = r15.icon
            boolean r14 = r14.sameAs(r15)
            if (r14 == 0) goto L_0x0189
            android.service.notification.StatusBarNotification r13 = r13.mNotification
            java.lang.String r13 = r13.getGroupKey()
            boolean r13 = r13.equals(r10)
            if (r13 == 0) goto L_0x0189
            if (r12 != 0) goto L_0x0187
            r12 = r6
            goto L_0x0189
        L_0x0187:
            r12 = r3
            goto L_0x018c
        L_0x0189:
            int r11 = r11 + 1
            goto L_0x015a
        L_0x018c:
            if (r12 == 0) goto L_0x01a3
            java.lang.Object r9 = r4.getOrDefault(r10, r9)
            java.util.ArrayList r9 = (java.util.ArrayList) r9
            if (r9 != 0) goto L_0x019e
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            r4.put(r10, r9)
        L_0x019e:
            com.android.internal.statusbar.StatusBarIcon r10 = r8.mIcon
            r9.add(r10)
        L_0x01a3:
            r5.add(r8)
        L_0x01a6:
            int r7 = r7 + 1
            goto L_0x0137
        L_0x01a9:
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.util.Set r8 = r4.keySet()
            androidx.collection.ArrayMap$KeySet r8 = (androidx.collection.ArrayMap.KeySet) r8
            java.util.Iterator r8 = r8.iterator()
        L_0x01b8:
            r10 = r8
            androidx.collection.IndexBasedArrayIterator r10 = (androidx.collection.IndexBasedArrayIterator) r10
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x01d7
            java.lang.Object r10 = r10.next()
            java.lang.String r10 = (java.lang.String) r10
            java.lang.Object r11 = r4.getOrDefault(r10, r9)
            java.util.ArrayList r11 = (java.util.ArrayList) r11
            int r11 = r11.size()
            if (r11 == r6) goto L_0x01b8
            r7.add(r10)
            goto L_0x01b8
        L_0x01d7:
            java.util.Iterator r7 = r7.iterator()
        L_0x01db:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x01e9
            java.lang.Object r8 = r7.next()
            r4.remove(r8)
            goto L_0x01db
        L_0x01e9:
            r1.mReplacingIcons = r4
            int r4 = r5.size()
            r7 = r3
        L_0x01f0:
            if (r7 >= r4) goto L_0x01fe
            java.lang.Object r8 = r5.get(r7)
            android.view.View r8 = (android.view.View) r8
            r1.removeView(r8)
            int r7 = r7 + 1
            goto L_0x01f0
        L_0x01fe:
            android.widget.FrameLayout$LayoutParams r4 = new android.widget.FrameLayout$LayoutParams
            int r5 = r0.mIconSize
            int r7 = r0.mIconHPadding
            int r7 = r7 * 2
            int r7 = r7 + r5
            com.android.systemui.statusbar.window.StatusBarWindowController r5 = r0.mStatusBarWindowController
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mBarHeight
            r4.<init>(r7, r5)
            r5 = r3
        L_0x0212:
            int r7 = r2.size()
            if (r5 >= r7) goto L_0x0233
            java.lang.Object r7 = r2.get(r5)
            com.android.systemui.statusbar.StatusBarIconView r7 = (com.android.systemui.statusbar.StatusBarIconView) r7
            r1.removeTransientView(r7)
            android.view.ViewParent r8 = r7.getParent()
            if (r8 != 0) goto L_0x0230
            if (r21 == 0) goto L_0x022d
            com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda0 r8 = r0.mUpdateStatusBarIcons
            r7.mOnDismissListener = r8
        L_0x022d:
            r1.addView(r7, r5, r4)
        L_0x0230:
            int r5 = r5 + 1
            goto L_0x0212
        L_0x0233:
            r1.mChangingViewPositions = r6
            int r0 = r18.getChildCount()
            r4 = r3
        L_0x023a:
            if (r4 >= r0) goto L_0x0252
            android.view.View r5 = r1.getChildAt(r4)
            java.lang.Object r6 = r2.get(r4)
            com.android.systemui.statusbar.StatusBarIconView r6 = (com.android.systemui.statusbar.StatusBarIconView) r6
            if (r5 != r6) goto L_0x0249
            goto L_0x024f
        L_0x0249:
            r1.removeView(r6)
            r1.addView(r6, r4)
        L_0x024f:
            int r4 = r4 + 1
            goto L_0x023a
        L_0x0252:
            r1.mChangingViewPositions = r3
            r1.mReplacingIcons = r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationIconAreaController.updateIconsForLayout(java.util.function.Function, com.android.systemui.statusbar.phone.NotificationIconContainer, boolean, boolean, boolean, boolean, boolean, boolean):void");
    }

    public final void updateNotificationIcons(List<ListEntry> list) {
        this.mNotificationEntries = list;
        Trace.beginSection("NotificationIconAreaController.updateNotificationIcons");
        updateIconsForLayout(NotificationIconAreaController$$ExternalSyntheticLambda2.INSTANCE, this.mNotificationIcons, false, this.mShowLowPriority, true, true, false, false);
        NotificationIconContainer notificationIconContainer = this.mShelfIcons;
        if (notificationIconContainer != null) {
            updateIconsForLayout(NotificationIconAreaController$$ExternalSyntheticLambda1.INSTANCE, notificationIconContainer, true, true, false, false, false, false);
        }
        updateAodNotificationIcons();
        applyNotificationIconsTint();
        Trace.endSection();
    }

    public final void updateTintForIcon(StatusBarIconView statusBarIconView, int i) {
        boolean z;
        int i2 = 0;
        if (!Boolean.TRUE.equals(statusBarIconView.getTag(C1777R.C1779id.icon_is_pre_L)) || R$array.isGrayscale(statusBarIconView, this.mContrastColorUtil)) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            i2 = DarkIconDispatcher.getTint(this.mTintAreas, statusBarIconView, i);
        }
        statusBarIconView.setStaticDrawableColor(i2);
        statusBarIconView.setDecorColor(i);
    }

    public NotificationIconAreaController(Context context, StatusBarStateController statusBarStateController, NotificationWakeUpCoordinator notificationWakeUpCoordinator, KeyguardBypassController keyguardBypassController, NotificationMediaManager notificationMediaManager, NotificationListener notificationListener, DozeParameters dozeParameters, Optional<Bubbles> optional, DemoModeController demoModeController, DarkIconDispatcher darkIconDispatcher, StatusBarWindowController statusBarWindowController, ScreenOffAnimationController screenOffAnimationController) {
        C14661 r0 = new NotificationListener.NotificationSettingsListener() {
            public final void onStatusBarIconsBehaviorChanged(boolean z) {
                NotificationIconAreaController notificationIconAreaController = NotificationIconAreaController.this;
                boolean z2 = !z;
                notificationIconAreaController.mShowLowPriority = z2;
                notificationIconAreaController.updateIconsForLayout(NotificationIconAreaController$$ExternalSyntheticLambda2.INSTANCE, notificationIconAreaController.mNotificationIcons, false, z2, true, true, false, false);
            }
        };
        this.mSettingsListener = r0;
        this.mContrastColorUtil = ContrastColorUtil.getInstance(context);
        this.mContext = context;
        this.mStatusBarStateController = statusBarStateController;
        statusBarStateController.addCallback(this);
        this.mMediaManager = notificationMediaManager;
        this.mDozeParameters = dozeParameters;
        this.mWakeUpCoordinator = notificationWakeUpCoordinator;
        Objects.requireNonNull(notificationWakeUpCoordinator);
        notificationWakeUpCoordinator.wakeUpListeners.add(this);
        this.mBypassController = keyguardBypassController;
        this.mBubblesOptional = optional;
        demoModeController.addCallback((DemoMode) this);
        this.mStatusBarWindowController = statusBarWindowController;
        this.mScreenOffAnimationController = screenOffAnimationController;
        Objects.requireNonNull(notificationListener);
        notificationListener.mSettingsListeners.add(r0);
        reloadDimens(context);
        View inflate = LayoutInflater.from(context).inflate(C1777R.layout.notification_icon_area, (ViewGroup) null);
        this.mNotificationIconArea = inflate;
        this.mNotificationIcons = (NotificationIconContainer) inflate.findViewById(C1777R.C1779id.notificationIcons);
        this.mAodIconTint = Utils.getColorAttrDefaultColor(this.mContext, C1777R.attr.wallpaperTextColor);
        darkIconDispatcher.addDarkReceiver((DarkIconDispatcher.DarkReceiver) this);
    }

    public final void reloadDimens(Context context) {
        Resources resources = context.getResources();
        this.mIconSize = resources.getDimensionPixelSize(17105554);
        this.mIconHPadding = resources.getDimensionPixelSize(C1777R.dimen.status_bar_icon_padding);
        this.mAodIconAppearTranslation = resources.getDimensionPixelSize(C1777R.dimen.shelf_appear_translation);
    }

    public final void updateIconLayoutParams(Context context) {
        reloadDimens(context);
        int i = (this.mIconHPadding * 2) + this.mIconSize;
        StatusBarWindowController statusBarWindowController = this.mStatusBarWindowController;
        Objects.requireNonNull(statusBarWindowController);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, statusBarWindowController.mBarHeight);
        for (int i2 = 0; i2 < this.mNotificationIcons.getChildCount(); i2++) {
            this.mNotificationIcons.getChildAt(i2).setLayoutParams(layoutParams);
        }
        if (this.mShelfIcons != null) {
            for (int i3 = 0; i3 < this.mShelfIcons.getChildCount(); i3++) {
                this.mShelfIcons.getChildAt(i3).setLayoutParams(layoutParams);
            }
        }
        if (this.mAodIcons != null) {
            for (int i4 = 0; i4 < this.mAodIcons.getChildCount(); i4++) {
                this.mAodIcons.getChildAt(i4).setLayoutParams(layoutParams);
            }
        }
    }

    public boolean shouldShouldLowPriorityIcons() {
        return this.mShowLowPriority;
    }
}
