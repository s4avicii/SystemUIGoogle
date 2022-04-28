package com.android.systemui.keyguard;

import android.media.SoundPool;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ KeyguardViewMediator f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda3(KeyguardViewMediator keyguardViewMediator, int i) {
        this.f$0 = keyguardViewMediator;
        this.f$1 = i;
    }

    public final void run() {
        KeyguardViewMediator keyguardViewMediator = this.f$0;
        int i = this.f$1;
        boolean z = KeyguardViewMediator.DEBUG;
        Objects.requireNonNull(keyguardViewMediator);
        if (!keyguardViewMediator.mAudioManager.isStreamMute(keyguardViewMediator.mUiSoundsStreamType)) {
            SoundPool soundPool = keyguardViewMediator.mLockSounds;
            float f = keyguardViewMediator.mLockSoundVolume;
            int play = soundPool.play(i, f, f, 1, 0, 1.0f);
            synchronized (keyguardViewMediator) {
                keyguardViewMediator.mLockSoundStreamId = play;
            }
        }
    }
}
