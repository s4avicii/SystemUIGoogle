package com.google.android.systemui.assist.uihints;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.leanback.R$color;
import com.android.systemui.navigationbar.NavigationModeController;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import kotlinx.atomicfu.AtomicFU;

public final class NgaMessageHandler {
    public static final boolean VERBOSE;
    public final AssistantPresenceHandler mAssistantPresenceHandler;
    public final Set<AudioInfoListener> mAudioInfoListeners;
    public final Set<CardInfoListener> mCardInfoListeners;
    public final Set<ChipsInfoListener> mChipsInfoListeners;
    public final Set<ClearListener> mClearListeners;
    public final Set<ConfigInfoListener> mConfigInfoListeners;
    public final Set<EdgeLightsInfoListener> mEdgeLightsInfoListeners;
    public final Set<GoBackListener> mGoBackListeners;
    public final Set<GreetingInfoListener> mGreetingInfoListeners;
    public final Handler mHandler;
    public boolean mIsGestureNav;
    public final Set<KeepAliveListener> mKeepAliveListeners;
    public final Set<KeyboardInfoListener> mKeyboardInfoListeners;
    public final Set<NavBarVisibilityListener> mNavBarVisibilityListeners;
    public final NgaUiController mNgaUiController;
    public final Set<StartActivityInfoListener> mStartActivityInfoListeners;
    public final Set<SwipeListener> mSwipeListeners;
    public final Set<TakeScreenshotListener> mTakeScreenshotListeners;
    public final Set<TranscriptionInfoListener> mTranscriptionInfoListeners;
    public final Set<WarmingListener> mWarmingListeners;
    public final Set<ZerostateInfoListener> mZerostateInfoListeners;

    public interface AudioInfoListener {
        void onAudioInfo(float f, float f2);
    }

    public interface CardInfoListener {
        void onCardInfo(boolean z, int i, boolean z2, boolean z3);
    }

    public interface ChipsInfoListener {
        void onChipsInfo(ArrayList arrayList);
    }

    public interface ClearListener {
        void onClear(boolean z);
    }

    public interface ConfigInfoListener {
        void onConfigInfo(ConfigInfo configInfo);
    }

    public interface EdgeLightsInfoListener {
        void onEdgeLightsInfo(String str, boolean z);
    }

    public interface GoBackListener {
        void onGoBack();
    }

    public interface GreetingInfoListener {
        void onGreetingInfo(String str, PendingIntent pendingIntent);
    }

    public interface KeepAliveListener {
        void onKeepAlive();
    }

    public interface KeyboardInfoListener {
        void onHideKeyboard();

        void onShowKeyboard(PendingIntent pendingIntent);
    }

    public interface NavBarVisibilityListener {
        void onVisibleRequest(boolean z);
    }

    public interface StartActivityInfoListener {
        void onStartActivityInfo(Intent intent, boolean z);
    }

    public interface SwipeListener {
        void onSwipe(Bundle bundle);
    }

    public interface TakeScreenshotListener {
        void onTakeScreenshot(PendingIntent pendingIntent);
    }

    public interface TranscriptionInfoListener {
        void onTranscriptionInfo(String str, PendingIntent pendingIntent, int i);
    }

    public interface WarmingListener {
        void onWarmingRequest(WarmingRequest warmingRequest);
    }

    public interface ZerostateInfoListener {
        void onHideZerostate();

        void onShowZerostate(PendingIntent pendingIntent);
    }

