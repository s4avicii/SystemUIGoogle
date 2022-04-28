package com.android.p012wm.shell.pip;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.PictureInPictureUiState;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import android.util.Size;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.function.TriConsumer;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.common.DisplayLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.pip.PipBoundsState */
public class PipBoundsState {
    public float mAspectRatio;
    public final Rect mBounds = new Rect();
    public final Context mContext;
    public int mDisplayId = 0;
    public final DisplayLayout mDisplayLayout = new DisplayLayout();
    public final Rect mExpandedBounds = new Rect();
    public final Rect mExpandedMovementBounds = new Rect();
    public boolean mHasUserResizedPip;
    public int mImeHeight;
    public boolean mIsImeShowing;
    public boolean mIsShelfShowing;
    public ComponentName mLastPipComponentName;
    public final Point mMaxSize = new Point();
    public int mMinEdgeSize;
    public final Point mMinSize = new Point();
    public final MotionBoundsState mMotionBoundsState = new MotionBoundsState();
    public final Rect mMovementBounds = new Rect();
    public final Rect mNormalBounds = new Rect();
    public final Rect mNormalMovementBounds = new Rect();
    public Runnable mOnMinimalSizeChangeCallback;
    public ArrayList mOnPipExclusionBoundsChangeCallbacks = new ArrayList();
    public TriConsumer<Boolean, Integer, Boolean> mOnShelfVisibilityChangeCallback;
    public Size mOverrideMinSize;
    public PipReentryState mPipReentryState;
    public int mShelfHeight;
    public int mStashOffset;
    public int mStashedState = 0;

    /* renamed from: com.android.wm.shell.pip.PipBoundsState$MotionBoundsState */
    public static class MotionBoundsState {
        public final Rect mAnimatingToBounds = new Rect();
        public final Rect mBoundsInMotion = new Rect();
    }

    @VisibleForTesting
    public void clearReentryState() {
        this.mPipReentryState = null;
    }

    /* renamed from: com.android.wm.shell.pip.PipBoundsState$PipReentryState */
    public static final class PipReentryState {
        public final Size mSize;
        public final float mSnapFraction;

        public PipReentryState(Size size, float f) {
            this.mSize = size;
            this.mSnapFraction = f;
        }
    }

    public final Rect getBounds() {
        return new Rect(this.mBounds);
    }

    public final Rect getDisplayBounds() {
        DisplayLayout displayLayout = this.mDisplayLayout;
        Objects.requireNonNull(displayLayout);
        int i = displayLayout.mWidth;
        DisplayLayout displayLayout2 = this.mDisplayLayout;
        Objects.requireNonNull(displayLayout2);
        return new Rect(0, 0, i, displayLayout2.mHeight);
    }

    public final boolean isStashed() {
        if (this.mStashedState != 0) {
            return true;
        }
        return false;
    }

    public final void setBounds(Rect rect) {
        this.mBounds.set(rect);
        Iterator it = this.mOnPipExclusionBoundsChangeCallbacks.iterator();
        while (it.hasNext()) {
            ((Consumer) it.next()).accept(rect);
        }
    }

    public final void setLastPipComponentName(ComponentName componentName) {
        boolean z = !Objects.equals(this.mLastPipComponentName, componentName);
        this.mLastPipComponentName = componentName;
        if (z) {
            clearReentryState();
            this.mHasUserResizedPip = false;
        }
    }

    public final void setShelfVisibility(boolean z, int i, boolean z2) {
        boolean z3;
        if (!z || i <= 0) {
            z3 = false;
        } else {
            z3 = true;
        }
        if (z3 != this.mIsShelfShowing || i != this.mShelfHeight) {
            this.mIsShelfShowing = z;
            this.mShelfHeight = i;
            TriConsumer<Boolean, Integer, Boolean> triConsumer = this.mOnShelfVisibilityChangeCallback;
            if (triConsumer != null) {
                triConsumer.accept(Boolean.valueOf(z), Integer.valueOf(this.mShelfHeight), Boolean.valueOf(z2));
            }
        }
    }

    public final void setStashed(int i) {
        boolean z;
        if (this.mStashedState != i) {
            this.mStashedState = i;
            try {
                IActivityTaskManager service = ActivityTaskManager.getService();
                if (i != 0) {
                    z = true;
                } else {
                    z = false;
                }
                service.onPictureInPictureStateChanged(new PictureInPictureUiState(z));
            } catch (RemoteException unused) {
                Log.e("PipBoundsState", "Unable to set alert PiP state change.");
            }
        }
    }

    public PipBoundsState(Context context) {
        this.mContext = context;
        this.mStashOffset = context.getResources().getDimensionPixelSize(C1777R.dimen.pip_stash_offset);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setBoundsStateForEntry(android.content.ComponentName r1, android.content.pm.ActivityInfo r2, android.app.PictureInPictureParams r3, com.android.p012wm.shell.pip.PipBoundsAlgorithm r4) {
        /*
            r0 = this;
            r0.setLastPipComponentName(r1)
            if (r3 == 0) goto L_0x0013
            java.util.Objects.requireNonNull(r4)
            boolean r1 = r3.hasSetAspectRatio()
            if (r1 == 0) goto L_0x0013
            float r1 = r3.getAspectRatio()
            goto L_0x0015
        L_0x0013:
            float r1 = r4.mDefaultAspectRatio
        L_0x0015:
            r0.mAspectRatio = r1
            android.util.Size r1 = r4.getMinimalSize(r2)
            android.util.Size r2 = r0.mOverrideMinSize
            boolean r2 = java.util.Objects.equals(r1, r2)
            r2 = r2 ^ 1
            r0.mOverrideMinSize = r1
            if (r2 == 0) goto L_0x002e
            java.lang.Runnable r0 = r0.mOnMinimalSizeChangeCallback
            if (r0 == 0) goto L_0x002e
            r0.run()
        L_0x002e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.pip.PipBoundsState.setBoundsStateForEntry(android.content.ComponentName, android.content.pm.ActivityInfo, android.app.PictureInPictureParams, com.android.wm.shell.pip.PipBoundsAlgorithm):void");
    }
}
