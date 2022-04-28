package com.android.systemui.media.dialog;

import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.media.LocalMediaManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import java.util.Objects;

public final class MediaOutputDialog extends MediaOutputBaseDialog {
    public final UiEventLogger mUiEventLogger;

    @VisibleForTesting
    public enum MediaOutputEvent implements UiEventLogger.UiEventEnum {
        ;
        
        private final int mId;

        /* access modifiers changed from: public */
        MediaOutputEvent() {
            this.mId = 655;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public final void getHeaderIconRes() {
    }

    public final Drawable getAppSourceIcon() {
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        Objects.requireNonNull(mediaOutputController);
        if (mediaOutputController.mPackageName.isEmpty()) {
            return null;
        }
        try {
            Log.d("MediaOutputController", "try to get app icon");
            return mediaOutputController.mContext.getPackageManager().getApplicationIcon(mediaOutputController.mPackageName);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("MediaOutputController", "icon not found");
            return null;
        }
    }

    public final IconCompat getHeaderIcon() {
        IconCompat iconCompat;
        Bitmap iconBitmap;
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        Objects.requireNonNull(mediaOutputController);
        MediaController mediaController = mediaOutputController.mMediaController;
        if (mediaController == null) {
            return null;
        }
        MediaMetadata metadata = mediaController.getMetadata();
        if (metadata == null || (iconBitmap = metadata.getDescription().getIconBitmap()) == null) {
            if (MediaOutputController.DEBUG) {
                Log.d("MediaOutputController", "Media meta data does not contain icon information");
            }
            if (TextUtils.isEmpty(mediaOutputController.mPackageName)) {
                return null;
            }
            for (NotificationEntry next : mediaOutputController.mNotifCollection.getAllNotifs()) {
                Objects.requireNonNull(next);
                Notification notification = next.mSbn.getNotification();
                if (notification.isMediaNotification() && TextUtils.equals(next.mSbn.getPackageName(), mediaOutputController.mPackageName)) {
                    Icon largeIcon = notification.getLargeIcon();
                    if (largeIcon == null) {
                        return null;
                    }
                    String str = IconCompat.EXTRA_TYPE;
                    int type = largeIcon.getType();
                    if (type == 2) {
                        iconCompat = IconCompat.createWithResource((Resources) null, largeIcon.getResPackage(), largeIcon.getResId());
                    } else if (type == 4) {
                        iconCompat = IconCompat.createWithContentUri(largeIcon.getUri());
                    } else if (type != 6) {
                        IconCompat iconCompat2 = new IconCompat(-1);
                        iconCompat2.mObj1 = largeIcon;
                        return iconCompat2;
                    } else {
                        iconCompat = IconCompat.createWithAdaptiveBitmapContentUri(largeIcon.getUri());
                    }
                    return iconCompat;
                }
            }
            return null;
        }
        Context context = mediaOutputController.mContext;
        Bitmap createBitmap = Bitmap.createBitmap(iconBitmap.getWidth(), iconBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RoundedBitmapDrawable21 roundedBitmapDrawable21 = new RoundedBitmapDrawable21(context.getResources(), iconBitmap);
        roundedBitmapDrawable21.mPaint.setAntiAlias(true);
        roundedBitmapDrawable21.invalidateSelf();
        roundedBitmapDrawable21.setCornerRadius((float) context.getResources().getDimensionPixelSize(C1777R.dimen.media_output_dialog_icon_corner_radius));
        Canvas canvas = new Canvas(createBitmap);
        roundedBitmapDrawable21.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        roundedBitmapDrawable21.draw(canvas);
        return IconCompat.createWithBitmap(createBitmap);
    }

    public final int getHeaderIconSize() {
        return this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.media_output_dialog_header_album_icon_size);
    }

    public final CharSequence getHeaderSubtitle() {
        MediaMetadata metadata;
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        Objects.requireNonNull(mediaOutputController);
        MediaController mediaController = mediaOutputController.mMediaController;
        if (mediaController == null || (metadata = mediaController.getMetadata()) == null) {
            return null;
        }
        return metadata.getDescription().getSubtitle();
    }

    public final CharSequence getHeaderText() {
        MediaMetadata metadata;
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        Objects.requireNonNull(mediaOutputController);
        MediaController mediaController = mediaOutputController.mMediaController;
        if (mediaController == null || (metadata = mediaController.getMetadata()) == null) {
            return mediaOutputController.mContext.getText(C1777R.string.controls_media_title);
        }
        return metadata.getDescription().getTitle();
    }

    public final int getStopButtonVisibility() {
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        Objects.requireNonNull(mediaOutputController);
        LocalMediaManager localMediaManager = mediaOutputController.mLocalMediaManager;
        Objects.requireNonNull(localMediaManager);
        if (MediaOutputController.isActiveRemoteDevice(localMediaManager.mCurrentConnectedDevice)) {
            return 0;
        }
        return 8;
    }

    public MediaOutputDialog(Context context, boolean z, MediaOutputController mediaOutputController, UiEventLogger uiEventLogger, SystemUIDialogManager systemUIDialogManager) {
        super(context, mediaOutputController, systemUIDialogManager);
        this.mUiEventLogger = uiEventLogger;
        this.mAdapter = new MediaOutputAdapter(this.mMediaOutputController);
        if (!z) {
            getWindow().setType(2038);
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUiEventLogger.log(MediaOutputEvent.MEDIA_OUTPUT_DIALOG_SHOW);
    }
}
