package com.android.p012wm.shell.pip.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.android.wm.shell.pip.phone.PipMenuActionView */
public class PipMenuActionView extends FrameLayout {
    public ImageView mImageView;

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mImageView = (ImageView) findViewById(C1777R.C1779id.image);
    }

    public PipMenuActionView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