    public NgaMessageHandler(NgaUiController ngaUiController, AssistantPresenceHandler assistantPresenceHandler, NavigationModeController navigationModeController, Set<KeepAliveListener> set, Set<AudioInfoListener> set2, Set<CardInfoListener> set3, Set<ConfigInfoListener> set4, Set<EdgeLightsInfoListener> set5, Set<TranscriptionInfoListener> set6, Set<GreetingInfoListener> set7, Set<ChipsInfoListener> set8, Set<ClearListener> set9, Set<StartActivityInfoListener> set10, Set<KeyboardInfoListener> set11, Set<ZerostateInfoListener> set12, Set<GoBackListener> set13, Set<SwipeListener> set14, Set<TakeScreenshotListener> set15, Set<WarmingListener> set16, Set<NavBarVisibilityListener> set17, Handler handler) {
        this.mNgaUiController = ngaUiController;
        this.mAssistantPresenceHandler = assistantPresenceHandler;
        this.mKeepAliveListeners = set;
        this.mAudioInfoListeners = set2;
        this.mCardInfoListeners = set3;
        this.mConfigInfoListeners = set4;
        this.mEdgeLightsInfoListeners = set5;
        this.mTranscriptionInfoListeners = set6;
        this.mGreetingInfoListeners = set7;
        this.mChipsInfoListeners = set8;
        this.mClearListeners = set9;
        this.mStartActivityInfoListeners = set10;
        this.mKeyboardInfoListeners = set11;
        this.mZerostateInfoListeners = set12;
        this.mGoBackListeners = set13;
        this.mSwipeListeners = set14;
        this.mTakeScreenshotListeners = set15;
        this.mWarmingListeners = set16;
        this.mNavBarVisibilityListeners = set17;
        this.mHandler = handler;
        NavigationModeController navigationModeController2 = navigationModeController;
        this.mIsGestureNav = R$color.isGesturalMode(navigationModeController.addListener(new NgaMessageHandler$$ExternalSyntheticLambda0(this)));
    }

    public static class ConfigInfo {
        public final PendingIntent configurationCallback;
        public final boolean ngaIsAssistant;
        public PendingIntent onColorChanged;
        public final PendingIntent onKeyboardShowingChange;
        public final PendingIntent onTaskChange;
        public final PendingIntent onTouchInside;
        public final PendingIntent onTouchOutside;
        public final boolean sysUiIsNgaUi;

        public ConfigInfo(Bundle bundle) {
            boolean z;
            boolean z2 = bundle.getBoolean("is_available");
            boolean z3 = bundle.getBoolean("nga_is_assistant", z2);
            this.ngaIsAssistant = z3;
            if (!z2 || !z3) {
                z = false;
            } else {
                z = true;
            }
            this.sysUiIsNgaUi = z;
            this.onColorChanged = (PendingIntent) bundle.getParcelable("color_changed");
            this.onTouchOutside = (PendingIntent) bundle.getParcelable("touch_outside");
            this.onTouchInside = (PendingIntent) bundle.getParcelable("touch_inside");
            this.onTaskChange = (PendingIntent) bundle.getParcelable("task_stack_changed");
            this.onKeyboardShowingChange = (PendingIntent) bundle.getParcelable("keyboard_showing_changed");
            this.configurationCallback = (PendingIntent) bundle.getParcelable("configuration");
        }
    }

    public static class WarmingRequest {
        public final PendingIntent onWarm;
        public final float threshold;

        public WarmingRequest(PendingIntent pendingIntent, float f) {
            this.onWarm = pendingIntent;
            this.threshold = AtomicFU.clamp(f, 0.0f, 1.0f);
        }
    }

