package com.android.p012wm.shell.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.hardware.HardwareBuffer;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.view.Choreographer;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.policy.AttributeCache;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.BaseProtoLogImpl;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0;
import com.android.systemui.p006qs.QSSecurityFooter$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.wm.shell.transition.DefaultTransitionHandler */
public final class DefaultTransitionHandler implements Transitions.TransitionHandler {
    public static boolean sDisableCustomTaskAnimationProperty = SystemProperties.getBoolean("persist.wm.disable_custom_task_animation", true);
    public final ShellExecutor mAnimExecutor;
    public final ArrayMap<IBinder, ArrayList<Animator>> mAnimations = new ArrayMap<>();
    public final Context mContext;
    public final int mCurrentUserId;
    public final DevicePolicyManager mDevicePolicyManager;
    public final DisplayController mDisplayController;
    public C19371 mEnterpriseResourceUpdatedReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("android.app.extra.RESOURCE_TYPE_DRAWABLE", false)) {
                DefaultTransitionHandler defaultTransitionHandler = DefaultTransitionHandler.this;
                Objects.requireNonNull(defaultTransitionHandler);
                defaultTransitionHandler.mEnterpriseThumbnailDrawable = defaultTransitionHandler.mDevicePolicyManager.getDrawable("WORK_PROFILE_ICON", "OUTLINE", "PROFILE_SWITCH_ANIMATION", new QSSecurityFooter$$ExternalSyntheticLambda0(defaultTransitionHandler, 1));
            }
        }
    };
    public Drawable mEnterpriseThumbnailDrawable;
    public final Rect mInsets = new Rect(0, 0, 0, 0);
    public final ShellExecutor mMainExecutor;
    public ScreenRotationAnimation mRotationAnimation;
    public final CounterRotatorHelper mRotator = new CounterRotatorHelper();
    public final SurfaceSession mSurfaceSession = new SurfaceSession();
    public final TransactionPool mTransactionPool;
    public final TransitionAnimation mTransitionAnimation;
    public float mTransitionAnimationScaleSetting = 1.0f;

    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    public static SurfaceControl createExtensionSurface(SurfaceControl surfaceControl, Rect rect, Rect rect2, int i, int i2, String str, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        SurfaceControl build = new SurfaceControl.Builder().setName(str).setParent(surfaceControl).setHidden(true).setCallsite("DefaultTransitionHandler#startAnimation").setOpaque(true).setBufferSize(rect2.width(), rect2.height()).build();
        SurfaceControl.ScreenshotHardwareBuffer captureLayers = SurfaceControl.captureLayers(new SurfaceControl.LayerCaptureArgs.Builder(surfaceControl).setSourceCrop(rect).setFrameScale(1.0f).setPixelFormat(1).setChildrenOnly(true).setAllowProtected(true).build());
        if (captureLayers == null) {
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.getSingleInstance().log(BaseProtoLogImpl.LogLevel.ERROR, ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 457420030, 0, "Failed to capture edge of window.", (Object[]) null);
            }
            return null;
        }
        Bitmap asBitmap = captureLayers.asBitmap();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        BitmapShader bitmapShader = new BitmapShader(asBitmap, tileMode, tileMode);
        Paint paint = new Paint();
        paint.setShader(bitmapShader);
        Surface surface = new Surface(build);
        Canvas lockHardwareCanvas = surface.lockHardwareCanvas();
        lockHardwareCanvas.drawRect(rect2, paint);
        surface.unlockCanvasAndPost(lockHardwareCanvas);
        surface.release();
        transaction.setLayer(build, Integer.MIN_VALUE);
        transaction.setPosition(build, (float) i, (float) i2);
        transaction.setVisibility(build, true);
        transaction2.remove(build);
        return build;
    }

    public static void edgeExtendWindow(TransitionInfo.Change change, Animation animation, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        Animation animation2 = animation;
        Transformation transformation = new Transformation();
        animation2.getTransformationAt(0.0f, transformation);
        Transformation transformation2 = new Transformation();
        animation2.getTransformationAt(1.0f, transformation2);
        Insets min = Insets.min(transformation.getInsets(), transformation2.getInsets());
        int max = Math.max(change.getStartAbsBounds().height(), change.getEndAbsBounds().height());
        int max2 = Math.max(change.getStartAbsBounds().width(), change.getEndAbsBounds().width());
        if (min.left < 0) {
            createExtensionSurface(change.getLeash(), new Rect(0, 0, 1, max), new Rect(0, 0, -min.left, max), min.left, 0, "Left Edge Extension", transaction, transaction2);
        }
        if (min.top < 0) {
            createExtensionSurface(change.getLeash(), new Rect(0, 0, max2, 1), new Rect(0, 0, max2, -min.top), 0, min.top, "Top Edge Extension", transaction, transaction2);
        }
        if (min.right < 0) {
            createExtensionSurface(change.getLeash(), new Rect(max2 - 1, 0, max2, max), new Rect(0, 0, -min.right, max), max2, 0, "Right Edge Extension", transaction, transaction2);
        }
        if (min.bottom < 0) {
            createExtensionSurface(change.getLeash(), new Rect(0, max - 1, max2, max), new Rect(0, 0, max2, -min.bottom), min.left, max, "Bottom Edge Extension", transaction, transaction2);
        }
    }

    @VisibleForTesting
    public static boolean isRotationSeamless(TransitionInfo transitionInfo, DisplayController displayController) {
        boolean z;
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 649960056, 0, "Display is changing, check if it should be seamless.", (Object[]) null);
        }
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        for (int size = transitionInfo.getChanges().size() - 1; size >= 0; size--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(size);
            if (change.getMode() == 6 && change.getEndRotation() != change.getStartRotation()) {
                int i = 3;
                if ((change.getFlags() & 32) == 0) {
                    int i2 = 2;
                    if ((change.getFlags() & 2) != 0) {
                        if (change.getRotationAnimation() != 3) {
                            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                                ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1015274864, 0, "  wallpaper is participating but isn't seamless.", (Object[]) null);
                            }
                            return false;
                        }
                    } else if (change.getTaskInfo() == null) {
                        continue;
                    } else if (change.getRotationAnimation() != 3) {
                        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1915000700, 0, "  task %s isn't requesting seamless, so not seamless.", String.valueOf(change.getTaskInfo().taskId));
                        }
                        return false;
                    } else if (!z4) {
                        DisplayLayout displayLayout = displayController.getDisplayLayout(change.getTaskInfo().displayId);
                        Objects.requireNonNull(displayLayout);
                        if (displayLayout.mWidth > displayLayout.mHeight) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (displayLayout.mRotation % 2 != 0) {
                            z = !z;
                        }
                        if (z) {
                            if (!displayLayout.mReverseDefaultRotation) {
                                i = 1;
                            }
                            i2 = i;
                        }
                        if (change.getStartRotation() == i2 || change.getEndRotation() == i2) {
                            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                                ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1167817788, 0, "  rotation involves upside-down portrait, so not seamless.", (Object[]) null);
                            }
                            return false;
                        } else if (displayLayout.mAllowSeamlessRotationDespiteNavBarMoving || (displayLayout.mNavigationBarCanMove && change.getStartAbsBounds().width() != change.getStartAbsBounds().height())) {
                            z2 = true;
                            z4 = true;
                        } else {
                            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                                ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1167654715, 0, "  nav bar changes sides, so not seamless.", (Object[]) null);
                            }
                            return false;
                        }
                    } else {
                        z2 = true;
                    }
                } else if ((change.getFlags() & 128) != 0) {
                    if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                        ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 42311280, 0, "  display has system alert windows, so not seamless.", (Object[]) null);
                    }
                    return false;
                } else if (change.getRotationAnimation() == 3) {
                    z3 = true;
                } else {
                    z3 = false;
                }
            }
        }
        if (!z2 && !z3) {
            return false;
        }
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1215677233, 0, "  Rotation IS seamless.", (Object[]) null);
        }
        return true;
    }

    public final void attachThumbnailAnimation(ArrayList arrayList, DefaultTransitionHandler$$ExternalSyntheticLambda1 defaultTransitionHandler$$ExternalSyntheticLambda1, TransitionInfo.Change change, TransitionInfo.AnimationOptions animationOptions, float f) {
        boolean z;
        SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
        WindowThumbnail createAndAttach = WindowThumbnail.createAndAttach(this.mSurfaceSession, change.getLeash(), animationOptions.getThumbnail(), acquire);
        Rect endAbsBounds = change.getEndAbsBounds();
        int i = this.mContext.getResources().getConfiguration().orientation;
        TransitionAnimation transitionAnimation = this.mTransitionAnimation;
        Rect rect = this.mInsets;
        HardwareBuffer thumbnail = animationOptions.getThumbnail();
        Rect transitionBounds = animationOptions.getTransitionBounds();
        if (animationOptions.getType() == 3) {
            z = true;
        } else {
            z = false;
        }
        Animation createThumbnailAspectScaleAnimationLocked = transitionAnimation.createThumbnailAspectScaleAnimationLocked(endAbsBounds, rect, thumbnail, i, (Rect) null, transitionBounds, z);
        DefaultTransitionHandler$$ExternalSyntheticLambda4 defaultTransitionHandler$$ExternalSyntheticLambda4 = new DefaultTransitionHandler$$ExternalSyntheticLambda4(this, createAndAttach, acquire, defaultTransitionHandler$$ExternalSyntheticLambda1);
        createThumbnailAspectScaleAnimationLocked.restrictDuration(3000);
        createThumbnailAspectScaleAnimationLocked.scaleCurrentDuration(this.mTransitionAnimationScaleSetting);
        SurfaceControl surfaceControl = createAndAttach.mSurfaceControl;
        TransactionPool transactionPool = this.mTransactionPool;
        ShellExecutor shellExecutor = this.mMainExecutor;
        startSurfaceAnimation(arrayList, createThumbnailAspectScaleAnimationLocked, surfaceControl, defaultTransitionHandler$$ExternalSyntheticLambda4, transactionPool, shellExecutor, this.mAnimExecutor, (Point) null, f, change.getEndAbsBounds());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0128, code lost:
        if (r0 != 3) goto L_0x0130;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x012f, code lost:
        r0 = 0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x02f6  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x0385  */
    /* JADX WARNING: Removed duplicated region for block: B:245:0x053d  */
    /* JADX WARNING: Removed duplicated region for block: B:249:0x0557  */
    /* JADX WARNING: Removed duplicated region for block: B:255:0x057a  */
    /* JADX WARNING: Removed duplicated region for block: B:259:0x0596  */
    /* JADX WARNING: Removed duplicated region for block: B:262:0x059b  */
    /* JADX WARNING: Removed duplicated region for block: B:275:0x05b6  */
    /* JADX WARNING: Removed duplicated region for block: B:304:0x0647  */
    /* JADX WARNING: Removed duplicated region for block: B:308:0x0667  */
    /* JADX WARNING: Removed duplicated region for block: B:311:0x066f  */
    /* JADX WARNING: Removed duplicated region for block: B:312:0x0691  */
    /* JADX WARNING: Removed duplicated region for block: B:315:0x06be  */
    /* JADX WARNING: Removed duplicated region for block: B:344:0x078e  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0175  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01a1  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0250  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x02a5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean startAnimation(android.os.IBinder r34, android.window.TransitionInfo r35, android.view.SurfaceControl.Transaction r36, android.view.SurfaceControl.Transaction r37, com.android.p012wm.shell.transition.Transitions.TransitionFinishCallback r38) {
        /*
            r33 = this;
            r8 = r33
            r9 = r34
            r10 = r35
            r11 = r37
            boolean r0 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled
            r12 = 0
            r13 = 1
            if (r0 == 0) goto L_0x0021
            java.lang.String r0 = java.lang.String.valueOf(r35)
            com.android.wm.shell.protolog.ShellProtoLogGroup r1 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_TRANSITIONS
            r2 = -146110597(0xfffffffff74a877b, float:-4.1077806E33)
            java.lang.Object[] r3 = new java.lang.Object[r13]
            r3[r12] = r0
            java.lang.String r0 = "start default transition animation, info = %s"
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r1, r2, r12, r0, r3)
        L_0x0021:
            int r0 = r35.getType()
            r14 = 11
            r15 = 0
            if (r0 != r14) goto L_0x003b
            boolean r0 = r35.isKeyguardGoingAway()
            if (r0 != 0) goto L_0x003b
            r36.apply()
            r0 = r38
            com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda1 r0 = (com.android.p012wm.shell.transition.Transitions$$ExternalSyntheticLambda1) r0
            r0.onTransitionFinished(r15)
            return r13
        L_0x003b:
            android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<android.animation.Animator>> r0 = r8.mAnimations
            boolean r0 = r0.containsKey(r9)
            if (r0 != 0) goto L_0x0844
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<android.animation.Animator>> r0 = r8.mAnimations
            r0.put(r9, r7)
            com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda1 r6 = new com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda1
            r5 = 0
            r0 = r6
            r1 = r33
            r2 = r7
            r3 = r34
            r4 = r38
            r0.<init>(r1, r2, r3, r4, r5)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.List r0 = r35.getChanges()
            int r0 = r0.size()
            int r0 = r0 - r13
            r1 = r12
            r2 = r1
        L_0x006b:
            if (r0 < 0) goto L_0x0098
            java.util.List r3 = r35.getChanges()
            java.lang.Object r3 = r3.get(r0)
            android.window.TransitionInfo$Change r3 = (android.window.TransitionInfo.Change) r3
            int r4 = r3.getFlags()
            r4 = r4 & r13
            if (r4 == 0) goto L_0x0095
            int r4 = r3.getMode()
            boolean r4 = com.android.p012wm.shell.transition.Transitions.isOpeningType(r4)
            if (r4 == 0) goto L_0x008a
            r1 = r13
            goto L_0x0095
        L_0x008a:
            int r3 = r3.getMode()
            boolean r3 = com.android.p012wm.shell.transition.Transitions.isClosingType(r3)
            if (r3 == 0) goto L_0x0095
            r2 = r13
        L_0x0095:
            int r0 = r0 + -1
            goto L_0x006b
        L_0x0098:
            if (r1 == 0) goto L_0x00ab
            if (r2 == 0) goto L_0x00ab
            int r1 = r35.getType()
            boolean r1 = com.android.p012wm.shell.transition.Transitions.isOpeningType(r1)
            if (r1 == 0) goto L_0x00a8
            r1 = 3
            goto L_0x00a9
        L_0x00a8:
            r1 = 4
        L_0x00a9:
            r2 = r1
            goto L_0x00b4
        L_0x00ab:
            if (r1 == 0) goto L_0x00af
            r2 = r13
            goto L_0x00b4
        L_0x00af:
            if (r2 == 0) goto L_0x00b3
            r2 = 2
            goto L_0x00b4
        L_0x00b3:
            r2 = r12
        L_0x00b4:
            java.util.List r1 = r35.getChanges()
            int r1 = r1.size()
            int r1 = r1 - r13
            r15 = r36
            r26 = r12
        L_0x00c1:
            if (r1 < 0) goto L_0x07b1
            java.util.List r12 = r35.getChanges()
            java.lang.Object r12 = r12.get(r1)
            android.window.TransitionInfo$Change r12 = (android.window.TransitionInfo.Change) r12
            android.app.ActivityManager$RunningTaskInfo r16 = r12.getTaskInfo()
            if (r16 == 0) goto L_0x00d6
            r24 = r13
            goto L_0x00d8
        L_0x00d6:
            r24 = 0
        L_0x00d8:
            int r4 = r12.getMode()
            r14 = 6
            if (r4 != r14) goto L_0x02e1
            int r4 = r12.getFlags()
            r4 = r4 & 32
            if (r4 == 0) goto L_0x02e1
            int r4 = r35.getType()
            if (r4 != r14) goto L_0x02e6
            com.android.wm.shell.common.DisplayController r4 = r8.mDisplayController
            boolean r4 = isRotationSeamless(r10, r4)
            r13 = 0
        L_0x00f4:
            java.util.List r16 = r35.getChanges()
            int r0 = r16.size()
            if (r13 >= r0) goto L_0x012f
            java.util.List r0 = r35.getChanges()
            java.lang.Object r0 = r0.get(r13)
            android.window.TransitionInfo$Change r0 = (android.window.TransitionInfo.Change) r0
            int r3 = r0.getMode()
            if (r3 == r14) goto L_0x010f
            goto L_0x012b
        L_0x010f:
            int r3 = r0.getEndRotation()
            int r14 = r0.getStartRotation()
            if (r3 != r14) goto L_0x011a
            goto L_0x012b
        L_0x011a:
            android.app.ActivityManager$RunningTaskInfo r3 = r0.getTaskInfo()
            if (r3 == 0) goto L_0x012b
            int r0 = r0.getRotationAnimation()
            r3 = -1
            if (r0 == r3) goto L_0x012f
            r3 = 3
            if (r0 != r3) goto L_0x0130
            goto L_0x012f
        L_0x012b:
            int r13 = r13 + 1
            r14 = 6
            goto L_0x00f4
        L_0x012f:
            r0 = 0
        L_0x0130:
            if (r4 != 0) goto L_0x02e1
            r3 = 2
            if (r0 == r3) goto L_0x02e1
            com.android.wm.shell.transition.ScreenRotationAnimation r3 = new com.android.wm.shell.transition.ScreenRotationAnimation
            android.content.Context r4 = r8.mContext
            android.view.SurfaceSession r13 = r8.mSurfaceSession
            com.android.wm.shell.common.TransactionPool r14 = r8.mTransactionPool
            android.view.SurfaceControl r22 = r35.getRootLeash()
            r16 = r3
            r17 = r4
            r18 = r13
            r19 = r14
            r20 = r36
            r21 = r12
            r23 = r0
            r16.<init>(r17, r18, r19, r20, r21, r22, r23)
            r8.mRotationAnimation = r3
            float r0 = r8.mTransitionAnimationScaleSetting
            com.android.wm.shell.common.ShellExecutor r4 = r8.mMainExecutor
            com.android.wm.shell.common.ShellExecutor r12 = r8.mAnimExecutor
            android.view.SurfaceControl r13 = r3.mScreenshotLayer
            if (r13 != 0) goto L_0x0164
            r30 = r1
            r31 = r5
            goto L_0x0374
        L_0x0164:
            int r13 = r3.mAnimHint
            r14 = 1
            if (r13 == r14) goto L_0x0170
            r14 = 2
            if (r13 != r14) goto L_0x016d
            goto L_0x0171
        L_0x016d:
            r16 = 0
            goto L_0x0173
        L_0x0170:
            r14 = 2
        L_0x0171:
            r16 = 1
        L_0x0173:
            if (r16 == 0) goto L_0x01a1
            android.content.Context r14 = r3.mContext
            r30 = r1
            r1 = 2
            if (r13 != r1) goto L_0x0180
            r1 = 17432715(0x10a008b, float:2.5346987E-38)
            goto L_0x0183
        L_0x0180:
            r1 = 17432716(0x10a008c, float:2.534699E-38)
        L_0x0183:
            android.view.animation.Animation r1 = android.view.animation.AnimationUtils.loadAnimation(r14, r1)
            r3.mRotateExitAnimation = r1
            android.content.Context r1 = r3.mContext
            r13 = 17432714(0x10a008a, float:2.5346984E-38)
            android.view.animation.Animation r1 = android.view.animation.AnimationUtils.loadAnimation(r1, r13)
            r3.mRotateEnterAnimation = r1
            android.content.Context r1 = r3.mContext
            r13 = 17432722(0x10a0092, float:2.5347006E-38)
            android.view.animation.Animation r1 = android.view.animation.AnimationUtils.loadAnimation(r1, r13)
            r3.mRotateAlphaAnimation = r1
            goto L_0x0212
        L_0x01a1:
            r30 = r1
            int r1 = r3.mEndRotation
            int r13 = r3.mStartRotation
            int r1 = android.util.RotationUtils.deltaRotation(r1, r13)
            if (r1 == 0) goto L_0x01fc
            r13 = 1
            if (r1 == r13) goto L_0x01e5
            r13 = 2
            if (r1 == r13) goto L_0x01ce
            r13 = 3
            if (r1 == r13) goto L_0x01b7
            goto L_0x0212
        L_0x01b7:
            android.content.Context r1 = r3.mContext
            r13 = 17432727(0x10a0097, float:2.534702E-38)
            android.view.animation.Animation r1 = android.view.animation.AnimationUtils.loadAnimation(r1, r13)
            r3.mRotateExitAnimation = r1
            android.content.Context r1 = r3.mContext
            r13 = 17432726(0x10a0096, float:2.5347017E-38)
            android.view.animation.Animation r1 = android.view.animation.AnimationUtils.loadAnimation(r1, r13)
            r3.mRotateEnterAnimation = r1
            goto L_0x0212
        L_0x01ce:
            android.content.Context r1 = r3.mContext
            r13 = 17432720(0x10a0090, float:2.5347E-38)
            android.view.animation.Animation r1 = android.view.animation.AnimationUtils.loadAnimation(r1, r13)
            r3.mRotateExitAnimation = r1
            android.content.Context r1 = r3.mContext
            r13 = 17432719(0x10a008f, float:2.5346998E-38)
            android.view.animation.Animation r1 = android.view.animation.AnimationUtils.loadAnimation(r1, r13)
            r3.mRotateEnterAnimation = r1
            goto L_0x0212
        L_0x01e5:
            android.content.Context r1 = r3.mContext
            r13 = 17432729(0x10a0099, float:2.5347026E-38)
            android.view.animation.Animation r1 = android.view.animation.AnimationUtils.loadAnimation(r1, r13)
            r3.mRotateExitAnimation = r1
            android.content.Context r1 = r3.mContext
            r13 = 17432728(0x10a0098, float:2.5347023E-38)
            android.view.animation.Animation r1 = android.view.animation.AnimationUtils.loadAnimation(r1, r13)
            r3.mRotateEnterAnimation = r1
            goto L_0x0212
        L_0x01fc:
            android.content.Context r1 = r3.mContext
            r13 = 17432718(0x10a008e, float:2.5346995E-38)
            android.view.animation.Animation r1 = android.view.animation.AnimationUtils.loadAnimation(r1, r13)
            r3.mRotateExitAnimation = r1
            android.content.Context r1 = r3.mContext
            r13 = 17432714(0x10a008a, float:2.5346984E-38)
            android.view.animation.Animation r1 = android.view.animation.AnimationUtils.loadAnimation(r1, r13)
            r3.mRotateEnterAnimation = r1
        L_0x0212:
            android.view.animation.Animation r1 = r3.mRotateExitAnimation
            int r13 = r3.mEndWidth
            int r14 = r3.mEndHeight
            int r9 = r3.mStartWidth
            r31 = r5
            int r5 = r3.mStartHeight
            r1.initialize(r13, r14, r9, r5)
            android.view.animation.Animation r1 = r3.mRotateExitAnimation
            r13 = 10000(0x2710, double:4.9407E-320)
            r1.restrictDuration(r13)
            android.view.animation.Animation r1 = r3.mRotateExitAnimation
            r1.scaleCurrentDuration(r0)
            android.view.animation.Animation r1 = r3.mRotateEnterAnimation
            int r5 = r3.mEndWidth
            int r9 = r3.mEndHeight
            int r13 = r3.mStartWidth
            int r14 = r3.mStartHeight
            r1.initialize(r5, r9, r13, r14)
            android.view.animation.Animation r1 = r3.mRotateEnterAnimation
            r13 = 10000(0x2710, double:4.9407E-320)
            r1.restrictDuration(r13)
            android.view.animation.Animation r1 = r3.mRotateEnterAnimation
            r1.scaleCurrentDuration(r0)
            com.android.wm.shell.common.TransactionPool r1 = r3.mTransactionPool
            android.view.SurfaceControl$Transaction r1 = r1.acquire()
            r3.mTransaction = r1
            if (r16 == 0) goto L_0x02a5
            android.view.animation.Animation r1 = r3.mRotateAlphaAnimation
            int r5 = r3.mEndWidth
            int r9 = r3.mEndHeight
            int r13 = r3.mStartWidth
            int r14 = r3.mStartHeight
            r1.initialize(r5, r9, r13, r14)
            android.view.animation.Animation r1 = r3.mRotateAlphaAnimation
            r13 = 10000(0x2710, double:4.9407E-320)
            r1.restrictDuration(r13)
            android.view.animation.Animation r1 = r3.mRotateAlphaAnimation
            r1.scaleCurrentDuration(r0)
            android.view.animation.Animation r0 = r3.mRotateAlphaAnimation
            android.view.SurfaceControl r1 = r3.mAnimLeash
            com.android.wm.shell.common.TransactionPool r5 = r3.mTransactionPool
            r23 = 0
            r24 = 0
            r25 = 0
            r16 = r7
            r17 = r0
            r18 = r1
            r19 = r6
            r20 = r5
            r21 = r4
            r22 = r12
            startSurfaceAnimation(r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)
            android.view.animation.Animation r0 = r3.mRotateEnterAnimation
            android.view.SurfaceControl r1 = r3.mSurfaceControl
            com.android.wm.shell.common.TransactionPool r3 = r3.mTransactionPool
            r23 = 0
            r24 = 0
            r25 = 0
            r16 = r7
            r17 = r0
            r18 = r1
            r19 = r6
            r20 = r3
            r21 = r4
            r22 = r12
            startSurfaceAnimation(r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)
            goto L_0x0374
        L_0x02a5:
            android.view.animation.Animation r0 = r3.mRotateEnterAnimation
            android.view.SurfaceControl r1 = r3.mSurfaceControl
            com.android.wm.shell.common.TransactionPool r5 = r3.mTransactionPool
            r23 = 0
            r24 = 0
            r25 = 0
            r16 = r7
            r17 = r0
            r18 = r1
            r19 = r6
            r20 = r5
            r21 = r4
            r22 = r12
            startSurfaceAnimation(r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)
            android.view.animation.Animation r0 = r3.mRotateExitAnimation
            android.view.SurfaceControl r1 = r3.mAnimLeash
            com.android.wm.shell.common.TransactionPool r3 = r3.mTransactionPool
            r23 = 0
            r24 = 0
            r25 = 0
            r16 = r7
            r17 = r0
            r18 = r1
            r19 = r6
            r20 = r3
            r21 = r4
            r22 = r12
            startSurfaceAnimation(r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)
            goto L_0x0374
        L_0x02e1:
            r30 = r1
            r31 = r5
            goto L_0x02ef
        L_0x02e6:
            r30 = r1
            r31 = r5
            com.android.wm.shell.transition.CounterRotatorHelper r0 = r8.mRotator
            r0.handleClosingChanges(r10, r15, r12)
        L_0x02ef:
            int r0 = r12.getMode()
            r1 = 6
            if (r0 != r1) goto L_0x036e
            if (r24 == 0) goto L_0x0320
            android.window.WindowContainerToken r0 = r12.getParent()
            if (r0 == 0) goto L_0x0320
            android.window.WindowContainerToken r0 = r12.getParent()
            android.window.TransitionInfo$Change r0 = r10.getChange(r0)
            android.app.ActivityManager$RunningTaskInfo r0 = r0.getTaskInfo()
            if (r0 == 0) goto L_0x0320
            android.app.ActivityManager$RunningTaskInfo r0 = r12.getTaskInfo()
            android.graphics.Point r0 = r0.positionInParent
            android.view.SurfaceControl r1 = r12.getLeash()
            int r3 = r0.x
            float r3 = (float) r3
            int r0 = r0.y
            float r0 = (float) r0
            r15.setPosition(r1, r3, r0)
            goto L_0x0374
        L_0x0320:
            if (r24 == 0) goto L_0x0332
            android.app.ActivityManager$RunningTaskInfo r0 = r12.getTaskInfo()
            android.content.res.Configuration r0 = r0.configuration
            android.app.WindowConfiguration r0 = r0.windowConfiguration
            int r0 = r0.getWindowingMode()
            r1 = 2
            if (r0 != r1) goto L_0x0332
            goto L_0x0374
        L_0x0332:
            android.view.SurfaceControl r0 = r12.getLeash()
            android.graphics.Rect r1 = r12.getEndAbsBounds()
            int r1 = r1.left
            android.graphics.Point r3 = r35.getRootOffset()
            int r3 = r3.x
            int r1 = r1 - r3
            float r1 = (float) r1
            android.graphics.Rect r3 = r12.getEndAbsBounds()
            int r3 = r3.top
            android.graphics.Point r4 = r35.getRootOffset()
            int r4 = r4.y
            int r3 = r3 - r4
            float r3 = (float) r3
            r15.setPosition(r0, r1, r3)
            if (r24 == 0) goto L_0x036e
            android.view.SurfaceControl r0 = r12.getLeash()
            android.graphics.Rect r1 = r12.getEndAbsBounds()
            int r1 = r1.width()
            android.graphics.Rect r3 = r12.getEndAbsBounds()
            int r3 = r3.height()
            r15.setWindowCrop(r0, r1, r3)
        L_0x036e:
            boolean r0 = android.window.TransitionInfo.isIndependent(r12, r10)
            if (r0 != 0) goto L_0x0385
        L_0x0374:
            r13 = r36
            r32 = r7
            r14 = r11
            r0 = r15
            r29 = r30
            r15 = r31
            r7 = 4
            r28 = 2
            r30 = r2
            goto L_0x079e
        L_0x0385:
            int r0 = r35.getType()
            int r1 = r35.getFlags()
            int r3 = r12.getMode()
            int r4 = r12.getFlags()
            boolean r5 = com.android.p012wm.shell.transition.Transitions.isOpeningType(r0)
            boolean r9 = com.android.p012wm.shell.transition.Transitions.isOpeningType(r3)
            android.app.ActivityManager$RunningTaskInfo r13 = r12.getTaskInfo()
            if (r13 == 0) goto L_0x03a5
            r13 = 1
            goto L_0x03a6
        L_0x03a5:
            r13 = 0
        L_0x03a6:
            android.window.TransitionInfo$AnimationOptions r14 = r35.getAnimationOptions()
            if (r14 == 0) goto L_0x03b1
            int r15 = r14.getType()
            goto L_0x03b2
        L_0x03b1:
            r15 = 0
        L_0x03b2:
            if (r13 == 0) goto L_0x03bc
            boolean r17 = sDisableCustomTaskAnimationProperty
            if (r17 != 0) goto L_0x03b9
            goto L_0x03bc
        L_0x03b9:
            r17 = 0
            goto L_0x03be
        L_0x03bc:
            r17 = 1
        L_0x03be:
            boolean r18 = com.android.p012wm.shell.transition.Transitions.isClosingType(r3)
            if (r18 == 0) goto L_0x03e9
            com.android.wm.shell.transition.CounterRotatorHelper r10 = r8.mRotator
            java.util.Objects.requireNonNull(r10)
            r25 = r6
            int r6 = r10.mLastRotationDelta
            if (r6 != 0) goto L_0x03d6
            android.graphics.Rect r6 = r12.getEndAbsBounds()
            r32 = r7
            goto L_0x03f1
        L_0x03d6:
            android.graphics.Rect r6 = new android.graphics.Rect
            r32 = r7
            android.graphics.Rect r7 = r12.getEndAbsBounds()
            r6.<init>(r7)
            android.graphics.Rect r7 = r10.mLastDisplayBounds
            int r10 = r10.mLastRotationDelta
            android.util.RotationUtils.rotateBounds(r6, r7, r10)
            goto L_0x03f1
        L_0x03e9:
            r25 = r6
            r32 = r7
            android.graphics.Rect r6 = r12.getEndAbsBounds()
        L_0x03f1:
            boolean r7 = r35.isKeyguardGoingAway()
            r10 = 12
            if (r7 == 0) goto L_0x0407
            com.android.internal.policy.TransitionAnimation r0 = r8.mTransitionAnimation
            r3 = r4 & 1
            if (r3 == 0) goto L_0x0401
            r3 = 1
            goto L_0x0402
        L_0x0401:
            r3 = 0
        L_0x0402:
            android.view.animation.Animation r0 = r0.loadKeyguardExitAnimation(r1, r3)
            goto L_0x0411
        L_0x0407:
            r1 = 9
            if (r0 != r1) goto L_0x0415
            com.android.internal.policy.TransitionAnimation r0 = r8.mTransitionAnimation
            android.view.animation.Animation r0 = r0.loadKeyguardUnoccludeAnimation()
        L_0x0411:
            r11 = 11
            goto L_0x0578
        L_0x0415:
            r7 = r4 & 16
            if (r7 == 0) goto L_0x0429
            if (r5 == 0) goto L_0x0422
            com.android.internal.policy.TransitionAnimation r0 = r8.mTransitionAnimation
            android.view.animation.Animation r0 = r0.loadVoiceActivityOpenAnimation(r9)
            goto L_0x0411
        L_0x0422:
            com.android.internal.policy.TransitionAnimation r0 = r8.mTransitionAnimation
            android.view.animation.Animation r0 = r0.loadVoiceActivityExitAnimation(r9)
            goto L_0x0411
        L_0x0429:
            r7 = 6
            if (r3 != r7) goto L_0x0439
            android.view.animation.AlphaAnimation r0 = new android.view.animation.AlphaAnimation
            r1 = 1065353216(0x3f800000, float:1.0)
            r0.<init>(r1, r1)
            r3 = 336(0x150, double:1.66E-321)
            r0.setDuration(r3)
            goto L_0x0411
        L_0x0439:
            r3 = 5
            if (r0 != r3) goto L_0x0445
            com.android.internal.policy.TransitionAnimation r0 = r8.mTransitionAnimation
            android.graphics.Rect r1 = r8.mInsets
            android.view.animation.Animation r0 = r0.createRelaunchAnimation(r6, r1, r6)
            goto L_0x0411
        L_0x0445:
            r11 = 1
            if (r15 != r11) goto L_0x0466
            if (r17 != 0) goto L_0x0450
            boolean r11 = r14.getOverrideTaskTransition()
            if (r11 == 0) goto L_0x0466
        L_0x0450:
            com.android.internal.policy.TransitionAnimation r0 = r8.mTransitionAnimation
            java.lang.String r1 = r14.getPackageName()
            if (r9 == 0) goto L_0x045d
            int r3 = r14.getEnterResId()
            goto L_0x0461
        L_0x045d:
            int r3 = r14.getExitResId()
        L_0x0461:
            android.view.animation.Animation r0 = r0.loadAnimationRes(r1, r3)
            goto L_0x0411
        L_0x0466:
            if (r15 != r10) goto L_0x0471
            if (r9 == 0) goto L_0x0471
            com.android.internal.policy.TransitionAnimation r0 = r8.mTransitionAnimation
            android.view.animation.Animation r0 = r0.loadCrossProfileAppEnterAnimation()
            goto L_0x0411
        L_0x0471:
            r11 = 11
            if (r15 != r11) goto L_0x048d
            com.android.internal.policy.TransitionAnimation r1 = r8.mTransitionAnimation
            android.graphics.Rect r22 = r14.getTransitionBounds()
            r16 = r1
            r17 = r0
            r18 = r2
            r19 = r9
            r20 = r6
            r21 = r6
            android.view.animation.Animation r0 = r16.createClipRevealAnimationLocked(r17, r18, r19, r20, r21, r22)
            goto L_0x0578
        L_0x048d:
            r1 = 2
            if (r15 != r1) goto L_0x04a6
            com.android.internal.policy.TransitionAnimation r1 = r8.mTransitionAnimation
            android.graphics.Rect r21 = r14.getTransitionBounds()
            r16 = r1
            r17 = r0
            r18 = r2
            r19 = r9
            r20 = r6
            android.view.animation.Animation r0 = r16.createScaleUpAnimationLocked(r17, r18, r19, r20, r21)
            goto L_0x0578
        L_0x04a6:
            r1 = 3
            if (r15 == r1) goto L_0x0559
            r3 = 4
            if (r15 != r3) goto L_0x04ae
            goto L_0x0559
        L_0x04ae:
            r3 = r4 & 8
            if (r3 == 0) goto L_0x04b9
            if (r5 == 0) goto L_0x04b9
            r3 = 3000(0xbb8, double:1.482E-320)
            r5 = 0
            goto L_0x0599
        L_0x04b9:
            r3 = 14
            if (r2 != r1) goto L_0x04c7
            if (r9 == 0) goto L_0x04c3
            r1 = 20
            goto L_0x0537
        L_0x04c3:
            r1 = 21
            goto L_0x0537
        L_0x04c7:
            r1 = 4
            if (r2 != r1) goto L_0x04d4
            if (r9 == 0) goto L_0x04d0
            r1 = 22
            goto L_0x0537
        L_0x04d0:
            r1 = 23
            goto L_0x0537
        L_0x04d4:
            r1 = 1
            if (r2 != r1) goto L_0x04e1
            if (r9 == 0) goto L_0x04dd
            r1 = 16
            goto L_0x0537
        L_0x04dd:
            r1 = 17
            goto L_0x0537
        L_0x04e1:
            r1 = 2
            if (r2 != r1) goto L_0x04ed
            if (r9 == 0) goto L_0x04ea
            r1 = 18
            goto L_0x0537
        L_0x04ea:
            r1 = 19
            goto L_0x0537
        L_0x04ed:
            r1 = 1
            if (r0 != r1) goto L_0x0509
            if (r13 == 0) goto L_0x04fa
            if (r9 == 0) goto L_0x04f7
            r1 = 8
            goto L_0x0537
        L_0x04f7:
            r1 = 9
            goto L_0x0537
        L_0x04fa:
            r0 = r4 & 4
            if (r0 == 0) goto L_0x0502
            if (r9 == 0) goto L_0x0502
            r0 = 1
            goto L_0x0503
        L_0x0502:
            r0 = 0
        L_0x0503:
            if (r9 == 0) goto L_0x0507
            r1 = 4
            goto L_0x053b
        L_0x0507:
            r1 = 5
            goto L_0x053b
        L_0x0509:
            r1 = 3
            if (r0 != r1) goto L_0x0513
            if (r9 == 0) goto L_0x0510
            r1 = r10
            goto L_0x0537
        L_0x0510:
            r1 = 13
            goto L_0x0537
        L_0x0513:
            r1 = 2
            if (r0 != r1) goto L_0x052e
            if (r13 == 0) goto L_0x051f
            if (r9 == 0) goto L_0x051d
            r1 = 10
            goto L_0x0537
        L_0x051d:
            r1 = r11
            goto L_0x0537
        L_0x051f:
            r0 = r4 & 4
            if (r0 == 0) goto L_0x0527
            if (r9 != 0) goto L_0x0527
            r0 = 1
            goto L_0x0528
        L_0x0527:
            r0 = 0
        L_0x0528:
            if (r9 == 0) goto L_0x052c
            r1 = r7
            goto L_0x053b
        L_0x052c:
            r1 = 7
            goto L_0x053b
        L_0x052e:
            r1 = 4
            if (r0 != r1) goto L_0x0539
            if (r9 == 0) goto L_0x0535
            r1 = r3
            goto L_0x0537
        L_0x0535:
            r1 = 15
        L_0x0537:
            r0 = 0
            goto L_0x053b
        L_0x0539:
            r0 = 0
            r1 = 0
        L_0x053b:
            if (r1 == 0) goto L_0x0557
            if (r15 != r3) goto L_0x0550
            if (r17 == 0) goto L_0x0550
            com.android.internal.policy.TransitionAnimation r3 = r8.mTransitionAnimation
            java.lang.String r4 = r14.getPackageName()
            int r5 = r14.getAnimations()
            android.view.animation.Animation r0 = r3.loadAnimationAttr(r4, r5, r1, r0)
            goto L_0x0578
        L_0x0550:
            com.android.internal.policy.TransitionAnimation r0 = r8.mTransitionAnimation
            android.view.animation.Animation r0 = r0.loadDefaultAnimationAttr(r1)
            goto L_0x0578
        L_0x0557:
            r0 = 0
            goto L_0x0578
        L_0x0559:
            if (r15 != r1) goto L_0x055e
            r18 = 1
            goto L_0x0560
        L_0x055e:
            r18 = 0
        L_0x0560:
            com.android.internal.policy.TransitionAnimation r1 = r8.mTransitionAnimation
            android.hardware.HardwareBuffer r22 = r14.getThumbnail()
            android.graphics.Rect r23 = r14.getTransitionBounds()
            r16 = r1
            r17 = r9
            r19 = r6
            r20 = r0
            r21 = r2
            android.view.animation.Animation r0 = r16.createThumbnailEnterExitAnimationLocked(r17, r18, r19, r20, r21, r22, r23)
        L_0x0578:
            if (r0 == 0) goto L_0x0596
            boolean r1 = r0.isInitialized()
            if (r1 != 0) goto L_0x058b
            int r1 = r6.width()
            int r3 = r6.height()
            r0.initialize(r1, r3, r1, r3)
        L_0x058b:
            r3 = 3000(0xbb8, double:1.482E-320)
            r0.restrictDuration(r3)
            float r1 = r8.mTransitionAnimationScaleSetting
            r0.scaleCurrentDuration(r1)
            goto L_0x0598
        L_0x0596:
            r3 = 3000(0xbb8, double:1.482E-320)
        L_0x0598:
            r5 = r0
        L_0x0599:
            if (r5 == 0) goto L_0x078e
            if (r24 == 0) goto L_0x05c6
            int r0 = r35.getType()
            r1 = 1
            if (r0 == r1) goto L_0x05b0
            r1 = 2
            r6 = 3
            if (r0 == r1) goto L_0x05b2
            r7 = 4
            if (r0 == r6) goto L_0x05b3
            if (r0 != r7) goto L_0x05ae
            goto L_0x05b3
        L_0x05ae:
            r0 = 0
            goto L_0x05b4
        L_0x05b0:
            r1 = 2
            r6 = 3
        L_0x05b2:
            r7 = 4
        L_0x05b3:
            r0 = 1
        L_0x05b4:
            if (r0 == 0) goto L_0x05c9
            android.app.ActivityThread r0 = android.app.ActivityThread.currentActivityThread()
            android.app.ContextImpl r0 = r0.getSystemUiContext()
            r9 = 17170984(0x1060228, float:2.461346E-38)
            int r26 = r0.getColor(r9)
            goto L_0x05c9
        L_0x05c6:
            r1 = 2
            r6 = 3
            r7 = 4
        L_0x05c9:
            boolean r0 = r5.hasRoundedCorners()
            r9 = 0
            if (r0 == 0) goto L_0x05e6
            if (r24 == 0) goto L_0x05e6
            com.android.wm.shell.common.DisplayController r0 = r8.mDisplayController
            android.app.ActivityManager$RunningTaskInfo r13 = r12.getTaskInfo()
            int r13 = r13.displayId
            android.content.Context r0 = r0.getDisplayContext(r13)
            if (r0 != 0) goto L_0x05e1
            goto L_0x05e6
        L_0x05e1:
            float r0 = com.android.internal.policy.ScreenDecorationsUtils.getWindowCornerRadius(r0)
            r9 = r0
        L_0x05e6:
            boolean r0 = r5.getShowBackground()
            if (r0 == 0) goto L_0x060b
            android.window.TransitionInfo$AnimationOptions r0 = r35.getAnimationOptions()
            int r0 = r0.getBackgroundColor()
            if (r0 == 0) goto L_0x05ff
            android.window.TransitionInfo$AnimationOptions r0 = r35.getAnimationOptions()
            int r0 = r0.getBackgroundColor()
            goto L_0x0609
        L_0x05ff:
            int r0 = r12.getBackgroundColor()
            if (r0 == 0) goto L_0x060b
            int r0 = r12.getBackgroundColor()
        L_0x0609:
            r26 = r0
        L_0x060b:
            if (r24 != 0) goto L_0x0637
            boolean r0 = r5.hasExtension()
            if (r0 == 0) goto L_0x0637
            int r0 = r12.getMode()
            boolean r0 = com.android.p012wm.shell.transition.Transitions.isOpeningType(r0)
            if (r0 != 0) goto L_0x0627
            r13 = r36
            r14 = r37
            edgeExtendWindow(r12, r5, r13, r14)
        L_0x0624:
            r15 = r31
            goto L_0x063c
        L_0x0627:
            r13 = r36
            r14 = r37
            com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda5 r0 = new com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda5
            r0.<init>(r8, r12, r5, r14)
            r15 = r31
            r15.add(r0)
            r0 = 1
            goto L_0x063d
        L_0x0637:
            r13 = r36
            r14 = r37
            goto L_0x0624
        L_0x063c:
            r0 = 0
        L_0x063d:
            int r16 = r12.getMode()
            boolean r16 = com.android.p012wm.shell.transition.Transitions.isClosingType(r16)
            if (r16 == 0) goto L_0x0667
            com.android.wm.shell.transition.CounterRotatorHelper r1 = r8.mRotator
            java.util.Objects.requireNonNull(r1)
            int r3 = r1.mLastRotationDelta
            if (r3 != 0) goto L_0x0655
            android.graphics.Rect r1 = r12.getEndAbsBounds()
            goto L_0x066b
        L_0x0655:
            android.graphics.Rect r3 = new android.graphics.Rect
            android.graphics.Rect r4 = r12.getEndAbsBounds()
            r3.<init>(r4)
            android.graphics.Rect r4 = r1.mLastDisplayBounds
            int r1 = r1.mLastRotationDelta
            android.util.RotationUtils.rotateBounds(r3, r4, r1)
            r1 = r3
            goto L_0x066b
        L_0x0667:
            android.graphics.Rect r1 = r12.getEndAbsBounds()
        L_0x066b:
            r27 = r1
            if (r0 == 0) goto L_0x0691
            com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda6 r4 = new com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda6
            r28 = 2
            r0 = r4
            r29 = r30
            r1 = r33
            r30 = r2
            r2 = r32
            r3 = r5
            r5 = r7
            r7 = r4
            r4 = r12
            r11 = r5
            r5 = r25
            r11 = r6
            r38 = r25
            r6 = r9
            r11 = r7
            r7 = r27
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            r15.add(r11)
            goto L_0x06b8
        L_0x0691:
            r38 = r25
            r29 = r30
            r28 = 2
            r30 = r2
            android.view.SurfaceControl r18 = r12.getLeash()
            com.android.wm.shell.common.TransactionPool r0 = r8.mTransactionPool
            com.android.wm.shell.common.ShellExecutor r1 = r8.mMainExecutor
            com.android.wm.shell.common.ShellExecutor r2 = r8.mAnimExecutor
            r23 = 0
            r16 = r32
            r17 = r5
            r19 = r38
            r20 = r0
            r21 = r1
            r22 = r2
            r24 = r9
            r25 = r27
            startSurfaceAnimation(r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)
        L_0x06b8:
            android.window.TransitionInfo$AnimationOptions r0 = r35.getAnimationOptions()
            if (r0 == 0) goto L_0x0789
            android.window.TransitionInfo$AnimationOptions r4 = r35.getAnimationOptions()
            android.app.ActivityManager$RunningTaskInfo r0 = r12.getTaskInfo()
            if (r0 == 0) goto L_0x06ca
            r0 = 1
            goto L_0x06cb
        L_0x06ca:
            r0 = 0
        L_0x06cb:
            int r1 = r12.getMode()
            boolean r1 = com.android.p012wm.shell.transition.Transitions.isOpeningType(r1)
            int r2 = r12.getMode()
            boolean r2 = com.android.p012wm.shell.transition.Transitions.isClosingType(r2)
            if (r1 == 0) goto L_0x0773
            int r1 = r4.getType()
            if (r1 != r10) goto L_0x075f
            if (r0 == 0) goto L_0x075f
            android.graphics.Rect r0 = r12.getEndAbsBounds()
            android.app.ActivityManager$RunningTaskInfo r1 = r12.getTaskInfo()
            int r1 = r1.userId
            int r2 = r8.mCurrentUserId
            if (r1 != r2) goto L_0x06fd
            android.content.Context r1 = r8.mContext
            r2 = 17302303(0x108031f, float:2.4981494E-38)
            android.graphics.drawable.Drawable r1 = r1.getDrawable(r2)
            goto L_0x06ff
        L_0x06fd:
            android.graphics.drawable.Drawable r1 = r8.mEnterpriseThumbnailDrawable
        L_0x06ff:
            com.android.internal.policy.TransitionAnimation r2 = r8.mTransitionAnimation
            android.hardware.HardwareBuffer r1 = r2.createCrossProfileAppsThumbnail(r1, r0)
            if (r1 != 0) goto L_0x0709
            goto L_0x0789
        L_0x0709:
            com.android.wm.shell.common.TransactionPool r2 = r8.mTransactionPool
            android.view.SurfaceControl$Transaction r2 = r2.acquire()
            android.view.SurfaceSession r3 = r8.mSurfaceSession
            android.view.SurfaceControl r4 = r12.getLeash()
            com.android.wm.shell.transition.WindowThumbnail r1 = com.android.p012wm.shell.transition.WindowThumbnail.createAndAttach(r3, r4, r1, r2)
            com.android.internal.policy.TransitionAnimation r3 = r8.mTransitionAnimation
            android.view.animation.Animation r3 = r3.createCrossProfileAppsThumbnailAnimationLocked(r0)
            if (r3 != 0) goto L_0x0723
            goto L_0x0789
        L_0x0723:
            com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda3 r4 = new com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda3
            r6 = r38
            r4.<init>(r8, r1, r2, r6)
            r10 = 3000(0xbb8, double:1.482E-320)
            r3.restrictDuration(r10)
            float r2 = r8.mTransitionAnimationScaleSetting
            r3.scaleCurrentDuration(r2)
            android.view.SurfaceControl r1 = r1.mSurfaceControl
            com.android.wm.shell.common.TransactionPool r2 = r8.mTransactionPool
            com.android.wm.shell.common.ShellExecutor r5 = r8.mMainExecutor
            com.android.wm.shell.common.ShellExecutor r7 = r8.mAnimExecutor
            android.graphics.Point r10 = new android.graphics.Point
            int r11 = r0.left
            int r0 = r0.top
            r10.<init>(r11, r0)
            android.graphics.Rect r25 = r12.getEndAbsBounds()
            r16 = r32
            r17 = r3
            r18 = r1
            r19 = r4
            r20 = r2
            r21 = r5
            r22 = r7
            r23 = r10
            r24 = r9
            startSurfaceAnimation(r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)
            goto L_0x078b
        L_0x075f:
            r6 = r38
            int r0 = r4.getType()
            r1 = 3
            if (r0 != r1) goto L_0x078b
            r0 = r33
            r1 = r32
            r2 = r6
            r3 = r12
            r5 = r9
            r0.attachThumbnailAnimation(r1, r2, r3, r4, r5)
            goto L_0x078b
        L_0x0773:
            r6 = r38
            if (r2 == 0) goto L_0x078b
            int r0 = r4.getType()
            r7 = 4
            if (r0 != r7) goto L_0x078c
            r0 = r33
            r1 = r32
            r2 = r6
            r3 = r12
            r5 = r9
            r0.attachThumbnailAnimation(r1, r2, r3, r4, r5)
            goto L_0x078c
        L_0x0789:
            r6 = r38
        L_0x078b:
            r7 = 4
        L_0x078c:
            r0 = r13
            goto L_0x079e
        L_0x078e:
            r13 = r36
            r14 = r37
            r6 = r25
            r29 = r30
            r15 = r31
            r7 = 4
            r28 = 2
            r30 = r2
            goto L_0x078c
        L_0x079e:
            int r1 = r29 + -1
            r9 = r34
            r10 = r35
            r11 = r14
            r5 = r15
            r2 = r30
            r7 = r32
            r12 = 0
            r13 = 1
            r14 = 11
            r15 = r0
            goto L_0x00c1
        L_0x07b1:
            r0 = r5
            r14 = r11
            r28 = 2
            if (r26 == 0) goto L_0x0802
            android.view.SurfaceControl r1 = r35.getRootLeash()
            android.graphics.Color r2 = android.graphics.Color.valueOf(r26)
            r3 = 3
            float[] r3 = new float[r3]
            float r4 = r2.red()
            r5 = 0
            r3[r5] = r4
            float r4 = r2.green()
            r7 = 1
            r3[r7] = r4
            float r2 = r2.blue()
            r3[r28] = r2
            android.view.SurfaceControl$Builder r2 = new android.view.SurfaceControl$Builder
            r2.<init>()
            java.lang.String r4 = "Animation Background"
            android.view.SurfaceControl$Builder r2 = r2.setName(r4)
            android.view.SurfaceControl$Builder r1 = r2.setParent(r1)
            android.view.SurfaceControl$Builder r1 = r1.setColorLayer()
            android.view.SurfaceControl$Builder r1 = r1.setOpaque(r7)
            android.view.SurfaceControl r1 = r1.build()
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            android.view.SurfaceControl$Transaction r2 = r15.setLayer(r1, r2)
            android.view.SurfaceControl$Transaction r2 = r2.setColor(r1, r3)
            r2.show(r1)
            r14.remove(r1)
            goto L_0x0803
        L_0x0802:
            r5 = 0
        L_0x0803:
            int r1 = r0.size()
            if (r1 <= 0) goto L_0x080b
            r12 = 1
            goto L_0x080c
        L_0x080b:
            r12 = r5
        L_0x080c:
            r15.apply(r12)
            java.util.Iterator r0 = r0.iterator()
        L_0x0813:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0831
            java.lang.Object r1 = r0.next()
            java.util.function.Consumer r1 = (java.util.function.Consumer) r1
            com.android.wm.shell.common.TransactionPool r2 = r8.mTransactionPool
            android.view.SurfaceControl$Transaction r2 = r2.acquire()
            r1.accept(r2)
            r2.apply()
            com.android.wm.shell.common.TransactionPool r1 = r8.mTransactionPool
            r1.release(r2)
            goto L_0x0813
        L_0x0831:
            com.android.wm.shell.transition.CounterRotatorHelper r0 = r8.mRotator
            r0.cleanUp(r14)
            android.window.TransitionMetrics r0 = android.window.TransitionMetrics.getInstance()
            r1 = r34
            r0.reportAnimationStart(r1)
            r6.run()
            r0 = 1
            return r0
        L_0x0844:
            r1 = r9
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Got a duplicate startAnimation call for "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.transition.DefaultTransitionHandler.startAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):boolean");
    }

    public DefaultTransitionHandler(DisplayController displayController, TransactionPool transactionPool, Context context, ShellExecutor shellExecutor, Handler handler, ShellExecutor shellExecutor2) {
        this.mDisplayController = displayController;
        this.mTransactionPool = transactionPool;
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mAnimExecutor = shellExecutor2;
        this.mTransitionAnimation = new TransitionAnimation(context, false, "ShellTransitions");
        this.mCurrentUserId = UserHandle.myUserId();
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mDevicePolicyManager = devicePolicyManager;
        this.mEnterpriseThumbnailDrawable = devicePolicyManager.getDrawable("WORK_PROFILE_ICON", "OUTLINE", "PROFILE_SWITCH_ANIMATION", new QSSecurityFooter$$ExternalSyntheticLambda0(this, 1));
        context.registerReceiver(this.mEnterpriseResourceUpdatedReceiver, new IntentFilter("android.app.action.DEVICE_POLICY_RESOURCE_UPDATED"), (String) null, handler);
        AttributeCache.init(context);
    }

    public static void applyTransformation(long j, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Animation animation, Transformation transformation, float[] fArr, Point point, float f, Rect rect) {
        animation.getTransformation(j, transformation);
        if (point != null) {
            transformation.getMatrix().postTranslate((float) point.x, (float) point.y);
        }
        transaction.setMatrix(surfaceControl, transformation.getMatrix(), fArr);
        transaction.setAlpha(surfaceControl, transformation.getAlpha());
        Insets min = Insets.min(transformation.getInsets(), Insets.NONE);
        if (!min.equals(Insets.NONE) && rect != null && !rect.isEmpty()) {
            rect.inset(min);
            transaction.setCrop(surfaceControl, rect);
        }
        if (animation.hasRoundedCorners() && f > 0.0f && rect != null) {
            transaction.setCrop(surfaceControl, rect);
            transaction.setCornerRadius(surfaceControl, f);
        }
        transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        transaction.apply();
    }

    public static void startSurfaceAnimation(ArrayList<Animator> arrayList, Animation animation, SurfaceControl surfaceControl, Runnable runnable, TransactionPool transactionPool, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, Point point, float f, Rect rect) {
        SurfaceControl.Transaction acquire = transactionPool.acquire();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        Transformation transformation = new Transformation();
        ofFloat.overrideDurationScale(1.0f);
        ofFloat.setDuration(animation.computeDurationHint());
        ValueAnimator valueAnimator = ofFloat;
        SurfaceControl.Transaction transaction = acquire;
        SurfaceControl surfaceControl2 = surfaceControl;
        Animation animation2 = animation;
        Transformation transformation2 = transformation;
        float[] fArr = new float[9];
        Point point2 = point;
        float f2 = f;
        Rect rect2 = rect;
        ofFloat.addUpdateListener(new DefaultTransitionHandler$$ExternalSyntheticLambda0(valueAnimator, transaction, surfaceControl2, animation2, transformation2, fArr, point2, f2, rect2));
        final DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda2 = r0;
        DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda22 = new DefaultTransitionHandler$$ExternalSyntheticLambda2(valueAnimator, transaction, surfaceControl2, animation2, transformation2, fArr, point2, f2, rect2, transactionPool, shellExecutor, arrayList, runnable);
        ofFloat.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationCancel(Animator animator) {
                defaultTransitionHandler$$ExternalSyntheticLambda2.run();
            }

            public final void onAnimationEnd(Animator animator) {
                defaultTransitionHandler$$ExternalSyntheticLambda2.run();
            }
        });
        arrayList.add(ofFloat);
        shellExecutor2.execute(new DozeScreenState$$ExternalSyntheticLambda0(ofFloat, 9));
    }

    public final void setAnimScaleSetting(float f) {
        this.mTransitionAnimationScaleSetting = f;
    }
}
