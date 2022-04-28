package com.android.systemui.media;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.util.Log;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.LinkedHashMap;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaTimeoutListener.kt */
public final class MediaTimeoutListener implements MediaDataManager.Listener {
    public final DelayableExecutor mainExecutor;
    public final MediaControllerFactory mediaControllerFactory;
    public final LinkedHashMap mediaListeners = new LinkedHashMap();
    public Function2<? super String, ? super Boolean, Unit> timeoutCallback;

    /* compiled from: MediaTimeoutListener.kt */
    public final class PlaybackStateListener extends MediaController.Callback {
        public Runnable cancellation;
        public boolean destroyed;
        public String key;
        public MediaController mediaController;
        public MediaData mediaData;
        public Boolean playing;
        public Boolean resumption;
        public boolean timedOut;

        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            processState(playbackState, true);
        }

        public final void setMediaData(MediaData mediaData2) {
            MediaController mediaController2;
            this.destroyed = false;
            MediaController mediaController3 = this.mediaController;
            if (mediaController3 != null) {
                mediaController3.unregisterCallback(this);
            }
            this.mediaData = mediaData2;
            Objects.requireNonNull(mediaData2);
            PlaybackState playbackState = null;
            if (mediaData2.token != null) {
                MediaControllerFactory mediaControllerFactory = MediaTimeoutListener.this.mediaControllerFactory;
                MediaData mediaData3 = this.mediaData;
                Objects.requireNonNull(mediaData3);
                MediaSession.Token token = mediaData3.token;
                Objects.requireNonNull(mediaControllerFactory);
                mediaController2 = new MediaController(mediaControllerFactory.mContext, token);
            } else {
                mediaController2 = null;
            }
            this.mediaController = mediaController2;
            if (mediaController2 != null) {
                mediaController2.registerCallback(this);
            }
            MediaController mediaController4 = this.mediaController;
            if (mediaController4 != null) {
                playbackState = mediaController4.getPlaybackState();
            }
            processState(playbackState, false);
        }

        public PlaybackStateListener(String str, MediaData mediaData2) {
            this.key = str;
            this.mediaData = mediaData2;
            setMediaData(mediaData2);
        }

