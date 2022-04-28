package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.IRotationWatcher;
import android.view.IWindowManager;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceSession;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import com.android.p012wm.shell.displayareahelper.DisplayAreaHelper;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.LinearLightRevealEffect;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

/* compiled from: UnfoldLightRevealOverlayAnimation.kt */
public final class UnfoldLightRevealOverlayAnimation {
    public final Executor backgroundExecutor;
    public final Context context;
    public int currentRotation;
    public final DeviceStateManager deviceStateManager;
    public final Optional<DisplayAreaHelper> displayAreaHelper;
    public final DisplayManager displayManager;
    public final Executor executor;
    public boolean isFolded;
    public boolean isUnfoldHandled = true;
    public SurfaceControl overlayContainer;
    public SurfaceControlViewHost root;
    public final RotationWatcher rotationWatcher = new RotationWatcher();
    public LightRevealScrim scrimView;
    public final TransitionListener transitionListener = new TransitionListener();
    public final UnfoldTransitionProgressProvider unfoldTransitionProgressProvider;
    public DisplayInfo unfoldedDisplayInfo;
    public final IWindowManager windowManagerInterface;
    public WindowlessWindowManager wwm;

    /* compiled from: UnfoldLightRevealOverlayAnimation.kt */
    public final class FoldListener extends DeviceStateManager.FoldStateListener {
        public FoldListener(final UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation) {
            super(unfoldLightRevealOverlayAnimation.context, new Consumer() {
                public final void accept(Object obj) {
                    Boolean bool = (Boolean) obj;
                    if (bool.booleanValue()) {
                        unfoldLightRevealOverlayAnimation.ensureOverlayRemoved();
                        unfoldLightRevealOverlayAnimation.isUnfoldHandled = false;
                    }
                    unfoldLightRevealOverlayAnimation.isFolded = bool.booleanValue();
                }
            });
        }
    }

    /* compiled from: UnfoldLightRevealOverlayAnimation.kt */
    public final class RotationWatcher extends IRotationWatcher.Stub {
        public RotationWatcher() {
        }

        public final void onRotationChanged(int i) {
            boolean z;
            UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = UnfoldLightRevealOverlayAnimation.this;
            Trace.beginSection("UnfoldLightRevealOverlayAnimation#onRotationChanged");
            try {
                if (unfoldLightRevealOverlayAnimation.currentRotation != i) {
                    unfoldLightRevealOverlayAnimation.currentRotation = i;
                    LightRevealScrim lightRevealScrim = unfoldLightRevealOverlayAnimation.scrimView;
                    if (lightRevealScrim != null) {
                        if (i != 0) {
                            if (i != 2) {
                                z = false;
                                lightRevealScrim.setRevealEffect(new LinearLightRevealEffect(z));
                            }
                        }
                        z = true;
                        lightRevealScrim.setRevealEffect(new LinearLightRevealEffect(z));
                    }
                    SurfaceControlViewHost surfaceControlViewHost = unfoldLightRevealOverlayAnimation.root;
                    if (surfaceControlViewHost != null) {
                        surfaceControlViewHost.relayout(unfoldLightRevealOverlayAnimation.getLayoutParams());
                    }
                }
            } finally {
                Trace.endSection();
            }
        }
    }

    /* compiled from: UnfoldLightRevealOverlayAnimation.kt */
    public final class TransitionListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
        public TransitionListener() {
        }

        public final void onTransitionFinished() {
            UnfoldLightRevealOverlayAnimation.this.ensureOverlayRemoved();
        }

        public final void onTransitionProgress(float f) {
            LightRevealScrim lightRevealScrim = UnfoldLightRevealOverlayAnimation.this.scrimView;
            if (lightRevealScrim != null) {
                lightRevealScrim.setRevealAmount(f);
            }
        }

