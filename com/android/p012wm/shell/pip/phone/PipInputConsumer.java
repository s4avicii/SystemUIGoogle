package com.android.p012wm.shell.pip.phone;

import android.os.Binder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.BatchedInputEventReceiver;
import android.view.Choreographer;
import android.view.IWindowManager;
import android.view.InputChannel;
import android.view.InputEvent;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda0;

/* renamed from: com.android.wm.shell.pip.phone.PipInputConsumer */
public final class PipInputConsumer {
    public InputEventReceiver mInputEventReceiver;
    public InputListener mListener;
    public final ShellExecutor mMainExecutor;
    public final String mName = "pip_input_consumer";
    public RegistrationListener mRegistrationListener;
    public final Binder mToken = new Binder();
    public final IWindowManager mWindowManager;

    /* renamed from: com.android.wm.shell.pip.phone.PipInputConsumer$InputEventReceiver */
    public final class InputEventReceiver extends BatchedInputEventReceiver {
        public final void onInputEvent(InputEvent inputEvent) {
            try {
                InputListener inputListener = PipInputConsumer.this.mListener;
                if (inputListener != null) {
                    ((PipController$$ExternalSyntheticLambda1) inputListener).onInputEvent(inputEvent);
                }
            } finally {
                finishInputEvent(inputEvent, true);
            }
        }

        public InputEventReceiver(InputChannel inputChannel, Looper looper, Choreographer choreographer) {
            super(inputChannel, looper, choreographer);
        }
    }

    /* renamed from: com.android.wm.shell.pip.phone.PipInputConsumer$InputListener */
    public interface InputListener {
    }

    /* renamed from: com.android.wm.shell.pip.phone.PipInputConsumer$RegistrationListener */
    public interface RegistrationListener {
    }

    public final void registerInputConsumer() {
        if (this.mInputEventReceiver == null) {
            InputChannel inputChannel = new InputChannel();
            try {
                this.mWindowManager.destroyInputConsumer(this.mName, 0);
                this.mWindowManager.createInputConsumer(this.mToken, this.mName, 0, inputChannel);
            } catch (RemoteException e) {
                Log.e("PipInputConsumer", "Failed to create input consumer", e);
            }
            this.mMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda0(this, inputChannel, 5));
        }
    }

    public PipInputConsumer(IWindowManager iWindowManager, ShellExecutor shellExecutor) {
        this.mWindowManager = iWindowManager;
        this.mMainExecutor = shellExecutor;
    }
}
