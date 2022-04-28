package com.android.settingslib.volume;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Slog;
import androidx.activity.result.ActivityResultRegistry$3$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.keyguard.KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.VolumeDialogControllerImpl;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class MediaSessions {
    public static final String TAG;
    public final Callbacks mCallbacks;
    public final Context mContext;
    public final C0610H mHandler;
    public final HandlerExecutor mHandlerExecutor;
    public boolean mInit;
    public final MediaSessionManager mMgr;
    public final HashMap mRecords = new HashMap();
    public final C06092 mRemoteSessionCallback = new MediaSessionManager.RemoteSessionCallback() {
        public final void onDefaultRemoteSessionChanged(MediaSession.Token token) {
            MediaSessions.this.mHandler.obtainMessage(3, token).sendToTarget();
        }

        public final void onVolumeChanged(MediaSession.Token token, int i) {
            MediaSessions.this.mHandler.obtainMessage(2, i, 0, token).sendToTarget();
        }
    };
    public final C06081 mSessionsListener = new MediaSessionManager.OnActiveSessionsChangedListener() {
        public final void onActiveSessionsChanged(List<MediaController> list) {
            MediaSessions.this.onActiveSessionsUpdatedH(list);
        }
    };

    public interface Callbacks {
    }

    /* renamed from: com.android.settingslib.volume.MediaSessions$H */
    public final class C0610H extends Handler {
        public C0610H(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            int intValue;
            MediaController mediaController;
            int i = message.what;
            boolean z = true;
            String str = null;
            if (i == 1) {
                MediaSessions mediaSessions = MediaSessions.this;
                mediaSessions.onActiveSessionsUpdatedH(mediaSessions.mMgr.getActiveSessions((ComponentName) null));
            } else if (i == 2) {
                MediaSessions mediaSessions2 = MediaSessions.this;
                int i2 = message.arg1;
                Objects.requireNonNull(mediaSessions2);
                MediaController mediaController2 = new MediaController(mediaSessions2.mContext, (MediaSession.Token) message.obj);
                if (C0607D.BUG) {
                    String str2 = MediaSessions.TAG;
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("remoteVolumeChangedH ");
                    m.append(mediaController2.getPackageName());
                    m.append(" ");
                    m.append(Util.audioManagerFlagsToString(i2));
                    Log.d(str2, m.toString());
                }
                MediaSession.Token sessionToken = mediaController2.getSessionToken();
                VolumeDialogControllerImpl.MediaSessionsCallbacks mediaSessionsCallbacks = (VolumeDialogControllerImpl.MediaSessionsCallbacks) mediaSessions2.mCallbacks;
                Objects.requireNonNull(mediaSessionsCallbacks);
                if (mediaSessionsCallbacks.showForSession(sessionToken)) {
                    mediaSessionsCallbacks.addStream(sessionToken, "onRemoteVolumeChanged");
                    synchronized (mediaSessionsCallbacks.mRemoteStreams) {
                        intValue = mediaSessionsCallbacks.mRemoteStreams.get(sessionToken).intValue();
                    }
                    VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                    Objects.requireNonNull(volumeDialogControllerImpl);
                    WakefulnessLifecycle wakefulnessLifecycle = volumeDialogControllerImpl.mWakefulnessLifecycle;
                    Objects.requireNonNull(wakefulnessLifecycle);
                    int i3 = wakefulnessLifecycle.mWakefulness;
                    if (i3 == 0 || i3 == 3 || !volumeDialogControllerImpl.mDeviceInteractive || (i2 & 1) == 0 || !volumeDialogControllerImpl.mShowVolumeDialog) {
                        z = false;
                    }
                    String str3 = VolumeDialogControllerImpl.TAG;
                    Slog.d(str3, "onRemoteVolumeChanged: stream: " + intValue + " showui? " + z);
                    boolean updateActiveStreamW = VolumeDialogControllerImpl.this.updateActiveStreamW(intValue);
                    if (z) {
                        updateActiveStreamW |= VolumeDialogControllerImpl.this.checkRoutedToBluetoothW(3);
                    }
                    if (updateActiveStreamW) {
                        Slog.d(str3, "onRemoteChanged: updatingState");
                        VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                        volumeDialogControllerImpl2.mCallbacks.onStateChanged(volumeDialogControllerImpl2.mState);
                    }
                    if (z) {
                        VolumeDialogControllerImpl.this.mCallbacks.onShowRequested(2);
                    }
                }
            } else if (i == 3) {
                MediaSessions mediaSessions3 = MediaSessions.this;
                MediaSession.Token token = (MediaSession.Token) message.obj;
                Objects.requireNonNull(mediaSessions3);
                if (token != null) {
                    mediaController = new MediaController(mediaSessions3.mContext, token);
                } else {
                    mediaController = null;
                }
                if (mediaController != null) {
                    str = mediaController.getPackageName();
                }
                if (C0607D.BUG) {
                    DialogFragment$$ExternalSyntheticOutline0.m17m("onUpdateRemoteSessionListH ", str, MediaSessions.TAG);
                }
                if (mediaSessions3.mInit) {
                    mediaSessions3.mHandler.sendEmptyMessage(1);
                }
            }
        }
    }

    public final class MediaControllerRecord extends MediaController.Callback {
        public final MediaController controller;
        public String name;
        public boolean sentRemote;

        public MediaControllerRecord(MediaController mediaController) {
            this.controller = mediaController;
        }

        /* renamed from: cb */
        public final String mo6381cb(String str) {
            StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m(str, " ");
            m.append(this.controller.getPackageName());
            m.append(" ");
            return m.toString();
        }

        public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            boolean z;
            if (C0607D.BUG) {
                String str = MediaSessions.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append(mo6381cb("onAudioInfoChanged"));
                sb.append(Util.playbackInfoToString(playbackInfo));
                sb.append(" sentRemote=");
                KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0.m29m(sb, this.sentRemote, str);
            }
            String str2 = MediaSessions.TAG;
            if (playbackInfo == null || playbackInfo.getPlaybackType() != 2) {
                z = false;
            } else {
                z = true;
            }
            if (!z && this.sentRemote) {
                ((VolumeDialogControllerImpl.MediaSessionsCallbacks) MediaSessions.this.mCallbacks).onRemoteRemoved(this.controller.getSessionToken());
                this.sentRemote = false;
            } else if (z) {
                MediaSessions.this.updateRemoteH(this.controller.getSessionToken(), this.name, playbackInfo);
                this.sentRemote = true;
            }
        }

        public final void onExtrasChanged(Bundle bundle) {
            if (C0607D.BUG) {
                String str = MediaSessions.TAG;
                Log.d(str, mo6381cb("onExtrasChanged") + bundle);
            }
        }

        public final void onMetadataChanged(MediaMetadata mediaMetadata) {
            String str;
            if (C0607D.BUG) {
                String str2 = MediaSessions.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append(mo6381cb("onMetadataChanged"));
                if (mediaMetadata == null) {
                    str = null;
                } else {
                    str = mediaMetadata.getDescription().toString();
                }
                ExifInterface$$ExternalSyntheticOutline2.m15m(sb, str, str2);
            }
        }

        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            if (C0607D.BUG) {
                String str = MediaSessions.TAG;
                Log.d(str, mo6381cb("onPlaybackStateChanged") + Util.playbackStateToString(playbackState));
            }
        }

        public final void onQueueChanged(List<MediaSession.QueueItem> list) {
            if (C0607D.BUG) {
                String str = MediaSessions.TAG;
                Log.d(str, mo6381cb("onQueueChanged") + list);
            }
        }

        public final void onQueueTitleChanged(CharSequence charSequence) {
            if (C0607D.BUG) {
                String str = MediaSessions.TAG;
                Log.d(str, mo6381cb("onQueueTitleChanged") + charSequence);
            }
        }

        public final void onSessionDestroyed() {
            if (C0607D.BUG) {
                Log.d(MediaSessions.TAG, mo6381cb("onSessionDestroyed"));
            }
        }

        public final void onSessionEvent(String str, Bundle bundle) {
            if (C0607D.BUG) {
                String str2 = MediaSessions.TAG;
                Log.d(str2, mo6381cb("onSessionEvent") + "event=" + str + " extras=" + bundle);
            }
        }
    }

    public final void init() {
        if (C0607D.BUG) {
            Log.d(TAG, "init");
        }
        this.mMgr.addOnActiveSessionsChangedListener(this.mSessionsListener, (ComponentName) null, this.mHandler);
        this.mInit = true;
        this.mHandler.sendEmptyMessage(1);
        this.mMgr.registerRemoteSessionCallback(this.mHandlerExecutor, this.mRemoteSessionCallback);
    }

    public final void onActiveSessionsUpdatedH(List<MediaController> list) {
        if (C0607D.BUG) {
            String str = TAG;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onActiveSessionsUpdatedH n=");
            m.append(list.size());
            Log.d(str, m.toString());
        }
        HashSet hashSet = new HashSet(this.mRecords.keySet());
        Iterator<MediaController> it = list.iterator();
        while (true) {
            boolean z = false;
            if (!it.hasNext()) {
                break;
            }
            MediaController next = it.next();
            MediaSession.Token sessionToken = next.getSessionToken();
            MediaController.PlaybackInfo playbackInfo = next.getPlaybackInfo();
            hashSet.remove(sessionToken);
            if (!this.mRecords.containsKey(sessionToken)) {
                MediaControllerRecord mediaControllerRecord = new MediaControllerRecord(next);
                PackageManager packageManager = this.mContext.getPackageManager();
                String packageName = next.getPackageName();
                try {
                    String trim = Objects.toString(packageManager.getApplicationInfo(packageName, 0).loadLabel(packageManager), "").trim();
                    if (trim.length() > 0) {
                        packageName = trim;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
                mediaControllerRecord.name = packageName;
                this.mRecords.put(sessionToken, mediaControllerRecord);
                next.registerCallback(mediaControllerRecord, this.mHandler);
            }
            MediaControllerRecord mediaControllerRecord2 = (MediaControllerRecord) this.mRecords.get(sessionToken);
            if (playbackInfo != null && playbackInfo.getPlaybackType() == 2) {
                z = true;
            }
            if (z) {
                updateRemoteH(sessionToken, mediaControllerRecord2.name, playbackInfo);
                mediaControllerRecord2.sentRemote = true;
            }
        }
        Iterator it2 = hashSet.iterator();
        while (it2.hasNext()) {
            MediaSession.Token token = (MediaSession.Token) it2.next();
            MediaControllerRecord mediaControllerRecord3 = (MediaControllerRecord) this.mRecords.get(token);
            mediaControllerRecord3.controller.unregisterCallback(mediaControllerRecord3);
            this.mRecords.remove(token);
            if (C0607D.BUG) {
                String str2 = TAG;
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Removing ");
                m2.append(mediaControllerRecord3.name);
                m2.append(" sentRemote=");
                KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0.m29m(m2, mediaControllerRecord3.sentRemote, str2);
            }
            if (mediaControllerRecord3.sentRemote) {
                ((VolumeDialogControllerImpl.MediaSessionsCallbacks) this.mCallbacks).onRemoteRemoved(token);
                mediaControllerRecord3.sentRemote = false;
            }
        }
    }

    public final void updateRemoteH(MediaSession.Token token, String str, MediaController.PlaybackInfo playbackInfo) {
        int intValue;
        boolean z;
        Callbacks callbacks = this.mCallbacks;
        if (callbacks != null) {
            VolumeDialogControllerImpl.MediaSessionsCallbacks mediaSessionsCallbacks = (VolumeDialogControllerImpl.MediaSessionsCallbacks) callbacks;
            Objects.requireNonNull(mediaSessionsCallbacks);
            if (mediaSessionsCallbacks.showForSession(token)) {
                mediaSessionsCallbacks.addStream(token, "onRemoteUpdate");
                synchronized (mediaSessionsCallbacks.mRemoteStreams) {
                    intValue = mediaSessionsCallbacks.mRemoteStreams.get(token).intValue();
                }
                String str2 = VolumeDialogControllerImpl.TAG;
                StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("onRemoteUpdate: stream: ", intValue, " volume: ");
                m.append(playbackInfo.getCurrentVolume());
                Slog.d(str2, m.toString());
                boolean z2 = true;
                if (VolumeDialogControllerImpl.this.mState.states.indexOfKey(intValue) < 0) {
                    z = true;
                } else {
                    z = false;
                }
                VolumeDialogController.StreamState streamStateW = VolumeDialogControllerImpl.this.streamStateW(intValue);
                streamStateW.dynamic = true;
                streamStateW.levelMin = 0;
                streamStateW.levelMax = playbackInfo.getMaxVolume();
                if (streamStateW.level != playbackInfo.getCurrentVolume()) {
                    streamStateW.level = playbackInfo.getCurrentVolume();
                    z = true;
                }
                if (!Objects.equals(streamStateW.remoteLabel, str)) {
                    streamStateW.name = -1;
                    streamStateW.remoteLabel = str;
                } else {
                    z2 = z;
                }
                if (z2) {
                    StringBuilder m2 = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("onRemoteUpdate: ", str, ": ");
                    m2.append(streamStateW.level);
                    m2.append(" of ");
                    KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(m2, streamStateW.levelMax, str2);
                    VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                    volumeDialogControllerImpl.mCallbacks.onStateChanged(volumeDialogControllerImpl.mState);
                }
            }
        }
    }

    static {
        String str = "vol.MediaSessions";
        if (str.length() >= 23) {
            str = str.substring(0, 23);
        }
        TAG = str;
    }

    public MediaSessions(Context context, Looper looper, VolumeDialogControllerImpl.MediaSessionsCallbacks mediaSessionsCallbacks) {
        this.mContext = context;
        C0610H h = new C0610H(looper);
        this.mHandler = h;
        this.mHandlerExecutor = new HandlerExecutor(h);
        this.mMgr = (MediaSessionManager) context.getSystemService("media_session");
        this.mCallbacks = mediaSessionsCallbacks;
    }
}
