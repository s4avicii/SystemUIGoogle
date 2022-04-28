package androidx.recyclerview.widget;

import com.android.systemui.p006qs.FgsManagerController$AppListAdapter$setData$1;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class DiffUtil {
    public static final C03171 DIAGONAL_COMPARATOR = new Comparator<Diagonal>() {
        public final int compare(Object obj, Object obj2) {
            return ((Diagonal) obj).f24x - ((Diagonal) obj2).f24x;
        }
    };

    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);
    }

    public static class Range {
        public int newListEnd;
        public int newListStart;
        public int oldListEnd;
        public int oldListStart;

        public Range() {
        }

        public Range(int i, int i2) {
            this.oldListStart = 0;
            this.oldListEnd = i;
            this.newListStart = 0;
            this.newListEnd = i2;
        }
    }

    public static class Snake {
        public int endX;
        public int endY;
        public boolean reverse;
        public int startX;
        public int startY;

        public final int diagonalSize() {
            return Math.min(this.endX - this.startX, this.endY - this.startY);
        }
    }

    public static class Diagonal {
        public final int size;

        /* renamed from: x */
        public final int f24x;

        /* renamed from: y */
        public final int f25y;

        public Diagonal(int i, int i2, int i3) {
            this.f24x = i;
            this.f25y = i2;
            this.size = i3;
        }
    }

    public static class DiffResult {
        public final Callback mCallback;
        public final boolean mDetectMoves = true;
        public final List<Diagonal> mDiagonals;
        public final int[] mNewItemStatuses;
        public final int mNewListSize;
        public final int[] mOldItemStatuses;
        public final int mOldListSize;

        public DiffResult(FgsManagerController$AppListAdapter$setData$1 fgsManagerController$AppListAdapter$setData$1, ArrayList arrayList, int[] iArr, int[] iArr2) {
            Diagonal diagonal;
            int i;
            Diagonal diagonal2;
            int i2;
            int i3;
            int i4;
            this.mDiagonals = arrayList;
            this.mOldItemStatuses = iArr;
            this.mNewItemStatuses = iArr2;
            Arrays.fill(iArr, 0);
            Arrays.fill(iArr2, 0);
            this.mCallback = fgsManagerController$AppListAdapter$setData$1;
            int oldListSize = fgsManagerController$AppListAdapter$setData$1.getOldListSize();
            this.mOldListSize = oldListSize;
            int newListSize = fgsManagerController$AppListAdapter$setData$1.getNewListSize();
            this.mNewListSize = newListSize;
            if (arrayList.isEmpty()) {
                diagonal = null;
            } else {
                diagonal = (Diagonal) arrayList.get(0);
            }
            if (!(diagonal != null && diagonal.f24x == 0 && diagonal.f25y == 0)) {
                arrayList.add(0, new Diagonal(0, 0, 0));
            }
            arrayList.add(new Diagonal(oldListSize, newListSize, 0));
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Diagonal diagonal3 = (Diagonal) it.next();
                for (int i5 = 0; i5 < diagonal3.size; i5++) {
                    int i6 = diagonal3.f24x + i5;
                    int i7 = diagonal3.f25y + i5;
                    if (this.mCallback.areContentsTheSame(i6, i7)) {
                        i4 = 1;
                    } else {
                        i4 = 2;
                    }
                    this.mOldItemStatuses[i6] = (i7 << 4) | i4;
                    this.mNewItemStatuses[i7] = (i6 << 4) | i4;
                }
            }
            if (this.mDetectMoves) {
                int i8 = 0;
                for (Diagonal next : this.mDiagonals) {
                    while (true) {
                        i = next.f24x;
                        if (i8 >= i) {
                            break;
                        }
                        if (this.mOldItemStatuses[i8] == 0) {
                            int size = this.mDiagonals.size();
                            int i9 = 0;
                            int i10 = 0;
                            while (true) {
                                if (i9 >= size) {
                                    break;
                                }
                                diagonal2 = this.mDiagonals.get(i9);
                                while (true) {
                                    i2 = diagonal2.f25y;
                                    if (i10 >= i2) {
                                        break;
                                    } else if (this.mNewItemStatuses[i10] != 0 || !this.mCallback.areItemsTheSame(i8, i10)) {
                                        i10++;
                                    } else {
                                        if (this.mCallback.areContentsTheSame(i8, i10)) {
                                            i3 = 8;
                                        } else {
                                            i3 = 4;
                                        }
                                        this.mOldItemStatuses[i8] = (i10 << 4) | i3;
                                        this.mNewItemStatuses[i10] = i3 | (i8 << 4);
                                    }
                                }
                                i10 = diagonal2.size + i2;
                                i9++;
                            }
                        }
                        i8++;
                    }
                    i8 = next.size + i;
                }
            }
        }

        public static PostponedUpdate getPostponedUpdate(ArrayDeque arrayDeque, int i, boolean z) {
            PostponedUpdate postponedUpdate;
            Iterator it = arrayDeque.iterator();
            while (true) {
                if (!it.hasNext()) {
                    postponedUpdate = null;
                    break;
                }
                postponedUpdate = (PostponedUpdate) it.next();
                if (postponedUpdate.posInOwnerList == i && postponedUpdate.removal == z) {
                    it.remove();
                    break;
                }
            }
            while (it.hasNext()) {
                PostponedUpdate postponedUpdate2 = (PostponedUpdate) it.next();
                if (z) {
                    postponedUpdate2.currentPos--;
                } else {
                    postponedUpdate2.currentPos++;
                }
            }
            return postponedUpdate;
        }
    }

    public static class PostponedUpdate {
        public int currentPos;
        public int posInOwnerList;
        public boolean removal;

        public PostponedUpdate(int i, int i2, boolean z) {
            this.posInOwnerList = i;
            this.currentPos = i2;
            this.removal = z;
        }
    }
}
