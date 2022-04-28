package com.android.systemui.media;

import android.view.View;
import com.android.systemui.media.MediaHost;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.MeasurementOutput;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: MediaHost.kt */
public final class MediaHost$init$2 implements UniqueObjectHostView.MeasurementManager {
    public final /* synthetic */ int $location;
    public final /* synthetic */ MediaHost this$0;

    public MediaHost$init$2(MediaHost mediaHost, int i) {
        this.this$0 = mediaHost;
        this.$location = i;
    }

    public final MeasurementOutput onMeasure(MeasurementInput measurementInput) {
        if (View.MeasureSpec.getMode(measurementInput.widthMeasureSpec) == Integer.MIN_VALUE) {
            measurementInput.widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measurementInput.widthMeasureSpec), 1073741824);
        }
        MediaHost.MediaHostStateHolder mediaHostStateHolder = this.this$0.state;
        boolean z = false;
        if (measurementInput.equals(mediaHostStateHolder.measurementInput)) {
            z = true;
        }
        if (!z) {
            mediaHostStateHolder.measurementInput = measurementInput;
            Function0<Unit> function0 = mediaHostStateHolder.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        } else {
            Objects.requireNonNull(mediaHostStateHolder);
        }
        MediaHost mediaHost = this.this$0;
        return mediaHost.mediaHostStatesManager.updateCarouselDimensions(this.$location, mediaHost.state);
    }
}
