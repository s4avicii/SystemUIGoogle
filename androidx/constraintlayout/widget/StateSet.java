package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.util.ArrayList;

public final class StateSet {
    public int mDefaultState = -1;
    public SparseArray<State> mStateList = new SparseArray<>();

    public static class Variant {
        public int mConstraintID = -1;
        public float mMaxHeight = Float.NaN;
        public float mMaxWidth = Float.NaN;
        public float mMinHeight = Float.NaN;
        public float mMinWidth = Float.NaN;

        public final boolean match(float f, float f2) {
            if (!Float.isNaN(this.mMinWidth) && f < this.mMinWidth) {
                return false;
            }
            if (!Float.isNaN(this.mMinHeight) && f2 < this.mMinHeight) {
                return false;
            }
            if (!Float.isNaN(this.mMaxWidth) && f > this.mMaxWidth) {
                return false;
            }
            if (Float.isNaN(this.mMaxHeight) || f2 <= this.mMaxHeight) {
                return true;
            }
            return false;
        }

        public Variant(Context context, XmlResourceParser xmlResourceParser) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.Variant);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 0) {
                    this.mConstraintID = obtainStyledAttributes.getResourceId(index, this.mConstraintID);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    "layout".equals(resourceTypeName);
                } else if (index == 1) {
                    this.mMaxHeight = obtainStyledAttributes.getDimension(index, this.mMaxHeight);
                } else if (index == 2) {
                    this.mMinHeight = obtainStyledAttributes.getDimension(index, this.mMinHeight);
                } else if (index == 3) {
                    this.mMaxWidth = obtainStyledAttributes.getDimension(index, this.mMaxWidth);
                } else if (index == 4) {
                    this.mMinWidth = obtainStyledAttributes.getDimension(index, this.mMinWidth);
                } else {
                    Log.v("ConstraintLayoutStates", "Unknown tag");
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    public final int stateGetConstraintID(int i) {
        int i2;
        State state;
        int findMatch;
        float f = (float) -1;
        if (-1 == i) {
            if (i == -1) {
                state = this.mStateList.valueAt(0);
            } else {
                state = this.mStateList.get(-1);
            }
            if (state == null || -1 == (findMatch = state.findMatch(f, f))) {
                return -1;
            }
            if (findMatch == -1) {
                i2 = state.mConstraintID;
            } else {
                i2 = state.mVariants.get(findMatch).mConstraintID;
            }
        } else {
            State state2 = this.mStateList.get(i);
            if (state2 == null) {
                return -1;
            }
            int findMatch2 = state2.findMatch(f, f);
            if (findMatch2 == -1) {
                i2 = state2.mConstraintID;
            } else {
                i2 = state2.mVariants.get(findMatch2).mConstraintID;
            }
        }
        return i2;
    }

    public static class State {
        public int mConstraintID = -1;
        public int mId;
        public ArrayList<Variant> mVariants = new ArrayList<>();

        public final int findMatch(float f, float f2) {
            for (int i = 0; i < this.mVariants.size(); i++) {
                if (this.mVariants.get(i).match(f, f2)) {
                    return i;
                }
            }
            return -1;
        }

        public State(Context context, XmlResourceParser xmlResourceParser) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.State);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 0) {
                    this.mId = obtainStyledAttributes.getResourceId(index, this.mId);
                } else if (index == 1) {
                    this.mConstraintID = obtainStyledAttributes.getResourceId(index, this.mConstraintID);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    "layout".equals(resourceTypeName);
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public StateSet(android.content.Context r10, android.content.res.XmlResourceParser r11) {
        /*
            r9 = this;
            r9.<init>()
            r0 = -1
            r9.mDefaultState = r0
            android.util.SparseArray r1 = new android.util.SparseArray
            r1.<init>()
            r9.mStateList = r1
            android.util.SparseArray r1 = new android.util.SparseArray
            r1.<init>()
            android.util.AttributeSet r1 = android.util.Xml.asAttributeSet(r11)
            int[] r2 = androidx.constraintlayout.widget.R$styleable.StateSet
            android.content.res.TypedArray r1 = r10.obtainStyledAttributes(r1, r2)
            int r2 = r1.getIndexCount()
            r3 = 0
            r4 = r3
        L_0x0022:
            if (r4 >= r2) goto L_0x0035
            int r5 = r1.getIndex(r4)
            if (r5 != 0) goto L_0x0032
            int r6 = r9.mDefaultState
            int r5 = r1.getResourceId(r5, r6)
            r9.mDefaultState = r5
        L_0x0032:
            int r4 = r4 + 1
            goto L_0x0022
        L_0x0035:
            r1 = 0
            int r2 = r11.getEventType()     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
        L_0x003a:
            r4 = 1
            if (r2 == r4) goto L_0x00d4
            if (r2 == 0) goto L_0x00c2
            java.lang.String r5 = "StateSet"
            r6 = 3
            r7 = 2
            if (r2 == r7) goto L_0x0055
            if (r2 == r6) goto L_0x0049
            goto L_0x00c5
        L_0x0049:
            java.lang.String r2 = r11.getName()     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            boolean r2 = r5.equals(r2)     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            if (r2 == 0) goto L_0x00c5
            goto L_0x00d4
        L_0x0055:
            java.lang.String r2 = r11.getName()     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            int r8 = r2.hashCode()     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            switch(r8) {
                case 80204913: goto L_0x007d;
                case 1301459538: goto L_0x0073;
                case 1382829617: goto L_0x006b;
                case 1901439077: goto L_0x0061;
                default: goto L_0x0060;
            }     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
        L_0x0060:
            goto L_0x0087
        L_0x0061:
            java.lang.String r5 = "Variant"
            boolean r5 = r2.equals(r5)     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            if (r5 == 0) goto L_0x0087
            r5 = r6
            goto L_0x0088
        L_0x006b:
            boolean r5 = r2.equals(r5)     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            if (r5 == 0) goto L_0x0087
            r5 = r4
            goto L_0x0088
        L_0x0073:
            java.lang.String r5 = "LayoutDescription"
            boolean r5 = r2.equals(r5)     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            if (r5 == 0) goto L_0x0087
            r5 = r3
            goto L_0x0088
        L_0x007d:
            java.lang.String r5 = "State"
            boolean r5 = r2.equals(r5)     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            if (r5 == 0) goto L_0x0087
            r5 = r7
            goto L_0x0088
        L_0x0087:
            r5 = r0
        L_0x0088:
            if (r5 == 0) goto L_0x00c5
            if (r5 == r4) goto L_0x00c5
            if (r5 == r7) goto L_0x00b5
            if (r5 == r6) goto L_0x00a8
            java.lang.String r4 = "ConstraintLayoutStates"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            r5.<init>()     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            java.lang.String r6 = "unknown tag "
            r5.append(r6)     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            r5.append(r2)     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            java.lang.String r2 = r5.toString()     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            android.util.Log.v(r4, r2)     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            goto L_0x00c5
        L_0x00a8:
            androidx.constraintlayout.widget.StateSet$Variant r2 = new androidx.constraintlayout.widget.StateSet$Variant     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            r2.<init>(r10, r11)     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            if (r1 == 0) goto L_0x00c5
            java.util.ArrayList<androidx.constraintlayout.widget.StateSet$Variant> r4 = r1.mVariants     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            r4.add(r2)     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            goto L_0x00c5
        L_0x00b5:
            androidx.constraintlayout.widget.StateSet$State r1 = new androidx.constraintlayout.widget.StateSet$State     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            r1.<init>(r10, r11)     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            android.util.SparseArray<androidx.constraintlayout.widget.StateSet$State> r2 = r9.mStateList     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            int r4 = r1.mId     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            r2.put(r4, r1)     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            goto L_0x00c5
        L_0x00c2:
            r11.getName()     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
        L_0x00c5:
            int r2 = r11.next()     // Catch:{ XmlPullParserException -> 0x00d0, IOException -> 0x00cb }
            goto L_0x003a
        L_0x00cb:
            r9 = move-exception
            r9.printStackTrace()
            goto L_0x00d4
        L_0x00d0:
            r9 = move-exception
            r9.printStackTrace()
        L_0x00d4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.StateSet.<init>(android.content.Context, android.content.res.XmlResourceParser):void");
    }
}
