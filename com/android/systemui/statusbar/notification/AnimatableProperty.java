package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;
import android.util.Property;
import android.view.View;
import com.android.p012wm.shell.C1777R;

public abstract class AnimatableProperty {
    public static final C12227 SCALE_X;
    public static final C12227 SCALE_Y;
    public static final C12227 TRANSLATION_X;

    /* renamed from: Y */
    public static final C12227 f80Y;

    public abstract int getAnimationEndTag();

    public abstract int getAnimationStartTag();

    public abstract int getAnimatorTag();

    public abstract Property getProperty();

    static {
        Property property = View.X;
        final Property property2 = View.Y;
        f80Y = new AnimatableProperty(C1777R.C1779id.y_animator_tag_start_value, C1777R.C1779id.y_animator_tag_end_value, C1777R.C1779id.y_animator_tag) {
            public final int getAnimationEndTag() {
                return C1777R.C1779id.scale_y_animator_end_value_tag;
            }

            public final int getAnimationStartTag() {
                return C1777R.C1779id.scale_y_animator_start_value_tag;
            }

            public final int getAnimatorTag() {
                return C1777R.C1779id.scale_y_animator_tag;
            }

            public final Property getProperty() {
                return r0;
            }
        };
        final Property property3 = View.TRANSLATION_X;
        TRANSLATION_X = new AnimatableProperty(C1777R.C1779id.x_animator_tag_start_value, C1777R.C1779id.x_animator_tag_end_value, C1777R.C1779id.x_animator_tag) {
            public final int getAnimationEndTag() {
                return C1777R.C1779id.scale_y_animator_end_value_tag;
            }

            public final int getAnimationStartTag() {
                return C1777R.C1779id.scale_y_animator_start_value_tag;
            }

            public final int getAnimatorTag() {
                return C1777R.C1779id.scale_y_animator_tag;
            }

            public final Property getProperty() {
                return r0;
            }
        };
        final Property property4 = View.SCALE_X;
        SCALE_X = new AnimatableProperty(C1777R.C1779id.scale_x_animator_start_value_tag, C1777R.C1779id.scale_x_animator_end_value_tag, C1777R.C1779id.scale_x_animator_tag) {
            public final int getAnimationEndTag() {
                return C1777R.C1779id.scale_y_animator_end_value_tag;
            }

            public final int getAnimationStartTag() {
                return C1777R.C1779id.scale_y_animator_start_value_tag;
            }

            public final int getAnimatorTag() {
                return C1777R.C1779id.scale_y_animator_tag;
            }

            public final Property getProperty() {
                return r0;
            }
        };
        final Property property5 = View.SCALE_Y;
        SCALE_Y = new AnimatableProperty(C1777R.C1779id.scale_y_animator_start_value_tag, C1777R.C1779id.scale_y_animator_end_value_tag, C1777R.C1779id.scale_y_animator_tag) {
            public final int getAnimationEndTag() {
                return C1777R.C1779id.scale_y_animator_end_value_tag;
            }

            public final int getAnimationStartTag() {
                return C1777R.C1779id.scale_y_animator_start_value_tag;
            }

            public final int getAnimatorTag() {
                return C1777R.C1779id.scale_y_animator_tag;
            }

            public final Property getProperty() {
                return property5;
            }
        };
        new FloatProperty<View>() {
            public final Object get(Object obj) {
                View view = (View) obj;
                Object tag = view.getTag(C1777R.C1779id.absolute_x_current_value);
                if (tag instanceof Float) {
                    return (Float) tag;
                }
                return (Float) View.X.get(view);
            }

            public final void setValue(Object obj, float f) {
                View view = (View) obj;
                view.setTag(C1777R.C1779id.absolute_x_current_value, Float.valueOf(f));
                View.X.set(view, Float.valueOf(f));
            }
        };
        new FloatProperty<View>() {
            public final Object get(Object obj) {
                View view = (View) obj;
                Object tag = view.getTag(C1777R.C1779id.absolute_y_current_value);
                if (tag instanceof Float) {
                    return (Float) tag;
                }
                return (Float) View.Y.get(view);
            }

            public final void setValue(Object obj, float f) {
                View view = (View) obj;
                view.setTag(C1777R.C1779id.absolute_y_current_value, Float.valueOf(f));
                View.Y.set(view, Float.valueOf(f));
            }
        };
        new FloatProperty<View>() {
            public final Object get(Object obj) {
                View view = (View) obj;
                Object tag = view.getTag(C1777R.C1779id.view_width_current_value);
                if (tag instanceof Float) {
                    return (Float) tag;
                }
                return Float.valueOf((float) view.getWidth());
            }

            public final void setValue(Object obj, float f) {
                View view = (View) obj;
                view.setTag(C1777R.C1779id.view_width_current_value, Float.valueOf(f));
                view.setRight((int) (((float) view.getLeft()) + f));
            }
        };
        new FloatProperty<View>() {
            public final Object get(Object obj) {
                View view = (View) obj;
                Object tag = view.getTag(C1777R.C1779id.view_height_current_value);
                if (tag instanceof Float) {
                    return (Float) tag;
                }
                return Float.valueOf((float) view.getHeight());
            }

            public final void setValue(Object obj, float f) {
                View view = (View) obj;
                view.setTag(C1777R.C1779id.view_height_current_value, Float.valueOf(f));
                view.setBottom((int) (((float) view.getTop()) + f));
            }
        };
    }
}
