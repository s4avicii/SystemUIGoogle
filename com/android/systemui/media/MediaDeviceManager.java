package com.android.systemui.media;

import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManager;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManagerFactory;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaDeviceManager.kt */
public final class MediaDeviceManager implements MediaDataManager.Listener, Dumpable {
    public final Executor bgExecutor;
    public final MediaControllerFactory controllerFactory;
    public final LinkedHashMap entries = new LinkedHashMap();
    public final Executor fgExecutor;
    public final LinkedHashSet listeners = new LinkedHashSet();
    public final LocalMediaManagerFactory localMediaManagerFactory;
    public final MediaRouter2Manager mr2manager;
    public final MediaMuteAwaitConnectionManagerFactory muteAwaitConnectionManagerFactory;

    /* compiled from: MediaDeviceManager.kt */
    public final class Entry extends MediaController.Callback implements LocalMediaManager.DeviceCallback {
        public MediaDeviceData aboutToConnectDeviceOverride;
        public final MediaController controller;
        public MediaDeviceData current;
        public final String key;
        public final LocalMediaManager localMediaManager;
        public final MediaMuteAwaitConnectionManager muteAwaitConnectionManager;
        public final String oldKey;
        public int playbackType;
        public boolean started;

        public final void onAboutToConnectDeviceChanged(String str, Drawable drawable) {
            MediaDeviceData mediaDeviceData = null;
            if (!(str == null || drawable == null)) {
                mediaDeviceData = new MediaDeviceData(true, drawable, str, (PendingIntent) null);
            }
            this.aboutToConnectDeviceOverride = mediaDeviceData;
            updateCurrent();
        }

        public Entry(String str, String str2, MediaController mediaController, LocalMediaManager localMediaManager2, MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager) {
            this.key = str;
            this.oldKey = str2;
            this.controller = mediaController;
            this.localMediaManager = localMediaManager2;
            this.muteAwaitConnectionManager = mediaMuteAwaitConnectionManager;
        }

        public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            int i;
            if (playbackInfo == null) {
                i = 0;
            } else {
                i = playbackInfo.getPlaybackType();
            }
            if (i != this.playbackType) {
                this.playbackType = i;
                updateCurrent();
            }
        }

        public final void onDeviceListUpdate(ArrayList arrayList) {
            MediaDeviceManager.this.bgExecutor.execute(new MediaDeviceManager$Entry$onDeviceListUpdate$1(this));
        }

        public final void onSelectedDeviceStateChanged(MediaDevice mediaDevice) {
            MediaDeviceManager.this.bgExecutor.execute(new MediaDeviceManager$Entry$onSelectedDeviceStateChanged$1(this));
        }

