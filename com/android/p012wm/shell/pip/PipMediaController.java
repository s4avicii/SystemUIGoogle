package com.android.p012wm.shell.pip;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.os.HandlerExecutor;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.PipMediaController */
public final class PipMediaController {
    public final ArrayList<ActionListener> mActionListeners = new ArrayList<>();
    public final Context mContext;
    public final HandlerExecutor mHandlerExecutor;
    public final Handler mMainHandler;
    public final C18871 mMediaActionReceiver;
    public MediaController mMediaController;
    public final MediaSessionManager mMediaSessionManager;
    public final ArrayList<MetadataListener> mMetadataListeners = new ArrayList<>();
    public RemoteAction mNextAction;
    public RemoteAction mPauseAction;
    public RemoteAction mPlayAction;
    public final C18882 mPlaybackChangedListener = new MediaController.Callback() {
        public final void onMetadataChanged(MediaMetadata mediaMetadata) {
            PipMediaController pipMediaController = PipMediaController.this;
            Objects.requireNonNull(pipMediaController);
            if (!pipMediaController.mMetadataListeners.isEmpty()) {
                pipMediaController.mMetadataListeners.forEach(new PipMediaController$$ExternalSyntheticLambda2(mediaMetadata, 0));
            }
        }

        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            PipMediaController pipMediaController = PipMediaController.this;
            Objects.requireNonNull(pipMediaController);
            if (!pipMediaController.mActionListeners.isEmpty()) {
                pipMediaController.mActionListeners.forEach(new PipMediaController$$ExternalSyntheticLambda1(pipMediaController.getMediaActions(), 0));
            }
        }
    };
    public RemoteAction mPrevAction;
    public final PipMediaController$$ExternalSyntheticLambda0 mSessionsChangedListener = new PipMediaController$$ExternalSyntheticLambda0(this);

    /* renamed from: com.android.wm.shell.pip.PipMediaController$ActionListener */
    public interface ActionListener {
        void onMediaActionsChanged(List<RemoteAction> list);
    }

    /* renamed from: com.android.wm.shell.pip.PipMediaController$MetadataListener */
    public interface MetadataListener {
        void onMediaMetadataChanged(MediaMetadata mediaMetadata);
    }

    public final RemoteAction getDefaultRemoteAction(int i, int i2, String str) {
        String string = this.mContext.getString(i);
        Intent intent = new Intent(str);
        intent.setPackage(this.mContext.getPackageName());
        return new RemoteAction(Icon.createWithResource(this.mContext, i2), string, string, PendingIntent.getBroadcast(this.mContext, 0, intent, 201326592));
    }

    @SuppressLint({"NewApi"})
    public final List<RemoteAction> getMediaActions() {
        boolean z;
        MediaController mediaController = this.mMediaController;
        if (mediaController == null || mediaController.getPlaybackState() == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        boolean isActive = this.mMediaController.getPlaybackState().isActive();
        long actions = this.mMediaController.getPlaybackState().getActions();
        RemoteAction remoteAction = this.mPrevAction;
        boolean z2 = true;
        if ((16 & actions) != 0) {
            z = true;
        } else {
            z = false;
        }
        remoteAction.setEnabled(z);
        arrayList.add(this.mPrevAction);
        if (!isActive && (4 & actions) != 0) {
            arrayList.add(this.mPlayAction);
        } else if (isActive && (2 & actions) != 0) {
            arrayList.add(this.mPauseAction);
        }
        RemoteAction remoteAction2 = this.mNextAction;
        if ((actions & 32) == 0) {
            z2 = false;
        }
        remoteAction2.setEnabled(z2);
        arrayList.add(this.mNextAction);
        return arrayList;
    }

    public final void resolveActiveMediaController(List<MediaController> list) {
        ComponentName componentName;
        if (!(list == null || (componentName = (ComponentName) PipUtils.getTopPipActivity(this.mContext).first) == null)) {
            for (int i = 0; i < list.size(); i++) {
                MediaController mediaController = list.get(i);
                if (mediaController.getPackageName().equals(componentName.getPackageName())) {
                    setActiveMediaController(mediaController);
                    return;
                }
            }
        }
        setActiveMediaController((MediaController) null);
    }

    public final void setActiveMediaController(MediaController mediaController) {
        MediaMetadata mediaMetadata;
        MediaController mediaController2 = this.mMediaController;
        if (mediaController != mediaController2) {
            if (mediaController2 != null) {
                mediaController2.unregisterCallback(this.mPlaybackChangedListener);
            }
            this.mMediaController = mediaController;
            if (mediaController != null) {
                mediaController.registerCallback(this.mPlaybackChangedListener, this.mMainHandler);
            }
            if (!this.mActionListeners.isEmpty()) {
                this.mActionListeners.forEach(new PipMediaController$$ExternalSyntheticLambda1(getMediaActions(), 0));
            }
            MediaController mediaController3 = this.mMediaController;
            if (mediaController3 != null) {
                mediaMetadata = mediaController3.getMetadata();
            } else {
                mediaMetadata = null;
            }
            if (!this.mMetadataListeners.isEmpty()) {
                this.mMetadataListeners.forEach(new PipMediaController$$ExternalSyntheticLambda2(mediaMetadata, 0));
            }
        }
    }

    public PipMediaController(Context context, Handler handler) {
        C18871 r1 = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                MediaController mediaController = PipMediaController.this.mMediaController;
                if (mediaController != null && mediaController.getTransportControls() != null) {
                    String action = intent.getAction();
                    Objects.requireNonNull(action);
                    char c = 65535;
                    switch (action.hashCode()) {
                        case 40376596:
                            if (action.equals("com.android.wm.shell.pip.NEXT")) {
                                c = 0;
                                break;
                            }
                            break;
                        case 40442197:
                            if (action.equals("com.android.wm.shell.pip.PLAY")) {
                                c = 1;
                                break;
                            }
                            break;
                        case 40448084:
                            if (action.equals("com.android.wm.shell.pip.PREV")) {
                                c = 2;
                                break;
                            }
                            break;
                        case 1253399509:
                            if (action.equals("com.android.wm.shell.pip.PAUSE")) {
                                c = 3;
                                break;
                            }
                            break;
                    }
                    switch (c) {
                        case 0:
                            PipMediaController.this.mMediaController.getTransportControls().skipToNext();
                            return;
                        case 1:
                            PipMediaController.this.mMediaController.getTransportControls().play();
                            return;
                        case 2:
                            PipMediaController.this.mMediaController.getTransportControls().skipToPrevious();
                            return;
                        case 3:
                            PipMediaController.this.mMediaController.getTransportControls().pause();
                            return;
                        default:
                            return;
                    }
                }
            }
        };
        this.mMediaActionReceiver = r1;
        this.mContext = context;
        this.mMainHandler = handler;
        this.mHandlerExecutor = new HandlerExecutor(handler);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.wm.shell.pip.PLAY");
        intentFilter.addAction("com.android.wm.shell.pip.PAUSE");
        intentFilter.addAction("com.android.wm.shell.pip.NEXT");
        intentFilter.addAction("com.android.wm.shell.pip.PREV");
        context.registerReceiverForAllUsers(r1, intentFilter, "com.android.systemui.permission.SELF", handler, 2);
        this.mPauseAction = getDefaultRemoteAction(C1777R.string.pip_pause, C1777R.C1778drawable.pip_ic_pause_white, "com.android.wm.shell.pip.PAUSE");
        this.mPlayAction = getDefaultRemoteAction(C1777R.string.pip_play, C1777R.C1778drawable.pip_ic_play_arrow_white, "com.android.wm.shell.pip.PLAY");
        this.mNextAction = getDefaultRemoteAction(C1777R.string.pip_skip_to_next, C1777R.C1778drawable.pip_ic_skip_next_white, "com.android.wm.shell.pip.NEXT");
        this.mPrevAction = getDefaultRemoteAction(C1777R.string.pip_skip_to_prev, C1777R.C1778drawable.pip_ic_skip_previous_white, "com.android.wm.shell.pip.PREV");
        this.mMediaSessionManager = (MediaSessionManager) context.getSystemService(MediaSessionManager.class);
    }
}
