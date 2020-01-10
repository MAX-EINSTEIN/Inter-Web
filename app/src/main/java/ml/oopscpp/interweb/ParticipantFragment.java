package ml.oopscpp.interweb;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class ParticipantFragment extends Fragment {

    private ParticipantAdapter mParticipantAdapter;
    private RecyclerView mParticipantList;
    private ArrayList<Participant> mParticipants;
    private ArrayList<String> mKeys;

    public ParticipantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set Toolbar's title
        Objects.requireNonNull(getActivity()).setTitle("Participants");

        if(getActivity() instanceof MainActivity){
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.fab.setVisibility(View.VISIBLE);
        }

        if(getActivity() instanceof SelectParticipant){
            getActivity().setTitle("Select Participants");
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_participant, container, false);

        mParticipants = new ArrayList<>();
        mKeys = new ArrayList<>();

        mParticipantList = rootView.findViewById(R.id.participantList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mParticipantList.setLayoutManager(layoutManager);

        ParticipantViewModel viewModel = ViewModelProviders.of(this).get(ParticipantViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                Log.e("Main", Objects.requireNonNull(dataSnapshot.getValue(Participant.class)).toString());
                Participant newParticipant = dataSnapshot.getValue(Participant.class);

                String key = dataSnapshot.getKey();

                if(mKeys.contains(key)){
                    int index = mKeys.indexOf(key);
                    mParticipants.set(index, newParticipant);
                }else{
                    mKeys.add(key);
                    if(newParticipant != null)
                        mParticipants.add(newParticipant);
                }

                if(mParticipants!=null){
                    mParticipantAdapter = new ParticipantAdapter(mParticipants);
                }

                mParticipantList.setAdapter(mParticipantAdapter);
            }
        });

        return rootView;
    }


    @Override
    public void onResume()
    {
        super.onResume();

        if(getActivity() instanceof MainActivity){
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Do what you want
                    Toast.makeText(getContext(),"New Participant",Toast.LENGTH_SHORT).show();
                    Intent newParticipant = new Intent(getActivity(),NewParticipant.class);
                    startActivity(newParticipant);
                }
            });
        }

    }

}
