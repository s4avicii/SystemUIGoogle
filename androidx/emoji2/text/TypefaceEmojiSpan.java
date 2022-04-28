package androidx.emoji2.text;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import java.util.Objects;

public final class TypefaceEmojiSpan extends EmojiSpan {
    public final void draw(Canvas canvas, @SuppressLint({"UnknownNullness"}) CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        Paint paint2 = paint;
        Objects.requireNonNull(EmojiCompat.get());
        EmojiMetadata emojiMetadata = this.mMetadata;
        Objects.requireNonNull(emojiMetadata);
        MetadataRepo metadataRepo = emojiMetadata.mMetadataRepo;
        Objects.requireNonNull(metadataRepo);
        Typeface typeface = metadataRepo.mTypeface;
        Typeface typeface2 = paint.getTypeface();
        paint2.setTypeface(typeface);
        int i6 = emojiMetadata.mIndex * 2;
        MetadataRepo metadataRepo2 = emojiMetadata.mMetadataRepo;
        Objects.requireNonNull(metadataRepo2);
        char[] cArr = metadataRepo2.mEmojiCharArray;
        canvas.drawText(cArr, i6, 2, f, (float) i4, paint);
        paint2.setTypeface(typeface2);
    }

    public TypefaceEmojiSpan(EmojiMetadata emojiMetadata) {
        super(emojiMetadata);
    }
}
