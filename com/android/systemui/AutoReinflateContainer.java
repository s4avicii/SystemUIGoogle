package com.android.systemui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;

public class AutoReinflateContainer extends FrameLayout implements ConfigurationController.ConfigurationListener {
    public final ArrayList mInflateListeners = new ArrayList();
    public final int mLayout;

    public interface InflateListener {
        void onInflated();
    }

    public AutoReinflateContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AutoReinflateContainer);
        if (obtainStyledAttributes.hasValue(0)) {
            this.mLayout = obtainStyledAttributes.getResourceId(0, 0);
            obtainStyledAttributes.recycle();
            inflateLayout();
            return;
        }
        throw new IllegalArgumentException("AutoReinflateContainer must contain a layout");
    }

    public final void inflateLayout() {
        removeAllViews();
        inflateLayoutImpl();
        int size = this.mInflateListeners.size();
        for (int i = 0; i < size; i++) {
            getChildAt(0);
            ((InflateListener) this.mInflateListeners.get(i)).onInflated();
        }
    }

    public void inflateLayoutImpl() {
        LayoutInflater.from(getContext()).inflate(this.mLayout, this);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).removeCallback(this);
    }

    public final void onDensityOrFontScaleChanged() {
        inflateLayout();
    }

    public final void onLocaleListChanged() {
        inflateLayout();
    }

    public final void onThemeChanged() {
        inflateLayout();
    }

    public final void onUiModeChanged() {
        inflateLayout();
    }
}
