package com.android.keyguard;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CarrierTextManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CarrierTextManager$$ExternalSyntheticLambda1(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:57:0x014d  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x019c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r9 = this;
            int r0 = r9.$r8$classId
            r1 = 1
            r2 = 0
            switch(r0) {
                case 0: goto L_0x00bb;
                case 1: goto L_0x007a;
                case 2: goto L_0x0044;
                case 3: goto L_0x001c;
                case 4: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x00c7
        L_0x0009:
            java.lang.Object r0 = r9.f$0
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$8 r0 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.C13788) r0
            java.lang.Object r9 = r9.f$1
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r9 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r9
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.this
            com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager r0 = r0.mNotificationRoundnessManager
            r0.updateView(r9, r1)
            return
        L_0x001c:
            java.lang.Object r0 = r9.f$0
            com.android.systemui.statusbar.RemoteInputController r0 = (com.android.systemui.statusbar.RemoteInputController) r0
            java.lang.Object r9 = r9.f$1
            android.util.IndentingPrintWriter r9 = (android.util.IndentingPrintWriter) r9
            boolean r1 = com.android.systemui.statusbar.RemoteInputController.ENABLE_REMOTE_INPUT
            java.util.Objects.requireNonNull(r0)
            android.util.ArrayMap<java.lang.String, java.lang.Object> r0 = r0.mSpinning
            java.util.Set r0 = r0.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0033:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0043
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            r9.println(r1)
            goto L_0x0033
        L_0x0043:
            return
        L_0x0044:
            java.lang.Object r0 = r9.f$0
            com.android.systemui.communal.CommunalHostViewController r0 = (com.android.systemui.communal.CommunalHostViewController) r0
            java.lang.Object r9 = r9.f$1
            com.google.common.util.concurrent.ListenableFuture r9 = (com.google.common.util.concurrent.ListenableFuture) r9
            boolean r1 = com.android.systemui.communal.CommunalHostViewController.DEBUG
            java.util.Objects.requireNonNull(r0)
            java.lang.Object r9 = r9.get()     // Catch:{ Exception -> 0x0062 }
            com.android.systemui.communal.CommunalSource$CommunalViewResult r9 = (com.android.systemui.communal.CommunalSource.CommunalViewResult) r9     // Catch:{ Exception -> 0x0062 }
            java.util.Objects.requireNonNull(r9)     // Catch:{ Exception -> 0x0062 }
            android.view.ViewGroup$LayoutParams r9 = new android.view.ViewGroup$LayoutParams     // Catch:{ Exception -> 0x0062 }
            r0 = -1
            r9.<init>(r0, r0)     // Catch:{ Exception -> 0x0062 }
            r9 = 0
            throw r9     // Catch:{ Exception -> 0x0062 }
        L_0x0062:
            r9 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "could not obtain communal view through callback:"
            r0.append(r1)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            java.lang.String r0 = "CommunalController"
            android.util.Log.e(r0, r9)
            return
        L_0x007a:
            java.lang.Object r0 = r9.f$0
            com.android.systemui.ImageWallpaper$GLEngine r0 = (com.android.systemui.ImageWallpaper.GLEngine) r0
            java.lang.Object r9 = r9.f$1
            java.util.List r9 = (java.util.List) r9
            int r3 = com.android.systemui.ImageWallpaper.GLEngine.MIN_SURFACE_WIDTH
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.ImageWallpaper r3 = com.android.systemui.ImageWallpaper.this
            android.util.ArraySet<android.graphics.RectF> r3 = r3.mColorAreas
            int r3 = r3.size()
            com.android.systemui.ImageWallpaper r4 = com.android.systemui.ImageWallpaper.this
            java.util.ArrayList<android.graphics.RectF> r4 = r4.mLocalColorsToAdd
            int r4 = r4.size()
            int r4 = r4 + r3
            if (r4 != 0) goto L_0x009d
            r0.setOffsetNotificationsEnabled(r1)
        L_0x009d:
            com.android.systemui.ImageWallpaper r1 = com.android.systemui.ImageWallpaper.this
            android.graphics.Bitmap r3 = r1.mMiniBitmap
            if (r3 != 0) goto L_0x00b7
            java.util.ArrayList<android.graphics.RectF> r1 = r1.mLocalColorsToAdd
            r1.addAll(r9)
            com.android.systemui.glwallpaper.ImageWallpaperRenderer r9 = r0.mRenderer
            if (r9 == 0) goto L_0x00ba
            com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda3 r1 = new com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda3
            r1.<init>(r0, r2)
            com.android.systemui.glwallpaper.ImageWallpaperRenderer$WallpaperTexture r9 = r9.mTexture
            r9.use(r1)
            goto L_0x00ba
        L_0x00b7:
            r0.computeAndNotifyLocalColors(r9, r3)
        L_0x00ba:
            return
        L_0x00bb:
            java.lang.Object r0 = r9.f$0
            com.android.keyguard.CarrierTextManager$CarrierTextCallback r0 = (com.android.keyguard.CarrierTextManager.CarrierTextCallback) r0
            java.lang.Object r9 = r9.f$1
            com.android.keyguard.CarrierTextManager$CarrierTextCallbackInfo r9 = (com.android.keyguard.CarrierTextManager.CarrierTextCallbackInfo) r9
            r0.updateCarrierInfo(r9)
            return
        L_0x00c7:
            java.lang.Object r0 = r9.f$0
            com.android.wm.shell.bubbles.BubbleViewInfoTask r0 = (com.android.p012wm.shell.bubbles.BubbleViewInfoTask) r0
            java.lang.Object r9 = r9.f$1
            com.android.wm.shell.bubbles.BubbleViewInfoTask$BubbleViewInfo r9 = (com.android.p012wm.shell.bubbles.BubbleViewInfoTask.BubbleViewInfo) r9
            int r3 = com.android.p012wm.shell.bubbles.BubbleViewInfoTask.$r8$clinit
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.bubbles.Bubble r3 = r0.mBubble
            java.util.Objects.requireNonNull(r3)
            com.android.wm.shell.bubbles.BadgedImageView r4 = r3.mIconView
            if (r4 == 0) goto L_0x00e3
            com.android.wm.shell.bubbles.BubbleExpandedView r4 = r3.mExpandedView
            if (r4 == 0) goto L_0x00e3
            r4 = r1
            goto L_0x00e4
        L_0x00e3:
            r4 = r2
        L_0x00e4:
            if (r4 != 0) goto L_0x00ee
            com.android.wm.shell.bubbles.BadgedImageView r4 = r9.imageView
            r3.mIconView = r4
            com.android.wm.shell.bubbles.BubbleExpandedView r4 = r9.expandedView
            r3.mExpandedView = r4
        L_0x00ee:
            android.content.pm.ShortcutInfo r4 = r9.shortcutInfo
            r3.mShortcutInfo = r4
            java.lang.String r4 = r9.appName
            r3.mAppName = r4
            com.android.wm.shell.bubbles.Bubble$FlyoutMessage r4 = r9.flyoutMessage
            r3.mFlyoutMessage = r4
            android.graphics.Bitmap r4 = r9.badgeBitmap
            r3.mBadgeBitmap = r4
            android.graphics.Bitmap r4 = r9.mRawBadgeBitmap
            r3.mRawBadgeBitmap = r4
            android.graphics.Bitmap r4 = r9.bubbleBitmap
            r3.mBubbleBitmap = r4
            int r4 = r9.dotColor
            r3.mDotColor = r4
            android.graphics.Path r9 = r9.dotPath
            r3.mDotPath = r9
            com.android.wm.shell.bubbles.BubbleExpandedView r9 = r3.mExpandedView
            if (r9 == 0) goto L_0x01be
            com.android.wm.shell.bubbles.BubbleStackView r4 = r9.mStackView
            java.lang.String r5 = "Bubbles"
            if (r4 != 0) goto L_0x012e
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r1 = "Stack is null for bubble: "
            r9.append(r1)
            r9.append(r3)
            java.lang.String r9 = r9.toString()
            android.util.Log.w(r5, r9)
            goto L_0x01be
        L_0x012e:
            com.android.wm.shell.bubbles.Bubble r4 = r9.mBubble
            if (r4 == 0) goto L_0x014a
            android.app.PendingIntent r6 = r9.mPendingIntent
            if (r6 == 0) goto L_0x0138
            r6 = r1
            goto L_0x0139
        L_0x0138:
            r6 = r2
        L_0x0139:
            android.app.PendingIntent r7 = r3.mIntent
            if (r7 == 0) goto L_0x013f
            r7 = r1
            goto L_0x0140
        L_0x013f:
            r7 = r2
        L_0x0140:
            if (r6 == r7) goto L_0x0144
            r6 = r1
            goto L_0x0145
        L_0x0144:
            r6 = r2
        L_0x0145:
            if (r6 == 0) goto L_0x0148
            goto L_0x014a
        L_0x0148:
            r6 = r2
            goto L_0x014b
        L_0x014a:
            r6 = r1
        L_0x014b:
            if (r6 != 0) goto L_0x0178
            java.lang.String r7 = r3.mKey
            java.util.Objects.requireNonNull(r4)
            java.lang.String r4 = r4.mKey
            boolean r4 = r7.equals(r4)
            if (r4 == 0) goto L_0x015b
            goto L_0x0178
        L_0x015b:
            java.lang.String r9 = "Trying to update entry with different key, new bubble: "
            java.lang.StringBuilder r9 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r9)
            java.lang.String r1 = r3.mKey
            r9.append(r1)
            java.lang.String r1 = " old bubble: "
            r9.append(r1)
            java.lang.String r1 = r3.mKey
            r9.append(r1)
            java.lang.String r9 = r9.toString()
            android.util.Log.w(r5, r9)
            goto L_0x01be
        L_0x0178:
            r9.mBubble = r3
            com.android.wm.shell.common.AlphaOptimizedButton r4 = r9.mManageButton
            android.content.res.Resources r5 = r9.getResources()
            r7 = 2131952070(0x7f1301c6, float:1.9540572E38)
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r8 = r3.mAppName
            r1[r2] = r8
            java.lang.String r1 = r5.getString(r7, r1)
            r4.setContentDescription(r1)
            com.android.wm.shell.common.AlphaOptimizedButton r1 = r9.mManageButton
            com.android.wm.shell.bubbles.BubbleExpandedView$3 r4 = new com.android.wm.shell.bubbles.BubbleExpandedView$3
            r4.<init>()
            r1.setAccessibilityDelegate(r4)
            if (r6 == 0) goto L_0x01bb
            com.android.wm.shell.bubbles.Bubble r1 = r9.mBubble
            java.util.Objects.requireNonNull(r1)
            android.app.PendingIntent r1 = r1.mIntent
            r9.mPendingIntent = r1
            if (r1 != 0) goto L_0x01af
            com.android.wm.shell.bubbles.Bubble r1 = r9.mBubble
            boolean r1 = r1.hasMetadataShortcutId()
            if (r1 == 0) goto L_0x01bb
        L_0x01af:
            com.android.wm.shell.TaskView r1 = r9.mTaskView
            if (r1 == 0) goto L_0x01bb
            r9.setContentVisibility(r2)
            com.android.wm.shell.TaskView r1 = r9.mTaskView
            r1.setVisibility(r2)
        L_0x01bb:
            r9.applyThemeAttrs()
        L_0x01be:
            com.android.wm.shell.bubbles.BadgedImageView r9 = r3.mIconView
            if (r9 == 0) goto L_0x01c5
            r9.setRenderedBubble(r3)
        L_0x01c5:
            com.android.wm.shell.bubbles.BubbleViewInfoTask$Callback r9 = r0.mCallback
            if (r9 == 0) goto L_0x01ce
            com.android.wm.shell.bubbles.Bubble r0 = r0.mBubble
            r9.onBubbleViewsReady(r0)
        L_0x01ce:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda1.run():void");
    }
}
