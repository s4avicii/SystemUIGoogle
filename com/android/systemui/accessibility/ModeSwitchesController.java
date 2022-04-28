package com.android.systemui.accessibility;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.systemui.accessibility.MagnificationModeSwitch;

public final class ModeSwitchesController implements MagnificationModeSwitch.SwitchListener {
    public MagnificationModeSwitch.SwitchListener mSwitchListenerDelegate;
    public final DisplayIdIndexSupplier<MagnificationModeSwitch> mSwitchSupplier;

    public static class SwitchSupplier extends DisplayIdIndexSupplier<MagnificationModeSwitch> {
        public final Context mContext;
        public final MagnificationModeSwitch.SwitchListener mSwitchListener;

        public final Object createInstance(Display display) {
            Context createWindowContext = this.mContext.createWindowContext(display, 2039, (Bundle) null);
            MagnificationModeSwitch.SwitchListener switchListener = this.mSwitchListener;
            ImageView imageView = new ImageView(createWindowContext);
            imageView.setClickable(true);
            imageView.setFocusable(true);
            imageView.setAlpha(0.0f);
            return new MagnificationModeSwitch(createWindowContext, imageView, new SfVsyncFrameCallbackProvider(), switchListener);
        }

        public SwitchSupplier(Context context, DisplayManager displayManager, ModeSwitchesController$$ExternalSyntheticLambda0 modeSwitchesController$$ExternalSyntheticLambda0) {
            super(displayManager);
            this.mContext = context;
            this.mSwitchListener = modeSwitchesController$$ExternalSyntheticLambda0;
        }
    }

    public ModeSwitchesController(Context context) {
        this.mSwitchSupplier = new SwitchSupplier(context, (DisplayManager) context.getSystemService(DisplayManager.class), new ModeSwitchesController$$ExternalSyntheticLambda0(this));
    }

    public final void onSwitch(int i, int i2) {
        MagnificationModeSwitch.SwitchListener switchListener = this.mSwitchListenerDelegate;
        if (switchListener != null) {
            switchListener.onSwitch(i, i2);
        }
    }

    @VisibleForTesting
    public ModeSwitchesController(DisplayIdIndexSupplier<MagnificationModeSwitch> displayIdIndexSupplier) {
        this.mSwitchSupplier = displayIdIndexSupplier;
    }
}