        public final void expireMediaTimeout(String str, String str2) {
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                Log.v("MediaTimeout", "media timeout cancelled for  " + str + ", reason: " + str2);
                runnable.run();
            }
            this.cancellation = null;
        }

        public final void onSessionDestroyed() {
            Log.d("MediaTimeout", Intrinsics.stringPlus("Session destroyed for ", this.key));
            if (Intrinsics.areEqual(this.resumption, Boolean.TRUE)) {
                MediaController mediaController2 = this.mediaController;
                if (mediaController2 != null) {
                    mediaController2.unregisterCallback(this);
                    return;
                }
                return;
            }
            MediaController mediaController3 = this.mediaController;
            if (mediaController3 != null) {
                mediaController3.unregisterCallback(this);
            }
            Runnable runnable = this.cancellation;
            if (runnable != null) {
                runnable.run();
            }
            this.destroyed = true;
        }

        public final void processState(PlaybackState playbackState, boolean z) {
            boolean z2;
            long j;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("processState ");
            m.append(this.key);
            m.append(": ");
            m.append(playbackState);
            Log.v("MediaTimeout", m.toString());
            if (playbackState == null || !NotificationMediaManager.isPlayingState(playbackState.getState())) {
                z2 = false;
            } else {
                z2 = true;
            }
            Boolean bool = this.resumption;
            MediaData mediaData2 = this.mediaData;
            Objects.requireNonNull(mediaData2);
            boolean areEqual = true ^ Intrinsics.areEqual(bool, Boolean.valueOf(mediaData2.resumption));
            if (!Intrinsics.areEqual(this.playing, Boolean.valueOf(z2)) || this.playing == null || areEqual) {
                this.playing = Boolean.valueOf(z2);
                MediaData mediaData3 = this.mediaData;
                Objects.requireNonNull(mediaData3);
                this.resumption = Boolean.valueOf(mediaData3.resumption);
                if (!z2) {
                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("schedule timeout for ");
                    m2.append(this.key);
                    m2.append(" playing ");
                    m2.append(z2);
                    m2.append(", ");
                    m2.append(this.resumption);
                    Log.v("MediaTimeout", m2.toString());
                    if (this.cancellation == null || areEqual) {
                        expireMediaTimeout(this.key, "PLAYBACK STATE CHANGED - " + playbackState + ", " + this.resumption);
                        MediaData mediaData4 = this.mediaData;
                        Objects.requireNonNull(mediaData4);
                        if (mediaData4.resumption) {
                            j = MediaTimeoutListenerKt.RESUME_MEDIA_TIMEOUT;
                        } else {
                            j = MediaTimeoutListenerKt.PAUSED_MEDIA_TIMEOUT;
                        }
                        MediaTimeoutListener mediaTimeoutListener = MediaTimeoutListener.this;
                        this.cancellation = mediaTimeoutListener.mainExecutor.executeDelayed(new MediaTimeoutListener$PlaybackStateListener$processState$1(this, mediaTimeoutListener), j);
                        return;
                    }
                    Log.d("MediaTimeout", "cancellation already exists, continuing.");
                    return;
                }
                expireMediaTimeout(this.key, "playback started - " + playbackState + ", " + this.key);
                this.timedOut = false;
                if (z) {
                    MediaTimeoutListener mediaTimeoutListener2 = MediaTimeoutListener.this;
                    Objects.requireNonNull(mediaTimeoutListener2);
                    Function2<? super String, ? super Boolean, Unit> function2 = mediaTimeoutListener2.timeoutCallback;
                    if (function2 == null) {
                        function2 = null;
                    }
                    function2.invoke(this.key, Boolean.valueOf(this.timedOut));
                }
            }
        }
    }

    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z, boolean z2) {
    }

    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
    }

    /* JADX WARNING: Failed to insert additional move for type inference */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMediaDataLoaded(java.lang.String r4, java.lang.String r5, com.android.systemui.media.MediaData r6, boolean r7, int r8) {
        /*
            r3 = this;
            java.util.LinkedHashMap r7 = r3.mediaListeners
            java.lang.Object r7 = r7.get(r4)
            com.android.systemui.media.MediaTimeoutListener$PlaybackStateListener r7 = (com.android.systemui.media.MediaTimeoutListener.PlaybackStateListener) r7
            java.lang.String r8 = "MediaTimeout"
            if (r7 != 0) goto L_0x000e
            r7 = 0
            goto L_0x001c
        L_0x000e:
            boolean r0 = r7.destroyed
            if (r0 != 0) goto L_0x0013
            return
        L_0x0013:
            java.lang.String r0 = "Reusing destroyed listener "
            java.lang.String r0 = kotlin.jvm.internal.Intrinsics.stringPlus(r0, r4)
            android.util.Log.d(r8, r0)
        L_0x001c:
            r0 = 0
            if (r5 == 0) goto L_0x0027
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
            if (r1 != 0) goto L_0x0027
            r1 = 1
            goto L_0x0028
        L_0x0027:
            r1 = r0
        L_0x0028:
            if (r1 == 0) goto L_0x0075
            java.util.LinkedHashMap r7 = r3.mediaListeners
            java.lang.Object r7 = r7.remove(r5)
            if (r7 == 0) goto L_0x0054
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "migrating key "
            r1.append(r2)
            r1.append(r5)
            java.lang.String r5 = " to "
            r1.append(r5)
            r1.append(r4)
            java.lang.String r5 = ", for resumption"
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            android.util.Log.d(r8, r5)
            goto L_0x0075
        L_0x0054:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Old key "
            r1.append(r2)
            r1.append(r5)
            java.lang.String r5 = " for player "
            r1.append(r5)
            r1.append(r4)
            java.lang.String r5 = " doesn't exist. Continuing..."
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            android.util.Log.w(r8, r5)
        L_0x0075:
            com.android.systemui.media.MediaTimeoutListener$PlaybackStateListener r7 = (com.android.systemui.media.MediaTimeoutListener.PlaybackStateListener) r7
            if (r7 != 0) goto L_0x0084
            java.util.LinkedHashMap r5 = r3.mediaListeners
            com.android.systemui.media.MediaTimeoutListener$PlaybackStateListener r7 = new com.android.systemui.media.MediaTimeoutListener$PlaybackStateListener
            r7.<init>(r4, r6)
            r5.put(r4, r7)
            return
        L_0x0084:
            java.lang.Boolean r5 = r7.playing
            if (r5 != 0) goto L_0x0089
            goto L_0x008d
        L_0x0089:
            boolean r0 = r5.booleanValue()
        L_0x008d:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r1 = "updating listener for "
            r5.append(r1)
            r5.append(r4)
            java.lang.String r1 = ", was playing? "
            r5.append(r1)
            r5.append(r0)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r8, r5)
            r7.setMediaData(r6)
            r7.key = r4
            java.util.LinkedHashMap r5 = r3.mediaListeners
            r5.put(r4, r7)
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r0)
            java.lang.Boolean r6 = r7.playing
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
            if (r5 != 0) goto L_0x00ca
            com.android.systemui.util.concurrency.DelayableExecutor r5 = r3.mainExecutor
            com.android.systemui.media.MediaTimeoutListener$onMediaDataLoaded$2$1 r6 = new com.android.systemui.media.MediaTimeoutListener$onMediaDataLoaded$2$1
            r6.<init>(r3, r4)
            r5.execute(r6)
        L_0x00ca:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaTimeoutListener.onMediaDataLoaded(java.lang.String, java.lang.String, com.android.systemui.media.MediaData, boolean, int):void");
    }

    public final void onMediaDataRemoved(String str) {
        PlaybackStateListener playbackStateListener = (PlaybackStateListener) this.mediaListeners.remove(str);
        if (playbackStateListener != null) {
            MediaController mediaController = playbackStateListener.mediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(playbackStateListener);
            }
            Runnable runnable = playbackStateListener.cancellation;
            if (runnable != null) {
                runnable.run();
            }
            playbackStateListener.destroyed = true;
        }
    }

    public MediaTimeoutListener(MediaControllerFactory mediaControllerFactory2, DelayableExecutor delayableExecutor) {
        this.mediaControllerFactory = mediaControllerFactory2;
        this.mainExecutor = delayableExecutor;
    }
}
