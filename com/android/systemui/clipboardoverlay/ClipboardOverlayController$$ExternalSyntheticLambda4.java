package com.android.systemui.clipboardoverlay;

import android.content.ClipData;
import android.os.IBinder;
import android.os.LocaleList;
import android.view.textclassifier.TextLinks;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.common.DisplayChangeController;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import com.android.p012wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ClipboardOverlayController$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ ClipboardOverlayController$$ExternalSyntheticLambda4(Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    public final void run() {
        WindowContainerTransaction windowContainerTransaction = null;
        switch (this.$r8$classId) {
            case 0:
                ClipboardOverlayController clipboardOverlayController = (ClipboardOverlayController) this.f$0;
                String str = (String) this.f$2;
                Objects.requireNonNull(clipboardOverlayController);
                ClipData.Item itemAt = ((ClipData) this.f$1).getItemAt(0);
                ArrayList arrayList = new ArrayList();
                for (TextLinks.TextLink next : itemAt.getTextLinks().getLinks()) {
                    arrayList.addAll(clipboardOverlayController.mTextClassifier.classifyText(itemAt.getText(), next.getStart(), next.getEnd(), (LocaleList) null).getActions());
                }
                clipboardOverlayController.mView.post(new ClipboardOverlayController$$ExternalSyntheticLambda5(clipboardOverlayController, arrayList, str));
                return;
            default:
                Transitions.TransitionPlayerImpl transitionPlayerImpl = (Transitions.TransitionPlayerImpl) this.f$0;
                IBinder iBinder = (IBinder) this.f$1;
                TransitionRequestInfo transitionRequestInfo = (TransitionRequestInfo) this.f$2;
                int i = Transitions.TransitionPlayerImpl.$r8$clinit;
                Objects.requireNonNull(transitionPlayerImpl);
                Transitions transitions = Transitions.this;
                Objects.requireNonNull(transitions);
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -2076257741, 0, "Transition requested: %s %s", String.valueOf(iBinder), String.valueOf(transitionRequestInfo));
                }
                if (transitions.findActiveTransition(iBinder) < 0) {
                    Transitions.ActiveTransition activeTransition = new Transitions.ActiveTransition(0);
                    int size = transitions.mHandlers.size() - 1;
                    while (true) {
                        if (size >= 0) {
                            windowContainerTransaction = transitions.mHandlers.get(size).handleRequest(iBinder, transitionRequestInfo);
                            if (windowContainerTransaction != null) {
                                activeTransition.mHandler = transitions.mHandlers.get(size);
                            } else {
                                size--;
                            }
                        }
                    }
                    if (transitionRequestInfo.getDisplayChange() != null) {
                        TransitionRequestInfo.DisplayChange displayChange = transitionRequestInfo.getDisplayChange();
                        if (displayChange.getEndRotation() != displayChange.getStartRotation()) {
                            if (windowContainerTransaction == null) {
                                windowContainerTransaction = new WindowContainerTransaction();
                            }
                            DisplayController displayController = transitions.mDisplayController;
                            Objects.requireNonNull(displayController);
                            DisplayChangeController displayChangeController = displayController.mChangeController;
                            int displayId = displayChange.getDisplayId();
                            int startRotation = displayChange.getStartRotation();
                            int endRotation = displayChange.getEndRotation();
                            Objects.requireNonNull(displayChangeController);
                            Iterator<DisplayChangeController.OnDisplayChangingListener> it = displayChangeController.mRotationListener.iterator();
                            while (it.hasNext()) {
                                it.next().onRotateDisplay(displayId, startRotation, endRotation, windowContainerTransaction);
                            }
                        }
                    }
                    activeTransition.mToken = transitions.mOrganizer.startTransition(transitionRequestInfo.getType(), iBinder, windowContainerTransaction);
                    transitions.mActiveTransitions.add(activeTransition);
                    return;
                }
                throw new RuntimeException("Transition already started " + iBinder);
        }
    }
}
