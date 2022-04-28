package com.android.p012wm.shell.common;

import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.drawable.shapes.PathShape;

/* renamed from: com.android.wm.shell.common.TriangleShape */
public final class TriangleShape extends PathShape {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Path mTriangularPath;

    public static TriangleShape createHorizontal(float f, float f2, boolean z) {
        Path path = new Path();
        if (z) {
            path.moveTo(0.0f, f2 / 2.0f);
            path.lineTo(f, f2);
            path.lineTo(f, 0.0f);
            path.close();
        } else {
            path.moveTo(0.0f, f2);
            path.lineTo(f, f2 / 2.0f);
            path.lineTo(0.0f, 0.0f);
            path.close();
        }
        return new TriangleShape(path, f, f2);
    }

    public final void getOutline(Outline outline) {
        outline.setPath(this.mTriangularPath);
    }

    public TriangleShape(Path path, float f, float f2) {
        super(path, f, f2);
        this.mTriangularPath = path;
    }
}
