package kotlin.jvm;

import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.reflect.KClass;

/* compiled from: JvmClassMapping.kt */
public final class JvmClassMappingKt {
    public static final <T> Class<T> getJavaObjectType(KClass<T> kClass) {
        Class<?> jClass = ((ClassBasedDeclarationContainer) kClass).getJClass();
        if (!jClass.isPrimitive()) {
            return jClass;
        }
        String name = jClass.getName();
        switch (name.hashCode()) {
            case -1325958191:
                if (!name.equals("double")) {
                    return jClass;
                }
                return Double.class;
            case 104431:
                if (!name.equals("int")) {
                    return jClass;
                }
                return Integer.class;
            case 3039496:
                if (!name.equals("byte")) {
                    return jClass;
                }
                return Byte.class;
            case 3052374:
                if (!name.equals("char")) {
                    return jClass;
                }
                return Character.class;
            case 3327612:
                if (!name.equals("long")) {
                    return jClass;
                }
                return Long.class;
            case 3625364:
                if (!name.equals("void")) {
                    return jClass;
                }
                return Void.class;
            case 64711720:
                if (!name.equals("boolean")) {
                    return jClass;
                }
                return Boolean.class;
            case 97526364:
                if (!name.equals("float")) {
                    return jClass;
                }
                return Float.class;
            case 109413500:
                if (!name.equals("short")) {
                    return jClass;
                }
                return Short.class;
            default:
                return jClass;
        }
    }
}
