package com.android.systemui.media;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.util.Log;
import com.android.systemui.media.ResumeMediaBrowser;
import java.util.Iterator;
import java.util.Objects;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaResumeListener.kt */
public final class MediaResumeListener$userChangeReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ MediaResumeListener this$0;

    public MediaResumeListener$userChangeReceiver$1(MediaResumeListener mediaResumeListener) {
        this.this$0 = mediaResumeListener;
    }

    public final void onReceive(Context context, Intent intent) {
        if (Intrinsics.areEqual("android.intent.action.USER_UNLOCKED", intent.getAction())) {
            MediaResumeListener mediaResumeListener = this.this$0;
            Objects.requireNonNull(mediaResumeListener);
            if (mediaResumeListener.useMediaResumption) {
                long currentTimeMillis = mediaResumeListener.systemClock.currentTimeMillis();
                Iterator<Pair<ComponentName, Long>> it = mediaResumeListener.resumeComponents.iterator();
                while (it.hasNext()) {
                    Pair next = it.next();
                    if (currentTimeMillis - ((Number) next.getSecond()).longValue() <= MediaTimeoutListenerKt.RESUME_MEDIA_TIMEOUT) {
                        ResumeMediaBrowserFactory resumeMediaBrowserFactory = mediaResumeListener.mediaBrowserFactory;
                        Objects.requireNonNull(resumeMediaBrowserFactory);
                        ResumeMediaBrowser resumeMediaBrowser = new ResumeMediaBrowser(resumeMediaBrowserFactory.mContext, mediaResumeListener.mediaBrowserCallback, (ComponentName) next.getFirst(), resumeMediaBrowserFactory.mBrowserFactory);
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Connecting to ");
                        m.append(resumeMediaBrowser.mComponentName);
                        Log.d("ResumeMediaBrowser", m.toString());
                        resumeMediaBrowser.disconnect();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("android.service.media.extra.RECENT", true);
                        MediaBrowserFactory mediaBrowserFactory = resumeMediaBrowser.mBrowserFactory;
                        ComponentName componentName = resumeMediaBrowser.mComponentName;
                        ResumeMediaBrowser.C08922 r5 = resumeMediaBrowser.mConnectionCallback;
                        Objects.requireNonNull(mediaBrowserFactory);
                        MediaBrowser mediaBrowser = new MediaBrowser(mediaBrowserFactory.mContext, componentName, r5, bundle);
                        resumeMediaBrowser.mMediaBrowser = mediaBrowser;
                        mediaBrowser.connect();
                    }
                }
            }
        } else if (Intrinsics.areEqual("android.intent.action.USER_SWITCHED", intent.getAction())) {
            this.this$0.currentUserId = intent.getIntExtra("android.intent.extra.user_handle", -1);
            this.this$0.loadSavedComponents();
        }
    }
}
