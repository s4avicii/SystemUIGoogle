package com.android.p012wm.shell.bubbles;

import android.content.Context;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.android.wm.shell.bubbles.BubbleIconFactory */
public class BubbleIconFactory extends BaseIconFactory {
    public BubbleIconFactory(Context context) {
        super(context, context.getResources().getConfiguration().densityDpi, context.getResources().getDimensionPixelSize(C1777R.dimen.bubble_size), false);
    }
}
