package com.android.systemui.statusbar.policy;

import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.Objects;

/* compiled from: SmartReplyStateInflater.kt */
public final class SmartReplyInflaterImpl implements SmartReplyInflater {
    public final SmartReplyConstants constants;
    public final Context context;
    public final KeyguardDismissUtil keyguardDismissUtil;
    public final NotificationRemoteInputManager remoteInputManager;
    public final SmartReplyController smartReplyController;

    /* JADX WARNING: type inference failed for: r0v7, types: [com.android.systemui.statusbar.policy.DelayedOnClickListener] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.widget.Button inflateReplyButton(com.android.systemui.statusbar.policy.SmartReplyView r12, com.android.systemui.statusbar.notification.collection.NotificationEntry r13, com.android.systemui.statusbar.policy.SmartReplyView.SmartReplies r14, int r15, java.lang.CharSequence r16, boolean r17) {
        /*
            r11 = this;
            r8 = r12
            android.content.Context r0 = r12.getContext()
            android.view.LayoutInflater r0 = android.view.LayoutInflater.from(r0)
            r1 = 2131624490(0x7f0e022a, float:1.8876161E38)
            r2 = 0
            android.view.View r0 = r0.inflate(r1, r12, r2)
            java.lang.String r1 = "null cannot be cast to non-null type android.widget.Button"
            java.util.Objects.requireNonNull(r0, r1)
            r9 = r0
            android.widget.Button r9 = (android.widget.Button) r9
            r7 = r16
            r9.setText(r7)
            com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1 r10 = new com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1
            r0 = r10
            r1 = r11
            r2 = r13
            r3 = r14
            r4 = r15
            r5 = r12
            r6 = r9
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            if (r17 == 0) goto L_0x003a
            com.android.systemui.statusbar.policy.DelayedOnClickListener r0 = new com.android.systemui.statusbar.policy.DelayedOnClickListener
            r1 = r11
            com.android.systemui.statusbar.policy.SmartReplyConstants r1 = r1.constants
            java.util.Objects.requireNonNull(r1)
            long r1 = r1.mOnClickInitDelay
            r0.<init>(r10, r1)
            r10 = r0
        L_0x003a:
            r9.setOnClickListener(r10)
            com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$inflateReplyButton$1$1 r0 = new com.android.systemui.statusbar.policy.SmartReplyInflaterImpl$inflateReplyButton$1$1
            r0.<init>(r12)
            r9.setAccessibilityDelegate(r0)
            android.view.ViewGroup$LayoutParams r0 = r9.getLayoutParams()
            java.lang.String r1 = "null cannot be cast to non-null type com.android.systemui.statusbar.policy.SmartReplyView.LayoutParams"
            java.util.Objects.requireNonNull(r0, r1)
            com.android.systemui.statusbar.policy.SmartReplyView$LayoutParams r0 = (com.android.systemui.statusbar.policy.SmartReplyView.LayoutParams) r0
            com.android.systemui.statusbar.policy.SmartReplyView$SmartButtonType r1 = com.android.systemui.statusbar.policy.SmartReplyView.SmartButtonType.REPLY
            r0.mButtonType = r1
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyInflaterImpl.inflateReplyButton(com.android.systemui.statusbar.policy.SmartReplyView, com.android.systemui.statusbar.notification.collection.NotificationEntry, com.android.systemui.statusbar.policy.SmartReplyView$SmartReplies, int, java.lang.CharSequence, boolean):android.widget.Button");
    }

    public SmartReplyInflaterImpl(SmartReplyConstants smartReplyConstants, KeyguardDismissUtil keyguardDismissUtil2, NotificationRemoteInputManager notificationRemoteInputManager, SmartReplyController smartReplyController2, Context context2) {
        this.constants = smartReplyConstants;
        this.keyguardDismissUtil = keyguardDismissUtil2;
        this.remoteInputManager = notificationRemoteInputManager;
        this.smartReplyController = smartReplyController2;
        this.context = context2;
    }

    public static final Intent access$createRemoteInputIntent(SmartReplyInflaterImpl smartReplyInflaterImpl, SmartReplyView.SmartReplies smartReplies, CharSequence charSequence) {
        Objects.requireNonNull(smartReplyInflaterImpl);
        Bundle bundle = new Bundle();
        bundle.putString(smartReplies.remoteInput.getResultKey(), charSequence.toString());
        Intent addFlags = new Intent().addFlags(268435456);
        RemoteInput.addResultsToIntent(new RemoteInput[]{smartReplies.remoteInput}, addFlags, bundle);
        RemoteInput.setResultsSource(addFlags, 1);
        return addFlags;
    }
}
