package com.android.systemui.p006qs;

import android.content.Context;
import android.content.res.ColorStateList;
import com.android.systemui.p006qs.tileimpl.SlashImageView;

/* renamed from: com.android.systemui.qs.AlphaControlledSignalTileView */
public final class AlphaControlledSignalTileView extends SignalTileView {
    public final SlashImageView createSlashImageView(Context context) {
        return new AlphaControlledSlashImageView(context);
    }

    /* renamed from: com.android.systemui.qs.AlphaControlledSignalTileView$AlphaControlledSlashDrawable */
    public static class AlphaControlledSlashDrawable extends SlashDrawable {
        public final void setDrawableTintList(ColorStateList colorStateList) {
        }

        public final void setFinalTintList(ColorStateList colorStateList) {
            super.setDrawableTintList(colorStateList);
        }
    }

    /* renamed from: com.android.systemui.qs.AlphaControlledSignalTileView$AlphaControlledSlashImageView */
    public static class AlphaControlledSlashImageView extends SlashImageView {
        public AlphaControlledSlashImageView(Context context) {
            super(context);
        }
    }

    public AlphaControlledSignalTileView(Context context) {
        super(context);
    }
}
