package com.android.systemui.util.leak;

import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.View;
import androidx.core.content.FileProvider;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda20;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSIconViewImpl;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.MessageRouter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public final class GarbageMonitor implements Dumpable {
    public static final boolean DEBUG = Log.isLoggable("GarbageMonitor", 3);
    public static final boolean ENABLE_AM_HEAP_LIMIT;
    public static final boolean HEAP_TRACKING_ENABLED;
    public static final boolean LEAK_REPORTING_ENABLED;
    public final Context mContext;
    public final LongSparseArray<ProcessMemInfo> mData = new LongSparseArray<>();
    public final DelayableExecutor mDelayableExecutor;
    public final DumpTruck mDumpTruck;
    public long mHeapLimit;
    public final LeakReporter mLeakReporter;
    public final MessageRouter mMessageRouter;
    public final ArrayList<Long> mPids = new ArrayList<>();
    public MemoryTile mQSTile;
    public final TrackedGarbage mTrackedGarbage;

    public static class MemoryGraphIcon extends QSTile.Icon {
        public long limit;
        public long rss;

        public final Drawable getDrawable(Context context) {
            MemoryIconDrawable memoryIconDrawable = new MemoryIconDrawable(context);
            long j = this.rss;
            if (j != memoryIconDrawable.rss) {
                memoryIconDrawable.rss = j;
                memoryIconDrawable.invalidateSelf();
            }
            long j2 = this.limit;
            if (j2 != memoryIconDrawable.limit) {
                memoryIconDrawable.limit = j2;
                memoryIconDrawable.invalidateSelf();
            }
            return memoryIconDrawable;
        }

        public MemoryGraphIcon(int i) {
        }
    }

    public static class MemoryIconDrawable extends Drawable {
        public final Drawable baseIcon;

        /* renamed from: dp */
        public final float f86dp;
        public long limit;
        public final Paint paint;
        public long rss;

        public final int getOpacity() {
            return -3;
        }

        public final void draw(Canvas canvas) {
            this.baseIcon.draw(canvas);
            long j = this.limit;
            if (j > 0) {
                long j2 = this.rss;
                if (j2 > 0) {
                    float min = Math.min(1.0f, ((float) j2) / ((float) j));
                    Rect bounds = getBounds();
                    float f = this.f86dp;
                    canvas.translate((f * 8.0f) + ((float) bounds.left), (f * 5.0f) + ((float) bounds.top));
                    float f2 = this.f86dp;
                    Canvas canvas2 = canvas;
                    canvas2.drawRect(0.0f, (1.0f - min) * f2 * 14.0f, (8.0f * f2) + 1.0f, (f2 * 14.0f) + 1.0f, this.paint);
                }
            }
        }

        public final int getIntrinsicHeight() {
            return this.baseIcon.getIntrinsicHeight();
        }

        public final int getIntrinsicWidth() {
            return this.baseIcon.getIntrinsicWidth();
        }

        public final void setAlpha(int i) {
            this.baseIcon.setAlpha(i);
        }

        public final void setColorFilter(ColorFilter colorFilter) {
            this.baseIcon.setColorFilter(colorFilter);
            this.paint.setColorFilter(colorFilter);
        }

        public MemoryIconDrawable(Context context) {
            Paint paint2 = new Paint();
            this.paint = paint2;
            this.baseIcon = context.getDrawable(C1777R.C1778drawable.ic_memory).mutate();
            this.f86dp = context.getResources().getDisplayMetrics().density;
            paint2.setColor(QSIconViewImpl.getIconColorForState(context, 2));
        }

        public final void setBounds(int i, int i2, int i3, int i4) {
            super.setBounds(i, i2, i3, i4);
            this.baseIcon.setBounds(i, i2, i3, i4);
        }

        public final void setTint(int i) {
            super.setTint(i);
            this.baseIcon.setTint(i);
        }

        public final void setTintList(ColorStateList colorStateList) {
            super.setTintList(colorStateList);
            this.baseIcon.setTintList(colorStateList);
        }

        public final void setTintMode(PorterDuff.Mode mode) {
            super.setTintMode(mode);
            this.baseIcon.setTintMode(mode);
        }
    }

    public static class MemoryTile extends QSTileImpl<QSTile.State> {
        public boolean dumpInProgress;

        /* renamed from: gm */
        public final GarbageMonitor f87gm;
        public ProcessMemInfo pmi;

        public final int getMetricsCategory() {
            return 0;
        }

        public final Intent getLongClickIntent() {
            return new Intent();
        }

        public final CharSequence getTileLabel() {
            return this.mState.label;
        }

        public final void handleClick(View view) {
            if (!this.dumpInProgress) {
                this.dumpInProgress = true;
                refreshState((Object) null);
                new Thread() {
                    public static final /* synthetic */ int $r8$clinit = 0;

                    public final void run() {
                        int i;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException unused) {
                        }
                        GarbageMonitor garbageMonitor = MemoryTile.this.f87gm;
                        boolean z = GarbageMonitor.LEAK_REPORTING_ENABLED;
                        Objects.requireNonNull(garbageMonitor);
                        DumpTruck dumpTruck = garbageMonitor.mDumpTruck;
                        ArrayList<Long> arrayList = garbageMonitor.mPids;
                        Objects.requireNonNull(dumpTruck);
                        File file = new File(dumpTruck.context.getCacheDir(), "leak");
                        file.mkdirs();
                        dumpTruck.hprofUri = null;
                        dumpTruck.body.setLength(0);
                        StringBuilder sb = dumpTruck.body;
                        sb.append("Build: ");
                        sb.append(Build.DISPLAY);
                        sb.append("\n\nProcesses:\n");
                        ArrayList arrayList2 = new ArrayList();
                        int myPid = Process.myPid();
                        Iterator<Long> it = arrayList.iterator();
                        while (it.hasNext()) {
                            int intValue = it.next().intValue();
                            StringBuilder sb2 = dumpTruck.body;
                            sb2.append("  pid ");
                            sb2.append(intValue);
                            GarbageMonitor garbageMonitor2 = dumpTruck.mGarbageMonitor;
                            Objects.requireNonNull(garbageMonitor2);
                            ProcessMemInfo processMemInfo = garbageMonitor2.mData.get((long) intValue);
                            if (processMemInfo != null) {
                                StringBuilder sb3 = dumpTruck.body;
                                sb3.append(":");
                                sb3.append(" up=");
                                sb3.append(System.currentTimeMillis() - processMemInfo.startTime);
                                sb3.append(" rss=");
                                sb3.append(processMemInfo.currentRss);
                                dumpTruck.rss = processMemInfo.currentRss;
                                i = myPid;
                            } else {
                                i = myPid;
                            }
                            if (intValue == i) {
                                String path = new File(file, String.format("heap-%d.ahprof", new Object[]{Integer.valueOf(intValue)})).getPath();
                                Log.v("DumpTruck", "Dumping memory info for process " + intValue + " to " + path);
                                try {
                                    Debug.dumpHprofData(path);
                                    arrayList2.add(path);
                                    dumpTruck.body.append(" (hprof attached)");
                                } catch (IOException e) {
                                    Log.e("DumpTruck", "error dumping memory:", e);
                                    StringBuilder sb4 = dumpTruck.body;
                                    sb4.append("\n** Could not dump heap: \n");
                                    sb4.append(e.toString());
                                    sb4.append("\n");
                                }
                            }
                            dumpTruck.body.append("\n");
                            myPid = i;
                        }
                        try {
                            String canonicalPath = new File(file, String.format("hprof-%d.zip", new Object[]{Long.valueOf(System.currentTimeMillis())})).getCanonicalPath();
                            if (DumpTruck.zipUp(canonicalPath, arrayList2)) {
                                dumpTruck.hprofUri = FileProvider.getPathStrategy(dumpTruck.context, "com.android.systemui.fileprovider").getUriForFile(new File(canonicalPath));
                                Log.v("DumpTruck", "Heap dump accessible at URI: " + dumpTruck.hprofUri);
                            }
                        } catch (IOException e2) {
                            Log.e("DumpTruck", "unable to zip up heapdumps", e2);
                            StringBuilder sb5 = dumpTruck.body;
                            sb5.append("\n** Could not zip up files: \n");
                            sb5.append(e2.toString());
                            sb5.append("\n");
                        }
                        Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
                        intent.addFlags(268435456);
                        intent.addFlags(1);
                        intent.putExtra("android.intent.extra.SUBJECT", String.format("SystemUI memory dump (rss=%dM)", new Object[]{Long.valueOf(dumpTruck.rss / 1024)}));
                        intent.putExtra("android.intent.extra.TEXT", dumpTruck.body.toString());
                        if (dumpTruck.hprofUri != null) {
                            ArrayList arrayList3 = new ArrayList();
                            arrayList3.add(dumpTruck.hprofUri);
                            intent.setType("application/zip");
                            intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList3);
                            intent.setClipData(new ClipData(new ClipDescription("content", new String[]{"text/plain"}), new ClipData.Item(dumpTruck.hprofUri)));
                            intent.addFlags(1);
                        }
                        MemoryTile.this.mHandler.post(new BubbleStackView$$ExternalSyntheticLambda20(this, intent, 3));
                    }
                }.start();
            }
        }

        public final void handleUpdateState(QSTile.State state, Object obj) {
            int i;
            String str;
            GarbageMonitor garbageMonitor = this.f87gm;
            int myPid = Process.myPid();
            Objects.requireNonNull(garbageMonitor);
            this.pmi = garbageMonitor.mData.get((long) myPid);
            MemoryGraphIcon memoryGraphIcon = new MemoryGraphIcon(0);
            memoryGraphIcon.limit = this.f87gm.mHeapLimit;
            boolean z = this.dumpInProgress;
            if (z) {
                i = 0;
            } else {
                i = 2;
            }
            state.state = i;
            if (z) {
                str = "Dumping...";
            } else {
                str = this.mContext.getString(C1777R.string.heap_dump_tile_name);
            }
            state.label = str;
            ProcessMemInfo processMemInfo = this.pmi;
            if (processMemInfo != null) {
                long j = processMemInfo.currentRss;
                memoryGraphIcon.rss = j;
                state.secondaryLabel = String.format("rss: %s / %s", new Object[]{GarbageMonitor.m286$$Nest$smformatBytes(j * 1024), GarbageMonitor.m286$$Nest$smformatBytes(this.f87gm.mHeapLimit * 1024)});
            } else {
                memoryGraphIcon.rss = 0;
                state.secondaryLabel = null;
            }
            state.icon = memoryGraphIcon;
        }

        public final QSTile.State newTileState() {
            return new QSTile.State();
        }

        public MemoryTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, GarbageMonitor garbageMonitor) {
            super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
            this.f87gm = garbageMonitor;
        }

        public final void handleSetListening(boolean z) {
            MemoryTile memoryTile;
            super.handleSetListening(z);
            GarbageMonitor garbageMonitor = this.f87gm;
            if (garbageMonitor != null) {
                if (z) {
                    memoryTile = this;
                } else {
                    memoryTile = null;
                }
                boolean z2 = GarbageMonitor.LEAK_REPORTING_ENABLED;
                Objects.requireNonNull(garbageMonitor);
                garbageMonitor.mQSTile = memoryTile;
                if (memoryTile != null) {
                    memoryTile.refreshState((Object) null);
                }
            }
            ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService(ActivityManager.class);
            if (z) {
                long j = this.f87gm.mHeapLimit;
                if (j > 0) {
                    activityManager.setWatchHeapLimit(j * 1024);
                    return;
                }
            }
            activityManager.clearWatchHeapLimit();
        }
    }

    public static class Service extends CoreStartable {
        public final GarbageMonitor mGarbageMonitor;

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            GarbageMonitor garbageMonitor = this.mGarbageMonitor;
            if (garbageMonitor != null) {
                printWriter.println("GarbageMonitor params:");
                printWriter.println(String.format("   mHeapLimit=%d KB", new Object[]{Long.valueOf(garbageMonitor.mHeapLimit)}));
                printWriter.println(String.format("   GARBAGE_INSPECTION_INTERVAL=%d (%.1f mins)", new Object[]{900000L, Float.valueOf(15.0f)}));
                printWriter.println(String.format("   HEAP_TRACK_INTERVAL=%d (%.1f mins)", new Object[]{60000L, Float.valueOf(1.0f)}));
                printWriter.println(String.format("   HEAP_TRACK_HISTORY_LEN=%d (%.1f hr total)", new Object[]{720, Float.valueOf(12.0f)}));
                printWriter.println("GarbageMonitor tracked processes:");
                Iterator<Long> it = garbageMonitor.mPids.iterator();
                while (it.hasNext()) {
                    ProcessMemInfo processMemInfo = garbageMonitor.mData.get(it.next().longValue());
                    if (processMemInfo != null) {
                        printWriter.print("{ \"pid\": ");
                        printWriter.print(processMemInfo.pid);
                        printWriter.print(", \"name\": \"");
                        printWriter.print(processMemInfo.name.replace('\"', '-'));
                        printWriter.print("\", \"start\": ");
                        printWriter.print(processMemInfo.startTime);
                        printWriter.print(", \"rss\": [");
                        for (int i = 0; i < processMemInfo.rss.length; i++) {
                            if (i > 0) {
                                printWriter.print(",");
                            }
                            long[] jArr = processMemInfo.rss;
                            printWriter.print(jArr[(processMemInfo.head + i) % jArr.length]);
                        }
                        printWriter.println("] }");
                    }
                }
            }
        }

        public final void start() {
            boolean z = false;
            if (Settings.Secure.getInt(this.mContext.getContentResolver(), "sysui_force_enable_leak_reporting", 0) != 0) {
                z = true;
            }
            if (GarbageMonitor.LEAK_REPORTING_ENABLED || z) {
                GarbageMonitor garbageMonitor = this.mGarbageMonitor;
                Objects.requireNonNull(garbageMonitor);
                if (garbageMonitor.mTrackedGarbage != null) {
                    garbageMonitor.mMessageRouter.sendMessage(1000);
                }
            }
            if (GarbageMonitor.HEAP_TRACKING_ENABLED || z) {
                GarbageMonitor garbageMonitor2 = this.mGarbageMonitor;
                Objects.requireNonNull(garbageMonitor2);
                long myPid = (long) Process.myPid();
                String packageName = garbageMonitor2.mContext.getPackageName();
                long currentTimeMillis = System.currentTimeMillis();
                synchronized (garbageMonitor2.mPids) {
                    if (!garbageMonitor2.mPids.contains(Long.valueOf(myPid))) {
                        garbageMonitor2.mPids.add(Long.valueOf(myPid));
                        garbageMonitor2.logPids();
                        garbageMonitor2.mData.put(myPid, new ProcessMemInfo(myPid, packageName, currentTimeMillis));
                    }
                }
                garbageMonitor2.mMessageRouter.sendMessage(3000);
            }
        }

        public Service(Context context, GarbageMonitor garbageMonitor) {
            super(context);
            this.mGarbageMonitor = garbageMonitor;
        }
    }

    public static class ProcessMemInfo implements Dumpable {
        public long currentRss;
        public int head = 0;
        public long max = 1;
        public String name;
        public long pid;
        public long[] rss = new long[720];
        public long startTime;

        public ProcessMemInfo(long j, String str, long j2) {
            this.pid = j;
            this.name = str;
            this.startTime = j2;
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.print("{ \"pid\": ");
            printWriter.print(this.pid);
            printWriter.print(", \"name\": \"");
            printWriter.print(this.name.replace('\"', '-'));
            printWriter.print("\", \"start\": ");
            printWriter.print(this.startTime);
            printWriter.print(", \"rss\": [");
            for (int i = 0; i < this.rss.length; i++) {
                if (i > 0) {
                    printWriter.print(",");
                }
                long[] jArr = this.rss;
                printWriter.print(jArr[(this.head + i) % jArr.length]);
            }
            printWriter.println("] }");
        }
    }

    /* renamed from: -$$Nest$smformatBytes  reason: not valid java name */
    public static String m286$$Nest$smformatBytes(long j) {
        String[] strArr = {"B", "K", "M", "G", "T"};
        int i = 0;
        while (i < 5 && j >= 1024) {
            j /= 1024;
            i++;
        }
        return j + strArr[i];
    }

    static {
        boolean z;
        boolean z2 = true;
        if (!Build.IS_DEBUGGABLE || !SystemProperties.getBoolean("debug.enable_leak_reporting", false)) {
            z = false;
        } else {
            z = true;
        }
        LEAK_REPORTING_ENABLED = z;
        boolean z3 = Build.IS_DEBUGGABLE;
        HEAP_TRACKING_ENABLED = z3;
        if (!z3 || !SystemProperties.getBoolean("debug.enable_sysui_heap_limit", false)) {
            z2 = false;
        }
        ENABLE_AM_HEAP_LIMIT = z2;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("GarbageMonitor params:");
        printWriter.println(String.format("   mHeapLimit=%d KB", new Object[]{Long.valueOf(this.mHeapLimit)}));
        printWriter.println(String.format("   GARBAGE_INSPECTION_INTERVAL=%d (%.1f mins)", new Object[]{900000L, Float.valueOf(15.0f)}));
        printWriter.println(String.format("   HEAP_TRACK_INTERVAL=%d (%.1f mins)", new Object[]{60000L, Float.valueOf(1.0f)}));
        printWriter.println(String.format("   HEAP_TRACK_HISTORY_LEN=%d (%.1f hr total)", new Object[]{720, Float.valueOf(12.0f)}));
        printWriter.println("GarbageMonitor tracked processes:");
        Iterator<Long> it = this.mPids.iterator();
        while (it.hasNext()) {
            ProcessMemInfo processMemInfo = this.mData.get(it.next().longValue());
            if (processMemInfo != null) {
                printWriter.print("{ \"pid\": ");
                printWriter.print(processMemInfo.pid);
                printWriter.print(", \"name\": \"");
                printWriter.print(processMemInfo.name.replace('\"', '-'));
                printWriter.print("\", \"start\": ");
                printWriter.print(processMemInfo.startTime);
                printWriter.print(", \"rss\": [");
                for (int i = 0; i < processMemInfo.rss.length; i++) {
                    if (i > 0) {
                        printWriter.print(",");
                    }
                    long[] jArr = processMemInfo.rss;
                    printWriter.print(jArr[(processMemInfo.head + i) % jArr.length]);
                }
                printWriter.println("] }");
            }
        }
    }

    public final void logPids() {
        if (DEBUG) {
            StringBuffer stringBuffer = new StringBuffer("Now tracking processes: ");
            for (int i = 0; i < this.mPids.size(); i++) {
                this.mPids.get(i).intValue();
                stringBuffer.append(" ");
            }
            Log.v("GarbageMonitor", stringBuffer.toString());
        }
    }

    public GarbageMonitor(Context context, DelayableExecutor delayableExecutor, MessageRouter messageRouter, LeakDetector leakDetector, LeakReporter leakReporter, DumpManager dumpManager) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mDelayableExecutor = delayableExecutor;
        this.mMessageRouter = messageRouter;
        messageRouter.subscribeTo(1000, (MessageRouter.SimpleMessageListener) new GarbageMonitor$$ExternalSyntheticLambda0(this));
        messageRouter.subscribeTo(3000, (MessageRouter.SimpleMessageListener) new GarbageMonitor$$ExternalSyntheticLambda1(this));
        Objects.requireNonNull(leakDetector);
        this.mTrackedGarbage = leakDetector.mTrackedGarbage;
        this.mLeakReporter = leakReporter;
        this.mDumpTruck = new DumpTruck(applicationContext, this);
        dumpManager.registerDumpable("GarbageMonitor", this);
        if (ENABLE_AM_HEAP_LIMIT) {
            this.mHeapLimit = (long) Settings.Global.getInt(context.getContentResolver(), "systemui_am_heap_limit", applicationContext.getResources().getInteger(C1777R.integer.watch_heap_limit));
        }
    }
}
