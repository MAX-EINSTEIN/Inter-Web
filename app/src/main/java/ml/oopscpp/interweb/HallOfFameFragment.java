package ml.oopscpp.interweb;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class HallOfFameFragment extends Fragment {

    private RecyclerView mWinnersList;
    private WinnerAdapter mWinnerAdapter;
    private ArrayList<Winner> mEventWinners;
    private ArrayList<String> mKeys;

    public HallOfFameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Hall of Fame");

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.fab.setVisibility(View.INVISIBLE);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hall_of_fame, container, false);

        mEventWinners = new ArrayList<>();
        mKeys = new ArrayList<>();

        mWinnersList = rootView.findViewById(R.id.winnersList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mWinnersList.setLayoutManager(layoutManager);

        WinnerViewModel viewModel = ViewModelProviders.of(this).get(WinnerViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                Log.e("Hall Of Fame",dataSnapshot.getValue(Winner.class).toString());
                Winner newWinner = dataSnapshot.getValue(Winner.class);

                String key = dataSnapshot.getKey();

                if(mKeys.contains(key)){
                    int index = mKeys.indexOf(key);
                    mEventWinners.set(index, newWinner);
                }else{
                    mKeys.add(key);
                    if(newWinner != null)
                        mEventWinners.add(newWinner);
                }

                if(mEventWinners!=null){
                    mWinnerAdapter = new WinnerAdapter(mEventWinners);
                }
                // Attach the adapter to a ListView
                mWinnersList.setAdapter(mWinnerAdapter);
            }
        });

        return rootView;
    }

}

