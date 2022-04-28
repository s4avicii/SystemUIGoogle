package com.android.systemui.wmshell;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubblesManager$5$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BubblesManager$5$$ExternalSyntheticLambda2(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v7, types: [int] */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r6 = this;
            int r0 = r6.$r8$classId
            r1 = 0
            r2 = 1
            switch(r0) {
                case 0: goto L_0x007a;
                case 1: goto L_0x0059;
                case 2: goto L_0x0048;
                case 3: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0095
        L_0x0009:
            java.lang.Object r0 = r6.f$0
            com.android.wm.shell.bubbles.animation.StackAnimationController r0 = (com.android.p012wm.shell.bubbles.animation.StackAnimationController) r0
            java.lang.Object r6 = r6.f$1
            java.util.List r6 = (java.util.List) r6
            java.util.Objects.requireNonNull(r0)
        L_0x0014:
            int r3 = r6.size()
            if (r1 >= r3) goto L_0x0047
            java.lang.Object r3 = r6.get(r1)
            android.view.View r3 = (android.view.View) r3
            r4 = 2
            if (r1 >= r4) goto L_0x002b
            int r4 = r0.mMaxBubbles
            int r5 = r0.mElevation
            int r4 = r4 * r5
            int r4 = r4 - r1
            float r4 = (float) r4
            goto L_0x002c
        L_0x002b:
            r4 = 0
        L_0x002c:
            r3.setZ(r4)
            com.android.wm.shell.bubbles.BadgedImageView r3 = (com.android.p012wm.shell.bubbles.BadgedImageView) r3
            if (r1 != 0) goto L_0x003c
            boolean r4 = r0.isStackOnLeftSide()
            r4 = r4 ^ r2
            r3.showDotAndBadge(r4)
            goto L_0x0044
        L_0x003c:
            boolean r4 = r0.isStackOnLeftSide()
            r4 = r4 ^ r2
            r3.hideDotAndBadge(r4)
        L_0x0044:
            int r1 = r1 + 1
            goto L_0x0014
        L_0x0047:
            return
        L_0x0048:
            java.lang.Object r0 = r6.f$0
            com.android.wm.shell.bubbles.BubbleController$BubblesImpl r0 = (com.android.p012wm.shell.bubbles.BubbleController.BubblesImpl) r0
            java.lang.Object r6 = r6.f$1
            com.android.wm.shell.bubbles.Bubbles$BubbleExpandListener r6 = (com.android.p012wm.shell.bubbles.Bubbles.BubbleExpandListener) r6
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.bubbles.BubbleController r0 = com.android.p012wm.shell.bubbles.BubbleController.this
            r0.setExpandListener(r6)
            return
        L_0x0059:
            java.lang.Object r0 = r6.f$0
            com.android.systemui.statusbar.phone.StatusBar$2 r0 = (com.android.systemui.statusbar.phone.StatusBar.C15432) r0
            java.lang.Object r6 = r6.f$1
            com.android.systemui.plugins.OverlayPlugin r6 = (com.android.systemui.plugins.OverlayPlugin) r6
            java.util.Objects.requireNonNull(r0)
            android.util.ArraySet<com.android.systemui.plugins.OverlayPlugin> r3 = r0.mOverlays
            r3.remove(r6)
            com.android.systemui.statusbar.phone.StatusBar r6 = com.android.systemui.statusbar.phone.StatusBar.this
            com.android.systemui.statusbar.NotificationShadeWindowController r6 = r6.mNotificationShadeWindowController
            android.util.ArraySet<com.android.systemui.plugins.OverlayPlugin> r3 = r0.mOverlays
            int r3 = r3.size()
            if (r3 == 0) goto L_0x0076
            r1 = r2
        L_0x0076:
            r6.setForcePluginOpen(r1, r0)
            return
        L_0x007a:
            java.lang.Object r0 = r6.f$0
            com.android.systemui.wmshell.BubblesManager$5 r0 = (com.android.systemui.wmshell.BubblesManager.C17525) r0
            java.lang.Object r6 = r6.f$1
            java.util.function.Consumer r6 = (java.util.function.Consumer) r6
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.wmshell.BubblesManager r0 = com.android.systemui.wmshell.BubblesManager.this
            com.android.systemui.statusbar.NotificationShadeWindowController r0 = r0.mNotificationShadeWindowController
            boolean r0 = r0.getPanelExpanded()
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            r6.accept(r0)
            return
        L_0x0095:
            java.lang.Object r6 = r6.f$0
            com.android.wm.shell.pip.tv.TvPipController$TvPipImpl r6 = (com.android.p012wm.shell.pip.p013tv.TvPipController.TvPipImpl) r6
            java.util.Objects.requireNonNull(r6)
            com.android.wm.shell.pip.tv.TvPipController r6 = com.android.p012wm.shell.pip.p013tv.TvPipController.this
            java.util.Objects.requireNonNull(r6)
            int r0 = r6.mState
            if (r0 == 0) goto L_0x00a6
            r1 = r2
        L_0x00a6:
            if (r1 == 0) goto L_0x00ab
            r6.closePip()
        L_0x00ab:
            android.content.Context r0 = r6.mContext
            android.content.res.Resources r0 = r0.getResources()
            r1 = 2131492902(0x7f0c0026, float:1.860927E38)
            int r0 = r0.getInteger(r1)
            r6.mResizeAnimationDuration = r0
            com.android.wm.shell.pip.tv.TvPipNotificationController r0 = r6.mPipNotificationController
            android.content.Context r6 = r6.mContext
            java.util.Objects.requireNonNull(r0)
            android.content.res.Resources r6 = r6.getResources()
            r1 = 2131952982(0x7f130556, float:1.9542422E38)
            java.lang.String r6 = r6.getString(r1)
            r0.mDefaultTitle = r6
            boolean r6 = r0.mNotified
            if (r6 == 0) goto L_0x00d5
            r0.update()
        L_0x00d5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda2.run():void");
    }
}
