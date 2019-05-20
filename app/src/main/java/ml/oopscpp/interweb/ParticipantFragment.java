package ml.oopscpp.interweb;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;


public class ParticipantFragment extends Fragment {

    private ParticipantAdapter adapter;
    private ListView participantList;
    private ArrayList<Participant> participants;

    public ParticipantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set Toolbar's title
        Objects.requireNonNull(getActivity()).setTitle("Participants");

        if(getActivity() instanceof SelectParticipant){
            getActivity().setTitle("Select Participants");
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_participant, container, false);

        participants = new ArrayList<>();
        participantList  = rootView.findViewById(R.id.participantList);
        participantList.setNestedScrollingEnabled(true);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("Main",dataSnapshot.getValue(Participant.class).toString());
                Participant newParticipant = dataSnapshot.getValue(Participant.class);
                if(newParticipant != null)
                participants.add(newParticipant);
                if(participants!=null && getContext() != null)
                adapter = new ParticipantAdapter(getContext(), participants);
                // Attach the adapter to a ListView
                participantList.setAdapter(adapter);
                addOnClickListenerToListViewItem();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Participant newParticipant = dataSnapshot.getValue(Participant.class);
                if(newParticipant != null)
                participants.add(newParticipant);
                if(participants!=null && getContext() != null)
                adapter = new ParticipantAdapter(getContext(), participants);
                // Attach the adapter to a ListView
                participantList.setAdapter(adapter);
                addOnClickListenerToListViewItem();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Participant newParticipant = dataSnapshot.getValue(Participant.class);
                if(newParticipant != null)
                participants.add(newParticipant);
                if(participants!=null && getContext() != null)
                adapter = new ParticipantAdapter(getContext(), participants);
                // Attach the adapter to a ListView
                participantList.setAdapter(adapter);
                addOnClickListenerToListViewItem();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Participant newParticipant = dataSnapshot.getValue(Participant.class);
                if(newParticipant != null)
                participants.add(newParticipant);
                if(participants!=null && getContext() != null)
                adapter = new ParticipantAdapter(getContext(), participants);
                // Attach the adapter to a ListView
                participantList.setAdapter(adapter);
                addOnClickListenerToListViewItem();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        DatabaseReference mEventsDatabase = mDatabase.child("participants");
        mEventsDatabase.addChildEventListener(childEventListener);

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
        if(getActivity() instanceof SelectParticipant){
            participantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Participant currentParticipant = adapter.getItem(position);
                    Toast.makeText(getContext(), Objects.requireNonNull(currentParticipant).getParticipantName(), Toast.LENGTH_SHORT).show();
                    SelectParticipant parent = (SelectParticipant) getActivity();
                    parent.returnResult(currentParticipant.getParticipantName(),currentParticipant.getParticipantContact());
                }
            });
        }
        else {
            participantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Participant currentParticipant = adapter.getItem(position);
                    Toast.makeText(getContext(), Objects.requireNonNull(currentParticipant).getParticipantName(), Toast.LENGTH_SHORT).show();
                    Intent detail = new Intent(getContext(),DetailParticipant.class);
                    detail.putExtra("details",currentParticipant);
                    startActivity(detail);
                }
            });
        }

    }

}
