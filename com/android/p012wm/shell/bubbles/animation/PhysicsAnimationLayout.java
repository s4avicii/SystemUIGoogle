package com.android.p012wm.shell.bubbles.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.emoji2.text.C0152xfb0118ab;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BadgedImageView;
import com.android.p012wm.shell.common.ExecutorUtils$$ExternalSyntheticLambda1;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout */
public final class PhysicsAnimationLayout extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public PhysicsAnimationController mController;
    public final HashMap<DynamicAnimation.ViewProperty, Runnable> mEndActionForProperty = new HashMap<>();

    /* renamed from: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$AllAnimationsForPropertyFinishedEndListener */
    public class AllAnimationsForPropertyFinishedEndListener implements DynamicAnimation.OnAnimationEndListener {
        public DynamicAnimation.ViewProperty mProperty;

        public AllAnimationsForPropertyFinishedEndListener(DynamicAnimation.ViewProperty viewProperty) {
            this.mProperty = viewProperty;
        }

        public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
            Runnable runnable;
            PhysicsAnimationLayout physicsAnimationLayout = PhysicsAnimationLayout.this;
            boolean z2 = true;
            DynamicAnimation.ViewProperty[] viewPropertyArr = {this.mProperty};
            Objects.requireNonNull(physicsAnimationLayout);
            int i = 0;
            while (true) {
                if (i >= physicsAnimationLayout.getChildCount()) {
                    z2 = false;
                    break;
                } else if (PhysicsAnimationLayout.arePropertiesAnimatingOnView(physicsAnimationLayout.getChildAt(i), viewPropertyArr)) {
                    break;
                } else {
                    i++;
                }
            }
            if (!z2 && PhysicsAnimationLayout.this.mEndActionForProperty.containsKey(this.mProperty) && (runnable = PhysicsAnimationLayout.this.mEndActionForProperty.get(this.mProperty)) != null) {
                runnable.run();
            }
        }
    }

    /* renamed from: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$PhysicsAnimationController */
    public static abstract class PhysicsAnimationController {
        public PhysicsAnimationLayout mLayout;

        /* renamed from: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$PhysicsAnimationController$ChildAnimationConfigurator */
        public interface ChildAnimationConfigurator {
            void configureAnimationForChildAtIndex(int i, PhysicsPropertyAnimator physicsPropertyAnimator);
        }

        public abstract HashSet getAnimatedProperties();

        public abstract int getNextAnimationInChain(DynamicAnimation.ViewProperty viewProperty, int i);

        public abstract float getOffsetForChainedPropertyAnimation(DynamicAnimation.ViewProperty viewProperty, int i);

        public abstract SpringForce getSpringForce();

        public abstract void onActiveControllerForLayout(PhysicsAnimationLayout physicsAnimationLayout);

        public abstract void onChildAdded(View view, int i);

        public abstract void onChildRemoved(View view, ExecutorUtils$$ExternalSyntheticLambda1 executorUtils$$ExternalSyntheticLambda1);

        public abstract void onChildReordered();

        public final C1831x49ea1e05 animationsForChildrenFromIndex(ChildAnimationConfigurator childAnimationConfigurator) {
            HashSet hashSet = new HashSet();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.mLayout.getChildCount(); i++) {
                PhysicsPropertyAnimator animationForChild = animationForChild(this.mLayout.getChildAt(i));
                childAnimationConfigurator.configureAnimationForChildAtIndex(i, animationForChild);
                HashSet hashSet2 = new HashSet(animationForChild.mAnimatedProperties.keySet());
                if (animationForChild.mPathAnimator != null) {
                    hashSet2.add(DynamicAnimation.TRANSLATION_X);
                    hashSet2.add(DynamicAnimation.TRANSLATION_Y);
                }
                hashSet.addAll(hashSet2);
                arrayList.add(animationForChild);
            }
            return new C1831x49ea1e05(this, hashSet, arrayList);
        }

        public final boolean isActiveController() {
            PhysicsAnimationLayout physicsAnimationLayout = this.mLayout;
            if (physicsAnimationLayout == null || this != physicsAnimationLayout.mController) {
                return false;
            }
            return true;
        }

        public final PhysicsPropertyAnimator animationForChild(View view) {
            PhysicsPropertyAnimator physicsPropertyAnimator = (PhysicsPropertyAnimator) view.getTag(C1777R.C1779id.physics_animator_tag);
            if (physicsPropertyAnimator == null) {
                PhysicsAnimationLayout physicsAnimationLayout = this.mLayout;
                Objects.requireNonNull(physicsAnimationLayout);
                physicsPropertyAnimator = new PhysicsPropertyAnimator(view);
                view.setTag(C1777R.C1779id.physics_animator_tag, physicsPropertyAnimator);
            }
            physicsPropertyAnimator.clearAnimator();
            physicsPropertyAnimator.mAssociatedController = this;
            return physicsPropertyAnimator;
        }
    }

    /* renamed from: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$PhysicsPropertyAnimator */
    public class PhysicsPropertyAnimator {
        public HashMap mAnimatedProperties = new HashMap();
        public PhysicsAnimationController mAssociatedController;
        public PointF mCurrentPointOnPath = new PointF();
        public final C18261 mCurrentPointOnPathXProperty = new FloatProperty<PhysicsPropertyAnimator>() {
            public final Object get(Object obj) {
                PhysicsPropertyAnimator physicsPropertyAnimator = (PhysicsPropertyAnimator) obj;
                return Float.valueOf(PhysicsPropertyAnimator.this.mCurrentPointOnPath.x);
            }

            public final void setValue(Object obj, float f) {
                PhysicsPropertyAnimator physicsPropertyAnimator = (PhysicsPropertyAnimator) obj;
                PhysicsPropertyAnimator.this.mCurrentPointOnPath.x = f;
            }
        };
        public final C18272 mCurrentPointOnPathYProperty = new FloatProperty<PhysicsPropertyAnimator>() {
            public final Object get(Object obj) {
                PhysicsPropertyAnimator physicsPropertyAnimator = (PhysicsPropertyAnimator) obj;
                return Float.valueOf(PhysicsPropertyAnimator.this.mCurrentPointOnPath.y);
            }

            public final void setValue(Object obj, float f) {
                PhysicsPropertyAnimator physicsPropertyAnimator = (PhysicsPropertyAnimator) obj;
                PhysicsPropertyAnimator.this.mCurrentPointOnPath.y = f;
            }
        };
        public float mDampingRatio = -1.0f;
        public float mDefaultStartVelocity = -3.4028235E38f;
        public HashMap mEndActionsForProperty = new HashMap();
        public HashMap mInitialPropertyValues = new HashMap();
        public ObjectAnimator mPathAnimator;
        public Runnable[] mPositionEndActions;
        public HashMap mPositionStartVelocities = new HashMap();
        public long mStartDelay = 0;
        public float mStiffness = -1.0f;
        public View mView;

        public final void animateValueForChild(DynamicAnimation.ViewProperty viewProperty, View view, float f, float f2, long j, float f3, float f4, Runnable... runnableArr) {
            long j2 = j;
            final Runnable[] runnableArr2 = runnableArr;
            if (view != null) {
                PhysicsAnimationLayout physicsAnimationLayout = PhysicsAnimationLayout.this;
                int i = PhysicsAnimationLayout.$r8$clinit;
                Objects.requireNonNull(physicsAnimationLayout);
                SpringAnimation springAnimation = (SpringAnimation) view.getTag(PhysicsAnimationLayout.getTagIdForProperty(viewProperty));
                if (springAnimation != null) {
                    if (runnableArr2 != null) {
                        springAnimation.addEndListener(new OneTimeEndListener() {
                            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                                Objects.requireNonNull(dynamicAnimation);
                                ArrayList<DynamicAnimation.OnAnimationEndListener> arrayList = dynamicAnimation.mEndListeners;
                                int indexOf = arrayList.indexOf(this);
                                if (indexOf >= 0) {
                                    arrayList.set(indexOf, (Object) null);
                                }
                                for (Runnable run : runnableArr2) {
                                    run.run();
                                }
                            }
                        });
                    }
                    SpringForce springForce = springAnimation.mSpring;
                    if (springForce != null) {
                        C1832x4b8fea75 physicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda0 = new C1832x4b8fea75(springForce, f3, f4, f2, springAnimation, f);
                        if (j2 > 0) {
                            PhysicsAnimationLayout.this.postDelayed(physicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda0, j2);
                        } else {
                            physicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda0.run();
                        }
                    }
                }
            }
        }

        public final PhysicsPropertyAnimator translationY(float f, Runnable... runnableArr) {
            this.mPathAnimator = null;
            property(DynamicAnimation.TRANSLATION_Y, f, runnableArr);
            return this;
        }

        public PhysicsPropertyAnimator(View view) {
            this.mView = view;
        }

        public final void clearAnimator() {
            this.mInitialPropertyValues.clear();
            this.mAnimatedProperties.clear();
            this.mPositionStartVelocities.clear();
            this.mDefaultStartVelocity = -3.4028235E38f;
            this.mStartDelay = 0;
            this.mStiffness = -1.0f;
            this.mDampingRatio = -1.0f;
            this.mEndActionsForProperty.clear();
            this.mPathAnimator = null;
            this.mPositionEndActions = null;
        }

        public final PhysicsPropertyAnimator property(DynamicAnimation.ViewProperty viewProperty, float f, Runnable... runnableArr) {
            this.mAnimatedProperties.put(viewProperty, Float.valueOf(f));
            this.mEndActionsForProperty.put(viewProperty, runnableArr);
            return this;
        }

        public final void start(Runnable... runnableArr) {
            boolean z;
            float f;
            PhysicsAnimationLayout physicsAnimationLayout = PhysicsAnimationLayout.this;
            PhysicsAnimationController physicsAnimationController = this.mAssociatedController;
            Objects.requireNonNull(physicsAnimationLayout);
            if (physicsAnimationLayout.mController == physicsAnimationController) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                Log.w("Bubbs.PAL", "Only the active animation controller is allowed to start animations. Use PhysicsAnimationLayout#setActiveController to set the active animation controller.");
                return;
            }
            HashSet hashSet = new HashSet(this.mAnimatedProperties.keySet());
            if (this.mPathAnimator != null) {
                hashSet.add(DynamicAnimation.TRANSLATION_X);
                hashSet.add(DynamicAnimation.TRANSLATION_Y);
            }
            if (runnableArr.length > 0) {
                DynamicAnimation.ViewProperty[] viewPropertyArr = (DynamicAnimation.ViewProperty[]) hashSet.toArray(new DynamicAnimation.ViewProperty[0]);
                PhysicsAnimationController physicsAnimationController2 = this.mAssociatedController;
                WifiEntry$$ExternalSyntheticLambda1 wifiEntry$$ExternalSyntheticLambda1 = new WifiEntry$$ExternalSyntheticLambda1(runnableArr, 6);
                Objects.requireNonNull(physicsAnimationController2);
                StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1 statusBarNotificationActivityStarter$$ExternalSyntheticLambda1 = new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1(physicsAnimationController2, viewPropertyArr, wifiEntry$$ExternalSyntheticLambda1, 1);
                for (DynamicAnimation.ViewProperty put : viewPropertyArr) {
                    physicsAnimationController2.mLayout.mEndActionForProperty.put(put, statusBarNotificationActivityStarter$$ExternalSyntheticLambda1);
                }
            }
            if (this.mPositionEndActions != null) {
                PhysicsAnimationLayout physicsAnimationLayout2 = PhysicsAnimationLayout.this;
                DynamicAnimation.C01371 r1 = DynamicAnimation.TRANSLATION_X;
                View view = this.mView;
                Objects.requireNonNull(physicsAnimationLayout2);
                SpringAnimation springAnimationFromView = PhysicsAnimationLayout.getSpringAnimationFromView(r1, view);
                PhysicsAnimationLayout physicsAnimationLayout3 = PhysicsAnimationLayout.this;
                DynamicAnimation.C01422 r5 = DynamicAnimation.TRANSLATION_Y;
                View view2 = this.mView;
                Objects.requireNonNull(physicsAnimationLayout3);
                C0152xfb0118ab emojiCompatInitializer$BackgroundDefaultLoader$$ExternalSyntheticLambda0 = new C0152xfb0118ab(this, springAnimationFromView, PhysicsAnimationLayout.getSpringAnimationFromView(r5, view2), 1);
                this.mEndActionsForProperty.put(r1, new Runnable[]{emojiCompatInitializer$BackgroundDefaultLoader$$ExternalSyntheticLambda0});
                this.mEndActionsForProperty.put(r5, new Runnable[]{emojiCompatInitializer$BackgroundDefaultLoader$$ExternalSyntheticLambda0});
            }
            if (this.mPathAnimator != null) {
                final SpringForce springForce = PhysicsAnimationLayout.this.mController.getSpringForce();
                final SpringForce springForce2 = PhysicsAnimationLayout.this.mController.getSpringForce();
                long j = this.mStartDelay;
                if (j > 0) {
                    this.mPathAnimator.setStartDelay(j);
                }
                final AccessPoint$$ExternalSyntheticLambda1 accessPoint$$ExternalSyntheticLambda1 = new AccessPoint$$ExternalSyntheticLambda1(this, 11);
                this.mPathAnimator.addUpdateListener(new ActivatableNotificationView$$ExternalSyntheticLambda0(accessPoint$$ExternalSyntheticLambda1, 1));
                this.mPathAnimator.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        accessPoint$$ExternalSyntheticLambda1.run();
                    }

                    public final void onAnimationStart(Animator animator) {
                        float f;
                        PhysicsPropertyAnimator physicsPropertyAnimator = PhysicsPropertyAnimator.this;
                        DynamicAnimation.C01371 r2 = DynamicAnimation.TRANSLATION_X;
                        View view = physicsPropertyAnimator.mView;
                        float f2 = physicsPropertyAnimator.mCurrentPointOnPath.x;
                        float f3 = physicsPropertyAnimator.mDefaultStartVelocity;
                        float f4 = physicsPropertyAnimator.mStiffness;
                        if (f4 < 0.0f) {
                            SpringForce springForce = springForce;
                            Objects.requireNonNull(springForce);
                            double d = springForce.mNaturalFreq;
                            f4 = (float) (d * d);
                        }
                        float f5 = f4;
                        float f6 = PhysicsPropertyAnimator.this.mDampingRatio;
                        if (f6 < 0.0f) {
                            SpringForce springForce2 = springForce;
                            Objects.requireNonNull(springForce2);
                            f6 = (float) springForce2.mDampingRatio;
                        }
                        physicsPropertyAnimator.animateValueForChild(r2, view, f2, f3, 0, f5, f6, new Runnable[0]);
                        PhysicsPropertyAnimator physicsPropertyAnimator2 = PhysicsPropertyAnimator.this;
                        DynamicAnimation.C01422 r14 = DynamicAnimation.TRANSLATION_Y;
                        View view2 = physicsPropertyAnimator2.mView;
                        float f7 = physicsPropertyAnimator2.mCurrentPointOnPath.y;
                        float f8 = physicsPropertyAnimator2.mDefaultStartVelocity;
                        float f9 = physicsPropertyAnimator2.mStiffness;
                        if (f9 < 0.0f) {
                            SpringForce springForce3 = springForce2;
                            Objects.requireNonNull(springForce3);
                            double d2 = springForce3.mNaturalFreq;
                            f9 = (float) (d2 * d2);
                        }
                        float f10 = f9;
                        float f11 = PhysicsPropertyAnimator.this.mDampingRatio;
                        if (f11 >= 0.0f) {
                            f = f11;
                        } else {
                            SpringForce springForce4 = springForce2;
                            Objects.requireNonNull(springForce4);
                            f = (float) springForce4.mDampingRatio;
                        }
                        physicsPropertyAnimator2.animateValueForChild(r14, view2, f7, f8, 0, f10, f, new Runnable[0]);
                    }
                });
                PhysicsAnimationLayout physicsAnimationLayout4 = PhysicsAnimationLayout.this;
                View view3 = this.mView;
                Objects.requireNonNull(physicsAnimationLayout4);
                ObjectAnimator objectAnimator = (ObjectAnimator) view3.getTag(C1777R.C1779id.target_animator_tag);
                if (objectAnimator != null) {
                    objectAnimator.cancel();
                }
                this.mView.setTag(C1777R.C1779id.target_animator_tag, this.mPathAnimator);
                this.mPathAnimator.start();
            }
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                DynamicAnimation.ViewProperty viewProperty = (DynamicAnimation.ViewProperty) it.next();
                if (this.mPathAnimator == null || (!viewProperty.equals(DynamicAnimation.TRANSLATION_X) && !viewProperty.equals(DynamicAnimation.TRANSLATION_Y))) {
                    if (this.mInitialPropertyValues.containsKey(viewProperty)) {
                        viewProperty.setValue(this.mView, ((Float) this.mInitialPropertyValues.get(viewProperty)).floatValue());
                    }
                    SpringForce springForce3 = PhysicsAnimationLayout.this.mController.getSpringForce();
                    View view4 = this.mView;
                    float floatValue = ((Float) this.mAnimatedProperties.get(viewProperty)).floatValue();
                    float floatValue2 = ((Float) this.mPositionStartVelocities.getOrDefault(viewProperty, Float.valueOf(this.mDefaultStartVelocity))).floatValue();
                    long j2 = this.mStartDelay;
                    float f2 = this.mStiffness;
                    if (f2 < 0.0f) {
                        Objects.requireNonNull(springForce3);
                        double d = springForce3.mNaturalFreq;
                        f2 = (float) (d * d);
                    }
                    float f3 = f2;
                    float f4 = this.mDampingRatio;
                    if (f4 >= 0.0f) {
                        f = f4;
                    } else {
                        Objects.requireNonNull(springForce3);
                        f = (float) springForce3.mDampingRatio;
                    }
                    animateValueForChild(viewProperty, view4, floatValue, floatValue2, j2, f3, f, (Runnable[]) this.mEndActionsForProperty.get(viewProperty));
                } else {
                    return;
                }
            }
            clearAnimator();
        }

        public final void updateValueForChild(DynamicAnimation.ViewProperty viewProperty, View view, float f) {
            SpringForce springForce;
            if (view != null) {
                PhysicsAnimationLayout physicsAnimationLayout = PhysicsAnimationLayout.this;
                int i = PhysicsAnimationLayout.$r8$clinit;
                Objects.requireNonNull(physicsAnimationLayout);
                SpringAnimation springAnimation = (SpringAnimation) view.getTag(PhysicsAnimationLayout.getTagIdForProperty(viewProperty));
                if (springAnimation != null && (springForce = springAnimation.mSpring) != null) {
                    springForce.mFinalPosition = (double) f;
                    springAnimation.start();
                }
            }
        }
    }

    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        addViewInternal(view, i, layoutParams, false);
    }

    public static String getReadablePropertyName(DynamicAnimation.ViewProperty viewProperty) {
        if (viewProperty.equals(DynamicAnimation.TRANSLATION_X)) {
            return "TRANSLATION_X";
        }
        if (viewProperty.equals(DynamicAnimation.TRANSLATION_Y)) {
            return "TRANSLATION_Y";
        }
        if (viewProperty.equals(DynamicAnimation.SCALE_X)) {
            return "SCALE_X";
        }
        if (viewProperty.equals(DynamicAnimation.SCALE_Y)) {
            return "SCALE_Y";
        }
        if (viewProperty.equals(DynamicAnimation.ALPHA)) {
            return "ALPHA";
        }
        return "Unknown animation property.";
    }

    public static int getTagIdForProperty(DynamicAnimation.ViewProperty viewProperty) {
        if (viewProperty.equals(DynamicAnimation.TRANSLATION_X)) {
            return C1777R.C1779id.translation_x_dynamicanimation_tag;
        }
        if (viewProperty.equals(DynamicAnimation.TRANSLATION_Y)) {
            return C1777R.C1779id.translation_y_dynamicanimation_tag;
        }
        if (viewProperty.equals(DynamicAnimation.SCALE_X)) {
            return C1777R.C1779id.scale_x_dynamicanimation_tag;
        }
        if (viewProperty.equals(DynamicAnimation.SCALE_Y)) {
            return C1777R.C1779id.scale_y_dynamicanimation_tag;
        }
        if (viewProperty.equals(DynamicAnimation.ALPHA)) {
            return C1777R.C1779id.alpha_dynamicanimation_tag;
        }
        return -1;
    }

    public final void cancelAllAnimationsOfProperties(DynamicAnimation.ViewProperty... viewPropertyArr) {
        if (this.mController != null) {
            for (int i = 0; i < getChildCount(); i++) {
                for (DynamicAnimation.ViewProperty springAnimationFromView : viewPropertyArr) {
                    SpringAnimation springAnimationFromView2 = getSpringAnimationFromView(springAnimationFromView, getChildAt(i));
                    if (springAnimationFromView2 != null) {
                        springAnimationFromView2.cancel();
                    }
                }
                ViewPropertyAnimator viewPropertyAnimator = (ViewPropertyAnimator) getChildAt(i).getTag(C1777R.C1779id.reorder_animator_tag);
                if (viewPropertyAnimator != null) {
                    viewPropertyAnimator.cancel();
                }
            }
        }
    }

    public final void removeView(View view) {
        if (this.mController != null) {
            int indexOfChild = indexOfChild(view);
            super.removeView(view);
            addTransientView(view, indexOfChild);
            this.mController.onChildRemoved(view, new ExecutorUtils$$ExternalSyntheticLambda1(this, view, 5));
            return;
        }
        super.removeView(view);
    }

    public final void reorderView(BadgedImageView badgedImageView, int i) {
        if (badgedImageView != null) {
            indexOfChild(badgedImageView);
            super.removeView(badgedImageView);
            if (badgedImageView.getParent() != null) {
                removeTransientView(badgedImageView);
            }
            addViewInternal(badgedImageView, i, badgedImageView.getLayoutParams(), true);
            PhysicsAnimationController physicsAnimationController = this.mController;
            if (physicsAnimationController != null) {
                physicsAnimationController.onChildReordered();
            }
        }
    }

    public final void setActiveController(PhysicsAnimationController physicsAnimationController) {
        PhysicsAnimationController physicsAnimationController2 = this.mController;
        if (physicsAnimationController2 != null) {
            cancelAllAnimationsOfProperties((DynamicAnimation.ViewProperty[]) physicsAnimationController2.getAnimatedProperties().toArray(new DynamicAnimation.ViewProperty[0]));
        }
        this.mEndActionForProperty.clear();
        this.mController = physicsAnimationController;
        Objects.requireNonNull(physicsAnimationController);
        physicsAnimationController.mLayout = this;
        physicsAnimationController.onActiveControllerForLayout(this);
        for (DynamicAnimation.ViewProperty viewProperty : this.mController.getAnimatedProperties()) {
            for (int i = 0; i < getChildCount(); i++) {
                setUpAnimationForChild(viewProperty, getChildAt(i));
            }
        }
    }

    public final void setUpAnimationForChild(DynamicAnimation.ViewProperty viewProperty, View view) {
        SpringAnimation springAnimation = new SpringAnimation(view, viewProperty);
        PhysicsAnimationLayout$$ExternalSyntheticLambda0 physicsAnimationLayout$$ExternalSyntheticLambda0 = new PhysicsAnimationLayout$$ExternalSyntheticLambda0(this, view, viewProperty);
        if (!springAnimation.mRunning) {
            if (!springAnimation.mUpdateListeners.contains(physicsAnimationLayout$$ExternalSyntheticLambda0)) {
                springAnimation.mUpdateListeners.add(physicsAnimationLayout$$ExternalSyntheticLambda0);
            }
            springAnimation.mSpring = this.mController.getSpringForce();
            springAnimation.addEndListener(new AllAnimationsForPropertyFinishedEndListener(viewProperty));
            view.setTag(getTagIdForProperty(viewProperty), springAnimation);
            return;
        }
        throw new UnsupportedOperationException("Error: Update listeners must be added beforethe animation.");
    }

    public PhysicsAnimationLayout(Context context) {
        super(context);
    }

    public static boolean arePropertiesAnimatingOnView(View view, DynamicAnimation.ViewProperty... viewPropertyArr) {
        boolean z;
        ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(C1777R.C1779id.target_animator_tag);
        for (DynamicAnimation.ViewProperty viewProperty : viewPropertyArr) {
            SpringAnimation springAnimationFromView = getSpringAnimationFromView(viewProperty, view);
            if (springAnimationFromView != null && springAnimationFromView.mRunning) {
                return true;
            }
            if (viewProperty.equals(DynamicAnimation.TRANSLATION_X) || viewProperty.equals(DynamicAnimation.TRANSLATION_Y)) {
                z = true;
            } else {
                z = false;
            }
            if (z && objectAnimator != null && objectAnimator.isRunning()) {
                return true;
            }
        }
        return false;
    }

    public static SpringAnimation getSpringAnimationFromView(DynamicAnimation.ViewProperty viewProperty, View view) {
        return (SpringAnimation) view.getTag(getTagIdForProperty(viewProperty));
    }

    public final void addViewInternal(View view, int i, ViewGroup.LayoutParams layoutParams, boolean z) {
        super.addView(view, i, layoutParams);
        PhysicsAnimationController physicsAnimationController = this.mController;
        if (physicsAnimationController != null && !z) {
            for (DynamicAnimation.ViewProperty upAnimationForChild : physicsAnimationController.getAnimatedProperties()) {
                setUpAnimationForChild(upAnimationForChild, view);
            }
            this.mController.onChildAdded(view, i);
        }
    }

    public final void cancelAnimationsOnView(View view) {
        ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(C1777R.C1779id.target_animator_tag);
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        for (DynamicAnimation.ViewProperty springAnimationFromView : this.mController.getAnimatedProperties()) {
            SpringAnimation springAnimationFromView2 = getSpringAnimationFromView(springAnimationFromView, view);
            if (springAnimationFromView2 != null) {
                springAnimationFromView2.cancel();
            }
        }
    }

    public final boolean isFirstChildXLeftOfCenter(float f) {
        if (getChildCount() <= 0 || f + ((float) (getChildAt(0).getWidth() / 2)) >= ((float) (getWidth() / 2))) {
            return false;
        }
        return true;
    }

    public final void removeViewAt(int i) {
        removeView(getChildAt(i));
    }
}
