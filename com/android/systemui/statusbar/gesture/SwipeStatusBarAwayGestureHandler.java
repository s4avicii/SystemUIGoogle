package com.android.systemui.statusbar.gesture;

import android.content.Context;
import android.os.Looper;
import android.view.Choreographer;
import android.view.InputMonitor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.google.android.setupcompat.util.Logger;
import java.util.LinkedHashMap;
import java.util.Objects;
import kotlin.jvm.functions.Function0;

/* compiled from: SwipeStatusBarAwayGestureHandler.kt */
public final class SwipeStatusBarAwayGestureHandler {
    public final LinkedHashMap callbacks = new LinkedHashMap();
    public Logger inputMonitor;
    public InputChannelCompat$InputEventReceiver inputReceiver;
    public final SwipeStatusBarAwayGestureLogger logger;
    public boolean monitoringCurrentTouch;
    public long startTime;
    public float startY;
    public final StatusBarWindowController statusBarWindowController;
    public int swipeDistanceThreshold;

    public final void addOnGestureDetectedCallback(Function0 function0) {
        boolean isEmpty = this.callbacks.isEmpty();
        this.callbacks.put("OngoingCallController", function0);
        if (isEmpty) {
            stopGestureListening();
            SwipeStatusBarAwayGestureLogger swipeStatusBarAwayGestureLogger = this.logger;
            Objects.requireNonNull(swipeStatusBarAwayGestureLogger);
            LogBuffer logBuffer = swipeStatusBarAwayGestureLogger.buffer;
            LogLevel logLevel = LogLevel.VERBOSE;
            SwipeStatusBarAwayGestureLogger$logInputListeningStarted$2 swipeStatusBarAwayGestureLogger$logInputListeningStarted$2 = SwipeStatusBarAwayGestureLogger$logInputListeningStarted$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                logBuffer.push(logBuffer.obtain("SwipeStatusBarAwayGestureHandler", logLevel, swipeStatusBarAwayGestureLogger$logInputListeningStarted$2));
            }
            Logger logger2 = new Logger(SwipeStatusBarAwayGestureHandlerKt.TAG, 0);
            this.inputReceiver = logger2.getInputReceiver(Looper.getMainLooper(), Choreographer.getInstance(), new SwipeStatusBarAwayGestureHandler$startGestureListening$1$1(this));
            this.inputMonitor = logger2;
        }
    }

    public final void removeOnGestureDetectedCallback() {
        this.callbacks.remove("OngoingCallController");
        if (this.callbacks.isEmpty()) {
            stopGestureListening();
        }
    }

    public final void stopGestureListening() {
        Logger logger2 = this.inputMonitor;
        if (logger2 != null) {
            SwipeStatusBarAwayGestureLogger swipeStatusBarAwayGestureLogger = this.logger;
            Objects.requireNonNull(swipeStatusBarAwayGestureLogger);
            LogBuffer logBuffer = swipeStatusBarAwayGestureLogger.buffer;
            LogLevel logLevel = LogLevel.VERBOSE;
            SwipeStatusBarAwayGestureLogger$logInputListeningStopped$2 swipeStatusBarAwayGestureLogger$logInputListeningStopped$2 = SwipeStatusBarAwayGestureLogger$logInputListeningStopped$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                logBuffer.push(logBuffer.obtain("SwipeStatusBarAwayGestureHandler", logLevel, swipeStatusBarAwayGestureLogger$logInputListeningStopped$2));
            }
            this.inputMonitor = null;
            ((InputMonitor) logger2.prefix).dispose();
        }
        InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = this.inputReceiver;
        if (inputChannelCompat$InputEventReceiver != null) {
            this.inputReceiver = null;
            inputChannelCompat$InputEventReceiver.mReceiver.dispose();
        }
    }

    public SwipeStatusBarAwayGestureHandler(Context context, StatusBarWindowController statusBarWindowController2, SwipeStatusBarAwayGestureLogger swipeStatusBarAwayGestureLogger) {
        this.statusBarWindowController = statusBarWindowController2;
        this.logger = swipeStatusBarAwayGestureLogger;
        this.swipeDistanceThreshold = context.getResources().getDimensionPixelSize(17105561);
    }
}
