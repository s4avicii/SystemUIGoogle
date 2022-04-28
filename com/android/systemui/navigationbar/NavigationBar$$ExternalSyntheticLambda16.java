package com.android.systemui.navigationbar;

import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda16 implements Consumer {
    public static final /* synthetic */ NavigationBar$$ExternalSyntheticLambda16 INSTANCE = new NavigationBar$$ExternalSyntheticLambda16();

    public final void accept(Object obj) {
        StatusBar statusBar = (StatusBar) obj;
        Objects.requireNonNull(statusBar);
        statusBar.mUiBgExecutor.execute(new QSTileImpl$$ExternalSyntheticLambda1(statusBar, 3));
    }
}
