package com.android.systemui.p006qs.tiles;

import android.view.View;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1;
import com.android.systemui.p006qs.tiles.CastTile;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.CastTile$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CastTile$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ CastTile f$0;
    public final /* synthetic */ CastTile.DialogHolder f$1;

    public /* synthetic */ CastTile$$ExternalSyntheticLambda0(CastTile castTile, CastTile.DialogHolder dialogHolder) {
        this.f$0 = castTile;
        this.f$1 = dialogHolder;
    }

    public final void onClick(View view) {
        CastTile castTile = this.f$0;
        CastTile.DialogHolder dialogHolder = this.f$1;
        Objects.requireNonNull(castTile);
        DialogLaunchAnimator dialogLaunchAnimator = castTile.mDialogLaunchAnimator;
        Objects.requireNonNull(dialogLaunchAnimator);
        DialogLaunchAnimator$createActivityLaunchController$1 createActivityLaunchController$default = DialogLaunchAnimator.createActivityLaunchController$default(dialogLaunchAnimator, view);
        if (createActivityLaunchController$default == null) {
            dialogHolder.mDialog.dismiss();
        }
        castTile.mActivityStarter.postStartActivityDismissingKeyguard(castTile.getLongClickIntent(), 0, createActivityLaunchController$default);
    }
}
