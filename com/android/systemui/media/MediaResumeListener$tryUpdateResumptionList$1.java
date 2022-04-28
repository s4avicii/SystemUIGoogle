package com.android.systemui.media;

import android.content.ComponentName;
import android.media.MediaDescription;
import android.util.Log;
import com.android.systemui.media.ResumeMediaBrowser;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaResumeListener.kt */
public final class MediaResumeListener$tryUpdateResumptionList$1 extends ResumeMediaBrowser.Callback {
    public final /* synthetic */ ComponentName $componentName;
    public final /* synthetic */ String $key;
    public final /* synthetic */ MediaResumeListener this$0;

    public MediaResumeListener$tryUpdateResumptionList$1(ComponentName componentName, MediaResumeListener mediaResumeListener, String str) {
        this.$componentName = componentName;
        this.this$0 = mediaResumeListener;
        this.$key = str;
    }

    public final void addTrack(MediaDescription mediaDescription, ComponentName componentName, ResumeMediaBrowser resumeMediaBrowser) {
        Pair<ComponentName, Long> pair;
        Log.d("MediaResumeListener", Intrinsics.stringPlus("Can get resumable media from ", this.$componentName));
        MediaResumeListener mediaResumeListener = this.this$0;
        MediaDataManager mediaDataManager = mediaResumeListener.mediaDataManager;
        if (mediaDataManager == null) {
            mediaDataManager = null;
        }
        mediaDataManager.setResumeAction(this.$key, new MediaResumeListener$getResumeAction$1(mediaResumeListener, this.$componentName));
        MediaResumeListener mediaResumeListener2 = this.this$0;
        ComponentName componentName2 = this.$componentName;
        Objects.requireNonNull(mediaResumeListener2);
        ConcurrentLinkedQueue<Pair<ComponentName, Long>> concurrentLinkedQueue = mediaResumeListener2.resumeComponents;
        Iterator<Pair<ComponentName, Long>> it = concurrentLinkedQueue.iterator();
        while (true) {
            if (!it.hasNext()) {
                pair = null;
                break;
            }
            pair = it.next();
            if (((ComponentName) pair.getFirst()).equals(componentName2)) {
                break;
            }
        }
        concurrentLinkedQueue.remove(pair);
        mediaResumeListener2.resumeComponents.add(new Pair(componentName2, Long.valueOf(mediaResumeListener2.systemClock.currentTimeMillis())));
        if (mediaResumeListener2.resumeComponents.size() > 5) {
            mediaResumeListener2.resumeComponents.remove();
        }
        mediaResumeListener2.writeSharedPrefs();
        this.this$0.mediaBrowser = null;
    }

    public final void onConnected() {
        Log.d("MediaResumeListener", Intrinsics.stringPlus("Connected to ", this.$componentName));
    }

    public final void onError() {
        Log.e("MediaResumeListener", Intrinsics.stringPlus("Cannot resume with ", this.$componentName));
        this.this$0.mediaBrowser = null;
    }
}
