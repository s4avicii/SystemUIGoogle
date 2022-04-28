package com.android.systemui.statusbar.phone.userswitcher;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

/* compiled from: StatusBarUserSwitcherContainer.kt */
public final class StatusBarUserSwitcherContainer extends LinearLayout {
    public ImageView avatar;
    public TextView text;

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.text = (TextView) findViewById(C1777R.C1779id.current_user_name);
        this.avatar = (ImageView) findViewById(C1777R.C1779id.current_user_avatar);
    }

    public StatusBarUserSwitcherContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
