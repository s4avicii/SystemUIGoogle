package com.android.settingslib.applications;

import android.app.Application;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.format.Formatter;
import android.util.SparseArray;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.R$dimen;
import com.android.settingslib.Utils;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda20;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.io.File;
import java.lang.ref.WeakReference;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public final class ApplicationsState {
    public static final C05831 ALPHA_COMPARATOR = new Comparator<AppEntry>() {
        public final Collator sCollator = Collator.getInstance();

        public final int compare(Object obj, Object obj2) {
            ApplicationInfo applicationInfo;
            int compare;
            AppEntry appEntry = (AppEntry) obj;
            AppEntry appEntry2 = (AppEntry) obj2;
            int compare2 = this.sCollator.compare(appEntry.label, appEntry2.label);
            if (compare2 != 0) {
                return compare2;
            }
            ApplicationInfo applicationInfo2 = appEntry.info;
            if (applicationInfo2 == null || (applicationInfo = appEntry2.info) == null || (compare = this.sCollator.compare(applicationInfo2.packageName, applicationInfo.packageName)) == 0) {
                return appEntry.info.uid - appEntry2.info.uid;
            }
            return compare;
        }
    };
    public static final C058518 FILTER_AUDIO = new Object() {
    };
    public static final C05888 FILTER_DOWNLOADED_AND_LAUNCHER = new Object() {
    };
    public static final C058417 FILTER_GAMES = new Object() {
    };
    public static final C058619 FILTER_MOVIES = new Object() {
    };
    public static final C058720 FILTER_PHOTOS = new Object() {
    };
    public static ApplicationsState sInstance;
    public static final Object sLock = new Object();
    public final ArrayList<WeakReference<Session>> mActiveSessions = new ArrayList<>();
    public final int mAdminRetrieveFlags;
    public final ArrayList<AppEntry> mAppEntries = new ArrayList<>();
    public ArrayList mApplications = new ArrayList();
    public final BackgroundHandler mBackgroundHandler;
    public final Application mContext;
    public String mCurComputingSizePkg;
    public int mCurComputingSizeUserId;
    public UUID mCurComputingSizeUuid;
    public long mCurId = 1;
    public final SparseArray<HashMap<String, AppEntry>> mEntriesMap = new SparseArray<>();
    public InterestingConfigChanges mInterestingConfigChanges = new InterestingConfigChanges(-2147474940);
    public final IPackageManager mIpm;
    public final MainHandler mMainHandler = new MainHandler(Looper.getMainLooper());
    public final PackageManager mPm;
    public final ArrayList<Session> mRebuildingSessions = new ArrayList<>();
    public final int mRetrieveFlags;
    public final ArrayList<Session> mSessions = new ArrayList<>();
    public final StorageStatsManager mStats;
    public final HashMap<String, Boolean> mSystemModules = new HashMap<>();
    public final UserManager mUm;

    public static class AppEntry extends SizeInfo {
        public final File apkFile;
        public long externalSize;
        public boolean hasLauncherEntry;
        public Drawable icon;
        public ApplicationInfo info;
        public long internalSize;
        public boolean isHomeApp;
        public String label;
        public String labelDescription;
        public boolean mounted;
        public long size = -1;
        public long sizeLoadStart;
        public boolean sizeStale = true;

        public final boolean ensureIconLocked(Application application) {
            Object obj = ApplicationsState.sLock;
            if (ThemeOverlayApplier.SETTINGS_PACKAGE.equals(application.getPackageName())) {
                return false;
            }
            if (this.icon == null) {
                if (this.apkFile.exists()) {
                    this.icon = Utils.getBadgedIcon(application, this.info);
                    return true;
                }
                this.mounted = false;
                this.icon = application.getDrawable(17303679);
            } else if (!this.mounted && this.apkFile.exists()) {
                this.mounted = true;
                this.icon = Utils.getBadgedIcon(application, this.info);
                return true;
            }
            return false;
        }

        public AppEntry(Context context, ApplicationInfo applicationInfo, long j) {
            String str;
            File file = new File(applicationInfo.sourceDir);
            this.apkFile = file;
            this.info = applicationInfo;
            if (this.label == null || !this.mounted) {
                if (!file.exists()) {
                    this.mounted = false;
                    this.label = this.info.packageName;
                } else {
                    this.mounted = true;
                    CharSequence loadLabel = this.info.loadLabel(context.getPackageManager());
                    if (loadLabel != null) {
                        str = loadLabel.toString();
                    } else {
                        str = this.info.packageName;
                    }
                    this.label = str;
                }
            }
            if (this.labelDescription == null) {
                R$dimen.postOnBackgroundThread(new StatusBar$$ExternalSyntheticLambda20(this, context, 1));
            }
        }
    }

    public class BackgroundHandler extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;
        public boolean mRunning;
        public final C05891 mStatsObserver = new IPackageStatsObserver.Stub() {
            /* JADX WARNING: Code restructure failed: missing block: B:51:0x011b, code lost:
                return;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void onGetStatsCompleted(android.content.pm.PackageStats r20, boolean r21) {
                /*
                    r19 = this;
                    r0 = r19
                    r1 = r20
                    if (r21 != 0) goto L_0x0007
                    return
                L_0x0007:
                    com.android.settingslib.applications.ApplicationsState$BackgroundHandler r2 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.this
                    com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                    android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r2 = r2.mEntriesMap
                    monitor-enter(r2)
                    com.android.settingslib.applications.ApplicationsState$BackgroundHandler r3 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.this     // Catch:{ all -> 0x011c }
                    com.android.settingslib.applications.ApplicationsState r3 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x011c }
                    android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r3 = r3.mEntriesMap     // Catch:{ all -> 0x011c }
                    int r4 = r1.userHandle     // Catch:{ all -> 0x011c }
                    java.lang.Object r3 = r3.get(r4)     // Catch:{ all -> 0x011c }
                    java.util.HashMap r3 = (java.util.HashMap) r3     // Catch:{ all -> 0x011c }
                    if (r3 != 0) goto L_0x0020
                    monitor-exit(r2)     // Catch:{ all -> 0x011c }
                    return
                L_0x0020:
                    java.lang.String r4 = r1.packageName     // Catch:{ all -> 0x011c }
                    java.lang.Object r3 = r3.get(r4)     // Catch:{ all -> 0x011c }
                    com.android.settingslib.applications.ApplicationsState$AppEntry r3 = (com.android.settingslib.applications.ApplicationsState.AppEntry) r3     // Catch:{ all -> 0x011c }
                    if (r3 == 0) goto L_0x00f8
                    monitor-enter(r3)     // Catch:{ all -> 0x011c }
                    r4 = 0
                    r3.sizeStale = r4     // Catch:{ all -> 0x00f5 }
                    r5 = 0
                    r3.sizeLoadStart = r5     // Catch:{ all -> 0x00f5 }
                    long r5 = r1.externalCodeSize     // Catch:{ all -> 0x00f5 }
                    long r7 = r1.externalObbSize     // Catch:{ all -> 0x00f5 }
                    long r5 = r5 + r7
                    long r7 = r1.externalDataSize     // Catch:{ all -> 0x00f5 }
                    long r9 = r1.externalMediaSize     // Catch:{ all -> 0x00f5 }
                    long r7 = r7 + r9
                    long r9 = r5 + r7
                    com.android.settingslib.applications.ApplicationsState$BackgroundHandler r11 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.this     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState r11 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x00f5 }
                    java.util.Objects.requireNonNull(r11)     // Catch:{ all -> 0x00f5 }
                    long r11 = r1.codeSize     // Catch:{ all -> 0x00f5 }
                    long r13 = r1.dataSize     // Catch:{ all -> 0x00f5 }
                    long r15 = r11 + r13
                    r17 = r5
                    long r4 = r1.cacheSize     // Catch:{ all -> 0x00f5 }
                    long r15 = r15 - r4
                    long r9 = r9 + r15
                    long r0 = r3.size     // Catch:{ all -> 0x00f5 }
                    int r0 = (r0 > r9 ? 1 : (r0 == r9 ? 0 : -1))
                    if (r0 != 0) goto L_0x0085
                    long r0 = r3.cacheSize     // Catch:{ all -> 0x00f5 }
                    int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                    if (r0 != 0) goto L_0x0085
                    long r0 = r3.codeSize     // Catch:{ all -> 0x00f5 }
                    int r0 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
                    if (r0 != 0) goto L_0x0085
                    long r0 = r3.dataSize     // Catch:{ all -> 0x00f5 }
                    int r0 = (r0 > r13 ? 1 : (r0 == r13 ? 0 : -1))
                    if (r0 != 0) goto L_0x0085
                    long r0 = r3.externalCodeSize     // Catch:{ all -> 0x00f5 }
                    int r0 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
                    if (r0 != 0) goto L_0x0085
                    long r0 = r3.externalDataSize     // Catch:{ all -> 0x00f5 }
                    int r0 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
                    if (r0 != 0) goto L_0x0085
                    long r0 = r3.externalCacheSize     // Catch:{ all -> 0x00f5 }
                    r6 = r20
                    r15 = r7
                    long r7 = r6.externalCacheSize     // Catch:{ all -> 0x00f5 }
                    int r0 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
                    if (r0 == 0) goto L_0x0081
                    goto L_0x0088
                L_0x0081:
                    r4 = 0
                    r0 = r19
                    goto L_0x00db
                L_0x0085:
                    r6 = r20
                    r15 = r7
                L_0x0088:
                    r3.size = r9     // Catch:{ all -> 0x00f5 }
                    r3.cacheSize = r4     // Catch:{ all -> 0x00f5 }
                    r3.codeSize = r11     // Catch:{ all -> 0x00f5 }
                    r3.dataSize = r13     // Catch:{ all -> 0x00f5 }
                    r0 = r17
                    r3.externalCodeSize = r0     // Catch:{ all -> 0x00f5 }
                    r7 = r15
                    r3.externalDataSize = r7     // Catch:{ all -> 0x00f5 }
                    long r0 = r6.externalCacheSize     // Catch:{ all -> 0x00f5 }
                    r3.externalCacheSize = r0     // Catch:{ all -> 0x00f5 }
                    r0 = r19
                    com.android.settingslib.applications.ApplicationsState$BackgroundHandler r1 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.this     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState.m160$$Nest$mgetSizeStr(r1, r9)     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState$BackgroundHandler r1 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.this     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x00f5 }
                    java.util.Objects.requireNonNull(r1)     // Catch:{ all -> 0x00f5 }
                    long r4 = r6.codeSize     // Catch:{ all -> 0x00f5 }
                    long r7 = r6.dataSize     // Catch:{ all -> 0x00f5 }
                    long r4 = r4 + r7
                    long r7 = r6.cacheSize     // Catch:{ all -> 0x00f5 }
                    long r4 = r4 - r7
                    r3.internalSize = r4     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState$BackgroundHandler r1 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.this     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState.m160$$Nest$mgetSizeStr(r1, r4)     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState$BackgroundHandler r1 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.this     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x00f5 }
                    java.util.Objects.requireNonNull(r1)     // Catch:{ all -> 0x00f5 }
                    long r4 = r6.externalCodeSize     // Catch:{ all -> 0x00f5 }
                    long r7 = r6.externalDataSize     // Catch:{ all -> 0x00f5 }
                    long r4 = r4 + r7
                    long r7 = r6.externalCacheSize     // Catch:{ all -> 0x00f5 }
                    long r4 = r4 + r7
                    long r7 = r6.externalMediaSize     // Catch:{ all -> 0x00f5 }
                    long r4 = r4 + r7
                    long r7 = r6.externalObbSize     // Catch:{ all -> 0x00f5 }
                    long r4 = r4 + r7
                    r3.externalSize = r4     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState$BackgroundHandler r1 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.this     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x00f5 }
                    com.android.settingslib.applications.ApplicationsState.m160$$Nest$mgetSizeStr(r1, r4)     // Catch:{ all -> 0x00f5 }
                    r4 = 1
                L_0x00db:
                    monitor-exit(r3)     // Catch:{ all -> 0x00f5 }
                    if (r4 == 0) goto L_0x00f9
                    com.android.settingslib.applications.ApplicationsState$BackgroundHandler r1 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.this     // Catch:{ all -> 0x011c }
                    com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x011c }
                    com.android.settingslib.applications.ApplicationsState$MainHandler r1 = r1.mMainHandler     // Catch:{ all -> 0x011c }
                    r3 = 4
                    java.lang.String r4 = r6.packageName     // Catch:{ all -> 0x011c }
                    android.os.Message r1 = r1.obtainMessage(r3, r4)     // Catch:{ all -> 0x011c }
                    com.android.settingslib.applications.ApplicationsState$BackgroundHandler r3 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.this     // Catch:{ all -> 0x011c }
                    com.android.settingslib.applications.ApplicationsState r3 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x011c }
                    com.android.settingslib.applications.ApplicationsState$MainHandler r3 = r3.mMainHandler     // Catch:{ all -> 0x011c }
                    r3.sendMessage(r1)     // Catch:{ all -> 0x011c }
                    goto L_0x00f9
                L_0x00f5:
                    r0 = move-exception
                    monitor-exit(r3)     // Catch:{ all -> 0x00f5 }
                    throw r0     // Catch:{ all -> 0x011c }
                L_0x00f8:
                    r6 = r1
                L_0x00f9:
                    com.android.settingslib.applications.ApplicationsState$BackgroundHandler r1 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.this     // Catch:{ all -> 0x011c }
                    com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x011c }
                    java.lang.String r1 = r1.mCurComputingSizePkg     // Catch:{ all -> 0x011c }
                    if (r1 == 0) goto L_0x011a
                    java.lang.String r3 = r6.packageName     // Catch:{ all -> 0x011c }
                    boolean r1 = r1.equals(r3)     // Catch:{ all -> 0x011c }
                    if (r1 == 0) goto L_0x011a
                    com.android.settingslib.applications.ApplicationsState$BackgroundHandler r0 = com.android.settingslib.applications.ApplicationsState.BackgroundHandler.this     // Catch:{ all -> 0x011c }
                    com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x011c }
                    int r3 = r1.mCurComputingSizeUserId     // Catch:{ all -> 0x011c }
                    int r4 = r6.userHandle     // Catch:{ all -> 0x011c }
                    if (r3 != r4) goto L_0x011a
                    r3 = 0
                    r1.mCurComputingSizePkg = r3     // Catch:{ all -> 0x011c }
                    r1 = 7
                    r0.sendEmptyMessage(r1)     // Catch:{ all -> 0x011c }
                L_0x011a:
                    monitor-exit(r2)     // Catch:{ all -> 0x011c }
                    return
                L_0x011c:
                    r0 = move-exception
                    monitor-exit(r2)     // Catch:{ all -> 0x011c }
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.applications.ApplicationsState.BackgroundHandler.C05891.onGetStatsCompleted(android.content.pm.PackageStats, boolean):void");
            }
        };

        public BackgroundHandler(Looper looper) {
            super(looper);
        }

        public final void getCombinedSessionFlags(ArrayList arrayList) {
            synchronized (ApplicationsState.this.mEntriesMap) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Objects.requireNonNull((Session) it.next());
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:224:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x00e5, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void handleMessage(android.os.Message r19) {
            /*
                r18 = this;
                r0 = r18
                r1 = r19
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                java.util.ArrayList<com.android.settingslib.applications.ApplicationsState$Session> r2 = r2.mRebuildingSessions
                monitor-enter(r2)
                com.android.settingslib.applications.ApplicationsState r3 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0378 }
                java.util.ArrayList<com.android.settingslib.applications.ApplicationsState$Session> r3 = r3.mRebuildingSessions     // Catch:{ all -> 0x0378 }
                int r3 = r3.size()     // Catch:{ all -> 0x0378 }
                r4 = 0
                if (r3 <= 0) goto L_0x0025
                java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ all -> 0x0378 }
                com.android.settingslib.applications.ApplicationsState r5 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0378 }
                java.util.ArrayList<com.android.settingslib.applications.ApplicationsState$Session> r5 = r5.mRebuildingSessions     // Catch:{ all -> 0x0378 }
                r3.<init>(r5)     // Catch:{ all -> 0x0378 }
                com.android.settingslib.applications.ApplicationsState r5 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0378 }
                java.util.ArrayList<com.android.settingslib.applications.ApplicationsState$Session> r5 = r5.mRebuildingSessions     // Catch:{ all -> 0x0378 }
                r5.clear()     // Catch:{ all -> 0x0378 }
                goto L_0x0026
            L_0x0025:
                r3 = r4
            L_0x0026:
                monitor-exit(r2)     // Catch:{ all -> 0x0378 }
                if (r3 == 0) goto L_0x003d
                java.util.Iterator r2 = r3.iterator()
            L_0x002d:
                boolean r3 = r2.hasNext()
                if (r3 == 0) goto L_0x003d
                java.lang.Object r3 = r2.next()
                com.android.settingslib.applications.ApplicationsState$Session r3 = (com.android.settingslib.applications.ApplicationsState.Session) r3
                java.util.Objects.requireNonNull(r3)
                goto L_0x002d
            L_0x003d:
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                java.util.ArrayList<com.android.settingslib.applications.ApplicationsState$Session> r2 = r2.mSessions
                r0.getCombinedSessionFlags(r2)
                int r2 = r1.what
                r3 = 8388608(0x800000, float:1.17549435E-38)
                r5 = 3
                r6 = 7
                r7 = 8
                r8 = 2
                r9 = 4
                r10 = 5
                r11 = 6
                r12 = 0
                r13 = 1
                switch(r2) {
                    case 2: goto L_0x02b3;
                    case 3: goto L_0x0258;
                    case 4: goto L_0x018f;
                    case 5: goto L_0x018f;
                    case 6: goto L_0x0115;
                    case 7: goto L_0x0057;
                    default: goto L_0x0055;
                }
            L_0x0055:
                goto L_0x0377
            L_0x0057:
                boolean r1 = com.android.settingslib.applications.ApplicationsState.hasFlag(r12, r9)
                if (r1 == 0) goto L_0x0377
                com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r1 = r1.mEntriesMap
                monitor-enter(r1)
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0112 }
                java.lang.String r2 = r2.mCurComputingSizePkg     // Catch:{ all -> 0x0112 }
                if (r2 == 0) goto L_0x006a
                monitor-exit(r1)     // Catch:{ all -> 0x0112 }
                return
            L_0x006a:
                long r4 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x0112 }
                r2 = r12
            L_0x006f:
                com.android.settingslib.applications.ApplicationsState r6 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0112 }
                java.util.ArrayList<com.android.settingslib.applications.ApplicationsState$AppEntry> r6 = r6.mAppEntries     // Catch:{ all -> 0x0112 }
                int r6 = r6.size()     // Catch:{ all -> 0x0112 }
                if (r2 >= r6) goto L_0x00e9
                com.android.settingslib.applications.ApplicationsState r6 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0112 }
                java.util.ArrayList<com.android.settingslib.applications.ApplicationsState$AppEntry> r6 = r6.mAppEntries     // Catch:{ all -> 0x0112 }
                java.lang.Object r6 = r6.get(r2)     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState$AppEntry r6 = (com.android.settingslib.applications.ApplicationsState.AppEntry) r6     // Catch:{ all -> 0x0112 }
                android.content.pm.ApplicationInfo r7 = r6.info     // Catch:{ all -> 0x0112 }
                int r7 = r7.flags     // Catch:{ all -> 0x0112 }
                boolean r7 = com.android.settingslib.applications.ApplicationsState.hasFlag(r7, r3)     // Catch:{ all -> 0x0112 }
                if (r7 == 0) goto L_0x00e6
                long r7 = r6.size     // Catch:{ all -> 0x0112 }
                r14 = -1
                int r7 = (r7 > r14 ? 1 : (r7 == r14 ? 0 : -1))
                if (r7 == 0) goto L_0x0099
                boolean r7 = r6.sizeStale     // Catch:{ all -> 0x0112 }
                if (r7 == 0) goto L_0x00e6
            L_0x0099:
                long r2 = r6.sizeLoadStart     // Catch:{ all -> 0x0112 }
                r7 = 0
                int r7 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
                if (r7 == 0) goto L_0x00a9
                r7 = 20000(0x4e20, double:9.8813E-320)
                long r7 = r4 - r7
                int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
                if (r2 >= 0) goto L_0x00e4
            L_0x00a9:
                boolean r2 = r0.mRunning     // Catch:{ all -> 0x0112 }
                if (r2 != 0) goto L_0x00c2
                r0.mRunning = r13     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState$MainHandler r2 = r2.mMainHandler     // Catch:{ all -> 0x0112 }
                java.lang.Integer r3 = java.lang.Integer.valueOf(r13)     // Catch:{ all -> 0x0112 }
                android.os.Message r2 = r2.obtainMessage(r11, r3)     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState r3 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState$MainHandler r3 = r3.mMainHandler     // Catch:{ all -> 0x0112 }
                r3.sendMessage(r2)     // Catch:{ all -> 0x0112 }
            L_0x00c2:
                r6.sizeLoadStart = r4     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0112 }
                android.content.pm.ApplicationInfo r3 = r6.info     // Catch:{ all -> 0x0112 }
                java.util.UUID r4 = r3.storageUuid     // Catch:{ all -> 0x0112 }
                r2.mCurComputingSizeUuid = r4     // Catch:{ all -> 0x0112 }
                java.lang.String r4 = r3.packageName     // Catch:{ all -> 0x0112 }
                r2.mCurComputingSizePkg = r4     // Catch:{ all -> 0x0112 }
                int r3 = r3.uid     // Catch:{ all -> 0x0112 }
                int r3 = android.os.UserHandle.getUserId(r3)     // Catch:{ all -> 0x0112 }
                r2.mCurComputingSizeUserId = r3     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState$BackgroundHandler r2 = r2.mBackgroundHandler     // Catch:{ all -> 0x0112 }
                com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda0 r3 = new com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda0     // Catch:{ all -> 0x0112 }
                r3.<init>(r0, r13)     // Catch:{ all -> 0x0112 }
                r2.post(r3)     // Catch:{ all -> 0x0112 }
            L_0x00e4:
                monitor-exit(r1)     // Catch:{ all -> 0x0112 }
                return
            L_0x00e6:
                int r2 = r2 + 1
                goto L_0x006f
            L_0x00e9:
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState$MainHandler r2 = r2.mMainHandler     // Catch:{ all -> 0x0112 }
                boolean r2 = r2.hasMessages(r10)     // Catch:{ all -> 0x0112 }
                if (r2 != 0) goto L_0x010f
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState$MainHandler r2 = r2.mMainHandler     // Catch:{ all -> 0x0112 }
                r2.sendEmptyMessage(r10)     // Catch:{ all -> 0x0112 }
                r0.mRunning = r12     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState$MainHandler r2 = r2.mMainHandler     // Catch:{ all -> 0x0112 }
                java.lang.Integer r3 = java.lang.Integer.valueOf(r12)     // Catch:{ all -> 0x0112 }
                android.os.Message r2 = r2.obtainMessage(r11, r3)     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState r0 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0112 }
                com.android.settingslib.applications.ApplicationsState$MainHandler r0 = r0.mMainHandler     // Catch:{ all -> 0x0112 }
                r0.sendMessage(r2)     // Catch:{ all -> 0x0112 }
            L_0x010f:
                monitor-exit(r1)     // Catch:{ all -> 0x0112 }
                goto L_0x0377
            L_0x0112:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0112 }
                throw r0
            L_0x0115:
                boolean r1 = com.android.settingslib.applications.ApplicationsState.hasFlag(r12, r8)
                if (r1 == 0) goto L_0x018a
                com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r1 = r1.mEntriesMap
                monitor-enter(r1)
                r2 = r12
            L_0x0121:
                com.android.settingslib.applications.ApplicationsState r3 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0187 }
                java.util.ArrayList<com.android.settingslib.applications.ApplicationsState$AppEntry> r3 = r3.mAppEntries     // Catch:{ all -> 0x0187 }
                int r3 = r3.size()     // Catch:{ all -> 0x0187 }
                if (r12 >= r3) goto L_0x016c
                if (r2 >= r8) goto L_0x016c
                com.android.settingslib.applications.ApplicationsState r3 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0187 }
                java.util.ArrayList<com.android.settingslib.applications.ApplicationsState$AppEntry> r3 = r3.mAppEntries     // Catch:{ all -> 0x0187 }
                java.lang.Object r3 = r3.get(r12)     // Catch:{ all -> 0x0187 }
                com.android.settingslib.applications.ApplicationsState$AppEntry r3 = (com.android.settingslib.applications.ApplicationsState.AppEntry) r3     // Catch:{ all -> 0x0187 }
                android.graphics.drawable.Drawable r4 = r3.icon     // Catch:{ all -> 0x0187 }
                if (r4 == 0) goto L_0x013f
                boolean r4 = r3.mounted     // Catch:{ all -> 0x0187 }
                if (r4 != 0) goto L_0x0166
            L_0x013f:
                monitor-enter(r3)     // Catch:{ all -> 0x0187 }
                com.android.settingslib.applications.ApplicationsState r4 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0169 }
                android.app.Application r4 = r4.mContext     // Catch:{ all -> 0x0169 }
                boolean r4 = r3.ensureIconLocked(r4)     // Catch:{ all -> 0x0169 }
                if (r4 == 0) goto L_0x0165
                boolean r4 = r0.mRunning     // Catch:{ all -> 0x0169 }
                if (r4 != 0) goto L_0x0163
                r0.mRunning = r13     // Catch:{ all -> 0x0169 }
                com.android.settingslib.applications.ApplicationsState r4 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0169 }
                com.android.settingslib.applications.ApplicationsState$MainHandler r4 = r4.mMainHandler     // Catch:{ all -> 0x0169 }
                java.lang.Integer r7 = java.lang.Integer.valueOf(r13)     // Catch:{ all -> 0x0169 }
                android.os.Message r4 = r4.obtainMessage(r11, r7)     // Catch:{ all -> 0x0169 }
                com.android.settingslib.applications.ApplicationsState r7 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0169 }
                com.android.settingslib.applications.ApplicationsState$MainHandler r7 = r7.mMainHandler     // Catch:{ all -> 0x0169 }
                r7.sendMessage(r4)     // Catch:{ all -> 0x0169 }
            L_0x0163:
                int r2 = r2 + 1
            L_0x0165:
                monitor-exit(r3)     // Catch:{ all -> 0x0169 }
            L_0x0166:
                int r12 = r12 + 1
                goto L_0x0121
            L_0x0169:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0169 }
                throw r0     // Catch:{ all -> 0x0187 }
            L_0x016c:
                monitor-exit(r1)     // Catch:{ all -> 0x0187 }
                if (r2 <= 0) goto L_0x0180
                com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this
                com.android.settingslib.applications.ApplicationsState$MainHandler r1 = r1.mMainHandler
                boolean r1 = r1.hasMessages(r5)
                if (r1 != 0) goto L_0x0180
                com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this
                com.android.settingslib.applications.ApplicationsState$MainHandler r1 = r1.mMainHandler
                r1.sendEmptyMessage(r5)
            L_0x0180:
                if (r2 < r8) goto L_0x018a
                r0.sendEmptyMessage(r11)
                goto L_0x0377
            L_0x0187:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0187 }
                throw r0
            L_0x018a:
                r0.sendEmptyMessage(r6)
                goto L_0x0377
            L_0x018f:
                if (r2 != r9) goto L_0x0197
                boolean r2 = com.android.settingslib.applications.ApplicationsState.hasFlag(r12, r7)
                if (r2 != 0) goto L_0x01a3
            L_0x0197:
                int r2 = r1.what
                if (r2 != r10) goto L_0x0248
                r2 = 16
                boolean r2 = com.android.settingslib.applications.ApplicationsState.hasFlag(r12, r2)
                if (r2 == 0) goto L_0x0248
            L_0x01a3:
                android.content.Intent r2 = new android.content.Intent
                java.lang.String r3 = "android.intent.action.MAIN"
                r2.<init>(r3, r4)
                int r3 = r1.what
                if (r3 != r9) goto L_0x01b1
                java.lang.String r3 = "android.intent.category.LAUNCHER"
                goto L_0x01b3
            L_0x01b1:
                java.lang.String r3 = "android.intent.category.LEANBACK_LAUNCHER"
            L_0x01b3:
                r2.addCategory(r3)
                r3 = r12
            L_0x01b7:
                com.android.settingslib.applications.ApplicationsState r4 = com.android.settingslib.applications.ApplicationsState.this
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r4 = r4.mEntriesMap
                int r4 = r4.size()
                if (r3 >= r4) goto L_0x0237
                com.android.settingslib.applications.ApplicationsState r4 = com.android.settingslib.applications.ApplicationsState.this
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r4 = r4.mEntriesMap
                int r4 = r4.keyAt(r3)
                com.android.settingslib.applications.ApplicationsState r5 = com.android.settingslib.applications.ApplicationsState.this
                android.content.pm.PackageManager r5 = r5.mPm
                r7 = 786944(0xc0200, float:1.102743E-39)
                java.util.List r5 = r5.queryIntentActivitiesAsUser(r2, r7, r4)
                com.android.settingslib.applications.ApplicationsState r7 = com.android.settingslib.applications.ApplicationsState.this
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r7 = r7.mEntriesMap
                monitor-enter(r7)
                com.android.settingslib.applications.ApplicationsState r8 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0234 }
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r8 = r8.mEntriesMap     // Catch:{ all -> 0x0234 }
                java.lang.Object r8 = r8.valueAt(r3)     // Catch:{ all -> 0x0234 }
                java.util.HashMap r8 = (java.util.HashMap) r8     // Catch:{ all -> 0x0234 }
                int r14 = r5.size()     // Catch:{ all -> 0x0234 }
                r15 = r12
            L_0x01e8:
                if (r15 >= r14) goto L_0x022c
                java.lang.Object r16 = r5.get(r15)     // Catch:{ all -> 0x0234 }
                r12 = r16
                android.content.pm.ResolveInfo r12 = (android.content.pm.ResolveInfo) r12     // Catch:{ all -> 0x0234 }
                android.content.pm.ActivityInfo r11 = r12.activityInfo     // Catch:{ all -> 0x0234 }
                java.lang.String r11 = r11.packageName     // Catch:{ all -> 0x0234 }
                java.lang.Object r17 = r8.get(r11)     // Catch:{ all -> 0x0234 }
                r10 = r17
                com.android.settingslib.applications.ApplicationsState$AppEntry r10 = (com.android.settingslib.applications.ApplicationsState.AppEntry) r10     // Catch:{ all -> 0x0234 }
                if (r10 == 0) goto L_0x0207
                r10.hasLauncherEntry = r13     // Catch:{ all -> 0x0234 }
                android.content.pm.ActivityInfo r10 = r12.activityInfo     // Catch:{ all -> 0x0234 }
                boolean r10 = r10.enabled     // Catch:{ all -> 0x0234 }
                goto L_0x0225
            L_0x0207:
                java.lang.String r10 = "ApplicationsState"
                java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0234 }
                r12.<init>()     // Catch:{ all -> 0x0234 }
                java.lang.String r13 = "Cannot find pkg: "
                r12.append(r13)     // Catch:{ all -> 0x0234 }
                r12.append(r11)     // Catch:{ all -> 0x0234 }
                java.lang.String r11 = " on user "
                r12.append(r11)     // Catch:{ all -> 0x0234 }
                r12.append(r4)     // Catch:{ all -> 0x0234 }
                java.lang.String r11 = r12.toString()     // Catch:{ all -> 0x0234 }
                android.util.Log.w(r10, r11)     // Catch:{ all -> 0x0234 }
            L_0x0225:
                int r15 = r15 + 1
                r10 = 5
                r11 = 6
                r12 = 0
                r13 = 1
                goto L_0x01e8
            L_0x022c:
                monitor-exit(r7)     // Catch:{ all -> 0x0234 }
                int r3 = r3 + 1
                r10 = 5
                r11 = 6
                r12 = 0
                r13 = 1
                goto L_0x01b7
            L_0x0234:
                r0 = move-exception
                monitor-exit(r7)     // Catch:{ all -> 0x0234 }
                throw r0
            L_0x0237:
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                com.android.settingslib.applications.ApplicationsState$MainHandler r2 = r2.mMainHandler
                boolean r2 = r2.hasMessages(r6)
                if (r2 != 0) goto L_0x0248
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                com.android.settingslib.applications.ApplicationsState$MainHandler r2 = r2.mMainHandler
                r2.sendEmptyMessage(r6)
            L_0x0248:
                int r1 = r1.what
                if (r1 != r9) goto L_0x0252
                r1 = 5
                r0.sendEmptyMessage(r1)
                goto L_0x0377
            L_0x0252:
                r1 = 6
                r0.sendEmptyMessage(r1)
                goto L_0x0377
            L_0x0258:
                r1 = r12
                r2 = r13
                boolean r3 = com.android.settingslib.applications.ApplicationsState.hasFlag(r1, r2)
                if (r3 == 0) goto L_0x02ae
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>()
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                android.content.pm.PackageManager r2 = r2.mPm
                r2.getHomeActivities(r1)
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r2 = r2.mEntriesMap
                monitor-enter(r2)
                com.android.settingslib.applications.ApplicationsState r3 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x02ab }
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r3 = r3.mEntriesMap     // Catch:{ all -> 0x02ab }
                int r3 = r3.size()     // Catch:{ all -> 0x02ab }
                r12 = 0
            L_0x027a:
                if (r12 >= r3) goto L_0x02a9
                com.android.settingslib.applications.ApplicationsState r4 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x02ab }
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r4 = r4.mEntriesMap     // Catch:{ all -> 0x02ab }
                java.lang.Object r4 = r4.valueAt(r12)     // Catch:{ all -> 0x02ab }
                java.util.HashMap r4 = (java.util.HashMap) r4     // Catch:{ all -> 0x02ab }
                java.util.Iterator r5 = r1.iterator()     // Catch:{ all -> 0x02ab }
            L_0x028a:
                boolean r6 = r5.hasNext()     // Catch:{ all -> 0x02ab }
                if (r6 == 0) goto L_0x02a6
                java.lang.Object r6 = r5.next()     // Catch:{ all -> 0x02ab }
                android.content.pm.ResolveInfo r6 = (android.content.pm.ResolveInfo) r6     // Catch:{ all -> 0x02ab }
                android.content.pm.ActivityInfo r6 = r6.activityInfo     // Catch:{ all -> 0x02ab }
                java.lang.String r6 = r6.packageName     // Catch:{ all -> 0x02ab }
                java.lang.Object r6 = r4.get(r6)     // Catch:{ all -> 0x02ab }
                com.android.settingslib.applications.ApplicationsState$AppEntry r6 = (com.android.settingslib.applications.ApplicationsState.AppEntry) r6     // Catch:{ all -> 0x02ab }
                if (r6 == 0) goto L_0x028a
                r7 = 1
                r6.isHomeApp = r7     // Catch:{ all -> 0x02ab }
                goto L_0x028a
            L_0x02a6:
                int r12 = r12 + 1
                goto L_0x027a
            L_0x02a9:
                monitor-exit(r2)     // Catch:{ all -> 0x02ab }
                goto L_0x02ae
            L_0x02ab:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x02ab }
                throw r0
            L_0x02ae:
                r0.sendEmptyMessage(r9)
                goto L_0x0377
            L_0x02b3:
                com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r1 = r1.mEntriesMap
                monitor-enter(r1)
                r2 = 0
                r4 = 0
            L_0x02ba:
                com.android.settingslib.applications.ApplicationsState r6 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0374 }
                java.util.ArrayList r6 = r6.mApplications     // Catch:{ all -> 0x0374 }
                int r6 = r6.size()     // Catch:{ all -> 0x0374 }
                if (r2 >= r6) goto L_0x0357
                r6 = 6
                if (r4 >= r6) goto L_0x0357
                boolean r6 = r0.mRunning     // Catch:{ all -> 0x0374 }
                if (r6 != 0) goto L_0x02e3
                r6 = 1
                r0.mRunning = r6     // Catch:{ all -> 0x0374 }
                com.android.settingslib.applications.ApplicationsState r9 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0374 }
                com.android.settingslib.applications.ApplicationsState$MainHandler r9 = r9.mMainHandler     // Catch:{ all -> 0x0374 }
                java.lang.Integer r10 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x0374 }
                r11 = 6
                android.os.Message r9 = r9.obtainMessage(r11, r10)     // Catch:{ all -> 0x0374 }
                com.android.settingslib.applications.ApplicationsState r10 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0374 }
                com.android.settingslib.applications.ApplicationsState$MainHandler r10 = r10.mMainHandler     // Catch:{ all -> 0x0374 }
                r10.sendMessage(r9)     // Catch:{ all -> 0x0374 }
                goto L_0x02e4
            L_0x02e3:
                r6 = 1
            L_0x02e4:
                com.android.settingslib.applications.ApplicationsState r9 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0374 }
                java.util.ArrayList r9 = r9.mApplications     // Catch:{ all -> 0x0374 }
                java.lang.Object r9 = r9.get(r2)     // Catch:{ all -> 0x0374 }
                android.content.pm.ApplicationInfo r9 = (android.content.pm.ApplicationInfo) r9     // Catch:{ all -> 0x0374 }
                int r10 = r9.uid     // Catch:{ all -> 0x0374 }
                int r10 = android.os.UserHandle.getUserId(r10)     // Catch:{ all -> 0x0374 }
                com.android.settingslib.applications.ApplicationsState r11 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0374 }
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r11 = r11.mEntriesMap     // Catch:{ all -> 0x0374 }
                java.lang.Object r11 = r11.get(r10)     // Catch:{ all -> 0x0374 }
                java.util.HashMap r11 = (java.util.HashMap) r11     // Catch:{ all -> 0x0374 }
                java.lang.String r12 = r9.packageName     // Catch:{ all -> 0x0374 }
                java.lang.Object r11 = r11.get(r12)     // Catch:{ all -> 0x0374 }
                if (r11 != 0) goto L_0x030d
                int r4 = r4 + 1
                com.android.settingslib.applications.ApplicationsState r11 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0374 }
                com.android.settingslib.applications.ApplicationsState.m159$$Nest$mgetEntryLocked(r11, r9)     // Catch:{ all -> 0x0374 }
            L_0x030d:
                if (r10 == 0) goto L_0x0352
                com.android.settingslib.applications.ApplicationsState r10 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0374 }
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r10 = r10.mEntriesMap     // Catch:{ all -> 0x0374 }
                r11 = 0
                int r10 = r10.indexOfKey(r11)     // Catch:{ all -> 0x0374 }
                if (r10 < 0) goto L_0x0350
                com.android.settingslib.applications.ApplicationsState r10 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0374 }
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r10 = r10.mEntriesMap     // Catch:{ all -> 0x0374 }
                java.lang.Object r10 = r10.get(r11)     // Catch:{ all -> 0x0374 }
                java.util.HashMap r10 = (java.util.HashMap) r10     // Catch:{ all -> 0x0374 }
                java.lang.String r11 = r9.packageName     // Catch:{ all -> 0x0374 }
                java.lang.Object r10 = r10.get(r11)     // Catch:{ all -> 0x0374 }
                com.android.settingslib.applications.ApplicationsState$AppEntry r10 = (com.android.settingslib.applications.ApplicationsState.AppEntry) r10     // Catch:{ all -> 0x0374 }
                if (r10 == 0) goto L_0x0352
                android.content.pm.ApplicationInfo r11 = r10.info     // Catch:{ all -> 0x0374 }
                int r11 = r11.flags     // Catch:{ all -> 0x0374 }
                boolean r11 = com.android.settingslib.applications.ApplicationsState.hasFlag(r11, r3)     // Catch:{ all -> 0x0374 }
                if (r11 != 0) goto L_0x0352
                com.android.settingslib.applications.ApplicationsState r11 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0374 }
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r11 = r11.mEntriesMap     // Catch:{ all -> 0x0374 }
                r12 = 0
                java.lang.Object r11 = r11.get(r12)     // Catch:{ all -> 0x0374 }
                java.util.HashMap r11 = (java.util.HashMap) r11     // Catch:{ all -> 0x0374 }
                java.lang.String r9 = r9.packageName     // Catch:{ all -> 0x0374 }
                r11.remove(r9)     // Catch:{ all -> 0x0374 }
                com.android.settingslib.applications.ApplicationsState r9 = com.android.settingslib.applications.ApplicationsState.this     // Catch:{ all -> 0x0374 }
                java.util.ArrayList<com.android.settingslib.applications.ApplicationsState$AppEntry> r9 = r9.mAppEntries     // Catch:{ all -> 0x0374 }
                r9.remove(r10)     // Catch:{ all -> 0x0374 }
                goto L_0x0353
            L_0x0350:
                r12 = r11
                goto L_0x0353
            L_0x0352:
                r12 = 0
            L_0x0353:
                int r2 = r2 + 1
                goto L_0x02ba
            L_0x0357:
                monitor-exit(r1)     // Catch:{ all -> 0x0374 }
                r1 = 6
                if (r4 < r1) goto L_0x035f
                r0.sendEmptyMessage(r8)
                goto L_0x0377
            L_0x035f:
                com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this
                com.android.settingslib.applications.ApplicationsState$MainHandler r1 = r1.mMainHandler
                boolean r1 = r1.hasMessages(r7)
                if (r1 != 0) goto L_0x0370
                com.android.settingslib.applications.ApplicationsState r1 = com.android.settingslib.applications.ApplicationsState.this
                com.android.settingslib.applications.ApplicationsState$MainHandler r1 = r1.mMainHandler
                r1.sendEmptyMessage(r7)
            L_0x0370:
                r0.sendEmptyMessage(r5)
                goto L_0x0377
            L_0x0374:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0374 }
                throw r0
            L_0x0377:
                return
            L_0x0378:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0378 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.applications.ApplicationsState.BackgroundHandler.handleMessage(android.os.Message):void");
        }
    }

    public class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARNING: CFG modification limit reached, blocks count: 173 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void handleMessage(android.os.Message r3) {
            /*
                r2 = this;
                com.android.settingslib.applications.ApplicationsState r0 = com.android.settingslib.applications.ApplicationsState.this
                java.util.Objects.requireNonNull(r0)
                android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r0 = r0.mEntriesMap
                monitor-enter(r0)
                monitor-exit(r0)     // Catch:{ all -> 0x0111 }
                int r0 = r3.what
                r1 = 0
                switch(r0) {
                    case 1: goto L_0x00e7;
                    case 2: goto L_0x00c9;
                    case 3: goto L_0x00ab;
                    case 4: goto L_0x0089;
                    case 5: goto L_0x006b;
                    case 6: goto L_0x004d;
                    case 7: goto L_0x002f;
                    case 8: goto L_0x0011;
                    default: goto L_0x000f;
                }
            L_0x000f:
                goto L_0x010e
            L_0x0011:
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                java.util.ArrayList<java.lang.ref.WeakReference<com.android.settingslib.applications.ApplicationsState$Session>> r2 = r2.mActiveSessions
                java.util.Iterator r2 = r2.iterator()
            L_0x0019:
                boolean r3 = r2.hasNext()
                if (r3 == 0) goto L_0x010e
                java.lang.Object r3 = r2.next()
                java.lang.ref.WeakReference r3 = (java.lang.ref.WeakReference) r3
                java.lang.Object r3 = r3.get()
                com.android.settingslib.applications.ApplicationsState$Session r3 = (com.android.settingslib.applications.ApplicationsState.Session) r3
                if (r3 != 0) goto L_0x002e
                goto L_0x0019
            L_0x002e:
                throw r1
            L_0x002f:
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                java.util.ArrayList<java.lang.ref.WeakReference<com.android.settingslib.applications.ApplicationsState$Session>> r2 = r2.mActiveSessions
                java.util.Iterator r2 = r2.iterator()
            L_0x0037:
                boolean r3 = r2.hasNext()
                if (r3 == 0) goto L_0x010e
                java.lang.Object r3 = r2.next()
                java.lang.ref.WeakReference r3 = (java.lang.ref.WeakReference) r3
                java.lang.Object r3 = r3.get()
                com.android.settingslib.applications.ApplicationsState$Session r3 = (com.android.settingslib.applications.ApplicationsState.Session) r3
                if (r3 != 0) goto L_0x004c
                goto L_0x0037
            L_0x004c:
                throw r1
            L_0x004d:
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                java.util.ArrayList<java.lang.ref.WeakReference<com.android.settingslib.applications.ApplicationsState$Session>> r2 = r2.mActiveSessions
                java.util.Iterator r2 = r2.iterator()
            L_0x0055:
                boolean r3 = r2.hasNext()
                if (r3 == 0) goto L_0x010e
                java.lang.Object r3 = r2.next()
                java.lang.ref.WeakReference r3 = (java.lang.ref.WeakReference) r3
                java.lang.Object r3 = r3.get()
                com.android.settingslib.applications.ApplicationsState$Session r3 = (com.android.settingslib.applications.ApplicationsState.Session) r3
                if (r3 != 0) goto L_0x006a
                goto L_0x0055
            L_0x006a:
                throw r1
            L_0x006b:
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                java.util.ArrayList<java.lang.ref.WeakReference<com.android.settingslib.applications.ApplicationsState$Session>> r2 = r2.mActiveSessions
                java.util.Iterator r2 = r2.iterator()
            L_0x0073:
                boolean r3 = r2.hasNext()
                if (r3 == 0) goto L_0x010e
                java.lang.Object r3 = r2.next()
                java.lang.ref.WeakReference r3 = (java.lang.ref.WeakReference) r3
                java.lang.Object r3 = r3.get()
                com.android.settingslib.applications.ApplicationsState$Session r3 = (com.android.settingslib.applications.ApplicationsState.Session) r3
                if (r3 != 0) goto L_0x0088
                goto L_0x0073
            L_0x0088:
                throw r1
            L_0x0089:
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                java.util.ArrayList<java.lang.ref.WeakReference<com.android.settingslib.applications.ApplicationsState$Session>> r2 = r2.mActiveSessions
                java.util.Iterator r2 = r2.iterator()
            L_0x0091:
                boolean r0 = r2.hasNext()
                if (r0 == 0) goto L_0x010e
                java.lang.Object r0 = r2.next()
                java.lang.ref.WeakReference r0 = (java.lang.ref.WeakReference) r0
                java.lang.Object r0 = r0.get()
                com.android.settingslib.applications.ApplicationsState$Session r0 = (com.android.settingslib.applications.ApplicationsState.Session) r0
                if (r0 != 0) goto L_0x00a6
                goto L_0x0091
            L_0x00a6:
                java.lang.Object r2 = r3.obj
                java.lang.String r2 = (java.lang.String) r2
                throw r1
            L_0x00ab:
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                java.util.ArrayList<java.lang.ref.WeakReference<com.android.settingslib.applications.ApplicationsState$Session>> r2 = r2.mActiveSessions
                java.util.Iterator r2 = r2.iterator()
            L_0x00b3:
                boolean r3 = r2.hasNext()
                if (r3 == 0) goto L_0x010e
                java.lang.Object r3 = r2.next()
                java.lang.ref.WeakReference r3 = (java.lang.ref.WeakReference) r3
                java.lang.Object r3 = r3.get()
                com.android.settingslib.applications.ApplicationsState$Session r3 = (com.android.settingslib.applications.ApplicationsState.Session) r3
                if (r3 != 0) goto L_0x00c8
                goto L_0x00b3
            L_0x00c8:
                throw r1
            L_0x00c9:
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                java.util.ArrayList<java.lang.ref.WeakReference<com.android.settingslib.applications.ApplicationsState$Session>> r2 = r2.mActiveSessions
                java.util.Iterator r2 = r2.iterator()
            L_0x00d1:
                boolean r3 = r2.hasNext()
                if (r3 == 0) goto L_0x010e
                java.lang.Object r3 = r2.next()
                java.lang.ref.WeakReference r3 = (java.lang.ref.WeakReference) r3
                java.lang.Object r3 = r3.get()
                com.android.settingslib.applications.ApplicationsState$Session r3 = (com.android.settingslib.applications.ApplicationsState.Session) r3
                if (r3 != 0) goto L_0x00e6
                goto L_0x00d1
            L_0x00e6:
                throw r1
            L_0x00e7:
                java.lang.Object r3 = r3.obj
                com.android.settingslib.applications.ApplicationsState$Session r3 = (com.android.settingslib.applications.ApplicationsState.Session) r3
                com.android.settingslib.applications.ApplicationsState r2 = com.android.settingslib.applications.ApplicationsState.this
                java.util.ArrayList<java.lang.ref.WeakReference<com.android.settingslib.applications.ApplicationsState$Session>> r2 = r2.mActiveSessions
                java.util.Iterator r2 = r2.iterator()
            L_0x00f3:
                boolean r0 = r2.hasNext()
                if (r0 == 0) goto L_0x010e
                java.lang.Object r0 = r2.next()
                java.lang.ref.WeakReference r0 = (java.lang.ref.WeakReference) r0
                java.lang.Object r0 = r0.get()
                com.android.settingslib.applications.ApplicationsState$Session r0 = (com.android.settingslib.applications.ApplicationsState.Session) r0
                if (r0 == 0) goto L_0x00f3
                if (r0 == r3) goto L_0x010a
                goto L_0x00f3
            L_0x010a:
                java.util.Objects.requireNonNull(r3)
                throw r1
            L_0x010e:
                return
            L_0x010f:
                monitor-exit(r0)     // Catch:{ all -> 0x0111 }
                throw r2
            L_0x0111:
                r2 = move-exception
                goto L_0x010f
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.applications.ApplicationsState.MainHandler.handleMessage(android.os.Message):void");
        }
    }

    public static class SizeInfo {
        public long cacheSize;
        public long codeSize;
        public long dataSize;
        public long externalCacheSize;
        public long externalCodeSize;
        public long externalDataSize;
    }

    public static boolean hasFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public void clearEntries() {
        for (int i = 0; i < this.mEntriesMap.size(); i++) {
            this.mEntriesMap.valueAt(i).clear();
        }
        this.mAppEntries.clear();
    }

    public class Session implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {
            onPause();
            throw null;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {
            throw null;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
            throw null;
        }
    }

    /* renamed from: -$$Nest$mgetSizeStr  reason: not valid java name */
    public static String m160$$Nest$mgetSizeStr(ApplicationsState applicationsState, long j) {
        if (j >= 0) {
            return Formatter.formatFileSize(applicationsState.mContext, j);
        }
        Objects.requireNonNull(applicationsState);
        return null;
    }

    static {
        Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    }

    public static ApplicationsState getInstance(Application application, IPackageManager iPackageManager) {
        ApplicationsState applicationsState;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new ApplicationsState(application, iPackageManager);
            }
            applicationsState = sInstance;
        }
        return applicationsState;
    }

    /* renamed from: -$$Nest$mgetEntryLocked  reason: not valid java name */
    public static AppEntry m159$$Nest$mgetEntryLocked(ApplicationsState applicationsState, ApplicationInfo applicationInfo) {
        boolean z;
        Objects.requireNonNull(applicationsState);
        int userId = UserHandle.getUserId(applicationInfo.uid);
        AppEntry appEntry = (AppEntry) applicationsState.mEntriesMap.get(userId).get(applicationInfo.packageName);
        if (appEntry == null) {
            Boolean bool = applicationsState.mSystemModules.get(applicationInfo.packageName);
            if (bool == null) {
                z = false;
            } else {
                z = bool.booleanValue();
            }
            if (z) {
                return null;
            }
            Application application = applicationsState.mContext;
            long j = applicationsState.mCurId;
            applicationsState.mCurId = 1 + j;
            appEntry = new AppEntry(application, applicationInfo, j);
            applicationsState.mEntriesMap.get(userId).put(applicationInfo.packageName, appEntry);
            applicationsState.mAppEntries.add(appEntry);
        } else if (appEntry.info != applicationInfo) {
            appEntry.info = applicationInfo;
        }
        return appEntry;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x00dc */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ApplicationsState(android.app.Application r8, android.content.pm.IPackageManager r9) {
        /*
            r7 = this;
            r7.<init>()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r7.mSessions = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r7.mRebuildingSessions = r0
            com.android.settingslib.applications.InterestingConfigChanges r0 = new com.android.settingslib.applications.InterestingConfigChanges
            r1 = -2147474940(0xffffffff80002204, float:-1.2203E-41)
            r0.<init>(r1)
            r7.mInterestingConfigChanges = r0
            android.util.SparseArray r0 = new android.util.SparseArray
            r0.<init>()
            r7.mEntriesMap = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r7.mAppEntries = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r7.mApplications = r0
            r0 = 1
            r7.mCurId = r0
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            r7.mSystemModules = r2
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r7.mActiveSessions = r2
            com.android.settingslib.applications.ApplicationsState$MainHandler r2 = new com.android.settingslib.applications.ApplicationsState$MainHandler
            android.os.Looper r3 = android.os.Looper.getMainLooper()
            r2.<init>(r3)
            r7.mMainHandler = r2
            r7.mContext = r8
            android.content.pm.PackageManager r2 = r8.getPackageManager()
            r7.mPm = r2
            android.util.IconDrawableFactory.newInstance(r8)
            r7.mIpm = r9
            java.lang.Class<android.os.UserManager> r9 = android.os.UserManager.class
            java.lang.Object r9 = r8.getSystemService(r9)
            android.os.UserManager r9 = (android.os.UserManager) r9
            r7.mUm = r9
            java.lang.Class<android.app.usage.StorageStatsManager> r2 = android.app.usage.StorageStatsManager.class
            java.lang.Object r8 = r8.getSystemService(r2)
            android.app.usage.StorageStatsManager r8 = (android.app.usage.StorageStatsManager) r8
            r7.mStats = r8
            int r8 = android.os.UserHandle.myUserId()
            int[] r8 = r9.getProfileIdsWithDisabled(r8)
            int r9 = r8.length
            r2 = 0
            r3 = r2
        L_0x0079:
            if (r3 >= r9) goto L_0x008a
            r4 = r8[r3]
            android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r5 = r7.mEntriesMap
            java.util.HashMap r6 = new java.util.HashMap
            r6.<init>()
            r5.put(r4, r6)
            int r3 = r3 + 1
            goto L_0x0079
        L_0x008a:
            android.os.HandlerThread r8 = new android.os.HandlerThread
            java.lang.String r9 = "ApplicationsState.Loader"
            r8.<init>(r9)
            r8.start()
            com.android.settingslib.applications.ApplicationsState$BackgroundHandler r9 = new com.android.settingslib.applications.ApplicationsState$BackgroundHandler
            android.os.Looper r8 = r8.getLooper()
            r9.<init>(r8)
            r7.mBackgroundHandler = r9
            r8 = 4227584(0x408200, float:5.924107E-39)
            r7.mAdminRetrieveFlags = r8
            r8 = 33280(0x8200, float:4.6635E-41)
            r7.mRetrieveFlags = r8
            android.content.pm.PackageManager r8 = r7.mPm
            java.util.List r8 = r8.getInstalledModules(r2)
            java.util.Iterator r8 = r8.iterator()
        L_0x00b3:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x00d1
            java.lang.Object r9 = r8.next()
            android.content.pm.ModuleInfo r9 = (android.content.pm.ModuleInfo) r9
            java.util.HashMap<java.lang.String, java.lang.Boolean> r2 = r7.mSystemModules
            java.lang.String r3 = r9.getPackageName()
            boolean r9 = r9.isHidden()
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)
            r2.put(r3, r9)
            goto L_0x00b3
        L_0x00d1:
            android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r8 = r7.mEntriesMap
            monitor-enter(r8)
            android.util.SparseArray<java.util.HashMap<java.lang.String, com.android.settingslib.applications.ApplicationsState$AppEntry>> r7 = r7.mEntriesMap     // Catch:{ InterruptedException -> 0x00dc }
            r7.wait(r0)     // Catch:{ InterruptedException -> 0x00dc }
            goto L_0x00dc
        L_0x00da:
            r7 = move-exception
            goto L_0x00de
        L_0x00dc:
            monitor-exit(r8)     // Catch:{ all -> 0x00da }
            return
        L_0x00de:
            monitor-exit(r8)     // Catch:{ all -> 0x00da }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.applications.ApplicationsState.<init>(android.app.Application, android.content.pm.IPackageManager):void");
    }

    public void setInterestingConfigChanges(InterestingConfigChanges interestingConfigChanges) {
        this.mInterestingConfigChanges = interestingConfigChanges;
    }
}
