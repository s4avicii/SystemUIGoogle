package androidx.preference;

import com.android.p012wm.shell.C1777R;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.C2497xa50de662;
import kotlin.coroutines.intrinsics.C2498xa50de663;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;

public class R$color {
    public static final int[] CardView = {16843071, 16843072, C1777R.attr.cardBackgroundColor, C1777R.attr.cardCornerRadius, C1777R.attr.cardElevation, C1777R.attr.cardMaxElevation, C1777R.attr.cardPreventCornerOverlap, C1777R.attr.cardUseCompatPadding, C1777R.attr.contentPadding, C1777R.attr.contentPaddingBottom, C1777R.attr.contentPaddingLeft, C1777R.attr.contentPaddingRight, C1777R.attr.contentPaddingTop};

    public static final Continuation createCoroutineUnintercepted(Function2 function2, Object obj, Continuation continuation) {
        if (function2 instanceof BaseContinuationImpl) {
            return ((BaseContinuationImpl) function2).create(obj, continuation);
        }
        CoroutineContext context = continuation.getContext();
        if (context == EmptyCoroutineContext.INSTANCE) {
            return new C2497xa50de662(continuation, function2, obj);
        }
        return new C2498xa50de663(continuation, context, function2, obj);
    }

    public static final Continuation intercepted(Continuation<Object> continuation) {
        ContinuationImpl continuationImpl;
        if (continuation instanceof ContinuationImpl) {
            continuationImpl = (ContinuationImpl) continuation;
        } else {
            continuationImpl = null;
        }
        if (continuationImpl != null && (continuation = continuationImpl.intercepted) == null) {
            ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) continuationImpl.getContext().get(ContinuationInterceptor.Key.$$INSTANCE);
            if (continuationInterceptor == null) {
                continuation = continuationImpl;
            } else {
                continuation = continuationInterceptor.interceptContinuation(continuationImpl);
            }
            continuationImpl.intercepted = continuation;
        }
        return continuation;
    }
}
