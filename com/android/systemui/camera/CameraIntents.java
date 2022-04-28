package com.android.systemui.camera;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.android.p012wm.shell.C1777R;

/* compiled from: CameraIntents.kt */
public final class CameraIntents {
    public static final Intent getInsecureCameraIntent(Context context) {
        Intent intent = new Intent("android.media.action.STILL_IMAGE_CAMERA");
        String string = context.getResources().getString(C1777R.string.config_cameraGesturePackage);
        if (string == null || TextUtils.isEmpty(string)) {
            string = null;
        }
        if (string != null) {
            intent.setPackage(string);
        }
        return intent;
    }
}
