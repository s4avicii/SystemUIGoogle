package com.android.systemui.dreams.touch;

import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.MotionEvent;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda9;
import com.android.p012wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda4;
import com.android.p012wm.shell.legacysplitscreen.WindowManagerProxy$$ExternalSyntheticLambda0;
import com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda2;
import com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda9;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.dreams.touch.dagger.InputSessionComponent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda3;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda4;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda5;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda5;
import com.google.android.setupcompat.util.Logger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class DreamOverlayTouchMonitor {
    public final HashSet<TouchSessionImpl> mActiveTouchSessions = new HashSet<>();
    public InputSession mCurrentInputSession;
    public final Executor mExecutor;
    public final Set mHandlers;
    public C08032 mInputEventListener = new InputChannelCompat$InputEventListener() {
        public final void onInputEvent(InputEvent inputEvent) {
            if (DreamOverlayTouchMonitor.this.mActiveTouchSessions.isEmpty()) {
                for (DreamTouchHandler onSessionStart : DreamOverlayTouchMonitor.this.mHandlers) {
                    TouchSessionImpl touchSessionImpl = new TouchSessionImpl(DreamOverlayTouchMonitor.this);
                    DreamOverlayTouchMonitor.this.mActiveTouchSessions.add(touchSessionImpl);
                    onSessionStart.onSessionStart(touchSessionImpl);
                }
            }
            DreamOverlayTouchMonitor.this.mActiveTouchSessions.stream().map(BubbleData$$ExternalSyntheticLambda4.INSTANCE$2).flatMap(DreamOverlayTouchMonitor$2$$ExternalSyntheticLambda0.INSTANCE).forEach(new WMShell$$ExternalSyntheticLambda3(inputEvent, 1));
        }
    };
    public InputSessionComponent.Factory mInputSessionFactory;
    public final Lifecycle mLifecycle;
    public final LifecycleObserver mLifecycleObserver = new DefaultLifecycleObserver() {
        public final void onPause$1() {
            DreamOverlayTouchMonitor dreamOverlayTouchMonitor = DreamOverlayTouchMonitor.this;
            Objects.requireNonNull(dreamOverlayTouchMonitor);
            InputSession inputSession = dreamOverlayTouchMonitor.mCurrentInputSession;
            if (inputSession != null) {
                InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = inputSession.mInputEventReceiver;
                if (inputChannelCompat$InputEventReceiver != null) {
                    inputChannelCompat$InputEventReceiver.mReceiver.dispose();
                }
                Logger logger = inputSession.mInputMonitor;
                if (logger != null) {
                    ((InputMonitor) logger.prefix).dispose();
                }
                dreamOverlayTouchMonitor.mCurrentInputSession = null;
            }
        }

        public final void onResume$1() {
            DreamOverlayTouchMonitor dreamOverlayTouchMonitor = DreamOverlayTouchMonitor.this;
            Objects.requireNonNull(dreamOverlayTouchMonitor);
            InputSession inputSession = dreamOverlayTouchMonitor.mCurrentInputSession;
            if (inputSession != null) {
                InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = inputSession.mInputEventReceiver;
                if (inputChannelCompat$InputEventReceiver != null) {
                    inputChannelCompat$InputEventReceiver.mReceiver.dispose();
                }
                Logger logger = inputSession.mInputMonitor;
                if (logger != null) {
                    ((InputMonitor) logger.prefix).dispose();
                }
                dreamOverlayTouchMonitor.mCurrentInputSession = null;
            }
            dreamOverlayTouchMonitor.mCurrentInputSession = dreamOverlayTouchMonitor.mInputSessionFactory.create("dreamOverlay", dreamOverlayTouchMonitor.mInputEventListener, dreamOverlayTouchMonitor.mOnGestureListener, true).getInputSession();
        }
    };
    public C08043 mOnGestureListener = new GestureDetector.OnGestureListener() {
        public final boolean evaluate(Evaluator evaluator) {
            HashSet hashSet = new HashSet();
            boolean anyMatch = DreamOverlayTouchMonitor.this.mActiveTouchSessions.stream().map(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda5(evaluator, hashSet)).anyMatch(DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda7.INSTANCE);
            if (anyMatch) {
                DreamOverlayTouchMonitor dreamOverlayTouchMonitor = DreamOverlayTouchMonitor.this;
                Objects.requireNonNull(dreamOverlayTouchMonitor);
                Collection collection = (Collection) dreamOverlayTouchMonitor.mActiveTouchSessions.stream().filter(new WindowManagerProxy$$ExternalSyntheticLambda0(hashSet, 1)).collect(Collectors.toCollection(DreamOverlayStateController$$ExternalSyntheticLambda9.INSTANCE));
                collection.forEach(SystemActions$$ExternalSyntheticLambda2.INSTANCE$1);
                dreamOverlayTouchMonitor.mActiveTouchSessions.removeAll(collection);
            }
            return anyMatch;
        }

        public final void observe(Consumer<GestureDetector.OnGestureListener> consumer) {
            DreamOverlayTouchMonitor.this.mActiveTouchSessions.stream().map(WifiPickerTracker$$ExternalSyntheticLambda5.INSTANCE$1).flatMap(DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda6.INSTANCE).forEach(new BubbleController$$ExternalSyntheticLambda9(consumer, 1));
        }

        public final boolean onDown(MotionEvent motionEvent) {
            return evaluate(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2(motionEvent));
        }

        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return evaluate(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda0(motionEvent, motionEvent2, f, f2));
        }

        public final void onLongPress(MotionEvent motionEvent) {
            observe(new WMShell$$ExternalSyntheticLambda5(motionEvent, 1));
        }

        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return evaluate(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda1(motionEvent, motionEvent2, f, f2));
        }

        public final void onShowPress(MotionEvent motionEvent) {
            observe(new WMShell$$ExternalSyntheticLambda4(motionEvent, 1));
        }

        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            return evaluate(new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda3(motionEvent));
        }
    };

    public interface Evaluator {
        boolean evaluate(GestureDetector.OnGestureListener onGestureListener);
    }

    public static class TouchSessionImpl implements DreamTouchHandler.TouchSession {
        public final HashSet<DreamTouchHandler.TouchSession.Callback> mCallbacks = new HashSet<>();
        public final HashSet<InputChannelCompat$InputEventListener> mEventListeners = new HashSet<>();
        public final HashSet<GestureDetector.OnGestureListener> mGestureListeners = new HashSet<>();
        public final TouchSessionImpl mPredecessor = null;
        public final DreamOverlayTouchMonitor mTouchMonitor;

        public TouchSessionImpl(DreamOverlayTouchMonitor dreamOverlayTouchMonitor) {
            this.mTouchMonitor = dreamOverlayTouchMonitor;
        }
    }

    public DreamOverlayTouchMonitor(Executor executor, Lifecycle lifecycle, InputSessionComponent.Factory factory, Set<DreamTouchHandler> set) {
        this.mHandlers = set;
        this.mInputSessionFactory = factory;
        this.mExecutor = executor;
        this.mLifecycle = lifecycle;
    }
}
