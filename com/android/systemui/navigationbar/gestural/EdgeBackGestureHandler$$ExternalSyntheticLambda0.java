package com.android.systemui.navigationbar.gestural;

import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class EdgeBackGestureHandler$$ExternalSyntheticLambda0 implements InputChannelCompat$InputEventListener {
    public final /* synthetic */ EdgeBackGestureHandler f$0;

    public /* synthetic */ EdgeBackGestureHandler$$ExternalSyntheticLambda0(EdgeBackGestureHandler edgeBackGestureHandler) {
        this.f$0 = edgeBackGestureHandler;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v18, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x01ae  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01b1  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01e7  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01ee  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x01fe  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0216  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0219  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onInputEvent(android.view.InputEvent r20) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler r0 = r0.f$0
            java.util.Objects.requireNonNull(r0)
            boolean r2 = r1 instanceof android.view.MotionEvent
            if (r2 != 0) goto L_0x000f
            goto L_0x03b0
        L_0x000f:
            android.view.MotionEvent r1 = (android.view.MotionEvent) r1
            int r2 = r1.getActionMasked()
            java.lang.String r3 = "NoBackGesture"
            r4 = 0
            r5 = 2
            r6 = 5
            r7 = 1
            if (r2 != 0) goto L_0x028c
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r8 = "Start gesture: "
            r2.append(r8)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r3, r2)
            com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver r2 = r0.mInputEventReceiver
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver$1 r2 = r2.mReceiver
            r2.setBatchingEnabled(r4)
            float r2 = r1.getX()
            int r3 = r0.mEdgeWidthLeft
            int r8 = r0.mLeftInset
            int r3 = r3 + r8
            float r3 = (float) r3
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 > 0) goto L_0x004b
            r2 = r7
            goto L_0x004c
        L_0x004b:
            r2 = r4
        L_0x004c:
            r0.mIsOnLeftEdge = r2
            r2 = 0
            r0.mMLResults = r2
            r0.mLogGesture = r4
            r0.mInRejectedExclusion = r4
            float r2 = r1.getX()
            int r2 = (int) r2
            float r3 = r1.getY()
            int r3 = (int) r3
            float r3 = (float) r3
            android.graphics.Point r8 = r0.mDisplaySize
            int r9 = r8.y
            float r9 = (float) r9
            float r10 = r0.mBottomGestureHeight
            float r9 = r9 - r10
            int r3 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r3 < 0) goto L_0x006d
            goto L_0x0080
        L_0x006d:
            int r3 = r0.mEdgeWidthLeft
            int r9 = r0.mLeftInset
            int r3 = r3 + r9
            int r3 = r3 * r5
            if (r2 <= r3) goto L_0x0082
            int r3 = r8.x
            int r8 = r0.mEdgeWidthRight
            int r9 = r0.mRightInset
            int r8 = r8 + r9
            int r8 = r8 * r5
            int r3 = r3 - r8
            if (r2 >= r3) goto L_0x0082
        L_0x0080:
            r2 = r4
            goto L_0x0083
        L_0x0082:
            r2 = r7
        L_0x0083:
            boolean r3 = r0.mDisabledForQuickstep
            if (r3 != 0) goto L_0x01e9
            boolean r3 = r0.mIsBackGestureAllowed
            if (r3 == 0) goto L_0x01e9
            if (r2 == 0) goto L_0x01e9
            boolean r3 = r0.mGestureBlockingActivityRunning
            if (r3 != 0) goto L_0x01e9
            int r3 = r0.mSysUiFlags
            boolean r3 = androidx.leanback.R$color.isBackGestureDisabled(r3)
            if (r3 != 0) goto L_0x01e9
            float r3 = r1.getX()
            int r3 = (int) r3
            float r9 = r1.getY()
            int r9 = (int) r9
            boolean r10 = r0.mIsInPipMode
            if (r10 == 0) goto L_0x00b1
            android.graphics.Rect r10 = r0.mPipExcludedBounds
            boolean r10 = r10.contains(r3, r9)
            if (r10 == 0) goto L_0x00b1
            r10 = r7
            goto L_0x00b2
        L_0x00b1:
            r10 = r4
        L_0x00b2:
            if (r10 != 0) goto L_0x01e4
            android.graphics.Rect r10 = r0.mNavBarOverlayExcludedBounds
            boolean r10 = r10.contains(r3, r9)
            if (r10 == 0) goto L_0x00be
            goto L_0x01e4
        L_0x00be:
            java.util.Map<java.lang.String, java.lang.Integer> r10 = r0.mVocab
            r11 = -1
            if (r10 == 0) goto L_0x00d4
            java.lang.String r12 = r0.mPackageName
            java.lang.Integer r13 = java.lang.Integer.valueOf(r11)
            java.lang.Object r10 = r10.getOrDefault(r12, r13)
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r10 = r10.intValue()
            goto L_0x00d5
        L_0x00d4:
            r10 = r11
        L_0x00d5:
            int r12 = r0.mEdgeWidthLeft
            int r13 = r0.mLeftInset
            int r12 = r12 + r13
            if (r3 < r12) goto L_0x00eb
            android.graphics.Point r12 = r0.mDisplaySize
            int r12 = r12.x
            int r14 = r0.mEdgeWidthRight
            int r12 = r12 - r14
            int r14 = r0.mRightInset
            int r12 = r12 - r14
            if (r3 < r12) goto L_0x00e9
            goto L_0x00eb
        L_0x00e9:
            r12 = r4
            goto L_0x00ec
        L_0x00eb:
            r12 = r7
        L_0x00ec:
            if (r12 == 0) goto L_0x016d
            int r14 = r0.mMLEnableWidth
            int r13 = r13 + r14
            if (r3 < r13) goto L_0x0100
            android.graphics.Point r13 = r0.mDisplaySize
            int r13 = r13.x
            int r13 = r13 - r14
            int r14 = r0.mRightInset
            int r13 = r13 - r14
            if (r3 < r13) goto L_0x00fe
            goto L_0x0100
        L_0x00fe:
            r13 = r4
            goto L_0x0101
        L_0x0100:
            r13 = r7
        L_0x0101:
            if (r13 != 0) goto L_0x016d
            boolean r13 = r0.mUseMLModel
            if (r13 == 0) goto L_0x016d
            if (r10 != r11) goto L_0x010c
            r8 = r9
            r5 = r11
            goto L_0x0165
        L_0x010c:
            double r13 = (double) r3
            android.graphics.Point r11 = r0.mDisplaySize
            int r11 = r11.x
            r20 = r9
            double r8 = (double) r11
            r15 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r8 = r8 / r15
            int r8 = (r13 > r8 ? 1 : (r13 == r8 ? 0 : -1))
            if (r8 > 0) goto L_0x011e
            r8 = r3
            r5 = r7
            goto L_0x0120
        L_0x011e:
            int r8 = r11 - r3
        L_0x0120:
            java.lang.Object[] r6 = new java.lang.Object[r6]
            long[] r9 = new long[r7]
            long r13 = (long) r11
            r9[r4] = r13
            r6[r4] = r9
            long[] r9 = new long[r7]
            long r13 = (long) r8
            r9[r4] = r13
            r6[r7] = r9
            long[] r8 = new long[r7]
            long r13 = (long) r5
            r8[r4] = r13
            r5 = 2
            r6[r5] = r8
            long[] r5 = new long[r7]
            long r8 = (long) r10
            r5[r4] = r8
            r8 = 3
            r6[r8] = r5
            long[] r5 = new long[r7]
            r8 = r20
            long r13 = (long) r8
            r5[r4] = r13
            r9 = 4
            r6[r9] = r5
            androidx.coordinatorlayout.R$styleable r5 = r0.mBackGestureTfClassifierProvider
            float r5 = r5.predict(r6)
            r0.mMLResults = r5
            r6 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 != 0) goto L_0x015b
            r5 = -1
            r11 = r5
            goto L_0x0164
        L_0x015b:
            float r6 = r0.mMLModelThreshold
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 < 0) goto L_0x0163
            r11 = r7
            goto L_0x0164
        L_0x0163:
            r11 = r4
        L_0x0164:
            r5 = -1
        L_0x0165:
            if (r11 == r5) goto L_0x016e
            if (r11 != r7) goto L_0x016b
            r12 = r7
            goto L_0x016e
        L_0x016b:
            r12 = r4
            goto L_0x016e
        L_0x016d:
            r8 = r9
        L_0x016e:
            com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$LogArray r5 = r0.mPredictionLog
            r6 = 6
            java.lang.Object[] r6 = new java.lang.Object[r6]
            long r13 = java.lang.System.currentTimeMillis()
            java.lang.Long r9 = java.lang.Long.valueOf(r13)
            r6[r4] = r9
            java.lang.Integer r9 = java.lang.Integer.valueOf(r3)
            r6[r7] = r9
            java.lang.Integer r9 = java.lang.Integer.valueOf(r8)
            r11 = 2
            r6[r11] = r9
            java.lang.Integer r9 = java.lang.Integer.valueOf(r10)
            r10 = 3
            r6[r10] = r9
            float r9 = r0.mMLResults
            java.lang.Float r9 = java.lang.Float.valueOf(r9)
            r10 = 4
            r6[r10] = r9
            java.lang.Integer r9 = java.lang.Integer.valueOf(r12)
            r10 = 5
            r6[r10] = r9
            java.lang.String r9 = "Prediction [%d,%d,%d,%d,%f,%d]"
            java.lang.String r6 = java.lang.String.format(r9, r6)
            r5.log(r6)
            boolean r5 = r0.mIsNavBarShownTransiently
            if (r5 == 0) goto L_0x01b1
            r0.mLogGesture = r7
            goto L_0x01e5
        L_0x01b1:
            android.graphics.Region r5 = r0.mExcludeRegion
            boolean r5 = r5.contains(r3, r8)
            if (r5 == 0) goto L_0x01d9
            if (r12 == 0) goto L_0x01e4
            com.android.systemui.recents.OverviewProxyService r13 = r0.mOverviewProxyService
            r14 = 0
            r15 = -1
            r16 = -1
            r17 = 0
            boolean r3 = r0.mIsOnLeftEdge
            r18 = r3 ^ 1
            r13.notifyBackAction(r14, r15, r16, r17, r18)
            android.graphics.PointF r3 = r0.mEndPoint
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            r3.x = r5
            r3.y = r5
            r0.mLogGesture = r7
            r3 = 3
            r0.logGesture(r3)
            goto L_0x01e4
        L_0x01d9:
            android.graphics.Region r5 = r0.mUnrestrictedExcludeRegion
            boolean r3 = r5.contains(r3, r8)
            r0.mInRejectedExclusion = r3
            r0.mLogGesture = r7
            goto L_0x01e5
        L_0x01e4:
            r12 = r4
        L_0x01e5:
            if (r12 == 0) goto L_0x01e9
            r3 = r7
            goto L_0x01ea
        L_0x01e9:
            r3 = r4
        L_0x01ea:
            r0.mAllowGesture = r3
            if (r3 == 0) goto L_0x01fa
            com.android.systemui.plugins.NavigationEdgeBackPlugin r3 = r0.mEdgeBackPlugin
            boolean r5 = r0.mIsOnLeftEdge
            r3.setIsLeftPanel(r5)
            com.android.systemui.plugins.NavigationEdgeBackPlugin r3 = r0.mEdgeBackPlugin
            r3.onMotionEvent(r1)
        L_0x01fa:
            boolean r3 = r0.mLogGesture
            if (r3 == 0) goto L_0x0214
            android.graphics.PointF r3 = r0.mDownPoint
            float r5 = r1.getX()
            float r1 = r1.getY()
            r3.set(r5, r1)
            android.graphics.PointF r1 = r0.mEndPoint
            r3 = -1082130432(0xffffffffbf800000, float:-1.0)
            r1.set(r3, r3)
            r0.mThresholdCrossed = r4
        L_0x0214:
            if (r2 == 0) goto L_0x0219
            com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$LogArray r1 = r0.mGestureLogInsideInsets
            goto L_0x021b
        L_0x0219:
            com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$LogArray r1 = r0.mGestureLogOutsideInsets
        L_0x021b:
            r2 = 11
            java.lang.Object[] r2 = new java.lang.Object[r2]
            long r5 = java.lang.System.currentTimeMillis()
            java.lang.Long r3 = java.lang.Long.valueOf(r5)
            r2[r4] = r3
            boolean r3 = r0.mAllowGesture
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            r2[r7] = r3
            boolean r3 = r0.mIsOnLeftEdge
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            r4 = 2
            r2[r4] = r3
            boolean r3 = r0.mIsBackGestureAllowed
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            r4 = 3
            r2[r4] = r3
            int r3 = r0.mSysUiFlags
            boolean r3 = androidx.leanback.R$color.isBackGestureDisabled(r3)
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            r4 = 4
            r2[r4] = r3
            android.graphics.Point r3 = r0.mDisplaySize
            r4 = 5
            r2[r4] = r3
            int r3 = r0.mEdgeWidthLeft
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r4 = 6
            r2[r4] = r3
            int r3 = r0.mLeftInset
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r4 = 7
            r2[r4] = r3
            int r3 = r0.mEdgeWidthRight
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r4 = 8
            r2[r4] = r3
            r3 = 9
            int r4 = r0.mRightInset
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r2[r3] = r4
            r3 = 10
            android.graphics.Region r4 = r0.mExcludeRegion
            r2[r3] = r4
            java.lang.String r3 = "Gesture [%d,alw=%B,%B,%B,%B,disp=%s,wl=%d,il=%d,wr=%d,ir=%d,excl=%s]"
            java.lang.String r2 = java.lang.String.format(r3, r2)
            r1.log(r2)
            goto L_0x038c
        L_0x028c:
            boolean r5 = r0.mAllowGesture
            if (r5 != 0) goto L_0x0294
            boolean r5 = r0.mLogGesture
            if (r5 == 0) goto L_0x038c
        L_0x0294:
            boolean r5 = r0.mThresholdCrossed
            if (r5 != 0) goto L_0x0383
            android.graphics.PointF r5 = r0.mEndPoint
            float r6 = r1.getX()
            int r6 = (int) r6
            float r6 = (float) r6
            r5.x = r6
            android.graphics.PointF r5 = r0.mEndPoint
            float r6 = r1.getY()
            int r6 = (int) r6
            float r6 = (float) r6
            r5.y = r6
            r5 = 5
            if (r2 != r5) goto L_0x02c3
            boolean r2 = r0.mAllowGesture
            if (r2 == 0) goto L_0x02bf
            r2 = 6
            r0.logGesture(r2)
            java.lang.String r2 = "Cancel back: multitouch"
            android.util.Log.d(r3, r2)
            r0.cancelGesture(r1)
        L_0x02bf:
            r0.mLogGesture = r4
            goto L_0x03b0
        L_0x02c3:
            r5 = 2
            if (r2 != r5) goto L_0x0383
            long r5 = r1.getEventTime()
            long r8 = r1.getDownTime()
            long r5 = r5 - r8
            int r2 = r0.mLongPressTimeout
            long r8 = (long) r2
            int r2 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            java.lang.String r5 = "  "
            if (r2 <= 0) goto L_0x030a
            boolean r2 = r0.mAllowGesture
            if (r2 == 0) goto L_0x0306
            r2 = 7
            r0.logGesture(r2)
            r0.cancelGesture(r1)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = "Cancel back [longpress]: "
            r2.append(r6)
            long r6 = r1.getEventTime()
            r2.append(r6)
            r2.append(r5)
            long r6 = r1.getDownTime()
            r2.append(r6)
            r2.append(r5)
            int r1 = r0.mLongPressTimeout
            com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(r2, r1, r3)
        L_0x0306:
            r0.mLogGesture = r4
            goto L_0x03b0
        L_0x030a:
            float r2 = r1.getX()
            android.graphics.PointF r6 = r0.mDownPoint
            float r6 = r6.x
            float r2 = r2 - r6
            float r2 = java.lang.Math.abs(r2)
            float r6 = r1.getY()
            android.graphics.PointF r8 = r0.mDownPoint
            float r8 = r8.y
            float r6 = r6 - r8
            float r6 = java.lang.Math.abs(r6)
            int r8 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r8 <= 0) goto L_0x035f
            float r8 = r0.mTouchSlop
            int r8 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x035f
            boolean r7 = r0.mAllowGesture
            if (r7 == 0) goto L_0x035c
            r7 = 8
            r0.logGesture(r7)
            r0.cancelGesture(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r7 = "Cancel back [vertical move]: "
            r1.append(r7)
            r1.append(r6)
            r1.append(r5)
            r1.append(r2)
            r1.append(r5)
            float r2 = r0.mTouchSlop
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r3, r1)
        L_0x035c:
            r0.mLogGesture = r4
            goto L_0x03b0
        L_0x035f:
            int r3 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r3 <= 0) goto L_0x0383
            float r3 = r0.mTouchSlop
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x0383
            boolean r2 = r0.mAllowGesture
            if (r2 == 0) goto L_0x037f
            r0.mThresholdCrossed = r7
            android.view.InputMonitor r2 = r0.mInputMonitor
            r2.pilferPointers()
            com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver r2 = r0.mInputEventReceiver
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver$1 r2 = r2.mReceiver
            r2.setBatchingEnabled(r7)
            goto L_0x0383
        L_0x037f:
            r2 = 5
            r0.logGesture(r2)
        L_0x0383:
            boolean r2 = r0.mAllowGesture
            if (r2 == 0) goto L_0x038c
            com.android.systemui.plugins.NavigationEdgeBackPlugin r2 = r0.mEdgeBackPlugin
            r2.onMotionEvent(r1)
        L_0x038c:
            com.android.systemui.tracing.ProtoTracer r0 = r0.mProtoTracer
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.shared.tracing.FrameProtoTracer<com.google.protobuf.nano.MessageNano, com.android.systemui.tracing.nano.SystemUiTraceFileProto, com.android.systemui.tracing.nano.SystemUiTraceEntryProto, com.android.systemui.tracing.nano.SystemUiTraceProto> r0 = r0.mProtoTracer
            java.util.Objects.requireNonNull(r0)
            boolean r1 = r0.mEnabled
            if (r1 == 0) goto L_0x03b0
            boolean r1 = r0.mFrameScheduled
            if (r1 == 0) goto L_0x039f
            goto L_0x03b0
        L_0x039f:
            android.view.Choreographer r1 = r0.mChoreographer
            if (r1 != 0) goto L_0x03a9
            android.view.Choreographer r1 = android.view.Choreographer.getMainThreadInstance()
            r0.mChoreographer = r1
        L_0x03a9:
            android.view.Choreographer r1 = r0.mChoreographer
            r1.postFrameCallback(r0)
            r0.mFrameScheduled = r7
        L_0x03b0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0.onInputEvent(android.view.InputEvent):void");
    }
}
