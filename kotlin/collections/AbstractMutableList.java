package kotlin.collections;

import java.util.AbstractList;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: AbstractMutableList.kt */
public abstract class AbstractMutableList<E> extends AbstractList<E> implements KMappedMarker {
    public final E remove(int i) {
        ArrayDeque arrayDeque = (ArrayDeque) this;
        int i2 = arrayDeque.size;
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + i2);
        } else if (i == SetsKt__SetsKt.getLastIndex(arrayDeque)) {
            if (!arrayDeque.isEmpty()) {
                int access$positiveMod = ArrayDeque.access$positiveMod(arrayDeque, arrayDeque.head + SetsKt__SetsKt.getLastIndex(arrayDeque));
                E[] eArr = arrayDeque.elementData;
                E e = eArr[access$positiveMod];
                eArr[access$positiveMod] = null;
                arrayDeque.size--;
                return e;
            }
            throw new NoSuchElementException("ArrayDeque is empty.");
        } else if (i == 0) {
            return arrayDeque.removeFirst();
        } else {
            int access$positiveMod2 = ArrayDeque.access$positiveMod(arrayDeque, arrayDeque.head + i);
            E[] eArr2 = arrayDeque.elementData;
            E e2 = eArr2[access$positiveMod2];
            if (i < (arrayDeque.size >> 1)) {
                int i3 = arrayDeque.head;
                if (access$positiveMod2 >= i3) {
                    System.arraycopy(eArr2, i3, eArr2, i3 + 1, access$positiveMod2 - i3);
                } else {
                    System.arraycopy(eArr2, 0, eArr2, 1, access$positiveMod2 - 0);
                    Object[] objArr = arrayDeque.elementData;
                    objArr[0] = objArr[objArr.length - 1];
                    int i4 = arrayDeque.head;
                    System.arraycopy(objArr, i4, objArr, i4 + 1, (objArr.length - 1) - i4);
                }
                Object[] objArr2 = arrayDeque.elementData;
                int i5 = arrayDeque.head;
                objArr2[i5] = null;
                arrayDeque.head = arrayDeque.incremented(i5);
            } else {
                int access$positiveMod3 = ArrayDeque.access$positiveMod(arrayDeque, arrayDeque.head + SetsKt__SetsKt.getLastIndex(arrayDeque));
                if (access$positiveMod2 <= access$positiveMod3) {
                    Object[] objArr3 = arrayDeque.elementData;
                    int i6 = access$positiveMod2 + 1;
                    System.arraycopy(objArr3, i6, objArr3, access$positiveMod2, (access$positiveMod3 + 1) - i6);
                } else {
                    Object[] objArr4 = arrayDeque.elementData;
                    int i7 = access$positiveMod2 + 1;
                    System.arraycopy(objArr4, i7, objArr4, access$positiveMod2, objArr4.length - i7);
                    Object[] objArr5 = arrayDeque.elementData;
                    objArr5[objArr5.length - 1] = objArr5[0];
                    System.arraycopy(objArr5, 1, objArr5, 0, (access$positiveMod3 + 1) - 1);
                }
                arrayDeque.elementData[access$positiveMod3] = null;
            }
            arrayDeque.size--;
            return e2;
        }
    }

    public final int size() {
        return ((ArrayDeque) this).size;
    }
}
