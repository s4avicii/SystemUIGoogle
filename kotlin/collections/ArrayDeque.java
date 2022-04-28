package kotlin.collections;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ArrayDeque.kt */
public final class ArrayDeque<E> extends AbstractMutableList<E> {
    public static final Object[] emptyElementData = new Object[0];
    public Object[] elementData = emptyElementData;
    public int head;
    public int size;

    public final void add(int i, E e) {
        int i2 = this.size;
        if (i < 0 || i > i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        } else if (i == i2) {
            addLast(e);
        } else if (i == 0) {
            ensureCapacity(i2 + 1);
            int i3 = this.head;
            if (i3 == 0) {
                i3 = this.elementData.length;
            }
            int i4 = i3 - 1;
            this.head = i4;
            this.elementData[i4] = e;
            this.size++;
        } else {
            ensureCapacity(i2 + 1);
            int access$positiveMod = access$positiveMod(this, this.head + i);
            int i5 = this.size;
            if (i < ((i5 + 1) >> 1)) {
                int length = access$positiveMod == 0 ? this.elementData.length - 1 : access$positiveMod - 1;
                int i6 = this.head;
                int length2 = i6 == 0 ? this.elementData.length - 1 : i6 - 1;
                if (length >= i6) {
                    Object[] objArr = this.elementData;
                    objArr[length2] = objArr[i6];
                    int i7 = i6 + 1;
                    System.arraycopy(objArr, i7, objArr, i6, (length + 1) - i7);
                } else {
                    Object[] objArr2 = this.elementData;
                    System.arraycopy(objArr2, i6, objArr2, i6 - 1, objArr2.length - i6);
                    Object[] objArr3 = this.elementData;
                    objArr3[objArr3.length - 1] = objArr3[0];
                    System.arraycopy(objArr3, 1, objArr3, 0, (length + 1) - 1);
                }
                this.elementData[length] = e;
                this.head = length2;
            } else {
                int access$positiveMod2 = access$positiveMod(this, this.head + i5);
                if (access$positiveMod < access$positiveMod2) {
                    Object[] objArr4 = this.elementData;
                    System.arraycopy(objArr4, access$positiveMod, objArr4, access$positiveMod + 1, access$positiveMod2 - access$positiveMod);
                } else {
                    Object[] objArr5 = this.elementData;
                    System.arraycopy(objArr5, 0, objArr5, 1, access$positiveMod2 - 0);
                    Object[] objArr6 = this.elementData;
                    objArr6[0] = objArr6[objArr6.length - 1];
                    System.arraycopy(objArr6, access$positiveMod, objArr6, access$positiveMod + 1, (objArr6.length - 1) - access$positiveMod);
                }
                this.elementData[access$positiveMod] = e;
            }
            this.size++;
        }
    }

