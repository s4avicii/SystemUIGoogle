package com.google.android.systemui.communal.dreams;

import android.content.Intent;
import android.view.View;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda0;
import com.android.systemui.dreams.DreamOverlayService;
import com.android.systemui.dreams.complication.ComplicationViewModel;
import com.android.systemui.plugins.ActivityStarter;
import java.util.Objects;

/* renamed from: com.google.android.systemui.communal.dreams.SetupDreamComplication$SetupDreamViewHolder$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C2207x5426cca6 implements View.OnClickListener {
    public final /* synthetic */ ComplicationViewModel f$0;
    public final /* synthetic */ ActivityStarter f$1;
    public final /* synthetic */ Intent f$2;

    public /* synthetic */ C2207x5426cca6(ComplicationViewModel complicationViewModel, ActivityStarter activityStarter, Intent intent) {
        this.f$0 = complicationViewModel;
        this.f$1 = activityStarter;
        this.f$2 = intent;
    }

    public final void onClick(View view) {
        ComplicationViewModel complicationViewModel = this.f$0;
        ActivityStarter activityStarter = this.f$1;
        Intent intent = this.f$2;
        Objects.requireNonNull(complicationViewModel);
        DreamOverlayService.C07851 r5 = (DreamOverlayService.C07851) complicationViewModel.mHost;
        Objects.requireNonNull(r5);
        DreamOverlayService dreamOverlayService = DreamOverlayService.this;
        dreamOverlayService.mExecutor.execute(new LockIconViewController$$ExternalSyntheticLambda0(dreamOverlayService, 1));
        activityStarter.startActivity(intent, true);
    }
}
