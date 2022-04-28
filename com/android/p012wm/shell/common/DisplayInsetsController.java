package com.android.p012wm.shell.common;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;
import android.view.IDisplayWindowInsetsController;
import android.view.IWindowManager;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import com.android.p012wm.shell.bubbles.BubbleController$5$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.common.DisplayController;
import com.android.systemui.ScreenDecorations$2$$ExternalSyntheticLambda0;
import com.android.systemui.util.condition.Monitor$$ExternalSyntheticLambda1;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: com.android.wm.shell.common.DisplayInsetsController */
public final class DisplayInsetsController implements DisplayController.OnDisplaysChangedListener {
    public final DisplayController mDisplayController;
    public final SparseArray<PerDisplay> mInsetsPerDisplay = new SparseArray<>();
    public final SparseArray<CopyOnWriteArrayList<OnInsetsChangedListener>> mListeners = new SparseArray<>();
    public final ShellExecutor mMainExecutor;
    public final IWindowManager mWmService;

    /* renamed from: com.android.wm.shell.common.DisplayInsetsController$OnInsetsChangedListener */
    public interface OnInsetsChangedListener {
        void hideInsets(int i) {
        }

        void insetsChanged(InsetsState insetsState) {
        }

        void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) {
        }

        void showInsets(int i) {
        }

        void topFocusedWindowChanged() {
        }
    }

    /* renamed from: com.android.wm.shell.common.DisplayInsetsController$PerDisplay */
    public class PerDisplay {
        public final int mDisplayId;
        public final DisplayWindowInsetsControllerImpl mInsetsControllerImpl = new DisplayWindowInsetsControllerImpl();

        /* renamed from: com.android.wm.shell.common.DisplayInsetsController$PerDisplay$DisplayWindowInsetsControllerImpl */
        public class DisplayWindowInsetsControllerImpl extends IDisplayWindowInsetsController.Stub {
            public static final /* synthetic */ int $r8$clinit = 0;

            public DisplayWindowInsetsControllerImpl() {
            }

            public final void hideInsets(int i, boolean z) throws RemoteException {
                DisplayInsetsController.this.mMainExecutor.execute(new C1842x9b4a9e25(this, i, z));
            }

            public final void insetsChanged(InsetsState insetsState) throws RemoteException {
                DisplayInsetsController.this.mMainExecutor.execute(new ScreenDecorations$2$$ExternalSyntheticLambda0(this, insetsState, 2));
            }

            public final void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) throws RemoteException {
                DisplayInsetsController.this.mMainExecutor.execute(new BubbleController$5$$ExternalSyntheticLambda0(this, insetsState, insetsSourceControlArr, 1));
            }

            public final void showInsets(int i, boolean z) throws RemoteException {
                DisplayInsetsController.this.mMainExecutor.execute(new C1841x9b4a9e24(this, i, z));
            }

            public final void topFocusedWindowChanged(String str) throws RemoteException {
                DisplayInsetsController.this.mMainExecutor.execute(new Monitor$$ExternalSyntheticLambda1(this, str, 2));
            }
        }

        public PerDisplay(int i) {
            this.mDisplayId = i;
        }
    }

    public final void addInsetsChangedListener(int i, OnInsetsChangedListener onInsetsChangedListener) {
        CopyOnWriteArrayList copyOnWriteArrayList = this.mListeners.get(i);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList();
            this.mListeners.put(i, copyOnWriteArrayList);
        }
        if (!copyOnWriteArrayList.contains(onInsetsChangedListener)) {
            copyOnWriteArrayList.add(onInsetsChangedListener);
        }
    }

    public final void onDisplayAdded(int i) {
        PerDisplay perDisplay = new PerDisplay(i);
        try {
            this.mWmService.setDisplayWindowInsetsController(perDisplay.mDisplayId, perDisplay.mInsetsControllerImpl);
        } catch (RemoteException unused) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unable to set insets controller on display ");
            m.append(perDisplay.mDisplayId);
            Slog.w("DisplayInsetsController", m.toString());
        }
        this.mInsetsPerDisplay.put(i, perDisplay);
    }

    public final void onDisplayRemoved(int i) {
        PerDisplay perDisplay = this.mInsetsPerDisplay.get(i);
        if (perDisplay != null) {
            try {
                DisplayInsetsController.this.mWmService.setDisplayWindowInsetsController(perDisplay.mDisplayId, (IDisplayWindowInsetsController) null);
            } catch (RemoteException unused) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unable to remove insets controller on display ");
                m.append(perDisplay.mDisplayId);
                Slog.w("DisplayInsetsController", m.toString());
            }
            this.mInsetsPerDisplay.remove(i);
        }
    }

    public DisplayInsetsController(IWindowManager iWindowManager, DisplayController displayController, ShellExecutor shellExecutor) {
        this.mWmService = iWindowManager;
        this.mDisplayController = displayController;
        this.mMainExecutor = shellExecutor;
    }
}