    public final boolean addAll(int i, Collection<? extends E> collection) {
        int i2 = this.size;
        if (i < 0 || i > i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        } else if (collection.isEmpty()) {
            return false;
        } else {
            int i3 = this.size;
            if (i == i3) {
                return addAll(collection);
            }
            ensureCapacity(collection.size() + i3);
            int access$positiveMod = access$positiveMod(this, this.head + this.size);
            int access$positiveMod2 = access$positiveMod(this, this.head + i);
            int size2 = collection.size();
            if (i < ((this.size + 1) >> 1)) {
                int i4 = this.head;
                int i5 = i4 - size2;
                if (access$positiveMod2 < i4) {
                    Object[] objArr = this.elementData;
                    System.arraycopy(objArr, i4, objArr, i5, objArr.length - i4);
                    if (size2 >= access$positiveMod2) {
                        Object[] objArr2 = this.elementData;
                        System.arraycopy(objArr2, 0, objArr2, objArr2.length - size2, access$positiveMod2 + 0);
                    } else {
                        Object[] objArr3 = this.elementData;
                        System.arraycopy(objArr3, 0, objArr3, objArr3.length - size2, size2 + 0);
                        Object[] objArr4 = this.elementData;
                        System.arraycopy(objArr4, size2, objArr4, 0, access$positiveMod2 - size2);
                    }
                } else if (i5 >= 0) {
                    Object[] objArr5 = this.elementData;
                    System.arraycopy(objArr5, i4, objArr5, i5, access$positiveMod2 - i4);
                } else {
                    Object[] objArr6 = this.elementData;
                    i5 += objArr6.length;
                    int i6 = access$positiveMod2 - i4;
                    int length = objArr6.length - i5;
                    if (length >= i6) {
                        System.arraycopy(objArr6, i4, objArr6, i5, i6);
                    } else {
                        System.arraycopy(objArr6, i4, objArr6, i5, (i4 + length) - i4);
                        Object[] objArr7 = this.elementData;
                        int i7 = this.head + length;
                        System.arraycopy(objArr7, i7, objArr7, 0, access$positiveMod2 - i7);
                    }
                }
                this.head = i5;
                int i8 = access$positiveMod2 - size2;
                if (i8 < 0) {
                    i8 += this.elementData.length;
                }
                copyCollectionElements(i8, collection);
            } else {
                int i9 = access$positiveMod2 + size2;
                if (access$positiveMod2 < access$positiveMod) {
                    int i10 = size2 + access$positiveMod;
                    Object[] objArr8 = this.elementData;
                    if (i10 <= objArr8.length) {
                        System.arraycopy(objArr8, access$positiveMod2, objArr8, i9, access$positiveMod - access$positiveMod2);
                    } else if (i9 >= objArr8.length) {
                        System.arraycopy(objArr8, access$positiveMod2, objArr8, i9 - objArr8.length, access$positiveMod - access$positiveMod2);
                    } else {
                        int length2 = access$positiveMod - (i10 - objArr8.length);
                        System.arraycopy(objArr8, length2, objArr8, 0, access$positiveMod - length2);
                        Object[] objArr9 = this.elementData;
                        System.arraycopy(objArr9, access$positiveMod2, objArr9, i9, length2 - access$positiveMod2);
                    }
                } else {
                    Object[] objArr10 = this.elementData;
                    System.arraycopy(objArr10, 0, objArr10, size2, access$positiveMod - 0);
                    Object[] objArr11 = this.elementData;
                    if (i9 >= objArr11.length) {
                        System.arraycopy(objArr11, access$positiveMod2, objArr11, i9 - objArr11.length, objArr11.length - access$positiveMod2);
                    } else {
                        int length3 = objArr11.length - size2;
                        System.arraycopy(objArr11, length3, objArr11, 0, objArr11.length - length3);
                        Object[] objArr12 = this.elementData;
                        System.arraycopy(objArr12, access$positiveMod2, objArr12, i9, (objArr12.length - size2) - access$positiveMod2);
                    }
                }
                copyCollectionElements(access$positiveMod2, collection);
            }
            return true;
        }
    }

    public final Object[] toArray() {
        return toArray(new Object[this.size]);
    }

    public final void addLast(E e) {
        ensureCapacity(this.size + 1);
        this.elementData[access$positiveMod(this, this.head + this.size)] = e;
        this.size++;
    }

    public final void clear() {
        int access$positiveMod = access$positiveMod(this, this.head + this.size);
        int i = this.head;
        if (i < access$positiveMod) {
            Arrays.fill(this.elementData, i, access$positiveMod, (Object) null);
        } else if (!isEmpty()) {
            Object[] objArr = this.elementData;
            Arrays.fill(objArr, this.head, objArr.length, (Object) null);
            Arrays.fill(this.elementData, 0, access$positiveMod, (Object) null);
        }
        this.head = 0;
        this.size = 0;
    }

    public final void ensureCapacity(int i) {
        if (i >= 0) {
            Object[] objArr = this.elementData;
            if (i > objArr.length) {
                if (objArr == emptyElementData) {
                    if (i < 10) {
                        i = 10;
                    }
                    this.elementData = new Object[i];
                    return;
                }
                int length = objArr.length;
                int i2 = length + (length >> 1);
                if (i2 - i < 0) {
                    i2 = i;
                }
                if (i2 - 2147483639 > 0) {
                    if (i > 2147483639) {
                        i2 = Integer.MAX_VALUE;
                    } else {
                        i2 = 2147483639;
                    }
                }
                Object[] objArr2 = new Object[i2];
                int i3 = this.head;
                System.arraycopy(objArr, i3, objArr2, 0, objArr.length - i3);
                Object[] objArr3 = this.elementData;
                int length2 = objArr3.length;
                int i4 = this.head;
                System.arraycopy(objArr3, 0, objArr2, length2 - i4, i4 - 0);
                this.head = 0;
                this.elementData = objArr2;
                return;
            }
            return;
        }
        throw new IllegalStateException("Deque is too big.");
    }

