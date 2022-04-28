package com.android.p012wm.shell.common;

import android.content.Context;
import android.content.res.Configuration;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Region;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.IScrollCaptureResponseListener;
import android.view.IWindow;
import android.view.IWindowManager;
import android.view.IWindowSessionCallback;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.ScrollCaptureResponse;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceSession;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.widget.FrameLayout;
import android.window.ClientWindowFrames;
import com.android.internal.os.IResultReceiver;
import com.android.p012wm.shell.common.DisplayController;
import java.util.HashMap;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.SystemWindows */
public final class SystemWindows {
    public final DisplayController mDisplayController;
    public final C18491 mDisplayListener;
    public final SparseArray<PerDisplay> mPerDisplay = new SparseArray<>();
    public final HashMap<View, SurfaceControlViewHost> mViewRoots = new HashMap<>();
    public final IWindowManager mWmService;

    /* renamed from: com.android.wm.shell.common.SystemWindows$ContainerWindow */
    public static class ContainerWindow extends IWindow.Stub {
        public final void closeSystemDialogs(String str) {
        }

        public final void dispatchAppVisibility(boolean z) {
        }

        public final void dispatchDragEvent(DragEvent dragEvent) {
        }

        public final void dispatchGetNewSurface() {
        }

        public final void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
        }

