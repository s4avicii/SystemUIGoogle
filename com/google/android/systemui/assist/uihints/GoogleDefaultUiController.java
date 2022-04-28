package com.google.android.systemui.assist.uihints;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.WindowManager;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.p003ui.DefaultUiController;
import com.google.android.systemui.assist.GoogleAssistLogger;
import dagger.Lazy;
import java.util.Objects;

public final class GoogleDefaultUiController extends DefaultUiController {
    public GoogleDefaultUiController(Context context, GoogleAssistLogger googleAssistLogger, WindowManager windowManager, MetricsLogger metricsLogger, Lazy<AssistManager> lazy) {
        super(context, googleAssistLogger, windowManager, metricsLogger, lazy);
        context.getResources();
        AssistantInvocationLightsView assistantInvocationLightsView = (AssistantInvocationLightsView) this.mInvocationLightsView;
        Objects.requireNonNull(assistantInvocationLightsView);
        assistantInvocationLightsView.mUseNavBarColor = true;
        assistantInvocationLightsView.mPaint.setStrokeCap(Paint.Cap.BUTT);
        assistantInvocationLightsView.attemptRegisterNavBarListener();
        AssistantInvocationLightsView assistantInvocationLightsView2 = (AssistantInvocationLightsView) LayoutInflater.from(context).inflate(C1777R.layout.invocation_lights, this.mRoot, false);
        this.mInvocationLightsView = assistantInvocationLightsView2;
        this.mRoot.addView(assistantInvocationLightsView2);
    }
}
