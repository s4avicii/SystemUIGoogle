package com.google.android.systemui.smartspace;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.smartspace.nano.SmartspaceProto$CardWrapper;
import com.android.systemui.util.Assert;
import com.google.protobuf.nano.MessageNano;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public final class SmartSpaceController implements Dumpable {
    public static final boolean DEBUG = Log.isLoggable("SmartSpaceController", 3);
    public final AlarmManager mAlarmManager;
    public boolean mAlarmRegistered;
    public final Context mAppContext;
    public final Handler mBackgroundHandler;
    public final Context mContext;
    public int mCurrentUserId;
    public final SmartSpaceData mData;
    public final SmartSpaceController$$ExternalSyntheticLambda0 mExpireAlarmAction = new SmartSpaceController$$ExternalSyntheticLambda0(this, 0);
    public boolean mHidePrivateData;
    public boolean mHideWorkData;
    public final C23141 mKeyguardMonitorCallback;
    public final ArrayList<SmartSpaceUpdateListener> mListeners = new ArrayList<>();
    public boolean mSmartSpaceEnabledBroadcastSent;
    public final ProtoStore mStore;
    public final Handler mUiHandler;

    public class UserSwitchReceiver extends BroadcastReceiver {
        public UserSwitchReceiver() {
        }

        public final void onReceive(Context context, Intent intent) {
            if (SmartSpaceController.DEBUG) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Switching user: ");
                m.append(intent.getAction());
                m.append(" uid: ");
                m.append(UserHandle.myUserId());
                Log.d("SmartSpaceController", m.toString());
            }
            if (intent.getAction().equals("android.intent.action.USER_SWITCHED")) {
                SmartSpaceController.this.mCurrentUserId = intent.getIntExtra("android.intent.extra.user_handle", -1);
                SmartSpaceData smartSpaceData = SmartSpaceController.this.mData;
                Objects.requireNonNull(smartSpaceData);
                smartSpaceData.mWeatherCard = null;
                smartSpaceData.mCurrentCard = null;
                SmartSpaceController.this.onExpire(true);
            }
            SmartSpaceController.this.onExpire(true);
        }
    }

    public final boolean isSmartSpaceDisabledByExperiments() {
        boolean z;
        String string = Settings.Global.getString(this.mContext.getContentResolver(), "always_on_display_constants");
        KeyValueListParser keyValueListParser = new KeyValueListParser(',');
        try {
            keyValueListParser.setString(string);
            z = keyValueListParser.getBoolean("smart_space_enabled", true);
        } catch (IllegalArgumentException unused) {
            Log.e("SmartSpaceController", "Bad AOD constants");
            z = true;
        }
        return !z;
    }

    public final SmartSpaceCard loadSmartSpaceData(boolean z) {
        FileInputStream fileInputStream;
        SmartspaceProto$CardWrapper smartspaceProto$CardWrapper = new SmartspaceProto$CardWrapper();
        ProtoStore protoStore = this.mStore;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("smartspace_");
        m.append(this.mCurrentUserId);
        m.append("_");
        m.append(z);
        String sb = m.toString();
        Objects.requireNonNull(protoStore);
        File fileStreamPath = ((Context) protoStore.mContext).getFileStreamPath(sb);
        boolean z2 = false;
        try {
            fileInputStream = new FileInputStream(fileStreamPath);
            int length = (int) fileStreamPath.length();
            byte[] bArr = new byte[length];
            fileInputStream.read(bArr, 0, length);
            MessageNano.mergeFrom(smartspaceProto$CardWrapper, bArr);
            fileInputStream.close();
            z2 = true;
        } catch (FileNotFoundException unused) {
            Log.d("ProtoStore", "no cached data");
        } catch (Exception e) {
            Log.e("ProtoStore", "unable to load data", e);
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        if (z2) {
            return SmartSpaceCard.fromWrapper(this.mContext, smartspaceProto$CardWrapper, !z);
        }
        return null;
        throw th;
    }

    public final void onGsaChanged() {
        if (DEBUG) {
            Log.d("SmartSpaceController", "onGsaChanged");
        }
        if (UserHandle.myUserId() == 0) {
            this.mAppContext.sendBroadcast(new Intent("com.google.android.systemui.smartspace.ENABLE_UPDATE").setPackage("com.google.android.googlequicksearchbox").addFlags(268435456));
            this.mSmartSpaceEnabledBroadcastSent = true;
        }
        ArrayList arrayList = new ArrayList(this.mListeners);
        for (int i = 0; i < arrayList.size(); i++) {
            ((SmartSpaceUpdateListener) arrayList.get(i)).onGsaChanged();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void update() {
        /*
            r18 = this;
            r0 = r18
            com.android.systemui.util.Assert.isMainThread()
            boolean r1 = DEBUG
            java.lang.String r2 = "SmartSpaceController"
            if (r1 == 0) goto L_0x0011
            java.lang.String r3 = "update"
            android.util.Log.d(r2, r3)
        L_0x0011:
            boolean r3 = r0.mAlarmRegistered
            r4 = 0
            if (r3 == 0) goto L_0x001f
            android.app.AlarmManager r3 = r0.mAlarmManager
            com.google.android.systemui.smartspace.SmartSpaceController$$ExternalSyntheticLambda0 r5 = r0.mExpireAlarmAction
            r3.cancel(r5)
            r0.mAlarmRegistered = r4
        L_0x001f:
            com.google.android.systemui.smartspace.SmartSpaceData r3 = r0.mData
            java.util.Objects.requireNonNull(r3)
            boolean r5 = r3.hasCurrent()
            r6 = 0
            r8 = 1
            if (r5 == 0) goto L_0x0047
            com.google.android.systemui.smartspace.SmartSpaceCard r5 = r3.mWeatherCard
            if (r5 == 0) goto L_0x0033
            r5 = r8
            goto L_0x0034
        L_0x0033:
            r5 = r4
        L_0x0034:
            if (r5 == 0) goto L_0x0047
            com.google.android.systemui.smartspace.SmartSpaceCard r5 = r3.mCurrentCard
            long r9 = r5.getExpiration()
            com.google.android.systemui.smartspace.SmartSpaceCard r3 = r3.mWeatherCard
            long r11 = r3.getExpiration()
            long r9 = java.lang.Math.min(r9, r11)
            goto L_0x0061
        L_0x0047:
            boolean r5 = r3.hasCurrent()
            if (r5 == 0) goto L_0x0054
            com.google.android.systemui.smartspace.SmartSpaceCard r3 = r3.mCurrentCard
            long r9 = r3.getExpiration()
            goto L_0x0061
        L_0x0054:
            com.google.android.systemui.smartspace.SmartSpaceCard r3 = r3.mWeatherCard
            if (r3 == 0) goto L_0x005a
            r5 = r8
            goto L_0x005b
        L_0x005a:
            r5 = r4
        L_0x005b:
            if (r5 == 0) goto L_0x0063
            long r9 = r3.getExpiration()
        L_0x0061:
            r13 = r9
            goto L_0x0064
        L_0x0063:
            r13 = r6
        L_0x0064:
            int r3 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r3 <= 0) goto L_0x007a
            android.app.AlarmManager r11 = r0.mAlarmManager
            r12 = 0
            com.google.android.systemui.smartspace.SmartSpaceController$$ExternalSyntheticLambda0 r3 = r0.mExpireAlarmAction
            android.os.Handler r5 = r0.mUiHandler
            java.lang.String r15 = "SmartSpace"
            r16 = r3
            r17 = r5
            r11.set(r12, r13, r15, r16, r17)
            r0.mAlarmRegistered = r8
        L_0x007a:
            java.util.ArrayList<com.google.android.systemui.smartspace.SmartSpaceUpdateListener> r3 = r0.mListeners
            if (r3 == 0) goto L_0x00ad
            if (r1 == 0) goto L_0x0092
            java.lang.String r1 = "notifying listeners data="
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            com.google.android.systemui.smartspace.SmartSpaceData r3 = r0.mData
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r2, r1)
        L_0x0092:
            java.util.ArrayList r1 = new java.util.ArrayList
            java.util.ArrayList<com.google.android.systemui.smartspace.SmartSpaceUpdateListener> r2 = r0.mListeners
            r1.<init>(r2)
            int r2 = r1.size()
        L_0x009d:
            if (r4 >= r2) goto L_0x00ad
            java.lang.Object r3 = r1.get(r4)
            com.google.android.systemui.smartspace.SmartSpaceUpdateListener r3 = (com.google.android.systemui.smartspace.SmartSpaceUpdateListener) r3
            com.google.android.systemui.smartspace.SmartSpaceData r5 = r0.mData
            r3.onSmartSpaceUpdated(r5)
            int r4 = r4 + 1
            goto L_0x009d
        L_0x00ad:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.SmartSpaceController.update():void");
    }

    public SmartSpaceController(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, Handler handler, AlarmManager alarmManager, DumpManager dumpManager) {
        C23141 r0 = new KeyguardUpdateMonitorCallback() {
            /* JADX WARNING: Removed duplicated region for block: B:24:0x005b  */
            /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void onTimeChanged() {
                /*
                    r10 = this;
                    com.google.android.systemui.smartspace.SmartSpaceController r0 = com.google.android.systemui.smartspace.SmartSpaceController.this
                    com.google.android.systemui.smartspace.SmartSpaceData r0 = r0.mData
                    if (r0 == 0) goto L_0x0060
                    boolean r0 = r0.hasCurrent()
                    if (r0 == 0) goto L_0x0060
                    com.google.android.systemui.smartspace.SmartSpaceController r0 = com.google.android.systemui.smartspace.SmartSpaceController.this
                    com.google.android.systemui.smartspace.SmartSpaceData r0 = r0.mData
                    java.util.Objects.requireNonNull(r0)
                    long r1 = java.lang.System.currentTimeMillis()
                    boolean r3 = r0.hasCurrent()
                    r4 = 0
                    r6 = 1
                    r7 = 0
                    if (r3 == 0) goto L_0x003b
                    com.google.android.systemui.smartspace.SmartSpaceCard r3 = r0.mWeatherCard
                    if (r3 == 0) goto L_0x0027
                    r3 = r6
                    goto L_0x0028
                L_0x0027:
                    r3 = r7
                L_0x0028:
                    if (r3 == 0) goto L_0x003b
                    com.google.android.systemui.smartspace.SmartSpaceCard r3 = r0.mCurrentCard
                    long r6 = r3.getExpiration()
                    com.google.android.systemui.smartspace.SmartSpaceCard r0 = r0.mWeatherCard
                    long r8 = r0.getExpiration()
                    long r6 = java.lang.Math.min(r6, r8)
                    goto L_0x0054
                L_0x003b:
                    boolean r3 = r0.hasCurrent()
                    if (r3 == 0) goto L_0x0048
                    com.google.android.systemui.smartspace.SmartSpaceCard r0 = r0.mCurrentCard
                    long r6 = r0.getExpiration()
                    goto L_0x0054
                L_0x0048:
                    com.google.android.systemui.smartspace.SmartSpaceCard r0 = r0.mWeatherCard
                    if (r0 == 0) goto L_0x004d
                    goto L_0x004e
                L_0x004d:
                    r6 = r7
                L_0x004e:
                    if (r6 == 0) goto L_0x0056
                    long r6 = r0.getExpiration()
                L_0x0054:
                    long r6 = r6 - r1
                    goto L_0x0057
                L_0x0056:
                    r6 = r4
                L_0x0057:
                    int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                    if (r0 <= 0) goto L_0x0060
                    com.google.android.systemui.smartspace.SmartSpaceController r10 = com.google.android.systemui.smartspace.SmartSpaceController.this
                    r10.update()
                L_0x0060:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.SmartSpaceController.C23141.onTimeChanged():void");
            }
        };
        this.mKeyguardMonitorCallback = r0;
        this.mContext = context;
        this.mUiHandler = new Handler(Looper.getMainLooper());
        this.mStore = new ProtoStore(context);
        new HandlerThread("smartspace-background").start();
        this.mBackgroundHandler = handler;
        this.mCurrentUserId = UserHandle.myUserId();
        this.mAppContext = context;
        this.mAlarmManager = alarmManager;
        SmartSpaceData smartSpaceData = new SmartSpaceData();
        this.mData = smartSpaceData;
        if (!isSmartSpaceDisabledByExperiments()) {
            keyguardUpdateMonitor.registerCallback(r0);
            smartSpaceData.mCurrentCard = loadSmartSpaceData(true);
            smartSpaceData.mWeatherCard = loadSmartSpaceData(false);
            update();
            onGsaChanged();
            C23152 r8 = new BroadcastReceiver() {
                public final void onReceive(Context context, Intent intent) {
                    SmartSpaceController.this.onGsaChanged();
                }
            };
            String[] strArr = {"android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.PACKAGE_REMOVED", "android.intent.action.PACKAGE_DATA_CLEARED"};
            IntentFilter intentFilter = new IntentFilter();
            for (int i = 0; i < 4; i++) {
                intentFilter.addAction(strArr[i]);
            }
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart("com.google.android.googlequicksearchbox", 0);
            context.registerReceiver(r8, intentFilter, 2);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.USER_SWITCHED");
            intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
            context.registerReceiver(new UserSwitchReceiver(), intentFilter2);
            context.registerReceiver(new SmartSpaceBroadcastReceiver(this), new IntentFilter("com.google.android.apps.nexuslauncher.UPDATE_SMARTSPACE"), "android.permission.CAPTURE_AUDIO_HOTWORD", this.mUiHandler, 2);
            dumpManager.registerDumpable(SmartSpaceController.class.getName(), this);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println();
        printWriter.println("SmartspaceController");
        StringBuilder sb = new StringBuilder();
        sb.append("  initial broadcast: ");
        StringBuilder m = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb, this.mSmartSpaceEnabledBroadcastSent, printWriter, "  weather ");
        m.append(this.mData.mWeatherCard);
        printWriter.println(m.toString());
        printWriter.println("  current " + this.mData.mCurrentCard);
        printWriter.println("serialized:");
        printWriter.println("  weather " + loadSmartSpaceData(false));
        printWriter.println("  current " + loadSmartSpaceData(true));
        printWriter.println("disabled by experiment: " + isSmartSpaceDisabledByExperiments());
    }

    public final void onExpire(boolean z) {
        Assert.isMainThread();
        this.mAlarmRegistered = false;
        if (this.mData.handleExpire() || z) {
            update();
        } else if (DEBUG) {
            Log.d("SmartSpaceController", "onExpire - cancelled");
        }
    }
}
