package com.android.framework.protobuf.nano;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public final class MessageNanoPrinter {
    public static String deCamelCaseify(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (i == 0) {
                stringBuffer.append(Character.toLowerCase(charAt));
            } else if (Character.isUpperCase(charAt)) {
                stringBuffer.append('_');
                stringBuffer.append(Character.toLowerCase(charAt));
            } else {
                stringBuffer.append(charAt);
            }
        }
        return stringBuffer.toString();
    }

    public static void print(String str, Object obj, StringBuffer stringBuffer, StringBuffer stringBuffer2) throws IllegalAccessException, InvocationTargetException {
        int i;
        Object obj2 = obj;
        StringBuffer stringBuffer3 = stringBuffer;
        StringBuffer stringBuffer4 = stringBuffer2;
        if (obj2 != null) {
            if (obj2 instanceof MessageNano) {
                int length = stringBuffer.length();
                if (str != null) {
                    stringBuffer4.append(stringBuffer3);
                    stringBuffer4.append(deCamelCaseify(str));
                    stringBuffer4.append(" <\n");
                    stringBuffer3.append("  ");
                }
                Class<?> cls = obj.getClass();
                for (Field field : cls.getFields()) {
                    int modifiers = field.getModifiers();
                    String name = field.getName();
                    if (!"cachedSize".equals(name) && (modifiers & 1) == 1 && (modifiers & 8) != 8 && !name.startsWith("_") && !name.endsWith("_")) {
                        Class<?> type = field.getType();
                        Object obj3 = field.get(obj2);
                        if (!type.isArray()) {
                            print(name, obj3, stringBuffer3, stringBuffer4);
                        } else if (type.getComponentType() == Byte.TYPE) {
                            print(name, obj3, stringBuffer3, stringBuffer4);
                        } else {
                            if (obj3 == null) {
                                i = 0;
                            } else {
                                i = Array.getLength(obj3);
                            }
                            for (int i2 = 0; i2 < i; i2++) {
                                print(name, Array.get(obj3, i2), stringBuffer3, stringBuffer4);
                            }
                        }
                    }
                }
                for (Method name2 : cls.getMethods()) {
                    String name3 = name2.getName();
                    if (name3.startsWith("set")) {
                        String substring = name3.substring(3);
                        try {
                            if (((Boolean) cls.getMethod("has" + substring, new Class[0]).invoke(obj2, new Object[0])).booleanValue()) {
                                print(substring, cls.getMethod("get" + substring, new Class[0]).invoke(obj2, new Object[0]), stringBuffer3, stringBuffer4);
                            }
                        } catch (NoSuchMethodException unused) {
                        }
                    }
                }
                if (str != null) {
                    stringBuffer3.setLength(length);
                    stringBuffer4.append(stringBuffer3);
                    stringBuffer4.append(">\n");
                }
            } else if (obj2 instanceof Map) {
                String deCamelCaseify = deCamelCaseify(str);
                for (Map.Entry entry : ((Map) obj2).entrySet()) {
                    stringBuffer4.append(stringBuffer3);
                    stringBuffer4.append(deCamelCaseify);
                    stringBuffer4.append(" <\n");
                    int length2 = stringBuffer.length();
                    stringBuffer3.append("  ");
                    print("key", entry.getKey(), stringBuffer3, stringBuffer4);
                    print("value", entry.getValue(), stringBuffer3, stringBuffer4);
                    stringBuffer3.setLength(length2);
                    stringBuffer4.append(stringBuffer3);
                    stringBuffer4.append(">\n");
                }
            } else {
                String deCamelCaseify2 = deCamelCaseify(str);
                stringBuffer4.append(stringBuffer3);
                stringBuffer4.append(deCamelCaseify2);
                stringBuffer4.append(": ");
                if (obj2 instanceof String) {
                    String str2 = (String) obj2;
                    if (!str2.startsWith("http") && str2.length() > 200) {
                        str2 = str2.substring(0, 200) + "[...]";
                    }
                    int length3 = str2.length();
                    StringBuilder sb = new StringBuilder(length3);
                    for (int i3 = 0; i3 < length3; i3++) {
                        char charAt = str2.charAt(i3);
                        if (charAt < ' ' || charAt > '~' || charAt == '\"' || charAt == '\'') {
                            sb.append(String.format("\\u%04x", new Object[]{Integer.valueOf(charAt)}));
                        } else {
                            sb.append(charAt);
                        }
                    }
                    String sb2 = sb.toString();
                    stringBuffer4.append("\"");
                    stringBuffer4.append(sb2);
                    stringBuffer4.append("\"");
                } else if (obj2 instanceof byte[]) {
                    byte[] bArr = (byte[]) obj2;
                    stringBuffer4.append('\"');
                    for (byte b : bArr) {
                        byte b2 = b & 255;
                        if (b2 == 92 || b2 == 34) {
                            stringBuffer4.append('\\');
                            stringBuffer4.append((char) b2);
                        } else if (b2 < 32 || b2 >= Byte.MAX_VALUE) {
                            stringBuffer4.append(String.format("\\%03o", new Object[]{Integer.valueOf(b2)}));
                        } else {
                            stringBuffer4.append((char) b2);
                        }
                    }
                    stringBuffer4.append('\"');
                } else {
                    stringBuffer4.append(obj2);
                }
                stringBuffer4.append("\n");
            }
        }
    }
}
