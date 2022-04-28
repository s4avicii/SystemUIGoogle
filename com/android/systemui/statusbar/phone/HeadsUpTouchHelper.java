package com.android.systemui.statusbar.phone;

import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import java.util.Objects;

public final class HeadsUpTouchHelper implements Gefingerpoken {
    public Callback mCallback;
    public boolean mCollapseSnoozes;
    public HeadsUpManagerPhone mHeadsUpManager;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public NotificationPanelViewController mPanel;
    public ExpandableNotificationRow mPickedChild;
    public float mTouchSlop;
    public boolean mTouchingHeadsUpView;
    public boolean mTrackingHeadsUp;
    public int mTrackingPointer;

    public interface Callback {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0148, code lost:
        if (r11.mIsPinned != false) goto L_0x014c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0108  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r11) {
        /*
            r10 = this;
            boolean r0 = r10.mTouchingHeadsUpView
            r1 = 0
            if (r0 != 0) goto L_0x000c
            int r0 = r11.getActionMasked()
            if (r0 == 0) goto L_0x000c
            return r1
        L_0x000c:
            int r0 = r10.mTrackingPointer
            int r0 = r11.findPointerIndex(r0)
            if (r0 >= 0) goto L_0x001b
            int r0 = r11.getPointerId(r1)
            r10.mTrackingPointer = r0
            r0 = r1
        L_0x001b:
            float r2 = r11.getX(r0)
            float r0 = r11.getY(r0)
            int r3 = r11.getActionMasked()
            r4 = 1
            if (r3 == 0) goto L_0x0116
            r5 = 0
            r6 = -1
            if (r3 == r4) goto L_0x00d6
            r7 = 2
            if (r3 == r7) goto L_0x0060
            r0 = 3
            if (r3 == r0) goto L_0x00d6
            r0 = 6
            if (r3 == r0) goto L_0x0039
            goto L_0x0176
        L_0x0039:
            int r0 = r11.getActionIndex()
            int r0 = r11.getPointerId(r0)
            int r2 = r10.mTrackingPointer
            if (r2 != r0) goto L_0x0176
            int r2 = r11.getPointerId(r1)
            if (r2 == r0) goto L_0x004c
            r4 = r1
        L_0x004c:
            int r0 = r11.getPointerId(r4)
            r10.mTrackingPointer = r0
            float r0 = r11.getX(r4)
            r10.mInitialTouchX = r0
            float r11 = r11.getY(r4)
            r10.mInitialTouchY = r11
            goto L_0x0176
        L_0x0060:
            float r11 = r10.mInitialTouchY
            float r11 = r0 - r11
            boolean r3 = r10.mTouchingHeadsUpView
            if (r3 == 0) goto L_0x0176
            float r3 = java.lang.Math.abs(r11)
            float r7 = r10.mTouchSlop
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 <= 0) goto L_0x0176
            float r3 = java.lang.Math.abs(r11)
            float r7 = r10.mInitialTouchX
            float r7 = r2 - r7
            float r7 = java.lang.Math.abs(r7)
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 <= 0) goto L_0x0176
            r10.setTrackingHeadsUp(r4)
            r3 = 0
            int r11 = (r11 > r3 ? 1 : (r11 == r3 ? 0 : -1))
            if (r11 >= 0) goto L_0x008c
            r11 = r4
            goto L_0x008d
        L_0x008c:
            r11 = r1
        L_0x008d:
            r10.mCollapseSnoozes = r11
            r10.mInitialTouchX = r2
            r10.mInitialTouchY = r0
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r11 = r10.mPickedChild
            java.util.Objects.requireNonNull(r11)
            int r11 = r11.mActualHeight
            float r11 = (float) r11
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = r10.mPickedChild
            float r7 = r7.getTranslationY()
            float r7 = r7 + r11
            int r11 = (int) r7
            com.android.systemui.statusbar.phone.NotificationPanelViewController r7 = r10.mPanel
            int r7 = r7.getMaxPanelHeight()
            float r7 = (float) r7
            com.android.systemui.statusbar.phone.NotificationPanelViewController r8 = r10.mPanel
            int r9 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r9 <= 0) goto L_0x00b2
            float r3 = (float) r11
            float r3 = r3 / r7
        L_0x00b2:
            r8.setPanelScrimMinFraction(r3)
            com.android.systemui.statusbar.phone.NotificationPanelViewController r3 = r10.mPanel
            float r11 = (float) r11
            r3.startExpandMotion(r2, r0, r4, r11)
            com.android.systemui.statusbar.phone.HeadsUpManagerPhone r11 = r10.mHeadsUpManager
            r11.unpinAll()
            com.android.systemui.statusbar.phone.NotificationPanelViewController r11 = r10.mPanel
            java.util.Objects.requireNonNull(r11)
            com.android.systemui.statusbar.phone.StatusBar r11 = r11.mStatusBar
            java.util.Objects.requireNonNull(r11)
            com.android.internal.statusbar.IStatusBarService r11 = r11.mBarService     // Catch:{ RemoteException -> 0x00cf }
            r11.clearNotificationEffects()     // Catch:{ RemoteException -> 0x00cf }
        L_0x00cf:
            r10.mTrackingPointer = r6
            r10.mPickedChild = r5
            r10.mTouchingHeadsUpView = r1
            return r4
        L_0x00d6:
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r11 = r10.mPickedChild
            if (r11 == 0) goto L_0x010f
            boolean r0 = r10.mTouchingHeadsUpView
            if (r0 == 0) goto L_0x010f
            com.android.systemui.statusbar.phone.HeadsUpManagerPhone r0 = r10.mHeadsUpManager
            com.android.systemui.statusbar.notification.collection.NotificationEntry r11 = r11.mEntry
            java.util.Objects.requireNonNull(r11)
            android.service.notification.StatusBarNotification r11 = r11.mSbn
            java.lang.String r11 = r11.getKey()
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.policy.HeadsUpManager$HeadsUpEntry r11 = r0.getHeadsUpEntry(r11)
            if (r11 == 0) goto L_0x0105
            com.android.systemui.statusbar.AlertingNotificationManager$Clock r0 = r0.mClock
            java.util.Objects.requireNonNull(r0)
            long r2 = android.os.SystemClock.elapsedRealtime()
            long r7 = r11.mPostTime
            int r11 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r11 >= 0) goto L_0x0105
            r11 = r4
            goto L_0x0106
        L_0x0105:
            r11 = r1
        L_0x0106:
            if (r11 == 0) goto L_0x010f
            r10.mTrackingPointer = r6
            r10.mPickedChild = r5
            r10.mTouchingHeadsUpView = r1
            return r4
        L_0x010f:
            r10.mTrackingPointer = r6
            r10.mPickedChild = r5
            r10.mTouchingHeadsUpView = r1
            goto L_0x0176
        L_0x0116:
            r10.mInitialTouchY = r0
            r10.mInitialTouchX = r2
            r10.setTrackingHeadsUp(r1)
            com.android.systemui.statusbar.phone.HeadsUpTouchHelper$Callback r11 = r10.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$9 r11 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.C13659) r11
            java.util.Objects.requireNonNull(r11)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r11 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
            com.android.systemui.statusbar.notification.row.ExpandableView r11 = r11.getChildAtRawPosition(r2, r0)
            r10.mTouchingHeadsUpView = r1
            boolean r0 = r11 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r0 == 0) goto L_0x0153
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r11 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r11
            com.android.systemui.statusbar.phone.HeadsUpTouchHelper$Callback r0 = r10.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$9 r0 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.C13659) r0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
            boolean r0 = r0.mIsExpanded
            if (r0 != 0) goto L_0x014b
            java.util.Objects.requireNonNull(r11)
            boolean r0 = r11.mIsHeadsUp
            if (r0 == 0) goto L_0x014b
            boolean r0 = r11.mIsPinned
            if (r0 == 0) goto L_0x014b
            goto L_0x014c
        L_0x014b:
            r4 = r1
        L_0x014c:
            r10.mTouchingHeadsUpView = r4
            if (r4 == 0) goto L_0x0176
            r10.mPickedChild = r11
            goto L_0x0176
        L_0x0153:
            if (r11 != 0) goto L_0x0176
            com.android.systemui.statusbar.phone.HeadsUpTouchHelper$Callback r11 = r10.mCallback
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$9 r11 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.C13659) r11
            java.util.Objects.requireNonNull(r11)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r11 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this
            boolean r11 = r11.mIsExpanded
            if (r11 != 0) goto L_0x0176
            com.android.systemui.statusbar.phone.HeadsUpManagerPhone r11 = r10.mHeadsUpManager
            com.android.systemui.statusbar.notification.collection.NotificationEntry r11 = r11.getTopEntry()
            if (r11 == 0) goto L_0x0176
            boolean r0 = r11.isRowPinned()
            if (r0 == 0) goto L_0x0176
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r11 = r11.row
            r10.mPickedChild = r11
            r10.mTouchingHeadsUpView = r4
        L_0x0176:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.HeadsUpTouchHelper.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mTrackingHeadsUp) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            this.mTrackingPointer = -1;
            this.mPickedChild = null;
            this.mTouchingHeadsUpView = false;
            setTrackingHeadsUp(false);
        }
        return true;
    }

    public final void setTrackingHeadsUp(boolean z) {
        ExpandableNotificationRow expandableNotificationRow;
        this.mTrackingHeadsUp = z;
        HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
        Objects.requireNonNull(headsUpManagerPhone);
        headsUpManagerPhone.mTrackingHeadsUp = z;
        NotificationPanelViewController notificationPanelViewController = this.mPanel;
        if (z) {
            expandableNotificationRow = this.mPickedChild;
        } else {
            expandableNotificationRow = null;
        }
        if (expandableNotificationRow != null) {
            Objects.requireNonNull(notificationPanelViewController);
            notificationPanelViewController.mTrackedHeadsUpNotification = expandableNotificationRow;
            for (int i = 0; i < notificationPanelViewController.mTrackingHeadsUpListeners.size(); i++) {
                notificationPanelViewController.mTrackingHeadsUpListeners.get(i).accept(expandableNotificationRow);
            }
            notificationPanelViewController.mExpandingFromHeadsUp = true;
            return;
        }
        Objects.requireNonNull(notificationPanelViewController);
    }

    public HeadsUpTouchHelper(HeadsUpManagerPhone headsUpManagerPhone, Callback callback, NotificationPanelViewController notificationPanelViewController) {
        this.mHeadsUpManager = headsUpManagerPhone;
        this.mCallback = callback;
        this.mPanel = notificationPanelViewController;
        NotificationStackScrollLayout.C13659 r2 = (NotificationStackScrollLayout.C13659) callback;
        Objects.requireNonNull(r2);
        this.mTouchSlop = (float) ViewConfiguration.get(NotificationStackScrollLayout.this.mContext).getScaledTouchSlop();
    }
}
