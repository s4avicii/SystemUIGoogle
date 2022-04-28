package com.android.p012wm.shell.common;

import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.IWindowManager;
import android.view.InsetsSource;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.InsetsVisibilities;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.animation.PathInterpolator;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/* renamed from: com.android.wm.shell.common.DisplayImeController */
public final class DisplayImeController implements DisplayController.OnDisplaysChangedListener {
    public static final PathInterpolator INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    public final DisplayController mDisplayController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final SparseArray<PerDisplay> mImePerDisplay = new SparseArray<>();
    public final Executor mMainExecutor;
    public final ArrayList<ImePositionProcessor> mPositionProcessors = new ArrayList<>();
    public final TransactionPool mTransactionPool;
    public final IWindowManager mWmService;

    /* renamed from: com.android.wm.shell.common.DisplayImeController$ImePositionProcessor */
    public interface ImePositionProcessor {
        void onImeControlTargetChanged(int i, boolean z) {
        }

        void onImeEndPositioning(int i, boolean z, SurfaceControl.Transaction transaction) {
        }

        void onImePositionChanged(int i, int i2, SurfaceControl.Transaction transaction) {
        }

        int onImeStartPositioning(int i, int i2, int i3, boolean z, boolean z2) {
            return 0;
        }

        void onImeVisibilityChanged(int i, boolean z) {
        }
    }

    /* renamed from: com.android.wm.shell.common.DisplayImeController$PerDisplay */
    public class PerDisplay implements DisplayInsetsController.OnInsetsChangedListener {
        public boolean mAnimateAlpha = true;
        public ValueAnimator mAnimation = null;
        public int mAnimationDirection = 0;
        public final int mDisplayId;
        public final Rect mImeFrame = new Rect();
        public boolean mImeShowing = false;
        public InsetsSourceControl mImeSourceControl = null;
        public final InsetsState mInsetsState = new InsetsState();
        public final InsetsVisibilities mRequestedVisibilities = new InsetsVisibilities();
        public int mRotation = 0;

        public final void topFocusedWindowChanged() {
        }

        public PerDisplay(int i, int i2) {
            this.mDisplayId = i;
            this.mRotation = i2;
        }

