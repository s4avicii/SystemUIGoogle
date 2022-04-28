package com.android.p012wm.shell.common;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.util.SparseArray;
import android.view.Display;
import android.view.IDisplayWindowListener;
import android.view.IWindowManager;
import android.view.InsetsState;
import com.android.p012wm.shell.common.DisplayChangeController;
import com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.DisplayController */
public final class DisplayController {
    public final DisplayChangeController mChangeController;
    public final Context mContext;
    public final ArrayList<OnDisplaysChangedListener> mDisplayChangedListeners = new ArrayList<>();
    public final DisplayWindowListenerImpl mDisplayContainerListener;
    public final SparseArray<DisplayRecord> mDisplays = new SparseArray<>();
    public final ShellExecutor mMainExecutor;
    public final IWindowManager mWmService;

    /* renamed from: com.android.wm.shell.common.DisplayController$DisplayRecord */
    public static class DisplayRecord {
        public Context mContext;
        public DisplayLayout mDisplayLayout;
        public InsetsState mInsetsState = new InsetsState();
    }

    /* renamed from: com.android.wm.shell.common.DisplayController$DisplayWindowListenerImpl */
    public class DisplayWindowListenerImpl extends IDisplayWindowListener.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;

        public DisplayWindowListenerImpl() {
        }

        public final void onDisplayAdded(int i) {
            DisplayController.this.mMainExecutor.execute(new VibratorHelper$$ExternalSyntheticLambda1(this, i, 2));
        }

        public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
            DisplayController.this.mMainExecutor.execute(new C1838xd1ef1ed1(this, i, configuration));
        }

        public final void onDisplayRemoved(int i) {
            DisplayController.this.mMainExecutor.execute(new C1836xd1ef1ecf(this, i));
        }

        public final void onFixedRotationFinished(int i) {
            DisplayController.this.mMainExecutor.execute(new KeyguardIndicationController$$ExternalSyntheticLambda1(this, i, 1));
        }

        public final void onFixedRotationStarted(int i, int i2) {
            DisplayController.this.mMainExecutor.execute(new C1837xd1ef1ed0(this, i, i2));
        }

        public final void onKeepClearAreasChanged(int i, List<Rect> list, List<Rect> list2) {
            DisplayController.this.mMainExecutor.execute(new C1839xd1ef1ed2(this, i, list, list2));
        }
    }

    /* renamed from: com.android.wm.shell.common.DisplayController$OnDisplaysChangedListener */
    public interface OnDisplaysChangedListener {
        void onDisplayAdded(int i) {
        }

        void onDisplayConfigurationChanged(int i, Configuration configuration) {
        }

        void onDisplayRemoved(int i) {
        }

        void onFixedRotationFinished() {
        }

        void onFixedRotationStarted(int i) {
        }
    }

    public final void addDisplayChangingController(DisplayChangeController.OnDisplayChangingListener onDisplayChangingListener) {
        DisplayChangeController displayChangeController = this.mChangeController;
        Objects.requireNonNull(displayChangeController);
        displayChangeController.mRotationListener.add(onDisplayChangingListener);
    }

    public final void addDisplayWindowListener(OnDisplaysChangedListener onDisplaysChangedListener) {
        synchronized (this.mDisplays) {
            if (!this.mDisplayChangedListeners.contains(onDisplaysChangedListener)) {
                this.mDisplayChangedListeners.add(onDisplaysChangedListener);
                for (int i = 0; i < this.mDisplays.size(); i++) {
                    onDisplaysChangedListener.onDisplayAdded(this.mDisplays.keyAt(i));
                }
            }
        }
    }

    public final Display getDisplay(int i) {
        return ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(i);
    }

    public final Context getDisplayContext(int i) {
        DisplayRecord displayRecord = this.mDisplays.get(i);
        if (displayRecord != null) {
            return displayRecord.mContext;
        }
        return null;
    }

    public final DisplayLayout getDisplayLayout(int i) {
        DisplayRecord displayRecord = this.mDisplays.get(i);
        if (displayRecord != null) {
            return displayRecord.mDisplayLayout;
        }
        return null;
    }

    public final void onDisplayAdded(int i) {
        Context context;
        synchronized (this.mDisplays) {
            if (this.mDisplays.get(i) == null) {
                Display display = getDisplay(i);
                if (display != null) {
                    if (i == 0) {
                        context = this.mContext;
                    } else {
                        context = this.mContext.createDisplayContext(display);
                    }
                    DisplayRecord displayRecord = new DisplayRecord();
                    DisplayLayout displayLayout = new DisplayLayout(context, display);
                    displayRecord.mContext = context;
                    displayRecord.mDisplayLayout = displayLayout;
                    Resources resources = context.getResources();
                    displayLayout.mInsetsState = displayRecord.mInsetsState;
                    displayLayout.recalcInsets(resources);
                    this.mDisplays.put(i, displayRecord);
                    for (int i2 = 0; i2 < this.mDisplayChangedListeners.size(); i2++) {
                        this.mDisplayChangedListeners.get(i2).onDisplayAdded(i);
                    }
                }
            }
        }
    }

    public DisplayController(Context context, IWindowManager iWindowManager, ShellExecutor shellExecutor) {
        this.mMainExecutor = shellExecutor;
        this.mContext = context;
        this.mWmService = iWindowManager;
        this.mChangeController = new DisplayChangeController(iWindowManager, shellExecutor);
        this.mDisplayContainerListener = new DisplayWindowListenerImpl();
    }
}