        public final void onTransitionStarted() {
            UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = UnfoldLightRevealOverlayAnimation.this;
            if (unfoldLightRevealOverlayAnimation.scrimView == null) {
                unfoldLightRevealOverlayAnimation.addView((Runnable) null);
            }
            InputManager.getInstance().cancelCurrentTouch();
        }
    }

    public final void addView(Runnable runnable) {
        boolean z;
        if (this.wwm != null) {
            ensureOverlayRemoved();
            Context context2 = this.context;
            Display display = context2.getDisplay();
            Intrinsics.checkNotNull(display);
            WindowlessWindowManager windowlessWindowManager = this.wwm;
            if (windowlessWindowManager == null) {
                windowlessWindowManager = null;
            }
            SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(context2, display, windowlessWindowManager, false);
            LightRevealScrim lightRevealScrim = new LightRevealScrim(this.context, (AttributeSet) null);
            int i = this.currentRotation;
            if (i == 0 || i == 2) {
                z = true;
            } else {
                z = false;
            }
            lightRevealScrim.setRevealEffect(new LinearLightRevealEffect(z));
            lightRevealScrim.isScrimOpaqueChangedListener = UnfoldLightRevealOverlayAnimation$addView$newView$1$1.INSTANCE;
            lightRevealScrim.setRevealAmount(0.0f);
            WindowManager.LayoutParams layoutParams = getLayoutParams();
            surfaceControlViewHost.setView(lightRevealScrim, layoutParams);
            if (runnable != null) {
                Trace.beginAsyncSection("UnfoldLightRevealOverlayAnimation#relayout", 0);
                surfaceControlViewHost.relayout(layoutParams, new UnfoldLightRevealOverlayAnimation$addView$2$1(this, runnable));
            }
            this.scrimView = lightRevealScrim;
            this.root = surfaceControlViewHost;
        } else if (runnable != null) {
            runnable.run();
        }
    }

    public final void ensureOverlayRemoved() {
        SurfaceControlViewHost surfaceControlViewHost = this.root;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.release();
        }
        this.root = null;
        this.scrimView = null;
    }

    public final WindowManager.LayoutParams getLayoutParams() {
        boolean z;
        int i;
        int i2;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        int i3 = this.currentRotation;
        if (i3 == 0 || i3 == 2) {
            z = true;
        } else {
            z = false;
        }
        DisplayInfo displayInfo = null;
        DisplayInfo displayInfo2 = this.unfoldedDisplayInfo;
        if (z) {
            if (displayInfo2 == null) {
                displayInfo2 = null;
            }
            i = displayInfo2.getNaturalHeight();
        } else {
            if (displayInfo2 == null) {
                displayInfo2 = null;
            }
            i = displayInfo2.getNaturalWidth();
        }
        layoutParams.height = i;
        if (z) {
            DisplayInfo displayInfo3 = this.unfoldedDisplayInfo;
            if (displayInfo3 != null) {
                displayInfo = displayInfo3;
            }
            i2 = displayInfo.getNaturalWidth();
        } else {
            DisplayInfo displayInfo4 = this.unfoldedDisplayInfo;
            if (displayInfo4 != null) {
                displayInfo = displayInfo4;
            }
            i2 = displayInfo.getNaturalHeight();
        }
        layoutParams.width = i2;
        layoutParams.format = -3;
        layoutParams.type = 2026;
        layoutParams.setTitle("Unfold Light Reveal Animation");
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.flags = 8;
        layoutParams.setTrustedOverlay();
        layoutParams.packageName = this.context.getOpPackageName();
        return layoutParams;
    }

    public final void init() {
        Object obj;
        this.deviceStateManager.registerCallback(this.executor, new FoldListener(this));
        this.unfoldTransitionProgressProvider.addCallback(this.transitionListener);
        this.windowManagerInterface.watchRotation(this.rotationWatcher, this.context.getDisplay().getDisplayId());
        this.displayAreaHelper.get().attachToRootDisplayArea(new SurfaceControl.Builder(new SurfaceSession()).setContainerLayer().setName("unfold-overlay-container"), new UnfoldLightRevealOverlayAnimation$init$1(this));
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new TransformingSequence(ArraysKt___ArraysKt.asSequence(this.displayManager.getDisplays()), UnfoldLightRevealOverlayAnimation$getUnfoldedDisplayInfo$1.INSTANCE), UnfoldLightRevealOverlayAnimation$getUnfoldedDisplayInfo$2.INSTANCE));
        if (!filteringSequence$iterator$1.hasNext()) {
            obj = null;
        } else {
            obj = filteringSequence$iterator$1.next();
            if (filteringSequence$iterator$1.hasNext()) {
                int naturalWidth = ((DisplayInfo) obj).getNaturalWidth();
                do {
                    Object next = filteringSequence$iterator$1.next();
                    int naturalWidth2 = ((DisplayInfo) next).getNaturalWidth();
                    if (naturalWidth < naturalWidth2) {
                        obj = next;
                        naturalWidth = naturalWidth2;
                    }
                } while (filteringSequence$iterator$1.hasNext());
            }
        }
        Intrinsics.checkNotNull(obj);
        this.unfoldedDisplayInfo = (DisplayInfo) obj;
    }

    public UnfoldLightRevealOverlayAnimation(Context context2, DeviceStateManager deviceStateManager2, DisplayManager displayManager2, UnfoldTransitionProgressProvider unfoldTransitionProgressProvider2, Optional<DisplayAreaHelper> optional, Executor executor2, Executor executor3, IWindowManager iWindowManager) {
        this.context = context2;
        this.deviceStateManager = deviceStateManager2;
        this.displayManager = displayManager2;
        this.unfoldTransitionProgressProvider = unfoldTransitionProgressProvider2;
        this.displayAreaHelper = optional;
        this.executor = executor2;
        this.backgroundExecutor = executor3;
        this.windowManagerInterface = iWindowManager;
        Display display = context2.getDisplay();
        Intrinsics.checkNotNull(display);
        this.currentRotation = display.getRotation();
    }
}
