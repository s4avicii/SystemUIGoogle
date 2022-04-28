package kotlinx.coroutines;

import androidx.activity.result.ActivityResultRegistry$3$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;

/* compiled from: JobSupport.kt */
public final class NodeList extends LockFreeLinkedListHead implements Incomplete {
    public final NodeList getList() {
        return this;
    }

    public final boolean isActive() {
        return true;
    }

    public final String getString(String str) {
        StringBuilder m = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("List{", str, "}[");
        boolean z = true;
        for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) getNext(); !Intrinsics.areEqual(lockFreeLinkedListNode, this); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
            if (lockFreeLinkedListNode instanceof JobNode) {
                JobNode jobNode = (JobNode) lockFreeLinkedListNode;
                if (z) {
                    z = false;
                } else {
                    m.append(", ");
                }
                m.append(jobNode);
            }
        }
        m.append("]");
        return m.toString();
    }

    public final String toString() {
        if (DebugKt.DEBUG) {
            return getString("Active");
        }
        return super.toString();
    }
}
