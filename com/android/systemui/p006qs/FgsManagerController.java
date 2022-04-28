package com.android.systemui.p006qs;

import android.app.IActivityManager;
import android.app.IForegroundServiceObserver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* renamed from: com.android.systemui.qs.FgsManagerController */
/* compiled from: FgsManagerController.kt */
public final class FgsManagerController extends IForegroundServiceObserver.Stub implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final IActivityManager activityManager;
    public final AppListAdapter appListAdapter = new AppListAdapter();
    public final Executor backgroundExecutor;
    public boolean changesSinceDialog;
    public final Context context;
    public final DeviceConfigProxy deviceConfigProxy;
    public SystemUIDialog dialog;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final DumpManager dumpManager;
    public boolean initialized;
    public boolean isAvailable;
    public final Object lock = new Object();
    public final Executor mainExecutor;
    public final LinkedHashSet onDialogDismissedListeners = new LinkedHashSet();
    public final LinkedHashSet onNumberOfPackagesChangedListeners = new LinkedHashSet();
    public final PackageManager packageManager;
    public ArrayMap<UserPackage, RunningApp> runningApps = new ArrayMap<>();
    public final LinkedHashMap runningServiceTokens = new LinkedHashMap();
    public final SystemClock systemClock;

    /* renamed from: com.android.systemui.qs.FgsManagerController$AppListAdapter */
    /* compiled from: FgsManagerController.kt */
    public final class AppListAdapter extends RecyclerView.Adapter<AppItemViewHolder> {
        public List<RunningApp> data = EmptyList.INSTANCE;
        public final Object lock = new Object();

        public AppListAdapter() {
        }

        public final int getItemCount() {
            return this.data.size();
        }

        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            T t;
            AppItemViewHolder appItemViewHolder = (AppItemViewHolder) viewHolder;
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            synchronized (this.lock) {
                t = this.data.get(i);
                ref$ObjectRef.element = t;
            }
            FgsManagerController fgsManagerController = FgsManagerController.this;
            ImageView imageView = appItemViewHolder.iconView;
            RunningApp runningApp = (RunningApp) t;
            Objects.requireNonNull(runningApp);
            imageView.setImageDrawable(runningApp.icon);
            TextView textView = appItemViewHolder.appLabelView;
            RunningApp runningApp2 = (RunningApp) ref$ObjectRef.element;
            Objects.requireNonNull(runningApp2);
            textView.setText(runningApp2.appLabel);
            TextView textView2 = appItemViewHolder.durationView;
            long elapsedRealtime = fgsManagerController.systemClock.elapsedRealtime();
            RunningApp runningApp3 = (RunningApp) ref$ObjectRef.element;
            Objects.requireNonNull(runningApp3);
            textView2.setText(DateUtils.formatDuration(Math.max(elapsedRealtime - runningApp3.timeStarted, 60000), 20));
            appItemViewHolder.stopButton.setOnClickListener(new FgsManagerController$AppListAdapter$onBindViewHolder$2$1(appItemViewHolder, fgsManagerController, ref$ObjectRef));
            RunningApp runningApp4 = (RunningApp) ref$ObjectRef.element;
            Objects.requireNonNull(runningApp4);
            if (runningApp4.uiControl == UIControl.HIDE_BUTTON) {
                appItemViewHolder.stopButton.setVisibility(4);
            }
            RunningApp runningApp5 = (RunningApp) ref$ObjectRef.element;
            Objects.requireNonNull(runningApp5);
            if (runningApp5.stopped) {
                appItemViewHolder.stopButton.setEnabled(false);
                appItemViewHolder.stopButton.setText(C1777R.string.fgs_manager_app_item_stop_button_stopped_label);
                appItemViewHolder.durationView.setVisibility(8);
                return;
            }
            appItemViewHolder.stopButton.setEnabled(true);
            appItemViewHolder.stopButton.setText(C1777R.string.fgs_manager_app_item_stop_button_label);
            appItemViewHolder.durationView.setVisibility(0);
        }

        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            return new AppItemViewHolder(LayoutInflater.from(recyclerView.getContext()).inflate(C1777R.layout.fgs_manager_app_item, recyclerView, false));
        }
    }

    /* renamed from: com.android.systemui.qs.FgsManagerController$OnDialogDismissedListener */
    /* compiled from: FgsManagerController.kt */
    public interface OnDialogDismissedListener {
        void onDialogDismissed();
    }

    /* renamed from: com.android.systemui.qs.FgsManagerController$OnNumberOfPackagesChangedListener */
    /* compiled from: FgsManagerController.kt */
    public interface OnNumberOfPackagesChangedListener {
        void onNumberOfPackagesChanged(int i);
    }

    /* renamed from: com.android.systemui.qs.FgsManagerController$RunningApp */
    /* compiled from: FgsManagerController.kt */
    public static final class RunningApp {
        public CharSequence appLabel = "";
        public Drawable icon;
        public final String packageName;
        public boolean stopped;
        public final long timeStarted;
        public final UIControl uiControl;
        public final int userId;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RunningApp)) {
                return false;
            }
            RunningApp runningApp = (RunningApp) obj;
            return this.userId == runningApp.userId && Intrinsics.areEqual(this.packageName, runningApp.packageName) && this.timeStarted == runningApp.timeStarted && this.uiControl == runningApp.uiControl;
        }

        public final int hashCode() {
            int hashCode = this.packageName.hashCode();
            int hashCode2 = Long.hashCode(this.timeStarted);
            return this.uiControl.hashCode() + ((hashCode2 + ((hashCode + (Integer.hashCode(this.userId) * 31)) * 31)) * 31);
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter, SystemClock systemClock) {
            indentingPrintWriter.println("RunningApp: [");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(Intrinsics.stringPlus("userId=", Integer.valueOf(this.userId)));
            indentingPrintWriter.println(Intrinsics.stringPlus("packageName=", this.packageName));
            indentingPrintWriter.println("timeStarted=" + this.timeStarted + " (time since start = " + (systemClock.elapsedRealtime() - this.timeStarted) + "ms)\"");
            indentingPrintWriter.println(Intrinsics.stringPlus("uiControl=", this.uiControl));
            indentingPrintWriter.println(Intrinsics.stringPlus("appLabel=", this.appLabel));
            indentingPrintWriter.println(Intrinsics.stringPlus("icon=", this.icon));
            indentingPrintWriter.println(Intrinsics.stringPlus("stopped=", Boolean.valueOf(this.stopped)));
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("]");
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("RunningApp(userId=");
            m.append(this.userId);
            m.append(", packageName=");
            m.append(this.packageName);
            m.append(", timeStarted=");
            m.append(this.timeStarted);
            m.append(", uiControl=");
            m.append(this.uiControl);
            m.append(')');
            return m.toString();
        }

        public RunningApp(int i, String str, long j, UIControl uIControl) {
            this.userId = i;
            this.packageName = str;
            this.timeStarted = j;
            this.uiControl = uIControl;
        }
    }

    /* renamed from: com.android.systemui.qs.FgsManagerController$StartTimeAndTokens */
    /* compiled from: FgsManagerController.kt */
    public static final class StartTimeAndTokens {
        public final long startTime;
        public final SystemClock systemClock;
        public final LinkedHashSet tokens = new LinkedHashSet();

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof StartTimeAndTokens) && Intrinsics.areEqual(this.systemClock, ((StartTimeAndTokens) obj).systemClock);
        }

        public final int hashCode() {
            return this.systemClock.hashCode();
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("StartTimeAndTokens: [");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("startTime=" + this.startTime + " (time running = " + (this.systemClock.elapsedRealtime() - this.startTime) + "ms)");
            indentingPrintWriter.println("tokens: [");
            indentingPrintWriter.increaseIndent();
            for (IBinder valueOf : this.tokens) {
                indentingPrintWriter.println(String.valueOf(valueOf));
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("]");
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("]");
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("StartTimeAndTokens(systemClock=");
            m.append(this.systemClock);
            m.append(')');
            return m.toString();
        }

        public StartTimeAndTokens(SystemClock systemClock2) {
            this.systemClock = systemClock2;
            this.startTime = systemClock2.elapsedRealtime();
        }
    }

    /* renamed from: com.android.systemui.qs.FgsManagerController$UIControl */
    /* compiled from: FgsManagerController.kt */
    public enum UIControl {
        NORMAL,
        HIDE_BUTTON,
        HIDE_ENTRY
    }

    /* renamed from: com.android.systemui.qs.FgsManagerController$UserPackage */
    /* compiled from: FgsManagerController.kt */
    public final class UserPackage {
        public final String packageName;
        public final Lazy uiControl$delegate;
        public final int userId;

        public final int hashCode() {
            return Objects.hash(new Object[]{Integer.valueOf(this.userId), this.packageName});
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("UserPackage: [");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(Intrinsics.stringPlus("userId=", Integer.valueOf(this.userId)));
            indentingPrintWriter.println(Intrinsics.stringPlus("packageName=", this.packageName));
            indentingPrintWriter.println(Intrinsics.stringPlus("uiControl=", (UIControl) this.uiControl$delegate.getValue()));
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("]");
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof UserPackage)) {
                return false;
            }
            UserPackage userPackage = (UserPackage) obj;
            if (!Intrinsics.areEqual(userPackage.packageName, this.packageName) || userPackage.userId != this.userId) {
                return false;
            }
            return true;
        }

        public UserPackage(FgsManagerController fgsManagerController, int i, String str) {
            this.userId = i;
            this.packageName = str;
            this.uiControl$delegate = LazyKt__LazyJVMKt.lazy(new FgsManagerController$UserPackage$uiControl$2(fgsManagerController, this));
        }
    }

    /* renamed from: com.android.systemui.qs.FgsManagerController$AppItemViewHolder */
    /* compiled from: FgsManagerController.kt */
    public static final class AppItemViewHolder extends RecyclerView.ViewHolder {
        public final TextView appLabelView;
        public final TextView durationView;
        public final ImageView iconView;
        public final Button stopButton;

        public AppItemViewHolder(View view) {
            super(view);
            this.appLabelView = (TextView) view.requireViewById(C1777R.C1779id.fgs_manager_app_item_label);
            this.durationView = (TextView) view.requireViewById(C1777R.C1779id.fgs_manager_app_item_duration);
            this.iconView = (ImageView) view.requireViewById(C1777R.C1779id.fgs_manager_app_item_icon);
            this.stopButton = (Button) view.requireViewById(C1777R.C1779id.fgs_manager_app_item_stop_button);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        synchronized (this.lock) {
            indentingPrintWriter.println(Intrinsics.stringPlus("changesSinceDialog=", Boolean.valueOf(this.changesSinceDialog)));
            indentingPrintWriter.println("Running service tokens: [");
            indentingPrintWriter.increaseIndent();
            for (Map.Entry entry : this.runningServiceTokens.entrySet()) {
                indentingPrintWriter.println("{");
                indentingPrintWriter.increaseIndent();
                ((UserPackage) entry.getKey()).dump(indentingPrintWriter);
                ((StartTimeAndTokens) entry.getValue()).dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("}");
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("]");
            indentingPrintWriter.println("Loaded package UI info: [");
            indentingPrintWriter.increaseIndent();
            for (Map.Entry next : this.runningApps.entrySet()) {
                indentingPrintWriter.println("{");
                ((UserPackage) next.getKey()).dump(indentingPrintWriter);
                ((RunningApp) next.getValue()).dump(indentingPrintWriter, this.systemClock);
                indentingPrintWriter.println("}");
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("]");
        }
    }

    public final int getNumRunningPackages() {
        int numRunningPackagesLocked;
        synchronized (this.lock) {
            numRunningPackagesLocked = getNumRunningPackagesLocked();
        }
        return numRunningPackagesLocked;
    }

    public final int getNumRunningPackagesLocked() {
        boolean z;
        Set<UserPackage> keySet = this.runningServiceTokens.keySet();
        if ((keySet instanceof Collection) && keySet.isEmpty()) {
            return 0;
        }
        int i = 0;
        for (UserPackage userPackage : keySet) {
            Objects.requireNonNull(userPackage);
            if (((UIControl) userPackage.uiControl$delegate.getValue()) != UIControl.HIDE_ENTRY) {
                z = true;
            } else {
                z = false;
            }
            if (z && (i = i + 1) < 0) {
                throw new ArithmeticException("Count overflow has happened.");
            }
        }
        return i;
    }

    public final void onForegroundStateChanged(IBinder iBinder, String str, int i, boolean z) {
        synchronized (this.lock) {
            int numRunningPackagesLocked = getNumRunningPackagesLocked();
            UserPackage userPackage = new UserPackage(this, i, str);
            if (z) {
                LinkedHashMap linkedHashMap = this.runningServiceTokens;
                Object obj = linkedHashMap.get(userPackage);
                if (obj == null) {
                    obj = new StartTimeAndTokens(this.systemClock);
                    linkedHashMap.put(userPackage, obj);
                }
                ((StartTimeAndTokens) obj).tokens.add(iBinder);
            } else {
                StartTimeAndTokens startTimeAndTokens = (StartTimeAndTokens) this.runningServiceTokens.get(userPackage);
                boolean z2 = false;
                if (startTimeAndTokens != null) {
                    startTimeAndTokens.tokens.remove(iBinder);
                    if (startTimeAndTokens.tokens.isEmpty()) {
                        z2 = true;
                    }
                }
                if (z2) {
                    this.runningServiceTokens.remove(userPackage);
                }
            }
            int numRunningPackagesLocked2 = getNumRunningPackagesLocked();
            if (numRunningPackagesLocked2 != numRunningPackagesLocked) {
                this.changesSinceDialog = true;
                for (OnNumberOfPackagesChangedListener fgsManagerController$onForegroundStateChanged$1$3$1 : this.onNumberOfPackagesChangedListeners) {
                    this.backgroundExecutor.execute(new FgsManagerController$onForegroundStateChanged$1$3$1(fgsManagerController$onForegroundStateChanged$1$3$1, numRunningPackagesLocked2));
                }
            }
            updateAppItemsLocked();
        }
    }

    public final void updateAppItemsLocked() {
        boolean z;
        if (this.dialog == null) {
            this.runningApps.clear();
            return;
        }
        Set keySet = this.runningServiceTokens.keySet();
        ArrayList arrayList = new ArrayList();
        Iterator it = keySet.iterator();
        while (true) {
            boolean z2 = false;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            UserPackage userPackage = (UserPackage) next;
            Objects.requireNonNull(userPackage);
            if (((UIControl) userPackage.uiControl$delegate.getValue()) != UIControl.HIDE_ENTRY) {
                RunningApp runningApp = this.runningApps.get(userPackage);
                if (runningApp != null && runningApp.stopped) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    z2 = true;
                }
            }
            if (z2) {
                arrayList.add(next);
            }
        }
        Set<UserPackage> keySet2 = this.runningApps.keySet();
        ArrayList arrayList2 = new ArrayList();
        for (T next2 : keySet2) {
            if (!this.runningServiceTokens.containsKey((UserPackage) next2)) {
                arrayList2.add(next2);
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            UserPackage userPackage2 = (UserPackage) it2.next();
            PackageManager packageManager2 = this.packageManager;
            Objects.requireNonNull(userPackage2);
            ApplicationInfo applicationInfoAsUser = packageManager2.getApplicationInfoAsUser(userPackage2.packageName, 0, userPackage2.userId);
            ArrayMap<UserPackage, RunningApp> arrayMap = this.runningApps;
            int i = userPackage2.userId;
            String str = userPackage2.packageName;
            Object obj = this.runningServiceTokens.get(userPackage2);
            Intrinsics.checkNotNull(obj);
            CharSequence loadLabel = applicationInfoAsUser.loadLabel(this.packageManager);
            Drawable loadIcon = applicationInfoAsUser.loadIcon(this.packageManager);
            RunningApp runningApp2 = new RunningApp(i, str, ((StartTimeAndTokens) obj).startTime, (UIControl) userPackage2.uiControl$delegate.getValue());
            runningApp2.appLabel = loadLabel;
            runningApp2.icon = loadIcon;
            arrayMap.put(userPackage2, runningApp2);
        }
        Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            UserPackage userPackage3 = (UserPackage) it3.next();
            RunningApp runningApp3 = this.runningApps.get(userPackage3);
            Intrinsics.checkNotNull(runningApp3);
            RunningApp runningApp4 = runningApp3;
            RunningApp runningApp5 = new RunningApp(runningApp4.userId, runningApp4.packageName, runningApp4.timeStarted, runningApp4.uiControl);
            runningApp5.stopped = true;
            runningApp5.appLabel = runningApp4.appLabel;
            runningApp5.icon = runningApp4.icon;
            this.runningApps.put(userPackage3, runningApp5);
        }
        this.mainExecutor.execute(new FgsManagerController$updateAppItemsLocked$3(this));
    }

    public FgsManagerController(Context context2, Executor executor, Executor executor2, SystemClock systemClock2, IActivityManager iActivityManager, PackageManager packageManager2, DeviceConfigProxy deviceConfigProxy2, DialogLaunchAnimator dialogLaunchAnimator2, DumpManager dumpManager2) {
        this.context = context2;
        this.mainExecutor = executor;
        this.backgroundExecutor = executor2;
        this.systemClock = systemClock2;
        this.activityManager = iActivityManager;
        this.packageManager = packageManager2;
        this.deviceConfigProxy = deviceConfigProxy2;
        this.dialogLaunchAnimator = dialogLaunchAnimator2;
        this.dumpManager = dumpManager2;
    }

    static {
        Class<FgsManagerController> cls = FgsManagerController.class;
    }
}
