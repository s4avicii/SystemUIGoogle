package com.android.systemui.navigationbar;

import android.view.View;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda4 implements View.OnLongClickListener {
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda4(NavigationBar navigationBar) {
        this.f$0 = navigationBar;
    }

    public final boolean onLongClick(View view) {
        NavigationBar navigationBar = this.f$0;
        Objects.requireNonNull(navigationBar);
        return navigationBar.onLongPressNavigationButtons(view, C1777R.C1779id.home);
    }
}
