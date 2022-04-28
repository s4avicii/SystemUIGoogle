package com.android.systemui.people;

import android.app.Activity;
import android.app.people.PeopleSpaceTile;
import android.content.Context;
import android.graphics.Outline;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleTileKey;
import com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda7;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda10;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda25;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class PeopleSpaceActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mAppWidgetId;
    public Context mContext;
    public PeopleSpaceWidgetManager mPeopleSpaceWidgetManager;
    public C09571 mViewOutlineProvider = new ViewOutlineProvider() {
        public final void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), PeopleSpaceActivity.this.mContext.getResources().getDimension(C1777R.dimen.people_space_widget_radius));
        }
    };

    public PeopleSpaceActivity(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        this.mPeopleSpaceWidgetManager = peopleSpaceWidgetManager;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getApplicationContext();
        this.mAppWidgetId = getIntent().getIntExtra("appWidgetId", 0);
        setResult(0);
    }

    public final void onResume() {
        super.onResume();
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        try {
            PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.mPeopleSpaceWidgetManager;
            Objects.requireNonNull(peopleSpaceWidgetManager);
            arrayList = PeopleSpaceUtils.getSortedTiles(peopleSpaceWidgetManager.mIPeopleManager, peopleSpaceWidgetManager.mLauncherApps, peopleSpaceWidgetManager.mUserManager, peopleSpaceWidgetManager.mINotificationManager.getConversations(true).getList().stream().filter(WifiPickerTracker$$ExternalSyntheticLambda25.INSTANCE$1).map(WifiPickerTracker$$ExternalSyntheticLambda10.INSTANCE$2));
            arrayList2 = this.mPeopleSpaceWidgetManager.getRecentTiles();
        } catch (Exception e) {
            Log.e("PeopleSpaceActivity", "Couldn't retrieve conversations", e);
        }
        if (!arrayList2.isEmpty() || !arrayList.isEmpty()) {
            setContentView(C1777R.layout.people_space_activity);
            setTileViews(C1777R.C1779id.priority, C1777R.C1779id.priority_tiles, arrayList);
            setTileViews(C1777R.C1779id.recent, C1777R.C1779id.recent_tiles, arrayList2);
            return;
        }
        setContentView(C1777R.layout.people_space_activity_no_conversations);
        ((GradientDrawable) ((LinearLayout) findViewById(16908288)).getBackground()).setColor(this.mContext.getTheme().obtainStyledAttributes(new int[]{17956909}).getColor(0, -1));
    }

    public final void setTileViews(int i, int i2, List<PeopleSpaceTile> list) {
        boolean z;
        boolean z2;
        if (list.isEmpty()) {
            ((LinearLayout) findViewById(i)).setVisibility(8);
            return;
        }
        ViewGroup viewGroup = (ViewGroup) findViewById(i2);
        viewGroup.setClipToOutline(true);
        viewGroup.setOutlineProvider(this.mViewOutlineProvider);
        for (int i3 = 0; i3 < list.size(); i3++) {
            PeopleSpaceTile peopleSpaceTile = list.get(i3);
            Context context = this.mContext;
            String id = peopleSpaceTile.getId();
            if (i3 == list.size() - 1) {
                z = true;
            } else {
                z = false;
            }
            PeopleSpaceTileView peopleSpaceTileView = new PeopleSpaceTileView(context, viewGroup, id, z);
            try {
                if (peopleSpaceTile.getUserName() != null) {
                    peopleSpaceTileView.mNameView.setText(peopleSpaceTile.getUserName().toString());
                }
                Context context2 = this.mContext;
                float f = context2.getResources().getDisplayMetrics().density;
                Pattern pattern = PeopleTileViewHelper.DOUBLE_EXCLAMATION_PATTERN;
                int dimension = (int) (context2.getResources().getDimension(C1777R.dimen.avatar_size_for_medium) / f);
                if (peopleSpaceTile.getStatuses() == null || !peopleSpaceTile.getStatuses().stream().anyMatch(ThemeOverlayApplier$$ExternalSyntheticLambda7.INSTANCE$1)) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                peopleSpaceTileView.mPersonIconView.setImageBitmap(PeopleTileViewHelper.getPersonIconBitmap(context2, peopleSpaceTile, dimension, z2));
                peopleSpaceTileView.setOnClickListener(new PeopleSpaceActivity$$ExternalSyntheticLambda0(this, peopleSpaceTile, new PeopleTileKey(peopleSpaceTile)));
            } catch (Exception e) {
                Log.e("PeopleSpaceActivity", "Couldn't retrieve shortcut information", e);
            }
        }
    }

    public void dismissActivity(View view) {
        finish();
    }
}
