package com.android.systemui.statusbar.phone.ongoingcall;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallLogger;
import java.util.Objects;

/* compiled from: OngoingCallController.kt */
public final class OngoingCallController$updateChipClickListener$1 implements View.OnClickListener {
    public final /* synthetic */ View $backgroundView;
    public final /* synthetic */ Intent $intent;
    public final /* synthetic */ OngoingCallController this$0;

    public OngoingCallController$updateChipClickListener$1(OngoingCallController ongoingCallController, Intent intent, View view) {
        this.this$0 = ongoingCallController;
        this.$intent = intent;
        this.$backgroundView = view;
    }

    public final void onClick(View view) {
        GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController;
        OngoingCallLogger ongoingCallLogger = this.this$0.logger;
        Objects.requireNonNull(ongoingCallLogger);
        ongoingCallLogger.logger.log(OngoingCallLogger.OngoingCallEvents.ONGOING_CALL_CLICKED);
        ActivityStarter activityStarter = this.this$0.activityStarter;
        Intent intent = this.$intent;
        View view2 = this.$backgroundView;
        if (!(view2.getParent() instanceof ViewGroup)) {
            Log.wtf("ActivityLaunchAnimator", "Skipping animation as view " + view2 + " is not attached to a ViewGroup", new Exception());
            ghostedViewLaunchAnimatorController = null;
        } else {
            ghostedViewLaunchAnimatorController = new GhostedViewLaunchAnimatorController(view2, (Integer) 34, 4);
        }
        activityStarter.postStartActivityDismissingKeyguard(intent, 0, ghostedViewLaunchAnimatorController);
    }
}
