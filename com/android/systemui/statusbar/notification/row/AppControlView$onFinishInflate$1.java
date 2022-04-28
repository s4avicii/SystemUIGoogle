package com.android.systemui.statusbar.notification.row;

import android.view.View;
import android.widget.Switch;
import java.util.Objects;

/* compiled from: ChannelEditorListView.kt */
public final class AppControlView$onFinishInflate$1 implements View.OnClickListener {
    public final /* synthetic */ AppControlView this$0;

    public AppControlView$onFinishInflate$1(AppControlView appControlView) {
        this.this$0 = appControlView;
    }

    public final void onClick(View view) {
        AppControlView appControlView = this.this$0;
        Objects.requireNonNull(appControlView);
        Switch switchR = appControlView.f169switch;
        if (switchR == null) {
            switchR = null;
        }
        switchR.toggle();
    }
}
