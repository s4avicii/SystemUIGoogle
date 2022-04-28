package com.android.systemui;

import android.service.dreams.DreamService;
import android.util.AttributeSet;
import com.android.systemui.DessertCaseView;
import java.util.Objects;

public class DessertCaseDream extends DreamService {
    public DessertCaseView.RescalingContainer mContainer;
    public DessertCaseView mView;

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        this.mView = new DessertCaseView(this, (AttributeSet) null);
        DessertCaseView.RescalingContainer rescalingContainer = new DessertCaseView.RescalingContainer(this);
        this.mContainer = rescalingContainer;
        DessertCaseView dessertCaseView = this.mView;
        rescalingContainer.addView(dessertCaseView);
        rescalingContainer.mView = dessertCaseView;
        setContentView(this.mContainer);
    }

    public final void onDreamingStarted() {
        super.onDreamingStarted();
        this.mView.postDelayed(new Runnable() {
            public final void run() {
                DessertCaseDream.this.mView.start();
            }
        }, 1000);
    }

    public final void onDreamingStopped() {
        super.onDreamingStopped();
        DessertCaseView dessertCaseView = this.mView;
        Objects.requireNonNull(dessertCaseView);
        dessertCaseView.mStarted = false;
        dessertCaseView.mHandler.removeCallbacks(dessertCaseView.mJuggle);
    }
}
