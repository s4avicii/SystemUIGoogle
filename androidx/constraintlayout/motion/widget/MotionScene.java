package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R$styleable;
import androidx.constraintlayout.widget.StateSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public final class MotionScene {
    public ArrayList<Transition> mAbstractTransitionList = new ArrayList<>();
    public HashMap<String, Integer> mConstraintSetIdMap = new HashMap<>();
    public SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray<>();
    public Transition mCurrentTransition = null;
    public int mDefaultDuration = 100;
    public Transition mDefaultTransition = null;
    public SparseIntArray mDeriveMap = new SparseIntArray();
    public MotionEvent mLastTouchDown;
    public float mLastTouchX;
    public float mLastTouchY;
    public int mLayoutDuringTransition = 0;
    public final MotionLayout mMotionLayout;
    public boolean mMotionOutsideRegion = false;
    public boolean mRtl;
    public StateSet mStateSet = null;
    public ArrayList<Transition> mTransitionList = new ArrayList<>();
    public MotionLayout.MyTracker mVelocityTracker;

    public final boolean autoTransition(MotionLayout motionLayout, int i) {
        boolean z;
        if (this.mVelocityTracker != null) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return false;
        }
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            Transition next = it.next();
            int i2 = next.mAutoTransition;
            if (i2 != 0) {
                if (i == next.mConstraintSetStart && (i2 == 4 || i2 == 2)) {
                    motionLayout.setTransition(next);
                    if (next.mAutoTransition == 4) {
                        motionLayout.animateTo(1.0f);
                    } else {
                        motionLayout.setProgress(1.0f);
                    }
                    return true;
                } else if (i == next.mConstraintSetEnd && (i2 == 3 || i2 == 1)) {
                    motionLayout.setTransition(next);
                    if (next.mAutoTransition == 3) {
                        motionLayout.animateTo(0.0f);
                    } else {
                        motionLayout.setProgress(0.0f);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public final ConstraintSet getConstraintSet(int i) {
        int stateGetConstraintID;
        StateSet stateSet = this.mStateSet;
        if (!(stateSet == null || (stateGetConstraintID = stateSet.stateGetConstraintID(i)) == -1)) {
            i = stateGetConstraintID;
        }
        if (this.mConstraintSetMap.get(i) != null) {
            return this.mConstraintSetMap.get(i);
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Warning could not find ConstraintSet id/");
        m.append(Debug.getName(this.mMotionLayout.getContext(), i));
        m.append(" In MotionScene");
        Log.e("MotionScene", m.toString());
        SparseArray<ConstraintSet> sparseArray = this.mConstraintSetMap;
        return sparseArray.get(sparseArray.keyAt(0));
    }

    public final int getId(Context context, String str) {
        int i;
        if (str.contains("/")) {
            i = context.getResources().getIdentifier(str.substring(str.indexOf(47) + 1), "id", context.getPackageName());
        } else {
            i = -1;
        }
        if (i != -1) {
            return i;
        }
        if (str.length() > 1) {
            return Integer.parseInt(str.substring(1));
        }
        Log.e("MotionScene", "error in parsing id");
        return i;
    }

    public final void getKeyFrames(MotionController motionController) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            Transition transition2 = this.mDefaultTransition;
            if (transition2 != null) {
                Iterator<KeyFrames> it = transition2.mKeyFramesList.iterator();
                while (it.hasNext()) {
                    it.next().addFrames(motionController);
                }
                return;
            }
            return;
        }
        Iterator<KeyFrames> it2 = transition.mKeyFramesList.iterator();
        while (it2.hasNext()) {
            it2.next().addFrames(motionController);
        }
    }

    public final float getMaxAcceleration() {
        TouchResponse touchResponse;
        Transition transition = this.mCurrentTransition;
        if (transition == null || (touchResponse = transition.mTouchResponse) == null) {
            return 0.0f;
        }
        Objects.requireNonNull(touchResponse);
        return touchResponse.mMaxAcceleration;
    }

    public final int getStartId() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) {
            return -1;
        }
        return transition.mConstraintSetStart;
    }

    public final void parseConstraintSet(Context context, XmlResourceParser xmlResourceParser) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.mForceId = false;
        int attributeCount = xmlResourceParser.getAttributeCount();
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < attributeCount; i3++) {
            String attributeName = xmlResourceParser.getAttributeName(i3);
            String attributeValue = xmlResourceParser.getAttributeValue(i3);
            Objects.requireNonNull(attributeName);
            if (attributeName.equals("deriveConstraintsFrom")) {
                i2 = getId(context, attributeValue);
            } else if (attributeName.equals("id")) {
                i = getId(context, attributeValue);
                HashMap<String, Integer> hashMap = this.mConstraintSetIdMap;
                int indexOf = attributeValue.indexOf(47);
                if (indexOf >= 0) {
                    attributeValue = attributeValue.substring(indexOf + 1);
                }
                hashMap.put(attributeValue, Integer.valueOf(i));
            }
        }
        if (i != -1) {
            int i4 = this.mMotionLayout.mDebugPath;
            constraintSet.load(context, xmlResourceParser);
            if (i2 != -1) {
                this.mDeriveMap.put(i, i2);
            }
            this.mConstraintSetMap.put(i, constraintSet);
        }
    }

    public final void readConstraintChain(int i) {
        int i2 = this.mDeriveMap.get(i);
        if (i2 > 0) {
            readConstraintChain(this.mDeriveMap.get(i));
            ConstraintSet constraintSet = this.mConstraintSetMap.get(i);
            ConstraintSet constraintSet2 = this.mConstraintSetMap.get(i2);
            if (constraintSet2 == null) {
                Log.e("MotionScene", "invalid deriveConstraintsFrom id");
            }
            Objects.requireNonNull(constraintSet);
            for (Integer next : constraintSet2.mConstraints.keySet()) {
                int intValue = next.intValue();
                ConstraintSet.Constraint constraint = constraintSet2.mConstraints.get(next);
                if (!constraintSet.mConstraints.containsKey(Integer.valueOf(intValue))) {
                    constraintSet.mConstraints.put(Integer.valueOf(intValue), new ConstraintSet.Constraint());
                }
                ConstraintSet.Constraint constraint2 = constraintSet.mConstraints.get(Integer.valueOf(intValue));
                ConstraintSet.Layout layout = constraint2.layout;
                if (!layout.mApply) {
                    layout.copyFrom(constraint.layout);
                }
                ConstraintSet.PropertySet propertySet = constraint2.propertySet;
                if (!propertySet.mApply) {
                    ConstraintSet.PropertySet propertySet2 = constraint.propertySet;
                    propertySet.mApply = propertySet2.mApply;
                    propertySet.visibility = propertySet2.visibility;
                    propertySet.alpha = propertySet2.alpha;
                    propertySet.mProgress = propertySet2.mProgress;
                    propertySet.mVisibilityMode = propertySet2.mVisibilityMode;
                }
                ConstraintSet.Transform transform = constraint2.transform;
                if (!transform.mApply) {
                    transform.copyFrom(constraint.transform);
                }
                ConstraintSet.Motion motion = constraint2.motion;
                if (!motion.mApply) {
                    motion.copyFrom(constraint.motion);
                }
                for (String next2 : constraint.mCustomConstraints.keySet()) {
                    if (!constraint2.mCustomConstraints.containsKey(next2)) {
                        constraint2.mCustomConstraints.put(next2, constraint.mCustomConstraints.get(next2));
                    }
                }
            }
            this.mDeriveMap.put(i, -1);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0013, code lost:
        if (r2 != -1) goto L_0x0018;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setTransition(int r8, int r9) {
        /*
            r7 = this;
            androidx.constraintlayout.widget.StateSet r0 = r7.mStateSet
            r1 = -1
            if (r0 == 0) goto L_0x0016
            int r0 = r0.stateGetConstraintID(r8)
            if (r0 == r1) goto L_0x000c
            goto L_0x000d
        L_0x000c:
            r0 = r8
        L_0x000d:
            androidx.constraintlayout.widget.StateSet r2 = r7.mStateSet
            int r2 = r2.stateGetConstraintID(r9)
            if (r2 == r1) goto L_0x0017
            goto L_0x0018
        L_0x0016:
            r0 = r8
        L_0x0017:
            r2 = r9
        L_0x0018:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionScene$Transition> r3 = r7.mTransitionList
            java.util.Iterator r3 = r3.iterator()
        L_0x001e:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0044
            java.lang.Object r4 = r3.next()
            androidx.constraintlayout.motion.widget.MotionScene$Transition r4 = (androidx.constraintlayout.motion.widget.MotionScene.Transition) r4
            int r5 = r4.mConstraintSetEnd
            if (r5 != r2) goto L_0x0032
            int r6 = r4.mConstraintSetStart
            if (r6 == r0) goto L_0x0038
        L_0x0032:
            if (r5 != r9) goto L_0x001e
            int r5 = r4.mConstraintSetStart
            if (r5 != r8) goto L_0x001e
        L_0x0038:
            r7.mCurrentTransition = r4
            androidx.constraintlayout.motion.widget.TouchResponse r8 = r4.mTouchResponse
            if (r8 == 0) goto L_0x0043
            boolean r7 = r7.mRtl
            r8.setRTL(r7)
        L_0x0043:
            return
        L_0x0044:
            androidx.constraintlayout.motion.widget.MotionScene$Transition r8 = r7.mDefaultTransition
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionScene$Transition> r3 = r7.mAbstractTransitionList
            java.util.Iterator r3 = r3.iterator()
        L_0x004c:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x005e
            java.lang.Object r4 = r3.next()
            androidx.constraintlayout.motion.widget.MotionScene$Transition r4 = (androidx.constraintlayout.motion.widget.MotionScene.Transition) r4
            int r5 = r4.mConstraintSetEnd
            if (r5 != r9) goto L_0x004c
            r8 = r4
            goto L_0x004c
        L_0x005e:
            androidx.constraintlayout.motion.widget.MotionScene$Transition r9 = new androidx.constraintlayout.motion.widget.MotionScene$Transition
            r9.<init>(r7, r8)
            r9.mConstraintSetStart = r0
            r9.mConstraintSetEnd = r2
            if (r0 == r1) goto L_0x006e
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionScene$Transition> r8 = r7.mTransitionList
            r8.add(r9)
        L_0x006e:
            r7.mCurrentTransition = r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionScene.setTransition(int, int):void");
    }

    public final boolean supportTouch() {
        Iterator<Transition> it = this.mTransitionList.iterator();
        while (it.hasNext()) {
            if (it.next().mTouchResponse != null) {
                return true;
            }
        }
        Transition transition = this.mCurrentTransition;
        if (transition == null || transition.mTouchResponse == null) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MotionScene(android.content.Context r9, androidx.constraintlayout.motion.widget.MotionLayout r10, int r11) {
        /*
            r8 = this;
            r8.<init>()
            r0 = 0
            r8.mStateSet = r0
            r8.mCurrentTransition = r0
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r8.mTransitionList = r1
            r8.mDefaultTransition = r0
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r8.mAbstractTransitionList = r1
            android.util.SparseArray r1 = new android.util.SparseArray
            r1.<init>()
            r8.mConstraintSetMap = r1
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            r8.mConstraintSetIdMap = r1
            android.util.SparseIntArray r1 = new android.util.SparseIntArray
            r1.<init>()
            r8.mDeriveMap = r1
            r1 = 100
            r8.mDefaultDuration = r1
            r1 = 0
            r8.mLayoutDuringTransition = r1
            r8.mMotionOutsideRegion = r1
            r8.mMotionLayout = r10
            android.content.res.Resources r10 = r9.getResources()
            android.content.res.XmlResourceParser r10 = r10.getXml(r11)
            int r2 = r10.getEventType()     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
        L_0x0044:
            r3 = 1
            if (r2 == r3) goto L_0x0167
            if (r2 == 0) goto L_0x0155
            r4 = 2
            if (r2 == r4) goto L_0x004e
            goto L_0x0158
        L_0x004e:
            java.lang.String r2 = r10.getName()     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            int r5 = r2.hashCode()     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r6 = -1
            java.lang.String r7 = "MotionScene"
            switch(r5) {
                case -1349929691: goto L_0x0096;
                case -1239391468: goto L_0x008c;
                case 269306229: goto L_0x0083;
                case 312750793: goto L_0x0079;
                case 327855227: goto L_0x006f;
                case 793277014: goto L_0x0067;
                case 1382829617: goto L_0x005d;
                default: goto L_0x005c;
            }
        L_0x005c:
            goto L_0x00a0
        L_0x005d:
            java.lang.String r3 = "StateSet"
            boolean r3 = r2.equals(r3)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            if (r3 == 0) goto L_0x00a0
            r3 = 4
            goto L_0x00a1
        L_0x0067:
            boolean r3 = r2.equals(r7)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            if (r3 == 0) goto L_0x00a0
            r3 = r1
            goto L_0x00a1
        L_0x006f:
            java.lang.String r3 = "OnSwipe"
            boolean r3 = r2.equals(r3)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            if (r3 == 0) goto L_0x00a0
            r3 = r4
            goto L_0x00a1
        L_0x0079:
            java.lang.String r3 = "OnClick"
            boolean r3 = r2.equals(r3)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            if (r3 == 0) goto L_0x00a0
            r3 = 3
            goto L_0x00a1
        L_0x0083:
            java.lang.String r4 = "Transition"
            boolean r4 = r2.equals(r4)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            if (r4 == 0) goto L_0x00a0
            goto L_0x00a1
        L_0x008c:
            java.lang.String r3 = "KeyFrameSet"
            boolean r3 = r2.equals(r3)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            if (r3 == 0) goto L_0x00a0
            r3 = 6
            goto L_0x00a1
        L_0x0096:
            java.lang.String r3 = "ConstraintSet"
            boolean r3 = r2.equals(r3)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            if (r3 == 0) goto L_0x00a0
            r3 = 5
            goto L_0x00a1
        L_0x00a0:
            r3 = r6
        L_0x00a1:
            switch(r3) {
                case 0: goto L_0x013c;
                case 1: goto L_0x0108;
                case 2: goto L_0x00cf;
                case 3: goto L_0x00c0;
                case 4: goto L_0x00b7;
                case 5: goto L_0x00b2;
                case 6: goto L_0x00a6;
                default: goto L_0x00a4;
            }     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
        L_0x00a4:
            goto L_0x0140
        L_0x00a6:
            androidx.constraintlayout.motion.widget.KeyFrames r2 = new androidx.constraintlayout.motion.widget.KeyFrames     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r2.<init>(r9, r10)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            java.util.ArrayList<androidx.constraintlayout.motion.widget.KeyFrames> r3 = r0.mKeyFramesList     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r3.add(r2)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            goto L_0x0158
        L_0x00b2:
            r8.parseConstraintSet(r9, r10)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            goto L_0x0158
        L_0x00b7:
            androidx.constraintlayout.widget.StateSet r2 = new androidx.constraintlayout.widget.StateSet     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r2.<init>(r9, r10)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r8.mStateSet = r2     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            goto L_0x0158
        L_0x00c0:
            java.util.Objects.requireNonNull(r0)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionScene$Transition$TransitionOnClick> r2 = r0.mOnClicks     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            androidx.constraintlayout.motion.widget.MotionScene$Transition$TransitionOnClick r3 = new androidx.constraintlayout.motion.widget.MotionScene$Transition$TransitionOnClick     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r3.<init>(r9, r0, r10)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r2.add(r3)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            goto L_0x0158
        L_0x00cf:
            if (r0 != 0) goto L_0x00fe
            android.content.res.Resources r2 = r9.getResources()     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            java.lang.String r2 = r2.getResourceEntryName(r11)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            int r3 = r10.getLineNumber()     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r4.<init>()     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            java.lang.String r5 = " OnSwipe ("
            r4.append(r5)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r4.append(r2)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            java.lang.String r2 = ".xml:"
            r4.append(r2)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r4.append(r3)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            java.lang.String r2 = ")"
            r4.append(r2)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            java.lang.String r2 = r4.toString()     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            android.util.Log.v(r7, r2)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
        L_0x00fe:
            androidx.constraintlayout.motion.widget.TouchResponse r2 = new androidx.constraintlayout.motion.widget.TouchResponse     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            androidx.constraintlayout.motion.widget.MotionLayout r3 = r8.mMotionLayout     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r2.<init>(r9, r3, r10)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r0.mTouchResponse = r2     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            goto L_0x0158
        L_0x0108:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionScene$Transition> r0 = r8.mTransitionList     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            androidx.constraintlayout.motion.widget.MotionScene$Transition r2 = new androidx.constraintlayout.motion.widget.MotionScene$Transition     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r2.<init>(r8, r9, r10)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r0.add(r2)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            androidx.constraintlayout.motion.widget.MotionScene$Transition r0 = r8.mCurrentTransition     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            if (r0 != 0) goto L_0x0125
            boolean r0 = r2.mIsAbstract     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            if (r0 != 0) goto L_0x0125
            r8.mCurrentTransition = r2     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            androidx.constraintlayout.motion.widget.TouchResponse r0 = r2.mTouchResponse     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            if (r0 == 0) goto L_0x0125
            boolean r3 = r8.mRtl     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r0.setRTL(r3)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
        L_0x0125:
            boolean r0 = r2.mIsAbstract     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            if (r0 == 0) goto L_0x013a
            int r0 = r2.mConstraintSetEnd     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            if (r0 != r6) goto L_0x0130
            r8.mDefaultTransition = r2     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            goto L_0x0135
        L_0x0130:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionScene$Transition> r0 = r8.mAbstractTransitionList     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r0.add(r2)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
        L_0x0135:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionScene$Transition> r0 = r8.mTransitionList     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r0.remove(r2)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
        L_0x013a:
            r0 = r2
            goto L_0x0158
        L_0x013c:
            r8.parseMotionSceneTags(r9, r10)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            goto L_0x0158
        L_0x0140:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r3.<init>()     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            java.lang.String r4 = "WARNING UNKNOWN ATTRIBUTE "
            r3.append(r4)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            r3.append(r2)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            java.lang.String r2 = r3.toString()     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            android.util.Log.v(r7, r2)     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            goto L_0x0158
        L_0x0155:
            r10.getName()     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
        L_0x0158:
            int r2 = r10.next()     // Catch:{ XmlPullParserException -> 0x0163, IOException -> 0x015e }
            goto L_0x0044
        L_0x015e:
            r9 = move-exception
            r9.printStackTrace()
            goto L_0x0167
        L_0x0163:
            r9 = move-exception
            r9.printStackTrace()
        L_0x0167:
            android.util.SparseArray<androidx.constraintlayout.widget.ConstraintSet> r9 = r8.mConstraintSetMap
            androidx.constraintlayout.widget.ConstraintSet r10 = new androidx.constraintlayout.widget.ConstraintSet
            r10.<init>()
            r11 = 2131428395(0x7f0b042b, float:1.8478433E38)
            r9.put(r11, r10)
            java.util.HashMap<java.lang.String, java.lang.Integer> r8 = r8.mConstraintSetIdMap
            java.lang.Integer r9 = java.lang.Integer.valueOf(r11)
            java.lang.String r10 = "motion_base"
            r8.put(r10, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionScene.<init>(android.content.Context, androidx.constraintlayout.motion.widget.MotionLayout, int):void");
    }

    public final void parseMotionSceneTags(Context context, XmlResourceParser xmlResourceParser) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.MotionScene);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 0) {
                this.mDefaultDuration = obtainStyledAttributes.getInt(index, this.mDefaultDuration);
            } else if (index == 1) {
                this.mLayoutDuringTransition = obtainStyledAttributes.getInteger(index, 0);
            }
        }
        obtainStyledAttributes.recycle();
    }

    public static class Transition {
        public int mAutoTransition = 0;
        public int mConstraintSetEnd = -1;
        public int mConstraintSetStart = -1;
        public int mDefaultInterpolator = 0;
        public int mDefaultInterpolatorID = -1;
        public String mDefaultInterpolatorString = null;
        public boolean mDisable = false;
        public int mDuration = 400;
        public int mId = -1;
        public boolean mIsAbstract = false;
        public ArrayList<KeyFrames> mKeyFramesList = new ArrayList<>();
        public int mLayoutDuringTransition = 0;
        public final MotionScene mMotionScene;
        public ArrayList<TransitionOnClick> mOnClicks = new ArrayList<>();
        public int mPathMotionArc = -1;
        public float mStagger = 0.0f;
        public TouchResponse mTouchResponse = null;

        public static class TransitionOnClick implements View.OnClickListener {
            public int mMode = 17;
            public int mTargetId = -1;
            public final Transition mTransition;

            public final void addOnClickListeners(MotionLayout motionLayout, int i, Transition transition) {
                boolean z;
                boolean z2;
                boolean z3;
                boolean z4;
                int i2 = this.mTargetId;
                View view = motionLayout;
                if (i2 != -1) {
                    view = motionLayout.findViewById(i2);
                }
                if (view == null) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("OnClick could not find id ");
                    m.append(this.mTargetId);
                    Log.e("MotionScene", m.toString());
                    return;
                }
                int i3 = transition.mConstraintSetStart;
                int i4 = transition.mConstraintSetEnd;
                if (i3 == -1) {
                    view.setOnClickListener(this);
                    return;
                }
                int i5 = this.mMode;
                int i6 = i5 & 1;
                boolean z5 = false;
                if (i6 == 0 || i != i3) {
                    z = false;
                } else {
                    z = true;
                }
                if ((i5 & 256) == 0 || i != i3) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                boolean z6 = z | z2;
                if (i6 == 0 || i != i3) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                boolean z7 = z3 | z6;
                if ((i5 & 16) == 0 || i != i4) {
                    z4 = false;
                } else {
                    z4 = true;
                }
                boolean z8 = z7 | z4;
                if ((i5 & 4096) != 0 && i == i4) {
                    z5 = true;
                }
                if (z8 || z5) {
                    view.setOnClickListener(this);
                }
            }

            public final void onClick(View view) {
                boolean z;
                boolean z2;
                boolean z3;
                int i;
                float f;
                float f2;
                StateSet stateSet;
                MotionLayout motionLayout = this.mTransition.mMotionScene.mMotionLayout;
                Objects.requireNonNull(motionLayout);
                if (motionLayout.mInteractionEnabled) {
                    Transition transition = this.mTransition;
                    int i2 = 0;
                    if (transition.mConstraintSetStart == -1) {
                        int i3 = motionLayout.mCurrentState;
                        if (i3 == -1) {
                            int i4 = transition.mConstraintSetEnd;
                            MotionScene motionScene = motionLayout.mScene;
                            if (!(motionScene == null || (stateSet = motionScene.mStateSet) == null)) {
                                float f3 = (float) -1;
                                StateSet.State state = stateSet.mStateList.get(i4);
                                if (state == null) {
                                    i3 = i4;
                                } else {
                                    int i5 = (f3 > -1.0f ? 1 : (f3 == -1.0f ? 0 : -1));
                                    if (i5 != 0 && i5 != 0) {
                                        Iterator<StateSet.Variant> it = state.mVariants.iterator();
                                        StateSet.Variant variant = null;
                                        while (true) {
                                            if (it.hasNext()) {
                                                StateSet.Variant next = it.next();
                                                if (next.match(f3, f3)) {
                                                    if (i3 == next.mConstraintID) {
                                                        break;
                                                    }
                                                    variant = next;
                                                }
                                            } else {
                                                i3 = variant != null ? variant.mConstraintID : state.mConstraintID;
                                            }
                                        }
                                    } else if (state.mConstraintID != i3) {
                                        Iterator<StateSet.Variant> it2 = state.mVariants.iterator();
                                        while (true) {
                                            if (it2.hasNext()) {
                                                if (i3 == it2.next().mConstraintID) {
                                                    break;
                                                }
                                            } else {
                                                i3 = state.mConstraintID;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (i3 != -1) {
                                    i4 = i3;
                                }
                            }
                            int i6 = motionLayout.mCurrentState;
                            if (i6 != i4) {
                                if (motionLayout.mBeginState == i4) {
                                    motionLayout.animateTo(0.0f);
                                } else if (motionLayout.mEndState == i4) {
                                    motionLayout.animateTo(1.0f);
                                } else {
                                    motionLayout.mEndState = i4;
                                    if (i6 != -1) {
                                        MotionScene motionScene2 = motionLayout.mScene;
                                        if (motionScene2 != null) {
                                            motionLayout.mBeginState = i6;
                                            motionLayout.mEndState = i4;
                                            motionScene2.setTransition(i6, i4);
                                            motionLayout.mModel.initFrom(motionLayout.mScene.getConstraintSet(i6), motionLayout.mScene.getConstraintSet(i4));
                                            motionLayout.rebuildScene();
                                            motionLayout.mTransitionLastPosition = 0.0f;
                                            motionLayout.animateTo(0.0f);
                                        }
                                        motionLayout.animateTo(1.0f);
                                        motionLayout.mTransitionLastPosition = 0.0f;
                                        motionLayout.animateTo(1.0f);
                                        return;
                                    }
                                    motionLayout.mTemporalInterpolator = false;
                                    motionLayout.mTransitionGoalPosition = 1.0f;
                                    motionLayout.mTransitionPosition = 0.0f;
                                    motionLayout.mTransitionLastPosition = 0.0f;
                                    motionLayout.mTransitionLastTime = System.nanoTime();
                                    motionLayout.mAnimationStartTime = System.nanoTime();
                                    motionLayout.mTransitionInstantly = false;
                                    motionLayout.mInterpolator = null;
                                    MotionScene motionScene3 = motionLayout.mScene;
                                    Objects.requireNonNull(motionScene3);
                                    Transition transition2 = motionScene3.mCurrentTransition;
                                    if (transition2 != null) {
                                        i = transition2.mDuration;
                                    } else {
                                        i = motionScene3.mDefaultDuration;
                                    }
                                    motionLayout.mTransitionDuration = ((float) i) / 1000.0f;
                                    motionLayout.mBeginState = -1;
                                    motionLayout.mScene.setTransition(-1, motionLayout.mEndState);
                                    motionLayout.mScene.getStartId();
                                    int childCount = motionLayout.getChildCount();
                                    motionLayout.mFrameArrayList.clear();
                                    for (int i7 = 0; i7 < childCount; i7++) {
                                        View childAt = motionLayout.getChildAt(i7);
                                        motionLayout.mFrameArrayList.put(childAt, new MotionController(childAt));
                                    }
                                    motionLayout.mInTransition = true;
                                    motionLayout.mModel.initFrom((ConstraintSet) null, motionLayout.mScene.getConstraintSet(i4));
                                    motionLayout.rebuildScene();
                                    motionLayout.mModel.build();
                                    int childCount2 = motionLayout.getChildCount();
                                    for (int i8 = 0; i8 < childCount2; i8++) {
                                        View childAt2 = motionLayout.getChildAt(i8);
                                        MotionController motionController = motionLayout.mFrameArrayList.get(childAt2);
                                        if (motionController != null) {
                                            MotionPaths motionPaths = motionController.mStartMotionPath;
                                            motionPaths.time = 0.0f;
                                            motionPaths.position = 0.0f;
                                            motionPaths.setBounds(childAt2.getX(), childAt2.getY(), (float) childAt2.getWidth(), (float) childAt2.getHeight());
                                            MotionConstrainedPoint motionConstrainedPoint = motionController.mStartPoint;
                                            Objects.requireNonNull(motionConstrainedPoint);
                                            childAt2.getX();
                                            childAt2.getY();
                                            childAt2.getWidth();
                                            childAt2.getHeight();
                                            motionConstrainedPoint.visibility = childAt2.getVisibility();
                                            if (childAt2.getVisibility() != 0) {
                                                f2 = 0.0f;
                                            } else {
                                                f2 = childAt2.getAlpha();
                                            }
                                            motionConstrainedPoint.alpha = f2;
                                            motionConstrainedPoint.elevation = childAt2.getElevation();
                                            motionConstrainedPoint.rotation = childAt2.getRotation();
                                            motionConstrainedPoint.rotationX = childAt2.getRotationX();
                                            motionConstrainedPoint.rotationY = childAt2.getRotationY();
                                            motionConstrainedPoint.scaleX = childAt2.getScaleX();
                                            motionConstrainedPoint.scaleY = childAt2.getScaleY();
                                            childAt2.getPivotX();
                                            childAt2.getPivotY();
                                            motionConstrainedPoint.translationX = childAt2.getTranslationX();
                                            motionConstrainedPoint.translationY = childAt2.getTranslationY();
                                            motionConstrainedPoint.translationZ = childAt2.getTranslationZ();
                                        }
                                    }
                                    int width = motionLayout.getWidth();
                                    int height = motionLayout.getHeight();
                                    for (int i9 = 0; i9 < childCount; i9++) {
                                        MotionController motionController2 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i9));
                                        motionLayout.mScene.getKeyFrames(motionController2);
                                        motionController2.setup(width, height, System.nanoTime());
                                    }
                                    MotionScene motionScene4 = motionLayout.mScene;
                                    Objects.requireNonNull(motionScene4);
                                    Transition transition3 = motionScene4.mCurrentTransition;
                                    if (transition3 != null) {
                                        f = transition3.mStagger;
                                    } else {
                                        f = 0.0f;
                                    }
                                    if (f != 0.0f) {
                                        float f4 = Float.MAX_VALUE;
                                        float f5 = -3.4028235E38f;
                                        for (int i10 = 0; i10 < childCount; i10++) {
                                            MotionController motionController3 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i10));
                                            Objects.requireNonNull(motionController3);
                                            MotionPaths motionPaths2 = motionController3.mEndMotionPath;
                                            float f6 = motionPaths2.f13y + motionPaths2.f12x;
                                            f4 = Math.min(f4, f6);
                                            f5 = Math.max(f5, f6);
                                        }
                                        while (i2 < childCount) {
                                            MotionController motionController4 = motionLayout.mFrameArrayList.get(motionLayout.getChildAt(i2));
                                            Objects.requireNonNull(motionController4);
                                            MotionPaths motionPaths3 = motionController4.mEndMotionPath;
                                            float f7 = motionPaths3.f12x;
                                            float f8 = motionPaths3.f13y;
                                            motionController4.mStaggerScale = 1.0f / (1.0f - f);
                                            motionController4.mStaggerOffset = f - ((((f7 + f8) - f4) * f) / (f5 - f4));
                                            i2++;
                                        }
                                    }
                                    motionLayout.mTransitionPosition = 0.0f;
                                    motionLayout.mTransitionLastPosition = 0.0f;
                                    motionLayout.mInTransition = true;
                                    motionLayout.invalidate();
                                }
                            }
                        } else {
                            Transition transition4 = new Transition(transition.mMotionScene, transition);
                            transition4.mConstraintSetStart = i3;
                            transition4.mConstraintSetEnd = this.mTransition.mConstraintSetEnd;
                            motionLayout.setTransition(transition4);
                            motionLayout.animateTo(1.0f);
                        }
                    } else {
                        Transition transition5 = transition.mMotionScene.mCurrentTransition;
                        int i11 = this.mMode;
                        if ((i11 & 1) == 0 && (i11 & 256) == 0) {
                            z = false;
                        } else {
                            z = true;
                        }
                        if ((i11 & 16) == 0 && (i11 & 4096) == 0) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        if (!z || !z2) {
                            z3 = false;
                        } else {
                            z3 = true;
                        }
                        if (z3) {
                            if (transition5 != transition) {
                                motionLayout.setTransition(transition);
                            }
                            if (motionLayout.mCurrentState == motionLayout.mEndState || motionLayout.mTransitionLastPosition > 0.5f) {
                                z = false;
                            } else {
                                z2 = false;
                            }
                        }
                        Transition transition6 = this.mTransition;
                        if (transition6 != transition5) {
                            int i12 = transition6.mConstraintSetEnd;
                            int i13 = transition6.mConstraintSetStart;
                            if (i13 != -1) {
                            }
                        }
                        i2 = 1;
                        if (i2 == 0) {
                            return;
                        }
                        if (z && (this.mMode & 1) != 0) {
                            motionLayout.setTransition(transition6);
                            motionLayout.animateTo(1.0f);
                        } else if (z2 && (this.mMode & 16) != 0) {
                            motionLayout.setTransition(transition6);
                            motionLayout.animateTo(0.0f);
                        } else if (z && (this.mMode & 256) != 0) {
                            motionLayout.setTransition(transition6);
                            motionLayout.setProgress(1.0f);
                        } else if (z2 && (this.mMode & 4096) != 0) {
                            motionLayout.setTransition(transition6);
                            motionLayout.setProgress(0.0f);
                        }
                    }
                }
            }

            public final void removeOnClickListeners(MotionLayout motionLayout) {
                View findViewById = motionLayout.findViewById(this.mTargetId);
                if (findViewById == null) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(" (*)  could not find id ");
                    m.append(this.mTargetId);
                    Log.e("MotionScene", m.toString());
                    return;
                }
                findViewById.setOnClickListener((View.OnClickListener) null);
            }

            public TransitionOnClick(Context context, Transition transition, XmlResourceParser xmlResourceParser) {
                this.mTransition = transition;
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.OnClick);
                int indexCount = obtainStyledAttributes.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    int index = obtainStyledAttributes.getIndex(i);
                    if (index == 1) {
                        this.mTargetId = obtainStyledAttributes.getResourceId(index, this.mTargetId);
                    } else if (index == 0) {
                        this.mMode = obtainStyledAttributes.getInt(index, this.mMode);
                    }
                }
                obtainStyledAttributes.recycle();
            }
        }

        public Transition(MotionScene motionScene, Transition transition) {
            this.mMotionScene = motionScene;
            if (transition != null) {
                this.mPathMotionArc = transition.mPathMotionArc;
                this.mDefaultInterpolator = transition.mDefaultInterpolator;
                this.mDefaultInterpolatorString = transition.mDefaultInterpolatorString;
                this.mDefaultInterpolatorID = transition.mDefaultInterpolatorID;
                this.mDuration = transition.mDuration;
                this.mKeyFramesList = transition.mKeyFramesList;
                this.mStagger = transition.mStagger;
                this.mLayoutDuringTransition = transition.mLayoutDuringTransition;
            }
        }

        public Transition(MotionScene motionScene, Context context, XmlResourceParser xmlResourceParser) {
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
            this.mMotionScene = motionScene;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.Transition);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 2) {
                    this.mConstraintSetEnd = obtainStyledAttributes.getResourceId(index, this.mConstraintSetEnd);
                    if ("layout".equals(context.getResources().getResourceTypeName(this.mConstraintSetEnd))) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.load(context, this.mConstraintSetEnd);
                        motionScene.mConstraintSetMap.append(this.mConstraintSetEnd, constraintSet);
                    }
                } else if (index == 3) {
                    this.mConstraintSetStart = obtainStyledAttributes.getResourceId(index, this.mConstraintSetStart);
                    if ("layout".equals(context.getResources().getResourceTypeName(this.mConstraintSetStart))) {
                        ConstraintSet constraintSet2 = new ConstraintSet();
                        constraintSet2.load(context, this.mConstraintSetStart);
                        motionScene.mConstraintSetMap.append(this.mConstraintSetStart, constraintSet2);
                    }
                } else if (index == 6) {
                    int i2 = obtainStyledAttributes.peekValue(index).type;
                    if (i2 == 1) {
                        int resourceId = obtainStyledAttributes.getResourceId(index, -1);
                        this.mDefaultInterpolatorID = resourceId;
                        if (resourceId != -1) {
                            this.mDefaultInterpolator = -2;
                        }
                    } else if (i2 == 3) {
                        String string = obtainStyledAttributes.getString(index);
                        this.mDefaultInterpolatorString = string;
                        if (string.indexOf("/") > 0) {
                            this.mDefaultInterpolatorID = obtainStyledAttributes.getResourceId(index, -1);
                            this.mDefaultInterpolator = -2;
                        } else {
                            this.mDefaultInterpolator = -1;
                        }
                    } else {
                        this.mDefaultInterpolator = obtainStyledAttributes.getInteger(index, this.mDefaultInterpolator);
                    }
                } else if (index == 4) {
                    this.mDuration = obtainStyledAttributes.getInt(index, this.mDuration);
                } else if (index == 8) {
                    this.mStagger = obtainStyledAttributes.getFloat(index, this.mStagger);
                } else if (index == 1) {
                    this.mAutoTransition = obtainStyledAttributes.getInteger(index, this.mAutoTransition);
                } else if (index == 0) {
                    this.mId = obtainStyledAttributes.getResourceId(index, this.mId);
                } else if (index == 9) {
                    this.mDisable = obtainStyledAttributes.getBoolean(index, this.mDisable);
                } else if (index == 7) {
                    this.mPathMotionArc = obtainStyledAttributes.getInteger(index, -1);
                } else if (index == 5) {
                    this.mLayoutDuringTransition = obtainStyledAttributes.getInteger(index, 0);
                }
            }
            if (this.mConstraintSetStart == -1) {
                this.mIsAbstract = true;
            }
            obtainStyledAttributes.recycle();
        }
    }
}