    static {
        boolean z;
        String str = Build.TYPE;
        Locale locale = Locale.ROOT;
        if (str.toLowerCase(locale).contains("debug") || str.toLowerCase(locale).equals("eng")) {
            z = true;
        } else {
            z = false;
        }
        VERBOSE = z;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x025e  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x02a5 A[LOOP:11: B:113:0x029f->B:115:0x02a5, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x02b8  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x02c6  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x02d2  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x02e0  */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x02eb  */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x02f6  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x02ff  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x030a  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x0316  */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x0321  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x032d  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x0339 A[FALL_THROUGH] */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x0340  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0343  */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x0367  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x038a  */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x03aa  */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x03c8  */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x03e2  */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x03f8  */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x041f  */
    /* JADX WARNING: Removed duplicated region for block: B:187:0x0435  */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x0451  */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x0478  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x015c  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0168  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0174  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0180  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x018b  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0197  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x01a2 A[FALL_THROUGH] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01a7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void processBundle(android.os.Bundle r18, java.lang.Runnable r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            android.os.Looper r2 = android.os.Looper.myLooper()
            android.os.Handler r3 = r0.mHandler
            android.os.Looper r3 = r3.getLooper()
            r4 = 2
            if (r2 == r3) goto L_0x001e
            android.os.Handler r2 = r0.mHandler
            com.android.wm.shell.bubbles.BubbleController$5$$ExternalSyntheticLambda0 r3 = new com.android.wm.shell.bubbles.BubbleController$5$$ExternalSyntheticLambda0
            r5 = r19
            r3.<init>(r0, r1, r5, r4)
            r2.post(r3)
            return
        L_0x001e:
            r5 = r19
            boolean r2 = VERBOSE
            java.lang.String r3 = "chips"
            java.lang.String r4 = "text"
            java.lang.String r6 = "audio_info"
            java.lang.String r7 = "action"
            java.lang.String r8 = "NgaMessageHandler"
            if (r2 == 0) goto L_0x00d6
            java.lang.Object r2 = r1.get(r7)
            boolean r2 = r6.equals(r2)
            if (r2 == 0) goto L_0x003b
            goto L_0x00d6
        L_0x003b:
            java.lang.String r2 = "Contents of NGA Bundle:"
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
            java.util.Set r9 = r18.keySet()
            java.util.Iterator r9 = r9.iterator()
        L_0x0049:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x00cf
            java.lang.Object r10 = r9.next()
            java.lang.String r10 = (java.lang.String) r10
            java.lang.String r11 = "\n   "
            r2.append(r11)
            r2.append(r10)
            java.lang.String r11 = ": "
            r2.append(r11)
            boolean r12 = r4.equals(r10)
            if (r12 == 0) goto L_0x007e
            java.lang.String r11 = "("
            r2.append(r11)
            java.lang.String r10 = r1.getString(r10)
            int r10 = r10.length()
            r2.append(r10)
            java.lang.String r10 = " characters)"
            r2.append(r10)
            goto L_0x0049
        L_0x007e:
            boolean r12 = r3.equals(r10)
            if (r12 == 0) goto L_0x00c6
            java.util.ArrayList r10 = r1.getParcelableArrayList(r3)
            if (r10 == 0) goto L_0x0049
            java.util.Iterator r10 = r10.iterator()
        L_0x008e:
            boolean r12 = r10.hasNext()
            if (r12 == 0) goto L_0x0049
            java.lang.Object r12 = r10.next()
            android.os.Bundle r12 = (android.os.Bundle) r12
            java.lang.String r13 = "\n      Chip:"
            r2.append(r13)
            java.util.Set r13 = r12.keySet()
            java.util.Iterator r13 = r13.iterator()
        L_0x00a7:
            boolean r14 = r13.hasNext()
            if (r14 == 0) goto L_0x008e
            java.lang.Object r14 = r13.next()
            java.lang.String r14 = (java.lang.String) r14
            java.lang.String r15 = "\n         "
            r2.append(r15)
            r2.append(r14)
            r2.append(r11)
            java.lang.Object r14 = r12.get(r14)
            r2.append(r14)
            goto L_0x00a7
        L_0x00c6:
            java.lang.Object r10 = r1.get(r10)
            r2.append(r10)
            goto L_0x0049
        L_0x00cf:
            java.lang.String r2 = r2.toString()
            android.util.Log.v(r8, r2)
        L_0x00d6:
            java.lang.String r2 = ""
            java.lang.String r7 = r1.getString(r7, r2)
            boolean r9 = r7.isEmpty()
            if (r9 == 0) goto L_0x00e8
            java.lang.String r0 = "No action specified, ignoring"
            android.util.Log.w(r8, r0)
            return
        L_0x00e8:
            com.google.android.systemui.assist.uihints.AssistantPresenceHandler r9 = r0.mAssistantPresenceHandler
            java.util.Objects.requireNonNull(r9)
            boolean r9 = r9.mNgaIsAssistant
            com.google.android.systemui.assist.uihints.AssistantPresenceHandler r10 = r0.mAssistantPresenceHandler
            java.util.Objects.requireNonNull(r10)
            boolean r10 = r10.mSysUiIsNgaUi
            java.lang.String r11 = "config"
            boolean r11 = r7.equals(r11)
            r12 = 1
            if (r11 != 0) goto L_0x012a
            java.lang.String r11 = "gesture_nav_bar_visible"
            boolean r11 = r7.equals(r11)
            if (r11 != 0) goto L_0x0109
            r11 = 0
            goto L_0x014e
        L_0x0109:
            boolean r11 = r0.mIsGestureNav
            if (r11 == 0) goto L_0x014d
            java.lang.String r11 = "visible"
            boolean r11 = r1.getBoolean(r11, r12)
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$NavBarVisibilityListener> r13 = r0.mNavBarVisibilityListeners
            java.util.Iterator r13 = r13.iterator()
        L_0x011a:
            boolean r14 = r13.hasNext()
            if (r14 == 0) goto L_0x014d
            java.lang.Object r14 = r13.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$NavBarVisibilityListener r14 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.NavBarVisibilityListener) r14
            r14.onVisibleRequest(r11)
            goto L_0x011a
        L_0x012a:
            com.google.android.systemui.assist.uihints.NgaMessageHandler$ConfigInfo r11 = new com.google.android.systemui.assist.uihints.NgaMessageHandler$ConfigInfo
            r11.<init>(r1)
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$ConfigInfoListener> r13 = r0.mConfigInfoListeners
            java.util.Iterator r13 = r13.iterator()
        L_0x0135:
            boolean r14 = r13.hasNext()
            if (r14 == 0) goto L_0x0145
            java.lang.Object r14 = r13.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$ConfigInfoListener r14 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.ConfigInfoListener) r14
            r14.onConfigInfo(r11)
            goto L_0x0135
        L_0x0145:
            com.google.android.systemui.assist.uihints.NgaUiController r11 = r0.mNgaUiController
            java.util.Objects.requireNonNull(r11)
            r11.refresh()
        L_0x014d:
            r11 = r12
        L_0x014e:
            r13 = 3
            if (r11 != 0) goto L_0x0476
            if (r9 == 0) goto L_0x0476
            int r11 = r7.hashCode()
            r14 = 4
            switch(r11) {
                case 3046160: goto L_0x0197;
                case 109854522: goto L_0x018b;
                case 192184798: goto L_0x0180;
                case 371207756: goto L_0x0174;
                case 777739294: goto L_0x0168;
                case 1124416317: goto L_0x015c;
                default: goto L_0x015b;
            }
        L_0x015b:
            goto L_0x01a2
        L_0x015c:
            java.lang.String r11 = "warming"
            boolean r11 = r7.equals(r11)
            if (r11 != 0) goto L_0x0166
            goto L_0x01a2
        L_0x0166:
            r11 = 5
            goto L_0x01a3
        L_0x0168:
            java.lang.String r11 = "take_screenshot"
            boolean r11 = r7.equals(r11)
            if (r11 != 0) goto L_0x0172
            goto L_0x01a2
        L_0x0172:
            r11 = r14
            goto L_0x01a3
        L_0x0174:
            java.lang.String r11 = "start_activity"
            boolean r11 = r7.equals(r11)
            if (r11 != 0) goto L_0x017e
            goto L_0x01a2
        L_0x017e:
            r11 = r13
            goto L_0x01a3
        L_0x0180:
            java.lang.String r11 = "go_back"
            boolean r11 = r7.equals(r11)
            if (r11 != 0) goto L_0x0189
            goto L_0x01a2
        L_0x0189:
            r11 = 2
            goto L_0x01a3
        L_0x018b:
            java.lang.String r11 = "swipe"
            boolean r11 = r7.equals(r11)
            if (r11 != 0) goto L_0x0195
            goto L_0x01a2
        L_0x0195:
            r11 = r12
            goto L_0x01a3
        L_0x0197:
            java.lang.String r11 = "card"
            boolean r11 = r7.equals(r11)
            if (r11 != 0) goto L_0x01a0
            goto L_0x01a2
        L_0x01a0:
            r11 = 0
            goto L_0x01a3
        L_0x01a2:
            r11 = -1
        L_0x01a3:
            java.lang.String r15 = "intent"
            if (r11 == 0) goto L_0x025e
            if (r11 == r12) goto L_0x0237
            r12 = 2
            if (r11 == r12) goto L_0x0221
            if (r11 == r13) goto L_0x01ff
            if (r11 == r14) goto L_0x01e1
            r12 = 5
            if (r11 == r12) goto L_0x01b6
            r11 = 0
            goto L_0x0295
        L_0x01b6:
            android.os.Parcelable r11 = r1.getParcelable(r15)
            android.app.PendingIntent r11 = (android.app.PendingIntent) r11
            r12 = 1036831949(0x3dcccccd, float:0.1)
            java.lang.String r13 = "threshold"
            float r12 = r1.getFloat(r13, r12)
            com.google.android.systemui.assist.uihints.NgaMessageHandler$WarmingRequest r13 = new com.google.android.systemui.assist.uihints.NgaMessageHandler$WarmingRequest
            r13.<init>(r11, r12)
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$WarmingListener> r11 = r0.mWarmingListeners
            java.util.Iterator r11 = r11.iterator()
        L_0x01d1:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x0293
            java.lang.Object r12 = r11.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$WarmingListener r12 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.WarmingListener) r12
            r12.onWarmingRequest(r13)
            goto L_0x01d1
        L_0x01e1:
            java.lang.String r11 = "on_finish"
            android.os.Parcelable r11 = r1.getParcelable(r11)
            android.app.PendingIntent r11 = (android.app.PendingIntent) r11
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$TakeScreenshotListener> r12 = r0.mTakeScreenshotListeners
            java.util.Iterator r12 = r12.iterator()
        L_0x01ef:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x0293
            java.lang.Object r13 = r12.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$TakeScreenshotListener r13 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.TakeScreenshotListener) r13
            r13.onTakeScreenshot(r11)
            goto L_0x01ef
        L_0x01ff:
            android.os.Parcelable r11 = r1.getParcelable(r15)
            android.content.Intent r11 = (android.content.Intent) r11
            java.lang.String r12 = "dismiss_shade"
            boolean r12 = r1.getBoolean(r12)
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$StartActivityInfoListener> r13 = r0.mStartActivityInfoListeners
            java.util.Iterator r13 = r13.iterator()
        L_0x0211:
            boolean r14 = r13.hasNext()
            if (r14 == 0) goto L_0x0293
            java.lang.Object r14 = r13.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$StartActivityInfoListener r14 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.StartActivityInfoListener) r14
            r14.onStartActivityInfo(r11, r12)
            goto L_0x0211
        L_0x0221:
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$GoBackListener> r11 = r0.mGoBackListeners
            java.util.Iterator r11 = r11.iterator()
        L_0x0227:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x0293
            java.lang.Object r12 = r11.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$GoBackListener r12 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.GoBackListener) r12
            r12.onGoBack()
            goto L_0x0227
        L_0x0237:
            java.lang.String r11 = "swipe_action"
            android.os.Parcelable r11 = r1.getParcelable(r11)
            android.os.Bundle r11 = (android.os.Bundle) r11
            if (r11 != 0) goto L_0x0248
            java.lang.String r11 = "Missing swipe action argument, ignoring request"
            android.util.Log.e(r8, r11)
            goto L_0x0293
        L_0x0248:
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$SwipeListener> r12 = r0.mSwipeListeners
            java.util.Iterator r12 = r12.iterator()
        L_0x024e:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x0293
            java.lang.Object r13 = r12.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$SwipeListener r13 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.SwipeListener) r13
            r13.onSwipe(r11)
            goto L_0x024e
        L_0x025e:
            java.lang.String r11 = "is_visible"
            boolean r11 = r1.getBoolean(r11)
            java.lang.String r12 = "sysui_color"
            r13 = 0
            int r12 = r1.getInt(r12, r13)
            java.lang.String r13 = "animate_transition"
            r14 = 1
            boolean r13 = r1.getBoolean(r13, r14)
            java.lang.String r14 = "card_forces_scrim"
            boolean r14 = r1.getBoolean(r14)
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$CardInfoListener> r15 = r0.mCardInfoListeners
            java.util.Iterator r15 = r15.iterator()
        L_0x027f:
            boolean r16 = r15.hasNext()
            if (r16 == 0) goto L_0x0293
            java.lang.Object r16 = r15.next()
            r5 = r16
            com.google.android.systemui.assist.uihints.NgaMessageHandler$CardInfoListener r5 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.CardInfoListener) r5
            r5.onCardInfo(r11, r12, r13, r14)
            r5 = r19
            goto L_0x027f
        L_0x0293:
            r5 = 1
            r11 = r5
        L_0x0295:
            if (r11 != 0) goto L_0x0476
            if (r10 == 0) goto L_0x0476
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$KeepAliveListener> r5 = r0.mKeepAliveListeners
            java.util.Iterator r5 = r5.iterator()
        L_0x029f:
            boolean r11 = r5.hasNext()
            if (r11 == 0) goto L_0x02af
            java.lang.Object r11 = r5.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$KeepAliveListener r11 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.KeepAliveListener) r11
            r11.onKeepAlive()
            goto L_0x029f
        L_0x02af:
            int r5 = r7.hashCode()
            switch(r5) {
                case -2051025175: goto L_0x032d;
                case -2040419289: goto L_0x0321;
                case -1160605116: goto L_0x0316;
                case -241763182: goto L_0x030a;
                case -207201236: goto L_0x02ff;
                case 94631335: goto L_0x02f6;
                case 94746189: goto L_0x02eb;
                case 205422649: goto L_0x02e0;
                case 771587807: goto L_0x02d2;
                case 1549039479: goto L_0x02c6;
                case 1642639251: goto L_0x02b8;
                default: goto L_0x02b6;
            }
        L_0x02b6:
            goto L_0x0339
        L_0x02b8:
            java.lang.String r5 = "keep_alive"
            boolean r5 = r7.equals(r5)
            if (r5 != 0) goto L_0x02c2
            goto L_0x0339
        L_0x02c2:
            r5 = 10
            goto L_0x033a
        L_0x02c6:
            boolean r5 = r7.equals(r6)
            if (r5 != 0) goto L_0x02ce
            goto L_0x0339
        L_0x02ce:
            r5 = 9
            goto L_0x033a
        L_0x02d2:
            java.lang.String r5 = "edge_lights"
            boolean r5 = r7.equals(r5)
            if (r5 != 0) goto L_0x02dc
            goto L_0x0339
        L_0x02dc:
            r5 = 8
            goto L_0x033a
        L_0x02e0:
            java.lang.String r5 = "greeting"
            boolean r5 = r7.equals(r5)
            if (r5 != 0) goto L_0x02e9
            goto L_0x0339
        L_0x02e9:
            r5 = 7
            goto L_0x033a
        L_0x02eb:
            java.lang.String r5 = "clear"
            boolean r5 = r7.equals(r5)
            if (r5 != 0) goto L_0x02f4
            goto L_0x0339
        L_0x02f4:
            r5 = 6
            goto L_0x033a
        L_0x02f6:
            boolean r5 = r7.equals(r3)
            if (r5 != 0) goto L_0x02fd
            goto L_0x0339
        L_0x02fd:
            r5 = 5
            goto L_0x033a
        L_0x02ff:
            java.lang.String r5 = "hide_zerostate"
            boolean r5 = r7.equals(r5)
            if (r5 != 0) goto L_0x0308
            goto L_0x0339
        L_0x0308:
            r5 = 4
            goto L_0x033a
        L_0x030a:
            java.lang.String r5 = "transcription"
            boolean r5 = r7.equals(r5)
            if (r5 != 0) goto L_0x0314
            goto L_0x0339
        L_0x0314:
            r5 = 3
            goto L_0x033a
        L_0x0316:
            java.lang.String r5 = "hide_keyboard"
            boolean r5 = r7.equals(r5)
            if (r5 != 0) goto L_0x031f
            goto L_0x0339
        L_0x031f:
            r5 = 2
            goto L_0x033a
        L_0x0321:
            java.lang.String r5 = "show_zerostate"
            boolean r5 = r7.equals(r5)
            if (r5 != 0) goto L_0x032b
            goto L_0x0339
        L_0x032b:
            r5 = 1
            goto L_0x033a
        L_0x032d:
            java.lang.String r5 = "show_keyboard"
            boolean r5 = r7.equals(r5)
            if (r5 != 0) goto L_0x0337
            goto L_0x0339
        L_0x0337:
            r5 = 0
            goto L_0x033a
        L_0x0339:
            r5 = -1
        L_0x033a:
            java.lang.String r6 = "tap_action"
            switch(r5) {
                case 0: goto L_0x0451;
                case 1: goto L_0x0435;
                case 2: goto L_0x041f;
                case 3: goto L_0x03f8;
                case 4: goto L_0x03e2;
                case 5: goto L_0x03c8;
                case 6: goto L_0x03aa;
                case 7: goto L_0x038a;
                case 8: goto L_0x0367;
                case 9: goto L_0x0343;
                case 10: goto L_0x046d;
                default: goto L_0x0340;
            }
        L_0x0340:
            r11 = 0
            goto L_0x0476
        L_0x0343:
            java.lang.String r2 = "volume"
            float r2 = r1.getFloat(r2)
            java.lang.String r3 = "speech_confidence"
            float r1 = r1.getFloat(r3)
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$AudioInfoListener> r3 = r0.mAudioInfoListeners
            java.util.Iterator r3 = r3.iterator()
        L_0x0357:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x046d
            java.lang.Object r4 = r3.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$AudioInfoListener r4 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.AudioInfoListener) r4
            r4.onAudioInfo(r2, r1)
            goto L_0x0357
        L_0x0367:
            java.lang.String r3 = "state"
            java.lang.String r2 = r1.getString(r3, r2)
            java.lang.String r3 = "listening"
            boolean r1 = r1.getBoolean(r3)
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$EdgeLightsInfoListener> r3 = r0.mEdgeLightsInfoListeners
            java.util.Iterator r3 = r3.iterator()
        L_0x037a:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x046d
            java.lang.Object r4 = r3.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$EdgeLightsInfoListener r4 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.EdgeLightsInfoListener) r4
            r4.onEdgeLightsInfo(r2, r1)
            goto L_0x037a
        L_0x038a:
            java.lang.String r2 = r1.getString(r4)
            android.os.Parcelable r1 = r1.getParcelable(r6)
            android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$GreetingInfoListener> r3 = r0.mGreetingInfoListeners
            java.util.Iterator r3 = r3.iterator()
        L_0x039a:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x046d
            java.lang.Object r4 = r3.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$GreetingInfoListener r4 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.GreetingInfoListener) r4
            r4.onGreetingInfo(r2, r1)
            goto L_0x039a
        L_0x03aa:
            java.lang.String r2 = "show_animation"
            r3 = 1
            boolean r1 = r1.getBoolean(r2, r3)
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$ClearListener> r2 = r0.mClearListeners
            java.util.Iterator r2 = r2.iterator()
        L_0x03b8:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x046d
            java.lang.Object r3 = r2.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$ClearListener r3 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.ClearListener) r3
            r3.onClear(r1)
            goto L_0x03b8
        L_0x03c8:
            java.util.ArrayList r1 = r1.getParcelableArrayList(r3)
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$ChipsInfoListener> r2 = r0.mChipsInfoListeners
            java.util.Iterator r2 = r2.iterator()
        L_0x03d2:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x046d
            java.lang.Object r3 = r2.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$ChipsInfoListener r3 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.ChipsInfoListener) r3
            r3.onChipsInfo(r1)
            goto L_0x03d2
        L_0x03e2:
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$ZerostateInfoListener> r1 = r0.mZerostateInfoListeners
            java.util.Iterator r1 = r1.iterator()
        L_0x03e8:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x046d
            java.lang.Object r2 = r1.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$ZerostateInfoListener r2 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.ZerostateInfoListener) r2
            r2.onHideZerostate()
            goto L_0x03e8
        L_0x03f8:
            java.lang.String r2 = r1.getString(r4)
            android.os.Parcelable r3 = r1.getParcelable(r6)
            android.app.PendingIntent r3 = (android.app.PendingIntent) r3
            java.lang.String r4 = "text_color"
            int r1 = r1.getInt(r4)
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$TranscriptionInfoListener> r4 = r0.mTranscriptionInfoListeners
            java.util.Iterator r4 = r4.iterator()
        L_0x040f:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x046d
            java.lang.Object r5 = r4.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$TranscriptionInfoListener r5 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.TranscriptionInfoListener) r5
            r5.onTranscriptionInfo(r2, r3, r1)
            goto L_0x040f
        L_0x041f:
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$KeyboardInfoListener> r1 = r0.mKeyboardInfoListeners
            java.util.Iterator r1 = r1.iterator()
        L_0x0425:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x046d
            java.lang.Object r2 = r1.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$KeyboardInfoListener r2 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.KeyboardInfoListener) r2
            r2.onHideKeyboard()
            goto L_0x0425
        L_0x0435:
            android.os.Parcelable r1 = r1.getParcelable(r6)
            android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$ZerostateInfoListener> r2 = r0.mZerostateInfoListeners
            java.util.Iterator r2 = r2.iterator()
        L_0x0441:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x046d
            java.lang.Object r3 = r2.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$ZerostateInfoListener r3 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.ZerostateInfoListener) r3
            r3.onShowZerostate(r1)
            goto L_0x0441
        L_0x0451:
            android.os.Parcelable r1 = r1.getParcelable(r6)
            android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            java.util.Set<com.google.android.systemui.assist.uihints.NgaMessageHandler$KeyboardInfoListener> r2 = r0.mKeyboardInfoListeners
            java.util.Iterator r2 = r2.iterator()
        L_0x045d:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x046d
            java.lang.Object r3 = r2.next()
            com.google.android.systemui.assist.uihints.NgaMessageHandler$KeyboardInfoListener r3 = (com.google.android.systemui.assist.uihints.NgaMessageHandler.KeyboardInfoListener) r3
            r3.onShowKeyboard(r1)
            goto L_0x045d
        L_0x046d:
            com.google.android.systemui.assist.uihints.NgaUiController r0 = r0.mNgaUiController
            java.util.Objects.requireNonNull(r0)
            r0.refresh()
            r11 = 1
        L_0x0476:
            if (r11 != 0) goto L_0x0495
            r0 = 3
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r1 = 0
            r0[r1] = r7
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r9)
            r2 = 1
            r0[r2] = r1
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r10)
            r2 = 2
            r0[r2] = r1
            java.lang.String r1 = "Invalid action \"%s\" for state:\n  NGA is Assistant = %b\n  SysUI is NGA UI = %b"
            java.lang.String r0 = java.lang.String.format(r1, r0)
            android.util.Log.w(r8, r0)
        L_0x0495:
            r19.run()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.assist.uihints.NgaMessageHandler.processBundle(android.os.Bundle, java.lang.Runnable):void");
    }
}
