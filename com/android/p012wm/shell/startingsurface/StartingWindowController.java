package com.android.p012wm.shell.startingsurface;

import android.content.Context;
import android.util.SparseIntArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.function.TriConsumer;
import com.android.launcher3.icons.IconProvider;
import com.android.p012wm.shell.ShellInitImpl$$ExternalSyntheticLambda5;
import com.android.p012wm.shell.common.RemoteCallable;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SingleInstanceRemoteListener;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.startingsurface.IStartingWindow;
import com.android.systemui.p006qs.tiles.InternetTile$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda4;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda6;

/* renamed from: com.android.wm.shell.startingsurface.StartingWindowController */
public final class StartingWindowController implements RemoteCallable<StartingWindowController> {
    public final Context mContext;
    public final StartingSurfaceImpl mImpl = new StartingSurfaceImpl();
    public final ShellExecutor mSplashScreenExecutor;
    public final StartingSurfaceDrawer mStartingSurfaceDrawer;
    public final StartingWindowTypeAlgorithm mStartingWindowTypeAlgorithm;
    @GuardedBy({"mTaskBackgroundColors"})
    public final SparseIntArray mTaskBackgroundColors = new SparseIntArray();
    public TriConsumer<Integer, Integer, Integer> mTaskLaunchingCallback;

    /* renamed from: com.android.wm.shell.startingsurface.StartingWindowController$StartingSurfaceImpl */
    public class StartingSurfaceImpl implements StartingSurface {
        public IStartingWindowImpl mIStartingWindow;

        public StartingSurfaceImpl() {
        }

