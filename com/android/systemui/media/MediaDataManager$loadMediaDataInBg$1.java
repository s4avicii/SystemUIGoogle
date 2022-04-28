package com.android.systemui.media;

import android.app.Notification;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import android.service.notification.StatusBarNotification;
import java.util.List;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$loadMediaDataInBg$1 implements Runnable {
    public final /* synthetic */ Ref$ObjectRef<List<MediaAction>> $actionIcons;
    public final /* synthetic */ Ref$ObjectRef<List<Integer>> $actionsToShowCollapsed;
    public final /* synthetic */ String $app;
    public final /* synthetic */ Icon $artWorkIcon;
    public final /* synthetic */ Ref$ObjectRef<CharSequence> $artist;
    public final /* synthetic */ Ref$ObjectRef<MediaDeviceData> $device;
    public final /* synthetic */ Boolean $isPlaying;
    public final /* synthetic */ String $key;
    public final /* synthetic */ long $lastActive;
    public final /* synthetic */ Notification $notif;
    public final /* synthetic */ String $oldKey;
    public final /* synthetic */ int $playbackLocation;
    public final /* synthetic */ StatusBarNotification $sbn;
    public final /* synthetic */ Ref$ObjectRef<MediaButton> $semanticActions;
    public final /* synthetic */ Icon $smallIcon;
    public final /* synthetic */ Ref$ObjectRef<CharSequence> $song;
    public final /* synthetic */ MediaSession.Token $token;
    public final /* synthetic */ MediaDataManager this$0;

    public MediaDataManager$loadMediaDataInBg$1(MediaDataManager mediaDataManager, String str, String str2, StatusBarNotification statusBarNotification, String str3, Icon icon, Ref$ObjectRef<CharSequence> ref$ObjectRef, Ref$ObjectRef<CharSequence> ref$ObjectRef2, Icon icon2, Ref$ObjectRef<List<MediaAction>> ref$ObjectRef3, Ref$ObjectRef<List<Integer>> ref$ObjectRef4, Ref$ObjectRef<MediaButton> ref$ObjectRef5, MediaSession.Token token, Notification notification, Ref$ObjectRef<MediaDeviceData> ref$ObjectRef6, int i, Boolean bool, long j) {
        this.this$0 = mediaDataManager;
        this.$key = str;
        this.$oldKey = str2;
        this.$sbn = statusBarNotification;
        this.$app = str3;
        this.$smallIcon = icon;
        this.$artist = ref$ObjectRef;
        this.$song = ref$ObjectRef2;
        this.$artWorkIcon = icon2;
        this.$actionIcons = ref$ObjectRef3;
        this.$actionsToShowCollapsed = ref$ObjectRef4;
        this.$semanticActions = ref$ObjectRef5;
        this.$token = token;
        this.$notif = notification;
        this.$device = ref$ObjectRef6;
        this.$playbackLocation = i;
        this.$isPlaying = bool;
        this.$lastActive = j;
    }

    public final void run() {
        Runnable runnable;
        boolean z;
        boolean z2;
        MediaData mediaData = this.this$0.mediaEntries.get(this.$key);
        if (mediaData == null) {
            runnable = null;
        } else {
            runnable = mediaData.resumeAction;
        }
        Runnable runnable2 = runnable;
        MediaData mediaData2 = this.this$0.mediaEntries.get(this.$key);
        if (mediaData2 != null && mediaData2.hasCheckedForResume) {
            z = true;
        } else {
            z = false;
        }
        MediaData mediaData3 = this.this$0.mediaEntries.get(this.$key);
        if (mediaData3 == null) {
            z2 = true;
        } else {
            z2 = mediaData3.active;
        }
        MediaDataManager mediaDataManager = this.this$0;
        String str = this.$key;
        MediaData mediaData4 = r2;
        MediaDataManager mediaDataManager2 = mediaDataManager;
        MediaData mediaData5 = new MediaData(this.$sbn.getNormalizedUserId(), true, this.this$0.bgColor, this.$app, this.$smallIcon, (CharSequence) this.$artist.element, (CharSequence) this.$song.element, this.$artWorkIcon, (List) this.$actionIcons.element, (List) this.$actionsToShowCollapsed.element, (MediaButton) this.$semanticActions.element, this.$sbn.getPackageName(), this.$token, this.$notif.contentIntent, (MediaDeviceData) this.$device.element, z2, runnable2, this.$playbackLocation, false, this.$key, z, this.$isPlaying, this.$sbn.isClearable(), this.$lastActive, 262144);
        MediaDataManager mediaDataManager3 = mediaDataManager2;
        mediaDataManager3.onMediaDataLoaded(str, this.$oldKey, mediaData4);
    }
}
