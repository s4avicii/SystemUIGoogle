package androidx.slice.view;

import android.content.res.Resources;
import android.service.controls.actions.BooleanAction;
import android.service.controls.actions.CommandAction;
import android.service.controls.actions.ControlAction;
import android.service.controls.actions.FloatAction;
import android.service.controls.actions.ModeAction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.controls.p004ui.ChallengeDialogs$createPinDialog$1;
import com.android.systemui.controls.p004ui.ChallengeDialogs$createPinDialog$2$1;
import com.android.systemui.controls.p004ui.ChallengeDialogs$createPinDialog$2$2;
import com.android.systemui.controls.p004ui.ChallengeDialogs$createPinDialog$2$4;
import com.android.systemui.controls.p004ui.ControlViewHolder;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.Symbol;

public class R$id {
    public static final R$id INSTANCE = new R$id();
    public static final Symbol NO_DECISION = new Symbol("NO_DECISION");

    public static ChallengeDialogs$createPinDialog$1 createPinDialog(ControlViewHolder controlViewHolder, boolean z, boolean z2, Function0 function0) {
        Pair pair;
        ControlAction controlAction = controlViewHolder.lastAction;
        if (controlAction == null) {
            Log.e("ControlsUiController", "PIN Dialog attempted but no last action is set. Will not show");
            return null;
        }
        Resources resources = controlViewHolder.context.getResources();
        if (z2) {
            pair = new Pair(resources.getString(C1777R.string.controls_pin_wrong), Integer.valueOf(C1777R.string.controls_pin_instructions_retry));
        } else {
            pair = new Pair(resources.getString(C1777R.string.controls_pin_verify, new Object[]{controlViewHolder.title.getText()}), Integer.valueOf(C1777R.string.controls_pin_instructions));
        }
        int intValue = ((Number) pair.component2()).intValue();
        ChallengeDialogs$createPinDialog$1 challengeDialogs$createPinDialog$1 = new ChallengeDialogs$createPinDialog$1(controlViewHolder.context);
        challengeDialogs$createPinDialog$1.setTitle((String) pair.component1());
        challengeDialogs$createPinDialog$1.setView(LayoutInflater.from(challengeDialogs$createPinDialog$1.getContext()).inflate(C1777R.layout.controls_dialog_pin, (ViewGroup) null));
        challengeDialogs$createPinDialog$1.setButton(-1, challengeDialogs$createPinDialog$1.getContext().getText(17039370), new ChallengeDialogs$createPinDialog$2$1(controlViewHolder, controlAction));
        challengeDialogs$createPinDialog$1.setButton(-2, challengeDialogs$createPinDialog$1.getContext().getText(17039360), new ChallengeDialogs$createPinDialog$2$2(function0));
        Window window = challengeDialogs$createPinDialog$1.getWindow();
        window.setType(2020);
        window.setSoftInputMode(4);
        challengeDialogs$createPinDialog$1.setOnShowListener(new ChallengeDialogs$createPinDialog$2$4(challengeDialogs$createPinDialog$1, intValue, z));
        return challengeDialogs$createPinDialog$1;
    }

    public static final ControlAction access$addChallengeValue(ControlAction controlAction, String str) {
        String templateId = controlAction.getTemplateId();
        if (controlAction instanceof BooleanAction) {
            return new BooleanAction(templateId, ((BooleanAction) controlAction).getNewState(), str);
        }
        if (controlAction instanceof FloatAction) {
            return new FloatAction(templateId, ((FloatAction) controlAction).getNewValue(), str);
        }
        if (controlAction instanceof CommandAction) {
            return new CommandAction(templateId, str);
        }
        if (controlAction instanceof ModeAction) {
            return new ModeAction(templateId, ((ModeAction) controlAction).getNewMode(), str);
        }
        throw new IllegalStateException(Intrinsics.stringPlus("'action' is not a known type: ", controlAction));
    }
}
