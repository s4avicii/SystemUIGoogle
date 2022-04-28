package com.android.systemui.media;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import java.util.Collections;
import java.util.List;

/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$loadMediaDataInBgForResumption$1 implements Runnable {
    public final /* synthetic */ PendingIntent $appIntent;
    public final /* synthetic */ String $appName;
    public final /* synthetic */ Icon $artworkIcon;
    public final /* synthetic */ MediaDescription $desc;
    public final /* synthetic */ long $lastActive;
    public final /* synthetic */ MediaAction $mediaAction;
    public final /* synthetic */ String $packageName;
    public final /* synthetic */ Runnable $resumeAction;
    public final /* synthetic */ MediaSession.Token $token;
    public final /* synthetic */ int $userId;
    public final /* synthetic */ MediaDataManager this$0;

    public MediaDataManager$loadMediaDataInBgForResumption$1(MediaDataManager mediaDataManager, String str, int i, String str2, MediaDescription mediaDescription, Icon icon, MediaAction mediaAction, MediaSession.Token token, PendingIntent pendingIntent, Runnable runnable, long j) {
        this.this$0 = mediaDataManager;
        this.$packageName = str;
        this.$userId = i;
        this.$appName = str2;
        this.$desc = mediaDescription;
        this.$artworkIcon = icon;
        this.$mediaAction = mediaAction;
        this.$token = token;
        this.$appIntent = pendingIntent;
        this.$resumeAction = runnable;
        this.$lastActive = j;
    }

    public final void run() {
        MediaDataManager mediaDataManager = this.this$0;
        String str = this.$packageName;
        int i = this.$userId;
        int i2 = mediaDataManager.bgColor;
        String str2 = this.$appName;
        CharSequence subtitle = this.$desc.getSubtitle();
        CharSequence title = this.$desc.getTitle();
        Icon icon = this.$artworkIcon;
        List singletonList = Collections.singletonList(this.$mediaAction);
        List singletonList2 = Collections.singletonList(0);
        String str3 = this.$packageName;
        MediaDataManager mediaDataManager2 = mediaDataManager;
        MediaData mediaData = r3;
        MediaData mediaData2 = new MediaData(i, true, i2, str2, (Icon) null, subtitle, title, icon, singletonList, singletonList2, (MediaButton) null, str3, this.$token, this.$appIntent, (MediaDeviceData) null, false, this.$resumeAction, 0, true, str3, true, (Boolean) null, false, this.$lastActive, 6422528);
        mediaDataManager2.onMediaDataLoaded(str, (String) null, mediaData);
    }
}
