package com.android.p012wm.shell.pip.p013tv;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.text.TextUtils;
import com.android.p012wm.shell.pip.PipMediaController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.tv.TvPipNotificationController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TvPipNotificationController$$ExternalSyntheticLambda0 implements PipMediaController.MetadataListener {
    public final /* synthetic */ TvPipNotificationController f$0;

    public /* synthetic */ TvPipNotificationController$$ExternalSyntheticLambda0(TvPipNotificationController tvPipNotificationController) {
        this.f$0 = tvPipNotificationController;
    }

    public final void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
        Bitmap bitmap;
        boolean z;
        TvPipNotificationController tvPipNotificationController = this.f$0;
        String str = null;
        if (mediaMetadata != null) {
            Objects.requireNonNull(tvPipNotificationController);
            str = mediaMetadata.getString("android.media.metadata.DISPLAY_TITLE");
            if (TextUtils.isEmpty(str)) {
                str = mediaMetadata.getString("android.media.metadata.TITLE");
            }
            Bitmap bitmap2 = mediaMetadata.getBitmap("android.media.metadata.ALBUM_ART");
            if (bitmap2 == null) {
                bitmap = mediaMetadata.getBitmap("android.media.metadata.ART");
            } else {
                bitmap = bitmap2;
            }
        } else {
            bitmap = null;
        }
        if (!TextUtils.equals(str, tvPipNotificationController.mMediaTitle) || !Objects.equals(bitmap, tvPipNotificationController.mArt)) {
            tvPipNotificationController.mMediaTitle = str;
            tvPipNotificationController.mArt = bitmap;
            z = true;
        } else {
            z = false;
        }
        if (z && tvPipNotificationController.mNotified) {
            tvPipNotificationController.update();
        }
    }
}
