package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;

/* compiled from: ChannelEditorListView.kt */
public final class ChannelRow extends LinearLayout {
    public NotificationChannel channel;
    public TextView channelDescription;
    public TextView channelName;
    public ChannelEditorDialogController controller;
    public final int highlightColor = Utils.getColorAttrDefaultColor(getContext(), 16843820);

    /* renamed from: switch  reason: not valid java name */
    public Switch f170switch;

    public ChannelRow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.channelName = (TextView) findViewById(C1777R.C1779id.channel_name);
        this.channelDescription = (TextView) findViewById(C1777R.C1779id.channel_description);
        Switch switchR = (Switch) findViewById(C1777R.C1779id.toggle);
        this.f170switch = switchR;
        switchR.setOnCheckedChangeListener(new ChannelRow$onFinishInflate$1(this));
        setOnClickListener(new ChannelRow$onFinishInflate$2(this));
    }
}
