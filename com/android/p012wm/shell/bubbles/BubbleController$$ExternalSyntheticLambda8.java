package com.android.p012wm.shell.bubbles;

import android.graphics.Rect;
import android.view.View;
import com.android.p012wm.shell.back.BackAnimation;
import com.android.p012wm.shell.bubbles.BubbleController;
import com.android.p012wm.shell.onehanded.OneHandedController;
import com.android.p012wm.shell.onehanded.OneHandedDisplayAreaOrganizer;
import com.android.p012wm.shell.onehanded.OneHandedTransitionCallback;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.ComplicationHostViewController;
import com.android.systemui.dreams.complication.ComplicationId;
import com.android.systemui.dreams.complication.ComplicationLayoutEngine;
import com.android.systemui.dreams.complication.ComplicationLayoutParams;
import com.android.systemui.dreams.complication.ComplicationViewModel;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.util.service.ObservableServiceConnection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda8 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda8(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BubbleController bubbleController = (BubbleController) this.f$0;
                OneHandedController oneHandedController = (OneHandedController) obj;
                Objects.requireNonNull(bubbleController);
                BubbleController.C17911 r0 = new OneHandedTransitionCallback() {
                    public final void onStartFinished(Rect rect) {
                        BubbleStackView bubbleStackView = BubbleController.this.mStackView;
                        if (bubbleStackView != null) {
                            bubbleStackView.mDismissView.setPadding(0, 0, 0, rect.top);
                        }
                    }

                    public final void onStopFinished(Rect rect) {
                        BubbleStackView bubbleStackView = BubbleController.this.mStackView;
                        if (bubbleStackView != null) {
                            bubbleStackView.mDismissView.setPadding(0, 0, 0, rect.top);
                        }
                    }
                };
                Objects.requireNonNull(oneHandedController);
                OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = oneHandedController.mDisplayAreaOrganizer;
                Objects.requireNonNull(oneHandedDisplayAreaOrganizer);
                oneHandedDisplayAreaOrganizer.mTransitionCallbacks.add(r0);
                return;
            case 1:
                ComplicationHostViewController complicationHostViewController = (ComplicationHostViewController) this.f$0;
                ComplicationViewModel complicationViewModel = (ComplicationViewModel) obj;
                Objects.requireNonNull(complicationHostViewController);
                Objects.requireNonNull(complicationViewModel);
                ComplicationId complicationId = complicationViewModel.mId;
                Complication.ViewHolder createView = complicationViewModel.mComplication.createView(complicationViewModel);
                complicationHostViewController.mComplications.put(complicationId, createView);
                ComplicationLayoutEngine complicationLayoutEngine = complicationHostViewController.mLayoutEngine;
                View view = createView.getView();
                ComplicationLayoutParams layoutParams = createView.getLayoutParams();
                Objects.requireNonNull(complicationLayoutEngine);
                if (complicationLayoutEngine.mEntries.containsKey(complicationId)) {
                    complicationLayoutEngine.removeComplication(complicationId);
                }
                int i = complicationLayoutEngine.mMargin;
                Objects.requireNonNull(layoutParams);
                int i2 = layoutParams.mPosition;
                if (!complicationLayoutEngine.mPositions.containsKey(Integer.valueOf(i2))) {
                    complicationLayoutEngine.mPositions.put(Integer.valueOf(i2), new ComplicationLayoutEngine.PositionGroup());
                }
                ComplicationLayoutEngine.PositionGroup positionGroup = complicationLayoutEngine.mPositions.get(Integer.valueOf(i2));
                Objects.requireNonNull(positionGroup);
                int i3 = layoutParams.mDirection;
                if (!positionGroup.mDirectionGroups.containsKey(Integer.valueOf(i3))) {
                    positionGroup.mDirectionGroups.put(Integer.valueOf(i3), new ComplicationLayoutEngine.DirectionGroup(positionGroup));
                }
                ComplicationLayoutEngine.DirectionGroup directionGroup = positionGroup.mDirectionGroups.get(Integer.valueOf(i3));
                Objects.requireNonNull(directionGroup);
                ComplicationLayoutEngine.ViewEntry viewEntry = new ComplicationLayoutEngine.ViewEntry(view, layoutParams, 1, directionGroup, i);
                directionGroup.mViews.add(viewEntry);
                Collections.sort(directionGroup.mViews);
                Collections.reverse(directionGroup.mViews);
                ((ComplicationLayoutEngine.PositionGroup) directionGroup.mParent).onEntriesChanged();
                complicationLayoutEngine.mEntries.put(complicationId, viewEntry);
                complicationLayoutEngine.mLayout.addView(view);
                return;
            case 2:
                NavigationBarView navigationBarView = (NavigationBarView) this.f$0;
                Objects.requireNonNull(navigationBarView);
                EdgeBackGestureHandler edgeBackGestureHandler = navigationBarView.mEdgeBackGestureHandler;
                Objects.requireNonNull(edgeBackGestureHandler);
                edgeBackGestureHandler.mBackAnimation = (BackAnimation) obj;
                return;
            default:
                ObservableServiceConnection observableServiceConnection = (ObservableServiceConnection) this.f$0;
                boolean z = ObservableServiceConnection.DEBUG;
                Objects.requireNonNull(observableServiceConnection);
                ((ObservableServiceConnection.Callback) obj).onConnected(observableServiceConnection.mProxy);
                return;
        }
    }
}
