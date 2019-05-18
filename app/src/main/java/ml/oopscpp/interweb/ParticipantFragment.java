package ml.oopscpp.interweb;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;


public class ParticipantFragment extends Fragment {

    private ParticipantAdapter adapter;
    private ListView participantList;

    public ParticipantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set Toolbar's title
        Objects.requireNonNull(getActivity()).setTitle("Participants");

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_participant, container, false);

        participantList  = rootView.findViewById(R.id.participantList);
        ArrayList<Participant> participants = new ArrayList<>();
        Participant test = new Participant(R.mipmap.school_medium,"Hogwarts International","9211420420");
        Participant test1 = new Participant(R.mipmap.school_medium,"Oxford University","9625845424");
        participants.add(test);
        participants.add(test1);
        adapter = new ParticipantAdapter(getContext(), participants);
        participantList.setAdapter(adapter);

        if(getActivity() instanceof NewEvent){
            getActivity().setTitle("Select Participant");
            addOnClickListenerToListViewItem();
        }

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

    private void addOnClickListenerToListViewItem(){
        participantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Participant currentParticipant = adapter.getItem(position);
                Toast.makeText(getContext(), Objects.requireNonNull(currentParticipant).getParticipantName(), Toast.LENGTH_SHORT).show();
                NewEvent parentActivity = (NewEvent) getActivity();
                for (Fragment fragment: Objects.requireNonNull(parentActivity).getSupportFragmentManager().getFragments())
                    if(fragment!=null) parentActivity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                parentActivity.setTitle("Add New Event");
                parentActivity.setNewEventLayout();
            }
        });
    }

}
