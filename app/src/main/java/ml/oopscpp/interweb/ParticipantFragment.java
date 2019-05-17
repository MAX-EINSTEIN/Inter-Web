package ml.oopscpp.interweb;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ParticipantFragment extends Fragment {

    private ParticipantAdapter adapter;
    private ArrayList<Participant> participants;
    private ListView participantList;

    public ParticipantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set Toolbar's title
        getActivity().setTitle("Participants");

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_participant, container, false);

        participantList  = rootView.findViewById(R.id.participantList);
        participants = new ArrayList<>();
        Participant test = new Participant(R.mipmap.school_medium,"Hogwarts International","9211420420");
        Participant test1 = new Participant(R.mipmap.school_medium,"Oxford University","9625845424");
        participants.add(test);
        participants.add(test1);
        adapter = new ParticipantAdapter(getContext(),participants);
        participantList.setAdapter(adapter);

        return rootView;
    }


    @Override
    public void onResume()
    {
        super.onResume();

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
