package com.android.systemui.media;

import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import com.android.systemui.media.MediaDeviceManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.BiConsumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaDeviceManager.kt */
public final class MediaDeviceManager$dump$1$1<T, U> implements BiConsumer {
    public final /* synthetic */ PrintWriter $pw;
    public final /* synthetic */ PrintWriter $this_with;

    public MediaDeviceManager$dump$1$1(PrintWriter printWriter, FileDescriptor fileDescriptor, PrintWriter printWriter2, String[] strArr) {
        this.$this_with = printWriter;
        this.$pw = printWriter2;
    }

    public final void accept(Object obj, Object obj2) {
        RoutingSessionInfo routingSessionInfo;
        List list;
        CharSequence charSequence;
        MediaController.PlaybackInfo playbackInfo;
        MediaDeviceManager.Entry entry = (MediaDeviceManager.Entry) obj2;
        this.$this_with.println(Intrinsics.stringPlus("  key=", (String) obj));
        PrintWriter printWriter = this.$pw;
        MediaController mediaController = entry.controller;
        Integer num = null;
        if (mediaController == null) {
            routingSessionInfo = null;
        } else {
            routingSessionInfo = MediaDeviceManager.this.mr2manager.getRoutingSessionForMediaController(mediaController);
        }
        if (routingSessionInfo == null) {
            list = null;
        } else {
            list = MediaDeviceManager.this.mr2manager.getSelectedRoutes(routingSessionInfo);
        }
        MediaDeviceData mediaDeviceData = entry.current;
        if (mediaDeviceData == null) {
            charSequence = null;
        } else {
            charSequence = mediaDeviceData.name;
        }
        printWriter.println(Intrinsics.stringPlus("    current device is ", charSequence));
        MediaController mediaController2 = entry.controller;
        if (!(mediaController2 == null || (playbackInfo = mediaController2.getPlaybackInfo()) == null)) {
            num = Integer.valueOf(playbackInfo.getPlaybackType());
        }
        printWriter.println("    PlaybackType=" + num + " (1 for local, 2 for remote) cached=" + entry.playbackType);
        printWriter.println(Intrinsics.stringPlus("    routingSession=", routingSessionInfo));
        printWriter.println(Intrinsics.stringPlus("    selectedRoutes=", list));
    }
}