        public final IStartingWindow createExternalInterface() {
            IStartingWindowImpl iStartingWindowImpl = this.mIStartingWindow;
            if (iStartingWindowImpl != null) {
                iStartingWindowImpl.mController = null;
            }
            IStartingWindowImpl iStartingWindowImpl2 = new IStartingWindowImpl(StartingWindowController.this);
            this.mIStartingWindow = iStartingWindowImpl2;
            return iStartingWindowImpl2;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0028, code lost:
            if (r1 != null) goto L_0x002c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x002c, code lost:
            r3 = r1.packageName;
            r4 = r9.userId;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            r5 = r8.mContext.createPackageContextAsUser(r3, 4, android.os.UserHandle.of(r4));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            r3 = android.app.ActivityThread.getPackageManager().getSplashScreenTheme(r3, r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0043, code lost:
            if (r3 == null) goto L_0x004f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0045, code lost:
            r3 = r5.getResources().getIdentifier(r3, (java.lang.String) null, (java.lang.String) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x004f, code lost:
            r3 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0050, code lost:
            if (r3 == 0) goto L_0x0053;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0057, code lost:
            if (r1.getThemeResource() == 0) goto L_0x005e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0059, code lost:
            r3 = r1.getThemeResource();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x005e, code lost:
            r3 = 16974563;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0065, code lost:
            if (r3 == r5.getThemeResId()) goto L_0x006a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0067, code lost:
            r5.setTheme(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x006a, code lost:
            java.util.Objects.requireNonNull(r8.mSplashscreenContentDrawer);
            r8 = new com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.SplashScreenWindowAttrs();
            com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.getWindowAttrs(r5, r8);
            r2 = com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.peekWindowBGColor(r5, r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x007c, code lost:
            r8 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x007d, code lost:
            r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m("failed get starting window background color at taskId: ");
            r1.append(r9.taskId);
            android.util.Slog.w("ShellStartingWindow", r1.toString(), r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0090, code lost:
            r8 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0091, code lost:
            r1 = androidx.activity.result.ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("Failed creating package context with package name ", r3, " for user ");
            r1.append(r9.userId);
            android.util.Slog.w("ShellStartingWindow", r1.toString(), r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x001c, code lost:
            r8 = r8.this$0.mStartingSurfaceDrawer;
            java.util.Objects.requireNonNull(r8);
            r1 = r9.topActivityInfo;
            r2 = 0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final int getBackgroundColor(android.app.ActivityManager.RunningTaskInfo r9) {
            /*
                r8 = this;
                com.android.wm.shell.startingsurface.StartingWindowController r0 = com.android.p012wm.shell.startingsurface.StartingWindowController.this
                android.util.SparseIntArray r0 = r0.mTaskBackgroundColors
                monitor-enter(r0)
                com.android.wm.shell.startingsurface.StartingWindowController r1 = com.android.p012wm.shell.startingsurface.StartingWindowController.this     // Catch:{ all -> 0x00ad }
                android.util.SparseIntArray r1 = r1.mTaskBackgroundColors     // Catch:{ all -> 0x00ad }
                int r2 = r9.taskId     // Catch:{ all -> 0x00ad }
                int r1 = r1.indexOfKey(r2)     // Catch:{ all -> 0x00ad }
                if (r1 < 0) goto L_0x001b
                com.android.wm.shell.startingsurface.StartingWindowController r8 = com.android.p012wm.shell.startingsurface.StartingWindowController.this     // Catch:{ all -> 0x00ad }
                android.util.SparseIntArray r8 = r8.mTaskBackgroundColors     // Catch:{ all -> 0x00ad }
                int r8 = r8.valueAt(r1)     // Catch:{ all -> 0x00ad }
                monitor-exit(r0)     // Catch:{ all -> 0x00ad }
                return r8
            L_0x001b:
                monitor-exit(r0)     // Catch:{ all -> 0x00ad }
                com.android.wm.shell.startingsurface.StartingWindowController r8 = com.android.p012wm.shell.startingsurface.StartingWindowController.this
                com.android.wm.shell.startingsurface.StartingSurfaceDrawer r8 = r8.mStartingSurfaceDrawer
                java.util.Objects.requireNonNull(r8)
                java.lang.String r0 = "ShellStartingWindow"
                android.content.pm.ActivityInfo r1 = r9.topActivityInfo
                r2 = 0
                if (r1 != 0) goto L_0x002c
                goto L_0x00a5
            L_0x002c:
                java.lang.String r3 = r1.packageName
                int r4 = r9.userId
                android.content.Context r5 = r8.mContext     // Catch:{ NameNotFoundException -> 0x0090 }
                r6 = 4
                android.os.UserHandle r7 = android.os.UserHandle.of(r4)     // Catch:{ NameNotFoundException -> 0x0090 }
                android.content.Context r5 = r5.createPackageContextAsUser(r3, r6, r7)     // Catch:{ NameNotFoundException -> 0x0090 }
                android.content.pm.IPackageManager r6 = android.app.ActivityThread.getPackageManager()     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                java.lang.String r3 = r6.getSplashScreenTheme(r3, r4)     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                if (r3 == 0) goto L_0x004f
                android.content.res.Resources r4 = r5.getResources()     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                r6 = 0
                int r3 = r4.getIdentifier(r3, r6, r6)     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                goto L_0x0050
            L_0x004f:
                r3 = r2
            L_0x0050:
                if (r3 == 0) goto L_0x0053
                goto L_0x0061
            L_0x0053:
                int r3 = r1.getThemeResource()     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                if (r3 == 0) goto L_0x005e
                int r3 = r1.getThemeResource()     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                goto L_0x0061
            L_0x005e:
                r3 = 16974563(0x10302e3, float:2.406297E-38)
            L_0x0061:
                int r1 = r5.getThemeResId()     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                if (r3 == r1) goto L_0x006a
                r5.setTheme(r3)     // Catch:{ RemoteException | RuntimeException -> 0x007c }
            L_0x006a:
                com.android.wm.shell.startingsurface.SplashscreenContentDrawer r8 = r8.mSplashscreenContentDrawer     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                java.util.Objects.requireNonNull(r8)     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SplashScreenWindowAttrs r8 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SplashScreenWindowAttrs     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                r8.<init>()     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.getWindowAttrs(r5, r8)     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                int r2 = com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.peekWindowBGColor(r5, r8)     // Catch:{ RemoteException | RuntimeException -> 0x007c }
                goto L_0x00a5
            L_0x007c:
                r8 = move-exception
                java.lang.String r1 = "failed get starting window background color at taskId: "
                java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
                int r9 = r9.taskId
                r1.append(r9)
                java.lang.String r9 = r1.toString()
                android.util.Slog.w(r0, r9, r8)
                goto L_0x00a5
            L_0x0090:
                r8 = move-exception
                java.lang.String r1 = "Failed creating package context with package name "
                java.lang.String r4 = " for user "
                java.lang.StringBuilder r1 = androidx.activity.result.ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m(r1, r3, r4)
                int r9 = r9.userId
                r1.append(r9)
                java.lang.String r9 = r1.toString()
                android.util.Slog.w(r0, r9, r8)
            L_0x00a5:
                if (r2 == 0) goto L_0x00a8
                goto L_0x00ac
            L_0x00a8:
                int r2 = com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer.getSystemBGColor()
            L_0x00ac:
                return r2
            L_0x00ad:
                r8 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00ad }
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.startingsurface.StartingWindowController.StartingSurfaceImpl.getBackgroundColor(android.app.ActivityManager$RunningTaskInfo):int");
        }

        public final void setSysuiProxy(StatusBar$$ExternalSyntheticLambda4 statusBar$$ExternalSyntheticLambda4) {
            StartingWindowController.this.mSplashScreenExecutor.execute(new InternetTile$$ExternalSyntheticLambda0(this, statusBar$$ExternalSyntheticLambda4, 2));
        }
    }

    /* renamed from: com.android.wm.shell.startingsurface.StartingWindowController$IStartingWindowImpl */
    public static class IStartingWindowImpl extends IStartingWindow.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public StartingWindowController mController;
        public SingleInstanceRemoteListener<StartingWindowController, IStartingWindowListener> mListener;
        public final C1932x795f7bd0 mStartingWindowListener = new C1932x795f7bd0(this);

        public IStartingWindowImpl(StartingWindowController startingWindowController) {
            this.mController = startingWindowController;
            this.mListener = new SingleInstanceRemoteListener<>(startingWindowController, new WMShell$$ExternalSyntheticLambda6(this, 4), ShellInitImpl$$ExternalSyntheticLambda5.INSTANCE$2);
        }
    }

    public StartingWindowController(Context context, ShellExecutor shellExecutor, StartingWindowTypeAlgorithm startingWindowTypeAlgorithm, IconProvider iconProvider, TransactionPool transactionPool) {
        this.mContext = context;
        this.mStartingSurfaceDrawer = new StartingSurfaceDrawer(context, shellExecutor, iconProvider, transactionPool);
        this.mStartingWindowTypeAlgorithm = startingWindowTypeAlgorithm;
        this.mSplashScreenExecutor = shellExecutor;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final ShellExecutor getRemoteCallExecutor() {
        return this.mSplashScreenExecutor;
    }
}
