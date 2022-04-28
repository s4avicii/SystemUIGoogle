package com.android.systemui.media;

import android.content.Intent;
import android.view.View;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda13 implements View.OnLongClickListener {
    public static final /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda13 INSTANCE = new MediaControlPanel$$ExternalSyntheticLambda13();

    public final boolean onLongClick(View view) {
        Intent intent = MediaControlPanel.SETTINGS_INTENT;
        View view2 = (View) view.getParent();
        if (view2 == null) {
            return true;
        }
        view2.performLongClick();
        return true;
    }
}
