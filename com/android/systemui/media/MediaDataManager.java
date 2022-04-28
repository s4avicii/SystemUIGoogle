package com.android.systemui.media;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceManager;
import android.app.smartspace.SmartspaceSession;
import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Icon;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.keyguard.KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Assert;
import com.android.systemui.util.Utils;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaDataManager.kt */
public final class MediaDataManager implements Dumpable, BcSmartspaceDataPlugin.SmartspaceTargetListener {
    public final ActivityStarter activityStarter;
    public boolean allowMediaRecommendations;
    public final Executor backgroundExecutor;
    public final int bgColor;
    public final Context context;
    public final DelayableExecutor foregroundExecutor;
    public final LinkedHashSet internalListeners;
    public final MediaControllerFactory mediaControllerFactory;
    public final MediaDataFilter mediaDataFilter;
    public final LinkedHashMap<String, MediaData> mediaEntries = new LinkedHashMap<>();
    public final MediaFlags mediaFlags;
    public SmartspaceMediaData smartspaceMediaData = MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA;
    public final SmartspaceMediaDataProvider smartspaceMediaDataProvider;
    public SmartspaceSession smartspaceSession;
    public final SystemClock systemClock;
    public final int themeText;
    public boolean useMediaResumption;
    public final boolean useQsMediaPlayer;

    /* compiled from: MediaDataManager.kt */
    public interface Listener {

        /* compiled from: MediaDataManager.kt */
        public static final class DefaultImpls {
            public static /* synthetic */ void onMediaDataLoaded$default(Listener listener, String str, String str2, MediaData mediaData, boolean z, int i, int i2) {
                if ((i2 & 8) != 0) {
                    z = true;
                }
                boolean z2 = z;
                if ((i2 & 16) != 0) {
                    i = 0;
                }
                listener.onMediaDataLoaded(str, str2, mediaData, z2, i);
            }
        }

        void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i);

        void onMediaDataRemoved(String str);

