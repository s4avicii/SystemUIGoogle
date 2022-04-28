package com.android.systemui.people;

import android.graphics.ImageDecoder;
import android.util.Size;
import android.util.TypedValue;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleTileViewHelper$$ExternalSyntheticLambda0 implements ImageDecoder.OnHeaderDecodedListener {
    public final /* synthetic */ PeopleTileViewHelper f$0;

    public /* synthetic */ PeopleTileViewHelper$$ExternalSyntheticLambda0(PeopleTileViewHelper peopleTileViewHelper) {
        this.f$0 = peopleTileViewHelper;
    }

    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        double d;
        PeopleTileViewHelper peopleTileViewHelper = this.f$0;
        Objects.requireNonNull(peopleTileViewHelper);
        int applyDimension = (int) TypedValue.applyDimension(1, (float) peopleTileViewHelper.mWidth, peopleTileViewHelper.mContext.getResources().getDisplayMetrics());
        int applyDimension2 = (int) TypedValue.applyDimension(1, (float) peopleTileViewHelper.mHeight, peopleTileViewHelper.mContext.getResources().getDisplayMetrics());
        int max = Math.max(applyDimension, applyDimension2);
        int min = (int) (((double) Math.min(applyDimension, applyDimension2)) * 1.5d);
        if (min < max) {
            max = min;
        }
        Size size = imageInfo.getSize();
        int max2 = Math.max(size.getHeight(), size.getWidth());
        if (max2 > max) {
            d = (double) ((((float) max2) * 1.0f) / ((float) max));
        } else {
            d = 1.0d;
        }
        imageDecoder.setTargetSampleSize(Math.max(1, Integer.highestOneBit((int) Math.floor(d))));
    }
}
