package kotlinx.coroutines.flow.internal;

import java.util.Objects;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlinx.atomicfu.AtomicRef;
import kotlinx.coroutines.ChildHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* compiled from: SafeCollector.common.kt */
final class SafeCollector_commonKt$checkContext$result$1 extends Lambda implements Function2<Integer, CoroutineContext.Element, Integer> {
    public final /* synthetic */ SafeCollector<?> $this_checkContext;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SafeCollector_commonKt$checkContext$result$1(SafeCollector<?> safeCollector) {
        super(2);
        this.$this_checkContext = safeCollector;
    }

    public final Object invoke(Object obj, Object obj2) {
        int i;
        int intValue = ((Number) obj).intValue();
        CoroutineContext.Element element = (CoroutineContext.Element) obj2;
        CoroutineContext.Key key = element.getKey();
        CoroutineContext.Element element2 = this.$this_checkContext.collectContext.get(key);
        if (key != Job.Key.$$INSTANCE) {
            if (element != element2) {
                i = Integer.MIN_VALUE;
            } else {
                i = intValue + 1;
            }
            return Integer.valueOf(i);
        }
        Job job = (Job) element2;
        Job job2 = (Job) element;
        while (true) {
            if (job2 != null) {
                if (job2 == job || !(job2 instanceof ScopeCoroutine)) {
                    break;
                }
                AtomicRef<ChildHandle> atomicRef = ((ScopeCoroutine) job2)._parentHandle;
                Objects.requireNonNull(atomicRef);
                ChildHandle childHandle = (ChildHandle) atomicRef.value;
                if (childHandle == null) {
                    job2 = null;
                } else {
                    job2 = childHandle.getParent();
                }
            } else {
                job2 = null;
                break;
            }
        }
        if (job2 == job) {
            if (job != null) {
                intValue++;
            }
            return Integer.valueOf(intValue);
        }
        throw new IllegalStateException(("Flow invariant is violated:\n\t\tEmission from another coroutine is detected.\n\t\tChild of " + job2 + ", expected child of " + job + ".\n\t\tFlowCollector is not thread-safe and concurrent emissions are prohibited.\n\t\tTo mitigate this restriction please use 'channelFlow' builder instead of 'flow'").toString());
    }
}
