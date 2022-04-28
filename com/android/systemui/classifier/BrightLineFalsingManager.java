package com.android.systemui.classifier;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.Uri;
import android.os.Build;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda7;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingDataProvider;
import com.android.systemui.classifier.HistoryTracker;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.phone.DozeParameters$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public final class BrightLineFalsingManager implements FalsingManager {
    public static final boolean DEBUG = Log.isLoggable("FalsingManager", 3);
    public static final ArrayDeque RECENT_INFO_LOG = new ArrayDeque(41);
    public static final ArrayDeque RECENT_SWIPES = new ArrayDeque(21);
    public AccessibilityManager mAccessibilityManager;
    public final C07172 mBeliefListener;
    public final Set mClassifiers;
    public final FalsingDataProvider mDataProvider;
    public boolean mDestroyed;
    public final DoubleTapClassifier mDoubleTapClassifier;
    public final ArrayList mFalsingBeliefListeners = new ArrayList();
    public ArrayList mFalsingTapListeners = new ArrayList();
    public final C07183 mGestureFinalizedListener;
    public final HistoryTracker mHistoryTracker;
    public final KeyguardStateController mKeyguardStateController;
    public final MetricsLogger mMetricsLogger;
    public int mPriorInteractionType;
    public Collection<FalsingClassifier.Result> mPriorResults;
    public final C07161 mSessionListener;
    public final SingleTapClassifier mSingleTapClassifier;
    public final boolean mTestHarness;

    public static class XYDt {
        public final int mDT;

        /* renamed from: mX */
        public final int f40mX;

        /* renamed from: mY */
        public final int f41mY;

        public final String toString() {
            return this.f40mX + "," + this.f41mY + "," + this.mDT;
        }

        public XYDt(int i, int i2, int i3) {
            this.f40mX = i;
            this.f41mY = i2;
            this.mDT = i3;
        }
    }

    public final void cleanupInternal() {
        this.mDestroyed = true;
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        C07161 r1 = this.mSessionListener;
        Objects.requireNonNull(falsingDataProvider);
        falsingDataProvider.mSessionListeners.remove(r1);
        FalsingDataProvider falsingDataProvider2 = this.mDataProvider;
        C07183 r12 = this.mGestureFinalizedListener;
        Objects.requireNonNull(falsingDataProvider2);
        falsingDataProvider2.mGestureFinalizedListeners.remove(r12);
        this.mClassifiers.forEach(BrightLineFalsingManager$$ExternalSyntheticLambda1.INSTANCE);
        this.mFalsingBeliefListeners.clear();
        HistoryTracker historyTracker = this.mHistoryTracker;
        C07172 r2 = this.mBeliefListener;
        Objects.requireNonNull(historyTracker);
        historyTracker.mBeliefListeners.remove(r2);
    }

    public final boolean isClassifierEnabled() {
        return true;
    }

    public final boolean isReportingEnabled() {
        return false;
    }

    public final boolean isUnlockingDisabled() {
        return false;
    }

    public final void onSuccessfulUnlock() {
    }

    public final Uri reportRejectedTouch() {
        return null;
    }

    public final boolean shouldEnforceBouncer() {
        return false;
    }

    public final boolean skipFalsing(int i) {
        boolean z;
        if (i != 16 && this.mKeyguardStateController.isShowing() && !this.mTestHarness) {
            FalsingDataProvider falsingDataProvider = this.mDataProvider;
            Objects.requireNonNull(falsingDataProvider);
            if (!falsingDataProvider.mJustUnlockedWithFace) {
                FalsingDataProvider falsingDataProvider2 = this.mDataProvider;
                Objects.requireNonNull(falsingDataProvider2);
                if (falsingDataProvider2.mBatteryController.isWirelessCharging() || falsingDataProvider2.mDockManager.isDocked()) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z && !this.mAccessibilityManager.isTouchExplorationEnabled()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static class DebugSwipeRecord {
        public final int mInteractionType;
        public final boolean mIsFalse;
        public final List<XYDt> mRecentMotionEvents;

        public DebugSwipeRecord(boolean z, int i, List<XYDt> list) {
            this.mIsFalse = z;
            this.mInteractionType = i;
            this.mRecentMotionEvents = list;
        }
    }

    public static void logDebug(String str) {
        if (DEBUG) {
            Log.d("FalsingManager", str, (Throwable) null);
        }
    }

    public static void logInfo(String str) {
        Log.i("FalsingManager", str);
        RECENT_INFO_LOG.add(str);
        while (true) {
            ArrayDeque arrayDeque = RECENT_INFO_LOG;
            if (arrayDeque.size() > 40) {
                arrayDeque.remove();
            } else {
                return;
            }
        }
    }

    public final void addFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        this.mFalsingBeliefListeners.add(falsingBeliefListener);
    }

    public final void addTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        this.mFalsingTapListeners.add(falsingTapListener);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        String str;
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("BRIGHTLINE FALSING MANAGER");
        indentingPrintWriter.print("classifierEnabled=");
        indentingPrintWriter.println(1);
        indentingPrintWriter.print("mJustUnlockedWithFace=");
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        Objects.requireNonNull(falsingDataProvider);
        indentingPrintWriter.println(falsingDataProvider.mJustUnlockedWithFace ? 1 : 0);
        indentingPrintWriter.print("isDocked=");
        FalsingDataProvider falsingDataProvider2 = this.mDataProvider;
        Objects.requireNonNull(falsingDataProvider2);
        if (falsingDataProvider2.mBatteryController.isWirelessCharging() || falsingDataProvider2.mDockManager.isDocked()) {
            i = 1;
        } else {
            i = 0;
        }
        indentingPrintWriter.println(i);
        indentingPrintWriter.print("width=");
        FalsingDataProvider falsingDataProvider3 = this.mDataProvider;
        Objects.requireNonNull(falsingDataProvider3);
        indentingPrintWriter.println(falsingDataProvider3.mWidthPixels);
        indentingPrintWriter.print("height=");
        FalsingDataProvider falsingDataProvider4 = this.mDataProvider;
        Objects.requireNonNull(falsingDataProvider4);
        indentingPrintWriter.println(falsingDataProvider4.mHeightPixels);
        indentingPrintWriter.println();
        ArrayDeque arrayDeque = RECENT_SWIPES;
        if (arrayDeque.size() != 0) {
            indentingPrintWriter.println("Recent swipes:");
            indentingPrintWriter.increaseIndent();
            Iterator it = arrayDeque.iterator();
            while (it.hasNext()) {
                DebugSwipeRecord debugSwipeRecord = (DebugSwipeRecord) it.next();
                Objects.requireNonNull(debugSwipeRecord);
                StringJoiner stringJoiner = new StringJoiner(",");
                StringJoiner add = stringJoiner.add(Integer.toString(1));
                if (debugSwipeRecord.mIsFalse) {
                    str = "1";
                } else {
                    str = "0";
                }
                add.add(str).add(Integer.toString(debugSwipeRecord.mInteractionType));
                for (XYDt xYDt : debugSwipeRecord.mRecentMotionEvents) {
                    stringJoiner.add(xYDt.toString());
                }
                indentingPrintWriter.println(stringJoiner.toString());
                indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
        } else {
            indentingPrintWriter.println("No recent swipes");
        }
        indentingPrintWriter.println();
        indentingPrintWriter.println("Recent falsing info:");
        indentingPrintWriter.increaseIndent();
        Iterator it2 = RECENT_INFO_LOG.iterator();
        while (it2.hasNext()) {
            indentingPrintWriter.println((String) it2.next());
        }
        indentingPrintWriter.println();
    }

    public final boolean isFalseDoubleTap() {
        if (this.mDestroyed) {
            Log.wtf("FalsingManager", "Tried to use FalsingManager after being destroyed!");
        }
        if (skipFalsing(7)) {
            this.mPriorResults = Collections.singleton(FalsingClassifier.Result.passed(1.0d));
            logDebug("Skipped falsing");
            return false;
        }
        DoubleTapClassifier doubleTapClassifier = this.mDoubleTapClassifier;
        this.mHistoryTracker.falseBelief();
        this.mHistoryTracker.falseConfidence();
        Objects.requireNonNull(doubleTapClassifier);
        FalsingClassifier.Result calculateFalsingResult = doubleTapClassifier.calculateFalsingResult(7);
        this.mPriorResults = Collections.singleton(calculateFalsingResult);
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("False Double Tap: ");
        m.append(calculateFalsingResult.mFalsed);
        logDebug(m.toString());
        return calculateFalsingResult.mFalsed;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00d5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isFalseTap(int r13) {
        /*
            r12 = this;
            boolean r0 = r12.mDestroyed
            if (r0 == 0) goto L_0x000b
            java.lang.String r0 = "FalsingManager"
            java.lang.String r1 = "Tried to use FalsingManager after being destroyed!"
            android.util.Log.wtf(r0, r1)
        L_0x000b:
            r0 = 7
            boolean r0 = r12.skipFalsing(r0)
            r1 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r3 = 0
            if (r0 == 0) goto L_0x0025
            com.android.systemui.classifier.FalsingClassifier$Result r13 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r1)
            java.util.Set r13 = java.util.Collections.singleton(r13)
            r12.mPriorResults = r13
            java.lang.String r12 = "Skipped falsing"
            logDebug(r12)
            return r3
        L_0x0025:
            r4 = 4591870180066957722(0x3fb999999999999a, double:0.1)
            r0 = 1
            r8 = 0
            if (r13 == 0) goto L_0x0046
            if (r13 == r0) goto L_0x0044
            r6 = 2
            if (r13 == r6) goto L_0x003e
            r6 = 3
            if (r13 == r6) goto L_0x0038
            goto L_0x0046
        L_0x0038:
            r6 = 4603579539098121011(0x3fe3333333333333, double:0.6)
            goto L_0x0047
        L_0x003e:
            r6 = 4599075939470750515(0x3fd3333333333333, double:0.3)
            goto L_0x0047
        L_0x0044:
            r6 = r4
            goto L_0x0047
        L_0x0046:
            r6 = r8
        L_0x0047:
            com.android.systemui.classifier.SingleTapClassifier r13 = r12.mSingleTapClassifier
            com.android.systemui.classifier.FalsingDataProvider r10 = r12.mDataProvider
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.classifier.TimeLimitedMotionEventBuffer r10 = r10.mRecentMotionEvents
            boolean r10 = r10.isEmpty()
            if (r10 == 0) goto L_0x005e
            com.android.systemui.classifier.FalsingDataProvider r10 = r12.mDataProvider
            java.util.Objects.requireNonNull(r10)
            java.util.List<android.view.MotionEvent> r10 = r10.mPriorMotionEvents
            goto L_0x0065
        L_0x005e:
            com.android.systemui.classifier.FalsingDataProvider r10 = r12.mDataProvider
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.classifier.TimeLimitedMotionEventBuffer r10 = r10.mRecentMotionEvents
        L_0x0065:
            com.android.systemui.classifier.FalsingClassifier$Result r13 = r13.isTap(r10, r6)
            java.util.Set r6 = java.util.Collections.singleton(r13)
            r12.mPriorResults = r6
            boolean r6 = r13.mFalsed
            if (r6 != 0) goto L_0x00d5
            com.android.systemui.classifier.FalsingDataProvider r13 = r12.mDataProvider
            java.util.Objects.requireNonNull(r13)
            boolean r13 = r13.mJustUnlockedWithFace
            if (r13 == 0) goto L_0x008c
            com.android.systemui.classifier.FalsingClassifier$Result r13 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r1)
            java.util.Set r13 = java.util.Collections.singleton(r13)
            r12.mPriorResults = r13
            java.lang.String r12 = "False Single Tap: false (face detected)"
            logDebug(r12)
            return r3
        L_0x008c:
            boolean r13 = r12.isFalseDoubleTap()
            if (r13 != 0) goto L_0x0098
            java.lang.String r12 = "False Single Tap: false (double tapped)"
            logDebug(r12)
            return r3
        L_0x0098:
            com.android.systemui.classifier.HistoryTracker r13 = r12.mHistoryTracker
            double r1 = r13.falseBelief()
            r6 = 4604480259023595110(0x3fe6666666666666, double:0.7)
            int r13 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r13 <= 0) goto L_0x00c5
            com.android.systemui.classifier.FalsingClassifier$Result r13 = new com.android.systemui.classifier.FalsingClassifier$Result
            r7 = 1
            java.lang.String r10 = "BrightLineFalsingManager"
            java.lang.String r11 = "bad history"
            r6 = r13
            r6.<init>(r7, r8, r10, r11)
            java.util.Set r13 = java.util.Collections.singleton(r13)
            r12.mPriorResults = r13
            java.lang.String r13 = "False Single Tap: true (bad history)"
            logDebug(r13)
            java.util.ArrayList r12 = r12.mFalsingTapListeners
            com.android.systemui.classifier.BrightLineFalsingManager$$ExternalSyntheticLambda0 r13 = com.android.systemui.classifier.BrightLineFalsingManager$$ExternalSyntheticLambda0.INSTANCE
            r12.forEach(r13)
            return r0
        L_0x00c5:
            com.android.systemui.classifier.FalsingClassifier$Result r13 = com.android.systemui.classifier.FalsingClassifier.Result.passed(r4)
            java.util.Set r13 = java.util.Collections.singleton(r13)
            r12.mPriorResults = r13
            java.lang.String r12 = "False Single Tap: false (default)"
            logDebug(r12)
            return r3
        L_0x00d5:
            java.lang.String r12 = "False Single Tap: "
            java.lang.StringBuilder r12 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r12)
            boolean r0 = r13.mFalsed
            r12.append(r0)
            java.lang.String r0 = " (simple)"
            r12.append(r0)
            java.lang.String r12 = r12.toString()
            logDebug(r12)
            boolean r12 = r13.mFalsed
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.classifier.BrightLineFalsingManager.isFalseTap(int):boolean");
    }

    public final boolean isFalseTouch(int i) {
        if (this.mDestroyed) {
            Log.wtf("FalsingManager", "Tried to use FalsingManager after being destroyed!");
        }
        this.mPriorInteractionType = i;
        if (skipFalsing(i)) {
            this.mPriorResults = Collections.singleton(FalsingClassifier.Result.passed(1.0d));
            logDebug("Skipped falsing");
            return false;
        }
        boolean[] zArr = {false};
        this.mPriorResults = (Collection) this.mClassifiers.stream().map(new BrightLineFalsingManager$$ExternalSyntheticLambda2(this, i, zArr)).collect(Collectors.toList());
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("False Gesture: ");
        m.append(zArr[0]);
        logDebug(m.toString());
        return zArr[0];
    }

    public final boolean isSimpleTap() {
        if (this.mDestroyed) {
            Log.wtf("FalsingManager", "Tried to use FalsingManager after being destroyed!");
        }
        SingleTapClassifier singleTapClassifier = this.mSingleTapClassifier;
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        Objects.requireNonNull(falsingDataProvider);
        FalsingClassifier.Result isTap = singleTapClassifier.isTap(falsingDataProvider.mRecentMotionEvents, 0.0d);
        this.mPriorResults = Collections.singleton(isTap);
        return !isTap.mFalsed;
    }

    public final void onProximityEvent(FalsingManager.ProximityEvent proximityEvent) {
        this.mClassifiers.forEach(new BubbleController$$ExternalSyntheticLambda7(proximityEvent, 1));
    }

    public final void removeFalsingBeliefListener(FalsingManager.FalsingBeliefListener falsingBeliefListener) {
        this.mFalsingBeliefListeners.remove(falsingBeliefListener);
    }

    public final void removeTapListener(FalsingManager.FalsingTapListener falsingTapListener) {
        this.mFalsingTapListeners.remove(falsingTapListener);
    }

    public BrightLineFalsingManager(FalsingDataProvider falsingDataProvider, MetricsLogger metricsLogger, Set<FalsingClassifier> set, SingleTapClassifier singleTapClassifier, DoubleTapClassifier doubleTapClassifier, HistoryTracker historyTracker, KeyguardStateController keyguardStateController, AccessibilityManager accessibilityManager, boolean z) {
        C07161 r0 = new FalsingDataProvider.SessionListener() {
            public final void onSessionEnded() {
                BrightLineFalsingManager.this.mClassifiers.forEach(BrightLineFalsingManager$1$$ExternalSyntheticLambda0.INSTANCE);
            }

            public final void onSessionStarted() {
                BrightLineFalsingManager.this.mClassifiers.forEach(BrightLineFalsingManager$1$$ExternalSyntheticLambda1.INSTANCE);
            }
        };
        this.mSessionListener = r0;
        C07172 r1 = new HistoryTracker.BeliefListener() {
            public final void onBeliefChanged(double d) {
                BrightLineFalsingManager.logInfo(String.format("{belief=%s confidence=%s}", new Object[]{Double.valueOf(BrightLineFalsingManager.this.mHistoryTracker.falseBelief()), Double.valueOf(BrightLineFalsingManager.this.mHistoryTracker.falseConfidence())}));
                if (d > 0.9d) {
                    BrightLineFalsingManager.this.mFalsingBeliefListeners.forEach(BrightLineFalsingManager$2$$ExternalSyntheticLambda0.INSTANCE);
                    BrightLineFalsingManager.logInfo("Triggering False Event (Threshold: 0.9)");
                }
            }
        };
        this.mBeliefListener = r1;
        C07183 r2 = new FalsingDataProvider.GestureFinalizedListener() {
            public final void onGestureFinalized(long j) {
                double d;
                BrightLineFalsingManager brightLineFalsingManager = BrightLineFalsingManager.this;
                Collection<FalsingClassifier.Result> collection = brightLineFalsingManager.mPriorResults;
                if (collection != null) {
                    boolean anyMatch = collection.stream().anyMatch(BrightLineFalsingManager$3$$ExternalSyntheticLambda1.INSTANCE);
                    BrightLineFalsingManager.this.mPriorResults.forEach(BrightLineFalsingManager$3$$ExternalSyntheticLambda0.INSTANCE);
                    if (Build.IS_ENG || Build.IS_USERDEBUG) {
                        ArrayDeque arrayDeque = BrightLineFalsingManager.RECENT_SWIPES;
                        BrightLineFalsingManager brightLineFalsingManager2 = BrightLineFalsingManager.this;
                        int i = brightLineFalsingManager2.mPriorInteractionType;
                        FalsingDataProvider falsingDataProvider = brightLineFalsingManager2.mDataProvider;
                        Objects.requireNonNull(falsingDataProvider);
                        arrayDeque.add(new DebugSwipeRecord(anyMatch, i, (List) falsingDataProvider.mRecentMotionEvents.stream().map(DozeParameters$$ExternalSyntheticLambda0.INSTANCE$1).collect(Collectors.toList())));
                        while (true) {
                            ArrayDeque arrayDeque2 = BrightLineFalsingManager.RECENT_SWIPES;
                            if (arrayDeque2.size() <= 40) {
                                break;
                            }
                            arrayDeque2.remove();
                        }
                    }
                    BrightLineFalsingManager brightLineFalsingManager3 = BrightLineFalsingManager.this;
                    brightLineFalsingManager3.mHistoryTracker.addResults(brightLineFalsingManager3.mPriorResults, j);
                    BrightLineFalsingManager brightLineFalsingManager4 = BrightLineFalsingManager.this;
                    brightLineFalsingManager4.mPriorResults = null;
                    brightLineFalsingManager4.mPriorInteractionType = 7;
                    return;
                }
                SingleTapClassifier singleTapClassifier = brightLineFalsingManager.mSingleTapClassifier;
                FalsingDataProvider falsingDataProvider2 = brightLineFalsingManager.mDataProvider;
                Objects.requireNonNull(falsingDataProvider2);
                if (singleTapClassifier.isTap(falsingDataProvider2.mRecentMotionEvents, 0.0d).mFalsed) {
                    d = 0.7d;
                } else {
                    d = 0.8d;
                }
                BrightLineFalsingManager.this.mHistoryTracker.addResults(Collections.singleton(new FalsingClassifier.Result(true, d, "", "unclassified")), j);
            }
        };
        this.mGestureFinalizedListener = r2;
        this.mPriorInteractionType = 7;
        this.mDataProvider = falsingDataProvider;
        this.mMetricsLogger = metricsLogger;
        this.mClassifiers = set;
        this.mSingleTapClassifier = singleTapClassifier;
        this.mDoubleTapClassifier = doubleTapClassifier;
        this.mHistoryTracker = historyTracker;
        this.mKeyguardStateController = keyguardStateController;
        this.mAccessibilityManager = accessibilityManager;
        this.mTestHarness = z;
        Objects.requireNonNull(falsingDataProvider);
        falsingDataProvider.mSessionListeners.add(r0);
        falsingDataProvider.mGestureFinalizedListeners.add(r2);
        Objects.requireNonNull(historyTracker);
        historyTracker.mBeliefListeners.add(r1);
    }
}