        public final void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) {
        }

        public final void dispatchWindowShown() {
        }

        public final void executeCommand(String str, String str2, ParcelFileDescriptor parcelFileDescriptor) {
        }

        public final void hideInsets(int i, boolean z) {
        }

        public final void insetsChanged(InsetsState insetsState, boolean z, boolean z2) {
        }

        public final void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr, boolean z, boolean z2) {
        }

        public final void moved(int i, int i2) {
        }

        public final void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) {
        }

        public final void resized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, boolean z2, boolean z3, int i) {
        }

        public final void showInsets(int i, boolean z) {
        }

        public final void updatePointerIcon(float f, float f2) {
        }

        public final void requestScrollCapture(IScrollCaptureResponseListener iScrollCaptureResponseListener) {
            try {
                iScrollCaptureResponseListener.onScrollCaptureResponse(new ScrollCaptureResponse.Builder().setDescription("Not Implemented").build());
            } catch (RemoteException unused) {
            }
        }
    }

    /* renamed from: com.android.wm.shell.common.SystemWindows$PerDisplay */
    public class PerDisplay {
        public final int mDisplayId;
        public final SparseArray<SysUiWindowManager> mWwms = new SparseArray<>();

        public PerDisplay(int i) {
            this.mDisplayId = i;
        }

        public final void setShellRootAccessibilityWindow(int i, FrameLayout frameLayout) {
            IWindow iWindow;
            if (this.mWwms.get(i) != null) {
                try {
                    SystemWindows systemWindows = SystemWindows.this;
                    IWindowManager iWindowManager = systemWindows.mWmService;
                    int i2 = this.mDisplayId;
                    if (frameLayout != null) {
                        iWindow = systemWindows.mViewRoots.get(frameLayout).getWindowToken();
                    } else {
                        iWindow = null;
                    }
                    iWindowManager.setShellRootAccessibilityWindow(i2, i, iWindow);
                } catch (RemoteException e) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Error setting accessibility window for ");
                    m.append(this.mDisplayId);
                    m.append(":");
                    m.append(i);
                    Slog.e("SystemWindows", m.toString(), e);
                }
            }
        }
    }

    /* renamed from: com.android.wm.shell.common.SystemWindows$SysUiWindowManager */
    public class SysUiWindowManager extends WindowlessWindowManager {
        public final HashMap<IBinder, SurfaceControl> mLeashForWindow = new HashMap<>();

        public final SurfaceControl getSurfaceControlForWindow(View view) {
            SurfaceControl surfaceControl;
            synchronized (this) {
                surfaceControl = this.mLeashForWindow.get(getWindowBinder(view));
            }
            return surfaceControl;
        }

        public final void attachToParentSurface(IWindow iWindow, SurfaceControl.Builder builder) {
            SurfaceControl build = new SurfaceControl.Builder(new SurfaceSession()).setContainerLayer().setName("SystemWindowLeash").setHidden(false).setParent(this.mRootSurface).setCallsite("SysUiWIndowManager#attachToParentSurface").build();
            synchronized (this) {
                this.mLeashForWindow.put(iWindow.asBinder(), build);
            }
            builder.setParent(build);
        }

        public SysUiWindowManager(Context context, SurfaceControl surfaceControl) {
            super(context.getResources().getConfiguration(), surfaceControl, (IBinder) null);
        }

        public final void remove(IWindow iWindow) throws RemoteException {
            SystemWindows.super.remove(iWindow);
            synchronized (this) {
                IBinder asBinder = iWindow.asBinder();
                new SurfaceControl.Transaction().remove(this.mLeashForWindow.get(asBinder)).apply();
                this.mLeashForWindow.remove(asBinder);
            }
        }

        public final void setTouchableRegionForWindow(View view, Region region) {
            IBinder windowToken = view.getWindowToken();
            if (windowToken != null) {
                setTouchRegion(windowToken, region);
            }
        }
    }

    public final SurfaceControl getViewSurface(View view) {
        for (int i = 0; i < this.mPerDisplay.size(); i++) {
            for (int i2 = 0; i2 < this.mPerDisplay.valueAt(i).mWwms.size(); i2++) {
                SurfaceControl surfaceControlForWindow = this.mPerDisplay.valueAt(i).mWwms.valueAt(i2).getSurfaceControlForWindow(view);
                if (surfaceControlForWindow != null) {
                    return surfaceControlForWindow;
                }
            }
        }
        return null;
    }

    public final void addView(FrameLayout frameLayout, WindowManager.LayoutParams layoutParams, int i, int i2) {
        SurfaceControl surfaceControl;
        PerDisplay perDisplay = this.mPerDisplay.get(i);
        if (perDisplay == null) {
            perDisplay = new PerDisplay(i);
            this.mPerDisplay.put(i, perDisplay);
        }
        WindowlessWindowManager windowlessWindowManager = (SysUiWindowManager) perDisplay.mWwms.get(i2);
        if (windowlessWindowManager == null) {
            try {
                surfaceControl = SystemWindows.this.mWmService.addShellRoot(perDisplay.mDisplayId, new ContainerWindow(), i2);
            } catch (RemoteException unused) {
                surfaceControl = null;
            }
            if (surfaceControl == null) {
                Slog.e("SystemWindows", "Unable to get root surfacecontrol for systemui");
                windowlessWindowManager = null;
            } else {
                WindowlessWindowManager sysUiWindowManager = new SysUiWindowManager(SystemWindows.this.mDisplayController.getDisplayContext(perDisplay.mDisplayId), surfaceControl);
                perDisplay.mWwms.put(i2, sysUiWindowManager);
                windowlessWindowManager = sysUiWindowManager;
            }
        }
        if (windowlessWindowManager == null) {
            Slog.e("SystemWindows", "Unable to create systemui root");
            return;
        }
        SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(frameLayout.getContext(), SystemWindows.this.mDisplayController.getDisplay(perDisplay.mDisplayId), windowlessWindowManager, true);
        layoutParams.flags |= 16777216;
        surfaceControlViewHost.setView(frameLayout, layoutParams);
        SystemWindows.this.mViewRoots.put(frameLayout, surfaceControlViewHost);
        perDisplay.setShellRootAccessibilityWindow(i2, frameLayout);
    }

    public final IBinder getFocusGrantToken(FrameLayout frameLayout) {
        SurfaceControlViewHost surfaceControlViewHost = this.mViewRoots.get(frameLayout);
        if (surfaceControlViewHost != null) {
            return surfaceControlViewHost.getFocusGrantToken();
        }
        Slog.e("SystemWindows", "Couldn't get focus grant token since view does not exist in SystemWindow:" + frameLayout);
        return null;
    }

    public final void updateViewLayout(View view, WindowManager.LayoutParams layoutParams) {
        SurfaceControlViewHost surfaceControlViewHost = this.mViewRoots.get(view);
        if (surfaceControlViewHost != null && (layoutParams instanceof WindowManager.LayoutParams)) {
            view.setLayoutParams(layoutParams);
            surfaceControlViewHost.relayout(layoutParams);
        }
    }

    public SystemWindows(DisplayController displayController, IWindowManager iWindowManager) {
        C18491 r0 = new DisplayController.OnDisplaysChangedListener() {
            public final void onDisplayAdded(int i) {
            }

            public final void onDisplayRemoved(int i) {
            }

            public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
                PerDisplay perDisplay = SystemWindows.this.mPerDisplay.get(i);
                if (perDisplay != null) {
                    for (int i2 = 0; i2 < perDisplay.mWwms.size(); i2++) {
                        SysUiWindowManager valueAt = perDisplay.mWwms.valueAt(i2);
                        Objects.requireNonNull(valueAt);
                        valueAt.setConfiguration(configuration);
                    }
                }
            }
        };
        this.mDisplayListener = r0;
        this.mWmService = iWindowManager;
        this.mDisplayController = displayController;
        displayController.addDisplayWindowListener(r0);
        try {
            iWindowManager.openSession(new IWindowSessionCallback.Stub() {
                public final void onAnimatorScaleChanged(float f) {
                }
            });
        } catch (RemoteException e) {
            Slog.e("SystemWindows", "Unable to create layer", e);
        }
    }
}
