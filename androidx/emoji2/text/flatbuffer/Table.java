package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

public class Table {

    /* renamed from: bb */
    public ByteBuffer f23bb;
    public int bb_pos;
    public int vtable_size;
    public int vtable_start;

    public final int __offset(int i) {
        if (i < this.vtable_size) {
            return this.f23bb.getShort(this.vtable_start + i);
        }
        return 0;
    }

    public final void __reset(int i, ByteBuffer byteBuffer) {
        this.f23bb = byteBuffer;
        if (byteBuffer != null) {
            this.bb_pos = i;
            int i2 = i - byteBuffer.getInt(i);
            this.vtable_start = i2;
            this.vtable_size = this.f23bb.getShort(i2);
            return;
        }
        this.bb_pos = 0;
        this.vtable_start = 0;
        this.vtable_size = 0;
    }

    public Table() {
        if (Utf8Safe.DEFAULT == null) {
            Utf8Safe.DEFAULT = new Utf8Safe();
        }
    }
}
