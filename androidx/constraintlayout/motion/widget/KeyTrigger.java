package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.R$styleable;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

public final class KeyTrigger extends Key {
    public RectF mCollisionRect = new RectF();
    public String mCross = null;
    public Method mFireCross;
    public boolean mFireCrossReset = true;
    public float mFireLastPos;
    public Method mFireNegativeCross;
    public boolean mFireNegativeReset = true;
    public Method mFirePositiveCross;
    public boolean mFirePositiveReset = true;
    public float mFireThreshold = Float.NaN;
    public String mNegativeCross = null;
    public String mPositiveCross = null;
    public boolean mPostLayout = false;
    public RectF mTargetRect = new RectF();
    public int mTriggerCollisionId = -1;
    public View mTriggerCollisionView = null;
    public int mTriggerID = -1;
    public int mTriggerReceiver = -1;
    public float mTriggerSlack = 0.1f;

    public static class Loader {
        public static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(0, 8);
            mAttrMap.append(4, 4);
            mAttrMap.append(5, 1);
            mAttrMap.append(6, 2);
            mAttrMap.append(1, 7);
            mAttrMap.append(7, 6);
            mAttrMap.append(9, 5);
            mAttrMap.append(3, 9);
            mAttrMap.append(2, 10);
            mAttrMap.append(8, 11);
        }
    }

    public final void addValues(HashMap<String, SplineSet> hashMap) {
    }

    public final void getAttributeNames(HashSet<String> hashSet) {
    }

    public final void load(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyTrigger);
        SparseIntArray sparseIntArray = Loader.mAttrMap;
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            switch (Loader.mAttrMap.get(index)) {
                case 1:
                    this.mNegativeCross = obtainStyledAttributes.getString(index);
                    continue;
                case 2:
                    this.mPositiveCross = obtainStyledAttributes.getString(index);
                    continue;
                case 4:
                    this.mCross = obtainStyledAttributes.getString(index);
                    continue;
                case 5:
                    this.mTriggerSlack = obtainStyledAttributes.getFloat(index, this.mTriggerSlack);
                    continue;
                case FalsingManager.VERSION /*6*/:
                    this.mTriggerID = obtainStyledAttributes.getResourceId(index, this.mTriggerID);
                    continue;
                case 7:
                    if (!MotionLayout.IS_IN_EDIT_MODE) {
                        if (obtainStyledAttributes.peekValue(index).type != 3) {
                            this.mTargetId = obtainStyledAttributes.getResourceId(index, this.mTargetId);
                            break;
                        } else {
                            this.mTargetString = obtainStyledAttributes.getString(index);
                            break;
                        }
                    } else {
                        int resourceId = obtainStyledAttributes.getResourceId(index, this.mTargetId);
                        this.mTargetId = resourceId;
                        if (resourceId == -1) {
                            this.mTargetString = obtainStyledAttributes.getString(index);
                            break;
                        } else {
                            continue;
                        }
                    }
                case 8:
                    int integer = obtainStyledAttributes.getInteger(index, this.mFramePosition);
                    this.mFramePosition = integer;
                    this.mFireThreshold = (((float) integer) + 0.5f) / 100.0f;
                    continue;
                case 9:
                    this.mTriggerCollisionId = obtainStyledAttributes.getResourceId(index, this.mTriggerCollisionId);
                    continue;
                case 10:
                    this.mPostLayout = obtainStyledAttributes.getBoolean(index, this.mPostLayout);
                    continue;
                case QSTileImpl.C1034H.STALE /*11*/:
                    this.mTriggerReceiver = obtainStyledAttributes.getResourceId(index, this.mTriggerReceiver);
                    break;
            }
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("unused attribute 0x");
            m.append(Integer.toHexString(index));
            m.append("   ");
            m.append(Loader.mAttrMap.get(index));
            Log.e("KeyTrigger", m.toString());
        }
    }

    public KeyTrigger() {
        this.mCustomConstraints = new HashMap<>();
    }

    public static void setUpRect(RectF rectF, View view, boolean z) {
        rectF.top = (float) view.getTop();
        rectF.bottom = (float) view.getBottom();
        rectF.left = (float) view.getLeft();
        rectF.right = (float) view.getRight();
        if (z) {
            view.getMatrix().mapRect(rectF);
        }
    }
}
