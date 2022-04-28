package kotlin.collections;

/* compiled from: CollectionsJVM.kt */
public final class CollectionSystemProperties {
    public static final boolean brittleContainsOptimizationEnabled;

    static {
        boolean z;
        String property = System.getProperty("kotlin.collections.convert_arg_to_set_in_removeAll");
        if (property == null) {
            z = false;
        } else {
            z = Boolean.parseBoolean(property);
        }
        brittleContainsOptimizationEnabled = z;
    }
}
