package com.android.systemui.scrim;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScrimView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ScrimView$$ExternalSyntheticLambda1(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:114:0x026b, code lost:
        if (r4 != false) goto L_0x026d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x02b2, code lost:
        if (r0 != false) goto L_0x02b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x022c, code lost:
        if (r6 != false) goto L_0x022e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0235  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x027a  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x029a  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x02c1  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02c4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r19 = this;
            r0 = r19
            int r1 = r0.$r8$classId
            r2 = 0
            r3 = 1
            switch(r1) {
                case 0: goto L_0x031f;
                case 1: goto L_0x02e8;
                case 2: goto L_0x011f;
                case 3: goto L_0x0101;
                case 4: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x0355
        L_0x000b:
            java.lang.Object r1 = r0.f$0
            com.android.wm.shell.bubbles.BubbleController$BubblesImpl r1 = (com.android.p012wm.shell.bubbles.BubbleController.BubblesImpl) r1
            java.lang.Object r0 = r0.f$1
            android.content.res.Configuration r0 = (android.content.res.Configuration) r0
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.bubbles.BubbleController r1 = com.android.p012wm.shell.bubbles.BubbleController.this
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.bubbles.BubblePositioner r2 = r1.mBubblePositioner
            if (r2 == 0) goto L_0x0022
            r2.update()
        L_0x0022:
            com.android.wm.shell.bubbles.BubbleStackView r2 = r1.mStackView
            if (r2 == 0) goto L_0x0100
            if (r0 == 0) goto L_0x0100
            int r2 = r0.densityDpi
            int r4 = r1.mDensityDpi
            if (r2 != r4) goto L_0x003c
            android.app.WindowConfiguration r2 = r0.windowConfiguration
            android.graphics.Rect r2 = r2.getBounds()
            android.graphics.Rect r4 = r1.mScreenBounds
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x007d
        L_0x003c:
            int r2 = r0.densityDpi
            r1.mDensityDpi = r2
            android.graphics.Rect r2 = r1.mScreenBounds
            android.app.WindowConfiguration r4 = r0.windowConfiguration
            android.graphics.Rect r4 = r4.getBounds()
            r2.set(r4)
            com.android.wm.shell.bubbles.BubbleData r2 = r1.mBubbleData
            java.util.Objects.requireNonNull(r2)
            com.android.wm.shell.bubbles.BubblePositioner r4 = r2.mPositioner
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.mMaxBubbles
            r2.mMaxBubbles = r4
            boolean r4 = r2.mExpanded
            if (r4 != 0) goto L_0x0064
            r2.trim()
            r2.dispatchPendingChanges()
            goto L_0x0066
        L_0x0064:
            r2.mNeedsTrimming = r3
        L_0x0066:
            com.android.wm.shell.bubbles.BubbleIconFactory r2 = new com.android.wm.shell.bubbles.BubbleIconFactory
            android.content.Context r3 = r1.mContext
            r2.<init>(r3)
            r1.mBubbleIconFactory = r2
            com.android.wm.shell.bubbles.BubbleBadgeIconFactory r2 = new com.android.wm.shell.bubbles.BubbleBadgeIconFactory
            android.content.Context r3 = r1.mContext
            r2.<init>(r3)
            r1.mBubbleBadgeIconFactory = r2
            com.android.wm.shell.bubbles.BubbleStackView r2 = r1.mStackView
            r2.onDisplaySizeChanged()
        L_0x007d:
            float r2 = r0.fontScale
            float r3 = r1.mFontScale
            int r3 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r3 == 0) goto L_0x00c0
            r1.mFontScale = r2
            com.android.wm.shell.bubbles.BubbleStackView r2 = r1.mStackView
            java.util.Objects.requireNonNull(r2)
            r2.setUpManageMenu()
            com.android.wm.shell.bubbles.BubbleFlyoutView r3 = r2.mFlyout
            r3.updateFontSize()
            com.android.wm.shell.bubbles.BubbleData r3 = r2.mBubbleData
            java.util.List r3 = r3.getBubbles()
            java.util.Iterator r3 = r3.iterator()
        L_0x009e:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x00b5
            java.lang.Object r4 = r3.next()
            com.android.wm.shell.bubbles.Bubble r4 = (com.android.p012wm.shell.bubbles.Bubble) r4
            java.util.Objects.requireNonNull(r4)
            com.android.wm.shell.bubbles.BubbleExpandedView r4 = r4.mExpandedView
            if (r4 == 0) goto L_0x009e
            r4.updateFontSize()
            goto L_0x009e
        L_0x00b5:
            com.android.wm.shell.bubbles.BubbleOverflow r2 = r2.mBubbleOverflow
            if (r2 == 0) goto L_0x00c0
            com.android.wm.shell.bubbles.BubbleExpandedView r2 = r2.expandedView
            if (r2 == 0) goto L_0x00c0
            r2.updateFontSize()
        L_0x00c0:
            int r2 = r0.getLayoutDirection()
            int r3 = r1.mLayoutDirection
            if (r2 == r3) goto L_0x0100
            int r0 = r0.getLayoutDirection()
            r1.mLayoutDirection = r0
            com.android.wm.shell.bubbles.BubbleStackView r1 = r1.mStackView
            java.util.Objects.requireNonNull(r1)
            android.view.ViewGroup r2 = r1.mManageMenu
            r2.setLayoutDirection(r0)
            com.android.wm.shell.bubbles.BubbleFlyoutView r2 = r1.mFlyout
            r2.setLayoutDirection(r0)
            com.android.wm.shell.bubbles.StackEducationView r2 = r1.mStackEduView
            if (r2 == 0) goto L_0x00e4
            r2.setLayoutDirection(r0)
        L_0x00e4:
            com.android.wm.shell.bubbles.ManageEducationView r2 = r1.mManageEduView
            if (r2 == 0) goto L_0x00eb
            r2.setLayoutDirection(r0)
        L_0x00eb:
            com.android.wm.shell.bubbles.BubbleData r1 = r1.mBubbleData
            java.util.List r1 = r1.getBubbles()
            boolean r2 = r1.isEmpty()
            if (r2 == 0) goto L_0x00f8
            goto L_0x0100
        L_0x00f8:
            com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda26 r2 = new com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda26
            r2.<init>(r0)
            r1.forEach(r2)
        L_0x0100:
            return
        L_0x0101:
            java.lang.Object r1 = r0.f$0
            com.android.systemui.util.condition.Monitor r1 = (com.android.systemui.util.condition.Monitor) r1
            java.lang.Object r0 = r0.f$1
            com.android.systemui.util.condition.Condition r0 = (com.android.systemui.util.condition.Condition) r0
            java.util.Objects.requireNonNull(r1)
            java.util.HashSet r2 = r1.mConditions
            r2.remove(r0)
            boolean r2 = r1.mHaveConditionsStarted
            if (r2 != 0) goto L_0x0116
            goto L_0x011e
        L_0x0116:
            com.android.systemui.util.condition.Monitor$1 r2 = r1.mConditionCallback
            r0.removeCallback((com.android.systemui.util.condition.Condition.Callback) r2)
            r1.updateConditionMetState()
        L_0x011e:
            return
        L_0x011f:
            java.lang.Object r1 = r0.f$0
            com.android.systemui.statusbar.policy.UserSwitcherController r1 = (com.android.systemui.statusbar.policy.UserSwitcherController) r1
            java.lang.Object r0 = r0.f$1
            android.util.SparseArray r0 = (android.util.SparseArray) r0
            java.util.Objects.requireNonNull(r1)
            android.os.UserManager r4 = r1.mUserManager
            java.util.List r4 = r4.getAliveUsers()
            if (r4 != 0) goto L_0x0134
            goto L_0x02e7
        L_0x0134:
            java.util.ArrayList r5 = new java.util.ArrayList
            int r6 = r4.size()
            r5.<init>(r6)
            com.android.systemui.settings.UserTracker r6 = r1.mUserTracker
            int r6 = r6.getUserId()
            android.os.UserManager r7 = r1.mUserManager
            com.android.systemui.settings.UserTracker r8 = r1.mUserTracker
            int r8 = r8.getUserId()
            android.os.UserHandle r8 = android.os.UserHandle.of(r8)
            int r7 = r7.getUserSwitchability(r8)
            if (r7 != 0) goto L_0x0157
            r7 = r3
            goto L_0x0158
        L_0x0157:
            r7 = r2
        L_0x0158:
            r8 = 0
            java.util.Iterator r4 = r4.iterator()
        L_0x015d:
            boolean r9 = r4.hasNext()
            if (r9 == 0) goto L_0x01d6
            java.lang.Object r9 = r4.next()
            r11 = r9
            android.content.pm.UserInfo r11 = (android.content.pm.UserInfo) r11
            int r9 = r11.id
            if (r6 != r9) goto L_0x0170
            r14 = r3
            goto L_0x0171
        L_0x0170:
            r14 = r2
        L_0x0171:
            if (r7 != 0) goto L_0x0179
            if (r14 == 0) goto L_0x0176
            goto L_0x0179
        L_0x0176:
            r17 = r2
            goto L_0x017b
        L_0x0179:
            r17 = r3
        L_0x017b:
            boolean r9 = r11.isEnabled()
            if (r9 == 0) goto L_0x015d
            boolean r9 = r11.isGuest()
            if (r9 == 0) goto L_0x019a
            com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord r17 = new com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord
            r10 = 0
            r12 = 1
            r13 = 0
            r15 = 0
            r16 = 0
            r8 = r17
            r9 = r11
            r11 = r12
            r12 = r14
            r14 = r15
            r15 = r7
            r8.<init>(r9, r10, r11, r12, r13, r14, r15, r16)
            goto L_0x015d
        L_0x019a:
            boolean r9 = r11.supportsSwitchToByUser()
            if (r9 == 0) goto L_0x015d
            int r9 = r11.id
            java.lang.Object r9 = r0.get(r9)
            android.graphics.Bitmap r9 = (android.graphics.Bitmap) r9
            if (r9 != 0) goto L_0x01c5
            android.os.UserManager r9 = r1.mUserManager
            int r10 = r11.id
            android.graphics.Bitmap r9 = r9.getUserIcon(r10)
            if (r9 == 0) goto L_0x01c5
            android.content.Context r10 = r1.mContext
            android.content.res.Resources r10 = r10.getResources()
            r12 = 2131166334(0x7f07047e, float:1.794691E38)
            int r10 = r10.getDimensionPixelSize(r12)
            android.graphics.Bitmap r9 = android.graphics.Bitmap.createScaledBitmap(r9, r10, r10, r3)
        L_0x01c5:
            r12 = r9
            com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord r9 = new com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord
            r13 = 0
            r15 = 0
            r16 = 0
            r18 = 0
            r10 = r9
            r10.<init>(r11, r12, r13, r14, r15, r16, r17, r18)
            r5.add(r9)
            goto L_0x015d
        L_0x01d6:
            int r0 = r5.size()
            if (r0 > r3) goto L_0x01de
            if (r8 == 0) goto L_0x01e5
        L_0x01de:
            android.content.Context r0 = r1.mContext
            java.lang.String r4 = "HasSeenMultiUser"
            com.android.systemui.Prefs.putBoolean(r0, r4, r3)
        L_0x01e5:
            java.lang.String r0 = "no_add_user"
            if (r8 != 0) goto L_0x024e
            boolean r4 = r1.mGuestUserAutoCreated
            if (r4 == 0) goto L_0x020f
            java.util.concurrent.atomic.AtomicBoolean r4 = r1.mGuestIsResetting
            boolean r4 = r4.get()
            if (r4 != 0) goto L_0x01f9
            if (r7 == 0) goto L_0x01f9
            r15 = r3
            goto L_0x01fa
        L_0x01f9:
            r15 = r2
        L_0x01fa:
            com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord r4 = new com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord
            r9 = 0
            r10 = 0
            r11 = 1
            r12 = 0
            r13 = 0
            r14 = 0
            r16 = 0
            r8 = r4
            r8.<init>(r9, r10, r11, r12, r13, r14, r15, r16)
            r1.checkIfAddUserDisallowedByAdminOnly(r4)
            r5.add(r4)
            goto L_0x0251
        L_0x020f:
            if (r8 == 0) goto L_0x0213
            r4 = r3
            goto L_0x0214
        L_0x0213:
            r4 = r2
        L_0x0214:
            boolean r6 = r1.currentUserCanCreateUsers()
            if (r6 != 0) goto L_0x022e
            android.os.UserManager r6 = r1.mUserManager
            android.os.UserHandle r8 = android.os.UserHandle.SYSTEM
            boolean r6 = r6.hasBaseUserRestriction(r0, r8)
            r6 = r6 ^ r3
            if (r6 == 0) goto L_0x022b
            boolean r6 = r1.mAddUsersFromLockScreen
            if (r6 == 0) goto L_0x022b
            r6 = r3
            goto L_0x022c
        L_0x022b:
            r6 = r2
        L_0x022c:
            if (r6 == 0) goto L_0x0232
        L_0x022e:
            if (r4 != 0) goto L_0x0232
            r4 = r3
            goto L_0x0233
        L_0x0232:
            r4 = r2
        L_0x0233:
            if (r4 == 0) goto L_0x0251
            com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord r4 = new com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord
            r9 = 0
            r10 = 0
            r11 = 1
            r12 = 0
            r13 = 0
            boolean r6 = r1.mAddUsersFromLockScreen
            r14 = r6 ^ 1
            r16 = 0
            r8 = r4
            r15 = r7
            r8.<init>(r9, r10, r11, r12, r13, r14, r15, r16)
            r1.checkIfAddUserDisallowedByAdminOnly(r4)
            r5.add(r4)
            goto L_0x0251
        L_0x024e:
            r5.add(r8)
        L_0x0251:
            boolean r4 = r1.currentUserCanCreateUsers()
            java.lang.String r6 = "android.os.usertype.full.SECONDARY"
            if (r4 != 0) goto L_0x026d
            android.os.UserManager r4 = r1.mUserManager
            android.os.UserHandle r8 = android.os.UserHandle.SYSTEM
            boolean r4 = r4.hasBaseUserRestriction(r0, r8)
            r4 = r4 ^ r3
            if (r4 == 0) goto L_0x026a
            boolean r4 = r1.mAddUsersFromLockScreen
            if (r4 == 0) goto L_0x026a
            r4 = r3
            goto L_0x026b
        L_0x026a:
            r4 = r2
        L_0x026b:
            if (r4 == 0) goto L_0x0277
        L_0x026d:
            android.os.UserManager r4 = r1.mUserManager
            boolean r4 = r4.canAddMoreUsers(r6)
            if (r4 == 0) goto L_0x0277
            r4 = r3
            goto L_0x0278
        L_0x0277:
            r4 = r2
        L_0x0278:
            if (r4 == 0) goto L_0x0292
            com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord r4 = new com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 1
            boolean r8 = r1.mAddUsersFromLockScreen
            r14 = r8 ^ 1
            r16 = 0
            r8 = r4
            r15 = r7
            r8.<init>(r9, r10, r11, r12, r13, r14, r15, r16)
            r1.checkIfAddUserDisallowedByAdminOnly(r4)
            r5.add(r4)
        L_0x0292:
            java.lang.String r4 = r1.mCreateSupervisedUserPackage
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x02c2
            boolean r4 = r1.currentUserCanCreateUsers()
            if (r4 != 0) goto L_0x02b4
            android.os.UserManager r4 = r1.mUserManager
            android.os.UserHandle r8 = android.os.UserHandle.SYSTEM
            boolean r0 = r4.hasBaseUserRestriction(r0, r8)
            r0 = r0 ^ r3
            if (r0 == 0) goto L_0x02b1
            boolean r0 = r1.mAddUsersFromLockScreen
            if (r0 == 0) goto L_0x02b1
            r0 = r3
            goto L_0x02b2
        L_0x02b1:
            r0 = r2
        L_0x02b2:
            if (r0 == 0) goto L_0x02be
        L_0x02b4:
            android.os.UserManager r0 = r1.mUserManager
            boolean r0 = r0.canAddMoreUsers(r6)
            if (r0 == 0) goto L_0x02be
            r0 = r3
            goto L_0x02bf
        L_0x02be:
            r0 = r2
        L_0x02bf:
            if (r0 == 0) goto L_0x02c2
            r2 = r3
        L_0x02c2:
            if (r2 == 0) goto L_0x02dc
            com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord r0 = new com.android.systemui.statusbar.policy.UserSwitcherController$UserRecord
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            boolean r2 = r1.mAddUsersFromLockScreen
            r14 = r2 ^ 1
            r16 = 1
            r8 = r0
            r15 = r7
            r8.<init>(r9, r10, r11, r12, r13, r14, r15, r16)
            r1.checkIfAddUserDisallowedByAdminOnly(r0)
            r5.add(r0)
        L_0x02dc:
            java.util.concurrent.Executor r0 = r1.mUiExecutor
            com.android.systemui.util.condition.Monitor$$ExternalSyntheticLambda0 r2 = new com.android.systemui.util.condition.Monitor$$ExternalSyntheticLambda0
            r3 = 2
            r2.<init>(r1, r5, r3)
            r0.execute(r2)
        L_0x02e7:
            return
        L_0x02e8:
            java.lang.Object r1 = r0.f$0
            com.android.systemui.communal.CommunalSourceMonitor r1 = (com.android.systemui.communal.CommunalSourceMonitor) r1
            java.lang.Object r0 = r0.f$1
            com.android.systemui.communal.CommunalSourceMonitor$Callback r0 = (com.android.systemui.communal.CommunalSourceMonitor.Callback) r0
            boolean r4 = com.android.systemui.communal.CommunalSourceMonitor.DEBUG
            java.util.Objects.requireNonNull(r1)
            java.util.ArrayList<java.lang.ref.WeakReference<com.android.systemui.communal.CommunalSourceMonitor$Callback>> r4 = r1.mCallbacks
            com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda4 r5 = new com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda4
            r5.<init>(r0, r3)
            r4.removeIf(r5)
            java.util.ArrayList<java.lang.ref.WeakReference<com.android.systemui.communal.CommunalSourceMonitor$Callback>> r0 = r1.mCallbacks
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x031e
            boolean r0 = r1.mListeningForConditions
            if (r0 == 0) goto L_0x031e
            com.android.systemui.util.condition.Monitor r0 = r1.mConditionsMonitor
            com.android.systemui.communal.CommunalSourceMonitor$$ExternalSyntheticLambda0 r3 = r1.mConditionsCallback
            java.util.Objects.requireNonNull(r0)
            java.util.concurrent.Executor r4 = r0.mExecutor
            com.android.systemui.util.condition.Monitor$$ExternalSyntheticLambda1 r5 = new com.android.systemui.util.condition.Monitor$$ExternalSyntheticLambda1
            r5.<init>(r0, r3, r2)
            r4.execute(r5)
            r1.mListeningForConditions = r2
        L_0x031e:
            return
        L_0x031f:
            java.lang.Object r1 = r0.f$0
            com.android.systemui.scrim.ScrimView r1 = (com.android.systemui.scrim.ScrimView) r1
            java.lang.Object r0 = r0.f$1
            android.graphics.drawable.Drawable r0 = (android.graphics.drawable.Drawable) r0
            int r2 = com.android.systemui.scrim.ScrimView.$r8$clinit
            java.util.Objects.requireNonNull(r1)
            r1.mDrawable = r0
            r0.setCallback(r1)
            android.graphics.drawable.Drawable r0 = r1.mDrawable
            int r2 = r1.getLeft()
            int r3 = r1.getTop()
            int r4 = r1.getRight()
            int r5 = r1.getBottom()
            r0.setBounds(r2, r3, r4, r5)
            android.graphics.drawable.Drawable r0 = r1.mDrawable
            float r2 = r1.mViewAlpha
            r3 = 1132396544(0x437f0000, float:255.0)
            float r2 = r2 * r3
            int r2 = (int) r2
            r0.setAlpha(r2)
            r1.invalidate()
            return
        L_0x0355:
            java.lang.Object r1 = r0.f$0
            com.android.wm.shell.pip.phone.PipController$PipImpl r1 = (com.android.p012wm.shell.pip.phone.PipController.PipImpl) r1
            java.lang.Object r0 = r0.f$1
            java.util.function.Consumer r0 = (java.util.function.Consumer) r0
            java.util.Objects.requireNonNull(r1)
            com.android.wm.shell.pip.phone.PipController r1 = com.android.p012wm.shell.pip.phone.PipController.this
            com.android.wm.shell.pip.PipBoundsState r1 = r1.mPipBoundsState
            java.util.Objects.requireNonNull(r1)
            java.util.ArrayList r1 = r1.mOnPipExclusionBoundsChangeCallbacks
            r1.remove(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda1.run():void");
    }
}
