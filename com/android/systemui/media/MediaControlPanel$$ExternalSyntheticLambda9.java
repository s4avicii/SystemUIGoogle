package com.android.systemui.media;

import android.app.PendingIntent;
import android.util.Log;
import android.view.View;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda9 implements View.OnClickListener {
    public final /* synthetic */ MediaControlPanel f$0;
    public final /* synthetic */ MediaDeviceData f$1;
    public final /* synthetic */ MediaData f$2;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda9(MediaControlPanel mediaControlPanel, MediaDeviceData mediaDeviceData, MediaData mediaData) {
        this.f$0 = mediaControlPanel;
        this.f$1 = mediaDeviceData;
        this.f$2 = mediaData;
    }

    public final void onClick(View view) {
        MediaControlPanel mediaControlPanel = this.f$0;
        MediaDeviceData mediaDeviceData = this.f$1;
        MediaData mediaData = this.f$2;
        Objects.requireNonNull(mediaControlPanel);
        if (!mediaControlPanel.mFalsingManager.isFalseTap(1)) {
            Objects.requireNonNull(mediaDeviceData);
            PendingIntent pendingIntent = mediaDeviceData.intent;
            if (pendingIntent == null) {
                MediaOutputDialogFactory mediaOutputDialogFactory = mediaControlPanel.mMediaOutputDialogFactory;
                Objects.requireNonNull(mediaData);
                String str = mediaData.packageName;
                MediaViewHolder mediaViewHolder = mediaControlPanel.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder);
                mediaOutputDialogFactory.create(str, true, mediaViewHolder.seamlessButton);
            } else if (pendingIntent.isActivity()) {
                mediaControlPanel.mActivityStarter.startActivity(mediaDeviceData.intent.getIntent(), true);
            } else {
                try {
                    mediaDeviceData.intent.send();
                } catch (PendingIntent.CanceledException unused) {
                    Log.e("MediaControlPanel", "Device pending intent was canceled");
                }
            }
        }
    }
}
