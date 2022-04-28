package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.p012wm.shell.C1777R;

/* compiled from: ForegroundServiceDungeonView.kt */
public final class ForegroundServiceDungeonView extends StackScrollerDecorView {
    public final View findSecondaryView() {
        return null;
    }

    public final void setVisible(boolean z, boolean z2) {
    }

    public final View findContentView() {
        return findViewById(C1777R.C1779id.foreground_service_dungeon);
    }

    public ForegroundServiceDungeonView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
