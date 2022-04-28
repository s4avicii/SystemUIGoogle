package com.android.systemui.volume;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.media.IAudioService;
import android.media.IVolumeController;
import android.media.MediaMetadata;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.media.VolumePolicy;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.util.ArrayMap;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import androidx.activity.result.ActivityResultRegistry$3$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.annotations.GuardedBy;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline1;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.volume.MediaSessions;
import com.android.settingslib.volume.Util;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.p006qs.tiles.QuickAccessWalletTile$$ExternalSyntheticLambda0;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda6;
import com.android.systemui.util.RingerModeLiveData;
import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.concurrency.ThreadFactory;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class VolumeDialogControllerImpl implements VolumeDialogController, Dumpable {
    public static final AudioAttributes SONIFICIATION_VIBRATION_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    public static final ArrayMap<Integer, Integer> STREAMS;
    public static final String TAG = Util.logTag(VolumeDialogControllerImpl.class);
    public AudioManager mAudio;
    public IAudioService mAudioService;
    public C1720C mCallbacks = new C1720C();
    public final Context mContext;
    public boolean mDeviceInteractive;
    public final boolean mHasVibrator;
    public long mLastToggledRingerOn;
    public final MediaSessions mMediaSessions;
    public final MediaSessionsCallbacks mMediaSessionsCallbacksW;
    public final NotificationManager mNoMan;
    public final PackageManager mPackageManager;
    public final RingerModeObservers mRingerModeObservers;
    public final MediaRouter2Manager mRouter2Manager;
    public boolean mShowA11yStream;
    public boolean mShowDndTile;
    public boolean mShowSafetyWarning;
    public boolean mShowVolumeDialog;
    public final VolumeDialogController.State mState;
    @GuardedBy({"this"})
    public UserActivityListener mUserActivityListener;
    public final VibratorHelper mVibrator;
    public final C1733VC mVolumeController;
    public VolumePolicy mVolumePolicy;
    public final C17191 mWakefullnessLifecycleObserver;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final C1734W mWorker;

    /* renamed from: com.android.systemui.volume.VolumeDialogControllerImpl$C */
    public class C1720C implements VolumeDialogController.Callbacks {
        public final ConcurrentHashMap mCallbackMap = new ConcurrentHashMap();

        public final void onAccessibilityModeChanged(Boolean bool) {
            final boolean z;
            if (bool == null) {
                z = false;
            } else {
                z = bool.booleanValue();
            }
            for (final Map.Entry entry : this.mCallbackMap.entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() {
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onAccessibilityModeChanged(Boolean.valueOf(z));
                    }
                });
            }
        }

        public final void onCaptionComponentStateChanged(Boolean bool, Boolean bool2) {
            boolean z;
            if (bool == null) {
                z = false;
            } else {
                z = bool.booleanValue();
            }
            for (Map.Entry entry : this.mCallbackMap.entrySet()) {
                ((Handler) entry.getValue()).post(new VolumeDialogControllerImpl$C$$ExternalSyntheticLambda0(entry, z, bool2));
            }
        }

        public final void onConfigurationChanged() {
            for (final Map.Entry entry : this.mCallbackMap.entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() {
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onConfigurationChanged();
                    }
                });
            }
        }

        public final void onDismissRequested(final int i) {
            for (final Map.Entry entry : this.mCallbackMap.entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() {
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onDismissRequested(i);
                    }
                });
            }
        }

        public final void onLayoutDirectionChanged(final int i) {
            for (final Map.Entry entry : this.mCallbackMap.entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() {
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onLayoutDirectionChanged(i);
                    }
                });
            }
        }

        public final void onScreenOff() {
            for (final Map.Entry entry : this.mCallbackMap.entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() {
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onScreenOff();
                    }
                });
            }
        }

        public final void onShowRequested(final int i) {
            for (final Map.Entry entry : this.mCallbackMap.entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() {
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onShowRequested(i);
                    }
                });
            }
        }

        public final void onShowSafetyWarning(final int i) {
            for (final Map.Entry entry : this.mCallbackMap.entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() {
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onShowSafetyWarning(i);
                    }
                });
            }
        }

        public final void onShowSilentHint() {
            for (final Map.Entry entry : this.mCallbackMap.entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() {
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onShowSilentHint();
                    }
                });
            }
        }

        public final void onShowVibrateHint() {
            for (final Map.Entry entry : this.mCallbackMap.entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() {
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onShowVibrateHint();
                    }
                });
            }
        }

        public final void onStateChanged(VolumeDialogController.State state) {
            System.currentTimeMillis();
            final VolumeDialogController.State copy = state.copy();
            for (final Map.Entry entry : this.mCallbackMap.entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() {
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onStateChanged(copy);
                    }
                });
            }
            String str = Events.TAG;
        }
    }

    public final class MediaSessionsCallbacks implements MediaSessions.Callbacks {
        public int mNextStream = 100;
        public final HashMap<MediaSession.Token, Integer> mRemoteStreams = new HashMap<>();
        public final boolean mVolumeAdjustmentForRemoteGroupSessions;

        public MediaSessionsCallbacks(Context context) {
            this.mVolumeAdjustmentForRemoteGroupSessions = context.getResources().getBoolean(17891813);
        }

        public final void addStream(MediaSession.Token token, String str) {
            synchronized (this.mRemoteStreams) {
                if (!this.mRemoteStreams.containsKey(token)) {
                    this.mRemoteStreams.put(token, Integer.valueOf(this.mNextStream));
                    String str2 = VolumeDialogControllerImpl.TAG;
                    Log.d(str2, str + ": added stream " + this.mNextStream + " from token + " + token.toString());
                    this.mNextStream = this.mNextStream + 1;
                }
            }
        }

        public final boolean showForSession(MediaSession.Token token) {
            boolean z;
            if (this.mVolumeAdjustmentForRemoteGroupSessions) {
                return true;
            }
            String packageName = new MediaController(VolumeDialogControllerImpl.this.mContext, token).getPackageName();
            Iterator it = VolumeDialogControllerImpl.this.mRouter2Manager.getRoutingSessions(packageName).iterator();
            boolean z2 = false;
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) it.next();
                if (!routingSessionInfo.isSystemSession()) {
                    if (routingSessionInfo.getSelectedRoutes().size() > 1) {
                        z = true;
                        z2 = true;
                        break;
                    }
                    z2 = true;
                }
            }
            if (z2) {
                return !z;
            }
            DialogFragment$$ExternalSyntheticOutline0.m17m("No routing session for ", packageName, VolumeDialogControllerImpl.TAG);
            return false;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x003a, code lost:
            r3.this$0.mState.states.remove(r4);
            r0 = r3.this$0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0049, code lost:
            if (r0.mState.activeStream != r4) goto L_0x004f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x004b, code lost:
            r0.updateActiveStreamW(-1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x004f, code lost:
            r3 = r3.this$0;
            r3.mCallbacks.onStateChanged(r3.mState);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onRemoteRemoved(android.media.session.MediaSession.Token r4) {
            /*
                r3 = this;
                boolean r0 = r3.showForSession(r4)
                if (r0 == 0) goto L_0x005c
                java.util.HashMap<android.media.session.MediaSession$Token, java.lang.Integer> r0 = r3.mRemoteStreams
                monitor-enter(r0)
                java.util.HashMap<android.media.session.MediaSession$Token, java.lang.Integer> r1 = r3.mRemoteStreams     // Catch:{ all -> 0x0059 }
                boolean r1 = r1.containsKey(r4)     // Catch:{ all -> 0x0059 }
                if (r1 != 0) goto L_0x002d
                java.lang.String r3 = com.android.systemui.volume.VolumeDialogControllerImpl.TAG     // Catch:{ all -> 0x0059 }
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0059 }
                r1.<init>()     // Catch:{ all -> 0x0059 }
                java.lang.String r2 = "onRemoteRemoved: stream doesn't exist, aborting remote removed for token:"
                r1.append(r2)     // Catch:{ all -> 0x0059 }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0059 }
                r1.append(r4)     // Catch:{ all -> 0x0059 }
                java.lang.String r4 = r1.toString()     // Catch:{ all -> 0x0059 }
                android.util.Log.d(r3, r4)     // Catch:{ all -> 0x0059 }
                monitor-exit(r0)     // Catch:{ all -> 0x0059 }
                return
            L_0x002d:
                java.util.HashMap<android.media.session.MediaSession$Token, java.lang.Integer> r1 = r3.mRemoteStreams     // Catch:{ all -> 0x0059 }
                java.lang.Object r4 = r1.get(r4)     // Catch:{ all -> 0x0059 }
                java.lang.Integer r4 = (java.lang.Integer) r4     // Catch:{ all -> 0x0059 }
                int r4 = r4.intValue()     // Catch:{ all -> 0x0059 }
                monitor-exit(r0)     // Catch:{ all -> 0x0059 }
                com.android.systemui.volume.VolumeDialogControllerImpl r0 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                com.android.systemui.plugins.VolumeDialogController$State r0 = r0.mState
                android.util.SparseArray<com.android.systemui.plugins.VolumeDialogController$StreamState> r0 = r0.states
                r0.remove(r4)
                com.android.systemui.volume.VolumeDialogControllerImpl r0 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                com.android.systemui.plugins.VolumeDialogController$State r1 = r0.mState
                int r1 = r1.activeStream
                if (r1 != r4) goto L_0x004f
                r4 = -1
                r0.updateActiveStreamW(r4)
            L_0x004f:
                com.android.systemui.volume.VolumeDialogControllerImpl r3 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                com.android.systemui.volume.VolumeDialogControllerImpl$C r4 = r3.mCallbacks
                com.android.systemui.plugins.VolumeDialogController$State r3 = r3.mState
                r4.onStateChanged(r3)
                goto L_0x005c
            L_0x0059:
                r3 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0059 }
                throw r3
            L_0x005c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogControllerImpl.MediaSessionsCallbacks.onRemoteRemoved(android.media.session.MediaSession$Token):void");
        }
    }

    public final class Receiver extends BroadcastReceiver {
        public Receiver() {
        }

        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean z = false;
            if (action.equals("android.media.VOLUME_CHANGED_ACTION")) {
                int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", -1);
                int intExtra3 = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", -1);
                if (C1716D.BUG) {
                    KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(GridLayoutManager$$ExternalSyntheticOutline0.m19m("onReceive VOLUME_CHANGED_ACTION stream=", intExtra, " level=", intExtra2, " oldLevel="), intExtra3, VolumeDialogControllerImpl.TAG);
                }
                z = VolumeDialogControllerImpl.this.updateStreamLevelW(intExtra, intExtra2);
            } else if (action.equals("android.media.STREAM_DEVICES_CHANGED_ACTION")) {
                int intExtra4 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                int intExtra5 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_DEVICES", -1);
                int intExtra6 = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES", -1);
                if (C1716D.BUG) {
                    KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(GridLayoutManager$$ExternalSyntheticOutline0.m19m("onReceive STREAM_DEVICES_CHANGED_ACTION stream=", intExtra4, " devices=", intExtra5, " oldDevices="), intExtra6, VolumeDialogControllerImpl.TAG);
                }
                z = VolumeDialogControllerImpl.this.checkRoutedToBluetoothW(intExtra4) | VolumeDialogControllerImpl.this.onVolumeChangedW(intExtra4, 0);
            } else if (action.equals("android.media.STREAM_MUTE_CHANGED_ACTION")) {
                int intExtra7 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                boolean booleanExtra = intent.getBooleanExtra("android.media.EXTRA_STREAM_VOLUME_MUTED", false);
                if (C1716D.BUG) {
                    String str = VolumeDialogControllerImpl.TAG;
                    Log.d(str, "onReceive STREAM_MUTE_CHANGED_ACTION stream=" + intExtra7 + " muted=" + booleanExtra);
                }
                z = VolumeDialogControllerImpl.this.updateStreamMuteW(intExtra7, booleanExtra);
            } else if (action.equals("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED")) {
                if (C1716D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onReceive ACTION_EFFECTS_SUPPRESSOR_CHANGED");
                }
                VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                z = volumeDialogControllerImpl.updateEffectsSuppressorW(volumeDialogControllerImpl.mNoMan.getEffectsSuppressor());
            } else if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                if (C1716D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onReceive ACTION_CONFIGURATION_CHANGED");
                }
                VolumeDialogControllerImpl.this.mCallbacks.onConfigurationChanged();
            } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                if (C1716D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onReceive ACTION_SCREEN_OFF");
                }
                VolumeDialogControllerImpl.this.mCallbacks.onScreenOff();
            } else if (action.equals("android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
                if (C1716D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onReceive ACTION_CLOSE_SYSTEM_DIALOGS");
                }
                VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                Objects.requireNonNull(volumeDialogControllerImpl2);
                volumeDialogControllerImpl2.mCallbacks.onDismissRequested(2);
            }
            if (z) {
                VolumeDialogControllerImpl volumeDialogControllerImpl3 = VolumeDialogControllerImpl.this;
                volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
            }
        }
    }

    public final class RingerModeObservers {
        public final RingerModeLiveData mRingerMode;
        public final RingerModeLiveData mRingerModeInternal;
        public final C17322 mRingerModeInternalObserver = new Observer<Integer>() {
            public final void onChanged(Object obj) {
                VolumeDialogControllerImpl.this.mWorker.post(new QuickAccessWalletTile$$ExternalSyntheticLambda0(this, (Integer) obj, 4));
            }
        };
        public final C17311 mRingerModeObserver = new Observer<Integer>() {
            public final void onChanged(Object obj) {
                VolumeDialogControllerImpl.this.mWorker.post(new NavBarTuner$$ExternalSyntheticLambda6(this, (Integer) obj, 5));
            }
        };

        public RingerModeObservers(RingerModeLiveData ringerModeLiveData, RingerModeLiveData ringerModeLiveData2) {
            this.mRingerMode = ringerModeLiveData;
            this.mRingerModeInternal = ringerModeLiveData2;
        }
    }

    public final class SettingObserver extends ContentObserver {
        public final Uri ZEN_MODE_CONFIG_URI = Settings.Global.getUriFor("zen_mode_config_etag");
        public final Uri ZEN_MODE_URI = Settings.Global.getUriFor("zen_mode");

        public SettingObserver(C1734W w) {
            super(w);
        }

        public final void onChange(boolean z, Uri uri) {
            boolean z2;
            if (this.ZEN_MODE_URI.equals(uri)) {
                z2 = VolumeDialogControllerImpl.this.updateZenModeW();
            } else {
                z2 = false;
            }
            if (this.ZEN_MODE_CONFIG_URI.equals(uri)) {
                z2 |= VolumeDialogControllerImpl.this.updateZenConfig();
            }
            if (z2) {
                VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                volumeDialogControllerImpl.mCallbacks.onStateChanged(volumeDialogControllerImpl.mState);
            }
        }
    }

    public interface UserActivityListener {
    }

    /* renamed from: com.android.systemui.volume.VolumeDialogControllerImpl$VC */
    public final class C1733VC extends IVolumeController.Stub {
        public final String TAG = MotionController$$ExternalSyntheticOutline1.m8m(new StringBuilder(), VolumeDialogControllerImpl.TAG, ".VC");

        public C1733VC() {
        }

        public final void dismiss() throws RemoteException {
            if (C1716D.BUG) {
                Log.d(this.TAG, "dismiss requested");
            }
            Objects.requireNonNull(VolumeDialogControllerImpl.this);
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(2, 2, 0).sendToTarget();
            VolumeDialogControllerImpl.this.mWorker.sendEmptyMessage(2);
        }

        public final void displaySafeVolumeWarning(int i) throws RemoteException {
            if (C1716D.BUG) {
                String str = this.TAG;
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("displaySafeVolumeWarning ");
                m.append(Util.audioManagerFlagsToString(i));
                Log.d(str, m.toString());
            }
            Objects.requireNonNull(VolumeDialogControllerImpl.this);
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(14, i, 0).sendToTarget();
        }

        public final void masterMuteChanged(int i) throws RemoteException {
            if (C1716D.BUG) {
                Log.d(this.TAG, "masterMuteChanged");
            }
        }

        public final void setA11yMode(int i) {
            if (C1716D.BUG) {
                ExifInterface$$ExternalSyntheticOutline1.m14m("setA11yMode to ", i, this.TAG);
            }
            Objects.requireNonNull(VolumeDialogControllerImpl.this);
            if (i == 0) {
                VolumeDialogControllerImpl.this.mShowA11yStream = false;
            } else if (i != 1) {
                KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m("Invalid accessibility mode ", i, this.TAG);
            } else {
                VolumeDialogControllerImpl.this.mShowA11yStream = true;
            }
            VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
            volumeDialogControllerImpl.mWorker.obtainMessage(15, Boolean.valueOf(volumeDialogControllerImpl.mShowA11yStream)).sendToTarget();
        }

        public final void setLayoutDirection(int i) throws RemoteException {
            if (C1716D.BUG) {
                Log.d(this.TAG, "setLayoutDirection");
            }
            Objects.requireNonNull(VolumeDialogControllerImpl.this);
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(8, i, 0).sendToTarget();
        }

        public final void volumeChanged(int i, int i2) throws RemoteException {
            if (C1716D.BUG) {
                String str = this.TAG;
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("volumeChanged ");
                m.append(AudioSystem.streamToString(i));
                m.append(" ");
                m.append(Util.audioManagerFlagsToString(i2));
                Log.d(str, m.toString());
            }
            Objects.requireNonNull(VolumeDialogControllerImpl.this);
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(1, i, i2).sendToTarget();
        }
    }

    /* renamed from: com.android.systemui.volume.VolumeDialogControllerImpl$W */
    public final class C1734W extends Handler {
        public C1734W(Looper looper) {
            super(looper);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v12, resolved type: android.net.Uri} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: android.media.session.MediaSession$Token} */
        /* JADX WARNING: type inference failed for: r4v0 */
        /* JADX WARNING: type inference failed for: r4v20 */
        /* JADX WARNING: type inference failed for: r4v21 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void handleMessage(android.os.Message r9) {
            /*
                r8 = this;
                int r0 = r9.what
                r1 = 0
                r2 = 1
                r3 = 100
                r4 = 0
                switch(r0) {
                    case 1: goto L_0x02e9;
                    case 2: goto L_0x02dc;
                    case 3: goto L_0x0234;
                    case 4: goto L_0x0218;
                    case 5: goto L_0x01fd;
                    case 6: goto L_0x01e3;
                    case 7: goto L_0x01cb;
                    case 8: goto L_0x01c0;
                    case 9: goto L_0x01b7;
                    case 10: goto L_0x00fa;
                    case 11: goto L_0x00e4;
                    case 12: goto L_0x00c1;
                    case 13: goto L_0x00aa;
                    case 14: goto L_0x0098;
                    case 15: goto L_0x0088;
                    case 16: goto L_0x000c;
                    default: goto L_0x000a;
                }
            L_0x000a:
                goto L_0x02f2
            L_0x000c:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                java.lang.Object r9 = r9.obj
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                java.util.Objects.requireNonNull(r8)
                android.content.Context r0 = r8.mContext     // Catch:{ Exception -> 0x0073 }
                r3 = 17039931(0x104023b, float:2.424617E-38)
                java.lang.String r0 = r0.getString(r3)     // Catch:{ Exception -> 0x0073 }
                boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0073 }
                if (r3 == 0) goto L_0x0035
                com.android.systemui.volume.VolumeDialogControllerImpl$C r0 = r8.mCallbacks     // Catch:{ Exception -> 0x0073 }
                java.lang.Boolean r1 = java.lang.Boolean.FALSE     // Catch:{ Exception -> 0x0073 }
                java.lang.Boolean r2 = java.lang.Boolean.valueOf(r9)     // Catch:{ Exception -> 0x0073 }
                r0.onCaptionComponentStateChanged(r1, r2)     // Catch:{ Exception -> 0x0073 }
                goto L_0x02f2
            L_0x0035:
                boolean r3 = com.android.systemui.volume.C1716D.BUG     // Catch:{ Exception -> 0x0073 }
                if (r3 == 0) goto L_0x0048
                java.lang.String r3 = com.android.systemui.volume.VolumeDialogControllerImpl.TAG     // Catch:{ Exception -> 0x0073 }
                java.lang.String r4 = "isCaptionsServiceEnabled componentNameString=%s"
                java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0073 }
                r5[r1] = r0     // Catch:{ Exception -> 0x0073 }
                java.lang.String r4 = java.lang.String.format(r4, r5)     // Catch:{ Exception -> 0x0073 }
                android.util.Log.i(r3, r4)     // Catch:{ Exception -> 0x0073 }
            L_0x0048:
                android.content.ComponentName r0 = android.content.ComponentName.unflattenFromString(r0)     // Catch:{ Exception -> 0x0073 }
                if (r0 != 0) goto L_0x005b
                com.android.systemui.volume.VolumeDialogControllerImpl$C r0 = r8.mCallbacks     // Catch:{ Exception -> 0x0073 }
                java.lang.Boolean r1 = java.lang.Boolean.FALSE     // Catch:{ Exception -> 0x0073 }
                java.lang.Boolean r2 = java.lang.Boolean.valueOf(r9)     // Catch:{ Exception -> 0x0073 }
                r0.onCaptionComponentStateChanged(r1, r2)     // Catch:{ Exception -> 0x0073 }
                goto L_0x02f2
            L_0x005b:
                com.android.systemui.volume.VolumeDialogControllerImpl$C r3 = r8.mCallbacks     // Catch:{ Exception -> 0x0073 }
                android.content.pm.PackageManager r4 = r8.mPackageManager     // Catch:{ Exception -> 0x0073 }
                int r0 = r4.getComponentEnabledSetting(r0)     // Catch:{ Exception -> 0x0073 }
                if (r0 != r2) goto L_0x0066
                r1 = r2
            L_0x0066:
                java.lang.Boolean r0 = java.lang.Boolean.valueOf(r1)     // Catch:{ Exception -> 0x0073 }
                java.lang.Boolean r1 = java.lang.Boolean.valueOf(r9)     // Catch:{ Exception -> 0x0073 }
                r3.onCaptionComponentStateChanged(r0, r1)     // Catch:{ Exception -> 0x0073 }
                goto L_0x02f2
            L_0x0073:
                r0 = move-exception
                java.lang.String r1 = com.android.systemui.volume.VolumeDialogControllerImpl.TAG
                java.lang.String r2 = "isCaptionsServiceEnabled failed to check for captions component"
                android.util.Log.e(r1, r2, r0)
                com.android.systemui.volume.VolumeDialogControllerImpl$C r8 = r8.mCallbacks
                java.lang.Boolean r0 = java.lang.Boolean.FALSE
                java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)
                r8.onCaptionComponentStateChanged(r0, r9)
                goto L_0x02f2
            L_0x0088:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                java.lang.Object r9 = r9.obj
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                java.util.Objects.requireNonNull(r8)
                com.android.systemui.volume.VolumeDialogControllerImpl$C r8 = r8.mCallbacks
                r8.onAccessibilityModeChanged(r9)
                goto L_0x02f2
            L_0x0098:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                int r9 = r9.arg1
                java.util.Objects.requireNonNull(r8)
                boolean r0 = r8.mShowSafetyWarning
                if (r0 == 0) goto L_0x02f2
                com.android.systemui.volume.VolumeDialogControllerImpl$C r8 = r8.mCallbacks
                r8.onShowSafetyWarning(r9)
                goto L_0x02f2
            L_0x00aa:
                com.android.systemui.volume.VolumeDialogControllerImpl r0 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                java.util.Objects.requireNonNull(r0)
                monitor-enter(r0)
                com.android.systemui.volume.VolumeDialogControllerImpl$UserActivityListener r8 = r0.mUserActivityListener     // Catch:{ all -> 0x00be }
                if (r8 == 0) goto L_0x00bb
                com.android.systemui.volume.VolumeDialogComponent r8 = (com.android.systemui.volume.VolumeDialogComponent) r8     // Catch:{ all -> 0x00be }
                com.android.systemui.keyguard.KeyguardViewMediator r8 = r8.mKeyguardViewMediator     // Catch:{ all -> 0x00be }
                r8.userActivity()     // Catch:{ all -> 0x00be }
            L_0x00bb:
                monitor-exit(r0)     // Catch:{ all -> 0x00be }
                goto L_0x02f2
            L_0x00be:
                r8 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00be }
                throw r8
            L_0x00c1:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                int r9 = r9.arg1
                if (r9 == 0) goto L_0x00c8
                r1 = r2
            L_0x00c8:
                java.util.Objects.requireNonNull(r8)
                android.media.AudioManager r9 = r8.mAudio
                com.android.systemui.volume.VolumeDialogControllerImpl$VC r0 = r8.mVolumeController
                r9.notifyVolumeControllerVisible(r0, r1)
                if (r1 != 0) goto L_0x02f2
                r9 = -1
                boolean r9 = r8.updateActiveStreamW(r9)
                if (r9 == 0) goto L_0x02f2
                com.android.systemui.volume.VolumeDialogControllerImpl$C r9 = r8.mCallbacks
                com.android.systemui.plugins.VolumeDialogController$State r8 = r8.mState
                r9.onStateChanged(r8)
                goto L_0x02f2
            L_0x00e4:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                int r9 = r9.arg1
                java.util.Objects.requireNonNull(r8)
                boolean r9 = r8.updateActiveStreamW(r9)
                if (r9 == 0) goto L_0x02f2
                com.android.systemui.volume.VolumeDialogControllerImpl$C r9 = r8.mCallbacks
                com.android.systemui.plugins.VolumeDialogController$State r8 = r8.mState
                r9.onStateChanged(r8)
                goto L_0x02f2
            L_0x00fa:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                int r0 = r9.arg1
                int r9 = r9.arg2
                java.util.Objects.requireNonNull(r8)
                boolean r2 = com.android.systemui.volume.C1716D.BUG
                if (r2 == 0) goto L_0x0125
                java.lang.String r2 = com.android.systemui.volume.VolumeDialogControllerImpl.TAG
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "onSetStreamVolume "
                r5.append(r6)
                r5.append(r0)
                java.lang.String r6 = " level="
                r5.append(r6)
                r5.append(r9)
                java.lang.String r5 = r5.toString()
                android.util.Log.d(r2, r5)
            L_0x0125:
                if (r0 < r3) goto L_0x01b0
                com.android.systemui.volume.VolumeDialogControllerImpl$MediaSessionsCallbacks r8 = r8.mMediaSessionsCallbacksW
                java.util.Objects.requireNonNull(r8)
                java.util.HashMap<android.media.session.MediaSession$Token, java.lang.Integer> r2 = r8.mRemoteStreams
                monitor-enter(r2)
                java.util.HashMap<android.media.session.MediaSession$Token, java.lang.Integer> r3 = r8.mRemoteStreams     // Catch:{ all -> 0x01ad }
                java.util.Set r3 = r3.entrySet()     // Catch:{ all -> 0x01ad }
                java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x01ad }
            L_0x0139:
                boolean r5 = r3.hasNext()     // Catch:{ all -> 0x01ad }
                if (r5 == 0) goto L_0x015e
                java.lang.Object r5 = r3.next()     // Catch:{ all -> 0x01ad }
                java.util.Map$Entry r5 = (java.util.Map.Entry) r5     // Catch:{ all -> 0x01ad }
                java.lang.Object r6 = r5.getValue()     // Catch:{ all -> 0x01ad }
                java.lang.Integer r6 = (java.lang.Integer) r6     // Catch:{ all -> 0x01ad }
                java.lang.Integer r7 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x01ad }
                boolean r6 = r6.equals(r7)     // Catch:{ all -> 0x01ad }
                if (r6 == 0) goto L_0x0139
                java.lang.Object r3 = r5.getKey()     // Catch:{ all -> 0x01ad }
                r4 = r3
                android.media.session.MediaSession$Token r4 = (android.media.session.MediaSession.Token) r4     // Catch:{ all -> 0x01ad }
                monitor-exit(r2)     // Catch:{ all -> 0x01ad }
                goto L_0x015f
            L_0x015e:
                monitor-exit(r2)     // Catch:{ all -> 0x01ad }
            L_0x015f:
                if (r4 != 0) goto L_0x016b
                java.lang.String r8 = com.android.systemui.volume.VolumeDialogControllerImpl.TAG
                java.lang.String r9 = "setStreamVolume: No token found for stream: "
                androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline1.m20m(r9, r0, r8)
                goto L_0x02f2
            L_0x016b:
                boolean r0 = r8.showForSession(r4)
                if (r0 == 0) goto L_0x02f2
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                com.android.settingslib.volume.MediaSessions r8 = r8.mMediaSessions
                java.util.Objects.requireNonNull(r8)
                java.util.HashMap r8 = r8.mRecords
                java.lang.Object r8 = r8.get(r4)
                com.android.settingslib.volume.MediaSessions$MediaControllerRecord r8 = (com.android.settingslib.volume.MediaSessions.MediaControllerRecord) r8
                if (r8 != 0) goto L_0x019b
                java.lang.String r8 = com.android.settingslib.volume.MediaSessions.TAG
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r0 = "setVolume: No record found for token "
                r9.append(r0)
                r9.append(r4)
                java.lang.String r9 = r9.toString()
                android.util.Log.w(r8, r9)
                goto L_0x02f2
            L_0x019b:
                boolean r0 = com.android.settingslib.volume.C0607D.BUG
                if (r0 == 0) goto L_0x01a6
                java.lang.String r0 = com.android.settingslib.volume.MediaSessions.TAG
                java.lang.String r2 = "Setting level to "
                androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r2, r9, r0)
            L_0x01a6:
                android.media.session.MediaController r8 = r8.controller
                r8.setVolumeTo(r9, r1)
                goto L_0x02f2
            L_0x01ad:
                r8 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x01ad }
                throw r8
            L_0x01b0:
                android.media.AudioManager r8 = r8.mAudio
                r8.setStreamVolume(r0, r9, r1)
                goto L_0x02f2
            L_0x01b7:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                com.android.systemui.volume.VolumeDialogControllerImpl$C r8 = r8.mCallbacks
                r8.onConfigurationChanged()
                goto L_0x02f2
            L_0x01c0:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                com.android.systemui.volume.VolumeDialogControllerImpl$C r8 = r8.mCallbacks
                int r9 = r9.arg1
                r8.onLayoutDirectionChanged(r9)
                goto L_0x02f2
            L_0x01cb:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                int r0 = r9.arg1
                int r9 = r9.arg2
                if (r9 == 0) goto L_0x01d4
                goto L_0x01d5
            L_0x01d4:
                r2 = r1
            L_0x01d5:
                java.util.Objects.requireNonNull(r8)
                android.media.AudioManager r8 = r8.mAudio
                if (r2 == 0) goto L_0x01de
                r3 = -100
            L_0x01de:
                r8.adjustStreamVolume(r0, r3, r1)
                goto L_0x02f2
            L_0x01e3:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                java.lang.Object r9 = r9.obj
                android.service.notification.Condition r9 = (android.service.notification.Condition) r9
                java.util.Objects.requireNonNull(r8)
                android.app.NotificationManager r0 = r8.mNoMan
                com.android.systemui.plugins.VolumeDialogController$State r8 = r8.mState
                int r8 = r8.zenMode
                if (r9 == 0) goto L_0x01f6
                android.net.Uri r4 = r9.id
            L_0x01f6:
                java.lang.String r9 = com.android.systemui.volume.VolumeDialogControllerImpl.TAG
                r0.setZenMode(r8, r4, r9)
                goto L_0x02f2
            L_0x01fd:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                int r9 = r9.arg1
                java.util.Objects.requireNonNull(r8)
                boolean r0 = com.android.systemui.volume.C1716D.BUG
                if (r0 == 0) goto L_0x020f
                java.lang.String r0 = com.android.systemui.volume.VolumeDialogControllerImpl.TAG
                java.lang.String r1 = "onSetZenModeW "
                androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r1, r9, r0)
            L_0x020f:
                android.app.NotificationManager r8 = r8.mNoMan
                java.lang.String r0 = com.android.systemui.volume.VolumeDialogControllerImpl.TAG
                r8.setZenMode(r9, r4, r0)
                goto L_0x02f2
            L_0x0218:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                int r0 = r9.arg1
                int r9 = r9.arg2
                if (r9 == 0) goto L_0x0221
                r1 = r2
            L_0x0221:
                java.util.Objects.requireNonNull(r8)
                if (r1 == 0) goto L_0x022d
                android.media.AudioManager r8 = r8.mAudio
                r8.setRingerMode(r0)
                goto L_0x02f2
            L_0x022d:
                android.media.AudioManager r8 = r8.mAudio
                r8.setRingerModeInternal(r0)
                goto L_0x02f2
            L_0x0234:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                java.util.Objects.requireNonNull(r8)
                android.util.ArrayMap<java.lang.Integer, java.lang.Integer> r9 = com.android.systemui.volume.VolumeDialogControllerImpl.STREAMS
                java.util.Set r9 = r9.keySet()
                java.util.Iterator r9 = r9.iterator()
            L_0x0243:
                boolean r0 = r9.hasNext()
                if (r0 == 0) goto L_0x02a3
                java.lang.Object r0 = r9.next()
                java.lang.Integer r0 = (java.lang.Integer) r0
                int r0 = r0.intValue()
                android.media.AudioManager r3 = r8.mAudio
                int r3 = r3.getLastAudibleStreamVolume(r0)
                r8.updateStreamLevelW(r0, r3)
                com.android.systemui.plugins.VolumeDialogController$StreamState r3 = r8.streamStateW(r0)
                android.media.AudioManager r4 = r8.mAudio
                int r4 = r4.getStreamMinVolumeInt(r0)
                r3.levelMin = r4
                com.android.systemui.plugins.VolumeDialogController$StreamState r3 = r8.streamStateW(r0)
                android.media.AudioManager r4 = r8.mAudio
                int r4 = r4.getStreamMaxVolume(r0)
                int r4 = java.lang.Math.max(r2, r4)
                r3.levelMax = r4
                android.media.AudioManager r3 = r8.mAudio
                boolean r3 = r3.isStreamMute(r0)
                r8.updateStreamMuteW(r0, r3)
                com.android.systemui.plugins.VolumeDialogController$StreamState r3 = r8.streamStateW(r0)
                android.media.AudioManager r4 = r8.mAudio
                boolean r4 = r4.isStreamAffectedByMute(r0)
                r3.muteSupported = r4
                android.util.ArrayMap<java.lang.Integer, java.lang.Integer> r4 = com.android.systemui.volume.VolumeDialogControllerImpl.STREAMS
                java.lang.Integer r5 = java.lang.Integer.valueOf(r0)
                java.lang.Object r4 = r4.get(r5)
                java.lang.Integer r4 = (java.lang.Integer) r4
                int r4 = r4.intValue()
                r3.name = r4
                r8.checkRoutedToBluetoothW(r0)
                goto L_0x0243
            L_0x02a3:
                com.android.systemui.volume.VolumeDialogControllerImpl$RingerModeObservers r9 = r8.mRingerModeObservers
                com.android.systemui.util.RingerModeLiveData r9 = r9.mRingerMode
                java.lang.Integer r9 = r9.getValue()
                int r9 = r9.intValue()
                com.android.systemui.plugins.VolumeDialogController$State r0 = r8.mState
                int r3 = r0.ringerModeExternal
                if (r9 != r3) goto L_0x02b6
                goto L_0x02c5
            L_0x02b6:
                r0.ringerModeExternal = r9
                r0 = 12
                java.lang.Object[] r2 = new java.lang.Object[r2]
                java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
                r2[r1] = r9
                com.android.systemui.volume.Events.writeEvent(r0, r2)
            L_0x02c5:
                r8.updateZenModeW()
                r8.updateZenConfig()
                android.app.NotificationManager r9 = r8.mNoMan
                android.content.ComponentName r9 = r9.getEffectsSuppressor()
                r8.updateEffectsSuppressorW(r9)
                com.android.systemui.volume.VolumeDialogControllerImpl$C r9 = r8.mCallbacks
                com.android.systemui.plugins.VolumeDialogController$State r8 = r8.mState
                r9.onStateChanged(r8)
                goto L_0x02f2
            L_0x02dc:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                int r9 = r9.arg1
                java.util.Objects.requireNonNull(r8)
                com.android.systemui.volume.VolumeDialogControllerImpl$C r8 = r8.mCallbacks
                r8.onDismissRequested(r9)
                goto L_0x02f2
            L_0x02e9:
                com.android.systemui.volume.VolumeDialogControllerImpl r8 = com.android.systemui.volume.VolumeDialogControllerImpl.this
                int r0 = r9.arg1
                int r9 = r9.arg2
                r8.onVolumeChangedW(r0, r9)
            L_0x02f2:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogControllerImpl.C1734W.handleMessage(android.os.Message):void");
        }
    }

    public VolumeDialogControllerImpl(Context context, BroadcastDispatcher broadcastDispatcher, RingerModeTracker ringerModeTracker, ThreadFactory threadFactory, AudioManager audioManager, NotificationManager notificationManager, VibratorHelper vibratorHelper, IAudioService iAudioService, AccessibilityManager accessibilityManager, PackageManager packageManager, WakefulnessLifecycle wakefulnessLifecycle) {
        WakefulnessLifecycle wakefulnessLifecycle2 = wakefulnessLifecycle;
        Receiver receiver = new Receiver();
        VolumeDialogController.State state = new VolumeDialogController.State();
        this.mState = state;
        this.mDeviceInteractive = true;
        this.mShowDndTile = true;
        C1733VC vc = new C1733VC();
        this.mVolumeController = vc;
        C17191 r5 = new WakefulnessLifecycle.Observer() {
            public final void onFinishedGoingToSleep() {
                VolumeDialogControllerImpl.this.mDeviceInteractive = false;
            }

            public final void onStartedWakingUp() {
                VolumeDialogControllerImpl.this.mDeviceInteractive = true;
            }
        };
        this.mWakefullnessLifecycleObserver = r5;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mPackageManager = packageManager;
        this.mWakefulnessLifecycle = wakefulnessLifecycle2;
        Events.writeEvent(5, new Object[0]);
        Looper buildLooperOnNewThread = threadFactory.buildLooperOnNewThread("VolumeDialogControllerImpl");
        C1734W w = new C1734W(buildLooperOnNewThread);
        this.mWorker = w;
        this.mRouter2Manager = MediaRouter2Manager.getInstance(applicationContext);
        MediaSessionsCallbacks mediaSessionsCallbacks = new MediaSessionsCallbacks(applicationContext);
        this.mMediaSessionsCallbacksW = mediaSessionsCallbacks;
        this.mMediaSessions = new MediaSessions(applicationContext, buildLooperOnNewThread, mediaSessionsCallbacks);
        this.mAudio = audioManager;
        this.mNoMan = notificationManager;
        SettingObserver settingObserver = new SettingObserver(w);
        RingerModeLiveData ringerMode = ringerModeTracker.getRingerMode();
        RingerModeLiveData ringerModeInternal = ringerModeTracker.getRingerModeInternal();
        RingerModeObservers ringerModeObservers = new RingerModeObservers(ringerMode, ringerModeInternal);
        this.mRingerModeObservers = ringerModeObservers;
        int intValue = ringerMode.getValue().intValue();
        if (intValue != -1) {
            state.ringerModeExternal = intValue;
        }
        ringerMode.observeForever(ringerModeObservers.mRingerModeObserver);
        int intValue2 = ringerModeInternal.getValue().intValue();
        if (intValue2 != -1) {
            state.ringerModeInternal = intValue2;
        }
        ringerModeInternal.observeForever(ringerModeObservers.mRingerModeInternalObserver);
        applicationContext.getContentResolver().registerContentObserver(settingObserver.ZEN_MODE_URI, false, settingObserver);
        applicationContext.getContentResolver().registerContentObserver(settingObserver.ZEN_MODE_CONFIG_URI, false, settingObserver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
        intentFilter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
        intentFilter.addAction("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED");
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        broadcastDispatcher.registerReceiverWithHandler(receiver, intentFilter, w);
        this.mVibrator = vibratorHelper;
        this.mHasVibrator = vibratorHelper.hasVibrator();
        this.mAudioService = iAudioService;
        vc.setA11yMode(accessibilityManager.isAccessibilityVolumeStreamActive() ? 1 : 0);
        Objects.requireNonNull(wakefulnessLifecycle);
        wakefulnessLifecycle2.mObservers.add(r5);
    }

    public final boolean checkRoutedToBluetoothW(int i) {
        boolean z;
        if (i != 3) {
            return false;
        }
        boolean z2 = true;
        if ((this.mAudio.getDevicesForStream(3) & 896) != 0) {
            z = true;
        } else {
            z = false;
        }
        VolumeDialogController.StreamState streamStateW = streamStateW(i);
        if (streamStateW.routedToBluetooth == z) {
            z2 = false;
        } else {
            streamStateW.routedToBluetooth = z;
            if (C1716D.BUG) {
                Log.d(TAG, "updateStreamRoutedToBluetoothW stream=" + i + " routedToBluetooth=" + z);
            }
        }
        return false | z2;
    }

    public final boolean isCaptionStreamOptedOut() {
        return false;
    }

    static {
        ArrayMap<Integer, Integer> arrayMap = new ArrayMap<>();
        STREAMS = arrayMap;
        arrayMap.put(4, Integer.valueOf(C1777R.string.stream_alarm));
        arrayMap.put(6, Integer.valueOf(C1777R.string.stream_bluetooth_sco));
        arrayMap.put(8, Integer.valueOf(C1777R.string.stream_dtmf));
        arrayMap.put(3, Integer.valueOf(C1777R.string.stream_music));
        arrayMap.put(10, Integer.valueOf(C1777R.string.stream_accessibility));
        arrayMap.put(5, Integer.valueOf(C1777R.string.stream_notification));
        arrayMap.put(2, Integer.valueOf(C1777R.string.stream_ring));
        arrayMap.put(1, Integer.valueOf(C1777R.string.stream_system));
        arrayMap.put(7, Integer.valueOf(C1777R.string.stream_system_enforced));
        arrayMap.put(9, Integer.valueOf(C1777R.string.stream_tts));
        arrayMap.put(0, Integer.valueOf(C1777R.string.stream_voice_call));
    }

    public final void addCallback(VolumeDialogController.Callbacks callbacks, Handler handler) {
        C1720C c = this.mCallbacks;
        Objects.requireNonNull(c);
        if (callbacks == null || handler == null) {
            throw new IllegalArgumentException();
        }
        c.mCallbackMap.put(callbacks, handler);
        callbacks.onAccessibilityModeChanged(Boolean.valueOf(this.mShowA11yStream));
    }

    public final boolean areCaptionsEnabled() {
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "odi_captions_enabled", 0, -2) == 1) {
            return true;
        }
        return false;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("VolumeDialogControllerImpl state:");
        printWriter.print("  mDestroyed: ");
        int i = 0;
        printWriter.println(false);
        printWriter.print("  mVolumePolicy: ");
        printWriter.println(this.mVolumePolicy);
        printWriter.print("  mState: ");
        printWriter.println(this.mState.toString(4));
        printWriter.print("  mShowDndTile: ");
        printWriter.println(this.mShowDndTile);
        printWriter.print("  mHasVibrator: ");
        printWriter.println(this.mHasVibrator);
        synchronized (this.mMediaSessionsCallbacksW.mRemoteStreams) {
            printWriter.print("  mRemoteStreams: ");
            printWriter.println(this.mMediaSessionsCallbacksW.mRemoteStreams.values());
        }
        printWriter.print("  mShowA11yStream: ");
        printWriter.println(this.mShowA11yStream);
        printWriter.println();
        MediaSessions mediaSessions = this.mMediaSessions;
        Objects.requireNonNull(mediaSessions);
        printWriter.println("MediaSessions state:");
        printWriter.print("  mInit: ");
        printWriter.println(mediaSessions.mInit);
        printWriter.print("  mRecords.size: ");
        printWriter.println(mediaSessions.mRecords.size());
        for (MediaSessions.MediaControllerRecord mediaControllerRecord : mediaSessions.mRecords.values()) {
            i++;
            MediaController mediaController = mediaControllerRecord.controller;
            StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("  Controller ", i, ": ");
            m.append(mediaController.getPackageName());
            printWriter.println(m.toString());
            Bundle extras = mediaController.getExtras();
            long flags = mediaController.getFlags();
            MediaMetadata metadata = mediaController.getMetadata();
            MediaController.PlaybackInfo playbackInfo = mediaController.getPlaybackInfo();
            PlaybackState playbackState = mediaController.getPlaybackState();
            List<MediaSession.QueueItem> queue = mediaController.getQueue();
            CharSequence queueTitle = mediaController.getQueueTitle();
            int ratingType = mediaController.getRatingType();
            PendingIntent sessionActivity = mediaController.getSessionActivity();
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("    PlaybackState: ");
            m2.append(Util.playbackStateToString(playbackState));
            printWriter.println(m2.toString());
            printWriter.println("    PlaybackInfo: " + Util.playbackInfoToString(playbackInfo));
            if (metadata != null) {
                StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  MediaMetadata.desc=");
                m3.append(metadata.getDescription());
                printWriter.println(m3.toString());
            }
            printWriter.println("    RatingType: " + ratingType);
            printWriter.println("    Flags: " + flags);
            if (extras != null) {
                printWriter.println("    Extras:");
                for (String next : extras.keySet()) {
                    StringBuilder m4 = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("      ", next, "=");
                    m4.append(extras.get(next));
                    printWriter.println(m4.toString());
                }
            }
            if (queueTitle != null) {
                printWriter.println("    QueueTitle: " + queueTitle);
            }
            if (queue != null && !queue.isEmpty()) {
                printWriter.println("    Queue:");
                for (MediaSession.QueueItem queueItem : queue) {
                    printWriter.println("      " + queueItem);
                }
            }
            if (playbackInfo != null) {
                printWriter.println("    sessionActivity: " + sessionActivity);
            }
        }
    }

    public final void getCaptionsComponentState(boolean z) {
        this.mWorker.obtainMessage(16, Boolean.valueOf(z)).sendToTarget();
    }

    public final void getState() {
        this.mWorker.sendEmptyMessage(3);
    }

    public final void notifyVisible(boolean z) {
        this.mWorker.obtainMessage(12, z ? 1 : 0, 0).sendToTarget();
    }

    public final boolean onVolumeChangedW(int i, int i2) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
        Objects.requireNonNull(wakefulnessLifecycle);
        int i3 = wakefulnessLifecycle.mWakefulness;
        int i4 = 3;
        if (i3 == 0 || i3 == 3 || !this.mDeviceInteractive || (i2 & 1) == 0 || !this.mShowVolumeDialog) {
            z = false;
        } else {
            z = true;
        }
        if ((i2 & 4096) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if ((i2 & 2048) != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        if ((i2 & 128) != 0) {
            z4 = true;
        } else {
            z4 = false;
        }
        if (z) {
            z5 = updateActiveStreamW(i) | false;
        } else {
            z5 = false;
        }
        int lastAudibleStreamVolume = this.mAudio.getLastAudibleStreamVolume(i);
        boolean updateStreamLevelW = z5 | updateStreamLevelW(i, lastAudibleStreamVolume);
        if (!z) {
            i4 = i;
        }
        boolean checkRoutedToBluetoothW = checkRoutedToBluetoothW(i4) | updateStreamLevelW;
        if (checkRoutedToBluetoothW) {
            this.mCallbacks.onStateChanged(this.mState);
        }
        if (z) {
            this.mCallbacks.onShowRequested(1);
        }
        if (z3) {
            this.mCallbacks.onShowVibrateHint();
        }
        if (z4) {
            this.mCallbacks.onShowSilentHint();
        }
        if (checkRoutedToBluetoothW && z2) {
            Events.writeEvent(4, Integer.valueOf(i), Integer.valueOf(lastAudibleStreamVolume));
        }
        return checkRoutedToBluetoothW;
    }

    public final void removeCallback(VolumeDialogController.Callbacks callbacks) {
        C1720C c = this.mCallbacks;
        Objects.requireNonNull(c);
        c.mCallbackMap.remove(callbacks);
    }

    public final void setActiveStream(int i) {
        this.mWorker.obtainMessage(11, i, 0).sendToTarget();
    }

    public final void setCaptionsEnabled(boolean z) {
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "odi_captions_enabled", z ? 1 : 0, -2);
    }

    public final void setRingerMode(int i, boolean z) {
        this.mWorker.obtainMessage(4, i, z ? 1 : 0).sendToTarget();
    }

    public final void setStreamVolume(int i, int i2) {
        this.mWorker.obtainMessage(10, i, i2).sendToTarget();
    }

    public final void setVolumePolicy(VolumePolicy volumePolicy) {
        this.mVolumePolicy = volumePolicy;
        if (volumePolicy != null) {
            try {
                this.mAudio.setVolumePolicy(volumePolicy);
            } catch (NoSuchMethodError unused) {
                Log.w(TAG, "No volume policy api");
            }
        }
    }

    public final VolumeDialogController.StreamState streamStateW(int i) {
        VolumeDialogController.StreamState streamState = this.mState.states.get(i);
        if (streamState != null) {
            return streamState;
        }
        VolumeDialogController.StreamState streamState2 = new VolumeDialogController.StreamState();
        this.mState.states.put(i, streamState2);
        return streamState2;
    }

    public final boolean updateActiveStreamW(int i) {
        VolumeDialogController.State state = this.mState;
        if (i == state.activeStream) {
            return false;
        }
        state.activeStream = i;
        Events.writeEvent(2, Integer.valueOf(i));
        if (C1716D.BUG) {
            ExifInterface$$ExternalSyntheticOutline1.m14m("updateActiveStreamW ", i, TAG);
        }
        if (i >= 100) {
            i = -1;
        }
        if (C1716D.BUG) {
            ExifInterface$$ExternalSyntheticOutline1.m14m("forceVolumeControlStream ", i, TAG);
        }
        this.mAudio.forceVolumeControlStream(i);
        return true;
    }

    public final boolean updateEffectsSuppressorW(ComponentName componentName) {
        String str;
        if (Objects.equals(this.mState.effectsSuppressor, componentName)) {
            return false;
        }
        VolumeDialogController.State state = this.mState;
        state.effectsSuppressor = componentName;
        PackageManager packageManager = this.mPackageManager;
        if (componentName == null) {
            str = null;
        } else {
            str = componentName.getPackageName();
            try {
                String trim = Objects.toString(packageManager.getApplicationInfo(str, 0).loadLabel(packageManager), "").trim();
                if (trim.length() > 0) {
                    str = trim;
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        state.effectsSuppressorName = str;
        VolumeDialogController.State state2 = this.mState;
        Events.writeEvent(14, state2.effectsSuppressor, state2.effectsSuppressorName);
        return true;
    }

    public final boolean updateRingerModeInternalW(int i) {
        VolumeDialogController.State state = this.mState;
        if (i == state.ringerModeInternal) {
            return false;
        }
        state.ringerModeInternal = i;
        Events.writeEvent(11, Integer.valueOf(i));
        if (this.mState.ringerModeInternal == 2 && System.currentTimeMillis() - this.mLastToggledRingerOn < 1000) {
            try {
                this.mAudioService.playSoundEffect(5, -2);
            } catch (RemoteException unused) {
            }
        }
        return true;
    }

    public final boolean updateZenConfig() {
        boolean z;
        boolean z2;
        boolean z3;
        NotificationManager.Policy consolidatedNotificationPolicy = this.mNoMan.getConsolidatedNotificationPolicy();
        int i = consolidatedNotificationPolicy.priorityCategories;
        if ((i & 32) == 0) {
            z = true;
        } else {
            z = false;
        }
        if ((i & 64) == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if ((i & 128) == 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        boolean areAllPriorityOnlyRingerSoundsMuted = ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(consolidatedNotificationPolicy);
        VolumeDialogController.State state = this.mState;
        if (state.disallowAlarms == z && state.disallowMedia == z2 && state.disallowRinger == areAllPriorityOnlyRingerSoundsMuted && state.disallowSystem == z3) {
            return false;
        }
        state.disallowAlarms = z;
        state.disallowMedia = z2;
        state.disallowSystem = z3;
        state.disallowRinger = areAllPriorityOnlyRingerSoundsMuted;
        Events.writeEvent(17, "disallowAlarms=" + z + " disallowMedia=" + z2 + " disallowSystem=" + z3 + " disallowRinger=" + areAllPriorityOnlyRingerSoundsMuted);
        return true;
    }

    public final boolean updateZenModeW() {
        int i = Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode", 0);
        VolumeDialogController.State state = this.mState;
        if (state.zenMode == i) {
            return false;
        }
        state.zenMode = i;
        Events.writeEvent(13, Integer.valueOf(i));
        return true;
    }

    public final void userActivity() {
        this.mWorker.removeMessages(13);
        this.mWorker.sendEmptyMessage(13);
    }

    public final void vibrate(VibrationEffect vibrationEffect) {
        this.mVibrator.vibrate(vibrationEffect, SONIFICIATION_VIBRATION_ATTRIBUTES);
    }

    public final void scheduleTouchFeedback() {
        this.mLastToggledRingerOn = System.currentTimeMillis();
    }

    public final boolean updateStreamLevelW(int i, int i2) {
        boolean z;
        VolumeDialogController.StreamState streamStateW = streamStateW(i);
        if (streamStateW.level == i2) {
            return false;
        }
        streamStateW.level = i2;
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 6) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            Events.writeEvent(10, Integer.valueOf(i), Integer.valueOf(i2));
        }
        return true;
    }

    public final boolean updateStreamMuteW(int i, boolean z) {
        boolean z2;
        VolumeDialogController.StreamState streamStateW = streamStateW(i);
        boolean z3 = false;
        if (streamStateW.muted == z) {
            return false;
        }
        streamStateW.muted = z;
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 6) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            Events.writeEvent(15, Integer.valueOf(i), Boolean.valueOf(z));
        }
        if (z) {
            if (i == 2 || i == 5) {
                z3 = true;
            }
            if (z3) {
                updateRingerModeInternalW(this.mRingerModeObservers.mRingerModeInternal.getValue().intValue());
            }
        }
        return true;
    }

    public final AudioManager getAudioManager() {
        return this.mAudio;
    }

    public final boolean hasVibrator() {
        return this.mHasVibrator;
    }
}
