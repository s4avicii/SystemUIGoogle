package com.android.systemui.assist.p003ui;

import android.content.Context;
import android.graphics.Path;
import com.android.systemui.R$id;
import com.android.systemui.assist.p003ui.CornerPathRenderer;

/* renamed from: com.android.systemui.assist.ui.CircularCornerPathRenderer */
public final class CircularCornerPathRenderer extends CornerPathRenderer {
    public final int mCornerRadiusBottom;
    public final int mCornerRadiusTop;
    public final int mHeight;
    public final Path mPath = new Path();
    public final int mWidth;

    public final Path getCornerPath(CornerPathRenderer.Corner corner) {
        this.mPath.reset();
        int ordinal = corner.ordinal();
        if (ordinal == 0) {
            this.mPath.moveTo(0.0f, (float) (this.mHeight - this.mCornerRadiusBottom));
            Path path = this.mPath;
            int i = this.mHeight;
            int i2 = this.mCornerRadiusBottom;
            path.arcTo(0.0f, (float) (i - (i2 * 2)), (float) (i2 * 2), (float) i, 180.0f, -90.0f, true);
        } else if (ordinal == 1) {
            this.mPath.moveTo((float) (this.mWidth - this.mCornerRadiusBottom), (float) this.mHeight);
            Path path2 = this.mPath;
            int i3 = this.mWidth;
            int i4 = this.mCornerRadiusBottom;
            int i5 = this.mHeight;
            path2.arcTo((float) (i3 - (i4 * 2)), (float) (i5 - (i4 * 2)), (float) i3, (float) i5, 90.0f, -90.0f, true);
        } else if (ordinal == 2) {
            this.mPath.moveTo((float) this.mWidth, (float) this.mCornerRadiusTop);
            Path path3 = this.mPath;
            int i6 = this.mWidth;
            int i7 = this.mCornerRadiusTop;
            path3.arcTo((float) (i6 - (i7 * 2)), 0.0f, (float) i6, (float) (i7 * 2), 0.0f, -90.0f, true);
        } else if (ordinal == 3) {
            this.mPath.moveTo((float) this.mCornerRadiusTop, 0.0f);
            Path path4 = this.mPath;
            int i8 = this.mCornerRadiusTop;
            path4.arcTo(0.0f, 0.0f, (float) (i8 * 2), (float) (i8 * 2), 270.0f, -90.0f, true);
        }
        return this.mPath;
    }

    public CircularCornerPathRenderer(Context context) {
        this.mCornerRadiusBottom = R$id.getCornerRadiusBottom(context);
        this.mCornerRadiusTop = R$id.getCornerRadiusTop(context);
        this.mHeight = R$id.getHeight(context);
        this.mWidth = R$id.getWidth(context);
    }
}
