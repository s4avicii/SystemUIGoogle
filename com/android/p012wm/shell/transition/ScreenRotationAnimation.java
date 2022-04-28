package com.android.p012wm.shell.transition;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Matrix;
import android.hardware.HardwareBuffer;
import android.media.Image;
import android.media.ImageReader;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import com.android.p012wm.shell.common.TransactionPool;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* renamed from: com.android.wm.shell.transition.ScreenRotationAnimation */
public final class ScreenRotationAnimation {
    public final int mAnimHint;
    public SurfaceControl mAnimLeash;
    public SurfaceControl mBackColorSurface;
    public final Context mContext;
    public final int mEndHeight;
    public final int mEndRotation;
    public final int mEndWidth;
    public Animation mRotateAlphaAnimation;
    public Animation mRotateEnterAnimation;
    public Animation mRotateExitAnimation;
    public SurfaceControl mScreenshotLayer;
    public final Matrix mSnapshotInitialMatrix = new Matrix();
    public final int mStartHeight;
    public float mStartLuma;
    public final int mStartRotation;
    public final int mStartWidth;
    public final SurfaceControl mSurfaceControl;
    public final float[] mTmpFloats = new float[9];
    public SurfaceControl.Transaction mTransaction;
    public final TransactionPool mTransactionPool;

