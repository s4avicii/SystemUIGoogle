package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;

@SuppressLint({"BanParcelableUsage"})
final class FragmentManagerState implements Parcelable {
    public static final Parcelable.Creator<FragmentManagerState> CREATOR = new Parcelable.Creator<FragmentManagerState>() {
        public final Object createFromParcel(Parcel parcel) {
            return new FragmentManagerState(parcel);
        }

        public final Object[] newArray(int i) {
            return new FragmentManagerState[i];
        }
    };
    public ArrayList<String> mActive;
    public ArrayList<String> mAdded;
    public BackStackRecordState[] mBackStack;
    public int mBackStackIndex;
    public ArrayList<String> mBackStackStateKeys = new ArrayList<>();
    public ArrayList<BackStackState> mBackStackStates = new ArrayList<>();
    public ArrayList<FragmentManager.LaunchedFragmentInfo> mLaunchedFragments;
    public String mPrimaryNavActiveWho = null;
    public ArrayList<String> mResultKeys = new ArrayList<>();
    public ArrayList<Bundle> mResults = new ArrayList<>();
    public ArrayList<FragmentState> mSavedState;

    public FragmentManagerState() {
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mSavedState);
        parcel.writeStringList(this.mActive);
        parcel.writeStringList(this.mAdded);
        parcel.writeTypedArray(this.mBackStack, i);
        parcel.writeInt(this.mBackStackIndex);
        parcel.writeString(this.mPrimaryNavActiveWho);
        parcel.writeStringList(this.mBackStackStateKeys);
        parcel.writeTypedList(this.mBackStackStates);
        parcel.writeStringList(this.mResultKeys);
        parcel.writeTypedList(this.mResults);
        parcel.writeTypedList(this.mLaunchedFragments);
    }

    public FragmentManagerState(Parcel parcel) {
        this.mSavedState = parcel.createTypedArrayList(FragmentState.CREATOR);
        this.mActive = parcel.createStringArrayList();
        this.mAdded = parcel.createStringArrayList();
        this.mBackStack = (BackStackRecordState[]) parcel.createTypedArray(BackStackRecordState.CREATOR);
        this.mBackStackIndex = parcel.readInt();
        this.mPrimaryNavActiveWho = parcel.readString();
        this.mBackStackStateKeys = parcel.createStringArrayList();
        this.mBackStackStates = parcel.createTypedArrayList(BackStackState.CREATOR);
        this.mResultKeys = parcel.createStringArrayList();
        this.mResults = parcel.createTypedArrayList(Bundle.CREATOR);
        this.mLaunchedFragments = parcel.createTypedArrayList(FragmentManager.LaunchedFragmentInfo.CREATOR);
    }
}
