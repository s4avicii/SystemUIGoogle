package com.android.p012wm.shell;

import android.app.ResourcesManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.display.DisplayManager;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.util.SparseArray;
import android.view.Display;
import android.view.SurfaceControl;
import android.window.DisplayAreaAppearedInfo;
import android.window.DisplayAreaInfo;
import android.window.DisplayAreaOrganizer;
import com.android.internal.protolog.BaseProtoLogImpl;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.android.wm.shell.RootTaskDisplayAreaOrganizer */
public class RootTaskDisplayAreaOrganizer extends DisplayAreaOrganizer {
    public final Context mContext;
    public final SparseArray<DisplayAreaContext> mDisplayAreaContexts = new SparseArray<>();
    public final SparseArray<DisplayAreaInfo> mDisplayAreasInfo = new SparseArray<>();
    public final SparseArray<SurfaceControl> mLeashes = new SparseArray<>();
    public final SparseArray<ArrayList<RootTaskDisplayAreaListener>> mListeners = new SparseArray<>();

    /* renamed from: com.android.wm.shell.RootTaskDisplayAreaOrganizer$DisplayAreaContext */
    public static class DisplayAreaContext extends ContextWrapper {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final ResourcesManager mResourcesManager = ResourcesManager.getInstance();
        public final Binder mToken;

        public DisplayAreaContext(Context context, Display display) {
            super((Context) null);
            Binder binder = new Binder();
            this.mToken = binder;
            attachBaseContext(context.createTokenContext(binder, display));
        }
    }

    /* renamed from: com.android.wm.shell.RootTaskDisplayAreaOrganizer$RootTaskDisplayAreaListener */
    public interface RootTaskDisplayAreaListener {
        void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo) {
        }

        void onDisplayAreaInfoChanged(DisplayAreaInfo displayAreaInfo) {
        }

        void onDisplayAreaVanished() {
        }
    }

    public final void applyConfigChangesToContext(DisplayAreaInfo displayAreaInfo) {
        int i = displayAreaInfo.displayId;
        Display display = ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(i);
        boolean z = true;
        if (display != null) {
            DisplayAreaContext displayAreaContext = this.mDisplayAreaContexts.get(i);
            if (displayAreaContext == null) {
                displayAreaContext = new DisplayAreaContext(this.mContext, display);
                this.mDisplayAreaContexts.put(i, displayAreaContext);
            }
            Configuration configuration = displayAreaInfo.configuration;
            int i2 = DisplayAreaContext.$r8$clinit;
            if (displayAreaContext.getResources().getConfiguration().diff(configuration) == 0) {
                z = false;
            }
            if (z) {
                displayAreaContext.mResourcesManager.updateResourcesForActivity(displayAreaContext.mToken, configuration, displayAreaContext.getDisplayId());
            }
        } else if (ShellProtoLogCache.WM_SHELL_TASK_ORG_enabled) {
            ShellProtoLogImpl.getSingleInstance().log(BaseProtoLogImpl.LogLevel.WARN, ShellProtoLogGroup.WM_SHELL_TASK_ORG, -581313329, 1, (String) null, new Object[]{Long.valueOf((long) i)});
        }
    }

    public final void onDisplayAreaAppeared(DisplayAreaInfo displayAreaInfo, SurfaceControl surfaceControl) {
        if (displayAreaInfo.featureId == 1) {
            int i = displayAreaInfo.displayId;
            if (this.mDisplayAreasInfo.get(i) == null) {
                this.mDisplayAreasInfo.put(i, displayAreaInfo);
                this.mLeashes.put(i, surfaceControl);
                ArrayList arrayList = this.mListeners.get(i);
                if (arrayList != null) {
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        ((RootTaskDisplayAreaListener) arrayList.get(size)).onDisplayAreaAppeared(displayAreaInfo);
                    }
                }
                applyConfigChangesToContext(displayAreaInfo);
                return;
            }
            throw new IllegalArgumentException("Duplicate DA for displayId: " + i + " displayAreaInfo:" + displayAreaInfo + " mDisplayAreasInfo.get():" + this.mDisplayAreasInfo.get(i));
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unknown feature: ");
        m.append(displayAreaInfo.featureId);
        m.append("displayAreaInfo:");
        m.append(displayAreaInfo);
        throw new IllegalArgumentException(m.toString());
    }

    public final void onDisplayAreaInfoChanged(DisplayAreaInfo displayAreaInfo) {
        int i = displayAreaInfo.displayId;
        if (this.mDisplayAreasInfo.get(i) != null) {
            this.mDisplayAreasInfo.put(i, displayAreaInfo);
            ArrayList arrayList = this.mListeners.get(i);
            if (arrayList != null) {
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    ((RootTaskDisplayAreaListener) arrayList.get(size)).onDisplayAreaInfoChanged(displayAreaInfo);
                }
            }
            applyConfigChangesToContext(displayAreaInfo);
            return;
        }
        throw new IllegalArgumentException("onDisplayAreaInfoChanged() Unknown DA displayId: " + i + " displayAreaInfo:" + displayAreaInfo + " mDisplayAreasInfo.get():" + this.mDisplayAreasInfo.get(i));
    }

    public final void onDisplayAreaVanished(DisplayAreaInfo displayAreaInfo) {
        int i = displayAreaInfo.displayId;
        if (this.mDisplayAreasInfo.get(i) != null) {
            this.mDisplayAreasInfo.remove(i);
            ArrayList arrayList = this.mListeners.get(i);
            if (arrayList != null) {
                int size = arrayList.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    ((RootTaskDisplayAreaListener) arrayList.get(size)).onDisplayAreaVanished();
                }
            }
            this.mDisplayAreaContexts.remove(i);
            return;
        }
        throw new IllegalArgumentException("onDisplayAreaVanished() Unknown DA displayId: " + i + " displayAreaInfo:" + displayAreaInfo + " mDisplayAreasInfo.get():" + this.mDisplayAreasInfo.get(i));
    }

    public final void registerListener(int i, RootTaskDisplayAreaListener rootTaskDisplayAreaListener) {
        ArrayList arrayList = this.mListeners.get(i);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mListeners.put(i, arrayList);
        }
        arrayList.add(rootTaskDisplayAreaListener);
        DisplayAreaInfo displayAreaInfo = this.mDisplayAreasInfo.get(i);
        if (displayAreaInfo != null) {
            rootTaskDisplayAreaListener.onDisplayAreaAppeared(displayAreaInfo);
        }
    }

    public final String toString() {
        StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m("RootTaskDisplayAreaOrganizer", "#");
        m.append(this.mDisplayAreasInfo.size());
        return m.toString();
    }

    public RootTaskDisplayAreaOrganizer(ShellExecutor shellExecutor, Context context) {
        super(shellExecutor);
        this.mContext = context;
        List registerOrganizer = registerOrganizer(1);
        for (int size = registerOrganizer.size() - 1; size >= 0; size--) {
            onDisplayAreaAppeared(((DisplayAreaAppearedInfo) registerOrganizer.get(size)).getDisplayAreaInfo(), ((DisplayAreaAppearedInfo) registerOrganizer.get(size)).getLeash());
        }
    }

    static {
        Class<RootTaskDisplayAreaOrganizer> cls = RootTaskDisplayAreaOrganizer.class;
    }
}
