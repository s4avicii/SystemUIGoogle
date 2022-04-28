package com.android.systemui.statusbar;

import android.app.ITransientNotificationCallback;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.hardware.biometrics.IBiometricContextListener;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.PromptInfo;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.IUdfpsHbmListener;
import android.media.INearbyMediaDevicesProvider;
import android.media.MediaRoute2Info;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Pair;
import android.util.SparseArray;
import android.view.InsetsVisibilities;
import com.android.internal.os.SomeArgs;
import com.android.internal.statusbar.IAddTileResultCallback;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.internal.util.GcUtils;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.shared.tracing.FrameProtoTracer;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.tracing.ProtoTracer;
import com.android.systemui.tracing.nano.SystemUiTraceEntryProto;
import com.android.systemui.tracing.nano.SystemUiTraceFileProto;
import com.android.systemui.tracing.nano.SystemUiTraceProto;
import com.google.protobuf.nano.MessageNano;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public final class CommandQueue extends IStatusBar.Stub implements CallbackController<Callbacks>, DisplayManager.DisplayListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ArrayList<Callbacks> mCallbacks = new ArrayList<>();
    public SparseArray<Pair<Integer, Integer>> mDisplayDisabled = new SparseArray<>();
    public C1129H mHandler = new C1129H(Looper.getMainLooper());
    public int mLastUpdatedImeDisplayId = -1;
    public final Object mLock = new Object();
    public ProtoTracer mProtoTracer;
    public final CommandRegistry mRegistry;

    public interface Callbacks {
        void abortTransient(int i, int[] iArr) {
        }

        void addQsTile(ComponentName componentName) {
        }

        void animateCollapsePanels(int i, boolean z) {
        }

        void animateExpandNotificationsPanel() {
        }

        void animateExpandSettingsPanel(String str) {
        }

        void appTransitionCancelled(int i) {
        }

        void appTransitionFinished(int i) {
        }

        void appTransitionPending(int i, boolean z) {
        }

        void appTransitionStarting(int i, long j, long j2, boolean z) {
        }

        void cancelPreloadRecentApps() {
        }

        void cancelRequestAddTile(String str) {
        }

        void clickTile(ComponentName componentName) {
        }

        void disable(int i, int i2, int i3, boolean z) {
        }

        void dismissInattentiveSleepWarning(boolean z) {
        }

        void dismissKeyboardShortcutsMenu() {
        }

        void handleShowGlobalActionsMenu() {
        }

        void handleShowShutdownUi(boolean z, String str) {
        }

        void handleSystemKey(int i) {
        }

        void handleWindowManagerLoggingCommand(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) {
        }

        void hideAuthenticationDialog() {
        }

        void hideRecentApps(boolean z, boolean z2) {
        }

        void hideToast(String str, IBinder iBinder) {
        }

        void onBiometricAuthenticated() {
        }

        void onBiometricError(int i, int i2, int i3) {
        }

        void onBiometricHelp(int i, String str) {
        }

        void onCameraLaunchGestureDetected(int i) {
        }

        void onDisplayReady(int i) {
        }

        void onDisplayRemoved(int i) {
        }

        void onEmergencyActionLaunchGestureDetected() {
        }

        void onRecentsAnimationStateChanged(boolean z) {
        }

        void onRotationProposal(int i, boolean z) {
        }

        void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, InsetsVisibilities insetsVisibilities, String str) {
        }

        void onTracingStateChanged(boolean z) {
        }

        void preloadRecentApps() {
        }

        void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        }

        void remQsTile(ComponentName componentName) {
        }

        void removeIcon(String str) {
        }

        void requestAddTile(ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) {
        }

        void requestWindowMagnificationConnection(boolean z) {
        }

        void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) {
        }

        void setIcon(String str, StatusBarIcon statusBarIcon) {
        }

        void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        }

        void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
        }

        void setTopAppHidesStatusBar(boolean z) {
        }

        void setUdfpsHbmListener(IUdfpsHbmListener iUdfpsHbmListener) {
        }

        void setWindowState(int i, int i2, int i3) {
        }

        void showAssistDisclosure() {
        }

        void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2, int i2) {
        }

        void showInattentiveSleepWarning() {
        }

        void showPictureInPictureMenu() {
        }

        void showPinningEnterExitToast(boolean z) {
        }

        void showPinningEscapeToast() {
        }

        void showRecentApps(boolean z) {
        }

        void showScreenPinningRequest(int i) {
        }

        void showToast(int i, String str, IBinder iBinder, CharSequence charSequence, IBinder iBinder2, int i2, ITransientNotificationCallback iTransientNotificationCallback) {
        }

        void showTransient(int i, int[] iArr, boolean z) {
        }

        void showWirelessChargingAnimation(int i) {
        }

        void startAssist(Bundle bundle) {
        }

        void suppressAmbientDisplay(boolean z) {
        }

        void toggleKeyboardShortcutsMenu(int i) {
        }

        void togglePanel() {
        }

        void toggleRecentApps() {
        }

        void toggleSplitScreen() {
        }

        void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        }

        void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) {
        }

        void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) {
        }
    }

    /* renamed from: com.android.systemui.statusbar.CommandQueue$H */
    public final class C1129H extends Handler {
        public C1129H(Looper looper) {
            super(looper);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:113:0x0349, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:114:0x034b, code lost:
            r0.this$0.mCallbacks.get(r4).showPinningEscapeToast();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:116:0x0363, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:117:0x0365, code lost:
            r0.this$0.mCallbacks.get(r4).showPinningEnterExitToast(((java.lang.Boolean) r1.obj).booleanValue());
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x0385, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:120:0x0387, code lost:
            r0.this$0.mCallbacks.get(r4).showWirelessChargingAnimation(r1.arg1);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:122:0x03a1, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:123:0x03a3, code lost:
            r0.this$0.mCallbacks.get(r4).hideAuthenticationDialog();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:135:0x040d, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:136:0x040f, code lost:
            r0.this$0.mCallbacks.get(r4).onBiometricAuthenticated();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:167:0x0501, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:168:0x0503, code lost:
            r0.this$0.mCallbacks.get(r4).togglePanel();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:170:0x051b, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:171:0x051d, code lost:
            r0.this$0.mCallbacks.get(r4).handleShowGlobalActionsMenu();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:173:0x0535, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:174:0x0537, code lost:
            r0.this$0.mCallbacks.get(r4).handleSystemKey(r1.arg1);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:176:0x0551, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:177:0x0553, code lost:
            r0.this$0.mCallbacks.get(r4).dismissKeyboardShortcutsMenu();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:179:0x056b, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:180:0x056d, code lost:
            r0.this$0.mCallbacks.get(r4).appTransitionFinished(r1.arg1);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:182:0x0587, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:183:0x0589, code lost:
            r0.this$0.mCallbacks.get(r4).toggleSplitScreen();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:185:0x05a1, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:186:0x05a3, code lost:
            r0.this$0.mCallbacks.get(r4).clickTile((android.content.ComponentName) r1.obj);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:188:0x05bf, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:189:0x05c1, code lost:
            r0.this$0.mCallbacks.get(r4).remQsTile((android.content.ComponentName) r1.obj);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:191:0x05dd, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:192:0x05df, code lost:
            r0.this$0.mCallbacks.get(r4).addQsTile((android.content.ComponentName) r1.obj);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:194:0x05fb, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:195:0x05fd, code lost:
            r0.this$0.mCallbacks.get(r4).showPictureInPictureMenu();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:197:0x0615, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:198:0x0617, code lost:
            r0.this$0.mCallbacks.get(r4).toggleKeyboardShortcutsMenu(r1.arg1);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:200:0x0631, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:201:0x0633, code lost:
            r0.this$0.mCallbacks.get(r4).onCameraLaunchGestureDetected(r1.arg1);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:203:0x064d, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:204:0x064f, code lost:
            r0.this$0.mCallbacks.get(r4).startAssist((android.os.Bundle) r1.obj);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:206:0x066b, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:207:0x066d, code lost:
            r0.this$0.mCallbacks.get(r4).showAssistDisclosure();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:217:0x06be, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:218:0x06c0, code lost:
            r0.this$0.mCallbacks.get(r4).appTransitionCancelled(r1.arg1);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x00be, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:228:0x06fe, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:229:0x0700, code lost:
            r0.this$0.mCallbacks.get(r4).showScreenPinningRequest(r1.arg1);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x00c0, code lost:
            r0.this$0.mCallbacks.get(r4).setBiometicContextListener((android.hardware.biometrics.IBiometricContextListener) r1.obj);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:251:0x0765, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:252:0x0767, code lost:
            r0.this$0.mCallbacks.get(r4).setWindowState(r1.arg1, r1.arg2, ((java.lang.Integer) r1.obj).intValue());
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:254:0x078b, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:255:0x078d, code lost:
            r0.this$0.mCallbacks.get(r4).cancelPreloadRecentApps();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:257:0x07a5, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:258:0x07a7, code lost:
            r0.this$0.mCallbacks.get(r4).preloadRecentApps();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:260:0x07bf, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:261:0x07c1, code lost:
            r0.this$0.mCallbacks.get(r4).toggleRecentApps();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:280:0x0840, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:281:0x0842, code lost:
            r0.this$0.mCallbacks.get(r4).onDisplayReady(r1.arg1);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:292:0x089d, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:293:0x089f, code lost:
            r0.this$0.mCallbacks.get(r4).animateExpandSettingsPanel((java.lang.String) r1.obj);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:303:0x08df, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:304:0x08e1, code lost:
            r0.this$0.mCallbacks.get(r4).animateExpandNotificationsPanel();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0139, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x013b, code lost:
            r0.this$0.mCallbacks.get(r4).setUdfpsHbmListener((android.hardware.fingerprint.IUdfpsHbmListener) r1.obj);
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:430:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:432:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:434:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:435:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:437:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:440:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:441:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:445:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:446:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:447:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:448:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:449:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x017b, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:453:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:454:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:455:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:456:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:457:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:458:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:459:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x017d, code lost:
            r0.this$0.mCallbacks.get(r4).onEmergencyActionLaunchGestureDetected();
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:460:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:461:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:462:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:463:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:464:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:465:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:466:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:468:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:470:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:473:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:474:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:475:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:476:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:477:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:478:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:480:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:71:0x01de, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:72:0x01e0, code lost:
            r0.this$0.mCallbacks.get(r4).requestWindowMagnificationConnection(((java.lang.Boolean) r1.obj).booleanValue());
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:78:0x0220, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:79:0x0222, code lost:
            r0.this$0.mCallbacks.get(r4).onTracingStateChanged(((java.lang.Boolean) r1.obj).booleanValue());
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:89:0x02a5, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:90:0x02a7, code lost:
            r0.this$0.mCallbacks.get(r4).dismissInattentiveSleepWarning(((java.lang.Boolean) r1.obj).booleanValue());
            r4 = r4 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:92:0x02c7, code lost:
            if (r4 >= r0.this$0.mCallbacks.size()) goto L_0x0969;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:93:0x02c9, code lost:
            r0.this$0.mCallbacks.get(r4).showInattentiveSleepWarning();
            r4 = r4 + 1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void handleMessage(android.os.Message r21) {
            /*
                r20 = this;
                r0 = r20
                r1 = r21
                int r2 = r1.what
                r3 = -65536(0xffffffffffff0000, float:NaN)
                r2 = r2 & r3
                r3 = 1
                r4 = 0
                switch(r2) {
                    case 65536: goto L_0x091d;
                    case 131072: goto L_0x08f1;
                    case 196608: goto L_0x08d7;
                    case 262144: goto L_0x08b3;
                    case 327680: goto L_0x0895;
                    case 393216: goto L_0x0854;
                    case 458752: goto L_0x0838;
                    case 524288: goto L_0x07d1;
                    case 589824: goto L_0x07b7;
                    case 655360: goto L_0x079d;
                    case 720896: goto L_0x0783;
                    case 786432: goto L_0x075d;
                    case 851968: goto L_0x073b;
                    case 917504: goto L_0x0712;
                    case 1179648: goto L_0x06f6;
                    case 1245184: goto L_0x06d2;
                    case 1310720: goto L_0x06b6;
                    case 1376256: goto L_0x067d;
                    case 1441792: goto L_0x0663;
                    case 1507328: goto L_0x0645;
                    case 1572864: goto L_0x0629;
                    case 1638400: goto L_0x060d;
                    case 1703936: goto L_0x05f3;
                    case 1769472: goto L_0x05d5;
                    case 1835008: goto L_0x05b7;
                    case 1900544: goto L_0x0599;
                    case 1966080: goto L_0x057f;
                    case 2031616: goto L_0x0563;
                    case 2097152: goto L_0x0549;
                    case 2162688: goto L_0x052d;
                    case 2228224: goto L_0x0513;
                    case 2293760: goto L_0x04f9;
                    case 2359296: goto L_0x04d3;
                    case 2424832: goto L_0x04b1;
                    case 2490368: goto L_0x048d;
                    case 2555904: goto L_0x041f;
                    case 2621440: goto L_0x0405;
                    case 2686976: goto L_0x03dc;
                    case 2752512: goto L_0x03b3;
                    case 2818048: goto L_0x0399;
                    case 2883584: goto L_0x037d;
                    case 2949120: goto L_0x035b;
                    case 3014656: goto L_0x0341;
                    case 3080192: goto L_0x031f;
                    case 3145728: goto L_0x02f9;
                    case 3211264: goto L_0x02d9;
                    case 3276800: goto L_0x02bf;
                    case 3342336: goto L_0x029d;
                    case 3407872: goto L_0x025e;
                    case 3473408: goto L_0x023a;
                    case 3538944: goto L_0x0218;
                    case 3604480: goto L_0x01f8;
                    case 3670016: goto L_0x01d6;
                    case 3735552: goto L_0x018d;
                    case 3801088: goto L_0x0173;
                    case 3866624: goto L_0x014f;
                    case 3932160: goto L_0x0131;
                    case 3997696: goto L_0x00f2;
                    case 4063232: goto L_0x00d4;
                    case 4128768: goto L_0x00b6;
                    case 4194304: goto L_0x0083;
                    case 4259840: goto L_0x004c;
                    case 4325376: goto L_0x002e;
                    case 4390912: goto L_0x0010;
                    default: goto L_0x000e;
                }
            L_0x000e:
                goto L_0x0969
            L_0x0010:
                java.lang.Object r1 = r1.obj
                android.media.INearbyMediaDevicesProvider r1 = (android.media.INearbyMediaDevicesProvider) r1
            L_0x0014:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                r2.unregisterNearbyMediaDevicesProvider(r1)
                int r4 = r4 + 1
                goto L_0x0014
            L_0x002e:
                java.lang.Object r1 = r1.obj
                android.media.INearbyMediaDevicesProvider r1 = (android.media.INearbyMediaDevicesProvider) r1
            L_0x0032:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                r2.registerNearbyMediaDevicesProvider(r1)
                int r4 = r4 + 1
                goto L_0x0032
            L_0x004c:
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
                java.lang.Object r2 = r1.arg1
                java.lang.Integer r2 = (java.lang.Integer) r2
                int r2 = r2.intValue()
                java.lang.Object r3 = r1.arg2
                android.media.MediaRoute2Info r3 = (android.media.MediaRoute2Info) r3
                java.lang.Object r5 = r1.arg3
                android.graphics.drawable.Icon r5 = (android.graphics.drawable.Icon) r5
                java.lang.Object r6 = r1.arg4
                java.lang.CharSequence r6 = (java.lang.CharSequence) r6
            L_0x0064:
                com.android.systemui.statusbar.CommandQueue r7 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r7 = r7.mCallbacks
                int r7 = r7.size()
                if (r4 >= r7) goto L_0x007e
                com.android.systemui.statusbar.CommandQueue r7 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r7 = r7.mCallbacks
                java.lang.Object r7 = r7.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r7 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r7
                r7.updateMediaTapToTransferReceiverDisplay(r2, r3, r5, r6)
                int r4 = r4 + 1
                goto L_0x0064
            L_0x007e:
                r1.recycle()
                goto L_0x0969
            L_0x0083:
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
                java.lang.Object r2 = r1.arg1
                java.lang.Integer r2 = (java.lang.Integer) r2
                int r2 = r2.intValue()
                java.lang.Object r3 = r1.arg2
                android.media.MediaRoute2Info r3 = (android.media.MediaRoute2Info) r3
                java.lang.Object r5 = r1.arg3
                com.android.internal.statusbar.IUndoMediaTransferCallback r5 = (com.android.internal.statusbar.IUndoMediaTransferCallback) r5
            L_0x0097:
                com.android.systemui.statusbar.CommandQueue r6 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r6 = r6.mCallbacks
                int r6 = r6.size()
                if (r4 >= r6) goto L_0x00b1
                com.android.systemui.statusbar.CommandQueue r6 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r6 = r6.mCallbacks
                java.lang.Object r6 = r6.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r6 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r6
                r6.updateMediaTapToTransferSenderDisplay(r2, r3, r5)
                int r4 = r4 + 1
                goto L_0x0097
            L_0x00b1:
                r1.recycle()
                goto L_0x0969
            L_0x00b6:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                android.hardware.biometrics.IBiometricContextListener r3 = (android.hardware.biometrics.IBiometricContextListener) r3
                r2.setBiometicContextListener(r3)
                int r4 = r4 + 1
                goto L_0x00b6
            L_0x00d4:
                java.lang.Object r1 = r1.obj
                java.lang.String r1 = (java.lang.String) r1
            L_0x00d8:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                r2.cancelRequestAddTile(r1)
                int r4 = r4 + 1
                goto L_0x00d8
            L_0x00f2:
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
                java.lang.Object r2 = r1.arg1
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                java.lang.Object r3 = r1.arg2
                java.lang.CharSequence r3 = (java.lang.CharSequence) r3
                java.lang.Object r5 = r1.arg3
                r11 = r5
                java.lang.CharSequence r11 = (java.lang.CharSequence) r11
                java.lang.Object r5 = r1.arg4
                r12 = r5
                android.graphics.drawable.Icon r12 = (android.graphics.drawable.Icon) r12
                java.lang.Object r5 = r1.arg5
                r13 = r5
                com.android.internal.statusbar.IAddTileResultCallback r13 = (com.android.internal.statusbar.IAddTileResultCallback) r13
            L_0x010d:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r4 >= r5) goto L_0x012c
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                r6 = r2
                r7 = r3
                r8 = r11
                r9 = r12
                r10 = r13
                r5.requestAddTile(r6, r7, r8, r9, r10)
                int r4 = r4 + 1
                goto L_0x010d
            L_0x012c:
                r1.recycle()
                goto L_0x0969
            L_0x0131:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                android.hardware.fingerprint.IUdfpsHbmListener r3 = (android.hardware.fingerprint.IUdfpsHbmListener) r3
                r2.setUdfpsHbmListener(r3)
                int r4 = r4 + 1
                goto L_0x0131
            L_0x014f:
                r2 = r4
            L_0x0150:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r2 >= r5) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r2)
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                int r6 = r1.arg1
                int r7 = r1.arg2
                if (r7 == 0) goto L_0x016c
                r7 = r3
                goto L_0x016d
            L_0x016c:
                r7 = r4
            L_0x016d:
                r5.setNavigationBarLumaSamplingEnabled(r6, r7)
                int r2 = r2 + 1
                goto L_0x0150
            L_0x0173:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.onEmergencyActionLaunchGestureDetected()
                int r4 = r4 + 1
                goto L_0x0173
            L_0x018d:
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
                java.lang.Object r2 = r1.arg2     // Catch:{ IOException -> 0x01c7 }
                android.os.ParcelFileDescriptor r2 = (android.os.ParcelFileDescriptor) r2     // Catch:{ IOException -> 0x01c7 }
            L_0x0195:
                com.android.systemui.statusbar.CommandQueue r3 = com.android.systemui.statusbar.CommandQueue.this     // Catch:{ all -> 0x01b9 }
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r3 = r3.mCallbacks     // Catch:{ all -> 0x01b9 }
                int r3 = r3.size()     // Catch:{ all -> 0x01b9 }
                if (r4 >= r3) goto L_0x01b3
                com.android.systemui.statusbar.CommandQueue r3 = com.android.systemui.statusbar.CommandQueue.this     // Catch:{ all -> 0x01b9 }
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r3 = r3.mCallbacks     // Catch:{ all -> 0x01b9 }
                java.lang.Object r3 = r3.get(r4)     // Catch:{ all -> 0x01b9 }
                com.android.systemui.statusbar.CommandQueue$Callbacks r3 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r3     // Catch:{ all -> 0x01b9 }
                java.lang.Object r5 = r1.arg1     // Catch:{ all -> 0x01b9 }
                java.lang.String[] r5 = (java.lang.String[]) r5     // Catch:{ all -> 0x01b9 }
                r3.handleWindowManagerLoggingCommand(r5, r2)     // Catch:{ all -> 0x01b9 }
                int r4 = r4 + 1
                goto L_0x0195
            L_0x01b3:
                if (r2 == 0) goto L_0x01d1
                r2.close()     // Catch:{ IOException -> 0x01c7 }
                goto L_0x01d1
            L_0x01b9:
                r0 = move-exception
                r3 = r0
                if (r2 == 0) goto L_0x01c6
                r2.close()     // Catch:{ all -> 0x01c1 }
                goto L_0x01c6
            L_0x01c1:
                r0 = move-exception
                r2 = r0
                r3.addSuppressed(r2)     // Catch:{ IOException -> 0x01c7 }
            L_0x01c6:
                throw r3     // Catch:{ IOException -> 0x01c7 }
            L_0x01c7:
                r0 = move-exception
                int r2 = com.android.systemui.statusbar.CommandQueue.$r8$clinit
                java.lang.String r2 = "CommandQueue"
                java.lang.String r3 = "Failed to handle logging command"
                android.util.Log.e(r2, r3, r0)
            L_0x01d1:
                r1.recycle()
                goto L_0x0969
            L_0x01d6:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                java.lang.Boolean r3 = (java.lang.Boolean) r3
                boolean r3 = r3.booleanValue()
                r2.requestWindowMagnificationConnection(r3)
                int r4 = r4 + 1
                goto L_0x01d6
            L_0x01f8:
                com.android.systemui.statusbar.CommandQueue r0 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r0 = r0.mCallbacks
                java.util.Iterator r0 = r0.iterator()
            L_0x0200:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto L_0x0969
                java.lang.Object r2 = r0.next()
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                java.lang.Boolean r3 = (java.lang.Boolean) r3
                boolean r3 = r3.booleanValue()
                r2.suppressAmbientDisplay(r3)
                goto L_0x0200
            L_0x0218:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                java.lang.Boolean r3 = (java.lang.Boolean) r3
                boolean r3 = r3.booleanValue()
                r2.onTracingStateChanged(r3)
                int r4 = r4 + 1
                goto L_0x0218
            L_0x023a:
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
                java.lang.Object r2 = r1.arg1
                java.lang.String r2 = (java.lang.String) r2
                java.lang.Object r1 = r1.arg2
                android.os.IBinder r1 = (android.os.IBinder) r1
                com.android.systemui.statusbar.CommandQueue r0 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r0 = r0.mCallbacks
                java.util.Iterator r0 = r0.iterator()
            L_0x024e:
                boolean r3 = r0.hasNext()
                if (r3 == 0) goto L_0x0969
                java.lang.Object r3 = r0.next()
                com.android.systemui.statusbar.CommandQueue$Callbacks r3 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r3
                r3.hideToast(r2, r1)
                goto L_0x024e
            L_0x025e:
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
                java.lang.Object r2 = r1.arg1
                java.lang.String r2 = (java.lang.String) r2
                java.lang.Object r3 = r1.arg2
                r11 = r3
                android.os.IBinder r11 = (android.os.IBinder) r11
                java.lang.Object r3 = r1.arg3
                r12 = r3
                java.lang.CharSequence r12 = (java.lang.CharSequence) r12
                java.lang.Object r3 = r1.arg4
                r13 = r3
                android.os.IBinder r13 = (android.os.IBinder) r13
                java.lang.Object r3 = r1.arg5
                r14 = r3
                android.app.ITransientNotificationCallback r14 = (android.app.ITransientNotificationCallback) r14
                int r15 = r1.argi1
                int r1 = r1.argi2
                com.android.systemui.statusbar.CommandQueue r0 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r0 = r0.mCallbacks
                java.util.Iterator r0 = r0.iterator()
            L_0x0286:
                boolean r3 = r0.hasNext()
                if (r3 == 0) goto L_0x0969
                java.lang.Object r3 = r0.next()
                com.android.systemui.statusbar.CommandQueue$Callbacks r3 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r3
                r4 = r15
                r5 = r2
                r6 = r11
                r7 = r12
                r8 = r13
                r9 = r1
                r10 = r14
                r3.showToast(r4, r5, r6, r7, r8, r9, r10)
                goto L_0x0286
            L_0x029d:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                java.lang.Boolean r3 = (java.lang.Boolean) r3
                boolean r3 = r3.booleanValue()
                r2.dismissInattentiveSleepWarning(r3)
                int r4 = r4 + 1
                goto L_0x029d
            L_0x02bf:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.showInattentiveSleepWarning()
                int r4 = r4 + 1
                goto L_0x02bf
            L_0x02d9:
                int r2 = r1.arg1
                java.lang.Object r1 = r1.obj
                int[] r1 = (int[]) r1
            L_0x02df:
                com.android.systemui.statusbar.CommandQueue r3 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r3 = r3.mCallbacks
                int r3 = r3.size()
                if (r4 >= r3) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r3 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r3 = r3.mCallbacks
                java.lang.Object r3 = r3.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r3 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r3
                r3.abortTransient(r2, r1)
                int r4 = r4 + 1
                goto L_0x02df
            L_0x02f9:
                int r2 = r1.arg1
                java.lang.Object r5 = r1.obj
                int[] r5 = (int[]) r5
                int r1 = r1.arg2
                if (r1 == 0) goto L_0x0304
                goto L_0x0305
            L_0x0304:
                r3 = r4
            L_0x0305:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.showTransient(r2, r5, r3)
                int r4 = r4 + 1
                goto L_0x0305
            L_0x031f:
                r2 = r4
            L_0x0320:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r2 >= r5) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r2)
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                int r6 = r1.arg1
                if (r6 <= 0) goto L_0x033a
                r6 = r3
                goto L_0x033b
            L_0x033a:
                r6 = r4
            L_0x033b:
                r5.onRecentsAnimationStateChanged(r6)
                int r2 = r2 + 1
                goto L_0x0320
            L_0x0341:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.showPinningEscapeToast()
                int r4 = r4 + 1
                goto L_0x0341
            L_0x035b:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                java.lang.Boolean r3 = (java.lang.Boolean) r3
                boolean r3 = r3.booleanValue()
                r2.showPinningEnterExitToast(r3)
                int r4 = r4 + 1
                goto L_0x035b
            L_0x037d:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                int r3 = r1.arg1
                r2.showWirelessChargingAnimation(r3)
                int r4 = r4 + 1
                goto L_0x037d
            L_0x0399:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.hideAuthenticationDialog()
                int r4 = r4 + 1
                goto L_0x0399
            L_0x03b3:
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
            L_0x03b7:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x03d7
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                int r3 = r1.argi1
                int r5 = r1.argi2
                int r6 = r1.argi3
                r2.onBiometricError(r3, r5, r6)
                int r4 = r4 + 1
                goto L_0x03b7
            L_0x03d7:
                r1.recycle()
                goto L_0x0969
            L_0x03dc:
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
            L_0x03e0:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0400
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                int r3 = r1.argi1
                java.lang.Object r5 = r1.arg1
                java.lang.String r5 = (java.lang.String) r5
                r2.onBiometricHelp(r3, r5)
                int r4 = r4 + 1
                goto L_0x03e0
            L_0x0400:
                r1.recycle()
                goto L_0x0969
            L_0x0405:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.onBiometricAuthenticated()
                int r4 = r4 + 1
                goto L_0x0405
            L_0x041f:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                com.android.systemui.statusbar.CommandQueue$H r2 = r2.mHandler
                r3 = 2752512(0x2a0000, float:3.857091E-39)
                r2.removeMessages(r3)
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                com.android.systemui.statusbar.CommandQueue$H r2 = r2.mHandler
                r3 = 2686976(0x290000, float:3.765255E-39)
                r2.removeMessages(r3)
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                com.android.systemui.statusbar.CommandQueue$H r2 = r2.mHandler
                r3 = 2621440(0x280000, float:3.67342E-39)
                r2.removeMessages(r3)
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
            L_0x043e:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0488
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                r5 = r2
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                java.lang.Object r2 = r1.arg1
                r6 = r2
                android.hardware.biometrics.PromptInfo r6 = (android.hardware.biometrics.PromptInfo) r6
                java.lang.Object r2 = r1.arg2
                r7 = r2
                android.hardware.biometrics.IBiometricSysuiReceiver r7 = (android.hardware.biometrics.IBiometricSysuiReceiver) r7
                java.lang.Object r2 = r1.arg3
                r8 = r2
                int[] r8 = (int[]) r8
                java.lang.Object r2 = r1.arg4
                java.lang.Boolean r2 = (java.lang.Boolean) r2
                boolean r9 = r2.booleanValue()
                java.lang.Object r2 = r1.arg5
                java.lang.Boolean r2 = (java.lang.Boolean) r2
                boolean r10 = r2.booleanValue()
                int r11 = r1.argi1
                long r12 = r1.argl1
                java.lang.Object r2 = r1.arg6
                r14 = r2
                java.lang.String r14 = (java.lang.String) r14
                long r2 = r1.argl2
                int r15 = r1.argi2
                r17 = r15
                r15 = r2
                r5.showAuthenticationDialog(r6, r7, r8, r9, r10, r11, r12, r14, r15, r17)
                int r4 = r4 + 1
                goto L_0x043e
            L_0x0488:
                r1.recycle()
                goto L_0x0969
            L_0x048d:
                r2 = r4
            L_0x048e:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r2 >= r5) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r2)
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                int r6 = r1.arg1
                int r7 = r1.arg2
                if (r7 == 0) goto L_0x04aa
                r7 = r3
                goto L_0x04ab
            L_0x04aa:
                r7 = r4
            L_0x04ab:
                r5.onRotationProposal(r6, r7)
                int r2 = r2 + 1
                goto L_0x048e
            L_0x04b1:
                r2 = r4
            L_0x04b2:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r2 >= r5) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r2)
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                int r6 = r1.arg1
                if (r6 == 0) goto L_0x04cc
                r6 = r3
                goto L_0x04cd
            L_0x04cc:
                r6 = r4
            L_0x04cd:
                r5.setTopAppHidesStatusBar(r6)
                int r2 = r2 + 1
                goto L_0x04b2
            L_0x04d3:
                r2 = r4
            L_0x04d4:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r2 >= r5) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r2)
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                int r6 = r1.arg1
                if (r6 == 0) goto L_0x04ee
                r6 = r3
                goto L_0x04ef
            L_0x04ee:
                r6 = r4
            L_0x04ef:
                java.lang.Object r7 = r1.obj
                java.lang.String r7 = (java.lang.String) r7
                r5.handleShowShutdownUi(r6, r7)
                int r2 = r2 + 1
                goto L_0x04d4
            L_0x04f9:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.togglePanel()
                int r4 = r4 + 1
                goto L_0x04f9
            L_0x0513:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.handleShowGlobalActionsMenu()
                int r4 = r4 + 1
                goto L_0x0513
            L_0x052d:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                int r3 = r1.arg1
                r2.handleSystemKey(r3)
                int r4 = r4 + 1
                goto L_0x052d
            L_0x0549:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.dismissKeyboardShortcutsMenu()
                int r4 = r4 + 1
                goto L_0x0549
            L_0x0563:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                int r3 = r1.arg1
                r2.appTransitionFinished(r3)
                int r4 = r4 + 1
                goto L_0x0563
            L_0x057f:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.toggleSplitScreen()
                int r4 = r4 + 1
                goto L_0x057f
            L_0x0599:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                android.content.ComponentName r3 = (android.content.ComponentName) r3
                r2.clickTile(r3)
                int r4 = r4 + 1
                goto L_0x0599
            L_0x05b7:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                android.content.ComponentName r3 = (android.content.ComponentName) r3
                r2.remQsTile(r3)
                int r4 = r4 + 1
                goto L_0x05b7
            L_0x05d5:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                android.content.ComponentName r3 = (android.content.ComponentName) r3
                r2.addQsTile(r3)
                int r4 = r4 + 1
                goto L_0x05d5
            L_0x05f3:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.showPictureInPictureMenu()
                int r4 = r4 + 1
                goto L_0x05f3
            L_0x060d:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                int r3 = r1.arg1
                r2.toggleKeyboardShortcutsMenu(r3)
                int r4 = r4 + 1
                goto L_0x060d
            L_0x0629:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                int r3 = r1.arg1
                r2.onCameraLaunchGestureDetected(r3)
                int r4 = r4 + 1
                goto L_0x0629
            L_0x0645:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                android.os.Bundle r3 = (android.os.Bundle) r3
                r2.startAssist(r3)
                int r4 = r4 + 1
                goto L_0x0645
            L_0x0663:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.showAssistDisclosure()
                int r4 = r4 + 1
                goto L_0x0663
            L_0x067d:
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
                r2 = r4
            L_0x0682:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r2 >= r5) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r2)
                r6 = r5
                com.android.systemui.statusbar.CommandQueue$Callbacks r6 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r6
                int r7 = r1.argi1
                java.lang.Object r5 = r1.arg1
                java.lang.Long r5 = (java.lang.Long) r5
                long r8 = r5.longValue()
                java.lang.Object r5 = r1.arg2
                java.lang.Long r5 = (java.lang.Long) r5
                long r10 = r5.longValue()
                int r5 = r1.argi2
                if (r5 == 0) goto L_0x06af
                r12 = r3
                goto L_0x06b0
            L_0x06af:
                r12 = r4
            L_0x06b0:
                r6.appTransitionStarting(r7, r8, r10, r12)
                int r2 = r2 + 1
                goto L_0x0682
            L_0x06b6:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                int r3 = r1.arg1
                r2.appTransitionCancelled(r3)
                int r4 = r4 + 1
                goto L_0x06b6
            L_0x06d2:
                r2 = r4
            L_0x06d3:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r2 >= r5) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r2)
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                int r6 = r1.arg1
                int r7 = r1.arg2
                if (r7 == 0) goto L_0x06ef
                r7 = r3
                goto L_0x06f0
            L_0x06ef:
                r7 = r4
            L_0x06f0:
                r5.appTransitionPending(r6, r7)
                int r2 = r2 + 1
                goto L_0x06d3
            L_0x06f6:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                int r3 = r1.arg1
                r2.showScreenPinningRequest(r3)
                int r4 = r4 + 1
                goto L_0x06f6
            L_0x0712:
                r2 = r4
            L_0x0713:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r2 >= r5) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r2)
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                int r6 = r1.arg1
                if (r6 == 0) goto L_0x072d
                r6 = r3
                goto L_0x072e
            L_0x072d:
                r6 = r4
            L_0x072e:
                int r7 = r1.arg2
                if (r7 == 0) goto L_0x0734
                r7 = r3
                goto L_0x0735
            L_0x0734:
                r7 = r4
            L_0x0735:
                r5.hideRecentApps(r6, r7)
                int r2 = r2 + 1
                goto L_0x0713
            L_0x073b:
                r2 = r4
            L_0x073c:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r2 >= r5) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r2)
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                int r6 = r1.arg1
                if (r6 == 0) goto L_0x0756
                r6 = r3
                goto L_0x0757
            L_0x0756:
                r6 = r4
            L_0x0757:
                r5.showRecentApps(r6)
                int r2 = r2 + 1
                goto L_0x073c
            L_0x075d:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                int r3 = r1.arg1
                int r5 = r1.arg2
                java.lang.Object r6 = r1.obj
                java.lang.Integer r6 = (java.lang.Integer) r6
                int r6 = r6.intValue()
                r2.setWindowState(r3, r5, r6)
                int r4 = r4 + 1
                goto L_0x075d
            L_0x0783:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.cancelPreloadRecentApps()
                int r4 = r4 + 1
                goto L_0x0783
            L_0x079d:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.preloadRecentApps()
                int r4 = r4 + 1
                goto L_0x079d
            L_0x07b7:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.toggleRecentApps()
                int r4 = r4 + 1
                goto L_0x07b7
            L_0x07d1:
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
                com.android.systemui.statusbar.CommandQueue r0 = com.android.systemui.statusbar.CommandQueue.this
                int r2 = r1.argi1
                java.lang.Object r5 = r1.arg1
                r11 = r5
                android.os.IBinder r11 = (android.os.IBinder) r11
                int r12 = r1.argi2
                int r13 = r1.argi3
                int r1 = r1.argi4
                if (r1 == 0) goto L_0x07e7
                goto L_0x07e8
            L_0x07e7:
                r3 = r4
            L_0x07e8:
                r1 = -1
                if (r2 != r1) goto L_0x07f0
                java.util.Objects.requireNonNull(r0)
                goto L_0x0969
            L_0x07f0:
                int r5 = r0.mLastUpdatedImeDisplayId
                if (r5 == r2) goto L_0x0818
                if (r5 == r1) goto L_0x0818
                r1 = r4
            L_0x07f7:
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r0.mCallbacks
                int r5 = r5.size()
                if (r1 >= r5) goto L_0x0818
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r0.mCallbacks
                java.lang.Object r5 = r5.get(r1)
                r14 = r5
                com.android.systemui.statusbar.CommandQueue$Callbacks r14 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r14
                int r15 = r0.mLastUpdatedImeDisplayId
                r16 = 0
                r17 = 4
                r18 = 0
                r19 = 0
                r14.setImeWindowStatus(r15, r16, r17, r18, r19)
                int r1 = r1 + 1
                goto L_0x07f7
            L_0x0818:
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r0.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0834
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r0.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                r5 = r1
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                r6 = r2
                r7 = r11
                r8 = r12
                r9 = r13
                r10 = r3
                r5.setImeWindowStatus(r6, r7, r8, r9, r10)
                int r4 = r4 + 1
                goto L_0x0818
            L_0x0834:
                r0.mLastUpdatedImeDisplayId = r2
                goto L_0x0969
            L_0x0838:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                int r3 = r1.arg1
                r2.onDisplayReady(r3)
                int r4 = r4 + 1
                goto L_0x0838
            L_0x0854:
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
                r2 = r4
            L_0x0859:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r2 >= r5) goto L_0x0890
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r2)
                r6 = r5
                com.android.systemui.statusbar.CommandQueue$Callbacks r6 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r6
                int r7 = r1.argi1
                int r8 = r1.argi2
                java.lang.Object r5 = r1.arg1
                r9 = r5
                com.android.internal.view.AppearanceRegion[] r9 = (com.android.internal.view.AppearanceRegion[]) r9
                int r5 = r1.argi3
                if (r5 != r3) goto L_0x087d
                r10 = r3
                goto L_0x087e
            L_0x087d:
                r10 = r4
            L_0x087e:
                int r11 = r1.argi4
                java.lang.Object r5 = r1.arg2
                r12 = r5
                android.view.InsetsVisibilities r12 = (android.view.InsetsVisibilities) r12
                java.lang.Object r5 = r1.arg3
                r13 = r5
                java.lang.String r13 = (java.lang.String) r13
                r6.onSystemBarAttributesChanged(r7, r8, r9, r10, r11, r12, r13)
                int r2 = r2 + 1
                goto L_0x0859
            L_0x0890:
                r1.recycle()
                goto L_0x0969
            L_0x0895:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                java.lang.String r3 = (java.lang.String) r3
                r2.animateExpandSettingsPanel(r3)
                int r4 = r4 + 1
                goto L_0x0895
            L_0x08b3:
                r2 = r4
            L_0x08b4:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r2 >= r5) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r2)
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                int r6 = r1.arg1
                int r7 = r1.arg2
                if (r7 == 0) goto L_0x08d0
                r7 = r3
                goto L_0x08d1
            L_0x08d0:
                r7 = r4
            L_0x08d1:
                r5.animateCollapsePanels(r6, r7)
                int r2 = r2 + 1
                goto L_0x08b4
            L_0x08d7:
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                int r1 = r1.size()
                if (r4 >= r1) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r1 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r1 = r1.mCallbacks
                java.lang.Object r1 = r1.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r1 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r1
                r1.animateExpandNotificationsPanel()
                int r4 = r4 + 1
                goto L_0x08d7
            L_0x08f1:
                java.lang.Object r1 = r1.obj
                com.android.internal.os.SomeArgs r1 = (com.android.internal.os.SomeArgs) r1
                r2 = r4
            L_0x08f6:
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                int r5 = r5.size()
                if (r2 >= r5) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r5 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r5 = r5.mCallbacks
                java.lang.Object r5 = r5.get(r2)
                com.android.systemui.statusbar.CommandQueue$Callbacks r5 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r5
                int r6 = r1.argi1
                int r7 = r1.argi2
                int r8 = r1.argi3
                int r9 = r1.argi4
                if (r9 == 0) goto L_0x0916
                r9 = r3
                goto L_0x0917
            L_0x0916:
                r9 = r4
            L_0x0917:
                r5.disable(r6, r7, r8, r9)
                int r2 = r2 + 1
                goto L_0x08f6
            L_0x091d:
                int r2 = r1.arg1
                if (r2 == r3) goto L_0x0943
                r3 = 2
                if (r2 == r3) goto L_0x0925
                goto L_0x0969
            L_0x0925:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.obj
                java.lang.String r3 = (java.lang.String) r3
                r2.removeIcon(r3)
                int r4 = r4 + 1
                goto L_0x0925
            L_0x0943:
                java.lang.Object r1 = r1.obj
                android.util.Pair r1 = (android.util.Pair) r1
            L_0x0947:
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                int r2 = r2.size()
                if (r4 >= r2) goto L_0x0969
                com.android.systemui.statusbar.CommandQueue r2 = com.android.systemui.statusbar.CommandQueue.this
                java.util.ArrayList<com.android.systemui.statusbar.CommandQueue$Callbacks> r2 = r2.mCallbacks
                java.lang.Object r2 = r2.get(r4)
                com.android.systemui.statusbar.CommandQueue$Callbacks r2 = (com.android.systemui.statusbar.CommandQueue.Callbacks) r2
                java.lang.Object r3 = r1.first
                java.lang.String r3 = (java.lang.String) r3
                java.lang.Object r5 = r1.second
                com.android.internal.statusbar.StatusBarIcon r5 = (com.android.internal.statusbar.StatusBarIcon) r5
                r2.setIcon(r3, r5)
                int r4 = r4 + 1
                goto L_0x0947
            L_0x0969:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.CommandQueue.C1129H.handleMessage(android.os.Message):void");
        }
    }

    public final void animateCollapsePanels() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(262144);
            this.mHandler.obtainMessage(262144, 0, 0).sendToTarget();
        }
    }

    public final void appTransitionStarting(int i, long j, long j2) {
        appTransitionStarting(i, j, j2, false);
    }

    public final void disable(int i, int i2, int i3, boolean z) {
        synchronized (this.mLock) {
            this.mDisplayDisabled.put(i, new Pair(Integer.valueOf(i2), Integer.valueOf(i3)));
            this.mHandler.removeMessages(131072);
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.argi3 = i3;
            obtain.argi4 = z ? 1 : 0;
            Message obtainMessage = this.mHandler.obtainMessage(131072, obtain);
            if (Looper.myLooper() == this.mHandler.getLooper()) {
                this.mHandler.handleMessage(obtainMessage);
                obtainMessage.recycle();
            } else {
                obtainMessage.sendToTarget();
            }
        }
    }

    public final void onDisplayAdded(int i) {
    }

    public final void onDisplayChanged(int i) {
    }

    public final boolean panelsEnabled() {
        return (((Integer) getDisabled(0).first).intValue() & 65536) == 0 && (((Integer) getDisabled(0).second).intValue() & 4) == 0 && !StatusBar.ONLY_CORE_APPS;
    }

    public final void abortTransient(int i, int[] iArr) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3211264, i, 0, iArr).sendToTarget();
        }
    }

    public final void addCallback(Callbacks callbacks) {
        this.mCallbacks.add(callbacks);
        for (int i = 0; i < this.mDisplayDisabled.size(); i++) {
            int keyAt = this.mDisplayDisabled.keyAt(i);
            callbacks.disable(keyAt, ((Integer) getDisabled(keyAt).first).intValue(), ((Integer) getDisabled(keyAt).second).intValue(), false);
        }
    }

    public final void addQsTile(ComponentName componentName) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1769472, componentName).sendToTarget();
        }
    }

    public final void animateExpandNotificationsPanel() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(196608);
            this.mHandler.sendEmptyMessage(196608);
        }
    }

    public final void animateExpandSettingsPanel(String str) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(327680);
            this.mHandler.obtainMessage(327680, str).sendToTarget();
        }
    }

    public final void appTransitionCancelled(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1310720, i, 0).sendToTarget();
        }
    }

    public final void appTransitionFinished(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2031616, i, 0).sendToTarget();
        }
    }

    public final void appTransitionPending(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1245184, i, 0).sendToTarget();
        }
    }

    public final void appTransitionStarting(int i, long j, long j2, boolean z) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = z ? 1 : 0;
            obtain.arg1 = Long.valueOf(j);
            obtain.arg2 = Long.valueOf(j2);
            this.mHandler.obtainMessage(1376256, obtain).sendToTarget();
        }
    }

    public final void cancelPreloadRecentApps() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(720896);
            this.mHandler.obtainMessage(720896, 0, 0, (Object) null).sendToTarget();
        }
    }

    public final void cancelRequestAddTile(String str) throws RemoteException {
        this.mHandler.obtainMessage(4063232, str).sendToTarget();
    }

    public final void clickQsTile(ComponentName componentName) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1900544, componentName).sendToTarget();
        }
    }

    public final void dismissInattentiveSleepWarning(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3342336, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public final void dismissKeyboardShortcutsMenu() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2097152);
            this.mHandler.obtainMessage(2097152).sendToTarget();
        }
    }

    public final Pair<Integer, Integer> getDisabled(int i) {
        Pair<Integer, Integer> pair = this.mDisplayDisabled.get(i);
        if (pair != null) {
            return pair;
        }
        Pair<Integer, Integer> pair2 = new Pair<>(0, 0);
        this.mDisplayDisabled.put(i, pair2);
        return pair2;
    }

    public final void handleSystemKey(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2162688, i, 0).sendToTarget();
        }
    }

    public final void handleWindowManagerLoggingCommand(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = strArr;
            obtain.arg2 = parcelFileDescriptor;
            this.mHandler.obtainMessage(3735552, obtain).sendToTarget();
        }
    }

    public final void hideAuthenticationDialog() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2818048).sendToTarget();
        }
    }

    public final void hideRecentApps(boolean z, boolean z2) {
        int i;
        synchronized (this.mLock) {
            this.mHandler.removeMessages(917504);
            C1129H h = this.mHandler;
            if (z2) {
                i = 1;
            } else {
                i = 0;
            }
            h.obtainMessage(917504, z ? 1 : 0, i, (Object) null).sendToTarget();
        }
    }

    public final void hideToast(String str, IBinder iBinder) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = iBinder;
            this.mHandler.obtainMessage(3473408, obtain).sendToTarget();
        }
    }

    public final void onBiometricAuthenticated() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2621440).sendToTarget();
        }
    }

    public final void onBiometricError(int i, int i2, int i3) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.argi3 = i3;
            this.mHandler.obtainMessage(2752512, obtain).sendToTarget();
        }
    }

    public final void onBiometricHelp(int i, String str) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.arg1 = str;
            this.mHandler.obtainMessage(2686976, obtain).sendToTarget();
        }
    }

    public final void onCameraLaunchGestureDetected(int i) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1572864);
            this.mHandler.obtainMessage(1572864, i, 0).sendToTarget();
        }
    }

    public final void onDisplayReady(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(458752, i, 0).sendToTarget();
        }
    }

    public final void onDisplayRemoved(int i) {
        synchronized (this.mLock) {
            this.mDisplayDisabled.remove(i);
        }
        for (int size = this.mCallbacks.size() - 1; size >= 0; size--) {
            this.mCallbacks.get(size).onDisplayRemoved(i);
        }
    }

    public final void onEmergencyActionLaunchGestureDetected() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(3801088);
            this.mHandler.obtainMessage(3801088).sendToTarget();
        }
    }

    public final void onProposedRotationChanged(int i, boolean z) {
        int i2;
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2490368);
            C1129H h = this.mHandler;
            if (z) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            h.obtainMessage(2490368, i, i2, (Object) null).sendToTarget();
        }
    }

    public final void onRecentsAnimationStateChanged(boolean z) {
        int i;
        synchronized (this.mLock) {
            C1129H h = this.mHandler;
            if (z) {
                i = 1;
            } else {
                i = 0;
            }
            h.obtainMessage(3080192, i, 0).sendToTarget();
        }
    }

    public final void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, InsetsVisibilities insetsVisibilities, String str) {
        int i4;
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            if (z) {
                i4 = 1;
            } else {
                i4 = 0;
            }
            obtain.argi3 = i4;
            obtain.arg1 = appearanceRegionArr;
            obtain.argi4 = i3;
            obtain.arg2 = insetsVisibilities;
            obtain.arg3 = str;
            this.mHandler.obtainMessage(393216, obtain).sendToTarget();
        }
    }

    public final void passThroughShellCommand(final String[] strArr, final ParcelFileDescriptor parcelFileDescriptor) {
        final PrintWriter printWriter = new PrintWriter(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
        new Thread() {
            public final void run() {
                try {
                    CommandRegistry commandRegistry = CommandQueue.this.mRegistry;
                    if (commandRegistry == null) {
                    } else {
                        commandRegistry.onShellCommand(printWriter, strArr);
                        printWriter.flush();
                        try {
                            parcelFileDescriptor.close();
                        } catch (Exception unused) {
                        }
                    }
                } finally {
                    printWriter.flush();
                    try {
                        parcelFileDescriptor.close();
                    } catch (Exception unused2) {
                    }
                }
            }
        }.start();
    }

    public final void preloadRecentApps() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(655360);
            this.mHandler.obtainMessage(655360, 0, 0, (Object) null).sendToTarget();
        }
    }

    public final void recomputeDisableFlags(int i, boolean z) {
        synchronized (this.mLock) {
            disable(i, ((Integer) getDisabled(i).first).intValue(), ((Integer) getDisabled(i).second).intValue(), z);
        }
    }

    public final void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        this.mHandler.obtainMessage(4325376, iNearbyMediaDevicesProvider).sendToTarget();
    }

    public final void remQsTile(ComponentName componentName) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1835008, componentName).sendToTarget();
        }
    }

    public final void removeCallback(Callbacks callbacks) {
        this.mCallbacks.remove(callbacks);
    }

    public final void removeIcon(String str) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(65536, 2, 0, str).sendToTarget();
        }
    }

    public final void requestWindowMagnificationConnection(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3670016, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public final void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(4128768, iBiometricContextListener).sendToTarget();
        }
    }

    public final void setIcon(String str, StatusBarIcon statusBarIcon) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(65536, 1, 0, new Pair(str, statusBarIcon)).sendToTarget();
        }
    }

    public final void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        int i4;
        synchronized (this.mLock) {
            this.mHandler.removeMessages(524288);
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.argi3 = i3;
            if (z) {
                i4 = 1;
            } else {
                i4 = 0;
            }
            obtain.argi4 = i4;
            obtain.arg1 = iBinder;
            this.mHandler.obtainMessage(524288, obtain).sendToTarget();
        }
    }

    public final void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
        int i2;
        synchronized (this.mLock) {
            C1129H h = this.mHandler;
            if (z) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            h.obtainMessage(3866624, i, i2).sendToTarget();
        }
    }

    public final void setTopAppHidesStatusBar(boolean z) {
        this.mHandler.removeMessages(2424832);
        this.mHandler.obtainMessage(2424832, z ? 1 : 0, 0).sendToTarget();
    }

    public final void setUdfpsHbmListener(IUdfpsHbmListener iUdfpsHbmListener) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3932160, iUdfpsHbmListener).sendToTarget();
        }
    }

    public final void setWindowState(int i, int i2, int i3) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(786432, i, i2, Integer.valueOf(i3)).sendToTarget();
        }
    }

    public final void showAssistDisclosure() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1441792);
            this.mHandler.obtainMessage(1441792).sendToTarget();
        }
    }

    public final void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2, int i2) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = promptInfo;
            obtain.arg2 = iBiometricSysuiReceiver;
            obtain.arg3 = iArr;
            obtain.arg4 = Boolean.valueOf(z);
            obtain.arg5 = Boolean.valueOf(z2);
            obtain.argi1 = i;
            obtain.arg6 = str;
            obtain.argl1 = j;
            obtain.argl2 = j2;
            obtain.argi2 = i2;
            this.mHandler.obtainMessage(2555904, obtain).sendToTarget();
        }
    }

    public final void showGlobalActionsMenu() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2228224);
            this.mHandler.obtainMessage(2228224).sendToTarget();
        }
    }

    public final void showInattentiveSleepWarning() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3276800).sendToTarget();
        }
    }

    public final void showPictureInPictureMenu() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1703936);
            this.mHandler.obtainMessage(1703936).sendToTarget();
        }
    }

    public final void showPinningEnterExitToast(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(2949120, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public final void showPinningEscapeToast() {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3014656).sendToTarget();
        }
    }

    public final void showRecentApps(boolean z) {
        int i;
        synchronized (this.mLock) {
            this.mHandler.removeMessages(851968);
            C1129H h = this.mHandler;
            if (z) {
                i = 1;
            } else {
                i = 0;
            }
            h.obtainMessage(851968, i, 0, (Object) null).sendToTarget();
        }
    }

    public final void showScreenPinningRequest(int i) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1179648, i, 0, (Object) null).sendToTarget();
        }
    }

    public final void showShutdownUi(boolean z, String str) {
        int i;
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2359296);
            C1129H h = this.mHandler;
            if (z) {
                i = 1;
            } else {
                i = 0;
            }
            h.obtainMessage(2359296, i, 0, str).sendToTarget();
        }
    }

    public final void showToast(int i, String str, IBinder iBinder, CharSequence charSequence, IBinder iBinder2, int i2, ITransientNotificationCallback iTransientNotificationCallback) {
        synchronized (this.mLock) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = iBinder;
            obtain.arg3 = charSequence;
            obtain.arg4 = iBinder2;
            obtain.arg5 = iTransientNotificationCallback;
            obtain.argi1 = i;
            obtain.argi2 = i2;
            this.mHandler.obtainMessage(3407872, obtain).sendToTarget();
        }
    }

    public final void showTransient(int i, int[] iArr, boolean z) {
        int i2;
        synchronized (this.mLock) {
            C1129H h = this.mHandler;
            if (z) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            h.obtainMessage(3145728, i, i2, iArr).sendToTarget();
        }
    }

    public final void showWirelessChargingAnimation(int i) {
        this.mHandler.removeMessages(2883584);
        this.mHandler.obtainMessage(2883584, i, 0).sendToTarget();
    }

    public final void startAssist(Bundle bundle) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1507328);
            this.mHandler.obtainMessage(1507328, bundle).sendToTarget();
        }
    }

    public final void startTracing() {
        synchronized (this.mLock) {
            ProtoTracer protoTracer = this.mProtoTracer;
            if (protoTracer != null) {
                FrameProtoTracer<MessageNano, SystemUiTraceFileProto, SystemUiTraceEntryProto, SystemUiTraceProto> frameProtoTracer = protoTracer.mProtoTracer;
                Objects.requireNonNull(frameProtoTracer);
                synchronized (frameProtoTracer.mLock) {
                    if (!frameProtoTracer.mEnabled) {
                        frameProtoTracer.mBuffer.resetBuffer();
                        frameProtoTracer.mEnabled = true;
                        frameProtoTracer.logState();
                    }
                }
            }
            this.mHandler.obtainMessage(3538944, Boolean.TRUE).sendToTarget();
        }
    }

    public final void stopTracing() {
        synchronized (this.mLock) {
            ProtoTracer protoTracer = this.mProtoTracer;
            if (protoTracer != null) {
                protoTracer.stop();
            }
            this.mHandler.obtainMessage(3538944, Boolean.FALSE).sendToTarget();
        }
    }

    public final void suppressAmbientDisplay(boolean z) {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(3604480, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public final void toggleKeyboardShortcutsMenu(int i) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1638400);
            this.mHandler.obtainMessage(1638400, i, 0).sendToTarget();
        }
    }

    public final void togglePanel() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(2293760);
            this.mHandler.obtainMessage(2293760, 0, 0).sendToTarget();
        }
    }

    public final void toggleRecentApps() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(589824);
            Message obtainMessage = this.mHandler.obtainMessage(589824, 0, 0, (Object) null);
            obtainMessage.setAsynchronous(true);
            obtainMessage.sendToTarget();
        }
    }

    public final void toggleSplitScreen() {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(1966080);
            this.mHandler.obtainMessage(1966080, 0, 0, (Object) null).sendToTarget();
        }
    }

    public final void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        this.mHandler.obtainMessage(4390912, iNearbyMediaDevicesProvider).sendToTarget();
    }

    public CommandQueue(Context context, ProtoTracer protoTracer, CommandRegistry commandRegistry) {
        this.mProtoTracer = protoTracer;
        this.mRegistry = commandRegistry;
        ((DisplayManager) context.getSystemService(DisplayManager.class)).registerDisplayListener(this, this.mHandler);
        this.mDisplayDisabled.put(0, new Pair(0, 0));
    }

    public final void requestAddTile(ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = componentName;
        obtain.arg2 = charSequence;
        obtain.arg3 = charSequence2;
        obtain.arg4 = icon;
        obtain.arg5 = iAddTileResultCallback;
        this.mHandler.obtainMessage(3997696, obtain).sendToTarget();
    }

    public final void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = Integer.valueOf(i);
        obtain.arg2 = mediaRoute2Info;
        obtain.arg3 = icon;
        obtain.arg4 = charSequence;
        this.mHandler.obtainMessage(4259840, obtain).sendToTarget();
    }

    public final void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) throws RemoteException {
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = Integer.valueOf(i);
        obtain.arg2 = mediaRoute2Info;
        obtain.arg3 = iUndoMediaTransferCallback;
        this.mHandler.obtainMessage(4194304, obtain).sendToTarget();
    }

    public final void animateCollapsePanels(int i, boolean z) {
        synchronized (this.mLock) {
            this.mHandler.removeMessages(262144);
            this.mHandler.obtainMessage(262144, i, z ? 1 : 0).sendToTarget();
        }
    }

    public final void disable(int i, int i2, int i3) {
        disable(i, i2, i3, true);
    }

    static {
        Class<CommandQueue> cls = CommandQueue.class;
    }

    public final void runGcForTest() {
        GcUtils.runGcAndFinalizersSync();
    }
}
