package com.android.systemui.statusbar.policy;

import android.view.View;
import android.widget.Button;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.Objects;

/* compiled from: SmartReplyStateInflater.kt */
public final class SmartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1 implements View.OnClickListener {
    public final /* synthetic */ CharSequence $choice;
    public final /* synthetic */ NotificationEntry $entry;
    public final /* synthetic */ SmartReplyView $parent;
    public final /* synthetic */ int $replyIndex;
    public final /* synthetic */ SmartReplyView.SmartReplies $smartReplies;
    public final /* synthetic */ Button $this_apply;
    public final /* synthetic */ SmartReplyInflaterImpl this$0;

    public SmartReplyInflaterImpl$inflateReplyButton$1$onClickListener$1(SmartReplyInflaterImpl smartReplyInflaterImpl, NotificationEntry notificationEntry, SmartReplyView.SmartReplies smartReplies, int i, SmartReplyView smartReplyView, Button button, CharSequence charSequence) {
        this.this$0 = smartReplyInflaterImpl;
        this.$entry = notificationEntry;
        this.$smartReplies = smartReplies;
        this.$replyIndex = i;
        this.$parent = smartReplyView;
        this.$this_apply = button;
        this.$choice = charSequence;
    }

    public final void onClick(View view) {
        SmartReplyInflaterImpl smartReplyInflaterImpl = this.this$0;
        NotificationEntry notificationEntry = this.$entry;
        SmartReplyView.SmartReplies smartReplies = this.$smartReplies;
        int i = this.$replyIndex;
        SmartReplyView smartReplyView = this.$parent;
        Button button = this.$this_apply;
        CharSequence charSequence = this.$choice;
        Objects.requireNonNull(smartReplyInflaterImpl);
        SmartReplyInflaterImpl$onSmartReplyClick$1 smartReplyInflaterImpl$onSmartReplyClick$1 = new SmartReplyInflaterImpl$onSmartReplyClick$1(smartReplyInflaterImpl, smartReplies, button, charSequence, i, notificationEntry, smartReplyView);
        boolean z = SmartReplyStateInflaterKt.DEBUG;
        smartReplyInflaterImpl.keyguardDismissUtil.executeWhenUnlocked(new C1645xc0c4f386(smartReplyInflaterImpl$onSmartReplyClick$1), !notificationEntry.isRowPinned(), false);
    }
}
