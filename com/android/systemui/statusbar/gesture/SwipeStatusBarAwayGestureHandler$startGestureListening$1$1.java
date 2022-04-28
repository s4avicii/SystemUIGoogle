package com.android.systemui.statusbar.gesture;

import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import java.util.Objects;
import kotlin.jvm.functions.Function0;

/* compiled from: SwipeStatusBarAwayGestureHandler.kt */
public final /* synthetic */ class SwipeStatusBarAwayGestureHandler$startGestureListening$1$1 implements InputChannelCompat$InputEventListener {
    public final /* synthetic */ SwipeStatusBarAwayGestureHandler $tmp0;

    public SwipeStatusBarAwayGestureHandler$startGestureListening$1$1(SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler) {
        this.$tmp0 = swipeStatusBarAwayGestureHandler;
    }

    public final void onInputEvent(InputEvent inputEvent) {
        SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler = this.$tmp0;
        Objects.requireNonNull(swipeStatusBarAwayGestureHandler);
        LogLevel logLevel = LogLevel.DEBUG;
        if (inputEvent instanceof MotionEvent) {
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked != 2) {
                        if (actionMasked != 3) {
                            return;
                        }
                    } else if (swipeStatusBarAwayGestureHandler.monitoringCurrentTouch) {
                        float y = motionEvent.getY();
                        float f = swipeStatusBarAwayGestureHandler.startY;
                        if (y < f && f - motionEvent.getY() >= ((float) swipeStatusBarAwayGestureHandler.swipeDistanceThreshold) && motionEvent.getEventTime() - swipeStatusBarAwayGestureHandler.startTime < 500) {
                            swipeStatusBarAwayGestureHandler.monitoringCurrentTouch = false;
                            SwipeStatusBarAwayGestureLogger swipeStatusBarAwayGestureLogger = swipeStatusBarAwayGestureHandler.logger;
                            int y2 = (int) motionEvent.getY();
                            Objects.requireNonNull(swipeStatusBarAwayGestureLogger);
                            LogBuffer logBuffer = swipeStatusBarAwayGestureLogger.buffer;
                            LogLevel logLevel2 = LogLevel.INFO;
                            SwipeStatusBarAwayGestureLogger$logGestureDetected$2 swipeStatusBarAwayGestureLogger$logGestureDetected$2 = SwipeStatusBarAwayGestureLogger$logGestureDetected$2.INSTANCE;
                            Objects.requireNonNull(logBuffer);
                            if (!logBuffer.frozen) {
                                LogMessageImpl obtain = logBuffer.obtain("SwipeStatusBarAwayGestureHandler", logLevel2, swipeStatusBarAwayGestureLogger$logGestureDetected$2);
                                obtain.int1 = y2;
                                logBuffer.push(obtain);
                            }
                            for (Function0 invoke : swipeStatusBarAwayGestureHandler.callbacks.values()) {
                                invoke.invoke();
                            }
                            return;
                        }
                        return;
                    } else {
                        return;
                    }
                }
                if (swipeStatusBarAwayGestureHandler.monitoringCurrentTouch) {
                    SwipeStatusBarAwayGestureLogger swipeStatusBarAwayGestureLogger2 = swipeStatusBarAwayGestureHandler.logger;
                    int y3 = (int) motionEvent.getY();
                    Objects.requireNonNull(swipeStatusBarAwayGestureLogger2);
                    LogBuffer logBuffer2 = swipeStatusBarAwayGestureLogger2.buffer;
                    C1215x6caa9506 swipeStatusBarAwayGestureLogger$logGestureDetectionEndedWithoutTriggering$2 = C1215x6caa9506.INSTANCE;
                    Objects.requireNonNull(logBuffer2);
                    if (!logBuffer2.frozen) {
                        LogMessageImpl obtain2 = logBuffer2.obtain("SwipeStatusBarAwayGestureHandler", logLevel, swipeStatusBarAwayGestureLogger$logGestureDetectionEndedWithoutTriggering$2);
                        obtain2.int1 = y3;
                        logBuffer2.push(obtain2);
                    }
                }
                swipeStatusBarAwayGestureHandler.monitoringCurrentTouch = false;
                return;
            }
            float y4 = motionEvent.getY();
            StatusBarWindowController statusBarWindowController = swipeStatusBarAwayGestureHandler.statusBarWindowController;
            Objects.requireNonNull(statusBarWindowController);
            if (y4 >= ((float) statusBarWindowController.mBarHeight)) {
                float y5 = motionEvent.getY();
                StatusBarWindowController statusBarWindowController2 = swipeStatusBarAwayGestureHandler.statusBarWindowController;
                Objects.requireNonNull(statusBarWindowController2);
                if (y5 <= ((float) (statusBarWindowController2.mBarHeight * 3))) {
                    SwipeStatusBarAwayGestureLogger swipeStatusBarAwayGestureLogger3 = swipeStatusBarAwayGestureHandler.logger;
                    int y6 = (int) motionEvent.getY();
                    Objects.requireNonNull(swipeStatusBarAwayGestureLogger3);
                    LogBuffer logBuffer3 = swipeStatusBarAwayGestureLogger3.buffer;
                    SwipeStatusBarAwayGestureLogger$logGestureDetectionStarted$2 swipeStatusBarAwayGestureLogger$logGestureDetectionStarted$2 = SwipeStatusBarAwayGestureLogger$logGestureDetectionStarted$2.INSTANCE;
                    Objects.requireNonNull(logBuffer3);
                    if (!logBuffer3.frozen) {
                        LogMessageImpl obtain3 = logBuffer3.obtain("SwipeStatusBarAwayGestureHandler", logLevel, swipeStatusBarAwayGestureLogger$logGestureDetectionStarted$2);
                        obtain3.int1 = y6;
                        logBuffer3.push(obtain3);
                    }
                    swipeStatusBarAwayGestureHandler.startY = motionEvent.getY();
                    swipeStatusBarAwayGestureHandler.startTime = motionEvent.getEventTime();
                    swipeStatusBarAwayGestureHandler.monitoringCurrentTouch = true;
                    return;
                }
            }
            swipeStatusBarAwayGestureHandler.monitoringCurrentTouch = false;
        }
    }
}
