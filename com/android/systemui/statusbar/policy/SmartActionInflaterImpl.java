package com.android.systemui.statusbar.policy;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.SmartReplyController;

/* compiled from: SmartReplyStateInflater.kt */
public final class SmartActionInflaterImpl implements SmartActionInflater {
    public final ActivityStarter activityStarter;
    public final SmartReplyConstants constants;
    public final SmartReplyController smartReplyController;

    public SmartActionInflaterImpl(SmartReplyConstants smartReplyConstants, ActivityStarter activityStarter2, SmartReplyController smartReplyController2) {
        this.constants = smartReplyConstants;
        this.activityStarter = activityStarter2;
        this.smartReplyController = smartReplyController2;
    }

    /* JADX WARNING: type inference failed for: r9v3, types: [com.android.systemui.statusbar.policy.DelayedOnClickListener] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.widget.Button inflateActionButton(com.android.systemui.statusbar.policy.SmartReplyView r8, com.android.systemui.statusbar.notification.collection.NotificationEntry r9, com.android.systemui.statusbar.policy.SmartReplyView.SmartActions r10, int r11, android.app.Notification.Action r12, boolean r13, android.view.ContextThemeWrapper r14) {
        /*
            r7 = this;
            android.content.Context r0 = r8.getContext()
            android.view.LayoutInflater r0 = android.view.LayoutInflater.from(r0)
            r1 = 2131624489(0x7f0e0229, float:1.887616E38)
            r2 = 0
            android.view.View r8 = r0.inflate(r1, r8, r2)
            java.lang.String r0 = "null cannot be cast to non-null type android.widget.Button"
            java.util.Objects.requireNonNull(r8, r0)
            android.widget.Button r8 = (android.widget.Button) r8
            java.lang.CharSequence r0 = r12.title
            r8.setText(r0)
            android.graphics.drawable.Icon r0 = r12.getIcon()
            android.graphics.drawable.Drawable r14 = r0.loadDrawable(r14)
            android.content.Context r0 = r8.getContext()
            android.content.res.Resources r0 = r0.getResources()
            r1 = 2131167021(0x7f07072d, float:1.7948304E38)
            int r0 = r0.getDimensionPixelSize(r1)
            r14.setBounds(r2, r2, r0, r0)
            r0 = 0
            r8.setCompoundDrawables(r14, r0, r0, r0)
            com.android.systemui.statusbar.policy.SmartActionInflaterImpl$inflateActionButton$1$onClickListener$1 r14 = new com.android.systemui.statusbar.policy.SmartActionInflaterImpl$inflateActionButton$1$onClickListener$1
            r1 = r14
            r2 = r7
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            r1.<init>(r2, r3, r4, r5, r6)
            if (r13 == 0) goto L_0x0054
            com.android.systemui.statusbar.policy.DelayedOnClickListener r9 = new com.android.systemui.statusbar.policy.DelayedOnClickListener
            com.android.systemui.statusbar.policy.SmartReplyConstants r7 = r7.constants
            java.util.Objects.requireNonNull(r7)
            long r10 = r7.mOnClickInitDelay
            r9.<init>(r14, r10)
            r14 = r9
        L_0x0054:
            r8.setOnClickListener(r14)
            android.view.ViewGroup$LayoutParams r7 = r8.getLayoutParams()
            java.lang.String r9 = "null cannot be cast to non-null type com.android.systemui.statusbar.policy.SmartReplyView.LayoutParams"
            java.util.Objects.requireNonNull(r7, r9)
            com.android.systemui.statusbar.policy.SmartReplyView$LayoutParams r7 = (com.android.systemui.statusbar.policy.SmartReplyView.LayoutParams) r7
            com.android.systemui.statusbar.policy.SmartReplyView$SmartButtonType r9 = com.android.systemui.statusbar.policy.SmartReplyView.SmartButtonType.ACTION
            r7.mButtonType = r9
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartActionInflaterImpl.inflateActionButton(com.android.systemui.statusbar.policy.SmartReplyView, com.android.systemui.statusbar.notification.collection.NotificationEntry, com.android.systemui.statusbar.policy.SmartReplyView$SmartActions, int, android.app.Notification$Action, boolean, android.view.ContextThemeWrapper):android.widget.Button");
    }
}