    public static float getMedianBorderLuma(HardwareBuffer hardwareBuffer, ColorSpace colorSpace) {
        boolean z;
        if (hardwareBuffer != null && hardwareBuffer.getFormat() == 1) {
            if ((hardwareBuffer.getUsage() & 16384) == 16384) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                ImageReader newInstance = ImageReader.newInstance(hardwareBuffer.getWidth(), hardwareBuffer.getHeight(), hardwareBuffer.getFormat(), 1);
                newInstance.getSurface().attachAndQueueBufferWithColorSpace(hardwareBuffer, colorSpace);
                Image acquireLatestImage = newInstance.acquireLatestImage();
                if (!(acquireLatestImage == null || acquireLatestImage.getPlanes().length == 0)) {
                    Image.Plane plane = acquireLatestImage.getPlanes()[0];
                    ByteBuffer buffer = plane.getBuffer();
                    int width = acquireLatestImage.getWidth();
                    int height = acquireLatestImage.getHeight();
                    int pixelStride = plane.getPixelStride();
                    int rowStride = plane.getRowStride();
                    int i = (height * 2) + (width * 2);
                    float[] fArr = new float[i];
                    int i2 = 0;
                    for (int i3 = 0; i3 < width; i3++) {
                        int i4 = i2 + 1;
                        fArr[i2] = getPixelLuminance(buffer, i3, 0, pixelStride, rowStride);
                        i2 = i4 + 1;
                        fArr[i4] = getPixelLuminance(buffer, i3, height - 1, pixelStride, rowStride);
                    }
                    for (int i5 = 0; i5 < height; i5++) {
                        int i6 = i2 + 1;
                        fArr[i2] = getPixelLuminance(buffer, 0, i5, pixelStride, rowStride);
                        i2 = i6 + 1;
                        fArr[i6] = getPixelLuminance(buffer, width - 1, i5, pixelStride, rowStride);
                    }
                    newInstance.close();
                    Arrays.sort(fArr);
                    return fArr[i / 2];
                }
            }
        }
        return 0.0f;
    }

    public static float getPixelLuminance(ByteBuffer byteBuffer, int i, int i2, int i3, int i4) {
        int i5 = (i * i3) + (i2 * i4);
        return Color.valueOf(((byteBuffer.get(i5 + 3) & 255) << 24) | ((byteBuffer.get(i5) & 255) << 16) | 0 | ((byteBuffer.get(i5 + 1) & 255) << 8) | (byteBuffer.get(i5 + 2) & 255)).luminance();
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x012c A[Catch:{ OutOfResourcesException -> 0x0178 }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x018e  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x01b5  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x01bf  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ScreenRotationAnimation(android.content.Context r17, android.view.SurfaceSession r18, com.android.p012wm.shell.common.TransactionPool r19, android.view.SurfaceControl.Transaction r20, android.window.TransitionInfo.Change r21, android.view.SurfaceControl r22, int r23) {
        /*
            r16 = this;
            r1 = r16
            r0 = r18
            r8 = r20
            r2 = r22
            r3 = r23
            java.lang.String r4 = "ShellTransitions"
            r16.<init>()
            r5 = 9
            float[] r5 = new float[r5]
            r1.mTmpFloats = r5
            android.graphics.Matrix r5 = new android.graphics.Matrix
            r5.<init>()
            r1.mSnapshotInitialMatrix = r5
            android.graphics.Rect r5 = new android.graphics.Rect
            r5.<init>()
            android.graphics.Rect r6 = new android.graphics.Rect
            r6.<init>()
            r7 = r17
            r1.mContext = r7
            r7 = r19
            r1.mTransactionPool = r7
            r1.mAnimHint = r3
            android.view.SurfaceControl r7 = r21.getLeash()
            r1.mSurfaceControl = r7
            android.graphics.Rect r9 = r21.getStartAbsBounds()
            int r9 = r9.width()
            r1.mStartWidth = r9
            android.graphics.Rect r10 = r21.getStartAbsBounds()
            int r10 = r10.height()
            r1.mStartHeight = r10
            android.graphics.Rect r11 = r21.getEndAbsBounds()
            int r11 = r11.width()
            r1.mEndWidth = r11
            android.graphics.Rect r11 = r21.getEndAbsBounds()
            int r11 = r11.height()
            r1.mEndHeight = r11
            int r11 = r21.getStartRotation()
            r1.mStartRotation = r11
            int r11 = r21.getEndRotation()
            r1.mEndRotation = r11
            android.graphics.Rect r11 = r21.getStartAbsBounds()
            r5.set(r11)
            android.graphics.Rect r5 = r21.getEndAbsBounds()
            r6.set(r5)
            android.view.SurfaceControl$Builder r5 = new android.view.SurfaceControl$Builder
            r5.<init>(r0)
            android.view.SurfaceControl$Builder r5 = r5.setParent(r2)
            android.view.SurfaceControl$Builder r5 = r5.setEffectLayer()
            java.lang.String r6 = "ShellRotationAnimation"
            android.view.SurfaceControl$Builder r5 = r5.setCallsite(r6)
            java.lang.String r11 = "Animation leash of screenshot rotation"
            android.view.SurfaceControl$Builder r5 = r5.setName(r11)
            android.view.SurfaceControl r5 = r5.build()
            r1.mAnimLeash = r5
            r5 = 1
            r11 = 0
            r12 = 2
            r14 = 1065353216(0x3f800000, float:1.0)
            r15 = 0
            android.view.SurfaceControl$LayerCaptureArgs$Builder r13 = new android.view.SurfaceControl$LayerCaptureArgs$Builder     // Catch:{ OutOfResourcesException -> 0x0178 }
            r13.<init>(r7)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$CaptureArgs$Builder r7 = r13.setCaptureSecureLayers(r5)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$LayerCaptureArgs$Builder r7 = (android.view.SurfaceControl.LayerCaptureArgs.Builder) r7     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$CaptureArgs$Builder r7 = r7.setAllowProtected(r5)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$LayerCaptureArgs$Builder r7 = (android.view.SurfaceControl.LayerCaptureArgs.Builder) r7     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.graphics.Rect r13 = new android.graphics.Rect     // Catch:{ OutOfResourcesException -> 0x0178 }
            r13.<init>(r11, r11, r9, r10)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$CaptureArgs$Builder r7 = r7.setSourceCrop(r13)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$LayerCaptureArgs$Builder r7 = (android.view.SurfaceControl.LayerCaptureArgs.Builder) r7     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$LayerCaptureArgs r7 = r7.build()     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$ScreenshotHardwareBuffer r7 = android.view.SurfaceControl.captureLayers(r7)     // Catch:{ OutOfResourcesException -> 0x0178 }
            if (r7 != 0) goto L_0x00c9
            java.lang.String r0 = "Unable to take screenshot of display"
            android.util.Slog.w(r4, r0)     // Catch:{ OutOfResourcesException -> 0x0178 }
            return
        L_0x00c9:
            android.view.SurfaceControl$Builder r9 = new android.view.SurfaceControl$Builder     // Catch:{ OutOfResourcesException -> 0x0178 }
            r9.<init>(r0)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r10 = r1.mAnimLeash     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$Builder r9 = r9.setParent(r10)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$Builder r9 = r9.setBLASTLayer()     // Catch:{ OutOfResourcesException -> 0x0178 }
            boolean r10 = r7.containsSecureLayers()     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$Builder r9 = r9.setSecure(r10)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$Builder r9 = r9.setCallsite(r6)     // Catch:{ OutOfResourcesException -> 0x0178 }
            java.lang.String r10 = "RotationLayer"
            android.view.SurfaceControl$Builder r9 = r9.setName(r10)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r9 = r9.build()     // Catch:{ OutOfResourcesException -> 0x0178 }
            r1.mScreenshotLayer = r9     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.hardware.HardwareBuffer r9 = r7.getHardwareBuffer()     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.graphics.GraphicBuffer r9 = android.graphics.GraphicBuffer.createFromHardwareBuffer(r9)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r10 = r1.mAnimLeash     // Catch:{ OutOfResourcesException -> 0x0178 }
            r13 = 2010000(0x1eab90, float:2.81661E-39)
            r8.setLayer(r10, r13)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r10 = r1.mAnimLeash     // Catch:{ OutOfResourcesException -> 0x0178 }
            r8.setPosition(r10, r15, r15)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r10 = r1.mAnimLeash     // Catch:{ OutOfResourcesException -> 0x0178 }
            r8.setAlpha(r10, r14)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r10 = r1.mAnimLeash     // Catch:{ OutOfResourcesException -> 0x0178 }
            r8.show(r10)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r10 = r1.mScreenshotLayer     // Catch:{ OutOfResourcesException -> 0x0178 }
            r8.setBuffer(r10, r9)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r9 = r1.mScreenshotLayer     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.graphics.ColorSpace r10 = r7.getColorSpace()     // Catch:{ OutOfResourcesException -> 0x0178 }
            r8.setColorSpace(r9, r10)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r9 = r1.mScreenshotLayer     // Catch:{ OutOfResourcesException -> 0x0178 }
            r8.show(r9)     // Catch:{ OutOfResourcesException -> 0x0178 }
            if (r3 == r5) goto L_0x0129
            if (r3 != r12) goto L_0x0127
            goto L_0x0129
        L_0x0127:
            r3 = r11
            goto L_0x012a
        L_0x0129:
            r3 = r5
        L_0x012a:
            if (r3 != 0) goto L_0x017e
            android.view.SurfaceControl$Builder r3 = new android.view.SurfaceControl$Builder     // Catch:{ OutOfResourcesException -> 0x0178 }
            r3.<init>(r0)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$Builder r0 = r3.setParent(r2)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$Builder r0 = r0.setColorLayer()     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl$Builder r0 = r0.setCallsite(r6)     // Catch:{ OutOfResourcesException -> 0x0178 }
            java.lang.String r2 = "BackColorSurface"
            android.view.SurfaceControl$Builder r0 = r0.setName(r2)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r0 = r0.build()     // Catch:{ OutOfResourcesException -> 0x0178 }
            r1.mBackColorSurface = r0     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.hardware.HardwareBuffer r0 = r7.getHardwareBuffer()     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.graphics.ColorSpace r2 = r7.getColorSpace()     // Catch:{ OutOfResourcesException -> 0x0178 }
            float r0 = getMedianBorderLuma(r0, r2)     // Catch:{ OutOfResourcesException -> 0x0178 }
            r1.mStartLuma = r0     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r0 = r1.mBackColorSurface     // Catch:{ OutOfResourcesException -> 0x0178 }
            r2 = -1
            r8.setLayer(r0, r2)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r0 = r1.mBackColorSurface     // Catch:{ OutOfResourcesException -> 0x0178 }
            r2 = 3
            float[] r3 = new float[r2]     // Catch:{ OutOfResourcesException -> 0x0178 }
            float r2 = r1.mStartLuma     // Catch:{ OutOfResourcesException -> 0x0178 }
            r3[r11] = r2     // Catch:{ OutOfResourcesException -> 0x0178 }
            r3[r5] = r2     // Catch:{ OutOfResourcesException -> 0x0178 }
            r3[r12] = r2     // Catch:{ OutOfResourcesException -> 0x0178 }
            r8.setColor(r0, r3)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r0 = r1.mBackColorSurface     // Catch:{ OutOfResourcesException -> 0x0178 }
            r8.setAlpha(r0, r14)     // Catch:{ OutOfResourcesException -> 0x0178 }
            android.view.SurfaceControl r0 = r1.mBackColorSurface     // Catch:{ OutOfResourcesException -> 0x0178 }
            r8.show(r0)     // Catch:{ OutOfResourcesException -> 0x0178 }
            goto L_0x017e
        L_0x0178:
            r0 = move-exception
            java.lang.String r2 = "Unable to allocate freeze surface"
            android.util.Slog.w(r4, r2, r0)
        L_0x017e:
            int r0 = r1.mEndRotation
            int r2 = r1.mStartRotation
            int r0 = android.util.RotationUtils.deltaRotation(r0, r2)
            int r2 = r1.mStartWidth
            int r3 = r1.mStartHeight
            android.graphics.Matrix r4 = r1.mSnapshotInitialMatrix
            if (r0 == 0) goto L_0x01b5
            if (r0 == r5) goto L_0x01ab
            if (r0 == r12) goto L_0x01a0
            r6 = 3
            if (r0 == r6) goto L_0x0196
            goto L_0x01b8
        L_0x0196:
            r0 = 1132920832(0x43870000, float:270.0)
            r4.setRotate(r0, r15, r15)
            float r0 = (float) r2
            r4.postTranslate(r15, r0)
            goto L_0x01b8
        L_0x01a0:
            r0 = 1127481344(0x43340000, float:180.0)
            r4.setRotate(r0, r15, r15)
            float r0 = (float) r2
            float r2 = (float) r3
            r4.postTranslate(r0, r2)
            goto L_0x01b8
        L_0x01ab:
            r0 = 1119092736(0x42b40000, float:90.0)
            r4.setRotate(r0, r15, r15)
            float r0 = (float) r3
            r4.postTranslate(r0, r15)
            goto L_0x01b8
        L_0x01b5:
            r4.reset()
        L_0x01b8:
            android.graphics.Matrix r0 = r1.mSnapshotInitialMatrix
            android.view.SurfaceControl r2 = r1.mScreenshotLayer
            if (r2 != 0) goto L_0x01bf
            goto L_0x01f0
        L_0x01bf:
            float[] r2 = r1.mTmpFloats
            r0.getValues(r2)
            float[] r0 = r1.mTmpFloats
            r2 = r0[r12]
            r3 = 5
            r0 = r0[r3]
            android.view.SurfaceControl r3 = r1.mScreenshotLayer
            r8.setPosition(r3, r2, r0)
            android.view.SurfaceControl r3 = r1.mScreenshotLayer
            float[] r0 = r1.mTmpFloats
            r4 = r0[r11]
            r2 = 3
            r6 = r0[r2]
            r7 = r0[r5]
            r2 = 4
            r0 = r0[r2]
            r2 = r20
            r5 = r6
            r6 = r7
            r7 = r0
            r2.setMatrix(r3, r4, r5, r6, r7)
            android.view.SurfaceControl r0 = r1.mScreenshotLayer
            r8.setAlpha(r0, r14)
            android.view.SurfaceControl r0 = r1.mScreenshotLayer
            r8.show(r0)
        L_0x01f0:
            r20.apply()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.transition.ScreenRotationAnimation.<init>(android.content.Context, android.view.SurfaceSession, com.android.wm.shell.common.TransactionPool, android.view.SurfaceControl$Transaction, android.window.TransitionInfo$Change, android.view.SurfaceControl, int):void");
    }
}
