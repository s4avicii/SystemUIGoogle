package com.android.p012wm.shell.pip;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceControl;
import com.android.p012wm.shell.transition.Transitions;

/* renamed from: com.android.wm.shell.pip.PipSurfaceTransactionHelper */
public final class PipSurfaceTransactionHelper {
    public int mCornerRadius;
    public final Rect mTmpDestinationRect = new Rect();
    public final RectF mTmpDestinationRectF = new RectF();
    public final float[] mTmpFloat9 = new float[9];
    public final RectF mTmpSourceRectF = new RectF();
    public final Matrix mTmpTransform = new Matrix();

    /* renamed from: com.android.wm.shell.pip.PipSurfaceTransactionHelper$SurfaceControlTransactionFactory */
    public interface SurfaceControlTransactionFactory {
        SurfaceControl.Transaction getTransaction();
    }

    public final PipSurfaceTransactionHelper round(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, boolean z) {
        transaction.setCornerRadius(surfaceControl, z ? (float) this.mCornerRadius : 0.0f);
        return this;
    }

    public final PipSurfaceTransactionHelper resetScale(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect) {
        transaction.setMatrix(surfaceControl, Matrix.IDENTITY_MATRIX, this.mTmpFloat9).setPosition(surfaceControl, (float) rect.left, (float) rect.top);
        return this;
    }

    public final PipSurfaceTransactionHelper rotateAndScaleWithCrop(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, Rect rect2, Rect rect3, float f, float f2, float f3, boolean z, boolean z2) {
        float f4;
        int i;
        float f5;
        float f6;
        int i2;
        this.mTmpDestinationRect.set(rect);
        this.mTmpDestinationRect.inset(rect3);
        int width = this.mTmpDestinationRect.width();
        int height = this.mTmpDestinationRect.height();
        int width2 = rect2.width();
        int height2 = rect2.height();
        if (width <= height) {
            f4 = ((float) width2) / ((float) width);
        } else {
            f4 = ((float) height2) / ((float) height);
        }
        Rect rect4 = this.mTmpDestinationRect;
        boolean z3 = Transitions.ENABLE_SHELL_TRANSITIONS;
        if (z3) {
            i = height2;
        } else {
            i = width2;
        }
        if (!z3) {
            width2 = height2;
        }
        rect4.set(0, 0, i, width2);
        rect4.scale(1.0f / f4);
        rect4.offset(rect3.left, rect3.top);
        if (z) {
            f6 = f2 - (((float) rect3.left) * f4);
            i2 = rect3.top;
        } else if (z2) {
            f6 = f2 - (((float) rect3.top) * f4);
            f5 = f3 + (((float) rect3.left) * f4);
            this.mTmpTransform.setScale(f4, f4);
            this.mTmpTransform.postRotate(f);
            this.mTmpTransform.postTranslate(f6, f5);
            transaction.setMatrix(surfaceControl, this.mTmpTransform, this.mTmpFloat9).setWindowCrop(surfaceControl, rect4);
            return this;
        } else {
            f6 = f2 + (((float) rect3.top) * f4);
            i2 = rect3.left;
        }
        f5 = f3 - (((float) i2) * f4);
        this.mTmpTransform.setScale(f4, f4);
        this.mTmpTransform.postRotate(f);
        this.mTmpTransform.postTranslate(f6, f5);
        transaction.setMatrix(surfaceControl, this.mTmpTransform, this.mTmpFloat9).setWindowCrop(surfaceControl, rect4);
        return this;
    }

    public final PipSurfaceTransactionHelper round(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, Rect rect2) {
        transaction.setCornerRadius(surfaceControl, ((float) this.mCornerRadius) * ((float) (Math.hypot((double) rect.width(), (double) rect.height()) / Math.hypot((double) rect2.width(), (double) rect2.height()))));
        return this;
    }

    public final PipSurfaceTransactionHelper scale(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, Rect rect2, float f) {
        this.mTmpSourceRectF.set(rect);
        this.mTmpSourceRectF.offsetTo(0.0f, 0.0f);
        this.mTmpDestinationRectF.set(rect2);
        this.mTmpTransform.setRectToRect(this.mTmpSourceRectF, this.mTmpDestinationRectF, Matrix.ScaleToFit.FILL);
        this.mTmpTransform.postRotate(f, this.mTmpDestinationRectF.centerX(), this.mTmpDestinationRectF.centerY());
        transaction.setMatrix(surfaceControl, this.mTmpTransform, this.mTmpFloat9);
        return this;
    }

    public final PipSurfaceTransactionHelper crop(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect) {
        transaction.setWindowCrop(surfaceControl, rect.width(), rect.height()).setPosition(surfaceControl, (float) rect.left, (float) rect.top);
        return this;
    }
}
