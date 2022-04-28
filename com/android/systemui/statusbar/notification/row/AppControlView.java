package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

/* compiled from: ChannelEditorListView.kt */
public final class AppControlView extends LinearLayout {
    public TextView channelName;
    public ImageView iconView;

    /* renamed from: switch  reason: not valid java name */
    public Switch f169switch;

    public final void onFinishInflate() {
        this.iconView = (ImageView) findViewById(C1777R.C1779id.icon);
        this.channelName = (TextView) findViewById(C1777R.C1779id.app_name);
        this.f169switch = (Switch) findViewById(C1777R.C1779id.toggle);
        setOnClickListener(new AppControlView$onFinishInflate$1(this));
    }

    public AppControlView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