    public final E get(int i) {
        int i2 = this.size;
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        }
        return this.elementData[access$positiveMod(this, this.head + i)];
    }

    public final int incremented(int i) {
        if (i == this.elementData.length - 1) {
            return 0;
        }
        return i + 1;
    }

    public final int indexOf(Object obj) {
        int i;
        int access$positiveMod = access$positiveMod(this, this.head + this.size);
        int i2 = this.head;
        if (i2 < access$positiveMod) {
            while (i2 < access$positiveMod) {
                int i3 = i2 + 1;
                if (Intrinsics.areEqual(obj, this.elementData[i2])) {
                    i = this.head;
                } else {
                    i2 = i3;
                }
            }
            return -1;
        } else if (i2 < access$positiveMod) {
            return -1;
        } else {
            int length = this.elementData.length;
            while (true) {
                if (i2 < length) {
                    int i4 = i2 + 1;
                    if (Intrinsics.areEqual(obj, this.elementData[i2])) {
                        i = this.head;
                        break;
                    }
                    i2 = i4;
                } else {
                    int i5 = 0;
                    while (i5 < access$positiveMod) {
                        int i6 = i5 + 1;
                        if (Intrinsics.areEqual(obj, this.elementData[i5])) {
                            i2 = i5 + this.elementData.length;
                            i = this.head;
                        } else {
                            i5 = i6;
                        }
                    }
                    return -1;
                }
            }
        }
        return i2 - i;
    }

    public final boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    public final int lastIndexOf(Object obj) {
        int i;
        int i2;
        int access$positiveMod = access$positiveMod(this, this.head + this.size);
        int i3 = this.head;
        if (i3 < access$positiveMod) {
            i = access$positiveMod - 1;
            if (i3 <= i) {
                while (true) {
                    int i4 = i - 1;
                    if (Intrinsics.areEqual(obj, this.elementData[i])) {
                        i2 = this.head;
                        break;
                    } else if (i == i3) {
                        break;
                    } else {
                        i = i4;
                    }
                }
            }
            return -1;
        }
        if (i3 > access$positiveMod) {
            int i5 = access$positiveMod - 1;
            if (i5 >= 0) {
                while (true) {
                    int i6 = i5 - 1;
                    if (Intrinsics.areEqual(obj, this.elementData[i5])) {
                        i = i5 + this.elementData.length;
                        i2 = this.head;
                        break;
                    } else if (i6 < 0) {
                        break;
                    } else {
                        i5 = i6;
                    }
                }
            }
            int length = this.elementData.length - 1;
            int i7 = this.head;
            if (i7 <= length) {
                while (true) {
                    int i8 = i - 1;
                    if (Intrinsics.areEqual(obj, this.elementData[i])) {
                        i2 = this.head;
                        break;
                    } else if (i == i7) {
                        break;
                    } else {
                        length = i8;
                    }
                }
            }
        }
        return -1;
        return i - i2;
    }

    public final E set(int i, E e) {
        int i2 = this.size;
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        }
        int access$positiveMod = access$positiveMod(this, this.head + i);
        E[] eArr = this.elementData;
        E e2 = eArr[access$positiveMod];
        eArr[access$positiveMod] = e;
        return e2;
    }

    public static final int access$positiveMod(ArrayDeque arrayDeque, int i) {
        Objects.requireNonNull(arrayDeque);
        Object[] objArr = arrayDeque.elementData;
        if (i >= objArr.length) {
            return i - objArr.length;
        }
        return i;
    }

    public final boolean contains(Object obj) {
        if (indexOf(obj) != -1) {
            return true;
        }
        return false;
    }

    public final void copyCollectionElements(int i, Collection<? extends E> collection) {
        Iterator<? extends E> it = collection.iterator();
        int length = this.elementData.length;
        while (i < length) {
            int i2 = i + 1;
            if (!it.hasNext()) {
                break;
            }
            this.elementData[i] = it.next();
            i = i2;
        }
        int i3 = 0;
        int i4 = this.head;
        while (i3 < i4) {
            int i5 = i3 + 1;
            if (!it.hasNext()) {
                break;
            }
            this.elementData[i3] = it.next();
            i3 = i5;
        }
        this.size = collection.size() + this.size;
    }

    public final boolean remove(Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf == -1) {
            return false;
        }
        remove(indexOf);
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r1v3, types: [int] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean removeAll(java.util.Collection<? extends java.lang.Object> r12) {
        /*
            r11 = this;
            boolean r0 = r11.isEmpty()
            r1 = 0
            if (r0 != 0) goto L_0x0096
            java.lang.Object[] r0 = r11.elementData
            int r0 = r0.length
            r2 = 1
            if (r0 != 0) goto L_0x000f
            r0 = r2
            goto L_0x0010
        L_0x000f:
            r0 = r1
        L_0x0010:
            if (r0 == 0) goto L_0x0014
            goto L_0x0096
        L_0x0014:
            int r0 = r11.size
            int r3 = r11.head
            int r3 = r3 + r0
            int r0 = access$positiveMod(r11, r3)
            int r3 = r11.head
            r4 = 0
            if (r3 >= r0) goto L_0x0044
            r5 = r3
        L_0x0023:
            if (r3 >= r0) goto L_0x003e
            int r6 = r3 + 1
            java.lang.Object[] r7 = r11.elementData
            r3 = r7[r3]
            boolean r7 = r12.contains(r3)
            r7 = r7 ^ r2
            if (r7 == 0) goto L_0x003b
            java.lang.Object[] r7 = r11.elementData
            int r8 = r5 + 1
            r7[r5] = r3
            r3 = r6
            r5 = r8
            goto L_0x0023
        L_0x003b:
            r1 = r2
            r3 = r6
            goto L_0x0023
        L_0x003e:
            java.lang.Object[] r12 = r11.elementData
            java.util.Arrays.fill(r12, r5, r0, r4)
            goto L_0x0089
        L_0x0044:
            java.lang.Object[] r5 = r11.elementData
            int r5 = r5.length
            r7 = r1
            r6 = r3
        L_0x0049:
            if (r3 >= r5) goto L_0x0066
            int r8 = r3 + 1
            java.lang.Object[] r9 = r11.elementData
            r10 = r9[r3]
            r9[r3] = r4
            boolean r3 = r12.contains(r10)
            r3 = r3 ^ r2
            if (r3 == 0) goto L_0x0063
            java.lang.Object[] r3 = r11.elementData
            int r9 = r6 + 1
            r3[r6] = r10
            r3 = r8
            r6 = r9
            goto L_0x0049
        L_0x0063:
            r7 = r2
            r3 = r8
            goto L_0x0049
        L_0x0066:
            int r3 = access$positiveMod(r11, r6)
            r5 = r3
        L_0x006b:
            if (r1 >= r0) goto L_0x0088
            int r3 = r1 + 1
            java.lang.Object[] r6 = r11.elementData
            r8 = r6[r1]
            r6[r1] = r4
            boolean r1 = r12.contains(r8)
            r1 = r1 ^ r2
            if (r1 == 0) goto L_0x0085
            java.lang.Object[] r1 = r11.elementData
            r1[r5] = r8
            int r5 = r11.incremented(r5)
            goto L_0x0086
        L_0x0085:
            r7 = r2
        L_0x0086:
            r1 = r3
            goto L_0x006b
        L_0x0088:
            r1 = r7
        L_0x0089:
            if (r1 == 0) goto L_0x0096
            int r12 = r11.head
            int r5 = r5 - r12
            if (r5 >= 0) goto L_0x0094
            java.lang.Object[] r12 = r11.elementData
            int r12 = r12.length
            int r5 = r5 + r12
        L_0x0094:
            r11.size = r5
        L_0x0096:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.ArrayDeque.removeAll(java.util.Collection):boolean");
    }

    public final E removeFirst() {
        if (!isEmpty()) {
            int i = this.head;
            E[] eArr = this.elementData;
            E e = eArr[i];
            eArr[i] = null;
            this.head = incremented(i);
            this.size--;
            return e;
        }
        throw new NoSuchElementException("ArrayDeque is empty.");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r1v3, types: [int] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean retainAll(java.util.Collection<? extends java.lang.Object> r12) {
        /*
            r11 = this;
            boolean r0 = r11.isEmpty()
            r1 = 0
            if (r0 != 0) goto L_0x0093
            java.lang.Object[] r0 = r11.elementData
            int r0 = r0.length
            r2 = 1
            if (r0 != 0) goto L_0x000f
            r0 = r2
            goto L_0x0010
        L_0x000f:
            r0 = r1
        L_0x0010:
            if (r0 == 0) goto L_0x0014
            goto L_0x0093
        L_0x0014:
            int r0 = r11.size
            int r3 = r11.head
            int r3 = r3 + r0
            int r0 = access$positiveMod(r11, r3)
            int r3 = r11.head
            r4 = 0
            if (r3 >= r0) goto L_0x0043
            r5 = r3
        L_0x0023:
            if (r3 >= r0) goto L_0x003d
            int r6 = r3 + 1
            java.lang.Object[] r7 = r11.elementData
            r3 = r7[r3]
            boolean r7 = r12.contains(r3)
            if (r7 == 0) goto L_0x003a
            java.lang.Object[] r7 = r11.elementData
            int r8 = r5 + 1
            r7[r5] = r3
            r3 = r6
            r5 = r8
            goto L_0x0023
        L_0x003a:
            r1 = r2
            r3 = r6
            goto L_0x0023
        L_0x003d:
            java.lang.Object[] r12 = r11.elementData
            java.util.Arrays.fill(r12, r5, r0, r4)
            goto L_0x0086
        L_0x0043:
            java.lang.Object[] r5 = r11.elementData
            int r5 = r5.length
            r7 = r1
            r6 = r3
        L_0x0048:
            if (r3 >= r5) goto L_0x0064
            int r8 = r3 + 1
            java.lang.Object[] r9 = r11.elementData
            r10 = r9[r3]
            r9[r3] = r4
            boolean r3 = r12.contains(r10)
            if (r3 == 0) goto L_0x0061
            java.lang.Object[] r3 = r11.elementData
            int r9 = r6 + 1
            r3[r6] = r10
            r3 = r8
            r6 = r9
            goto L_0x0048
        L_0x0061:
            r7 = r2
            r3 = r8
            goto L_0x0048
        L_0x0064:
            int r3 = access$positiveMod(r11, r6)
            r5 = r3
        L_0x0069:
            if (r1 >= r0) goto L_0x0085
            int r3 = r1 + 1
            java.lang.Object[] r6 = r11.elementData
            r8 = r6[r1]
            r6[r1] = r4
            boolean r1 = r12.contains(r8)
            if (r1 == 0) goto L_0x0082
            java.lang.Object[] r1 = r11.elementData
            r1[r5] = r8
            int r5 = r11.incremented(r5)
            goto L_0x0083
        L_0x0082:
            r7 = r2
        L_0x0083:
            r1 = r3
            goto L_0x0069
        L_0x0085:
            r1 = r7
        L_0x0086:
            if (r1 == 0) goto L_0x0093
            int r12 = r11.head
            int r5 = r5 - r12
            if (r5 >= 0) goto L_0x0091
            java.lang.Object[] r12 = r11.elementData
            int r12 = r12.length
            int r5 = r5 + r12
        L_0x0091:
            r11.size = r5
        L_0x0093:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.ArrayDeque.retainAll(java.util.Collection):boolean");
    }

    public final <T> T[] toArray(T[] tArr) {
        int length = tArr.length;
        int i = this.size;
        if (length < i) {
            Object newInstance = Array.newInstance(tArr.getClass().getComponentType(), i);
            Objects.requireNonNull(newInstance, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.arrayOfNulls>");
            tArr = (Object[]) newInstance;
        }
        int access$positiveMod = access$positiveMod(this, this.head + this.size);
        int i2 = this.head;
        if (i2 < access$positiveMod) {
            ArraysKt___ArraysKt.copyInto$default(this.elementData, tArr, 0, i2, access$positiveMod, 2);
        } else if (!isEmpty()) {
            Object[] objArr = this.elementData;
            int i3 = this.head;
            System.arraycopy(objArr, i3, tArr, 0, objArr.length - i3);
            Object[] objArr2 = this.elementData;
            System.arraycopy(objArr2, 0, tArr, objArr2.length - this.head, access$positiveMod - 0);
        }
        int length2 = tArr.length;
        int i4 = this.size;
        if (length2 > i4) {
            tArr[i4] = null;
        }
        return tArr;
    }

    public final boolean add(E e) {
        addLast(e);
        return true;
    }

    public final boolean addAll(Collection<? extends E> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        ensureCapacity(collection.size() + this.size);
        copyCollectionElements(access$positiveMod(this, this.head + this.size), collection);
        return true;
    }
}
