package com.android.systemui.statusbar.notification.row;

import android.view.View;
import android.widget.Switch;

/* compiled from: ChannelEditorListView.kt */
public final class ChannelRow$onFinishInflate$2 implements View.OnClickListener {
    public final /* synthetic */ ChannelRow this$0;

    public ChannelRow$onFinishInflate$2(ChannelRow channelRow) {
        this.this$0 = channelRow;
    }

    public final void onClick(View view) {
        Switch switchR = this.this$0.f170switch;
        if (switchR == null) {
            switchR = null;
        }
        switchR.toggle();
    }
}
