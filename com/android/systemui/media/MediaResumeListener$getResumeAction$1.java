package com.android.systemui.media;

import android.content.ComponentName;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.os.Bundle;
import android.util.Log;
import com.android.systemui.media.ResumeMediaBrowser;
import java.util.Objects;

/* compiled from: MediaResumeListener.kt */
public final class MediaResumeListener$getResumeAction$1 implements Runnable {
    public final /* synthetic */ ComponentName $componentName;
    public final /* synthetic */ MediaResumeListener this$0;

    public MediaResumeListener$getResumeAction$1(MediaResumeListener mediaResumeListener, ComponentName componentName) {
        this.this$0 = mediaResumeListener;
        this.$componentName = componentName;
    }

    public final void run() {
        MediaResumeListener mediaResumeListener = this.this$0;
        ResumeMediaBrowserFactory resumeMediaBrowserFactory = mediaResumeListener.mediaBrowserFactory;
        ComponentName componentName = this.$componentName;
        Objects.requireNonNull(resumeMediaBrowserFactory);
        mediaResumeListener.mediaBrowser = new ResumeMediaBrowser(resumeMediaBrowserFactory.mContext, (ResumeMediaBrowser.Callback) null, componentName, resumeMediaBrowserFactory.mBrowserFactory);
        ResumeMediaBrowser resumeMediaBrowser = this.this$0.mediaBrowser;
        if (resumeMediaBrowser != null) {
            resumeMediaBrowser.disconnect();
            Bundle bundle = new Bundle();
            bundle.putBoolean("android.service.media.extra.RECENT", true);
            MediaBrowserFactory mediaBrowserFactory = resumeMediaBrowser.mBrowserFactory;
            ComponentName componentName2 = resumeMediaBrowser.mComponentName;
            ResumeMediaBrowser.C08933 r3 = new MediaBrowser.ConnectionCallback() {
                public final void onConnected() {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Connected for restart ");
                    m.append(ResumeMediaBrowser.this.mMediaBrowser.isConnected());
                    Log.d("ResumeMediaBrowser", m.toString());
                    MediaBrowser mediaBrowser = ResumeMediaBrowser.this.mMediaBrowser;
                    if (mediaBrowser == null || !mediaBrowser.isConnected()) {
                        Callback callback = ResumeMediaBrowser.this.mCallback;
                        if (callback != null) {
                            callback.onError();
                        }
                        ResumeMediaBrowser.this.disconnect();
                        return;
                    }
                    MediaController createMediaController = ResumeMediaBrowser.this.createMediaController(ResumeMediaBrowser.this.mMediaBrowser.getSessionToken());
                    createMediaController.getTransportControls();
                    createMediaController.getTransportControls().prepare();
                    createMediaController.getTransportControls().play();
                    Callback callback2 = ResumeMediaBrowser.this.mCallback;
                    if (callback2 != null) {
                        callback2.onConnected();
                    }
                }

                public final void onConnectionFailed() {
                    Callback callback = ResumeMediaBrowser.this.mCallback;
                    if (callback != null) {
                        callback.onError();
                    }
                    ResumeMediaBrowser.this.disconnect();
                }

                public final void onConnectionSuspended() {
                    Callback callback = ResumeMediaBrowser.this.mCallback;
                    if (callback != null) {
                        callback.onError();
                    }
                    ResumeMediaBrowser.this.disconnect();
                }
            };
            Objects.requireNonNull(mediaBrowserFactory);
            MediaBrowser mediaBrowser = new MediaBrowser(mediaBrowserFactory.mContext, componentName2, r3, bundle);
            resumeMediaBrowser.mMediaBrowser = mediaBrowser;
            mediaBrowser.connect();
        }
    }
}
