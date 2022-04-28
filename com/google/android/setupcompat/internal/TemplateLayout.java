package com.google.android.setupcompat.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import androidx.annotation.Keep;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$id;
import com.google.android.setupcompat.template.Mixin;
import java.util.HashMap;

public class TemplateLayout extends FrameLayout {
    public ViewGroup container;
    public final HashMap mixins = new HashMap();
    public C21431 preDrawListener;
    public float xFraction;

    public TemplateLayout(Context context, int i, int i2) {
        super(context);
        init(i, i2, (AttributeSet) null, C1777R.attr.sucLayoutTheme);
    }

    public void onBeforeTemplateInflated(AttributeSet attributeSet, int i) {
    }

    public View onInflateTemplate(LayoutInflater layoutInflater, int i) {
        return inflateTemplate(layoutInflater, 0, i);
    }

    public void onTemplateInflated() {
    }

    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        this.container.addView(view, i, layoutParams);
    }

    public final <M extends Mixin> M getMixin(Class<M> cls) {
        return (Mixin) this.mixins.get(cls);
    }

    public final View inflateTemplate(LayoutInflater layoutInflater, int i, int i2) {
        if (i2 != 0) {
            if (i != 0) {
                layoutInflater = LayoutInflater.from(new FallbackThemeWrapper(layoutInflater.getContext(), i));
            }
            return layoutInflater.inflate(i2, this, false);
        }
        throw new IllegalArgumentException("android:layout not specified for TemplateLayout");
    }

    public final <M extends Mixin> void registerMixin(Class<M> cls, M m) {
        this.mixins.put(cls, m);
    }

    @TargetApi(11)
    @Keep
    public void setXFraction(float f) {
        this.xFraction = f;
        int width = getWidth();
        if (width != 0) {
            setTranslationX(((float) width) * f);
        } else if (this.preDrawListener == null) {
            this.preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                public final boolean onPreDraw() {
                    TemplateLayout.this.getViewTreeObserver().removeOnPreDrawListener(TemplateLayout.this.preDrawListener);
                    TemplateLayout templateLayout = TemplateLayout.this;
                    templateLayout.setXFraction(templateLayout.xFraction);
                    return true;
                }
            };
            getViewTreeObserver().addOnPreDrawListener(this.preDrawListener);
        }
    }

    public ViewGroup findContainer(int i) {
        return (ViewGroup) findViewById(i);
    }

    public <T extends View> T findManagedViewById(int i) {
        return findViewById(i);
    }

    public final void init(int i, int i2, AttributeSet attributeSet, int i3) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$id.SucTemplateLayout, i3, 0);
        if (i == 0) {
            i = obtainStyledAttributes.getResourceId(0, 0);
        }
        if (i2 == 0) {
            i2 = obtainStyledAttributes.getResourceId(1, 0);
        }
        onBeforeTemplateInflated(attributeSet, i3);
        super.addView(onInflateTemplate(LayoutInflater.from(getContext()), i), -1, generateDefaultLayoutParams());
        ViewGroup findContainer = findContainer(i2);
        this.container = findContainer;
        if (findContainer != null) {
            onTemplateInflated();
            obtainStyledAttributes.recycle();
            return;
        }
        throw new IllegalArgumentException("Container cannot be null in TemplateLayout");
    }

    public TemplateLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(0, 0, attributeSet, C1777R.attr.sucLayoutTheme);
    }

    @TargetApi(11)
    public TemplateLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(0, 0, attributeSet, i);
    }

    @TargetApi(11)
    @Keep
    public float getXFraction() {
        return this.xFraction;
    }
}
