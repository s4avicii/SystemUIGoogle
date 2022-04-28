package com.google.android.systemui.assist;

import android.graphics.Paint;
import android.os.Handler;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.p003ui.EdgeLight;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda19;
import com.google.android.systemui.assist.uihints.AssistantInvocationLightsView;
import com.google.android.systemui.assist.uihints.AssistantPresenceHandler;
import com.google.android.systemui.assist.uihints.GoogleDefaultUiController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AssistManagerGoogle$$ExternalSyntheticLambda1 implements AssistantPresenceHandler.AssistantPresenceChangeListener {
    public final /* synthetic */ AssistManagerGoogle f$0;

    public /* synthetic */ AssistManagerGoogle$$ExternalSyntheticLambda1(AssistManagerGoogle assistManagerGoogle) {
        this.f$0 = assistManagerGoogle;
    }

    public final void onAssistantPresenceChanged(boolean z, boolean z2) {
        AssistManagerGoogle assistManagerGoogle = this.f$0;
        Objects.requireNonNull(assistManagerGoogle);
        if (!(assistManagerGoogle.mGoogleIsAssistant == z && assistManagerGoogle.mNgaIsAssistant == z2)) {
            if (!z2) {
                if (!assistManagerGoogle.mUiController.equals(assistManagerGoogle.mDefaultUiController)) {
                    AssistManager.UiController uiController = assistManagerGoogle.mUiController;
                    assistManagerGoogle.mUiController = assistManagerGoogle.mDefaultUiController;
                    Handler handler = assistManagerGoogle.mUiHandler;
                    Objects.requireNonNull(uiController);
                    handler.post(new CarrierTextManager$$ExternalSyntheticLambda0(uiController, 10));
                }
                GoogleDefaultUiController googleDefaultUiController = assistManagerGoogle.mDefaultUiController;
                Objects.requireNonNull(googleDefaultUiController);
                AssistantInvocationLightsView assistantInvocationLightsView = (AssistantInvocationLightsView) googleDefaultUiController.mInvocationLightsView;
                Objects.requireNonNull(assistantInvocationLightsView);
                if (z) {
                    int i = assistantInvocationLightsView.mColorBlue;
                    int i2 = assistantInvocationLightsView.mColorRed;
                    int i3 = assistantInvocationLightsView.mColorYellow;
                    int i4 = assistantInvocationLightsView.mColorGreen;
                    assistantInvocationLightsView.mUseNavBarColor = false;
                    assistantInvocationLightsView.attemptUnregisterNavBarListener();
                    EdgeLight edgeLight = assistantInvocationLightsView.mAssistInvocationLights.get(0);
                    Objects.requireNonNull(edgeLight);
                    edgeLight.mColor = i;
                    EdgeLight edgeLight2 = assistantInvocationLightsView.mAssistInvocationLights.get(1);
                    Objects.requireNonNull(edgeLight2);
                    edgeLight2.mColor = i2;
                    EdgeLight edgeLight3 = assistantInvocationLightsView.mAssistInvocationLights.get(2);
                    Objects.requireNonNull(edgeLight3);
                    edgeLight3.mColor = i3;
                    EdgeLight edgeLight4 = assistantInvocationLightsView.mAssistInvocationLights.get(3);
                    Objects.requireNonNull(edgeLight4);
                    edgeLight4.mColor = i4;
                } else {
                    assistantInvocationLightsView.mUseNavBarColor = true;
                    assistantInvocationLightsView.mPaint.setStrokeCap(Paint.Cap.BUTT);
                    assistantInvocationLightsView.attemptRegisterNavBarListener();
                }
            } else if (!assistManagerGoogle.mUiController.equals(assistManagerGoogle.mNgaUiController)) {
                AssistManager.UiController uiController2 = assistManagerGoogle.mUiController;
                assistManagerGoogle.mUiController = assistManagerGoogle.mNgaUiController;
                Handler handler2 = assistManagerGoogle.mUiHandler;
                Objects.requireNonNull(uiController2);
                handler2.post(new StatusBar$$ExternalSyntheticLambda19(uiController2, 9));
            }
            assistManagerGoogle.mGoogleIsAssistant = z;
            assistManagerGoogle.mNgaIsAssistant = z2;
        }
        assistManagerGoogle.mCheckAssistantStatus = false;
    }
}
