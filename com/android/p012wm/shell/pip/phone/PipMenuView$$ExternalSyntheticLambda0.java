package com.android.p012wm.shell.pip.phone;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipMenuView$$ExternalSyntheticLambda0 implements Icon.OnDrawableLoadedListener {
    public final /* synthetic */ PipMenuActionView f$0;

    public /* synthetic */ PipMenuView$$ExternalSyntheticLambda0(PipMenuActionView pipMenuActionView) {
        this.f$0 = pipMenuActionView;
    }

    public final void onDrawableLoaded(Drawable drawable) {
        PipMenuActionView pipMenuActionView = this.f$0;
        if (drawable != null) {
            drawable.setTint(-1);
            Objects.requireNonNull(pipMenuActionView);
            pipMenuActionView.mImageView.setImageDrawable(drawable);
        }
    }
}
