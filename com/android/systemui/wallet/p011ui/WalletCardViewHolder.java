package com.android.systemui.wallet.p011ui;

import android.view.View;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.android.systemui.wallet.ui.WalletCardViewHolder */
public final class WalletCardViewHolder extends RecyclerView.ViewHolder {
    public final CardView mCardView;
    public WalletCardViewInfo mCardViewInfo;
    public final ImageView mImageView;

    public WalletCardViewHolder(View view) {
        super(view);
        CardView cardView = (CardView) view.requireViewById(C1777R.C1779id.card);
        this.mCardView = cardView;
        this.mImageView = (ImageView) cardView.requireViewById(C1777R.C1779id.card_image);
    }
}
