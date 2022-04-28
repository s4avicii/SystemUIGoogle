package kotlinx.coroutines.channels;

import kotlin.Pair;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ChannelResult;

/* compiled from: Channels.kt */
public final /* synthetic */ class ChannelsKt__ChannelsKt {
    public static final void sendBlocking(SendChannel sendChannel, Pair pair) {
        if (!(!(sendChannel.m328trySendJP2dKIU(pair) instanceof ChannelResult.Failed))) {
            BuildersKt.runBlocking$default(new ChannelsKt__ChannelsKt$sendBlocking$1(sendChannel, pair, (Continuation<? super ChannelsKt__ChannelsKt$sendBlocking$1>) null));
        }
    }
}
