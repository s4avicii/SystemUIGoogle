package com.android.p012wm.shell.pip.p013tv;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.tv.TvPipMenuView$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TvPipMenuView$$ExternalSyntheticLambda0 implements Icon.OnDrawableLoadedListener {
    public final /* synthetic */ TvPipMenuActionButton f$0;

    public /* synthetic */ TvPipMenuView$$ExternalSyntheticLambda0(TvPipMenuActionButton tvPipMenuActionButton) {
        this.f$0 = tvPipMenuActionButton;
    }

    public final void onDrawableLoaded(Drawable drawable) {
        TvPipMenuActionButton tvPipMenuActionButton = this.f$0;
        Objects.requireNonNull(tvPipMenuActionButton);
        tvPipMenuActionButton.mIconImageView.setImageDrawable(drawable);
    }
}
