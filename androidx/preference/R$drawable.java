package androidx.preference;

import androidx.cardview.widget.CardView;
import androidx.cardview.widget.CardViewDelegate;
import androidx.cardview.widget.RoundRectDrawable;
import androidx.cardview.widget.RoundRectDrawableWithShadow;
import com.android.systemui.R$array;
import java.util.Objects;

public final class R$drawable {
    public static void assertLengthInRange(String str, String str2, int i, int i2) {
        boolean z;
        Objects.requireNonNull(str, String.format("%s cannot be null.", new Object[]{str2}));
        int length = str.length();
        if (length > i2 || length < i) {
            z = false;
        } else {
            z = true;
        }
        R$array.checkArgument(z, String.format("Length of %s should be in the range [%s-%s]", new Object[]{str2, Integer.valueOf(i), Integer.valueOf(i2)}));
    }

    public void updatePadding(CardViewDelegate cardViewDelegate) {
        float f;
        CardView.C00931 r8 = (CardView.C00931) cardViewDelegate;
        Objects.requireNonNull(r8);
        CardView cardView = CardView.this;
        Objects.requireNonNull(cardView);
        if (!cardView.mCompatPadding) {
            r8.setShadowPadding(0, 0, 0, 0);
            return;
        }
        RoundRectDrawable roundRectDrawable = (RoundRectDrawable) r8.mCardBackground;
        Objects.requireNonNull(roundRectDrawable);
        float f2 = roundRectDrawable.mPadding;
        RoundRectDrawable roundRectDrawable2 = (RoundRectDrawable) r8.mCardBackground;
        Objects.requireNonNull(roundRectDrawable2);
        float f3 = roundRectDrawable2.mRadius;
        CardView cardView2 = CardView.this;
        Objects.requireNonNull(cardView2);
        boolean z = cardView2.mPreventCornerOverlap;
        int i = RoundRectDrawableWithShadow.$r8$clinit;
        if (z) {
            f = (float) (((1.0d - RoundRectDrawableWithShadow.COS_45) * ((double) f3)) + ((double) f2));
        } else {
            f = f2;
        }
        int ceil = (int) Math.ceil((double) f);
        CardView cardView3 = CardView.this;
        Objects.requireNonNull(cardView3);
        int ceil2 = (int) Math.ceil((double) RoundRectDrawableWithShadow.calculateVerticalPadding(f2, f3, cardView3.mPreventCornerOverlap));
        r8.setShadowPadding(ceil, ceil2, ceil, ceil2);
    }
}
