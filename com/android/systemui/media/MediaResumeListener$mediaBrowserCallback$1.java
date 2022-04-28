package com.android.systemui.media;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.media.MediaDescription;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.util.Log;
import com.android.systemui.media.ResumeMediaBrowser;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaResumeListener.kt */
public final class MediaResumeListener$mediaBrowserCallback$1 extends ResumeMediaBrowser.Callback {
    public final /* synthetic */ MediaResumeListener this$0;

    public MediaResumeListener$mediaBrowserCallback$1(MediaResumeListener mediaResumeListener) {
        this.this$0 = mediaResumeListener;
    }

    public final void addTrack(MediaDescription mediaDescription, ComponentName componentName, ResumeMediaBrowser resumeMediaBrowser) {
        MediaSession.Token token;
        MediaDataManager mediaDataManager;
        ResumeMediaBrowser resumeMediaBrowser2 = resumeMediaBrowser;
        MediaBrowser mediaBrowser = resumeMediaBrowser2.mMediaBrowser;
        if (mediaBrowser == null || !mediaBrowser.isConnected()) {
            token = null;
        } else {
            token = resumeMediaBrowser2.mMediaBrowser.getSessionToken();
        }
        PendingIntent activity = PendingIntent.getActivity(resumeMediaBrowser2.mContext, 0, resumeMediaBrowser2.mContext.getPackageManager().getLaunchIntentForPackage(resumeMediaBrowser2.mComponentName.getPackageName()), 33554432);
        PackageManager packageManager = this.this$0.context.getPackageManager();
        Object packageName = componentName.getPackageName();
        MediaResumeListener mediaResumeListener = this.this$0;
        Objects.requireNonNull(mediaResumeListener);
        MediaResumeListener$getResumeAction$1 mediaResumeListener$getResumeAction$1 = new MediaResumeListener$getResumeAction$1(mediaResumeListener, componentName);
        try {
            packageName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(componentName.getPackageName(), 0));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("MediaResumeListener", "Error getting package information", e);
        }
        Log.d("MediaResumeListener", Intrinsics.stringPlus("Adding resume controls ", mediaDescription));
        MediaResumeListener mediaResumeListener2 = this.this$0;
        MediaDataManager mediaDataManager2 = mediaResumeListener2.mediaDataManager;
        if (mediaDataManager2 == null) {
            mediaDataManager = null;
        } else {
            mediaDataManager = mediaDataManager2;
        }
        int i = mediaResumeListener2.currentUserId;
        String obj = packageName.toString();
        String packageName2 = componentName.getPackageName();
        Objects.requireNonNull(mediaDataManager);
        if (!mediaDataManager.mediaEntries.containsKey(packageName2)) {
            mediaDataManager.mediaEntries.put(packageName2, MediaData.copy$default(MediaDataManagerKt.LOADING, (List) null, (List) null, packageName2, (MediaDeviceData) null, false, mediaResumeListener$getResumeAction$1, false, true, (Boolean) null, false, 15661055));
        }
        mediaDataManager.backgroundExecutor.execute(new MediaDataManager$addResumptionControls$1(mediaDataManager, i, mediaDescription, mediaResumeListener$getResumeAction$1, token, obj, activity, packageName2));
    }
}
