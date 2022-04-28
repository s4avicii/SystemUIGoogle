package kotlinx.coroutines;

/* compiled from: JobSupport.kt */
public final class InactiveNodeList implements Incomplete {
    public final NodeList list;

    public final boolean isActive() {
        return false;
    }

    public final String toString() {
        if (DebugKt.DEBUG) {
            return this.list.getString("New");
        }
        return super.toString();
    }

    public InactiveNodeList(NodeList nodeList) {
        this.list = nodeList;
    }

    public final NodeList getList() {
        return this.list;
    }
}
