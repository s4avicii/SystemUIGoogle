package com.android.p012wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda15;
import com.android.p012wm.shell.bubbles.animation.PhysicsAnimationLayout;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* renamed from: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$PhysicsAnimationController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1831x49ea1e05 {
    public final /* synthetic */ PhysicsAnimationLayout.PhysicsAnimationController f$0;
    public final /* synthetic */ Set f$1;
    public final /* synthetic */ List f$2;

    public /* synthetic */ C1831x49ea1e05(PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController, HashSet hashSet, ArrayList arrayList) {
        this.f$0 = physicsAnimationController;
        this.f$1 = hashSet;
        this.f$2 = arrayList;
    }

    public final void startAll(Runnable[] runnableArr) {
        PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController = this.f$0;
        Set set = this.f$1;
        List<PhysicsAnimationLayout.PhysicsPropertyAnimator> list = this.f$2;
        Objects.requireNonNull(physicsAnimationController);
        BubbleStackView$$ExternalSyntheticLambda15 bubbleStackView$$ExternalSyntheticLambda15 = new BubbleStackView$$ExternalSyntheticLambda15(runnableArr, 7);
        if (physicsAnimationController.mLayout.getChildCount() == 0) {
            bubbleStackView$$ExternalSyntheticLambda15.run();
            return;
        }
        DynamicAnimation.ViewProperty[] viewPropertyArr = (DynamicAnimation.ViewProperty[]) set.toArray(new DynamicAnimation.ViewProperty[0]);
        StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1 statusBarNotificationActivityStarter$$ExternalSyntheticLambda1 = new StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1(physicsAnimationController, viewPropertyArr, bubbleStackView$$ExternalSyntheticLambda15, 1);
        for (DynamicAnimation.ViewProperty put : viewPropertyArr) {
            physicsAnimationController.mLayout.mEndActionForProperty.put(put, statusBarNotificationActivityStarter$$ExternalSyntheticLambda1);
        }
        for (PhysicsAnimationLayout.PhysicsPropertyAnimator start : list) {
            start.start(new Runnable[0]);
        }
    }
}
