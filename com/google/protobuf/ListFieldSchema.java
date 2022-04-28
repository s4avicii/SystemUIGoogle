package com.google.protobuf;

import com.google.protobuf.Internal;
import java.util.Collections;
import java.util.List;

public abstract class ListFieldSchema {
    public static final ListFieldSchemaFull FULL_INSTANCE = new ListFieldSchemaFull();
    public static final ListFieldSchemaLite LITE_INSTANCE = new ListFieldSchemaLite();

    public abstract void makeImmutableListAt(Object obj, long j);

    public abstract <L> void mergeListsAt(Object obj, Object obj2, long j);

    public static final class ListFieldSchemaFull extends ListFieldSchema {
        public static final Class<?> UNMODIFIABLE_LIST_CLASS = Collections.unmodifiableList(Collections.emptyList()).getClass();

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: com.google.protobuf.LazyStringArrayList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: java.util.ArrayList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: com.google.protobuf.LazyStringArrayList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: com.google.protobuf.LazyStringArrayList} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static <L> java.util.List<L> mutableListAt(java.lang.Object r3, long r4, int r6) {
            /*
                java.lang.Object r0 = com.google.protobuf.UnsafeUtil.getObject(r3, r4)
                java.util.List r0 = (java.util.List) r0
                boolean r1 = r0.isEmpty()
                if (r1 == 0) goto L_0x002f
                boolean r1 = r0 instanceof com.google.protobuf.LazyStringList
                if (r1 == 0) goto L_0x0016
                com.google.protobuf.LazyStringArrayList r0 = new com.google.protobuf.LazyStringArrayList
                r0.<init>((int) r6)
                goto L_0x002b
            L_0x0016:
                boolean r1 = r0 instanceof com.google.protobuf.PrimitiveNonBoxingCollection
                if (r1 == 0) goto L_0x0026
                boolean r1 = r0 instanceof com.google.protobuf.Internal.ProtobufList
                if (r1 == 0) goto L_0x0026
                com.google.protobuf.Internal$ProtobufList r0 = (com.google.protobuf.Internal.ProtobufList) r0
                com.google.protobuf.Internal$ProtobufList r6 = r0.mutableCopyWithCapacity(r6)
                r0 = r6
                goto L_0x002b
            L_0x0026:
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>(r6)
            L_0x002b:
                com.google.protobuf.UnsafeUtil.putObject(r3, r4, r0)
                goto L_0x0081
            L_0x002f:
                java.lang.Class<?> r1 = UNMODIFIABLE_LIST_CLASS
                java.lang.Class r2 = r0.getClass()
                boolean r1 = r1.isAssignableFrom(r2)
                if (r1 == 0) goto L_0x004d
                java.util.ArrayList r1 = new java.util.ArrayList
                int r2 = r0.size()
                int r2 = r2 + r6
                r1.<init>(r2)
                r1.addAll(r0)
                com.google.protobuf.UnsafeUtil.putObject(r3, r4, r1)
            L_0x004b:
                r0 = r1
                goto L_0x0081
            L_0x004d:
                boolean r1 = r0 instanceof com.google.protobuf.UnmodifiableLazyStringList
                if (r1 == 0) goto L_0x0064
                com.google.protobuf.LazyStringArrayList r1 = new com.google.protobuf.LazyStringArrayList
                int r2 = r0.size()
                int r2 = r2 + r6
                r1.<init>((int) r2)
                com.google.protobuf.UnmodifiableLazyStringList r0 = (com.google.protobuf.UnmodifiableLazyStringList) r0
                r1.addAll(r0)
                com.google.protobuf.UnsafeUtil.putObject(r3, r4, r1)
                goto L_0x004b
            L_0x0064:
                boolean r1 = r0 instanceof com.google.protobuf.PrimitiveNonBoxingCollection
                if (r1 == 0) goto L_0x0081
                boolean r1 = r0 instanceof com.google.protobuf.Internal.ProtobufList
                if (r1 == 0) goto L_0x0081
                r1 = r0
                com.google.protobuf.Internal$ProtobufList r1 = (com.google.protobuf.Internal.ProtobufList) r1
                boolean r2 = r1.isModifiable()
                if (r2 != 0) goto L_0x0081
                int r0 = r0.size()
                int r0 = r0 + r6
                com.google.protobuf.Internal$ProtobufList r0 = r1.mutableCopyWithCapacity(r0)
                com.google.protobuf.UnsafeUtil.putObject(r3, r4, r0)
            L_0x0081:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.ListFieldSchema.ListFieldSchemaFull.mutableListAt(java.lang.Object, long, int):java.util.List");
        }

        public final void makeImmutableListAt(Object obj, long j) {
            Object obj2;
            List list = (List) UnsafeUtil.getObject(obj, j);
            if (list instanceof LazyStringList) {
                obj2 = ((LazyStringList) list).getUnmodifiableView();
            } else if (!UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
                if (!(list instanceof PrimitiveNonBoxingCollection) || !(list instanceof Internal.ProtobufList)) {
                    obj2 = Collections.unmodifiableList(list);
                } else {
                    Internal.ProtobufList protobufList = (Internal.ProtobufList) list;
                    if (protobufList.isModifiable()) {
                        protobufList.makeImmutable();
                        return;
                    }
                    return;
                }
            } else {
                return;
            }
            UnsafeUtil.putObject(obj, j, obj2);
        }

        public final <E> void mergeListsAt(Object obj, Object obj2, long j) {
            List list = (List) UnsafeUtil.getObject(obj2, j);
            List mutableListAt = mutableListAt(obj, j, list.size());
            int size = mutableListAt.size();
            int size2 = list.size();
            if (size > 0 && size2 > 0) {
                mutableListAt.addAll(list);
            }
            if (size > 0) {
                list = mutableListAt;
            }
            UnsafeUtil.putObject(obj, j, list);
        }
    }

    public static final class ListFieldSchemaLite extends ListFieldSchema {
        public final void makeImmutableListAt(Object obj, long j) {
            ((Internal.ProtobufList) UnsafeUtil.getObject(obj, j)).makeImmutable();
        }

        public final <E> void mergeListsAt(Object obj, Object obj2, long j) {
            Internal.ProtobufList protobufList = (Internal.ProtobufList) UnsafeUtil.getObject(obj, j);
            Internal.ProtobufList protobufList2 = (Internal.ProtobufList) UnsafeUtil.getObject(obj2, j);
            int size = protobufList.size();
            int size2 = protobufList2.size();
            if (size > 0 && size2 > 0) {
                if (!protobufList.isModifiable()) {
                    protobufList = protobufList.mutableCopyWithCapacity(size2 + size);
                }
                protobufList.addAll(protobufList2);
            }
            if (size > 0) {
                protobufList2 = protobufList;
            }
            UnsafeUtil.putObject(obj, j, protobufList2);
        }
    }
}
