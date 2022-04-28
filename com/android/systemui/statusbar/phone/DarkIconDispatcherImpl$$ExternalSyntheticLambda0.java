package com.android.systemui.statusbar.phone;

import android.content.res.ColorStateList;
import android.widget.ImageView;
import com.android.systemui.plugins.DarkIconDispatcher;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DarkIconDispatcherImpl$$ExternalSyntheticLambda0 implements DarkIconDispatcher.DarkReceiver {
    public final /* synthetic */ DarkIconDispatcherImpl f$0;
    public final /* synthetic */ ImageView f$1;

    public /* synthetic */ DarkIconDispatcherImpl$$ExternalSyntheticLambda0(DarkIconDispatcherImpl darkIconDispatcherImpl, ImageView imageView) {
        this.f$0 = darkIconDispatcherImpl;
        this.f$1 = imageView;
    }

    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        DarkIconDispatcherImpl darkIconDispatcherImpl = this.f$0;
        ImageView imageView = this.f$1;
        Objects.requireNonNull(darkIconDispatcherImpl);
        imageView.setImageTintList(ColorStateList.valueOf(DarkIconDispatcher.getTint(darkIconDispatcherImpl.mTintAreas, imageView, darkIconDispatcherImpl.mIconTint)));
    }
}
