package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.phone.PanelView;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: ViewGroupFadeHelper.kt */
public final class ViewGroupFadeHelper {
    public static final Function1<View, Boolean> visibilityIncluder = ViewGroupFadeHelper$Companion$visibilityIncluder$1.INSTANCE;

    public static final void fadeOutAllChildrenExcept(PanelView panelView, View view, long j, Runnable runnable) {
        Function1<View, Boolean> function1 = visibilityIncluder;
        LinkedHashSet<View> linkedHashSet = new LinkedHashSet<>();
        ViewParent parent = view.getParent();
        ViewGroup viewGroup = view;
        while (true) {
            ViewGroup viewGroup2 = (ViewGroup) parent;
            View view2 = viewGroup;
            ViewGroup viewGroup3 = viewGroup2;
            if (viewGroup3 == null) {
                break;
            }
            int i = 0;
            int childCount = viewGroup3.getChildCount();
            while (i < childCount) {
                int i2 = i + 1;
                View childAt = viewGroup3.getChildAt(i);
                if (((Boolean) ((ViewGroupFadeHelper$Companion$visibilityIncluder$1) function1).invoke(childAt)).booleanValue() && !Intrinsics.areEqual(view2, childAt)) {
                    linkedHashSet.add(childAt);
                }
                i = i2;
            }
            if (Intrinsics.areEqual(viewGroup3, panelView)) {
                break;
            }
            parent = viewGroup3.getParent();
            viewGroup = viewGroup3;
        }
        for (View view3 : linkedHashSet) {
            if (view3.getHasOverlappingRendering() && view3.getLayerType() == 0) {
                view3.setLayerType(2, (Paint) null);
                view3.setTag(C1777R.C1779id.view_group_fade_helper_hardware_layer, Boolean.TRUE);
            }
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        ofFloat.setDuration(j);
        ofFloat.setInterpolator(Interpolators.ALPHA_OUT);
        ofFloat.addUpdateListener(new C1242xbb47cb26(panelView, linkedHashSet));
        ofFloat.addListener(new C1243xbb47cb27(runnable));
        ofFloat.start();
        panelView.setTag(C1777R.C1779id.view_group_fade_helper_modified_views, linkedHashSet);
        panelView.setTag(C1777R.C1779id.view_group_fade_helper_animator, ofFloat);
    }

    public static final void reset(PanelView panelView) {
        boolean z;
        Object tag = panelView.getTag(C1777R.C1779id.view_group_fade_helper_modified_views);
        if (!(tag instanceof KMappedMarker)) {
            try {
                Set<View> set = (Set) tag;
                Animator animator = (Animator) panelView.getTag(C1777R.C1779id.view_group_fade_helper_animator);
                if (set != null && animator != null) {
                    animator.cancel();
                    Float f = (Float) panelView.getTag(C1777R.C1779id.view_group_fade_helper_previous_value_tag);
                    for (View view : set) {
                        Float f2 = (Float) view.getTag(C1777R.C1779id.view_group_fade_helper_restore_tag);
                        if (f2 != null) {
                            float alpha = view.getAlpha();
                            if (f == null || f.floatValue() != alpha) {
                                z = false;
                            } else {
                                z = true;
                            }
                            if (z) {
                                view.setAlpha(f2.floatValue());
                            }
                            if (Intrinsics.areEqual((Boolean) view.getTag(C1777R.C1779id.view_group_fade_helper_hardware_layer), Boolean.TRUE)) {
                                view.setLayerType(0, (Paint) null);
                                view.setTag(C1777R.C1779id.view_group_fade_helper_hardware_layer, (Object) null);
                            }
                            view.setTag(C1777R.C1779id.view_group_fade_helper_restore_tag, (Object) null);
                        }
                    }
                    panelView.setTag(C1777R.C1779id.view_group_fade_helper_modified_views, (Object) null);
                    panelView.setTag(C1777R.C1779id.view_group_fade_helper_previous_value_tag, (Object) null);
                    panelView.setTag(C1777R.C1779id.view_group_fade_helper_animator, (Object) null);
                }
            } catch (ClassCastException e) {
                Intrinsics.sanitizeStackTrace(e, TypeIntrinsics.class.getName());
                throw e;
            }
        } else {
            TypeIntrinsics.throwCce(tag, "kotlin.collections.MutableSet");
            throw null;
        }
    }
}
