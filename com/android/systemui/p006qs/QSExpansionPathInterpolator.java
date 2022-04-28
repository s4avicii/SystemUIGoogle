package com.android.systemui.p006qs;

import com.android.systemui.p006qs.PathInterpolatorBuilder;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QSExpansionPathInterpolator */
/* compiled from: QSExpansionPathInterpolator.kt */
public final class QSExpansionPathInterpolator {
    public PathInterpolatorBuilder pathInterpolatorBuilder = new PathInterpolatorBuilder();

    public final PathInterpolatorBuilder.PathInterpolator getYInterpolator() {
        PathInterpolatorBuilder pathInterpolatorBuilder2 = this.pathInterpolatorBuilder;
        Objects.requireNonNull(pathInterpolatorBuilder2);
        return new PathInterpolatorBuilder.PathInterpolator(pathInterpolatorBuilder2.mDist, pathInterpolatorBuilder2.f69mY);
    }
}