        public final void insetsChanged(InsetsState insetsState) {
            if (!this.mInsetsState.equals(insetsState)) {
                updateImeVisibility(insetsState.getSourceOrDefaultVisibility(19));
                InsetsSource source = insetsState.getSource(19);
                Rect frame = source.getFrame();
                Rect frame2 = this.mInsetsState.getSource(19).getFrame();
                this.mInsetsState.set(insetsState, true);
                if (this.mImeShowing && !frame.equals(frame2) && source.isVisible()) {
                    startAnimation(this.mImeShowing, true);
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x0044  */
        /* JADX WARNING: Removed duplicated region for block: B:16:0x0063  */
        /* JADX WARNING: Removed duplicated region for block: B:28:0x0083  */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x009e  */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x00bc  */
        /* JADX WARNING: Removed duplicated region for block: B:37:0x00be  */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x00c1  */
        /* JADX WARNING: Removed duplicated region for block: B:40:0x00c3  */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x00d2  */
        /* JADX WARNING: Removed duplicated region for block: B:49:0x00d4  */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x00e8  */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x00eb  */
        /* JADX WARNING: Removed duplicated region for block: B:56:0x00f2  */
        /* JADX WARNING: Removed duplicated region for block: B:59:0x0126  */
        /* JADX WARNING: Removed duplicated region for block: B:65:0x0147  */
        /* JADX WARNING: Removed duplicated region for block: B:74:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void startAnimation(boolean r17, boolean r18) {
            /*
                r16 = this;
                r8 = r16
                android.view.InsetsState r0 = r8.mInsetsState
                r9 = 19
                android.view.InsetsSource r0 = r0.getSource(r9)
                if (r0 == 0) goto L_0x0161
                android.view.InsetsSourceControl r1 = r8.mImeSourceControl
                if (r1 != 0) goto L_0x0012
                goto L_0x0161
            L_0x0012:
                android.graphics.Rect r1 = r0.getFrame()
                android.graphics.Rect r0 = r0.getFrame()
                int r2 = r0.height()
                r10 = 0
                r11 = 1
                if (r2 != 0) goto L_0x0023
                goto L_0x0038
            L_0x0023:
                int r0 = r0.height()
                com.android.wm.shell.common.DisplayImeController r2 = com.android.p012wm.shell.common.DisplayImeController.this
                com.android.wm.shell.common.DisplayController r2 = r2.mDisplayController
                int r3 = r8.mDisplayId
                com.android.wm.shell.common.DisplayLayout r2 = r2.getDisplayLayout(r3)
                java.util.Objects.requireNonNull(r2)
                int r2 = r2.mNavBarFrameHeight
                if (r0 > r2) goto L_0x003a
            L_0x0038:
                r0 = r11
                goto L_0x003b
            L_0x003a:
                r0 = r10
            L_0x003b:
                if (r0 == 0) goto L_0x0041
                if (r17 == 0) goto L_0x0041
                r6 = r11
                goto L_0x0042
            L_0x0041:
                r6 = r10
            L_0x0042:
                if (r6 == 0) goto L_0x0063
                android.graphics.Rect r0 = r8.mImeFrame
                r0.set(r1)
                com.android.wm.shell.common.DisplayImeController r0 = com.android.p012wm.shell.common.DisplayImeController.this
                com.android.wm.shell.common.DisplayController r0 = r0.mDisplayController
                int r1 = r8.mDisplayId
                com.android.wm.shell.common.DisplayLayout r0 = r0.getDisplayLayout(r1)
                float r0 = r0.density()
                r1 = -1029701632(0xffffffffc2a00000, float:-80.0)
                float r0 = r0 * r1
                int r0 = (int) r0
                android.graphics.Rect r1 = r8.mImeFrame
                int r2 = r1.bottom
                int r2 = r2 - r0
                r1.bottom = r2
                goto L_0x006e
            L_0x0063:
                int r0 = r1.height()
                if (r0 == 0) goto L_0x006e
                android.graphics.Rect r0 = r8.mImeFrame
                r0.set(r1)
            L_0x006e:
                if (r18 != 0) goto L_0x0076
                int r0 = r8.mAnimationDirection
                if (r0 != r11) goto L_0x0076
                if (r17 != 0) goto L_0x007d
            L_0x0076:
                int r0 = r8.mAnimationDirection
                r1 = 2
                if (r0 != r1) goto L_0x007e
                if (r17 != 0) goto L_0x007e
            L_0x007d:
                return
            L_0x007e:
                r0 = 0
                android.animation.ValueAnimator r2 = r8.mAnimation
                if (r2 == 0) goto L_0x009e
                boolean r2 = r2.isRunning()
                if (r2 == 0) goto L_0x0097
                android.animation.ValueAnimator r0 = r8.mAnimation
                java.lang.Object r0 = r0.getAnimatedValue()
                java.lang.Float r0 = (java.lang.Float) r0
                float r0 = r0.floatValue()
                r2 = r11
                goto L_0x0098
            L_0x0097:
                r2 = r10
            L_0x0098:
                android.animation.ValueAnimator r3 = r8.mAnimation
                r3.cancel()
                goto L_0x009f
            L_0x009e:
                r2 = r10
            L_0x009f:
                android.view.InsetsSourceControl r3 = r8.mImeSourceControl
                android.graphics.Point r3 = r3.getSurfacePosition()
                int r3 = r3.y
                float r7 = (float) r3
                android.view.InsetsSourceControl r3 = r8.mImeSourceControl
                android.graphics.Point r3 = r3.getSurfacePosition()
                int r3 = r3.x
                float r12 = (float) r3
                android.graphics.Rect r3 = r8.mImeFrame
                int r3 = r3.height()
                float r3 = (float) r3
                float r13 = r7 + r3
                if (r17 == 0) goto L_0x00be
                r14 = r13
                goto L_0x00bf
            L_0x00be:
                r14 = r7
            L_0x00bf:
                if (r17 == 0) goto L_0x00c3
                r15 = r7
                goto L_0x00c4
            L_0x00c3:
                r15 = r13
            L_0x00c4:
                int r3 = r8.mAnimationDirection
                if (r3 != 0) goto L_0x00d0
                boolean r3 = r8.mImeShowing
                if (r3 == 0) goto L_0x00d0
                if (r17 == 0) goto L_0x00d0
                r0 = r7
                r2 = r11
            L_0x00d0:
                if (r17 == 0) goto L_0x00d4
                r3 = r11
                goto L_0x00d5
            L_0x00d4:
                r3 = r1
            L_0x00d5:
                r8.mAnimationDirection = r3
                r16.updateImeVisibility(r17)
                float[] r1 = new float[r1]
                r1[r10] = r14
                r1[r11] = r15
                android.animation.ValueAnimator r1 = android.animation.ValueAnimator.ofFloat(r1)
                r8.mAnimation = r1
                if (r17 == 0) goto L_0x00eb
                r3 = 275(0x113, double:1.36E-321)
                goto L_0x00ed
            L_0x00eb:
                r3 = 340(0x154, double:1.68E-321)
            L_0x00ed:
                r1.setDuration(r3)
                if (r2 == 0) goto L_0x00fb
                android.animation.ValueAnimator r1 = r8.mAnimation
                float r0 = r0 - r14
                float r2 = r15 - r14
                float r0 = r0 / r2
                r1.setCurrentFraction(r0)
            L_0x00fb:
                android.animation.ValueAnimator r5 = r8.mAnimation
                com.android.wm.shell.common.DisplayImeController$PerDisplay$$ExternalSyntheticLambda0 r4 = new com.android.wm.shell.common.DisplayImeController$PerDisplay$$ExternalSyntheticLambda0
                r0 = r4
                r1 = r16
                r2 = r12
                r3 = r6
                r11 = r4
                r4 = r13
                r10 = r5
                r5 = r7
                r0.<init>(r1, r2, r3, r4, r5)
                r10.addUpdateListener(r11)
                android.animation.ValueAnimator r0 = r8.mAnimation
                android.view.animation.PathInterpolator r1 = com.android.p012wm.shell.common.DisplayImeController.INTERPOLATOR
                r0.setInterpolator(r1)
                android.animation.ValueAnimator r10 = r8.mAnimation
                com.android.wm.shell.common.DisplayImeController$PerDisplay$1 r11 = new com.android.wm.shell.common.DisplayImeController$PerDisplay$1
                r0 = r11
                r1 = r16
                r3 = r14
                r7 = r15
                r0.<init>(r2, r3, r4, r5, r6, r7)
                r10.addListener(r11)
                if (r17 != 0) goto L_0x0140
                android.view.InsetsState r0 = r8.mInsetsState
                android.view.InsetsSource r0 = r0.getSource(r9)
                r1 = 0
                r0.setVisible(r1)
                android.view.InsetsVisibilities r0 = r8.mRequestedVisibilities
                r0.setVisibility(r9, r1)
                com.android.wm.shell.common.DisplayImeController r0 = com.android.p012wm.shell.common.DisplayImeController.this     // Catch:{ RemoteException -> 0x0140 }
                android.view.IWindowManager r0 = r0.mWmService     // Catch:{ RemoteException -> 0x0140 }
                int r1 = r8.mDisplayId     // Catch:{ RemoteException -> 0x0140 }
                android.view.InsetsVisibilities r2 = r8.mRequestedVisibilities     // Catch:{ RemoteException -> 0x0140 }
                r0.updateDisplayWindowRequestedVisibilities(r1, r2)     // Catch:{ RemoteException -> 0x0140 }
            L_0x0140:
                android.animation.ValueAnimator r0 = r8.mAnimation
                r0.start()
                if (r17 == 0) goto L_0x0161
                android.view.InsetsState r0 = r8.mInsetsState
                android.view.InsetsSource r0 = r0.getSource(r9)
                r1 = 1
                r0.setVisible(r1)
                android.view.InsetsVisibilities r0 = r8.mRequestedVisibilities
                r0.setVisibility(r9, r1)
                com.android.wm.shell.common.DisplayImeController r0 = com.android.p012wm.shell.common.DisplayImeController.this     // Catch:{ RemoteException -> 0x0161 }
                android.view.IWindowManager r0 = r0.mWmService     // Catch:{ RemoteException -> 0x0161 }
                int r1 = r8.mDisplayId     // Catch:{ RemoteException -> 0x0161 }
                android.view.InsetsVisibilities r2 = r8.mRequestedVisibilities     // Catch:{ RemoteException -> 0x0161 }
                r0.updateDisplayWindowRequestedVisibilities(r1, r2)     // Catch:{ RemoteException -> 0x0161 }
            L_0x0161:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.common.DisplayImeController.PerDisplay.startAnimation(boolean, boolean):void");
        }

        public final void updateImeVisibility(boolean z) {
            if (this.mImeShowing != z) {
                this.mImeShowing = z;
                DisplayImeController displayImeController = DisplayImeController.this;
                int i = this.mDisplayId;
                PathInterpolator pathInterpolator = DisplayImeController.INTERPOLATOR;
                Objects.requireNonNull(displayImeController);
                synchronized (displayImeController.mPositionProcessors) {
                    Iterator<ImePositionProcessor> it = displayImeController.mPositionProcessors.iterator();
                    while (it.hasNext()) {
                        it.next().onImeVisibilityChanged(i, z);
                    }
                }
            }
        }

        public final void hideInsets(int i) {
            if ((i & WindowInsets.Type.ime()) != 0) {
                startAnimation(false, false);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:48:0x009b  */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x00a3 A[ADDED_TO_REGION] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void insetsControlChanged(android.view.InsetsState r8, android.view.InsetsSourceControl[] r9) {
            /*
                r7 = this;
                r7.insetsChanged(r8)
                r8 = 0
                r0 = 0
                if (r9 == 0) goto L_0x001d
                int r1 = r9.length
                r3 = r8
                r2 = r0
            L_0x000a:
                if (r2 >= r1) goto L_0x001e
                r4 = r9[r2]
                if (r4 != 0) goto L_0x0011
                goto L_0x001a
            L_0x0011:
                int r5 = r4.getType()
                r6 = 19
                if (r5 != r6) goto L_0x001a
                r3 = r4
            L_0x001a:
                int r2 = r2 + 1
                goto L_0x000a
            L_0x001d:
                r3 = r8
            L_0x001e:
                android.view.InsetsSourceControl r9 = r7.mImeSourceControl
                r1 = 1
                if (r9 == 0) goto L_0x0025
                r9 = r1
                goto L_0x0026
            L_0x0025:
                r9 = r0
            L_0x0026:
                if (r3 == 0) goto L_0x002a
                r2 = r1
                goto L_0x002b
            L_0x002a:
                r2 = r0
            L_0x002b:
                if (r9 == r2) goto L_0x0054
                com.android.wm.shell.common.DisplayImeController r9 = com.android.p012wm.shell.common.DisplayImeController.this
                int r4 = r7.mDisplayId
                android.view.animation.PathInterpolator r5 = com.android.p012wm.shell.common.DisplayImeController.INTERPOLATOR
                java.util.Objects.requireNonNull(r9)
                java.util.ArrayList<com.android.wm.shell.common.DisplayImeController$ImePositionProcessor> r5 = r9.mPositionProcessors
                monitor-enter(r5)
                java.util.ArrayList<com.android.wm.shell.common.DisplayImeController$ImePositionProcessor> r9 = r9.mPositionProcessors     // Catch:{ all -> 0x0051 }
                java.util.Iterator r9 = r9.iterator()     // Catch:{ all -> 0x0051 }
            L_0x003f:
                boolean r6 = r9.hasNext()     // Catch:{ all -> 0x0051 }
                if (r6 == 0) goto L_0x004f
                java.lang.Object r6 = r9.next()     // Catch:{ all -> 0x0051 }
                com.android.wm.shell.common.DisplayImeController$ImePositionProcessor r6 = (com.android.p012wm.shell.common.DisplayImeController.ImePositionProcessor) r6     // Catch:{ all -> 0x0051 }
                r6.onImeControlTargetChanged(r4, r2)     // Catch:{ all -> 0x0051 }
                goto L_0x003f
            L_0x004f:
                monitor-exit(r5)     // Catch:{ all -> 0x0051 }
                goto L_0x0054
            L_0x0051:
                r7 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x0051 }
                throw r7
            L_0x0054:
                if (r2 == 0) goto L_0x00f4
                android.view.InsetsSourceControl r9 = r7.mImeSourceControl
                if (r9 == 0) goto L_0x005e
                android.graphics.Point r8 = r9.getSurfacePosition()
            L_0x005e:
                android.graphics.Point r9 = r3.getSurfacePosition()
                boolean r8 = r9.equals(r8)
                r8 = r8 ^ r1
                android.view.InsetsSourceControl r9 = r7.mImeSourceControl
                android.view.animation.PathInterpolator r2 = com.android.p012wm.shell.common.DisplayImeController.INTERPOLATOR
                if (r9 != r3) goto L_0x006e
                goto L_0x007a
            L_0x006e:
                if (r9 == 0) goto L_0x0095
                android.view.SurfaceControl r2 = r9.getLeash()
                android.view.SurfaceControl r4 = r3.getLeash()
                if (r2 != r4) goto L_0x007c
            L_0x007a:
                r0 = r1
                goto L_0x0095
            L_0x007c:
                android.view.SurfaceControl r2 = r9.getLeash()
                if (r2 == 0) goto L_0x0095
                android.view.SurfaceControl r2 = r3.getLeash()
                if (r2 != 0) goto L_0x0089
                goto L_0x0095
            L_0x0089:
                android.view.SurfaceControl r9 = r9.getLeash()
                android.view.SurfaceControl r0 = r3.getLeash()
                boolean r0 = r9.isSameSurface(r0)
            L_0x0095:
                r9 = r0 ^ 1
                android.animation.ValueAnimator r0 = r7.mAnimation
                if (r0 == 0) goto L_0x00a3
                if (r8 == 0) goto L_0x00f2
                boolean r8 = r7.mImeShowing
                r7.startAnimation(r8, r1)
                goto L_0x00f2
            L_0x00a3:
                if (r9 == 0) goto L_0x00c8
                android.view.SurfaceControl r8 = r3.getLeash()
                if (r8 == 0) goto L_0x00c8
                com.android.wm.shell.common.DisplayImeController r9 = com.android.p012wm.shell.common.DisplayImeController.this
                com.android.wm.shell.common.TransactionPool r9 = r9.mTransactionPool
                android.view.SurfaceControl$Transaction r9 = r9.acquire()
                boolean r0 = r7.mImeShowing
                if (r0 == 0) goto L_0x00bb
                r9.show(r8)
                goto L_0x00be
            L_0x00bb:
                r9.hide(r8)
            L_0x00be:
                r9.apply()
                com.android.wm.shell.common.DisplayImeController r8 = com.android.p012wm.shell.common.DisplayImeController.this
                com.android.wm.shell.common.TransactionPool r8 = r8.mTransactionPool
                r8.release(r9)
            L_0x00c8:
                boolean r8 = r7.mImeShowing
                if (r8 != 0) goto L_0x00e9
                com.android.wm.shell.common.DisplayImeController r8 = com.android.p012wm.shell.common.DisplayImeController.this
                java.util.Objects.requireNonNull(r8)
                java.lang.String r8 = "input_method"
                android.os.IBinder r8 = android.os.ServiceManager.getService(r8)
                com.android.internal.view.IInputMethodManager r8 = com.android.internal.view.IInputMethodManager.Stub.asInterface(r8)
                if (r8 == 0) goto L_0x00e9
                r8.removeImeSurface()     // Catch:{ RemoteException -> 0x00e1 }
                goto L_0x00e9
            L_0x00e1:
                r8 = move-exception
                java.lang.String r9 = "DisplayImeController"
                java.lang.String r0 = "Failed to remove IME surface."
                android.util.Slog.e(r9, r0, r8)
            L_0x00e9:
                android.view.InsetsSourceControl r8 = r7.mImeSourceControl
                if (r8 == 0) goto L_0x00f2
                com.android.systemui.classifier.BrightLineFalsingManager$2$$ExternalSyntheticLambda0 r9 = com.android.systemui.classifier.BrightLineFalsingManager$2$$ExternalSyntheticLambda0.INSTANCE$1
                r8.release(r9)
            L_0x00f2:
                r7.mImeSourceControl = r3
            L_0x00f4:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.common.DisplayImeController.PerDisplay.insetsControlChanged(android.view.InsetsState, android.view.InsetsSourceControl[]):void");
        }

        public final void showInsets(int i) {
            if ((i & WindowInsets.Type.ime()) != 0) {
                startAnimation(true, false);
            }
        }
    }

    public final void addPositionProcessor(ImePositionProcessor imePositionProcessor) {
        synchronized (this.mPositionProcessors) {
            if (!this.mPositionProcessors.contains(imePositionProcessor)) {
                this.mPositionProcessors.add(imePositionProcessor);
            }
        }
    }

    public final void onDisplayAdded(int i) {
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(i);
        Objects.requireNonNull(displayLayout);
        PerDisplay perDisplay = new PerDisplay(i, displayLayout.mRotation);
        this.mDisplayInsetsController.addInsetsChangedListener(perDisplay.mDisplayId, perDisplay);
        this.mImePerDisplay.put(i, perDisplay);
    }

    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        boolean z;
        InsetsSource source;
        PerDisplay perDisplay = this.mImePerDisplay.get(i);
        if (perDisplay != null) {
            DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(i);
            Objects.requireNonNull(displayLayout);
            if (displayLayout.mRotation != perDisplay.mRotation) {
                PerDisplay perDisplay2 = this.mImePerDisplay.get(i);
                if (perDisplay2 == null || (source = perDisplay2.mInsetsState.getSource(19)) == null || perDisplay2.mImeSourceControl == null || !source.isVisible()) {
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    perDisplay.startAnimation(true, false);
                }
            }
        }
    }

    public final void onDisplayRemoved(int i) {
        PerDisplay perDisplay = this.mImePerDisplay.get(i);
        if (perDisplay != null) {
            DisplayInsetsController displayInsetsController = DisplayImeController.this.mDisplayInsetsController;
            int i2 = perDisplay.mDisplayId;
            Objects.requireNonNull(displayInsetsController);
            CopyOnWriteArrayList copyOnWriteArrayList = displayInsetsController.mListeners.get(i2);
            if (copyOnWriteArrayList != null) {
                copyOnWriteArrayList.remove(perDisplay);
            }
            this.mImePerDisplay.remove(i);
        }
    }

    public DisplayImeController(IWindowManager iWindowManager, DisplayController displayController, DisplayInsetsController displayInsetsController, ShellExecutor shellExecutor, TransactionPool transactionPool) {
        this.mWmService = iWindowManager;
        this.mDisplayController = displayController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mMainExecutor = shellExecutor;
        this.mTransactionPool = transactionPool;
    }
}
