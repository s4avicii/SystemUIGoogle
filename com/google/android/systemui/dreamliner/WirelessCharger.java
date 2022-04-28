package com.google.android.systemui.dreamliner;

import com.android.systemui.volume.CaptionsToggleImageButton$$ExternalSyntheticLambda0;

public abstract class WirelessCharger {

    public interface AlignInfoListener {
    }

    public interface ChallengeCallback {
    }

    public interface GetFanInformationCallback {
    }

    public interface GetFanSimpleInformationCallback {
    }

    public interface GetFeaturesCallback {
    }

    public interface GetInformationCallback {
    }

    public interface GetWpcAuthCertificateCallback {
    }

    public interface GetWpcAuthChallengeResponseCallback {
    }

    public interface GetWpcAuthDigestsCallback {
    }

    public interface IsDockPresentCallback {
    }

    public interface KeyExchangeCallback {
    }

    public interface SetFanCallback {
    }

    public abstract void asyncIsDockPresent(IsDockPresentCallback isDockPresentCallback);

    public abstract void challenge(byte b, byte[] bArr, ChallengeCallback challengeCallback);

    public abstract void getFanInformation(byte b, GetFanInformationCallback getFanInformationCallback);

    public abstract int getFanLevel();

    public abstract void getFanSimpleInformation(byte b, GetFanSimpleInformationCallback getFanSimpleInformationCallback);

    public abstract void getFeatures(long j, GetFeaturesCallback getFeaturesCallback);

    public abstract void getInformation(GetInformationCallback getInformationCallback);

    public abstract void getWpcAuthCertificate(byte b, short s, short s2, GetWpcAuthCertificateCallback getWpcAuthCertificateCallback);

    public abstract void getWpcAuthChallengeResponse(byte b, byte[] bArr, GetWpcAuthChallengeResponseCallback getWpcAuthChallengeResponseCallback);

    public abstract void getWpcAuthDigests(byte b, GetWpcAuthDigestsCallback getWpcAuthDigestsCallback);

    public abstract void keyExchange(byte[] bArr, KeyExchangeCallback keyExchangeCallback);

    public abstract void registerAlignInfo(AlignInfoListener alignInfoListener);

    public abstract void setFan(byte b, byte b2, int i, SetFanCallback setFanCallback);

    public abstract void setFeatures(long j, long j2, CaptionsToggleImageButton$$ExternalSyntheticLambda0 captionsToggleImageButton$$ExternalSyntheticLambda0);
}
