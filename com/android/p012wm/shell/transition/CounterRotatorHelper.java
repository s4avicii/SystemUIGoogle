package com.android.p012wm.shell.transition;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.RotationUtils;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import com.android.p012wm.shell.util.CounterRotator;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.wm.shell.transition.CounterRotatorHelper */
public final class CounterRotatorHelper {
    public final Rect mLastDisplayBounds = new Rect();
    public int mLastRotationDelta;
    public final ArrayMap<WindowContainerToken, CounterRotator> mRotatorMap = new ArrayMap<>();

    public final void cleanUp(SurfaceControl.Transaction transaction) {
        for (int size = this.mRotatorMap.size() - 1; size >= 0; size--) {
            CounterRotator valueAt = this.mRotatorMap.valueAt(size);
            Objects.requireNonNull(valueAt);
            SurfaceControl surfaceControl = valueAt.mSurface;
            if (surfaceControl != null) {
                transaction.remove(surfaceControl);
            }
        }
        this.mRotatorMap.clear();
        this.mLastRotationDelta = 0;
    }

    public final void handleClosingChanges(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, TransitionInfo.Change change) {
        int i;
        TransitionInfo transitionInfo2 = transitionInfo;
        SurfaceControl.Transaction transaction2 = transaction;
        int deltaRotation = RotationUtils.deltaRotation(change.getStartRotation(), change.getEndRotation());
        Rect endAbsBounds = change.getEndAbsBounds();
        int width = endAbsBounds.width();
        int height = endAbsBounds.height();
        this.mLastRotationDelta = deltaRotation;
        this.mLastDisplayBounds.set(endAbsBounds);
        List changes = transitionInfo.getChanges();
        int size = changes.size();
        int i2 = size - 1;
        while (i2 >= 0) {
            TransitionInfo.Change change2 = (TransitionInfo.Change) changes.get(i2);
            WindowContainerToken parent = change2.getParent();
            if (Transitions.isClosingType(change2.getMode()) && TransitionInfo.isIndependent(change2, transitionInfo2) && parent != null) {
                CounterRotator counterRotator = this.mRotatorMap.get(parent);
                if (counterRotator == null) {
                    counterRotator = new CounterRotator();
                    SurfaceControl leash = transitionInfo2.getChange(parent).getLeash();
                    float f = (float) width;
                    float f2 = (float) height;
                    if (deltaRotation != 0) {
                        SurfaceControl build = new SurfaceControl.Builder().setName("Transition Unrotate").setContainerLayer().setParent(leash).build();
                        counterRotator.mSurface = build;
                        RotationUtils.rotateSurface(transaction2, build, deltaRotation);
                        Point point = new Point(0, 0);
                        if (deltaRotation % 2 != 0) {
                            float f3 = f2;
                            f2 = f;
                            f = f3;
                        }
                        RotationUtils.rotatePoint(point, deltaRotation, (int) f, (int) f2);
                        transaction2.setPosition(counterRotator.mSurface, (float) point.x, (float) point.y);
                        transaction2.show(counterRotator.mSurface);
                    }
                    SurfaceControl surfaceControl = counterRotator.mSurface;
                    if (surfaceControl != null) {
                        if ((change2.getFlags() & 2) == 0) {
                            i = size - i2;
                        } else {
                            i = -1;
                        }
                        transaction2.setLayer(surfaceControl, i);
                    }
                    this.mRotatorMap.put(parent, counterRotator);
                }
                SurfaceControl leash2 = change2.getLeash();
                SurfaceControl surfaceControl2 = counterRotator.mSurface;
                if (surfaceControl2 != null) {
                    transaction2.reparent(leash2, surfaceControl2);
                }
            }
            i2--;
            transitionInfo2 = transitionInfo;
        }
    }
}
