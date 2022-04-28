package com.android.p012wm.shell.common;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.android.wm.shell.common.DismissCircleView */
public final class DismissCircleView extends FrameLayout {
    public final ImageView mIconView;

    public DismissCircleView(Context context) {
        super(context);
        ImageView imageView = new ImageView(getContext());
        this.mIconView = imageView;
        Resources resources = getResources();
        setBackground(resources.getDrawable(C1777R.C1778drawable.dismiss_circle_background));
        imageView.setImageDrawable(resources.getDrawable(C1777R.C1778drawable.pip_ic_close_white));
        addView(imageView);
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.dismiss_target_x_size);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize, 17));
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.dismiss_target_x_size);
        this.mIconView.setLayoutParams(new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize, 17));
    }
}
