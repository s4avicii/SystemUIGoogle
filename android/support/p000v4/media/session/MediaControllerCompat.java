package android.support.p000v4.media.session;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.p000v4.media.MediaDescriptionCompat;
import android.support.p000v4.media.MediaMetadataCompat;
import android.support.p000v4.media.session.IMediaControllerCallback;
import android.support.p000v4.media.session.MediaSessionCompat;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* renamed from: android.support.v4.media.session.MediaControllerCompat */
public final class MediaControllerCompat {
    public final MediaControllerImplApi21 mImpl;

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$Callback */
    public static abstract class Callback implements IBinder.DeathRecipient {
        public final MediaControllerCallbackApi21 mCallbackFwk = new MediaControllerCallbackApi21(this);
        public MessageHandler mHandler;
        public MediaControllerImplApi21.ExtraCallback mIControllerCallback;

        /* renamed from: android.support.v4.media.session.MediaControllerCompat$Callback$MediaControllerCallbackApi21 */
        public static class MediaControllerCallbackApi21 extends MediaController.Callback {
            public final WeakReference<Callback> mCallback;

            public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
                if (this.mCallback.get() != null) {
                    playbackInfo.getPlaybackType();
                    playbackInfo.getAudioAttributes();
                    playbackInfo.getVolumeControl();
                    playbackInfo.getMaxVolume();
                    playbackInfo.getCurrentVolume();
                }
            }

