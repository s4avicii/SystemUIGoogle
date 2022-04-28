package com.android.systemui.media;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$addResumptionControls$1 implements Runnable {
    public final /* synthetic */ Runnable $action;
    public final /* synthetic */ PendingIntent $appIntent;
    public final /* synthetic */ String $appName;
    public final /* synthetic */ MediaDescription $desc;
    public final /* synthetic */ String $packageName;
    public final /* synthetic */ MediaSession.Token $token;
    public final /* synthetic */ int $userId;
    public final /* synthetic */ MediaDataManager this$0;

    public MediaDataManager$addResumptionControls$1(MediaDataManager mediaDataManager, int i, MediaDescription mediaDescription, MediaResumeListener$getResumeAction$1 mediaResumeListener$getResumeAction$1, MediaSession.Token token, String str, PendingIntent pendingIntent, String str2) {
        this.this$0 = mediaDataManager;
        this.$userId = i;
        this.$desc = mediaDescription;
        this.$action = mediaResumeListener$getResumeAction$1;
        this.$token = token;
        this.$appName = str;
        this.$appIntent = pendingIntent;
        this.$packageName = str2;
    }

    public final void run() {
        Icon icon;
        MediaDataManager mediaDataManager = this.this$0;
        int i = this.$userId;
        MediaDescription mediaDescription = this.$desc;
        Runnable runnable = this.$action;
        MediaSession.Token token = this.$token;
        String str = this.$appName;
        PendingIntent pendingIntent = this.$appIntent;
        String str2 = this.$packageName;
        Objects.requireNonNull(mediaDataManager);
        if (TextUtils.isEmpty(mediaDescription.getTitle())) {
            Log.e("MediaDataManager", "Description incomplete");
            mediaDataManager.mediaEntries.remove(str2);
            return;
        }
        Log.d("MediaDataManager", "adding track for " + i + " from browser: " + mediaDescription);
        Bitmap iconBitmap = mediaDescription.getIconBitmap();
        if (iconBitmap == null && mediaDescription.getIconUri() != null) {
            Uri iconUri = mediaDescription.getIconUri();
            Intrinsics.checkNotNull(iconUri);
            iconBitmap = mediaDataManager.loadBitmapFromUri(iconUri);
        }
        if (iconBitmap != null) {
            icon = Icon.createWithBitmap(iconBitmap);
        } else {
            icon = null;
        }
        mediaDataManager.foregroundExecutor.execute(new MediaDataManager$loadMediaDataInBgForResumption$1(mediaDataManager, str2, i, str, mediaDescription, icon, mediaDataManager.getResumeMediaAction(runnable), token, pendingIntent, runnable, mediaDataManager.systemClock.elapsedRealtime()));
    }
}
