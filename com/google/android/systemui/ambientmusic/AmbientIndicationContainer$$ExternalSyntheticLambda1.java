package com.google.android.systemui.ambientmusic;

import android.widget.ImageView;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.AutoReinflateContainer;
import com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda4;
import com.android.systemui.wallet.p011ui.WalletView$$ExternalSyntheticLambda0;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AmbientIndicationContainer$$ExternalSyntheticLambda1 implements AutoReinflateContainer.InflateListener {
    public final /* synthetic */ AmbientIndicationContainer f$0;

    public /* synthetic */ AmbientIndicationContainer$$ExternalSyntheticLambda1(AmbientIndicationContainer ambientIndicationContainer) {
        this.f$0 = ambientIndicationContainer;
    }

    public final void onInflated() {
        AmbientIndicationContainer ambientIndicationContainer = this.f$0;
        int i = AmbientIndicationContainer.$r8$clinit;
        Objects.requireNonNull(ambientIndicationContainer);
        ambientIndicationContainer.mTextView = (TextView) ambientIndicationContainer.findViewById(C1777R.C1779id.ambient_indication_text);
        ambientIndicationContainer.mIconView = (ImageView) ambientIndicationContainer.findViewById(C1777R.C1779id.ambient_indication_icon);
        ambientIndicationContainer.mAmbientMusicAnimation = null;
        ambientIndicationContainer.mAmbientMusicNoteIcon = null;
        ambientIndicationContainer.mReverseChargingAnimation = null;
        ambientIndicationContainer.mTextColor = ambientIndicationContainer.mTextView.getCurrentTextColor();
        ambientIndicationContainer.mAmbientIndicationIconSize = ambientIndicationContainer.getResources().getDimensionPixelSize(C1777R.dimen.ambient_indication_icon_size);
        ambientIndicationContainer.mAmbientMusicNoteIconIconSize = ambientIndicationContainer.getResources().getDimensionPixelSize(C1777R.dimen.ambient_indication_note_icon_size);
        ambientIndicationContainer.getResources().getDimensionPixelSize(C1777R.dimen.default_burn_in_prevention_offset);
        ambientIndicationContainer.mTextView.setEnabled(!ambientIndicationContainer.mDozing);
        ambientIndicationContainer.updateColors();
        ambientIndicationContainer.updatePill();
        ambientIndicationContainer.mTextView.setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda4(ambientIndicationContainer, 1));
        ambientIndicationContainer.mIconView.setOnClickListener(new WalletView$$ExternalSyntheticLambda0(ambientIndicationContainer, 3));
    }
}
