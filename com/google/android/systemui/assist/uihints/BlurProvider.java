package com.google.android.systemui.assist.uihints;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public final class BlurProvider {
    public final BlurKernel mBlurKernel;
    public Bitmap mBuffer;
    public final SourceDownsampler mImageSource;
    public final Resources mResources;

    public static class BlurKernel {
        public final RenderScript mBlurRenderScript;
        public final ScriptIntrinsicBlur mBlurScript;
        public Allocation mLastInputAllocation;
        public Bitmap mLastInputBitmap;
        public Allocation mLastOutputAllocation;
        public Bitmap mLastOutputBitmap;

        public final void prepareInputAllocation(Bitmap bitmap) {
            Bitmap bitmap2 = this.mLastInputBitmap;
            boolean z = false;
            if (bitmap2 != null && bitmap != null && bitmap2.getWidth() == bitmap.getWidth() && bitmap2.getHeight() == bitmap.getHeight()) {
                z = true;
            }
            this.mLastInputBitmap = bitmap;
            if (z) {
                this.mLastInputAllocation.copyFrom(bitmap);
                return;
            }
            Allocation allocation = this.mLastInputAllocation;
            if (allocation != null) {
                allocation.destroy();
                this.mLastInputAllocation = null;
            }
            Bitmap bitmap3 = this.mLastInputBitmap;
            if (bitmap3 != null) {
                this.mLastInputAllocation = Allocation.createFromBitmap(this.mBlurRenderScript, bitmap3, Allocation.MipmapControl.MIPMAP_NONE, 1);
            }
        }

        public final void prepareOutputAllocation(Bitmap bitmap) {
            if (this.mLastOutputAllocation != null) {
                Bitmap bitmap2 = this.mLastOutputBitmap;
                boolean z = false;
                if (bitmap2 != null && bitmap != null && bitmap2.getWidth() == bitmap.getWidth() && bitmap2.getHeight() == bitmap.getHeight()) {
                    z = true;
                }
                if (!z) {
                    this.mLastOutputAllocation.destroy();
                    this.mLastOutputAllocation = null;
                }
            }
            this.mLastOutputBitmap = bitmap;
            if (bitmap != null && this.mLastOutputAllocation == null) {
                this.mLastOutputAllocation = Allocation.createFromBitmap(this.mBlurRenderScript, bitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
            }
        }

        public BlurKernel(Context context) {
            RenderScript create = RenderScript.create(context);
            this.mBlurRenderScript = create;
            this.mBlurScript = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
        }
    }

    public class BlurResult {
        public final RectF cropRegion;
        public final Drawable drawable;

        public BlurResult(Drawable drawable2, RectF rectF) {
            this.drawable = drawable2;
            this.cropRegion = rectF;
        }
    }

    public static class SourceDownsampler {
        public final Drawable mDrawable;

        public SourceDownsampler(Drawable drawable) {
            this.mDrawable = drawable;
        }
    }

    public BlurProvider(Context context, Drawable drawable) {
        this.mResources = context.getResources();
        this.mImageSource = new SourceDownsampler(drawable);
        this.mBlurKernel = new BlurKernel(context);
    }
}
