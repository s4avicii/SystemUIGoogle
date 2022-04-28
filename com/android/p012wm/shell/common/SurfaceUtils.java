package com.android.p012wm.shell.common;

import android.view.SurfaceControl;
import android.view.SurfaceSession;

/* renamed from: com.android.wm.shell.common.SurfaceUtils */
public final class SurfaceUtils {
    public static SurfaceControl makeDimLayer(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, String str, SurfaceSession surfaceSession) {
        SurfaceControl build = new SurfaceControl.Builder(surfaceSession).setParent(surfaceControl).setColorLayer().setName(str).setCallsite("SurfaceUtils.makeColorLayer").build();
        transaction.setLayer(build, Integer.MAX_VALUE).setColor(build, new float[]{0.0f, 0.0f, 0.0f});
        return build;
    }
}
