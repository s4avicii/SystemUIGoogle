package com.android.systemui.media;

import android.content.ComponentName;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.media.MediaDescription;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;

public final class ResumeMediaBrowser {
    public MediaBrowserFactory mBrowserFactory;
    public final Callback mCallback;
    public ComponentName mComponentName;
    public final C08922 mConnectionCallback = new MediaBrowser.ConnectionCallback() {
        public final void onConnected() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Service connected for ");
            m.append(ResumeMediaBrowser.this.mComponentName);
            Log.d("ResumeMediaBrowser", m.toString());
            MediaBrowser mediaBrowser = ResumeMediaBrowser.this.mMediaBrowser;
            if (mediaBrowser != null && mediaBrowser.isConnected()) {
                String root = ResumeMediaBrowser.this.mMediaBrowser.getRoot();
                if (!TextUtils.isEmpty(root)) {
                    Callback callback = ResumeMediaBrowser.this.mCallback;
                    if (callback != null) {
                        callback.onConnected();
                    }
                    ResumeMediaBrowser resumeMediaBrowser = ResumeMediaBrowser.this;
                    MediaBrowser mediaBrowser2 = resumeMediaBrowser.mMediaBrowser;
                    if (mediaBrowser2 != null) {
                        mediaBrowser2.subscribe(root, resumeMediaBrowser.mSubscriptionCallback);
                        return;
                    }
                    return;
                }
            }
            Callback callback2 = ResumeMediaBrowser.this.mCallback;
            if (callback2 != null) {
                callback2.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }

        public final void onConnectionFailed() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Connection failed for ");
            m.append(ResumeMediaBrowser.this.mComponentName);
            Log.d("ResumeMediaBrowser", m.toString());
            Callback callback = ResumeMediaBrowser.this.mCallback;
            if (callback != null) {
                callback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }

        public final void onConnectionSuspended() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Connection suspended for ");
            m.append(ResumeMediaBrowser.this.mComponentName);
            Log.d("ResumeMediaBrowser", m.toString());
            Callback callback = ResumeMediaBrowser.this.mCallback;
            if (callback != null) {
                callback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }
    };
    public final Context mContext;
    public MediaBrowser mMediaBrowser;
    public final C08911 mSubscriptionCallback = new MediaBrowser.SubscriptionCallback() {
        public final void onError(String str) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Subscribe error for ");
            m.append(ResumeMediaBrowser.this.mComponentName);
            m.append(": ");
            m.append(str);
            Log.d("ResumeMediaBrowser", m.toString());
            Callback callback = ResumeMediaBrowser.this.mCallback;
            if (callback != null) {
                callback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:7:0x0037, code lost:
            r3 = r2.this$0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onChildrenLoaded(java.lang.String r3, java.util.List<android.media.browse.MediaBrowser.MediaItem> r4) {
            /*
                r2 = this;
                int r3 = r4.size()
                java.lang.String r0 = "ResumeMediaBrowser"
                if (r3 != 0) goto L_0x0026
                java.lang.String r3 = "No children found for "
                java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r3)
                com.android.systemui.media.ResumeMediaBrowser r4 = com.android.systemui.media.ResumeMediaBrowser.this
                android.content.ComponentName r4 = r4.mComponentName
                r3.append(r4)
                java.lang.String r3 = r3.toString()
                android.util.Log.d(r0, r3)
                com.android.systemui.media.ResumeMediaBrowser r3 = com.android.systemui.media.ResumeMediaBrowser.this
                com.android.systemui.media.ResumeMediaBrowser$Callback r3 = r3.mCallback
                if (r3 == 0) goto L_0x0068
                r3.onError()
                goto L_0x0068
            L_0x0026:
                r3 = 0
                java.lang.Object r3 = r4.get(r3)
                android.media.browse.MediaBrowser$MediaItem r3 = (android.media.browse.MediaBrowser.MediaItem) r3
                android.media.MediaDescription r4 = r3.getDescription()
                boolean r3 = r3.isPlayable()
                if (r3 == 0) goto L_0x004b
                com.android.systemui.media.ResumeMediaBrowser r3 = com.android.systemui.media.ResumeMediaBrowser.this
                android.media.browse.MediaBrowser r1 = r3.mMediaBrowser
                if (r1 == 0) goto L_0x004b
                com.android.systemui.media.ResumeMediaBrowser$Callback r3 = r3.mCallback
                if (r3 == 0) goto L_0x0068
                android.content.ComponentName r0 = r1.getServiceComponent()
                com.android.systemui.media.ResumeMediaBrowser r1 = com.android.systemui.media.ResumeMediaBrowser.this
                r3.addTrack(r4, r0, r1)
                goto L_0x0068
            L_0x004b:
                java.lang.String r3 = "Child found but not playable for "
                java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r3)
                com.android.systemui.media.ResumeMediaBrowser r4 = com.android.systemui.media.ResumeMediaBrowser.this
                android.content.ComponentName r4 = r4.mComponentName
                r3.append(r4)
                java.lang.String r3 = r3.toString()
                android.util.Log.d(r0, r3)
                com.android.systemui.media.ResumeMediaBrowser r3 = com.android.systemui.media.ResumeMediaBrowser.this
                com.android.systemui.media.ResumeMediaBrowser$Callback r3 = r3.mCallback
                if (r3 == 0) goto L_0x0068
                r3.onError()
            L_0x0068:
                com.android.systemui.media.ResumeMediaBrowser r2 = com.android.systemui.media.ResumeMediaBrowser.this
                r2.disconnect()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.ResumeMediaBrowser.C08911.onChildrenLoaded(java.lang.String, java.util.List):void");
        }

        public final void onError(String str, Bundle bundle) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Subscribe error for ");
            m.append(ResumeMediaBrowser.this.mComponentName);
            m.append(": ");
            m.append(str);
            m.append(", options: ");
            m.append(bundle);
            Log.d("ResumeMediaBrowser", m.toString());
            Callback callback = ResumeMediaBrowser.this.mCallback;
            if (callback != null) {
                callback.onError();
            }
            ResumeMediaBrowser.this.disconnect();
        }
    };

    public static class Callback {
        public void addTrack(MediaDescription mediaDescription, ComponentName componentName, ResumeMediaBrowser resumeMediaBrowser) {
            throw null;
        }

        public void onConnected() {
        }

        public void onError() {
        }
    }

    @VisibleForTesting
    public MediaController createMediaController(MediaSession.Token token) {
        return new MediaController(this.mContext, token);
    }

    public final void disconnect() {
        MediaBrowser mediaBrowser = this.mMediaBrowser;
        if (mediaBrowser != null) {
            mediaBrowser.disconnect();
        }
        this.mMediaBrowser = null;
    }

    public ResumeMediaBrowser(Context context, Callback callback, ComponentName componentName, MediaBrowserFactory mediaBrowserFactory) {
        this.mContext = context;
        this.mCallback = callback;
        this.mComponentName = componentName;
        this.mBrowserFactory = mediaBrowserFactory;
    }
}
