package com.android.systemui.media;

import android.content.ComponentName;
import android.content.pm.ResolveInfo;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.util.Log;
import com.android.systemui.media.ResumeMediaBrowser;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaResumeListener.kt */
public final class MediaResumeListener$onMediaDataLoaded$1 implements Runnable {
    public final /* synthetic */ List<ResolveInfo> $inf;
    public final /* synthetic */ String $key;
    public final /* synthetic */ MediaResumeListener this$0;

    public MediaResumeListener$onMediaDataLoaded$1(MediaResumeListener mediaResumeListener, String str, ArrayList arrayList) {
        this.this$0 = mediaResumeListener;
        this.$key = str;
        this.$inf = arrayList;
    }

    public final void run() {
        MediaResumeListener mediaResumeListener = this.this$0;
        String str = this.$key;
        List<ResolveInfo> list = this.$inf;
        Intrinsics.checkNotNull(list);
        ComponentName componentName = list.get(0).getComponentInfo().getComponentName();
        Objects.requireNonNull(mediaResumeListener);
        Log.d("MediaResumeListener", Intrinsics.stringPlus("Testing if we can connect to ", componentName));
        MediaDataManager mediaDataManager = mediaResumeListener.mediaDataManager;
        if (mediaDataManager == null) {
            mediaDataManager = null;
        }
        mediaDataManager.setResumeAction(str, (MediaResumeListener$getResumeAction$1) null);
        ResumeMediaBrowser resumeMediaBrowser = mediaResumeListener.mediaBrowser;
        if (resumeMediaBrowser != null) {
            resumeMediaBrowser.disconnect();
        }
        ResumeMediaBrowserFactory resumeMediaBrowserFactory = mediaResumeListener.mediaBrowserFactory;
        MediaResumeListener$tryUpdateResumptionList$1 mediaResumeListener$tryUpdateResumptionList$1 = new MediaResumeListener$tryUpdateResumptionList$1(componentName, mediaResumeListener, str);
        Objects.requireNonNull(resumeMediaBrowserFactory);
        ResumeMediaBrowser resumeMediaBrowser2 = new ResumeMediaBrowser(resumeMediaBrowserFactory.mContext, mediaResumeListener$tryUpdateResumptionList$1, componentName, resumeMediaBrowserFactory.mBrowserFactory);
        mediaResumeListener.mediaBrowser = resumeMediaBrowser2;
        resumeMediaBrowser2.disconnect();
        Bundle bundle = new Bundle();
        bundle.putBoolean("android.service.media.extra.RECENT", true);
        MediaBrowserFactory mediaBrowserFactory = resumeMediaBrowser2.mBrowserFactory;
        ComponentName componentName2 = resumeMediaBrowser2.mComponentName;
        ResumeMediaBrowser.C08922 r3 = resumeMediaBrowser2.mConnectionCallback;
        Objects.requireNonNull(mediaBrowserFactory);
        MediaBrowser mediaBrowser = new MediaBrowser(mediaBrowserFactory.mContext, componentName2, r3, bundle);
        resumeMediaBrowser2.mMediaBrowser = mediaBrowser;
        mediaBrowser.connect();
    }
}