        public final void updateCurrent() {
            RoutingSessionInfo routingSessionInfo;
            boolean z;
            String str;
            Drawable drawable;
            CharSequence name;
            MediaDeviceData mediaDeviceData = this.aboutToConnectDeviceOverride;
            if (mediaDeviceData == null) {
                LocalMediaManager localMediaManager2 = this.localMediaManager;
                Objects.requireNonNull(localMediaManager2);
                MediaDevice mediaDevice = localMediaManager2.mCurrentConnectedDevice;
                MediaController mediaController = this.controller;
                if (mediaController == null) {
                    routingSessionInfo = null;
                } else {
                    routingSessionInfo = MediaDeviceManager.this.mr2manager.getRoutingSessionForMediaController(mediaController);
                }
                if (mediaDevice == null || (this.controller != null && routingSessionInfo == null)) {
                    z = false;
                } else {
                    z = true;
                }
                if (routingSessionInfo == null || (name = routingSessionInfo.getName()) == null) {
                    str = null;
                } else {
                    str = name.toString();
                }
                if (str == null) {
                    if (mediaDevice == null) {
                        str = null;
                    } else {
                        str = mediaDevice.getName();
                    }
                }
                if (mediaDevice == null) {
                    drawable = null;
                } else {
                    drawable = mediaDevice.getIconWithoutBackground();
                }
                MediaDeviceData mediaDeviceData2 = new MediaDeviceData(z, drawable, str, (PendingIntent) null);
                if (!this.started || !Intrinsics.areEqual(mediaDeviceData2, this.current)) {
                    this.current = mediaDeviceData2;
                    MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
                    mediaDeviceManager.fgExecutor.execute(new MediaDeviceManager$Entry$current$1(mediaDeviceManager, this, mediaDeviceData2));
                }
            } else if (!this.started || !Intrinsics.areEqual(mediaDeviceData, this.current)) {
                this.current = mediaDeviceData;
                MediaDeviceManager mediaDeviceManager2 = MediaDeviceManager.this;
                mediaDeviceManager2.fgExecutor.execute(new MediaDeviceManager$Entry$current$1(mediaDeviceManager2, this, mediaDeviceData));
            }
        }
    }

    /* compiled from: MediaDeviceManager.kt */
    public interface Listener {
        void onKeyRemoved(String str);

        void onMediaDeviceChanged(String str, String str2, MediaDeviceData mediaDeviceData);
    }

    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z, boolean z2) {
    }

    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("MediaDeviceManager state:");
        this.entries.forEach(new MediaDeviceManager$dump$1$1(printWriter, fileDescriptor, printWriter, strArr));
    }

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i) {
        MediaController mediaController;
        MediaSession.Token token;
        Entry entry;
        if (!(str2 == null || Intrinsics.areEqual(str2, str) || (entry = (Entry) this.entries.remove(str2)) == null)) {
            MediaDeviceManager.this.bgExecutor.execute(new MediaDeviceManager$Entry$stop$1(entry));
        }
        Entry entry2 = (Entry) this.entries.get(str);
        MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager = null;
        if (entry2 != null) {
            MediaController mediaController2 = entry2.controller;
            if (mediaController2 == null) {
                token = null;
            } else {
                token = mediaController2.getSessionToken();
            }
            if (Intrinsics.areEqual(token, mediaData.token)) {
                return;
            }
        }
        if (entry2 != null) {
            MediaDeviceManager.this.bgExecutor.execute(new MediaDeviceManager$Entry$stop$1(entry2));
        }
        MediaDeviceData mediaDeviceData = mediaData.device;
        if (mediaDeviceData != null) {
            for (Listener onMediaDeviceChanged : this.listeners) {
                onMediaDeviceChanged.onMediaDeviceChanged(str, str2, mediaDeviceData);
            }
            return;
        }
        MediaSession.Token token2 = mediaData.token;
        if (token2 == null) {
            mediaController = null;
        } else {
            MediaControllerFactory mediaControllerFactory = this.controllerFactory;
            Objects.requireNonNull(mediaControllerFactory);
            mediaController = new MediaController(mediaControllerFactory.mContext, token2);
        }
        LocalMediaManagerFactory localMediaManagerFactory2 = this.localMediaManagerFactory;
        String str3 = mediaData.packageName;
        Objects.requireNonNull(localMediaManagerFactory2);
        LocalMediaManager localMediaManager = new LocalMediaManager(localMediaManagerFactory2.context, localMediaManagerFactory2.localBluetoothManager, new InfoMediaManager(localMediaManagerFactory2.context, str3, localMediaManagerFactory2.localBluetoothManager), str3);
        MediaMuteAwaitConnectionManagerFactory mediaMuteAwaitConnectionManagerFactory = this.muteAwaitConnectionManagerFactory;
        Objects.requireNonNull(mediaMuteAwaitConnectionManagerFactory);
        MediaFlags mediaFlags = mediaMuteAwaitConnectionManagerFactory.mediaFlags;
        Objects.requireNonNull(mediaFlags);
        if (mediaFlags.featureFlags.isEnabled(Flags.MEDIA_MUTE_AWAIT)) {
            mediaMuteAwaitConnectionManager = new MediaMuteAwaitConnectionManager(mediaMuteAwaitConnectionManagerFactory.mainExecutor, localMediaManager, mediaMuteAwaitConnectionManagerFactory.context, mediaMuteAwaitConnectionManagerFactory.deviceIconUtil);
        }
        Entry entry3 = new Entry(str, str2, mediaController, localMediaManager, mediaMuteAwaitConnectionManager);
        this.entries.put(str, entry3);
        this.bgExecutor.execute(new MediaDeviceManager$Entry$start$1(entry3));
    }

    public final void onMediaDataRemoved(String str) {
        Entry entry = (Entry) this.entries.remove(str);
        if (entry != null) {
            MediaDeviceManager.this.bgExecutor.execute(new MediaDeviceManager$Entry$stop$1(entry));
        }
        if (entry != null) {
            for (Listener onKeyRemoved : this.listeners) {
                onKeyRemoved.onKeyRemoved(str);
            }
        }
    }

    public MediaDeviceManager(MediaControllerFactory mediaControllerFactory, LocalMediaManagerFactory localMediaManagerFactory2, MediaRouter2Manager mediaRouter2Manager, MediaMuteAwaitConnectionManagerFactory mediaMuteAwaitConnectionManagerFactory, Executor executor, Executor executor2, DumpManager dumpManager) {
        this.controllerFactory = mediaControllerFactory;
        this.localMediaManagerFactory = localMediaManagerFactory2;
        this.mr2manager = mediaRouter2Manager;
        this.muteAwaitConnectionManagerFactory = mediaMuteAwaitConnectionManagerFactory;
        this.fgExecutor = executor;
        this.bgExecutor = executor2;
        dumpManager.registerDumpable(MediaDeviceManager.class.getName(), this);
    }
}
