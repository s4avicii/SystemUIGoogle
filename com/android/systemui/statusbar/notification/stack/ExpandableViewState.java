package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.Property;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.StackStateAnimator;
import java.util.Objects;

public class ExpandableViewState extends ViewState {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean belowSpeedBump;
    public int clipTopAmount;
    public boolean headsUpIsVisible;
    public int height;
    public boolean hideSensitive;
    public boolean inShelf;
    public int location;
    public int notGoneIndex;

    public void animateTo(View view, AnimationProperties animationProperties) {
        View view2 = view;
        AnimationProperties animationProperties2 = animationProperties;
        super.animateTo(view, animationProperties);
        if (view2 instanceof ExpandableView) {
            final ExpandableView expandableView = (ExpandableView) view2;
            StackStateAnimator.C13881 r4 = (StackStateAnimator.C13881) animationProperties2;
            Objects.requireNonNull(r4);
            AnimationFilter animationFilter = StackStateAnimator.this.mAnimationFilter;
            if (this.height != expandableView.mActualHeight) {
                Integer num = (Integer) expandableView.getTag(C1777R.C1779id.height_animator_start_value_tag);
                Integer num2 = (Integer) expandableView.getTag(C1777R.C1779id.height_animator_end_value_tag);
                int i = this.height;
                if (num2 == null || num2.intValue() != i) {
                    ValueAnimator valueAnimator = (ValueAnimator) expandableView.getTag(C1777R.C1779id.height_animator_tag);
                    if (StackStateAnimator.this.mAnimationFilter.animateHeight) {
                        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{expandableView.mActualHeight, i});
                        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                ExpandableView.this.setActualHeight(((Integer) valueAnimator.getAnimatedValue()).intValue(), false);
                            }
                        });
                        ofInt.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                        ofInt.setDuration(ViewState.cancelAnimatorAndGetNewDuration(animationProperties2.duration, valueAnimator));
                        if (animationProperties2.delay > 0 && (valueAnimator == null || valueAnimator.getAnimatedFraction() == 0.0f)) {
                            ofInt.setStartDelay(animationProperties2.delay);
                        }
                        AnimatorListenerAdapter animationFinishListener = animationProperties2.getAnimationFinishListener((Property) null);
                        if (animationFinishListener != null) {
                            ofInt.addListener(animationFinishListener);
                        }
                        ofInt.addListener(new AnimatorListenerAdapter() {
                            public boolean mWasCancelled;

                            public final void onAnimationCancel(Animator animator) {
                                this.mWasCancelled = true;
                            }

                            public final void onAnimationStart(Animator animator) {
                                this.mWasCancelled = false;
                            }

                            public final void onAnimationEnd(Animator animator) {
                                ExpandableView expandableView = ExpandableView.this;
                                int i = ExpandableViewState.$r8$clinit;
                                expandableView.setTag(C1777R.C1779id.height_animator_tag, (Object) null);
                                ExpandableView.this.setTag(C1777R.C1779id.height_animator_start_value_tag, (Object) null);
                                ExpandableView.this.setTag(C1777R.C1779id.height_animator_end_value_tag, (Object) null);
                                ExpandableView.this.setActualHeightAnimating(false);
                                if (!this.mWasCancelled) {
                                    ExpandableView expandableView2 = ExpandableView.this;
                                    if (expandableView2 instanceof ExpandableNotificationRow) {
                                        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView2;
                                        Objects.requireNonNull(expandableNotificationRow);
                                        expandableNotificationRow.mGroupExpansionChanging = false;
                                    }
                                }
                            }
                        });
                        ViewState.startAnimator(ofInt, animationFinishListener);
                        expandableView.setTag(C1777R.C1779id.height_animator_tag, ofInt);
                        expandableView.setTag(C1777R.C1779id.height_animator_start_value_tag, Integer.valueOf(expandableView.mActualHeight));
                        expandableView.setTag(C1777R.C1779id.height_animator_end_value_tag, Integer.valueOf(i));
                        expandableView.setActualHeightAnimating(true);
                    } else if (valueAnimator != null) {
                        PropertyValuesHolder[] values = valueAnimator.getValues();
                        int intValue = num.intValue() + (i - num2.intValue());
                        values[0].setIntValues(new int[]{intValue, i});
                        expandableView.setTag(C1777R.C1779id.height_animator_start_value_tag, Integer.valueOf(intValue));
                        expandableView.setTag(C1777R.C1779id.height_animator_end_value_tag, Integer.valueOf(i));
                        valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
                    } else {
                        expandableView.setActualHeight(i, false);
                    }
                }
            } else {
                ViewState.abortAnimation(view2, C1777R.C1779id.height_animator_tag);
            }
            if (this.clipTopAmount != expandableView.mClipTopAmount) {
                Integer num3 = (Integer) expandableView.getTag(C1777R.C1779id.top_inset_animator_start_value_tag);
                Integer num4 = (Integer) expandableView.getTag(C1777R.C1779id.top_inset_animator_end_value_tag);
                int i2 = this.clipTopAmount;
                if (num4 == null || num4.intValue() != i2) {
                    ValueAnimator valueAnimator2 = (ValueAnimator) expandableView.getTag(C1777R.C1779id.top_inset_animator_tag);
                    if (StackStateAnimator.this.mAnimationFilter.animateTopInset) {
                        ValueAnimator ofInt2 = ValueAnimator.ofInt(new int[]{expandableView.mClipTopAmount, i2});
                        ofInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                ExpandableView.this.setClipTopAmount(((Integer) valueAnimator.getAnimatedValue()).intValue());
                            }
                        });
                        ofInt2.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
                        ofInt2.setDuration(ViewState.cancelAnimatorAndGetNewDuration(animationProperties2.duration, valueAnimator2));
                        if (animationProperties2.delay > 0 && (valueAnimator2 == null || valueAnimator2.getAnimatedFraction() == 0.0f)) {
                            ofInt2.setStartDelay(animationProperties2.delay);
                        }
                        AnimatorListenerAdapter animationFinishListener2 = animationProperties2.getAnimationFinishListener((Property) null);
                        if (animationFinishListener2 != null) {
                            ofInt2.addListener(animationFinishListener2);
                        }
                        ofInt2.addListener(new AnimatorListenerAdapter() {
                            public final void onAnimationEnd(Animator animator) {
                                ExpandableView expandableView = ExpandableView.this;
                                int i = ExpandableViewState.$r8$clinit;
                                expandableView.setTag(C1777R.C1779id.top_inset_animator_tag, (Object) null);
                                ExpandableView.this.setTag(C1777R.C1779id.top_inset_animator_start_value_tag, (Object) null);
                                ExpandableView.this.setTag(C1777R.C1779id.top_inset_animator_end_value_tag, (Object) null);
                            }
                        });
                        ViewState.startAnimator(ofInt2, animationFinishListener2);
                        expandableView.setTag(C1777R.C1779id.top_inset_animator_tag, ofInt2);
                        expandableView.setTag(C1777R.C1779id.top_inset_animator_start_value_tag, Integer.valueOf(expandableView.mClipTopAmount));
                        expandableView.setTag(C1777R.C1779id.top_inset_animator_end_value_tag, Integer.valueOf(i2));
                    } else if (valueAnimator2 != null) {
                        PropertyValuesHolder[] values2 = valueAnimator2.getValues();
                        int intValue2 = num3.intValue() + (i2 - num4.intValue());
                        values2[0].setIntValues(new int[]{intValue2, i2});
                        expandableView.setTag(C1777R.C1779id.top_inset_animator_start_value_tag, Integer.valueOf(intValue2));
                        expandableView.setTag(C1777R.C1779id.top_inset_animator_end_value_tag, Integer.valueOf(i2));
                        valueAnimator2.setCurrentPlayTime(valueAnimator2.getCurrentPlayTime());
                    } else {
                        expandableView.setClipTopAmount(i2);
                    }
                }
            } else {
                ViewState.abortAnimation(view2, C1777R.C1779id.top_inset_animator_tag);
            }
            boolean z = animationFilter.animateDimmed;
            expandableView.setBelowSpeedBump(this.belowSpeedBump);
            expandableView.setHideSensitive(this.hideSensitive, animationFilter.animateHideSensitive, animationProperties2.delay, animationProperties2.duration);
            if (animationProperties2.wasAdded(view2) && !this.hidden) {
                expandableView.performAddAnimation(animationProperties2.delay, animationProperties2.duration);
            }
            if (!expandableView.mInShelf && this.inShelf) {
                expandableView.mTransformingInShelf = true;
            }
            expandableView.mInShelf = this.inShelf;
            if (this.headsUpIsVisible) {
                expandableView.setHeadsUpIsVisible();
            }
        }
    }

    public static int getFinalActualHeight(ExpandableView expandableView) {
        if (((ValueAnimator) expandableView.getTag(C1777R.C1779id.height_animator_tag)) == null) {
            return expandableView.mActualHeight;
        }
        return ((Integer) expandableView.getTag(C1777R.C1779id.height_animator_end_value_tag)).intValue();
    }

    public void applyToView(View view) {
        super.applyToView(view);
        if (view instanceof ExpandableView) {
            ExpandableView expandableView = (ExpandableView) view;
            Objects.requireNonNull(expandableView);
            int i = expandableView.mActualHeight;
            int i2 = this.height;
            if (i != i2) {
                expandableView.setActualHeight(i2, false);
            }
            expandableView.setHideSensitive(this.hideSensitive, false, 0, 0);
            expandableView.setBelowSpeedBump(this.belowSpeedBump);
            int i3 = this.clipTopAmount;
            if (((float) expandableView.mClipTopAmount) != ((float) i3)) {
                expandableView.setClipTopAmount(i3);
            }
            expandableView.mTransformingInShelf = false;
            expandableView.mInShelf = this.inShelf;
            if (this.headsUpIsVisible) {
                expandableView.setHeadsUpIsVisible();
            }
        }
    }

    public final void cancelAnimations(View view) {
        super.cancelAnimations(view);
        Animator animator = (Animator) view.getTag(C1777R.C1779id.height_animator_tag);
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = (Animator) view.getTag(C1777R.C1779id.top_inset_animator_tag);
        if (animator2 != null) {
            animator2.cancel();
        }
    }

    public void copyFrom(ExpandableViewState expandableViewState) {
        super.copyFrom(expandableViewState);
        if (expandableViewState instanceof ExpandableViewState) {
            this.height = expandableViewState.height;
            this.hideSensitive = expandableViewState.hideSensitive;
            this.belowSpeedBump = expandableViewState.belowSpeedBump;
            this.clipTopAmount = expandableViewState.clipTopAmount;
            this.notGoneIndex = expandableViewState.notGoneIndex;
            this.location = expandableViewState.location;
            this.headsUpIsVisible = expandableViewState.headsUpIsVisible;
        }
    }
}
