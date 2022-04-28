package com.android.p012wm.shell.startingsurface;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Trace;
import android.util.Slog;
import android.window.SplashScreenView;
import android.window.StartingWindowInfo;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda4;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplashscreenContentDrawer$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SplashscreenContentDrawer f$0;
    public final /* synthetic */ Context f$1;
    public final /* synthetic */ StartingWindowInfo f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ Consumer f$4;
    public final /* synthetic */ Consumer f$5;

    public /* synthetic */ SplashscreenContentDrawer$$ExternalSyntheticLambda0(SplashscreenContentDrawer splashscreenContentDrawer, Context context, StartingWindowInfo startingWindowInfo, int i, ShellCommandHandlerImpl$$ExternalSyntheticLambda4 shellCommandHandlerImpl$$ExternalSyntheticLambda4, ShellCommandHandlerImpl$$ExternalSyntheticLambda1 shellCommandHandlerImpl$$ExternalSyntheticLambda1) {
        this.f$0 = splashscreenContentDrawer;
        this.f$1 = context;
        this.f$2 = startingWindowInfo;
        this.f$3 = i;
        this.f$4 = shellCommandHandlerImpl$$ExternalSyntheticLambda4;
        this.f$5 = shellCommandHandlerImpl$$ExternalSyntheticLambda1;
    }

    public final void run() {
        SplashScreenView splashScreenView;
        SplashscreenContentDrawer splashscreenContentDrawer = this.f$0;
        Context context = this.f$1;
        StartingWindowInfo startingWindowInfo = this.f$2;
        int i = this.f$3;
        Consumer consumer = this.f$4;
        Consumer consumer2 = this.f$5;
        Objects.requireNonNull(splashscreenContentDrawer);
        try {
            Trace.traceBegin(32, "makeSplashScreenContentView");
            splashScreenView = splashscreenContentDrawer.makeSplashScreenContentView(context, startingWindowInfo, i, consumer);
            Trace.traceEnd(32);
        } catch (RuntimeException e) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("failed creating starting window content at taskId: ");
            m.append(startingWindowInfo.taskInfo.taskId);
            Slog.w("ShellStartingWindow", m.toString(), e);
            splashScreenView = null;
        }
        consumer2.accept(splashScreenView);
    }
}
