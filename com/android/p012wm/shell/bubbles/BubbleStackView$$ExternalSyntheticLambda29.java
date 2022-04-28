package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelperController;
import java.util.Objects;
import java.util.function.Function;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda29 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda29 implements Function {
    public static final /* synthetic */ BubbleStackView$$ExternalSyntheticLambda29 INSTANCE = new BubbleStackView$$ExternalSyntheticLambda29(0);
    public static final /* synthetic */ BubbleStackView$$ExternalSyntheticLambda29 INSTANCE$1 = new BubbleStackView$$ExternalSyntheticLambda29(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda29(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Bubble bubble = (Bubble) obj;
                int i = BubbleStackView.FLYOUT_HIDE_AFTER;
                Objects.requireNonNull(bubble);
                return bubble.mIconView;
            default:
                TaskSurfaceHelperController taskSurfaceHelperController = (TaskSurfaceHelperController) obj;
                Objects.requireNonNull(taskSurfaceHelperController);
                return taskSurfaceHelperController.mImpl;
        }
    }
}
