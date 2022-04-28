package com.android.systemui.screenshot;

import android.graphics.RenderNode;
import com.android.systemui.screenshot.ImageTileSet;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TiledImageDrawable$$ExternalSyntheticLambda0 implements ImageTileSet.OnContentChangedListener {
    public final /* synthetic */ TiledImageDrawable f$0;

    public /* synthetic */ TiledImageDrawable$$ExternalSyntheticLambda0(TiledImageDrawable tiledImageDrawable) {
        this.f$0 = tiledImageDrawable;
    }

    public final void onContentChanged() {
        TiledImageDrawable tiledImageDrawable = this.f$0;
        Objects.requireNonNull(tiledImageDrawable);
        RenderNode renderNode = tiledImageDrawable.mNode;
        if (renderNode != null && renderNode.hasDisplayList()) {
            tiledImageDrawable.mNode.discardDisplayList();
        }
        tiledImageDrawable.invalidateSelf();
    }
}
