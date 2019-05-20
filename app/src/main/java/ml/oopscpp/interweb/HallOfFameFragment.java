package ml.oopscpp.interweb;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HallOfFameFragment extends Fragment {

    public HallOfFameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hall_of_fame, container, false);

        rootView.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        return rootView;
    }

}
