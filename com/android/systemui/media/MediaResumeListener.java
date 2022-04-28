package com.android.systemui.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Utils;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: MediaResumeListener.kt */
public final class MediaResumeListener implements MediaDataManager.Listener, Dumpable {
    public final Executor backgroundExecutor;
    public final Context context;
    public int currentUserId;
    public ResumeMediaBrowser mediaBrowser;
    public final MediaResumeListener$mediaBrowserCallback$1 mediaBrowserCallback;
    public final ResumeMediaBrowserFactory mediaBrowserFactory;
    public MediaDataManager mediaDataManager;
    public final ConcurrentLinkedQueue<Pair<ComponentName, Long>> resumeComponents = new ConcurrentLinkedQueue<>();
    public final SystemClock systemClock;
    public final TunerService tunerService;
    public boolean useMediaResumption;
    public final MediaResumeListener$userChangeReceiver$1 userChangeReceiver;

    @VisibleForTesting
    public static /* synthetic */ void getUserChangeReceiver$annotations() {
    }

    public final void onMediaDataRemoved(String str) {
    }

    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z, boolean z2) {
    }

    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(Intrinsics.stringPlus("resumeComponents: ", this.resumeComponents));
    }

    public final void loadSavedComponents() {
        long j;
        long j2;
        boolean z;
        this.resumeComponents.clear();
        boolean z2 = false;
        Iterable<String> iterable = null;
        String string = this.context.getSharedPreferences("media_control_prefs", 0).getString(Intrinsics.stringPlus("browser_components_", Integer.valueOf(this.currentUserId)), (String) null);
        if (string != null) {
            List split = new Regex(":").split(string);
            if (!split.isEmpty()) {
                ListIterator listIterator = split.listIterator(split.size());
                while (true) {
                    if (!listIterator.hasPrevious()) {
                        break;
                    }
                    if (((String) listIterator.previous()).length() == 0) {
                        z = true;
                        continue;
                    } else {
                        z = false;
                        continue;
                    }
                    if (!z) {
                        iterable = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                        break;
                    }
                }
            }
            iterable = EmptyList.INSTANCE;
        }
        if (iterable != null) {
            boolean z3 = false;
            for (String split$default : iterable) {
                List split$default2 = StringsKt__StringsKt.split$default(split$default, new String[]{"/"});
                ComponentName componentName = new ComponentName((String) split$default2.get(0), (String) split$default2.get(1));
                if (split$default2.size() == 3) {
                    try {
                        j = Long.parseLong((String) split$default2.get(2));
                    } catch (NumberFormatException unused) {
                        j2 = this.systemClock.currentTimeMillis();
                    }
                } else {
                    j2 = this.systemClock.currentTimeMillis();
                    j = j2;
                    z3 = true;
                }
                this.resumeComponents.add(new Pair(componentName, Long.valueOf(j)));
            }
            z2 = z3;
        }
        Log.d("MediaResumeListener", Intrinsics.stringPlus("loaded resume components ", Arrays.toString(this.resumeComponents.toArray())));
        if (z2) {
            writeSharedPrefs();
        }
    }

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i) {
        boolean z2;
        ArrayList arrayList;
        if (this.useMediaResumption) {
            if (!str.equals(str2)) {
                ResumeMediaBrowser resumeMediaBrowser = this.mediaBrowser;
                if (resumeMediaBrowser != null) {
                    resumeMediaBrowser.disconnect();
                }
                this.mediaBrowser = null;
            }
            if (mediaData.resumeAction == null && !mediaData.hasCheckedForResume) {
                if (mediaData.playbackLocation == 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    Log.d("MediaResumeListener", Intrinsics.stringPlus("Checking for service component for ", mediaData.packageName));
                    List<ResolveInfo> queryIntentServices = this.context.getPackageManager().queryIntentServices(new Intent("android.media.browse.MediaBrowserService"), 0);
                    if (queryIntentServices == null) {
                        arrayList = null;
                    } else {
                        arrayList = new ArrayList();
                        for (T next : queryIntentServices) {
                            if (Intrinsics.areEqual(((ResolveInfo) next).serviceInfo.packageName, mediaData.packageName)) {
                                arrayList.add(next);
                            }
                        }
                    }
                    if (arrayList == null || arrayList.size() <= 0) {
                        MediaDataManager mediaDataManager2 = this.mediaDataManager;
                        if (mediaDataManager2 == null) {
                            mediaDataManager2 = null;
                        }
                        mediaDataManager2.setResumeAction(str, (MediaResumeListener$getResumeAction$1) null);
                        return;
                    }
                    this.backgroundExecutor.execute(new MediaResumeListener$onMediaDataLoaded$1(this, str, arrayList));
                }
            }
        }
    }

    public final void writeSharedPrefs() {
        StringBuilder sb = new StringBuilder();
        for (Pair pair : this.resumeComponents) {
            sb.append(((ComponentName) pair.getFirst()).flattenToString());
            sb.append("/");
            sb.append(((Number) pair.getSecond()).longValue());
            sb.append(":");
        }
        this.context.getSharedPreferences("media_control_prefs", 0).edit().putString(Intrinsics.stringPlus("browser_components_", Integer.valueOf(this.currentUserId)), sb.toString()).apply();
    }

    public MediaResumeListener(Context context2, BroadcastDispatcher broadcastDispatcher, Executor executor, TunerService tunerService2, ResumeMediaBrowserFactory resumeMediaBrowserFactory, DumpManager dumpManager, SystemClock systemClock2) {
        this.context = context2;
        this.backgroundExecutor = executor;
        this.tunerService = tunerService2;
        this.mediaBrowserFactory = resumeMediaBrowserFactory;
        this.systemClock = systemClock2;
        this.useMediaResumption = Utils.useMediaResumption(context2);
        this.currentUserId = context2.getUserId();
        MediaResumeListener$userChangeReceiver$1 mediaResumeListener$userChangeReceiver$1 = new MediaResumeListener$userChangeReceiver$1(this);
        this.userChangeReceiver = mediaResumeListener$userChangeReceiver$1;
        this.mediaBrowserCallback = new MediaResumeListener$mediaBrowserCallback$1(this);
        if (this.useMediaResumption) {
            dumpManager.registerDumpable("MediaResumeListener", this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_UNLOCKED");
            intentFilter.addAction("android.intent.action.USER_SWITCHED");
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, mediaResumeListener$userChangeReceiver$1, intentFilter, (Executor) null, UserHandle.ALL, 16);
            loadSavedComponents();
        }
    }
}