            public final void onMetadataChanged(MediaMetadata mediaMetadata) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(mediaMetadata));
                }
            }

            public final void onPlaybackStateChanged(PlaybackState playbackState) {
                Callback callback = this.mCallback.get();
                if (callback != null && callback.mIControllerCallback == null) {
                    callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(playbackState));
                }
            }

            public final void onQueueChanged(List<MediaSession.QueueItem> list) {
                MediaSessionCompat.QueueItem queueItem;
                if (this.mCallback.get() != null && list != null) {
                    ArrayList arrayList = new ArrayList();
                    for (MediaSession.QueueItem next : list) {
                        if (next != null) {
                            MediaSession.QueueItem queueItem2 = next;
                            queueItem = new MediaSessionCompat.QueueItem(MediaDescriptionCompat.fromMediaDescription(queueItem2.getDescription()), queueItem2.getQueueId());
                        } else {
                            queueItem = null;
                        }
                        arrayList.add(queueItem);
                    }
                }
            }

            public final void onQueueTitleChanged(CharSequence charSequence) {
                Callback callback = this.mCallback.get();
            }

            public final void onSessionDestroyed() {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onSessionDestroyed();
                }
            }

            public MediaControllerCallbackApi21(Callback callback) {
                this.mCallback = new WeakReference<>(callback);
            }

            public final void onExtrasChanged(Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                Callback callback = this.mCallback.get();
            }

            public final void onSessionEvent(String str, Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                Callback callback = this.mCallback.get();
            }
        }

        /* renamed from: android.support.v4.media.session.MediaControllerCompat$Callback$MessageHandler */
        public class MessageHandler extends Handler {
        }

        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
        }

        public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
        }

        public void onSessionDestroyed() {
        }

        /* renamed from: android.support.v4.media.session.MediaControllerCompat$Callback$StubCompat */
        public static class StubCompat extends IMediaControllerCallback.Stub {
            public final WeakReference<Callback> mCallback;

            public StubCompat(Callback callback) {
                this.mCallback = new WeakReference<>(callback);
            }
        }

        public final void binderDied() {
            postToHandler(8, (Object) null, (Bundle) null);
        }

        public final void postToHandler(int i, Object obj, Bundle bundle) {
            MessageHandler messageHandler = this.mHandler;
            if (messageHandler != null) {
                Message obtainMessage = messageHandler.obtainMessage(i, obj);
                obtainMessage.setData(bundle);
                obtainMessage.sendToTarget();
            }
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21 */
    public static class MediaControllerImplApi21 {
        public HashMap<Callback, ExtraCallback> mCallbackMap;
        public final MediaController mControllerFwk;
        public final Object mLock;
        public final ArrayList mPendingCallbacks;
        public final MediaSessionCompat.Token mSessionToken;

        /* renamed from: android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver */
        public static class ExtraBinderRequestResultReceiver extends ResultReceiver {
            public WeakReference<MediaControllerImplApi21> mMediaControllerImpl;

            /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
                java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
                	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
                	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
                	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
                	at java.base/java.util.Objects.checkIndex(Objects.java:372)
                	at java.base/java.util.ArrayList.get(ArrayList.java:458)
                	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
                	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
                	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
                	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
                	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
                	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
                	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
                	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
                	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
                	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
                	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
                */
            public final void onReceiveResult(int r6, android.os.Bundle r7) {
                /*
                    r5 = this;
                    java.lang.ref.WeakReference<android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21> r5 = r5.mMediaControllerImpl
                    java.lang.Object r5 = r5.get()
                    android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21 r5 = (android.support.p000v4.media.session.MediaControllerCompat.MediaControllerImplApi21) r5
                    if (r5 == 0) goto L_0x0084
                    if (r7 != 0) goto L_0x000e
                    goto L_0x0084
                L_0x000e:
                    java.lang.Object r6 = r5.mLock
                    monitor-enter(r6)
                    android.support.v4.media.session.MediaSessionCompat$Token r0 = r5.mSessionToken     // Catch:{ all -> 0x0081 }
                    java.lang.String r1 = "android.support.v4.media.session.EXTRA_BINDER"
                    android.os.IBinder r1 = r7.getBinder(r1)     // Catch:{ all -> 0x0081 }
                    int r2 = android.support.p000v4.media.session.IMediaSession.Stub.$r8$clinit     // Catch:{ all -> 0x0081 }
                    r2 = 0
                    if (r1 != 0) goto L_0x0020
                    r3 = r2
                    goto L_0x0034
                L_0x0020:
                    java.lang.String r3 = "android.support.v4.media.session.IMediaSession"
                    android.os.IInterface r3 = r1.queryLocalInterface(r3)     // Catch:{ all -> 0x0081 }
                    if (r3 == 0) goto L_0x002f
                    boolean r4 = r3 instanceof android.support.p000v4.media.session.IMediaSession     // Catch:{ all -> 0x0081 }
                    if (r4 == 0) goto L_0x002f
                    android.support.v4.media.session.IMediaSession r3 = (android.support.p000v4.media.session.IMediaSession) r3     // Catch:{ all -> 0x0081 }
                    goto L_0x0034
                L_0x002f:
                    android.support.v4.media.session.IMediaSession$Stub$Proxy r3 = new android.support.v4.media.session.IMediaSession$Stub$Proxy     // Catch:{ all -> 0x0081 }
                    r3.<init>(r1)     // Catch:{ all -> 0x0081 }
                L_0x0034:
                    java.util.Objects.requireNonNull(r0)     // Catch:{ all -> 0x0081 }
                    java.lang.Object r1 = r0.mLock     // Catch:{ all -> 0x0081 }
                    monitor-enter(r1)     // Catch:{ all -> 0x0081 }
                    r0.mExtraBinder = r3     // Catch:{ all -> 0x007e }
                    monitor-exit(r1)     // Catch:{ all -> 0x007e }
                    android.support.v4.media.session.MediaSessionCompat$Token r0 = r5.mSessionToken     // Catch:{ all -> 0x0081 }
                    java.lang.String r1 = "android.support.v4.media.session.SESSION_TOKEN2"
                    android.os.Parcelable r7 = r7.getParcelable(r1)     // Catch:{ RuntimeException -> 0x006d }
                    android.os.Bundle r7 = (android.os.Bundle) r7     // Catch:{ RuntimeException -> 0x006d }
                    if (r7 != 0) goto L_0x004a
                    goto L_0x006d
                L_0x004a:
                    java.lang.Class<androidx.versionedparcelable.ParcelUtils> r1 = androidx.versionedparcelable.ParcelUtils.class
                    java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch:{ RuntimeException -> 0x006d }
                    r7.setClassLoader(r1)     // Catch:{ RuntimeException -> 0x006d }
                    java.lang.String r1 = "a"
                    android.os.Parcelable r7 = r7.getParcelable(r1)     // Catch:{ RuntimeException -> 0x006d }
                    boolean r1 = r7 instanceof androidx.versionedparcelable.ParcelImpl     // Catch:{ RuntimeException -> 0x006d }
                    if (r1 == 0) goto L_0x0065
                    androidx.versionedparcelable.ParcelImpl r7 = (androidx.versionedparcelable.ParcelImpl) r7     // Catch:{ RuntimeException -> 0x006d }
                    java.util.Objects.requireNonNull(r7)     // Catch:{ RuntimeException -> 0x006d }
                    androidx.versionedparcelable.VersionedParcelable r2 = r7.mParcel     // Catch:{ RuntimeException -> 0x006d }
                    goto L_0x006d
                L_0x0065:
                    java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ RuntimeException -> 0x006d }
                    java.lang.String r1 = "Invalid parcel"
                    r7.<init>(r1)     // Catch:{ RuntimeException -> 0x006d }
                    throw r7     // Catch:{ RuntimeException -> 0x006d }
                L_0x006d:
                    java.util.Objects.requireNonNull(r0)     // Catch:{ all -> 0x0081 }
                    java.lang.Object r7 = r0.mLock     // Catch:{ all -> 0x0081 }
                    monitor-enter(r7)     // Catch:{ all -> 0x0081 }
                    r0.mSession2Token = r2     // Catch:{ all -> 0x007b }
                    monitor-exit(r7)     // Catch:{ all -> 0x007b }
                    r5.processPendingCallbacksLocked()     // Catch:{ all -> 0x0081 }
                    monitor-exit(r6)     // Catch:{ all -> 0x0081 }
                    return
                L_0x007b:
                    r5 = move-exception
                    monitor-exit(r7)     // Catch:{ all -> 0x007b }
                    throw r5     // Catch:{ all -> 0x0081 }
                L_0x007e:
                    r5 = move-exception
                    monitor-exit(r1)     // Catch:{ all -> 0x007e }
                    throw r5     // Catch:{ all -> 0x0081 }
                L_0x0081:
                    r5 = move-exception
                    monitor-exit(r6)     // Catch:{ all -> 0x0081 }
                    throw r5
                L_0x0084:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.media.session.MediaControllerCompat.MediaControllerImplApi21.ExtraBinderRequestResultReceiver.onReceiveResult(int, android.os.Bundle):void");
            }
        }

        /* renamed from: android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraCallback */
        public static class ExtraCallback extends Callback.StubCompat {
            public final void onExtrasChanged(Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            public final void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
                throw new AssertionError();
            }

            public final void onQueueChanged(ArrayList arrayList) throws RemoteException {
                throw new AssertionError();
            }

            public final void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                throw new AssertionError();
            }

            public final void onSessionDestroyed() throws RemoteException {
                throw new AssertionError();
            }

            public final void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
                throw new AssertionError();
            }

            public ExtraCallback(Callback callback) {
                super(callback);
            }
        }

        public final void processPendingCallbacksLocked() {
            if (this.mSessionToken.getExtraBinder() != null) {
                Iterator it = this.mPendingCallbacks.iterator();
                while (it.hasNext()) {
                    Callback callback = (Callback) it.next();
                    ExtraCallback extraCallback = new ExtraCallback(callback);
                    this.mCallbackMap.put(callback, extraCallback);
                    callback.mIControllerCallback = extraCallback;
                    try {
                        this.mSessionToken.getExtraBinder().registerCallbackListener(extraCallback);
                        callback.postToHandler(13, (Object) null, (Bundle) null);
                    } catch (RemoteException e) {
                        Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
                    }
                }
                this.mPendingCallbacks.clear();
            }
        }
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$TransportControls */
    public static abstract class TransportControls {
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$TransportControlsApi21 */
    public static class TransportControlsApi21 extends TransportControls {
        public final MediaController.TransportControls mControlsFwk;
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$TransportControlsApi23 */
    public static class TransportControlsApi23 extends TransportControlsApi21 {
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$TransportControlsApi24 */
    public static class TransportControlsApi24 extends TransportControlsApi23 {
    }

    /* renamed from: android.support.v4.media.session.MediaControllerCompat$TransportControlsApi29 */
    public static class TransportControlsApi29 extends TransportControlsApi24 {
    }

    public final TransportControlsApi29 getTransportControls() {
        throw null;
    }

    public final void unregisterCallback(Callback callback) {
        throw null;
    }
}
