package com.android.p012wm.shell.unfold;

import android.content.Context;
import android.graphics.Color;
import android.view.SurfaceControl;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.RootTaskDisplayAreaOrganizer;
import java.util.Objects;

/* renamed from: com.android.wm.shell.unfold.UnfoldBackgroundController */
public final class UnfoldBackgroundController {
    public final float[] mBackgroundColor;
    public SurfaceControl mBackgroundLayer;
    public final RootTaskDisplayAreaOrganizer mRootTaskDisplayAreaOrganizer;

    public final void ensureBackground(SurfaceControl.Transaction transaction) {
        if (this.mBackgroundLayer == null) {
            SurfaceControl.Builder colorLayer = new SurfaceControl.Builder().setName("app-unfold-background").setCallsite("AppUnfoldTransitionController").setColorLayer();
            RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer = this.mRootTaskDisplayAreaOrganizer;
            Objects.requireNonNull(rootTaskDisplayAreaOrganizer);
            colorLayer.setParent(rootTaskDisplayAreaOrganizer.mLeashes.get(0));
            SurfaceControl build = colorLayer.build();
            this.mBackgroundLayer = build;
            transaction.setColor(build, this.mBackgroundColor).show(this.mBackgroundLayer).setLayer(this.mBackgroundLayer, -1);
        }
    }

    public final void removeBackground(SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl = this.mBackgroundLayer;
        if (surfaceControl != null) {
            if (surfaceControl.isValid()) {
                transaction.remove(this.mBackgroundLayer);
            }
            this.mBackgroundLayer = null;
        }
    }

    public UnfoldBackgroundController(Context context, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        this.mRootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        int color = context.getResources().getColor(C1777R.color.unfold_transition_background);
        this.mBackgroundColor = new float[]{((float) Color.red(color)) / 255.0f, ((float) Color.green(color)) / 255.0f, ((float) Color.blue(color)) / 255.0f};
    }
}