        void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z, boolean z2);

        void onSmartspaceMediaDataRemoved(String str, boolean z);
    }

    public MediaDataManager(Context context2, Executor executor, DelayableExecutor delayableExecutor, MediaControllerFactory mediaControllerFactory2, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, MediaDataFilter mediaDataFilter2, ActivityStarter activityStarter2, SmartspaceMediaDataProvider smartspaceMediaDataProvider2, SystemClock systemClock2, TunerService tunerService, MediaFlags mediaFlags2) {
        MediaTimeoutListener mediaTimeoutListener2 = mediaTimeoutListener;
        MediaResumeListener mediaResumeListener2 = mediaResumeListener;
        MediaSessionBasedFilter mediaSessionBasedFilter2 = mediaSessionBasedFilter;
        MediaDeviceManager mediaDeviceManager2 = mediaDeviceManager;
        MediaDataCombineLatest mediaDataCombineLatest2 = mediaDataCombineLatest;
        MediaDataFilter mediaDataFilter3 = mediaDataFilter2;
        SmartspaceMediaDataProvider smartspaceMediaDataProvider3 = smartspaceMediaDataProvider2;
        boolean useMediaResumption2 = Utils.useMediaResumption(context2);
        boolean useQsMediaPlayer2 = Utils.useQsMediaPlayer(context2);
        this.context = context2;
        this.backgroundExecutor = executor;
        this.foregroundExecutor = delayableExecutor;
        this.mediaControllerFactory = mediaControllerFactory2;
        this.mediaDataFilter = mediaDataFilter3;
        this.activityStarter = activityStarter2;
        this.smartspaceMediaDataProvider = smartspaceMediaDataProvider3;
        this.useMediaResumption = useMediaResumption2;
        this.useQsMediaPlayer = useQsMediaPlayer2;
        this.systemClock = systemClock2;
        this.mediaFlags = mediaFlags2;
        this.themeText = com.android.settingslib.Utils.getColorAttr(context2, 16842806).getDefaultColor();
        this.bgColor = context2.getColor(C1777R.color.material_dynamic_secondary95);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.internalListeners = linkedHashSet;
        boolean z = true;
        this.allowMediaRecommendations = (!Utils.useQsMediaPlayer(context2) || Settings.Secure.getInt(context2.getContentResolver(), "qs_media_recommend", 1) <= 0) ? false : z;
        MediaDataManager$appChangeReceiver$1 mediaDataManager$appChangeReceiver$1 = new MediaDataManager$appChangeReceiver$1(this);
        dumpManager.registerDumpable("MediaDataManager", this);
        linkedHashSet.add(mediaTimeoutListener2);
        linkedHashSet.add(mediaResumeListener2);
        linkedHashSet.add(mediaSessionBasedFilter2);
        Objects.requireNonNull(mediaSessionBasedFilter);
        mediaSessionBasedFilter2.listeners.add(mediaDeviceManager2);
        mediaSessionBasedFilter2.listeners.add(mediaDataCombineLatest2);
        mediaDeviceManager2.listeners.add(mediaDataCombineLatest2);
        mediaDataCombineLatest2.listeners.add(mediaDataFilter3);
        mediaTimeoutListener2.timeoutCallback = new Function2<String, Boolean, Unit>() {
            public final Object invoke(Object obj, Object obj2) {
                boolean booleanValue = ((Boolean) obj2).booleanValue();
                MediaDataManager.this.mo8906x855293df((String) obj, booleanValue, false);
                return Unit.INSTANCE;
            }
        };
        mediaResumeListener2.mediaDataManager = this;
        mediaResumeListener2.tunerService.addTunable(new MediaResumeListener$setManager$1(mediaResumeListener2), "qs_media_resumption");
        mediaDataFilter3.mediaDataManager = this;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, mediaDataManager$appChangeReceiver$1, new IntentFilter("android.intent.action.PACKAGES_SUSPENDED"), (Executor) null, UserHandle.ALL, 16);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addDataScheme("package");
        context2.registerReceiver(mediaDataManager$appChangeReceiver$1, intentFilter);
        smartspaceMediaDataProvider3.registerListener(this);
        SmartspaceSession createSmartspaceSession = ((SmartspaceManager) context2.getSystemService(SmartspaceManager.class)).createSmartspaceSession(new SmartspaceConfig.Builder(context2, "media_data_manager").build());
        this.smartspaceSession = createSmartspaceSession;
        if (createSmartspaceSession != null) {
            createSmartspaceSession.addOnTargetsAvailableListener(Executors.newCachedThreadPool(), new MediaDataManager$2$1(this));
        }
        SmartspaceSession smartspaceSession2 = this.smartspaceSession;
        if (smartspaceSession2 != null) {
            smartspaceSession2.requestSmartspaceUpdate();
        }
        tunerService.addTunable(new TunerService.Tunable() {
            public final void onTuningChanged(String str, String str2) {
                MediaDataManager mediaDataManager = MediaDataManager.this;
                Context context = mediaDataManager.context;
                boolean z = true;
                int i = Settings.Secure.getInt(context.getContentResolver(), "qs_media_recommend", 1);
                if (!Utils.useQsMediaPlayer(context) || i <= 0) {
                    z = false;
                }
                mediaDataManager.allowMediaRecommendations = z;
                MediaDataManager mediaDataManager2 = MediaDataManager.this;
                if (!mediaDataManager2.allowMediaRecommendations) {
                    SmartspaceMediaData smartspaceMediaData = mediaDataManager2.smartspaceMediaData;
                    Objects.requireNonNull(smartspaceMediaData);
                    mediaDataManager2.dismissSmartspaceRecommendation(smartspaceMediaData.targetId, 0);
                }
            }
        }, "qs_media_recommend");
    }

    public final MediaAction getStandardAction(MediaController mediaController, long j, long j2) {
        if ((j & j2) == 0) {
            return null;
        }
        if (j2 == 4) {
            return new MediaAction(Icon.createWithResource(this.context, C1777R.C1778drawable.ic_media_play), new MediaDataManager$getStandardAction$1(mediaController), this.context.getString(C1777R.string.controls_media_button_play));
        }
        if (j2 == 2) {
            return new MediaAction(Icon.createWithResource(this.context, C1777R.C1778drawable.ic_media_pause), new MediaDataManager$getStandardAction$2(mediaController), this.context.getString(C1777R.string.controls_media_button_pause));
        }
        if (j2 == 16) {
            return new MediaAction(Icon.createWithResource(this.context, C1777R.C1778drawable.ic_media_prev), new MediaDataManager$getStandardAction$3(mediaController), this.context.getString(C1777R.string.controls_media_button_prev));
        }
        if (j2 == 32) {
            return new MediaAction(Icon.createWithResource(this.context, C1777R.C1778drawable.ic_media_next), new MediaDataManager$getStandardAction$4(mediaController), this.context.getString(C1777R.string.controls_media_button_next));
        }
        return null;
    }

    public final void addListener(Listener listener) {
        MediaDataFilter mediaDataFilter2 = this.mediaDataFilter;
        Objects.requireNonNull(mediaDataFilter2);
        mediaDataFilter2._listeners.add(listener);
    }

    public final boolean dismissMediaData(String str, long j) {
        boolean z;
        if (this.mediaEntries.get(str) != null) {
            z = true;
        } else {
            z = false;
        }
        this.backgroundExecutor.execute(new MediaDataManager$dismissMediaData$1(this, str));
        this.foregroundExecutor.executeDelayed(new MediaDataManager$dismissMediaData$2(this, str), j);
        return z;
    }

    public final void dismissSmartspaceRecommendation(String str, long j) {
        SmartspaceMediaData smartspaceMediaData2 = this.smartspaceMediaData;
        Objects.requireNonNull(smartspaceMediaData2);
        if (Intrinsics.areEqual(smartspaceMediaData2.targetId, str)) {
            Log.d("MediaDataManager", "Dismissing Smartspace media target");
            SmartspaceMediaData smartspaceMediaData3 = this.smartspaceMediaData;
            Objects.requireNonNull(smartspaceMediaData3);
            if (smartspaceMediaData3.isActive) {
                SmartspaceMediaData smartspaceMediaData4 = MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA;
                SmartspaceMediaData smartspaceMediaData5 = this.smartspaceMediaData;
                Objects.requireNonNull(smartspaceMediaData5);
                this.smartspaceMediaData = SmartspaceMediaData.copy$default(smartspaceMediaData4, smartspaceMediaData5.targetId, false, false, (Intent) null, 0, 0, 510);
            }
            this.foregroundExecutor.executeDelayed(new MediaDataManager$dismissSmartspaceRecommendation$1(this), j);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(Intrinsics.stringPlus("internalListeners: ", this.internalListeners));
        printWriter.println(Intrinsics.stringPlus("externalListeners: ", this.mediaDataFilter.mo8888xef59304f()));
        printWriter.println(Intrinsics.stringPlus("mediaEntries: ", this.mediaEntries));
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.useMediaResumption, "useMediaResumption: ", printWriter);
    }

    public final MediaAction getResumeMediaAction(Runnable runnable) {
        return new MediaAction(Icon.createWithResource(this.context, C1777R.C1778drawable.ic_media_play).setTint(this.themeText), runnable, this.context.getString(C1777R.string.controls_media_resume));
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean hasActiveMedia() {
        /*
            r4 = this;
            com.android.systemui.media.MediaDataFilter r4 = r4.mediaDataFilter
            java.util.Objects.requireNonNull(r4)
            java.util.LinkedHashMap<java.lang.String, com.android.systemui.media.MediaData> r0 = r4.userEntries
            boolean r1 = r0.isEmpty()
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0010
            goto L_0x0033
        L_0x0010:
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0018:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0033
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r1 = r1.getValue()
            com.android.systemui.media.MediaData r1 = (com.android.systemui.media.MediaData) r1
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.active
            if (r1 == 0) goto L_0x0018
            r0 = r2
            goto L_0x0034
        L_0x0033:
            r0 = r3
        L_0x0034:
            if (r0 != 0) goto L_0x0041
            com.android.systemui.media.SmartspaceMediaData r4 = r4.smartspaceMediaData
            java.util.Objects.requireNonNull(r4)
            boolean r4 = r4.isActive
            if (r4 == 0) goto L_0x0040
            goto L_0x0041
        L_0x0040:
            r2 = r3
        L_0x0041:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaDataManager.hasActiveMedia():boolean");
    }

    public final Bitmap loadBitmapFromUri(Uri uri) {
        if (uri.getScheme() == null) {
            return null;
        }
        if (!uri.getScheme().equals("content") && !uri.getScheme().equals("android.resource") && !uri.getScheme().equals("file")) {
            return null;
        }
        try {
            return ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.context.getContentResolver(), uri), MediaDataManager$loadBitmapFromUri$1.INSTANCE);
        } catch (IOException e) {
            Log.e("MediaDataManager", "Unable to load bitmap", e);
            return null;
        } catch (RuntimeException e2) {
            Log.e("MediaDataManager", "Unable to load bitmap", e2);
            return null;
        }
    }

    public final void notifyMediaDataLoaded(String str, String str2, MediaData mediaData) {
        for (Listener onMediaDataLoaded$default : this.internalListeners) {
            Listener.DefaultImpls.onMediaDataLoaded$default(onMediaDataLoaded$default, str, str2, mediaData, false, 0, 24);
        }
    }

    public final void notifyMediaDataRemoved(String str) {
        for (Listener onMediaDataRemoved : this.internalListeners) {
            onMediaDataRemoved.onMediaDataRemoved(str);
        }
    }

    public final void notifySmartspaceMediaDataRemoved(String str, boolean z) {
        for (Listener onSmartspaceMediaDataRemoved : this.internalListeners) {
            onSmartspaceMediaDataRemoved.onSmartspaceMediaDataRemoved(str, z);
        }
    }

    public final void onNotificationAdded(String str, StatusBarNotification statusBarNotification) {
        if (this.useQsMediaPlayer) {
            String[] strArr = MediaDataManagerKt.ART_URIS;
            if (statusBarNotification.getNotification().isMediaNotification()) {
                Assert.isMainThread();
                String packageName = statusBarNotification.getPackageName();
                if (this.mediaEntries.containsKey(str)) {
                    packageName = str;
                } else if (!this.mediaEntries.containsKey(packageName)) {
                    packageName = null;
                }
                if (packageName == null) {
                    this.mediaEntries.put(str, MediaData.copy$default(MediaDataManagerKt.LOADING, (List) null, (List) null, statusBarNotification.getPackageName(), (MediaDeviceData) null, false, (MediaResumeListener$getResumeAction$1) null, false, false, (Boolean) null, false, 16775167));
                } else if (!Intrinsics.areEqual(packageName, str)) {
                    MediaData remove = this.mediaEntries.remove(packageName);
                    Intrinsics.checkNotNull(remove);
                    this.mediaEntries.put(str, remove);
                }
                this.backgroundExecutor.execute(new MediaDataManager$loadMediaData$1(this, str, statusBarNotification, packageName));
                return;
            }
        }
        onNotificationRemoved(str);
    }

    public final void onNotificationRemoved(String str) {
        Runnable runnable;
        boolean z;
        String str2 = str;
        Assert.isMainThread();
        MediaData remove = this.mediaEntries.remove(str2);
        if (this.useMediaResumption) {
            Boolean bool = null;
            if (remove == null) {
                runnable = null;
            } else {
                runnable = remove.resumeAction;
            }
            if (runnable != null) {
                boolean z2 = true;
                if (remove != null) {
                    if (remove.playbackLocation == 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    bool = Boolean.valueOf(z);
                }
                if (bool.booleanValue()) {
                    Log.d("MediaDataManager", "Not removing " + str2 + " because resumable");
                    Objects.requireNonNull(remove);
                    Runnable runnable2 = remove.resumeAction;
                    Intrinsics.checkNotNull(runnable2);
                    MediaData copy$default = MediaData.copy$default(remove, Collections.singletonList(getResumeMediaAction(runnable2)), Collections.singletonList(0), (String) null, (MediaDeviceData) null, false, (MediaResumeListener$getResumeAction$1) null, true, false, Boolean.FALSE, true, 10185983);
                    String str3 = remove.packageName;
                    if (this.mediaEntries.put(str3, copy$default) != null) {
                        z2 = false;
                    }
                    if (z2) {
                        notifyMediaDataLoaded(str3, str2, copy$default);
                        return;
                    }
                    notifyMediaDataRemoved(str);
                    notifyMediaDataLoaded(str3, str3, copy$default);
                    return;
                }
            }
        }
        if (remove != null) {
            notifyMediaDataRemoved(str);
        }
    }

    public final void onSmartspaceTargetsUpdated(List<? extends Parcelable> list) {
        Intent intent;
        String str;
        SmartspaceMediaData smartspaceMediaData2;
        String string;
        if (!this.allowMediaRecommendations) {
            Log.d("MediaDataManager", "Smartspace recommendation is disabled in Settings.");
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (T next : list) {
            if (next instanceof SmartspaceTarget) {
                arrayList.add(next);
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            SmartspaceMediaData smartspaceMediaData3 = this.smartspaceMediaData;
            Objects.requireNonNull(smartspaceMediaData3);
            if (smartspaceMediaData3.isActive) {
                Log.d("MediaDataManager", "Set Smartspace media to be inactive for the data update");
                SmartspaceMediaData smartspaceMediaData4 = MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA;
                SmartspaceMediaData smartspaceMediaData5 = this.smartspaceMediaData;
                Objects.requireNonNull(smartspaceMediaData5);
                SmartspaceMediaData copy$default = SmartspaceMediaData.copy$default(smartspaceMediaData4, smartspaceMediaData5.targetId, false, false, (Intent) null, 0, 0, 510);
                this.smartspaceMediaData = copy$default;
                notifySmartspaceMediaDataRemoved(copy$default.targetId, false);
            }
        } else if (size != 1) {
            Log.wtf("MediaDataManager", "More than 1 Smartspace Media Update. Resetting the status...");
            SmartspaceMediaData smartspaceMediaData6 = this.smartspaceMediaData;
            Objects.requireNonNull(smartspaceMediaData6);
            notifySmartspaceMediaDataRemoved(smartspaceMediaData6.targetId, false);
            this.smartspaceMediaData = MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA;
        } else {
            SmartspaceTarget smartspaceTarget = (SmartspaceTarget) arrayList.get(0);
            SmartspaceMediaData smartspaceMediaData7 = this.smartspaceMediaData;
            Objects.requireNonNull(smartspaceMediaData7);
            if (!Intrinsics.areEqual(smartspaceMediaData7.targetId, smartspaceTarget.getSmartspaceTargetId())) {
                Log.d("MediaDataManager", "Forwarding Smartspace media update.");
                if (smartspaceTarget.getBaseAction() == null || smartspaceTarget.getBaseAction().getExtras() == null) {
                    intent = null;
                } else {
                    intent = (Intent) smartspaceTarget.getBaseAction().getExtras().getParcelable("dismiss_intent");
                }
                List<SmartspaceAction> iconGrid = smartspaceTarget.getIconGrid();
                if (iconGrid == null || iconGrid.isEmpty()) {
                    Log.w("MediaDataManager", "Empty or null media recommendation list.");
                } else {
                    for (SmartspaceAction extras : iconGrid) {
                        Bundle extras2 = extras.getExtras();
                        if (extras2 != null && (string = extras2.getString("package_name")) != null) {
                            str = string;
                            break;
                        }
                    }
                    Log.w("MediaDataManager", "No valid package name is provided.");
                }
                str = null;
                if (str == null) {
                    smartspaceMediaData2 = SmartspaceMediaData.copy$default(MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA, smartspaceTarget.getSmartspaceTargetId(), true, false, intent, 0, smartspaceTarget.getCreationTimeMillis(), 188);
                } else {
                    smartspaceMediaData2 = new SmartspaceMediaData(smartspaceTarget.getSmartspaceTargetId(), true, true, str, smartspaceTarget.getBaseAction(), smartspaceTarget.getIconGrid(), intent, 0, smartspaceTarget.getCreationTimeMillis());
                }
                this.smartspaceMediaData = smartspaceMediaData2;
                String str2 = smartspaceMediaData2.targetId;
                for (Listener onSmartspaceMediaDataLoaded : this.internalListeners) {
                    onSmartspaceMediaDataLoaded.onSmartspaceMediaDataLoaded(str2, smartspaceMediaData2, false, false);
                }
            }
        }
    }

    public final void setResumeAction(String str, MediaResumeListener$getResumeAction$1 mediaResumeListener$getResumeAction$1) {
        MediaData mediaData = this.mediaEntries.get(str);
        if (mediaData != null) {
            mediaData.resumeAction = mediaResumeListener$getResumeAction$1;
            mediaData.hasCheckedForResume = true;
        }
    }

    /* renamed from: setTimedOut$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo8906x855293df(String str, boolean z, boolean z2) {
        MediaData mediaData = this.mediaEntries.get(str);
        if (mediaData != null) {
            boolean z3 = !z;
            if (mediaData.active != z3 || z2) {
                mediaData.active = z3;
                Log.d("MediaDataManager", "Updating " + str + " timedOut: " + z);
                onMediaDataLoaded(str, str, mediaData);
            } else if (mediaData.resumption) {
                Log.d("MediaDataManager", Intrinsics.stringPlus("timing out resume player ", str));
                dismissMediaData(str, 0);
            }
        }
    }

    public static final void access$removeAllForPackage(MediaDataManager mediaDataManager, String str) {
        Objects.requireNonNull(mediaDataManager);
        Assert.isMainThread();
        LinkedHashMap<String, MediaData> linkedHashMap = mediaDataManager.mediaEntries;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry next : linkedHashMap.entrySet()) {
            MediaData mediaData = (MediaData) next.getValue();
            Objects.requireNonNull(mediaData);
            if (Intrinsics.areEqual(mediaData.packageName, str)) {
                linkedHashMap2.put(next.getKey(), next.getValue());
            }
        }
        for (Map.Entry key : linkedHashMap2.entrySet()) {
            String str2 = (String) key.getKey();
            mediaDataManager.mediaEntries.remove(str2);
            mediaDataManager.notifyMediaDataRemoved(str2);
        }
    }

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData) {
        Assert.isMainThread();
        if (this.mediaEntries.containsKey(str)) {
            this.mediaEntries.put(str, mediaData);
            notifyMediaDataLoaded(str, str2, mediaData);
        }
    }
}
